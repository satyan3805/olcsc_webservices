package com.etcc.csc.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.datatype.PlanDetailBalances;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.SessionDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.TransactionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;

public class Account implements AccountInterface {

	Logger logger = Logger.getLogger(Account.class);

	public Account() {
	}

	public AccountLoginDTO accountExists(String accountNumber,
			String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber, String taxId)
			throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.accountExists(accountNumber, tolltagPrefix,
					tolltagNumber, driverLicenseState, driverLicenseNumber,
					taxId);
		} catch (Exception e) {
			logger.error("Error in accounExists " + e, e);
			throw new EtccException(e);
		}

	}

	public AccountLoginDTO setupOnlineAccessStep1(String acctCard,
			String accountNumber, String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber,
			String taxId, String ipAddress, String sessionId,
			UserEnvDTO userEnvDto, String locale) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.setupOnlineAccessStep1(acctCard, accountNumber,
					tolltagPrefix, tolltagNumber, driverLicenseState,
					driverLicenseNumber, taxId, ipAddress, sessionId,
					userEnvDto, locale);
		} catch (EtccException e) {
			logger.error("Error in setupOnlineAccessStep1 " + e, e);
			throw e;
		}
	}

	public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto,
			OnlineAccessSetupDTO oasDto) throws EtccSecurityException,
			EtccException {
		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.setupOnlineAccess(acctLoginDto, oasDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in setupOnlineAccess() " + oasDto
					+ " " + ese, ese);
			throw ese;
		} catch (EtccException e) {
			logger.error("Error in setupOnlineAccess " + oasDto + " " + e, e);
			throw e;
		}
	}

	/**
	 * @Deprecated loginAccount takes over the function of this method.
	 **/
	public AccountLoginDTO validateAccount(String userName, String password,
			String ipAddress) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.validateAccount(userName, password, ipAddress);
		} catch (Exception e) {
			logger.error("Error in validateAccount " + userName + " " + e, e);
			throw new EtccException(e);
		}

	}

	public AccountLoginDTO loginAccount(String userName, String password,
			String ipAddress, String sessionId, UserEnvDTO userEnvDto)
			throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			AccountLoginDTO acctDto = accountDao.loginAccount(userName,
					password, ipAddress, sessionId, userEnvDto);
			/*
			 * if (acctDto != null) { Session session = new Session();
			 * SessionDTO sessionDto = new SessionDTO();
			 * sessionDto.setDocumentId(acctDto.getAcctId());
			 * sessionDto.setIpAddress(ipAddress);
			 * sessionDto.setUserName(userName);
			 * sessionDto.setDocType(Constants.LOGIN_TYPE_ACCOUNT); String
			 * sessionId = session.makeSession(sessionDto);
			 * acctDto.setDbSessionId(sessionId); }
			 */
			return acctDto;
		} catch (Exception e) {
			logger.error("Error in loginAccount " + userName + " " + e, e);
			throw new EtccException(e);
		}

	}

	public AccountLoginDTO createSession(long acctId, String ipAddress)
			throws EtccException {

		logger.debug("Start");
		AccountLoginDTO result = null;
		try {
			Session session = new Session();
			SessionDTO sessionDto = new SessionDTO();
			sessionDto.setDocumentId(acctId);
			sessionDto.setIpAddress(ipAddress);
			sessionDto.setUserName(Constants.LOGIN_GENERIC_USER);
			sessionDto.setDocType(Constants.LOGIN_TYPE_ACCOUNT);
			String sessionId = session.makeSession(sessionDto);
			if (sessionId != null) {
				result = new AccountLoginDTO();
				result.setAcctId(acctId);
				result.setLastLoginIp(ipAddress);
				result.setLoginId(Constants.LOGIN_GENERIC_USER);
				result.setLoginType(Constants.LOGIN_TYPE_ACCOUNT);
				result.setDbSessionId(sessionId);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error in createSession " + acctId + " " + e, e);
			throw new EtccException(e);
		}
	}

	public Map getAccountCardInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccountCardInfo(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountCardInfo() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getAccountCardInfo() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getAccountReasonCodes(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccountReasonCodes(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountReasonCodes() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getAccountReasonCodes() "
							+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public int removeAccountCard(AccountLoginDTO acctLoginDto,
			AccountCardDTO acctCardDto) throws EtccException,
			EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.removeAccountCard(acctLoginDto, acctCardDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in removeAccountCard() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in removeAccountCard() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public boolean saveAccountCardRequest(AccountLoginDTO acctLoginDto,
			int numOfUnits) throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.saveAccountCardRequest(acctLoginDto, numOfUnits);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in saveAccountCardRequest() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in saveAccountCardRequest() "
							+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public Collection<AccountCardDTO> getAccountCardDetailInfo(
			AccountLoginDTO acctLoginDto, String locale) throws EtccException,
			EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccountCardDetailInfo(acctLoginDto, locale);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountCardDetailInfo() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getAccountCardDetailInfo() "
							+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public AccountDTO getAccount(AccountLoginDTO acctLoginDto, long acctId)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccount(acctLoginDto, acctId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccount() " + acctId + " "
					+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getAccount() " + acctId
					+ " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public AccountDTO getNewAccount(AccountLoginDTO acctLoginDto, long acctId)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getNewAccount(acctLoginDto, acctId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getNewAccount() " + acctId
					+ " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getNewAccount() "
					+ acctId + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getAccountTags(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccountTags(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getAccountTagsBySearch(AccountLoginDTO acctLoginDto,
			String[] searchArr) throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAccountTagsBySearch(acctLoginDto, searchArr);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public List<TagDTO> getProposedAccountTags(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getProposedAccountTags(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getProposedAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getProposedAccountTags() "
							+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, boolean checkDuplicate) throws EtccException,
			EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.updateAccountTags(acctLoginDto, tags,
					checkDuplicate);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in updateAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in updateAccountTags() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public UserPreferenceResultDTO getUserPreference(
			AccountLoginDTO acctLoginDto) throws EtccException,
			EtccSecurityException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.getUserPreference(acctLoginDto);
	}

	public UserPreferenceResultDTO getTollTagUserPreference(String locale)
			throws EtccException, EtccSecurityException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.getTollTagUserPreference(locale);
	}

	public Collection<ErrorMessageDTO> updateUserPreference(
			AccountLoginDTO acctLoginDto, AccountIopDTO[] acctIops,
			UserPreferenceDTO[] userPrefs) throws EtccException,
			EtccSecurityException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.updateUserPreference(acctLoginDto, acctIops,
				userPrefs);
	}

	public Collection updateAccount(AccountLoginDTO acctLoginDto,
			AccountDTO acctDto) throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.updateAccount(acctLoginDto, acctDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in updateAccount() "
					+ acctLoginDto + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in updateAccount() "
					+ acctDto + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getLastTransactions(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getLastTransactions(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getLastTransactions() "
					+ acctLoginDto + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getLastTransactions()"
					+ ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getAlerts(AccountLoginDTO acctLoginDto, String pageContext)
			throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getAlerts(acctLoginDto, pageContext);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAlerts() " + acctLoginDto
					+ " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getAlerts()" + ee, ee,
					true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId,
			TagDTO[] licensePlates) throws EtccException, EtccSecurityException {
		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.isPaymentOwed(acctLoginDto, transId,
					licensePlates);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in isPaymentOwed() "
					+ acctLoginDto + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in isPaymentOwed()" + ee,
					ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	/**
	 * stub to get TransactionDTO included in the wsdl
	 * 
	 * @return
	 */
	public TransactionDTO getTransactionDto() {
		return null;
	}

	/**
	 * stub to get errorMessageDTO included in the wsdl
	 * 
	 * @return
	 */
	public ErrorMessageDTO getErrorMessage() {
		return null;
	}

	/**
	 * stub to get TagDTO included in the wsdl
	 * 
	 * @return
	 */
	public TagDTO getAccountTag() {
		return null;
	}

	/**
	 * stub only.
	 * 
	 * @return
	 */
	public AlertDTO getAlert() {
		return null;
	}

	public UserPreferenceDTO getDummyUserPref() {
		return null;
	}

	public AccountDeviceDTO getDummyAccountDevice() {
		return null;
	}

	public AccountIopDTO getDummyAccountIop() {
		return null;
	}

	public boolean isBigAccount(long accountId) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.isBigAccount(accountId);
	}

	public void resetEmailAddressValid(AccountLoginDTO acctLoginDto,
			String emailAddress) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		accountDao.resetEmailAddressValid(acctLoginDto, emailAddress);
	}

	public int isVerificationRequired(AccountLoginDTO acctLoginDto) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.isVerificationRequired(acctLoginDto);
	}

	public int verifyAccount(AccountLoginDTO acctLoginDto) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.verifyAccount(acctLoginDto);
	}

	public int isEnoughtSecurityQuestions(AccountLoginDTO acctLoginDto) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.isEnoughtSecurityQuestions(acctLoginDto);
	}

	public String getIntialPrepaidBalanceDetails(AccountLoginDTO acctLoginDto) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		return accountDao.getIntialPrepaidBalanceDetails(acctLoginDto);
	}

	public List<PlanDetailBalances> getPlanDetailBalances(long planDetailId)
			throws EtccException, EtccSecurityException {

		logger.debug("Start getPlanDetailBalances");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getPlanDetailBalances(planDetailId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getPlanDetailBalances() " + " "
					+ ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getPlanDetailBalances()" + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public Double getItemPriceByInventoryTypeIdAndDate(
			AccountLoginDTO acctLoginDto, Long inventoryTypeId, Date date)
			throws EtccException, EtccSecurityException {

		logger.debug("Start getItemPriceByInventoryTypeIdAndDate");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getItemPriceByInventoryTypeIdAndDate(
					acctLoginDto, inventoryTypeId, date);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getItemPriceByInventoryTypeIdAndDate() "
							+ acctLoginDto + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getItemPriceByInventoryTypeIdAndDate()" + ee,
					ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public Double getTagSalePriceByItemType(AccountLoginDTO acctLoginDto,
			long inventoryTypeId, Date date) throws EtccException,
			EtccSecurityException {

		logger.debug("Start getTagSalePriceByItemType");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getTagSalePriceByItemType(acctLoginDto,
					inventoryTypeId, date);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getTagSalePriceByItemType() "
					+ acctLoginDto + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getTagSalePriceByItemType()" + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}

	}

	public String getAutoChargeAmounts(AccountLoginDTO acctLoginDto) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountDAO accountDao = daoFactory.getAccountDAO();
		try {
			return accountDao.getAutoChargeAmounts(acctLoginDto);
		} catch (Exception ese) {
		}
		return null;
	}

	public double getTagDepositAmout(AccountLoginDTO accountLoginDTO)
			throws Exception {
		logger.debug("Start getTagDepositAmout");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getTagDepositAmout(accountLoginDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getTagDepositAmout() "
					+ accountLoginDTO + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getTagDepositAmout()"
					+ ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public double getCancelFulfillmentTagDepositAmout(
			AccountLoginDTO accountLoginDTO, String fulfillmentID)
			throws Exception {
		logger.debug("Start getCancelFulfillmentTagDepositAmout");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getCancelFulfillmentTagDepositAmout(
					accountLoginDTO, fulfillmentID);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getCancelFulfillmentTagDepositAmout() "
							+ accountLoginDTO + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getCancelFulfillmentTagDepositAmout()" + ee,
					ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	public Collection getInvoiceDetails(AccountLoginDTO acctLoginDto)
			throws Exception {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountDAO accountDao = daoFactory.getAccountDAO();
			return accountDao.getInvoiceDetails(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAccountReasonCodes() "
					+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error(
					"Exception in getAccountReasonCodes() "
							+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}
}
