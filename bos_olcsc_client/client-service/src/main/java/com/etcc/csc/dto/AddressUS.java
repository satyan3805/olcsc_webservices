/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Interface for United States address fields.
 * Unused fields are expected to be <tt>null</tt> or empty strings.
 * @author Milosh Boroyevich
 */
public interface AddressUS {
	public static final String COUNTRY_CODE_USA = "USA";

    public String getName();

	public String getAddress1();

	public void setAddress1(String address1);

	public String getAddress2();

	public void setAddress2(String address2);

	public String getCity();

	public void setCity(String city);

	public String getState();

	public void setState(String state);

	public String getZip();

	public void setZip(String zip);

	public String getPlus4();

	public void setPlus4(String plus4);
}
