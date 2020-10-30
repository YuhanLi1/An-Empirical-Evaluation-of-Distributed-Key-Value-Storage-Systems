
~/group-5/project/redis$ ./redis-trib.rb create --replicas 0 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002


# 1. Connect to BM
ssh -i ~/sp/group-5/project/keypairs/gp5-keypair1.pem cc@129.114.108.28

ssh -i ~project/keypairs/gp5-keypair1.pem cc@129.114.108.28

# 2. Start/restart redis-server on 1/2/4/8 nodes
bash script/node-redis-server-start.sh 4

# 2.1 Validate the cluster is up by
redis-cli -h 192.168.122.96 -c -p 7000

-----------------------------------------------------------------
# Connect to vms
ssh -i ~/.ssh/id_rsa ubuntu@192.168.122.96
# ...etc

# Install ruby and redis with
sudo apt-get update
sudo apt install ruby
sudo apt install redis-server


# start redis nodes on machines
redis-server ./redis.conf

# start a redis cluster (on deploy machine)
./redis-trib.rb create --replicas 0 $HOST:$PORT ... ...

# connect to the cluster with cli
redis-cli -h 192.168.122.96 -c -p 7000


#run install-redis.sh to install redis and redis cluster in python
pip3 install redis
pip3 install redis-py-cluster



