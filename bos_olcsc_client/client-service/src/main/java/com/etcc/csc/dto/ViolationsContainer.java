/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

import com.etcc.csc.cart.CartDetail;
import com.etcc.csc.cart.CartItem;
import com.etcc.csc.common.AgencyEnum;

/**
 * Common methods for violations/invoices containers.
 * @author Milosh Boroyevich
 */
public interface ViolationsContainer extends Serializable, CartDetail {
    /**
     * Calculate totals and cache them locally.
     * Note: the implementation of this method may calculate all of the totals or just a subset.
     * @return an instance of the implementing object
     */
    public ViolationsContainer calculateTotals();

    /**
     * Returns total items in container.
     * Same as <tt>size()</tt> but available as a getter.
     * @return total items in container
     * @see #size()
     */
    public int getTotalItems();

    /**
     * Returns total items in container.
     * @return total items in container
     * @see #getTotalItems()
     */
    public int size();

    /**
     * Returns the issuing agency.
     * @return the agency
     */
    public AgencyEnum getAgency();

    /**
     * Returns the type of cart items in this container.
     * @return the type of cart items
     * @see CartItem#getItemType()
     */
    public CartItem.ItemType getItemType();
}
