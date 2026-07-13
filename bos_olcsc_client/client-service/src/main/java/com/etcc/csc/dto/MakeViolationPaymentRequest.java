package com.etcc.csc.dto;

import java.math.BigDecimal;
import java.util.List;

public class MakeViolationPaymentRequest {

	private AccountLoginDTO acctLoginDto;
	private BigDecimal accountVehicleId;
	private String emailAddress;
	private BigDecimal eventId;
	private String uiCall;
	private BigDecimal addressId;
	private String deliveryMethod;
	private String isMotorCycle;
	private String licPlate;
	private String licState;
	private BillingInfoDTO billingInfoDTO;
	private String sourceUserName;
	private BigDecimal tagActivationFee;
	private BigDecimal initialPrepaidBal;
	private Boolean convertViolator;
	private BigDecimal deliveryMode;
	private BigDecimal totalPaidAmount;
	private BigDecimal totalDiscountAmount;
	private String statusReason;
	private BigDecimal serviceFeePaid;
	private List<OlcInvPmtRec> invoicePaymentArray;
	private List<OlcUninvPmtRec> uninvoicePaymentArray;
	private String newLogin;
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public BigDecimal getAccountVehicleId() {
		return accountVehicleId;
	}
	public void setAccountVehicleId(BigDecimal accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public BigDecimal getEventId() {
		return eventId;
	}
	public void setEventId(BigDecimal eventId) {
		this.eventId = eventId;
	}
	public String getUiCall() {
		return uiCall;
	}
	public void setUiCall(String uiCall) {
		this.uiCall = uiCall;
	}
	public BigDecimal getAddressId() {
		return addressId;
	}
	public void setAddressId(BigDecimal addressId) {
		this.addressId = addressId;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getIsMotorCycle() {
		return isMotorCycle;
	}
	public void setIsMotorCycle(String isMotorCycle) {
		this.isMotorCycle = isMotorCycle;
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
	public BillingInfoDTO getBillingInfoDTO() {
		return billingInfoDTO;
	}
	public void setBillingInfoDTO(BillingInfoDTO billingInfoDTO) {
		this.billingInfoDTO = billingInfoDTO;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	public BigDecimal getTagActivationFee() {
		return tagActivationFee;
	}
	public void setTagActivationFee(BigDecimal tagActivationFee) {
		this.tagActivationFee = tagActivationFee;
	}
	public BigDecimal getInitialPrepaidBal() {
		return initialPrepaidBal;
	}
	public void setInitialPrepaidBal(BigDecimal initialPrepaidBal) {
		this.initialPrepaidBal = initialPrepaidBal;
	}
	public Boolean getConvertViolator() {
		return convertViolator;
	}
	public void setConvertViolator(Boolean convertViolator) {
		this.convertViolator = convertViolator;
	}
	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public BigDecimal getServiceFeePaid() {
		return serviceFeePaid;
	}
	public void setServiceFeePaid(BigDecimal serviceFeePaid) {
		this.serviceFeePaid = serviceFeePaid;
	}
	public List<OlcInvPmtRec> getInvoicePaymentArray() {
		return invoicePaymentArray;
	}
	public void setInvoicePaymentArray(List<OlcInvPmtRec> invoicePaymentArray) {
		this.invoicePaymentArray = invoicePaymentArray;
	}
	public List<OlcUninvPmtRec> getUninvoicePaymentArray() {
		return uninvoicePaymentArray;
	}
	public void setUninvoicePaymentArray(List<OlcUninvPmtRec> uninvoicePaymentArray) {
		this.uninvoicePaymentArray = uninvoicePaymentArray;
	}
	public String getNewLogin() {
		return newLogin;
	}
	public void setNewLogin(String newLogin) {
		this.newLogin = newLogin;
	}
	@Override
	public String toString() {
		return "MakeViolationPaymentRequest [acctLoginDto=" + acctLoginDto + ", accountVehicleId=" + accountVehicleId
				+ ", emailAddress=" + emailAddress + ", eventId=" + eventId + ", uiCall=" + uiCall + ", addressId="
				+ addressId + ", deliveryMethod=" + deliveryMethod + ", isMotorCycle=" + isMotorCycle + ", licPlate="
				+ licPlate + ", licState=" + licState + ", billingInfoDTO=" + billingInfoDTO + ", sourceUserName="
				+ sourceUserName + ", tagActivationFee=" + tagActivationFee + ", initialPrepaidBal=" + initialPrepaidBal
				+ ", convertViolator=" + convertViolator + ", deliveryMode=" + deliveryMode + ", totalPaidAmount="
				+ totalPaidAmount + ", totalDiscountAmount=" + totalDiscountAmount + ", statusReason=" + statusReason
				+ ", serviceFeePaid=" + serviceFeePaid + ", invoicePaymentArray=" + invoicePaymentArray
				+ ", uninvoicePaymentArray=" + uninvoicePaymentArray + ", newLogin=" + newLogin + "]";
	}
	
	

}
