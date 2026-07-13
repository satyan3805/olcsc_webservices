package com.etcc.csc.presentation.action;

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
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class PwdRetrievalRetrieveSecurityQuestion extends CSCBaseAction {
	private static final Logger logger = Logger
			.getLogger(PwdRetrievalRetrieveSecurityQuestion.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		logger.trace("***in PwdRetrievalRetrieveSecurityQuestion");
		OnlineAccessSetupDTO onlineAccessDTO = new OnlineAccessSetupDTO();
		OnlineAccessForm dynaForm = (OnlineAccessForm) form;
		if (dynaForm.getAcctId() != null) {
			String acctString = dynaForm.getAcctId();
			if (acctString.trim().length() > 0)
				onlineAccessDTO.setAcctId(Long.parseLong(acctString));
		}

		String tagID = dynaForm.getTagId();
		if (tagID.indexOf("-") > 0) {
			onlineAccessDTO.setAgencyId(tagID.substring(0, tagID.indexOf("-"))
					.toUpperCase());
			onlineAccessDTO.setTagId(tagID.substring(tagID.indexOf("-") + 1));
		} else {
			// onlineAccessDTO.setAgencyId(dynaForm.getAgencyId());
			onlineAccessDTO.setAgencyId(dynaForm.getTagIdentifier());
			onlineAccessDTO.setTagId(tagID);
		}
		onlineAccessDTO.setCompanyTaxId(dynaForm.getCompanyTaxId());
		onlineAccessDTO.setDriverLicNbr(dynaForm.getDriverLicNbr());
		onlineAccessDTO.setDriverLicState(dynaForm.getDriverLicState());
		logger
				.trace("***in PwdRetrievalRetrieveSecurityQuestion.onlineAccessDTO.1="
						+ onlineAccessDTO);
		onlineAccessDTO.setEmailAddress(dynaForm.getEmailAddress());
		PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
		onlineAccessDTO = delegate.retrieveAccountInfo(onlineAccessDTO,
				HttpDataUtil.getClientIpAddress(request), session.getId(),
				HttpDataUtil.getUserAgentAttributes(request));

		logger
				.info("***in PwdRetrievalRetrieveSecurityQuestion.onlineAccessDTO="
						+ onlineAccessDTO);

		if (saveErrorMessages(request, onlineAccessDTO, "invalidAccountInfo")) {
			return mapping.findForward("failure");
		}

		// if (onlineAccessDTO.getErrors() != null) {
		// Collection errors = onlineAccessDTO.getErrors();
		// Iterator itr = errors.iterator();
		// ActionMessages messages = new ActionMessages();
		// if (itr.hasNext()) {
		// String error = (String)itr.next();
		// ActionMessage msg = new ActionMessage(error, false);
		// messages.add("invalidAccountInfo", msg);
		// logger.trace("***in PwdRetrievalRetrieveSecurityQuestion.err.1.msg="
		// + msg);
		// }
		// saveMessages(request, messages);
		// return mapping.findForward("failure");
		// }

		// TODO: set session info
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

		logger.info("PwdRetrievalRetrieveSecurityQuestion.securityQuestion="
				+ securityQuestion + ";eMail=" + eMail);
		if (eMail != null  && tagID == null && dynaForm.getAcctId() == null) {
			return mapping.findForward("emailRequest");
		}
		if ((securityQuestion != null && securityQuestion.length() > 0)
				&& (eMail != null && eMail.length() > 0)) {
			logger.info("SecurityQuestion:" + securityQuestion);
			return mapping.findForward("success");
		}// else
		// TODO No sec qn on file OR sec qn on file no more active -> Contact
		// CSR
		logger.info("PwdRetrievalRetrieveSecurityQuestion.contactcsr");
		return mapping.findForward("contactcsr");
	}
}
