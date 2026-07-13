/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.cart.CartItem;
import com.etcc.csc.cart.CartItemLocator;
import com.etcc.csc.cart.CartSummary;
import com.etcc.csc.cart.ShoppingCart;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Represents a Violator.
 */
//NOTE: XFire has been having issues with the computed getters, so a mapping file, InvoiceDTO.aegis.xml was created.
//This file MUST be kept up to date with the properties, or at least, the exclusions.
public class ViolatorDTO extends BaseDTO implements Cloneable, CartSummary, CartItemLocator {
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -9202388263482408946L;

    private static final Logger logger = Logger.getLogger(ViolatorDTO.class);

    /**
     * For printing out invoices to a log.
     */
    private static final int LOG_MAX_INVOICE_COUNT = 2;

    private long violatorId;
    private String firstName;
    private String lastName;
    private String licPlateNbr;
    private String licPlateState;
    private Calendar lastUpdatedDate;
    private boolean payPlanExists;
    private AccountLoginDTO accountLoginDTO;

    /** Sorted Map of Invoiced violations for each agency. */
    private Map<String,InvoicedViolationsDTO> invoicedViolationsMap = null;
    /** Sorted Map of Un-invoiced violations for each agency. */
    private Map<String,UninvoicedViolationsDTO> uninvoicedViolationsMap = null;

    /** Map keyed by concatenation of agency code and invoice id. */
    private Map<String, InvoiceDTO> invoicesById;

    /** EZ account associated with this violator for use with payments/discounts. */
	private AccountDTO account;
	/**
	 * Whether user selected to use their existing EZ account for resolving the violations.
	 * If the user has not made a choice, this field is <tt>null</tt>.
	 */
	private Boolean payWithAccount = null;
	/** The shopping cart for this violator. */
	private ShoppingCart shoppingCart = new ShoppingCart();


    // transient cache variables
    private float amountDue;
    private float pastPayments;
    private float currentAmountDue;
	private float discount;
    private float payment;
    /** Payments made toward uninvoiced violations. */
	private float uninvoicedPayment;
    private boolean waiverEligible = false;
    private boolean adjustmentEligible = false;


	/**
	 * Clear all containers of invoiced and uninvoiced violations (including the shopping cart).
	 * @see #getInvoicedViolationsMap()
	 * @see #getUninvoicedViolationsMap()
	 * @see #getShoppingCart()
	 * @see #getInvoiceById
	 */
	public void clear() {
		this.invoicedViolationsMap = null;
		this.uninvoicedViolationsMap = null;
		this.invoicesById = null;
		clearShoppingCart();

	}

	/**
     * Clear the failed payments and recalculate the cart totals.
     * @return <tt>true</tt> if the shopping cart is modified
     * @see ShoppingCart#clearFailedPayments()
     * @see ShoppingCart#calculateTotals()
     * @see #calculateTotals()
     */
	public boolean clearFailedPayments() {
		boolean modified = this.shoppingCart.clearFailedPayments();
		if (modified) {
			this.shoppingCart.calculateTotals();
			this.calculateTotals();
		}
		return modified;
	}

    /**
     * Clear the bin confirmation codes.
     * @return <tt>true</tt> if the shopping cart is modified
     * @see ShoppingCart#clearConfirmationCodes()
     */
	public boolean clearConfirmationCodes() {
		return this.shoppingCart.clearConfirmationCodes();
	}

	/**
	 * Clear the shopping cart.
     * @return <tt>true</tt> if the shopping cart is modified
     * @see ShoppingCart#clear()
	 */
	public boolean clearShoppingCart() {
		return this.shoppingCart.clear();
	}

	/**
	 * Returns the shopping cart confirmation code(s).
	 * @return the confirmation codes (or an empty list if there are no confirmation codes)
	 * @see ShoppingCart#getConfirmationCodes()
	 */
	@IgnoreProperty
	public List<String> getConfirmationCodes() {
		return this.shoppingCart.getConfirmationCodes();
	}

