#!/bin/bash

parallel-ssh -h ../pssh-hosts -x "-i ~/.ssh/id_rsa -l ubuntu" "rm /home/ubuntu/project/memcached/memcached-project/result.txt"

echo "***result.txt deleted"
