/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.dao;

import com.etcc.csc.common.EtccException;

/**
 * Unwraps the source exception from EtccConnectionException and rethrows it.  Allows us to deal with Objects such as
 * Menu which do NOT throw EtccExceptions.
 * @author (task 488) Stephen Davidson
 */
public aspect PreserveEtccException {
    declare precedence: PreserveEtccException, ConnectionController;
    
    after() throwing(EtccConnectionException e) throws EtccException 
    : execution (* *.*(..) throws EtccException) 
    && execution(public * com.etcc.csc.service.*.*(..))
    && target(com.etcc.csc.service.ServiceInterface+)
    //HACK: The above line is acting like an 'OR', not an 'AND'
    && !target(com.etcc.csc.dao.BaseDAO+)

    {
        Throwable cause = e.getCause();
        if (cause instanceof EtccException){
            throw (EtccException)cause;
        }
        if (cause instanceof RuntimeException){
            throw (RuntimeException)cause;
        }
        throw e;
    }
}
