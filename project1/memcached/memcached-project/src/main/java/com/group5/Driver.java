package com.group5;

import java.io.BufferedReader;
import java.io.FileReader;
/* 0:[nodeId filepath]
 * 1:[nodelist filepath]
 * 2:[expType, insert/lookup/delete]
 * 3:[number of active nodes 1/2/4/8]
 * 4:[size of experiment 1k/10k/100k]
 * 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.group5.utils.Logger;
import com.group5.utils.NodeListUtils;
import com.group5.utils.Pair;
import com.group5.utils.ResultStamper;

import net.spy.memcached.*;

public class Driver {
	static int MAXNODES = 8;
	static int nodeId;
	static String nodelistPath;
	static int expType;
	static int numNodes;
	static String expSizeStr;
	static int expSize;
	static long timeout;
	
	static MemcachedClient mcc;
	
	static Random rd = new Random(nodeId);
	static long[] add_data;
	static long[] get_data;
	static long[] del_data;
	
	static Logger l;
	public static void main(String[] args) throws IOException {
		
		if(parseArgs(args) == -1) {
			l.log("Driver parsing failed.");
			return;
		}
		rd.setSeed(nodeId); // set seed
		l = new Logger(nodeId);
		if(nodeId > numNodes) {
			System.out.printf("==Node(%d) Idle.==\n", nodeId);
			return;
		}
		String nodeStr = NodeListUtils.getString(nodelistPath, 11211, numNodes);
		// Start a memcached client
		mcc = new MemcachedClient(new BinaryConnectionFactory(), 
				AddrUtil.getAddresses(nodeStr));
		
		
		l.log("calculating for [" + expType + "]...");
		switch(expType) {
			case 0: // latency
				runExp();
				latency();
				break;
			case 1: // throughput
				throughput();
				break;
			case 2: // both
				
				latency();
				throughput();
		}
		mcc.shutdown(timeout, TimeUnit.MILLISECONDS);
		
		l.close();
	}
	
	public static ArrayList<Pair<String, byte[]>> createKVPairs(){
		ArrayList<Pair<String, byte[]>> kvPairs = new ArrayList<Pair<String,byte[]>>();
		for(int i = 0; i < expSize; i++) {
			kvPairs.add(randPair());
		}
		return kvPairs;
	}
	
	public static void runExp() {
		l.log(String.format("runExp Starting..."));
		add_data = new long[expSize];
		get_data = new long[expSize];
		del_data = new long[expSize];
		ArrayList<Pair<String, byte[]>> kvPairs = createKVPairs();
		long startTime;
		int i;
		i = 0;
		for(Pair<String, byte[]> kvp: kvPairs) {
			startTime = System.currentTimeMillis();
			mcc.add(kvp.getKey(), 600, kvp.getValue());
			add_data[i] = System.currentTimeMillis() - startTime;
			i++;
		}
		
		i = 0;
		int matchCount = 0;
		byte[] ret;
		for(Pair<String, byte[]> kvp: kvPairs) {
			startTime = System.currentTimeMillis();
			ret = (byte[]) mcc.get(kvp.getKey());
			get_data[i] = System.currentTimeMillis() - startTime;
			i++;
			if(Arrays.equals(kvp.getValue(), ret)) {
				matchCount += 1;
			}
		}
		if(matchCount != expSize) {
			l.log(String.format("Lookup mismatch - incorrect matches: %d", expSize-matchCount));
		}
		
		i = 0;
		for(Pair<String, byte[]> kvp: kvPairs) {
			startTime = System.currentTimeMillis();
			mcc.delete(kvp.getKey());
			del_data[i] = System.currentTimeMillis() - startTime;
			i++;
		}

		l.log(String.format("runExp Done. Data store in static array."));
		
		return;
	}
	
	public static void cal_latency() {
		// calculate average and stamp
		double add_res = 0;
		double get_res = 0;
		double del_res = 0;
		for(int i = 0; i < expSize; i++) {
			add_res += (double) add_data[i];
			get_res += (double) get_data[i];
			del_res += (double) del_data[i];
		}
		double avg_res = (add_res + get_res + del_res)/(3*expSize);
		ResultStamper rs = new ResultStamper();
		rs.stampRaw(nodeId, "latency[ms/op]", numNodes, expSizeStr, avg_res);
		
		return;
	}
	
	public static void latency() {
		runExp();
		cal_latency();
		return;
	}
	
	public static void throughput() {
		// create a list of key-value pairs to be inserted
		ArrayList<Pair<String, byte[]>> kvPairs = new ArrayList<Pair<String,byte[]>>();
		for(int i = 0; i < expSize; i++) {
			kvPairs.add(randPair());
		}
		long time = System.currentTimeMillis();
		l.log(String.format("workload(size=%d) generated, starting insert...", kvPairs.size()));
		for(Pair<String, byte[]> kvp: kvPairs) {
			mcc.add(kvp.getKey(), 600, kvp.getValue());
		}
		
		l.log(String.format("Insert done, starting random lookup..."));
		Pair<String,byte[]> kvp1;
		int index;
		for(int i = 0; i < expSize; i++) {
			index = rd.nextInt(expSize); // draw a random key from previously inserted pair
			kvp1 = kvPairs.get(index);
			mcc.get(kvp1.getKey());
		}
		
		l.log(String.format("Random lookup done, starting delete..."));
		for(Pair<String,byte[]> kvp2: kvPairs) {
			mcc.delete(kvp2.getKey());
		}
		time = System.currentTimeMillis() - time;
		double res = (3*expSize*1000)/time;
		ResultStamper rs = new ResultStamper();
		rs.stampRaw(nodeId, "throughput[ops/s]", numNodes, expSizeStr, res);
		return;
		
	}
	
	public static Pair<String, byte[]> randPair(){
		// create a key-value pair, 10bytes key, 90bytes value 
		byte[] key, val;
		key = new byte[10-1];
		val = new byte[90-1];
		rd.nextBytes(key);
		rd.nextBytes(val);
		return new Pair<String, byte[]>(key.toString(), val);
	}

	public static int parseArgs(String[] args) {
		if(args.length != 5) {
			return -1;
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String line = reader.readLine();
			nodeId = Integer.parseInt(line);
			reader.close();
		}catch (Exception e) {
			return -1;
		}
		
		if(nodeId <= 0 || nodeId > MAXNODES) {
			return -1;
		}
		nodelistPath = args[1];
		if(args[2].compareTo("latency") == 0) {
			expType = 0;
		}else if(args[2].compareTo("throughput") == 0) {
			expType = 1;
		}else if(args[2].compareTo("both") == 0){
			expType = 2;
		}else {
			return -1;
		}
		numNodes = Integer.parseInt(args[3]);
		
		expSizeStr = args[4];
		if(args[4].compareTo("1k") == 0) {
			expSize = 1000;
		}else if(args[4].compareTo("10k") == 0) {
			expSize = 10000;
		}else if(args[4].compareTo("100k") == 0) {
			expSize = 100000;
		}else if(args[4].compareTo("1m") == 0) {
			expSize = 1000000;
		}else {
			return -1;
		}
		int factor = 1000;
		timeout = numNodes * factor;
		
		return 0;
		
	}

}
