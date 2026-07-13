package com.etcc.csc.webservice.rest.dto;

public class UpdateMailingAddressRequest {

	private Long acctId;
	private String loginId;
	private String lastLoginIp;
	private String dbSessionId;

	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String state;
	private String country;
	private String zip;
	private String plus4;

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getDbSessionId() {
		return dbSessionId;
	}

	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
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

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPlus4() {
		return plus4;
	}

	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}

	@Override
	public String toString() {
		return "UpdateMailingAddressRequest [acctId=" + acctId + ", loginId=" + loginId + ", lastLoginIp=" + lastLoginIp
				+ ", dbSessionId=" + dbSessionId + ", address1=" + address1 + ", address2=" + address2 + ", address3="
				+ address3 + ", address4=" + address4 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zip=" + zip + ", plus4=" + plus4 + ", getAcctId()=" + getAcctId() + ", getLoginId()="
				+ getLoginId() + ", getLastLoginIp()=" + getLastLoginIp() + ", getDbSessionId()=" + getDbSessionId()
				+ ", getAddress1()=" + getAddress1() + ", getAddress2()=" + getAddress2() + ", getAddress3()="
				+ getAddress3() + ", getAddress4()=" + getAddress4() + ", getCity()=" + getCity() + ", getState()="
				+ getState() + ", getCountry()=" + getCountry() + ", getZip()=" + getZip() + ", getPlus4()="
				+ getPlus4() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
