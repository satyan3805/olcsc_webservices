/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Interface for global address fields.
 * A United States address uses the fields: <tt>address1</tt>, <tt>address2</tt>,
 * <tt>city</tt>, <tt>state</tt>, <tt>zip</tt>, and <tt>plus4</tt>.
 * A non-US address uses the fields: <tt>address1</tt>, <tt>address2</tt>,
 * <tt>address3</tt>, <tt>address4</tt>, and <tt>country</tt>.
 * Unused fields are expected to be <tt>null</tt> or empty strings.
 * @author Milosh Boroyevich
 */
public interface Address extends AddressUS {
	public String getAddress3();

	public void setAddress3(String address3);

	public String getAddress4();

	public void setAddress4(String address4);

	public String getCountry();

	public void setCountry(String country);
}
