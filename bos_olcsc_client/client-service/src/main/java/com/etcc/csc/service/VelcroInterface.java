/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

import javax.ejb.Local;

/**
 * Contains methods for managing the account's velcro.
 */
//Copied from com.etcc.csc.service.VelcroInterface in OLCSCService module.
@Local
public interface VelcroInterface extends ServiceInterface {

    /**
     * Retrieves the account's personal info prior to ordering velcro.
     * @return VelcroDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */
    VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) 
        throws EtccException, EtccSecurityException;

    /**
     * Submits the request for velcro.
     * @param acctLoginDto
     * @param qty The number of velcro to order.
     * @return Returns a collection of ErrorMessageDTOs, or <tt>null</tt> on success.
     * @throws EtccException
     * @throws EtccSecurityException Thrown when any security credential is 
     * invalid.
     */
    ResultDTO submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty) 
        throws EtccException, EtccSecurityException;
    
    /**
     * Returns the url that will generate a PDF velcro receipt.
     * @param acctLoginDto
     * @return the url that will generate a PDF velcro receipt
     * @throws EtccException
     * @throws EtccSecurityException
     */
    String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException;
}
