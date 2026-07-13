/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.InvoiceDTO;

/**
 * Shopping cart container. This is the container for items that would be stored in the Shopping cart.
 * Sorts the items by {@link CartItem.ItemType}.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class ShoppingCart extends BaseDTO implements CartSummary, Iterable<CartList> {
    /**
     * Unique ID for Serialization with version.
     */
    private static final long serialVersionUID = -8263363777776254332L;

    private static final Logger logger = Logger.getLogger(ShoppingCart.class);

    /**
     * Ordered map of cart items grouped by item type.
     * Bins are ordered naturally according to the ordering of the item type enumeration. 
     */
    protected TreeMap<CartItem.ItemType, CartList> lists = new TreeMap<CartItem.ItemType, CartList>();
    
    // transient summary values
    private float amountDue;
    private float pastPayments;
    private float currentAmountDue;
    private float discount;
    private float payment;
    private float remainingBalance;

    /**
     * Return whether this cart is empty.
     * @return <tt>true</tt> if this cart is empty
     * @see #clear()
     * @see TreeMap#isEmpty()
     */
    @IgnoreProperty
    public boolean isEmpty() {
    	return this.lists.isEmpty();
	}

    /**
     * Clears the contents of this shopping cart making it empty.
     * @return <tt>true</tt> if this cart is modified as a result of the call
     * @see TreeMap#clear()
     * @see #isEmpty()
     */
	public boolean clear() {
		if (this.lists.isEmpty())
			return false;
		this.lists.clear();
		return true;
	}

    /**
     * Clear the payment amounts for all failed bins.
     * @return <tt>true</tt> if the contents of this shopping cart are modified
     * @see CartList#clearFailedPayments()
     */
    public boolean clearFailedPayments() {
		// once true, modified should remain true
    	boolean modified = false;
    	for (CartList bin : this.lists.values())
    		modified = bin.clearFailedPayments() || modified;
    	return modified;
    }

    /**
     * Clear the confirmation code on all bins by setting to <tt>null</tt>.
     * @return <tt>true</tt> if the contents of this shopping cart are modified
     * @see CartList#setConfirmationCode(String)
     */
    public boolean clearConfirmationCodes() {
		// once true, modified should remain true
    	boolean modified = false;
    	for (CartList bin : this.lists.values()) {
    		if (bin.getConfirmationCode() != null) {
    			modified = true;
    			bin.setConfirmationCode(null);
    		}
    	}
    	return modified;
	}

	/**
	 * Returns the non-<tt>null</tt> confirmation codes.
	 * @return the confirmation codes
	 * @see CartList#getConfirmationCode()
	 */
    @IgnoreProperty
	public List<String> getConfirmationCodes() {
		int size = this.lists.size();
		List<String> confirmationCodes = new ArrayList<String>(size);
		for (CartList bin : this.lists.values()) {
			String code = bin.getConfirmationCode();
			if (code != null)
				confirmationCodes.add(code);
		}
		return confirmationCodes;
	}

    /**
     * Add a collection of cart items to the appropriate Shopping cart "Bin(s)".
     * @param items collection of items to add.
     * @return a reference to this shopping cart
     * @see #add(CartItem)
     * @author Milosh Boroyevich
     */
    public ShoppingCart addAll(final Collection<? extends CartItem> items) {
        if (items != null) {
            for (CartItem item : items) {
                add(item);
            }
        }
        return this;
    }

    /**
     * Add an Item to the appropriate Shopping cart "Bin".
     * @param item The item to add to the "bin".
     * @return a reference to this shopping cart
     * @see CartItem#getItemType()
     */
    public ShoppingCart add(final CartItem item) {
        CartItem.ItemType type = item.getItemType();
        CartList list = this.lists.get(type);
        if (list == null) {
            list = new CartList(type);
            this.lists.put(type, list);
        }
        if (list.add(item) && logger.isDebugEnabled())
        	logger.debug("Added item: " + item);
        return this;
    }

    /**
     * Clean up the shopping cart by cleaning the bins.
     * Note: an item can be modified after addition to a bin, making it
     * possible for the item to fail bin addition criteria afterwards.
     * @return a reference to this shopping cart
     * @see CartList#cleanup()
     * @author Milosh Boroyevich
     */
	public ShoppingCart cleanup() {
		for (CartList bin : this.lists.values()) {
			bin.cleanup();
		}
		return this;
	}

    @IgnoreProperty
    public Collection<CartList> getBins() {
        return this.lists.values();
    }

    @IgnoreProperty
    public CartList getBin(final CartItem.ItemType binType) {
        return this.lists.get(binType);
    }

    /**
     * @return bins wrapped in the XFire-compatible array of CartList DTOs
     * @throws IllegalAccessError always
     * @deprecated get bins using <tt>getBins</tt>
     * @see #getBins()
     */
    @Deprecated
    public CartList.WsDTO[] getXbins() {
        if (!isCallerXFire()) {
            throw new IllegalAccessError("Bins cannot be read as an array.");
        }
        if (this.lists != null && this.lists.size() > 0 ) {
            final Collection<CartList> bins = getBins();
            CartList.WsDTO[] wsdto = new CartList.WsDTO[bins.size()];
            int i = 0;
            for (CartList cartList : bins) {
                wsdto[i++] = cartList.toWsDto();
            }
            return wsdto;
        }
        return null;
    }

    /**
     * Note: This method does <i>not</i> set the cart items, as they must be
     * set in a context that also has access to the items on the violator.
     * @param bins cannot be set here
     * @throws IllegalAccessError always
     * @deprecated add bin items using <tt>addAll</tt>
     * @see #addAll(Collection)
     * @see #add(CartItem)
     */
    @Deprecated
    public void setXbins(final CartList.WsDTO[] bins) throws IllegalAccessError {
        if (!isCallerXFire()) {
            throw new IllegalAccessError("Bins cannot be set.");
        }
        if (bins != null && bins.length > 0) {
            for (CartList.WsDTO wsDTO : bins) {
                final CartItem.ItemType type = wsDTO.getType();
                CartList bin = new CartList(wsDTO);
                this.lists.put(type, bin);
            }
        }
    }

    /**
     * Initialize the cart items in all the bins using the specified locator
     * and web service proxy objects present on each bin.
     * Note: the proxy objects are used to populate the actual cart items before being added.
     * @param locator cart item locator used to lookup the actual cart items
     * @return <tt>true</tt> if the contents of this shopping cart changed as a result of the call
     * @throws NullPointerException if the WebService proxy wrapper is <tt>null</tt>
     * @see CartList#getWsDto()
     * @see CartList#forceAdd(CartItem)
     * @see CartItemLocator#lookup(String, String)
     */
    public boolean initCartItems(CartItemLocator locator) {
        boolean modified = false;
        for (CartList bin : this.getBins()) {
            for (CartItem itemProxy : bin.getWsDto().getItems()) {
                CartItem cartItem = locator.lookup(itemProxy.getKey(), itemProxy.getClassName());
                if (cartItem != null) {
                        try {
                            PropertyUtils.copyProperties(cartItem, itemProxy);
                        } catch (Exception e) {
                            logger.error("Error copying properties" + e, e);
                        }
                    modified = bin.forceAdd(cartItem) || modified;
                } else {
                    logger.error("Cart item lookup returned null.");
                    logger.error("WebService Proxy: " + itemProxy);
                    logger.error("Locator: " + locator);
                }
            }
        }
        if (modified)
            calculateTotals();
        return modified;
    }

    /**
     * @param bins cannot be set here
     * @throws IllegalAccessError always
     * @deprecated add bin items using <tt>addAll</tt>
     * @see #addAll(Collection)
     * @see #add(CartItem)
     */
    @Deprecated
    //BUG: Right now, XFire is sending uninitialized Invoices.
    public void setBins(Collection<? extends CartList> bins) throws IllegalAccessError {
        if (isCallerXFire()) {
        	if (bins != null) {
    			TreeMap<CartItem.ItemType, CartList> binMap = new TreeMap<CartItem.ItemType, CartList>();
        		for (CartList bin : bins) {
        			if (bin != null && !bin.isEmpty()) {
        				CartItem.ItemType type = bin.getItemType();
        				binMap.put(type, new CartList(bin));
        			}
        		}
//        		for (Collection<? extends CartItem> bin : bins) {
//        			if (bin != null && !bin.isEmpty()) {
//        				CartItem.ItemType type = bin.iterator().next().getItemType();
//        				binMap.put(type, new CartList(type, bin));
//        			}
//        		}
        		this.lists = binMap;
        	}
        } else {
        	throw new IllegalAccessError("Bins cannot be set.");
        }
    }

    /**
     * Gets a List of Lists of {@link CartItem}, sorted by {@link CartItem.ItemType}
     */
    public Iterator<CartList> iterator() {
        return this.lists.values().iterator();
//        // Need to return items in the same order as the enum.
//        final List<CartList> list = new ArrayList<CartList>(this.lists.size());
//        for (CartItem.ItemType type : CartItem.ItemType.values()) {
//            list.add(this.lists.get(type));
//        }
//        return list.listIterator();
//        // end iterator
    }

