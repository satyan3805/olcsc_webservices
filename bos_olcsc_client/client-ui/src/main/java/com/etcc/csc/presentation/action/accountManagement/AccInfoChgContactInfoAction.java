package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgContactInfoAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
        OnlineAccessForm onlineAccessForm = (OnlineAccessForm)form;
        System.out.println("AccInfoChgContactInfoAction");
        
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();
        System.out.println("**ext=" + onlineAccessForm.getAlternatePhoneExt());
        
        AccountLoginDTO result = acctDel.updateContactInfo(""+acctLogin.getAcctId(), "AC",
                              acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(),
                              acctLogin.getLoginId(), onlineAccessForm.getEmailAddress(),
                              onlineAccessForm.getAlternateEmail(),
                              onlineAccessForm.getPrimaryPhone(), onlineAccessForm.getAlternatePhone(),
                              onlineAccessForm.getAlternatePhoneExt());
        
        if (saveErrorMessages(request, result, "changeContactInfoError")){
            return mapping.findForward("failure");
        }//else
             //acctLogin.setLoginId(result.getLoginId());
             //SessionUtil.setSessionAccountLogin(request.getSession(), acctLogin);
        
//        SessionUtil.getAcctDTO(request);
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
     }
}
