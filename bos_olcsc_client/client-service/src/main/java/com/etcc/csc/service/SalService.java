package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.sal.CalculateSalPaymentResponseType;
import com.etcc.csc.service.sal.CalculateSalPaymentType;
import com.etcc.csc.service.sal.CheckSalEligibilityResponseType;
import com.etcc.csc.service.sal.CheckSalEligibilityType;
import com.etcc.csc.service.sal.GetInvoiceViolationsResponseType;
import com.etcc.csc.service.sal.GetInvoiceViolationsType;
import com.etcc.csc.service.sal.PostSalCcPaymentResponseType;
import com.etcc.csc.service.sal.PostSalCcPaymentType;
import com.etcc.csc.service.sal.PostSalEftPaymentResponseType;
import com.etcc.csc.service.sal.PostSalEftPaymentType;

/**
 * SalService defines the interface for services that implement the SAL service.
 * This provides a way of separating the web service interface from the actual implementation.
 * 
 * @author vsparks
 *
 */
public interface SalService extends ServiceInterface {

	/**
	 * Checks if the call is eligible to use the SAL feature.  If eligible, returns list of unpaid invoices.
	 * @param request
	 * @return
	 */
	CheckSalEligibilityResponseType checkSalEligibility (
			CheckSalEligibilityType request)  throws EtccException;
	
	/**
	 * Calculates the payment and reduced fees of the selected invoices
	 * @param request
	 * @return
	 */
	CalculateSalPaymentResponseType calculateSalPayment(
			CalculateSalPaymentType request)  throws EtccException;
	
	/**
	 * Posts SAL Credit Card payment
	 * @param request
	 * @return
	 */
	PostSalCcPaymentResponseType postSalCcPayment(
			PostSalCcPaymentType request,String emailAddress)  throws EtccException;

	/**
	 * Posts SAL EFT payment
	 * @param request
	 * @return
	 */
	PostSalEftPaymentResponseType postSalEftPayment(
			PostSalEftPaymentType request, String emailAddress)  throws EtccException;

	/**
	 * Returns the list of Violations associate with given Invoice
	 * @param request
	 * @return
	 */
	GetInvoiceViolationsResponseType getInvoiceViolations(GetInvoiceViolationsType request)  throws EtccException;

}
