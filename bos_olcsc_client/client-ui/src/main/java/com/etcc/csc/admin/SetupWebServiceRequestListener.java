/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.admin;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.WebServiceFactory;

/**
 * If the WebService Factory is in use, set up the port to use to send the requests on.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class SetupWebServiceRequestListener implements ServletRequestListener {
    private static final Logger logger = Logger.getLogger(SetupWebServiceRequestListener.class);

    private static AtomicBoolean initialized = new AtomicBoolean(false);

    /** 
     * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre) {
        //end requestDestroyed
    }

    /** 
     * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre) {
        if (initialized.compareAndSet(false, true)) {
            ServiceFactory defaultFactory = ServiceFactory.getDefaultServiceFactory();
            logger.info("Initializing...");
            final boolean traceEnabled = logger.isTraceEnabled();
            if (traceEnabled) {
                logger.trace("ServiceFactory: " + defaultFactory.getClass());
            }
            try {
            	if (defaultFactory instanceof WebServiceFactory) {
            		final int port = sre.getServletRequest().getLocalPort();
            		((WebServiceFactory)defaultFactory).setPort(port);
            		logger.info("Webservice Port: " + port);
            	}
            } catch(NoClassDefFoundError e) {
            	logger.warn("Problem loading WebServiceFactory.", e);
            }
            logger.info("Initialization complete.");
        }
        //end requestInitialized
    }
}
