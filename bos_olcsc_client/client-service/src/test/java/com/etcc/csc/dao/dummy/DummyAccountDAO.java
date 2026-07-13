/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.dummy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceStatusDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
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
import com.etcc.csc.dto.PaymentType;
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
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.service.AccountTestImpl;

/**
 * Dummy DAO for basic testing & Demos.
 * @author Stephen Davidson
 */
public class DummyAccountDAO extends AccountDAO {
    private static Map<String, AccountDTO> accounts = new ConcurrentHashMap<String, AccountDTO>();
    /**
     * @see AccountInterface#accountExists(String, String, String, String, String, String)
     */
    public AccountLoginDTO accountExists(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId) throws EtccException {
        return makeAccountLoginDTO(null, null);
    }

    public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        AccountDTO accountDTO = accounts.get(Long.toString(acctLoginDto.getAcctId()));
        if (accountDTO == null){
            accountDTO = new AccountTestImpl().getAccount(acctLoginDto);
            accountDTO.setAcctId(acctLoginDto.getAcctId());
//        accountDTO.setFirstName("Bobby");
//        accountDTO.setLastName("Hill");
            accountDTO.setPmtTypeEnum(PaymentType.CREDIT);
            accountDTO.setEmailAddress(acctLoginDto.getLoginId() + "@test.etcc.com");
            accountDTO.setSecurityQuestionID(1);
            accountDTO.setSecurityQuestionAnswer("security answer");
            accounts.put(Long.toString(acctLoginDto.getAcctId()), accountDTO);
        }

