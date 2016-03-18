/**
 * The file KASDebuger.java was created on 2008.26.8 at 21:14:05
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

public class KASDebuger {
	private static Boolean debug = true;
	
	public static void print(String s){
		if (debug)
			System.out.print(s);
	}

	public static void println(String s){
		if (debug) {
			System.out.println(s);
		}
	}
	public static void println(int i) {
		println(String.valueOf(i));
	}

	public static Boolean getDebug() {
		return debug;
	}

	public static void setDebug(Boolean debug) {
		KASDebuger.debug = debug;
	}
}
