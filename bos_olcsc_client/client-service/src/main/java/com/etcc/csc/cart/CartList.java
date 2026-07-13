/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.cart;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * Bin that holds shopping cart items of a given type.
 * The items are ordered and duplicates are not allowed.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 * @see CartItem
 * @see CartItem.ItemType
 * @see TreeSet
 * @see CartBin
 */
//This should be internal to Shopping Cart, but needs to be external for XFire usage.
public class CartList extends TreeSet<CartItem> implements CartBin {
    /**
     * Unique ID for Serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = 2930794913015495140L;

    private static final Logger logger = Logger.getLogger(CartList.class);


    /**
     * Wrapper for transferring a CartList over WebServices.
     * @author (task 488) Stephen Davidson
     */
    public static class WsDTO implements Serializable {
        private static final long serialVersionUID = 7713351124284445734L;

        /**
         * The types of Shopping Cart Items that this list/bin will hold.
         */
        private CartItem.ItemType type;

        /**
         * The confirmation code assigned when this bin is processed.
         */
        private String confirmationCode;

        private CartItem[] items;

        /**
         * Constructor.
         */
        public WsDTO() {
            super();
        }

        /**
         * Constructor to load a cart list into this X-Fire compatible wrapper.
         * @param bin the cart list to wrap
         */
        private WsDTO(CartList bin) {
            this();
            this.type = bin.type;
            this.confirmationCode = bin.confirmationCode;
            this.items = bin.toArray(new CartItem[bin.size()]);
        }

        /**
         * @return the type
         */
        public CartItem.ItemType getType() {
            return this.type;
        }

        /**
         * @param type the type to set
         */
        public void setType(CartItem.ItemType type) {
            this.type = type;
        }

        /**
         * @return the confirmationCode
         */
        public String getConfirmationCode() {
            return this.confirmationCode;
        }

        /**
         * @param confirmationCode the confirmationCode to set
         */
        public void setConfirmationCode(String confirmationCode) {
            this.confirmationCode = confirmationCode;
        }

        /**
         * @return the items
         */
        public CartItem[] getItems() {
            return this.items;
        }

        /**
         * @param items the items to set
         */
        public void setItems(CartItem[] items) {
            this.items = items;
        }
    }


    private final WsDTO wsDto;

    /**
     * The types of Shopping Cart Items that this list/bin will hold.
     */
    private final CartItem.ItemType type;

    /**
     * The confirmation code assigned when this bin is processed.
     */
    private String confirmationCode = null;

    /**
     * Default Constructor.
     * @param itemType The type of shopping cart items this list will hold.
     */
    public CartList(CartItem.ItemType itemType) {
        this.type = itemType;
        this.wsDto = null;
    }

    /**
     * Copy Constructor.
     * @param bin add the cart items from the bin to this cart list
     * @throws NullPointerException if bin is <tt>null</tt>
     */
    public CartList(CartList bin) throws NullPointerException {
        this(bin.getItemType(), bin.getConfirmationCode(), bin);
    }

    /**
     * Copy Constructor.
     * @param confirmationCode the confirmation code (<tt>null</tt> if none present)
     * @param bin add the cart items from the bin to this cart list
     * @throws NullPointerException if bin is <tt>null</tt>
     */
    public CartList(String confirmationCode, CartBin bin) throws NullPointerException {
        this(bin.getItemType(), confirmationCode, bin);
    }

    /**
     * Copy Constructor.
     * @param itemType The type of shopping cart items this list will hold
     * @param confirmationCode the confirmation code (<tt>null</tt> if none present)
     * @param bin add the cart items from the bin to this cart list
     * @throws NullPointerException if bin is <tt>null</tt>
     */
    public CartList(CartItem.ItemType itemType, String confirmationCode, Collection<? extends CartItem> bin) throws NullPointerException {
        this.type = itemType;
        this.confirmationCode = confirmationCode;
        this.addAll(bin);
        this.wsDto = null;
    }

    /**
     * Copy Constructor.
     * @param itemType The type of shopping cart items this list will hold
     * @param confirmationCode the confirmation code (<tt>null</tt> if none present)
     * @param bin add the cart items from the bin to this cart list
     * @throws NullPointerException if bin is <tt>null</tt>
     */
    public CartList(CartItem.ItemType itemType, String confirmationCode, CartItem[] bin) throws NullPointerException {
        this.type = itemType;
        this.confirmationCode = confirmationCode;
        for (CartItem cartItem : bin) {
            add(cartItem);
        }
        this.wsDto = null;
    }

