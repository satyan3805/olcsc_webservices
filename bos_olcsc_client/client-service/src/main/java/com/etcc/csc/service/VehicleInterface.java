package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;

/**
 * Retrieves the current list of Vehicle Makes and Classes.
 * @author unknown
 * @author Stephen Davidson (task 488)
 * @author Milosh Boroyevich
 */
//Copied from com.etcc.csc.service.VehicleMakeInterface and com.etcc.csc.service.VehicleInterface in OLCSCService module.
@Local
public interface VehicleInterface extends ServiceInterface {
    /**
     * Retrieves the collection of vehicle makes from the Vehicle_Make table.
     * @return the collection of vehicle makes from the Vehicle_Make table
     * @throws EtccException
     */
    Collection<VehicleMakeDTO> getVehicleMakes() throws EtccException;

    /**
     * Retrieves a list of vehicle classes.
     * @return a collection of VehicleClassDTO.
     * @throws EtccException
     */
    Collection<VehicleClassDTO> getVehicleClasses() throws EtccException;
}
