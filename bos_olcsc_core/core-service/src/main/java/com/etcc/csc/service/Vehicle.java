package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;

public class Vehicle implements VehicleInterface {
	private Logger logger = Logger.getLogger(Vehicle.class);

	public Vehicle() {
	}

	public Collection getVehicleClasses(String lang) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			Collection col = vehicleDao.getVehicleClasses(lang);
			return col;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getStates() " + ee, ee);
			throw ee;
		}
	}

	public Collection getLicPlateTypes(String lang) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			Collection col = vehicleDao.getLicPlateTypes(lang);
			return col;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getLicPlateTypes() " + ee, ee);
			throw ee;
		}
	}

	public String[] getValidationMessages(AccountLoginDTO accountLoginDTO,
			TagDTO tag, long eventId, String action) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			String col[] = vehicleDao.getValidationMessages(accountLoginDTO,
					tag, eventId, action);
			return col;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getStates() " + ee, ee);
			throw ee;
		}
	}

	public TagDTO[] saveVehicleInfo(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, String action) throws EtccSecurityException,
			EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			tags = vehicleDao.saveVehicleInfo(acctLoginDto, tags, action);
			return tags;
		} catch (EtccSecurityException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Security Exception in getStates() " + ee, ee);
			throw ee;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getStates() " + ee, ee);
			throw ee;
		}
	}

	public String[] deleteVehicle(AccountLoginDTO acctLoginDto, TagDTO tagDTO)
			throws EtccSecurityException, EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			String[] result = vehicleDao.deleteVehicle(acctLoginDto, tagDTO);
			return result;
		} catch (EtccSecurityException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Security Exception in getStates() " + ee, ee);
			throw ee;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getStates() " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub only to get VehicleClassDTO included in the WSDL.
	 * 
	 * @return
	 */
	public VehicleClassDTO getVehicle() {
		return null;
	}

	public boolean findExistingLicPlateNbr(String licPlate, Long accountId)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			boolean result = vehicleDao.findExistingLicPlateNbr(licPlate,
					accountId);
			return result;
		} catch (EtccException ee) {
			logger.error("Error while getting Lic Plate Nbr " + ee);
			throw ee;
		}
	}

	public String calculateAutochargeAmount(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			String message = vehicleDao.calculateAutochargeAmount(acctLoginDto);
			return message;
		} catch (EtccSecurityException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Security Exception in calculateAutochargeAmount() "
					+ ee, ee);
			throw ee;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in calculateAutochargeAmount() " + ee, ee);
			throw ee;
		}
	}
}