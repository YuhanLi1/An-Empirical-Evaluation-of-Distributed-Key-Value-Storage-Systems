#!/bin/bash

rm -rf tmp.txt
for i in {1..8}
do
    cat ./bmset2/node$i.result.txt >> tmp.txt
done

sort -k4 tmp.txt > out.txt
rm tmp.txt
