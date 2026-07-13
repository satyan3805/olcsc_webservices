package com.etcc.csc.payment.cart;

import java.util.UUID;

import com.etcc.csc.payment.checkoutxml.CartXML;

public class CartItem {
	private String itemDescription;
	private String itemId;
	private Long accountId;
	private boolean isAmountNegativeInCartSummary = false;
	private CartXML cartItemDetailsForPayment;
	private String paymentRequired = "Y";
	private UUID idOne;
	private boolean deleteFlag = true;
	private int sequenceNumber;
	private boolean isDisplay = true;

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public boolean isAmountNegativeInCartSummary() {
		return isAmountNegativeInCartSummary;
	}

	public void setAmountNegativeInCartSummary(
			boolean isAmountNegativeInCartSummary) {
		this.isAmountNegativeInCartSummary = isAmountNegativeInCartSummary;
	}

	public CartXML getCartItemDetailsForPayment() {
		return cartItemDetailsForPayment;
	}

	public void setCartItemDetailsForPayment(CartXML cartItemDetailsForPayment) {
		this.cartItemDetailsForPayment = cartItemDetailsForPayment;
	}

	public String getPaymentRequired() {
		return paymentRequired;
	}

	public void setPaymentRequired(String paymentRequired) {
		this.paymentRequired = paymentRequired;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public CartItem() {
		this.idOne = UUID.randomUUID();
		// setId();
	}

	public String getId() {
		return (this.getCartItemDetailsForPayment().getitemTypeDescription()
				+ this.getItemId() + this.idOne);

	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
}
