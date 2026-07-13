package com.etcc.csc.enums;

public enum PaymentCheckoutResponseEnum {
	PAYMENT_SUCCESS("successful"),
	PAYMENT_FAILURE("failure"),
	PAYMENT_CONFIRMATION("confirm_payment"),
	PAYMENT_CANCEL("cancel_payment");
	
	private String status;
	
	private PaymentCheckoutResponseEnum(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