	/**
     * Add the specified invoices to this Violator.  Each invoice is added
     * to the agency issuing the invoice (the specified invoices can belong
     * to multiple agencies).
     * @param invoices invoices to add
     * @see #add(InvoiceDTO)
     */
	public void addInvoices(Collection<InvoiceDTO> invoices) {
        if (invoices != null && invoices.size() > 0)
    		for (InvoiceDTO invoice : invoices)
    			add(invoice);
	}

    /**
     * Add a violation invoice to this Violator.
     * Note: This method will create the underlying container objects if not present.
     * @param invoice The invoice to add
     * @return <tt>true</tt> if invoice is added as a result of the call
     * @throws NullPointerException if the specified invoice is <tt>null</tt>
     * @see #getInvoicedViolationsWithCreate(AgencyEnum)
     * @see InvoiceDTO#getKey()
     */
    public boolean add(InvoiceDTO invoice) {
    	if ( this.invoicesById == null )
    	{
    		this.invoicesById = new TreeMap<String,InvoiceDTO>();
    	}
        this.invoicesById.put(invoice.getKey(), invoice);
    	return getInvoicedViolationsWithCreate(invoice.getAgency()).add(invoice);
    }

    /**
     * Returns the invoiced violations for the specified agency.
     * Note: This method will <i>never</i> return <tt>null</tt> creating empty objects if necessary.
     * @param agency the agency issuing the invoices
     * @return the invoiced violations for the specified agency
     * @throws NullPointerException if the specified agency is <tt>null</tt>
     */
    protected InvoicedViolationsDTO getInvoicedViolationsWithCreate(AgencyEnum agency) {
        InvoicedViolationsDTO invoiced = null;
        if (this.invoicedViolationsMap == null) {
            this.invoicedViolationsMap = new TreeMap<String,InvoicedViolationsDTO>();
        } else {
        	invoiced = this.invoicedViolationsMap.get(agency.getCode());
        }
        if (invoiced == null) {
            invoiced = new InvoicedViolationsDTO(agency);
            this.invoicedViolationsMap.put(agency.getCode(), invoiced);
        }
        return invoiced;
    }

    /**
     * Add the uninvoiced violations to this Violator.
     * Note: Invoiced violations should be added on an Invoice and not here.
     * If uninvoiced violations already exist for the agency, this call will replace them.
     * @param violations the uninvoiced violations to add
     * @see #addInvoices(Collection)
     */
    public void setViolations(UninvoicedViolationsDTO violations) {
        if (violations != null) {
            if (this.uninvoicedViolationsMap == null)
                this.uninvoicedViolationsMap = new TreeMap<String,UninvoicedViolationsDTO>();
            this.uninvoicedViolationsMap.put(violations.getAgency().getCode(), violations);
        }
    }

    /**
     * @deprecated use {@link #addViolation(AgencyEnum, ViolationDTO)}
     * @param agency The agency issuing the specified invoice
     * @param violation the uninvoiced violation to add
     */
    @Deprecated
    public void addViolation(String agency, ViolationDTO violation) {
        addViolation(AgencyEnum.valueOf(agency), violation);
    }
    /**
     * Add an uninvoiced violation to this Violator.
     * Note: Invoiced violations should be added on an Invoice and not here.
     * @param agency The agency issuing the specified invoice
     * @param violation the uninvoiced violation to add
     * @return <tt>true</tt> if the violation is added as a result of the call
     * @see #add(InvoiceDTO)
     */
    public boolean addViolation(AgencyEnum agency, ViolationDTO violation) {
    	return getUninvoicedViolationsWithCreate(agency).addViolation(violation);
    }

    /**
     * Add the specified violations to this Violator.  All violations are
     * added to the specified agency.
     * @param agency The agency generating the specified violations
     * @param violations violations to add
     * @return <tt>true</tt> if a violation is added as a result of the call
     * @see #addViolation(AgencyEnum, ViolationDTO)
     */
	public boolean addViolations(AgencyEnum agency, Collection<ViolationDTO> violations) {
    	return getUninvoicedViolationsWithCreate(agency).addAllViolations(violations);
	}

