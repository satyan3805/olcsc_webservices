package com.etcc.csc.dto;

import java.math.BigDecimal;

public class AccountPhoneInfo {

	private BigDecimal personPhoneId;
	private String phoneNumber;
	private String phoneExtension;
	private String primaryCommunication;
	private String communicationType;

	public BigDecimal getPersonPhoneId() {
		return personPhoneId;
	}

	public void setPersonPhoneId(BigDecimal personPhoneId) {
		this.personPhoneId = personPhoneId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneExtension() {
		return phoneExtension;
	}

	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

	public String getPrimaryCommunication() {
		return primaryCommunication;
	}

	public void setPrimaryCommunication(String primaryCommunication) {
		this.primaryCommunication = primaryCommunication;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	@Override
	public String toString() {
		return "AccountPhoneInfo [personPhoneId=" + personPhoneId + ", phoneNumber=" + phoneNumber + ", phoneExtension="
				+ phoneExtension + ", primaryCommunication=" + primaryCommunication + ", communicationType="
				+ communicationType + "]";
	}

	
}
