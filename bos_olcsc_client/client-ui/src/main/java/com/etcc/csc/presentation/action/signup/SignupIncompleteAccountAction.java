/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.util.Collection;
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

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AuthorizedContactDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
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
public class SignupIncompleteAccountAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(SignupIncompleteAccountAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping,
			       ActionForm form, 
			       HttpServletRequest request,
			       HttpServletResponse response) 
    throws Exception {

		AccountDelegate acctDel = new AccountDelegate();
		HttpSession session = request.getSession();
		AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin( session );
		if (acctLoginDto == null) {
			throw new EtccSecurityException("Session Timed out in SignupIncompleteAccountAction");
		}

		//BUG: Why two remote calls here?
		AccountDTO acctDto = SessionUtil.getAcctDTO(request);
		acctDto = acctDel.getAccountStatus(acctLoginDto, acctDto);

		if (saveErrorMessages(request, acctDto, "acctInfoError")){
			return mapping.findForward("success");
		}

		if (acctDto.isSuspensionFlag()) {
            ActionMessages messages = this.getMessages(request);
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

        AuthorizedContactDelegate authDel = new AuthorizedContactDelegate();
        AuthorizedContactDTO[] authContacts = authDel.getAuthContacts(acctLoginDto);
		if (acctDto.getFirstName() != null) {
			ContactInfoForm contactInfoForm = new ContactInfoForm(acctDto);
			contactInfoForm.setAuthorizedContacts(authContacts, authDel.getAuthContactLimit(), ContactInfoForm.WhichPage.INCOMPLETE);
			request.setAttribute("contactInfoForm", contactInfoForm);
			session.setAttribute("contactInfoForm", contactInfoForm);
			session.setAttribute("whichPage", ContactInfoForm.WhichPage.INCOMPLETE.getTag());

			TagDelegate tagRequestDel = new TagDelegate();
			AccountTagsDTO acctTagsDto = tagRequestDel.getAccountTags(acctLoginDto, null);

			Collection<TagDTO> tagsDtoList = acctTagsDto.getAccountTags();
			if (tagsDtoList != null) {
			    Collection<TagDTO> tagsDtoListPlate = acctTagsDto.getPbpTags();     
			    //EZPLATE ==> ADD any EZPlate if it exists
			    if(tagsDtoListPlate != null && tagsDtoListPlate.size() > 0) {
			        TagDTO tagDTO = tagsDtoListPlate.iterator().next();
			        tagsDtoList.add(tagDTO);
			    }

			    if (tagsDtoList.size() > 0 ) {
			        TagRequestForm tagRequestForm = new TagRequestForm();
			        tagRequestForm.setSavedVehicles(tagsDtoList);
			        //logger.debug("No of vehicles in the list " + tagRequestForm.getSavedVehicles().size());
			        if(checkIfEZPlateExists(tagRequestForm.getSavedVehicles())){ //check for EZplate vehicles
			            tagRequestForm.setActivePbpTagExists(true);
			            tagRequestForm.setPbpTag(true);
			        } else {
			            tagRequestForm.setActivePbpTagExists(false);
			            tagRequestForm.setPbpTag(false);
			        }       
			        tagRequestForm.setPbpTag(false);
			        SessionUtil.setVehicles(request, tagsDtoList);
			        session.setAttribute("tagRequestForm", tagRequestForm);
			        BillingInfoDTO billingInfoDto = SessionUtil.getBillingInfo(request);
			        if (billingInfoDto != null) {
			            BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfoDto, acctDto);
			            session.setAttribute("billingInfoForm", billingInfoForm);
			        }
			        SessionUtil.getAcctPrefDTO(request);
			    } // End of EZtag implementation
			}
		} else {
			if(authContacts != null && authContacts.length > 0) {
				ContactInfoForm contactInfoForm = new ContactInfoForm();
				contactInfoForm.setAuthorizedContacts(authContacts);
				request.setAttribute("contactInfoForm", contactInfoForm);
				session.setAttribute("contactInfoForm",contactInfoForm);
				session.setAttribute("whichPage" , ContactInfoForm.WhichPage.INCOMPLETE.getTag());
				SessionUtil.getAcctPrefDTO(request);
			}
		}

		return mapping.findForward("success");
	}

	private boolean checkIfEZPlateExists(List<TagDTO> vehicles) {
		if (logger.isDebugEnabled()){
			logger.debug("Vehicle " + vehicles.get(0).getClass() );
		}

		if(vehicles == null || vehicles.size() == 0){
			return false;
		}//else
		int i = 0;
		for (TagDTO tagDTO : vehicles) {

			// if(tagDTO.isActivePbpTagExist()){
			//logger.debug("The transTypeCode ==> " + tagDTO.getTagTypeCode());
			if(tagDTO.getPbpStartDate() != null && tagDTO.getPbpEndDate()!= null){
				logger.info("IncompleteSign Up : EZPlate Exists" + i);
				return true;                  
			}//else
			// logger.debug("We do not have an ez plate vehicle" + i);
			i++;
		}
		return false;
	}
}
