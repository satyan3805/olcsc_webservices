package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;

public class OlcPmtPlanTypeRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319390959867192840L;
	private BigDecimal paymentPlanId;
	private BigDecimal accountId;
	private String planStatusCode;
	private String installmentFreqTypeCode;
	private String paymentPlanNumber;
	private BigDecimal planDiscountAmount;
	private BigDecimal planSettlementAmount;
	private BigDecimal planTotalAmount;
	private BigDecimal uncollectableAmount;
	private BigDecimal numberOfInstallments;
	private Date firstPaymentDate;
	private BigDecimal rebillFailedCount;
	private BigDecimal openAmount;
	private BigDecimal paidAmount;
	private Date statusDate;
	private BigDecimal agcyId;
	private BigDecimal accountBillingMethodId;
	private String billingFullName;
	private String billingPaymentForm;
	private BigDecimal billingEftActTypeId;
	private String billingCardType;
	private List<OlcPaymentInfoRec> pmtPlanPmtTypeArr = new ArrayList<OlcPaymentInfoRec>();
	private List<OlcPmtPlanHstTypeRec> pmtPlanHistTypeArr = new ArrayList<OlcPmtPlanHstTypeRec>();
	private List<OlcPmtPlanInstTypeRec> pmtPlanInstTypeArr = new ArrayList<OlcPmtPlanInstTypeRec>();

	public OlcPmtPlanTypeRec() {
		super();
	}

	public BigDecimal getPaymentPlanId() {
		return paymentPlanId;
	}

	public void setPaymentPlanId(BigDecimal paymentPlanId) {
		this.paymentPlanId = paymentPlanId;
	}

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public String getPlanStatusCode() {
		return planStatusCode;
	}

	public void setPlanStatusCode(String planStatusCode) {
		this.planStatusCode = planStatusCode;
	}

	public String getInstallmentFreqTypeCode() {
		return installmentFreqTypeCode;
	}

	public void setInstallmentFreqTypeCode(String installmentFreqTypeCode) {
		this.installmentFreqTypeCode = installmentFreqTypeCode;
	}

	public String getPaymentPlanNumber() {
		return paymentPlanNumber;
	}

	public void setPaymentPlanNumber(String paymentPlanNumber) {
		this.paymentPlanNumber = paymentPlanNumber;
	}

	public BigDecimal getPlanDiscountAmount() {
		return planDiscountAmount;
	}

	public void setPlanDiscountAmount(BigDecimal planDiscountAmount) {
		this.planDiscountAmount = planDiscountAmount;
	}

	public BigDecimal getPlanSettlementAmount() {
		return planSettlementAmount;
	}

	public void setPlanSettlementAmount(BigDecimal planSettlementAmount) {
		this.planSettlementAmount = planSettlementAmount;
	}

	public BigDecimal getPlanTotalAmount() {
		return planTotalAmount;
	}

	public void setPlanTotalAmount(BigDecimal planTotalAmount) {
		this.planTotalAmount = planTotalAmount;
	}

	public BigDecimal getUncollectableAmount() {
		return uncollectableAmount;
	}

	public void setUncollectableAmount(BigDecimal uncollectableAmount) {
		this.uncollectableAmount = uncollectableAmount;
	}

	public BigDecimal getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(BigDecimal numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	public Date getFirstPaymentDate() {
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(Date firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}

	public BigDecimal getRebillFailedCount() {
		return rebillFailedCount;
	}

	public void setRebillFailedCount(BigDecimal rebillFailedCount) {
		this.rebillFailedCount = rebillFailedCount;
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

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public BigDecimal getAgcyId() {
		return agcyId;
	}

	public void setAgcyId(BigDecimal agcyId) {
		this.agcyId = agcyId;
	}

	public BigDecimal getAccountBillingMethodId() {
		return accountBillingMethodId;
	}

	public void setAccountBillingMethodId(BigDecimal accountBillingMethodId) {
		this.accountBillingMethodId = accountBillingMethodId;
	}

	public String getBillingFullName() {
		return billingFullName;
	}

	public void setBillingFullName(String billingFullName) {
		this.billingFullName = billingFullName;
	}

	public String getBillingPaymentForm() {
		return billingPaymentForm;
	}

	public void setBillingPaymentForm(String billingPaymentForm) {
		this.billingPaymentForm = billingPaymentForm;
	}

	public BigDecimal getBillingEftActTypeId() {
		return billingEftActTypeId;
	}

	public void setBillingEftActTypeId(BigDecimal billingEftActTypeId) {
		this.billingEftActTypeId = billingEftActTypeId;
	}

	public String getBillingCardType() {
		return billingCardType;
	}

	public void setBillingCardType(String billingCardType) {
		this.billingCardType = billingCardType;
	}

	public List<OlcPaymentInfoRec> getPmtPlanPmtTypeArr() {
		return pmtPlanPmtTypeArr;
	}

	public void setPmtPlanPmtTypeArr(List<OlcPaymentInfoRec> pmtPlanPmtTypeArr) {
		this.pmtPlanPmtTypeArr = pmtPlanPmtTypeArr;
	}

	public List<OlcPmtPlanHstTypeRec> getPmtPlanHistTypeArr() {
		return pmtPlanHistTypeArr;
	}

	public void setPmtPlanHistTypeArr(List<OlcPmtPlanHstTypeRec> pmtPlanHistTypeArr) {
		this.pmtPlanHistTypeArr = pmtPlanHistTypeArr;
	}

	public List<OlcPmtPlanInstTypeRec> getPmtPlanInstTypeArr() {
		return pmtPlanInstTypeArr;
	}

	public void setPmtPlanInstTypeArr(List<OlcPmtPlanInstTypeRec> pmtPlanInstTypeArr) {
		this.pmtPlanInstTypeArr = pmtPlanInstTypeArr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcPmtPlanTypeRec [paymentPlanId=" + paymentPlanId + ", accountId=" + accountId + ", planStatusCode="
				+ planStatusCode + ", installmentFreqTypeCode=" + installmentFreqTypeCode + ", paymentPlanNumber="
				+ paymentPlanNumber + ", planDiscountAmount=" + planDiscountAmount + ", planSettlementAmount="
				+ planSettlementAmount + ", planTotalAmount=" + planTotalAmount + ", uncollectableAmount="
				+ uncollectableAmount + ", numberOfInstallments=" + numberOfInstallments + ", firstPaymentDate="
				+ firstPaymentDate + ", rebillFailedCount=" + rebillFailedCount + ", openAmount=" + openAmount
				+ ", paidAmount=" + paidAmount + ", statusDate=" + statusDate + ", agcyId=" + agcyId
				+ ", accountBillingMethodId=" + accountBillingMethodId + ", billingFullName=" + billingFullName
				+ ", billingPaymentForm=" + billingPaymentForm + ", billingEftActTypeId=" + billingEftActTypeId
				+ ", billingCardType=" + billingCardType + ", pmtPlanPmtTypeArr=" + pmtPlanPmtTypeArr
				+ ", pmtPlanHistTypeArr=" + pmtPlanHistTypeArr + ", pmtPlanInstTypeArr=" + pmtPlanInstTypeArr + "]";
	}

}
