/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.violation;

import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectedInvoiceAction extends Action {
    private static final Logger logger = Logger.getLogger(SelectedInvoiceAction.class);

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
//        SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm) form;
        SelectedInvoiceForm allInvoicesForm = (SelectedInvoiceForm)session.getAttribute("allInvoicesForm");
        SelectedInvoiceForm selectedInvoiceForm = allInvoicesForm.clone(false);

        allInvoicesForm.getViolatorDTO().calculateTotals();
        ViolatorDTO violator = selectedInvoiceForm.getViolatorDTO().calculateTotals();

        if (logger.isDebugEnabled()) {
            logger.debug("SelectedInvoiceAction.totalpayment=" + violator.getPayment());
            logger.debug("SelectedInvoiceAction.totalremainingbalance=" + violator.getRemainingBalance());
            logger.debug("selectedInvoiceForm: " + selectedInvoiceForm);
            logger.debug("allInvoicesForm: " + allInvoicesForm);
        }

        if (violator.getPayment() <= 0) {
            request.setAttribute("nopay", "true");
            return mapping.findForward("nopay");
        } else {
            session.setAttribute("selectedInvoiceForm", selectedInvoiceForm);
        }
        return mapping.findForward("success");
    }
}
