/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;

/**
 * From the original OLSCSService project.
 */
@Local
public interface AccountPreferenceInterface extends ServiceInterface {
    /**
     * Gets the preferences for the User Account.
     * @param acctLoginDTO User Account to get the preferences for.
     * @return The Preferences for this user account.
     * @throws EtccException If any exceptions occur getting the user preferences.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO) 
    throws EtccException, EtccSecurityException;
    
    /**
     * Sets the preferences for the Account.
     * @param acctLoginDTO The Account to set the preferences for.
     * @param preferenceDTO The Preferences to set.
     * @return the provided preference object, populated with DB errors if any occurred
     * @throws EtccException If any exceptions occur setting the user preferences.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO, AccountPreferencesDTO preferenceDTO)
    throws EtccException, EtccSecurityException;
    
    /**
     * Sets the push notifications are to be enabled.
     * @param acctLoginDTO The Account to set the preferences for.
     * @param pushNtfOptin The user selected push notifications option.
     * @return This will be the updated option
     * @throws EtccException If any exceptions occur setting the user preferences.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
	public String updateOptin(AccountLoginDTO acctLoginDTO, String pushNtfOptin)
			throws EtccException, EtccSecurityException;
	
	/**
     * gets the status of push notifications
     * @param acctLoginDTO The Account to set the preferences for.
     * @return This will be the updated option
     * @throws EtccException If any exceptions occur setting the user preferences.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
	public String getOptin(AccountLoginDTO acctLoginDTO) throws EtccException,
			EtccSecurityException;
}//end AccountPreferenceInterface
