package com.etcc.csc.payment.checkoutxml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentConfirmation implements Serializable {
	private static final long serialVersionUID = 4651591135663812400L;
	@XmlAttribute(name = "payment_id", required = true)
	private long paymentid;
	@XmlAttribute(name = "sequence", required = true)
	private int sequence;

	public long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
