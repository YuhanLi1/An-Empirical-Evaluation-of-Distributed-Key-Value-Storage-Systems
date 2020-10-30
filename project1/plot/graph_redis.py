import matplotlib.pyplot as plt
import pandas as pd

throughput = {'throughput_mem':[32794, 38938, 51998, 90099], 'throughput_redis':[8088, 8558, 12792, 22336],
              'throughput_riak':[309, 507, 683, 723], 'throughput_mem_paper':[7385, 7961, 14480, 40995],
              'throughput_ZHT':[4117, 5524, 9813, 18680], 'nodes':[1, 2, 4, 8]}
throughput = pd.DataFrame(throughput)
latency = {'latency_men':[0.0301, 0.0584, 0.0698, 0.0908], 'latency_redis':[0.1331, 0.2560, 0.3232, 0.3706],
           'latency_riak':[3.182, 3.771, 5.843, 10.516], 'latency_mem_paper':[0.122, 0.324, 0.272, 0.278],
           'latency_ZHT':[0.243, 0.362, 0.408, 0.428], 'nodes':[1, 2, 4, 8]}
latency = pd.DataFrame(latency)

#throughput mem redis rack mem_paper zht
plt.figure(figsize=(9,9))
plt.plot(throughput['nodes'], throughput['throughput_mem'], '-p', color='gray',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='blue',
         markeredgewidth=2, label = 'Memcached')
plt.plot(throughput['nodes'], throughput['throughput_redis'], '-p', color='orange',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='black',
         markeredgewidth=2, label = 'Redis')
plt.plot(throughput['nodes'], throughput['throughput_riak'], '-p', color='green',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='red',
         markeredgewidth=2, label = 'Riak')
plt.plot(throughput['nodes'], throughput['throughput_mem_paper'], '-p', color='yellow',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='purple',
         markeredgewidth=2, label = 'Memcached(paper)')
plt.plot(throughput['nodes'], throughput['throughput_ZHT'], '-p', color='blue',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='green',
         markeredgewidth=2, label = 'ZHT')

plt.grid(True)
plt.title('Throughput plot for all systems')
plt.xlabel('Number of nodes')
plt.ylabel('ops/s')
plt.legend(title = 'Throughput', loc =2, bbox_to_anchor=(0.0, 1.00), fontsize = 14)
plt.show()

#latnecy mem redis rack mem_paper
plt.figure(figsize=(9,9))
plt.plot(latency['nodes'], latency['latency_men'], '-p', color='gray',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='blue',
         markeredgewidth=2, label = 'Memcached')
plt.plot(latency['nodes'], latency['latency_redis'], '-p', color='orange',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='black',
         markeredgewidth=2, label = 'Redis')
# plt.plot(latency['nodes'], latency['latency_riak'], '-p', color='green',
#          markersize=10, linewidth=4,
#          markerfacecolor='white',
#          markeredgecolor='red',
#          markeredgewidth=2, label = 'Riak')
plt.plot(latency['nodes'], latency['latency_mem_paper'], '-p', color='yellow',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='purple',
         markeredgewidth=2, label = 'Memcached(paper)')
plt.plot(latency['nodes'], latency['latency_ZHT'], '-p', color='blue',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='green',
         markeredgewidth=2, label = 'ZHT')

plt.grid(True)
plt.title('Latency plot for all systems(except rick)')
plt.xlabel('Number of nodes')
plt.ylabel('ms/op')
plt.legend(title = 'Latency', loc =2, bbox_to_anchor=(0.0, 1.00), fontsize = 14)
plt.show()

#latnecy mem redis mem_paper
plt.figure(figsize=(9,9))
plt.ylim(0, 0.47)
plt.plot(latency['nodes'], latency['latency_men'], '-p', color='gray',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='blue',
         markeredgewidth=2, label = 'Memcached')
plt.plot(latency['nodes'], latency['latency_redis'], '-p', color='orange',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='black',
         markeredgewidth=2, label = 'Redis')
plt.plot(latency['nodes'], latency['latency_mem_paper'], '-p', color='yellow',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='purple',
         markeredgewidth=2, label = 'Memcached(paper)')
plt.plot(latency['nodes'], latency['latency_ZHT'], '-p', color='blue',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='green',
         markeredgewidth=2, label = 'ZHT')
plt.grid(True)
plt.title('Latency plot (without Riak)')
plt.xlabel('Number of nodes')
plt.ylabel('ms/op')
plt.legend(title = 'Latency', loc =2, bbox_to_anchor=(0.0, 1.00), fontsize = 14)
plt.show()

#throughput redis
plt.figure(figsize=(9,9))
plt.plot(throughput['nodes'], throughput['throughput_redis'], '-p', color='green',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='red',
         markeredgewidth=2, label = 'redis')
plt.grid(True)
plt.title('Throughput plot for redis')
plt.xlabel('Number of nodes')
plt.ylabel('ops/s')
plt.legend(title = 'Throughput', loc =2, bbox_to_anchor=(0.0, 1.00), fontsize = 14)
plt.show()

#rredis latency
plt.figure(figsize=(9,9))
plt.plot(latency['nodes'], latency['latency_redis'], '-p', color='green',
         markersize=10, linewidth=4,
         markerfacecolor='white',
         markeredgecolor='red',
         markeredgewidth=2, label = 'redis')
plt.grid(True)
plt.title('Latency plot for redis')
plt.xlabel('Number of nodes')
plt.ylabel('ms/op')
plt.legend(title = 'Latency', loc =2, bbox_to_anchor=(0.0, 1.00), fontsize = 14)
plt.show()

