/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.service.VelcroFactory;

/**
 * @author Milosh Boroyevich
 */
public class DummyVelcroDAO extends VelcroDAO {
	public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		if (acctLoginDto != null)
			return VelcroFactory.getVelcro();
		return null;
	}

	public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		if (acctLoginDto != null)
			return VelcroFactory.getVelcroUrl();
		return null;
	}

	public ResultDTO submitVelcroRequest(
			AccountLoginDTO acctLoginDto, int qty) throws EtccException,
			EtccSecurityException {
		if (acctLoginDto != null)
	        return new ResultDTO();
		// return result with errors
		return VelcroFactory.getSubmitVelcroError();
	}
}
