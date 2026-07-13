/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.math.BigDecimal;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
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
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.ViolationInterface;
import com.etcc.csc.service.ViolationServiceInterface;
import com.etcc.csc.service.WebServiceFactory;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class ViolationDelegate implements ViolationInterface {

    public ViolatorDTO loginViolator(AccountLoginDTO acctLoginDto, String sessionId,
            String ipAddress, UserEnvDTO userEnvDto, String invoiceId,
            String licPlate, String licState,String dbSessionId) throws EtccException, EtccSecurityException {
        return stub().loginViolator(acctLoginDto, sessionId, ipAddress, userEnvDto, invoiceId, licPlate, licState,dbSessionId);
    }

    /**
     * @see ViolatorDTO#calculatePreliminaryTotals()
     * @see ViolatorDTO#calculateTotals()
     */
//    public ViolatorDTO makePayment(ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress)
//            throws EtccException, EtccSecurityException {
//        try {
//            if (ServiceFactory.getDefaultServiceFactory() instanceof WebServiceFactory)
//                return this.wsMakePayment(violatorDTO, paymentMethod, ipAddress).calculatePreliminaryTotals().calculateTotals();
//        } catch (NoClassDefFoundError e) {
//            // there is no WebServiceFactory class available at run-time, so silently proceed to default implementation
//        }
//        return stub().makePayment(violatorDTO, paymentMethod, ipAddress).calculatePreliminaryTotals().calculateTotals();
//    }

    /**
     * X-Fire web services will cause instantiation exception on abstract class.
     * @param violatorDTO
     * @param paymentMethod
     * @param ipAddress
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     * @see #makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)
     */
//    private ViolatorDTO wsMakePayment(ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress)
//            throws EtccException, EtccSecurityException {
//        switch (paymentMethod.getPaymentType()) {
//        case CREDIT:
//            violatorDTO = stub().makePaymentService(violatorDTO, (AccountCreditCardDTO)paymentMethod, ipAddress);
//            break;
//        case EFT:
//            violatorDTO = stub().makePaymentService(violatorDTO, (AccountEFTDTO)paymentMethod, ipAddress);
//            break;
//        default:
//            // default (this will cause instantiation exception on abstract payment method class if web services are used)
//            violatorDTO = stub().makePayment(violatorDTO, paymentMethod, ipAddress);
//            break;
//        }
//        violatorDTO.getShoppingCart().initCartItems(violatorDTO);
//        return violatorDTO;
//    }

    public PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID, String licPlate,
            String sessionID, String ipAddress, long acctID) throws EtccException, EtccSecurityException {
        return stub().getPaymentPlan(licKey, invoiceID, licPlate, sessionID, ipAddress, acctID);
    }

    public ViolatorDTO getInvoices(AccountLoginDTO accountLoginDTO,
            String ipAddress, String licPlate, String licState, AgencyEnum agency) throws EtccException, EtccSecurityException {
        return stub().getInvoices(accountLoginDTO, ipAddress, licPlate, licState, null);
    }

    public DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoiceDTO) throws EtccException, EtccSecurityException {
        return stub().getInvoiceDoc(violatorDTO, invoiceDTO);
    }

//    public ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod, String email) throws EtccException, EtccSecurityException {
//        return stub().emailReceipt(violator, paymentMethod, email);
//    }

    private ViolationServiceInterface stub() {
        return ServiceFactory.getImplementation(ViolationServiceInterface.class);
    }
    /*public ResultDTO makeInvoicePaymentCreditCard(AccountLoginDTO acctLoginDto,
    		AccountCreditCardDTO paymentMethodDTO, ViolatorDTO violatorDTO, BigDecimal paymentAmount,
    		String email) throws EtccException, EtccSecurityException {
    	return stub().makeInvoicePaymentCreditCard(acctLoginDto, paymentMethodDTO, violatorDTO, paymentAmount, email);
    }
    public ResultDTO makeInvoicePaymentEFT(AccountLoginDTO acctLoginDto,
    		AccountEFTDTO paymentMethodDTO, ViolatorDTO violatorDTO,BigDecimal paymentAmount,
    		String email) throws EtccException, EtccSecurityException {
    	return stub().makeInvoicePaymentEFT(acctLoginDto, paymentMethodDTO, violatorDTO, paymentAmount, email );
    }*/
    public ResultDTO makePayment(ViolatorDTO violatorDTO,
    		BillingInfoDTO billingInfoDTO, BigDecimal amount,
    		 String email) throws EtccException,
    		EtccSecurityException {
    	// TODO Auto-generated method stub
    	return stub().makePayment(violatorDTO, billingInfoDTO, amount, email);
    }

	public ViolatorDTO loginViolatorPmtPlan(AccountLoginDTO acctLoginDto,
			String sessionId, String ipAddress, UserEnvDTO userEnvDto,
			String licPlate, String licState, String paymentPlanId,
			String dbSessionId) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	public CheckEligibilityResponseDTO checkEligibility(String licPlate, String licState, String invoiceNo, Long accountId, String ipAddress,
			String jsessionId, String sourceUserName, String user, String sessionId, Integer PmtPlanId, String source)
			throws EtccException {
		return stub().checkEligibility(licPlate, licState, invoiceNo, accountId, ipAddress, jsessionId, sourceUserName, user, sessionId, PmtPlanId, source);
		
	}

	public SearchOpenViolationResponse searchOpenViolations(String licPlate, String licState) throws EtccException {
		
		return stub().searchOpenViolations(licPlate, licState);
	}

	public GetViolationResponseDTO getViolationsForCCRMA(String licPlate, String licState, String invoiceNo, Long accountId,
			String ipAddress, String jsessionId, String sourceUserName, String user, String sessionId,
			Integer PmtPlanId) throws Exception {
		return stub().getViolationsForCCRMA(licPlate, licState, invoiceNo, accountId, ipAddress, jsessionId, sourceUserName, user, sessionId, PmtPlanId);
	}

	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {
		return stub().makeViolationPayment(makeViolationPaymentRequest);
	}
    
}
