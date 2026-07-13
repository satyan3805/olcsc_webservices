/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.DbLoggerInterface;

/**
 * Wrapper between the web service stub and the web service client.
 */
//TODO: Does not currently send any calls to the WebServices, but instead sends everything to the local log file.
public class DbLoggerDelegate implements DbLoggerInterface {
    private static final Logger logger = Logger.getLogger(DbLoggerDelegate.class);

    public String logError(String message, StackTraceElement[] stack) throws EtccException {
        try {
            //UtilWsSoapHttpPortClient stub = new UtilWsSoapHttpPortClient();
            //stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "utilWsSoapHttpPort");
            logger.error(message);
            logger.error(Arrays.toString(stack));
            return "123";
            // return new DbLogger().logError(message, stack);
        } catch (Throwable t) {
            throw new EtccException("Error running logError: " + t.getMessage(), t);
        }
    }

	public String logError(String message, String stack) throws EtccException {
        try {
            logger.error(message);
            logger.error(stack);
            return "123";
            // return new DbLogger().logError(message, stack);
        } catch (Throwable t) {
            throw new EtccException("Error running logError: " + t.getMessage(), t);
        }
	}

    public String logSecurityViolation(String message) throws EtccException {
        try {
            //UtilWsSoapHttpPortClient stub = new UtilWsSoapHttpPortClient();
            //stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "utilWsSoapHttpPort");
            //return stub.logSecurityViolation(message);
             logger.info(message);
             return "123";
            // return new DbLogger().logSecurityViolation(message);
        } catch (Throwable t) {
            throw new EtccException("Error running logSecurityViolation: " + t.getMessage(), t);
        }
    }
}
