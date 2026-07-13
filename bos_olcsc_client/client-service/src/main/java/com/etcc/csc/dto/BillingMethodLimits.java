package com.etcc.csc.dto;

import java.math.BigDecimal;

public class BillingMethodLimits {

	private BigDecimal accountId;
	private String plan;
	private BigDecimal minBillingMethod;
	private BigDecimal maxBillingMethod;
	private BigDecimal minCreditCard;
	private BigDecimal maxCreditCard;
	private BigDecimal minAchAccount;
	private BigDecimal maxAchAccount;

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public BigDecimal getMinBillingMethod() {
		return minBillingMethod;
	}

	public void setMinBillingMethod(BigDecimal minBillingMethod) {
		this.minBillingMethod = minBillingMethod;
	}

	public BigDecimal getMaxBillingMethod() {
		return maxBillingMethod;
	}

	public void setMaxBillingMethod(BigDecimal maxBillingMethod) {
		this.maxBillingMethod = maxBillingMethod;
	}

	public BigDecimal getMinCreditCard() {
		return minCreditCard;
	}

	public void setMinCreditCard(BigDecimal minCreditCard) {
		this.minCreditCard = minCreditCard;
	}

	public BigDecimal getMaxCreditCard() {
		return maxCreditCard;
	}

	public void setMaxCreditCard(BigDecimal maxCreditCard) {
		this.maxCreditCard = maxCreditCard;
	}

	public BigDecimal getMinAchAccount() {
		return minAchAccount;
	}

	public void setMinAchAccount(BigDecimal minAchAccount) {
		this.minAchAccount = minAchAccount;
	}

	public BigDecimal getMaxAchAccount() {
		return maxAchAccount;
	}

	public void setMaxAchAccount(BigDecimal maxAchAccount) {
		this.maxAchAccount = maxAchAccount;
	}

	@Override
	public String toString() {
		return "BillingMethodLimits [accountId=" + accountId + ", plan=" + plan + ", minBillingMethod="
				+ minBillingMethod + ", maxBillingMethod=" + maxBillingMethod + ", minCreditCard=" + minCreditCard
				+ ", maxCreditCard=" + maxCreditCard + ", minAchAccount=" + minAchAccount + ", maxAchAccount="
				+ maxAchAccount + "]";
	}

}
