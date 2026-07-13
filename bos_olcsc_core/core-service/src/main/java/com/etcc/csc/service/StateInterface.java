package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

/**
 * Defines methods that need to be implemented by the State classes.
 */
public interface StateInterface extends BusinessObjectInterface {
	/**
	 * Retrieves the collection of states for the default country.
	 * 
	 * @return
	 */
	Collection getStates() throws EtccException, EtccSecurityException;

	Collection getStatesByCountry(String countryCode) throws EtccException,
			EtccSecurityException;
}