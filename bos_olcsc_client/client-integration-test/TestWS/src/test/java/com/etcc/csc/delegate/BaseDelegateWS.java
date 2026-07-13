/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.WebServiceFactory;
import com.etcc.csc.service.WebServiceFactoryTestImpl;

/**
 * Initial setup for the JUnit tests, especially for when running in Eclipse.
 * @author Stephen Davidson
 *
 */
public abstract class BaseDelegateWS {

    /**
     * Do the initial class level setup.  Called by the setupBeforeClass method in the child classes.
     * @throws Exception If any exceptions occur during setup.
     */
    public static void setUpBeforeClass() throws Exception {
        String testFactory = System.getProperty(ServiceFactory.IMPL);
        if (testFactory == null || 
                !(testFactory.equalsIgnoreCase(WebServiceFactoryTestImpl.class.getName())
                        || testFactory.equalsIgnoreCase(WebServiceFactory.class.getName()))
            ){
          System.setProperty(ServiceFactory.IMPL, WebServiceFactoryTestImpl.class.getName());
            
        }
//        System.setProperty(ServiceFactory.IMPL, ServiceFactoryTestImpl.class.getName());
//        System.setProperty(ServiceFactory.IMPL, WebServiceFactory.class.getName());
    }
}
