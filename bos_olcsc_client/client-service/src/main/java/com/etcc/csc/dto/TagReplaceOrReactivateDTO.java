package com.etcc.csc.dto;

public class TagReplaceOrReactivateDTO {
	private String accountId;
	private String docType;
	private String sessionId;
	private String ipAddress;
	private String userId;
	private String tagId;
	private String licPlate;
	private String licState;
	private Long accountVehicleId;
	private String  motorcycleFlag;
	private String deliveryMethod;
	private String action;
	private String reason;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
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
	public Long getAccountVehicleId() {
		return accountVehicleId;
	}
	public void setAccountVehicleId(Long accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}
	public String getMotorcycleFlag() {
		return motorcycleFlag;
	}
	public void setMotorcycleFlag(String motorcycleFlag) {
		this.motorcycleFlag = motorcycleFlag;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "TagReplaceOrReactivateDTO [accountId=" + accountId + ", docType=" + docType + ", sessionId=" + sessionId
				+ ", ipAddress=" + ipAddress + ", userId=" + userId + ", tagId=" + tagId + ", licPlate=" + licPlate
				+ ", licState=" + licState + ", accountVehicleId=" + accountVehicleId + ", motorcycleFlag="
				+ motorcycleFlag + ", deliveryMethod=" + deliveryMethod + ", action=" + action + ", reason=" + reason
				+ "]";
	}
	
	
	

}
