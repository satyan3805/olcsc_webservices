/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgSecQnAction extends CSCBaseAction{
	private static Logger logger = Logger.getLogger(AccInfoChgSecQnAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception
     {

        OnlineAccessForm dynaActionForm = (OnlineAccessForm)form;
        //System.out.println("AccInfoChgSecQnAction.password=" + dynaActionForm.getPassword());
        System.out.println("AccInfoChgSecQnAction.securityQuestionID=" + dynaActionForm.getSecurityQuestionID());
        System.out.println("AccInfoChgSecQnAction.securityQuestionAnswer=" + dynaActionForm.getSecurityQuestionAnswer());
         request.setAttribute("changeSecQnMessage", "");
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());

        AccountDelegate acctDel = new AccountDelegate();
        BaseDTO result;
        try {
        	
        	// Modified for D15822 begin
			result = acctDel.updatePasswordEmailSecurityQA(acctLogin, AccountInterface.SECURITY_QA, dynaActionForm.getPassword(),null, null, 
            Integer.parseInt(dynaActionForm.getSecurityQuestionID()), dynaActionForm.getSecurityQuestionAnswer());
		// end	
//            result = acctDel.updatePasswordEmailSecurityQA(acctLogin, AccountInterface.SECURITY_QA, null, null, null, 
//                    Integer.parseInt(dynaActionForm.getSecurityQuestionID()), dynaActionForm.getSecurityQuestionAnswer());
////                acctDel.updateSecQn(acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(),
//                                  acctLogin.getLoginId(), dynaActionForm.getPassword(),
//                                  acctLogin.getAcctId()+"", Integer.parseInt(dynaActionForm.getSecurityQuestionID()),
//                                  dynaActionForm.getSecurityQuestionAnswer());
        } catch (EtccSecurityException se) {
            logger.trace("Security error in AccInfoChgSecQnAction.", se);
            return mapping.findForward("securityError");
        }

        if (saveErrorMessages(request, result, "changeSecQnError")){
            request.setAttribute("changeSecQnMessage", "Invalid Security Question");
            return mapping.findForward("failure");
        }

        SessionUtil.getAcctDTO(request);
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
     }
}
