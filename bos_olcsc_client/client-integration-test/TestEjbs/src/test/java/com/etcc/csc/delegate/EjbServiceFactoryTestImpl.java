package com.etcc.csc.delegate;

import java.util.Properties;

import javax.naming.Context;

import org.apache.log4j.Logger;

import com.etcc.csc.service.EjbServiceFactory;
import com.etcc.csc.service.ServiceInterface;
import com.etcc.csc.service.WebServiceFactory;

/**
 * Test factory used to determine Web service interface objects to be returned for invocation. This object has been
 * used to test the code in {@link WebServiceFactory}.
 */
public class EjbServiceFactoryTestImpl extends EjbServiceFactory {
    private static final Logger logger = Logger.getLogger(EjbServiceFactoryTestImpl.class);

    /**
     * Constructor. Should be a singleton.
     */
    private EjbServiceFactoryTestImpl() {
        // end init
    }

    /** 
     * @see com.etcc.csc.service.EjbServiceFactory#getJndiLookupName(java.lang.Class)
     */
    @Override
    protected String getJndiLookupName(Class<? extends ServiceInterface> theInterface) {
        final String lookupKey = "com/etcc/" + getServiceName(theInterface) + "Local";
        logger.trace("EjbServiceFactoryTestImpl: " + lookupKey);
        return lookupKey;
        //end getJndiLookupName
        
    }

    /**
     * Gets the properties needed for setting up an Initial Context suitable for looking up EJBs from the OpenEJB 
     * container.
     * @return Properties with appropriate settings for looking up an EJB from and OpenEJB container.
     * @see com.etcc.csc.service.EjbServiceFactory#getInitialContextProps()
     */
    @Override
    protected Properties getInitialContextProps() {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        return properties;
    }

}
