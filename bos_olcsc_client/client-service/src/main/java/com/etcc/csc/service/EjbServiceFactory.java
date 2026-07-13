/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


/**
 * Generates EJB Clients for the Services.  Currently, only Local EJB clients (with active Transaction contexts) are
 * generated.  This factory is specifically for OC4J deployments.  For different App Servers, a different implementation
 * WILL BE NEEDED.  Note that for this to work properly on OC4J, the method {@link #setVersion(String)} needs to be
 * called before the first invocation of {@link #getImplementation(Class)} in order to properly set the jndi prefix to 
 * match the Hash that Oracle uses.
 * @author Stephen Davidson
 * @since task 488
 */
public class EjbServiceFactory extends ServiceFactory {

    private static final Logger logger = Logger.getLogger(EjbServiceFactory.class);
    private static final boolean isDebugEnabled = logger.isDebugEnabled();

    private static String version;
    
    private static String jndiLookupBase = "java:comp/env/com/etcc/";  //Default for if ejb-jar.xml & orion-ejb.xml files are defined.
    
    /**
     * If a check has been made to see if the current container uses Home Interfaces or not.
     */
    boolean unset = true;
    /**
     * If the current container uses Home Interfaces.
     */
    boolean usesHomes = false;
    
    /**
     * Constructor.  Should be a singleton, but can be extended.
     */
    protected EjbServiceFactory() {
        // end <init>
    }

    /**
     * Computes the JNDI lookup key from the Interface.
     * @param theInterface The Interface that the desired service implements.
     * @return Jndi lookup key to get a handle to the service.
     */
    protected String getJndiLookupName(Class<? extends ServiceInterface> theInterface){
        final String lookupKey = jndiLookupBase + getServiceName(theInterface);
        if (isDebugEnabled){
            logger.debug(lookupKey);
        }
        return lookupKey;
    }
    
    /**
     * Returns the object implementing the specified interface.
     * @param theInterface Interface that the returned object should implement.
     * @return the object implementing the specified interface
     */
    @Override   
    protected final <T extends ServiceInterface> T getImpl(Class<T> theInterface) {
        if (logger.isTraceEnabled())
            logger.trace("Getting EJB Client for: " + theInterface.getName());
        InitialContext initialContext = null;
        try {
            initialContext = getInitialContext();
            final String jndiLookupName = getJndiLookupName(theInterface);
            Object lookup = initialContext.lookup(jndiLookupName);
            //Some containers return a Home Object, some return the impl.
            if (this.unset){
                if (isDebugEnabled){
                    logger.debug("Checking for Home Implementation");
                }
                final String proxyName = lookup.getClass().getName();
                if (proxyName.endsWith("Home") || proxyName.endsWith("HomeImpl")){
                    this.usesHomes = true;
                } else {
                    if (proxyName.contains("Home")){
                        logger.warn("'Home' present in Proxy class name, but does not appear to be a Home Interface: " 
                                + proxyName);
                    }
                }
                if (isDebugEnabled){
                    logger.debug("Uses Homes: " + this.usesHomes);
                }
                this.unset ^= this.unset; //quick flip to false
            }
            if (this.usesHomes){
                lookup = getHandle(jndiLookupName, lookup);
            }
            @SuppressWarnings("unchecked")
            T service = (T)lookup;
            return service;
        } catch (NamingException e) {
          String msg = "Unable to access EJB " + getServiceName(theInterface) + ": " + e.getMessage();
          logger.debug(msg, e);// just debug here, as where the RuntimeException is caught will give the entire
                               // trace.
          throw new RuntimeException(msg, e);
        } finally {
            if (initialContext != null){
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    logger.warn("Exception closing initial context: " + e.getMessage(), e);
                }
            }
        }
    }
    
    private Object getHandle(final String jndiName, final Object home){
        try {
            final Method create = home.getClass().getMethod("create", new Class[]{});
            return create.invoke(home, new Object[]{});
        } catch (SecurityException e) {
            throw new RuntimeException("SecurityExcepton accessing create method for " + jndiName + ": " + e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Missing create method for " + jndiName + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unable to invoke create method for " + jndiName + ": " + e.getMessage(), e);
        }
    }
    
    /**
     * Default implementation that child classes can override to set properties with.  This is called by the 
     * {@link #getInitialContext()} method to get the properties for the context.
     * @return This implementation returns null.  Child Implementations may return a populated properties object.
     */
    protected Properties getInitialContextProps(){
        return null;
    }

    /**
     * Gets the InitialContext to use for looking up the EJB Home Interfaces.  If the properties need to be changed
     * from the defaults, the {@link #getInitialContextProps()} should be overridden.
     * @return The initial context to use to look up the Interfaces.
     * @throws NamingException If the Context can not be created.
     * @see #getInitialContextProps()
     */
    protected final InitialContext getInitialContext() throws NamingException {
        InitialContext initialContext = new InitialContext(getInitialContextProps());
        return initialContext;
    }

    /**
     * Get the version of the project.
     * @return the project version
     */
    public static String getVersion() {
        return version;
        //end getVersion
    }

    /**
     * Sets the Version of the project.  Can only be set once.
     * @param version the version to set.
     */
    public static void setVersion(String version) {
        if (EjbServiceFactory.version == null){
            EjbServiceFactory.version = version == null ? "" : version;
            if (version != null){
                jndiLookupBase = "java:comp/env/com/etcc/";
            }
        } else {
            throw new IllegalStateException("Version has already been set.");
        }
        //end setVersion
    }
}
