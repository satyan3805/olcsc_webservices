/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import org.apache.log4j.Logger;

import com.etcc.csc.service.AdminInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.StringUtil;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class AdminDelegate implements AdminInterface {
    private static final Logger logger = Logger.getLogger(AdminDelegate.class);

    public void insertSessionCount(String serverName, int count) {
        try {
            if (!StringUtil.isEmpty(serverName)) {
                /*
                AdminWS_Service service = new AdminWS_ServiceLocator();
                AdminWS_PortType port = service.getAdminWSSoapHttpPort();
                InsertSessionCount param = 
                    new InsertSessionCount(serverName.toUpperCase(), count);
                port.insertSessionCount(param);
                */
                /*import com.etcc.csc.service.Admin;
                new Admin().insertSessionCount(serverName.toUpperCase(), count);*/
            	AdminInterface stub = ServiceFactory.getImplementation(AdminInterface.class);
//                stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "adminWsSoapHttpPort");
                stub.insertSessionCount(serverName.toUpperCase(), count);
            }
        }
        catch (Throwable t) 
        {
            logger.error("insertSessionCount failed: " + t.getMessage(), t);
          // handle the exception quietly
        }
    }
}
