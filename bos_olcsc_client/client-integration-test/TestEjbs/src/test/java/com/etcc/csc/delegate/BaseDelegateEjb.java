/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import javax.naming.Context;

import org.apache.log4j.Logger;
import org.apache.openejb.client.LocalInitialContextFactory;
import org.junit.Test;

import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.GenericDAOFactory;
import com.etcc.csc.service.ServiceFactory;

/**
 * Initializes settings for running inside of Eclipse, as well as handling a Maven/Jetty/OpenEJB(?) bug.
 * 
 * @author Stephen Davidson
 * 
 */
public abstract class BaseDelegateEjb {
    
    private static final Logger logger = Logger.getLogger(BaseDelegateEjb.class);

    /**
     * Do the initial class level setup. Called by the setupBeforeClass method in the child classes.
     * 
     * @throws Exception If any exceptions occur during setup.
     */
    public static void setUpBeforeClass() throws Exception {
        String namingFactory = System.getProperty(Context.INITIAL_CONTEXT_FACTORY);
        if (logger.isTraceEnabled()){
            logger.trace("Naming Factory: " + namingFactory);
        }
        if (namingFactory == null || !namingFactory.equals(LocalInitialContextFactory.class.getName())){
            logger.info("Setting EJB Factories and Test System properties.");
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, LocalInitialContextFactory.class.getName());
            System.setProperty("openejb.deployments.classpath.include", ".*services.*");
            System.setProperty("openejb.webservices.enabled", Boolean.FALSE.toString());
            
            System.setProperty(ServiceFactory.IMPL, EjbServiceFactoryTestImpl.class.getName());
            System.setProperty(DAOFactory.IMPL, "com.etcc.csc.dao.JUnitGenericDAOFactory");
            System.setProperty(GenericDAOFactory.DATABASE, "dummy");
        }
    }

    /**
     * Dummy test method for some dumb test scanners.
     */
    @Test
    public void dummyTest() {
        // For some dumb test scanners.
    }
}
