package com.etcc.csc.datatype;

import java.io.Serializable;

public class PlanDetailBalances implements Serializable {
	private static final long serialVersionUID = 5128052826069746752L;

	private Long planDetailBalanceId;
	private Long planDetailId;
	private String accountBalanceTypeCode;
	private String balanceBreakdownTypeCode;
	private Double amount;
	private Integer multiplier;
	private Long tagTypeId;

	public Long getPlanDetailBalanceId() {
		return planDetailBalanceId;
	}

	public void setPlanDetailBalanceId(Long planDetailBalanceId) {
		this.planDetailBalanceId = planDetailBalanceId;
	}

	public Long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(Long planDetailId) {
		this.planDetailId = planDetailId;
	}

	public String getAccountBalanceTypeCode() {
		return accountBalanceTypeCode;
	}

	public void setAccountBalanceTypeCode(String accountBalanceTypeCode) {
		this.accountBalanceTypeCode = accountBalanceTypeCode;
	}

	public String getBalanceBreakdownTypeCode() {
		return balanceBreakdownTypeCode;
	}

	public void setBalanceBreakdownTypeCode(String balanceBreakdownTypeCode) {
		this.balanceBreakdownTypeCode = balanceBreakdownTypeCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Integer multiplier) {
		this.multiplier = multiplier;
	}

	public Long getTagTypeId() {
		return tagTypeId;
	}

	public void setTagTypeId(Long tagTypeId) {
		this.tagTypeId = tagTypeId;
	}
}// end of PlanDetailBalances Class
