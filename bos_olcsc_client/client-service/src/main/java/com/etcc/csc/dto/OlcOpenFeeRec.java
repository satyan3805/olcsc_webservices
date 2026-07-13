package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OlcOpenFeeRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8693469641712672218L;

	private String feeCode;
	private BigDecimal feeDueOpenAmt;
	private BigDecimal feeDiscAmt;
	private BigDecimal feeDueAfterDiscAmt;
	private BigDecimal retailDetailId;
	private BigDecimal feeGrpId;

	public OlcOpenFeeRec() {
		super();
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public BigDecimal getFeeDueOpenAmt() {
		return feeDueOpenAmt;
	}

	public void setFeeDueOpenAmt(BigDecimal feeDueOpenAmt) {
		this.feeDueOpenAmt = feeDueOpenAmt;
	}

	public BigDecimal getFeeDiscAmt() {
		return feeDiscAmt;
	}

	public void setFeeDiscAmt(BigDecimal feeDiscAmt) {
		this.feeDiscAmt = feeDiscAmt;
	}

	public BigDecimal getFeeDueAfterDiscAmt() {
		return feeDueAfterDiscAmt;
	}

	public void setFeeDueAfterDiscAmt(BigDecimal feeDueAfterDiscAmt) {
		this.feeDueAfterDiscAmt = feeDueAfterDiscAmt;
	}

	public BigDecimal getRetailDetailId() {
		return retailDetailId;
	}

	public void setRetailDetailId(BigDecimal retailDetailId) {
		this.retailDetailId = retailDetailId;
	}

	public BigDecimal getFeeGrpId() {
		return feeGrpId;
	}

	public void setFeeGrpId(BigDecimal feeGrpId) {
		this.feeGrpId = feeGrpId;
	}

	@Override
	public String toString() {
		return "OlcOpenFeeRec [feeCode=" + feeCode + ", feeDueOpenAmt=" + feeDueOpenAmt + ", feeDiscAmt=" + feeDiscAmt
				+ ", feeDueAfterDiscAmt=" + feeDueAfterDiscAmt + ", retailDetailId=" + retailDetailId + ", feeGrpId="
				+ feeGrpId + "]";
	}

}
