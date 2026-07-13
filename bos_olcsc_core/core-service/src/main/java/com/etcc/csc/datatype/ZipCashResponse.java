package com.etcc.csc.datatype;

import java.math.BigDecimal;

public class ZipCashResponse {
	private String postingStatus;
	private BigDecimal paymentId;

	public ZipCashResponse() {

	}

	public void setPostingStatus(String postingStatus) {
		this.postingStatus = postingStatus;
	}

	public String getPostingStatus() {
		return postingStatus;
	}

	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getPaymentId() {
		return paymentId;
	}
}// end of ZipCashResponse Class