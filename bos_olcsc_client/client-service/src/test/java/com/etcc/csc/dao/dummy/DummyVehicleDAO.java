/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.service.VehicleFactory;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class DummyVehicleDAO extends VehicleDAO {
    /** 
     * @see com.etcc.csc.dao.VehicleDAO#loadVehicleMakes()
     */
    @Override
    protected Collection<VehicleMakeDTO> loadVehicleMakes() throws EtccException {
        return VehicleFactory.getVehicleMakes();
        //end loadVehicleMakes
    }

	@Override
	protected Collection<VehicleClassDTO> loadVehicleClasses() throws EtccException {
        return VehicleFactory.getVehicleClasses();
	}
}
