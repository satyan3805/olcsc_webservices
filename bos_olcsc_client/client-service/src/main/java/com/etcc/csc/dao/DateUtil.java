/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * DAO specific functions from the DateUtil on OLCSCService.
 * @deprecated use {@link com.etcc.csc.util.WSDateUtil}
 */
@Deprecated
public class DateUtil {

    /**
     * Constructor.
     */
    private DateUtil() {
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

    public static Calendar dateToCalendar(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        return null;
    }
}
