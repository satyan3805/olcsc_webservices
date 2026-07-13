package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;

/**
 * Contains methods for persisting errors to the database.
 */
public interface DbLoggerInterface {

    /**
     * Logs an error to the database.
     * @param message
     * @param stack
     * @throws EtccException
     */
    String logError(String message, StackTraceElement[] stack) throws EtccException;

    /**
     * Logs an error to the database.
     * @param message
     * @param stack
     * @throws EtccException
     */
    String logError(String message, String stack) throws EtccException;

    /**
     * Logs security violation to the database.
     * @param message
     * @throws EtccException
     */
    String logSecurityViolation(String message) throws EtccException;
}
