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
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.validation.CreditCardValidator;

/**
 * CreditCard is the Component that's published for web service CreditCard, which provides the following operations:
 * <ul>
 * <li>getAccountCreditCard
 * <li>getCreditCard
 * <li>getCreditCardName
 * <li>updateAccountCreditCard
 * </ul>
 * 
 * @author Wade Wang
 * @author (task 488) Stephen Davidson
 * @since phase 1
 */
@Stateless(name = "com/etcc/CreditCard")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "CreditCard", targetNamespace = "http://ws.csc.etcc.com/CreditCard")
public class CreditCard implements CreditCardInterface {

    //@WebMethod(operationName = "getAccountCreditCard", action = "urn:getAccountCreditCard")
    //@WebResult(name = "acountCreditCard")
    public AccountCreditCardDTO getAccountCreditCard(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {

        // validation - none needed
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CreditCardDAO ccDao = daoFactory.getDAO(CreditCardDAO.class);
        return ccDao.getAccountCreditCard(acctLoginDto);
    }

    //@WebMethod(operationName = "updateAccountCreditCard", action = "urn:updateAccountCreditCard")
    //@WebResult(name = "paymentResult")
    public PaymentResultDTO updateAccountCreditCard(AccountLoginDTO acctLoginDto,
            AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen) throws EtccException,
            EtccSecurityException {

        // validation
        CreditCardValidator validator = new CreditCardValidator(acctCreditCardDto);
        validator.isValid();

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CreditCardDAO ccDao = daoFactory.getDAO(CreditCardDAO.class);
        return ccDao.updateAccountCreditCard(acctLoginDto, acctCreditCardDto, fromPaymentScreen);
    }

    //@WebMethod(operationName = "getCreditCardTypes", action = "urn:getCreditCardTypes")
    //@WebResult(name = "creditCards")
    public Collection<CreditCardDTO> getCreditCardTypes() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CreditCardDAO ccDao = daoFactory.getDAO(CreditCardDAO.class);
        Collection<CreditCardDTO> col = ccDao.getCreditCardTypes();
        return col;
    }
}
