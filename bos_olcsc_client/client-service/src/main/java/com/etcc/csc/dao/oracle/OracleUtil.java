/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.dao.ConnectionUtil;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.plsql.OLC_ALERT_DISPLAY_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;

/**
 * Utilities for manipulating Oracle-specific database objects.
 * @author Milosh Boroyevich
 */
// Copied from the original Util in OLSC Service.
public final class OracleUtil extends ConnectionUtil {
    private static final Logger logger = Logger.getLogger(OracleUtil.class);
    private static String[] errorParts=null;
    private static String errorMsg=null;
    private static String errorMessage=null;
    private static String errorCode=null;
    
    public static final String EXPIRATION_DATE_PASED ="Expiration date past.~-20032";
    public static final String INVOICE_PAYMENT_ERROR ="The invoices you selected contain multiple persons. Unable to process to the payment.~-20450";
    public static final String INVALID_BANK_ACCOUNT ="Invalid bank account number.~-20408";
	public static final int arryLen =1;
	public static final String GENERIC_ERR_MSG ="Sorry, we are unable to process your request at this time. Please try again later.";

    /**
     * Constructor.  Private as class should not be instantiated or extended.
     */
    private OracleUtil() {
        //end <init>
    }

    /**
     * Converts the Array of Messages to ErrorMessageDTOs.
     * @param errors messages to be converted
     * @return the message DTOs
     * @throws SQLException thrown if any exceptions occur accessing the error messages from the Oracle objects.
     * @see Array#getArray()
     * @see #convertToMessages(Object[])
     */
    protected static ErrorMessageDTO[] convertToMessages(Array errors) throws SQLException {
        return (errors == null ? null : convertToMessages(errors.getArray()));
    }

    public static ErrorMessageDTO[] convertToMessages(Object errors) throws SQLException {
        try {
            return convertToMessages((Object[]) errors);
        } catch (ClassCastException e) {
            final Logger callerLogger = Logger.getLogger(getCallerClass());
            callerLogger.error("ClassCastException casting to Object[] -- Switching to fallback: " + e.getMessage());
            callerLogger.trace("ClassCastException casting to Object[]: " + e.getMessage(), e);
            String s = errors.toString();
            return new ErrorMessageDTO[] {new ErrorMessageDTO().withMessage(s)};
        }
    }

    /**
     * Converts the Array of Messages to ErrorMessageDTOs.
     * Note: assumes the specified array is of type <tt>OLC_ERROR_MSG_ARR[]</tt>, but will also handle <tt>OLC_ERROR_MSG_REC[]</tt>, and default to <tt>Object[]</tt>.
     * @param errors messages to be converted
     * @return the message DTOs
     * @throws SQLException thrown if any exceptions occur accessing the error messages from the Oracle objects.
     */
    protected static ErrorMessageDTO[] convertToMessages(Object[] errors) throws SQLException {
        // Use the caller class logger, so if anything weird happens,
        // can be traced to which module(s) and which DTO were involved.
        final Logger callerLogger = Logger.getLogger(getCallerClass());
        //NOTE: Partially initialized Oracle Arrays can have a length of -1!
        if (errors == null || errors.length <= 0) {
            callerLogger.warn("No Message for errors.");
            Thread.dumpStack();
            return null;
        } //else
        try {
            OLC_ERROR_MSG_ARR[] array = (OLC_ERROR_MSG_ARR[]) errors;
            OLC_ERROR_MSG_REC[] msgs = array[0].getArray();
            return convertToMessages(msgs);
        } catch (ClassCastException e) {
            // Database type map not properly set up.
            callerLogger.warn("ClassCastException casting to OLC_ERROR_MSG_ARR[] -- Switching to fallback: " + e.getMessage());
            callerLogger.trace("ClassCastException casting to OLC_ERROR_MSG_ARR[]: " + e.getMessage(), e);
            callerLogger.trace("ClassCastException casting to OLC_ERROR_MSG_ARR[] -- Switching to fallback");
            try {
                OLC_ERROR_MSG_REC[] msgs = (OLC_ERROR_MSG_REC[]) errors;
                return convertToMessages(msgs);
            } catch (ClassCastException e2) {
                callerLogger.trace("ClassCastException casting to OLC_ERROR_MSG_REC[]: " + e2.getMessage(), e2);
                // Last ditch to get error message
                ErrorMessageDTO[] messages = new ErrorMessageDTO[errors.length];
                int idx = 0;
                callerLogger.warn("Converting all (" + errors.length + ") error objects using toString: " + errors.getClass());
                for (Object object : errors) {
                    Class<? extends Object> errorClass = object.getClass();
                    callerLogger.debug("Error object class: " + errorClass);
                    String s = null;
                    if (errorClass == OLC_ERROR_MSG_REC.class) {
                        try {
                            s = ((OLC_ERROR_MSG_REC) object).getERROR_MSG();
                        } catch (ClassCastException e3) {
                            callerLogger.trace("ClassCastException casting to OLC_ERROR_MSG_REC: " + e3.getMessage(), e3);
                        }
                    }
                    // fail-safe conversion using toString
                    if (s == null)
                        s = object.toString();
                    messages[idx++] = new ErrorMessageDTO().withMessage(s);
                }
                return messages;
            }
        }
    }

