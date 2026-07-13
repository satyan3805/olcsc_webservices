package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OlcVpsUninvoicedViolsRec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1165665153768271979L;
	private BigDecimal agencyId;
	private String agencyName;
	private BigDecimal violationId;
	private BigDecimal violatorId;
	private String fullLocationName;
	private Date violationDateTime;
	private String status;
	private String licPlate;
	private String licState;
	private BigDecimal origTollAmt;
	private BigDecimal openTollAmt;
	private BigDecimal paidTollAmt;

	public OlcVpsUninvoicedViolsRec() {
		super();
	}

	public BigDecimal getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(BigDecimal agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public BigDecimal getViolationId() {
		return violationId;
	}

	public void setViolationId(BigDecimal violationId) {
		this.violationId = violationId;
	}

	public BigDecimal getViolatorId() {
		return violatorId;
	}

	public void setViolatorId(BigDecimal violatorId) {
		this.violatorId = violatorId;
	}

	public String getFullLocationName() {
		return fullLocationName;
	}

	public void setFullLocationName(String fullLocationName) {
		this.fullLocationName = fullLocationName;
	}

	public Date getViolationDateTime() {
		return violationDateTime;
	}

	public void setViolationDateTime(Date violationDateTime) {
		this.violationDateTime = violationDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public BigDecimal getOrigTollAmt() {
		return origTollAmt;
	}

	public void setOrigTollAmt(BigDecimal origTollAmt) {
		this.origTollAmt = origTollAmt;
	}

	public BigDecimal getOpenTollAmt() {
		return openTollAmt;
	}

	public void setOpenTollAmt(BigDecimal openTollAmt) {
		this.openTollAmt = openTollAmt;
	}

	public BigDecimal getPaidTollAmt() {
		return paidTollAmt;
	}

	public void setPaidTollAmt(BigDecimal paidTollAmt) {
		this.paidTollAmt = paidTollAmt;
	}

	@Override
	public String toString() {
		return "OlcVpsUninvoicedViolsRecBean [agencyId=" + agencyId + ", agencyName=" + agencyName + ", violationId="
				+ violationId + ", violatorId=" + violatorId + ", fullLocationName=" + fullLocationName
				+ ", violationDateTime=" + violationDateTime + ", status=" + status + ", licPlate=" + licPlate
				+ ", licState=" + licState + ", origTollAmt=" + origTollAmt + ", openTollAmt=" + openTollAmt
				+ ", paidTollAmt=" + paidTollAmt + "]";
	}

}
