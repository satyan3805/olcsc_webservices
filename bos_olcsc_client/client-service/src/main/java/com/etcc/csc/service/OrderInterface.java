package com.etcc.csc.service;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Contains methods for managing the account's orders.
 */
@Local
public interface OrderInterface extends ServiceInterface {
    /**
     * Retrieves orders belonging to an account.
     * @return OrderDTO[]
     * @throws EtccException
     * @throws EtccSecurityException
     */
    Collection<OrderDTO> getOrders(AccountLoginDTO acctLoginDto, boolean pendingOnly) 
        throws EtccException, EtccSecurityException;
}
