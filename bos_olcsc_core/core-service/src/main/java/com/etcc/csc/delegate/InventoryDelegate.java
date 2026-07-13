package com.etcc.csc.delegate;

import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FulfillmentDTO;
import com.etcc.csc.dto.InventoryItemAttrbsDTO;
import com.etcc.csc.dto.InventoryItemDTO;
import com.etcc.csc.service.InventoryInterface;

public class InventoryDelegate extends Delegate implements InventoryInterface {
	InventoryInterface invI = (InventoryInterface) getServiceObject(ServiceObjectEnum.INVENTORY);

	public InventoryDelegate() {
		super(InventoryDelegate.class);
	}

	public Collection getAvailableItems(AccountLoginDTO acctLoginDto)
			throws EtccErrorMessageException, Exception {
		try {
			return invI.getAvailableItems(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error(se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running Inventory: " + t, t);
		}
	}

	public Collection<InventoryItemAttrbsDTO> getItemsAtrbs(
			AccountLoginDTO acctLoginDto, String inventroyTypeId)
			throws EtccErrorMessageException, Exception {
		try {
			return invI.getItemsAtrbs(acctLoginDto, inventroyTypeId);
		} catch (EtccSecurityException se) {
			logger.error(se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running Inventory: " + t, t);
		}
	}

	public FulfillmentDTO addToFulfillment(AccountLoginDTO acctLoginDto,
			List<InventoryItemDTO> inventoryItemList, Long fulfillmentId)
			throws EtccErrorMessageException, Exception {

		try {
			return invI.addToFulfillment(acctLoginDto, inventoryItemList,
					fulfillmentId);
		} catch (EtccSecurityException se) {
			logger.error(se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running Inventory: " + t, t);
		}
	}

	public FulfillmentDTO getFulfillmentDetails(AccountLoginDTO acctLoginDto,
			Long fullFillmentId) throws EtccErrorMessageException, Exception {
		try {
			return invI.getFulfillmentDetails(acctLoginDto, fullFillmentId);
		} catch (EtccSecurityException se) {
			logger.error(se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running Inventory: " + t, t);
		}
	}
}
