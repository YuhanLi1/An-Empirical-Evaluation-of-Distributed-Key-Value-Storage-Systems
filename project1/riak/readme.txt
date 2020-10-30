#start riak
Sudo riak start

#stop riak
Sudo risk stop

#check riak cluster status
Sudo riak-admin cluster status

#join Riak cluster 
Sudo riak-admin cluster join riak@192.168.122.96 (server IP address)

#for each node (i) run
Python nodei.py 