/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

public class ProcessPaymentDetailAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        HttpSession session = request.getSession();
        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
        processInvoicesSelected((String[])dynaForm.get("payInvoice"), 
                                paymentContext.getInvoices());
        processViolationsSelected((String[])dynaForm.get("payViolation"), 
                                  paymentContext.getViolations());
        processTagAmount(paymentContext, form );
        
        if ( !verifyTagAmount( paymentContext ) )
        {
            ActionMessages actionMessages = getMessages(request);
          actionMessages.add(ActionErrors.GLOBAL_MESSAGE, 
            new ActionMessage( "Please enter a valid Tagstore payment amount", false));
          saveErrors(session, actionMessages);
          return mapping.findForward("failure");  
        }
        
        processVeaAccept(paymentContext, form );

        if ( "addTags".equalsIgnoreCase( paymentContext.getCallBack() ) && 
             paymentContext.getPaymentAmount( ).compareTo( new BigDecimal(0.0) )== 0 ) 
        {
          return mapping.findForward("fulfillTag");    
        }

        SessionUtil.setPaymentContext(session, paymentContext);

        return mapping.findForward("success");
    }


    private void processInvoicesSelected(String[] selected, 
                                         Invoice[] invoices) {
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                invoices[i].setAuthorized(!ArrayUtils.isEmpty(selected) && 
                                          ArrayUtils.contains(selected, 
                                                              invoices[i].getId()));
            }
        }
    }

    private void processViolationsSelected(String[] selected, 
                                           Violation[] violations) {
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                violations[i].setAuthorized(!ArrayUtils.isEmpty(selected) && 
                                            ArrayUtils.contains(selected, 
                                                                violations[i].getId()));
            }
        }
    }

    private void processVeaAccept(PaymentContext paymentContext, ActionForm form ) {
        paymentContext.setVeaAccepted("Y".equals( ActionFormUtil.getString( (DynaActionForm) form, "acceptVea" ) ));
        Invoice[] invoices = paymentContext.getInvoices();
        if (!ArrayUtils.isEmpty(invoices)) {
            HashMap<String,Invoice> veaLicPlatesMap = new HashMap<String,Invoice>();
            for (int i = 0; i < invoices.length; i++) {
                if (invoices[i].isVeaEligible()) {
                    veaLicPlatesMap.put(invoices[i].getLicPlateNumber() + 
                                        invoices[i].getLicPlateState(), 
                                        invoices[i]);
                }
            }
            paymentContext.setVeaLicPlates(veaLicPlatesMap.values().toArray(new Invoice[0]));
        }
    }
    
    private void processTagAmount( PaymentContext paymentContext, ActionForm form ) 
    {
      BigDecimal tagAmount = new BigDecimal( 0.0 );
      try
      {
        tagAmount = new BigDecimal( ActionFormUtil.getString( (DynaActionForm) form, "tagAmount" ) );
      }
      catch ( Exception e ) {
        //System.out.println( "[PAYMENT] Tag amount empty or not valid: " + ActionFormUtil.getString( (DynaActionForm) form, "tagAmount" ) );    
      }
      paymentContext.setTagAmount( tagAmount );
    }
    
    private boolean verifyTagAmount( PaymentContext paymentContext ) 
    {
      BigDecimal tagAmount = paymentContext.getTagAmount( );
      BigDecimal originalTagAmount =  paymentContext.getOriginalTagAmount( );
      
      if ( tagAmount.compareTo( new BigDecimal( 0.0 ) ) < 0 || 
           ( originalTagAmount != null && tagAmount.compareTo( originalTagAmount ) < 0 ) ) 
      {
        return false;    
      }
      return true;
    }
}
