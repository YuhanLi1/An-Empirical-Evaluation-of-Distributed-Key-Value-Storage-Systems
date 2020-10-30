import riak
import random
import string
from datetime import datetime as dt
import statistics

NUM_OPS = 100000
IP_ADDR = '192.168.122.140'


def run_exp_throughput(myBucket, pairs):
    num = len(pairs)

    start = dt.now()
    for i in range(0, 100):
        print("> Running insert: " + str(i) + "% \r")
        j = 0
        while j < (num / 100):
            val = pairs[i * (num // 100)][10:]
            key = myBucket.new(pairs[i * (num // 100) + j][:10], data=val)
            key.store()
            #rv = myBucket.get(pairs[i * (num // 100) + j][:10])
            #rv.delete()
            j += 1

    for i in range(0, 100):
        print("> Running get, and delete experiment: " + str(i) + "% \r")
        j = 0
        while j < (num / 100):
            rv = myBucket.get(pairs[i * (num // 100) + j][:10])
            rv.delete()
            j += 1
    end = dt.now()

    delta = end - start
    delta_sec = delta.seconds + delta.microseconds / 1000000
    print("> Clapsed time! " + str(delta_sec) + " seconds")
    print("> Total Throughput " + str(3 * NUM_OPS / delta_sec) + " OPs/s")
    tp = 3 * NUM_OPS / delta_sec
    return tp


def run_exp_latency(myBucket, pairs):
    num = len(pairs)
    delta = []
    for i in range(0, 100):
        print("> Running insert, get, and delete experiment: " + str(i) + "% \r")
        j = 0
        while j < (num / 100):
            val = pairs[i * (num // 100)][10:]
            key = myBucket.new(pairs[i * (num // 100) + j][:10], data=val)
            start_in = dt.now()
            key.store()
            end_in = dt.now()
            delta_in = end_in - start_in

            start_get = dt.now()
            rv = myBucket.get(pairs[i * (num // 100) + j][:10])
            end_get = dt.now()
            delta_get = end_get - start_get

            start_det = dt.now()
            rv.delete()
            end_det = dt.now()
            delta_det = end_det - start_det
            delta.append(float(delta_in.microseconds * 0.001))
            delta.append(float(delta_get.microseconds * 0.001))
            delta.append(float(delta_det.microseconds * 0.001))
            j += 1
    print("> Total Latency " + str(statistics.mean(delta)) + " ms")
    la = statistics.mean(delta)
    return la


def generate_pair(size, alphabet):
    res = ''
    for _ in range(0, size):
        res += random.choice(alphabet)
    return res


def run_exp_init(num):
    pairs = []
    alphabet = string.ascii_uppercase + string.ascii_lowercase + string.digits

    for i in range(0, 100):
        #print("> Initializing random key-value pairs: " + str(i) + "% \r", end='')
        j = 0
        while j < (num / 100):
            pair = generate_pair(100, alphabet)
            if pair not in pairs:
                pairs.append(pair)
                j += 1

    print("> Initializing random key-value pairs: " + "100% " + "...completed.")
    return pairs


def main():
    # Connect to Riak.
    myClient = riak.RiakClient()
    myClient = riak.RiakClient(protocol='http', host=IP_ADDR, http_port=8098)
    #myClient = riak.RiakClient(nodes=[{'host': IP_ADDR, 'http_port': 8098}])
    # myClient = riak.RiakClient(protocol='http', nodes=[riak.RiakNode()]

    myBucket = myClient.bucket('test')

    pairs = run_exp_init(NUM_OPS)
    tp = run_exp_throughput(myBucket, pairs)
    la = run_exp_latency(myBucket, pairs)
    print("> Total Throughput " + str(tp) + " OPs/s")
    print("> Total Latency " + str(la) + " ms")


if __name__ == "__main__":
    main()
