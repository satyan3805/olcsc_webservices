/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.util.StringUtil;

/**
 * Credit Card Account Data Transfer Object.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Based on the AccountCreditCardDTO's from the original OLSCSC Services project.
public class AccountCreditCardDTO extends AccountPaymentMethodDTO {
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 1638886843584316976L;
    private long token;
    private String paypageRegistrationId;
    private String cardNbr;
    private String nameOnCard;
    private String cardExpires;
    private Calendar dateApplied;
    private Calendar dateWithdrawn;
    private long acctId;
    private String cardStatus;
    private CreditCardDTO.CreditCardType cardType;
    private boolean paymentFailedFlag;
    private Calendar paymentFailedDate;
    private String cardNbrNew;
    private String personalizedName;
    /** Primary must be set to <tt>true</tt> if there is only one credit card on the account. */
    private boolean primary;
    //changes for Tripos Phase 2
    private String omniToken;

    @IgnoreProperty
    @Override
	public final PaymentType getPaymentType() {
		return PaymentType.CREDIT;
	}

    @IgnoreProperty
    public String getName() {
        return this.nameOnCard;
    }

    /**
     * Returns the char code for the credit card type.
     * @return the char code for the credit card type
     */
    public String getCardCode() {
        return (this.cardType == null ? null : Character.toString(this.cardType.getCode()));
    }

    /**
     * Convenience method for {@link #setCardType(CreditCardDTO.CreditCardType)}.
     * @param code
     * @see #setCardType(CreditCardDTO.CreditCardType)
     * @see CreditCardDTO.CreditCardType#valueOfCode(String)
     */
    public void setCardCode(String code) {
        this.cardType = CreditCardDTO.CreditCardType.valueOfCode(code);
    }


    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("cardNbr=");
        sb.append(StringUtil.maskAccount(this.cardNbr));
        sb.append(",nameOnCard=");
        sb.append(this.nameOnCard);
        sb.append(",cardExpires=");
        sb.append("****");
        sb.append(",address1=");
        sb.append(this.address1);
        sb.append(",address2=");
        sb.append(this.address2);
        sb.append(",address3=");
        sb.append(this.address3);
        sb.append(",address4=");
        sb.append(this.address4);
        sb.append(",city=");
        sb.append(this.city);
        sb.append(",state=");
        sb.append(this.state);
        sb.append(",zipCode=");
        sb.append(this.zip);
        sb.append(",plus4=");
        sb.append(this.plus4);
        sb.append(",country=");
        sb.append(this.country);
        sb.append(",dateApplied=");
        sb.append(this.dateApplied);
        sb.append(",dateWithdrawn=");
        sb.append(this.dateWithdrawn);
        sb.append(",acctId=");
        sb.append(this.acctId);
        sb.append(",cardStatus=");
        sb.append(this.cardStatus);
        sb.append(",cardCode=");
        sb.append(this.cardType);
        sb.append(",paymentFailedFlag=");
        sb.append(this.paymentFailedFlag);
        sb.append(",paymentFailedDate=");
        sb.append(this.paymentFailedDate);
        sb.append(",cardNbrNew=");
        sb.append(StringUtil.maskAccount(this.cardNbrNew));
        sb.append(",personalizedName=");
        sb.append(this.personalizedName);
        sb.append(",primary=");
        sb.append(this.primary);
        sb.append(",omniToken=");
        sb.append(this.omniToken);
        sb.append("]");
        return sb;
    }