        return accountDTO;
    }

    /**
     * @see AccountInterface#getAccountStatus(AccountLoginDTO, AccountDTO)
     */
    public AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto, AccountDTO accountDTO) throws EtccException,
            EtccSecurityException {
        return new AccountTestImpl().getAccountStatus(acctLoginDto, accountDTO);
    }

    /**
     * @see AccountInterface#getAccountTags(AccountLoginDTO)
     */
    public TagDTO[] getAccountTags(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return new AccountTestImpl().getAccountTags(acctLoginDto);
    }

    /**
     * @see AccountInterface#getAlerts(AccountLoginDTO)
     */
    public AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return new AccountTestImpl().getAlerts(acctLoginDto);
    }

    /**
     * @see AccountInterface#getBillingInfo(AccountLoginDTO)
     */
    public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return new AccountTestImpl().getBillingInfo(acctLoginDto);
    }

    /**
     * @see AccountInterface#getLastTransactions(AccountLoginDTO)
     */
    public TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {        throw new UnsupportedOperationException("getLastTransactions not implemented yet.");
    }

    /**
     * @see AccountInterface#getTollTagUserPreference()
     */
    public UserPreferenceResultDTO getTollTagUserPreference() throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("getTollTagUserPreference not implemented yet.");
    }

    /**
     * @see AccountInterface#getUserPreference(AccountLoginDTO)
     */
    public UserPreferenceResultDTO getUserPreference(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {
        throw new UnsupportedOperationException("getUserPreference not implemented yet.");
    }

    /**
     * @see AccountInterface#isBigAccount(long)
     */
    public boolean isBigAccount(long accountId) throws EtccException{

        //return false;
        throw new UnsupportedOperationException("isBigAccount not implemented yet.");
    }

    /**
     * @see AccountInterface#isPaymentOwed(AccountLoginDTO, long, TagDTO[])
     */
    public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId, TagDTO[] licensePlates)
            throws EtccException, EtccSecurityException {

        //return false;
        throw new UnsupportedOperationException("isPaymentOwed not implemented yet.");
    }

    /**
     * @see AccountInterface#loginAccount(String, String, String, String, UserEnvDTO)
     */
    public AccountLoginDTO loginAccount(String userName, String password, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
        final AccountLoginDTO loginDTO = makeAccountLoginDTO(ipAddress, sessionId);
        loginDTO.setAcctId(userName.hashCode());
        loginDTO.setLoginId(userName);
        return loginDTO;
    }

    /**
     * @see AccountInterface#makePayment(AccountLoginDTO, BillingInfoDTO, double)
     */
    public ResultDTO makePayment(AccountLoginDTO loginDTO, BillingInfoDTO billingInfoDTO, Double rebillAmount, Double lowBalanceAmount, java.util.List<TagDTO> tagList, double paymentAmt) throws EtccException ,EtccSecurityException {return null;}

    /**
     * @see AccountInterface#setBillingInfo(AccountLoginDTO, BillingInfoDTO)
     */
    public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO)
            throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("setBillingInfo not implemented yet.");
    }

    /**
     * @see AccountInterface#setPersonalInfo(AccountLoginDTO, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, boolean, boolean, boolean, Long, String, String)
     */
    public PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4)
            throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("setPersonalInfo not implemented yet.");
    }

    /**
     * @see AccountInterface#setupAccountStep1(String, String, String, int, String, String, String, String)
     */
    public AccountLoginDTO setupAccountStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,UserEnvDTO userEnvDto) throws EtccException {
        throw new UnsupportedOperationException("setupAccountStep1 not implemented yet.");
    }

    /**
     * @see AccountInterface#setupOnlineAccess(AccountLoginDTO, OnlineAccessSetupDTO)
     */
    public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccSecurityException, EtccException {
        throw new UnsupportedOperationException("setupOnlineAccess not implemented yet.");
    }

    /**
     * @see AccountInterface#setupOnlineAccessStep1(String, String, String, String, String, String, String, String, UserEnvDTO)
     */
    public AccountLoginDTO setupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
        throw new UnsupportedOperationException("setupOnlineAccessStep1 not implemented yet.");
    }

    /**
     * @see AccountInterface#updateAccount(AccountLoginDTO, AccountDTO)
     */
    public ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto, AccountDTO accountDto) throws EtccException,
            EtccSecurityException {
        throw new UnsupportedOperationException("updateAccount not implemented yet.");
    }

    /**
     * @see AccountInterface#updateAccountTags(AccountLoginDTO, TagDTO[], boolean)
     */
    public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto, TagDTO[] tags, boolean checkDuplicate)
            throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("updateAccountTags not implemented yet.");
    }

    /**
     * @see AccountInterface#updateBillingAddress(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
     */
    public AccountLoginDTO updateBillingAddress(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String zip, String plus4, String country) throws EtccSecurityException, EtccException {
        throw new UnsupportedOperationException("updateBillingAddress not implemented yet.");
    }

    /**
     * @see AccountInterface#updateBillingInfo(AccountLoginDTO, BillingInfoDTO)
     */
    public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfo) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("updateBillingInfo not implemented yet.");
    }

    /**
     * @see AccountInterface#updateContactInfo(String, String, String, String, String, String, String, String, String, String)
     */
    public AccountLoginDTO updateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
            String alternatePhoneExt) throws EtccSecurityException, EtccException {
        throw new UnsupportedOperationException("updateContactInfo not implemented yet.");
    }

    /**
     * @see AccountInterface#updateLoginId(String, String, String, String, String, String)
     */
    public AccountLoginDTO updateLoginId(String sessionID, String ipAddress, String loginId, String oldLoginId,
            String password, String acctID) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("updateLoginId not implemented yet.");
    }

    /**
     * @see AccountInterface#updateLoginInfo(AccountLoginDTO, String, String, String, int, String, String, BaseContactInfo)
     */
    public ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto,
            String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer,
            String alternateEmail, BaseContactInfo contactInfo) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("updateLoginInfo not implemented yet.");
    }

    /**
     * @see AccountInterface#updateMailingAddr(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
     */
    public AccountLoginDTO updateMailingAddr(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String country, String zip, String plus4) throws EtccSecurityException, EtccException {
        throw new UnsupportedOperationException("updateMailingAddr not implemented yet.");
    }

    /**
     * @see AccountInterface#updatePasswordEmailSecurityQA(AccountLoginDTO, int, String, String, String, int, String)
     */
	public ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto,
			int updateFlags, String oldPw, String newPw, String emailAddress,
            int sQuestionID, String sqAnswer) throws EtccSecurityException, EtccException {
        throw new UnsupportedOperationException("updatePwEmailSq not implemented yet.");
    }

    /**
     * @see AccountInterface#updateRebillAmt(String, String, String, String, String, double, double)
     */
    public AccountLoginDTO updateRebillAmt(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccSecurityException, EtccException {
        AccountDTO accountDTO = accounts.get(acctID);
        accountDTO.setRebillAmt(rebillAmt.doubleValue());
        //Return an object with no errors.
        AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
        accountLoginDTO.setAcctId(Long.valueOf(acctID).longValue());
        return accountLoginDTO;
    }

    /**
     * @see AccountInterface#updateUserPreference(AccountLoginDTO, AccountIopDTO[], UserPreferenceDTO[])
     */
    public ErrorMessageDTO[] updateUserPreference(AccountLoginDTO acctLoginDto, AccountIopDTO[] acctIops,
            UserPreferenceDTO[] userPrefs) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("updateUserPreference not implemented yet.");
    }

    private AccountLoginDTO makeAccountLoginDTO(final String ipAddress, final String sessionId){
        AccountLoginDTO accountLoginDTO = AccountFactory.getAccountLoginDTO();
        accountLoginDTO.setDbSessionId(sessionId);
        accountLoginDTO.setLoginId(AccountLoginDTO.GENERIC_USER);
        accountLoginDTO.setLastLoginIp(ipAddress);
        return accountLoginDTO;
    }

	public ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto,
			double activationFee, List<TagDTO> tagDTOs) {

		throw new UnsupportedOperationException("sendWelcomeNotification not implemented yet.");
	}
	public ResultDTO makeInvoicePayment(AccountLoginDTO acctLoginDto,
			AccountPaymentMethodDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			String email) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	/*public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO,
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
		return null;
	}
	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException {
		return null;
	}
	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException {
		return null;
	}
	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return null;
	}
	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException {
		return null;
	}

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, Long accountDocumentId)
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

	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO)
			throws EtccException {
		return null;
	}

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, String documentType,
			String description, Long accountDocumentId) throws EtccException {
		// TODO Auto-generated method stub
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
	
	//defect 9064 txphung end 
}
