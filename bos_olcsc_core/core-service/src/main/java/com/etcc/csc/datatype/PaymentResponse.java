package com.etcc.csc.datatype;

import java.math.BigDecimal;

public class PaymentResponse {
	private String postingStatus;
	private BigDecimal paymentId;

	public PaymentResponse() {

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
}// end of PaymentResponse Class