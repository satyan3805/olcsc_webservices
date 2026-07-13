/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * DTO for handling the setup of online access to the system.
 */
public class OnlineAccessSetupDTO extends BaseDTO {

    /**
     * Unique ID for Serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 3186946486843682474L;
    private long acctId;
    private String agencyId;
    private String tagId;
    private String driverLicNbr;
    private String driverLicState;
    private String companyTaxId;
    private String loginId;
    private String emailAddress;
    private int securityQuestionID;
    private String securityQuestionAnswer;
    private String dbSessionId;
    private String password;
    private String alternateEmail;
    private String securityQuestion;
    private String userID;
    private String validationId;
    private String phoneNumber;

    /**
     * Constructor.
     */
    public OnlineAccessSetupDTO() {
        // end <init>
    }

    /**
     * @return the acctId
     */
    public long getAcctId() {
        return this.acctId;
        // end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
        // end setAcctId
    }

    /**
     * @return the agencyId
     */
    public String getAgencyId() {
        return this.agencyId;
        // end getAgencyId
    }

    /**
     * @param agencyId the agencyId to set
     */
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
        // end setAgencyId
    }

    /**
     * @return the tagId
     */
    public String getTagId() {
        return this.tagId;
        // end getTagId
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
        // end setTagId
    }

    /**
     * @return the driverLicNbr
     */
    public String getDriverLicNbr() {
        return this.driverLicNbr;
        // end getDriverLicNbr
    }

    /**
     * @param driverLicNbr the driverLicNbr to set
     */
    public void setDriverLicNbr(String driverLicNbr) {
        this.driverLicNbr = driverLicNbr;
        // end setDriverLicNbr
    }

    /**
     * @return the driverLicState
     */
    public String getDriverLicState() {
        return this.driverLicState;
        // end getDriverLicState
    }

    /**
     * @param driverLicState the driverLicState to set
     */
    public void setDriverLicState(String driverLicState) {
        this.driverLicState = driverLicState;
        // end setDriverLicState
    }

    /**
     * @return the companyTaxId
     */
    public String getCompanyTaxId() {
        return this.companyTaxId;
        // end getCompanyTaxId
    }

    /**
     * @param companyTaxId the companyTaxId to set
     */
    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
        // end setCompanyTaxId
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return this.loginId;
        // end getLoginId
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
        // end setLoginId
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return this.emailAddress;
        // end getEmailAddress
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        // end setEmailAddress
    }

    /**
     * @return the securityQuestionID
     */
    public int getSecurityQuestionID() {
        return this.securityQuestionID;
        // end getSecurityQuestionID
    }

    /**
     * @param securityQuestionID the securityQuestionID to set
     */
    public void setSecurityQuestionID(int securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
        // end setSecurityQuestionID
    }

    /**
     * @return the securityQuestionAnswer
     */
    public String getSecurityQuestionAnswer() {
        return this.securityQuestionAnswer;
        // end getSecurityQuestionAnswer
    }

    /**
     * @param securityQuestionAnswer the securityQuestionAnswer to set
     */
    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
        // end setSecurityQuestionAnswer
    }

    /**
     * @return the dbSessionId
     */
    public String getDbSessionId() {
        return this.dbSessionId;
        // end getDbSessionId
    }

    /**
     * @param dbSessionId the dbSessionId to set
     */
    public void setDbSessionId(String dbSessionId) {
        this.dbSessionId = dbSessionId;
        // end setDbSessionId
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
        // end getPassword
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
        // end setPassword
    }

    /**
     * @return the alternateEmail
     */
    public String getAlternateEmail() {
        return this.alternateEmail;
        // end getAlternateEmail
    }

    /**
     * @param alternateEmail the alternateEmail to set
     */
    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
        // end setAlternateEmail
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return this.securityQuestion;
        // end getSecurityQuestion
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        // end setSecurityQuestion
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return this.userID;
        // end getUserID
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
        // end setUserID
    }
    

    public String getValidationId() {
		return validationId;
	}

	public void setValidationId(String validationId) {
		this.validationId = validationId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "OnlineAccessSetupDTO [acctId=" + acctId + ", agencyId=" + agencyId + ", tagId=" + tagId
				+ ", driverLicNbr=" + driverLicNbr + ", driverLicState=" + driverLicState + ", companyTaxId="
				+ companyTaxId + ", loginId=" + loginId + ", emailAddress=" + emailAddress + ", securityQuestionID="
				+ securityQuestionID + ", securityQuestionAnswer=" + securityQuestionAnswer + ", dbSessionId="
				+ dbSessionId + ", password=" + password + ", alternateEmail=" + alternateEmail + ", securityQuestion="
				+ securityQuestion + ", userID=" + userID + ", validationId=" + validationId + ", phoneNumber="
				+ phoneNumber + "]";
	}

}
