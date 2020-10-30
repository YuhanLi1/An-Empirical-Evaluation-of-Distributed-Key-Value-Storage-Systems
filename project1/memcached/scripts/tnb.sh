#!/bin/bash

LOCAL_PROJECT_DIR='/home/cc/group-5/project'
# node hostname assigned nodeIds, 1-8
hostlist=$LOCAL_PROJECT_DIR/nList.config

DEST="/home/ubuntu/"

echo "maven packaging memcached-project.."
cd $LOCAL_PROJECT_DIR/memcached/memcached-project
mvn package
echo "maven packaging done."

echo "rsync memcached-project directory to nodes.."
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $HOST:$DEST
    rsync -az -e "ssh -i ~/.ssh/id_rsa -l ubuntu" $LOCAL_PROJECT_DIR $HOST:$DEST
    ssh -i ~/.ssh/id_rsa -l ubuntu -n $HOST "
    cd $DEST/project;         
    echo $nodeId > nodeId;
    cat nodeId;
"        
done < "$hostlist"
echo "rsync and peer.config done."

cd $LOCAL_PROJECT_DIR
cat $hostlist | cut -d' ' -f2 > pssh-hosts
echo "pssh-hosts file created."
echo "***tnb.sh done."

