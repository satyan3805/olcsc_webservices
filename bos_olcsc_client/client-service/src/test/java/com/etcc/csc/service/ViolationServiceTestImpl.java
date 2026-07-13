/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Test implementation of <tt>ViolationServiceInterface</tt>.
 * @author Milosh Boroyevich
 */
public class ViolationServiceTestImpl extends ViolationTestImpl implements ViolationServiceInterface {
	public ViolatorDTO makePaymentService(ViolatorDTO violatorDTO, AccountCreditCardDTO paymentMethod, String ipAddress)
			throws EtccException, EtccSecurityException {
		return makePayment(violatorDTO, paymentMethod, ipAddress);
	}

	public ViolatorDTO makePaymentService(ViolatorDTO violatorDTO, AccountEFTDTO paymentMethod, String ipAddress)
			throws EtccException, EtccSecurityException {
		return makePayment(violatorDTO, paymentMethod, ipAddress);
	}
}
