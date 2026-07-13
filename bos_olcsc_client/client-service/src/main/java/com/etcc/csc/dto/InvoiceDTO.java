/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.cart.CartItem;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.util.CoreDateUtil;

/**
 * Represents a violation Invoice.
 * Waivers, discounts, and adjustments are only applied to administrative fees.
 */
//NOTE: XFire has been having issues with the computed getters, so a mapping file, InvoiceDTO.aegis.xml was created.
//This file MUST be kept up to date with the properties, or at least, the exclusions.
public class InvoiceDTO extends BaseDTO implements CartItem {
    /**
     * Unique ID for Serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = 5825683205728185676L;

    // TODO: Move this to sys_param table
    public static final float ONLINE_DISCOUNT_ADMIN = 0.2f;
    // TODO: Move this to range lookup stored in DB
    public static final float ADJUSTMENT_ADMIN = 0.5f;

    /**
     * The types of payments that can be applied to an invoice.
     * @author Milosh Boroyevich
     */
    public enum PaymentOption {
        /**
         * Balance due will be paid in full (minus any applicable discounts).
         */
    	PAY_IN_FULL("pay in full"),
    	/**
    	 * User is specifying how much of the outstanding balance will be paid.
    	 */
    	SPECIFY_AMOUNT("partially pay"),
    	/**
    	 * User will not be paying this invoice.
    	 */
    	DONT_PAY("don't pay now"),
    	/**
    	 * User is waiving admin fees & other charges.
    	 */
    	WAIVE("waive fees"),
    	/**
    	 * User is adjusting admin fees & other charges.
    	 */
    	ADJUST("adjust fees");

    	/**
    	 * The user-friendly name of this enum.
    	 */
    	private final String display;

    	private PaymentOption(final String display){
    	    this.display = display;
    	}

    	/**
    	 * Gets the "user-friendly" string form.
    	 * @return the display string
    	 */
    	public String getDisplay() {
    	    return this.display;
    	}
    }


    private AgencyEnum agency;
    private String invoiceId;
    private Calendar invoiceDate;
    private Calendar dueDate;
    private PaymentOption paymentType;
    /** Invoice due (already includes all violations and fees). */
    private float invoiceDue;
    private float amountAlreadyPaid;
    private float paymentsPending;
    private float payment;
    private float administrativeFee;
    private float collectionFee;
    private float otherFee;
    private ItemType itemType;
    
//    private Blob invoiceDoc;
    private Collection<ViolationDTO> violations;
    /**
     * Whether this invoice can have admin fees discounted.
     * @see #administrativeFee
     */
    private boolean onlineDiscountEligible = false;
    /**
     * Whether this invoice can have admin fees waived.
     * @see #adjustmentEligible
     */
    private boolean waiverEligible = false;
    /**
     * Whether this invoice can have admin fees adjusted.
     * @see #administrativeFee
     */
    private boolean adjustmentEligible = false;


    /**
     * Constructor that sets the issuing agency.
     * @param agency the issuing agency
     * @throws NullPointerException if agency is <tt>null</tt>
     */
    public InvoiceDTO(AgencyEnum agency) throws NullPointerException {
        if (agency == null) throw new NullPointerException("Agency cannot be null!");
    	this.agency = agency;
    }

    /**
     * Constructor required for HTML form submission.
     * This constructor will NOT be removed, but is deprecated to ensure its use is limited.
     * Form processing must set the agency on this object as part of the initialization.
     * @deprecated use {@link #InvoiceDTO(AgencyEnum)} (this constructor only to be used in HTML form processing classes).
     * @see #InvoiceDTO(AgencyEnum)
     * @see #setAgency(AgencyEnum)
     * @throws IllegalAccessError always
     */
    @Deprecated
    public InvoiceDTO() throws IllegalAccessError {
    	if (!isCallerXFire()) {
            throw new IllegalAccessError("Agency must be specified.");
    	}
    }

    /**
     * @see #getInvoiceDate()
     */
    public Calendar getIssueDate() {
    	return this.invoiceDate;
    }


    public void setIssueDate(Calendar value)
    {
    	// Do nothing
    }

//    /**
//     * @see CoreDateUtil#SHORT_DATE_PATTERN
//     * @see #getDueDate()
//     */
//    public String getStrDueDate() {
//    	return (this.dueDate == null ? "" : CoreDateUtil.getShortDate(this.dueDate.getTime()));
//    }

    /**
     * Returns the agency code + invoice ID.
     * @see #getInvoiceId()
     * @see #getAgency()
     * @see AgencyEnum#getCode()
     */
    public String getKey() {
        return agency.getCode() + invoiceId;
    }

    public void setKey(String value)
    {
    	//Do nothing
    }

    @IgnoreProperty
    public String getClassName() {
        return getClass().getName();
    }

