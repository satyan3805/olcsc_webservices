package com.etcc.csc.service;

import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FulfillmentDTO;
import com.etcc.csc.dto.InventoryItemAttrbsDTO;
import com.etcc.csc.dto.InventoryItemDTO;

public interface InventoryInterface extends BusinessObjectInterface {
	public Collection<InventoryItemDTO> getAvailableItems(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception;

	public Collection<InventoryItemAttrbsDTO> getItemsAtrbs(
			AccountLoginDTO acctLoginDto, String inventroyTypeId)
			throws EtccErrorMessageException, Exception;

	public FulfillmentDTO addToFulfillment(AccountLoginDTO acctLoginDto,
			List<InventoryItemDTO> inventoryItemList, Long fulfillmentId)
			throws EtccErrorMessageException, Exception;

	public FulfillmentDTO getFulfillmentDetails(AccountLoginDTO acctLoginDto,
			Long fullFillmentId) throws EtccErrorMessageException, Exception;

}// end of InventoryInterface Interface
