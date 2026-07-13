package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

/**
 * Methods to manage the tagAuthority.
 */
public interface TagAuthorityInterface extends BusinessObjectInterface {

	/**
	 * Returns a collection of tag authorities.
	 * 
	 * @return
	 */
	Collection getTagAuthorities() throws EtccException, EtccSecurityException;

	public String getTagApplicationAgreement(String lang) throws EtccException;
}
