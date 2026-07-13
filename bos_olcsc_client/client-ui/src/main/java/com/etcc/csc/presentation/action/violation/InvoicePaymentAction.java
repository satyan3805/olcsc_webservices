/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;

/**
 * Make payment and send e-mail receipt.
 * @see ViolationDelegate#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)
 * @see ViolationDelegate#emailReceipt(ViolatorDTO, AccountPaymentMethodDTO, String)
 * @author Unknown
 * @author Milosh Boroyevich
 */
public class InvoicePaymentAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(InvoicePaymentAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
            SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm)form;
            //AccountPaymentMethodDTO paymentMethod = selectedInvoiceForm.newPaymentMethod();
            
            ViolatorDTO violatorDTO = selectedInvoiceForm.getViolatorDTO();
            AccountLoginDTO accountLoginDTO = violatorDTO.getAccountLoginDTO();

            AccountPaymentMethodDTO paymentMethodDTO = (AccountPaymentMethodDTO) request.getSession().getAttribute("paymentMethodDTO");
            BillingInfoDTO billingInfoDTO = new BillingInfoDTO();
            billingInfoDTO.setBillingType(paymentMethodDTO.getPaymentType());
            
            
            if (paymentMethodDTO instanceof AccountCreditCardDTO)
            {
            	 Collection cards = new ArrayList();
            	 cards.add(paymentMethodDTO);
            	 billingInfoDTO.setCards(cards);
            	 
            }
            else
            {
            	billingInfoDTO.setEft((AccountEFTDTO)paymentMethodDTO); 
            }

            BigDecimal amount = new BigDecimal(violatorDTO.getShoppingCart().getPayment());
			
			String email = selectedInvoiceForm.getBillingInfoForm().getEmail();
            //BaseDTO resultDTO =  new AccountDelegate().makePayment(accountLoginDTO, paymentMethodDTO, xmlPosting, email);
            BaseDTO resultDTO;
            /*if (paymentMethodDTO instanceof AccountCreditCardDTO)
            {
            	resultDTO =  new ViolationDelegate().makeInvoicePaymentCreditCard(accountLoginDTO, (AccountCreditCardDTO)paymentMethodDTO,violatorDTO, new BigDecimal(violatorDTO.getShoppingCart().getPayment()),email );
            }
            else
            {
            	resultDTO =  new ViolationDelegate().makeInvoicePaymentEFT(accountLoginDTO, (AccountEFTDTO)paymentMethodDTO,violatorDTO, new BigDecimal(violatorDTO.getShoppingCart().getPayment()),email);
            }*/
            resultDTO =  new ViolationDelegate().makePayment(violatorDTO, billingInfoDTO, amount, email);

            if (saveErrorMessages(request, resultDTO, "violationError")){
                return mapping.findForward("failure");
            }

            // TODO: modify JSP (specifically JavaScript) to handle multiple confirmation numbers if appropriate
            List<String> confirmationNumbers = violatorDTO.getConfirmationCodes();
            logger.debug("confirmationNumbers: " + confirmationNumbers);
            String confirmationNumber = null;
            if (confirmationNumbers != null && !confirmationNumbers.isEmpty())
            	confirmationNumber = confirmationNumbers.get(0);

            String forward = "success";
            // get the violator with all the invoices and recalculate the totals in case they have changed
            // the all-invoices violator is used for displaying totals in the e-mail receipt and final UI pages
            violatorDTO = ((SelectedInvoiceForm)request.getSession().getAttribute("allInvoicesForm"))
                .getViolatorDTO().calculateTotals();
            if (confirmationNumber != null) {
                request.setAttribute("confirmationNumber", confirmationNumber);
                forward = "success";
                // send the e-mail receipt if payment is successful

                //TODO: handling sending email address  separately
                //Sending Email will be different

            }
            logger.debug("forwarding to: " + forward);
            return mapping.findForward(forward);
    }



	
}
