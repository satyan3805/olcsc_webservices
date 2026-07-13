package com.etcc.csc.service.sal;

import java.util.List;

import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.olcsc.attlas.exceptions.AttlasException;

public interface SalInterface {
	
	//Long getAccountVehicleId(String licPlateNum, String jurisdiction, String invoiceNum) throws AttlasException;
	
	/**
	 * 
	 * @param violationDocumentId
	 * @return
	 * @throws Exception 
	 */
	GetInvoiceViolationsProcResponse getInvoiceViolations(String violationDocumentId,String sessionId) throws  Exception;
	
	/**
	 * 
	 * @param account_vehicle_id
	 * @return
	 * @throws AttlasException
	 */
	//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
	CheckSalEligibilityProcResponse checkEligibility(String licPlateNum, String jurisdiction, String invoiceNum, Long accountId,String userName,String ipAddress,String sourceUserName,String jSessionId,String dbSessionId) throws AttlasException;
	
	/**
	 * 
	 * @param partyId
	 * @param invoices
	 * @param violations
	 * @return
	 * @throws AttlasException
	 */
	//PUSH NOTIFICATION PHASE II Adding dbSessionId
	CalculateSalPaymentProcResponse calculatePayment(String licPlateNum, String jurisdiction, String invoiceNum, List<InvoiceType> invoices, List<ViolationType> violations, Long accountId,String dbSessionId) throws AttlasException; 
	
	/**
	 * 
	 * @param partyId
	 * @param invoices
	 * @param violations
	 * @param paymentType
	 * @param paymentMethod
	 * @param expireMonth
	 * @param expireYear
	 * @param fullName
	 * @param zipCode
	 * @param notes
	 * @throws AttlasException
	 */
	//PUSH NOTIFICATION PHASE II Adding dbSessionId
	PostSalCcPaymentProcResponse postCcPayment(String licPlateNum, String jurisdiction, String invoiceNum, List<InvoiceType> invoices, List<ViolationType> violations, 
			String paymentType, String paymentMethod,
			String expireMonth, String expireYear,
			String fullName,
			String zipCode, String notes,String emailAddress,String paypageRegistrationId,CreditCardType cardType, Long accountId,String omniToken,String dbSessionId) throws Exception;
	
	/**
	 * 
	 * @param partyId
	 * @param invoices
	 * @param violations
	 * @param paymentType
	 * @param paymentMethod
	 * @param routingInfo
	 * @param accountType
	 * @param fullName
	 * @param notes
	 * @throws AttlasException
	 */
	//PUSH NOTIFICATION PHASE II Adding dbSessionId
	PostSalEftPaymentProcResponse postEftPayment(String licPlateNum, String jurisdiction, String invoiceNum, List<InvoiceType> invoices, List<ViolationType> violations, 
			String paymentType, String paymentMethod,
			String routingInfo, String accountType,
			String fullName,
			String notes, String emailAddress, Long accountId,String dbSessionId) throws Exception;
}
