/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.util.OLCSCContextListener;
import com.etcc.csc.util.PropertyUtil;

/**
 * Copied from the original Util in OLSC Service.
 */
public class ConnectionUtil {
    private static final Logger logger = Logger.getLogger(ConnectionUtil.class);

    private static final ConnectionUtil instance = new ConnectionUtil();
    
    /**
     * Singleton, should not be instantiated, but can be extended.
     */
    protected ConnectionUtil() {
        //end <init>
    }

    /**
     * Returns a database connection.
     * 
     * @return
     */
    static Connection getDbConnection() {
        String appDataSource = OLCSCContextListener.getAppDataSource(); // Constants.APP_DATASOURCE
        return getDbConnection(appDataSource);
    }

    /**
     * Returns a connection to a specific database.
     * 
     * @param connName the name of the Connection to get.
     * @return the Database connection.
     * @throws EtccSysException Thrown if the Datasource can not be found via JNDI lookup.
     */
    static Connection getDbConnection(String connName) {
        return instance.getConnection(connName);
    }
    
    protected Connection getConnection(final String connName){
        Context initialContext = null;
        try {
            logger.debug("Getting db connection for " + connName);
            initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(connName);
            final Connection connection = datasource.getConnection();
            logger.info("ConnectionUtil.getConnection Got a connection ["+ connection + "] from JNDI ["+ connName +"] lookup");
            return connection;
        } catch (NamingException e) {
            throw new EtccSysException("Unable to obtain data source connection '" + connName + "': " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new EtccSysException("Unable to obtain data source connection '" + connName + "': " + e.getMessage(), e);
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    // Finished anyways, so just log and carry on.
                    logger.warn("Exception closing initial context: " + e.getMessage(), e);
                }//end catch(NamingException
            }//end if (initialContext !=null
        }//end finally
        //end getDbConnection
    }

    public static String trimErrorMessage(String errMsg) {
        try {
            return errMsg.substring(0, errMsg.lastIndexOf("-"));
        } catch (Exception e) {
            return errMsg;
        }
    }

    /**
     * Gets the field mapping for the message from the ErrorMapping properties file.
     * 
     * @param msg the key for which to get the message
     * @return the message
     * @deprecated Should be called in the UI tier to return a language-specific string based on locale.
     */
    @Deprecated
    public static String getFieldMapping(String msg) {
        // TODO: Since this method is deprecated, what should be used instead?
        try {
            String key = msg.substring(msg.lastIndexOf("-") + 1);
            Properties props = PropertyUtil.loadProperties("ErrorMapping");
            return props.getProperty(key);
        } catch (Exception e) {
            logger.error("Error loading ErrorMapping.properties: " + e.getMessage());
            return null;
        }
    }
}
