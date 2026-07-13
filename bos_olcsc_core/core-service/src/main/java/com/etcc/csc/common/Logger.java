package com.etcc.csc.common;

import org.apache.log4j.Level;

/**
 * Wrapper for the Logging mechanism.
 */
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
    public static Logger getLogger(Class clazz) {
        Logger logger = new Logger(org.apache.log4j.Logger.getLogger(clazz));
        return logger;
    }

    public void debug(Object message) {
        logger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        logger.debug(message, t);
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void info(Object message, Throwable t) {
        logger.info(message, t);
    }

    public void warn(Object message) {
        logger.warn(message);
    }

    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

    public void error(Object message) {
        logger.error(message);
    }

    public void error(Object message, Throwable t) {
        logger.error(message, t);
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
        logger.error(message, t);
        return result;
    }

    public void fatal(Object message) {
        logger.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        logger.fatal(message, t);
    }

    // generic printing method:

    public void log(Level l, Object message) {
        logger.log(l, message);
    }

}