    /**
     * Converts the Array of Message records to ErrorMessageDTOs.
     * Note: assumes that {@link Array#getArray()} on the specified errors returns <tt>OLC_ERROR_MSG_REC[]</tt>.
     * @param errors messages to be converted
     * @return the message DTOs
     * @throws SQLException thrown if any exceptions occur accessing the error messages from the Oracle objects.
     * @see #convertToMessages(OLC_ERROR_MSG_REC[])
     */
    protected static ErrorMessageDTO[] convertToMessagesFromRecs(Array errors) throws SQLException {
        try {
            return (errors == null ? null : convertToMessages((OLC_ERROR_MSG_REC[])errors.getArray()));
        } catch (ClassCastException e) {
            // try default path
            final Logger callerLogger = Logger.getLogger(getCallerClass());
            callerLogger.error("ClassCastException casting to OLC_ERROR_MSG_REC[] -- Switching to fallback: " + e.getMessage());
            callerLogger.debug("ClassCastException casting to OLC_ERROR_MSG_REC[]: " + e.getMessage(), e);
            return convertToMessages(errors.getArray());
        }
    }

    protected static Class<?> getCallerClass(){
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // Modified by Srinivas
       // final String className = stack[3].getClassName();
        final String className = stack[5].getClassName();
        try {
            assert !className.equals(OracleUtil.class.getName()) : "Got current class instead of calling class.";
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("Can not load caller class " + className +": " + e.getMessage(), e);
            return OracleUtil.class;
        }
    }

    protected static List<ErrorMessageDTO> convertToMessageList(OLC_ERROR_MSG_ARR[] errors) throws SQLException {
        assert errors != null && errors.length > 0 : "Oracle Error Array not properly created.";
        int length = errors[0].length();
        if (length < 0) {
            logger.error("Oracle array has negative size: " + length);
            return null;
        }
        return convertToMessageList(errors[0].getArray());
    }

