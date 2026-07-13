package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OlcPmtPlanInstTypeRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937873032759442656L;
	private BigDecimal paymentPlanInstallmentId;
	private String planInstallmentStatusCode;
	private BigDecimal paymentPlanId;
	private BigDecimal itemsPaymentId;
	private String isFuture;
	private BigDecimal installmentNumber;
	private Date dueDate;
	private BigDecimal installmentAmount;
	private BigDecimal openAmount;
	private BigDecimal paidAmount;
	private BigDecimal recordVersion;
	private Date statusDate;
	private BigDecimal tollAmount;
	private BigDecimal feeAmount;
	private String createdBy;
	private Date dateCreated;
	private String modifiedBy;
	private Date dateModified;
	private BigDecimal planInstallmentTypeId;
	private BigDecimal lastCartId;
	private BigDecimal uncollectableAmount;
	private BigDecimal cancelledAmount;

	public OlcPmtPlanInstTypeRec() {
		super();
	}

	public BigDecimal getPaymentPlanInstallmentId() {
		return paymentPlanInstallmentId;
	}

	public void setPaymentPlanInstallmentId(BigDecimal paymentPlanInstallmentId) {
		this.paymentPlanInstallmentId = paymentPlanInstallmentId;
	}

	public String getPlanInstallmentStatusCode() {
		return planInstallmentStatusCode;
	}

	public void setPlanInstallmentStatusCode(String planInstallmentStatusCode) {
		this.planInstallmentStatusCode = planInstallmentStatusCode;
	}

	public BigDecimal getPaymentPlanId() {
		return paymentPlanId;
	}

	public void setPaymentPlanId(BigDecimal paymentPlanId) {
		this.paymentPlanId = paymentPlanId;
	}

	public BigDecimal getItemsPaymentId() {
		return itemsPaymentId;
	}

	public void setItemsPaymentId(BigDecimal itemsPaymentId) {
		this.itemsPaymentId = itemsPaymentId;
	}

	public String getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(String isFuture) {
		this.isFuture = isFuture;
	}

	public BigDecimal getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(BigDecimal installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public BigDecimal getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(BigDecimal openAmount) {
		this.openAmount = openAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(BigDecimal recordVersion) {
		this.recordVersion = recordVersion;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public BigDecimal getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(BigDecimal tollAmount) {
		this.tollAmount = tollAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public BigDecimal getPlanInstallmentTypeId() {
		return planInstallmentTypeId;
	}

	public void setPlanInstallmentTypeId(BigDecimal planInstallmentTypeId) {
		this.planInstallmentTypeId = planInstallmentTypeId;
	}

	public BigDecimal getLastCartId() {
		return lastCartId;
	}

	public void setLastCartId(BigDecimal lastCartId) {
		this.lastCartId = lastCartId;
	}

	public BigDecimal getUncollectableAmount() {
		return uncollectableAmount;
	}

	public void setUncollectableAmount(BigDecimal uncollectableAmount) {
		this.uncollectableAmount = uncollectableAmount;
	}

	public BigDecimal getCancelledAmount() {
		return cancelledAmount;
	}

	public void setCancelledAmount(BigDecimal cancelledAmount) {
		this.cancelledAmount = cancelledAmount;
	}

	@Override
	public String toString() {
		return "OlcPmtPlanInstTypeRecBean [paymentPlanInstallmentId=" + paymentPlanInstallmentId
				+ ", planInstallmentStatusCode=" + planInstallmentStatusCode + ", paymentPlanId=" + paymentPlanId
				+ ", itemsPaymentId=" + itemsPaymentId + ", isFuture=" + isFuture + ", installmentNumber="
				+ installmentNumber + ", dueDate=" + dueDate + ", installmentAmount=" + installmentAmount
				+ ", openAmount=" + openAmount + ", paidAmount=" + paidAmount + ", recordVersion=" + recordVersion
				+ ", statusDate=" + statusDate + ", tollAmount=" + tollAmount + ", feeAmount=" + feeAmount
				+ ", createdBy=" + createdBy + ", dateCreated=" + dateCreated + ", modifiedBy=" + modifiedBy
				+ ", dateModified=" + dateModified + ", planInstallmentTypeId=" + planInstallmentTypeId
				+ ", lastCartId=" + lastCartId + ", uncollectableAmount=" + uncollectableAmount + ", cancelledAmount="
				+ cancelledAmount + "]";
	}

}