//    /**
//     * Masks a credit card number, such that only first four and last three are showing.
//     * @param cardNbr the credit card number to mask.
//     * @return Masked credit card number.
//     */
//    public static StringBuilder getMaskedNumber(String cardNbr){
//        if (cardNbr == null){
//            return null;
//        }//else
//        StringBuilder sb = new StringBuilder(16);
//        sb.append(cardNbr.substring(0, 4));
//        final int end = cardNbr.length() - 3;
//        for(int i = 4; i < end; i++){
//            sb.append('*');
//        }
//        sb.append(cardNbr.substring(end));
//        return sb;
//    }

    /**
     * Gets the Expiry Date for the Credit Card as a Date object.
     * @return Expiry Date.
     * @throws EtccException thrown if the Date can not be parsed into a Date object.
     */
    public Date getCardExpiresDate() throws EtccException{
        if (this.cardExpires == null){
            return null;
        } //else
        try {
            Calendar cal = Calendar.getInstance();
            int pos = this.cardExpires.indexOf("/");
            int month = Integer.parseInt(this.cardExpires.substring(0, pos)) - 1;
            int year = Integer.parseInt(this.cardExpires.substring(pos + 1, this.cardExpires.length()));
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DATE, 1);
            return cal.getTime();
        } catch (RuntimeException e){
            //Unable to parse stored date.
            throw new EtccException("Unable to parse Credit Card Expired Date " + this.cardExpires + ": "
                    + e.getMessage(), e);
        }
    }

    /**
     * Card expiration date in the format <tt>MM/yyyy</tt>.
     * @param cardExpires the expiry month/Year to set
     */
    public void setCardExpires(String cardExpires) {
        this.cardExpires = cardExpires;
        // end setCardExpires
    }

    /**
     * Convenience wrapper method to set the credit card expiry.
     * @param cardExpires the card expiry date to set
     */
    public void setCardExpiresDate(Date cardExpires) {
        DateFormat df = new SimpleDateFormat("MM/yyyy");
        this.cardExpires = df.format(cardExpires);
    }


    // Default getter/setter pairs

    public String getCardNbr() {
		return cardNbr;
	}

	public void setCardNbr(String cardNbr) {
		this.cardNbr = cardNbr;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public Calendar getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Calendar dateApplied) {
		this.dateApplied = dateApplied;
	}

	public Calendar getDateWithdrawn() {
		return dateWithdrawn;
	}

	public void setDateWithdrawn(Calendar dateWithdrawn) {
		this.dateWithdrawn = dateWithdrawn;
	}

	public long getAcctId() {
		return acctId;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public CreditCardDTO.CreditCardType getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardDTO.CreditCardType cardType) {
		this.cardType = cardType;
	}

	public boolean isPaymentFailedFlag() {
		return paymentFailedFlag;
	}

	public void setPaymentFailedFlag(boolean paymentFailedFlag) {
		this.paymentFailedFlag = paymentFailedFlag;
	}

	public Calendar getPaymentFailedDate() {
		return paymentFailedDate;
	}

	public void setPaymentFailedDate(Calendar paymentFailedDate) {
		this.paymentFailedDate = paymentFailedDate;
	}

	public String getCardNbrNew() {
		return cardNbrNew;
	}

	public void setCardNbrNew(String cardNbrNew) {
		this.cardNbrNew = cardNbrNew;
	}

	public String getPersonalizedName() {
		return personalizedName;
	}

	public void setPersonalizedName(String personalizedName) {
		this.personalizedName = personalizedName;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getCardExpires() {
		return cardExpires;
	}

	



	public boolean equivalent(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCreditCardDTO other = (AccountCreditCardDTO) obj;
		if (cardExpires == null) {
			if (other.cardExpires != null)
				return false;
		} else if (!cardExpires.equals(other.cardExpires))
			return false;
		if (cardNbr == null) {
			if (other.cardNbr != null)
				return false;
		} else if (!StringUtil.last4Of(cardNbr).equals(StringUtil.last4Of(other.cardNbr)))
			return false;
		if (cardType != other.cardType)
			return false;
		if (nameOnCard == null) {
			if (other.nameOnCard != null)
				return false;
		} else if (!nameOnCard.equals(other.nameOnCard.toUpperCase()))
			return false;
		if (personalizedName == null) {
			if (other.personalizedName != null)
				return false;
		} else if (!personalizedName.equals(other.personalizedName))
			return false;
		if (primary != other.primary)
			return false;
		if (token != other.token)
			return false;
		return true;
	}

	public String getPaypageRegistrationId() {
		return paypageRegistrationId;
	}

	public void setPaypageRegistrationId(String paypageRegistrationId) {
		this.paypageRegistrationId = paypageRegistrationId;
	}

	@IgnoreProperty
	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}
	//changes for Tripos Phase 2
	public String getOmniToken() {
		return omniToken;
	}

	public void setOmniToken(String omniToken) {
		this.omniToken = omniToken;
	}

	
}
