package com.etcc.csc.dto;

public class FulfillmentDetailToAccountItemDTO {
	private String fulfillmentId;
	private String fulfillmentDetailId;
	private String accountTagId;
	private String accountInventoryId;
	private Long cartId;
	private Double appliedAmount;
	
	public FulfillmentDetailToAccountItemDTO() {
		super();
	}
	public String getFulfillmentId() {
		return fulfillmentId;
	}
	public void setFulfillmentId(String fulfillmentId) {
		this.fulfillmentId = fulfillmentId;
	}
	public String getFulfillmentDetailId() {
		return fulfillmentDetailId;
	}
	public void setFulfillmentDetailId(String fulfillmentDetailId) {
		this.fulfillmentDetailId = fulfillmentDetailId;
	}
	public String getAccountTagId() {
		return accountTagId;
	}
	public void setAccountTagId(String accountTagId) {
		this.accountTagId = accountTagId;
	}
	public String getAccountInventoryId() {
		return accountInventoryId;
	}
	public void setAccountInventoryId(String accountInventoryId) {
		this.accountInventoryId = accountInventoryId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Double getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
}
