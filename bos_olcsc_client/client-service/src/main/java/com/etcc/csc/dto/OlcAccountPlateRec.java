package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OlcAccountPlateRec implements Serializable{

	private static final long serialVersionUID = 3193583164236394245L;
	
	private String discountRule;
	private BigDecimal accountId;
	private String accountPlan;
	private BigDecimal ttlAmountDue;
	private BigDecimal origInvFeeAmount;
	private BigDecimal openInvFeeAmount;
	private BigDecimal origInvAmount;
	private BigDecimal openInvAmount;
	private BigDecimal origUninvAmount;
	private BigDecimal openUninvAmount;
	private BigDecimal totalPaidAmount;
	private BigDecimal totalExcAmount;
	private BigDecimal accountDueAfterExc;
	private BigDecimal accountDueBeforeExc;
	private BigDecimal serviceFee;
	private BigDecimal invTollOpenAmount;
	private BigDecimal univTollOpenAmount;
	private BigDecimal ttlTollOpenAmount;
	private List<OlcVpsPlateTypeRec> olcPlateTypeArr = new ArrayList<OlcVpsPlateTypeRec>();
	private String sessionId;
	public String getDiscountRule() {
		return discountRule;
	}
	public void setDiscountRule(String discountRule) {
		this.discountRule = discountRule;
	}
	public BigDecimal getAccountId() {
		return accountId;
	}
	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}
	public String getAccountPlan() {
		return accountPlan;
	}
	public void setAccountPlan(String accountPlan) {
		this.accountPlan = accountPlan;
	}
	public BigDecimal getTtlAmountDue() {
		return ttlAmountDue;
	}
	public void setTtlAmountDue(BigDecimal ttlAmountDue) {
		this.ttlAmountDue = ttlAmountDue;
	}
	public BigDecimal getOrigInvFeeAmount() {
		return origInvFeeAmount;
	}
	public void setOrigInvFeeAmount(BigDecimal origInvFeeAmount) {
		this.origInvFeeAmount = origInvFeeAmount;
	}
	public BigDecimal getOpenInvFeeAmount() {
		return openInvFeeAmount;
	}
	public void setOpenInvFeeAmount(BigDecimal openInvFeeAmount) {
		this.openInvFeeAmount = openInvFeeAmount;
	}
	public BigDecimal getOrigInvAmount() {
		return origInvAmount;
	}
	public void setOrigInvAmount(BigDecimal origInvAmount) {
		this.origInvAmount = origInvAmount;
	}
	public BigDecimal getOpenInvAmount() {
		return openInvAmount;
	}
	public void setOpenInvAmount(BigDecimal openInvAmount) {
		this.openInvAmount = openInvAmount;
	}
	public BigDecimal getOrigUninvAmount() {
		return origUninvAmount;
	}
	public void setOrigUninvAmount(BigDecimal origUninvAmount) {
		this.origUninvAmount = origUninvAmount;
	}
	public BigDecimal getOpenUninvAmount() {
		return openUninvAmount;
	}
	public void setOpenUninvAmount(BigDecimal openUninvAmount) {
		this.openUninvAmount = openUninvAmount;
	}
	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public BigDecimal getTotalExcAmount() {
		return totalExcAmount;
	}
	public void setTotalExcAmount(BigDecimal totalExcAmount) {
		this.totalExcAmount = totalExcAmount;
	}
	public BigDecimal getAccountDueAfterExc() {
		return accountDueAfterExc;
	}
	public void setAccountDueAfterExc(BigDecimal accountDueAfterExc) {
		this.accountDueAfterExc = accountDueAfterExc;
	}
	public BigDecimal getAccountDueBeforeExc() {
		return accountDueBeforeExc;
	}
	public void setAccountDueBeforeExc(BigDecimal accountDueBeforeExc) {
		this.accountDueBeforeExc = accountDueBeforeExc;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public BigDecimal getInvTollOpenAmount() {
		return invTollOpenAmount;
	}
	public void setInvTollOpenAmount(BigDecimal invTollOpenAmount) {
		this.invTollOpenAmount = invTollOpenAmount;
	}
	public BigDecimal getUnivTollOpenAmount() {
		return univTollOpenAmount;
	}
	public void setUnivTollOpenAmount(BigDecimal univTollOpenAmount) {
		this.univTollOpenAmount = univTollOpenAmount;
	}
	public BigDecimal getTtlTollOpenAmount() {
		return ttlTollOpenAmount;
	}
	public void setTtlTollOpenAmount(BigDecimal ttlTollOpenAmount) {
		this.ttlTollOpenAmount = ttlTollOpenAmount;
	}
	public List<OlcVpsPlateTypeRec> getOlcPlateTypeArr() {
		return olcPlateTypeArr;
	}
	public void setOlcPlateTypeArr(List<OlcVpsPlateTypeRec> olcPlateTypeArr) {
		this.olcPlateTypeArr = olcPlateTypeArr;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "OlcAccountPlateRec [discountRule=" + discountRule + ", accountId=" + accountId + ", accountPlan="
				+ accountPlan + ", ttlAmountDue=" + ttlAmountDue + ", origInvFeeAmount=" + origInvFeeAmount
				+ ", openInvFeeAmount=" + openInvFeeAmount + ", origInvAmount=" + origInvAmount + ", openInvAmount="
				+ openInvAmount + ", origUninvAmount=" + origUninvAmount + ", openUninvAmount=" + openUninvAmount
				+ ", totalPaidAmount=" + totalPaidAmount + ", totalExcAmount=" + totalExcAmount
				+ ", accountDueAfterExc=" + accountDueAfterExc + ", accountDueBeforeExc=" + accountDueBeforeExc
				+ ", serviceFee=" + serviceFee + ", invTollOpenAmount=" + invTollOpenAmount + ", univTollOpenAmount="
				+ univTollOpenAmount + ", ttlTollOpenAmount=" + ttlTollOpenAmount + ", olcPlateTypeArr="
				+ olcPlateTypeArr + ", sessionId=" + sessionId + "]";
	}

	
}
