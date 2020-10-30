#!/bin/bash
# A binder script to call the driver on all the nodes

# Experiment parameters
nodeIdfp=/home/ubuntu/project/nodeId
nListfp=/home/ubuntu/project/nList.config
# Arguments
expType=$1
numNodes=$2
expSize=$3

arg="$nListfp $expType $numNodes $expSize"

parallel-ssh -h ../pssh-hosts -P -x "-i ~/.ssh/id_rsa -l ubuntu" -i "
	     cd project/memcached/memcached-project;
	     mvn exec:java -Dexec.mainClass=com.group5.Driver -Dexec.args='$nodeIdfp $nListfp $expType $numNodes $expSize';
	     
"
