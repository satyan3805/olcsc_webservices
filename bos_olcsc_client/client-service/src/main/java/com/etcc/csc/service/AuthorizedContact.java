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
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.AuthorizedContactDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * Copied from the original implementation in OLCSCService, then updated during the task 488 rework.
 * @author (task 488) Stephen Davidson
 */
@Stateless(name="com/etcc/AuthorizedContact")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "AuthorizedContact", targetNamespace = "http://ws.csc.etcc.com/AuthorizedContact")
public class AuthorizedContact implements AuthorizedContactInterface {

    //@WebMethod(operationName = "getAuthContacts", action = "urn:getAuthContacts")
    //@WebResult(name = "contacts")
    public AuthorizedContactDTO[] getAuthContacts(AccountLoginDTO accountLogin)  throws EtccException, EtccSecurityException {
        return authorizedContactDAO().getAuthContacts(accountLogin);
    }

    //@WebMethod(operationName = "setAuthContacts", action = "urn:setAuthContacts")
    //@WebResult(name = "result")
    public ResultDTO setAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts)  throws EtccException, EtccSecurityException {
        return authorizedContactDAO().setAuthContacts(accountLogin, authorizedContacts);
    }

    //@WebMethod(operationName = "modifyAuthContacts", action = "urn:modifyAuthContacts")
    //@WebResult(name = "result")
    public ResultDTO modifyAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts, 
            @Sensitive String password)  throws EtccException, EtccSecurityException {
        return authorizedContactDAO().modifyAuthContacts(accountLogin, authorizedContacts, password);
    }

    private AuthorizedContactDAO authorizedContactDAO() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        return daoFactory.getDAO(AuthorizedContactDAO.class);
    }
}
