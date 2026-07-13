/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Factory of DAO Objects that can be used in/for transactions.  To set explicitly set the factory to use, set the
 * environment variable or System property <code>ETCC_HCTRA_DAO_FACTORY_IMPL</code>.  If this is not set, the default
 * implementation will be used.  Currently, the Default Implementation is {@link GenericDAOFactory}.
 * @see GenericDAOFactory
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
//Initially Refactored from the DAOFactory Object in OLCSCService com.etcc.csc.common.
public abstract class DAOFactory {
    private static final Logger logger = Logger.getLogger(DAOFactory.class);

    /**
     * The environment variable used to override the Default DAO Factory Impl.
     */
    public static final String IMPL = "ETCC_HCTRA_DAO_FACTORY_IMPL";

    /**
     * The FQDN of the Default DAO Factory Implementation.
     * @see #IMPL
     */
//    private static final String DEFAULT_IMPL = "com.etcc.csc.common.ServiceFactoryReflectiveImpl";
    static final String DEFAULT_IMPL = GenericDAOFactory.class.getName();
    protected static final String DAO_PACKAGE = "com.etcc.csc.dao";
    
    private static final DAOFactory factory;
    
    static {
        //Set Default.
        final String factoryImplName = getDefaultFactoryImplName();
        try {
            factory = loadDaoFactory(factoryImplName);
        } catch (Exception e) {
            final String msg = "Unable to load DAO Factory Implementation '" + factoryImplName +
                "':" + e.getMessage();
            logger.fatal(msg, e);
            throw new ExceptionInInitializerError(e);
        }
        logger.debug("Loaded DAO factory implementation: " + factoryImplName);
    }

    protected static String getDefaultFactoryImplName() {
        //Allow override first by a command line property, followed by an ENV Var.
        final String factoryImpl = System.getProperty(IMPL, System.getenv(IMPL));
        if (factoryImpl == null){
            logger.trace("No DAO Factory implementation defined, using default: " + DEFAULT_IMPL);
            return DEFAULT_IMPL;
        }//else
        return factoryImpl;
    }
    
    protected static DAOFactory loadDaoFactory(final String factoryName) throws Exception{
        if (logger.isDebugEnabled()){
            logger.debug("Loading factory: '" + factoryName +'\'');
        }
        assert !(factoryName == null || factoryName.trim().length() == 0): 
            "Coding errory, factoryName should not be null or blank!";
        @SuppressWarnings("unchecked") //Generics is currently compile time, and this would be runtime.
        Class<? extends DAOFactory> currentImpl = (Class<DAOFactory>)DAOFactory.class.getClassLoader().loadClass(factoryName);
        Constructor<? extends DAOFactory> constructor = currentImpl.getDeclaredConstructor(new Class[]{});
        constructor.setAccessible(true);
        DAOFactory f = constructor.newInstance(new Object[]{});
        return f;
        //end loadDaoFactory
    }
    
    /**
     * Constructor.  Should only be called by the child classes.
     */
    protected DAOFactory() {
    	//end <init>
    }

    /**
     * Gets the Default DAO Factory.
     * @return The default DAO Factory.
     */
    public static DAOFactory getDAOFactory() {
        return factory;
        //end getDAOFactory
    }

    /**
     * Returns the implementation of the specified DAO type. 
     * @param <T> The class type.
     * @param daoType The Base/Abstract DAO to get the implementation of.
     * @return an instance implementation of the requested DAO.
     */
    public abstract <T extends BaseDAO> T getDAO(Class<T> daoType);

    /**
     * Returns a DB-specific class implementing the requested simple name.
     * The class name pattern is
     * <code>&lt;DAO_PACKAGE&gt;.&lt;databasename&gt;.&lt;Databasename&gt;&lt;classSimpleName&gt;</code>.
     * For example: <tt>AccountAlertDAO</tt> on an Oracle Database translates to
     * <code>com.etcc.csc.dao.oracle.OracleAccountAlertDAO</code>.
     * @param classSimpleName the simple name of the class to append to DB name
     * @return the database specific class implementing the requested simple name
     * @throws ClassNotFoundException If the class was not found
     * @see Class#getSimpleName()
     */
    protected abstract <T> Class<?> getClass(String classSimpleName) throws ClassNotFoundException;

    /**
     * Invokes a no-argument static method on the DB-specific utility class.
     * @param methodName name of method to invoke
     * @return the result of invoking the specified method
     * @throws UnsupportedOperationException if the utility class or method do not exist
     * @throws InvocationTargetException if an error occurs during the underlying method invocation
     * @see #invokeUtility(String, Object...)
     */
    public final Object invokeUtility(String methodName) throws UnsupportedOperationException, InvocationTargetException {
        return invokeUtility(methodName, (Object[]) null);
    }

    /**
     * Invokes a static method on the DB-specific utility class.
     * The utility class name pattern is
     * <code>&lt;DAO_PACKAGE&gt;.&lt;databasename&gt;.&lt;Databasename&gt;Util</code>.
     * On an Oracle Database this translates to
     * <code>com.etcc.csc.dao.oracle.OracleUtil</code>.
     * @param methodName name of method to invoke
     * @param args arguments to pass to specified method
     * @return the result of invoking the specified method
     * @throws UnsupportedOperationException if the utility class or method do not exist
     * @throws InvocationTargetException if an error occurs during the underlying method invocation
     * @see #getClass(String)
     * @see Class#getMethod(String, Class...)
     * @see Method#invoke(Object, Object...)
     */
    public final Object invokeUtility(String methodName, Object... args) throws UnsupportedOperationException, InvocationTargetException {
        Class<?> clazz;
        try {
            clazz = getClass("Util");
        } catch (ClassNotFoundException e) {
            String msg = "Unable to find DB-specific Utility class.";
            logger.error(msg, e);
            throw new UnsupportedOperationException(msg, e);
        }
        Method method;
        try {
            Class<?>[] parameterTypes = null;
            if (args != null) {
                parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++ )
                    parameterTypes[i] = args[i].getClass();
            }
            method = clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            String msg = "Unable to invoke Utility class method: " + methodName;
            logger.error(msg, e);
            throw new UnsupportedOperationException(msg, e);
        }
        try {
            // invoke statically
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            String msg = "Error encountered invoking Utility class method: " + methodName;
            Throwable targetException = e.getCause();
            logger.error(msg, targetException);
            throw e;
        } catch (IllegalAccessException e) {
            // this should never occur
            String msg = "Unable to invoke Utility class method: " + methodName;
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}//end DAOFactory
