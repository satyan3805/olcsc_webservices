/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Data Transfer Object for Account Preferences.
 */
public class AccountPreferencesDTO extends BaseDTO {
	/**
	 * Unique ID for Serialization with Version number.
	 */
	//Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
	private static final long serialVersionUID = 6211579591954770188L;
	private static final Logger logger = Logger.getLogger(AccountPreferencesDTO.class);

	public static final String HOUSTON_AIRPORT_SYSTEM = "HAS";

	public enum EmailFormat {
		HTML,
		TEXT,
		;
	}

	private AccountPreferenceValueDTO[] preferences;
    private AccountIopValueDTO[] iopSettings;
    private EmailFormat emailFormat = EmailFormat.HTML;
    
    boolean byMail=true;
    boolean byEmail=false;
    boolean bySms=false;
    

    /**
     * Check if Houston Airport System (HAS) is present in the array of <tt>AccountIopValueDTO</tt>.
     * @return <tt>true</tt> if HAS is available
     */
    @IgnoreProperty
    public boolean isHoustonAirportAvailable() {
        if (iopSettings != null && iopSettings.length > 0)
            return HOUSTON_AIRPORT_SYSTEM.equalsIgnoreCase(iopSettings[0].getAgcyAbbrev());
        return true;
    }

	/**
	 * @return the emailFormat as a string
	 */
	public String getEmailFormat() {
		return this.emailFormat.toString();
	}

	/**
	 * @param emailFormat the emailFormat to set
	 */
	public void setEmailFormat(String emailFormat) {
		try {
			if (!StringUtil.isEmpty(emailFormat))
				this.emailFormat = EmailFormat.valueOf(emailFormat);
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid e-mail format: " + emailFormat, e);
		}
	}

    /**
	 * @return the preferences
	 */
	public AccountPreferenceValueDTO[] getPreferences() {
		return this.preferences;
		//end getPreferences
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(AccountPreferenceValueDTO[] preferences) {
		this.preferences = preferences;
		//end setPreferences
	}

	/**
	 * @return the iopSettings
	 */
	public AccountIopValueDTO[] getIopSettings() {
		return this.iopSettings;
		//end getIopSettings
	}

	/**
	 * @param iopSettings the iopSettings to set
	 */
	public void setIopSettings(AccountIopValueDTO[] iopSettings) {
		this.iopSettings = iopSettings;
		//end setIopSettings
	}


	public boolean isByMail() {
		return byMail;
	}

	public void setByMail(boolean byMail) {
		this.byMail = byMail;
	}

	public boolean isByEmail() {
		return byEmail;
	}

	public void setByEmail(boolean byEmail) {
		this.byEmail = byEmail;
	}

	public boolean isBySms() {
		return bySms;
	}

	public void setBySms(boolean bySms) {
		this.bySms = bySms;
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("[");
        sb.append("emailFormat=");
        sb.append(this.emailFormat);
        sb.append(",preferences=");
        sb.append(Arrays.toString(this.preferences));
        sb.append(",iopSettings=");
        sb.append(Arrays.toString(this.iopSettings));
        sb.append("]");
        return sb.toString();
        //end toString
    }
}