    /**
     * @see #CartList(CartList)
     * @throws NullPointerException if wsDto is <tt>null</tt>
     */
    CartList(WsDTO wsDto) throws NullPointerException {
        this.type = wsDto.type;
        this.confirmationCode = wsDto.confirmationCode;
        this.wsDto = wsDto;
    }

//    /**
//     * @deprecated use constructor {@link #CartList(CartItem.ItemType)}.
//     * @throws IllegalAccessError always
//     * @see #CartList(CartItem.ItemType)
//     */
//    @Deprecated
//    public CartList() throws IllegalAccessError {
//        this.type = null;
//        this.wsDto = null;
//        if (!ShoppingCart.isCallerXFire()) {
//            throw new IllegalAccessError("Cart item type must be specified.");
//        }
//    }

    public CartItem.ItemType getItemType() {
        return this.type;
    }

//    /**
//     * @deprecated use constructor {@link #CartList(CartItem.ItemType)}.
//     * @param itemType cannot be set here
//     * @throws IllegalAccessError always
//     * @see #CartList(CartItem.ItemType)
//     */
//    @Deprecated
//    public void setItemType(CartItem.ItemType itemType) {
//        if (ShoppingCart.isCallerXFire()) {
//            this.type = itemType;
//        } else {
//            throw new IllegalAccessError("Cart Item Type cannot be set.");
//        }
//    }

    /**
     * Add the specified item to this cart list if it is valid.
     * @see #isValid(CartItem)
     */
    @Override
    public boolean add(CartItem o) {
        // only add the item if it's valid
        if (isValid(o))
            return super.add(o);
        return false;
    }

    /**
     * Force the addition of the specified element to this cart list.
     * Note: use this method only if circumventing validation is desired.
     * @param o element whose presence in this cart list is to be ensured
     * @return <tt>true</tt> if this cart list changed as a result of the call
     * @see #add(CartItem)
     * @see #isValid(CartItem)
     */
    public boolean forceAdd(CartItem o) {
        assert o.getItemType() == this.type : 
            "Type Mismatch: Attempt to add Item type [" + o.getItemType() + "] to list of type " + this.type;
        return super.add(o);
    }

    /**
     * Returns this cart list/bin as a collection of cart items.
     * @return this cart list/bin as a collection of cart items
     */
    public Collection<? extends CartItem> collection() {
        return this;
    }

    /**
     * Clear the payment amounts for all cart items in this bin if the
     * confirmation code is <tt>null</tt>.
     * @return <tt>true</tt> if the contents of this cart list/bin are modified
     * @see #getConfirmationCode()
     * @see CartItem#clearPayment()
     */
    public boolean clearFailedPayments() {
        boolean modified = false;
        if (this.confirmationCode == null) {
            logger.debug("Clearing payment amounts for bin: " + this.type);
            for (CartItem item : this) {
                // once true, modified should remain true
                modified = item.clearPayment() || modified;
            }
        }
        return modified;
    }

    /**
     * Clean this bin by removing invalid items.
     * Note: an item can be modified after addition to a bin, making it
     * possible for the item to fail bin validation afterwards.
     * @return <tt>true</tt> if the contents of this cart list/bin are modified
     * @see #isValid(CartItem)
     */
    public boolean cleanup() {
        boolean modified = false;
        Iterator<CartItem> i = iterator();
        while (i.hasNext()) {
            CartItem item = i.next();
            if (!isValid(item)) {
                if (logger.isDebugEnabled())
                    logger.debug("Removing item: " + item);
                i.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Validate the specified cart item to determine eligibility for
     * addition to this cart list.
     * Note: The item is valid if <code>payment > 0 or discount > 0</code>
     * and if the item type is the same as the list type.
     * @param item cart item to validate
     * @return <tt>true</tt> only if valid
     * @see CartItem#getPayment()
     * @see CartItem#getDiscount()
     * @see CartItem#getItemType()
     * @see #getItemType()
     */
    public boolean isValid(CartItem item) {
        assert item.getItemType() == this.type : 
            "Type Mismatch: Attempt to add Item type [" + item.getItemType() + "] to list of type " + this.type;
        // only add the item if there's a payment or discount applied
        return (item.getPayment() > 0 || item.getDiscount() > 0);
    }

    /**
     * @see ShoppingCart#getXbins()
     */
    //For use by Shopping cart when being serialized/deserialized via XFire.
    WsDTO toWsDto() {
        return new WsDTO(this);
    }

    /**
     * @see ShoppingCart#getXbins()
     */
    //For use by Shopping cart when being serialized/deserialized via XFire.
    WsDTO getWsDto() {
        return wsDto;
    }

    /**
     * @return a string representation of this cart list
     * @see #toStringBuilder()
     */
    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    /**
     * Returns a string representation of this cart list. The string
     * representation consists of the item type, confirmation code and
     * an ordered list of cart items, enclosed in square brackets ("[]").
     * Cart items are converted to strings as by {@link String#valueOf(Object)}.
     * @return a string representation of this cart list as a string builder
     * @see java.util.AbstractCollection#toString()
     */
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append("=[");
        sb.append("itemType=").append(this.type).append(", ");
        sb.append("confirmationCode=").append(this.confirmationCode).append(", ");
        sb.append(super.toString());
        return sb.append(']');
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }
}
