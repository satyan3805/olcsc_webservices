package com.etcc.csc.dto;

import java.io.Serializable;

/**
 * Representation of an Account Transaction.
 */
public class AccountTransaction implements Serializable {
    /**
     * Unique ID for Serialization.
     */
    private static final long serialVersionUID = 8597710636330726854L;
    private float amt;
    private String description;

    /**
     * Constructor.
     */
    public AccountTransaction() {
        // end <init>
    }

    /**
     * Sets the Amount of the Transaction.
     * @param amt the amount of the transaction.
     */
    public void setAmt(float amt) {
        this.amt = amt;
    }

    /**
     * Gets the amount of the Transaction.
     * @return the amount of the transaction.
     */
    public float getAmt() {
        return this.amt;
    }

//    public String getAmtStr() {
//        return StringUtil.currencyFormat.format(amt);
//    }

    /**
     * Sets the Description of the transaction.
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the transaction.
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }
}
