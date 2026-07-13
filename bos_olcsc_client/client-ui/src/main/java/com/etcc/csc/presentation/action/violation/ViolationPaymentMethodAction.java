/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.violation;

import java.util.Arrays;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WebServiceClientUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import webservice.paymentappliance.etcc.com.CreditCardTypeEnum;
import webservice.paymentappliance.etcc.com.GetCCTokenResponseDTO;
import webservice.paymentappliance.etcc.com.NewPANRequestDTO;
import webservice.paymentappliance.etcc.com.NewPANResponseDTO;
import webservice.paymentappliance.etcc.com.NewTokenRequestDTO;
import webservice.paymentappliance.etcc.com.NewTokenResponseDTO;

public class ViolationPaymentMethodAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(ViolationPaymentMethodAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    	boolean trace = logger.isTraceEnabled();
    	String result = "success";
    	BillingInfoForm billingInfoForm = ((SelectedInvoiceForm) form).getBillingInfoForm();
    	billingInfoForm.setPaypageRegistrationId(request.getParameter("paypageRegistrationId"));
    	if (trace) logger.trace(billingInfoForm);
    	// basic validation only since Struts has already performed most of the validation
        ErrorMessageDTO[] messages = billingInfoForm.validate(new CreditCardDelegate().getCreditCardTypes(), true);
    	if (trace)
    		logger.trace("Billing validation messages: " + Arrays.toString(messages));
    	if (saveErrorMessages(request, messages, getClass().getName())
    			|| !getErrors(request).isEmpty()) {
    		result = "failure";
    	}
    	billingInfoForm.cleanupBillingInfo();
    	AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());


    	ContactInfoForm contactForm = null;
    	if (billingInfoForm.getPaymentType().equals("credit"))
    	{
	    	BillingInfoDTO billingInfoDTO = billingInfoForm.newBillingInfoDTO(contactForm);
	    	AccountCreditCardDTO card = (AccountCreditCardDTO) billingInfoDTO.getPrimaryCard();
	    	
	    	String paypageRegistrationId = request.getParameter("response$paypageRegistrationId");
			card.setPaypageRegistrationId(paypageRegistrationId);
			
			/*NewPANRequestDTO dto = convertToPANRequest(billingInfoForm, acctLoginDto.getViolatorId());
			card.setToken(dto.getToken());*/
			card.setPrimary(false);
//			BillingInfoResultDTO resultDTO = new AccountDelegate().setBillingInfo(acctLoginDto, card);
//			BillingInfoDTO newbillingInfo = new AccountDelegate().getBillingInfo(acctLoginDto);
//
//			for (int i = 0;i < newbillingInfo.getCards().length;i++ )
//			{
//				AccountCreditCardDTO oldCard = newbillingInfo.getCards()[i];
//
//
//				if (oldCard.equivalent(card))
//				{
//					card.setAccountBillingMethodId(oldCard.getAccountBillingMethodId());
//					card.setPersonId(oldCard.getPersonId());
//					card.setAddressId(oldCard.getAddressId());
//					card.setPhoneId(oldCard.getPhoneId());
//				}
//			}

//			NewPANResponseDTO addPANResponse = WebServiceClientUtil.getPANManagerImpl().addPAN(dto );
//			if (addPANResponse.getCode()!=200)
//			{
//				response.getWriter().print("There is an issue with PAN Webservice");
//				return null;
//			}

			request.getSession().setAttribute("paymentMethodDTO", card);
    	}
    	else
    	{
    		BillingInfoDTO billingInfoDTO = billingInfoForm.newBillingInfoDTO(contactForm);
	    	AccountEFTDTO eft = (AccountEFTDTO) billingInfoDTO.getPrimaryCard();
//	    	BillingInfoResultDTO resultDTO = new AccountDelegate().setBillingInfo(acctLoginDto, eft);
//			BillingInfoDTO newbillingInfo = new AccountDelegate().getBillingInfo(acctLoginDto);
//			AccountEFTDTO oldCard = newbillingInfo.getEft();
//			eft.setAccountBillingMethodId(oldCard.getAccountBillingMethodId());
//			eft.setPersonId(oldCard.getPersonId());
//			eft.setAddressId(oldCard.getAddressId());
//			eft.setPhoneId(oldCard.getPhoneId());
	    	request.getSession().setAttribute("paymentMethodDTO", eft);
    	}


        logger.debug("Result=" + result);
    	return mapping.findForward(result);
    }


    private NewPANRequestDTO convertToPANRequest(BillingInfoForm billingForm, Long accountId) throws Exception
	{
		NewPANRequestDTO result = new NewPANRequestDTO();

		String creditType = getCreditCardType(billingForm);
		GetCCTokenResponseDTO response = WebServiceClientUtil.getPANManagerImpl().getCCProcessorToken(CartUtil.MERCHARD_ID,
				accountId + "", creditType, billingForm.getPaypageRegistrationId(),
				StringUtil.last4Of(billingForm.getCardNumber()), AccountLoginDTO.GENERIC_USER, "OLCSC");


		result.setAccountId(accountId+"");
		result.setAddress1(billingForm.getAddress1());
		result.setAddress2(billingForm.getAddress2());
		result.setAppName("OLCSC");
		result.setCity(billingForm.getCity());
		result.setCountryCode(billingForm.getCountry());
		result.setExpMonth(billingForm.getCardExpirationMonth());
		result.setExpYear(billingForm.getCardExpirationYear());
		result.setFirstName(billingForm.getName());
		result.setLastName(""); // TODO : split it from name
		result.setMiddleInitial(""); // TODO : split it from name
		result.setNameSuffix("");// TODO : split it from name
		result.setNumber(billingForm.getCardNumber());
		result.setPhoneExtension("");
		result.setPhoneNumber("");
		result.setStateCode(billingForm.getState());
		result.setToken(Long.valueOf(response.getToken()));
		result.setTypeid(FormatUtil.convertCardType(billingForm.getCardType()));
		result.setUserName(AccountLoginDTO.GENERIC_USER);
		result.setZipCode(billingForm.getZip());
		result.setZipExtension(billingForm.getPlus4());
		return result;

	}


	private String getCreditCardType(BillingInfoForm billingForm) {
		String creditType = null;

		if (billingForm.getCardType().equals("V"))
		{
			creditType = CreditCardTypeEnum.VISA.getValue();
		}
		else if (billingForm.getCardType().equals("M"))
		{
			creditType = CreditCardTypeEnum.MASTERCARD.getValue();
		}
		else if (billingForm.getCardType().equals("A"))
		{
			creditType = CreditCardTypeEnum.AMERICANEXPRESS.getValue();
		}
		else if (billingForm.getCardType().equals("D"))
		{
			creditType = CreditCardTypeEnum.DISCJCBCUP.getValue();
		}
		return creditType;
	}


}
