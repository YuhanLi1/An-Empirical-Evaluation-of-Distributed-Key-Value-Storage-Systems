#!/bin/bash

# run all the experiments once.
# and fetch the results.

cd /home/ubuntu/group-5/project/memcached/
bash scripts/clearResult.sh

expTypes='latency throughput'
numNodes='1 2 4 8'
expSizes='100k'

echo "killing zombie javas"
bash scripts/killjavas.sh

echo "***Starting experiments..."
for nodes in $numNodes
do
    for expType in $expTypes
    do
	for expSize in $expSizes
	do
	    echo $expType $nodes $expSize
	    bash scripts/nodeExp.sh $expType $nodes $expSize
	done
    done
done

echo "***Experiments done."
echo "***Fetching results..."
bash scripts/fetchData.sh
echo "***Results fetched. (~/group-5/project/data/memcached)"

echo "***DONE***"
