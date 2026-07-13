/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountIopValueDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.dto.AccountPreferenceValueDTO;

/**
 * Generates the return values for the methods needed for the UI Tests (and demos).
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AccountPreferenceFactory {
	private static final BigDecimal ACCT_ID = BigDecimal.valueOf(AccountFactory.POPULATED_ACCOUNT_ID);

	//Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AccountPreferenceInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).getPreferences(with(any(AccountLoginDTO.class)));
                will(returnValue(getPreferences()));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Get test Preference DTO.
     * @return dto loaded with dummy data.
     */
    public static AccountPreferencesDTO getPreferences() {
        AccountPreferencesDTO dto = new AccountPreferencesDTO();
    	dto.setIopSettings(getAccountIops());
        dto.setPreferences(getPreferenceValues());
        return dto;
    }

    /**
     * Get test IOP values.
     * @return array with dummy data for testing.
     */
    public static AccountIopValueDTO[] getAccountIops() {
        String[] agcys = {"HAS","NTTA", null, "CTRMA"}; 
        AccountIopValueDTO[] iopSettings = new AccountIopValueDTO[agcys.length];
        for (int i = 0; i < agcys.length; i++) {
            iopSettings[i] = new AccountIopValueDTO();
            iopSettings[i].setActive(agcys[i] != null);
            iopSettings[i].setAcctId(ACCT_ID);
            iopSettings[i].setAgcyAbbrev(agcys[i]);
            iopSettings[i].setAgcyName(agcys[i]);
            iopSettings[i].setAgcyId(BigDecimal.valueOf(i+1));
        }
        return iopSettings;
    }
    static AccountPreferenceValueDTO[] getPreferenceValues() {
    	int size = 5;
    	AccountPreferenceValueDTO[] prefs = new AccountPreferenceValueDTO[size];
    	for (int i = 0; i < size; i++) {
    		prefs[i] = new AccountPreferenceValueDTO(
    				ACCT_ID, "Type " + i,
    				"Notification " + i,
    				"email", true, BigDecimal.valueOf(2));
    	}
    	return prefs;
    }
}
