/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.PasswordRetrievalDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class AccInfoEmailURLValidationAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccInfoEmailURLValidationAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
    logger.debug("AccInfoEmailURLValidationAction.execute()");
    	HttpSession session = request.getSession();
        OnlineAccessSetupDTO onlineAccessDTO = new OnlineAccessSetupDTO();
        OnlineAccessForm emailValidForm = (OnlineAccessForm)form;
       
        logger.debug("AccInfoEmailURLValidationAction.execute()"); 
          
            if(request.getParameter("id") != null){
            	onlineAccessDTO.setValidationId( request.getParameter("id"));
            }           
        
      PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
    		onlineAccessDTO = delegate.validationData(onlineAccessDTO,
    				HttpDataUtil.getClientIpAddress(request), session.getId(),
    				HttpDataUtil.getUserAgentAttributes(request));
    		
    if (saveErrorMessages(request, onlineAccessDTO, "invalidAccountInfo")) {
    			return mapping.findForward("failure");
    }
	long acctId = onlineAccessDTO.getAcctId();
	AccountLoginDTO acctLogin = new AccountLoginDTO();
	acctLogin.setAcctId(acctId);
	acctLogin.setLastLoginIp(HttpDataUtil.getClientIpAddress(request));
	acctLogin.setLoginId(AccountLoginDTO.GENERIC_USER);
	logger.info("Session Created: " + acctLogin);
	SessionUtil.setSessionAccountLogin(session, acctLogin);
	onlineAccessDTO.setUserID(onlineAccessDTO.getLoginId());
	onlineAccessDTO.setLoginId(acctLogin.getLoginId());
        session.setAttribute("passwordRetrievalDTO", onlineAccessDTO);
        
        if (onlineAccessDTO.getSecurityQuestionAnswer()!= null) {
			logger.info("SecurityQuestion:" + onlineAccessDTO.getSecurityQuestionAnswer());
			return mapping.findForward("validateSecurityQuestion");
		}
        return mapping.findForward("success");
    }
     
}
