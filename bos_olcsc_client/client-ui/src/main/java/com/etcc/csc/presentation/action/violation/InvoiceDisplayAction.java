/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.StringUtil;

/**
 * Display the open invoices, but first check if user has selected an account option.
 * @author Milosh Boroyevich
 */
public class InvoiceDisplayAction extends Action {
    private static final Logger logger = Logger.getLogger(InvoiceDisplayAction.class);

    protected static final String ACTION_MAPPING_ACCOUNT_OPTIONS = "accountOptions";
    protected static final String ACTION_MAPPING_LOGIN = "login";
    protected static final String ACTION_MAPPING_SIGNUP = "signup";
    protected static final String ACTION_MAPPING_SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String entryPoint = request.getParameter("returnAction");
        if (entryPoint != null)
            request.setAttribute("returnAction", entryPoint);
        HttpSession session = request.getSession();
        SelectedInvoiceForm allInvoicesForm = (SelectedInvoiceForm)session.getAttribute("allInvoicesForm");
        int requestedPageNum = getRequestedPageNum(request);
        allInvoicesForm.setRequestedPageNum(requestedPageNum);
        SelectedInvoiceForm selectedInvoiceForm = allInvoicesForm.clone();
        session.setAttribute("selectedInvoiceForm", selectedInvoiceForm);

        String forward = getNext(request, selectedInvoiceForm.getViolatorDTO());
        logger.info("Forwarding to: " + forward);
    	return mapping.findForward(forward);
    }

    private String getNext(HttpServletRequest request, ViolatorDTO violator) {
        Boolean accountOption = violator.getPayWithAccount();
        String forward = request.getParameter("nextResult");
        if (accountOption == null) {
	    	if (StringUtil.isEmpty(forward)) {
	    		forward = ACTION_MAPPING_ACCOUNT_OPTIONS;
	    	} else if (forward.equals(ACTION_MAPPING_LOGIN) || forward.equals(ACTION_MAPPING_SIGNUP)) {
	    		violator.setPayWithAccount(Boolean.TRUE);
	    	} else {
	    		logger.debug("User did not select to pay with account: " + forward);
	    		violator.setPayWithAccount(Boolean.FALSE);
	    		// display the invoices
	    		forward = ACTION_MAPPING_SUCCESS;
	    	}
        } else {
	    	if (StringUtil.isEmpty(forward))
	    		forward = (accountOption.booleanValue() ? ACTION_MAPPING_LOGIN : ACTION_MAPPING_SUCCESS);
        }
        return forward;
    }

    private int getRequestedPageNum(HttpServletRequest request) {
    	int requestedPageNum = 0;
    	Object p = request.getParameter("requestedPageNum");
    	if (p != null) {
    		try {
    			requestedPageNum = Integer.parseInt(p.toString());
    		} catch(NumberFormatException e) {
    			logger.debug("Invalid requestedPageNum.", e);
    		}
    	}
    	logger.debug("InvoiceDisplayAction.requestedPageNum=" + requestedPageNum);
    	return requestedPageNum;
    }
}
