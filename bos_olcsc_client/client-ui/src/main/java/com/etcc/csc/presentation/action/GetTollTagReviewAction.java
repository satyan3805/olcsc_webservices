/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.delegate.TolltagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
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

public class GetTollTagReviewAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;

        HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);

        double paymentAmount = 
            new TolltagDelegate().getTagPayInfo(accountLoginDTO.getAcctId(), 
            		accountLoginDTO.getDbSessionId(), 
            		accountLoginDTO.getLoginId(), 
            		accountLoginDTO.getLastLoginIp(), 
            		SessionUtil.getRetailTransId(session),
            		SessionUtil.getPosId(request));
        session.setAttribute( "getTolltagPaymentAmount", new Double(paymentAmount) );
        GetTollTagReviewViewHelper viewHelper = 
            new GetTollTagReviewViewHelper(SessionUtil.getSavedVehicleMap(session), 
                                           paymentAmount, 
                                           ActionFormUtil.getString(dynaForm, 
                                        		   CreditCardValidator.FIELD_CC_TYPE), 
                                           new CreditCardDelegate().getCreditCardTypes());

        request.setAttribute("viewHelper", viewHelper);

        return mapping.findForward("success");
    }
}
