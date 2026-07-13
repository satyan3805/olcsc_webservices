/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;

@Local
public interface AccountUtilInterface extends ServiceInterface{
    AccountLoginDTO checkCloseAccount(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException;
    ResultDTO closeAccount(AccountLoginDTO accountLogin, String refundType) throws EtccException, EtccSecurityException;
    AccountLoginDTO autoLogin(String userName, String jSessionId,
            String ipAddress, String sessionId, String acctId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException;
    ResultDTO generatePlateReminder(AccountLoginDTO accountLogin, String licensePlate) throws EtccException, EtccSecurityException;
    String getCloseAccountAgreement() throws EtccException;
}
