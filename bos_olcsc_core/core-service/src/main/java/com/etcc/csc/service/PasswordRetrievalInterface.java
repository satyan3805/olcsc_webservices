package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;

public interface PasswordRetrievalInterface extends BusinessObjectInterface {
	/**
	 * @param onlineAccessDTO
	 * @param ipAddress
	 * @return Returns OnlineAccessSetupDTO containing the user's online access
	 *         account info. null indicates that the account does not exist.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public OnlineAccessSetupDTO retrieveAccountInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto, String locale)
			throws EtccException, EtccSecurityException;

	/**
	 * @param sessionId
	 * @param ipAddress
	 * @param onlineAccessDTO
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public boolean validateSecurityAnswer(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException;

	/**
	 * @param sessionId
	 * @param ipAddress
	 * @param onlineAccessDTO
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public boolean addSecurityQuestionAnswer(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException;

	/**
	 * @param sessionId
	 * @param ipAddress
	 * @param onlineAccessDTO
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public boolean addEmailAddress(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException;

	/**
	 * @param acctLoginDTO
	 * @param pwd
	 * @return A collection of ErrorMessageDTO. Null or empty means the
	 *         operation is successful. These error messages are from the
	 *         validation process that are performed at the server.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public Collection changePassword(AccountLoginDTO acctLoginDTO,
			String oldPwd, String pwd) throws EtccException,
			EtccSecurityException;

	/**
	 * @param acctLoginDTO
	 * @param pwd
	 * @return A collection of ErrorMessageDTO. Null or empty means the
	 *         operation is successful. These error messages are from the
	 *         validation process that are performed at the server.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public Collection resetPassword(AccountLoginDTO acctLoginDTO, String pwd)
			throws EtccException, EtccSecurityException;
}
