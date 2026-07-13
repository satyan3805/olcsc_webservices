/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountUtilDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.service.AccountUtilFactory;
import com.etcc.csc.service.AccountUtilInterface;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class DummyAccountUtilDAO extends AccountUtilDAO {

    /** 
     * @see AccountUtilInterface#autoLogin(String, String, String, String, String, UserEnvDTO)
     */
    public AccountLoginDTO autoLogin(String userName, String jSessionId, String ipAddress, String sessionId,
            String acctId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException {
        final AccountLoginDTO loginAccount = new DummyAccountDAO().loginAccount(userName, null, ipAddress, sessionId, userEnvDto);
        loginAccount.setDbSessionId(jSessionId);
        return loginAccount;
    }

    /** 
     * @see AccountUtilInterface#checkCloseAccount(AccountLoginDTO)
     */
    public AccountLoginDTO checkCloseAccount(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
        return null;
    }

    /** 
     * @see AccountUtilInterface#closeAccount(AccountLoginDTO, String)
     */
	@Override
	protected ResultDTO closeAccountImpl(AccountLoginDTO accountLogin, String refundType) throws EtccSecurityException {
	    return AccountUtilFactory.processRefund(refundType);
    }

    /** 
     * @see AccountUtilInterface#generatePlateReminder(AccountLoginDTO, String)
     */
	@Override
    protected ResultDTO generatePlateReminderEmail(AccountLoginDTO accountLogin, String licensePlate) throws EtccSecurityException {
        return null;
    }

    /** 
     * @see AccountUtilInterface#getCloseAccountAgreement()
     */
    @Override
    public String loadCloseAccountAgreement() throws EtccException {
        return AccountUtilFactory.CLOSE_ACCOUNT_AGREEMENT;
    }
}
