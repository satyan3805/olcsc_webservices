/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AccountUtilInterface;

/**
 * Database agnostic implementation DAO for the AccountUtilInterface.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
//TODO: JMX Bean - set this up to query/use JMX Bean for cache access/control
public abstract class AccountUtilDAO extends BaseDAO implements AccountUtilInterface {
    private static final Logger logger = Logger.getLogger(AccountUtilDAO.class);

    private static final String ZERO = "0";
    private static final String CHAR_O = "O";

    private static Date lastLoad;
    private static Date expires = new Date(); //Expire immediately.

    private static String closeAccountAgreement;


    protected abstract ResultDTO closeAccountImpl(AccountLoginDTO accountLogin, String refundType)
    		throws SQLException, EtccSecurityException;
    protected abstract ResultDTO generatePlateReminderEmail(AccountLoginDTO accountLogin, String licensePlate)
    		throws SQLException, EtccSecurityException;

    protected abstract String loadCloseAccountAgreement() throws EtccException;

    public String getCloseAccountAgreement() throws EtccException {
        checkAndClearCache();
        if (closeAccountAgreement == null){
            logger.info("Last load: " + 
                    (lastLoad == null ? "never loaded" : DateFormat.getInstance().format(lastLoad)));
            closeAccountAgreement = loadCloseAccountAgreement();
        }
        return closeAccountAgreement;

    }

    private void checkAndClearCache(){
        Date currentDate = new Date();
        if (checkExpires(currentDate, expires)){
            synchronized(AccountUtilDAO.class){
                if (!checkExpires(currentDate, expires)){
                    //Another thread already reset the cache, so ABORT.
                    return;
                }//else
                closeAccountAgreement = null;
                expires = getNextExpire();
            }
            logger.trace("Cache cleared.");
        }
    }

    public final ResultDTO closeAccount(AccountLoginDTO accountLogin, String refundType)
			throws EtccException, EtccSecurityException {
        if (ZERO.equals(refundType)) {
            logger.debug("closeAccount.refundType is invalid, it is zero instead of character O");
            refundType = CHAR_O;
            logger.debug("closeAccount.refundType is changed to " + refundType);
        }
		try {
			return closeAccountImpl(accountLogin, refundType);
		} catch (SQLException t) {
			throw new EtccException(t.getMessage(), t);
		}
	}

	public final ResultDTO generatePlateReminder(AccountLoginDTO accountLogin, String licensePlate)
			throws EtccException, EtccSecurityException {
		try {
			return generatePlateReminderEmail(accountLogin, licensePlate);
		} catch (SQLException t) {
			throw new EtccException(t.getMessage(), t);
		}
	}
}
