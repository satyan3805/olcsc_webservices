package com.etcc.csc.payment.checkoutxml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cart")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentCart implements Serializable {
	private static final long serialVersionUID = -8943378451372953570L;
	@XmlAttribute(name = "account_id", required = true)
	private long accountId;
	@XmlAttribute(name = "shift_id", required = true)
	private long shiftId;
	@XmlAttribute(name = "cart_id", required = true)
	private long cartId;
	@XmlAttribute(name = "supervisor_id", required = false)
	private Long supervisorId;

	@XmlElementWrapper(name = "payments")
	@XmlElement(name = "payment")
	private List<PaymentItem> payment;

	@XmlElementWrapper(name = "payment_confirmations")
	@XmlElement(name = "payment_confirmation")
	private List<PaymentConfirmation> paymentConfirmation;

	@XmlElementWrapper(name = "items")
	@XmlElement(name = "item")
	private List<CartItemXmlDetails> cartItem;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getShiftId() {
		return shiftId;
	}

	public void setShiftId(long shiftId) {
		this.shiftId = shiftId;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public List<PaymentItem> getPayment() {
		return payment;
	}

	public void setPayment(List<PaymentItem> payment) {
		this.payment = payment;
	}

	public List<PaymentConfirmation> getPaymentConfirmation() {
		return paymentConfirmation;
	}

	public void setPaymentConfirmation(
			List<PaymentConfirmation> paymentConfirmation) {
		this.paymentConfirmation = paymentConfirmation;
	}

	public List<CartItemXmlDetails> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<CartItemXmlDetails> cartItem) {
		this.cartItem = cartItem;
	}
}
