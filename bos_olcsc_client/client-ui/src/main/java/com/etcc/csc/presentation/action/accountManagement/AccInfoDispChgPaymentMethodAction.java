/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import webservice.paymentappliance.etcc.com.GetCCTokenResponseDTO;
import webservice.paymentappliance.etcc.com.NewPANRequestDTO;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.presentation.action.signup.SignupBillingPANAction;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.WebServiceClientUtil;

public class AccInfoDispChgPaymentMethodAction extends Action {

	private static final String ADD_NEW = "addNew";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String paymentTypeParam = request.getParameter("paymentType");
		AccountLoginDTO acctLoginDto = SessionUtil
				.getSessionAccountLogin(request.getSession());
		AccountDelegate acctDel = new AccountDelegate();
		HttpSession session = request.getSession();
		AccountDTO accountDTO = SessionUtil.getAcctDTO(request);

		String dispatchAction = request.getParameter("dispatchAction");

		BillingInfoDTO billingInfo = acctDel.getBillingInfo(acctLoginDto);
		PaymentType paymentType = billingInfo.getBillingType();
		// joe changes start

		if (paymentType == null) {
			String paymentT = accountDTO.getPmtType();

			if (paymentT.startsWith("credit")) {// ||(paymentTypeParam.equals("credit"))){
				paymentType = PaymentType.CREDIT;
				if ((paymentTypeParam != null)
						&& (paymentTypeParam.equals("eft"))) {
					paymentType = PaymentType.EFT;
				}
				AccountForm accountForm = new AccountForm(accountDTO,
						paymentType);

				request.setAttribute("accountForm", accountForm);
				if ((paymentTypeParam != null)
						&& (paymentTypeParam.equals("eft"))) {
					return mapping.findForward("successeft");
				}
				return mapping.findForward("successcc");
			}
			if (paymentT.equals("EFT")) {// (paymentTypeParam.equals("eft"))){
				paymentType = PaymentType.EFT;
				if ((paymentTypeParam != null)
						&& (paymentTypeParam.equals("credit"))) {
					paymentType = PaymentType.CREDIT;
				}
				AccountForm accountForm = new AccountForm(accountDTO,
						paymentType);

				request.setAttribute("accountForm", accountForm);
				if ((paymentTypeParam != null)
						&& (paymentTypeParam.equals("credit"))) {
					return mapping.findForward("successcc");
				}
				return mapping.findForward("successeft");

			}
			// return mapping.findForward("successinv");
		}

		if (paymentType == null)
		{
			paymentType = PaymentType.CREDIT;
		}
		// joe changes ends
		AccountForm accountForm = new AccountForm(accountDTO, paymentType);
		BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfo,
				accountDTO);

		request.setAttribute("billingInfoForm", billingInfoForm);
		request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);
		request.setAttribute("accountForm", accountForm);
		request.setAttribute("closure",
				Boolean.valueOf(request.getParameter("closure")));

		List<AccountCreditCardDTO> creditCardList = (List<AccountCreditCardDTO>) session
				.getAttribute("creditCardList");
		// naphan-2369
		if (creditCardList == null) {
			creditCardList = new ArrayList<AccountCreditCardDTO>();
		}
		if (billingInfo.getCards() != null) {
			creditCardList.addAll(Arrays.asList(billingInfo.getCards()));
		}

		AppDelegate appDel = new AppDelegate();
		request.setAttribute("LITLE_PAYPAGE_URL",
				appDel.getSysParam("LITLE_PAYPAGE_URL"));
		request.setAttribute("LITLE_PAYPAGE_ID",
				appDel.getSysParam("LITLE_PAYPAGE_ID"));
		request.setAttribute("LITLE_REPORT_GROUP",
				appDel.getSysParam("LITLE_REPORT_GROUP"));
		
		String paypageRegistrationId = request.getParameter("response$paypageRegistrationId");

		if (ADD_NEW.equals(dispatchAction)) {
			BillingInfoForm currentForm = (BillingInfoForm) form;
			AccountCreditCardDTO card = currentForm
					.createAccountCreditCardDTO(accountDTO);
			/*NewPANRequestDTO dto = convertToPANRequest(currentForm,
					acctLoginDto);
			card.setToken(dto.getToken());*/
			card.setPaypageRegistrationId(paypageRegistrationId);
			
			BillingInfoDTO billingInfoDTO = new BillingInfoDTO();
			billingInfoDTO.setBillingType(PaymentType.CREDIT);
			AccountCreditCardDTO[] accountCreditCardDTOs = new AccountCreditCardDTO[1];
			accountCreditCardDTOs[0] = card;
			billingInfoDTO.setCards(accountCreditCardDTOs);
			acctDel.setBillingInfo(acctLoginDto, billingInfoDTO);
			creditCardList.add(card);
		} else if (SignupBillingPANAction.CHANGE_DEFAULT_BILLING
				.equals(dispatchAction)) {
			String cardIndex = request.getParameter("index");
			for (int i = 0; i < creditCardList.size(); i++) {
				AccountCreditCardDTO ccDto = creditCardList.get(i);
				if (i == Integer.valueOf(cardIndex)) {
					ccDto.setPrimary(true);
					BillingInfoDTO billingInfoDTO = new BillingInfoDTO();
					billingInfoDTO.setBillingType(PaymentType.CREDIT);
					AccountCreditCardDTO[] accountCreditCardDTOs = new AccountCreditCardDTO[1];
					accountCreditCardDTOs[0] = ccDto;
					billingInfoDTO.setCards(accountCreditCardDTOs);
					acctDel.setBillingInfo(acctLoginDto, billingInfoDTO);
				} else {
					ccDto.setPrimary(false);
				}
			}

			// Update account Billing Method person ID
			BillingInfoDTO newbillingInfo = acctDel
					.getBillingInfo(acctLoginDto);

			session.setAttribute("billingInfoDTO", newbillingInfo);
			billingInfoForm = new BillingInfoForm(newbillingInfo, accountDTO);
			request.setAttribute("billingInfoForm", billingInfoForm);

		}
		session.setAttribute("creditCardList", creditCardList);
		if (paymentTypeParam != null)
			paymentType = PaymentType.valueOfCode(paymentTypeParam);
		if (paymentType != null) {
			switch (paymentType) {
			case CREDIT:
				return mapping.findForward("successcc");
			case EFT:
				return mapping.findForward("successeft");
			}
		}
		/* if (billingInfoForm.getPaymentType().equals("invoice")) */
		return mapping.findForward("successinv");
	}

	private NewPANRequestDTO convertToPANRequest(BillingInfoForm billingForm,
			AccountLoginDTO accountLoginDTO) throws Exception {

		GetCCTokenResponseDTO getCCResp = WebServiceClientUtil
				.getPANManagerImpl().getCCProcessorToken(
						CartUtil.MERCHARD_ID,
						accountLoginDTO.getAcctId() + "",
						CreditCardDTO.CreditCardType.valueOfCode(
								billingForm.getCardType()).name(),
						billingForm.getPaypageRegistrationId(),
						billingForm.getCardNumber(),
						accountLoginDTO.getLoginId(), "OLCSC");

		NewPANRequestDTO result = new NewPANRequestDTO();

		result.setAccountId(accountLoginDTO.getAcctId() + "");
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
		result.setToken(Long.valueOf(getCCResp.getToken()));
		result.setTypeid(FormatUtil.convertCardType(billingForm.getCardType()));
		result.setUserName(accountLoginDTO.getLoginId());
		result.setZipCode(billingForm.getZip());
		result.setZipExtension(billingForm.getPlus4());
		return result;

	}

}
