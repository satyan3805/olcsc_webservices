package com.etcc.csc.oracleerrortest.service;

import java.util.Collection;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.oracleerrortest.ErrorTestInterface;
import com.etcc.csc.oracleerrortest.dao.ErrorTestDAO;

public class ErrorTest implements ErrorTestInterface {
    Logger logger = Logger.getLogger(ErrorTest.class);

    public ErrorTest() {
    }
    
    public Collection performETest(String param1) throws EtccException {
    
        logger.debug("Start");
        try {
            DAOFactory daoFactory = DAOFactory.getDAOFactory();
            ErrorTestDAO dao = daoFactory.getErrorTestDAO();
            return dao.performETest(param1);
        } catch (Exception e) {
            logger.error("Error: " + e, e);
            throw new EtccException(e);
        }
    }
    
}