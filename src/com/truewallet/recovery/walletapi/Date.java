package com.truewallet.recovery.walletapi;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Date {

	public static String dateMod(int mod) {
	    ZoneId zoneId = ZoneId.of("Asia/Bangkok");
	    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).plusDays(mod);
	    DateTimeFormatter formatterOutput = DateTimeFormatter.ISO_LOCAL_DATE;
	    String date = formatterOutput.format(zonedDateTime);
	    return date;
	}
	
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int secs = cal.get(Calendar.SECOND);
		if (min <= 10) {
			return hour + ":0" + min + ":" + secs;
		}
		return hour + ":" + min  + ":" + secs;
	}
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return day + "/" + month +"/" + year;
	}
	
}
