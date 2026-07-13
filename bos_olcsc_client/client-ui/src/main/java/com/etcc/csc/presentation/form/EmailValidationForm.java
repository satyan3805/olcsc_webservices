/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import org.apache.struts.validator.ValidatorActionForm;

public class EmailValidationForm extends ValidatorActionForm {
	private static final long serialVersionUID = 3269622905136945714L;

	// private String name;
	private long accountId;
    private String primaryEmailAddress;
    private String alternateEmailAddress;
    private String emailAddress;
    private int validationStatus; 
    private boolean display=false;
    private boolean confirm = false;
    

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setAlternateEmailAddress(String alternateEmailAddress) {
        this.alternateEmailAddress = alternateEmailAddress;
    }

    public String getAlternateEmailAddress() {
        return alternateEmailAddress;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setValidationStatus(int validationStatus) {
        this.validationStatus = validationStatus;
    }

    public int getValidationStatus() {
        return validationStatus;
    }

    public void setDisplay(boolean isDisplay) {
        this.display = isDisplay;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isConfirm() {
        return confirm;
    }
}
