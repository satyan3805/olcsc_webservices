/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.dummy;

import java.util.HashMap;
import java.util.Map;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountPreferenceDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.service.AccountPreferenceFactory;

/**
 * Dummy DAO for testing.  Will store updates to preferences for the test run.
 * @author (task 488) Stephen Davidson
 */
public class DummyAccountPreferenceDAO extends AccountPreferenceDAO {
    private Map<AccountLoginDTO, AccountPreferencesDTO> preferences = new HashMap<AccountLoginDTO, AccountPreferencesDTO>();

    public AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO) throws EtccException,
            EtccSecurityException {
        AccountPreferencesDTO dto = this.preferences.get(acctLoginDTO);
        return dto == null ? AccountPreferenceFactory.getPreferences() : dto;
    }

    public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO, AccountPreferencesDTO preferenceDTO)
            throws EtccException, EtccSecurityException {
        this.preferences.put(acctLoginDTO, preferenceDTO);
        preferenceDTO.clearErrors();
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
