/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.cart.CartItem;
import com.etcc.csc.common.AgencyEnum;

/**
 * Represents a collection of invoices along with meta information.
 * Each invoice consists of a collection of violations.
 * @author Milosh Boroyevich
 */
public class InvoicedViolationsDTO implements ViolationsContainer {
    /**
     * Unique ID for Serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -1394008880667366623L;

    /** Agency issuing the invoices. */
    private AgencyEnum agency;
    /** Ordered list of invoices corresponding to the agency. */
    private List<InvoiceDTO> invoices;

    // transient cache variables
    private float amountDue;
    private float pastPayments;
    private float currentAmountDue;
    private float finalAmountDue;
    private float payment;
//    private float remainingBalance;
    private boolean waiverEligible = false;
    private boolean adjustmentEligible = false;

    /**
     * Constructor required for X-fire serialization.
     * This constructor will NOT be removed, but is deprecated to ensure its use is limited.
     * X-fire is stupid and requires public no-arg constructors.
     * For data integrity reasons, it is critical that X-fire set the agency on this object as part of initialization.
     * @deprecated use {@link #InvoicedViolationsDTO(AgencyEnum)} (only to be used in X-fire serialization classes).
     * @see #InvoicedViolationsDTO(AgencyEnum)
     * @see #setAgency(AgencyEnum)
     * @throws IllegalAccessError always, unless called by X-fire setter.
     */
    @Deprecated
    //TODO: Java Serialization appears to use a different mechanism.  This needs to be Confirmed.
    public InvoicedViolationsDTO() throws IllegalAccessError {
        if (BaseDTO.isCallerXFire()){
            this.invoices = new ArrayList<InvoiceDTO>();
        } else {
            throw new IllegalAccessError("Constructor InvoicedViolationsDTO() cannot be called explicitly.");
        }
    }

    /**
     * Constructor to initialize the invoices and set the issuing agency.
     * @param agency the issuing agency
     * @throws NullPointerException if agency is <tt>null</tt>
     */
    public InvoicedViolationsDTO(AgencyEnum agency) throws NullPointerException {
        if (agency == null) throw new NullPointerException("Agency cannot be null!");
        this.agency = agency;
        this.invoices = new ArrayList<InvoiceDTO>();
    }

    public AgencyEnum getAgency() {
        return this.agency;
    }

    /**
     * X-fire must set the agency on this object as part of the initialization.
     * This method will NOT be removed, but is deprecated to ensure its use is limited.
     * @param agency the agency to set
     * @deprecated set agency in the constructor
     * @see #InvoicedViolationsDTO(AgencyEnum)
     * @see #InvoicedViolationsDTO()
     */
    @Deprecated
    public void setAgency(AgencyEnum agency) {
        if (BaseDTO.isCallerXFire()){
            this.agency = agency;
        } else {
            throw new UnsupportedOperationException("setAgency() cannot be called explicitly.");
        }
    }

    /**
     * Add the specified invoice. This method validates the agency on the invoice.
     * @param invoice invoice to add
     * @return <tt>true</tt> if invoice added as a result of the call
     * @throws IllegalArgumentException if the agency on the specified invoice prevents it from being added
     * @see InvoiceDTO#getAgency()
     * @see #getAgency()
     */
    public boolean add(InvoiceDTO invoice) throws IllegalArgumentException {
    	if (invoice.getAgency() != this.agency)
            throw new IllegalArgumentException("Invalid agency: "
    				+ invoice.getAgency() + ", expected: " + this.agency);
    	return this.invoices.add(invoice);
    }

    /**
     * Adds all the invoices in the collection. The agency on each invoice is validated.
     * Note: this method is <i>not</i> faster than making multiple calls to {@link #add(InvoiceDTO)}.
     * @param invoices invoices to add
     * @return <tt>true</tt> if this object is modified as a result of the call
     * @throws IllegalArgumentException if the agency on an invoice prevents it from being added
     * @see #add(InvoiceDTO)
     */
    public boolean addAll(Collection<InvoiceDTO> invoices) {
        boolean result = false;
        for (InvoiceDTO invoice : invoices)
            result = result || add(invoice);
        return result;
    }

    @IgnoreProperty
    public CartItem.ItemType getItemType() {
        return CartItem.ItemType.copy(this.agency);
    }

    public int getTotalItems() {
    	return this.invoices.size();
    }

    public void setTotalItems(int value)
    {
    	//do nothing
    }

    public int size() {
    	return this.invoices.size();
    }

