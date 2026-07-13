/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Payment Method common fields and methods.
 * @author Milosh Boroyevich
 */
public abstract class AccountPaymentMethodDTO extends BaseDTO implements Address {
	private static final long serialVersionUID = -6608866193813443099L;
	private static final Logger logger = Logger.getLogger(AccountPaymentMethodDTO.class);

	protected long retailTransId;
	protected double paymentAmount;
	protected String address1;
	protected String address2;
	protected String address3;
	protected String address4;
	protected String city;
	protected String state;
	protected String zip;
	protected String plus4;
	protected String country;

	protected Long personId;
	protected Long accountBillingMethodId;
	protected Long addressId;
	protected Long phoneId;
	protected  String isBlocked;
	protected String billingType;
	protected Long billingPriority;
	

	/**
	 * Returns the type of the payment method.
	 * Note: implementations of this method must never return <tt>null</tt>.
	 * @return the payment type
	 */
	@IgnoreProperty
	public abstract PaymentType getPaymentType();

	/**
	 * Compares the address on this payment method with the Address on the provided object.
	 * @param accountDto The account to read the address from.
	 * @return if the Credit Card has the same address as the account.
	 */
	public boolean sameAddress(Address accountDto) {
		if (accountDto == null) return false;
		String accountCountry = accountDto.getCountry();
        if (logger.isDebugEnabled()) {
			logger.debug("in sameaddress.accountDTO.country" + accountCountry + ";address1="
	            + accountDto.getAddress1() + ";address2=" + accountDto.getAddress2() + ";address3="
	            + accountDto.getAddress3() + ";address4=" + accountDto.getAddress4() + ";city=" + accountDto.getCity()
	            + ";state=" + accountDto.getState() + ";zip=" + accountDto.getZip());
			logger.debug("in sameaddress.card.country=" + this.country + ";address1=" + this.address1 + ";address2="
	            + this.address2 + ";address3=" + this.address3 + ";address4=" + this.address4 + ";city=" + this.city
	            + ";state=" + this.state + ";zip=" + this.zip);
		}
	    if (StringUtil.isEmpty(accountCountry) || (accountCountry.equalsIgnoreCase(AddressUS.COUNTRY_CODE_USA))) {
	        // compare US Addresses
	        if (!(StringUtil.isEmpty(this.country) || this.country.equalsIgnoreCase(AddressUS.COUNTRY_CODE_USA))
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress1(), this.address1)
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress2(), this.address2)
	                || !StringUtil.equalsIgnoreCase(accountDto.getCity(), this.city)
	                || !StringUtil.equalsIgnoreCase(accountDto.getState(), this.state)
	                || !StringUtil.equalsIgnoreCase(accountDto.getZip(), this.zip)
	        )
	            return false;
	    } else {
            // compare non-US Addresses
	        if (StringUtil.isEmpty(this.country) || this.country.equalsIgnoreCase(AddressUS.COUNTRY_CODE_USA)
	                || !StringUtil.equalsIgnoreCase(accountCountry, this.country)
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress1(), this.address1)
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress2(), this.address2)
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress3(), this.address3)
	                || !StringUtil.equalsIgnoreCase(accountDto.getAddress4(), this.address4)
	        )
	            return false;
	    }
	    return true;
	}

	public long getRetailTransId() {
		return retailTransId;
	}
	public void setRetailTransId(long retailTransId) {
		this.retailTransId = retailTransId;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
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
	public String getZipCode() {
		return zip;
	}
	public void setZipCode(String zipCode) {
		this.zip = zipCode;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
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

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getAccountBillingMethodId() {
		return accountBillingMethodId;
	}

	public void setAccountBillingMethodId(Long accountBillingMethodId) {
		this.accountBillingMethodId = accountBillingMethodId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Long phoneId) {
		this.phoneId = phoneId;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public Long getBillingPriority() {
		return billingPriority;
	}

	public void setBillingPriority(Long billingPriority) {
		this.billingPriority = billingPriority;
	}

}
