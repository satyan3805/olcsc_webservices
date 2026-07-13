package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoDispChgUsernameAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
//        SessionUtil.getAcctDTO(request);

        OnlineAccessForm onlineAccessForm = new OnlineAccessForm();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        onlineAccessForm.setLoginId(acctLoginDto.getLoginId());
        request.setAttribute("OnlineAccessForm", onlineAccessForm);

        return mapping.findForward("success");
     }
}
