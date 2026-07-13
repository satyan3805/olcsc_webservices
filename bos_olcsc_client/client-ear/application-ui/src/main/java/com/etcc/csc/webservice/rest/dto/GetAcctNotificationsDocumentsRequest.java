package com.etcc.csc.webservice.rest.dto;

public class GetAcctNotificationsDocumentsRequest {

	private Long acctId;
	private String loginType;
	private String loginId;
	private String dbSessionId;
	private String ipAddress;

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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	@Override
	public String toString() {
		return "GetAcctNotificationsDocumentsRequest [acctId=" + acctId + ", loginType=" + loginType + ", loginId="
				+ loginId + ", dbSessionId=" + dbSessionId + ", ipAddress=" + ipAddress + "]";
	}

}
