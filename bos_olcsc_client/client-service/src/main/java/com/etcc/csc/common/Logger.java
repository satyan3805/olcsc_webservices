/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

import org.apache.log4j.Level;

/**
 * Wrapper for the Logging mechanism.
 * @deprecated use {@link org.apache.log4j.Logger}
 */
@Deprecated
public class Logger {
    private org.apache.log4j.Logger logger;

    /**
     * Must use the getLogger methods to get an instance of Logger.
     */
    private Logger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    /**
     * Returns a logger for a given name.
     * @param name
     * @return
     */
    public static Logger getLogger(String name) {
        Logger logger = new Logger(org.apache.log4j.Logger.getLogger(name));
        return logger;
    }

    /**
     * Returns a logger for a given class.
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = new Logger(org.apache.log4j.Logger.getLogger(clazz));
        return logger;
    }

    public void trace(Object message){
        this.logger.trace(message);
    }
    
    public void trace(Object message, Throwable t){
        this.logger.trace(message, t);
    }

    public void debug(Object message) {
        this.logger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        this.logger.debug(message, t);
    }

    public void info(Object message) {
        this.logger.info(message);
    }

    public void info(Object message, Throwable t) {
        this.logger.info(message, t);
    }

    public void warn(Object message) {
        this.logger.warn(message);
    }

    public void warn(Object message, Throwable t) {
        this.logger.warn(message, t);
    }

    public void error(Object message) {
        this.logger.error(message);
    }

    public void error(Object message, Throwable t) {
        this.logger.error(message, t);
    }

    /**
     * If the logToDb flag is true, logs the error to the database returning
     * the issue number, in addition to the normal logging of error.
     *
     * @param message
     * @param t
     * @param logToDb
     * @return
     */
    public String error(Object message, Throwable t, boolean logToDb) {
        String result = "123";
        if (logToDb) {
            // TODO: call database api to log this error
        }
        this.logger.error(message, t);
        return result;
    }

    public void fatal(Object message) {
        this.logger.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        this.logger.fatal(message, t);
    }

    // generic printing method:

    public void log(Level l, Object message) {
        this.logger.log(l, message);
    }
    
    public boolean isDebugEnabled() {
        if (this.logger == null)
            return false;
        return this.logger.isDebugEnabled();
    }

    public boolean isTraceEnabled() {
        if (this.logger == null)
            return false;
        return this.logger.isTraceEnabled();
    }
    
    public boolean isInfoEnabled() {
        if (this.logger == null)
            return false;
        return this.logger.isInfoEnabled();
    }

}
