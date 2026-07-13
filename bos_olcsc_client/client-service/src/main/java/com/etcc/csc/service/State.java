/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dto.StateDTO;

/**
 * Service to load the states. Copied from OLCSCService com.etcc.csc.service.State
 * 
 * @author (task 488) Stephen Davidson
 * 
 */
@Stateless(name="com/etcc/State")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "State", targetNamespace = "http://ws.csc.etcc.com/State")
public class State implements StateInterface {

    //@WebMethod(operationName = "getStates", action = "urn:getStates")
    //@WebResult(name = "states")
    public StateDTO[] getStates() throws EtccException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        StateDAO stateDao = daoFactory.getDAO(StateDAO.class);
        StateDTO[] col = stateDao.getStates();
        return col;
    }

}
