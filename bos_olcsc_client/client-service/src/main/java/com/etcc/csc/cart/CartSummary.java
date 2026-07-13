/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.cart;

import java.util.Collection;

/**
 * This interface specifies the methods that a Shopping Cart or other
 * object must implement to present a summary of the contained items.
 * @see ShoppingCart
 * @see CartBin
 * @author Milosh Boroyevich
 */
public interface CartSummary extends CartDetail {
    /**
     * Gets all of the cart items of the specified type.
     * @param type The type of cart items to get.
     * @return Collection of cart items
     */
    public Collection<? extends CartItem> getBin(final CartItem.ItemType type);

    /**
     * Gets a collection of "bins" (collection of cart items).
     * @return Collection of collections of cart items
     */
    public Collection<? extends Collection<? extends CartItem>> getBins();
}