    public boolean clearPayment() {
        boolean modified = (this.payment != 0);
        this.payment = 0;
        return modified;
    }

    public String getName() {
        //return this.agency.getDisplay() + " " + this.invoiceId;
        return this.invoiceId;
    }


    public void setName(String value)
    {
    	// Do nothing
    }

    /**
     * Returns the eligible payment types.
     * @return the eligible payment types
     */
    public Collection<PaymentOption> getEligiblePaymentTypes() {
    	PaymentOption[] paymentTypes = PaymentOption.values();
    	Collection<PaymentOption> payments = new ArrayList<PaymentOption>(paymentTypes.length);
    	for (PaymentOption payment : paymentTypes) {
            switch(payment) {
            case PAY_IN_FULL:
            case SPECIFY_AMOUNT:
            case DONT_PAY:
                payments.add(payment);
                break;
            case WAIVE:
                if (this.isWaiverEligible()) payments.add(payment);
                break;
            case ADJUST:
                if (this.isAdjustmentEligible()) payments.add(payment);
                break;
            }
    	}
    	return payments;
    }

    public void setEligiblePaymentTypes(Collection<PaymentOption> values) {
    	// Do nothing
    }


    /**
     * Returns the past payments made.
     * Calculated as (amount paid + pending payments).
     * @see #getAmountAlreadyPaid()
     * @see #getPaymentsPending()
     */
    public float getPastPayments() {
        return this.amountAlreadyPaid + this.paymentsPending;
    }

    public void  setPastPayments(float value) {
    	// Do nothing
    }

    /**
     * @see #getInvoiceDue()
     */
    public float getAmountDue() {
    	return this.invoiceDue;
	}

    public void setAmountDue(float value)
    {
    	// Do nothing
    }


    /**
     * @see #getInvoiceDue()
     * @see #getPastPayments()
     * @see #getFinalAmountDue()
     */
    public float getCurrentAmountDue() {
        return this.invoiceDue - getPastPayments();
    }

    public void setCurrentAmountDue(float value)
    {
    	// Do nothing
    }

    /**
     * Returns the final (discounted) amount due.
     * Calculated as (current amount - applied discount).
     * @return the final amount due
     * @see #getDiscount()
     * @see #getCurrentAmountDue()
     */
    public float getFinalAmountDue() {
        return getCurrentAmountDue() - getDiscount();
    }


    public void setFinalAmountDue(float value)
    {
    	// Do nothing
    }

    /**
     * Returns the discount amount.  The discount can be one of online,
     * waiver, or adjustment depending on payment type.
     * @see PaymentOption
     * @see #getOnlineDiscount()
     * @see #getWaiver()
     * @see #getAdjustment()
     */
    public float getDiscount() {
    	if (this.paymentType != null) {
            switch(this.paymentType) {
            case SPECIFY_AMOUNT:
                // may be eligible for online discount if the payment specified is same or higher than "pay-in-full"
            case PAY_IN_FULL:
                return getOnlineDiscount();
            case DONT_PAY:
                return 0.0f;
            case WAIVE:
                return getWaiver();
            case ADJUST:
                return getAdjustment();
            }
    	}
        return 0.0f;
    }

    public void setDiscount(float value) {
    	//Do notning
    }

    /**
     * Returns the online discount amount.
     * An online discount is applied for payments made in full and is
     * calculated as <tt>ONLINE_DISCOUNT_ADMIN * administrativeFee</tt>.
     * For the actual discount applied, see {@link #getDiscount()}.
     * @return the online discount amount
     * @see #isOnlineDiscountEligible()
     * @see #getCurrentAmountDue()
     * @see #getPayment()
     */
    public float getOnlineDiscount() {
        float discount = this.administrativeFee * ONLINE_DISCOUNT_ADMIN;
        if (this.onlineDiscountEligible && (getCurrentAmountDue() - discount - this.payment <= 0))
            return discount;
        return 0.0f;
    }

    public void setOnlineDiscount(float value)
    {
    	// Do nothing
    }

    /**
     * An adjustment discount is a reduces the administrative fees.
     * @return the adjustment discount
     * @see #isAdjustmentEligible()
     * @see #getAdministrativeFee()
     */
    public float getAdjustment() {
        return (this.adjustmentEligible ? this.administrativeFee * ADJUSTMENT_ADMIN : 0.0f);
    }

    public void setAdjustment(float value)
    {
    	// Do nothing
    }

    /**
     * A waiver discount removes all administrative fees.
     * @return the waiver discount
     * @see #isWaiverEligible()
     * @see #getAdministrativeFee()
     */

    public float getWaiver() {
        return (this.waiverEligible ? this.administrativeFee : 0.0f);
    }

    public void setWaiver(float value)
    {
    	// Do nothing
    }

