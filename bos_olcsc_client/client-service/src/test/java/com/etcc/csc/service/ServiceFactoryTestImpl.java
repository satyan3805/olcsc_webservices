/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jmock.Mockery;

/**
 * <p>Test factory used to determine service interface objects to be returned for invocation. This class then checks to
 * see if there is a Test Implementation of the {@link ServiceInterface} available and if so, loads it and returns an
 * instance of that implementation. If a Test Implementation is not available, it will then Mock up an implementation.
 * Once the implementation is mocked, a check is made to see if there is a Factory available to populate any return
 * values for the Mocked Methods. </p>
 * <p>To use this class in a Website (useful for testing purposes where the DB is erratic, as well as for Demonstration
 * purposes), JMock <b>MUST</b> be in the class path of the Application Server (generally in a Lib folder). This class
 * should <b>ONLY</b> be used for testing or demo purposes, and <b>NEVER IN PRODUCTION!</b></p>
 * <p>To use this class, set the environment variable or Java System property <code>ETCC_HCTRA_SERVICE_FACTORY_IMPL</code>
 * to the value <code>com.etcc.csc.service.ServiceFactoryTestImpl</code>.</p>
 * 
 * @author Milosh Boroyevich
 * @author Stephen Davidson
 */
public class ServiceFactoryTestImpl extends ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactoryTestImpl.class);

    Mockery mockery = new Mockery();

    private final Map<Class<? extends ServiceInterface>, ServiceInterface> cache = 
        new HashMap<Class<? extends ServiceInterface>, ServiceInterface>();

    /**
     * Cache for the Impl methods on the factories.  The Impl methods are all static,
     * so no object is actually needed to invoke them.
     */
    private final Map<Class<? extends ServiceInterface>, Method> methodCache =
        new HashMap<Class<? extends ServiceInterface>, Method>();

    /**
     * Constructor.  Should be a singleton.
     */
    protected ServiceFactoryTestImpl() {/* end <init>*/ }


    /**
     * Gets the mockery in use so that the return values can be set where needed.
     * @return The current mockery.
     */
    public Mockery getMockeryContext() {
        return this.mockery;
    }

    /**
     * Returns the object implementing the specified interface.
     * @param theInterface Interface that the returned object should implement.
     * @return the object implementing the specified interface
     */
    @SuppressWarnings("unchecked")
    @Override   
    protected <T extends ServiceInterface> T getImpl(Class<T> theInterface) {
        //If the majority of the methods for an interface are implemented, or
        //the implementation of a method is complex (lots of used parameters, for instance)
        //then a TestImpl should be used.  Otherwise, a simpler Factory to populate the odd
        //method would be best.  Examples of the former, ViolationTestImpl.  Examples of the
        //latter, StateFactory & SecurityQuestionFactory.
        //JMOCK BUG: JMock is apparently occasionally unable to support multiple matchers for a parameter call.
        //For methods which take multiple parameters, a Test Impl is currently needed.
        T theImpl = (T) this.cache.get(theInterface);
        if (theImpl == null) {
            synchronized(this.cache) {
                //Check if still not present (possibly cached by another thread)
                theImpl = (T) this.cache.get(theInterface);
                if (theImpl == null) {
                    //Not yet cached.
                    theImpl = getTestImpl(theInterface);
                    if (theImpl == null){
                        //No Test Impl
                        theImpl = this.mockery.mock(theInterface);
                        try {
                            runFactory(theInterface, theImpl);
                        } catch (ClassNotFoundException e) {
                            logger.warn("No Initialization Factory for: " + theInterface.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("Exception initializing Impl for: " + theInterface.getName(), e);
                        }
                    }
                    this.cache.put(theInterface, theImpl);
                }
            }
        }
        return theImpl;
        //throw new IllegalArgumentException("Test implementation not available for: " + theInterface.getName());
    }

    protected <T extends ServiceInterface> T getTestImpl(Class<T> theInterface){
        final String name = theInterface.getSimpleName();
        final String implName = name.substring(0, name.length() - "Interface".length());
        try {
            @SuppressWarnings("unchecked")
            Class<T> implClass = (Class<T>) getClass().getClassLoader().loadClass("com.etcc.csc.service." + implName + "TestImpl");
            return implClass.newInstance();
        } catch (ClassNotFoundException e){
            //No TestImpl
            return null;
        } catch (InstantiationException e) {
            throw new RuntimeException(
                    name + "TestImpl can not be instantiated because it is an interface or is an abstract class: " + e.getMessage(), 
                    e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the constructor for " + implName + "TestImpl:" + e.getMessage(), e);
        }
    }
    
    protected <T extends ServiceInterface> void runFactory(Class<T> theInterface, ServiceInterface mockImpl) throws Exception {
        assert theInterface != null && mockImpl != null : "Nulls not allowed for parameters.";
        final String name = theInterface.getSimpleName();
        final String factoryName = name.substring(0, name.length() - "Interface".length());
        Class<?> factoryClass = getClass().getClassLoader().loadClass("com.etcc.csc.service." + factoryName + "Factory");
        //Does not work for package level access.
//        Method loadImpl = factoryClass.getMethod("loadImpl", getClass(), theInterface);
        Method loadImpl = this.methodCache.get(theInterface);
        if (loadImpl == null) {
            Method[] methods = factoryClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals("loadImpl")) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 2 && parameterTypes[1] == theInterface) {
                        method.setAccessible(true);
                        loadImpl = method;
                    }
                }
            }
            this.methodCache.put(theInterface, loadImpl);
        }
        assert loadImpl != null : 
            "static void loadImpl(ServiceFactoryTestImpl f, final " + theInterface.getName() + 
            " mocked) was not declared on " + factoryName;
        loadImpl.invoke(null, this, mockImpl);
    }
}
