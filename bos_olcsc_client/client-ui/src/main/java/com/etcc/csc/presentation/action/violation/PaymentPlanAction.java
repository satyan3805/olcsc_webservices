package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.PaymentPlanDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.HttpDataUtil;

public class PaymentPlanAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(PaymentPlanAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

        SelectedInvoiceForm selectedInvoiceForm = (SelectedInvoiceForm)form;
        String agency = request.getParameter("agencyId");
        logger.debug("PaymentPlanAction.agencyId= " + agency);
        request.setAttribute("agencyId",agency);
        ViolationDelegate violationDelegate = new ViolationDelegate();
        String licKey = selectedInvoiceForm.getViolatorDTO().getLicPlateState()
                        + "-" + selectedInvoiceForm.getViolatorDTO().getLicPlateNbr()
                        + "-NORMAL";
        
        PaymentPlanDTO paymentPlan = violationDelegate.getPaymentPlan(licKey, 
                            selectedInvoiceForm.getViolatorDTO().getAccountLoginDTO().getInvoiceId(), 
                            selectedInvoiceForm.getViolatorDTO().getLicPlateNbr(),
                            selectedInvoiceForm.getViolatorDTO().getAccountLoginDTO().getDbSessionId(),
                            HttpDataUtil.getClientIpAddress(request), 
                            selectedInvoiceForm.getViolatorDTO().getAccountLoginDTO().getAcctId());
        
        request.setAttribute("paymentPlan", paymentPlan);

        if (saveErrorMessages(request, paymentPlan, "paymentPlanError")){
            return mapping.findForward("success");
        }
        return mapping.findForward("success");
    }
}
