/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountPreferenceDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.service.AccountPreferenceInterface;

/**
 * Webservice Interface Implementation for working with Preferences. Based on the original
 * version from OLCSCService.
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/AccountPreference")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "AccountPreference", targetNamespace = "http://ws.csc.etcc.com/AccountPreference")
public class AccountPreference implements AccountPreferenceInterface {
    private final Logger logger = Logger.getLogger(AccountPreference.class);

    //@WebMethod(operationName = "getPreferences", action = "urn:getPreferences")
    //@WebResult(name = "preferences")
    public AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO) throws EtccException,
            EtccSecurityException {

        // validation - none needed
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountPreferenceDAO accountPreferenceDAO = daoFactory.getDAO(AccountPreferenceDAO.class);
        this.logger.trace("Calling NewAccountPreferenceDAO getPreferences...");
        return accountPreferenceDAO.getPreferences(acctLoginDTO);
    }

    //@WebMethod(operationName = "setPreferences", action = "urn:setPreferences")
    //@WebResult(name = "preferences")
    public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO, AccountPreferencesDTO preferenceDTO)
            throws EtccException, EtccSecurityException {

        // validation - none needed
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AccountPreferenceDAO accountPreferenceDAO = daoFactory.getDAO(AccountPreferenceDAO.class);
        this.logger.trace("Calling NewAccountPreferenceDAO setPreferences...");
        return accountPreferenceDAO.setPreferences(acctLoginDTO, preferenceDTO);
    }
    
    // olcsc update optin service start
	public String updateOptin(AccountLoginDTO acctLoginDTO, String pushNtfOptin)
			throws EtccException, EtccSecurityException {
		// validation - none needed
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountPreferenceDAO accountPreferenceDAO = daoFactory
				.getDAO(AccountPreferenceDAO.class);
		this.logger.trace("Calling NewAccountPreferenceDAO update optin service...");
		return accountPreferenceDAO.updateOptin(acctLoginDTO, pushNtfOptin);
	}
    // olcsc update optin service end
	
	// olcsc get optin service start
	public String getOptin(AccountLoginDTO acctLoginDTO)
			throws EtccException, EtccSecurityException {
		// validation - none needed
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountPreferenceDAO accountPreferenceDAO = daoFactory
				.getDAO(AccountPreferenceDAO.class);
		this.logger
				.trace("Calling NewAccountPreferenceDAO get optin service...");
		return accountPreferenceDAO.getOptin(acctLoginDTO);
	}
	// olcsc get optin service end
	
    
}// end AccountPreference
