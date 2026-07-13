/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.PasswordRetrievalDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class PwdRetrievalAddEmailAddress extends CSCBaseAction {
//    private Logger logger = Logger.getLogger(PwdRetrievalAddEmailAddress.class);
    
    @Override
    public ActionForward execute (ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        OnlineAccessSetupDTO onlineAccessDTO = (OnlineAccessSetupDTO) session.getAttribute("passwordRetrievalDTO");
        OnlineAccessForm dynaForm =  (OnlineAccessForm) form;
        onlineAccessDTO.setEmailAddress(dynaForm.getEmailAddress());
        
        PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);
        onlineAccessDTO = delegate.addEmailAddress(acctLogin.getDbSessionId(), HttpDataUtil.getClientIpAddress( request ),onlineAccessDTO);
      //Weblogic upgrade cluster issues fix
        session.setAttribute("passwordRetrievalDTO",onlineAccessDTO);
      //Weblogic upgrade cluster issues fix ends 
        if (saveErrorMessages(request, onlineAccessDTO, "addEmailFailed")){
            return mapping.findForward("failure");
        } else {
            session.invalidate();
            return mapping.findForward("success");
        }
    }
}
