/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.DbLoggerDAO;
import com.etcc.csc.service.DbLoggerInterface;

/**
 * Exposes Violation functionality through web service.
 */
//TODO: To activate this as a functional WebService, either add this class's entry to the services.xml file, or if
//JSR 181 is in use, annotate.
public class DbLogger implements DbLoggerInterface {
    private static final Logger logger = Logger.getLogger(DbLogger.class);

    public String logError(String message, StackTraceElement[] stack) throws EtccException {
        logger.trace("Start");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DbLoggerDAO dbLoggerDao = daoFactory.getDAO(DbLoggerDAO.class);
        String result = dbLoggerDao.logError(message, stack);
        return result;
    }

    public String logError(String message, String stack) throws EtccException {
        logger.trace("Start");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DbLoggerDAO dbLoggerDao = daoFactory.getDAO(DbLoggerDAO.class);
        String result = dbLoggerDao.logError(message, stack);
        return result;
    }

    public String logSecurityViolation(String message) throws EtccException {
        logger.trace("Start");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DbLoggerDAO dbLoggerDao = daoFactory.getDAO(DbLoggerDAO.class);
        String result = dbLoggerDao.logSecurityViolation(message);
        return result;
    }
}
