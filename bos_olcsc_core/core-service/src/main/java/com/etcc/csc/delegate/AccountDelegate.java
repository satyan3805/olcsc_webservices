package com.etcc.csc.delegate;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.datatype.PlanDetailBalances;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.service.AccountInterface;

public class AccountDelegate extends Delegate implements AccountInterface {

	AccountInterface account = (AccountInterface) getServiceObject(ServiceObjectEnum.ACCOUNT);

	public AccountDelegate() {
		super(AccountDelegate.class);
	}

	public AccountLoginDTO accountExists(String accountNumber,
										 String tolltagPrefix,
										 String tolltagNumber,
										 String driverLicenseState,
										 String driverLicenseNumber,
										 String taxId) throws EtccException {
        logger.info("Entering accountExists() @ AccountDelegate");		
		try {
			return account.accountExists(accountNumber,
									tolltagPrefix, tolltagNumber,
									driverLicenseState, driverLicenseNumber,
									taxId);
		} catch (Throwable t) {
			logger.error("Error in accountExists() @ AccountDelegate ", t);			
			throw new EtccException("Error running accountExists: " + t, t);
		}// end of try-catch()
	}// end of accountExists()

	public AccountLoginDTO setupOnlineAccessStep1(String acctCard, String accountNumber,
			String tolltagPrefix, String tolltagNumber, String driverLicenseState,
			String driverLicenseNumber, String taxId, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto, String locale) throws EtccException {
        logger.info("Entering setupOnlineAccessStep1() @ AccountDelegate");
		try {
			return account.setupOnlineAccessStep1(acctCard, accountNumber, tolltagPrefix,
					tolltagNumber, driverLicenseState, driverLicenseNumber,
					taxId, ipAddress, sessionId, userEnvDto, locale);
		} catch (Throwable t) {
			logger.error("Error in setupOnlineAccessStep1() @ AccountDelegate ", t);			
			throw new EtccException("Error running accountExists: " + t, t);
		}// end of try-catch()
	}// end of setupOnlineAccessStep1()

