package com.etcc.csc.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.InventoryDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FulfillmentDTO;
import com.etcc.csc.dto.InventoryItemAttrbsDTO;
import com.etcc.csc.dto.InventoryItemDTO;

public class Inventory implements InventoryInterface {
	private Logger logger = Logger.getLogger(Inventory.class);

	public Collection<InventoryItemDTO> getAvailableItems(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception {
		logger.info("Entering getAvailableItems() @ Inventory");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			InventoryDAO inventoryDao = daoFactory.getInventoryDAO();
			logger.info("Leaving getAvailableItems() @ Inventory");
			return inventoryDao.getAvailableItems(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAvailableItems() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getAvailableItems() " + ee, ee);
			throw ee;
		}// end of try-catch()
	}// end of getAvailableItems()

	public Collection<InventoryItemAttrbsDTO> getItemsAtrbs(
			AccountLoginDTO acctLoginDto, String inventroyTypeId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getItemsAtrbs() @ Inventory");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			InventoryDAO inventoryDao = daoFactory.getInventoryDAO();
			logger.info("Leaving getItemsAtrbs() @ Inventory");
			return inventoryDao.getItemsAtrbs(acctLoginDto, inventroyTypeId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getItemsAtrbs() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getItemsAtrbs() " + ee, ee);
			throw ee;
		}// end of try-catch()
	}// end of getItemsAtrbs()

	public FulfillmentDTO addToFulfillment(AccountLoginDTO acctLoginDto,
			List<InventoryItemDTO> inventoryItemList, Long fulfillmentId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering addToFulfillment() @ Inventory");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			InventoryDAO inventoryDao = daoFactory.getInventoryDAO();
			logger.info("Leaving addToFulfillment() @ Inventory");
			return inventoryDao.addToFulfillment(acctLoginDto,
					inventoryItemList, fulfillmentId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in addToFulfillment() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in addToFulfillment() " + ee, ee);
			throw ee;
		}// end of try-catch()
	}// end of getFulfillmentDetails()

	public FulfillmentDTO getFulfillmentDetails(AccountLoginDTO acctLoginDto,
			Long fullFillmentId) throws EtccErrorMessageException, Exception {
		logger.info("Entering getFulfillmentDetails() @ Inventory");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			InventoryDAO inventoryDao = daoFactory.getInventoryDAO();
			logger.info("Leaving getFulfillmentDetails() @ Inventory");
			return inventoryDao.getFulfillmentDetails(acctLoginDto,
					fullFillmentId);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getFulfillmentDetails() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getFulfillmentDetails() " + ee, ee);
			throw ee;
		}// end of try-catch()
	}// end of getFulfillmentDetails()

}// end of Inventory Class
