/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccSysException;

/**
 * Reflectively loads and instantiates the DAO Factory as needed.  Currently, by default loads Oracle DAOs.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class GenericDAOFactory extends DAOFactory {
    private static final Logger logger = Logger.getLogger(GenericDAOFactory.class);

    /**
     * The Database to be used.
     */
    public static final String DATABASE = "ETCC_HCTRA_DATABASE";

    private static final String DEFAULT_DB = "oracle";
    private static final String databaseName;
    /**
     * Map of Abstract DAOs to the current instances of the DAOs.
     */
    private static Map<Class<? extends BaseDAO>, Class<? extends BaseDAO>> daoMap = 
        new HashMap<Class<? extends BaseDAO>, Class<? extends BaseDAO>>();


    static {
        databaseName = getDatabaseName();
        logger.info("Database type set to: " + databaseName);
    }

    /**
     * Returns the database name used for package and DAO class names.
     * @return the database name used for package and DAO class names
     */
    protected static String getDatabaseName() {
        //Allow override first by a command line property, followed by an ENV Var.
        final String database = System.getProperty(DATABASE, System.getenv(DATABASE));
        if (database == null || database.trim().length() == 0){
            return DEFAULT_DB;
        }//else
        return database.trim().toLowerCase();
    }

    /**
     * Constructor. Should only be called by the Child classes, or DAOFactory.
     */
    protected GenericDAOFactory() {
        // end <init>
    }

    @Override
    protected <T> Class<?> getClass(String classSimpleName) throws ClassNotFoundException {
        //Pattern is com.etcc.csc.dao.<databasename>.<Databasename>DaoTypeName.
        String className = DAO_PACKAGE + '.' +
            databaseName + '.' + Character.toUpperCase(databaseName.charAt(0)) + databaseName.substring(1) +
            classSimpleName;
        if (logger.isTraceEnabled()){
            logger.trace("Loading class: " + className);
        }
        return this.getClass().getClassLoader().loadClass(className);
    }

    /**
     * Returns the class that implements the specified DAO type.
     * @param <T> The DAO superclass type
     * @param daoType The DAO superclass type for which an implementation is needed
     * @return the database specific class implementing the requested DAO type
     * @see #getClass(String)
     * @see Class#getSimpleName()
     */
    @SuppressWarnings("unchecked")
    protected <T> Class<? extends BaseDAO> getDaoClass(Class<T> daoType) {
        try {
            return (Class<? extends BaseDAO>) getClass(daoType.getSimpleName());
        } catch (ClassNotFoundException e) {
            String msg = "Unable to find DAO for: " + daoType.getName();
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * @see #getDaoClass(Class)
     */
    @Override
    public <T extends BaseDAO> T getDAO(Class<T> daoType) {
        Class<? extends BaseDAO> clazz = daoMap.get(daoType);
        if (clazz == null) {
            //Don't have yet.
            clazz = getDaoClass(daoType);
            //Store for next time.
            daoMap.put(daoType, clazz);
        }
        try {
            @SuppressWarnings("unchecked")
            T dao = (T)clazz.newInstance();
            setupDAO(dao);
            return dao;
        } catch (RuntimeException e){
            throw e;
        } catch (Exception e) {
            String msg = "Unable to instantiate DAO for: " + daoType.getName();
            throw new EtccSysException(msg, e);
        }
        // end getDAO
    }

    /**
     * Setup the DAO.
     * @param dao
     */
    protected void setupDAO(final BaseDAO dao){
    	logger.info("GenericDAOFactory.setupDAO setConnection for DAO ["+ dao.getClass().getCanonicalName() +"]");
        dao.setConnection(ConnectionUtil.getDbConnection());
    }
}
