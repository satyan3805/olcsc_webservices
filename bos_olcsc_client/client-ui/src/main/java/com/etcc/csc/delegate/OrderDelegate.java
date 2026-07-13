package com.etcc.csc.delegate;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.service.OrderInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class OrderDelegate implements OrderInterface {
    private static final Logger logger = Logger.getLogger(OrderDelegate.class);

    public Collection<OrderDTO> getOrders(AccountLoginDTO acctLoginDto, boolean pendingOnly) throws EtccException, EtccSecurityException {
        try {
            return ServiceFactory.getImplementation(OrderInterface.class).getOrders(acctLoginDto, pendingOnly);
        } catch (EtccSecurityException se) {
            logger.error("getOrders: " + se.getMessage(), se);
            throw new EtccSecurityException(se);
        } catch (Exception t) {
            logger.error("getOrders: " + t.getMessage(), t);
            throw new EtccException(t.getMessage());
        }
    }

/*
    private com.etcc.csc.order.types.AccountLoginDTO convert(AccountLoginDTO theInput) {
        if (theInput == null)
            return null;
        com.etcc.csc.order.types.AccountLoginDTO theOutput = new com.etcc.csc.order.types.AccountLoginDTO();
        DtoUtil.copySimpleProperties(theOutput, theInput);
        return theOutput;
    }
    
    private Collection convertOrders(com.etcc.csc.order.types.OrderDTO[] orderDtos){
        
        OrderDTO[] newOrders = null;
        Collection orders = new ArrayList();
        if(orderDtos == null) return orders;
        
        newOrders = new OrderDTO[orderDtos.length];
        for(int i = 0; i < orderDtos.length;i++){
            newOrders[i] = new OrderDTO();
            DtoUtil.copySimpleProperties(newOrders[i],orderDtos[i]);
            orders.add(newOrders[i]);
            
        }
        
        return orders;
    }    
    private OrderDTO convert(com.etcc.csc.order.types.OrderDTO theInput) {
        if (theInput == null)
            return null;
        OrderDTO theOutput = new OrderDTO();
        DtoUtil.copySimpleProperties(theOutput, theInput);
        theOutput.setErrors(convertWsErrorCollection(theInput.getErrors()));
        return theOutput;
    }
    private Collection convertWsErrorCollection(com.etcc.csc.order.types.ErrorMessageDTO[] theInput) {
        if (theInput == null)
            return null;
        ArrayList theOutput = new ArrayList();
        for (int i = 0; i < theInput.length; i++) {
            theOutput.add(convertWsErrorToString(theInput[i])); // or theOutput.add(convertWsErrorToErrorMessageDTO(theInput[i]));
        }
        return theOutput;
    }

    private Object convertWsErrorToString(com.etcc.csc.order.types.ErrorMessageDTO theInput) {
        if (theInput == null)
            return null;
        return theInput.getMessage();
    }    

    private OrderWsSoapHttpPortClient stub() throws Exception {
        OrderWsSoapHttpPortClient stub = new OrderWsSoapHttpPortClient();
        stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "orderWsSoapHttpPort");
        return stub;
    }
*/
}