    /**
     * Converts Oracle OLC_ERROR_MSG_REC objects to ErrorMessageDTO objects.
     *
     * @param errors an array of OLC_ERROR_MSG_REC objects to convert.
     * @return collection of ErrorMessageDTOs representing the OracleErrorMessages.
     * @throws SQLException If an error occurs accessing the details on the OLC_ERROR_MSG_REC
     */
    protected static List<ErrorMessageDTO> convertToMessageList(OLC_ERROR_MSG_REC[] errors) throws SQLException {
        ArrayList<ErrorMessageDTO> result = null;
        if (errors != null && errors.length >= 0) {
            result = new ArrayList<ErrorMessageDTO>(errors.length);
            for (int i = 0; i < errors.length; i++) {
                errorMsg = errors[i].getERROR_MSG();
              //added new logic for getting error  message and code
            	if (errorMsg!=null && errorMsg.contains("~")){
                  errorParts = errorMsg.split("~");
                  errorMessage =errorParts[0];
                  errorCode =errorParts[1];
            	}else{
            		logger.info(" Converting to generic error  actual error message received  is " + errorMsg);
            		errorMessage =GENERIC_ERR_MSG;
              		errorCode ="-20398";
            	}
                ErrorMessageDTO errorDTO = new ErrorMessageDTO();
                errorDTO.setKey(errorCode);
                errorDTO.setMessage(trimErrorMessage(errorMessage));
                result.add(errorDTO);
            }
        }
        return result;
    }

    /**
     * Converts Oracle Error Objects to DTO Error Messages.
     *
     * @param errors Oracle error Objects to convert.
     * @return the DTO Error Messages.
     * @throws SQLException Thrown if any exceptions occur accessing the Oracle Error object
     * @see #convertToMessages(OLC_ERROR_MSG_ARR[], Object)
     */
    protected static ErrorMessageDTO[] convertToMessages(OLC_ERROR_MSG_ARR[] errors) throws SQLException {
        return convertToMessages(errors, null);
    }

    /**
     * Converts Oracle Error Objects to DTO Error Messages. <b>WARNING:</b> the
     * <tt>oracle.jpub.runtime.MutableArray</tt> object allows for a negative length!
     *
     * @param errors Oracle error Objects to convert.
     * @param cause converted to a string and prefixed to each error message
     * @return the DTO Error Messages.
     * @throws SQLException Thrown if any exceptions occur accessing the Oracle Error object
     * @see #convertToMessages(OLC_ERROR_MSG_REC[], Object)
     * @see oracle.jpub.runtime.MutableArray
     */
    protected static ErrorMessageDTO[] convertToMessages(OLC_ERROR_MSG_ARR[] errors, Object cause) throws SQLException {
        assert errors != null && errors.length > 0 : "Oracle Error Array not properly created.";
        int length = errors[0].length();
        if (length < 0) {
            logger.error("Oracle array has negative size: " + length);
            return null;
        }
        return convertToMessages(errors[0].getArray(), cause);
    }

    /**
     * Converts Oracle Error Objects to DTO Error Messages.
     *
     * @param errors Oracle error Objects to convert.
     * @return the DTO Error Messages.
     * @throws SQLException Thrown if any exceptions occur accessing the Oracle Error object
     * @see #convertToMessages(OLC_ERROR_MSG_REC[], Object)
     */
    protected static ErrorMessageDTO[] convertToMessages(OLC_ERROR_MSG_REC[] errors) throws SQLException {
        return convertToMessages(errors, null);
    }

    /**
     * Converts Oracle Error Objects to DTO Error Messages.
     *
     * @param errors Oracle error Objects to convert.
     * @param cause converted to a string and prefixed to each error message
     * @return the DTO Error Messages.
     * @throws SQLException Thrown if any exceptions occur accessing the Oracle Error object
     */
    // Moved from BaseDTO
    protected static ErrorMessageDTO[] convertToMessages(OLC_ERROR_MSG_REC[] errors, Object cause) throws SQLException {
        ErrorMessageDTO[] messages = null;
        if (errors != null) {
            final String messagePrefix = (cause == null ? null : cause.toString());
            messages = new ErrorMessageDTO[errors.length];
            for (int idx = 0, size = errors.length; idx < size; idx++) {
            	//added new logic for getting error  message and code
            		errorMsg =errors[idx].getERROR_MSG();
            	if (errorMsg!=null && errorMsg.contains("~")){
                    errorParts = errorMsg.split("~");
                    errorMessage =errorParts[0];
                    errorCode =errorParts[1];
              	}else{
              		logger.info(" Converting to generic error  actual error message received  is " + errorMsg);
              		errorMessage =GENERIC_ERR_MSG;
              		errorCode ="-20398";
              	}
                messages[idx] = new ErrorMessageDTO();
                messages[idx].setMessage(messagePrefix == null ? errorMessage: messagePrefix + ": " + errorMessage);
                messages[idx].setKey(messagePrefix == null ? errorCode: messagePrefix + ": " + errorCode);
                // messages[idx].setMessage(Util.trimErrorMessage(errors[idx].getErrMsg()));
            }
        }
        return messages;
    }

