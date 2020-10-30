package com.group5.samples;

import java.util.Random;

public class StringToBytes {

	public static void main(String[] args) {
		String s1 = "12345";
		
		System.out.println(s1.getBytes().length); // length = 5
		
		String s2 = "abcde12345";
		
		System.out.println(s2.getBytes().length); // length = 10;
		
		Random rd = new Random();
		
		byte[] ba = new byte[10];
		rd.nextBytes(ba);
		
		System.out.println(ba.length);
		System.out.println(ba.toString().getBytes().length);
		
	}

}
