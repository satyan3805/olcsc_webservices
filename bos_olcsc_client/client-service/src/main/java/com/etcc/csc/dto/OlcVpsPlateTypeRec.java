package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OlcVpsPlateTypeRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4717088061626994480L;

	private String discountRule;
	private BigDecimal ttlAmountDue;
	private List<OlcVpsInvTypeRec> invTypeArr = new ArrayList<OlcVpsInvTypeRec>();
	private String licPlate;
	private String licState;
	private BigDecimal origInvFeeAmount;
	private BigDecimal openInvFeeAmount;
	private BigDecimal origInvAmount;
	private BigDecimal openInvAmount;
	private BigDecimal origPlateAmount;
	private BigDecimal origUninvAmount;
	private BigDecimal openUninvAmount;
	private BigDecimal totalPaidAmount;
	private BigDecimal totalExcAmount;
	private BigDecimal plateDueAfterExc;
	private BigDecimal plateDueBeforeExc;
	private List<OlcVpsUninvoicedViolsRec> uninvTypeArr= new ArrayList<OlcVpsUninvoicedViolsRec>();
	private BigDecimal serviceFee;
	private BigDecimal accountVehicleId;
	private List<OlcPmtPlanTypeRec> pmtPlanTypeArr = new ArrayList<OlcPmtPlanTypeRec>();
	private BigDecimal invTollOpenAmount;
	private BigDecimal univTollOpenAmount;
	private BigDecimal ttlTollOpenAmount;
	private OlcVpsAccountConvRec vpsAccountConvRec;
	private List<OlcVpsPlateAgcyRec> vpsPlateAgcyRec = new ArrayList<OlcVpsPlateAgcyRec>();

	public OlcVpsPlateTypeRec() {
		super();
	}

	public String getDiscountRule() {
		return discountRule;
	}

	public void setDiscountRule(String discountRule) {
		this.discountRule = discountRule;
	}

	public BigDecimal getTtlAmountDue() {
		return ttlAmountDue;
	}

	public void setTtlAmountDue(BigDecimal ttlAmountDue) {
		this.ttlAmountDue = ttlAmountDue;
	}

	public List<OlcVpsInvTypeRec> getInvTypeArr() {
		return invTypeArr;
	}

	public void setInvTypeArr(List<OlcVpsInvTypeRec> invTypeArr) {
		this.invTypeArr = invTypeArr;
	}

	public String getLicPlate() {
		return licPlate;
	}

	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}

	public String getLicState() {
		return licState;
	}

	public void setLicState(String licState) {
		this.licState = licState;
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

	public BigDecimal getOrigPlateAmount() {
		return origPlateAmount;
	}

	public void setOrigPlateAmount(BigDecimal origPlateAmount) {
		this.origPlateAmount = origPlateAmount;
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

	public BigDecimal getPlateDueAfterExc() {
		return plateDueAfterExc;
	}

	public void setPlateDueAfterExc(BigDecimal plateDueAfterExc) {
		this.plateDueAfterExc = plateDueAfterExc;
	}

	public BigDecimal getPlateDueBeforeExc() {
		return plateDueBeforeExc;
	}

	public void setPlateDueBeforeExc(BigDecimal plateDueBeforeExc) {
		this.plateDueBeforeExc = plateDueBeforeExc;
	}

	public List<OlcVpsUninvoicedViolsRec> getUninvTypeArr() {
		return uninvTypeArr;
	}

	public void setUninvTypeArr(List<OlcVpsUninvoicedViolsRec> uninvTypeArr) {
		this.uninvTypeArr = uninvTypeArr;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getAccountVehicleId() {
		return accountVehicleId;
	}

	public void setAccountVehicleId(BigDecimal accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}

	public List<OlcPmtPlanTypeRec> getPmtPlanTypeArr() {
		return pmtPlanTypeArr;
	}

	public void setPmtPlanTypeArr(List<OlcPmtPlanTypeRec> pmtPlanTypeArr) {
		this.pmtPlanTypeArr = pmtPlanTypeArr;
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

	public OlcVpsAccountConvRec getVpsAccountConvRec() {
		return vpsAccountConvRec;
	}

	public void setVpsAccountConvRec(OlcVpsAccountConvRec vpsAccountConvRec) {
		this.vpsAccountConvRec = vpsAccountConvRec;
	}

	public List<OlcVpsPlateAgcyRec> getVpsPlateAgcyRec() {
		return vpsPlateAgcyRec;
	}

	public void setVpsPlateAgcyRec(List<OlcVpsPlateAgcyRec> vpsPlateAgcyRec) {
		this.vpsPlateAgcyRec = vpsPlateAgcyRec;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcVpsPlateTypeRec [discountRule=" + discountRule + ", ttlAmountDue=" + ttlAmountDue + ", invTypeArr="
				+ invTypeArr + ", licPlate=" + licPlate + ", licState=" + licState + ", origInvFeeAmount="
				+ origInvFeeAmount + ", openInvFeeAmount=" + openInvFeeAmount + ", origInvAmount=" + origInvAmount
				+ ", openInvAmount=" + openInvAmount + ", origPlateAmount=" + origPlateAmount + ", origUninvAmount="
				+ origUninvAmount + ", openUninvAmount=" + openUninvAmount + ", totalPaidAmount=" + totalPaidAmount
				+ ", totalExcAmount=" + totalExcAmount + ", plateDueAfterExc=" + plateDueAfterExc
				+ ", plateDueBeforeExc=" + plateDueBeforeExc + ", uninvTypeArr=" + uninvTypeArr + ", serviceFee="
				+ serviceFee + ", accountVehicleId=" + accountVehicleId + ", pmtPlanTypeArr=" + pmtPlanTypeArr
				+ ", invTollOpenAmount=" + invTollOpenAmount + ", univTollOpenAmount=" + univTollOpenAmount
				+ ", ttlTollOpenAmount=" + ttlTollOpenAmount + ", vpsAccountConvRec=" + vpsAccountConvRec
				+ ", vpsPlateAgcyRec=" + vpsPlateAgcyRec + "]";
	}
	
	


	

}
