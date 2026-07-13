/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.cart;

/**
 * This is the interface that any object that can be added to the shopping cart must implement.
 * @see CartItem
 * @author Milosh Boroyevich
 */
public interface CartItemLocator {
    /**
     * Lookup and return a cart item given the specified id and class name.
     * @param key used to perform the lookup
     * @param className name of class of the object to look up
     * @return the desired cart item (if available)
     */
    public CartItem lookup(String key, java.lang.String className);
}
