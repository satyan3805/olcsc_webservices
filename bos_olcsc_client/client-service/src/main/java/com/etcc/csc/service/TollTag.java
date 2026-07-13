/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.TolltagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PersonalInfoResponse;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;
import com.etcc.csc.service.TolltagInterface;
import com.etcc.csc.validation.TollTagValidator;

/**
 * Copied from OLCSCService com.etcc.csc.service.TollTag
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/TollTag")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "TollTag", targetNamespace = "http://ws.csc.etcc.com/TollTag")
public class TollTag implements TolltagInterface {

    //@WebMethod(operationName = "createAccount", action = "urn:createAccount")
    //@WebResult(name = "accountLogin")
    public AccountLoginDTO createAccount(String firstName, String middleInitial, String lastName, String companyName,
            String email, String loginId, 
            @Sensitive(displayValue="<SecurityQuestion>") String securityQuestion, 
            @Sensitive(displayValue="<SecurityAnswer>") String securityAnswer, String ipAddress,
            String jsessionId, String userEnv) throws EtccErrorMessageException, EtccException {
        // validation
        TollTagValidator.validateCreateAccount(firstName, middleInitial, lastName, companyName, email, loginId,
        securityQuestion, securityAnswer, ipAddress, jsessionId, userEnv);

        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).createAccount(firstName, middleInitial, lastName,
                companyName, email, loginId, securityQuestion, securityAnswer, ipAddress, jsessionId, userEnv);

    }

    //@WebMethod(operationName = "setPersonalInfo", action = "urn:setPersonalInfo")
    //@WebResult(name = "personalInfo")
    public PersonalInfoResponse setPersonalInfo(long accountId, String address, String addressLine2, String city,
            String state, String zipcode, String zipPlus4, String homePhoneNumber, String workPhoneNumber,
            String workPhoneExt, String dlNumber, String dlState, String monthlyStmtFlag, int msId, String dbSessionId,
            String loginId, String ipAddress, long transactionId, boolean checkDuplicateDriverLicense, Long posId)
            throws EtccErrorMessageException, EtccException {

        // validation
        TollTagValidator.validatePersonalInfo(accountId, address, addressLine2, city, state, zipcode, zipPlus4,
                homePhoneNumber, workPhoneNumber, workPhoneExt, dlNumber, dlState, monthlyStmtFlag, msId, dbSessionId,
                loginId, ipAddress, transactionId, checkDuplicateDriverLicense, posId);

        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).setPersonalInfo(accountId, address, addressLine2, city,
                state, zipcode, zipPlus4, homePhoneNumber, workPhoneNumber, workPhoneExt, dlNumber, dlState,
                monthlyStmtFlag, msId, dbSessionId, loginId, ipAddress, transactionId, checkDuplicateDriverLicense,
                posId);
    }

    //@WebMethod(operationName = "setPaymentInfo", action = "urn:setPaymentInfo")
    public void setPaymentInfo(long acctId, String dbSessionId, String ipAddress, String loginId, long rtlTrxnId,
            String name, String adddress, String addressLine2, String city, String state, String zipcode, String plus4,
            @Sensitive(displayValue="<CreditCard>")String cardNumber, 
            CreditCardDTO.CreditCardType cardType, 
            @Sensitive(displayValue="MM")String expirationMonth, 
            @Sensitive(displayValue="YYYY") String expirationYear)
            throws EtccErrorMessageException, EtccException {

        // validation
        TollTagValidator.validatePaymentInfo(acctId, dbSessionId, ipAddress, loginId, rtlTrxnId, name, adddress, addressLine2,
                city, state, zipcode, plus4, cardNumber, cardType, expirationMonth, expirationYear);

        DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).setPaymentInfo(acctId, dbSessionId, ipAddress, loginId, rtlTrxnId,
                name, adddress, addressLine2, city, state, zipcode, plus4, cardNumber, cardType, expirationMonth,
                expirationYear);
    }

    //@WebMethod(operationName = "getTagPayInfo", action = "urn:getTagPayInfo")
    //@WebResult(name = "payInfo")
    public double getTagPayInfo(long accountId, String dbSessionId, String loginId, String ipAddress,
            long transactionId, Long posId) throws EtccErrorMessageException, EtccException {
        
        // validation - none needed - as all parameters coming from AccountLoginDTO i.e. accountLoginDTO =
        // SessionUtil.getSessionAccountLogin(session);
        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).getTagPayInfo(accountId, dbSessionId, loginId, ipAddress,
                transactionId, posId);
    }

    //@WebMethod(operationName = "makePayment", action = "urn:makePayment")
    //@WebResult(name = "successful")
    @Deprecated
    public boolean makePayment(long accountId, String dbSessionId, String loginId, String ipAddress,
            long transactionId, double paymentAmount, 
            @Sensitive(displayValue="VVV")String cardCode, String paymentType, 
            @Sensitive(displayValue="<CreditCard>")String cardNumber,
            @Sensitive(displayValue="MM")String expireMonth, 
            @Sensitive(displayValue="YYYY")String expireYear, 
            String nameOnCard, String address, String address2, String city,
            String state, String zipCode, String plus4, String cardSecurityCode) throws EtccErrorMessageException,
            EtccException {

        // validation - none - this function is deprecated and therefore not used
        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).makePayment(accountId, dbSessionId, loginId, ipAddress,
                transactionId, paymentAmount, cardCode, paymentType, cardNumber, expireMonth, expireYear, nameOnCard,
                address, address2, city, state, zipCode, plus4, cardSecurityCode);
    }

    //@WebMethod(operationName = "loginCreation", action = "urn:loginCreation")
    //@WebResult(name = "login")
    @Deprecated
    public String loginCreation(BigDecimal accountId, String loginType, String dbSessionId, String loginId,
            String ipAddress, String firstName, String middleInitial, String lastName, String email)
            throws EtccErrorMessageException, EtccException {

        // validation - none - this function is deprecated and therefore not used
        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).loginCreation(accountId, loginType, dbSessionId, loginId,
                ipAddress, firstName, middleInitial, lastName, email);
    }

	public OlcMarketSourceRecBean[] getMarketSource() throws EtccException {
        // validation - none needed
        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).getMarketSource();
    }
}
