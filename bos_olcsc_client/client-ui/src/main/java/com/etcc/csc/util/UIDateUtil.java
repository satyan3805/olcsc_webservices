/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.struts.util.LabelValueBean;

/**
 * Common methods migrated to CoreDateUtil.
 * Class previously called DateUtil.
 */
public class UIDateUtil extends CoreDateUtil {
    /**
     * Constructor. Should not be instantiated.
     */
    private UIDateUtil() {
        //end <init>
    }

    /**
     * Parse the string using the specified date format.
     * Note: returns <tt>null</tt> if <tt>ParseException</tt> is encountered.
     * @param dateString date represented in the specified date format
     * @param dateFormat date format to use for parsing
     * @return the date object
     * @see DateFormat#parse(String)
     */
    public static Date parseDate(String dateString, DateFormat dateFormat) {
        try {
			return dateFormat.parse(dateString);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return null;
    }
    /**
     * Parse the string using the specified date format.
     * Note: returns <tt>null</tt> if <tt>ParseException</tt> is encountered.
     * @param dateString date represented in the specified date format
     * @param dateFormat date format to use for parsing
     * @return the date object
     * @see DateFormat#parse(String)
     */
    public static Date parseDate(String dateString, String dateFormat) {
        return parseDate(dateString, getFormatter(dateFormat));
    }

    /**
     * Parse the string using the <tt>shortDateFormat</tt>.
     * @param dateString date represented in <tt>shortDateFormat</tt>
     * @return the date object
     * @see #SHORT_DATE_PATTERN
     * @see #parseDate(String, DateFormat)
     */
    public static Date parseDate(String dateString) {
        return parseDate(dateString, getFormatter(SHORT_DATE_PATTERN));
    }

    public static Date getTheFirstOfLastMonth() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH, -1);
        rightNow.set(Calendar.DAY_OF_MONTH, 1);
        return rightNow.getTime();
    }

    public static ArrayList<LabelValueBean> getMonthList(boolean includingCurrentMonth, int numberOfMonths) {
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        Calendar rightNow = Calendar.getInstance();
        if (!includingCurrentMonth)
            rightNow.add(Calendar.MONTH, -1);

        ArrayList<LabelValueBean> monthList = new ArrayList<LabelValueBean>();

        for (int i = 0; i < numberOfMonths; i++) {
            int month = rightNow.get(Calendar.MONTH);
            int year = rightNow.get(Calendar.YEAR);

            String itemString = months[month] + ", " + year;
            LabelValueBean optionBean = new LabelValueBean(itemString, itemString);
            monthList.add(optionBean);
            rightNow.add(Calendar.MONTH, -1);
        }
        return monthList;
    }

    public static String getJanOfLastYear() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.YEAR, -1);
        int lastYear = rightNow.get(Calendar.YEAR);

        return "January, " + Integer.toString(lastYear);
    }

    public static Collection<LabelValueBean> getVehicleYears(int endYear) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.YEAR, 1);
        Collection<LabelValueBean> col = new ArrayList<LabelValueBean>();

        while (rightNow.get(Calendar.YEAR) >= endYear) {
            int tmpYear = rightNow.get(Calendar.YEAR);
            col.add(new LabelValueBean(tmpYear + "", tmpYear + ""));
            rightNow.add(Calendar.YEAR, -1);
        }
        return col;
    }

    public static Collection<LabelValueBean> getCreditCardExpYears(int numberOfYears) {
        Calendar rightNow = Calendar.getInstance();
        Collection<LabelValueBean> col = new ArrayList<LabelValueBean>();

        int i = 0;
        while (i++ < numberOfYears) {
            int tmpYear = rightNow.get(Calendar.YEAR);
            col.add(new LabelValueBean(tmpYear + "", tmpYear + ""));
            rightNow.add(Calendar.YEAR, 1);
        }
        return col;
    }

    public static Collection<LabelValueBean> getCreditCardMonths() {
        Collection<LabelValueBean> col = new ArrayList<LabelValueBean>();
        col.add(new LabelValueBean("01 - January", "01"));
        col.add(new LabelValueBean("02 - February", "02"));
        col.add(new LabelValueBean("03 - March", "03"));
        col.add(new LabelValueBean("04 - April", "04"));
        col.add(new LabelValueBean("05 - May", "05"));
        col.add(new LabelValueBean("06 - June", "06"));
        col.add(new LabelValueBean("07 - July", "07"));
        col.add(new LabelValueBean("08 - August", "08"));
        col.add(new LabelValueBean("09 - September", "09"));
        col.add(new LabelValueBean("10 - October", "10"));
        col.add(new LabelValueBean("11 - November", "11"));
        col.add(new LabelValueBean("12 - December", "12"));
        return col;
    }

    // I am doing this for the logs

    public static String timeStamp() {
        return getISO8061DateTime(new Date());
    }

    public static String getDateAndTimeStamp(String time_s) throws Exception {
        return getFormatter(ISO8601_DATE_TIME_PATTERN).parse(time_s).toString();
    }

    public static String convertDateFormat(String time_s) throws Exception {
        String DATE_FORMAT_incoming = "MM/dd/yyyy hh:mm a"; 

        DateFormat formatter;
        //Date date_old = new Date();
        formatter = new SimpleDateFormat(DATE_FORMAT_incoming);
        //formatter.format(date_old);
        Date date_old = formatter.parse(time_s);
        // System.out.println("old date is " + date_old);
        // Outgoing date format is MEDIUM_DATE_TIME_PATTERN
        formatter = getFormatter(MEDIUM_DATE_TIME_PATTERN); // MM/dd/yyyy HH:mm:ss
        String output = formatter.format(date_old);
        // System.out.println("new date is " + output);
        return output;
    }
}
