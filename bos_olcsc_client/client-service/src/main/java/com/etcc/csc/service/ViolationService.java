/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;

import javax.ejb.Stateless;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.CheckEligibilityResponseDTO;
import com.etcc.csc.dto.DocumentDTO;
import com.etcc.csc.dto.GetViolationResponseDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.MakeViolationPaymentRequest;

import com.etcc.csc.dto.PaymentPlanDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SearchOpenViolationResponse;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Violation Service implementation.
 *
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Copied from OLCSCService com.etcc.csc.violation.ViolationInterface
@Stateless(name = "com/etcc/ViolationService")
@StatelessDeployment(transactionTimeout=90)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "ViolationService", targetNamespace = "http://ws.csc.etcc.com/ViolationService")
public class ViolationService implements ViolationServiceInterface {

    //@WebMethod(operationName = "loginViolator", action = "urn:loginViolator")
    //@WebResult(name = "violator")
    public ViolatorDTO loginViolator(AccountLoginDTO acctLoginDto, String sessionId, String ipAddress,
            UserEnvDTO userEnvDto, String invoiceId, String licPlate, String licState, String dbSessionId) throws EtccException,
            EtccSecurityException {
        return violationDAO().loginViolator(acctLoginDto, sessionId, ipAddress, userEnvDto, invoiceId, licPlate, licState, dbSessionId);
    }
    
    
   public ViolatorDTO loginViolatorPmtPlan(AccountLoginDTO acctLoginDto, String sessionId, String ipAddress,
            UserEnvDTO userEnvDto, String licPlate, String licState,String paymentPlanId, String dbSessionId) throws EtccException,EtccSecurityException{
	   
	   return violationDAO().loginViolatorPmtPlan(acctLoginDto, sessionId, ipAddress, userEnvDto,licPlate, licState,  paymentPlanId,dbSessionId);
   }

    /**
     * @see ViolationDAO#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)
     */
    //@WebMethod(operationName = "makePayment", action = "urn:makePayment")
    //@WebResult(name = "violator")
//    public ViolatorDTO makePayment(@ReturnParam ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress)
//            throws EtccException, EtccSecurityException {
//        BillingInfoDTO billingDto = new BillingInfoDTO();
//        billingDto.setPaymentMethod(paymentMethod);
//        try {
//            AccountValidator.validateSetBillingInfo(violatorDTO.getAccountLoginDTO(), paymentMethod);
//            violatorDTO.clearErrors();
//            violatorDTO.clearConfirmationCodes();
//            violatorDTO = violationDAO().makePayment(violatorDTO, paymentMethod, ipAddress);
//        } finally {
//            if (!violatorDTO.getConfirmationCodes().isEmpty())
//                violatorDTO.clearFailedPayments();
//        }
//        return violatorDTO;
//    }

    /**
     * @see ViolationDAO#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)
     */
    //@WebMethod(operationName = "makePaymentService", action = "urn:makePaymentService")
    //@WebResult(name = "violator")
//    public ViolatorDTO makePaymentService(@ReturnParam ViolatorDTO violatorDTO, AccountCreditCardDTO paymentMethod, String ipAddress)
//            throws EtccException, EtccSecurityException {
//        violatorDTO.getShoppingCart().initCartItems(violatorDTO);
//        return makePayment(violatorDTO, paymentMethod, ipAddress);
//    }

    /**
     * @see ViolationDAO#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)
     */
    //@WebMethod(operationName = "makePaymentService", action = "urn:makePaymentService")
    //@WebResult(name = "violator")
//    public ViolatorDTO makePaymentService(@ReturnParam ViolatorDTO violatorDTO, AccountEFTDTO paymentMethod, String ipAddress)
//            throws EtccException, EtccSecurityException {
//        violatorDTO.getShoppingCart().initCartItems(violatorDTO);
//        return makePayment(violatorDTO, paymentMethod, ipAddress);
//    }

