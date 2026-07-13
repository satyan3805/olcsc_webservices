/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Interface for an address container.  Implementing classes behave as a container for an <tt>Address</tt>.
 * @author Milosh Boroyevich
 */
public interface AddressContainer {
    /**
     * Returns the enclosed address.
     * @return the address
     */
	public Address getAddress();
}
