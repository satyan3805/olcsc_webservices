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

public class PwdRetrievalValidateSecurityAnswer extends CSCBaseAction {
//    private Logger logger = Logger.getLogger(PwdRetrievalValidateSecurityAnswer.class);
    
    @Override
    public ActionForward execute (ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        OnlineAccessSetupDTO onlineAccessDTO = (OnlineAccessSetupDTO) session.getAttribute("passwordRetrievalDTO");
        request.setAttribute("retrieveSecAnsError","");
        OnlineAccessForm dynaForm =  (OnlineAccessForm) form;
        onlineAccessDTO.setSecurityQuestionAnswer(dynaForm.getSecurityQuestionAnswer());
        
        PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);
            String userid=onlineAccessDTO.getUserID();
            
            if(acctLogin.getDbSessionId() == null){
            	acctLogin.setDbSessionId(onlineAccessDTO.getDbSessionId());
            }
            try{
        onlineAccessDTO = delegate.validateSecurityAnswer(acctLogin.getDbSessionId(), HttpDataUtil.getClientIpAddress( request ),onlineAccessDTO);
            }catch(Throwable e) {
            	e.printStackTrace();
            }
        if (saveErrorMessages(request, onlineAccessDTO, "invalidSecurityAnswer")){
            request.setAttribute("retrieveSecAnsError","Security answer is incorrect");
            return mapping.findForward("failure");
        }//else
            if (onlineAccessDTO.getEmailAddress()!=null){
                //TODO Why invalidate the session?
            	session.invalidate();
            	session = request.getSession();
                onlineAccessDTO.setUserID(userid);
                onlineAccessDTO.setLoginId(acctLogin.getLoginId());
                session.setAttribute("passwordRetrievalDTO", onlineAccessDTO);
                SessionUtil.setSessionAccountLogin(session, acctLogin);
                return mapping.findForward("success");
            }//else
            //TODO contact CSR
            return mapping.findForward("addEmailAddress");
    }
}
