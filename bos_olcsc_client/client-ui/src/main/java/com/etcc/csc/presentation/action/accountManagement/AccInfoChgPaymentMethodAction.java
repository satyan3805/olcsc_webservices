/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;


public class AccInfoChgPaymentMethodAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception
     {
        BillingInfoForm billingInfoForm = (BillingInfoForm)form;
        AccountDTO acctInfo = SessionUtil.getAcctDTO(request);


        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();

        AccountPaymentMethodDTO primaryCard = billingInfoForm.newBillingInfoDTO(SessionUtil.getAcctDTO(request)).getPrimaryCard();

        BillingInfoDTO currentBillingInfo = acctDel.getBillingInfo(acctLogin);
        //msp 2464 : check null value
        if (currentBillingInfo != null && currentBillingInfo.getPrimaryCard() != null){
        	primaryCard.setAccountBillingMethodId(currentBillingInfo.getPrimaryCard().getAccountBillingMethodId());
        }
        //end msp 2464

		AccountLoginDTO result = acctDel.updateBillingInfo(acctLogin,billingInfoForm.newBillingInfoDTO(SessionUtil.getAcctDTO(request)));

        if (saveErrorMessages(request, result, "changePaymentMethodError")) {
            return mapping.findForward("failure");
        }
        if (billingInfoForm.isDiffBillingAddress()) {
            result = acctDel.updateBillingAddress(acctLogin, billingInfoForm);
            if (saveErrorMessages(request, result, "changePaymentMethodError"))
                return mapping.findForward("failure");
        }

        if (!billingInfoForm.isDiffBillingAddress()) {

			result = acctDel.updateBillingAddress(acctLogin, acctInfo);

        }

        if (Boolean.parseBoolean(request.getParameter("acctInfoRefund"))) {
            request.setAttribute("closure", Boolean.TRUE);
             request.setAttribute("acctInfoRefund","true");
             request.setAttribute("paymenttype","creditC");
            // return mapping.findForward("acctInfoPage");
         }
        //request.setAttribute("billingInfoForm", billingInfoForm);
        //request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);
        //SessionUtil.getAcctDTO(request);
        request.setAttribute("setEvent3", Boolean.TRUE);
        request.getSession().removeAttribute("creditCardList");
        return mapping.findForward("success");
     }
}
