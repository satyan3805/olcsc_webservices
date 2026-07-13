/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class AccountUpdateForm extends ValidatorActionForm {
	private static final long serialVersionUID = 4658488674629551137L;

	private boolean pwChangeRequired = false;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
    private boolean sqChangeRequired = false;
    private String securityQuestionID;
    private String securityQuestionAnswer;
    private String securityQuestionAnswer2;
    private boolean emailChangeRequired = false;
    private String emailAddress;
    private String emailAddress2;
    private String requiredUpdate;


    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) { 
    	pwChangeRequired = false;
        sqChangeRequired = false;
        emailChangeRequired = false;
    }

    public void setPwChangeRequired(boolean pwChangeRequired) {
        this.pwChangeRequired = pwChangeRequired;
    }

    public boolean isPwChangeRequired() {
        return pwChangeRequired;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setSqChangeRequired(boolean sqChangeRequired) {
        this.sqChangeRequired = sqChangeRequired;
    }

    public boolean isSqChangeRequired() {
        return sqChangeRequired;
    }

    public void setSecurityQuestionID(String securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
    }

    public String getSecurityQuestionID() {
        return securityQuestionID;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer2(String securityQuestionAnswer2) {
        this.securityQuestionAnswer2 = securityQuestionAnswer2;
    }

    public String getSecurityQuestionAnswer2() {
        return securityQuestionAnswer2;
    }

    public void setEmailChangeRequired(boolean emailChangeRequired) {
        this.emailChangeRequired = emailChangeRequired;
    }

    public boolean isEmailChangeRequired() {
        return emailChangeRequired;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setRequiredUpdate(String requiredUpdate) {
        this.requiredUpdate = requiredUpdate;
    }

    public String getRequiredUpdate() {
        return requiredUpdate;
    }
}
