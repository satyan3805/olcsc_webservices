/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import java.util.Arrays;
import java.util.List;

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

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.EtccActionException;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Action for validating account login as part of violation processing flow.
 * @author Milosh Boroyevich
 */
public class ViolationLoginAccountAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(ViolationLoginAccountAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	logger.debug("Validate EZ Account");
        	HttpSession session = request.getSession();
            String label = new AppDelegate().getSysParam(AppDelegate.UNPAID_TOLL_LABEL);
            int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
            SessionUtil.setCurrentTabMenuId(session, menuId);
            Object entryPoint = request.getParameter("returnAction");
            if (entryPoint != null) {
                request.setAttribute("returnAction", entryPoint);
            }
        	// Check if user is already logged in to OLCSC
            AccountDTO account = SessionUtil.getAcctDTO(request);
            DynaValidatorForm actionForm = (DynaValidatorForm)form;
            if (actionForm == null)
            	logger.info("accountUserForm is null!");
    		AccountLoginDTO acctLogin = null;
    		AccountDelegate acctDel = new AccountDelegate();
            if (actionForm != null && account == null) {
            	String userName = (String)actionForm.get("userName");
            	logger.info("Validate username/password for: " + userName);
            	if (!StringUtil.isEmpty(userName)) {
                	String password = (String)actionForm.get("password");
                	if (!StringUtil.isEmpty(password)) {
                		userName = userName.toUpperCase();
                		acctLogin = loginAccount(userName, password, acctDel, request, response);
                        if (acctLogin != null)
                            account = SessionUtil.getSessionAccount(acctLogin, session);
                	}
            	}
            }
            if (account != null) {
            	logger.debug("EZ Account Validated.  Now check the vehicle plate.");
            	// User is successfully logged in
            	ViolatorDTO violator = ((SelectedInvoiceForm) session.getAttribute("allInvoicesForm")).getViolatorDTO();
            	// TODO: These lines are for authfilter -- certainly don't need them all since we're not really logging in!
				String sessionId = session.getId();
				SessionUtil.getSessionAccountLogin(session).setDbSessionId(sessionId);
				violator.getAccountLoginDTO().setDbSessionId(sessionId);
        		HttpDataUtil.setDbSessionIdCookie(response, sessionId);
            	// TODO: The preceding lines are for authfilter **********************************
            	violator.setAccount(account);
            	if (validateVehicle(acctLogin, violator, acctDel)) {
            		return mapping.findForward("success");
            	} else {
            		TagRequestForm reqform = (TagRequestForm) request.getAttribute("tagRequestForm");
            		if (reqform == null) {
            			reqform = new TagRequestForm();
            		}
        			reqform.setLicensePlate(violator.getLicPlateNbr());
        			reqform.setState(violator.getLicPlateState());
            		request.setAttribute("tagRequestForm", reqform);
            		return mapping.findForward("addVehicle");
            	}
            } else {
            	logger.debug("EZ Account not found!");
            	return mapping.findForward("failure");
            }
        } catch (Throwable t) {
            String msg = "Error validating EZ account login for violation payment.";
            logger.debug(msg, t);
            throw new EtccSysException(msg, t);
        }
    }

    /**
     * Create a new Account Login DTO using the specified credentials.
     * @param userName
     * @param password
     * @param acctDel
     * @param request
     * @param response
     * @return the account login
     * @throws EtccSecurityException
     * @throws EtccException
     */
    private AccountLoginDTO loginAccount(String userName, String password, AccountDelegate acctDel, HttpServletRequest request, HttpServletResponse response) throws EtccSecurityException, EtccException {
		String target = null;
		AccountLoginDTO acctLogin = acctDel.loginAccount(userName, password, request);
        try {
        	target = acctDel.verifyLogin(acctLogin, request, response);
        } catch(EtccActionException e) {
        	ActionMessage a = e.getActionMessage();
            if (a == null) {
                saveErrorMessages(request, acctLogin, e.getMessage());
            } else {
                ActionMessages messages = getMessages(request);
                messages.add(DEFAULT_ERROR_KEY, a);
                saveMessages(request, messages);
            }
            logger.info("Failed login: " + userName);
        }

		if (target != null && (target.equals(AccountDelegate.LOGIN_RESULT_CHANGE_PASS)
				|| target.equals(AccountDelegate.LOGIN_RESULT_SUCCESS))) {
//			SessionUtil.setSessionAccountLogin(session, acctLogin);
			return acctLogin;
		}
        return null;
	}

    /**
     * Check if the license plate on the specified violator exists as a tag on the account login.
     * @param acctLogin
     * @param violator
     * @param acctDel
     * @return <tt>true</tt> if the account contains a tag corresponding to the specified license plate
     * @throws EtccSecurityException
     * @throws EtccException
     */
    private boolean validateVehicle(AccountLoginDTO acctLogin, ViolatorDTO violator, AccountDelegate acctDel) throws EtccSecurityException, EtccException {
    	TagDTO[] tagArray = acctDel.getAccountTags(acctLogin);
    	if (tagArray == null || tagArray.length < 1)
    		return false;
		List<TagDTO> tags = Arrays.asList(tagArray);
		logger.debug("Tags on account: " + tags);
		TagDTO violationTag = new TagDTO();
		violationTag.setLicPlate(violator.getLicPlateNbr());
		violationTag.setLicState(violator.getLicPlateState());
		logger.debug("Checking for existence of tag: " + violationTag);
		return tags.contains(violationTag);
	}
}
