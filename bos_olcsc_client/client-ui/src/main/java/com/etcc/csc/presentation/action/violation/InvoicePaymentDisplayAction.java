/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.StringUtil;

public class InvoicePaymentDisplayAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(InvoicePaymentDisplayAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	boolean trace = logger.isTraceEnabled();
        String result = "success";
        HttpSession session = request.getSession();
        SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm) session.getAttribute("selectedInvoiceForm");
        String changePayMethod = request.getParameter("changePayMethod");
        // check change pay method set to "y" or "Y"
        if (StringUtil.stringToBoolean(changePayMethod)) {
        	logger.debug("Edit payMethod.");
            request.setAttribute("changePayMethod", "true");
        } else {
            // check if payment method was submitted in the form
            String paymentType = selectedInvoiceForm.getPaymentType();
            if (trace) logger.trace("paymentType=" + paymentType);
            BillingInfoForm billingForm = selectedInvoiceForm.getBillingInfoForm();
            // user can edit pay method even when it has not changed
            if (!Boolean.parseBoolean(changePayMethod)) {
                if (StringUtil.isEmpty(paymentType))
                    // update shopping cart with the selected invoices
                    selectedInvoiceForm.getViolatorDTO().updateShoppingCart();
                else if (billingForm.getPaymentTypeEnum() == null)
                    // set the selected payment method on the billing form
                    billingForm.setPaymentType(paymentType);
                // validate the billing form
                if (trace) logger.trace(billingForm);
                ErrorMessageDTO[] messages = billingForm.validate(new CreditCardDelegate().getCreditCardTypes(), true);
                if (trace) {
                    logger.trace("Billing validation messages: " + Arrays.toString(messages));
                    logger.trace("Action messages: " + getMessages(request));
                    logger.trace("Action errors: " + getErrors(request));
                }
                if ((messages == null || messages.length <= 0) && getErrors(request).isEmpty()) {
                    logger.debug("Payment method selected and valid.");
                    result = "done";
                }
            } else if (!StringUtil.isEmpty(paymentType)) {
                // update the payment type on the selected invoices and billing info with the submitted value
                if (trace)
                    logger.trace("Updating payment option on billing form. Previous value: " + billingForm.getPaymentTypeEnum());
                billingForm.setPaymentType(paymentType);
            }
        }
        //createURLForPCI(request);

    	AppDelegate appDel = new AppDelegate();
		request.setAttribute("LITLE_PAYPAGE_URL",appDel.getSysParam("LITLE_PAYPAGE_URL"));
        request.setAttribute("LITLE_PAYPAGE_ID",appDel.getSysParam("LITLE_PAYPAGE_ID"));
        request.setAttribute("LITLE_REPORT_GROUP",appDel.getSysParam("LITLE_REPORT_GROUP"));
        logger.debug("Result=" + result);
        return mapping.findForward(result);
    }

    /*
    private void createURLForPCI(HttpServletRequest request) throws EtccException {
    	HttpSession session = request.getSession();
        SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm) session.getAttribute("selectedInvoiceForm");
		String psId= (String)request.getSession().getAttribute("psid");
		String rSessionId = request.getSession().getId();
		StringBuffer urlVar = new StringBuffer();
		StringBuffer redirectUrl = createRedirectURL(request);

		urlVar.append(new AppDelegate().getSysParamFromCache("PCI_APPLIANCE_URL"));
		urlVar.append("internal/addPAN.do?myaction=load&applicationName=CSC&rsessionId="+rSessionId+
		"&redirectUrl="+redirectUrl.toString()+"&psId="+psId);
		urlVar.append("&loggedUserName="+selectedInvoiceForm.getViolatorDTO().getAccountLoginDTO().getInvoiceId());
		urlVar.append("&clientIP="+request.getRemoteAddr());
		urlVar.append("&actionPath="+"payment.do");
		logger.info("PCI URL (Payment): " + urlVar.toString().replaceAll(" ", "%20"));
		request.setAttribute("urlVal", urlVar.toString().replaceAll(" ", "%20"));
	}

    private StringBuffer createRedirectURL(HttpServletRequest request) throws EtccException {
		logger.info("Entering createRedirectURL() @ InvoicePaymentDisplayAction");
		HttpSession session = request.getSession();
		String portalId = (String) session.getAttribute("PORTALID");
		logger.debug("Inside createRedirectURL() @ InvoicePaymentDisplayAction :: Portal Id ::" + portalId);
		StringBuffer redirectUrl = new StringBuffer();
        if(StringUtils.isNotBlank(portalId)){
			redirectUrl.append(new AppDelegate().getSysParamFromCache( "REDIRECT_URL_FOR_EXTERNAL_CSC_FROM_PCI" ));
		}else{
			redirectUrl.append(new AppDelegate().getSysParamFromCache( "REDIRECT_URL_FOR_CSC_FROM_PCI" ));
			logger.info("Leaving createRedirectURL() @ InvoicePaymentDisplayAction : url : " + redirectUrl);
		}
		redirectUrl.append(request.getRequestURI());
		redirectUrl.append("?myaction=returnFromPCI");
        logger.debug("Inside createRedirectURL() @ InvoicePaymentDisplayAction :: Redirect URL::" + redirectUrl);
        logger.info("Leaving createRedirectURL() @ InvoicePaymentDisplayAction");
		return redirectUrl;
	}*/

}
