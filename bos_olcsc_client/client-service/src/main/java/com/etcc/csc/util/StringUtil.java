/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Misc Utility methods for Strings.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class StringUtil {
    public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public static final DecimalFormat decimalFormat = new DecimalFormat("###.00");

    /** Default value of four (4) for the number of visible digits after account masking. */
	public static final int MASK_VISIBLE = 4;
	public static final String NULL_AS_STRING = "null";
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
	public static final String AMERICANEXPRESS = "AMERICANEXPRESS";

    /**
     * Constructor. Should not be instantiated.
     */
    private StringUtil() { }

    /**
     * Converts the subset of <code>0..max</code> of the array to a StringBuilder.  If <code>max</code> is greater than
     * or equal to <code>array.length</code>, then the entire array is written.
     * @param array The array to write.
     * @param max The maximum number of elements to write.
     * @return the String representation of the array in a StringBuilder.
     */
    public static StringBuilder toStringBuilder(final Object[] array, int max){
        final StringBuilder sb = new StringBuilder();
        sb.append(array.getClass().getName());
        sb.append('[');
        final int size = max > array.length ? max : array.length;
        for(int i = 0; i < size; i++){
            sb.append(array[i]);
        }
        sb.append(']');
        return sb;
    }

    /**
     * Converts the subset of <code>0..max</code> of the collection to a StringBuilder.  If <code>max</code> is greater than
     * or equal to <code>array.length</code>, then the entire collection is written.
     * @param array The collection to write.
     * @param max The maximum number of elements to write.
     * @return the String representation of the collection in a StringBuilder.
     */
    public static StringBuilder toStringBuilder(final Collection<? extends Object> array, int max) {
    	if (array == null)
    		return new StringBuilder(NULL_AS_STRING);
        final StringBuilder sb = new StringBuilder();
        sb.append(array.getClass().getName());
        if (array.size() <= max){
            sb.append(array);
        } else {
            sb.append(toStringBuilder(array.iterator(), max));
        }
        return sb;
    }

    /**
     * Converts the subset of <code>0..max</code> of the map to a StringBuilder.  If <code>max</code> is greater than
     * or equal to <code>array.length</code>, then the entire map is written.
     * @param o The map to write.
     * @param max The maximum number of elements to write.
     * @return the String representation of the map in a StringBuilder.
     */
    public static StringBuilder toStringBuilder(final Map<? extends Object,? extends Object> o, int max){
        final StringBuilder sb = new StringBuilder();
        if (o == null){
            sb.append(o);
        } else {
            sb.append(o.getClass().getName());
            if (o.size() <= max){
                sb.append(o);
            } else {
                sb.append(toStringBuilder(o.entrySet().iterator(), max));
            }
        }
        return sb;
    }

    /**
     * Converts the subset of <code>0..max</code> of the iterator to a StringBuilder.  If <code>max</code> is greater than
     * or equal to <code>array.length</code>, then the entire iterator is written.
     * @param o The iterator to write.
     * @param max The maximum number of elements to write.
     * @return the String representation of the iterator in a StringBuilder.
     */
    public static StringBuilder toStringBuilder(final Iterator<? extends Object> o, int max){
    	if (o != null) {
    		final StringBuilder sb = new StringBuilder('[');
    		int i = 0;
    		for(Iterator<? extends Object> iterator = o; i < max; i++){
    			sb.append(iterator.next());
    		}
    		sb.append(']');
    		return sb;
    	}
    	return null;
    }

    /**
	 * Converts the string argument to boolean.
	 * The <tt>boolean</tt> returned represents the value <tt>true</tt> if
	 * the string argument is not <tt>null</tt> and after trimming is equal,
	 * ignoring case, to the string <tt>"Y"</tt>.
	 * @param value string to convert
	 * @return <tt>true</tt> if "Y" or "y", otherwise <tt>false</tt>
	 * @see #booleanToString(boolean)
	 */
	public static boolean stringToBoolean(String value) {
		return (value != null && value.trim().equalsIgnoreCase("Y"));
	}

    /**
     * Converts a boolean to string.
     * @param value boolean value
     * @return <tt>"Y"</tt> if value is <tt>true</tt>, otherwise <tt>"N"</tt>
     * @throws NullPointerException if value is <tt>null</tt>
     * @see #booleanToString(boolean)
     */
    public static String booleanToString(Boolean value) throws NullPointerException {
        return booleanToString(value.booleanValue());
    }
    /**
     * Converts a boolean to string.
     * @param value boolean value
     * @return <tt>"Y"</tt> if value is <tt>true</tt>, otherwise <tt>"N"</tt>
     * @see #stringToBoolean(String)
     */
	public static String booleanToString(boolean value) {
		return (value ? "Y" : "N");
	}

	/**
	 * Checks if the specified string is empty.
	 * Empty is defined as <tt>null</tt>, or a zero-length string after trimming.
	 * @param s string to check
	 * @return <tt>true</tt> if specified string is empty
	 * @see String#trim()
	 */
	public static boolean isEmpty(String s) {
	    return (s == null || s.equals("NULL") || s=="NULL" || s.length() == 0 || s.trim().length() == 0);
	}

    /**
     * Returns a non-empty string representation of the object.
     * @param o object to check and convert to string
     * @return string representation of specified object, or <tt>null</tt> if object is <tt>null</tt> or its string representation is empty
     * @see #isEmpty(String)
     * @see Object#toString()
     */
    public static String safeToString(Object o) {
        if (o == null)
            return null;
        String s = o.toString();
        return (isEmpty(s) ? null : s);
    }

    /**
     * Compare equality of the string representation of the specified object and the specified string.
     * Note: this method considers two <i>empty</i> strings as equal.
     * @param object object to convert to string for comparison
     * @param string string to compare
     * @return <tt>true</tt> if the object's <tt>toString</tt> method returns a string equal to the specified string
     * @see #safeToString(Object)
     * @see #isEmpty(String)
     * @see String#equals(Object)
     */
    public static boolean equals(Object object, String string) {
        String s = safeToString(object);
        if (s == string || (s == null && isEmpty(string)))
            return true;
        return (s != null && string != null && s.equals(string));
    }

    /**
     * Compare equality (ignoring case) of the string representation of the specified object and the specified string.
     * Note: this method considers two <i>empty</i> strings as equal.
     * @param object object to convert to string for comparison
     * @param string string to compare
     * @return <tt>true</tt> if the object's <tt>toString</tt> method returns a string equal (ignoring case) to the specified string
     * @see #safeToString(Object)
     * @see #isEmpty(String)
     * @see String#equalsIgnoreCase(String)
     */
    public static boolean equalsIgnoreCase(Object object, String string) {
        String s = safeToString(object);
        if (s == string || (s == null && isEmpty(string)))
            return true;
        return (s != null && string != null && s.equalsIgnoreCase(string));
    }

	/**
	 * Returns the masked account number.
     * Masked portion is converted to "***-".
	 * @param accountNumber string account number to mask
	 * @return the masked account number
	 * @see #maskAccount(String, int)
	 * @see #MASK_VISIBLE
	 */
	public static String maskAccount(String accountNumber) {
		return maskAccount(accountNumber, MASK_VISIBLE);
	}

	/**
	 * Returns the masked account number with only the specified number of digits visible.
	 * Masked portion is converted to "***-".
	 * @param accountNumber string account number to mask
	 * @param visible number of visible digits
	 * @return the masked account number
	 */
	public static String maskAccount(String accountNumber, int visible) {
	    if (isEmpty(accountNumber)) return "";
	   // return "***-" + (accountNumber.length() < visible ? accountNumber : accountNumber.substring(accountNumber.length() - visible));
	   return  accountNumber.substring(accountNumber.length() - visible);
	}


	/**
	 * Returns the masked cc number with only the specified number of digits visible.
	 * Masked portion is converted to "***-".
	 * @param ccNumber string account number to mask
	 * @param visible number of visible digits
	 * @return the masked account number
	 */
	public static String maskCCNumber(String ccNumber) {
	    if (isEmpty(ccNumber)) return "";
	  //  return "************-" + (ccNumber.length() < 4 ? ccNumber : ccNumber.substring(ccNumber.length() - 4));
	    return  ccNumber.substring(ccNumber.length() - 4);
	}


	/**
	 *
	 * return the last 4 digit of the value
	 */
	public static String last4Of(String value)
	{
		if (StringUtils.isEmpty(value) || value.length()<4) return null;
		return value.substring(value.length()-4);
	}


	public static String extractFirstName(String nameOnCard)
	{
		return extractNames(nameOnCard)[0].trim().toUpperCase();
	}

	public static String extractLastName(String nameOnCard)
	{
		return extractNames(nameOnCard)[1].trim().toUpperCase();
	}


	public static String nameOnCard(String... names)
	{
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < names.length;i++)
		{
			buffer.append(names[i]).append(" ");
		}
		return buffer.toString().trim().toUpperCase();
	}



	/**
	 *
	 * return the first name and last name from name on card
	 */
	private static String[] extractNames(String nameOnCard)
	{
		String[] names = nameOnCard.split(" ");
		if (names.length == 1)
		{
			return new String[]{nameOnCard,""};
		}
		else if (names.length > 1)
		{
			String firstName = names[0];
			StringBuffer buffer = new StringBuffer();
			for (int i = 1;i<names.length;i++)
			{
				buffer.append(names[i]).append(" ");
			}
			return new String[]{firstName,buffer.toString().trim()};

		}
		return null;
	}
	public static boolean validateCardExpiryDate(String expiryDate) {
        simpleDateFormat.setLenient(false);
        Date expiry;
        boolean expired=false;
		try {
			expiry = (Date)simpleDateFormat.parse(expiryDate);
			Calendar c = Calendar.getInstance();
			c.setTime(expiry);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, 23);
		    c.set(Calendar.MINUTE, 59);
		    c.set(Calendar.SECOND, 59);
		    c.set(Calendar.MILLISECOND, 999);
			Date expDateWithDate = c.getTime();
			expired = expDateWithDate.before(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return expired;
	}
	
	
}
