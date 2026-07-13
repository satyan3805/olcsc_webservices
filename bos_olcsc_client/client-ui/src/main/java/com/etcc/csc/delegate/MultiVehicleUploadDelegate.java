/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;
import com.etcc.csc.service.MultiVehicleUploadInterface;
import com.etcc.csc.service.ServiceFactory;

public class MultiVehicleUploadDelegate implements MultiVehicleUploadInterface {
    public MultiVehicleUploadDTO validateMultipleVehicles(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest) throws EtccException {
        return stub().validateMultipleVehicles(acctLoginDto, uploadRequest);
    }

    public MultiVehicleUploadDTO loadMultipleVehicles(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest) throws EtccException {
        return stub().loadMultipleVehicles(acctLoginDto, uploadRequest);
    }

    private MultiVehicleUploadInterface stub() {
        return ServiceFactory.getImplementation(MultiVehicleUploadInterface.class);
    }

	public MultiVehicleUploadDTO loadMultipleVehiclesFile(
			AccountLoginDTO acctLoginDto,
			FileMultiVehicleUploadDTO uploadRequest) throws EtccException,
			EtccSecurityException {
		 return stub().loadMultipleVehiclesFile(acctLoginDto, uploadRequest);
	}
}
