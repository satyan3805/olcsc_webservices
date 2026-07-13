package com.etcc.csc.presentation.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.AccountIopValueDTO;
import com.etcc.csc.dto.AccountPreferenceValueDTO;

public class AccountPreferenceForm extends ValidatorActionForm{
	private static final long serialVersionUID = 733693675753801230L;

	private String emailFormat;
    
    private List<AccountPreferenceValueDTO> userPreferences;
    private List<AccountIopValueDTO> iopPreferences;
    
    
    public void setEmailFormat(String emailFormat) {
        this.emailFormat = emailFormat;
    }

    public String getEmailFormat() {
        return emailFormat;
    }

    public void setUserPreferences(List<AccountPreferenceValueDTO> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<AccountPreferenceValueDTO> getUserPreferences() {
        return userPreferences;
    }

    public void setIopPreferences(List<AccountIopValueDTO> iopPreferences) {
        this.iopPreferences = iopPreferences;
    }

    public List<AccountIopValueDTO> getIopPreferences() {
        return iopPreferences;
    }

    public AccountIopValueDTO getIopPreference(int index) {
        if (iopPreferences == null) {
            iopPreferences = new ArrayList<AccountIopValueDTO>();
        }
        int size = iopPreferences.size();
        for (int i=0; i<index+1-size; i++) {
            iopPreferences.add(new AccountIopValueDTO());
        }
        return iopPreferences.get(index);
    }
    
    public AccountPreferenceValueDTO getUserPreference(int index) {
        if (userPreferences == null) {
            userPreferences = new ArrayList<AccountPreferenceValueDTO>();
        }
        int size = userPreferences.size();
        for (int i=0; i<index+1-size; i++) {
            userPreferences.add(new AccountPreferenceValueDTO());
        }
        return userPreferences.get(index);
    }
}
