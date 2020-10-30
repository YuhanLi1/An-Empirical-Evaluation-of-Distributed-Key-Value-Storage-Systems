package com.group5.utils;

public class NHPair {
	public int peerId;
	public String hostname;
	
	public NHPair(int id, String hn) {
		peerId = id;
		hostname = hn;
	}
	public String toString() {
		return String.format("{id=%d, hn=%s}", peerId, hostname);
	}
}