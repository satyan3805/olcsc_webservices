/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;


import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.presentation.EtccActionException;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class AuthenticateUserAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AuthenticateUserAction.class);
     
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
//        SessionUtil.resetCurrentTabMenuId(request.getSession());

        logger.info("***entered AuthenticateUserAction");      
               
       
        HttpSession session = request.getSession();
        
            
        session.removeAttribute( "OnlineAccessForm" );
        session.removeAttribute( "contactInfoForm" );
        session.removeAttribute( "tagRequestForm" );
        session.removeAttribute( "billingInfoForm" );
        session.removeAttribute("password");
        session.removeAttribute("whichPage");       
        //logger.debug("The Email Valid form from JSP Page " + request.getAttribute("acctId"));
        request.setAttribute("acctId",request.getParameter("acctId"));        
        DynaValidatorForm actionForm = (DynaValidatorForm)form;        
        String userName = (String)actionForm.get("userName");
        if (userName != null) {
            userName = userName.toUpperCase();
        }
        String password = (String)actionForm.get("password");
        session.setAttribute("password",password);
        boolean rememberUser = false;
//        try {
//            rememberUser = ((Boolean)actionForm.get("remember"))
//                .booleanValue();
//        } catch (NullPointerException npe) {
//            // ignore error
//        }

        String ipAddress = HttpDataUtil.getClientIpAddress( request );

  //      Cookie userNameCookie = new Cookie( "NTTACustomerServiceUserName", userName );
  //      if (rememberUser) {
  //          userNameCookie.setMaxAge( 60 * 60 * 24 * 30 ); 
  //      } else {
  //          userNameCookie.setMaxAge(0); 
  //      }
  //      response.addCookie( userNameCookie );
               

        AccountDelegate acctDel = new AccountDelegate();
        logger.info("User login: " + userName + "/" + ipAddress);
        UserEnvDTO userEnvDto = HttpDataUtil.getUserAgentAttributes(request);
        logger.debug("***going to call acctDel.loginAccount");
        AccountLoginDTO acctLogin = acctDel.loginAccount(userName, 
            password, ipAddress, session.getId(), userEnvDto);

        
              
        String forward = AccountDelegate.LOGIN_RESULT_FAILURE;
        try {
        	forward = acctDel.verifyLogin(acctLogin, request, response);
    		String userInfoCookie = HttpDataUtil.createUserInfoCookie(SessionUtil.getAcctDTO(request));
    		HttpDataUtil.setUserInfoCookie(response, userInfoCookie);
    		SessionUtil.setLoginEntryPoint(session, AccountLoginDTO.LoginType.AC.toString());
        } catch(EtccActionException e) {
        	ActionMessage a = e.getActionMessage();
            if (a == null) {
                saveErrorMessages(request, acctLogin, e.getMessage());
            } else {
                ActionMessages messages = getMessages(request);
                messages.add(e.getMessage(), a);
                saveMessages(request, messages);
            }
            logger.info("Failed login: " + userName + "/" + ipAddress);
        }

        int requiredUpdate = AccountInterface.NONE;
		if (forward.equals(AccountDelegate.LOGIN_RESULT_CHANGE_PASS)) {
			requiredUpdate += AccountInterface.PASSWORD; // old +1
		} else if (!forward.equals(AccountDelegate.LOGIN_RESULT_FAILURE)) {
        	AccountDTO acctDTO = SessionUtil.getAcctDTO(request);            
            if (acctDTO.getEmailAddress()==null || 
                acctDTO.getEmailAddress().trim().length()==0){
//                request.setAttribute("emailChangeRequired", "true");
                requiredUpdate += AccountInterface.EMAIL; // old +2
                forward = AccountDelegate.LOGIN_RESULT_CHANGE_PASS;
            }
            if (acctDTO.getSecurityQuestionID() <= 0) {
//                request.setAttribute("sqChangeRequired", true);
                requiredUpdate += AccountInterface.SECURITY_QA; // old +4
                forward = AccountDelegate.LOGIN_RESULT_CHANGE_PASS;
            }
        }

        request.setAttribute("requiredUpdate", Integer.valueOf(requiredUpdate));
        request.setAttribute("setEvent2", Boolean.TRUE);

//        SessionUtil.setProtocal(request.getSession(), SslUtil.HTTPS);
        
       
       
        
        return mapping.findForward(forward);
    }
}
