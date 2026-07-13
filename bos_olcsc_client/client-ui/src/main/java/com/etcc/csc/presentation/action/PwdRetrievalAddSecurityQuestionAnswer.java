/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.PasswordRetrievalDelegate;
import com.etcc.csc.delegate.SecurityQuestionDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class PwdRetrievalAddSecurityQuestionAnswer extends CSCBaseAction{
//    private Logger logger = Logger.getLogger(PwdRetrievalAddSecurityQuestionAnswer.class);
    
    @Override
    public ActionForward execute (ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        OnlineAccessSetupDTO onlineAccessDTO = (OnlineAccessSetupDTO) session.getAttribute("passwordRetrievalDTO");
        OnlineAccessForm dynaForm =  (OnlineAccessForm) form;
        //TODO need to check
        //onlineAccessDTO.setSecurityQuestion(dynaForm.getSecurityQuestion());
        onlineAccessDTO.setSecurityQuestionAnswer(dynaForm.getSecurityQuestionAnswer());
        
        PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);
        onlineAccessDTO = delegate.addSecurityQuestionAnswer(acctLogin.getDbSessionId(), HttpDataUtil.getClientIpAddress( request ),onlineAccessDTO);
      //Weblogic upgrade cluster issues fix
        session.setAttribute("passwordRetrievalDTO",onlineAccessDTO);
      //Weblogic upgrade cluster issues fix ends 
        if (onlineAccessDTO.hasErrors()){
            ErrorMessageDTO msg = new ErrorMessageDTO();
            msg.setKey("error.passwordRetrieval.addSecurity.addFailed");
            msg.setMessage(new AppDelegate().getContactPhoneNumber());
            saveErrorMessage(request, msg, "addFailed");

            Collection<LabelValueBean> securityQuestions =  getSecurityQuestions();
            request.setAttribute("securityQuestions", securityQuestions);
            return mapping.findForward("failure");
        }//else
            if (onlineAccessDTO.getEmailAddress()!=null){
            	session.invalidate();
                return mapping.findForward("success");
            }//else
            return mapping.findForward("addEmailAddress");
    }

    private Collection<LabelValueBean> getSecurityQuestions() throws EtccException, EtccSecurityException {
        SecurityQuestionDelegate sQuestionDelegate =  new SecurityQuestionDelegate();
        Collection<SecurityQuestionDTO> questionList =  sQuestionDelegate.getSecurityQuestions();
        Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
        for (SecurityQuestionDTO securityQuestionDTO : questionList) {
            String question = securityQuestionDTO.getSecurityQuestion();
            result.add(new LabelValueBean(question, question));
        }
        return result;
    }

    @Override
    protected String toString(ErrorMessageDTO error) {
        return error.getMessage();
    }
}
