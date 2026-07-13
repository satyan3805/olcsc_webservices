/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Contains date-specific methods.  Based on the class com.etcc.csc.common.DateUtil and com.etcc.csc.daoDateUtil from OLCSCService.
 * Common methods migrated to CoreDateUtil.
 */
public class WSDateUtil extends CoreDateUtil {
    public static final DateFormat monthYearFormat = 
    	new SimpleDateFormat("MM/yyyy");

    /**
     * Constructor.  Should not be instantiated.
     */
    private WSDateUtil() {
        //end <init>
    }

    /**
     * Converts a timestamp to date.
     * @param ts the Timestamp to convert.
     * @return Date based on the supplied timestamp.
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

    /**
     * Extracts the month/year of a calendar for use in cases such as an expiration date.
     * @param cal
     * @return A string in the form MM/yyyy.
     */
    public static String getMonthYear(Calendar cal) {
        if (cal != null) {
            return monthYearFormat .format(cal.getTime());
        }
        return null;
    }
    
    /**
     * Converts a string in the form MM/yyyy to a timestamp with the day as
     * the first day of the month.
     * @param monthYear
     * @return
     */
    public static Timestamp monthYearToTimestamp(String monthYear) {
        Timestamp returnDate = null;
        try {
            if (monthYear != null) {
                Calendar cal = Calendar.getInstance();
                int pos = monthYear.indexOf("/");
                int month = Integer.parseInt(monthYear.substring(0, pos)) ;
                int year = Integer.parseInt(monthYear.substring(pos + 1, 
                    monthYear.length()));
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DATE, 0);
                returnDate =  new Timestamp(cal.getTimeInMillis());             
            }
        } catch (Exception e) {
            return null;
        }
        return returnDate;
    }    

    /**
     * Converts a calendar to a timestamp.
     * @param cal
     * @return
     */
    public static Timestamp calendarToTimestamp(Calendar cal) {
        if (cal != null) {
            return new Timestamp(cal.getTimeInMillis());
        }
        return null;
    }
}
