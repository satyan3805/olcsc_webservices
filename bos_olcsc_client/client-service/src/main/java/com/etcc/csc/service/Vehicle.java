/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;

/**
 * Returns the DTOs representing known vehicle makes and classes.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
@Stateless(name="com/etcc/Vehicle")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Vehicle", targetNamespace = "http://ws.csc.etcc.com/Vehicle")
// Copied from OLCSCService com.etcc.csc.service.VehicleMake and com.etcc.csc.service.Vehicle
public class Vehicle implements VehicleInterface {

	//@WebMethod(operationName = "getVehicleClasses", action = "urn:getVehicleClasses")
	//@WebResult(name = "vehicleClasses")
	public Collection<VehicleClassDTO> getVehicleClasses() throws EtccException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		VehicleDAO vehicleDao = daoFactory.getDAO(VehicleDAO.class);
		Collection<VehicleClassDTO> col = vehicleDao.getVehicleClasses();
		return col;
	}

	//@WebMethod(operationName = "getVehicleMakes", action = "urn:getVehicleMakes")
	//@WebResult(name = "vehicleMakes")
	public Collection<VehicleMakeDTO> getVehicleMakes() throws EtccException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		VehicleDAO vehicleDao = daoFactory.getDAO(VehicleDAO.class);
		Collection<VehicleMakeDTO> col = vehicleDao.getVehicleMakes();
		return col;
	}
}