    /**
     * Add the specified violation to the list of violations.
     * @param violation the violation to add
     * @return <tt>true</tt> if this invoice changed as a result of the call
     */
    public boolean addViolation(ViolationDTO violation) {
        if (this.violations == null) {
            this.violations = new ArrayList<ViolationDTO>();
        }
        return this.violations.add(violation);
    }

    /**
     * Add the specified violations to the existing list of violations.
     * @param violations the violations to add
     * @return <tt>true</tt> if this invoice changed as a result of the call
     */
    public boolean addAllViolations(Collection<ViolationDTO> violations) {
        if (this.violations == null) {
            this.violations = new ArrayList<ViolationDTO>();
        }
        return this.violations.addAll(violations);
    }

    /**
     * Form processing must set the agency on this object as part of the initialization.
     * This method will NOT be removed, but is deprecated to ensure its use is limited.
     * @param agency the agency to set
     * @deprecated set agency in the constructor
     * @see #InvoiceDTO(AgencyEnum)
     * @see #InvoiceDTO()
     * @throws IllegalAccessError always
     */
    @Deprecated
    public void setAgency(AgencyEnum agency) throws IllegalAccessError {
        if (isCallerXFire()){
            this.agency = agency;
        } else {
            throw new IllegalAccessError("Agency cannot be set.");
        }
    }

    /**
     * <strong>NOTE</strong>: It is contrary to the Struts1 JavaBeans
     * Implementation to have more than one setter method (with different
     * argument signatures) for the same property.
     * @param paymentType the paymentType to set
     * @see #setPaymentType(PaymentOption)
     */
    public void setStringPaymentType(String paymentType) {
        this.paymentType = PaymentOption.valueOf(paymentType);
    }

    /**
     * @see #getFinalAmountDue()
     * @see #payment
     */
    public float getRemainingBalance() {
        return this.getFinalAmountDue() - this.payment;
    }

    public void setRemainingBalance(float value) {
    	// Do nothing
    }


    public ItemType getItemType() {
        return ItemType.copy(this.agency);
    }

    public void setItemType(ItemType it) {
       this.itemType = it;
    }

    /**
     * Returns the full id of this invoice as the string obtained by
     * concatenating the agency ordinal value to the invoice id.
     * @see AgencyEnum#ordinal()
     * @see #invoiceId
     * @return the full id of this invoice
     */
    protected String getSortableId() {
        return (this.agency.ordinal() + this.invoiceId);
    }

    /**
     * Invoices are ordered first by agency then by invoice id.
     * @throws NullPointerException if the specified object is <tt>null</tt>.
     * @see #agency
     * @see #invoiceId
     */
    public int compareTo(CartItem o) throws NullPointerException, ClassCastException {
        return this.getSortableId().compareTo(((InvoiceDTO) o).getSortableId());
    }

    /**
     * Returns a hash code for this invoice.
     * The hash code for an invoice is computed as the hash code of the
     * string obtained by concatenating the agency ordinal to the invoice id.
     * @see #getSortableId()
     * @see #invoiceId
     * @see String#hashCode()
     */
    @Override
    public int hashCode() {
    	return this.getSortableId().hashCode();
    }

    /**
     * Two invoices are considered equal if they have the same agency and invoice id.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        try {
            InvoiceDTO other = (InvoiceDTO) obj;
            return (this.getAgency() == other.getAgency()
                    && ((this.invoiceId == other.invoiceId)
                        || (this.invoiceId != null && this.invoiceId.equals(other.invoiceId))));
        } catch (ClassCastException e) { }
        // Not an invoice
        return false;
    }


    /**
     * Set the payment amount and return a reference to this instance.
     * @param payment the payment amount
     * @return a reference to this instance
     */
    public InvoiceDTO withPayment(float payment) {
        this.setPayment(payment);
        return this;
    }
    /**
     * Set the amount invoiced and return a reference to this instance.
     * @param due the amount invoiced
     * @return a reference to this instance
     */
    public InvoiceDTO withInvoiceDue(float due) {
        this.setInvoiceDue(due);
        return this;
    }


