package com.etcc.csc.datatype;

import java.util.List;

import com.etcc.csc.dto.InventoryItemDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VelcroDTO;

public class ItemsInShoppingCart {

	private String fulfillmentId;
	private TagDTO[] tagArray;
	private List<InventoryItemDTO> inventoryItems;
	private List<VelcroDTO> velcroList;

	public ItemsInShoppingCart() {
		super();
	}

	public String getFulfillmentId() {
		return fulfillmentId;
	}

	public void setFulfillmentId(String fulfillmentId) {
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

	public List<VelcroDTO> getVelcroList() {
		return velcroList;
	}

	public void setVelcroList(List<VelcroDTO> velcroList) {
		this.velcroList = velcroList;
	}
}
