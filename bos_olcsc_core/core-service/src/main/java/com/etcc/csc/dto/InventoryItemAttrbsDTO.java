package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.common.BaseDTO;

public class InventoryItemAttrbsDTO extends BaseDTO {
	private String attrbId;	
	private String attrbName;
	private String attrbType;
	private String attrbValue;
	private String attrbPromptText;
	private double attrbPrice;
	private String isDisplyedInDropDown;
	private Collection attrbValueAsList = new ArrayList();
	private int maxLength;
	private String isRequired;
	private Long inventoryTypeAttrValueId;
	
	public String getAttrbId() {
		return attrbId;
	}
	public void setAttrbId(String attrbId) {
		this.attrbId = attrbId;
	}
	public String getAttrbName() {
		return attrbName;
	}
	public void setAttrbName(String attrbName) {
		this.attrbName = attrbName;
	}
	public String getAttrbType() {
		return attrbType;
	}
	public void setAttrbType(String attrbType) {
		this.attrbType = attrbType;
	}
	public String getAttrbValue() {
		return attrbValue;
	}
	public void setAttrbValue(String attrbValue) {
		this.attrbValue = attrbValue;
	}
	public String getAttrbPromptText() {
		return attrbPromptText;
	}
	public void setAttrbPromptText(String attrbPromptText) {
		this.attrbPromptText = attrbPromptText;
	}
	
	public double getAttrbPrice() {
		return attrbPrice;
	}
	public void setAttrbPrice(double attrbPrice) {
		this.attrbPrice = attrbPrice;
	}
	
	public String getIsDisplyedInDropDown() {
		return isDisplyedInDropDown;
	}
	public void setIsDisplyedInDropDown(String isDisplyedInDropDown) {
		this.isDisplyedInDropDown = isDisplyedInDropDown;		
	}

	public void setAttrbValueAsList(String attrbValueStr){
		System.out.println("attrbValueStr = "+ attrbValueStr);
		String[] results = attrbValueStr.split(";");		
		for(int i=0; i<results.length; i++){
			System.out.println("results[i] = "+ results[i]);
			attrbValueAsList.add(new LabelValueBean(results[i], Integer.toString(i)));
		}
		//attrbValueAsList = Arrays.asList(results); 
	}
	
	public void addToAttrbValueAsList(String id, String label){
		attrbValueAsList.add(new LabelValueBean(label, id));
	}
	
	public Collection getAttrbValueAsList() {
		return attrbValueAsList;
	}
	
	public void setAttrbValueAsList(Collection attrbValueAsList) {
		this.attrbValueAsList = attrbValueAsList;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public String getIsRequired() {
		return isRequired;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public Long getInventoryTypeAttrValueId() {
		return inventoryTypeAttrValueId;
	}
	public void setInventoryTypeAttrValueId(Long inventoryTypeAttrValueId) {
		this.inventoryTypeAttrValueId = inventoryTypeAttrValueId;
	}
	
	
	
	
}