    /**
     * Returns the uninvoiced violations for the specified agency.
     * Note: This method will <i>never</i> return <tt>null</tt> creating empty objects if necessary.
     * @param agency the agency generating the violations
     * @return the uninvoiced violations for the specified agency
     * @throws NullPointerException if the specified agency is <tt>null</tt>
     */
    protected UninvoicedViolationsDTO getUninvoicedViolationsWithCreate(AgencyEnum agency) {
        UninvoicedViolationsDTO uninvoicedViolations = null;
    	if (this.uninvoicedViolationsMap == null)
    		this.uninvoicedViolationsMap = new TreeMap<String,UninvoicedViolationsDTO>();
    	else
    		uninvoicedViolations = this.uninvoicedViolationsMap.get(agency);
    	if (uninvoicedViolations == null) {
    		uninvoicedViolations = new UninvoicedViolationsDTO(agency);
    		this.uninvoicedViolationsMap.put(agency.getCode(), uninvoicedViolations);
    	}
    	return uninvoicedViolations;
    }

    /**
     * Used in JSPs and due to XFire issues with Maps.
     */
    public Collection<InvoicedViolationsDTO> getInvoicedViolations() {
        return this.invoicedViolationsMap == null ? null : this.invoicedViolationsMap.values();
    }

    public InvoicedViolationsDTO getInvoicedViolations(String agency) {
        return getInvoicedViolations(AgencyEnum.valueOf(agency));
    }
    public InvoicedViolationsDTO getInvoicedViolations(AgencyEnum agency) {
        return (this.invoicedViolationsMap == null ? null : this.invoicedViolationsMap.get(agency));
    }

    /**
     * Sets the map of invoices by agency to the specified collection.
     * Note: the collection should contain no more than one set of invoiced
     * violations per agency (if it does, only one will be stored).
     * @param agencyInvoices collection of agency invoices (one per agency)
     * @deprecated Used only by XFIRE!
     * @see #add(InvoiceDTO)
     * @see #addInvoices(Collection)
     * @throws IllegalAccessError always
     */
    @Deprecated
    public void setInvoicedViolations(Collection<InvoicedViolationsDTO> agencyInvoices) throws IllegalAccessError {
        if (isCallerXFire()){
            if (agencyInvoices == null){
                this.invoicedViolationsMap = null;
            } else {
                this.invoicedViolationsMap = new TreeMap<String, InvoicedViolationsDTO>();
                for (InvoicedViolationsDTO dto : agencyInvoices) {
                    addInvoices(dto.getInvoices());
                    this.invoicedViolationsMap.put(dto.getAgency().getCode(), dto);
                }
            }
        } else {
            throw new IllegalAccessError("setInvoicedViolations only supported for XFire deserialization");
        }
    }

    /**
     * Used in JSPs and due to XFire issues with Maps.
     */
    public Collection<UninvoicedViolationsDTO> getUninvoicedViolations(){
        return this.uninvoicedViolationsMap == null ? null : this.uninvoicedViolationsMap.values();
    }
    public UninvoicedViolationsDTO getUninvoicedViolations(AgencyEnum agency) {
        return (this.uninvoicedViolationsMap == null ? null : this.uninvoicedViolationsMap.get(agency));
    }
    /**
     * Sets the map of uninvoiced violations by agency to the specified collection.
     * Note: the collection should contain no more than one uninvoiced
     * violations object per agency (if it does, only one will be stored).
     * @param agencyViolations collection of uninvoiced violations objects (one per agency)
     * @deprecated Used only by XFIRE!
     * @see #addViolation(AgencyEnum, ViolationDTO)
     * @throws IllegalAccessError always
     */
    public void setUninvoicedViolations(Collection<UninvoicedViolationsDTO> agencyViolations) throws IllegalAccessError {
        if (isCallerXFire()){
            if (agencyViolations == null){
                this.uninvoicedViolationsMap = null;
            } else {
                this.uninvoicedViolationsMap = new TreeMap<String, UninvoicedViolationsDTO>();
                for (UninvoicedViolationsDTO dto : agencyViolations) {
                    this.uninvoicedViolationsMap.put(dto.getAgency().getCode(), dto);
                }
            }
        } else {
            throw new IllegalAccessError("setInvoicedViolations only supported for XFire deserialization");
        }
    }


