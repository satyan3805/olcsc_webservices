/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

/**
 * To support 2nd pass through signup process, because the user
 * failed to complete signup on 1st pass.  This class gets all the data
 * the user entered during the failed 1st attempt to signup so that
 * data will be available in the request for all of the signup pages.
 */
public class SignupContactInfoUpdateAction extends CSCBaseAction {
//	private static final Logger logger = Logger.getLogger(SignupContactInfoUpdateAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping,
			       ActionForm form, 
			       HttpServletRequest request,
			       HttpServletResponse response) 
                                      throws Exception {

      AccountDTO acctDto;

    AccountDelegate acctDel = new AccountDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());

    if (acctLoginDto == null) {
      throw new EtccSecurityException("Session Timed out in SignupContactInfoUpdateAction");
    }

    //BUG: Why the two remote calls here?
    acctDto = SessionUtil.getAcctDTO(request);
    acctDto = acctDel.getAccountStatus(acctLoginDto, acctDto);
    
    if (saveErrorMessages(request, acctDto, "acctInfoError")){
        return mapping.findForward("success");
    }

    if (acctDto.isSuspensionFlag()) {
        ActionMessages messages = getMessages(request);
        ActionMessage msg;
        if (acctDto.isViolationFlag()) {
            msg = new ActionMessage("account.suspended.with.violation", 
			    acctDto.getViolationLicPlatesDisplay(), 
			    request.getContextPath()+"/violatorLoginDisplay.do?returnAction=accountInformation");
        } else {
            msg = new ActionMessage("account.suspended.without.violation");
        }
        messages.add("alerts", msg);
        saveMessages(request, messages);
    }

    ContactInfoForm contactInfoForm = new ContactInfoForm(acctDto);

    TagDelegate tagRequestDel = new TagDelegate();
    AccountTagsDTO acctTagsDto = tagRequestDel.getAccountTags(acctLoginDto, null);
    Collection<TagDTO> tagsDtoList = acctTagsDto.getAccountTags();
    TagRequestForm tagRequestForm = new TagRequestForm();
    tagRequestForm.setSavedVehicles( tagsDtoList instanceof List<?> ? (List<TagDTO>)tagsDtoList : new ArrayList<TagDTO>(tagsDtoList));

    BillingInfoDTO billingInfoDto = SessionUtil.getBillingInfo(request);
    BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfoDto, acctDto);

    SessionUtil.getAcctPrefDTO(request);

    request.setAttribute("contactInfoForm", contactInfoForm);
    request.setAttribute("tagRequestForm", tagRequestForm);
    request.setAttribute("billingInfoForm", billingInfoForm);

    return mapping.findForward("success");
  }
}
