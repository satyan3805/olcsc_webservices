package com.etcc.csc.webservice.rest.dto;

public class UpdateContactInfoRequest {
	private String acctID;
	private String acctType;
	private String sessionID;
	private String ipAddress;
	private String loginId;
	private String emailAddress;
	private String altEmailAddress;
	private String primaryPhone;
	private String alternatePhone;
	private String alternatePhoneExt;
	private String primaryPhoneExt;
	private String mobilePhone;
	private String mobilePhoneExt;
	private String smsAlertsOptIn;

	public String getAcctID() {
		return acctID;
	}

	public void setAcctID(String acctID) {
		this.acctID = acctID;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAltEmailAddress() {
		return altEmailAddress;
	}

	public void setAltEmailAddress(String altEmailAddress) {
		this.altEmailAddress = altEmailAddress;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getAlternatePhoneExt() {
		return alternatePhoneExt;
	}

	public void setAlternatePhoneExt(String alternatePhoneExt) {
		this.alternatePhoneExt = alternatePhoneExt;
	}

	public String getPrimaryPhoneExt() {
		return primaryPhoneExt;
	}

	public void setPrimaryPhoneExt(String primaryPhoneExt) {
		this.primaryPhoneExt = primaryPhoneExt;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhoneExt() {
		return mobilePhoneExt;
	}

	public void setMobilePhoneExt(String mobilePhoneExt) {
		this.mobilePhoneExt = mobilePhoneExt;
	}

	public String getSmsAlertsOptIn() {
		return smsAlertsOptIn;
	}

	public void setSmsAlertsOptIn(String smsAlertsOptIn) {
		this.smsAlertsOptIn = smsAlertsOptIn;
	}

	@Override
	public String toString() {
		return "UpdateContactInfoRequest [acctID=" + acctID + ", acctType=" + acctType + ", sessionID=" + sessionID
				+ ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", emailAddress=" + emailAddress
				+ ", altEmailAddress=" + altEmailAddress + ", primaryPhone=" + primaryPhone + ", alternatePhone="
				+ alternatePhone + ", alternatePhoneExt=" + alternatePhoneExt + ", primaryPhoneExt=" + primaryPhoneExt
				+ ", mobilePhone=" + mobilePhone + ", mobilePhoneExt=" + mobilePhoneExt + ", smsAlertsOptIn="
				+ smsAlertsOptIn + "]";
	}

}