    // Moved from OracleTagDAO
    protected static AlertDTO[] convertToAlerts(Array alerts) throws SQLException {
        AlertDTO[] result = null;
        if (alerts != null) {
            final boolean isDebugEnabled = logger.isDebugEnabled();
            Object array[] = (Object[]) alerts.getArray();
            if (array != null && array.length >= 0) {
                result = new AlertDTO[array.length];
                for (int i = 0; i < array.length; i++) {
                    if (isDebugEnabled && array[i] != null)
                        logger.debug("alert type is " + array[i].getClass().getName());
                    OLC_ALERT_DISPLAY_REC msgRec = (OLC_ALERT_DISPLAY_REC) array[i];
                    if (msgRec != null) {
                        result[i] = new AlertDTO(msgRec.getALERT_ID(), msgRec.getDISPLAY());
                        if (isDebugEnabled)
                            logger.debug("application alert:" + msgRec.getDISPLAY());
                    } else {
                        result[i] = new AlertDTO(-1, "");
                        if (isDebugEnabled)
                            logger.debug("application alert: <none>");
                    }
                }
            }
        } else {
            logger.trace("errors array is null");
        }
        return result;
    }

    /**
     * Returns the DB type mappings.
     * Used reflexively by DAO Factory.
     * @return the DB type mappings
     * @see com.etcc.csc.dao.DAOFactory#invokeUtility(String)
     */
    public static Map<String, Class<?>> getDbTypeMap() {
        Map<String, Class<?>> theTypeMap = new HashMap<String, Class<?>>();
        //theTypeMap.put("OL_OWNER.OLC_ERROR_MSG_ARR", OLC_ERROR_MSG_ARR.class);
        theTypeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OLC_ERROR_MSG_REC.class);
        theTypeMap.put("OL_OWNER.OLC_ALERT_DISPLAY_REC", OLC_ALERT_DISPLAY_REC.class);
        return theTypeMap;
    }
    //Method implemented for Card Expire Date validation
    public static OLC_ERROR_MSG_REC[] getErrorMessage() throws SQLException{
    	    OLC_ERROR_MSG_REC[] messages = new OLC_ERROR_MSG_REC[arryLen];
			messages[0] =new OLC_ERROR_MSG_REC();
			messages[0].setERROR_MSG(EXPIRATION_DATE_PASED);
			return messages;
    }
    
    
    //Method implemented for Card Expire Date validation
    public static OLC_ERROR_MSG_REC[] getInvoicePaymentErrorMessage() throws SQLException{
    	    OLC_ERROR_MSG_REC[] messages = new OLC_ERROR_MSG_REC[arryLen];
			messages[0] =new OLC_ERROR_MSG_REC();
			messages[0].setERROR_MSG(INVOICE_PAYMENT_ERROR);
			return messages;
    }
    
    
    //Method implemented for Bank Account validation
    public static OLC_ERROR_MSG_REC[] getBankAccountErrorMessage() throws SQLException{
	    OLC_ERROR_MSG_REC[] messages = new OLC_ERROR_MSG_REC[arryLen];
		messages[0] =new OLC_ERROR_MSG_REC();
		messages[0].setERROR_MSG(INVALID_BANK_ACCOUNT);
		return messages;
}
   
}
