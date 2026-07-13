/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.dto.PersonalInfoUpdateResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

/**
 * This action saves the User contact information.
 */
public class SignupContactInfoAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(SignupContactInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        ContactInfoForm contactForm = (ContactInfoForm) form;
        HttpSession session = request.getSession();
        if (logger.isInfoEnabled()) {
        	logger.info("account type: "+ contactForm.getAccountType());
        	logger.info("firstName:" + contactForm.getFirstName());
        	logger.info("Company Name:" + contactForm.getCompanyName());
        	logger.info("pickup: " + contactForm.isPickup());
        	logger.info("byMail: " + contactForm.isByMail());
        	logger.info("byEmail: " + contactForm.isByEmail());
        	logger.info("byPhone: " + contactForm.isByPhone());
        	logger.info("nonUSAddress: " + contactForm.isNonUSAddress());
        	logger.info("authorizedContacts " + contactForm.getAuthorizedContacts());
        }

        if (AccountEFTDTO.BankAccountType.PERSONAL.equals(contactForm.getAccountType())) {
            contactForm.setCompanyName("");
            contactForm.setTaxId("");
        }

        if (!contactForm.isNonUSAddress()) {
        	contactForm.setCountry(AddressUS.COUNTRY_CODE_USA);
            contactForm.setAddressLine3("");
            contactForm.setAddressLine4("");
        }
        else {

            contactForm.setState("");
            contactForm.setZip("");
            contactForm.setPlus4("");
        }

        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        PersonalInfoUpdateResultDTO resultDto = new AccountDelegate().setPersonalInfo(
        		accountLogin,
        		contactForm
        );
        if(contactForm.hasAuthorizedContacts()){
            OnlineAccessForm onlineAccessForm = null;
            if (session.getAttribute("password") != null) {
                contactForm.setWhichPageEnum(ContactInfoForm.WhichPage.INCOMPLETE);
                session.setAttribute("whichPage", ContactInfoForm.WhichPage.INCOMPLETE.getTag());
            } else {
                onlineAccessForm = (OnlineAccessForm) session.getAttribute("OnlineAccessForm");
                contactForm.setWhichPageEnum(ContactInfoForm.WhichPage.CONTACT);
                session.setAttribute("whichPage", ContactInfoForm.WhichPage.CONTACT.getTag());
            }
            if (contactForm.isFromConfirmation()) {
                onlineAccessForm = (OnlineAccessForm) session.getAttribute("OnlineAccessForm");
                request.setAttribute("OnlineAccessForm", onlineAccessForm);
            }
        }

        if(saveErrorMessages(request, resultDto, "invalidAccount")){
            return mapping.findForward("failure");
        } //else

        saveToken(request);
        contactForm.setRetailTransId(Long.valueOf(resultDto.getRetailTransId()));

        TagRequestForm tagRequestForm = (TagRequestForm)session.getAttribute("tagRequestForm");
        if(tagRequestForm!=null) {
            List<TagDTO> theSavedVehicles = tagRequestForm.getSavedVehicles();
            if (theSavedVehicles != null) {
            	for (TagDTO aTag : theSavedVehicles) {
                    if (aTag != null) {
                        aTag.setTransactionId(resultDto.getRetailTransId());
                        aTag.setAcctId(accountLogin.getAcctId());
                        TagDelegate tagRequestor = new TagDelegate();
                        tagRequestor.addTag(accountLogin, aTag, SessionUtil.getPosId(request));
//                        long trxnId = aTag.getTransactionId(); // tagRequestForm.setRetailTransId(tagDTO.getTransactionId());
//                        if (trxnId == resultDto.getRetailTransId()) {
//                            String msg = "The same transaction ID is returned.";
//                        } else {
//                            String msg = "The transaction IDs are different.";
//                        }
                    }
                }
            }
        }
        if (!contactForm.isFromConfirmation())
            return mapping.findForward("success");
        //else
        return mapping.findForward("confirmation");
    }
}