	public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto,
			OnlineAccessSetupDTO oasDto) throws EtccSecurityException,
			EtccException {
        logger.info("Entering setupOnlineAccess() @ AccountDelegate");		
		try {
			return account.setupOnlineAccess(acctLoginDto, oasDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in setupOnlineAccess() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in setupOnlineAccess() @ AccountDelegate ", t);
			throw new EtccException("Error running setupOnlineAccess: " + t,
									t);
		}// end of try-catch()
	}// end of setupOnlineAccess()

	public AccountLoginDTO validateAccount(String userName, String password,
										   String ipAddress) throws EtccException {
        logger.info("Entering validateAccount() @ AccountDelegate");
		try {
			return account.validateAccount(userName, password, ipAddress);
		} catch (Exception ex) {
			logger.error("Error in validateAccount() @ AccountDelegate ", ex);
			throw new EtccException(ex);
		}// end of try-catch()
	}// end of validateAccount()

	public AccountLoginDTO loginAccount(String userName, String password,
			String ipAddress, String sessionId, UserEnvDTO userEnvDto)
			throws EtccException {
        logger.info("Entering loginAccount() @ AccountDelegate");		
		try {
			return account.loginAccount(userName, password, ipAddress,
								   sessionId, userEnvDto);
		} catch (Exception ex) {
			logger.error("Error in loginAccount() @ AccountDelegate ", ex);
			throw new EtccException(ex);
		}// end of try-catch()
	}// end of loginAccount()

	public AccountDTO getAccount(AccountLoginDTO acctLoginDto,
								 long acctId) throws EtccException,
													 EtccSecurityException {
        logger.info("Entering getAccount() @ AccountDelegate");
		try {
			return account.getAccount(acctLoginDto, acctId);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccount() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccount() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccount: " + t, t);
		}// end of try-catch()
	}// end of getAccount()

	public AccountDTO getNewAccount(AccountLoginDTO acctLoginDto,
								 long acctId) throws EtccException,
													 EtccSecurityException {
        logger.info("Entering getNewAccount() @ AccountDelegate");
		try {
			return account.getNewAccount(acctLoginDto, acctId);
		} catch (EtccSecurityException se) {
			logger.error("Error in getNewAccount() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getNewAccount() @ AccountDelegate ", t);
			throw new EtccException("Error running getNewAccount: " + t, t);
		}// end of try-catch()
	}// end of getNewAccount()
	
	public Collection getInvoiceDetails(AccountLoginDTO acctLoginDto) throws EtccException,
								 EtccSecurityException {
        logger.info("Entering getInvoiceDetails() @ AccountDelegate");
		try {
			return account.getInvoiceDetails(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getInvoiceDetails() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getInvoiceDetails() @ AccountDelegate ", t);
			throw new EtccException("Error running getNewAccount: " + t, t);
		}// end of try-catch()
	}// end of getInvoiceDetails()
	
	public Collection getAccountTags(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAccountTags() @ AccountDelegate");
		try {
			return account.getAccountTags(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccountTags() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccountTags() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountTags: " + t, t);
		}// end of try-catch()
	}// end of getAccountTags()

	public Collection getAccountTagsBySearch(AccountLoginDTO acctLoginDto, String[] searchString)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAccountTagsBySearch() @ AccountDelegate");
		try {
			return account.getAccountTagsBySearch(acctLoginDto, searchString);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccountTagsBySearch() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccountTagsBySearch() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountTagsBySearch: " + t, t);
		}// end of try-catch()
	}// end of getAccountTagsBySearch()	

	public List<TagDTO> getProposedAccountTags(AccountLoginDTO acctLoginDto) throws EtccException,
			EtccSecurityException {
        logger.info("Entering getProposedAccountTags() @ AccountDelegate");
		try {
			return account.getProposedAccountTags(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getProposedAccountTags() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getProposedAccountTags() @ AccountDelegate ", t);
			throw new EtccException("Error running getProposedAccountTags: " + t, t);
		}// end of try-catch()
	}// end of getProposedAccountTags()

	public Map getAccountCardInfo(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAccountCardInfo() @ AccountDelegate");
		try {
			return account.getAccountCardInfo(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccountCardInfo() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccountCardInfo() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountCardInfo: " + t, t);
		}// end of try-catch()
	}// end of getAccountCardInfo()

	public int removeAccountCard(AccountLoginDTO acctLoginDto, AccountCardDTO acctCardDto)
			throws EtccException, EtccSecurityException {
        logger.info("Entering removeAccountCard() @ AccountDelegate");
		try {
			return account.removeAccountCard(acctLoginDto, acctCardDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in removeAccountCard() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in removeAccountCard() @ AccountDelegate ", t);
			throw new EtccException("Error running removeAccountCard: " + t, t);
		}// end of try-catch()
	}// end of removeAccountCard()

	public Collection getAccountReasonCodes(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAccountReasonCodes() @ AccountDelegate");
		try {
			return account.getAccountReasonCodes(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccountReasonCodes() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccountReasonCodes() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountReasonCodes: " + t, t);
		}// end of try-catch()
	}// end of getAccountReasonCodes()

	public boolean saveAccountCardRequest(AccountLoginDTO acctLoginDto, int numOfUnits)
			throws EtccSecurityException, EtccException {
        logger.info("Entering saveAccountCardRequest() @ AccountDelegate");
		try {
			return account.saveAccountCardRequest(acctLoginDto, numOfUnits);
		} catch (EtccSecurityException se) {
			logger.error("Error in saveAccountCardRequest() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in saveAccountCardRequest() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountCardInfo: " + t, t);
		}// end of try-catch()
	}// end of saveAccountCardRequest()

	public Collection<AccountCardDTO> getAccountCardDetailInfo(AccountLoginDTO acctLoginDto, String locale)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAccountCardDetailInfo() @ AccountDelegate");
		try {
			return account.getAccountCardDetailInfo(acctLoginDto, locale);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAccountCardDetailInfo() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAccountCardDetailInfo() @ AccountDelegate ", t);
			throw new EtccException("Error running getAccountCardDetailInfo: " + t, t);
		}// end of try-catch()
	}// end of getAccountCardDetailInfo()

	public UserPreferenceResultDTO getUserPreference(AccountLoginDTO
			acctLoginDto) throws EtccSecurityException, EtccException {
        logger.info("Entering getUserPreference() @ AccountDelegate");		
		try {
			return account.getUserPreference(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getUserPreference() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getUserPreference() @ AccountDelegate ", t);
			throw new EtccException("Error running getUserPreference: " + t, t);
		}// end of try-catch()
	}// end of getUserPreference()

	public UserPreferenceResultDTO getTollTagUserPreference(String locale)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getTollTagUserPreference() @ AccountDelegate");		
		try {
			return account.getTollTagUserPreference(locale);
		} catch (EtccSecurityException se) {
			logger.error("Error in getTollTagUserPreference() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getTollTagUserPreference() @ AccountDelegate ", t);
			throw new EtccException("Error running getTollTagUserPreference: " + t, t);
		}// end of try-catch()
	}// end of getTollTagUserPreference()

	public Collection updateUserPreference(AccountLoginDTO acctLoginDto,
			AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs)
			throws EtccSecurityException, EtccException {
        logger.info("Entering updateUserPreference() @ AccountDelegate");
		try {
			return account.updateUserPreference(
					acctLoginDto, acctIops, userPrefs);
		} catch (EtccSecurityException se) {
			logger.error("Error in updateUserPreference() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in updateUserPreference() @ AccountDelegate ", t);
			throw new EtccException("Error running updateUserPreference: " + t, t);
		}// end of try-catch()
	}// end of updateUserPreference()

	public Collection updateAccount(AccountLoginDTO acctLoginDto,
			AccountDTO acctDto) throws EtccSecurityException, EtccException {
        logger.info("Entering updateAccount() @ AccountDelegate");
		try {
			return account.updateAccount(acctLoginDto, acctDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in updateAccount() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in updateAccount() @ AccountDelegate ", t);
			throw new EtccException("Error running updateAccount: " + t, t);
		}// end of try-catch()
	}// end of updateAccount()

	public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, boolean checkDuplicate)
			throws EtccSecurityException, EtccException {
        logger.info("Entering updateAccountTags() @ AccountDelegate");
		try {
			return account.updateAccountTags(acctLoginDto, tags, checkDuplicate);
		} catch (EtccSecurityException se) {
			logger.error("Error in updateAccountTags() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in updateAccountTags() @ AccountDelegate ", t);
			throw new EtccException("Error running updateAccountTags: " + t, t);
		}// end of try-catch()
	}// end of updateAccountTags()

	public Collection getLastTransactions(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getLastTransactions() @ AccountDelegate");
		try {
			return account.getLastTransactions(acctLoginDto);
		} catch (EtccSecurityException se) {
			logger.error("Error in getLastTransactions() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getLastTransactions() @ AccountDelegate ", t);
			throw new EtccException("Error running getLastTransactions: " + t, t);
		}// end of try-catch()
	}// end of getLastTransactions()

	public Collection getAlerts(AccountLoginDTO acctLoginDto, String pageContext)
			throws EtccSecurityException, EtccException {
        logger.info("Entering getAlerts() @ AccountDelegate");
		try {
			return account.getAlerts(acctLoginDto, pageContext);
		} catch (EtccSecurityException se) {
			logger.error("Error in getAlerts() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in getAlerts() @ AccountDelegate ", t);
			throw new EtccException("Error running getAlerts: " + t, t);
		}// end of try-catch()
	}// end of getAlerts()

	public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId,
			TagDTO[] licPlates) throws EtccException, EtccSecurityException {
        logger.info("Entering isPaymentOwed() @ AccountDelegate");		
		try {
			return account.isPaymentOwed(acctLoginDto, transId, licPlates);
		} catch (EtccSecurityException se) {
			logger.error("Error in isPaymentOwed() @ AccountDelegate ", se);
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			logger.error("Error in isPaymentOwed() @ AccountDelegate ", t);
			throw new EtccException("Error running isPaymentOwed: " + t, t);
		}// end of try-catch()
	}// end of isPaymentOwed()

	public boolean isBigAccount(long accountId) {
        logger.info("Entering isBigAccount() @ AccountDelegate");		
		try {
			return account.isBigAccount(accountId);
		} catch (Throwable t) {
			logger.error("Error in isBigAccount() @ AccountDelegate ", t);
			return false;
		}// end of try-catch()
	}// end of isBigAccount()

	public void resetEmailAddressValid(AccountLoginDTO acctLoginDto, String emailAddress) {
        logger.info("Entering resetEmailAddressValid() @ AccountDelegate");		
		try {
			account.resetEmailAddressValid(acctLoginDto, emailAddress);
		} catch (Exception ex) {
			logger.error("Error in resetEmailAddressValid() @ AccountDelegate ", ex);
		}// end of try-catch()
	}// end of resetEmailAddressValid()

	public int isVerificationRequired(AccountLoginDTO acctLoginDto) {
        logger.info("Entering isVerificationRequired() @ AccountDelegate");
		int result = 0;
		try {
			result = account.isVerificationRequired(acctLoginDto);
		} catch (Exception ex) {
			logger.error("Error in isVerificationRequired() @ AccountDelegate ", ex);
		}// end of try-catch()
		return result;
	}// end of isVerificationRequired()

	public int verifyAccount(AccountLoginDTO acctLoginDto) {
        logger.info("Entering verifyAccount() @ AccountDelegate");
		int result = 0;
		try {
			return account.verifyAccount(acctLoginDto);
		} catch (Exception ex) {
			logger.error("Error in verifyAccount() @ AccountDelegate ", ex);
		}// end of try-catch()
		return result;
	}// end of verifyAccount()

	public int isEnoughtSecurityQuestions(AccountLoginDTO acctLoginDto) {
        logger.info("Entering isEnoughtSecurityQuestions() @ AccountDelegate");
		int result = 0;
		try {
			return account.isEnoughtSecurityQuestions(acctLoginDto);
		} catch (Exception ex) {
			logger.error("Error in isEnoughtSecurityQuestions() @ AccountDelegate ", ex);
		}// end of try-catch()
		return result;
	}// end of isEnoughtSecurityQuestions()

	public String getIntialPrepaidBalanceDetails(AccountLoginDTO acctLoginDto) {
        logger.info("Entering getIntialPrepaidBalanceDetails() @ AccountDelegate");
		String result = "";
		try {
			return account.getIntialPrepaidBalanceDetails(acctLoginDto);
		} catch (Exception ex) {
			logger.error("Error in getIntialPrepaidBalanceDetails() @ AccountDelegate ", ex);
		}// end of try-catch()
		return result;
	}// end of getIntialPrepaidBalanceDetails()

	public List<PlanDetailBalances> getPlanDetailBalances(long planDetailId)
			throws EtccException, EtccSecurityException {
        logger.info("Entering getPlanDetailBalances() @ AccountDelegate");
		try {
			return account.getPlanDetailBalances(planDetailId);
		} catch (Exception ex) {
			logger.error("Error in getPlanDetailBalances() @ AccountDelegate ", ex);
		}// end of try-catch()
		return null;
	}// end of getPlanDetailBalances()

	public Double getItemPriceByInventoryTypeIdAndDate(AccountLoginDTO acctLoginDto,
			Long inventoryTypeId, Date date)
			throws EtccException, EtccSecurityException {
        logger.info("Entering getItemPriceByInventoryTypeIdAndDate() @ AccountDelegate");
		try {
			return account
					.getItemPriceByInventoryTypeIdAndDate(acctLoginDto, inventoryTypeId, date);
		} catch (Exception ex) {
			logger.error("Error in getItemPriceByInventoryTypeIdAndDate() @ AccountDelegate ", ex);
		}// end of try-catch()
		return null;
	}// end of getItemPriceByInventoryTypeIdAndDate()

	public Double getTagSalePriceByItemType(AccountLoginDTO acctLoginDto, long inventoryTypeId,
			Date date)
			throws EtccException, EtccSecurityException {
        logger.info("Entering getTagSalePriceByItemType() @ AccountDelegate");		
		try {
			return account.getTagSalePriceByItemType(acctLoginDto, inventoryTypeId, date);
		} catch (Exception ex) {
			logger.error("Error in getTagSalePriceByItemType() @ AccountDelegate ", ex);
		}// end of try-catch()
		return null;
	}// end of getTagSalePriceByItemType()

	public String getAutoChargeAmounts(AccountLoginDTO acctLoginDto) {
        logger.info("Entering getAutoChargeAmounts() @ AccountDelegate");		
		try {
			return account.getAutoChargeAmounts(acctLoginDto);
		} catch (Exception ex) {
			logger.error("Error in getAutoChargeAmounts() @ AccountDelegate ", ex);
		}// end of try-catch()
		return null;
	}// end of getAutoChargeAmounts()

	public double getTagDepositAmout(AccountLoginDTO accountLoginDTO) throws Exception {
        logger.info("Entering getTagDepositAmout() @ AccountDelegate");		
		try {
			return account.getTagDepositAmout(accountLoginDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getTagDepositAmout() "
					 + accountLoginDTO + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getTagDepositAmout()"
					 + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}// end of try-catch()
	}// end of getTagDepositAmout()
	
	public double getCancelFulfillmentTagDepositAmout(AccountLoginDTO accountLoginDTO,
			String fulfillmentID) throws Exception{
        logger.info("Entering getCancelFulfillmentTagDepositAmout() @ AccountDelegate");		
		try {
			return account.getCancelFulfillmentTagDepositAmout(accountLoginDTO, fulfillmentID);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getCancelFulfillmentTagDepositAmout() "
					 + accountLoginDTO + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getCancelFulfillmentTagDepositAmout()"
					 + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}// end of try-catch()
	}// end of getCancelFulfillmentTagDepositAmout()

}// end of AccountDelegate Class
