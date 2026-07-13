package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.PaymentResultDTO;

/**
 * Contains methods for managing credit cards.
 */
public interface CreditCardInterface extends BusinessObjectInterface {

	/**
	 * Retrieves a collection of credit card information for a particular
	 * account.
	 * 
	 * @param acctLoginDto
	 * @param acctId
	 * @return
	 * @throws EtccException
	 */
	AccountCreditCardDTO getAccountCreditCard(AccountLoginDTO acctLoginDto,
			long acctId) throws EtccException, EtccSecurityException;

	/**
	 * Updates an account's credit card info.
	 * 
	 * @param acctLoginDto
	 *            Login credentials.
	 * @param acctCreditCardDto
	 *            The credit card info.
	 * @param fromPaymentScreen
	 *            Tells the database that this is called from the payment
	 *            screen.
	 * @return Returns PaymentResultDTO containing the success flag.
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	PaymentResultDTO updateAccountCreditCard(AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen)
			throws EtccException, EtccSecurityException;

	/**
	 * Retrieves a collection of credit card types.
	 * 
	 * @return Returns a collection of CreditCardDTO.
	 * @throws EtccException
	 */
	Collection getCreditCardTypes() throws EtccException;

	/**
	 * 
	 * @param accountNum
	 * @return
	 * @throws EtccException
	 */
	String isCreditCardScrubbed(long accountNum) throws EtccException;

	public int saveBillingPersonInfo(AddressDTO add) throws EtccException;
}
