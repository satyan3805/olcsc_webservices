package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentResultDTO;

public class CreditCard implements CreditCardInterface {
	private Logger logger = Logger.getLogger(CreditCard.class);

	public CreditCard() {
	}

	public AccountCreditCardDTO getAccountCreditCard(
			AccountLoginDTO acctLoginDto, long acctId) throws EtccException,
			EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CreditCardDAO ccDao = daoFactory.getCreditCardDAO();
			return ccDao.getAccountCreditCard(acctLoginDto, acctId);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getAccountCreditCards() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getAccountCreditCards() " + ee, ee);
			throw ee;
		}
	}

	public PaymentResultDTO updateAccountCreditCard(
			AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CreditCardDAO ccDao = daoFactory.getCreditCardDAO();
			return ccDao.updateAccountCreditCard(acctLoginDto,
					acctCreditCardDto, fromPaymentScreen);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in updateAccountCreditCards() "
					+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in updateAccountCreditCards() " + ee, ee);
			throw ee;
		}
	}

	public Collection getCreditCardTypes() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CreditCardDAO ccDao = daoFactory.getCreditCardDAO();
			Collection col = ccDao.getCreditCardTypes();
			return col;
		} catch (EtccException ee) {
			logger.error("Exception in getCreditCardTypes() " + ee, ee);
			throw ee;
		}
	}

	public String isCreditCardScrubbed(long accountNum) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CreditCardDAO ccDao = daoFactory.getCreditCardDAO();
			String result = ccDao.isCreditCardScrubbed(accountNum);
			return result;
		} catch (EtccException ee) {
			logger.error("Exception in isCreditCardScrubbed() " + ee, ee);
			throw ee;
		}
	}

	public int saveBillingPersonInfo(AddressDTO add) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CreditCardDAO ccDao = daoFactory.getCreditCardDAO();
			int result = ccDao.saveBillingPersonInfo(add);
			return result;

		} catch (EtccException ee) {
			logger.error("Exception in saveBillingPersonInfo() " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub method to force the inclusion of CreditCardDTO to the wsdl.
	 * 
	 * @return
	 */
	public CreditCardDTO getCreditCard() {
		return null;
	}

	/**
	 * Stub method to force the inclusion of ErrorMessageDTO to the wsdl.
	 * 
	 * @return
	 */
	public ErrorMessageDTO getErrorMessage() {
		return null;
	}
}
