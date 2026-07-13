/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;
import java.util.Collection;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;

/**
 * Billing Information Data Transfer Object.  Based on the BillingInfoDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class BillingInfoDTO extends BaseDTO implements BillingInfo, AddressContainer {
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = -9130630919423060178L;

    private PaymentType billingType;
    private AccountEFTDTO eft;
    private AccountCreditCardDTO[] cards;
    private long transactionId;


    private boolean diffBillingAddress;
    private String billingTypeCode;
    private String billingTypeDisplay;
    private BillingMethodLimits billingMethodLimits;
    

	/**
     * Sets the credit cards to the specified collection converted to an array and updates the billing type.
     * @param cardsDTO the cardsDTO to set
     * @see #setCards(AccountCreditCardDTO[])
     */
    @IgnoreProperty
    public void setCards(Collection<AccountCreditCardDTO> cardsDTO) {
        if (cardsDTO == null)
            this.cards = null;
        else
            setCards(cardsDTO.toArray(new AccountCreditCardDTO[cardsDTO.size()]));
    }

    /**
     * Sets the credit cards to the specified array and updates the billing type.
     * @param cardsDTO the cardsDTO to set
     * @see #setBillingType(PaymentType)
     */
    public void setCards(AccountCreditCardDTO[] cardsDTO) {
        this.cards = cardsDTO;
        //this.billingType = PaymentType.CREDIT; //msp_2464 remove this. it should be checked by default billing method value
    }

    /**
     * Sets the EFT to the specified object and updates the billing type.
     * @param eftDTO the eftDTO to set
     * @see #setBillingType(PaymentType)
     */
    public void setEft(AccountEFTDTO eftDTO) {
        this.eft = eftDTO;
        //this.billingType = PaymentType.EFT;//msp_2464 remove this. it should be checked by default billing method value
    }

    /**
     * Sets the payment method to the supplied object.  Note that any other payment types that had been set will be
     * deleted.
     * @param paymentMethod The Payment Method to set.
     */
    @IgnoreProperty
    public void setPaymentMethod(AccountPaymentMethodDTO paymentMethod) {
        this.billingType = paymentMethod.getPaymentType();
        switch(this.billingType) {
        case CREDIT:
            this.cards = new AccountCreditCardDTO[1];
            this.cards[0] = (AccountCreditCardDTO)paymentMethod;
            this.eft = null;
            break;
        case EFT:
            this.eft = (AccountEFTDTO)paymentMethod;
            this.cards = null;
            break;
        case INVOICE:
            this.eft = null;
            this.cards = null;
            throw new IllegalArgumentException("Invoices not supported yet.");
        default:
            throw new IllegalArgumentException(this.billingType.getDisplay() + " not supported yet.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("billingType=");
        sb.append(this.billingType);
        sb.append(",transactionId=");
        sb.append(this.transactionId);
        sb.append('\n');
        if (billingType == PaymentType.CREDIT) {
            sb.append(Arrays.toString(this.cards));
        } else if (this.eft != null) {
        	sb.append(this.eft.toStringBuilder());
        }
        return sb.toString();
    }

    @IgnoreProperty
    public boolean isCreditCard() {
    	return this.billingType == PaymentType.CREDIT;
    }
    
    public void setBillingTypeCode(String billingTypeCode) {
		this.billingTypeCode = billingTypeCode;
	}

	public void setBillingTypeDisplay(String billingTypeDisplay) {
		this.billingTypeDisplay = billingTypeDisplay;
	}

    //@IgnoreProperty
    public String getBillingTypeCode() {
    	return (this.billingType == null ? "" : String.valueOf(this.billingType.getCode()));
    }

    //@IgnoreProperty
    public String getBillingTypeDisplay() {
        return (billingType == null ? "" : billingType.toString());
    }

    public void setBillingType(String billingType) {
        this.billingType = PaymentType.valueOf(billingType);
    }

    /**
     * Returns the primary credit card.
     * @return the first primary credit card or <tt>null</tt> if no primary card found
     * @see AccountCreditCardDTO#isPrimary()
     */
    @IgnoreProperty
	public AccountPaymentMethodDTO getPrimaryCard() {
        if (PaymentType.CREDIT.equals(this.billingType) && this.cards != null && this.cards.length>0) {
            for (AccountCreditCardDTO cc : this.cards) {
                if (cc.isPrimary()) {
                    return cc;
                }
            }
        }
        else if (PaymentType.EFT.equals(this.billingType) && this.eft != null)
        {
        	return this.eft;
        }
        return null;
    }

    /**
     * @see #getPrimaryCard()
     */
    @IgnoreProperty
    public Address getAddress() {
        return getPrimaryCard();
    }


    /* (non-Javadoc)
     * @see BillingInfo#getEft()
     */
    public AccountEFTDTO getEft() {
        return this.eft;
    }

    /* (non-Javadoc)
     * @see BillingInfo#getCards()
     */
    public AccountCreditCardDTO[] getCards() {
        return this.cards;
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return this.transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

	/* (non-Javadoc)
     * @see BillingInfo#getBillingType()
     */
	public PaymentType getBillingType() {
		return billingType;
	}

	public void setBillingType(PaymentType billingType) {
        this.billingType = billingType;
    }
	@IgnoreProperty
	public boolean isDiffBillingAddress() {
		return diffBillingAddress;
	}

	public void setDiffBillingAddress(boolean diffBillingAddress) {
		this.diffBillingAddress = diffBillingAddress;
	}

	public BillingMethodLimits getBillingMethodLimits() {
		return billingMethodLimits;
	}

	public void setBillingMethodLimits(BillingMethodLimits billingMethodLimits) {
		this.billingMethodLimits = billingMethodLimits;
	}

	


}
