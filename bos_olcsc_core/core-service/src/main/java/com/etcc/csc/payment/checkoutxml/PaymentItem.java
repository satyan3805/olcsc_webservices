package com.etcc.csc.payment.checkoutxml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentItem implements CartXML, Serializable {
	private static final long serialVersionUID = 4271224390932595333L;

	@XmlAttribute(name = "sequence", required = true)
	private int sequence;

	@XmlElement(name = "form", required = true)
	private String paymentForm;

	@XmlElement(name = "payment_id")
	private Long paymentId;

	@XmlElement(name = "token")
	private Long token;

	@XmlElement(name = "cvv")
	private Long cvv;

	// one time credit card data
	@XmlElement(name = "credit_card_type_id")
	private Long creditCardTypeId;

	@XmlElement(name = "credit_card_exp_date")
	private String creditCardExpDate;

	@XmlElement(name = "cc_last4")
	private Integer ccLast4;

	@XmlElement(name = "payment_status_code")
	private String paymentStatusCode;

	@XmlElement(name = "delivery_mode")
	private Long deliveryMode;

	@XmlElement(name = "ca_company_id")
	private Long caCompanyId;

	@XmlElement(name = "is_combined")
	private Boolean isCombined;

	@XmlElement(name = "items_start_date")
	private String itemsStartDate;

	@XmlElement(name = "items_end_date")
	private String itemsEndDate;

	@XmlElement(name = "billing_method")
	private Long billingMethod;

	@XmlElement(name = "amount")
	private Double amount;

	@XmlElement(name = "check_mo_nbr")
	private String checkMoNumber;

	@XmlElement(name = "aba_number")
	private String abaNumber;

	@XmlElement(name = "comment_action_id")
	private Long commentActionId;

	@XmlElement(name = "comment")
	private String comments;

	@XmlElement(name = "person_id")
	private Long personId;

	@XmlElement(name = "person_revision_number")
	private Long personRevisionNumber;

	@XmlElement(name = "address_id")
	private Long addressId;

	@XmlElement(name = "person_phone_id")
	private Long personPhoneId;

	@XmlElement(name = "phone_revision_number")
	private Long phoneRevisionNumber;

	@XmlElement(name = "person_email_id")
	private Long personEmailId;

	@XmlElement(name = "email_revision_number")
	private Long emailRevisionNumber;

	public String getitemTypeDescription() {
		return paymentForm;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getPaymentForm() {
		return paymentForm;
	}

	public void setPaymentForm(String paymentForm) {
		this.paymentForm = paymentForm;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	public Long getCvv() {
		return cvv;
	}

	public void setCvv(Long cvv) {
		this.cvv = cvv;
	}

	public Long getCreditCardTypeId() {
		return creditCardTypeId;
	}

	public void setCreditCardTypeId(Long creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
	}

	public String getCreditCardExpDate() {
		return creditCardExpDate;
	}

	public void setCreditCardExpDate(String creditCardExpDate) {
		this.creditCardExpDate = creditCardExpDate;
	}

	public Integer getCcLast4() {
		return ccLast4;
	}

	public void setCcLast4(Integer ccLast4) {
		this.ccLast4 = ccLast4;
	}

	public String getPaymentStatusCode() {
		return paymentStatusCode;
	}

	public void setPaymentStatusCode(String paymentStatusCode) {
		this.paymentStatusCode = paymentStatusCode;
	}

	public Long getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(Long deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Long getCaCompanyId() {
		return caCompanyId;
	}

	public void setCaCompanyId(Long caCompanyId) {
		this.caCompanyId = caCompanyId;
	}

	public Boolean getIsCombined() {
		return isCombined;
	}

	public void setIsCombined(Boolean isCombined) {
		this.isCombined = isCombined;
	}

	public String getItemsStartDate() {
		return itemsStartDate;
	}

	public void setItemsStartDate(String itemsStartDate) {
		this.itemsStartDate = itemsStartDate;
	}

	public String getItemsEndDate() {
		return itemsEndDate;
	}

	public void setItemsEndDate(String itemsEndDate) {
		this.itemsEndDate = itemsEndDate;
	}

	public Long getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(Long billingMethod) {
		this.billingMethod = billingMethod;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCheckMoNumber() {
		return checkMoNumber;
	}

	public void setCheckMoNumber(String checkMoNumber) {
		this.checkMoNumber = checkMoNumber;
	}

	public String getAbaNumber() {
		return abaNumber;
	}

	public void setAbaNumber(String abaNumber) {
		this.abaNumber = abaNumber;
	}

	public Long getCommentActionId() {
		return commentActionId;
	}

	public void setCommentActionId(Long commentActionId) {
		this.commentActionId = commentActionId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getPersonRevisionNumber() {
		return personRevisionNumber;
	}

	public void setPersonRevisionNumber(Long personRevisionNumber) {
		this.personRevisionNumber = personRevisionNumber;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getPersonPhoneId() {
		return personPhoneId;
	}

	public void setPersonPhoneId(Long personPhoneId) {
		this.personPhoneId = personPhoneId;
	}

	public Long getPhoneRevisionNumber() {
		return phoneRevisionNumber;
	}

	public void setPhoneRevisionNumber(Long phoneRevisionNumber) {
		this.phoneRevisionNumber = phoneRevisionNumber;
	}

	public Long getPersonEmailId() {
		return personEmailId;
	}

	public void setPersonEmailId(Long personEmailId) {
		this.personEmailId = personEmailId;
	}

	public Long getEmailRevisionNumber() {
		return emailRevisionNumber;
	}

	public void setEmailRevisionNumber(Long emailRevisionNumber) {
		this.emailRevisionNumber = emailRevisionNumber;
	}
}
