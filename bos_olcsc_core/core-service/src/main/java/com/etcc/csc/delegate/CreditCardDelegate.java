package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.service.CreditCardInterface;

public class CreditCardDelegate extends Delegate implements CreditCardInterface {

	CreditCardInterface cc = (CreditCardInterface) getServiceObject(ServiceObjectEnum.CREDIT_CARD);

	public CreditCardDelegate() {
		super(CreditCardDelegate.class);
	}

	public AccountCreditCardDTO getAccountCreditCard(
			AccountLoginDTO acctLoginDto, long acctId) throws EtccException,
			EtccSecurityException {

		try {

			return cc.getAccountCreditCard(acctLoginDto, acctId);

		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running getAccountCreditCard: " + t,
					t);
		}
	}

	public PaymentResultDTO updateAccountCreditCard(
			AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen)
			throws EtccException, EtccSecurityException {

		try {

			return cc.updateAccountCreditCard(acctLoginDto, acctCreditCardDto,
					fromPaymentScreen);

		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running updateAccountCreditCard: "
					+ t, t);
		}
	}

	public Collection getCreditCardTypes() throws EtccException {

		try {
			return cc.getCreditCardTypes();
		} catch (Throwable t) {
			throw new EtccException("Error running getCreditCardTypes: " + t, t);
		}
	}

	public String isCreditCardScrubbed(long accountNum) throws EtccException {
		try {
			return cc.isCreditCardScrubbed(accountNum);
		} catch (Throwable t) {
			throw new EtccException("Error running isCreditCardScrubbed: " + t,
					t);
		}
	}

	public int saveBillingPersonInfo(AddressDTO add) throws EtccException {
		try {
			int result = cc.saveBillingPersonInfo(add);
			return result;

		} catch (Throwable t) {
			throw new EtccException(
					"Error running saveBillingPersonInfo: " + t, t);
		}
	}
}
