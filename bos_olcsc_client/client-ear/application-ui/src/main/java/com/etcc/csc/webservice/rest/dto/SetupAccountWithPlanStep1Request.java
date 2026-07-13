package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.UserEnvDTO;

public class SetupAccountWithPlanStep1Request {

	private String loginId;
	private String password;
	private String emailAddress;
	private int securityQuestionID;
	private String securityQuestionAnswer;
	private String alternateEmail;
	private String ipAddress;
	private String sessionID;
	private UserEnvDTO userEnvDto;
	private String plan;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getSecurityQuestionID() {
		return securityQuestionID;
	}

	public void setSecurityQuestionID(int securityQuestionID) {
		this.securityQuestionID = securityQuestionID;
	}

	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public UserEnvDTO getUserEnvDto() {
		return userEnvDto;
	}

	public void setUserEnvDto(UserEnvDTO userEnvDto) {
		this.userEnvDto = userEnvDto;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "SetupAccountWithPlanStep1Request [loginId=" + loginId + ", password=" + password + ", emailAddress="
				+ emailAddress + ", securityQuestionID=" + securityQuestionID + ", securityQuestionAnswer="
				+ securityQuestionAnswer + ", alternateEmail=" + alternateEmail + ", ipAddress=" + ipAddress
				+ ", sessionID=" + sessionID + ", userEnvDto=" + userEnvDto + ", plan=" + plan + "]";
	}

}
