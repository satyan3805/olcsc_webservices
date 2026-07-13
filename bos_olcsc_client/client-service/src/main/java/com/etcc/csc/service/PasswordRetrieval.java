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
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.validation.PasswordRetrievalValidator;

/**
 * PasswordRetrieval is the Component that's published for web service passwordretrieval.ws,
 * which provides the following operations:
 * <ul>
 * <li>addEmailAddress
 * <li>addSecurityQuestionAnswer
 * <li>retrieveAccountInfo
 * <li>validateSecurityAnswer
 * </ul>
 * 
 * @author      Wade Wang     
 * @since       phase 1
 */
@Stateless(name="com/etcc/PasswordRetrieval")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "PasswordRetrieval", targetNamespace = "http://ws.csc.etcc.com/PasswordRetrieval")
public class PasswordRetrieval implements PasswordRetrievalInterface{

    //@WebMethod(operationName = "getSecurityQuestions", action = "urn:getSecurityQuestions")
   //@WebResult(name = "onlineAccessSetup")
    public OnlineAccessSetupDTO retrieveAccountInfo(OnlineAccessSetupDTO onlineAccessDTO,
    String ipAddress, String sessionId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException {

            //Call this validator before calling dao, if failure, throw EtccException with warning
//commenting out...this validator is not working.  UI validation
//PasswordRetrievalValidator.retrieveAccountInfoValidator(onlineAccessDTO, ipAddress, sessionId, userEnvDto);

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

        return  passwordRetrievalDAO.retrieveAccountInfo(onlineAccessDTO, ipAddress, sessionId, userEnvDto);
    }

    //@WebMethod(operationName = "validateSecurityAnswer", action = "urn:validateSecurityAnswer")
   //@WebResult(name = "onlineAccessSetup")
    public OnlineAccessSetupDTO validateSecurityAnswer(String sessionId, String ipAddress,
    OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException {
       //Call this validator before calling dao, if failure, throw EtccException with warning
       PasswordRetrievalValidator.validateSecurityAnswerValidator(sessionId, ipAddress, onlineAccessDTO);

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

        return  passwordRetrievalDAO.validateSecurityAnswer(sessionId, ipAddress,onlineAccessDTO);
    }

    //@WebMethod(operationName = "addSecurityQuestionAnswer", action = "urn:addSecurityQuestionAnswer")
   //@WebResult(name = "onlineAccessSetup")
    public OnlineAccessSetupDTO addSecurityQuestionAnswer(String sessionId, String ipAddress,
    OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException {
        //Call this validator before calling dao, if failure, throw EtccException with warning
        PasswordRetrievalValidator.addSQAnswerValidator(sessionId, ipAddress, onlineAccessDTO);


        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

        return  passwordRetrievalDAO.addSecurityQuestionAnswer(sessionId, ipAddress,onlineAccessDTO);
    }
    
    //@WebMethod(operationName = "addEmailAddress", action = "urn:addEmailAddress")
   //@WebResult(name = "onlineAccessSetup")
    public OnlineAccessSetupDTO addEmailAddress(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException {
        //Call this validator before calling dao, if failure, throw EtccException with warning
        PasswordRetrievalValidator.addEmailAddressValidator(sessionId, ipAddress, onlineAccessDTO);


        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

        return  passwordRetrievalDAO.addEmailAddress(sessionId, ipAddress,onlineAccessDTO);
    }

    //@WebMethod(operationName = "changePassword", action = "urn:changePassword")
   //@WebResult(name = "changePasswordMessagess")
    public Collection<String> changePassword(AccountLoginDTO acctLoginDTO, 
            @Sensitive String oldPwd, 
            @Sensitive String pwd) throws EtccException, EtccSecurityException
    {

        //Call this validator before calling dao, if failure, throw EtccException with warning
         PasswordRetrievalValidator.changePasswordValidator(acctLoginDTO, oldPwd, pwd);

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);
        Collection<String> col = passwordRetrievalDAO.changePassword(acctLoginDTO, oldPwd, pwd);
        return col;
    }

    //@WebMethod(operationName = "validateEmailAddress", action = "urn:validateEmailAddress")
   //@WebResult(name = "validateEmailAddress")
	public OnlineAccessSetupDTO retrieveEmailAddressInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto) throws EtccException,
			EtccSecurityException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

	        return  passwordRetrievalDAO.retrieveEmailAddressInfo(onlineAccessDTO, ipAddress, sessionId, userEnvDto);
	}

	 //@WebMethod(operationName = "validationData", action = "urn:validationData")
   //@WebResult(name = "validationData")
	public OnlineAccessSetupDTO validationData(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto) throws EtccException,
			EtccSecurityException {
			  DAOFactory daoFactory = DAOFactory.getDAOFactory();
		        PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

		        return  passwordRetrievalDAO.validationData(onlineAccessDTO, ipAddress, sessionId, userEnvDto);
	}

}
