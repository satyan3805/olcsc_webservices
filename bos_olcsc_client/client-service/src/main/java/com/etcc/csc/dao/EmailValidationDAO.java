/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.service.EmailValidationInterface;

/**
 * @author Milosh Boroyevich
 */
public abstract class EmailValidationDAO extends BaseDAO implements EmailValidationInterface {
    private static final Logger logger = Logger.getLogger(EmailValidationDAO.class);

    private static final long INVALID_ACCOUNT_ID = -1;
    private static final String NO_EMAIL_ADDRESS = "";

	protected static void validateAccountLogin(AccountLoginDTO accountLogin) {
	    if (accountLogin == null) {
	        String msg = "Non-existing (null) account login";
	        logger.error("validateAccountLogin: " + msg);
	        throw new NullPointerException(msg);
	    }
	}

	protected static final long accountId(BigDecimal[] theAcctId) {
	    try {
	        if (theAcctId == null)
	            return INVALID_ACCOUNT_ID;
	        if (theAcctId.length == 0)
	            return INVALID_ACCOUNT_ID;
	        if (theAcctId[0] == null)
	            return INVALID_ACCOUNT_ID;
	        return theAcctId[0].longValue();
	    } catch (Throwable t) {
	        logger.error("accountId failed: " + t.getMessage(), t);
	        return INVALID_ACCOUNT_ID;
	    }
	}

	protected static final String emailAddress(String[] theEmailAddress) {
	    if (theEmailAddress == null)
	        return NO_EMAIL_ADDRESS;
	    if (theEmailAddress.length == 0)
	        return NO_EMAIL_ADDRESS;
	    if (theEmailAddress[0] == null)
	        return NO_EMAIL_ADDRESS;
	    return theEmailAddress[0];
	}

	protected final ErrorMessageDTO[] toErrorMessageDTOArray(Object[] errorArray) throws EtccException, SQLException {
		if ((errorArray == null) || (errorArray.length == 0)) {
			String msg = "Database error message does not match return value";
			logger.error("toErrorMessageDTO: " + msg);
			throw new EtccException(msg);
		}
		ErrorMessageDTO[] result = new ErrorMessageDTO[errorArray.length];
		for (int i = 0; i < errorArray.length; i++) {
			ErrorMessageDTO anErrorMessageDTO = new ErrorMessageDTO();
			if (errorArray[i] != null) {
				String aMsg = getErrorMsg(errorArray[i]);
				if (logger.isDebugEnabled())
					logger.debug("toErrorMessageDTOArray.aMsg=" + aMsg);
				anErrorMessageDTO.setMessage(aMsg == null ? "An error occured" : aMsg);
				result[i] = anErrorMessageDTO;
			} else {
				String msg = "Database error message is empty";
				logger.error("toErrorMessageDTO: " + msg);
				throw new EtccException(msg);
			}
		}
		return result;
	}

    protected abstract String getErrorMsg(Object error) throws SQLException;
}
