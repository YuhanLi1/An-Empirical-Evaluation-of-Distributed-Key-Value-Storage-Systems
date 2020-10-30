package com.group5.utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultStamper {
	public static String fp = "./result.txt";
	public Date date;
	SimpleDateFormat formatter;
	
	public ResultStamper() {
		this.date = new Date();
		this.formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	}
	public void stampAnnotate(int nodeId, String expType, int numNodes, String expSize, double time) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fp, true));
			bw.append(String.format("[%s] PeerID - %d, Exp.Type - %s, NumNodes - %d, expSize - %s, time - %.4f (ms)\n", 
					formatter.format(date), nodeId, expType, numNodes, expSize, time));
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stampRaw(int nodeId, String expType, int numNodes, String expSize, double time) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fp, true));
			bw.append(String.format("[%s] %d %s %d %s %.4f\n", 
					formatter.format(date), nodeId, expType, numNodes, expSize, time));
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}