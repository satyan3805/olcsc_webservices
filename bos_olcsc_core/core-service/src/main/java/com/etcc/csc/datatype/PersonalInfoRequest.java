package com.etcc.csc.datatype;

public class PersonalInfoRequest {
	private long accountId;
	private String address;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	private String zipPlus4;
	private String homePhoneNumber;
	private String workPhoneNumber;
	private String workPhoneExt;
	private String dlNumber;
	private String dlState;
	private String monthlyStmtFlag;
	private int msId;
	private String dbSessionId;
	private String loginId;
	private String ipAddress;
	private Long transactionId;
	private boolean checkDuplicateDriverLicense;
	private Long posId;
	private String loginType;
	private String eventId;
	private String dlCountry;

	public PersonalInfoRequest() {
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipPlus4(String zipPlus4) {
		this.zipPlus4 = zipPlus4;
	}

	public String getZipPlus4() {
		return zipPlus4;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneExt(String workPhoneExt) {
		this.workPhoneExt = workPhoneExt;
	}

	public String getWorkPhoneExt() {
		return workPhoneExt;
	}

	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}

	public String getDlNumber() {
		return dlNumber;
	}

	public void setDlState(String dlState) {
		this.dlState = dlState;
	}

	public String getDlState() {
		return dlState;
	}

	public void setMonthlyStmtFlag(String monthlyStmtFlag) {
		this.monthlyStmtFlag = monthlyStmtFlag;
	}

	public String getMonthlyStmtFlag() {
		return monthlyStmtFlag;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}

	public int getMsId() {
		return msId;
	}

	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}

	public String getDbSessionId() {
		return dbSessionId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setCheckDuplicateDriverLicense(
			boolean checkDuplicateDriverLicense) {
		this.checkDuplicateDriverLicense = checkDuplicateDriverLicense;
	}

	public boolean isCheckDuplicateDriverLicense() {
		return checkDuplicateDriverLicense;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Long getPosId() {
		return posId;
	}

	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @param loginType
	 *            the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getDlCountry() {
		return dlCountry;
	}

	public void setDlCountry(String dlCountry) {
		this.dlCountry = dlCountry;
	}
}
