package com.etcc.csc.dao.oracle.util;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import webservice.paymentappliance.etcc.com.GetCCTokenResponseDTO;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.service.App;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WebServiceClientUtil;

public class CCTokenDAOHelper {

	private static CCTokenDAOHelper instance = new CCTokenDAOHelper();
	private final Logger logger = Logger.getLogger(CCTokenDAOHelper.class);
	private static String merchantId = "";

	private CCTokenDAOHelper() {

	}

	public static CCTokenDAOHelper getInstance() {

		return instance;
	}

	@SuppressWarnings("unused")
	public void getCCToken(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO paymentMethodDTO)
			throws EtccException {
		String tokenFromPA;
		AccountCreditCardDTO cardDTO = (AccountCreditCardDTO) paymentMethodDTO;
		//triPOS phase 2 changes start
		if (StringUtils.isNotBlank(cardDTO.getOmniToken())){
			// set omni token to card dto
			cardDTO.setToken(Long.valueOf(cardDTO.getOmniToken()));
			logger.info(" token recevied from request " + cardDTO.getOmniToken());
			//triPOS phase 2 changes end
		 }else if(StringUtils.isNotBlank(cardDTO.getPaypageRegistrationId())){
				// set PaypageRegistrationId that we need to call credit card processor to get it
			try {
						if (StringUtil.isEmpty(merchantId)){
							merchantId = new App().getSysParam("LITLE_CONFIG_MERCHANT_ID");	}

				GetCCTokenResponseDTO getCCResp = WebServiceClientUtil.getPANManagerImpl().getCCProcessorToken(
						merchantId, new Long(acctLoginDto.getAcctId()).toString(), cardDTO.getCardType().name(),
						cardDTO.getPaypageRegistrationId(), StringUtil.last4Of(cardDTO.getCardNbr()),
						acctLoginDto.getLoginId(), "OLCSC");
						
				tokenFromPA = getCCResp.getToken();
				logger.info(" token recevied from PA " + tokenFromPA);				
				if (StringUtils.isNotBlank(tokenFromPA)) {
					cardDTO.setToken(Long.valueOf(tokenFromPA));
				} else {
					throw new EtccException("Unable to get the token from PA");
				}
			} catch (RemoteException e) {
				logger.error(" Remote Exception  while retrieving the token ", e);
				throw new EtccException(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(" Error while retrieving the token ", e);
				throw new EtccException(e);
			}
		  }// pay page if close
		 else{
			   if (paymentMethodDTO.getAccountBillingMethodId() == null 
						|| paymentMethodDTO.getAccountBillingMethodId().longValue() <= 0)
			       throw new EtccException(" Both  Omini Token and Paypage Registration can not be empty ");
		}
	}

	public String getCCToken(String accountId, String cardType, String payPageRegId, String cardNumber,
			String loginUser) throws EtccException {
		String tokenFromPA = null;

		GetCCTokenResponseDTO getCCResp;
		try {
			if (StringUtil.isEmpty(merchantId))
				merchantId = new App().getSysParam("LITLE_CONFIG_MERCHANT_ID");

			getCCResp = WebServiceClientUtil.getPANManagerImpl().getCCProcessorToken(merchantId, accountId, cardType,
					payPageRegId, cardNumber, loginUser, "OLCSC");

			tokenFromPA = getCCResp.getToken();
			if (StringUtils.isNotBlank(tokenFromPA)) {
				return tokenFromPA;
			}
			else {
				throw new EtccException("Unable to get the token from PA");
			}
		} catch (RemoteException e) {
			logger.error(" Remote Exception  while retrieving the token ", e);
			throw new EtccException(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(" Error while retrieving the token ", e);
			throw new EtccException(e);
		}

	}

}
