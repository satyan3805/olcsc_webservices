package com.etcc.csc.webservice.rest.dto;

public class GetMailedTagsRequest {

	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String ipAddress;
	private String loginId;
	private String transactionId;
	private String driverLicenseNumber;
	private String driverLicenseState;
	private String taxId;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public String getDriverLicenseState() {
		return driverLicenseState;
	}

	public void setDriverLicenseState(String driverLicenseState) {
		this.driverLicenseState = driverLicenseState;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Override
	public String toString() {
		return "GetMailedTagsRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId=" + dbSessionId
				+ ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", transactionId=" + transactionId
				+ ", driverLicenseNumber=" + driverLicenseNumber + ", driverLicenseState=" + driverLicenseState
				+ ", taxId=" + taxId + "]";
	}

}
