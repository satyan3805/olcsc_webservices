/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

/**
 * Extracted base contact data from various UI forms.
 * @author Milosh Boroyevich
 */
public class BaseContactInfo implements Address, Serializable {
	private static final long serialVersionUID = -1321294251446231220L;

	private String firstName;
	private String lastName;
	private String primaryPhone;
	private String driverLicState;
	private String driverLic;
	private boolean nonUSAddress;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String country;
	private String city;
	private String state;
	private String zip;
	private String plus4;

	@IgnoreProperty
    public String getName() {
        return this.firstName + ' ' + this.lastName;
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

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getDriverLicState() {
		return driverLicState;
	}

	public void setDriverLicState(String driversLicState) {
		this.driverLicState = driversLicState;
	}

	public String getDriverLic() {
		return driverLic;
	}

	public void setDriverLic(String driversLic) {
		this.driverLic = driversLic;
	}

	public boolean isNonUSAddress() {
		return nonUSAddress;
	}

	public void setNonUSAddress(boolean nonUSAddress) {
		this.nonUSAddress = nonUSAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
}
