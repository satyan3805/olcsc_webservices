/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;

/**
 * @deprecated email of receipt moved to {@link InvoicePaymentAction}.
 */
@Deprecated
public class EmailReceiptAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(EmailReceiptAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm)form;

//			ViolationDelegate violationDelegate = new ViolationDelegate();
//			ViolatorDTO violatorDTO = violationDelegate.emailReceipt(
//					selectedInvoiceForm.getViolatorDTO(),
//					selectedInvoiceForm.newPaymentMethod(),
//					selectedInvoiceForm.getBillingInfoForm().getEmail());
			String agency = request.getParameter("agencyId");
			logger.debug("EmailReceiptAction.agencyId= " + agency);
			request.setAttribute("agencyId",agency);
//			if(saveErrorMessages(request, violatorDTO, "violationError")){
//                return mapping.findForward("failure");
//            } //else
			ErrorMessageDTO msg = new ErrorMessageDTO().withMessage("E-Mail successfully sent");
            saveErrorMessage(request, msg, Globals.MESSAGE_KEY);
			return mapping.findForward("success");
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new EtccException(t);
        }
    }
}
