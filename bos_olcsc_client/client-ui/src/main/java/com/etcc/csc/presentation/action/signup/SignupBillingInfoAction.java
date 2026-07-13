/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class SignupBillingInfoAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(SignupBillingInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    	HttpSession session = request.getSession();
    	boolean debugEnabled = logger.isDebugEnabled();
        BillingInfoForm billingInfoForm = (BillingInfoForm) form;
        BillingInfoDTO billingInfoDTO = (BillingInfoDTO) session.getAttribute("billingInfoDTO");
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
        
        if (billingInfoDTO == null || billingInfoForm.getPaymentType().equalsIgnoreCase(PaymentType.EFT.getDisplay()))
        {
      	  billingInfoDTO = billingInfoForm.newBillingInfoDTO((ContactInfoForm) session.getAttribute("contactInfoForm"));
      	    //billingInfoDTO.setDefaultBillingMethod("Y");
            //request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoDTO.getPrimaryCard().getCardType()));
      	  BillingInfoResultDTO resultInfo;
      	  /*if (billingInfoDTO.getPrimaryCard() instanceof AccountCreditCardDTO)
          {
            resultInfo = new AccountDelegate().setBillingInfoCreditCard(loginDTO, (AccountCreditCardDTO)billingInfoDTO.getPrimaryCard());
          }
      	  else
      	  {
      		resultInfo = new AccountDelegate().setBillingInfoEFT(loginDTO, (AccountEFTDTO)billingInfoDTO.getPrimaryCard());
      	  }*/
          resultInfo = new AccountDelegate().setBillingInfo(loginDTO, billingInfoDTO);


          TagRequestForm tagRequestForm = (TagRequestForm)session.getAttribute("tagRequestForm");
          if (debugEnabled)
          	logger.debug("Tag Request form " +  tagRequestForm);

          tagRequestForm.setTotalAmount(resultInfo.getTotalAmount());
          tagRequestForm.setDepositAmount(resultInfo.getDepositAmount());
          tagRequestForm.setRebillAmount(resultInfo.getRebillAmount());// Rebill Amount
          tagRequestForm.setLowBalanceAmount(resultInfo.getLowBalanceAmount());
          tagRequestForm.setTagSaleAmount(SignupBillingPANAction.getActiviationFee(loginDTO, tagRequestForm));
        //Weblogic upgrade cluster issues fix
          session.setAttribute("tagRequestForm",tagRequestForm);
        //Weblogic upgrade cluster issues fix ends 

	       // Update account Billing Method person ID
	      	BillingInfoDTO newbillingInfo = new AccountDelegate().getBillingInfo(loginDTO);

	      	AccountEFTDTO eftDto = newbillingInfo.getEft();
	      	billingInfoDTO.getEft().setAccountBillingMethodId(eftDto.getAccountBillingMethodId());
	      	billingInfoDTO.getEft().setAddressId(eftDto.getAddressId());
	      	billingInfoDTO.getEft().setPersonId(eftDto.getPersonId());
	      	billingInfoDTO.getEft().setPhoneId(eftDto.getPhoneId());
	      	session.setAttribute("billingInfoDTO",billingInfoDTO);

        }


//        if (debugEnabled)
//        	logger.debug("Submitted BillingInfoDTO: " + billingInfoDTO);
        //BillingInfoResultDTO resultDTO = new AccountDelegate().setBillingInfo(loginDTO, billingInfoDTO);
//        if (debugEnabled)
//        	logger.debug("Received BillingInfoResultDTO: " + resultDTO);
        /*---------------------------------------------------------------------------------------*/
          ContactInfoForm contactForm = (ContactInfoForm)session.getAttribute("contactInfoForm");
          OnlineAccessForm onlineAccessForm = (OnlineAccessForm)session.getAttribute("OnlineAccessForm");

          String password = (String)session.getAttribute("password");
        if (StringUtil.isEmpty(password) && onlineAccessForm != null)
              password = onlineAccessForm.getPassword();

         // contactForm = saveAuthorizedContacts(contactForm,loginDTO, contactForm.getAuthorizedContacts(),password,contactForm.getWhichPage());
          logger.debug("contactForm.getAuthorizedContacts() size:"+contactForm.getAuthorizedContacts().size());
          if(contactForm.getAuthorizedContacts().size() > 1)
             contactForm.setAuthorizedContacts(contactForm.getAuthorizedContacts());
          else if(contactForm.getAuthorizedContacts().size() == 1)
          {
            AuthorizedContactDTO authorizedContact = contactForm.getAuthorizedContacts().get(0);
            if (StringUtil.isEmpty(authorizedContact.getFirstName()))
                contactForm.setAuthorizedContacts(new ArrayList<AuthorizedContactDTO>());
            else
              contactForm.setAuthorizedContacts(contactForm.getAuthorizedContacts());
          }else if(contactForm.getAuthorizedContacts().size() <=0)
        	  contactForm.setAuthorizedContacts(new ArrayList<AuthorizedContactDTO>());
          //Weblogic upgrade cluster issues fix
          session.setAttribute("contactInfoForm",contactForm);
        //Weblogic upgrade cluster issues fix ends 
        /*----------------------------------------------------------------------*/
//        if (debugEnabled)
//           logger.debug("Finished saving Authorised Contacts ...." + resultDTO.getErrors());

//        if (saveErrorMessages(request,resultDTO.getErrors(), "billingInfoSaveFailed")) {
//        	logger.info("Forwarding to failure.");
//            return mapping.findForward("failure");
//        }




//        session.setAttribute("tagRequestForm",tagRequestForm);
//        if (debugEnabled)
//        	logger.debug("Finished saving TagrequestForm ...." + tagRequestForm);
        SessionUtil.getAcctPrefDTO(request);
        if (onlineAccessForm == null)
            onlineAccessForm=new OnlineAccessForm();
        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        if (acctDto != null) {
            onlineAccessForm.setLoginId(loginDTO.getLoginId());
            onlineAccessForm.setEmailAddress(acctDto.getEmailAddress());
            request.setAttribute("OnlineAccessForm",onlineAccessForm);
            session.setAttribute("OnlineAccessForm",onlineAccessForm);
        }
        request.setAttribute("acctId", Long.valueOf(loginDTO.getAcctId()));

    	logger.debug("Forwarding to success.");
        return mapping.findForward("success");
    }

}
