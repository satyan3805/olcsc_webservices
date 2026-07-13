package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;

/**
 * Contains methods for persisting errors to the database.
 */
public interface DbLoggerInterface extends BusinessObjectInterface{

    /**
     * Logs an error to the database.
     * @param message
     * @param stack
     * @throws EtccException
     */
    String logError(String message, String stack, String sessionId, String ip) throws EtccException;

    /**
     * Logs security violation to the database.
     * @param message
s     * @throws EtccException
     */
    String logSecurityViolation(String message) 
        throws EtccException;
}
