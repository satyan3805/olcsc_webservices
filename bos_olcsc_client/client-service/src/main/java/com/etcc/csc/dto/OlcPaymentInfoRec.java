package com.etcc.csc.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OlcPaymentInfoRec {

	private String pmtType;
	private BigDecimal personId;
	private String cardNbr;
	private Date cardExpires;
	private BigDecimal cardType;
	private String cardName;
	private BigDecimal accountBillingMethodId;
	private String isDefaultBillingMethod;
	private String isActive;
	private BigDecimal token;
	private BigDecimal addressId;
	private BigDecimal phoneId;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String plus4;
	private String phoneNbr;
	private String phoneExtn;
	private String bankAcctType;
	private String bankAcctNumber;
	private String routingNbr;
	private String isBlocked;
	private String billingType;
	private BigDecimal billingPriority;

	/**
	 * @return the pmtType
	 */
	public String getPmtType() {
		return pmtType;
	}

	/**
	 * @param pmtType
	 *            the pmtType to set
	 */
	public void setPmtType(String pmtType) {
		this.pmtType = pmtType;
	}

	/**
	 * @return the personId
	 */
	public BigDecimal getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(BigDecimal personId) {
		this.personId = personId;
	}

	/**
	 * @return the cardNbr
	 */
	public String getCardNbr() {
		return cardNbr;
	}

	/**
	 * @param cardNbr
	 *            the cardNbr to set
	 */
	public void setCardNbr(String cardNbr) {
		this.cardNbr = cardNbr;
	}

	/**
	 * @return the cardExpires
	 */
	public Date getCardExpires() {
		return cardExpires;
	}

	/**
	 * @param cardExpires
	 *            the cardExpires to set
	 */
	public void setCardExpires(Date cardExpires) {
		this.cardExpires = cardExpires;
	}

	/**
	 * @return the cardType
	 */
	public BigDecimal getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * @param cardName
	 *            the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * @return the accountBillingMethodId
	 */
	public BigDecimal getAccountBillingMethodId() {
		return accountBillingMethodId;
	}

	/**
	 * @param accountBillingMethodId
	 *            the accountBillingMethodId to set
	 */
	public void setAccountBillingMethodId(BigDecimal accountBillingMethodId) {
		this.accountBillingMethodId = accountBillingMethodId;
	}

	/**
	 * @return the isDefaultBillingMethod
	 */
	public String getIsDefaultBillingMethod() {
		return isDefaultBillingMethod;
	}

	/**
	 * @param isDefaultBillingMethod
	 *            the isDefaultBillingMethod to set
	 */
	public void setIsDefaultBillingMethod(String isDefaultBillingMethod) {
		this.isDefaultBillingMethod = isDefaultBillingMethod;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the token
	 */
	public BigDecimal getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(BigDecimal token) {
		this.token = token;
	}

	/**
	 * @return the addressId
	 */
	public BigDecimal getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(BigDecimal addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the phoneId
	 */
	public BigDecimal getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId
	 *            the phoneId to set
	 */
	public void setPhoneId(BigDecimal phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the plus4
	 */
	public String getPlus4() {
		return plus4;
	}

	/**
	 * @param plus4
	 *            the plus4 to set
	 */
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}

	/**
	 * @return the phoneNbr
	 */
	public String getPhoneNbr() {
		return phoneNbr;
	}

	/**
	 * @param phoneNbr
	 *            the phoneNbr to set
	 */
	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	/**
	 * @return the phoneExtn
	 */
	public String getPhoneExtn() {
		return phoneExtn;
	}

	/**
	 * @param phoneExtn
	 *            the phoneExtn to set
	 */
	public void setPhoneExtn(String phoneExtn) {
		this.phoneExtn = phoneExtn;
	}

	/**
	 * @return the bankAcctType
	 */
	public String getBankAcctType() {
		return bankAcctType;
	}

	/**
	 * @param bankAcctType
	 *            the bankAcctType to set
	 */
	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	/**
	 * @return the bankAcctNumber
	 */
	public String getBankAcctNumber() {
		return bankAcctNumber;
	}

	/**
	 * @param bankAcctNumber
	 *            the bankAcctNumber to set
	 */
	public void setBankAcctNumber(String bankAcctNumber) {
		this.bankAcctNumber = bankAcctNumber;
	}

	/**
	 * @return the routingNbr
	 */
	public String getRoutingNbr() {
		return routingNbr;
	}

	/**
	 * @param routingNbr
	 *            the routingNbr to set
	 */
	public void setRoutingNbr(String routingNbr) {
		this.routingNbr = routingNbr;
	}

	/**
	 * @return the isBlocked
	 */
	public String getIsBlocked() {
		return isBlocked;
	}

	/**
	 * @param isBlocked
	 *            the isBlocked to set
	 */
	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	/**
	 * @return the billingType
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            the billingType to set
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return the billingPriority
	 */
	public BigDecimal getBillingPriority() {
		return billingPriority;
	}

	/**
	 * @param billingPriority
	 *            the billingPriority to set
	 */
	public void setBillingPriority(BigDecimal billingPriority) {
		this.billingPriority = billingPriority;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcPaymentInfoRec [pmtType=" + pmtType + ", personId=" + personId + ", cardNbr=" + cardNbr
				+ ", cardExpires=" + cardExpires + ", cardType=" + cardType + ", cardName=" + cardName
				+ ", accountBillingMethodId=" + accountBillingMethodId + ", isDefaultBillingMethod="
				+ isDefaultBillingMethod + ", isActive=" + isActive + ", token=" + token + ", addressId=" + addressId
				+ ", phoneId=" + phoneId + ", firstName=" + firstName + ", lastName=" + lastName + ", address1="
				+ address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zipcode=" + zipcode + ", plus4=" + plus4 + ", phoneNbr=" + phoneNbr + ", phoneExtn=" + phoneExtn
				+ ", bankAcctType=" + bankAcctType + ", bankAcctNumber=" + bankAcctNumber + ", routingNbr=" + routingNbr
				+ ", isBlocked=" + isBlocked + ", billingType=" + billingType + ", billingPriority=" + billingPriority
				+ "]";
	}

	


}
