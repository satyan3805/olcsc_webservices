/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.BaseContactInfo;

/**
 * Account information form for collecting user data along with Tag information (OLVPS support).
 * @author Milosh Boroyevich
 * @see BaseContactInfo
 */
public class ViolationNewAccountForm extends TagRequestForm implements Address {
	private static final long serialVersionUID = 7631421474379346918L;

    private BaseContactInfo contact = new BaseContactInfo();
    private String email;
    private String confirmEmail;

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) { 
        contact.setNonUSAddress(false);
        setPickup(false);
    }

    public String getName() {
        return contact.getName();
    }

    public void setFirstName(String firstName) {
        this.contact.setFirstName(firstName);
    }

    public String getFirstName() {
        return contact.getFirstName();
    }

    public void setLastName(String lastName) {
        this.contact.setLastName(lastName);
    }

    public String getLastName() {
        return contact.getLastName();
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.contact.setPrimaryPhone(primaryPhone);
    }

    public String getPrimaryPhone() {
        return contact.getPrimaryPhone();
    }

    public void setDriversLicState(String driverLicState) {
        this.contact.setDriverLicState(driverLicState);
    }

    public String getDriversLicState() {
        return contact.getDriverLicState();
    }

    public void setDriversLic(String driverLic) {
        this.contact.setDriverLic(driverLic);
    }

    public String getDriversLic() {
        return contact.getDriverLic();
    }

    public void setNonUSAddress(boolean foreignAddress) {
        this.contact.setNonUSAddress(foreignAddress);
    }

    public boolean isNonUSAddress() {
        return contact.isNonUSAddress();
    }

    public void setAddressLine1(String addressLine1) {
        this.contact.setAddress1(addressLine1);
    }

    public String getAddressLine1() {
        return contact.getAddress1();
    }

    public void setAddressLine2(String addressLine2) {
        this.contact.setAddress2(addressLine2);
    }

    public String getAddressLine2() {
        return contact.getAddress2();
    }

    public void setAddressLine3(String addressLine3) {
        this.contact.setAddress3(addressLine3);
    }

    public String getAddressLine3() {
        return contact.getAddress3();
    }

    public void setAddressLine4(String addressLine4) {
        this.contact.setAddress4(addressLine4);
    }

    public String getAddressLine4() {
        return contact.getAddress4();
    }

    public void setCountry(String country) {
        this.contact.setCountry(country);
    }

    public String getCountry() {
        return contact.getCountry();
    }

    public void setCity(String city) {
        this.contact.setCity(city);
    }

    public String getCity() {
        return contact.getCity();
    }

    public void setContactState(String state) {
        this.contact.setState(state);
    }

    public String getContactState() {
        return contact.getState();
    }

    public void setZip(String zip) {
        this.contact.setZip(zip);
    }

    public String getZip() {
        return contact.getZip();
    }

    public void setPlus4(String plus4) {
        this.contact.setPlus4(plus4);
    }

    public String getPlus4() {
        return contact.getPlus4();
    }

    public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public String getAddress1() {
        return contact.getAddress1();
	}
	public String getAddress2() {
        return contact.getAddress2();
	}
	public String getAddress3() {
        return contact.getAddress3();
	}
	public String getAddress4() {
        return contact.getAddress4();
	}
	public void setAddress1(String address1) {
        this.contact.setAddress1(address1);
	}
	public void setAddress2(String address2) {
        this.contact.setAddress2(address2);
	}
	public void setAddress3(String address3) {
        this.contact.setAddress3(address3);
	}
	public void setAddress4(String address4) {
        this.contact.setAddress4(address4);
	}
}
