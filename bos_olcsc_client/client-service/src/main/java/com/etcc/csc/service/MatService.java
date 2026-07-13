package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.mat.CheckMatEligibilityResponseType;
import com.etcc.csc.service.mat.CheckMatEligibilityType;
import com.etcc.csc.service.mat.PostMatCcPaymentResponseType;
import com.etcc.csc.service.mat.PostMatCcPaymentType;
import com.etcc.csc.service.mat.PostMatEftPaymentResponseType;
import com.etcc.csc.service.mat.PostMatEftPaymentType;

/**
 * MatService defines the interface for services that implement the MAT service.
 * This provides a way of separating the web service interface from the actual implementation.
 * 
 *
 */
public interface MatService extends ServiceInterface{

	/**
	 * Checks if the caller is eligible to use the MAT feature.  If eligible, returns list of unpaid uninvoiced violations.
	 * @param request
	 * @return
	 */
	CheckMatEligibilityResponseType checkMatEligibility (
		CheckMatEligibilityType request
    ) throws EtccException; 
	
	/**
	 * Posts MAT credit card payment 
	 * @param request
	 * @return
	 */
	PostMatCcPaymentResponseType postMatCcPayment (
			PostMatCcPaymentType request, String emailAddress)  throws EtccException;

	/**
	 * Posts MAT EFT payment 
	 * @param request
	 * @return
	 */
	PostMatEftPaymentResponseType postMatEftPayment (
			PostMatEftPaymentType request, String emailAddress)  throws EtccException;

}