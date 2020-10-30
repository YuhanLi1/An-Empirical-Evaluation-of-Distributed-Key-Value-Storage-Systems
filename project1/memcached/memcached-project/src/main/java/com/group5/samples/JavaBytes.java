package com.group5.samples;

import java.util.Random;
import java.lang.*;

public class JavaBytes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rd = new Random();
		byte[] key = new byte[10];
		rd.nextBytes(key);
		System.out.println(key);
		System.out.println(new String(key));
		
		
		long[] a = new long[7];
		
		System.out.println(a.length);
	}

}
