package com.etcc.csc.webservice.rest.dto;

import java.math.BigDecimal;
public class UpdateRebillAmtRequest {
	private String acctID;
	private String acctType;
	private String sessionID;
	private String ipAddress;
	private String loginId;
	private BigDecimal rebillAmt;
	private BigDecimal lowBalanceLevel;

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

	public BigDecimal getRebillAmt() {
		return rebillAmt;
	}

	public void setRebillAmt(BigDecimal rebillAmt) {
		this.rebillAmt = rebillAmt;
	}

	public BigDecimal getLowBalanceLevel() {
		return lowBalanceLevel;
	}

	public void setLowBalanceLevel(BigDecimal lowBalanceLevel) {
		this.lowBalanceLevel = lowBalanceLevel;
	}

	@Override
	public String toString() {
		return "UpdateRebillAmtRequest [acctID=" + acctID + ", acctType=" + acctType + ", sessionID=" + sessionID
				+ ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", rebillAmt=" + rebillAmt
				+ ", lowBalanceLevel=" + lowBalanceLevel + "]";
	}

}
