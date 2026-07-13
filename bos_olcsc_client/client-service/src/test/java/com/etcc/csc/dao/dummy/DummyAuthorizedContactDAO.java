/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.sql.SQLException;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AuthorizedContactDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AuthorizedContactFactory;

/**
 * @author Milosh Boroyevich
 */
public class DummyAuthorizedContactDAO extends AuthorizedContactDAO {
	@Override
	protected ResultDTO changeAuthContacts(AccountLoginDTO accountLogin,
			AuthorizedContactDTO[] authorizedContacts, String acctPassword)
			throws SQLException, EtccSecurityException, EtccException {
		return null;
	}

	@Override
	protected AuthorizedContactDTO[] retrieveAuthContacts(AccountLoginDTO accountLogin)
			throws SQLException, EtccSecurityException, EtccException {
		return AuthorizedContactFactory.getAuthorizedContacts();
	}
}
