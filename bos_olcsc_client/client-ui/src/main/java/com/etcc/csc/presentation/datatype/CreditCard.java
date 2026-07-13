package com.etcc.csc.presentation.datatype;

import java.io.Serializable;
////weblogic upgrade cluster issues fix 
public class CreditCard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String billingName;
    private String billingAddress;
    private String billingAddressLine2;
    private String billingCity;
    private String billingState;
    private String billingZipcode;
    private String billingPlus4;
    private String cardNumber;
    private String cardTypeCode;
    private String cardTypeName;
    private String cardSecurityCode;
    private String expirationMonth;
    private String expirationYear;

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddressLine2(String billingAddressLine2) {
        this.billingAddressLine2 = billingAddressLine2;
    }

    public String getBillingAddressLine2() {
        return billingAddressLine2;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingZipcode(String billingZipcode) {
        this.billingZipcode = billingZipcode;
    }

    public String getBillingZipcode() {
        return billingZipcode;
    }

    public void setBillingPlus4(String billingPlus4) {
        this.billingPlus4 = billingPlus4;
    }

    public String getBillingPlus4() {
        return billingPlus4;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }
}
