/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.common.AgencyEnum;

/**
 * Represents a collection of uninvoiced violations along with meta information.
 * This object is structurally similar to an Invoice since they both consist
 * of a collection of violations.
 * 
 * @author Milosh Boroyevich
 */
public class UninvoicedViolationsDTO extends InvoiceDTO implements ViolationsContainer {
    /**
     * Unique ID for Serialization with version.
     */
    // Do NOT regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -8500916893801743168L;

    private static final String ID = "Uninvoiced Tolls";

    private float amountDue = 0;

    /**
     * Constructor to initialize the violations and set the issuing agency.
     * 
     * @param agency the issuing agency
     * @throws NullPointerException if agency is <tt>null</tt>
     */
    public UninvoicedViolationsDTO(AgencyEnum agency) throws NullPointerException {
        super(agency);
        super.setViolations(new ArrayList<ViolationDTO>());
        super.setInvoiceId(ID);
    }

    /**
     * Constructor required for X-fire serialization. This constructor will NOT be removed, but is deprecated to ensure
     * its use is limited. X-fire is stupid and requires public no-arg constructors. For data integrity reasons, it is
     * critical that X-fire set the agency on this object as part of initialization.
     * 
     * @deprecated use {@link #UninvoicedViolationsDTO(AgencyEnum)} (this constructor only to be used in X-fire
     *             serialization classes).
     * @see #UninvoicedViolationsDTO(AgencyEnum)
     * @see #setAgency(AgencyEnum)
     * @throws IllegalAccessError always
     */
    @SuppressWarnings("deprecation")
	@Deprecated
    public UninvoicedViolationsDTO() throws IllegalAccessError {
        if (isCallerXFire()) {
            super.setViolations(new ArrayList<ViolationDTO>());
            super.setInvoiceId(ID);
        } else {
            throw new IllegalAccessError("Agency is immutable.");
        }
    }

    /**
     * Overridden behavior from {@link InvoiceDTO}.
     * 
     * @param agency cannot be set here
     * @throws IllegalAccessError always
     * @deprecated set agency in the constructor
     * @see #UninvoicedViolationsDTO(AgencyEnum)
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    @Override
    public void setAgency(AgencyEnum agency) throws IllegalAccessError {
        if (isCallerXFire()){
            super.setAgency(agency);
        } else {
            throw new IllegalAccessError("Agency is immutable.");
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.copy(this.getAgency());
    }

    @IgnoreProperty
    public int getTotalItems() {
        return size();
    }

    public int size() {
        return super.getViolations().size();
    }

    /**
     * Calculate the current amount due based on the sum of all the violations.
     * @see #getCurrentAmountDue()
     */
    public UninvoicedViolationsDTO calculateTotals() {
        this.amountDue = 0.0f;
        for (ViolationDTO v : super.getViolations()) {
            this.amountDue += v.getAmountDue();
        }
        return this;
    }

    /**
     * Returns an unmodifiable view of the violations.
     * @return an unmodifiable view of the violations
     */
    @Override
    public Collection<ViolationDTO> getViolations() {
        return Collections.unmodifiableCollection(super.getViolations());
    }

    /**
     * @param violations the violations to set
     * @throws IllegalAccessError always, unless called by XFire setter.
     * @see #addViolation(ViolationDTO)
     * @deprecated
     */
    @Override
    @Deprecated
    public void setViolations(Collection<ViolationDTO> violations) throws IllegalAccessError {
        if (isCallerXFire()){
            super.setViolations(violations);
        } else {
            throw new IllegalAccessError("Violations cannot be set explicitly.");
        }
    }

    /**
     * Always returns <tt>0</tt>. Past payments are not applicable to uninvoiced violations.
     * @return <tt>0</tt>
     */
    @Override
    public float getPastPayments() {
        return 0.0f;
    }

    /**
     * Always returns <tt>0</tt>. Discounts are not applicable to uninvoiced violations.
     * @return <tt>0</tt>
     */
    @Override
    public float getDiscount() {
        return 0.0f;
    }


    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder _sb = new StringBuilder();
        _sb.append(this.getClass().getName()).append("=[");
        _sb.append("size=").append(size()).append(",");
        _sb.append(super.toStringBuilder());
        _sb.append("]");
        return _sb;
    }

    @Override
    public float getAmountDue() {
        return this.amountDue;
    }
}
