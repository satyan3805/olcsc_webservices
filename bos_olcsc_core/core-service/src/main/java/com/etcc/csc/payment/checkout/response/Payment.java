package com.etcc.csc.payment.checkout.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Payment implements Serializable {
	private static final long serialVersionUID = -5425932125183346219L;

	@XmlAttribute(name = "sequence")
	private Integer sequence;
	@XmlElement(name = "payment_id", required = true)
	private Long paymentId;
	@XmlElement(name = "status", required = true)
	private Status status;

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}