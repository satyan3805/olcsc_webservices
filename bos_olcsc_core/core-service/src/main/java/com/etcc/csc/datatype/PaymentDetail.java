package com.etcc.csc.datatype;

import java.math.BigDecimal;

import com.etcc.csc.dto.TagDTO;

public class PaymentDetail {

	private Invoice[] invoices;
	private Violation[] violations;
	private TagDTO[] tags;
	private BigDecimal tagAmount;
	private BigDecimal depAmount;
	private BigDecimal transactionId;
	private String forcePayment;

	public PaymentDetail() {
	}

	public void setInvoices(Invoice[] invoices) {
		this.invoices = invoices;
	}

	public Invoice[] getInvoices() {
		return invoices;
	}

	public void setViolations(Violation[] violations) {
		this.violations = violations;
	}

	public Violation[] getViolations() {
		return violations;
	}

	public void setTags(TagDTO[] tags) {
		this.tags = tags;
	}

	public TagDTO[] getTags() {
		return tags;
	}

	public void setTagAmount(BigDecimal tagAmount) {
		this.tagAmount = tagAmount;
	}

	public BigDecimal getTagAmount() {
		return tagAmount;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setForcePayment(String forcePayment) {
		this.forcePayment = forcePayment;
	}

	public String getForcePayment() {
		return forcePayment;
	}

	public void setDepAmount(BigDecimal depAmount) {
		this.depAmount = depAmount;
	}

	public BigDecimal getDepAmount() {
		return depAmount;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nForce payment flag: ");
		sb.append(forcePayment);
		sb.append("\nTag amount:         ");
		sb.append(tagAmount);
		sb.append("\nDep amount:         ");
		sb.append(depAmount);

		return sb.toString();
	}
}
