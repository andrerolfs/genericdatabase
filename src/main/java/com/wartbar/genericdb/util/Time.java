package com.wartbar.genericdb.util;

import java.util.Calendar;

/**
 * Created by amos on 26.02.17.
 */
public class Time {

	public static Calendar getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar;
	}
}
