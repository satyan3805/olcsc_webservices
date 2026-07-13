/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.cart;

import java.io.Serializable;

/**
 * This interface specifies the methods that all Shopping Cart objects must
 * implement to present their basic details.
 * <table><tr><td colspan="2">The detail is expected to be structured as:</td></tr>
 * <tr><td style="text-align:right;"></td><td>Amount Due</td></tr>
 * <tr><td style="text-align:right;">-</td><td>Past Payments</td></tr>
 * <tr><td style="text-align:right;">=</td><td>Current Amount Due</td></tr>
 * <tr><td style="text-align:right;">-</td><td>Discount</td></tr>
 * <tr><td style="text-align:right;">-</td><td>Payment</td></tr>
 * <tr><td style="text-align:right;">=</td><td>Remaining Balance</td></tr>
 * </table>
 * @see ShoppingCart
 * @author Milosh Boroyevich
 */
public interface CartDetail extends Serializable {
    /**
     * Returns the amount due.
     * @return the amount due
     */
    public float getAmountDue();

    /**
     * Returns the past payments made.
     * @return the amount of past payments
     */
    public float getPastPayments();

    /**
     * Returns the amount due before discount.
     * Calculated as (amount due - past payments).
     * @return the amount due before discount
     * @see #getAmountDue()
     * @see #getPastPayments()
     */
    public float getCurrentAmountDue();

    /**
     * Returns the discount amount.
     * @return the discount amount
     */
    public float getDiscount();

    /**
     * Returns the payment amount.
     * @return the payment amount
     */
    public float getPayment();

    /**
     * Returns the remaining balance (amount due after applying discount and payments).
     * @return the remaining balance
     */
    public float getRemainingBalance();
}
