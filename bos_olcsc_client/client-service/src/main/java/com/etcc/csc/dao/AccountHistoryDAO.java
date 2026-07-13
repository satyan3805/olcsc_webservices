/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.service.AccountHistoryInterface;

/**
 * Copied from com.etcc.csc.dao.AccountHistoryDAO
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public abstract class AccountHistoryDAO extends BaseDAO implements AccountHistoryInterface {
    private static final Logger logger = Logger.getLogger(AccountHistoryDAO.class);

    //TODO: Refactor common/default code here.

	protected abstract String getMonthDesc(Object statementDate) throws SQLException;

    protected String[] getDateList(Array dates, long acctId) throws SQLException {
	    ArrayList<String> result = null;
	    Object array[] = (Object[]) dates.getArray();
	    boolean infoEnabled = logger.isInfoEnabled();
	    if (array != null && array.length >= 0) {
	        final int length = array.length;
	        result = new ArrayList<String>(length);
	        for (int i = 0; i < length; i++) {
	        	String monthDesc = getMonthDesc(array[i]);
	            if (infoEnabled)
	                logger.info("AcctId:" + acctId + "****" + "statement Months [" + i + "]: " + monthDesc);
	            result.add(monthDesc);
	        }
	    } else {
	        if (infoEnabled)
	            logger.info("AcctId:" + acctId + "****" + "no monthly statement available");
	    }
	    return (result == null ? null : result.toArray(new String[result.size()]));
	}

	protected String toString(AccountLoginDTO login) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("AccountLoginDTO[");
	    if (login == null) {
	        sb.append(NULL_STRING);
	    } else {
	        sb.append("getAcctActivity()=" + login.getAcctActivity());
	        sb.append(",getAcctId()" + login.getAcctId());
	        sb.append(",getAcctStatus()=" + login.getAcctStatus());
	        sb.append(",getCreatedBy()=" + login.getCreatedBy());
	        sb.append(",getDateCreated()=" + login.getDateCreated());
	        sb.append(",getDateModified()=" + login.getDateModified());
	        sb.append(",getDbSessionId()=" + login.getDbSessionId());
	        sb.append(",getInvoiceId()=" + login.getInvoiceId());
	        sb.append(",getLastLoginDate()=" + login.getLastLoginDate());
	        sb.append(",getLastLoginIp()=" + login.getLastLoginIp());
	        sb.append(",getLicPlate()=" + login.getLicPlate());
	        sb.append(",getLicState()=" + login.getLicState());
	        sb.append(",getLoginId()=" + login.getLoginId());
	        sb.append(",getLoginType()=" + login.getLoginType());
	        sb.append(",getModifiedBy()=" + login.getModifiedBy());
	        sb.append(",getViolatorId()=" + login.getViolatorId());
	    }
	    sb.append("]");
	    return sb.toString();
	}

	protected Calendar convert(Timestamp theInput) {
	    if (theInput == null)
	        return null;
	    Calendar theOutput = Calendar.getInstance();
	    theOutput.setTimeInMillis(theInput.getTime());
	    return theOutput;
	}
}
