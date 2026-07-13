/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountUtilDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;

/**
 * Service Interface Implementation for working with Accounts. Based on the original
 * version from OLCSCService, then updated during the task 488 rework.
 * 
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/AccountUtil")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "AccountUtil", targetNamespace = "http://ws.csc.etcc.com/AccountUtil")
public class AccountUtil implements AccountUtilInterface {

    //@WebMethod(operationName = "checkCloseAccount", action = "urn:checkCloseAccount")
    //@WebResult(name = "accountUtilResponse")
    public AccountLoginDTO checkCloseAccount(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
        return accountUtilDAO().checkCloseAccount(accountLogin);
    }

    //@WebMethod(operationName = "closeAccount", action = "urn:closeAccount")
    //@WebResult(name = "accountUtilResponse")
    public ResultDTO closeAccount(AccountLoginDTO accountLogin, 
                                String refundType) throws EtccException, EtccSecurityException {
        return accountUtilDAO().closeAccount(accountLogin, refundType);
    }

    private AccountUtilDAO accountUtilDAO() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        return daoFactory.getDAO(AccountUtilDAO.class);
    }

    //@WebMethod(operationName = "autoLogin", action = "urn:autoLogin")
    //@WebResult(name = "accountLogin")
    public AccountLoginDTO autoLogin(String userName, String jSessionId,
            String ipAddress, String sessionId, String acctId, UserEnvDTO userEnvDto) throws EtccSecurityException, EtccException{

         AccountUtilDAO accountUtilDao = accountUtilDAO();
         return accountUtilDao.autoLogin( userName, jSessionId, ipAddress, sessionId, acctId, userEnvDto);
    }
    
    //@WebMethod(operationName = "generatePlateReminder", action = "urn:generatePlateReminder")
    //@WebResult(name = "accountUtilResponse")
    public ResultDTO generatePlateReminder(AccountLoginDTO accountLogin, 
                                String licensePlate) throws EtccException, EtccSecurityException {
        return accountUtilDAO().generatePlateReminder(accountLogin, licensePlate);
    }

    //@WebMethod(operationName = "getCloseAccountAgreement", action = "urn:getCloseAccountAgreement")
    //@WebResult(name = "closeAgreement")
    public String getCloseAccountAgreement() throws EtccException {
        return accountUtilDAO().getCloseAccountAgreement();
    }
}
