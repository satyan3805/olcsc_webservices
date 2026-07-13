package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.List;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.dto.PickupLocationDTO;

/**
 * Contains methods for managing the account's orders.
 */
public interface OrderInterface extends BusinessObjectInterface {

	/**
	 * Retrieves orders belonging to an account.
	 * 
	 * @return Collection of OrderDTO
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	List<OrderDTO> getOrders(AccountLoginDTO acctLoginDto, boolean pendingOnly)
			throws EtccException, EtccSecurityException;

	public String updateOrder(AccountLoginDTO acctLoginDto, OrderDTO orderDTO,
			String callType) throws EtccException, EtccSecurityException;

	public List<AddressDTO> getShippingAddresses(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException;

	public List<PickupLocationDTO> getPOSLocations(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException;

	public List<Object> getFulfillmentRelatedAccountItems(
			AccountLoginDTO acctLoginDto, String fulfillmentId)
			throws EtccException, EtccSecurityException;

	public String getPickupLocationAddress(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException;

	public String getPickupLocationHours(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException;
}
