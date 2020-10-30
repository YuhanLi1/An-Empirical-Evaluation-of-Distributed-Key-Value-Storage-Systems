package com.group5.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class NodeListUtils {
public static String NLIST_PATH = "/media/justin/sp/group-5/project/memcached/nList.config";
	
	public static ArrayList<NHPair> getList(String fp){
		BufferedReader reader = null;
		ArrayList<NHPair> l = new ArrayList<NHPair>();
		NHPair pair = null;
		try {
			reader = new BufferedReader(new FileReader(fp));
			String line = reader.readLine();
			while(line != null) {
				String[] tmp = line.split(" ");
				pair = new NHPair(Integer.parseInt(tmp[0]), tmp[1]);
				l.add(pair);
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	public static String getString(String fp, int port, int numNodes) {
		BufferedReader reader = null;
		ArrayList<NHPair> l = getList(fp);
		String s = "";
		for(NHPair nhp: l) {
			s = s + String.format("%s:%d", nhp.hostname, port);
			if(nhp.peerId >= numNodes) {
				break;
			}
			if(nhp.peerId < l.size()) {
				s = s + " ";
			}
		}
		
		return s;
	}

	public static void main(String[] args) {
		System.out.println(NodeListUtils.getList(NLIST_PATH));
		System.out.println(NodeListUtils.getString(NLIST_PATH, 11211, 3));
		
	}
}
