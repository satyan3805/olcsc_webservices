/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.dummy;

import java.sql.SQLException;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.MultiVehicleUploadDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;

/**
 * @author (task 488) Stephen Davidson
 */
public class DummyMultiVehicleUploadDAO extends MultiVehicleUploadDAO {
    @Override
    public boolean isMultiVehicleUploadAllowed(AccountLoginDTO accountLogin)
            throws EtccException, EtccSecurityException {
        return false;
    }

    public MultiVehicleUploadDTO loadMultipleVehicles(AccountLoginDTO acctLoginDto,
            MultiVehicleUploadDTO uploadRequest) throws EtccException, EtccSecurityException {
        return uploadRequest;
    }

    public MultiVehicleUploadDTO validateMultipleVehicles(AccountLoginDTO acctLoginDto,
            MultiVehicleUploadDTO uploadRequest) throws EtccException, EtccSecurityException {
        return uploadRequest;
    }

    @Override
    protected MultiVehicleDTO convertVehicleRec(Object vehicle) throws SQLException {
        return null;
    }

	public MultiVehicleUploadDTO loadMultipleVehiclesFile(
			AccountLoginDTO acctLoginDto,
			FileMultiVehicleUploadDTO uploadRequest) throws EtccException,
			EtccSecurityException {
		return uploadRequest;
	}
	@Override
	protected MultiVehicleDTO convertVehicleRecFile(Object vehicle) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
