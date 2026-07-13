/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Contains base dao functionality. Procedure return values;<pre>
 * -1 : Security Exception
 *  0 : Database Exception has occurred.
 *  1 : General Success.  Any out parameters in the SQL should be populated.
 *  >1 for any other integer return codes.
 *  </pre>
 */
public abstract class BaseDAO {
    protected static final String NULL_STRING = "null";

    //Need to know WHICH DAO is executing if anything gets logged.
    private final Logger logger = Logger.getLogger(getClass());

    //Set & Closed implicitly by AOP Injection.  See ConnectionController.
    protected Connection conn;

    /**
     * Set the type map to assist the connection with mapping of custom SQL types.
     * Note: this method relies on the DAO Factory to invoke the
     * <tt>getDbTypeMap</tt> database utility class method.
     * @return the updated connection type map
     * @throws SQLException
     * @see Connection#setTypeMap(Map)
     * @see DAOFactory#invokeUtility(String)
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Class<?>> setTypeMap() throws SQLException {
        Map<String, Class<?>> theTypeMap = this.conn.getTypeMap();
        Map<String, Class<?>> dbTypeMap = null;
        try {
            dbTypeMap = (Map<String, Class<?>>) DAOFactory.getDAOFactory().invokeUtility("getDbTypeMap");
        } catch (UnsupportedOperationException e) {
            this.logger.debug("DB-specific type map method not implemented: " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            String message = "DB-specific type map method invocation error.";
            this.logger.debug(message, t);
            SQLException sqle = new SQLException(message);
            sqle.initCause(t);
            throw sqle;
        }
        if (theTypeMap == null)
            theTypeMap = dbTypeMap;
        else
            theTypeMap.putAll(dbTypeMap);
        this.conn.setTypeMap(theTypeMap);
        return theTypeMap;
    }

    /**
     * Sets the database connection for the dao.
     * 
     * @param conn
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * Returns the current database connection for this dao.
     * 
     * @return the current database connection.
     */
    public Connection getConnection() {
        return this.conn;
    }
    
    /**
     * Closes the ResultSet.
     * @param rs the ResultSet to close.
     */
    protected void close(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                this.logger.warn("BaseDAO: Exception closing ResultSet: " + e.getMessage(), e);
            }
        }
        //end close
    }
    
    protected void close(Statement stmt){
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                this.logger.warn("BaseDAO: Exception closing Statement: " + e.getMessage(), e);
            }
        }
    }
    
    /**
     * Clears the cache elements sent if current time is after the expiration date.
     * @param expires When the cache expires.
     * @param cache List/Collection of Cache's to clear.
     * @return Next expiration date.
     */
    protected Date checkAndClearCache(Date expires, Collection<?>... cache ){
        Date currentDate = new Date();
        if (checkExpires(currentDate, expires)){
            synchronized(getClass()){
                if (!checkExpires(currentDate, expires)){
                    //Another thread already reset the cache, so ABORT.
                    return getNextExpire();
                }//else
                for (Collection<?> collection : cache) {
                    collection.clear();
                }
                this.logger.trace("BaseDAO: Cache cleared.");
                return getNextExpire();
            }
        }
        return expires;
    }
    
    /**
     * Clears the cache elements sent if current time is after the expiration date.
     * @param expires When the cache expires.
     * @param cache List/Collection of Cache's to clear.
     * @return Next expiration date.
     */
    protected Date checkAndClearCache(Date expires, Map<?,?>... cache ){
        Date currentDate = new Date();
        if (checkExpires(currentDate, expires)){
            synchronized(getClass()){
                if (!checkExpires(currentDate, expires)){
                    //Another thread already reset the cache, so ABORT.
                    return getNextExpire();
                }//else
                for (Map<?,?> map : cache) {
                    map.clear();
                }
                this.logger.trace("BaseDAO: Cache cleared.");
                return getNextExpire();
            }
        }
        return expires;
    }
    
    protected boolean checkExpires(Date currentDate, Date expires){
        return expires == null || expires.before(currentDate);
    }
    
    protected Date getNextExpire(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 1);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        return now.getTime();
    }

    /**
     * Set the string on the callable statement.  If the specified value is
     * <tt>null</tt>, then set the parameter to <i>null</i>.
     * @param cstmt callable statement to set
     * @param index position of parameter
     * @param value parameter value
     * @throws SQLException
     * @see CallableStatement#setString(int, String)
     * @see CallableStatement#setNull(int, int)
     */
    protected static void setStringCheckNull(CallableStatement cstmt, int index, String value) throws SQLException {
    	if (value == null) {
    		cstmt.setNull(index, Types.VARCHAR);
    	} else {
    		cstmt.setString(index, value);
    	}
    }
}// end BaseDAO