    /**
     * Calculate the preliminary totals (before payments have been applied) and cache them locally.
     * @return this object instance
     * @see #amountDue
     * @see #pastPayments
     * @see #currentAmountDue
     * @see #adjustmentEligible
     * @see #waiverEligible
     * @see #calculateTotals()
     */
    public InvoicedViolationsDTO calculatePreliminaryTotals() {
        this.amountDue = 0;
        this.pastPayments = 0;
        this.currentAmountDue = 0;
        this.adjustmentEligible = false;
        this.waiverEligible = false;
        for (InvoiceDTO invoice : this.invoices) {
            this.amountDue += invoice.getInvoiceDue();
            this.pastPayments += invoice.getPastPayments();
            this.currentAmountDue += invoice.getCurrentAmountDue();
            this.adjustmentEligible = (this.adjustmentEligible || invoice.isAdjustmentEligible());
            this.waiverEligible = (this.waiverEligible || invoice.isWaiverEligible());
        }
        return this;
    }

    /**
     * Calculate the final totals (after payments have been applied) and cache them locally.
     * @see #finalAmountDue
     * @see #payment
     * @see #getRemainingBalance()
     * @see #calculatePreliminaryTotals()
     */
    public InvoicedViolationsDTO calculateTotals() {
        this.finalAmountDue = 0;
        this.payment = 0;
        for (InvoiceDTO invoice : this.invoices) {
            this.finalAmountDue += invoice.getFinalAmountDue();
            this.payment += invoice.getPayment();
        }
        return this;
    }

    /**
     * Returns the discount amount.
     * Note: since the discount is based on actual payments made, it is calculated on the fly.
     * @return the discount amount
     */
    public float getDiscount() {
        float discount = 0;
        for (InvoiceDTO invoice : this.invoices)
            discount += invoice.getDiscount();
        return discount;
    }

    public void setDiscount(float value)
    {
    	//Do nothing
    }

    /**
     * Returns an unmodifiable view of the invoices.
     * @return an unmodifiable view of the invoices
     */
    public List<InvoiceDTO> getInvoices() {
        return Collections.unmodifiableList(this.invoices);
    }

    /**
     * X-fire must set the invoices during deserialization.
     * This method will NOT be removed, but is deprecated to ensure its use is limited.
     * @param invoices the invoices to load
     * @deprecated use {@link #add(InvoiceDTO)} or {@link #addAll(Collection)}
     * @see #add(InvoiceDTO)
     * @see #addAll(Collection)
     * @throws IllegalAccessError always
     */
    @Deprecated
    public void setInvoices(List<InvoiceDTO> invoices) throws IllegalAccessError {
        if (BaseDTO.isCallerXFire()){
            addAll(invoices);
        } else {
            throw new IllegalAccessError("setInvoices() cannot be called explicitly.");
        }
    }

    /**
     * @see InvoiceDTO#getInvoiceDue()
     */
    public float getAmountDue() {
        return this.amountDue;
    }

    public void setAmountDue(float value) {
    }

    /**
     * @see InvoiceDTO#getPastPayments()
     */
    public float getPastPayments() {
        return this.pastPayments;
    }

    public void setPastPayments(float value) {

    }

    /**
     * @see InvoiceDTO#getCurrentAmountDue()
     */
    public float getCurrentAmountDue() {
        return this.currentAmountDue;
    }

    public void setCurrentAmountDue(float value)
    {
    }

    /**
     * @see InvoiceDTO#getFinalAmountDue()
     * @see #calculateTotals()
     */
    public float getFinalAmountDue() {
        return this.finalAmountDue;
    }

    public void setFinalAmountDue(float value)
    {
    	// Do nothing
    }

    /**
     * @see InvoiceDTO#getPayment()
     * @see #calculateTotals()
     */
    public float getPayment() {
        return this.payment;
    }

    public void setPayment(float value)
    {
    }

    /**
     * @see #calculateTotals()
     * @see InvoiceDTO#getFinalAmountDue()
     * @see InvoiceDTO#getPayment()
     */

    public float getRemainingBalance() {
        return this.finalAmountDue - this.payment;
    }

    public void setRemainingBalance(float value) {}


    public boolean isWaiverEligible() {
        return this.waiverEligible;
    }

    public void setWaiverEligible(boolean value) {}

    public boolean isAdjustmentEligible() {
        return this.adjustmentEligible;
    }

    public void setAdjustmentEligible(boolean value) {}

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
    public StringBuilder toStringBuilder() {
        StringBuilder _sb = new StringBuilder();
        _sb.append(this.getClass().getName()).append("=[");
        _sb.append("agency=").append(this.agency).append(",");
        _sb.append("size=").append(size()).append(",");
        _sb.append("invoices=").append(this.invoices).append(",");
        _sb.append(super.toString());
        _sb.append("]");
        return _sb;
    }
}
