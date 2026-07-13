/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.AddressUS;

public class CreditCardForm extends ValidatorActionForm implements AddressUS {
	private static final long serialVersionUID = 4958186590671248534L;

	private String cardNbr;
    private String nameOnCard;
    private String cardExpires;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String plus4;
    private Calendar dateApplied;
    private Calendar dateWithdrawn;
    private Calendar dateCreated;
    private String createdBy;
    private long acctId;
    private String cardStatus;
    private String cardCode;
    private boolean paymentFailedFlag;
    private Calendar paymentFailedDate;
    private String cardNbrNew;
    private String personalizedName;
    private boolean editMode = false;
    private String selectedPersonalizedName;
    private byte monthExp;
    private short yearExp;
    private String paymentType;
    private double amountDue;
    private boolean updateConfirmed;
    private boolean successful;
    private long retailTransId;
    private boolean paymentRequired;
    private String origCardNbr;
    private String securityCode;
    
    public CreditCardForm() {
    }

    public String getName() {
        return this.nameOnCard;
    }

    public void setName(String name) {
        this.nameOnCard = name;
    }

    public void setCardNbr(String cardNbr) {
        this.cardNbr = cardNbr;
    }

    public String getCardNbr() {
        return cardNbr;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setCardExpires(String cardExpires) {
        this.cardExpires = cardExpires;
    }

    public String getCardExpires() {
        return cardExpires;
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
        this.zip = zipCode;
    }

    public String getZipCode() {
        return zip;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setDateApplied(Calendar dateApplied) {
        this.dateApplied = dateApplied;
    }

    public Calendar getDateApplied() {
        return dateApplied;
    }

    public void setDateWithdrawn(Calendar dateWithdrawn) {
        this.dateWithdrawn = dateWithdrawn;
    }

    public Calendar getDateWithdrawn() {
        return dateWithdrawn;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setPaymentFailedFlag(boolean paymentFailedFlag) {
        this.paymentFailedFlag = paymentFailedFlag;
    }

    public boolean isPaymentFailedFlag() {
        return paymentFailedFlag;
    }

    public void setPaymentFailedDate(Calendar paymentFailedDate) {
        this.paymentFailedDate = paymentFailedDate;
    }

    public Calendar getPaymentFailedDate() {
        return paymentFailedDate;
    }

    public void setCardNbrNew(String cardNbrNew) {
        this.cardNbrNew = cardNbrNew;
    }

    public String getCardNbrNew() {
        return cardNbrNew;
    }

    public void setPersonalizedName(String personalizedName) {
        this.personalizedName = personalizedName;
    }

    public String getPersonalizedName() {
        return personalizedName;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }
    
    public String getMaskedCardNbr() {
        if (cardNbr != null) {
            try {
                return "*****" + cardNbr.substring(cardNbr.length()-4);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void setSelectedPersonalizedName(String selectedPersonalizedName) {
        this.selectedPersonalizedName = selectedPersonalizedName;
    }

    public String getSelectedPersonalizedName() {
        return selectedPersonalizedName;
    }

    public void setMonthExp(byte monthExp) {
        this.monthExp = monthExp;
    }

    public byte getMonthExp() {
        return monthExp;
    }

    public void setYearExp(short yearExp) {
        this.yearExp = yearExp;
    }

    public short getYearExp() {
        return yearExp;
    }
    
    public byte[] getExpMonths() {
        byte[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
        return months;
    }

    public short[] getExpYears() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy");
        short currYear = 0;
        short[] expYears = new short[10];
        try {
            currYear = Short.parseShort(df.format(date));
        } catch (Exception e) {}
        for (short b=0; b<10; b++) {
            expYears[b] = (short)(currYear + b);
        }
        return expYears;
    }
    
/*    public CreditCardDTO getCreditCardType(int index) {
        if (creditCardTypes == null) {
            creditCardTypes = new ArrayList();
        }
        return (CreditCardDTO) creditCardTypes.get(index);
    }
*/

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setUpdateConfirmed(boolean updateConfirmed) {
        this.updateConfirmed = updateConfirmed;
    }

    public boolean isUpdateConfirmed() {
        return updateConfirmed;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
    }

    public long getRetailTransId() {
        return retailTransId;
    }

    public void setPaymentRequired(boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public boolean isPaymentRequired() {
        return paymentRequired;
    }

    public void setOrigCardNbr(String origCardNbr) {
        this.origCardNbr = origCardNbr;
    }

    public String getOrigCardNbr() {
        return origCardNbr;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
