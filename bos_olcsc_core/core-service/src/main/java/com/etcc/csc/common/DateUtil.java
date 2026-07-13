package com.etcc.csc.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Contains date-specific methods.
 */
public class DateUtil {
	private static DateFormat shortDateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");
	private static DateFormat shortDateTimeFormat = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm");

	private DateUtil() {
	}

	/**
	 * Converts a timestamp to date.
	 * 
	 * @param ts
	 * @return
	 */
	public static Date timestampToDate(Timestamp ts) {
		if (ts != null) {
			return new Date(ts.getTime());
		}
		return null;
	}

	public static Calendar timestampToCalendar(Timestamp ts) {
		if (ts != null) {
			Date date = new Date(ts.getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
		return null;
	}

	public static Calendar dateToCalendar(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
		return null;
	}

	/**
	 * Extracts the month/year of a calendar for use in cases such as an
	 * expiration date.
	 * 
	 * @param cal
	 * @return A string in the form MM/yyyy.
	 */
	public static String getMonthYear(Calendar cal) {
		if (cal != null) {
			DateFormat df = new SimpleDateFormat("MM/yyyy");
			return df.format(cal.getTime());
		}
		return null;
	}

	/**
	 * Converts a string in the form MM/yyyy to a timestamp with the day as the
	 * first day of the month.
	 * 
	 * @param monthYear
	 * @return
	 */
	public static Timestamp monthYearToTimestamp(String monthYear) {
		Calendar cal = Calendar.getInstance();
		try {
			if (monthYear != null) {
				int pos = monthYear.indexOf("/");
				int month = Integer.parseInt(monthYear.substring(0, pos)) - 1;
				int year = Integer.parseInt(monthYear.substring(pos + 1,
						monthYear.length()));
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.DATE, 1);

			}
		} catch (Exception e) {
			// disregard error
		}
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Converts a calendar into a short date.
	 * 
	 * @param cal
	 * @return
	 */
	public static String getShortDate(Calendar cal) {
		if (cal != null) {
			return shortDateFormat.format(cal.getTime());
		}
		return null;
	}

	/**
	 * Converts a date into a short date time string.
	 * 
	 * @param date
	 * @return A date String in the form mm/dd/yyyy hh:mm.
	 */
	public static String getShortDateTime(Date date) {
		if (date != null) {
			return shortDateTimeFormat.format(date);
		}
		return null;
	}

	/**
	 * Converts a calendar to a timestamp.
	 * 
	 * @param cal
	 * @return
	 */
	public static Timestamp calendarToTimestamp(Calendar cal) {
		if (cal != null) {
			return new Timestamp(cal.getTimeInMillis());
		}
		return null;
	}

	/**
	 * Converts a short string date to calendar.
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar stringToCalendar(String date) {
		if (date != null) {
			try {
				Date dt = shortDateFormat.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				return cal;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Returns the current year in numeric form.
	 * 
	 * @return
	 */
	public static short getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy");
		return Short.parseShort(df.format(cal.getTime()));
	}

	/**
	 * Returns the current month in numeric form.
	 * 
	 * @return
	 */
	public static byte getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM");
		return Byte.parseByte(df.format(cal.getTime()));
	}
}
