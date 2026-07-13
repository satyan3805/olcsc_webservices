package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.OnlineAccessSetupDTO;

public class ValidateSecurityAnswerRequest {
	private OnlineAccessSetupDTO onlineAccessDTO;
	private String sessionId;
	private String ipAddress;

	public OnlineAccessSetupDTO getOnlineAccessDTO() {
		return onlineAccessDTO;
	}

	public void setOnlineAccessDTO(OnlineAccessSetupDTO onlineAccessDTO) {
		this.onlineAccessDTO = onlineAccessDTO;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "ValidateSecurityAnswerRequest [onlineAccessDTO=" + onlineAccessDTO + ", sessionId=" + sessionId
				+ ", ipAddress=" + ipAddress + "]";
	}

}
