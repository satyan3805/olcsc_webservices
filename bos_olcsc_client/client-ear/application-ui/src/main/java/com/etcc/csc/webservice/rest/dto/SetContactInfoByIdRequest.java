package com.etcc.csc.webservice.rest.dto;

import java.math.BigDecimal;

public class SetContactInfoByIdRequest {

	private BigDecimal acctId;
	private String loginType;
	private String dbSessionId;
	private String ipAddress;
	private String loginId;
	private String emailAddress;
	private String altEmail;
	private String homePhoNbr;
	private String workPhoNbr;
	private String workPhoExt;
	private String homePhoExt;
	private String mobilePhone;
	private String mobilePhoExt;
	private String smsAlertsOptIn;
	private BigDecimal homePhoneId;
	private BigDecimal workPhoneId;
	private BigDecimal mobilePhoneId;

	public BigDecimal getAcctId() {
		return acctId;
	}

	public void setAcctId(BigDecimal acctId) {
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getHomePhoNbr() {
		return homePhoNbr;
	}

	public void setHomePhoNbr(String homePhoNbr) {
		this.homePhoNbr = homePhoNbr;
	}

	public String getWorkPhoNbr() {
		return workPhoNbr;
	}

	public void setWorkPhoNbr(String workPhoNbr) {
		this.workPhoNbr = workPhoNbr;
	}

	public String getWorkPhoExt() {
		return workPhoExt;
	}

	public void setWorkPhoExt(String workPhoExt) {
		this.workPhoExt = workPhoExt;
	}

	public String getHomePhoExt() {
		return homePhoExt;
	}

	public void setHomePhoExt(String homePhoExt) {
		this.homePhoExt = homePhoExt;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhoExt() {
		return mobilePhoExt;
	}

	public void setMobilePhoExt(String mobilePhoExt) {
		this.mobilePhoExt = mobilePhoExt;
	}

	public String getSmsAlertsOptIn() {
		return smsAlertsOptIn;
	}

	public void setSmsAlertsOptIn(String smsAlertsOptIn) {
		this.smsAlertsOptIn = smsAlertsOptIn;
	}

	public BigDecimal getHomePhoneId() {
		return homePhoneId;
	}

	public void setHomePhoneId(BigDecimal homePhoneId) {
		this.homePhoneId = homePhoneId;
	}

	public BigDecimal getWorkPhoneId() {
		return workPhoneId;
	}

	public void setWorkPhoneId(BigDecimal workPhoneId) {
		this.workPhoneId = workPhoneId;
	}

	public BigDecimal getMobilePhoneId() {
		return mobilePhoneId;
	}

	public void setMobilePhoneId(BigDecimal mobilePhoneId) {
		this.mobilePhoneId = mobilePhoneId;
	}

	@Override
	public String toString() {
		return "SetContactInfoByIdRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId="
				+ dbSessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", emailAddress=" + emailAddress
				+ ", altEmail=" + altEmail + ", homePhoNbr=" + homePhoNbr + ", workPhoNbr=" + workPhoNbr
				+ ", workPhoExt=" + workPhoExt + ", homePhoExt=" + homePhoExt + ", mobilePhone=" + mobilePhone
				+ ", mobilePhoExt=" + mobilePhoExt + ", smsAlertsOptIn=" + smsAlertsOptIn + ", homePhoneId="
				+ homePhoneId + ", workPhoneId=" + workPhoneId + ", mobilePhoneId=" + mobilePhoneId + "]";
	}

}
