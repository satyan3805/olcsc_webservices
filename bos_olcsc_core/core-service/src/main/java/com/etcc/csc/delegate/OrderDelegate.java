package com.etcc.csc.delegate;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
//import com.etcc.csc.dto.FulfillmentDetailToAccountItemDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.dto.PickupLocationDTO;
import com.etcc.csc.service.OrderInterface;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class OrderDelegate extends Delegate implements OrderInterface {
    
    OrderInterface order = (OrderInterface)getServiceObject(ServiceObjectEnum.ORDER);
    public OrderDelegate() {
        super(OrderDelegate.class);
    }

    public List<OrderDTO> getOrders(AccountLoginDTO acctLoginDto, 
        boolean pendingOnly) throws EtccException, EtccSecurityException {
        
        try {
            
            return order.getOrders(acctLoginDto, pendingOnly);
                
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getOrders: " + t, t);
        }
    }

	public String updateOrder(AccountLoginDTO acctLoginDto, OrderDTO orderDTO,
			String callType) throws EtccException, EtccSecurityException {
        try {
            
            return order.updateOrder(acctLoginDto, orderDTO, callType);
                
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running updateOrder: " + t, t);
        }
	}

	public List<AddressDTO> getShippingAddresses(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
        try {
            
            return order.getShippingAddresses(acctLoginDto);
                
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getShippingAddresses: " + t, t);
        }
	}

	public List<PickupLocationDTO> getPOSLocations(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
        try {
            
            return order.getPOSLocations(acctLoginDto);
                
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getPOSLocations: " + t, t);
        }
	}
	
	public List<Object> getFulfillmentRelatedAccountItems(AccountLoginDTO acctLoginDto, String fulfillmentId) 
			throws EtccException, EtccSecurityException {
        try {
            
            return order.getFulfillmentRelatedAccountItems(acctLoginDto, fulfillmentId);
                
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getFulfillmentRelatedAccountItems: " + t, t);
        }
	}
	
	public String getPickupLocationAddress(BigDecimal pickupLocationId) 
	throws EtccException, EtccSecurityException{
		try {    
		    
			return order.getPickupLocationAddress(pickupLocationId);
			
		} catch (EtccSecurityException ese) {
		    logger.error("Security exception in getPickupLocationAddress() " + ese, ese); 
		    throw ese;
		} catch (EtccException ee) {
		    logger.error("Exception in getPickupLocationAddress() " + ee, ee);
		    throw ee;
		}
	}
	
	public String getPickupLocationHours(BigDecimal pickupLocationId) 
	throws EtccException, EtccSecurityException{
		try {    
		    
			return order.getPickupLocationHours(pickupLocationId);
		    
		} catch (EtccSecurityException ese) {
		    logger.error("Security exception in getPickupLocationHours() " + ese, ese); 
		    throw ese;
		} catch (EtccException ee) {
		    logger.error("Exception in getPickupLocationHours() " + ee, ee);
		    throw ee;
		}
	}

}
