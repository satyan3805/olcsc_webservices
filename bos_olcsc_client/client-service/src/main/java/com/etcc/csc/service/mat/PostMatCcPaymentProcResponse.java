package com.etcc.csc.service.mat;

import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.PaymentType;

public class PostMatCcPaymentProcResponse {
	private List<PaymentType> paymentInfo = null;

	public List<PaymentType> getPaymentInfo() {
		if ( paymentInfo == null)
			paymentInfo = new ArrayList<PaymentType>();
		return paymentInfo;
	}

	public void setPaymentInfo(List<PaymentType> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
}
