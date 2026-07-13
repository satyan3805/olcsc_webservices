/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;

/**
 * 
 * @author Milosh Boroyevich
 */
@Stateless(name = "com/etcc/Velcro")
@StatelessDeployment(transactionTimeout=120)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Velcro", targetNamespace = "http://ws.csc.etcc.com/Velcro")
public class Velcro implements VelcroInterface {

    //@WebMethod(operationName = "getVelcroInfo", action = "urn:getVelcroInfo")
    //@WebResult(name = "velcro")
    public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        VelcroDAO velcroDao = daoFactory.getDAO(VelcroDAO.class);
        return velcroDao.getVelcroInfo(acctLoginDto);
    }

    //@WebMethod(operationName = "submitVelcroRequest", action = "urn:submitVelcroRequest")
    //@WebResult(name = "result")
    public ResultDTO submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty) throws EtccException,
            EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        VelcroDAO velcroDao = daoFactory.getDAO(VelcroDAO.class);
        return velcroDao.submitVelcroRequest(acctLoginDto, qty);
    }

    //@WebMethod(operationName = "getVelcroReceiptPDF", action = "urn:getVelcroReceiptPDF")
    //@WebResult(name = "receiptUrl")
    public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        VelcroDAO velcroDao = daoFactory.getDAO(VelcroDAO.class);
        return velcroDao.getVelcroReceiptPDF(acctLoginDto);
    }
}