    public Collection<InvoiceDTO> getInvoices()
    {
    	return invoicesById != null ? invoicesById.values() : null;
    }


    public void setInvoices(Collection<InvoiceDTO> values)
    {
    	for (InvoiceDTO invoice : values)
    	{
    		if (invoicesById!=null && !this.invoicesById.containsKey(invoice.getKey()))
    		{
    			add(invoice);
    		}
    	}

    }

    public List<InvoiceDTO> getInvoices(String agency) {
        return agency == null ? getAllInvoices() : getInvoices(AgencyEnum.valueOf(agency));
    }
    /**
     * Returns the collection of invoices associated with the specified agency.
     * @param agency agency issuing the invoices.  If <tt>null</tt>, returns all invoices for this violator.
     * @return collection of invoices
     * @see #getAllInvoices()
     */
    public List<InvoiceDTO> getInvoices(AgencyEnum agency) {
        if (agency != null){
            InvoicedViolationsDTO invoiced = getInvoicedViolations(agency);
            return (invoiced == null ? null : invoiced.getInvoices());
        }//else
        return getAllInvoices();
    }
    /**
     * Returns sorted list of all invoices grouped by agency for this violator.
     * Note: this method will never return <tt>null</tt>
     * @return all invoices from all agencies
     */
    public List<InvoiceDTO> getAllInvoices() {
        List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();
        if (this.invoicedViolationsMap != null) {
            for (InvoicedViolationsDTO invoiced : this.invoicedViolationsMap.values()) {
                if (invoiced != null) {
                    invoices.addAll(invoiced.getInvoices());
                }
            }
        }
        return invoices;
    }


    public void setAllInvoices(List<InvoiceDTO> values)
    {
    	// Do nothing
    }

    /**
     * @see #getInvoiceById(String)
     */
    public InvoiceDTO lookup(String key, String className) {
        if (InvoiceDTO.class.getName().equals(className)) {
            return getInvoiceById(key);
        }
        return null;
    }

    /**
     * Gets the invoiced by "hashed" id.
     * @param id The id in the format &ltAgencyCode&gt&ltInvoiceId&gt (e.g. HCTRA12345678)
     * @return Invoice corresponding to the Agency & Invoice ID.
     * @see InvoiceDTO#getKey()
     */
    public InvoiceDTO getInvoiceById(final String id) {
    	if (this.invoicesById !=null)
    	{
    		return this.invoicesById.get(id);
    	}
    	return null;
    }

    /**
     * Gets the invoice by id.
     * @param agency The agency the invoice belongs to.
     * @param id The invoice id (e.g. 12345678)
     * @return Invoice corresponding to the Agency & Invoice ID.
     * @see AgencyEnum#getCode()
     * @see InvoiceDTO#getKey()
     */
    public InvoiceDTO getInvoiceById(final AgencyEnum agency, final String id) {
        return getInvoiceById(agency.getCode() + id);
    }

    /**
     * Adds the violations on this violator to the shopping cart.
     * @see #invoicedViolationsMap
     * @see #uninvoicedViolationsMap
     * @see ShoppingCart#addAll(Collection)
     * @see #getShoppingCart()
     * @return the shopping cart
     */
    public ShoppingCart updateShoppingCart() {
    	logger.debug("Adding to Shopping Cart...");
        if (this.invoicedViolationsMap != null) {
            for (InvoicedViolationsDTO invoiced : this.invoicedViolationsMap.values()) {
                if (invoiced != null){
                    this.shoppingCart.addAll(invoiced.getInvoices());
                }
            }
        }
    	if (this.uninvoicedViolationsMap != null) {
    		this.shoppingCart.addAll(this.uninvoicedViolationsMap.values());
    	}
    	this.shoppingCart.cleanup();
		this.shoppingCart.calculateTotals();
		if (logger.isTraceEnabled())
			logger.trace("Updated cart: " + this.shoppingCart);
		return this.shoppingCart;
	}

    /**
     * Return the shopping cart associated with this violator.
     * @return the shopping cart
     */
    public ShoppingCart getShoppingCart() {
    	return this.shoppingCart;
    }

