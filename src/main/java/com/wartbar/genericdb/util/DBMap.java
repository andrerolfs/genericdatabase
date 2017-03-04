package com.wartbar.genericdb.util;

import java.util.HashMap;

/**
 * Created by amos on 22.01.17.
 */
public class DBMap {

	private static HashMap<Integer,String> map = new HashMap<>();
	private static int keyCounter = 0;

	public static void add(String className) {
		keyCounter++;
		map.put(keyCounter, className);
	}

	public static String get(int key) {
		return map.get(key);
	}
}