    //@WebMethod(operationName = "getPaymentPlan", action = "urn:getPaymentPlan")
    //@WebResult(name = "paymentPlan")
    public PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID, String licPlate, String sessionID,
            String ipAddress, long acctID) throws EtccException, EtccSecurityException {
        return violationDAO().getPaymentPlan(licKey, invoiceID, licPlate, sessionID, ipAddress, acctID);
    }

    //@WebMethod(operationName = "getInvoices", action = "urn:getInvoices")
    //@WebResult(name = "violator")
    public ViolatorDTO getInvoices(AccountLoginDTO accountLoginDTO, String ipAddress, String licPlate, String licState,
            AgencyEnum agency) throws EtccException, EtccSecurityException {
        return violationDAO().getInvoices(accountLoginDTO, ipAddress, licPlate, licState, agency);
    }

    //@WebMethod(operationName = "getInvoiceDoc", action = "urn:getInvoiceDoc")
    //@WebResult(name = "document")
    public DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoice) throws EtccException,
            EtccSecurityException {
        return violationDAO().getInvoiceDoc(violatorDTO, invoice);
        // end getInvoiceDoc

    }

    //@WebMethod(operationName = "emailReceipt", action = "urn:emailReceipt")
    //@WebResult(name = "violator")
//    public ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod, String email)
//            throws EtccException, EtccSecurityException {
//        return violationDAO().emailReceipt(violator, paymentMethod, email);
//    }

    private ViolationDAO violationDAO() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        ViolationDAO dao = daoFactory.getDAO(ViolationDAO.class);
        return dao;
    }
  //@WebMethod(operationName = "makeInvoicePayment", action = "urn:makeInvoicePayment")
    //@WebResult(name = "dto")
    public ResultDTO makePayment(ViolatorDTO violatorDTO, BillingInfoDTO billingInfoDTO , BigDecimal amount, String email)
            throws EtccException, EtccSecurityException {
    	
    	
        //AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        
        return violationDAO().makePayment(violatorDTO, billingInfoDTO, amount, email);
    }

	public CheckEligibilityResponseDTO checkEligibility(String licPlate, String licState, String invoiceNo, Long accountId, String ipAddress,
			String jsessionId, String sourceUserName, String user, String sessionId, Integer PmtPlanId, String source)
			throws EtccException {
		
		return violationDAO().checkEligibility(licPlate, licState, invoiceNo, accountId, ipAddress, jsessionId, sourceUserName, user, sessionId, PmtPlanId, source);
	}


	public SearchOpenViolationResponse searchOpenViolations(String licPlate, String licState) throws EtccException {
		
		return violationDAO().searchOpenViolations(licPlate, licState);
	}


	public GetViolationResponseDTO getViolationsForCCRMA(String licPlate, String licState, String invoiceNo, Long accountId,
			String ipAddress, String jsessionId, String sourceUserName, String user, String sessionId,
			Integer PmtPlanId) throws Exception {
		return violationDAO().getViolationsForCCRMA(licPlate, licState, invoiceNo, accountId, ipAddress, jsessionId, sourceUserName, user, sessionId, PmtPlanId);
	}


	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {
		return violationDAO().makeViolationPayment(makeViolationPaymentRequest);
	}
    
    
    
    /*public ResultDTO makeInvoicePaymentEFT(AccountLoginDTO acctLoginDto,AccountEFTDTO paymentMethodDTO, ViolatorDTO violatorDTO,BigDecimal paymentAmount, String email)
            throws EtccException, EtccSecurityException {
    	
    	//Long[] ids =ServiceFactory.getImplementation(AppInterface.class).getAccountPostingAndShiftId(acctLoginDto,1L);
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        
    	Long[] ids =appDao.getAccountPostingAndShiftId(acctLoginDto, 1L);
    	
		String xmlPosting = generatePostingXML(paymentMethodDTO,violatorDTO,ids, paymentAmount);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        
        return accountDao.makePayment(acctLoginDto, paymentMethodDTO, xmlPosting, email);
    }
    */
   
}
