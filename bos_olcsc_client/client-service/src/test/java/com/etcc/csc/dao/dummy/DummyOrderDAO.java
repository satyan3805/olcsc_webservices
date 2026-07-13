/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.service.OrderFactory;

/**
 * @author Milosh Boroyevich
 */
public class DummyOrderDAO extends OrderDAO {
	public Collection<OrderDTO> getOrders(AccountLoginDTO acctLoginDto,
			boolean pendingOnly) throws EtccException, EtccSecurityException {
		if (acctLoginDto == null)
			return null;
		else if (pendingOnly)
			return OrderFactory.getPendingOrders();
		else
			return OrderFactory.getAllOrders();
	}
}
