/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.service.VehicleInterface;

/**
 * Copied from OLCSCService.  Current implementation caches the vehicle makes until the first call after midnight.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
//TODO: JMX Bean - load from cache.
public abstract class VehicleDAO extends BaseDAO implements VehicleInterface {
    private static final Logger logger = Logger.getLogger(VehicleDAO.class);

    //TODO: First pass, this needs to be cleaned up.
    //TODO: Replace equals/hashcode method on CountryDTO to avoid duplicate "entries".
    //TODO: Build a country cache.
    
    /**
     * Collection of VehicleClasses.
     */
    private static final Collection<VehicleClassDTO> vehicleClasses = new ArrayList<VehicleClassDTO>();

    /**
     * Collection of VehicleMakes.
     */
    private static final Collection<VehicleMakeDTO> vehicleMakes = new ArrayList<VehicleMakeDTO>();

    private static Date lastLoad;
    private static Date expires;

    protected abstract Collection<VehicleClassDTO> loadVehicleClasses() throws EtccException;
    protected abstract Collection<VehicleMakeDTO> loadVehicleMakes() throws EtccException;

    public Collection<VehicleClassDTO> getVehicleClasses() throws EtccException {
        expires = checkAndClearCache(expires, vehicleClasses);
        if (vehicleClasses.isEmpty()){
            if (logger.isDebugEnabled()){
                logger.debug("Last load: " + (lastLoad == null ? "never" : DateFormat.getDateTimeInstance().format(lastLoad)));
            }
            vehicleClasses.addAll(loadVehicleClasses());
            lastLoad = new Date();
        }
        return vehicleClasses;
    }

    public Collection<VehicleMakeDTO> getVehicleMakes() throws EtccException {
        expires = checkAndClearCache(expires, vehicleMakes);
        if (vehicleMakes.isEmpty()){
            if (logger.isDebugEnabled()){
                logger.debug("Last load: " + (lastLoad == null ? "never" : DateFormat.getDateTimeInstance().format(lastLoad)));
            }
            vehicleMakes.addAll(loadVehicleMakes());
            lastLoad = new Date();
        }
        return vehicleMakes;
    }
}
