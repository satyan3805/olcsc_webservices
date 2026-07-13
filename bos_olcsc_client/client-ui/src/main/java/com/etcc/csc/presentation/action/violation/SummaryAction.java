/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.HttpDataUtil;

/**
 * Summary of the account information.  This action will reload the violator
 * invoices if the shopping cart has been processed.
 * @see ViolatorDTO#getConfirmationCodes()
 * @author Milosh Boroyevich
 */
public class SummaryAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(SummaryAction.class);

	/**
	 * @see ViolatorDTO#getConfirmationCodes()
	 */
	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm)form;
        ViolatorDTO violator = selectedInvoiceForm.getViolatorDTO();
        SelectedInvoiceForm allInvoicesForm = (SelectedInvoiceForm) session.getAttribute("allInvoicesForm");
        if (violator.getConfirmationCodes().size() > 0) {
        	logger.debug("Shopping Cart processed - reload the violations.");
            // shopping cart has been successfully processed - clear the violator
            violator.clear();
            AccountLoginDTO accountLoginDTO = violator.getAccountLoginDTO();
            String ipAddress = HttpDataUtil.getClientIpAddress(request);
            String licPlate = violator.getLicPlateNbr();
            String licState = violator.getLicPlateState();
            ViolationDelegate violationDelegate = new ViolationDelegate();
            // Load the invoices
            violator = violationDelegate.getInvoices(
                accountLoginDTO, ipAddress, licPlate, licState, null);
            // TODO: Load the uninvoiced violations
            // update the allInvoicesForm with the refreshed violator
            if (allInvoicesForm == null)
            	allInvoicesForm = new SelectedInvoiceForm();
            allInvoicesForm.setViolatorDTO(violator.calculatePreliminaryTotals());
        } else if (allInvoicesForm == null) {
        	allInvoicesForm = new SelectedInvoiceForm();
        	allInvoicesForm.setViolatorDTO(violator.calculatePreliminaryTotals());
        }

        if (saveErrorMessages(request, violator, "violationError")){
            return mapping.findForward("failure");
        }

        Object entryPoint = request.getParameter("returnAction");
        if (entryPoint != null) {
            allInvoicesForm.setReturnAction(entryPoint.toString());
        }

        session.setAttribute("allInvoicesForm", allInvoicesForm);
        session.setAttribute("selectedInvoiceForm", allInvoicesForm.clone());
        return mapping.findForward("success");
    }
}
