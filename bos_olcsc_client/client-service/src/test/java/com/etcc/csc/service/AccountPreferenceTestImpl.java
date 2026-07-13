/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;

/**
 * Test implementation of AccountPreferenceInterface.
 * @author Milosh Boroyevich
 */
public class AccountPreferenceTestImpl implements AccountPreferenceInterface {
	private static final Logger logger = Logger.getLogger(AccountPreferenceTestImpl.class);

	public AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO)
			throws EtccException, EtccSecurityException {
		return AccountPreferenceFactory.getPreferences();
	}

	public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO,
			AccountPreferencesDTO preferenceDTO) throws EtccException,
			EtccSecurityException {
		logger.debug("AccountPreferenceTestImpl.setPreferences:" + preferenceDTO);
		return preferenceDTO;
	}

	public String updateOptin(AccountLoginDTO acctLoginDTO, String pushNtfOptin)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOptin(AccountLoginDTO acctLoginDTO) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
}
