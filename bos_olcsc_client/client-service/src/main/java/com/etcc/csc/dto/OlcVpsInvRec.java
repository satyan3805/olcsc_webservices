package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OlcVpsInvRec implements Serializable {

	private static final long serialVersionUID = -2706651154884320903L;

	private BigDecimal transactionId;
	private String invoiceNumber;
	private BigDecimal amount;
	private Date transactionDate;
	private String status;
	private String vLocation;
	private BigDecimal origTollAmount;

	public OlcVpsInvRec() {
		super();
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getvLocation() {
		return vLocation;
	}

	public void setvLocation(String vLocation) {
		this.vLocation = vLocation;
	}

	public BigDecimal getOrigTollAmount() {
		return origTollAmount;
	}

	public void setOrigTollAmount(BigDecimal origTollAmount) {
		this.origTollAmount = origTollAmount;
	}

	@Override
	public String toString() {
		return "OlcVpsInvRecDBean [transactionId=" + transactionId + ", invoiceNumber=" + invoiceNumber + ", amount="
				+ amount + ", transactionDate=" + transactionDate + ", status=" + status + ", vLocation=" + vLocation
				+ ", origTollAmount=" + origTollAmount + "]";
	}

}
