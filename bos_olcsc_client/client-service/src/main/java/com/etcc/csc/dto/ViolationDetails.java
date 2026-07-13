package com.etcc.csc.dto;

import java.math.BigDecimal;

public class ViolationDetails {

	private String agencyName;
	private String plateNumber;
	private String jurisdiction;
	private String type;
	private String location;
	private String timeStamp;
	private BigDecimal tollCharge;
	private BigDecimal transactionId;
	private BigDecimal invoiceNumber;

	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName
	 *            the agencyName to set
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * @return the plateNumber
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * @param plateNumber
	 *            the plateNumber to set
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	/**
	 * @return the jurisdiction
	 */
	public String getJurisdiction() {
		return jurisdiction;
	}

	/**
	 * @param jurisdiction
	 *            the jurisdiction to set
	 */
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the tollCharge
	 */
	public BigDecimal getTollCharge() {
		return tollCharge;
	}

	/**
	 * @param tollCharge
	 *            the tollCharge to set
	 */
	public void setTollCharge(BigDecimal tollCharge) {
		this.tollCharge = tollCharge;
	}

	/**
	 * @return the transactionId
	 */
	public BigDecimal getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the invoiceNumber
	 */
	public BigDecimal getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(BigDecimal invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ViolationDetails [agencyName=" + agencyName + ", plateNumber=" + plateNumber + ", jurisdiction="
				+ jurisdiction + ", type=" + type + ", location=" + location + ", timeStamp=" + timeStamp
				+ ", tollCharge=" + tollCharge + ", transactionId=" + transactionId + ", invoiceNumber=" + invoiceNumber
				+ "]";
	}

}
