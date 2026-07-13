package com.etcc.csc.dto;

import java.util.List;

public class FulfillmentDTO {
	private Long fulfillmentId;
	private TagDTO[] tagArray;
	private List<InventoryItemDTO> inventoryItems;

	public Long getFulfillmentId() {
		return fulfillmentId;
	}
	public void setFulfillmentId(Long fulfillmentId) {
		this.fulfillmentId = fulfillmentId;
	}
	public TagDTO[] getTagArray() {
		return tagArray;
	}
	public void setTagArray(TagDTO[] tagArray) {
		this.tagArray = tagArray;
	}
	public List<InventoryItemDTO> getInventoryItems() {
		return inventoryItems;
	}
	public void setInventoryItems(List<InventoryItemDTO> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}
}
