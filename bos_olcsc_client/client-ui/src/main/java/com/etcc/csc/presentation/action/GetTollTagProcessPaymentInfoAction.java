/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.delegate.TolltagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.presentation.validator.CreditCardValidator;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GetTollTagProcessPaymentInfoAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);

        try {
        new TolltagDelegate().setPaymentInfo(accountLoginDTO.getAcctId(), 
        		accountLoginDTO.getDbSessionId(), 
                accountLoginDTO.getLastLoginIp(), 
                accountLoginDTO.getLoginId(), 
                SessionUtil.getRetailTransId(session), 
                ActionFormUtil.getString(dynaForm, "billingName"), 
                ActionFormUtil.getString(dynaForm, "billingAddress"), 
                ActionFormUtil.getString(dynaForm, "billingAddressLine2"), 
                ActionFormUtil.getString(dynaForm, "billingCity"), 
                ActionFormUtil.getString(dynaForm, "billingState"), 
                ActionFormUtil.getString(dynaForm, "billingZipcode"), 
                ActionFormUtil.getString(dynaForm, "billingPlus4"), 
                ActionFormUtil.getString(dynaForm, 
               		 CreditCardValidator.FIELD_CC_NUMBER), 
               	CreditCardDTO.CreditCardType.valueOfCode(
               			ActionFormUtil.getString(dynaForm, 
               					CreditCardValidator.FIELD_CC_TYPE)), 
                ActionFormUtil.getString(dynaForm, 
               		 CreditCardValidator.FIELD_CC_EXPIRE_MONTH), 
                ActionFormUtil.getString(dynaForm, 
               		 CreditCardValidator.FIELD_CC_EXPIRE_YEAR));

        } catch (EtccErrorMessageException errorMessageException) {
            saveErrorMessages(request, errorMessageException);
            return mapping.findForward("failure");
        }
        return mapping.findForward("success");
    }

/*
    private void processErrorMessages(EtccErrorMessageException errorMessageException, 
                                      HttpServletRequest request) {
        ActionMessages actionMessages = new ActionMessages();
        for (String errMsg : errorMessageException.getErrorMessages()) {
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, DtoUtil.createActionMessage(errMsg, false));
        }
        saveErrors(request, actionMessages);
    }
*/
}
