#!/bin/bash

cd ~/group-5/pa2/

for ((i=1;i<=5;i++)); 
do 
    bash scripts/expOnce.sh
    mv data data-store/repSet$i
done

echo "*****REP EXP DONE. CHECK DIR=data-store"
