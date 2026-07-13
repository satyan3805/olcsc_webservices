package com.etcc.csc.service;

import com.etcc.csc.service.ServiceInterface;
import com.etcc.csc.service.WebServiceFactory;

/**
 * Test factory used to determine Web service interface objects to be returned for invocation. This object has been
 * used to test the code in {@link WebServiceFactory}.
 */
//In Web Test, so as to be picked up by Integration tests as needed.
public class WebServiceFactoryTestImpl extends WebServiceFactory {

    private static final String SERVICE_BASE = "webservices/services/";

    /**
     * Constructor. Should be a singleton.
     */
    private WebServiceFactoryTestImpl() {
        // end init
    }


    @Override
    protected String getServiceUrl(Class<? extends ServiceInterface> theInterface){
        return super.baseUrl + SERVICE_BASE + getServiceName(theInterface);
    }
}
