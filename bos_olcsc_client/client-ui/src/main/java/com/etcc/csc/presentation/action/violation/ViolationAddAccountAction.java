/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;

/**
 * Action for validating a new EZ account (including one vehicle) and adding it to the cart for processing.
 * Note: this action will also add a vehicle to the cart if a valid account is present on the violator.
 * @author Milosh Boroyevich
 */
public class ViolationAddAccountAction extends Action {
    private static final Logger logger = Logger.getLogger(ViolationAddAccountAction.class);

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	HttpSession session = request.getSession();
//            String label = new AppDelegate().getSysParam(AppDelegate.UNPAID_TOLL_LABEL);
//            int menuId = SessionUtil.getPageMenuIdByLabel(request, label);
//            SessionUtil.setCurrentTabMenuId(request.getSession(), menuId);
//            Object entryPoint = request.getParameter("returnAction");
//            if (entryPoint != null) {
//                request.setAttribute("returnAction", entryPoint);
//            }
        	// Get the violator
        	ViolatorDTO violator = ((SelectedInvoiceForm) session.getAttribute("allInvoicesForm")).getViolatorDTO();
            if (addAccount(violator, form) && addVehicle(violator, form)) {
            	return mapping.findForward("success");
            }
            return mapping.findForward("failure");
        } catch (Throwable t) {
            String msg = "Error validating EZ account login for violation payment.";
            logger.debug(msg, t);
            throw new EtccSysException(msg, t);
        }
    }

	/**
	 * Create a new account, add it to the cart and log in. 
	 * Note: if a valid account already exists in the session (i.e. user is
	 * authenticated), return <tt>true</tt>.
	 * @param violator 
	 * @param actionForm 
	 * @return <tt>true</tt> when account is logged in
	 */
    private boolean addAccount(ViolatorDTO violator, ActionForm actionForm) {
    	AccountDTO account = violator.getAccount();
    	if (account != null) {
    		// account already exists
    		return true;
    	} else {
    		if (actionForm != null) {
    			logger.debug("Create lite account");
    			// TODO: Create a new account object and add it to the cart
//    	    	String userName = (String)actionForm.get("userName");
//    	    	logger.info("Validate username/password for: " + userName);
//    	    	if (!StringUtil.isEmpty(userName)) {
//    	        	String password = (String)actionForm.get("password");
//    	        	if (!StringUtil.isEmpty(password)) {
//    	        		userName = userName.toUpperCase();
//    	        		acctLogin = loginAccount(userName, password, acctDel, request, response);
//    	        		acct = acctDel.getAccount(acctLogin);
//    	        	}
//    	    	}
    			return true;
    		} else {
    			logger.warn("Bad state: no account found and form is empty!");
    		}
    	}
    	logger.info("Failed adding account");
		return false;
	}

    /**
     * Add a vehicle described in the action form for the specified violator.
     * @param violator
     * @param actionForm
     * @return <tt>true</tt> on success
     */
    private boolean addVehicle(ViolatorDTO violator, ActionForm actionForm) {
		// TODO perform the vehicle add function using the appropriate delegate
		return true;
	}
}
