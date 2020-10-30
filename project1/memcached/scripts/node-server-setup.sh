#!/bin/bash

parallel-ssh -h ../pssh-hosts -P -v -x "-t -t -i ~/.ssh/id_rsa -l ubuntu" "
	     sudo service memcached stop;
	     killall -9 memcached;
	     memcached -d -p 11211;
"
echo "Memcached server nodes restarted."
