/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.ServiceInterface;

/**
 * <p>
 * Generates Clients for WebServices. To use this factory for HCTRA OLCSC, set ETCC_HCTRA_SERVICE_FACTORY_IMPL =
 * {@link com.etcc.csc.service.WebServiceFactory}. By default, this class is set to go to
 * <code>http://localhost:8080/eztagstore/webservices/services/ServiceName<code> for a service.  The port can be
 * changed (at any time) by calling {@link #setPort(int)}.  The default Web Implementation using this service has a
 * listener configured to reset the port this factory is using to the one its receiving requests on if it is something
 * other than port 8080.  For either a Remote URL and/or a different base URL for the application, at least an extension
 * of this class (such as <code>WebServiceFactoryTestImpl</code> if not a completely different class will be needed.
 * </p>
 * 
 * @author Stephen Davidson
 * 
 */
public class WebServiceFactory extends ServiceFactory {

    private static final Logger logger = Logger.getLogger(WebServiceFactory.class);

    private static final String SERVICE_BASE = "/eztagstore/webservices/services/";
    
    /**
     * Base URL for the current WebService Application.  Default value: http://localhost:8080, but can
     * be updated by Context or other listeners if needed.
     */
    protected String baseUrl = "http://localhost:8080/";
    
    /**
     * Constructor.  Should be a singleton.
     */
    protected WebServiceFactory() {
        // end <init>
    }
    
    /**
     * Gets the URL that the Web Service can be found at.  This implementation will by default return 
     * <code>http://localhost:8080/eztagstore/webservices/services/ServiceName</code>, where ServiceName is the
     * computed name of the service returned by {@link #getServiceName(Class)}.  The port for the URL can be
     * updated/changed by calling {@link #setPort(int)}.
     * @param theInterface The service interface to get the URL for.
     * @return URL for the WebService for the Interface.
     * @see #getServiceName(Class)
     */
    protected String getServiceUrl(Class<? extends ServiceInterface> theInterface){
        return this.baseUrl + SERVICE_BASE + getServiceName(theInterface);
    }

    /**
     * Returns the object implementing the specified interface.
     * @param theInterface Interface that the returned object should implement.
     * @return the object implementing the specified interface
     */
    @Override   
    protected <T extends ServiceInterface> T getImpl(Class<T> theInterface) {
        final boolean traceEnabled = logger.isTraceEnabled();
        if (traceEnabled)
            logger.trace("Getting WS Client for: " + theInterface.getName());
        Service serviceModel = new ObjectServiceFactory().create(theInterface);
        XFire xfire = XFireFactory.newInstance().getXFire();
        XFireProxyFactory factory = new XFireProxyFactory(xfire);

        try {
            @SuppressWarnings("unchecked")
            T service = (T) factory.create(serviceModel, getServiceUrl(theInterface));
            if (traceEnabled){
                enableClientWSLogging(service);
            }
            return service;
        } catch (MalformedURLException e) {
            String msg = "Unable to access WebService " + getServiceName(theInterface) + ": " + e.getMessage();
            logger.debug(msg, e);//just debug here, as where the RuntimeException is caught will give the entire trace.
            throw new RuntimeException(msg, e);
        }
    }
    
    private void enableClientWSLogging(ServiceInterface proxy){
        // Enable Logging, if desired
        Client client = ((XFireProxy) Proxy.getInvocationHandler(proxy)).getClient();

        // Tell XFire to cache a DOM document for the various in/out/fault flows
        client.addInHandler(new org.codehaus.xfire.util.dom.DOMInHandler());
        client.addOutHandler(new org.codehaus.xfire.util.dom.DOMOutHandler());
        client.addFaultHandler(new org.codehaus.xfire.util.dom.DOMOutHandler());

        // Add a logging handler to each flow
        client.addInHandler(new org.codehaus.xfire.util.LoggingHandler());
        client.addOutHandler(new org.codehaus.xfire.util.LoggingHandler());
        client.addFaultHandler(new org.codehaus.xfire.util.LoggingHandler());
    }
    
    /**
     * Sets the port to use to contact the server on.
     * @param port the port the server is listening on.
     */
    public void setPort(int port){
        this.baseUrl = "http://localhost:" + port + "/";
    }
    
}
