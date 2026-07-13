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
import com.etcc.csc.dao.AccountAlertDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * Service Interface Implementation for working with Preferences. Based on the original
 * version from OLCSCService, then updated during task 488.
 * 
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/AccountAlert")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "AccountAlert", targetNamespace = "http://ws.csc.etcc.com/AccountAlert")
public class AccountAlert implements AccountAlertInterface {

    //@WebMethod(operationName = "getAcctSummAlerts", action = "urn:getAcctSummAlerts")
    //@WebResult(name = "alerts")
    public AlertDTO[] getAcctSummAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountAlertDAO accountAlertDao = daoFactory.getDAO(AccountAlertDAO.class);
        return accountAlertDao.getAcctSummAlerts(acctLoginDto);
    }

    //@WebMethod(operationName = "getVehicleInfoAlerts", action = "urn:getVehicleInfoAlerts")
    //@WebResult(name = "alerts")
    public AlertDTO[] getVehicleInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountAlertDAO accountAlertDao = daoFactory.getDAO(AccountAlertDAO.class);
        return accountAlertDao.getVehicleInfoAlerts(acctLoginDto);
    }

    //@WebMethod(operationName = "getContactInfoAlerts", action = "urn:getContactInfoAlerts")
    //@WebResult(name = "alerts")
    public AlertDTO[] getContactInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountAlertDAO accountAlertDao = daoFactory.getDAO(AccountAlertDAO.class);
        return accountAlertDao.getContactInfoAlerts(acctLoginDto);
    }

    //@WebMethod(operationName = "addressCleanseAlertResponse", action = "urn:addressCleanseAlertResponse")
    //@WebResult(name = "response")
    public ResultDTO addressCleanseAlertResponse(AccountLoginDTO acctLoginDto, String cleanseAlertResponse)
            throws EtccException, EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountAlertDAO accountAlertDao = daoFactory.getDAO(AccountAlertDAO.class);
        return accountAlertDao.addressCleanseAlertResponse(acctLoginDto, cleanseAlertResponse);
    }
}
