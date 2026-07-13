package com.etcc.csc.dto;

import java.math.BigDecimal;

public class OlcUninvPmtRec {

	private BigDecimal accountId;
	private BigDecimal violationId;
	private BigDecimal tollPaidAmount;

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getViolationId() {
		return violationId;
	}

	public void setViolationId(BigDecimal violationId) {
		this.violationId = violationId;
	}

	public BigDecimal getTollPaidAmount() {
		return tollPaidAmount;
	}

	public void setTollPaidAmount(BigDecimal tollPaidAmount) {
		this.tollPaidAmount = tollPaidAmount;
	}

	@Override
	public String toString() {
		return "OlcUninvPmtRec [accountId=" + accountId + ", violationId=" + violationId + ", tollPaidAmount="
				+ tollPaidAmount + "]";
	}

}
