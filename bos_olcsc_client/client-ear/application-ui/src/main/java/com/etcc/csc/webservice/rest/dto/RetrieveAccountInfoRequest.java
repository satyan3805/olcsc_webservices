package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;

public class RetrieveAccountInfoRequest {
	
	private OnlineAccessSetupDTO onlineAccessDTO;
	private String ipAddress;
	private String sessionId;
	private UserEnvDTO userEnvDto;

	public OnlineAccessSetupDTO getOnlineAccessDTO() {
		return onlineAccessDTO;
	}

	public void setOnlineAccessDTO(OnlineAccessSetupDTO onlineAccessDTO) {
		this.onlineAccessDTO = onlineAccessDTO;
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
		return "RetrieveAccountInfoRequest [onlineAccessDTO=" + onlineAccessDTO + ", ipAddress=" + ipAddress
				+ ", sessionId=" + sessionId + ", userEnvDto=" + userEnvDto + "]";
	}

}
