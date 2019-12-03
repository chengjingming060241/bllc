package com.gizwits.lease.utils;

import java.util.Calendar;

public class CalendarUtils {

	public static Calendar getZeroHourOfToday() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	public static Calendar getZeroHourOfYesterday() {
		Calendar c = getZeroHourOfToday();
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c;
	}

	public static Calendar getZeroHourBeforeYesterday() {
		Calendar c = getZeroHourOfToday();
		c.add(Calendar.DAY_OF_YEAR, -2);
		return c;
	}

	public static Calendar getFirstDayOfThisMonth() {
		Calendar c = getZeroHourOfToday();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c;
	}

}
