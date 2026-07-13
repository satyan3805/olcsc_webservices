/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;


import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.Matcher;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceStatusDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.AuthenticatedSessionidDTO;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.DocumentUploadResponse;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.GetAccountDocumentResponse;
import com.etcc.csc.dto.GetAccountNotificationsDocumentResponse;
import com.etcc.csc.dto.GetAccountPhoneInfoResponse;
import com.etcc.csc.dto.GetInitialAutoChargeAmountsResponse;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.PersonalInfoUpdateResultDTO;
import com.etcc.csc.dto.RemoveBillingInfoResponse;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.TransactionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.dto.ValidateDocDownloadResponse;

/**
 * Test implementation of <tt>AccountInterface</tt>.
 * @author Milosh Boroyevich
 * @see AccountFactory
 */
public class AccountTestImpl implements AccountInterface {
    private static final Logger logger = Logger.getLogger(AccountTestImpl.class);

	private static int transactionId = 1;

	/**
	 * @see AccountFactory#getAccount(AccountLoginDTO)
	 */
	public AccountDTO getAccount(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
        return AccountFactory.getAccount(acctLoginDto);
	}

	/**
	 * @see AccountFactory#getAccountTagDTOs()
	 */
	public TagDTO[] getAccountTags(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		return (acctLoginDto == null ? null : AccountFactory.getAccountTagDTOs());
	}

	/**
	 * @see AccountFactory#getAccountLoginDTO()
	 * @see AccountFactory#getAccountLoginError()
	 */
	public AccountLoginDTO loginAccount(String userName, String password,
			String ipAddress, String sessionId, UserEnvDTO userEnvDto)
			throws EtccException {
		Matcher<String> matcher = AccountFactory.IsEmpty.aNonEmpty(String.class);
		if (userEnvDto != null
				&& matcher.matches(userName)
				&& matcher.matches(password)
				&& matcher.matches(ipAddress)
				&& matcher.matches(sessionId)
				) {
			final AccountLoginDTO accountLoginDTO = AccountFactory.getAccountLoginDTO();
			accountLoginDTO.setLoginId(userName);
			accountLoginDTO.setDbSessionId(sessionId);
			accountLoginDTO.setAcctStatus("A");
			accountLoginDTO.setPwChangeRequired(false);
            return accountLoginDTO;
		}
        return AccountFactory.getAccountLoginError();
	}


	public AccountLoginDTO accountExists(String accountNumber,
			String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber, String taxId)
			throws EtccException {
        final AccountLoginDTO accountLoginDTO = AccountFactory.getAccountLoginDTO();
        return accountLoginDTO;
	}

	public AccountLoginDTO createSession(long acctId, String ipAddress)
			throws EtccException {
		throw new UnsupportedOperationException();
	}

