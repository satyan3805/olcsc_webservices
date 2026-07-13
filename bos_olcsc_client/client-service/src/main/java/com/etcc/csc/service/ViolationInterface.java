/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
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
 * Interface for Violation handling.
 * @author unknown
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public interface ViolationInterface extends ServiceInterface {
	/**
	 * Login a violator given the specified login parameters.
	 * @param accountLoginDTO
	 * @param sessionId
	 * @param ipAddress
	 * @param userEnvDto
	 * @param invoiceId
	 * @param licPlate
	 * @param licState
	 * @param agencyId
	 * @return the logged in violator
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
    ViolatorDTO loginViolator(AccountLoginDTO accountLoginDTO, String sessionId, String ipAddress,
        UserEnvDTO userEnvDto, String invoiceId, String licPlate, String licState,String dbSessionId) throws EtccException, EtccSecurityException;
    
    ViolatorDTO loginViolatorPmtPlan(AccountLoginDTO acctLoginDto, String sessionId, String ipAddress,
            UserEnvDTO userEnvDto, String licPlate, String licState,String paymentPlanId,String dbSessionId) throws EtccException,EtccSecurityException; 

    /**
     * Violation payment method.  The items to pay are expected to be contained in the specified violator.
     * @param violatorDTO container for items to pay
     * @param paymentMethod mechanism used for applying payment
     * @param ipAddress for security check
     * @return the modified violator with updated payment details
     * @throws EtccException
     * @throws EtccSecurityException
     */
   //ViolatorDTO makePayment(ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress) throws EtccException, EtccSecurityException;
   ResultDTO makePayment(ViolatorDTO violatorDTO, BillingInfoDTO billingInfoDTO,  BigDecimal amount, String email) throws EtccException, EtccSecurityException;

    PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID, String licPlate, String sessionID, String ipAddress, long acctID) throws EtccException, EtccSecurityException;

    ViolatorDTO getInvoices(AccountLoginDTO accountLoginDTO, String ipAddress, String licPlate, String licState, AgencyEnum agency) throws EtccException, EtccSecurityException;

    /**
     * Gets the PDF version of the Invoice that was mailed to the violator.
     * @param violatorDTO Violator details.
     * @param invoice The invoice to get the PDF of.
     * @return the invoice populated with the
     * @throws EtccException
     * @throws EtccSecurityException
     */
    DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoice) throws EtccException, EtccSecurityException;

    /**
     * Email payment confirmation receipt.
     * @param violatorDTO container for items to pay
     * @param paymentMethod mechanism used for applying payment
     * @param email email to use for sending the receipt
     * @return the violator with errors if encountered
     * @throws EtccException
     * @throws EtccSecurityException
     */
    //ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod, String email) throws EtccException, EtccSecurityException;
    //public ResultDTO makeInvoicePaymentCreditCard(AccountLoginDTO acctLoginDto,AccountCreditCardDTO paymentMethodDTO, ViolatorDTO violatorDTO,BigDecimal paymentAmount, String email) throws EtccException, EtccSecurityException;
    //public ResultDTO makeInvoicePaymentEFT(AccountLoginDTO acctLoginDto,AccountEFTDTO paymentMethodDTO, ViolatorDTO violatorDTO,BigDecimal paymentAmount,String email) throws EtccException, EtccSecurityException;
   
    public CheckEligibilityResponseDTO checkEligibility(final String licPlate, final String licState, final String invoiceNo,
			Long accountId, final String ipAddress, final String jsessionId, final String sourceUserName,
			final String user, final String sessionId, final Integer PmtPlanId, final String source)  throws EtccException;
    
    public SearchOpenViolationResponse searchOpenViolations(final String licPlate, final String licState) throws EtccException;
    
    public GetViolationResponseDTO getViolationsForCCRMA(final String licPlate, final String licState, final String invoiceNo,
			final Long accountId, final String ipAddress, final String jsessionId, final String sourceUserName,
			final String user, final String sessionId, final Integer PmtPlanId) throws Exception ;
    
    public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException;
}
