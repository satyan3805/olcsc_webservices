/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.cart;

import java.util.Collection;

/**
 * A bin containing a collection of cart items of the same type.
 * @see CartItem
 * @see CartItem.ItemType
 * @see Collection
 * @author Milosh Boroyevich
 */
public interface CartBin extends Collection<CartItem> {
    /**
     * Returns the type of items stored in this bin.
     * @return the type of items stored in this bin
     */
    public CartItem.ItemType getItemType();
}
