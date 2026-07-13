package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.UserEnvDTO;

public class SetupOnlineAccessStepOneRequest {

	private String accountNumber;
	private String tolltagPrefix;
	private String tolltagNumber;
	private String emailAddress;
	private String phoneNumber;
	private String ipAddress;
	private String jsessionId;
	private UserEnvDTO userEnvDto;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTolltagPrefix() {
		return tolltagPrefix;
	}

	public void setTolltagPrefix(String tolltagPrefix) {
		this.tolltagPrefix = tolltagPrefix;
	}

	public String getTolltagNumber() {
		return tolltagNumber;
	}

	public void setTolltagNumber(String tolltagNumber) {
		this.tolltagNumber = tolltagNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}

	public UserEnvDTO getUserEnvDto() {
		return userEnvDto;
	}

	public void setUserEnvDto(UserEnvDTO userEnvDto) {
		this.userEnvDto = userEnvDto;
	}

	@Override
	public String toString() {
		return "SetupOnlineAccessStepOneRequest [accountNumber=" + accountNumber + ", tolltagPrefix=" + tolltagPrefix
				+ ", tolltagNumber=" + tolltagNumber + ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber
				+ ", ipAddress=" + ipAddress + ", jsessionId=" + jsessionId + ", userEnvDto=" + userEnvDto + "]";
	}

}
