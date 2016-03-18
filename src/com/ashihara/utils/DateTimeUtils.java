/**
 * The file DateTimeUtils.java was created on Apr 30, 2009 at 3:39:49 PM
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ashihara.enums.UIC;

public class DateTimeUtils {
	public static Date getDayBeginingDate(Date date){
		if (date == null) return date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar.getTime();
	}
	
	public static Date getDayEndDate(Date date){
		if (date == null) return date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return calendar.getTime();
	}
	
	public static String convertMillis(Long time, UIC uic){
		if (time == null)
			return "";
		
		Long hour = time / (1000 * 60 * 60);
		Long timeMinusHours = time - hour * 1000* 60 * 60;
		Long min = timeMinusHours / (1000 * 60 );
		Long timeMinusMins =  timeMinusHours - min * 1000 * 60;
		Long sec = timeMinusMins / (1000 );

		String result = "";
		if (hour > 0)
			result +=hour+""+uic.H().toLowerCase()+" ";
		if (min > 0)
			result += min+""+uic.MIN().toLowerCase()+" ";
		if (sec > 0)
			result+=sec+""+uic.SEC().toLowerCase()+" ";
			
		if (result.length() == 0)
			result = "0"+uic.SEC().toLowerCase()+" ";
		
		return result;
	}

	public static String convertMillis(int value, UIC uic) {
		return convertMillis(Long.valueOf(value), uic);
	}
}
