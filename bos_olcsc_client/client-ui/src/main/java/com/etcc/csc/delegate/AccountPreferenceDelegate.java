/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.service.AccountPreferenceInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * Delegate for handling updates updates to the User's Account Preferences.
 *
 */
public class AccountPreferenceDelegate implements AccountPreferenceInterface {
    private static final Logger logger = Logger.getLogger(AccountPreferenceDelegate.class);

    /**
     * @see com.etcc.csc.util.SessionUtil#getAcctPrefDTO(javax.servlet.http.HttpServletRequest)
     */
    public AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO) throws EtccException, EtccSecurityException{
        logger.trace ("**************===========> 11 - came to AccountPreferenceDelegate.getPreferences()");
        return stub().getPreferences(acctLoginDTO);
    }

    public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO, AccountPreferencesDTO preferenceDTO) throws EtccException, EtccSecurityException{
        logger.trace("**************===========> 12 - came to AccountPreferenceDelegate.setPreferences()");
        return stub().setPreferences(acctLoginDTO, preferenceDTO);
    }

    private AccountPreferenceInterface stub() {
    	return ServiceFactory.getImplementation(AccountPreferenceInterface.class);
    }

	public String updateOptin(AccountLoginDTO acctLoginDTO, String pushNtfOptin)
			throws EtccException, EtccSecurityException {
		logger.trace("**************===========> 13 - came to AccountPreferenceDelegate.updateOptin()");
        return stub().updateOptin(acctLoginDTO, pushNtfOptin);
	}

	public String getOptin(AccountLoginDTO acctLoginDTO) throws EtccException,
			EtccSecurityException {
		logger.trace("**************===========> 13 - came to AccountPreferenceDelegate.getOptin()");
        return stub().getOptin(acctLoginDTO);
	}
}
