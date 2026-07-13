/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Interface for Violation services.
 * This interface extension exists for use by web services which do not
 * support OOP, specifically inheritance (such as X-Fire).
 * @author Milosh Boroyevich
 */
@Local
public interface ViolationServiceInterface extends ViolationInterface {
    /**
     * Method exposed for web services which do not support OOP (such as X-Fire).
     * This method is only invoked by the delegate implementation on the service object.
     * @param violatorDTO
     * @param paymentMethod
     * @param ipAddress
     * @return the modified violator with update payment details
     * @throws EtccException
     * @throws EtccSecurityException
     * @see #makePayment(ViolatorDTO, com.etcc.csc.dto.AccountPaymentMethodDTO, String)
     */
    //ViolatorDTO makePaymentService(ViolatorDTO violatorDTO, AccountCreditCardDTO paymentMethod, String ipAddress) throws EtccException, EtccSecurityException;

    /**
     * Method exposed for web services which do not support OOP (such as X-Fire).
     * This method is only invoked by the delegate implementation on the service object.
     * @param violatorDTO
     * @param paymentMethod
     * @param ipAddress
     * @return the modified violator with update payment details
     * @throws EtccException
     * @throws EtccSecurityException
     * @see #makePayment(ViolatorDTO, com.etcc.csc.dto.AccountPaymentMethodDTO, String)
     */
   //ViolatorDTO makePaymentService(ViolatorDTO violatorDTO, AccountEFTDTO paymentMethod, String ipAddress) throws EtccException, EtccSecurityException;
}
