package com.ashihara.utils;

/**
 * <p>Title: PswHasher</p>
 * <p>Description: Password Hashing</p>
 * <p>Copyright:  (c)Copyright RTU 2005</p>
 * <p>Company: RTU </p>
 * @author Egons Lavendelis
 * @version 1.0
 */

public class PswHasher {
	static public long hash(String psw) {
		long hashvalue = 0;
		int B = 16; //2^7
		long W = 4611686018427387904L; //4611686018427387904=2^62
		char[] name = psw.toCharArray();
		for (int i = 0; i < psw.length(); i++) {
			hashvalue = hashvalue * B;
			hashvalue += (int) name[i];
		}
		return (hashvalue % W);
	}
	
	static public long hash(char[] psw) {
		return hash(new String(psw));
	}

	public static void main(String[] args) {
		System.out.println(hash("a"));
	}
}