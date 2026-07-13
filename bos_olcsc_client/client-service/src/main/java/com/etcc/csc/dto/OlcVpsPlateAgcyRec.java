package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OlcVpsPlateAgcyRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934278522884100731L;
	private BigDecimal agencyId;
	private String discountRule;
	private BigDecimal ttlAmountDue;
	private BigDecimal totalExcAmount;
	private BigDecimal ttlAmountDueAfterDisc;
	private String statusReason;
	private BigDecimal serviceFee;

	/**
	 * @return the agencyId
	 */
	public BigDecimal getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId
	 *            the agencyId to set
	 */
	public void setAgencyId(BigDecimal agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the discountRule
	 */
	public String getDiscountRule() {
		return discountRule;
	}

	/**
	 * @param discountRule
	 *            the discountRule to set
	 */
	public void setDiscountRule(String discountRule) {
		this.discountRule = discountRule;
	}

	/**
	 * @return the ttlAmountDue
	 */
	public BigDecimal getTtlAmountDue() {
		return ttlAmountDue;
	}

	/**
	 * @param ttlAmountDue
	 *            the ttlAmountDue to set
	 */
	public void setTtlAmountDue(BigDecimal ttlAmountDue) {
		this.ttlAmountDue = ttlAmountDue;
	}

	/**
	 * @return the totalExcAmount
	 */
	public BigDecimal getTotalExcAmount() {
		return totalExcAmount;
	}

	/**
	 * @param totalExcAmount
	 *            the totalExcAmount to set
	 */
	public void setTotalExcAmount(BigDecimal totalExcAmount) {
		this.totalExcAmount = totalExcAmount;
	}

	/**
	 * @return the ttlAmountDueAfterDisc
	 */
	public BigDecimal getTtlAmountDueAfterDisc() {
		return ttlAmountDueAfterDisc;
	}

	/**
	 * @param ttlAmountDueAfterDisc
	 *            the ttlAmountDueAfterDisc to set
	 */
	public void setTtlAmountDueAfterDisc(BigDecimal ttlAmountDueAfterDisc) {
		this.ttlAmountDueAfterDisc = ttlAmountDueAfterDisc;
	}

	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason
	 *            the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the serviceFee
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * @param serviceFee
	 *            the serviceFee to set
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcVpsPlateAgcyRec [agencyId=" + agencyId + ", discountRule=" + discountRule + ", ttlAmountDue="
				+ ttlAmountDue + ", totalExcAmount=" + totalExcAmount + ", ttlAmountDueAfterDisc="
				+ ttlAmountDueAfterDisc + ", statusReason=" + statusReason + ", serviceFee=" + serviceFee + "]";
	}

}
