package com.b5m.test;

import java.util.StringTokenizer;

public class Test {
	
	static String a = "0;  1;         1;  3;     0.1;  0.1;     0;    61;   0.1;";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		StringTokenizer tokenizer = new StringTokenizer(a,";");
		String[] tokenizer = a.split(";");
		System.out.println(tokenizer[1]);
	}

}
