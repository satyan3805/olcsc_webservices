/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Contains date-specific methods. Based on the classes
 * com.etcc.csc.common.DateUtil from OLCSCService and com.etcc.csc.util.DateUtil
 * from OLCSCWeb(UI).
 */
public class CoreDateUtil {
    /** MM */
    public static final String MONTH_PATTERN = "MM";
    /** yyyy */
    public static final String YEAR_PATTERN = "yyyy";
    /** MM/dd/yyyy */
    public static final String SHORT_DATE_PATTERN = "MM/dd/yyyy";
    /** MM/dd/yyyy HH:mm */
    public static final String SHORT_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm";
    /** MM/dd/yyyy HH:mm:ss */
    public static final String MEDIUM_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss";
    /** yyyy-MM-dd */
    public static final String ISO8601_PATTERN = "yyyy-MM-dd";
    /** yyyy-MM-dd HH:mm:ss */
    public static final String ISO8601_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // /** MM */
    // public static final DateFormat monthFormat =
    // new SimpleDateFormat(MONTH_PATTERN);
    // /** yyyy */
    // public static final DateFormat yearFormat =
    // new SimpleDateFormat(YEAR_PATTERN);
    // /** MM/dd/yyyy */
    // public static final DateFormat shortDateFormat =
    // new SimpleDateFormat(SHORT_DATE_PATTERN);
    // /** MM/dd/yyyy HH:mm */
    // public static final DateFormat shortDateTimeFormat =
    // new SimpleDateFormat(SHORT_DATE_TIME_PATTERN);
    // /** MM/dd/yyyy HH:mm:ss */
    // public static final DateFormat mediumDateTimeFormat =
    // new SimpleDateFormat(MEDIUM_DATE_TIME_PATTERN);
    // /** yyyy-MM-dd */
    // public static final DateFormat ISO8601Format =
    // new SimpleDateFormat(ISO8601_PATTERN);
    // /** yyyy-MM-dd HH:mm:ss */
    // public static final DateFormat ISO8601DateTimeFormat =
    // new SimpleDateFormat(ISO8601_DATE_TIME_PATTERN);

    /**
     * Constructor. Should not be instantiated.
     */
    protected CoreDateUtil() {
        // end <init>
    }

    public static Calendar dateToCalendar(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        return null;
    }

    protected static DateFormat getFormatter(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * Converts a date time string to a Calendar.
     * 
     * @param datetime
     * @return
     * @see #MEDIUM_DATE_TIME_PATTERN
     */
    public static Calendar stringDateTimeToCalendar(String datetime) {
        Date dt = stringDateTimeToDate(datetime);
        if (dt != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            return cal;
        }
        return null;
    }

    /**
     * Converts a date-time string to a Date.
     * 
     * @param datetime
     * @return
     * @see #MEDIUM_DATE_TIME_PATTERN
     */
    public static Date stringDateTimeToDate(String datetime) {
        if (datetime != null) {
            try {
                return getFormatter(MEDIUM_DATE_TIME_PATTERN).parse(datetime);
            } catch (Exception e) {
                return null;
            }
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
                Date dt = getFormatter(SHORT_DATE_PATTERN).parse(date);
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
     * Converts a calendar into a short date.
     * 
     * @param cal
     * @return
     */
    public static String getShortDate(Calendar cal) {
        if (cal != null) {
            return getShortDate(cal.getTime());
        }
        return null;
    }

    /**
     * Converts a Date to a String with the Short Date Pattern.
     * 
     * @param date
     *            The Date to return.
     * @return String version of the Date.
     * @see #SHORT_DATE_PATTERN
     */
    public static String getShortDate(Date date) {
        if (date != null)
            return getFormatter(SHORT_DATE_PATTERN).format(date);
        return null;
    }

    /**
     * Returns the current date using short date format.
     * 
     * @return the current date using short date format
     * @see #SHORT_DATE_PATTERN
     */
    public static String getCurrentShortDate() {
        return getFormatter(SHORT_DATE_PATTERN).format(new Date());
    }

    /**
     * Converts a date into a short date time string.
     * 
     * @param date
     * @return A date String in the form mm/dd/yyyy hh:mm.
     */
    public static String getShortDateTime(Date date) {
        if (date != null) {
            return getFormatter(SHORT_DATE_TIME_PATTERN).format(date);
        }
        return null;
    }

    /**
     * @param cal the calendar object to format
     * @return
     * @see #MEDIUM_DATE_TIME_PATTERN
     */
    public static String getMediumDateTime(Calendar cal) {
        if (cal != null) {
            return getMediumDateTime(cal.getTime());
        }
        return null;
    }

    /**
     * @param date the date object to format
     * @return
     * @see #MEDIUM_DATE_TIME_PATTERN
     */
    public static String getMediumDateTime(Date date) {
        if (date != null) {
            return getFormatter(MEDIUM_DATE_TIME_PATTERN).format(date);
        }
        return null;
    }

    public static String getLongDate(Date date) {
        if (date != null) {
            return DateFormat.getDateInstance(DateFormat.LONG).format(date);
        }
        return null;
    }

    public static String getLongDateTime(Date date) {
        if (date != null) {
            return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date);
        }
        return null;
    }

    /**
     * Gets the ISO 8061 formatted date, for use in cookies, etc.
     * 
     * @param date
     *            The Date to format.
     * @return The ISO 8061 string formatted date.
     * @see #ISO8601_PATTERN
     */
    public static String getISO8061Date(Date date) {
        if (date != null) {
            return getFormatter(ISO8601_PATTERN).format(date);
        }
        return null;
    }

    /**
     * Gets the ISO 8061 formatted date/time.
     * 
     * @param date
     *            The Date to format.
     * @return The ISO 8061 string formatted date/time.
     * @see #ISO8601_DATE_TIME_PATTERN
     */
    public static String getISO8061DateTime(Date date) {
        if (date != null) {
            return getFormatter(ISO8601_DATE_TIME_PATTERN).format(date);
        }
        return null;
    }

    /**
     * Returns the current month in numeric form.
     * 
     * @return
     */
    public static byte getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return (byte) cal.get(Calendar.MONTH);
        // return Byte.parseByte(monthFormat.format(cal.getTime()));
    }

    /**
     * Returns the current year in numeric form.
     * 
     * @return
     */
    public static short getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return (short) cal.get(Calendar.YEAR);
        // return Short.parseShort(yearFormat.format(cal.getTime()));
    }

    public static int getLastYear() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.YEAR, -1);
        int lastYear = rightNow.get(Calendar.YEAR);
        return lastYear;
    }

    public static Calendar getNextMonthCal() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH, 1);
        return rightNow;
    }

    public static String getNextMonthString() {
        Calendar nextMonth = getNextMonthCal();
        int monthValue = nextMonth.get(Calendar.MONTH);
        // monthValue++; // already next month from method call!
        if (monthValue < 10)
            return "0" + monthValue;
        else
            return Integer.toString(monthValue);
    }

    public static Date getPrevDate(int daysBefore) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_MONTH, (-1) * daysBefore);
        return rightNow.getTime();
    }
}
