package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.VelcroDTO;

/**
 * Contains methods for managing the account's velcro.
 */
public interface VelcroInterface extends BusinessObjectInterface {
	/**
	 * Retrieves the account's personal info prior to ordering velcro.
	 * 
	 * @return VelcroDTO
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) throws EtccException,
			EtccSecurityException;

	/**
	 * Submits the request for velcro.
	 * 
	 * @param acctLoginDto
	 * @param qty
	 *            The number of velcro to order.
	 * @return Returns a collection of ErrorMessageDTOs. Null if successful.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 *             Thrown when any security credential is invalid.
	 */
	Collection submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty)
			throws EtccException, EtccSecurityException;

	/**
	 * Returns the url that will generate a PDF velcro receipt.
	 * 
	 * @param acctLoginDto
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException;

}
