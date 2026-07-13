package com.etcc.csc.payment.checkout.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.etcc.csc.payment.checkoutxml.PaymentConfirmation;

@XmlRootElement(name = "cart_response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CartResponse implements Serializable {
	private static final long serialVersionUID = -2768692344471065775L;

	@XmlAttribute(name = "cart_id")
	private Long cartId;

	@XmlElement(name = "status")
	private Status status;

	@XmlElementWrapper(name = "payments")
	@XmlElement(name = "payment")
	private List<Payment> payment;

	@XmlElementWrapper(name = "payment_confirmations")
	@XmlElement(name = "payment_confirmation")
	private List<PaymentConfirmation> paymentConfirmation;

	@XmlElementWrapper(name = "items")
	@XmlElement(name = "item")
	private List<Item> item;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public List<PaymentConfirmation> getPaymentConfirmation() {
		return paymentConfirmation;
	}

	public void setPaymentConfirmation(
			List<PaymentConfirmation> paymentConfirmation) {
		this.paymentConfirmation = paymentConfirmation;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}
}
