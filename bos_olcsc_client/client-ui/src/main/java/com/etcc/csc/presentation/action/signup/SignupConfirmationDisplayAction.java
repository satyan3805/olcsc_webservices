package com.etcc.csc.presentation.action.signup;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SignupConfirmationDisplayAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    	//getCardNameByCode(request);
    	SessionUtil.getAcctPrefDTO(request);
    	AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
    	request.setAttribute("acctId", loginDTO.getAcctId());
    	return mapping.findForward("success");
    }

    private void getCardNameByCode(HttpServletRequest request) throws EtccException {
    	HttpSession session = request.getSession();
    	if (session != null) {
    		//BillingInfoForm billingInfoForm = (BillingInfoForm) session.getAttribute("billingInfoForm");
    		BillingInfoDTO billingInfoDTO = (BillingInfoDTO) session.getAttribute("billingInfoDTO");//billingInfoForm.newBillingInfoDTO((ContactInfoForm) session.getAttribute("contactInfoForm"));
            //request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoDTO.getPrimaryCard().getCardType()));

//            TagRequestForm tagRequestForm =(TagRequestForm)session.getAttribute("tagRequestForm");
    	}
    }
}
