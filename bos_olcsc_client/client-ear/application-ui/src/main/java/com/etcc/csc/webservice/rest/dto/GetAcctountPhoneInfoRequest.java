package com.etcc.csc.webservice.rest.dto;

public class GetAcctountPhoneInfoRequest {
	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String ipAddress;
	private String loginId;
	public Long getAcctId() {
		return acctId;
	}
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getDbSessionId() {
		return dbSessionId;
	}
	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@Override
	public String toString() {
		return "GetAcctountPhoneInfoRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId="
				+ dbSessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId + "]";
	}

	
}
