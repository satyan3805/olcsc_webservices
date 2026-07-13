/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * Service Interface Implementation for getting Security Questions. Based on the original
 * version from OLCSCService.
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/SecurityQuestion")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "SecurityQuestion", targetNamespace = "http://ws.csc.etcc.com/SecurityQuestion")
public class SecurityQuestion implements SecurityQuestionInterface {

    //@WebMethod(operationName = "getSecurityQuestions", action = "urn:getSecurityQuestions")
    //@WebResult(name = "securityQuestions")
    public Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        SecurityQuestionDAO securityQuestionDao = daoFactory.getDAO(SecurityQuestionDAO.class);
        Collection<SecurityQuestionDTO> col = securityQuestionDao.getSecurityQuestions();
        return col;
   }
}
