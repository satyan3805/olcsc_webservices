package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.UserEnvDTO;

public class LoginAccountRequest {

	private String userName;
	private String password;
	private String ipAddress;
	private String sessionId;
	private UserEnvDTO userEnvDto;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public UserEnvDTO getUserEnvDto() {
		return userEnvDto;
	}

	public void setUserEnvDto(UserEnvDTO userEnvDto) {
		this.userEnvDto = userEnvDto;
	}

	@Override
	public String toString() {
		return "LoginAccountRequest [userName=" + userName + ", password=" + password + ", ipAddress=" + ipAddress
				+ ", sessionId=" + sessionId + ", userEnvDto=" + userEnvDto + "]";
	}

}
