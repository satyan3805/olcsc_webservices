/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.EmailValidationDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * @author Milosh Boroyevich
 */
@Stateless(name="com/etcc/EmailValidation")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "EmailValidation", targetNamespace = "http://ws.csc.etcc.com/EmailValidation")
public class EmailValidation implements EmailValidationInterface {

    //@WebMethod(operationName = "validationData", action = "urn:validationData")
    //@WebResult(name = "emailValidationData")
    public EmailValidationDataDTO validationData(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        return emailValidationDAO().validationData(acctLogin);
    }

    //@WebMethod(operationName = "validationStatus", action = "urn:validationStatus")
    //@WebResult(name = "dto")
    public ResultDTO validationStatus(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        return emailValidationDAO().validationStatus(acctLogin);
    }

    //@WebMethod(operationName = "setValidationDone", action = "urn:setValidationDone")
    //@WebResult(name = "dto")
    public ResultDTO setValidationDone(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        return emailValidationDAO().setValidationDone(acctLogin);
    }

    //@WebMethod(operationName = "generateEmailValidationMsg", action = "urn:generateEmailValidationMsg")
    //@WebResult(name = "dto")
    public ResultDTO generateEmailValidationMsg(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        return emailValidationDAO().generateEmailValidationMsg(acctLogin);
    }
    
    private EmailValidationDAO emailValidationDAO() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        return daoFactory.getDAO(EmailValidationDAO.class);
    }
}
