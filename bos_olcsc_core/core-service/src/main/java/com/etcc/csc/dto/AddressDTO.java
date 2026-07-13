package com.etcc.csc.dto;

public class AddressDTO {
	
	Long addressId;
	String firstName;
	String lastName; 
	String address1;
	String address2;
	String city;
	String state;
	String zipCode;
	String plus4; 
	String countryCode;
	String phone;
	String phoneExtn;
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	  public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneExtn() {
		return phoneExtn;
	}
	public void setPhoneExtn(String phoneExtn) {
		this.phoneExtn = phoneExtn;
	}
	public String getLabel() {
       return checkForNull(getFirstName())
    		    + " " + checkForNull(getLastName())
    			+ " - " + checkForNull(getAddress1())
    			+ " " + checkForNull(getAddress2())
    			+ " " + checkForNull(getCity())
    			+ " "+ checkForNull(getState())
    			+ " "+ checkForNull(getZipCode());

	}
	private String checkForNull(String val) {
    	return val == null? "" : val;
    }

	public AddressDTO(){
		
	}
	public AddressDTO(String firstName, String lastName, String address1, String address2, String city, String state, 
			String zipCode, String plus4, String countryCode,String phone,String phoneExtn)
	  { 
	    setFirstName(firstName);
	    setLastName(lastName);
	    setAddress1(address1);
	    setAddress2(address2);
	    setCity(city);
	    setState(state);
	    setZipCode(zipCode);
	    setPlus4(plus4);
	    setCountryCode(countryCode);
	    setPhone(phone);
	    setPhoneExtn(phoneExtn);
	  }
}
