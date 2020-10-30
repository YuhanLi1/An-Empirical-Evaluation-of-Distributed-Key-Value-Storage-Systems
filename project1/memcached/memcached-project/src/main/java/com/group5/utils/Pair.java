package com.group5.utils;

public class Pair<T1,T2> {
	T1 key;
	T2 value;
	
	public Pair(T1 k, T2 v){
		key = k;
		value = v;
	}
	public T1 getKey() {
		return key;
	}
	public T2 getValue() {
		return value;
	}
}
