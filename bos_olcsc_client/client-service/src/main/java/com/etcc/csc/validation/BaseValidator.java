/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.apache.commons.validator.CreditCardValidator;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Base class for all the validators.  This has class has the common Validation methods.
 * @author (task 488) Stephen Davidson
 * @author Milosh Boroyevich
 */
public abstract class BaseValidator {
    private static final Logger logger = Logger.getLogger(BaseValidator.class);

    private static final String CC_MASKED_PATTERN = "^[0-9]{4}$";
	protected static final Pattern ccMaskPattern = Pattern.compile(CC_MASKED_PATTERN);
    private static final String CC_NUM_PATTERN = "^[0-9 ]*$";
	protected static final Pattern ccNumPattern = Pattern.compile(CC_NUM_PATTERN);

    /**
     * Returns <tt>true</tt> if the object being examined by this validator is valid.
     * @return <tt>true</tt> if valid
     * @throws ValidationException when the object is not valid.
     */
    public boolean isValid() throws ValidationException {
        //Default implemention -- do nothing.
    	return true;
    }

    protected static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    protected static boolean isValidCreditCard(String cardNumber) {
        if (StringUtil.isEmpty(cardNumber))
            return false;
        if (ccMaskPattern.matcher(cardNumber).matches())
            return true;
        return new CreditCardValidator().isValid(cardNumber);
    }

    protected static boolean isValidCreditCard(String cardNumber, CreditCardDTO.CreditCardType cardType) {
        if (StringUtil.isEmpty(cardNumber))
            return false;
       /* if (ccMaskPattern.matcher(cardNumber).matches())
            return true;
        CreditCardValidator validator = newCreditCardValidator(cardType);
        if (validator == null)
            return false;
        return validator.isValid(cardNumber)*/;
        return true;
    }

    protected static CreditCardValidator newCreditCardValidator(CreditCardDTO.CreditCardType cardType) {
    	switch (cardType) {
    	case AMERICANEXPRESS:
            return new CreditCardValidator(CreditCardValidator.AMEX);
    	case DISCOVER:
            return new CreditCardValidator(CreditCardValidator.DISCOVER);
    	case MASTERCARD:
            return new CreditCardValidator(CreditCardValidator.MASTERCARD);
    	case VISA:
            return new CreditCardValidator(CreditCardValidator.VISA);
    	default:
            logger.error("Invalid credit card type: " + cardType);
            return null;
    	}
    }

    protected static boolean isMinLength(String str, int minLength, boolean requiredField) {
    	if (StringUtil.isEmpty(str))
    		return (!requiredField);
    	return (str.length() >= minLength);
    }

    protected static boolean isMaxLength(String str, int maxLength, boolean requiredField) {
    	if (StringUtil.isEmpty(str))
    		return (!requiredField);
    	return (str.length() <= maxLength);
    }

    protected static boolean isIntegerString(String str, int minLength, boolean requiredField) {
    	if (StringUtil.isEmpty(str))
    		return (!requiredField);
        String pattern = "[0-9]{" + minLength + ",}";
        return (str.matches(pattern));
    }

    protected static boolean isLong(String s) {
        try {
            if (StringUtil.isEmpty(s) == false)
                Long.parseLong(s);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected static boolean validateMinLength(int fieldLength,int minLength){
        return fieldLength >= minLength;
    }

    protected static boolean validateMaxLength(int fieldLength,int maxLength){
        return (fieldLength <= maxLength);
    }

    protected static boolean compareTwoStringsIgnoreCase(String stringFirst,String stringSecond) throws EtccException{
        if (StringUtil.isEmpty(stringFirst)||StringUtil.isEmpty(stringSecond)){
            throw new EtccException("It is empty, please try again");
        }else if(!stringFirst.equalsIgnoreCase(stringSecond)){
            throw new EtccException("The Two Value must be equal, please try again");
        }else {
            return true;
        }
    }

    protected static boolean isValidUrl(String url) {
         UrlValidator validator = new UrlValidator();
         return validator.isValid(url);
     }

    /**
     * Replace multiple white spaces with a single space and trims the result.
     * @param str string to clean
     * @return cleaned string or empty string if <tt>null</tt>
     */
    protected static String cleanSpace(String str) {
        if(!StringUtil.isEmpty(str))
            //remove multiple white space(s) with only 1
            return str.replaceAll("\\s+"," ").trim();
        return "";
    }

    /**
     * Validate specified string as a date-time.
     * Attempt to parse the string, and return false on exception.
     * @param dateStr
     * @return <tt>true</tt> if string represents a valid date-time
     * @see CoreDateUtil#MEDIUM_DATE_TIME_PATTERN
     */
    protected static boolean isDate(String dateStr) {
        try {
        	Calendar valDate = CoreDateUtil.stringDateTimeToCalendar(dateStr);
        	logger.debug("Formatted Date String: "+ valDate );
        } catch(Exception ex) {
        	return false; //Invalid date
        }
        return true;
    }

    public static String getPhoneAreaCode(String phoneNumber) {
      return isValidTenDigitTN( phoneNumber ) ? phoneNumber.substring( 0, 3 ) : null;
    }

    public static String getPhonePrefix(String phoneNumber) {
      return isValidTenDigitTN( phoneNumber ) ? phoneNumber.substring( 3, 7 ) : null;
    }

    public static String getPhoneSuffix(String phoneNumber) {
      return isValidTenDigitTN( phoneNumber ) ? phoneNumber.substring( 7, 10 ) : null;
    }

    /**
     * Validate a 10-digit Telephone Number
     * @param phoneNumber string TN consisting only of digits
     * @return <tt>true</tt> if valid
     */
    public static boolean isValidTenDigitTN(String phoneNumber) {
      return !StringUtil.isEmpty(phoneNumber) && phoneNumber.length() == 10;
    }
}