	public AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto,
			AccountDTO accountDTO) throws EtccException, EtccSecurityException {
	    return accountDTO;
	}

	public AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		return AccountFactory.getBillingInfo(acctLoginDto);
	}

    public TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public UserPreferenceResultDTO getTollTagUserPreference()
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public UserPreferenceResultDTO getUserPreference(
			AccountLoginDTO acctLoginDto) throws EtccException,
			EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public boolean isBigAccount(long accountId) throws EtccException{
		return false;
	}

	public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId,
			TagDTO[] licensePlates) throws EtccException, EtccSecurityException {
		return false;
	}

	public ResultDTO makePayment(AccountLoginDTO loginDTO,
			BillingInfoDTO billingInfoDTO, Double rebillAmount,
			Double lowBalanceAmount, List<TagDTO> tagList, double paymentAmt)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto,
			BillingInfoDTO billingInfoDTO) throws EtccException,
			EtccSecurityException {
	    return new BillingInfoResultDTO();
	}

	public PersonalInfoUpdateResultDTO setPersonalInfo(
			AccountLoginDTO acctLoginDto, String acctType, String companyName,
			String firstName, String lastName, String primaryPhone,
			String alternatePhone, String taxId, String driverLicState,
			String driverLicNumber, String address1, String address2,
			String address3, String address4, String city, String state,
			String zip, String country, boolean byMail, boolean byEmail,
			boolean byPhone, Long retailTransId, String altPhoneExt,
			String plus4) throws EtccException, EtccSecurityException {
		StringBuilder sb = new StringBuilder("setPersonalInfo[");
		sb.append("acctLoginDto=").append(acctLoginDto)
			.append(", acctType=").append(acctType)
			.append(", companyName=").append(companyName)
			.append(", firstName=").append(firstName)
			.append(", lastName=").append(lastName)
			.append(", primaryPhone=").append(primaryPhone)
			.append(", alternatePhone=").append(alternatePhone)
			.append(", taxId=").append(taxId)
			.append(", driverLicState=").append(driverLicState)
			.append(", driverLicNumber=").append(driverLicNumber)
			.append(", address1=").append(address1)
			.append(", address2=").append(address2)
			.append(", address3=").append(address3)
			.append(", address4=").append(address4)
			.append(", city=").append(city)
			.append(", state=").append(state)
			.append(", zip=").append(zip)
			.append(", country=").append(country)
			.append(", byMail=").append(byMail)
			.append(", byEmail=").append(byEmail)
			.append(", byPhone=").append(byPhone)
			.append(", retailTransId=").append(retailTransId)
			.append(", altPhoneExt=").append(altPhoneExt)
			.append(", plus4=").append(plus4);
		logger.debug(sb.append("]").toString());
		PersonalInfoUpdateResultDTO result = new PersonalInfoUpdateResultDTO();
		if (!AccountFactory.ACCOUNT_USERNAME.equalsIgnoreCase(acctLoginDto.getLoginId()))
			result.addErrors(new String[] {"UnsupportedOperation"});
		return result;
	}

	public AccountLoginDTO setupAccountStep1(String loginId, String password,
			String emailAddress, int securityQuestionID,
			String securityQuestionAnswer, String alternateEmail,
			String ipAddress, String sessionID,UserEnvDTO userEnvDto) throws EtccException {
		AccountLoginDTO login = new AccountLoginDTO();
		login.setLoginId(loginId);
		login.setDbSessionId(sessionID);
		return login;
	}

	public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto,
			OnlineAccessSetupDTO oasDto) throws EtccSecurityException,
			EtccException {
		throw new UnsupportedOperationException();
	}

	public AccountLoginDTO setupOnlineAccessStep1(String accountNumber,
			String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber,
			String taxId, String ipAddress, String sessionId,
			UserEnvDTO userEnvDto) throws EtccException {
		throw new UnsupportedOperationException();
	}

	public ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto,
			AccountDTO accountDto) throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, boolean checkDuplicate) throws EtccException,
			EtccSecurityException {
		throw new UnsupportedOperationException();
	}

    public AccountLoginDTO updateBillingAddress(AccountLoginDTO acctLogin, Address address)
            throws EtccException, EtccSecurityException {
        logger.debug("Mock updateBillingAddress: " + address);
        return acctLogin;
    }

	@Deprecated
    public AccountLoginDTO updateBillingAddress(String acctID, String acctType,
			String sessionID, String ipAddress, String loginId,
			String address1, String address2, String address3, String address4,
			String city, String state, String zip, String plus4, String country)
			throws EtccSecurityException, EtccException {
		StringBuilder sb = new StringBuilder("Mock updateBillingAddress [");
		sb.append("address1=").append(address1);
		sb.append("address2=").append(address2);
		sb.append("address3=").append(address3);
		sb.append("address4=").append(address4);
		sb.append("city=").append(city);
		sb.append("state=").append(state);
		sb.append("zip=").append(zip);
		sb.append("plus4=").append(plus4);
		sb.append("country=").append(country);
		sb.append(']');
		logger.debug(sb.toString());
		return new AccountLoginDTO();
	}

	public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfo) throws EtccException,
			EtccSecurityException {
		logger.debug("Mock updateBillingInfo: " + billingInfo);
		return acctLogin;
	}

	public AccountLoginDTO updateContactInfo(String acctID, String acctType,
			String sessionID, String ipAddress, String loginId,
			String emailAddress, String altEmailAddress, String primaryPhone,
			String alternatePhone, String alternatePhoneExt)
			throws EtccSecurityException, EtccException {
		throw new UnsupportedOperationException();
	}



	public ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto,
			double activationFee, List<TagDTO> tagDTOs) throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public AccountLoginDTO updateLoginId(String sessionID, String ipAddress,
			String loginId, String oldLoginId, String password, String acctID)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto,
			String loginId, String password, String emailAddress,
			int securityQuestionID, String securityQuestionAnswer,
			String alternateEmail, BaseContactInfo contactInfo)
	        throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public AccountLoginDTO updateMailingAddress(AccountLoginDTO acctLogin, Address address)
	        throws EtccSecurityException, EtccException {
	    logger.debug("Mock updateMailingAddress: " + address);
	    return acctLogin;
	}

	@Deprecated
    public AccountLoginDTO updateMailingAddr(String acctID, String acctType,
			String sessionID, String ipAddress, String loginId,
			String address1, String address2, String address3, String address4,
			String city, String state, String country, String zip, String plus4)
			throws EtccSecurityException, EtccException {
	    logger.debug("Mock updateMailingAddr: " + acctID
	            + '|' + acctType + '|' + sessionID
	            + '|' + ipAddress + '|' + loginId
	            + '|' + address1 + '|' + address2
	            + '|' + address3 + '|' + address4
	            + '|' + city + '|' + state + '|'
	            + country + '|' + zip + '|' + plus4);
	    return new AccountLoginDTO();
	}

	@SuppressWarnings("deprecation")
	@Deprecated
	public AccountLoginDTO updatePassword(String sessionID, String ipAddress,
			String loginId, String oldPassword, String password, String acctID)
			throws EtccException, EtccSecurityException {
	    AccountLoginDTO dto = loginAccount(loginId, password, ipAddress, sessionID, null);
	    dto.setPwChangeRequired(false);
	    return dto;
	}

	public ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto, int updateFlags, String oldPw,
			String newPw, String emailAddress, int sQuestionID, String sAnswer)
			throws EtccSecurityException, EtccException {
	    acctLoginDto.setPwChangeRequired(false);
        return new ResultDTO();
	}

	public AccountLoginDTO updateRebillAmt(String acctID, String acctType,
			String sessionID, String ipAddress, String loginId,
			BigDecimal rebillAmt, BigDecimal lowBalanceLevel)
			throws EtccSecurityException, EtccException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Deprecated
	public AccountLoginDTO updateSecQn(String sessionID, String ipAddress,
			String loginId, String password, String acctID, int securityQnID,
			String securityAns) throws EtccException, EtccSecurityException {
        AccountLoginDTO dto = loginAccount(loginId, password, ipAddress, sessionID, null);
        dto.setPwChangeRequired(false);
        return dto;
	}

	public ErrorMessageDTO[] updateUserPreference(AccountLoginDTO acctLoginDto,
			AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}
	/*public ResultDTO makeInvoicePayment(AccountLoginDTO acctLoginDto,
			AccountPaymentMethodDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			String email) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public BaseDTO makeAccountPayment(AccountLoginDTO loginDTO,
			AccountPaymentMethodDTO primaryCard, Double rebillAmount,
			Double depositAmount, List<TagDTO> savedVehicles, Double paymentAmt)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}*/
