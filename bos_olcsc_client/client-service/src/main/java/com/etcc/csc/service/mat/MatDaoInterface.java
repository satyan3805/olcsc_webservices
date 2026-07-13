package com.etcc.csc.service.mat;

import java.util.List;

import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.olcsc.attlas.exceptions.AttlasException;

public interface MatDaoInterface {
	
	/**
	 * 
	 * @param plate
	 * @param jurisdiction
	 * @return
	 * @throws Exception
	 */
	//PUSH NOTIFICATION PHASE II START Adding userName,ipAddress,sourceUserName,jSessionId
	CheckMatEligibilityProcResponse checkEligibility(String plate, String jurisdiction,String userName,String ipAddress,String sourceUserName,String jSessionId,String dbSessionId) throws AttlasException;
	
	/**
	 * 
	 * @param plate
	 * @param jurisdiction
	 * @param violations
	 * @param paymentType
	 * @param paymentMethod
	 * @param expireMonth
	 * @param expireYear
	 * @param fullName
	 * @param zipCode
	 * @param notes
	 * @return
	 */
	//PUSH NOTIFICATION PHASE II START Adding dbSessionId
	PostMatCcPaymentProcResponse postCcPayment(
			String plate, String jurisdiction, List<ViolationType> violations,
			String paymentType, String paymentMethod, String expireMonth, String expireYear,
			String fullName, String zipCode, 
			String notes, String emailAddress,String paypageRegistrationId,CreditCardType cardType,String omniToken,String dbSessionId) throws Exception;
	
	/**
	 * 
	 * @param plate
	 * @param jurisdiction
	 * @param violations
	 * @param paymentType
	 * @param paymentMethod
	 * @param routingInfo
	 * @param accountType
	 * @param fullName
	 * @param notes
	 * @return
	 */
	//PUSH NOTIFICATION PHASE II START Adding dbSessionId
	PostMatEftPaymentProcResponse postEftPayment(
			String plate, String jurisdiction, List<ViolationType> violations,
			String paymentType, String paymentMethod, String routingInfo, String accountType,
			String fullName, 
			String notes, String emailAddress,String dbSessionId) throws Exception;
}
