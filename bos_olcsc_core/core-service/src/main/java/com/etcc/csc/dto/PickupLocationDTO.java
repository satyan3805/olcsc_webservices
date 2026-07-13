package com.etcc.csc.dto;

public class PickupLocationDTO {
	Long pickupLocationId;
	String posName;
	String address1;
	String address2;
	String city;
	String state;
	
	public Long getPickupLocationId() {
		return pickupLocationId;
	}
	public void setPickupLocationId(Long pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
