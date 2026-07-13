/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.service.CreditCardFactory;

/**
 * @author Stephen Davidson
 */
public class DummyCreditCardDAO extends CreditCardDAO {

    /** 
     * @see CreditCardDAO#loadCreditCardTypes()
     */
    @Override
    protected Collection<CreditCardDTO> loadCreditCardTypes() throws EtccException {
        return CreditCardFactory.getCreditCardTypes();
        //end loadCreditCardTypes
    }

    /** 
     * @see CreditCardDAO#getAccountCreditCard(AccountLoginDTO)
     */
    public AccountCreditCardDTO getAccountCreditCard(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {
        //return null;
        throw new RuntimeException("getAccountCreditCard not implemented yet.");
        //end getAccountCreditCard
    }

    /** 
     * @see CreditCardDAO#updateAccountCreditCard(AccountLoginDTO, AccountCreditCardDTO, boolean)
     */
    public PaymentResultDTO updateAccountCreditCard(AccountLoginDTO acctLoginDto,
            AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen) throws EtccException,
            EtccSecurityException {
        //return null;
        throw new RuntimeException("updateAccountCreditCard not implemented yet.");
        //end updateAccountCreditCard
    }
}
