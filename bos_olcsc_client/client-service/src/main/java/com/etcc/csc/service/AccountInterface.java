/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

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
import com.etcc.csc.dto.BaseDTO;
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
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Contains methods for managing user accounts.
 */
@Local
public interface AccountInterface extends ServiceInterface {
    /**
     * Option specifying no updates required.
     */
    public static final int NONE = 0;
    /**
     * Option specifying that Password update is required.
     */
    public static final int PASSWORD = 1 << 0;
    /**
     * Option specifying that E-Mail update is required.
     */
    public static final int EMAIL = 1 << 1;
    /**
     * Option specifying that Security Question update is required.
     */
    public static final int SECURITY_QA = 1 << 2;

    /**
     * Validates if an account exists.
     * @param accountNumber
     * @param tolltagPrefix
     * @param tolltagNumber
     * @param driverLicenseState
     * @param driverLicenseNumber
     * @param taxId
     * @return Returns AccountLoginDTO containing the user's account info. If
     * the acctId is empty, this indicates that the account does not exist.
     * @throws EtccException
     */
    AccountLoginDTO accountExists(String accountNumber, String tolltagPrefix,
                                  String tolltagNumber,
                                  String driverLicenseState,
                                  String driverLicenseNumber,
                                  String taxId) throws EtccException;

    /**
     * Validates if the account exists and logs the user to the database.
     * @param accountNumber
     * @param tolltagPrefix
     * @param tolltagNumber
     * @param driverLicenseState
     * @param driverLicenseNumber
     * @param taxId
     * @param ipAddress
     * @param sessionId
     * @param userEnvDto
     * @return
     * @throws EtccException
     */
    AccountLoginDTO setupOnlineAccessStep1(String accountNumber,
        String tolltagPrefix, String tolltagNumber, String driverLicenseState,
        String driverLicenseNumber, String taxId, String ipAddress,
        String sessionId, UserEnvDTO userEnvDto) throws EtccException;

    /**
     * Performs the setup of online access account.
     * @param oasDto
     * @return Returns an AccountLoginDTO. It contains the new db session id
     * created by the database. If unsuccessful, the errors property contains
     * the error messages.
     * @throws EtccException
     */
    AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto,
        OnlineAccessSetupDTO oasDto) throws EtccSecurityException,
        EtccException;

    /**
     * Validates if the account matches what's in the database.
     * @param userName
     * @param password
     * @param ipAddress
     * @return
     * @throws EtccException
     * @deprecated Handled by {@link #loginAccount(String, String, String, String, UserEnvDTO)}.
     * @see #loginAccount(String, String, String, String, UserEnvDTO)
     */
