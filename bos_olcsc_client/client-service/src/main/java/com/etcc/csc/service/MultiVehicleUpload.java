/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.MultiVehicleUploadDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;

/**
 * Service Implementation for Multiple Vehicle Upload. Based on the original version from OLCSCService.
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/MultiVehicleUpload")
@StatelessDeployment(transactionTimeout=240)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "MultiVehicleUpload", targetNamespace = "http://ws.csc.etcc.com/MultiVehicleUpload")
public class MultiVehicleUpload implements MultiVehicleUploadInterface {

    //@WebMethod(operationName = "validateMultipleVehicles", action = "urn:validateMultipleVehicles")
    //@WebResult(name = "multiVehicleUploadDTO")
    public MultiVehicleUploadDTO validateMultipleVehicles(AccountLoginDTO acctLoginDto,
            MultiVehicleUploadDTO uploadRequest) throws EtccException, EtccSecurityException {
        return getDao().validateMultipleVehicles(acctLoginDto, uploadRequest);
    }

    //@WebMethod(operationName = "loadMultipleVehicles", action = "urn:loadMultipleVehicles")
    //@WebResult(name = "multiVehicleUploadDTO")
    public MultiVehicleUploadDTO loadMultipleVehicles(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
            throws EtccException, EtccSecurityException {
        return getDao().loadMultipleVehicles(acctLoginDto, uploadRequest);
    }
    
    public MultiVehicleUploadDTO loadMultipleVehiclesFile(AccountLoginDTO acctLoginDto, FileMultiVehicleUploadDTO uploadRequest)
            throws EtccException, EtccSecurityException {
        return getDao().loadMultipleVehiclesFile(acctLoginDto, uploadRequest);
    }

    private MultiVehicleUploadDAO getDao() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        return daoFactory.getDAO(MultiVehicleUploadDAO.class);
    }
}
