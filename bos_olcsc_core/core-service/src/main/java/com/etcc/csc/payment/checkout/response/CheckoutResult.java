package com.etcc.csc.payment.checkout.response;

import java.io.Serializable;
import java.util.List;

import com.etcc.csc.payment.cart.CartItem;
import com.etcc.csc.payment.checkoutxml.PaymentDTO;

public class CheckoutResult implements Serializable {
	private static final long serialVersionUID = -3227465301890912435L;

	private boolean isSuccessful = false;
	private Long cartId;
	private Long shiftId;
	private Long accountId;
	private PaymentDTO payments;
	private List<CartItem> cartItems;
	private CartResponse cartResponse;

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public PaymentDTO getPayments() {
		return payments;
	}

	public void setPayments(PaymentDTO payments) {
		this.payments = payments;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public CartResponse getCartResponse() {
		return cartResponse;
	}

	public void setCartResponse(CartResponse cartResponse) {
		this.cartResponse = cartResponse;
	}
}
