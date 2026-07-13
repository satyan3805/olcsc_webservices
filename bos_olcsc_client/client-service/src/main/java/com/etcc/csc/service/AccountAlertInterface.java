/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * Retrieves the alerts on an Account.  Copied from OLCSCService.
 * @author mmiller
 * @author Milosh Boroyevich
 * @author Stephen Davidson
 */
@Local
public interface AccountAlertInterface extends ServiceInterface {

    /**
     * Retrieves the account summary alerts for the given account.
     * 
     * @param acctLoginDto
     * @return Returns a collection of AlertDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AlertDTO[] getAcctSummAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException;

    /**
     * Retrieves the vehicle info alerts for the given account.
     * 
     * @param acctLoginDto
     * @return Returns a collection of AlertDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AlertDTO[] getVehicleInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException;

    /**
     * Retrieves the contact info alerts for the given account.
     * 
     * @param acctLoginDto
     * @return Returns a collection of AlertDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AlertDTO[] getContactInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException;

    /**
     * Response for address cleanse alert.
     * 
     * @param acctLoginDto
     * @return Returns a BaseDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    ResultDTO addressCleanseAlertResponse(AccountLoginDTO acctLoginDto, String cleanseAlertResponse)
            throws EtccException, EtccSecurityException;
}
