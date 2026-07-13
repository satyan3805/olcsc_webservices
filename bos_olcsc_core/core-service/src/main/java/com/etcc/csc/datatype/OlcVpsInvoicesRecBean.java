package com.etcc.csc.datatype;

import java.math.BigDecimal;
import java.util.Calendar;

public class OlcVpsInvoicesRecBean {
	private BigDecimal invoiceId;
	private java.util.Calendar invoiceDate;
	private java.util.Calendar currDueDate;
	private String firstName;
	private String lastName;
	private String licPlateNbr;
	private String licState;
	private BigDecimal invoiceAmount;
	private BigDecimal veaAmount;
	private BigDecimal onlineFee;
	private BigDecimal violatorId;
	private OlcViolationRecBean[] violations;

	public OlcVpsInvoicesRecBean() {
	}

	public void setInvoiceId(BigDecimal invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceDate(Calendar invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Calendar getInvoiceDate() {
		return invoiceDate;
	}

	public void setCurrDueDate(Calendar currDueDate) {
		this.currDueDate = currDueDate;
	}

	public Calendar getCurrDueDate() {
		return currDueDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLicPlateNbr(String licPlateNbr) {
		this.licPlateNbr = licPlateNbr;
	}

	public String getLicPlateNbr() {
		return licPlateNbr;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public String getLicState() {
		return licState;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setVeaAmount(BigDecimal veaAmount) {
		this.veaAmount = veaAmount;
	}

	public BigDecimal getVeaAmount() {
		return veaAmount;
	}

	public void setOnlineFee(BigDecimal onlineFee) {
		this.onlineFee = onlineFee;
	}

	public BigDecimal getOnlineFee() {
		return onlineFee;
	}

	public void setViolatorId(BigDecimal violatorId) {
		this.violatorId = violatorId;
	}

	public BigDecimal getViolatorId() {
		return violatorId;
	}

	public void setViolations(OlcViolationRecBean[] violations) {
		this.violations = violations;
	}

	public OlcViolationRecBean[] getViolations() {
		return violations;
	}
}
