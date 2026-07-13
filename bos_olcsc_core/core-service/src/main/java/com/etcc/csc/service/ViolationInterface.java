package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Contains violation-specific methods.
 */
public interface ViolationInterface extends BusinessObjectInterface {
	/**
	 * Logs a violator into the system.
	 * 
	 * @param sessionId
	 * @param ipAddress
	 * @param userEnvDto
	 * @param invoiceId
	 * @param collectionsId
	 * @param licPlate
	 * @param licState
	 * @return Returns an AccountLoginDTO populated with the user's account id,
	 *         dbSessionId, and other info.
	 * @throws EtccException
	 */
	AccountLoginDTO loginViolator(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String collectionsId,
			String licPlate, String licState) throws EtccException;

	/**
	 * Retrieves a violator's invoiced and uninvoicedViolations.
	 * 
	 * @param acctLoginDto
	 * @param invoiceId
	 * @param collectionsId
	 * @param licPlate
	 * @param licState
	 * @return Returns ViolatorDTO containing the violator's invoiced and
	 *         uninvoiced violations.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	ViolatorDTO getViolations(AccountLoginDTO acctLoginDto, String invoiceId,
			String collectionsId, String licPlate, String licState)
			throws EtccException, EtccSecurityException;

	/**
	 * Logs a ZipCash invoice or account to the system.
	 * 
	 * @param sessionId
	 * @param ipAddress
	 * @param userEnvDto
	 * @param invoiceId
	 * @param accountId
	 * @param licPlate
	 * @param licState
	 * @return Returns AccountLoginDTO which contains the user's login info.
	 * @throws EtccException
	 */
	AccountLoginDTO loginZipCash(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String accountId,
			String licPlate, String licState) throws EtccException;
}
