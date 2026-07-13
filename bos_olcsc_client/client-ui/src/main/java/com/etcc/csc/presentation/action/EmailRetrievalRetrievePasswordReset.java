  package com.etcc.csc.presentation.action;

 import com.etcc.csc.dto.AccountLoginDTO;
 import com.etcc.csc.dto.OnlineAccessSetupDTO;
 import com.etcc.csc.presentation.action.onlineAccess.OnlineAccessUserInfoAction;
 import com.etcc.csc.common.EtccException;
 import com.etcc.csc.common.Logger;
 import com.etcc.csc.delegate.AppDelegate;
 import com.etcc.csc.delegate.PasswordRetrievalDelegate;
 import com.etcc.csc.delegate.SecurityQuestionDelegate;

 import com.etcc.csc.presentation.form.OnlineAccessForm;
 import com.etcc.csc.util.DtoUtil;
 import com.etcc.csc.util.HttpDataUtil;

 import java.util.ArrayList;
 import java.util.Collection;

 import java.util.Iterator;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;

 import org.apache.commons.beanutils.BeanUtils;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionForm;
 import com.etcc.csc.dto.ErrorMessageDTO;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.action.ActionMessage;
 import org.apache.struts.action.ActionMessages;
 import org.apache.struts.util.LabelValueBean;
 import org.apache.struts.validator.DynaValidatorActionForm;
 import com.etcc.csc.util.SessionUtil;

 public class EmailRetrievalRetrievePasswordReset extends CSCBaseAction {
 	private Logger logger = Logger
 			.getLogger(EmailRetrievalRetrievePasswordReset.class);

 	public EmailRetrievalRetrievePasswordReset() {
 	}

 	public ActionForward execute(ActionMapping mapping, ActionForm form,
 			HttpServletRequest request, HttpServletResponse response)
 			throws Exception {
 		HttpSession session = request.getSession();
 		System.out.println("***in EmailRetrievalRetrievePasswordReset");
 		OnlineAccessSetupDTO onlineAccessDTO = new OnlineAccessSetupDTO();
 		OnlineAccessForm dynaForm = (OnlineAccessForm) form;
 		  String emailAddress = dynaForm.getEmailAddress();
 		if (emailAddress != null) {
 				onlineAccessDTO.setEmailAddress(emailAddress);
 		}
 		
 		PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
 		onlineAccessDTO = delegate.retrieveEmailAddressInfo(onlineAccessDTO,
 				HttpDataUtil.getClientIpAddress(request), request.getSession()
 						.getId(), HttpDataUtil.getUserAgentAttributes(request));

 		logger.info("***in EmailRetrievalRetrievePasswordReset.onlineAccessDTO="
 						+ onlineAccessDTO);
 		
 		if (onlineAccessDTO.hasErrors()){
 			saveErrorMessages(request, onlineAccessDTO, "invalidAccountInfo");
 			request.setAttribute("showContactLink", onlineAccessDTO.hasErrors());
            return mapping.findForward("failure");
        }
 		
 		long acctId = onlineAccessDTO.getAcctId();
		AccountLoginDTO acctLogin = new AccountLoginDTO();
		acctLogin.setAcctId(acctId);
		acctLogin.setDbSessionId(onlineAccessDTO.getDbSessionId());
		acctLogin.setLastLoginIp(HttpDataUtil.getClientIpAddress(request));
		acctLogin.setLoginId(AccountLoginDTO.GENERIC_USER);
		logger.info("Session Created: " + acctLogin);
		SessionUtil.setSessionAccountLogin(session, acctLogin);
		onlineAccessDTO.setUserID(onlineAccessDTO.getLoginId());
		onlineAccessDTO.setLoginId(acctLogin.getLoginId());
		HttpDataUtil.setDbSessionIdCookie(response, acctLogin.getDbSessionId());

		session.setAttribute("passwordRetrievalDTO", onlineAccessDTO);

		String securityQuestion = onlineAccessDTO.getSecurityQuestion();
		String eMail = onlineAccessDTO.getEmailAddress();
		
		if  ((securityQuestion != null && securityQuestion.length() > 0)
				&& (eMail != null && eMail.length() > 0)) {
			return mapping.findForward("success");
		}// else
		// TODO No sec qn on file OR sec qn on file no more active -> Contact
		// CSR
		logger.info("PwdRetrievalRetrieveSecurityQuestion.contactcsr");
		return mapping.findForward("contactcsr");
 	}

 	}
 
