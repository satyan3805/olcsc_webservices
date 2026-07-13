package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Collection;

/**
 * Contains user preference options. It wraps a collection of UserPreferenceDTO
 * and AccountIopDTO.
 */
public class UserPreferenceResultDTO extends BaseDTO {
    private static final long serialVersionUID = -1331857408913533222L;
    
	private Collection<UserPreferenceDTO> userPreferences;
    private Collection<AccountIopDTO> accountIops;

    public UserPreferenceResultDTO() {
    }


    public void setUserPreferences(Collection<UserPreferenceDTO> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public Collection<UserPreferenceDTO> getUserPreferences() {
        return userPreferences;
    }

    public void setAccountIops(Collection<AccountIopDTO> accountIops) {
        this.accountIops = accountIops;
    }

    public Collection<AccountIopDTO> getAccountIops() {
        return accountIops;
    }
}
