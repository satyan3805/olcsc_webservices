package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentDetail implements Serializable {
	private static final long serialVersionUID = -1913021651054657122L;
	private Invoice[] invoices;
    private Violation[] violations;
    private TagDTO[] tags;
    private BigDecimal tagAmount;
    private BigDecimal transactionId;
    private String forcePayment;

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

    public String toString() {
        StringBuilder sb = new StringBuilder ();
        sb.append("[");
        sb.append("invoices=").append(invoices.toString());
        sb.append(", violations=").append(violations.toString());
        sb.append(", tags=").append(tags.toString());
        sb.append(", tagAmount=").append(tagAmount);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", forcePayment=").append(forcePayment);
        sb.append("]");
        return sb.toString();
    }
}
