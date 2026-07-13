/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;

/**
 * API for MultiVehicleUpload.  From the original version in OLCSCService.
 */
@Local
public interface MultiVehicleUploadInterface extends ServiceInterface {

//    /**
//     * Returns boolean for whether or not multiple vehicle upload is allowed for account.
//     * @param acctLoginDto
//     * @return Returns a BaseDTO.
//     * @throws EtccException
//     * @throws EtccSecurityException
//     */
//    BaseDTO isMultiVehicleUploadAllowed(AccountLoginDTO acctLoginDto)
//        throws EtccException, EtccSecurityException;

    /**
     * Used to validate vehicles to upload, returns MultiVehicleTagDTO with updated upload attempt status
     * @param acctLoginDto
     * @param uploadRequest
     * @return Returns MultiVehicleUploadDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */
    MultiVehicleUploadDTO validateMultipleVehicles(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
        throws EtccException, EtccSecurityException;

    /**
     * Used to load multiple vehicles returns MultiVehicleUploadDTO with updated upload attempt status
     * @param acctLoginDto
     * @param uploadRequest
     * @return Returns MultiVehicleUploadDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */        
    MultiVehicleUploadDTO loadMultipleVehicles(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
        throws EtccException, EtccSecurityException;   
    
    
    /**
     * Used to load multiple vehicles returns MultiVehicleUploadDTO with updated upload attempt status
     * @param acctLoginDto
     * @param uploadRequest
     * @return Returns MultiVehicleUploadDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */        
    MultiVehicleUploadDTO loadMultipleVehiclesFile(AccountLoginDTO acctLoginDto, FileMultiVehicleUploadDTO uploadRequest)
        throws EtccException, EtccSecurityException;   
}
