#!/bin/bash

LOCAL_PROJECT_DIR='/home/cc/group-5/project'
# node hostname assigned nodeIds, 1-8
hostlist=$LOCAL_PROJECT_DIR/nList.config
KEYPATH=~/.ssh/id_rsa
USERNAME=ubuntu

DEST="/home/ubuntu/"
echo "rsync project directory to nodes.."
while IFS= read -r line
do
    HOST=$(echo $line | cut -d' ' -f2)
    nodeId=$(echo $line | cut -d' ' -f1)
    echo $HOST:$DEST
    ssh -i  $KEYPATH  -l $USERNAME -n $HOST "rm -rf project"
    rsync -az -e "ssh -i $KEYPATH -l $USERNAME" $LOCAL_PROJECT_DIR $HOST:$DEST
    ssh -i $KEYPATH -l $USERNAME -n $HOST "
    cd $DEST/project;         
    echo $nodeId > nodeId;
    cat nodeId;
"        
done < "$hostlist"
echo "rsync and nodeId done."

cd $LOCAL_PROJECT_DIR
cat $hostlist | cut -d' ' -f2 > pssh-hosts
echo "pssh-hosts file created."
echo "***sync.sh done."
