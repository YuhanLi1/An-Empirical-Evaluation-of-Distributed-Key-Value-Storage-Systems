#!/bin/bash

# kill all the stranded java processes in nodes and server
LOCAL_PROJECT_DIR=/home/cc/group-5/project
hostlist=$LOCAL_PROJECT_DIR/nList.config
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $HOST:$DEST
    ssh -i ~/.ssh/id_rsa -l ubuntu -n $HOST "
    killall -9 java
"
done < "$hostlist"

echo "javas eliminated"
