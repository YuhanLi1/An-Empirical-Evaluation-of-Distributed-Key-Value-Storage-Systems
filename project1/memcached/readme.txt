# Memcached benchmark readme.txt

# Dependencies:
# maven, memcached, pssh, rsync
# which all can be installed with apt-get install.

# Before running any scripts in the project/memcached/scripts directory,
# make sure the config file "group-5/project/nList.config" has the correct ip addresses
format:
[nodeid] [ip address]
...

# Notes on re-running the experiments:

# 1. connect to the baremetal machine by
ssh -i ~/group-5/project/keypairs/gp5-keypair1.pem cc@129.114.108.28

# 2. cd to memcached directory
cd group-5/project/memcached

# 3. package the project and transfer to nodes with rsync
bash scripts/tnb.sh

# 4. restart the memcached servers on nodes
bash scripts/node-server-setup.sh

# 5. run the experiment by
bash scripts/expOnce.sh
# DONE.

# Read the results in ~/group-5/project/data/memcached directory

# Notes on benchmark results:
# Previously ran experiment raw result files are stored in separated directories
# - set1 to set5 are the oldest, with varying number of experiment sizes
# - fset1 and fset2 are ran on m1.mediums vms on the new kvm cluster
# - bmset1 and bmset2 are ran on baremetal machine vms, with specs identical to pa2

# The (manually) calculated results can be viewed in the files:
# "result.medium.out" and "result.bm.out"

# (Optional)
# 6.1 Individual experiments can be ran with
bash scripts/nodeExp.sh [latency/throughput] [1/2/4/8] [1k/10k/100k/1m]
# which appends the experiment results to the result.txt file on each vm
# 6.2 The result.txt(s) can be fetched using scp with
bash scripts/fetchData.sh
# which puts the result files in dir: ~/group-5/project/data/memcached


##################################################
# notes on specific commands:
ssh to nodes
ssh ubuntu@192.168.5.11

start a memcached server with default ports
memcached -d -p 11211

To shutdown memcache daemon:
sudo service memcached stop

You can see if Memcache is currently runing:
ps aux | grep memcached

maven
to compile and package
mvn package

mvn exec:java -D exec.mainClass=<your main class>

e.g. mvn exec:java -D exec.mainClass=com.group5.Node
test and connects to a local memcached server

mvn exec:java -Dexec.mainClass=com.group5.Driver -Dexec.args="/media/justin/sp/group-5/project/memcached/nodeId /media/justin/sp/group-5/project/memcached/nList.config delete 1 100k"
 proper args for driver

disable sudo passwd requirement:
sudo vim /etc/sudoers.d/group5
  group5 ALL = (root) NOPASSWD:ALL

sudo chmod 0440 /etc/sudoers.d/group5