    @Override
    public String toString() {
    	return toStringBuilder().toString();
    }
    /**
     * String representation of this object as a StringBuilder, generally used for logging.
     * @return StringBuilder representation of this object.
     * @see #toString()
     */
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
		sb.append("agency=").append(this.agency).append(",");
        sb.append("invoiceId=");
        sb.append(this.invoiceId);
        sb.append(",invoiceDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.invoiceDate));
        // sb.append(",strInvoiceDate=");
        // sb.append(strInvoiceDate);
        sb.append(",dueDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.dueDate));
        // sb.append(",strDueDate=");
        // sb.append(strDueDate);
//        sb.append(",amtDue=");
//        sb.append(this.amtDue);
        sb.append(",paymentType=");
        sb.append(this.paymentType);
        sb.append(",payment=");
        sb.append(this.payment);
        sb.append(",administrativeFee=");
        sb.append(this.administrativeFee);
        sb.append(",totalInvoiceDue=");
        sb.append(this.invoiceDue);
        sb.append(",paymentMadeOnline=");
        sb.append(this.paymentsPending);
        sb.append(",amtAlreadyPaid=");
        sb.append(this.amountAlreadyPaid);
        sb.append(",violations=");
        sb.append(this.violations);
        sb.append("]");
        return sb;
    }



    // *** Standard getters & setters ***
    /**
     * @return the agency
     */
    public AgencyEnum getAgency() {
        return this.agency;
        //end getAgency
    }

    /**
     * @return the invoiceId
     */
    public String getInvoiceId() {
        return this.invoiceId;
        // end getInvoiceId
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        // end setInvoiceId
    }

    /**
     * @return the invoiceDate
     */
    public Calendar getInvoiceDate() {
        return this.invoiceDate;
        // end getInvoiceDate
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
        // end setInvoiceDate
    }

    /**
     * @return the dueDate
     */
    public Calendar getDueDate() {
        return this.dueDate;
        // end getDueDate
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
        // end setDueDate
    }

    /**
     * @return the paymentType
     */
    public PaymentOption getPaymentType() {
        return this.paymentType;
        // end getPaymentType
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(PaymentOption paymentType) {
        this.paymentType = paymentType;
        // end setPaymentType
    }

    public float getPayment() {
        return this.payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(float payment) {
        this.payment = payment;
        // end setPayment
    }

    /**
     * @return the administrativeFee
     */
    public float getAdministrativeFee() {
        return this.administrativeFee;
        // end getAdministrativeFee
    }

    /**
     * @param administrativeFee the administrativeFee to set
     */
    public void setAdministrativeFee(float administrativeFee) {
        this.administrativeFee = administrativeFee;
        // end setAdministrativeFee
    }

    /**
     * @return the collectionFee
     */
    public float getCollectionFee() {
        return this.collectionFee;
        // end getCollectionFee
    }

    /**
     * @param collectionFee the collectionFee to set
     */
    public void setCollectionFee(float collectionFee) {
        this.collectionFee = collectionFee;
        // end setCollectionFee
    }

    /**
     * @return the otherFee
     */
    public float getOtherFee() {
        return this.otherFee;
        // end getOtherFee
    }

    /**
     * @param otherFee the otherFee to set
     */
    public void setOtherFee(float otherFee) {
        this.otherFee = otherFee;
        // end setOtherFee
    }

    /**
     * @return the totalInvoiceDue
     */
    public float getInvoiceDue() {
        return this.invoiceDue;
        // end getTotalInvoiceDue
    }

    /**
     * @param totalInvoiceDue the totalInvoiceDue to set
     */
    public void setInvoiceDue(float totalInvoiceDue) {
        this.invoiceDue = totalInvoiceDue;
        // end setTotalInvoiceDue
    }

    /**
     * @return the paymentMadeOnline
     */
    public float getPaymentsPending() {
        return this.paymentsPending;
        // end getPaymentMadeOnline
    }

    /**
     * @param paymentMadeOnline the paymentMadeOnline to set
     */
    public void setPaymentsPending(float paymentMadeOnline) {
        this.paymentsPending = paymentMadeOnline;
        // end setPaymentMadeOnline
    }

    /**
     * @return the amtAlreadyPaid
     */
    public float getAmountAlreadyPaid() {
        return this.amountAlreadyPaid;
        // end getAmtAlreadyPaid
    }

    /**
     * @param amtAlreadyPaid the amtAlreadyPaid to set
     */
    public void setAmountAlreadyPaid(float amtAlreadyPaid) {
        this.amountAlreadyPaid = amtAlreadyPaid;
        // end setAmtAlreadyPaid
    }

    /**
     * @return the violations
     */
    public Collection<ViolationDTO> getViolations() {
        return this.violations;
        // end getViolations
    }

    /**
     * @param violations the violations to set
     */
    public void setViolations(Collection<ViolationDTO> violations) {
        this.violations = violations;
        // end setViolations
    }

    public boolean isWaiverEligible() {
        return this.waiverEligible;
    }

    public void setWaiverEligible(boolean waiverEligible) {
        this.waiverEligible = waiverEligible;
    }

    public boolean isAdjustmentEligible() {
        return this.adjustmentEligible;
    }

    public void setAdjustmentEligible(boolean adjustmentEligible) {
        this.adjustmentEligible = adjustmentEligible;
    }

    public void setOnlineDiscountEligible(boolean discountEligible) {
        this.onlineDiscountEligible = discountEligible;
    }

    public boolean isOnlineDiscountEligible() {
        return this.onlineDiscountEligible;
    }
}
