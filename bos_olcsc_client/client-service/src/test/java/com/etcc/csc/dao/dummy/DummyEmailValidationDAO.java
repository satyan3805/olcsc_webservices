/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.sql.SQLException;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.EmailValidationDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.EmailValidationFactory;

/**
 * @author Milosh Boroyevich
 */
public class DummyEmailValidationDAO extends EmailValidationDAO {

	@Override
	protected String getErrorMsg(Object error) throws SQLException {

		return null;
	}

	public ResultDTO generateEmailValidationMsg(AccountLoginDTO acctLogin)
			throws EtccSecurityException, EtccException {

		return null;
	}

	public ResultDTO setValidationDone(AccountLoginDTO acctLogin)
			throws EtccSecurityException, EtccException {

		return null;
	}

	public EmailValidationDataDTO validationData(AccountLoginDTO acctLogin)
			throws EtccSecurityException, EtccException {

		return null;
	}

	public ResultDTO validationStatus(AccountLoginDTO acctLogin)
			throws EtccSecurityException, EtccException {
	    return EmailValidationFactory.getStatus();
	}
}
