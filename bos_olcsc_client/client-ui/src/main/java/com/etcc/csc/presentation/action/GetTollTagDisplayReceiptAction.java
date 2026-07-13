package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.CreditCardDelegate;

import com.etcc.csc.presentation.validator.CreditCardValidator;
import com.etcc.csc.presentation.viewhelper.GetTollTagReviewViewHelper;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GetTollTagDisplayReceiptAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();                                 
        DynaActionForm dynaForm = (DynaActionForm)form;

        GetTollTagReviewViewHelper viewHelper = 
            new GetTollTagReviewViewHelper(SessionUtil.getSavedVehicleMap(session), 
                                           ((Double)session.getAttribute("getTolltagPaymentAmount")).doubleValue(), 
                                           ActionFormUtil.getString(dynaForm, 
                                        		   CreditCardValidator.FIELD_CC_TYPE), 
                                           new CreditCardDelegate().getCreditCardTypes());
        request.setAttribute("viewHelper", viewHelper);
        return mapping.findForward("success");
    }
}