    /**
     * For XFire deserialization only.
     * @param cart cannot be set here
     * @deprecated Used only by XFIRE!
     * @throws IllegalAccessError always
     */
    @Deprecated
    public void setShoppingCart(final ShoppingCart cart) throws IllegalAccessError {
        if (isCallerXFire()){
            this.shoppingCart = cart;
        } else {
            throw new IllegalAccessError("setShoppingCart() cannot be called explicitly.");
        }
    }

    /**
     * Returns all invoices and uninvoiced violations of the specified type (converted to an agency).
     * @see CartItem.ItemType#getAgency()
     */
    public Collection<CartItem> getBin(CartItem.ItemType type) {
    	AgencyEnum agency = type.getAgency();
    	return this.getBin(agency);
    }

    /**
     * Returns all invoices and uninvoiced violations for the specified agency.
     * @param agency the agency issuing the violations
     * @return all invoices and uninvoiced violations for the specified agency
     */
    private Collection<CartItem> getBin(AgencyEnum agency) {
		Collection<CartItem> bin = null;
    	if (agency != null) {
    		bin = new ArrayList<CartItem>();
    		if (this.invoicedViolationsMap != null) {
    			InvoicedViolationsDTO iv = this.invoicedViolationsMap.get(agency.getCode());
    			if (iv != null) {
    				bin.addAll(iv.getInvoices());
    			}
    		}
    		if (this.uninvoicedViolationsMap != null) {
    			UninvoicedViolationsDTO uv = this.uninvoicedViolationsMap.get(agency.getCode());
    			if (uv != null) {
    				bin.add(uv);
    			}
    		}
    	}
    	return bin;
	}

    /**
     * Returns all invoices and uninvoiced violations grouped by agency.
     * @see #getBin(AgencyEnum)
     */
    @IgnoreProperty
    public Collection<Collection<CartItem>> getBins() {
		Collection<Collection<CartItem>> bins = new ArrayList<Collection<CartItem>>();
		for (AgencyEnum agency : AgencyEnum.values()) {
			bins.add(this.getBin(agency));
		}
		return bins;
	}

    /**
     * Calculate the preliminary totals (only applies to invoices) and cache them locally.
     * @return this object instance
     * @see InvoicedViolationsDTO#calculatePreliminaryTotals()
     * @see #amountDue
     * @see #pastPayments
     * @see #currentAmountDue
     * @see #adjustmentEligible
     * @see #waiverEligible
     */
    public ViolatorDTO calculatePreliminaryTotals() {
        this.amountDue = 0;
        this.pastPayments = 0;
        this.currentAmountDue = 0;
        this.waiverEligible = false;
        this.adjustmentEligible = false;
        if (this.invoicedViolationsMap != null) {
            for (InvoicedViolationsDTO invoiced : this.invoicedViolationsMap.values()) {
                invoiced.calculatePreliminaryTotals();
                this.amountDue += invoiced.getAmountDue();
                this.pastPayments += invoiced.getPastPayments();
                this.currentAmountDue += invoiced.getCurrentAmountDue();
                this.adjustmentEligible = (this.adjustmentEligible || invoiced.isAdjustmentEligible());
                this.waiverEligible = (this.waiverEligible || invoiced.isWaiverEligible());
            }
        }
        return this;
    }

    /**
     * Calculate the final totals (after payments have been applied) and cache them locally.
     * @return this object instance
     * @see ViolationsContainer#calculateTotals()
     * @see #discount
     * @see #payment
     * @see #uninvoicedPayment
     * @see #getRemainingBalance()
     * @see #calculatePreliminaryTotals()
     */
	public ViolatorDTO calculateTotals() {
	    this.discount = 0;
	    this.payment = 0;
	    this.uninvoicedPayment = 0;
	    if (this.invoicedViolationsMap != null) {
	        for (InvoicedViolationsDTO invoiced : this.invoicedViolationsMap.values()) {
	            invoiced.calculateTotals();
	            this.discount += invoiced.getDiscount();
	            this.payment += invoiced.getPayment();
	        }
	    }
        if (this.uninvoicedViolationsMap != null){
            for (UninvoicedViolationsDTO uninvoicedViolations : this.uninvoicedViolationsMap.values()) {
                this.uninvoicedPayment += uninvoicedViolations.calculateTotals().getPayment();
            }
        }
	    return this;
	}

