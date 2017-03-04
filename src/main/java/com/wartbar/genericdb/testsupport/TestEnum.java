package com.wartbar.genericdb.testsupport;

/**
 * Created by amos on 22.01.17.
 */
public enum TestEnum {
	one,
	two,
	three,
	four,
	five;

	public static TestEnum get(int ordinal) {
		return TestEnum.values()[ordinal];
	}
}
