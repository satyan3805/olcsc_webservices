package com.etcc.csc.dto;

import java.math.BigDecimal;
import java.util.List;

public class OlcInvPmtRec {

	private BigDecimal accountId;
	private BigDecimal invoiceId;
	private BigDecimal invoicePaidAmt;
	private BigDecimal invoiceDiscAmt;
	private List<OlcOpenFeeRec> invoiceFeeArr;
	private String discountRule;
	private List<OlcVpsInvRec> lineItems;

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(BigDecimal invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getInvoicePaidAmt() {
		return invoicePaidAmt;
	}

	public void setInvoicePaidAmt(BigDecimal invoicePaidAmt) {
		this.invoicePaidAmt = invoicePaidAmt;
	}

	public BigDecimal getInvoiceDiscAmt() {
		return invoiceDiscAmt;
	}

	public void setInvoiceDiscAmt(BigDecimal invoiceDiscAmt) {
		this.invoiceDiscAmt = invoiceDiscAmt;
	}

	public List<OlcOpenFeeRec> getInvoiceFeeArr() {
		return invoiceFeeArr;
	}

	public void setInvoiceFeeArr(List<OlcOpenFeeRec> invoiceFeeArr) {
		this.invoiceFeeArr = invoiceFeeArr;
	}

	public String getDiscountRule() {
		return discountRule;
	}

	public void setDiscountRule(String discountRule) {
		this.discountRule = discountRule;
	}

	public List<OlcVpsInvRec> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OlcVpsInvRec> lineItems) {
		this.lineItems = lineItems;
	}

	@Override
	public String toString() {
		return "OlcInvPmtRec [accountId=" + accountId + ", invoiceId=" + invoiceId + ", invoicePaidAmt="
				+ invoicePaidAmt + ", invoiceDiscAmt=" + invoiceDiscAmt + ", invoiceFeeArr=" + invoiceFeeArr
				+ ", discountRule=" + discountRule + ", lineItems=" + lineItems + "]";
	}

}
