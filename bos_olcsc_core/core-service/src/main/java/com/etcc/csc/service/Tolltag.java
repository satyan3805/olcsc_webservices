package com.etcc.csc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.datatype.OlcMarketSourceRec;
import com.etcc.csc.datatype.PersonalInfoRequest;
import com.etcc.csc.datatype.PersonalInfoResponse;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.TagDTO;

public class Tolltag implements TollTagInterface {

	public AccountLoginDTO createAccount(String ipAddress, String sessionId,
			String browserType, String browserVersion, String os_type,
			String osVersion, String attribute1, String attribute2,
			String attributes3, String attribute4, String attribute5,
			String accountType, String firstName, String middleInitial,
			String lastName, String taxId, String email, String loginId,
			String password, List<SecurityQuestionDTO> listOfQuestionsnAnswers,
			String locale, String planCode, String eventId, String startDate,
			String endDate, String companyName)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getTolltagDAO()
				.createAccount(ipAddress, sessionId, browserType,
						browserVersion, os_type, osVersion, attribute1,
						attribute2, attributes3, attribute4, attribute5,
						accountType, firstName, middleInitial, lastName, taxId,
						email, loginId, password, listOfQuestionsnAnswers,
						locale, planCode, eventId, startDate, endDate,
						companyName);

	}

	public PersonalInfoResponse setPersonalInfo(PersonalInfoRequest pir)
			throws EtccErrorMessageException, Exception {
		/*
		 * return
		 * DAOFactory.getDAOFactory().getTolltagDAO().setPersonalInfo(pir.
		 * getAccountId(), pir.getAddress(), pir.getAddressLine2(),
		 * pir.getCity(), pir.getState(), pir.getZipcode(), pir.getZipPlus4(),
		 * pir.getHomePhoneNumber(), pir.getWorkPhoneNumber(),
		 * pir.getWorkPhoneExt(), pir.getDlNumber(), pir.getDlState(),
		 * pir.getMonthlyStmtFlag(), pir.getMsId(), pir.getDbSessionId(),
		 * pir.getLoginId(), pir.getIpAddress(), pir.getTransactionId(),
		 * pir.isCheckDuplicateDriverLicense(), pir.getPosId());
		 */
		return DAOFactory.getDAOFactory().getTolltagDAO().setPersonalInfo(pir);
	}

	public void setPaymentInfo(long acctId, String dbSessionId,
			String ipAddress, String loginId, long personId, String cardNumber,
			long cardType, String token, Calendar expirationDate, long eventId)
			throws EtccErrorMessageException, Exception {
		DAOFactory
				.getDAOFactory()
				.getTolltagDAO()
				.setPaymentInfo(acctId, dbSessionId, ipAddress, loginId,
						personId, cardNumber, cardType, token, expirationDate,
						eventId);
	}

	public double getTagPayInfo(long accountId, String dbSessionId,
			String loginId, String ipAddress, long transactionId, Long posId)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getTolltagDAO()
				.getTagPayInfo(accountId, dbSessionId, loginId, ipAddress,
						transactionId, posId);
	}

	public OlcMarketSourceRec[] getMarketSource(String lang) throws Exception {
		return DAOFactory.getDAOFactory().getTolltagDAO().getMarketSource(lang);
	}

	public boolean makePayment(long accountId, String dbSessionId,
			String loginId, String ipAddress, long transactionId,
			double paymentAmount, String cardCode, String paymentType,
			String cardNumber, String expireMonth, String expireYear,

			String nameOnCard, String address, String address2, String city,
			String state, String zipCode, String plus4, String cardSecurityCode)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getTolltagDAO()
				.makePayment(accountId, dbSessionId, loginId, ipAddress,
						transactionId, paymentAmount, cardCode, paymentType,
						cardNumber, expireMonth, expireYear, nameOnCard,
						address, address2, city, state, zipCode, plus4,
						cardSecurityCode);
	}

	public String loginCreation(BigDecimal accountId, String loginType,
			String dbSessionId, String loginId, String password,
			String ipAddress, String firstName, String middleInitial,
			String lastName, String email) throws EtccErrorMessageException,
			Exception {
		return DAOFactory
				.getDAOFactory()
				.getTolltagDAO()
				.loginCreation(accountId, loginType, dbSessionId, loginId,
						password, ipAddress, firstName, middleInitial,
						lastName, email);
	}

	public ArrayList<AccountCardDTO> getTollRemoveReasonList(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception {
		return DAOFactory.getDAOFactory().getTolltagDAO()
				.getTollRemoveReasonList(acctLoginDto);
	}

	public Collection removeTag(AccountLoginDTO acctLoginDto, String tagId,
			String rsnCode, String flag) throws EtccErrorMessageException,
			Exception {
		return DAOFactory.getDAOFactory().getTolltagDAO()
				.removeTag(acctLoginDto, tagId, rsnCode, flag);
	}

	public int validateVehicleInfo(AccountLoginDTO acctLoginDto, TagDTO tags)
			throws EtccException, EtccSecurityException, IOException {
		return DAOFactory.getDAOFactory().getTolltagDAO()
				.validateVehicleInfo(acctLoginDto, tags);
	}

	public Collection getAccountVehicles(AccountLoginDTO accountLoginDTO,
			String includeTag) throws EtccException, EtccSecurityException {
		return DAOFactory.getDAOFactory().getTolltagDAO()
				.getAccountVehicles(accountLoginDTO, includeTag);
	}

	public void createShiftEditsRecord(Long posId, String editItemTypeCode,
			String activityCode) throws EtccException {
		// TODO Auto-generated method stub
		DAOFactory.getDAOFactory().getTolltagDAO()
				.createShiftEditsRecord(posId, editItemTypeCode, activityCode);
	}
}
