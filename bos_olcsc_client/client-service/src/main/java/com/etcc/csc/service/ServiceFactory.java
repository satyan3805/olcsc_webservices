package com.etcc.csc.service;

import java.lang.reflect.Constructor;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.etcc.csc.util.StringUtil;

/**
 * <p>
 * Factory used to determine implementations of service interface objects to be returned for invocation. To change
 * which Implementation is being used w/o a recompile, set the environment variable or command line property
 * <code>ETCC_HCTRA_SERVICE_FACTORY_IMPL</code>.
 * </p>
 * <p>
 * Note that two implementations running in different JVMs (or even classloaders!) can have different factory classes
 * active at any given time.
 * </p>
 * <p>
 * Note: The class specified in ETCC_HCTRA_SERVICE_FACTORY_IMPL <b><u>MUST</u></b> be available to the classloader
 * that loaded this class.
 * </p>
 * <p>
 * Known and by default supported factories are;
 * </p>
 *
 * <pre>
 * com.etcc.csc.service.EjbServiceFactory (Default)
 * com.etcc.csc.service.WebServiceFactory
 * </pre>
 *
 * @author Milosh Boroyevich
 * @author Stephen Davidson
 * @since task 488
 */
public abstract class ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactory.class);
    /**
     * The field name on the interface that has the Factory to use to get the implementations of the Interface.  This is
     * used to override the defaults when the defaults are not appropriate for a given API.  Be advised that this will
     * ALWAYS override the defaults.
     */
    public static final String FACTORY_CLASS = "FACTORY_CLASS";

    /**
     * The environment variable used to override the Default Service Factory Impl.  For a sample value, see
     * {@link #DEFAULT_IMPL}
     * @see #DEFAULT_IMPL
     */
    public static final String IMPL = "ETCC_HCTRA_SERVICE_FACTORY_IMPL";

    /**
     * The FQDN of the Default Service Factory Implementation.
     */
    private static final String DEFAULT_IMPL = "com.etcc.csc.service.WebServiceFactory";
    //private static final String DEFAULT_IMPL = "com.etcc.csc.service.EjbServiceFactory";

    private static final ServiceFactory instance;


    static {
//        if (logger.isTraceEnabled() && false){
//            dumpSysProps();
//        }
        //Set Default.
        final String factoryImplName = getDefaultFactoryImplName();
        try {
            ServiceFactory factory = loadDefaultServiceFactory(factoryImplName);
            logger.info("Loading Default service factory implementation: " + factoryImplName);
            instance = factory;
        } catch (Throwable e) {//Need to catch Throwable here to cover NoClassDefFoundError and other similar errors.
            final String msg = "Unable to load Service Factory Implementation '" + factoryImplName +
                "':" + e.getMessage();
            logger.fatal(msg, e);
            throw new ExceptionInInitializerError(e);
        }
        logger.info("Loaded service factory implementation: " + factoryImplName);
    }

    protected static void dumpSysProps(){
        logger.trace("==================");
        logger.trace("System.properties:");
        final Properties sysProps = System.getProperties();
        for (Object key : sysProps.keySet()) {
            logger.trace(key + ": " + sysProps.getProperty((String)key));
        }
        logger.trace("==================");
    }

    protected static String getDefaultFactoryImplName() {
        //Allow override first by a command line property, followed by an ENV Var.
        final String factoryImpl = System.getProperty(IMPL, System.getenv(IMPL));
        if (factoryImpl == null){
            return DEFAULT_IMPL;
        }//else
        return factoryImpl;
    }


    protected static ServiceFactory loadDefaultServiceFactory(final String factoryName) throws Exception {
        assert !StringUtil.isEmpty(factoryName):
            "Coding error, factoryName should not be null or blank!";
        @SuppressWarnings("unchecked") //Generics is currently compile time, and this would be runtime.
        Class<? extends ServiceFactory> currentImpl = (Class<? extends ServiceFactory>) ServiceFactory.class.getClassLoader().loadClass(factoryName);
        Constructor<? extends ServiceFactory> init = currentImpl.getDeclaredConstructor();
        return createFactory(init);
    }

    private static ServiceFactory createFactory(Constructor<? extends ServiceFactory> constructor) throws Exception{
        if (!constructor.isAccessible()){
            constructor.setAccessible(true);
        }
        return constructor.newInstance(new Object[]{});
    }

    /**
     * Gets the Default Service Factory.
     * @return the Default Service Factory.
     */
    public static ServiceFactory getDefaultServiceFactory(){
        return instance;
    }

    /**
     * Constructor.
     */
    //Protected, as should only be available internally and by child classes.
    protected ServiceFactory() {
        //end <init>
    }

    /**
     * Returns the object implementing the specified interface.
     * @param <T> The interface implemented by the requested object.
     * @param theInterface Interface that the returned object should implement.
     * @return the object implementing the specified interface
     */
    protected abstract <T extends ServiceInterface> T getImpl(Class<T> theInterface);

    /**
     * Returns the object implementing the specified interface.
     * @param <T> The interface implemented by the requested object.
     * @param theInterface Interface that the returned object should implement.
     * @return the object implementing the specified interface
     */
    public static <T extends ServiceInterface> T getImplementation(Class<T> theInterface) {
        return instance.getImpl(theInterface);
    }

    protected final <T extends ServiceInterface> String getServiceName(Class<T> theInterface) {
        // The rule is, Interface Class is package.ServiceNameInterface
        String interfaceName = theInterface.getSimpleName();
        String serviceName = interfaceName.substring(0, interfaceName.length() - "Interface".length());
        if (logger.isTraceEnabled()) {
            logger.trace("Loading service: " + serviceName);
        }
        return serviceName;
    }

}