//    AccountLoginDTO validateAccount(String userName, String password, String ipAddress) throws EtccException;

    /**
     * Logs the account in the database. This is the first step for subsequent
     * user requests to the database.
     * @param userName
     * @param password
     * @param ipAddress
     * @param sessionId The id given by the web server for this user session.
     * @return AccountLoginDTO containing the account id and db session id
     * generated by the database.
     * @throws EtccException
     */
    AccountLoginDTO loginAccount(String userName, String password,
        String ipAddress, String sessionId, UserEnvDTO userEnvDto)
        throws EtccException;

    /**
     * Creates a session for an account. This is for account holders
     * without an account login.
     * @param acctId
     * @param ipAddress
     * @return
     * @throws EtccException
     */
    AccountLoginDTO createSession(long acctId, String ipAddress) throws EtccException;

    /**
     * Login and retrieve the account information using the specified credentials.
     * @param acctLoginDto must satisfy security credential to retrieve this record.
     * @return the account corresponding to the account login
     * @throws EtccException
     * @throws EtccSecurityException Thrown when the security verification fails.
     */
    public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException;

    /**
     * Similar to getAccountTags of AccountHistory only that it returns an
     * Array of TagDTO instead of the Oracle-generated class.
     * @param acctLoginDto
     * @return The Tags associated with the account.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    TagDTO[] getAccountTags(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException;

    /**
     * Retrieves the user's preferences from the database.
     * @param acctLoginDto Contains the user's login credentials.
     * @return Returns a UserPreferenceDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    UserPreferenceResultDTO getUserPreference(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException;

    /**
     * Retrieves the user preference options for customers applying for
     * a tolltag.
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    UserPreferenceResultDTO getTollTagUserPreference()
        throws EtccException, EtccSecurityException;

    /**
     * Persists the changes to the user preference to the database.
     * @param acctLoginDto
     * @param acctIops
     * @param userPrefs
     * @throws EtccException
     * @throws EtccSecurityException
     * @return Any errors that may have occured.
     */

    ErrorMessageDTO[] updateUserPreference(AccountLoginDTO acctLoginDto,
        AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs)
        throws EtccException, EtccSecurityException;

    /**
     * Saves the changes to an existing account.
     * @param acctLoginDto
     * @param accountDto Account Information.
     * @return Returns an Array of error messages.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto,
        AccountDTO accountDto) throws EtccException, EtccSecurityException;

    /**
     * Updates the account's tag information.
     * @param acctLoginDto Login credentials.
     * @param tags A collection of TagDTO to update.
     * @param checkDuplicate Flag to determine if duplicates are to be checked.
     * Record will not be updated if a duplicate license plate is found and the
     * flag is set to true.
     * @return Returns TagUpdateResultDTO containing the result of the
     * operation, collection of errors, and duplicate license tags.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto, TagDTO[] tags,
        boolean checkDuplicate) throws EtccException, EtccSecurityException;

    /**
     * Retrieves the alerts for the given account.
     * @param acctLoginDto
     * @return Returns an array of AlertDTO.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException;
    /**
     * Retrieves the previous x transactions for the given account.
     * @param acctLoginDto
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException;

    /**
     * Checks whether the account has an outstanding payment given a retail
     * transaction id, or given a collection of license plates, if there
     * are violations that need to be settled.s
     * @param acctLoginDto
     * @param transId Retail transaction ID. Generated when a toll tag is
     * ordered.
     * @param licensePlates A collection of TagDTOs.
     * @return True if the account owes something, false otherwise.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId,
            TagDTO[] licensePlates) throws EtccException,
            EtccSecurityException;

    boolean isBigAccount( long accountId )throws EtccException;

    AccountLoginDTO setupAccountStep1(String loginId, String password, String emailAddress,
                    int securityQuestionID, String securityQuestionAnswer, String alternateEmail,
                    String ipAddress, String sessionID, UserEnvDTO userEnvDto) throws EtccException;


    AccountLoginDTO updateLoginId(String sessionID,
                    String ipAddress, String loginId, String oldLoginId,
                    String password, String acctID) throws EtccException, EtccSecurityException;

    /**
     * @deprecated use {@link #updatePasswordEmailSecurityQA(AccountLoginDTO, int, String, String, String, int, String)}
     */
    @Deprecated
    AccountLoginDTO updatePassword(String sessionID,
                    String ipAddress, String loginId, String oldPassword,
                    String password, String acctID) throws EtccException, EtccSecurityException;

    PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto,
                        String acctType,
                        String companyName,
                        String firstName,
                        String lastName,
                        String primaryPhone,
                        String alternatePhone,
                        String taxId,
                        String driverLicState,
                        String driverLicNumber,
                        String address1,
                        String address2,
                        String address3,
                        String address4,
                        String city,
                        String state,
                        String zip,
                        String country,
                        boolean byMail,
                        boolean byEmail,
                        boolean byPhone,
                        Long retailTransId,
                        String altPhoneExt,
                        String plus4
                       ) throws EtccException, EtccSecurityException;

    /**
     * Updates account login information and saves the changes to the account_logins table
     * TODO: The user should not be allowed to change their name through the online interface!
     * @param acctLoginDto
     * @param loginId
     * @param password
     * @param emailAddress
     * @param securityQuestionID
     * @param securityQuestionAnswer
     * @param alternateEmail
     * @param contactInfo The user should not be allowed to change their name through the online interface
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto, String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, BaseContactInfo contactInfo)
    throws EtccException, EtccSecurityException;

    /**
     * @deprecated use {@link #updatePasswordEmailSecurityQA(AccountLoginDTO, int, String, String, String, int, String)}
     */
    @Deprecated
    AccountLoginDTO updateSecQn(String sessionID,
                    String ipAddress, String loginId, String password,
                    String acctID, int securityQnID, String securityAns) throws EtccException, EtccSecurityException;


    /**Saves the billing information to the account_cards table or the account_eft table
     * @param acctLoginDto
     * @param billingInfoDTO
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException, EtccSecurityException;
    /*BillingInfoResultDTO setBillingInfoEFT(AccountLoginDTO acctLoginDto, AccountEFTDTO paymentMethodDto) throws EtccException, EtccSecurityException;
    BillingInfoResultDTO setBillingInfoCreditCard(AccountLoginDTO acctLoginDto, AccountCreditCardDTO paymentMethodDto) throws EtccException, EtccSecurityException;
*/
    /**
     * Charges a customer via the supplied billing information for the amount specified.
     * @param acctLoginDto the customer to charge.
     * @param billingInfoDTO The details needed to bill the customer.
     * @param xmlPosting XML Postintg Content for Payment.
     * @return dto with the Transaction ID populated if successful, or error messages if not.
     * @throws EtccException thrown if any exceptions occur during processing.
     * @throws EtccSecurityException thrown if the user is not properly logged in.
     */
    //ResultDTO makePayment(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO billingInfoDTO, String xmlPosting, String emailAddress) throws EtccException, EtccSecurityException;

    BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException;

    AccountLoginDTO updateContactInfo(String acctID, String acctType, String sessionID, String ipAddress, String loginId, String emailAddress, String altEmailAddress,
                                String primaryPhone, String alternatePhone, String alternatePhoneExt) throws EtccSecurityException, EtccException;

    /**
     * Update mailing address convenience method with safer parameters.
     * @param acctLogin account login details
     * @param address the new address details
     * @return the updated account login.
     * @throws EtccSecurityException
     * @throws EtccException
     * @see #updateMailingAddr(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
     */
    public AccountLoginDTO updateMailingAddress(AccountLoginDTO acctLogin, Address address)
            throws EtccSecurityException, EtccException;

    /**
     * Takes a bunch of strings and updates the mailing address.
     * Make sure strings are in the correct order!
     * @return ErrorMessageDTO[] packaged in an AccountLoginDTO on error, otherwise an empty AccountLoginDTO
     * @deprecated use the safer interface {@link #updateMailingAddress(AccountLoginDTO, Address)}
     */
    @Deprecated
    AccountLoginDTO updateMailingAddr(String acctID, String acctType, String sessionID,
                        String ipAddress, String loginId, String address1, String address2,
                        String address3, String address4, String city, String state,
                        String country, String zip, String plus4) throws EtccSecurityException, EtccException;

    /**
     * @param acctLogin
     * @param billingInfo
     * @return ErrorMessageDTO[] packaged in an AccountLoginDTO on error, otherwise an empty AccountLoginDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfo) throws EtccException, EtccSecurityException;

    /**
     * Updates the billing address with the specified address.
     * @param acctLogin
     * @param address
     * @return ErrorMessageDTO[] packaged in an AccountLoginDTO on error, otherwise the AccountLoginDTO specified
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AccountLoginDTO updateBillingAddress(AccountLoginDTO acctLogin, Address address) throws EtccException, EtccSecurityException;

    /**
     * Takes a bunch of strings and updates the billing address.
     * Make sure strings are in the correct order!
     * @return ErrorMessageDTO[] packaged in an AccountLoginDTO on error, otherwise an empty AccountLoginDTO
     * @deprecated use the safer interface {@link #updateBillingAddress(AccountLoginDTO, Address)}
     */
    @Deprecated
    AccountLoginDTO updateBillingAddress(String acctID, String acctType, String sessionID,
                        String ipAddress, String loginId, String address1, String address2, String address3,
                        String address4, String city, String state, String zip, String plus4, String country) throws EtccSecurityException, EtccException;

    AccountLoginDTO updateRebillAmt(String acctID, String acctType, String sessionID,
                        String ipAddress, String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccSecurityException, EtccException;

    AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto, AccountDTO accountDTO) throws EtccException, EtccSecurityException;

    /**
     * Updates the password, e-mail and security question/answer.
     * Note: the flag determines which fields are validated and updated.
     * @param acctLoginDto
     * @param updateFlags flags indicating which fields to update
     * @param oldPw old password
     * @param newPw new password
     * @param emailAddress email address
     * @param sQuestionID ID of the security question
     * @param sAnswer security answer
     * @return error messages wrapped in a BaseDTO
     * @throws EtccSecurityException
     * @throws EtccException
     */
    ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto, int updateFlags, String oldPw, String newPw, String emailAddress, int sQuestionID, String sAnswer) throws EtccSecurityException, EtccException;


    ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto, double activationFee, List<TagDTO> tagDTOs) throws EtccException, EtccSecurityException;

	//ResultDTO makeInvoicePayment(AccountLoginDTO acctLoginDto,AccountPaymentMethodDTO paymentMethodDTO, ViolatorDTO violatorDTO,String email)  throws EtccException, EtccSecurityException;
    

    ResultDTO makePayment(AccountLoginDTO loginDTO, BillingInfoDTO billingInfoDTO, Double rebillAmount, Double depAmount, List<TagDTO> tagList, double paymentAmt) throws EtccException, EtccSecurityException;

    /*public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO, AccountCreditCardDTO primaryCard, Double rebillAmount,Double depAmount, List<TagDTO> tagList, double paymentAmt) throws EtccException, EtccSecurityException;
    public ResultDTO makeAccountPaymentEFT(AccountLoginDTO loginDTO, AccountEFTDTO primaryCard, Double rebillAmount,Double depAmount, List<TagDTO> tagList, double paymentAmt) throws EtccException, EtccSecurityException;*/
    
  //defect 9064 txphung beign
    public ResultDTO validateRoutingNumber(String routingNumber) throws EtccException, EtccSecurityException;
  //defect 9064 txphung end
  //Express Enh Account changes: added new method to create the account
    AccountLoginDTO setupAccountWithPlanStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail,
            String ipAddress, String sessionID, UserEnvDTO userEnvDto,String plan) throws EtccException;
	public AccountDeviceStatusDTO getAccountDeviceStatus(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId, String fingerprintId) throws EtccException;
	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException;
	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException;
	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException;
	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException;

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, String documentType,
			String description, Long accountDocumentId) throws EtccException;
	public GetAccountDocumentResponse getAccountDocument(Long acctId, String acctType, String dbSessionId, String ipAddress, String loginId)throws EtccException;
	
	public GetAccountNotificationsDocumentResponse getAcctNotificationDocuments(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId) throws EtccException;
	
	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException;
	
	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException;
	
	public ResultDTO setContactInfoByContactId(BigDecimal acctId, String docType, String sessionId, String ipAddress,
			String userId, String emailAddress, String altEmail, String homePhoNbr, String workPhoNbr,
			String workPhoExt, String homePhoExt, String MobilePhone, String mobilePhoExt, String smsAlertsOptIn,
			BigDecimal homePhoneId, BigDecimal workPhoneId, BigDecimal mobilePhoneId) throws EtccException;
	
	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId, BigDecimal acctTypeId, BigDecimal acctPlanId,
			BigDecimal planDetailId, BigDecimal paymentFormId, BigDecimal vehicleCount) throws EtccException;
	
	public ValidateDocDownloadResponse validateDocDownload(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException;
	
	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException;
	
	public AccountLoginDTO ccrmaSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
			String emailAddress, String phoneNumber, String ipAddress, String jsessionId, UserEnvDTO userEnvDto)
			throws EtccException;

	public AccountLoginDTO ccrmaSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
			throws EtccSecurityException, EtccException;
	
	public PersonalInfoUpdateResultDTO setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4, String primaryPhoneExt, String smsAlertsOptIn)
            throws EtccException, EtccSecurityException;
}