/*	
	public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO,
			AccountCreditCardDTO primaryCard, Double rebillAmount,
			Double depAmount, List<TagDTO> tagList, double paymentAmt)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultDTO makeAccountPaymentEFT(AccountLoginDTO loginDTO,
			AccountEFTDTO primaryCard, Double rebillAmount, Double depAmount,
			List<TagDTO> tagList, double paymentAmt) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
	
	public BillingInfoResultDTO setBillingInfoCreditCard(
			AccountLoginDTO acctLoginDto, AccountCreditCardDTO paymentMethodDto)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public BillingInfoResultDTO setBillingInfoEFT(AccountLoginDTO acctLoginDto,
			AccountEFTDTO paymentMethodDto) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//defect 9064 txphung beign
	public ResultDTO validateRoutingNumber(String routingNumber)
			throws EtccException, EtccSecurityException {
		return null;
	}

	public AccountLoginDTO setupAccountWithPlanStep1(String loginId,
			String password, String emailAddress, int securityQuestionID,
			String securityQuestionAnswer, String alternateEmail,
			String ipAddress, String sessionID, UserEnvDTO userEnvDto,
			String plan) throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountDeviceStatusDTO getAccountDeviceStatus(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId, String fingerprintId) throws EtccException {
		throw new UnsupportedOperationException();
	}
	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException {
		throw new UnsupportedOperationException();
	}
	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException {
		throw new UnsupportedOperationException();
	}
	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return null;
	}
	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException {
		return null;
	}
	//defect 9064 txphung end 

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, String documentType,
			String description, Long accountDocumentId)
			throws EtccException {
		return null;
	}

	public GetAccountDocumentResponse getAccountDocument(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return null;
	}

	public GetAccountNotificationsDocumentResponse getAcctNotificationDocuments(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId) throws EtccException {
		return null;
	}

	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException {
		return null;
	}

	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return null;
	}

	public ResultDTO setContactInfoByContactId(BigDecimal acctId, String docType, String sessionId, String ipAddress,
			String userId, String emailAddress, String altEmail, String homePhoNbr, String workPhoNbr,
			String workPhoExt, String homePhoExt, String MobilePhone, String mobilePhoExt, String smsAlertsOptIn,
			BigDecimal homePhoneId, BigDecimal workPhoneId, BigDecimal mobilePhoneId) throws EtccException {
		return null;
	}

	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId, BigDecimal acctTypeId, BigDecimal acctPlanId,
			BigDecimal planDetailId, BigDecimal paymentFormId, BigDecimal vehicleCount) throws EtccException {
		return null;
	}

	public ValidateDocDownloadResponse validateDocDownload(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return null;
	}
	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException {
		return null;
	}
	public AccountLoginDTO ccrmaSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
			String emailAddress, String phoneNumber, String ipAddress, String jsessionId, UserEnvDTO userEnvDto)
			throws EtccException {
		return null;
	}
	public AccountLoginDTO ccrmaSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
			throws EtccSecurityException, EtccException {
		return null;
	}
	public PersonalInfoUpdateResultDTO setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto,
			String acctType, String companyName, String firstName, String lastName, String primaryPhone,
			String alternatePhone, String taxId, String driverLicState, String driverLicNumber, String address1,
			String address2, String address3, String address4, String city, String state, String zip, String country,
			boolean byMail, boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4,
			String primaryPhoneExt, String smsAlertsOptIn) throws EtccException, EtccSecurityException {
		return null;
	}
	
}
