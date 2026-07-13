package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.etcc.csc.common.BaseDTO;

public class InventoryItemDTO extends BaseDTO{

	private static final long serialVersionUID = -8414242764687981998L;
	
	private Long fulfillmentDetailId;
	private Long inventoryTypeId;
	private String itemDscp;
	private int itemQuantity;
	private int previousItemQty;
	private double itemPrice = 0.0;
	private String priceTypeId="";
	private Long[] accountInventory;
	private List <InventoryItemAttrbsDTO>inventoryItemAttrbs;
	private boolean isSelected;
	private String attributes;
	private String updateFlag;
	
	public Long getFulfillmentDetailId() {
		return fulfillmentDetailId;
	}
	public void setFulfillmentDetailId(Long fulfillmentDetailId) {
		this.fulfillmentDetailId = fulfillmentDetailId;
	}
	public Long getInventoryTypeId() {
		return inventoryTypeId;
	}
	public void setInventoryTypeId(Long inventoryTypeId) {
		this.inventoryTypeId = inventoryTypeId;
	}
	public String getItemDscp() {
		return itemDscp;
	}
	public void setItemDscp(String itemDscp) {
		this.itemDscp = itemDscp;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public int getPreviousItemQty() {
		return previousItemQty;
	}
	public void setPreviousItemQty(int previousItemQty) {
		this.previousItemQty = previousItemQty;
	}
	public List<InventoryItemAttrbsDTO> getInventoryItemAttrbs() {
		return inventoryItemAttrbs;
	}
	public void setInventoryItemAttrbs(List<InventoryItemAttrbsDTO> inventoryItemAttrbs) {
		this.inventoryItemAttrbs = inventoryItemAttrbs;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public String getPriceTypeId() {
		return priceTypeId;
	}
	
	public void setPriceTypeId(String priceTypeId) {
		this.priceTypeId = priceTypeId;
	}
	public Long[] getAccountInventory() {
		return accountInventory;
	}
	public void setAccountInventory(Long[] accountInventory) {
		this.accountInventory = accountInventory;
	}
	public String getAttributes() {
		//StringBuffer attributes = new StringBuffer("");
		if (attributes != null && !attributes.equals("")) {
			return attributes;
		}
   		StringBuffer temp = new StringBuffer("");
    	if(this.inventoryItemAttrbs != null){
 			for(Iterator it = inventoryItemAttrbs.iterator();it.hasNext();){
				InventoryItemAttrbsDTO inventoryItemAttrbs= (InventoryItemAttrbsDTO)it.next();
				String attrbName = inventoryItemAttrbs.getAttrbName().toLowerCase();
				char[] stringArray = attrbName.toCharArray(); 
				stringArray[0] = Character.toUpperCase(stringArray[0]); 
				attrbName = new String(stringArray); 
				temp.append(attrbName + " - " + inventoryItemAttrbs.getAttrbValue());
				if(it.hasNext())
					temp.append(", ");
			}
		}
    	return temp.toString();
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	// this is the method that will be called to save
    //  the indexed properties when the form is saved
    public InventoryItemAttrbsDTO getItemAttrb(int index)
    {
        // make sure that orderList is not null
        if(this.inventoryItemAttrbs == null)
        {
            this.inventoryItemAttrbs = new ArrayList<InventoryItemAttrbsDTO>();
        }
 
        // indexes do not come in order, populate empty spots
        while(index >= this.inventoryItemAttrbs.size())
        {
        	this.inventoryItemAttrbs.add(new InventoryItemAttrbsDTO());
        }
 
        // return the requested item
        return (InventoryItemAttrbsDTO) inventoryItemAttrbs.get(index);
    }
    public double getItemTotal() {
		return itemPrice * itemQuantity;
	}
    
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public boolean equals(Object obj)
    {
      //if the two objects are equal in reference, they are equal
      if(this == obj)
        return true;
      if (obj instanceof InventoryItemDTO)
      {
    	InventoryItemDTO dto = (InventoryItemDTO) obj;
    	if (dto.getFulfillmentDetailId() != null && this.getFulfillmentDetailId() != null) {
    		return dto.getFulfillmentDetailId().equals( this.getFulfillmentDetailId() );
    	} else {
    		return false;
    	}
      }
      else
      {
        return false;
      }
    }

}
