package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OlcVpsAccountConvRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1540466013707529015L;
	private BigDecimal personId;
	private BigDecimal addressId;
	private String acctConvElig;
	private BigDecimal ttlAmtDueForConversion;
	private BigDecimal initialPrePaidBalance;
	private BigDecimal tagActivationFee;
	private BigDecimal acctConvDiscPct;
	private BigDecimal totalDiscountedAmount;

	/**
	 * @return the personId
	 */
	public BigDecimal getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(BigDecimal personId) {
		this.personId = personId;
	}

	/**
	 * @return the addressId
	 */
	public BigDecimal getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(BigDecimal addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the acctConvElig
	 */
	public String getAcctConvElig() {
		return acctConvElig;
	}

	/**
	 * @param acctConvElig
	 *            the acctConvElig to set
	 */
	public void setAcctConvElig(String acctConvElig) {
		this.acctConvElig = acctConvElig;
	}

	/**
	 * @return the ttlAmtDueForConversion
	 */
	public BigDecimal getTtlAmtDueForConversion() {
		return ttlAmtDueForConversion;
	}

	/**
	 * @param ttlAmtDueForConversion
	 *            the ttlAmtDueForConversion to set
	 */
	public void setTtlAmtDueForConversion(BigDecimal ttlAmtDueForConversion) {
		this.ttlAmtDueForConversion = ttlAmtDueForConversion;
	}

	/**
	 * @return the initialPrePaidBalance
	 */
	public BigDecimal getInitialPrePaidBalance() {
		return initialPrePaidBalance;
	}

	/**
	 * @param initialPrePaidBalance
	 *            the initialPrePaidBalance to set
	 */
	public void setInitialPrePaidBalance(BigDecimal initialPrePaidBalance) {
		this.initialPrePaidBalance = initialPrePaidBalance;
	}

	/**
	 * @return the tagActivationFee
	 */
	public BigDecimal getTagActivationFee() {
		return tagActivationFee;
	}

	/**
	 * @param tagActivationFee
	 *            the tagActivationFee to set
	 */
	public void setTagActivationFee(BigDecimal tagActivationFee) {
		this.tagActivationFee = tagActivationFee;
	}

	/**
	 * @return the acctConvDiscPct
	 */
	public BigDecimal getAcctConvDiscPct() {
		return acctConvDiscPct;
	}

	/**
	 * @param acctConvDiscPct
	 *            the acctConvDiscPct to set
	 */
	public void setAcctConvDiscPct(BigDecimal acctConvDiscPct) {
		this.acctConvDiscPct = acctConvDiscPct;
	}

	/**
	 * @return the totalDiscountedAmount
	 */
	public BigDecimal getTotalDiscountedAmount() {
		return totalDiscountedAmount;
	}

	/**
	 * @param totalDiscountedAmount
	 *            the totalDiscountedAmount to set
	 */
	public void setTotalDiscountedAmount(BigDecimal totalDiscountedAmount) {
		this.totalDiscountedAmount = totalDiscountedAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcVpsAccountConvRec [personId=" + personId + ", addressId=" + addressId + ", acctConvElig="
				+ acctConvElig + ", ttlAmtDueForConversion=" + ttlAmtDueForConversion + ", initialPrePaidBalance="
				+ initialPrePaidBalance + ", tagActivationFee=" + tagActivationFee + ", acctConvDiscPct="
				+ acctConvDiscPct + ", totalDiscountedAmount=" + totalDiscountedAmount + "]";
	}

}