	/**
	 * The remaining balance for this violator.
	 * Calculated as the current amount due minus discounts and payments.
	 * Note: discount and payment are from the shopping cart if present
	 * @see #calculateTotals()
	 * @see #currentAmountDue
	 * @see #shoppingCart
	 */

	public float getRemainingBalance() {
		float balance = this.currentAmountDue;
		if (shoppingCart.isEmpty())
			balance -= this.getDiscount() + this.getPayment();
		else
			balance -= shoppingCart.getDiscount() + shoppingCart.getPayment();
		return balance;
    }

	public void setRemainingBalance(float amount)
	{
		//DO NOTHING just for compliance
	}

    /**
     * Return the total number of invoices across agencies.
     * @return Total invoices
     * @see InvoicedViolationsDTO#size()
     * @see #getViolationContainerCount()
     */

    public int getInvoiceCount() {
    	int size = 0;
    	if (this.invoicedViolationsMap != null) {
            for (InvoicedViolationsDTO invoices : this.invoicedViolationsMap.values()) {
                size += invoices.size();
            }
    	}
        return size;
    }

	public void setInvoiceCount(int value)
	{
		//DO NOTHING just for compliance
	}

    /**
     * Gets the total number of violation containers (total invoices + total uninvoiced containers).
     * @return the total violation containers
     * @see #getInvoiceCount()
     */
    public int getViolationContainerCount() {
    	int size = getInvoiceCount();
    	if (this.uninvoicedViolationsMap != null) {
            for (UninvoicedViolationsDTO uninvoicedViolations : this.uninvoicedViolationsMap.values()) {
                size += (uninvoicedViolations.size() > 0 ? 1 : 0);
            }
    	}
    	return size;
    }

    public void setViolationContainerCount(int value)
    {
    	// DO NOTHING
    }

    /**
     * Returns a clone of this Violator which does not include the underlying violations maps.
     * @return a clone of this violator with empty invoice and violations maps
     * @throws CloneNotSupportedException if this object's parent class does not support the <tt>Cloneable</tt> interface.
     * @see #clone()
     * @see #getInvoicedViolationsMap()
     * @see #getUninvoicedViolationsMap()
     */
	public ViolatorDTO cloneWithoutViolations() throws CloneNotSupportedException {
		ViolatorDTO clone = (ViolatorDTO) super.clone();
		clone.invoicedViolationsMap = null;
		clone.uninvoicedViolationsMap = null;
		return clone;
    }


    @Override
    public String toString() {
        return toStringBuilder(LOG_MAX_INVOICE_COUNT, LOG_MAX_INVOICE_COUNT).toString();
    }
    /**
     * String representation of this object wrapped by a StringBuilder.
     * @return this object represented by a StringBuilder.
     */
    public StringBuilder toStringBuilder(int maxInvoiceCount, int maxViolationCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("ViolatorDTO[");
        sb.append("violatorId=").append(this.violatorId);
        sb.append(", firstName=").append(this.firstName);
        sb.append(", lastName=").append(this.lastName);
        sb.append(", licPlateNbr=").append(this.licPlateNbr);
        sb.append(", licPlateState=").append(this.licPlateState);
        sb.append(", lastUpdatedDate=").append(CoreDateUtil.getMediumDateTime(this.lastUpdatedDate));
//        sb.append(", strLastUpdatedDate=").append(strLastUpdatedDate);
        sb.append(", totalInvoiceDue=").append(this.amountDue);
        sb.append(", totalPastPayments=").append(this.pastPayments);
        sb.append(", totalAmtDue=").append(this.currentAmountDue);
        sb.append(", totalPayment=").append(this.getPayment());
        sb.append(", totalRemainingBalance=").append(this.getRemainingBalance());
        sb.append(", payPlanExists=").append(this.payPlanExists);
        sb.append(", invoices[0..").append(maxInvoiceCount).append("]=").append(StringUtil.toStringBuilder(this.invoicedViolationsMap, maxViolationCount));
        sb.append(", uninvoicedViolations[0..").append(maxInvoiceCount).append("]=").append(StringUtil.toStringBuilder(this.uninvoicedViolationsMap, maxViolationCount));
        sb.append(", accountLoginDTO=").append(this.accountLoginDTO);
        sb.append(", ").append(super.toString());
        sb.append("]");
        return sb;
    }


