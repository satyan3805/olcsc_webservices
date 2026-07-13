package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OlcPmtPlanHstTypeRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5849349194151528869L;
	private BigDecimal paymentId;
	private String paymentForm;
	private String paymentType;
	private String last4;
	private Date paymentDate;
	private Date dueDate;
	private BigDecimal installmentNumber;
	private String installmentStatus;
	private BigDecimal paymentAmount;
	private BigDecimal installmentAmount;
	private BigDecimal paidAmount;
	private BigDecimal openAmount;

	public OlcPmtPlanHstTypeRec() {
		super();
	}

	public BigDecimal getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentForm() {
		return paymentForm;
	}

	public void setPaymentForm(String paymentForm) {
		this.paymentForm = paymentForm;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(BigDecimal installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public String getInstallmentStatus() {
		return installmentStatus;
	}

	public void setInstallmentStatus(String installmentStatus) {
		this.installmentStatus = installmentStatus;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(BigDecimal openAmount) {
		this.openAmount = openAmount;
	}

	@Override
	public String toString() {
		return "OlcPmtPlanHstTypeRecBean [paymentId=" + paymentId + ", paymentForm=" + paymentForm + ", paymentType="
				+ paymentType + ", last4=" + last4 + ", paymentDate=" + paymentDate + ", dueDate=" + dueDate
				+ ", installmentNumber=" + installmentNumber + ", installmentStatus=" + installmentStatus
				+ ", paymentAmount=" + paymentAmount + ", installmentAmount=" + installmentAmount + ", paidAmount="
				+ paidAmount + ", openAmount=" + openAmount + "]";
	}

}
