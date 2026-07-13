package com.etcc.csc.payment.cart;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.enums.CartItemTypeEnum;
import com.etcc.csc.payment.checkoutxml.CartItemXmlDetails;
import com.etcc.istha.util.Utilities;

public class ShoppingCart {
	private AccountLoginDTO acctLoginDto;

	private double amtDue = 0.0;
	private double miniAmtDue = 0.0;
	private double balAfterCheckout = 0.0;

	private Map<String, CartItem> cartItems = new LinkedHashMap<String, CartItem>();

	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}

	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}

	public double getAmtDue() {
		return amtDue;
	}

	public void setAmtDue(double amtDue) {
		this.amtDue = amtDue;
	}

	public double getMiniAmtDue() {
		return miniAmtDue;
	}

	public void setMiniAmtDue(double miniAmtDue) {
		this.miniAmtDue = miniAmtDue;
	}

	public double getBalAfterCheckout() {
		return balAfterCheckout;
	}

	public void setBalAfterCheckout(double balAfterCheckout) {
		this.balAfterCheckout = balAfterCheckout;
	}

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public ShoppingCart(AccountLoginDTO acctLoginDto) {
		super();
		this.acctLoginDto = acctLoginDto;
	}

	public void addToCart(CartItem item) throws Exception {
		addToCart(item, true);
	}

	/**
	 * AmountDue is required only for CSR checkout. Some back end processes
	 * don't need it.
	 */

	private void addToCart(CartItem item, boolean calculateAmountDueRequired)
			throws Exception {
		// item.checkDuplicate(this);
		// item.process(this);
		cartItems.put(item.getId(), item);
		// item.reCalculate(this);
		if (calculateAmountDueRequired
				&& item.getCartItemDetailsForPayment().getAmount() != null) {
			setMiniAmtDue(calculateMiniAmtDue());
			setAmtDue(calculateAmtDue());
		}
	}

	public double calculateMiniAmtDue() throws Exception {
		double amtDue = 0.0;
		Map items = getCartItems();
		Collection col = items.values();
		for (Iterator i = (Iterator) col.iterator(); i.hasNext();) {
			CartItem item = (CartItem) i.next();
			if ("Y".equals(item.getPaymentRequired()))
				amtDue += item.getCartItemDetailsForPayment().getAmount();
		}
		return Utilities.round(amtDue, 2);
	}

	private double calculateAmtDue() throws Exception {
		double amtDue = 0.0;
		double movePaymentInAmount = 0.00;
		Map items = getCartItems();
		Collection col = items.values();
		for (Iterator i = (Iterator) col.iterator(); i.hasNext();) {
			CartItem item = (CartItem) i.next();
			if ("Y".equals(item.getPaymentRequired())) {
				amtDue += item.getCartItemDetailsForPayment().getAmount();
			}
			if (item.getCartItemDetailsForPayment() instanceof CartItemXmlDetails) {
				CartItemXmlDetails itemDetails = (CartItemXmlDetails) item
						.getCartItemDetailsForPayment();
				if (CartItemTypeEnum.MOVE_PAYMENT_IN.getValue().equals(
						itemDetails.getItemType())) {
					movePaymentInAmount += itemDetails.getAmount();
				}
			}
		}
		amtDue -= movePaymentInAmount;
		return Utilities.round(amtDue, 2);
		/*
		 * CartItem item = (CartItem)i.next();
		 * if(CSCCartItem.class.isInstance(item)){
		 * sum+=sumCSCAmountDueEffected(item.getTxns()); } if (item instanceof
		 * VPSCartItem){ sum+=((VPSCartItem)item).getAmount(); }
		 */

	}
}
