package com.etcc.csc.dto.bean;

import java.io.Serializable;

import java.util.Calendar;

public class OlcPaymentInfoRecBean implements Serializable {
    public OlcPaymentInfoRecBean() {
    }

    private String pmtType;
    private String cardCode;
    private String cardSecCode;
    private String cardNbr;
    private Calendar cardExpires;
    private String nameOnCard;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String plus4;
    private String bankAcctType;
    private String bankAcctNumber;
    private String routingNbr;

    public void setPmtType(String pmtType) {
        this.pmtType = pmtType;
    }

    public String getPmtType() {
        return pmtType;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardSecCode(String cardSecCode) {
        this.cardSecCode = cardSecCode;
    }

    public String getCardSecCode() {
        return cardSecCode;
    }

    public void setCardNbr(String cardNbr) {
        this.cardNbr = cardNbr;
    }

    public String getCardNbr() {
        return cardNbr;
    }

    public void setCardExpires(Calendar cardExpires) {
        this.cardExpires = cardExpires;
    }

    public Calendar getCardExpires() {
        return cardExpires;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setBankAcctType(String bankAcctType) {
        this.bankAcctType = bankAcctType;
    }

    public String getBankAcctType() {
        return bankAcctType;
    }

    public void setBankAcctNumber(String bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public String getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setRoutingNbr(String routingNbr) {
        this.routingNbr = routingNbr;
    }

    public String getRoutingNbr() {
        return routingNbr;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("pmtType=").append(pmtType);
        sb.append(", cardCode=").append(cardCode);
        sb.append(", cardSecCode=").append("****"); //cardSecCode);
        sb.append(", cardNbr=").append("****"); //cardNbr);
        sb.append(", cardExpires=").append(cardExpires);
        sb.append(", nameOnCard=").append(nameOnCard);
        sb.append(", address1=").append(address1);
        sb.append(", address2=").append(address2);
        sb.append(", city=").append(city);
        sb.append(", state=").append(state);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", plus4=").append(plus4);
        sb.append(", bankAcctType=").append(bankAcctType);
        sb.append(", bankAcctNumber=").append("****"); //bankAcctNumber);
        sb.append(", routingNbr=").append("****"); //routingNbr);
        sb.append("]");
        return sb.toString();
    }
}
