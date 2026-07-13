/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;

/**
 * Generates the return values for the methods needed for the UI Tests (and demos).
 * @author Milosh Boroyevich
 */
public class MultiVehicleUploadTestImpl implements MultiVehicleUploadInterface {
    private static final Logger logger = Logger.getLogger(MultiVehicleUploadTestImpl.class);

    public MultiVehicleUploadDTO loadMultipleVehicles(
            AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
            throws EtccException, EtccSecurityException {
        logger.debug("loadMultipleVehicles.");
        MultiVehicleDTO[] tags = uploadRequest.getMultiVehicleTags();
        int add = 0, inactivate = 0;
        /*if (tags != null) {
            for (MultiVehicleDTO tag : tags) {
                logger.debug(tag);
                switch (tag.getUploadAction()) {
                case ADD:
                    add++;
                    break;
                case INACTIVATE:
                    inactivate++;
                    break;
                }
            }
        }*/
        uploadRequest.setVehicleAddCount(add);
        uploadRequest.setVehicleInactivateCount(inactivate);
        logger.debug("loadMultipleVehicles=" + uploadRequest);
        return uploadRequest;
    }

    public MultiVehicleUploadDTO validateMultipleVehicles(
            AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
            throws EtccException, EtccSecurityException {
        logger.debug("validateMultipleVehicles=" + uploadRequest);
        return uploadRequest;
    }

	public MultiVehicleUploadDTO loadMultipleVehiclesFile(
			AccountLoginDTO acctLoginDto,
			FileMultiVehicleUploadDTO uploadRequest) throws EtccException,
			EtccSecurityException {
		  logger.debug("loadMultipleVehicles.");
	        MultiVehicleDTO[] tags = uploadRequest.getMultiVehicleTags();
	        int add = 0, inactivate = 0;
	        /*if (tags != null) {
	            for (MultiVehicleDTO tag : tags) {
	                logger.debug(tag);
	                switch (tag.getUploadAction()) {
	                case ADD:
	                    add++;
	                    break;
	                case INACTIVATE:
	                    inactivate++;
	                    break;
	                }
	            }
	        }*/
	        uploadRequest.setVehicleAddCount(add);
	        uploadRequest.setVehicleInactivateCount(inactivate);
	        logger.debug("loadMultipleVehicles=" + uploadRequest);
	        return uploadRequest;
	    }
}
