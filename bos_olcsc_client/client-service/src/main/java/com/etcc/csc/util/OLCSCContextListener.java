/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.etcc.csc.service.EjbServiceFactory;
import com.etcc.csc.service.ServiceFactory;

/**
 * From the original OLSCSService project.
 * @author Stephen Davidson
 */
public class OLCSCContextListener implements ServletContextListener {
    
    private static final Logger logger = Logger.getLogger(OLCSCContextListener.class);
    
    /**
     * Default JNDI Lookup key for the Datasource.
     */
//    public static final String APP_DATASOURCE_DEFAULT = "java:comp/env/jdbc/OLCSCDS";
    public static final String APP_DATASOURCE_DEFAULT = "jdbc/OLCSCDS";
    /**
     * Web Context parameter name for the JNDI Lookup key for the Datasource.
     */
    public static final String APP_DATASOURCE_KEY = "APP_DATASOURCE";
    
    /**
     * Web Context parameter name for the current OLCSC Version.
     */
    public static final String OLCSC_VERSION_KEY = "OLCSC_VERSION";
    
    private static String appDataSource = APP_DATASOURCE_DEFAULT;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.trace("initializing");
        ServletContext aContext = servletContextEvent.getServletContext();
        if (aContext != null) {
            String theDataSource = aContext.getInitParameter(APP_DATASOURCE_KEY);
            logger.info("OLCSCContextListener.theDataSource=" + theDataSource);
            if ((theDataSource == null) || (theDataSource.length() == 0))
                theDataSource = APP_DATASOURCE_DEFAULT;
            setAppDataSource(theDataSource);
            String version = aContext.getInitParameter(OLCSC_VERSION_KEY);
            //HACK: There is probably a cleaner way of doing this!
            if (version != null && ServiceFactory.getDefaultServiceFactory() instanceof EjbServiceFactory){
                EjbServiceFactory.setVersion(version);
            }
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //end <contextDestroyed>
    }

    private static void setAppDataSource(String aDataSource) {
        OLCSCContextListener.appDataSource = aDataSource;
    }

    /**
     * The lookup key for Datasource.
     * @return the JNDI lookup key for the Datasource.
     */
    public static String getAppDataSource() {
        return OLCSCContextListener.appDataSource;
    }
}
