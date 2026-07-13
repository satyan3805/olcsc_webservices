/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.service;

import com.etcc.csc.dao.DAOFactory;

/**
 * @author (task 488) Stephen Davidson
 *
 */
public class SetupFactories {
    
    private static boolean initialized = false;

    public static void setUpBeforeClass() throws Exception {
        if (!initialized) {
            System.out.println("Initializing");
            System.setProperty(DAOFactory.IMPL, "com.etcc.csc.dao.JUnitGenericDAOFactory");
            System.setProperty("ETCC_HCTRA_DATABASE", "Dummy");
            String factoryClass = System.getProperty(ServiceFactory.IMPL);
            if (factoryClass == null){
                factoryClass = System.getenv(ServiceFactory.IMPL);
            }
            if (factoryClass == null || !factoryClass.equals("com.etcc.csc.service.ServiceFactoryTestImpl")){
                System.setProperty(ServiceFactory.IMPL, ServiceFactoryTestImpl.class.getName());
            }
            initialized = true;
        }
    }
}
