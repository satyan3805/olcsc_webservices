package com.etcc.csc.webservice.rest.dto;

public class CheckEligibilityRequest {
	private String licPlate;
	private String licState;
	private String invoiceNo;
	private Long accountId;
	private String ipAddress;
	private String jsessionId;
	private String sourceUserName;
	private String user;
	private String sessionId;
	private Integer PmtPlanId;
	private String source;

	/**
	 * @return the licPlate
	 */
	public String getLicPlate() {
		return licPlate;
	}

	/**
	 * @param licPlate
	 *            the licPlate to set
	 */
	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}

	/**
	 * @return the licState
	 */
	public String getLicState() {
		return licState;
	}

	/**
	 * @param licState
	 *            the licState to set
	 */
	public void setLicState(String licState) {
		this.licState = licState;
	}

	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 *            the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the jsessionId
	 */
	public String getJsessionId() {
		return jsessionId;
	}

	/**
	 * @param jsessionId
	 *            the jsessionId to set
	 */
	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}

	/**
	 * @return the sourceUserName
	 */
	public String getSourceUserName() {
		return sourceUserName;
	}

	/**
	 * @param sourceUserName
	 *            the sourceUserName to set
	 */
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the pmtPlanId
	 */
	public Integer getPmtPlanId() {
		return PmtPlanId;
	}

	/**
	 * @param pmtPlanId
	 *            the pmtPlanId to set
	 */
	public void setPmtPlanId(Integer pmtPlanId) {
		PmtPlanId = pmtPlanId;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckEligibilityRequest [licPlate=" + licPlate + ", licState=" + licState + ", invoiceNo=" + invoiceNo
				+ ", accountId=" + accountId + ", ipAddress=" + ipAddress + ", jsessionId=" + jsessionId
				+ ", sourceUserName=" + sourceUserName + ", user=" + user + ", sessionId=" + sessionId + ", PmtPlanId="
				+ PmtPlanId + ", source=" + source + "]";
	}

}
