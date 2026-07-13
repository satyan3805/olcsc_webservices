package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import webservice.paymentappliance.etcc.com.CreditCardTypeEnum;
import webservice.paymentappliance.etcc.com.GetCCTokenResponseDTO;
import webservice.paymentappliance.etcc.com.NewPANRequestDTO;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WebServiceClientUtil;

/**
 * Add PAN Manager Action also add default billing method
 * @author hxha
 */
public class SignupBillingPANAction extends Action {

	public static final String CHANGE_DEFAULT_BILLING = "changeDefaultBilling";


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BillingInfoForm billingInfoForm = (BillingInfoForm) form;
		BillingInfoDTO billingInfoDTO = (BillingInfoDTO) request.getSession().getAttribute("billingInfoDTO");
		HttpSession session = request.getSession();
		AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session );
		// Signup Set Payment Info
		BillingInfoResultDTO resultDTO= null;
		String dispatchAction = request.getParameter("dispatchAction");

		ContactInfoForm contactForm = (ContactInfoForm) session.getAttribute("contactInfoForm");
		if (StringUtils.isEmpty(dispatchAction))
		{
			List<AccountCreditCardDTO> creditCardList = (List<AccountCreditCardDTO>) session.getAttribute("creditCardList");
			if (creditCardList == null)
			{
				creditCardList = new ArrayList<AccountCreditCardDTO>();
			}
			AccountCreditCardDTO card = null;

			if (billingInfoDTO == null)
			{
				billingInfoDTO = billingInfoForm.newBillingInfoDTO(contactForm);
				card = (AccountCreditCardDTO) billingInfoDTO.getPrimaryCard();
			}
			else
			{
				BillingInfoDTO newbillingInfoDTO = billingInfoForm.newBillingInfoDTO(contactForm);
				card = (AccountCreditCardDTO) newbillingInfoDTO.getPrimaryCard();
				card.setPrimary(false);
			}


			//NewPANRequestDTO dto = convertToPANRequest(request,billingInfoForm, accountLoginDTO);
			//card.setToken(dto.getToken());
			String paypageRegistrationId = request.getParameter("response$paypageRegistrationId");
			card.setPaypageRegistrationId(paypageRegistrationId);

			resultDTO = new AccountDelegate().setBillingInfo(accountLoginDTO, billingInfoDTO);
			//NewPANResponseDTO addPANResponse = WebServiceClientUtil.getPANManagerImpl().addPAN(dto );

//			if (addPANResponse.getCode()!=200)
//			{
//				response.getWriter().print("There is an issue with PAN Webservice");
//				return null;
//			}
			//billingInfoDTO.setDefaultBillingMethod(creditCardList.size()==0 ? "Y":"N");
			creditCardList.add(card);
			session.setAttribute("creditCardList", creditCardList);
			billingInfoDTO.setCards(creditCardList);
			//billingInfoForm.reset(mapping, request);
		}
		else if (CHANGE_DEFAULT_BILLING.equals(dispatchAction))
		{
			// Update credit Card Session List
			String cardIndex = (String) request.getParameter("index");
			List<AccountCreditCardDTO> creditCardList = (List<AccountCreditCardDTO>) session.getAttribute("creditCardList");
			billingInfoDTO.setCards(creditCardList);

			if (creditCardList != null)
			{
				for (int i = 0 ; i < creditCardList.size();i++)
				{
					AccountCreditCardDTO ccDto = creditCardList.get(i);
					if (i == Integer.valueOf(cardIndex))
					{
						ccDto.setPrimary(true);

					}
					else
					{
						ccDto.setPrimary(false);
					}
				}
				resultDTO = new AccountDelegate().setBillingInfo(accountLoginDTO, billingInfoDTO );

			}
		}
		if (resultDTO != null )
		{
	        TagRequestForm tagRequestForm = (TagRequestForm)session.getAttribute("tagRequestForm");
	        tagRequestForm.setTotalAmount(resultDTO.getTotalAmount());
	        tagRequestForm.setDepositAmount(resultDTO.getDepositAmount());
	        tagRequestForm.setLowBalanceAmount(resultDTO.getLowBalanceAmount());
	        tagRequestForm.setRebillAmount(resultDTO.getRebillAmount());// Rebill Amount
	        tagRequestForm.setTagSaleAmount(getActiviationFee(accountLoginDTO, tagRequestForm));
        	request.setAttribute("tagRequestForm", tagRequestForm);

        	// Update account Billing Method person ID
        	BillingInfoDTO newbillingInfo = new AccountDelegate().getBillingInfo(accountLoginDTO);

        	for (int i=0;i<newbillingInfo.getCards().length;i++)
        	{
        		AccountCreditCardDTO dto = newbillingInfo.getCards()[i];
	        	billingInfoDTO.getCards()[i].setAccountBillingMethodId(dto.getAccountBillingMethodId());
	        	billingInfoDTO.getCards()[i].setAddressId(dto.getAddressId());
	        	billingInfoDTO.getCards()[i].setPersonId(dto.getPersonId());
	        	billingInfoDTO.getCards()[i].setPhoneId(dto.getPhoneId());
        	}
        	session.setAttribute("billingInfoDTO",billingInfoDTO);
        	BillingInfoForm newForm = new BillingInfoForm();
        	newForm.setPaymentTypeEnum(PaymentType.CREDIT);
        	newForm.reset(mapping, request);
			request.setAttribute(mapping.getName(),  newForm);

		}
		return mapping.findForward("success");
	}

	private NewPANRequestDTO convertToPANRequest(HttpServletRequest request, BillingInfoForm billingForm, AccountLoginDTO accountLoginDTO) throws Exception
	{
		String paypageRegistrationId = request.getParameter("response$paypageRegistrationId");
		NewPANRequestDTO result = new NewPANRequestDTO();

		String creditType = getCreditCardType(billingForm);
		//We will call the new Payment API for getting the token after payment application finish
		/*GetCCTokenResponseDTO response = WebServiceClientUtil.getPANManagerImpl().getCCProcessorToken(CartUtil.MERCHARD_ID,
				accountLoginDTO.getAcctId()+"", billingForm.getCardType(), paypageRegistrationId,
				StringUtil.last4Of(billingForm.getCardNumber()), accountLoginDTO.getLoginId(), "OLCSC");*/

		result.setAccountId(accountLoginDTO.getAcctId()+"");
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
		//result.setToken(Long.valueOf(response.getToken()));
		result.setTypeid(FormatUtil.convertCardType(billingForm.getCardType()));
		result.setUserName(accountLoginDTO.getLoginId());
		result.setZipCode(billingForm.getZip());
		result.setZipExtension(billingForm.getPlus4());
		return result;

	}


	/**
	 * Recalulate the activation fee
	 * @param accountLogin
	 * @param tagRequestForm
	 * @return
	 * @throws EtccException
	 */
	public static double getActiviationFee(AccountLoginDTO accountLogin,
			TagRequestForm tagRequestForm) throws EtccException {
		TagDelegate delegate = new TagDelegate();
		long stickerTagCount = 0l;
		long motocycleCount = 0l;
		long licensePlateTagCount = 0l;

		List<TagDTO> savedVehicles = tagRequestForm.getSavedVehicles();
		for (TagDTO tagDTO : savedVehicles)
		{
			if (tagDTO.isMotorcycle())
			{
				motocycleCount++;
			} else if (tagDTO.isPbpTag())
				licensePlateTagCount++;
			{
				stickerTagCount++;
			}
		}
		return delegate.calculateActivationFee(accountLogin, stickerTagCount, motocycleCount, licensePlateTagCount);

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
