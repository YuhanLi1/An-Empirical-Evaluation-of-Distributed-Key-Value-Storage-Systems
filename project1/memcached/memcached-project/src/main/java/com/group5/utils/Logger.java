package com.group5.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static String fp = "./log.txt";
	public Date date;
	SimpleDateFormat formatter;
	public BufferedWriter bw;
	public int nodeId;
	
	
	public Logger(int nodeId) throws IOException {
		this.nodeId = nodeId;
		this.date = new Date();
		this.formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.bw = new BufferedWriter(new FileWriter(fp, true));
	}
	public void log(String msg) {
		try {
			bw.append(String.format("[%s] <node%d> %s\n", formatter.format(date), nodeId, msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Logger l = new Logger(1);
		l.log("LOG THIS");
		l.close();
	}
}
