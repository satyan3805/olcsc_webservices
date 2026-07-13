/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Common billing interface.
 * @author Milosh Boroyevich
 */
public interface BillingInfo {
    /**
     * Returns the type of this billing info.
     * @return the type of this billing info
     */
    public PaymentType getBillingType();

    /**
     * Returns the EFT object for this billing info.
     * @return the EFT object
     */
    public AccountEFTDTO getEft();

    /**
     * Returns the credit cards array for this billing info.
     * @return the credit cards array
     */
    public AccountCreditCardDTO[] getCards();
}