    // *** Standard getters & setters ***
    /**
     * @return the violatorId
     */
    public long getViolatorId() {
        return this.violatorId;
    }

    /**
     * @param violatorId the violatorId to set
     */
    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the licPlateNbr
     */
    public String getLicPlateNbr() {
        return this.licPlateNbr;
    }

    /**
     * @param licPlateNbr the licPlateNbr to set
     */
    public void setLicPlateNbr(String licPlateNbr) {
        this.licPlateNbr = licPlateNbr;
    }

    /**
     * @return the licPlateState
     */
    public String getLicPlateState() {
        return this.licPlateState;
    }

    /**
     * @param licPlateState the licPlateState to set
     */
    public void setLicPlateState(String licPlateState) {
        this.licPlateState = licPlateState;
    }

    /**
     * @return the lastUpdatedDate
     */
    public Calendar getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    /**
     * @param lastUpdatedDate the lastUpdatedDate to set
     */
    public void setLastUpdatedDate(Calendar lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * @return the totalInvoiceDue
     */
    @IgnoreProperty
    public float getAmountDue() {
        return this.amountDue;
    }

    /**
     * @return the totalPastPayments
     */
    @IgnoreProperty
    public float getPastPayments() {
        return this.pastPayments;
    }

    /**
     * @return the totalAmtDue
     */
    @IgnoreProperty
    public float getCurrentAmountDue() {
        return this.currentAmountDue;
    }

    /**
     * @return the totalPayment
     */
    @IgnoreProperty
    public float getPayment() {
        return this.payment;
    }

    /**
     * @return the payPlanExists
     */
    public boolean isPayPlanExists() {
        return this.payPlanExists;
    }

    /**
     * @param payPlanExists the payPlanExists to set
     */
    public void setPayPlanExists(boolean payPlanExists) {
        this.payPlanExists = payPlanExists;
    }

    /**
     * @return the accountLoginDTO
     */
    public AccountLoginDTO getAccountLoginDTO() {
        return this.accountLoginDTO;
    }

    /**
     * @param accountLoginDTO the accountLoginDTO to set
     */
    public void setAccountLoginDTO(AccountLoginDTO accountLoginDTO) {
        this.accountLoginDTO = accountLoginDTO;
    }

	public AccountDTO getAccount() {
		return this.account;
	}

	public void setAccount(AccountDTO a) {
		this.account = a;
	}

	@IgnoreProperty
	public boolean isWaiverEligible() {
		return this.waiverEligible;
	}

	@IgnoreProperty
	public boolean isAdjustmentEligible() {
		return this.adjustmentEligible;
	}

	public void setPayWithAccount(Boolean payWithAccount) {
		this.payWithAccount = payWithAccount;
	}

	public Boolean getPayWithAccount() {
		return this.payWithAccount;
	}

	@IgnoreProperty
	public float getDiscount() {
		return this.discount;
	}

	public Map<String, InvoicedViolationsDTO> getInvoicedViolationsMap() {
		return invoicedViolationsMap;
	}

	public Map<String, UninvoicedViolationsDTO> getUninvoicedViolationsMap() {
		return uninvoicedViolationsMap;
	}

	public void setInvoicedViolationsMap(
			Map<String, InvoicedViolationsDTO> invoicedViolationsMap) {
		if (this.invoicedViolationsMap !=null && this.invoicedViolationsMap.size()==0)
		{
			this.invoicedViolationsMap = invoicedViolationsMap;
		}
	}

	public void setUninvoicedViolationsMap(
			Map<String, UninvoicedViolationsDTO> uninvoicedViolationsMap) {
		if (this.uninvoicedViolationsMap !=null && this.uninvoicedViolationsMap.size()==0)
		{
			this.uninvoicedViolationsMap = uninvoicedViolationsMap;
		}
	}



}
