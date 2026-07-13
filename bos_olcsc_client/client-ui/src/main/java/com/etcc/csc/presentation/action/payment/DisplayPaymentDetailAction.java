package com.etcc.csc.presentation.action.payment;

import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.util.SessionUtil;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class DisplayPaymentDetailAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
        processPopulate(paymentContext, form);
        return mapping.findForward("success");
    }

    private void processPopulate(PaymentContext paymentContext,
                                 ActionForm form) {
        DynaActionForm dynaForm = (DynaActionForm)form;

        Invoice [] invoices = paymentContext.getInvoices();
        ArrayList<String> invoicesSelected = new ArrayList<String>();
        for (int i = 0; invoices != null && i < invoices.length; i++) {
            if ((invoices[i]).isAuthorized()) {
                invoicesSelected.add((invoices[i]).getId() );
            }
        }
        dynaForm.set("payInvoice", invoicesSelected.toArray( new String[0] ) );

        Violation [] violations = paymentContext.getViolations();
        ArrayList<String> violationsSelected = new ArrayList<String>();
        for (int i = 0; violations != null && i < violations.length; i++) {
            if (violations[i].isAuthorized()) {
                violationsSelected.add(violations[i].getId() );
            }
        }
        dynaForm.set( "payViolation", violationsSelected.toArray( new String[0] ) );
        dynaForm.set( "acceptVea", paymentContext.isVeaAccepted( ) ? "Y" : "" );
        dynaForm.set( "tagAmount", paymentContext.getTagAmount( ).toPlainString( ) );
    }
}
