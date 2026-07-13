package com.etcc.csc.dto;

public class DMVVehicleOwnerDTO {
	

	
	private String vehiclemake;
	private String vehiclemodel;
	private String vehicleyear;
	private String dmvvehicleid;
	private String vin;
	private String ownershipstartdate;
	private String ownershipenddate;
	private String firstname;
	private String middlename;
	private String lastname;
	private String firstname2;
	private String middlename2;
	private String lastname2;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String plus4;
	
	public DMVVehicleOwnerDTO(){}
	
	public DMVVehicleOwnerDTO(String vehiclemake, String vehiclemodel, String vehicleyear,
			String vin, String ownershipstartdate, String ownershipenddate, String firstname,
			String middlename, String lastname, String firstname2, String middlename2,
			String lastname2, String address1, String address2, String city, String state,
			String zipcode, String plus4) {
		super();
		this.vehiclemake = vehiclemake;
		this.vehiclemodel = vehiclemodel;
		this.vehicleyear = vehicleyear;
		this.vin = vin;
		this.ownershipstartdate = ownershipstartdate;
		this.ownershipenddate = ownershipenddate;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.firstname2 = firstname2;
		this.middlename2 = middlename2;
		this.lastname2 = lastname2;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.plus4 = plus4;
	}


	public String getVehiclemodel() {
		return vehiclemodel;
	}

	public void setVehiclemodel(String vehiclemodel) {
		this.vehiclemodel = vehiclemodel;
	}

	public String getVehicleyear() {
		return vehicleyear;
	}

	public void setVehicleyear(String vehicleyear) {
		this.vehicleyear = vehicleyear;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getOwnershipstartdate() {
		return ownershipstartdate;
	}

	public void setOwnershipstartdate(String ownershipstartdate) {
		this.ownershipstartdate = ownershipstartdate;
	}

	public String getOwnershipenddate() {
		return ownershipenddate;
	}

	public void setOwnershipenddate(String ownershipenddate) {
		this.ownershipenddate = ownershipenddate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname2() {
		return firstname2;
	}

	public void setFirstname2(String firstname2) {
		this.firstname2 = firstname2;
	}

	public String getMiddlename2() {
		return middlename2;
	}

	public void setMiddlename2(String middlename2) {
		this.middlename2 = middlename2;
	}

	public String getLastname2() {
		return lastname2;
	}

	public void setLastname2(String lastname2) {
		this.lastname2 = lastname2;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPlus4() {
		return plus4;
	}

	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	

	public String getDmvvehicleid() {
		return dmvvehicleid;
	}

	public void setDmvvehicleid(String dmvvehicleid) {
		this.dmvvehicleid = dmvvehicleid;
	}

	public String getVehicleDetails() {
		StringBuffer vehicleDetails = new StringBuffer(50);
		if(vehiclemake != null){
			appenedText(vehicleDetails,vehiclemake,"-");
		}
		if(vehiclemodel != null){
			appenedText(vehicleDetails,vehiclemodel,"-");
		}
		/*if(vehicleColor != null){
			appenedText(vehicleDetails,vehicleColor,"-");
		}
		if(vehicleYear != null){
			appenedText(vehicleDetails,vehicleYear,"-");
		}*/
		return vehicleDetails.toString();
	}
	
	public void setVehicleDetails(String vehicleDetails) {}
	
	public String getPrimaryOwner(){
		StringBuffer name = new StringBuffer(50);
		if(firstname != null){
			appenedText(name,firstname," ");
		}
		if(middlename != null){
			appenedText(name,middlename," ");
		}
		if(lastname != null){
			appenedText(name,lastname,", ");
		}
		return name.toString();
	}
	
	public void setPrimaryOwner(String primaryOwner){}
	
	public String getSecondaryOwner(){
		StringBuffer name = new StringBuffer(50);
		if(firstname2 != null){
			appenedText(name,firstname2," ");
		}
		if(middlename2 != null){
			appenedText(name,middlename2," ");
		}
		if(lastname2 != null){
			appenedText(name,lastname2,", ");
		}
		return name.toString();
	}
	
	public void setSecondaryOwner(String secondaryOwner){}
	
	public String getRegAddress(){
		StringBuffer addressBuf = new StringBuffer(50);
		if(address1 != null){
			appenedText(addressBuf,address1,", ");
		}
		if(address2 != null){
			appenedText(addressBuf,address2,", ");
		}
		if(city != null){
			appenedText(addressBuf,city,", ");
		}
		if(state != null){
			appenedText(addressBuf,state,", ");
		}
		if(zipcode != null){
			appenedText(addressBuf,zipcode,", ");
			if(plus4 != null){
				appenedText(addressBuf,plus4,"-");
			}
		}
		return addressBuf.toString();
	}
	
	public void setRegAddress(String regAddress1){}
	
	private void appenedText(StringBuffer buff, String obj, String delimeter){
		if(obj != null && obj.length()>0){
			if(buff.length()>0){
				buff.append(delimeter);
			}
			buff.append(obj);
		}
	}

	public String getVehiclemake() {
		return vehiclemake;
	}

	public void setVehiclemake(String vehiclemake) {
		this.vehiclemake = vehiclemake;
	}


}
