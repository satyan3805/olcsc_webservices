package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SessionDTO;

/**
 * Methods for managing user session.
 */
public interface SessionInterface extends BusinessObjectInterface {
	/**
	 * Creates a database session for a user.
	 * 
	 * @param sessionDto
	 * @return A db-generated session id.
	 * @throws EtccException
	 */
	String makeSession(SessionDTO sessionDto) throws EtccException;

	/**
	 * Destroys the DB session for the given dbSessionId.
	 * 
	 * @param dbSessionId
	 * @return
	 * @throws EtccException
	 */
	void destroySession(String dbSessionId) throws EtccException;

	void updateSessionLanguage(AccountLoginDTO acctLoginDto, String lang)
			throws EtccException;
}
