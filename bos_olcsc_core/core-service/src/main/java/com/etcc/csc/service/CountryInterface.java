package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

public interface CountryInterface extends BusinessObjectInterface {
	Collection getCountry() throws EtccException, EtccSecurityException;

	String getDefaultCountryCode() throws EtccException;
}