//    /**
//     * Returns a list of all uninvoiced violations in this Shopping Cart.
//     * @return List of all Shopping Cart violations.
//     * @see AgencyEnum
//     * @see CartList
//     */
//    public List<ViolationDTO> getAllViolations() {
//        List<ViolationDTO> list = new ArrayList<ViolationDTO>();
//        for (AgencyEnum agency : AgencyEnum.values()) {
//        	CartList bin = this.lists.get(CartItem.ItemType.copy(agency));
//        	if (bin != null) {
//        		for (CartItem item : bin) {
//        			if (item.getClass() == UninvoicedViolationsDTO.class) {
//        				list.addAll(((UninvoicedViolationsDTO) item).getViolations());
//        			}
//        		}
//        	}
//        }
//        return list;
//    }

    /**
     * Returns a list of all invoices across all bins in this Shopping Cart.
     * @return List of all Shopping Cart invoices.
     * @see AgencyEnum
     * @see CartList
     */
    @IgnoreProperty
    public List<InvoiceDTO> getAllInvoices() {
        List<InvoiceDTO> list = new ArrayList<InvoiceDTO>();
        for (AgencyEnum agency : AgencyEnum.values()) {
        	CartList bin = this.lists.get(CartItem.ItemType.copy(agency));
        	if (bin != null) {
        		for (CartItem item : bin) {
        			if (item.getClass() == InvoiceDTO.class) {
        				list.add((InvoiceDTO) item);
        			}
        		}
        	}
        }
        return list;
    }

    /**
     * Calculate the cart's total amounts for all invoices present.
     * Note: this method will also process invoices with no payment amount.
     * Use {@link #cleanup()} to remove unpaid invoices.
     * @return the shopping cart.
     * @see #getAmountDue()
     * @see #getPastPayments()
     * @see #getCurrentAmountDue()
     * @see #getDiscount()
     * @see #getPayment()
     * @see #getRemainingBalance()
     */
    public ShoppingCart calculateTotals() {
        this.amountDue = 0;
        this.pastPayments = 0;
        this.currentAmountDue = 0;
        this.discount = 0;
        this.payment = 0;
        this.remainingBalance = 0;
        for (CartList cl : this.lists.values()) {
            for (CartItem item : cl) {
            	this.amountDue += item.getAmountDue();
            	this.pastPayments += item.getPastPayments();
            	this.currentAmountDue += item.getCurrentAmountDue();
            	this.discount += item.getDiscount();
            	this.payment += item.getPayment();
            	this.remainingBalance += item.getRemainingBalance();
            }
        }
        return this;
    }

    /**
     * @return a string representation of this shopping cart
     * @see #toStringBuilder()
     */
    @Override
	public String toString() {
		return toStringBuilder().toString();
	}

	/**
	 * Returns a string representation of this shopping cart. The string
	 * representation consists of the calculated totals ({@link #calculateTotals()}) and
	 * an ordered list of the bins, enclosed in square brackets ("[]").
	 * Bins are converted to strings as by {@link String#valueOf(Object)}.
     * @return a string representation of this cart list as a string builder
     * @see java.util.AbstractCollection#toString()
     */
	public StringBuilder toStringBuilder() {
    	StringBuilder sb = new StringBuilder(getClass().getName());
    	sb.append("=[");
    	sb.append("amountDue=").append(amountDue).append(", ");
    	sb.append("pastPayments=").append(pastPayments).append(", ");
    	sb.append("currentAmountDue=").append(currentAmountDue).append(", ");
    	sb.append("discount=").append(discount).append(", ");
    	sb.append("payment=").append(payment).append(", ");
    	sb.append("remainingBalance=").append(remainingBalance).append(", ");
    	sb.append("lists=").append(lists).append(", ");
    	sb.append(super.toString());
    	return sb.append(']');
	}

    @IgnoreProperty
    public float getDiscount() {
        return this.discount;
    }
    @IgnoreProperty
    public float getCurrentAmountDue() {
        return this.currentAmountDue;
    }
    @IgnoreProperty
    public float getAmountDue() {
        return this.amountDue;
    }
    @IgnoreProperty
    public float getPastPayments() {
        return this.pastPayments;
    }
    @IgnoreProperty
    public float getPayment() {
        return this.payment;
    }
    @IgnoreProperty
    public float getRemainingBalance() {
        return this.remainingBalance;
    }
}
