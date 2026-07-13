package com.etcc.csc.webservice.rest.dto;

public class GetViolationsRequest {
	private String licPlate;
	private String licState;
	private String invoiceNo;
	private Long accountId;
	private String ipAddress;
	private String jsessionId;
	private String sourceUserName;
	private String user;
	private String sessionId;
	private Integer pmtPlanId;
	private String isRetry;

	public String getLicPlate() {
		return licPlate;
	}

	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}

	public String getLicState() {
		return licState;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public String getSourceUserName() {
		return sourceUserName;
	}

	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getPmtPlanId() {
		return pmtPlanId;
	}

	public void setPmtPlanId(Integer pmtPlanId) {
		this.pmtPlanId = pmtPlanId;
	}
	
	public String getIsRetry() {
		return isRetry;
	}

	public void setIsRetry(String isRetry) {
		this.isRetry = isRetry;
	}

	@Override
	public String toString() {
		return "GetViolationsRequest [licPlate=" + licPlate + ", licState=" + licState + ", invoiceNo=" + invoiceNo
				+ ", accountId=" + accountId + ", ipAddress=" + ipAddress + ", jsessionId=" + jsessionId
				+ ", sourceUserName=" + sourceUserName + ", user=" + user + ", sessionId=" + sessionId + ", pmtPlanId="
				+ pmtPlanId + ", isRetry=" + isRetry + "]";
	}

	

}
