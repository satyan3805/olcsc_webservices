package com.etcc.csc.dto;


/**
 * Contains user preference options. It wraps a collection of UserPreferenceDTO
 * and AccountIopDTO.
 */
public class UserPreferenceResultDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -3176187333074438575L;
    private UserPreferenceDTO[] userPreferences;
    private AccountIopDTO[] accountIops;
    /**
     * @return the userPreferences
     */
    public UserPreferenceDTO[] getUserPreferences() {
        return this.userPreferences;
        //end getUserPreferences
    }
    /**
     * @param userPreferences the userPreferences to set
     */
    public void setUserPreferences(UserPreferenceDTO[] userPreferences) {
        this.userPreferences = userPreferences;
        //end setUserPreferences
    }
    /**
     * @return the accountIops
     */
    public AccountIopDTO[] getAccountIops() {
        return this.accountIops;
        //end getAccountIops
    }
    /**
     * @param accountIops the accountIops to set
     */
    public void setAccountIops(AccountIopDTO[] accountIops) {
        this.accountIops = accountIops;
        //end setAccountIops
    }

}
