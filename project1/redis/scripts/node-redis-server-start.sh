#!/bin/bash

LOCAL_PROJECT_DIR='/home/cc/group-5/project'
hostlist=$LOCAL_PROJECT_DIR/nList.config
KEYPATH=~/.ssh/id_rsa
USERNAME=ubuntu

# number of nodes to start
numNodes=$1
arg=""
redisPort=6397

echo "Stopping all nodes."
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $HOST
    ssh -i $KEYPATH -l $USERNAME -n $HOST "
    	killall -9 redis-server;
 	rm -rf *.aof nodes.conf;
     "    
done < "$hostlist"

echo "Starting redis-server-nodes."
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $HOST    
    if [ $numNodes -eq 1 ]
    then
	echo "1 node only..."
	ssh -i $KEYPATH -l $USERNAME -n $HOST "
	    cd /home/ubuntu/project/;
	    redis-server /home/ubuntu/project/redis/redis-node/redis-single.conf; 
	"
	arg=$HOST:$redisPort
	break
    elif [ $numNodes -eq 2 ]
    then
	echo "2 nodes only..."
	if [ $nodeId -eq 1 ]; then
	    echo "starting master"
	    ssh -i $KEYPATH -l $USERNAME -n $HOST "
	  	cd /home/ubuntu/project/;
	    	redis-server /home/ubuntu/project/redis/redis-node/redis-double-master.conf;  
	    "
	    arg="(Master)"$HOST:$redisPort
	    continue
	elif [ $nodeId -eq 2 ]; then
	    echo "starting slave"
	    ssh -i $KEYPATH -l $USERNAME -n $HOST "
		cd /home/ubuntu/project/;
	    	redis-server /home/ubuntu/project/redis/redis-node/redis-double-slave.conf;  
	    "
	    arg=$arg" (Slave)"$HOST:$redisPort
	    break
	fi  
    fi
       
    if [ $nodeId -lt $(($numNodes+1)) ]
    then
	ssh -i $KEYPATH -l $USERNAME -n $HOST "
	    redis-server /home/ubuntu/project/redis/redis-node/redis.conf;
    	 "
	if [ $nodeId -eq 1 ]; then
       	    arg=$HOST:$redisPort;
	else
	    arg=$arg" "$HOST:$redisPort;
	fi
    fi    
done < "$hostlist"

echo "arg"
echo $arg
if [ $numNodes -eq 1 ]; then
    echo "(Solo) redis-server started on $arg"
elif [ $numNodes -eq 2 ]; then
    echo "(Master-slave) redis-server started on $arg"
else
    $LOCAL_PROJECT_DIR/redis/redis-trib.rb create --replicas 0 $arg
    echo "Redis cluster of ($numNodes)nodes started."
fi

