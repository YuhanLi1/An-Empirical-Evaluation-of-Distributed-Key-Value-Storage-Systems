#!/bin/bash

cd /home/cc/group-5/project
mkdir data; mkdir data/memcached;
hostlist=nList.config
user=ubuntu
RESULT_PATH=/home/ubuntu/project/memcached/memcached-project/result.txt
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)                                                                
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $user@$HOST
    scp -i ~/.ssh/id_rsa $user@$HOST:$RESULT_PATH data/memcached/node$nodeId.result.txt
done < $hostlist

