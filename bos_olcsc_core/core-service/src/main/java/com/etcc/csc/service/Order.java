package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.dto.PickupLocationDTO;

public class Order implements OrderInterface {
	private Logger logger = Logger.getLogger(Order.class);

	public Order() {
	}

	public List<OrderDTO> getOrders(AccountLoginDTO acctLoginDto,
			boolean pendingOnly) throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getOrders(acctLoginDto, pendingOnly);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getOrders() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getOrders() " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub only to force the inclusion of errorMessageDTO.
	 * 
	 * @return
	 */
	public ErrorMessageDTO getErrorMessage() {
		return null;
	}

	/**
	 * Stub only to force the inclusion of OrderDTO.
	 * 
	 * @return
	 */
	public OrderDTO getOrder() {
		return null;
	}

	public String updateOrder(AccountLoginDTO acctLoginDto, OrderDTO orderDTO,
			String callType) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.updateOrder(acctLoginDto, orderDTO, callType);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in updateOrder() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in updateOrder() " + ee, ee);
			throw ee;
		}
	}

	public List<AddressDTO> getShippingAddresses(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getShippingAddresses(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getShippingAddresses() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getShippingAddresses() " + ee, ee);
			throw ee;
		}
	}

	public List<PickupLocationDTO> getPOSLocations(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getPOSLocations(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getPOSLocations() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getPOSLocations() " + ee, ee);
			throw ee;
		}
	}

	public List<Object> getFulfillmentRelatedAccountItems(
			AccountLoginDTO acctLoginDto, String fulfillmentId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getFulfillmentRelatedAccountItems(acctLoginDto,
					fulfillmentId);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getFulfillmentRelatedAccountItems() "
							+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getFulfillmentRelatedAccountItems() "
					+ ee, ee);
			throw ee;
		}
	}

	public String getPickupLocationAddress(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getPickupLocationAddress(pickupLocationId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getPickupLocationAddress() "
					+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getPickupLocationAddress() " + ee, ee);
			throw ee;
		}
	}

	public String getPickupLocationHours(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			OrderDAO orderDao = daoFactory.getOrderDAO();
			return orderDao.getPickupLocationHours(pickupLocationId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getPickupLocationHours() "
					+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getPickupLocationHours() " + ee, ee);
			throw ee;
		}
	}
}