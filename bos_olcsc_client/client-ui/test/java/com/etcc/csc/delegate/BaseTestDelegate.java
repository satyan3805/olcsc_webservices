/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.ServiceFactoryTestImpl;

/**
 * Initial setup for the JUnit tests, especially for when running in Eclipse.
 * @author Stephen Davidson
 */
public abstract class BaseTestDelegate {
	protected static final String NOT_IMPLEMENTED_MSG = "Test not implemented yet.";

	private static boolean initialized = false;
	
    /**
     * Do the initial class level setup.  Called by the setUpBeforeClass method in the child classes.
     * @throws Exception If any exceptions occur during setup.
     */
    public static void setUpBeforeClass() throws Exception {
        if (!initialized) {
        	String factoryClass = System.getProperty(ServiceFactory.IMPL);
        	if (factoryClass == null){
        		factoryClass = System.getenv(ServiceFactory.IMPL);
        	}
        	if (factoryClass == null || !factoryClass.equals("com.etcc.csc.service.ServiceFactoryTestImpl")){
        		System.setProperty(ServiceFactory.IMPL, ServiceFactoryTestImpl.class.getName());
        	}
        }
    }
}
