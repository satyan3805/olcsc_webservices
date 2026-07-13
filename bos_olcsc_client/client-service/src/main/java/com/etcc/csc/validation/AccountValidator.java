package com.etcc.csc.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.bean.AuthorizedContactBean;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.util.StringUtil;

/**
 * Copied from OLCSCService com.etcc.csc.service.validation.AccountValidator.
 * @author (task 488) Stephen Davidson
 */
/*
 * The Public methods should throw a ValidationException, which in general will be handled by an Aspect.  The individual
 * private validation methods should return null if no validation errors, or one or more strings if there are validation
 * errors.
 */
//TODO: Review and refactor the various validation methods (ESPECIALLY THE PRIVATE STATIC ONES!) as needed.
public class AccountValidator extends BaseValidator {
	private static final Logger logger = Logger.getLogger(AccountValidator.class);

	/**
	 * Adds a non-null value to the ArrayList.
	 * @author (task 488) Stephen Davidson
	 *
	 */
	private static class MessageList<E> extends ArrayList<E>{

        /**
         * Unique Id with version for serialization.
         */
        private static final long serialVersionUID = -7892513618500042385L;

        /**
         * Constructor.
         */
        public MessageList() {
            super();
        }

        /**
         * Constructor.
         * @param c The initial collection.
         */
        public MessageList(Collection<? extends E> c) {
            super(c == null ? 16 : c.size());
            addAll(c);
        }

        /**
         * Constructor.
         * @param initialCapacity The initial size of the list.
         */
        public MessageList(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        public boolean add(E o) {
            if (o != null){
                return super.add(o);
            } //else
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            if (c == null || c.size() == 0){
                return false;
            }
            ensureCapacity(size() + c.size());
            boolean changed = false;
            for (E o : c) {
                if (add(o) && !changed){
                    changed = true;
                }
            }
            return changed;
        }
	}

    private static final int MAX_USER_NAME_LENGTH = 80;
    private static final int MAX_PASSWORD_LENGTH = 80; //defect#9765 change max length from 30 to 80
    private static final int MIN_TAX_ID_LENGTH = 9;
    private static final int MAX_TAX_ID_LENGTH = 11;
    //Start defect#9421
    //private static final String[] TOLLTAG_PREFIXES = { "HCTR", "TEX.", "TXDT", "OTA." , "CTRM" , "MTAH" };
    private static final String[] TOLLTAG_PREFIXES = { "HCTR", "DNT", "OTA", "TXDT" , "TEX" , "KTA", "MTAH", "BANCP"};
    //End defect#9421
    private static final int MIN_LOGIN_ID_LENGTH = 6;
    private static final int MAX_LOGIN_ID_LENGTH = 80;
    // EZTSS reset password function sets the length to 6 (not 8) characters -- the minimum length should not be checked here.
    // Plus, it's best not to tell potential hackers what is the minimum length!
    private static final int MIN_PASSWORD_LENGTH = 0;
    private static final String BUSINESS_ACCOUNT_TYPE = "business";
    private static final String PERSONAL_ACCOUNT_TYPE = "personal";
    private static final int MAX_SEC_QN_ANSWER_LENGTH = 20;
    private static final int MAX_EMAIL_LENGTH = 80; // see /accountInformation/changeContactInfo
    private static final int MAX_PHONE_LENGTH = 20; // see /accountInformation/changeContactInfo
    private static final int MAX_PHONE_EXT_LENGTH = 6; // see /accountInformation/changeContactInfo
//    private static final String[] BILLING_TYPE_CREDIT = { PaymentType.CREDIT.toString(), PaymentType.CREDIT.getCodeString() };
//    private static final String[] BILLING_TYPE_EFT = { PaymentType.EFT.toString(), PaymentType.EFT.getCodeString() };
//    private static final String[] CARD_TYPES = { "A", "D", "M", "V" };
//    private static final String[] BANK_ACCOUNT_TYPES = { "PC", "CC" };
    private static final String VALID_PHONE_PATTERN =
        // // ^\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1,}
        "^\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1,}";



    /**
     * Constructor.  Utility class which should not be instantiated.
     */
    private AccountValidator() {
        //end <init>
    }

    /**
     * Validates login parameters prior to calling the Database.
     * @param userName The account username.
     * @param password The account password.
     * @param ipAddress The ipaddress of the user logging in.
     * @param sessionId The App Server session ID.
     * @param userEnvDto The user environment object.
     * @throws ValidationException thrown if validation does not succeed in order to abort DB calls.
     */
    public static void validateLoginAccount(String userName, String password, String ipAddress, String sessionId, UserEnvDTO userEnvDto,String regExpPass) throws ValidationException {
        final Collection<String> messages = new MessageList<String>(4);
        messages.add(validateUserName(userName));
        messages.add(validatePassword(password,regExpPass));
        messages.addAll(validateContext(ipAddress, sessionId, userEnvDto));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }
    @Deprecated
    public static void validateSetupOnlineAccessStep1(String accountNumber,
        String tolltagPrefix, String tolltagNumber, String driverLicenseState,
        String driverLicenseNumber, String taxId, String ipAddress,
        String sessionId, UserEnvDTO userEnvDto) throws ValidationException {

        logger.warn("Deprecated validateSetupOnlineAccessStep1 method called.");
        Thread.dumpStack();

        // cleanNumericField(accountNumber)
        // removeUnwantedChar(tolltagNumber)
        // removeUnwantedChar(driverLicenseNumber)
        // removeUnwantedChar(taxId)
        final Collection<String> messages = new MessageList<String>(8);
        messages.addAll(validateContext(ipAddress, sessionId, userEnvDto));

        if (StringUtil.isEmpty(accountNumber) && StringUtil.isEmpty(tolltagNumber))
            messages.add("Please provide a Account Number or TollTag Number");
        //ddinh - 5351 - Driver License & TaxID are not required
        /*if (StringUtil.isEmpty(driverLicenseNumber) && StringUtil.isEmpty(taxId))
            messages.add("Please provide a Driver License Number or Tax ID");*/
        if (StringUtil.isEmpty(accountNumber) == false)
            messages.add(validateAccountNumber(accountNumber));
        if (StringUtil.isEmpty(tolltagNumber) == false) {
            messages.add(validateTolltagPrefix(tolltagPrefix));
            messages.add(validateTolltagNumber(tolltagNumber));
        }
        if (StringUtil.isEmpty(driverLicenseNumber) == false)
            messages.add(validateDriverLicenseNumber(driverLicenseNumber, driverLicenseState));
        if (StringUtil.isEmpty(taxId) == false)
            messages.add(validateTaxId(taxId));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }

    }

    @Deprecated
    public static void validateSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto) throws ValidationException{
        logger.warn("Deprecated validateSetupOnlineAccess method called.");
        Thread.dumpStack();

        if (oasDto == null)
            throw new ValidationException("Input is empty");

        final Collection<String> messages = new MessageList<String>(8);
        messages.add(validateAccountLoginId(oasDto.getLoginId())); // removeUnwantedChar
        messages.add(validatePassword(oasDto.getPassword(),null));
        messages.add(validateEmailAddress(oasDto.getEmailAddress()));// removeUnwantedChar
        //validate Security Question: the question is not passed only the ID
        messages.add(validateSecurityQuestionAnswer(oasDto.getSecurityQuestionAnswer()));
        messages.add(validateAlternateEmail(oasDto.getAlternateEmail())); // removeUnwantedChar
        messages.add(validateEmailAddresses(oasDto.getEmailAddress(), oasDto.getAlternateEmail()));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Validates if the information on Screen 1 of Account Setup is "legal" & valid (ex. valid length, patterns, etc.) to be
     * sent to the database.
     * @param loginId the username to validate.
     * @param password the password to validate.
     * @param emailAddress the email address to validate
     * @param securityQuestionID
     * @param securityQuestionAnswer the security answer to validate.
     * @param alternateEmail alternate email address to validate.
     * @param ipAddress the ip address to validate.
     * @param sessionId the session id to validate.
     * @throws ValidationException thrown if anything fails validation.
     */
    public static void validateSetupAccountStep1(String loginId,
            String password, String emailAddress, int securityQuestionID,
            String securityQuestionAnswer, String alternateEmail,
            String ipAddress, String sessionId,String regExpPass) throws ValidationException{

        final Collection<String> messages = new MessageList<String>(8);
        messages.add(validateAccountLoginId(loginId));
        messages.add(validatePassword(password,regExpPass));
        messages.add(validateEmailAddress(emailAddress));
        messages.add(validateSecurityQuestionAnswer(securityQuestionAnswer));
        messages.add(validateAlternateEmail(alternateEmail));
        messages.add(validateEmailAddresses(emailAddress, alternateEmail));
        messages.add(validateIpAddress(ipAddress));
        messages.add(validateSessionId(sessionId));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verifies that the parameters for the Update Login ID service are valid.
     * @param sessionId The session id to validate.
     * @param ipAddress the ip address of the client computer to validate.
     * @param loginId the (new!) username to validate.
     * @param oldLoginId the old username (must be present)
     * @param password the user's password (must be present for security reasons).
     * @param acctID the ID of the account.
     * @throws ValidationException thrown if anything fails validation.
     */
    public static void validateUpdateLoginId(String sessionId, String ipAddress, String loginId, String oldLoginId,
            String password, String acctID,String regExpPass) throws ValidationException {

        Collection<String> messages = new MessageList<String>(Arrays.asList(new String[] {
                validateSessionId(sessionId),
                validateIpAddress(ipAddress),
                validatePassword(password,regExpPass),
                validateAccountLoginId(loginId),
                StringUtil.isEmpty(oldLoginId) ? "Old Login ID is required" : null,
                StringUtil.isEmpty(acctID) ? "Account ID is required" : null }));
        if (messages.size() > 0) {
            throw new ValidationException(messages);
        }
    }

    /**
     * Verify that the parameters for the Update Password service are valid enough to send to the database.
     * @param sessionId The session id to validate.
     * @param ipAddress the ip address of the client computer to validate.
     * @param loginId the username to validate.
     * @param oldPassword The original password (which is being changed).
     * @param password The new password.
     * @param acctID the ID of the account.
     * @throws ValidationException thrown if anything fails validation.
     */
     @Deprecated
    public static void validateUpdatePassword(String sessionId, String ipAddress, String loginId, String oldPassword,
            String password, String acctID) throws ValidationException {
        Collection<String> messages = new MessageList<String>(Arrays.asList(new String[] {
                validateSessionId(sessionId),
                validateIpAddress(ipAddress),
                validatePassword(password,null),
                validateAccountLoginId(loginId),
                StringUtil.isEmpty(acctID) ? "Account ID is required" : null }));
        messages.add(validatePassword(oldPassword,null));
        messages.add(validatePassword(password,null));
        if (messages.size() > 0) {
            throw new ValidationException(messages);
        }
    }

    /**
     * Validates the personal information for Account Setup.
     * @param acctLoginDto The Login DTO for the current user.
     * @param acctType The type of the account.
     * @param companyName The name of the company owning the account if a Business Account.
     * @param firstName The first name of the Account holder if a personal Account.
     * @param lastName The last name of the Account holder if a personal Account.
     * @param primaryPhone The primary contact phone number.
     * @param alternatePhone The alternate phone number, if any.
     * @param taxId The ID of the corporation if a Business account.
     * @param driverLicState The State that issued the account holder's DL.
     * @param driverLicNumber The number of the Account holder's DL.
     * @param address1 First Line of the Account Holder's Address. (required)
     * @param address2 Second Line of the Account Holder's Address.
     * @param address3 Third Line of the Account Holder's Address.
     * @param address4 Forth Line of the Account Holder's Address.
     * @param city City the account holder resides in if a US Address.
     * @param state State the account holder resides in if a US Address.
     * @param zip Account holder's zip or postal code.
     * @param country Country the Account holder resides in.
     * @param byMail If the User wishes to receives notifications by mail.
     * @param byEmail  If the User wishes to receives notifications by email.
     * @param retailTransId Not currently used.
     * @param altPhoneExt Extension for the Alternate phone number, if any.
     * @param plus4 The "Plus4" of the Zip code, if any.
     * @throws ValidationException if any of the Validations fail.
     */
     //TODO: Refactor this method
    public static void validateSetPersonalInfo(AccountLoginDTO acctLoginDto,
                        String acctType, String companyName, String firstName,
                        String lastName, String primaryPhone, String alternatePhone,
                        String taxId, String driverLicState, String driverLicNumber,
                        String address1, String address2, String address3,
                        String address4, String city, String state, String zip,
                        String country, boolean byMail, boolean byEmail,
                        Long retailTransId, String altPhoneExt, String plus4,boolean isExpAccount) throws ValidationException {
        if (acctLoginDto == null)
            throw new ValidationException("Account Login Information is required");

        final Collection<String> messages = new MessageList<String>(16);
        String msg = validateAccountType(acctType);
        if (msg != null){
            messages.add(msg);
        }
        if (PERSONAL_ACCOUNT_TYPE.equals(acctType)) {
            messages.add(validateDriverLicenseState(driverLicState,isExpAccount));
            messages.add(validatePersonalInfoDriverLicenseNumber(driverLicNumber, driverLicState));
        } else if (BUSINESS_ACCOUNT_TYPE.equals(acctType)) {
            if ((StringUtil.isEmpty(companyName) == false) && (isValidName(companyName) == false))
               messages.add("Invalid Company Name");
            if (StringUtil.isEmpty(taxId) && StringUtil.isEmpty(driverLicNumber))
                messages.add("Either Tax ID Number -or- Driver's License Number is required");
            if (StringUtil.isEmpty(taxId) == false)
                messages.add(validatePersonalInfoTaxId(taxId));
            if (StringUtil.isEmpty(driverLicNumber) == false) {
                messages.add(validateDriverLicenseState(driverLicState,isExpAccount));
                messages.add(validatePersonalInfoDriverLicenseNumber(driverLicNumber, driverLicState));
            }
        } 
        if (isValidName(firstName) == false){
            messages.add("Invalid First Name");
        }
        if (isValidName(lastName) == false){
            messages.add("Invalid Last Name");
        }
        messages.add(validatePrimaryPhone(primaryPhone));
        messages.add(validateAlternatePhone(alternatePhone));
        messages.add(validateAlternatePhoneExt(altPhoneExt, alternatePhone));
        
        if (isExpAccount && (!StringUtil.isEmpty(country) && !StringUtil.isEmpty(state))){
		    messages.addAll(validateMailingAddress(country, address1, address2, address3, address4, city, state, zip, plus4, true));
        }else if (!isExpAccount){
        	messages.addAll(validateMailingAddress(country, address1, address2, address3, address4, city, state, zip, plus4, true));
        }
//        if (byMail == null)
//            messages.add("Mail Notification preference is required");
//        if (byEmail == null)
//            messages.add("E-Mail Notification preference is required");
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    @Deprecated
    public static void validateUpdateLoginInfo(AccountLoginDTO acctLoginDto,
                                   String loginId, String password,
                                   String emailAddress, int securityQuestionID,
                                   String securityQuestionAnswer,
                                   String alternateEmail) throws ValidationException {
        logger.warn("Deprecated validateUpdateLoginInfo method called.");
        Thread.dumpStack();

        final Collection<String> messages = new MessageList<String>(Arrays.asList(new String[]{
                validateAccountLoginId(loginId),
                validatePassword(password,null),
                validateEmailAddress(emailAddress),
                validateSecurityQuestionAnswer(securityQuestionAnswer),
                validateAlternateEmail(alternateEmail),
        }));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }

    }

    @Deprecated
    public static void validateUpdateLoginInfo(AccountLoginDTO acctLoginDto,
                                   String loginId, String password,
                                   String emailAddress, int securityQuestionID,
                                   String securityQuestionAnswer,
                                   String alternateEmail,
                                   String firstName,
                                   String lastName) throws ValidationException {
        logger.warn("Deprecated validateUpdateLoginInfo method called.");
        Thread.dumpStack();

        final Collection<String> messages = new MessageList<String>(Arrays.asList(new String[]{
                validateName(firstName, "First"),
                validateName(lastName, "Last")
        }));
        try {
            validateUpdateLoginInfo(acctLoginDto, loginId, password, emailAddress, securityQuestionID,
                    securityQuestionAnswer, alternateEmail);
        } catch (ValidationException e){
            e.addErrorMessages(messages);
            throw e;
        }
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    @Deprecated
    public static void validateUpdateSecQn(String sessionId,
                    String ipAddress, String loginId, String password,
                    String acctID, int securityQnID, String securityAns) throws ValidationException {
        logger.warn("Deprecated validateUpdateSecQn method called.");
        Thread.dumpStack();

        final Collection<String> messages = new MessageList<String>(Arrays.asList(new String[]{
                validateAccountLoginId(loginId),
                validatePassword(password,null),
                validateSessionId(sessionId),
                validateIpAddress(ipAddress),
                validateSecurityQuestionAnswer(securityAns),
                StringUtil.isEmpty(acctID) ? "Account ID is required" : null
        }));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verify if the Contact info to be sent to the database is valid.
     * @param acctID The ID of the account.
     * @param acctType The type of the account.
     * @param sessionID The ID of the Session.
     * @param ipAddress The client IP Address.
     * @param loginId The username.
     * @param emailAddress the new email address for the user.
     * @param altEmailAddress the new alternate email address for the user.
     * @param primaryPhone the new primary phone number for the user.
     * @param alternatePhone the new alternate phone number for the user, if any.
     * @param alternatePhoneExt the new alternate phone number extension for the user, if any.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateContactInfo(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId, String emailAddress, String altEmailAddress,
                    String primaryPhone, String alternatePhone, String alternatePhoneExt) throws ValidationException {

        final Collection<String> messages = new MessageList<String>(8);
        messages.addAll(validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId));
        messages.add(validateEmailAddress(emailAddress));
        messages.add(validateAlternateEmail(altEmailAddress));
        messages.add(validatePrimaryPhone(primaryPhone));
        messages.add(validateAlternatePhone(alternatePhone));
        messages.add(validateAlternatePhoneExt(alternatePhoneExt, alternatePhone));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verifies that the new Billing info is valid enough to submit to the Database.
     * @param acctLoginDto Login object -- must be present.
     * @param billingInfoDTO The new billing information.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateSetBillingInfo(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO paymentMethodDTO,boolean isExpAccount) throws ValidationException {
        if (acctLoginDto == null)
            throw new ValidationException("Account Login Information is required");
        //else
        Collection<String> msgs = validateBillingInfo(paymentMethodDTO,isExpAccount);
        if (msgs.size() > 0){
            throw new ValidationException(msgs);
        }
    }

    /**
     * Validates that the payment details are valid enough to send to the database.
     * @param acctLoginDto The account the charges are for.
     * @param billingInfoDTO The information to generate a payment transaction.
     * @param paymentAmt The amount the transaction is for.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateMakePayment(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO billingInfoDTO, double paymentAmt,boolean isExpAccount) throws ValidationException{
        final String msg = "Payment Amount cannot be less than 0";
        try {
            validateSetBillingInfo(acctLoginDto, billingInfoDTO,isExpAccount);
        } catch (ValidationException e){
            if (paymentAmt < 0.0) {
                e.addErrorMessages(Arrays.asList(msg));
            }
            throw e;

        }
        if (paymentAmt < 0.0) {
            throw new ValidationException(msg);
        }
    }

    /**
     * Verifies that the user's address information is valid enough to submit to the Database.
     * @param acctID The ID of the account.
     * @param acctType The type of the account.
     * @param sessionID The ID of the Session.
     * @param ipAddress The client IP Address.
     * @param loginId The username.
     * @param address1 Address Line1 -- should always be present.
     * @param address2 Address Line2
     * @param address3 Address Line3
     * @param address4 Address Line4
     * @param city The user's City -- required if USA address.
     * @param state The User's State -- required if USA address.
     * @param country The User's country.  If not present, USA is the default.
     * @param zip The Zip code -- required if USA address.
     * @param plus4 The Zip +4 extension.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateMailingAddr(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId, String address1, String address2,
                    String address3, String address4, String city, String state,
                    String country, String zip, String plus4) throws  ValidationException {
        final Collection<String> messages = new MessageList<String>(8);
        messages.addAll(validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId));
        messages.addAll(validateMailingAddress(country, address1, address2, address3, address4,
            city, state, zip, plus4, true));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verifies that the billing information is valid enough to submit to the Database.
     * @param acctLogin The account login information
     * @param billingInfo The new billing information.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateBillingInfo(AccountLoginDTO acctLogin, AccountPaymentMethodDTO billingInfo,boolean isExpAccount) throws ValidationException {
        final Collection<String> messages = new MessageList<String>(8);
        messages.addAll(validateAcctLoginInfo(acctLogin));
        messages.addAll(validateBillingInfo(billingInfo,isExpAccount));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verifies that the billing information is valid enough to submit to the Database.
     * @param acctID The ID of the account.
     * @param acctType The type of the account.
     * @param sessionID The ID of the Session.
     * @param ipAddress The client IP Address.
     * @param loginId The username.
     * @param address1 Line 1 of the Address -- Required.
     * @param address2 Line 2 of the Address -- Optional.
     * @param address3 Line 3 of the Address -- Optional.
     * @param address4 Line 4 of the Address -- Optional.
     * @param city The City -- Required if USA address.
     * @param state The State for the Billing Address -- Required if USA Address.
     * @param zip The Zip code for the USA Address -- Required if USA Address.
     * @param plus4 The zip+4 -- Optional.
     * @param country May be blank.  If blank, handled as if USA address.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateBillingAddress(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId, String address1, String address2, String address3,
                    String address4, String city, String state, String zip, String plus4, String country) throws ValidationException {
        logger.warn("Deprecated validateMakePayment method called.");
        final Collection<String> messages = new MessageList<String>(
                validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId));
        messages.addAll(validateMailingAddress(country, address1, address2, address3, address4,city, state, zip, plus4,false));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Verifies that the rebill amount (the amount to load the user's account to) is valid enough to submit to the
     * database.
     * @param acctID The ID of the account.
     * @param acctType The type of the account.
     * @param sessionID The ID of the Session.
     * @param ipAddress The client IP Address.
     * @param loginId The username.
     * @param rebillAmt the new rebill amount.
     * @param lowBalanceLevel the balance below which the user's account is "refreshed" or recharged.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateRebillAmt(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws ValidationException {
    	
    	final double rebill = rebillAmt !=null ?rebillAmt.doubleValue() : 0.0;
    	final double lowBalance = lowBalanceLevel != null ? lowBalanceLevel.doubleValue() : 0.0;
    	
        if (lowBalance < 0.0){
            //TODO: This probably should be an IllegalArgumentException.
            throw new ValidationException("Low Balance Level must be bigger than 0");
        }//else
        final List<String> messages = new MessageList<String>();
        messages.addAll(validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId));
        if ((rebill < 0.01) || (rebill > 4800.00)){
            messages.add("Rebill amount is out of range");
        } else  if (rebill < lowBalance){
            messages.add("Rebill amount is below low balance level");
        }
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    /**
     * Checks that passwords, email, and security answers are valid.
     * @param acctLoginDto The Login object for this user -- only logged in users can update passwords, email addresses,
     * and security questions.
     * @param newPw The new user password.
     * @param oldPw The old user password.
     * @param emailAddress the new email address.
     * @param sQuestionID The id of the Security Question (currently not checked).
     * @param sAnswer The user's answer for the security question.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validatePasswordEmailSecurity(AccountLoginDTO acctLoginDto, int updateFlags,
            String oldPw, String newPw, String emailAddress,
            int sQuestionID, String sAnswer,String regExpPass) throws ValidationException{
        if (acctLoginDto == null){
            //Hard stop.
            throw new ValidationException("Account Login Information is required");
        } //else
        final List<String> messages = new MessageList<String>();
        if ((updateFlags & AccountInterface.PASSWORD) > 0) {
            messages.add(validatePassword(oldPw,regExpPass));
            messages.add(validatePassword(newPw,regExpPass));
        }
        if ((updateFlags & AccountInterface.EMAIL) > 0) {
            messages.add(validateEmailAddress(emailAddress));
        }
        if ((updateFlags & AccountInterface.SECURITY_QA) > 0) {
            //validateSecurityQuestion(sQuestion);
            messages.add(validateSecurityQuestionAnswer(sAnswer));
        }
         if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }

    public static void validateSetAuthorizedContacts(AccountLoginDTO acctLoginDto, List<AuthorizedContactBean> authorizedContacts) throws ValidationException {
        if (acctLoginDto == null)
            throw new ValidationException("Account Login Information is required");
        AuthorizedContactBean.validate(authorizedContacts);
    }

    private static Collection<String> validateBillingInfo(AccountPaymentMethodDTO paymentMethodDTO,boolean isExpAccount) throws ValidationException {
        if (paymentMethodDTO == null)
            throw new ValidationException("Billing Information is required");
//        validateBillingType(billingInfoDTO.getBillingTypeDisplay());

        Collection<String> messages = new MessageList<String>();
        if (paymentMethodDTO instanceof AccountCreditCardDTO){

            AccountCreditCardDTO cc = (AccountCreditCardDTO) paymentMethodDTO;


                if (cc == null)
                    throw new ValidationException("Card Account Data is required");
                validateName(cc.getNameOnCard(), null);
                CreditCardDTO.CreditCardType cardType = cc.getCardType();
                final boolean primary = cc.isPrimary();
                if (cardType == null){
                    messages.add("Card Type (a.k.a. card code) is required");
                } else {
                    messages.add(validateCardNumber(cc.getCardNbr(), cardType, primary));
                }
                messages.addAll(validateCardExpires(cc.getCardExpires(), primary));
                //Express Account Changes
                if (isExpAccount &&  (!StringUtil.isEmpty(cc.getCountry()) && !StringUtil.isEmpty(cc.getCity()))){
                messages.addAll(validateMailingAddress(cc.getCountry(), cc.getAddress1(), cc.getAddress2(),
                        cc.getAddress3(), cc.getAddress4(), cc.getCity(), cc.getState(), cc.getZipCode(),
                        cc.getPlus4(), false));
                }else if (!isExpAccount){
                	messages.addAll(validateMailingAddress(cc.getCountry(), cc.getAddress1(), cc.getAddress2(),
                            cc.getAddress3(), cc.getAddress4(), cc.getCity(), cc.getState(), cc.getZipCode(),
                            cc.getPlus4(), false));
                }

        } else if (paymentMethodDTO instanceof AccountEFTDTO){

            AccountEFTDTO eftAcct = (AccountEFTDTO) paymentMethodDTO;
            if (eftAcct == null)
                throw new ValidationException("EFT Account Information is required");
            messages.add(validateBankAccountType(eftAcct.getAccountType()));
            messages.add(validateRoutingNumber(eftAcct.getRoutingNumber()));
            messages.add(validateBankAccountNumber(eftAcct.getAccountNumber()));
        }
        return messages;
    }

    private static Collection<String> validateAcctLoginInfo(AccountLoginDTO acctLogin) {
    	String acctID = Long.toString(acctLogin.getAcctId());
    	String acctType = acctLogin.getLoginTypeString();
    	String sessionID = acctLogin.getDbSessionId();
    	String ipAddress = acctLogin.getLastLoginIp();
    	String loginId = acctLogin.getLoginId();
    	return validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId);
    }

    /**
     * @deprecated use {@link #validateAcctLoginInfo(AccountLoginDTO)}
     * @TODO consolidate with {@link #validateAcctLoginInfo(AccountLoginDTO)} once no longer in use
     */
    @Deprecated
    private static Collection<String> validateAcctLoginInfo(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId) {
        Collection<String> messages = new MessageList<String>(4);
        if (StringUtil.isEmpty(acctID))
            messages.add("Account ID is required");
        if (StringUtil.isEmpty(acctType)) // AC is hard-coded in AccInfoChgContactInfoAction
            messages.add("Account type is required");
        if (StringUtil.isEmpty(sessionID))
            messages.add("Session ID is required");
        if (StringUtil.isEmpty(ipAddress))
            messages.add("IP Address is required");
        if (StringUtil.isEmpty(loginId))
            messages.add("Login ID is required");
        return messages;
    }

    private static Collection<String> validateMailingAddress(String country, String address1, String address2, String address3, String address4,
        String city, String state, String zip, String plus4, boolean required) {
        List<String> messages = new MessageList<String>();
        if (!required && isUSMailingAddress(country) && StringUtil.isEmpty(address1) &&
                StringUtil.isEmpty(city) && StringUtil.isEmpty(zip)){
            //Special case -- no address is being updated.  Front-end needs to enforce this where required.
            return messages;
        }

        if (isUSMailingAddress(country)) {
            messages.add(validateAddressLine(true, "Line1", address1));
            messages.add(validateAddressLine(false, "Line2", address2));
            messages.add(validateCity(city));
            messages.add(validateState(state));
            messages.add(validateZip(zip));
            messages.add(validatePlus4(plus4));
        } else {
            messages.add(validateCountry(country));
            messages.add(validateAddressLine(true, "Line1", address1));
            messages.add(validateAddressLine(false, "Line2", address2));
            messages.add(validateAddressLine(false, "Line3", address3));
            messages.add(validateAddressLine(false, "Line4", address4));
        }
        return messages;
    }

    private static String validateUserName(String userName){
        // Note, that login time user name validation is different from creation time validation
       String msg = validateString(userName, "User name", 1, MAX_USER_NAME_LENGTH, null,true);
       if (msg == null){
           msg = userName.equals(userName.toUpperCase()) == false ? "User name must be uppercase" : null;
       }
       return msg;
    }

    private static Collection<String> validateContext(String ipAddress, String sessionId, UserEnvDTO userEnvDto){
        return Arrays.asList(new String[]{
                validateIpAddress(ipAddress),
                validateSessionId(sessionId),
                userEnvDto == null ? "User Environment must not be empty" : null});
    }

    private static String validateIpAddress(String ipAddress){
        if (StringUtil.isEmpty(ipAddress)) {
            return "IP Address must not be empty";
        }
        return null;
    }

    private static String validateSessionId(String sessionId){
        if (StringUtil.isEmpty(sessionId)) {
            return "Session ID must not be empty";
        }
        return null;
    }

    private static String validateDriverLicenseNumber(String driverLicenseNumber, String driverLicenseState) {
        // from javascript
        if (StringUtil.isEmpty(driverLicenseNumber) == false) {
            // if(/^[0-9a-zA-Z]*$/.test(document.forms[0].driverLicNbr.value)==false)
            if (driverLicenseNumber.matches("^[0-9a-zA-Z]*$") == false) {
                return "DL number is invalid.";
            }
            if (StringUtil.isEmpty(driverLicenseState) == false) {
                if (driverLicenseState.toUpperCase().equals("TX")) {
                    if (driverLicenseNumber.length() != 8)
                        return "Texas DL number is invalid.";
                }
            }
        }
        return null;
    }

    private static String validatePersonalInfoDriverLicenseNumber(String driverLicenseNumber, String driverLicenseState){
        // The validation here is different from the validation in validateDriverLicenseNumber
        // This is the result that the two actions that check driver licences use different method
        // This is probably inconsistency, however at this late in the project I leave it as it is
        // - WSR 2.0.
        if (StringUtil.isEmpty(driverLicenseState))
            return null;
        if (driverLicenseState.equals("WA"))
            return null;
        if (driverLicenseState.equals("-"))
            return null;
		//D17633 07/08/2013 vgovindaswamy
       // if (StringUtil.isEmpty(driverLicenseNumber) == false) {
         //   if (driverLicenseNumber.matches("^\\d+$") == false)
           //     return "Invalid Driver License Number";
       // }
        if (driverLicenseState.equals("TX")) {
            if (driverLicenseNumber.length() != 8)
                return "Texas DL number is invalid.";
        }
        //All validations passed.
        return null;
    }

    private static String validateAccountNumber(String accountNumber){
        if (isLong(accountNumber) == false)
            return("Account number must be numeric");
        return null;
    }

    private static String validateTolltagPrefix(String tolltagPrefix) {
        for (int i = 0; i < TOLLTAG_PREFIXES.length; i++) {
            if (TOLLTAG_PREFIXES[i].equals(tolltagPrefix))
                return null;
        }
        return "Invalid tolltag prefix";
    }

    private static String validateTolltagNumber(String tolltagNumber){
        if (isLong(tolltagNumber) == false)
            return "Account number must be numeric";
        return null;
    }

    private static String validateTaxId(String taxId){
        return validateString(taxId, "Tax ID", MIN_TAX_ID_LENGTH, MAX_TAX_ID_LENGTH, null,true);
    }

    private static String validatePersonalInfoTaxId(String taxId) {
        // The reason why this exists in addition to validateTaxId is
        //   that the actions use differing validations quite inconsistently.
        // I did not want to change the existing behaviour
        if (StringUtil.isEmpty(taxId))
            return null;
        return validateString(taxId, "Tax ID", 9, 11, "^[\\d|-]{9,11}$",false); // ^[\d|-]{9,11}$
    }

    private static String validateString(String toValidate, String fieldName, int minLength, int maxLength, String validChars, boolean displayValue){
        if (StringUtil.isEmpty(toValidate))
            return fieldName + " is required.";
        if (toValidate.length() < minLength)
            return fieldName + " '" +
            (displayValue ? toValidate : "********") +
            "' too short (min is " + minLength + " characters)";
        if (toValidate.length() > maxLength)
            return fieldName + " '" +
            (displayValue ? toValidate : "********") +  "' too long (max is " + maxLength + " characters)";
        if (toValidate.matches("^[\\s]+.*$"))
            return fieldName + " '" +
            (displayValue ? toValidate : "********") +  "' can not start with white space";
        if (validChars != null && toValidate.matches(validChars) == false) // ^[0-9a-zA-Z\._\-@]*$
            return fieldName + " '" +
            (displayValue ? toValidate : "********") +  "' contains invalid characters";
        return null;
    }

      private static String validateAccountLoginId(String loginId){
          return validateString(loginId, "Login ID", MIN_LOGIN_ID_LENGTH, MAX_LOGIN_ID_LENGTH, "^[0-9a-zA-Z._@-]*$", true);
      }

    private static String validatePassword(String password,String pasRegExp){
        return validateString(password, "Password", MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, pasRegExp, false);//^[0-9a-zA-Z\._\-@]*$
    }

    private static String validateEmailAddress(String emailAddress) {
        String msg = validateString(emailAddress, "E-Mail Address", 5, MAX_EMAIL_LENGTH, null,true);
        if (msg == null && !isValidEmail(emailAddress)){
            return "Invalid E-Mail Address";
        } //else
        return msg;
    }

    private static String validateSecurityQuestionAnswer(String answer){
        //This length may be too restrictive.
        return validateString(answer, "Security Question Answer", 1, MAX_SEC_QN_ANSWER_LENGTH, "^[0-9a-zA-Z?.,\"'\\s]*$",true); // ^[0-9a-zA-Z?\.\,\"\'\s]*$
    }

    private static String validateAlternateEmail(String emailAddress){
        if (StringUtil.isEmpty(emailAddress))
            return null;
        //else
        // see /accountInformation/changeContactInfo
        String msg = validateString(emailAddress, "Alternate E-mail", 5, MAX_EMAIL_LENGTH, null,true);
        if (msg == null && !isValidEmail(emailAddress)){
            return "Invalid Alternate E-Mail Address";
        } //else
        return msg;
    }

    private static String validateEmailAddresses(String email, String alternateEmail){
        if (StringUtil.isEmpty(email))
            return null;
        if (email.equals(alternateEmail))
            return "Alternate E-mail Address cannot be the same as Email Address";
        return null;
    }

    private static String validateAccountType(String acctType) {
    	
        if (!StringUtil.isEmpty(acctType)){
        	if (BUSINESS_ACCOUNT_TYPE.equals(acctType) || PERSONAL_ACCOUNT_TYPE.equals(acctType)) {
                return null;
        	}else{
        		return "Invalid account type";
        	}
        } 
        return null;
    }

    private static String validatePlus4(String plus4) {
        if (StringUtil.isEmpty(plus4))
            return null;
        if (plus4.matches("^[0-9]{4}$") == false) //^[0-9]{4}$
            return "Plus4 of zip is invalid";
        return null;
    }

    private static String validateZip(String zip){
        if (StringUtil.isEmpty(zip))
            return "Missing zip code";
        if (zip.matches("^[0-9]{5}$") == false) //^[0-9]{5}$
            return "Invalid zip code";
        return null;
    }

    @Deprecated
    private static boolean isValidName(String name) {
        if (StringUtil.isEmpty(name))
            return false;
        return name.matches("^[0-9a-zA-Z,.'\\s-]*$"); //^[0-9a-zA-Z\-\,\.\'\s]*$
    }

    private static String validateDriverLicenseState(String driverLicenseState,boolean isExpAccount){
        if (!isExpAccount && StringUtil.isEmpty(driverLicenseState))
            return "Driver License State is required"; // can be -
        return null;
    }

    private static String validatePrimaryPhone(String phone) {
        return validateString(phone, "Primary phone", 1, MAX_PHONE_LENGTH, VALID_PHONE_PATTERN,true);
    }

    private static String validateAlternatePhone(String phone) {
        if (StringUtil.isEmpty(phone))
            return null;
        //else
        return validateString(phone, "Alternate phone", 1, MAX_PHONE_LENGTH, VALID_PHONE_PATTERN,true);
    }

    private static String validateAlternatePhoneExt(String altPhoneExt, String alternatePhone){
        if (StringUtil.isEmpty(altPhoneExt))
            return null;
        if (StringUtil.isEmpty(alternatePhone))
            return("Alternate Phone Extension cannot exist without alternate phone number");
        return validateString(altPhoneExt, "Alternate Phone Extension", 0, MAX_PHONE_EXT_LENGTH, "^\\d+$",true);
    }

    @Deprecated
    private static boolean isValidPhone(String phone) {
        if (StringUtil.isEmpty(phone))
            return false;
        // // ^\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1}[-]?\d{1,}
        return phone.matches("^\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1,}");
    }

    private static boolean isUSMailingAddress(String country) {
        return (StringUtil.isEmpty(country)
                || country.equals("USA") || country.equals("US"));
    }

    private static String validateCountry(String country) {
        if (StringUtil.isEmpty(country))
            return "Country is mandatory";
        return null;
    }

    private static String validateAddressLine(final boolean required, String line, String address){
        if (required && StringUtil.isEmpty(address)){
            return validateString(address, "Address " + line, 1, Byte.MAX_VALUE, "^[0-9a-zA-Z,#.:'\\s/-]*$",true); //^[0-9a-zA-Z\-\,#\.\:\'\s/]*$
        }
        return null;
    }

    private static String validateCity(String city) {
        if (StringUtil.isEmpty(city))
            return "City is required";
        if (city.matches("^[0-9a-zA-Z\\s/.]*$") == false) // ^[0-9a-zA-Z\s\/\.]*$
            return "Invalid city";
        return null;
    }

    private static String validateState(String state){
        if (StringUtil.isEmpty(state))
            return("State is required");
        return null;
    }

//    private static void validateBillingType(String billingType) throws EtccException {
//        if (StringUtil.isEmpty(billingType))
//            throw new EtccException("Billing Type is required");
//        if (isCreditBillingType(billingType))
//            return;
//        if (isEftBillingType(billingType))
//            return;
//        throw new EtccException("Billing Type is invalid");
//    }
//
//    private static boolean isCreditBillingType(String billingType) {
//        if (StringUtil.isEmpty(billingType))
//            return false;
//        return contains(BILLING_TYPE_CREDIT, billingType);
//    }
//
//    private static boolean isEftBillingType(String billingType) {
//        if (StringUtil.isEmpty(billingType))
//            return false;
//        return contains(BILLING_TYPE_EFT, billingType);
//    }
//
//    private static boolean contains(String[] container, String value) {
//        if (container == null)
//            return false;
//        for (int i = 0; i < container.length; i++) {
//            if (container[i] != null) {
//                if (container[i].equals(value))
//                    return true;
//            }
//        }
//        return false;
//    }

    private static String validateRoutingNumber(String routingNumber){
        return validateString(routingNumber, "Routing Number", 9, 9, "^[0-9]{9}$",true);// ^[0-9]{9}$
    }

    private static String validateBankAccountNumber(String acctNbr) {
        //Valid patterns: 99999999999, *******9999 (when masking is in use).
        return validateString(acctNbr, "Bank Account Number", 1, 17, "^\\**[0-9]{1,17}$",true);// ^[0-9]{1,17}$
    }

    /**
     * Validates that the character's in a name are valid.
     * @param name the name to validate.
     * @param order If first, middle, or last.
     * @return Error message, if any.
     */
    private static String validateName(String name, String order) {
        return validateString(name, order == null ? "Name" : order + " Name", 1, Byte.MAX_VALUE, "^[0-9a-zA-Z,.'\\s-]*$",true); //^[0-9a-zA-Z\-\,\.\'\s]*$
    }

//    private static void validateCardType(String cardType) throws EtccException {
//        if (StringUtil.isEmpty(cardType))
//            throw new EtccException("Card Type (a.k.a. card code) is required");
//        for (int i = 0; i < CARD_TYPES.length; i++) {
//            if (CARD_TYPES[i].equals(cardType))
//                return;
//        }
//        throw new EtccException("Card Type (a.k.a. card code) is invalid");
//    }

    private static String validateCardNumber(String cardNumber, CreditCardDTO.CreditCardType creditCardType, boolean primary){

        if (StringUtil.isEmpty(cardNumber)){
            return primary ? "Primary Card Number is required" : null; //Secondary card not required.
        }//else
        if (!BaseValidator.isValidCreditCard(cardNumber, creditCardType))
        	return primary ? "Primary Card Number is invalid" : "Secondary Card Number is invalid";
        //else
        return null;
    }

    private static Collection<String> validateCardExpires(String cardExpires, boolean primary) {
        if (StringUtil.isEmpty(cardExpires)){
            //Nothing to validate -- abort.
            return new MessageList<String>(Arrays.asList(new String[]{
                    primary ? "Primary Card Expires Date required" : "Secondary Card Expires Date required"}));
        }//else
        Collection<String> messages = new MessageList<String>();
        String[] monthYear = cardExpires.split("/");
        if ((monthYear == null) || (monthYear.length != 2)){
            messages.add(primary ? "Primary Card Expires Date is invalid" : "Secondary Card Expires Date is invalid");
        } else {
            int year = -1;
            try {
                year = Integer.parseInt(monthYear[1]);
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                if (year < (thisYear - 1))
                    messages.add(primary ? "Primary Card Expires Date Year is invalid" :
                        "Secondary Card Expires Date Year is invalid");
            } catch (NumberFormatException t) {
                messages.add(primary ? "Primary Card Expires Date Year is invalid" :
                        "Secondary Card Expires Date Year is invalid");
            }
            int month = -1;
            try {
                month = Integer.parseInt(monthYear[0]);
                if ((month < 1) || (month > 12))
                    messages.add(primary ? "Primary Card Expires Date Month is invalid" :
                        "Secondary Card Expires Date Month is invalid" );
            } catch (NumberFormatException t) {
                messages.add(primary ? "Primary Card Expires Date Month is invalid" :
                        "Secondary Card Expires Date Month is invalid" );
            }
        }
        return messages;
    }

    private static String validateBankAccountType(AccountEFTDTO.BankAccountType bankAccountType){
        if (bankAccountType == null)
            return "Bank account type is required";
        //else
        return null;
    }


    public static void main(String[] argv) {
        AccountValidator.testRegex();
    }

    private static void testRegex() {
        String[] driverLicenseNumbers = { "", "asdf", "1234", "12qw", "2.3" };
        for (int i = 0; i < driverLicenseNumbers.length; i++) {
            if (driverLicenseNumbers[i].matches("^[0-9a-zA-Z]*$") == false)
                logger.debug("\""+ driverLicenseNumbers[i] + "\" does not match");
            else
                logger.debug("\""+ driverLicenseNumbers[i] + "\" does match");
        }
        logger.debug("---------------------");
        for (int i = 0; i < driverLicenseNumbers.length; i++) {
            if (driverLicenseNumbers[i].matches("^\\d+$") == false)
                logger.debug("\""+ driverLicenseNumbers[i] + "\" does not match");
            else
                logger.debug("\""+ driverLicenseNumbers[i] + "\" does match");
        }
        logger.debug("---------------------");

        String[] loginIds = { ".", "-", "?", "0aA-", ".5kM", "-5kM", "@5kM_" };
        for (int i = 0; i < loginIds.length; i++) {
            if (loginIds[i].matches("^[0-9a-zA-Z._@-]*$") == false) // ^[0-9a-zA-Z\._\-@]*$
                logger.debug("\""+ loginIds[i] + "\" does not match");
            else
                logger.debug("\""+ loginIds[i] + "\" does match");
        }
        logger.debug("---------------------");
        String[] taxIds = { "", "12-", "-", "----------", "123-5678-", "123-5678-0", "123-5678-01", "123-5678-012" };
        for (int i = 0; i < taxIds.length; i++) {
            if (taxIds[i].matches("^[\\d|-]{9,11}$") == false) // ^[\d|-]{9,11}$
                 logger.debug("\""+ taxIds[i] + "\" does not match");
             else
                logger.debug("\""+ taxIds[i] + "\" does match");
        }
        logger.debug("---------------------");
        String[] plus4s = { "1234", "012", "12345", ".a" };
        for (int i = 0; i < plus4s.length; i++) {
            if (plus4s[i].matches("^[0-9]{4}$") == false) //^[0-9]{4}$
             logger.debug("\""+ plus4s[i] + "\" does not match");
            else
             logger.debug("\""+ plus4s[i] + "\" does match");
        }
        logger.debug("---------------------");
        // name.matches("^[0-9a-zA-Z,.'\\s-]*$")
        String[] names = { "5mK,. -", "\t", "al!-" };
        for (int i = 0; i < names.length; i++) {
            if (isValidName(names[i]))
                logger.debug("\"" + names[i] + "\" is valid");
            else
                logger.debug("\"" + names[i] + "\" is not valid");
        }
        logger.debug("---------------------");
        // phone.matches("^\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1}[-]?\\d{1,}");
        String[] phones = { "12", "1234567890", "12345-6789-1234", "ab" };
        for (int i = 0; i < phones.length; i++) {
            if (isValidPhone(phones[i]))
                logger.debug("\"" + phones[i] + "\" is valid");
            else
                logger.debug("\"" + phones[i] + "\" is not valid");
        }
    }

    /**
     * @see BaseValidator#isValid()
     */
    @Override
    public boolean isValid() throws ValidationException {
        // TODO Auto-generated method stub
        //
        throw new RuntimeException("isValid not implemented yet.");
        //end isValid
    }
    private static String validatePlan(String plan){
        if (StringUtil.isEmpty(plan)) {
            return "plan must not be empty";
        }
        return null;
    }
    public static void validateSetupAccountWithPlanStep1(String loginId,
            String password, String emailAddress, int securityQuestionID,
            String securityQuestionAnswer, String alternateEmail,
            String ipAddress, String sessionId,String plan,String regExpPass) throws ValidationException{

        final Collection<String> messages = new MessageList<String>(8);
        messages.add(validateAccountLoginId(loginId));
        messages.add(validatePassword(password,regExpPass));
        messages.add(validateEmailAddress(emailAddress));
        messages.add(validateSecurityQuestionAnswer(securityQuestionAnswer));
        messages.add(validateAlternateEmail(alternateEmail));
        messages.add(validateEmailAddresses(emailAddress, alternateEmail));
        messages.add(validateIpAddress(ipAddress));
        messages.add(validateSessionId(sessionId));
        messages.add(validatePlan(plan));
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }
    private static String validatePrimaryPhoneExt(String primaryPhoneExt, String primaryPhone){
        if (StringUtil.isEmpty(primaryPhoneExt))
            return null;
        if (StringUtil.isEmpty(primaryPhone))
            return("Primary Phone Extension cannot exist without Primary Phone Number");
        return validateString(primaryPhoneExt, "Primary Phone Extension", 0, MAX_PHONE_EXT_LENGTH, "^\\d+$",true);
    }
    private static String validateMobilePhoneExt(String mobilePhoneExt, String mobilePhone) {
        if (StringUtil.isEmpty(mobilePhoneExt))
            return null;
        if (StringUtil.isEmpty(mobilePhone))
            return("Mobile Phone Extension cannot exist without Mobile Phone Number");
        return validateString(mobilePhoneExt, "Mobile Phone Extension", 0, MAX_PHONE_EXT_LENGTH, "^\\d+$",true);
    }
	private static String validateMobilePhone(String mobilePhone) {
		if (StringUtil.isEmpty(mobilePhone))
			return null;
		return validateString(mobilePhone, "Mobile phone", 1, MAX_PHONE_LENGTH, VALID_PHONE_PATTERN, true);
	}
	
	/**
     * Verifies that the rebill amount For CCRMA (the amount to load the user's account to) is valid enough to submit to the
     * database.
     * @param acctID The ID of the account.
     * @param acctType The type of the account.
     * @param sessionID The ID of the Session.
     * @param ipAddress The client IP Address.
     * @param loginId The username.
     * @param rebillAmt the new rebill amount.
     * @param lowBalanceLevel the balance below which the user's account is "refreshed" or recharged.
     * @throws ValidationException thrown if any of the validation checks fail.
     */
    public static void validateUpdateRebillAmtForCCRMA(String acctID, String acctType, String sessionID,
                    String ipAddress, String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws ValidationException {
      
    	final double rebill = rebillAmt !=null ?rebillAmt.doubleValue() : 0.0;
    	final double lowBalance = lowBalanceLevel != null ? lowBalanceLevel.doubleValue() : 0.0;
    	
        final List<String> messages = new MessageList<String>();
        messages.addAll(validateAcctLoginInfo(acctID, acctType, sessionID, ipAddress, loginId));
        if ((rebill >0.0 && lowBalance>0.0) && rebill < lowBalance){
            messages.add("Rebill amount is below low balance level");
        }
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }
    
    /**
     * Validates the personal information for Account Setup.
     * @param acctLoginDto The Login DTO for the current user.
     * @param acctType The type of the account.
     * @param companyName The name of the company owning the account if a Business Account.
     * @param firstName The first name of the Account holder if a personal Account.
     * @param lastName The last name of the Account holder if a personal Account.
     * @param primaryPhone The primary contact phone number.
     * @param alternatePhone The alternate phone number, if any.
     * @param taxId The ID of the corporation if a Business account.
     * @param driverLicState The State that issued the account holder's DL.
     * @param driverLicNumber The number of the Account holder's DL.
     * @param address1 First Line of the Account Holder's Address. (required)
     * @param address2 Second Line of the Account Holder's Address.
     * @param address3 Third Line of the Account Holder's Address.
     * @param address4 Forth Line of the Account Holder's Address.
     * @param city City the account holder resides in if a US Address.
     * @param state State the account holder resides in if a US Address.
     * @param zip Account holder's zip or postal code.
     * @param country Country the Account holder resides in.
     * @param byMail If the User wishes to receives notifications by mail.
     * @param byEmail  If the User wishes to receives notifications by email.
     * @param retailTransId Not currently used.
     * @param altPhoneExt Extension for the Alternate phone number, if any.
     * @param plus4 The "Plus4" of the Zip code, if any.
     * @throws ValidationException if any of the Validations fail.
     */
     //TODO: Refactor this method
    public static void validateSetPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto,
                        String acctType, String companyName, String firstName,
                        String lastName, String primaryPhone, String alternatePhone,
                        String taxId, String driverLicState, String driverLicNumber,
                        String address1, String address2, String address3,
                        String address4, String city, String state, String zip,
                        String country, boolean byMail, boolean byEmail,
                        Long retailTransId, String altPhoneExt, String plus4,boolean isExpAccount, String primaryPhoneExt) throws ValidationException {
        if (acctLoginDto == null)
            throw new ValidationException("Account Login Information is required");

        final Collection<String> messages = new MessageList<String>(16);
        String msg = validateAccountType(acctType);
        if (msg != null){
            messages.add(msg);
        }
       /* if (PERSONAL_ACCOUNT_TYPE.equals(acctType)) {
            messages.add(validateDriverLicenseState(driverLicState,isExpAccount));
            messages.add(validatePersonalInfoDriverLicenseNumber(driverLicNumber, driverLicState));
        } else */
        if (BUSINESS_ACCOUNT_TYPE.equals(acctType)) {
            if ((StringUtil.isEmpty(companyName) == false) && (isValidName(companyName) == false))
               messages.add("Invalid Company Name");
            /*if (StringUtil.isEmpty(taxId) && StringUtil.isEmpty(driverLicNumber))
                messages.add("Either Tax ID Number -or- Driver's License Number is required");
            if (StringUtil.isEmpty(taxId) == false)
                messages.add(validatePersonalInfoTaxId(taxId));
            if (StringUtil.isEmpty(driverLicNumber) == false) {
                messages.add(validateDriverLicenseState(driverLicState,isExpAccount));
                messages.add(validatePersonalInfoDriverLicenseNumber(driverLicNumber, driverLicState));
            }*/
        } 
        if (isValidName(firstName) == false){
            messages.add("Invalid First Name");
        }
        if (isValidName(lastName) == false){
            messages.add("Invalid Last Name");
        }
        messages.add(validatePrimaryPhone(primaryPhone));
        messages.add(validatePrimaryPhoneExt(primaryPhoneExt, primaryPhone));
        messages.add(validateAlternatePhone(alternatePhone));
        messages.add(validateAlternatePhoneExt(altPhoneExt, alternatePhone));
        
        if (isExpAccount && (!StringUtil.isEmpty(country) && !StringUtil.isEmpty(state))){
		    messages.addAll(validateMailingAddress(country, address1, address2, address3, address4, city, state, zip, plus4, true));
        }else if (!isExpAccount){
        	messages.addAll(validateMailingAddress(country, address1, address2, address3, address4, city, state, zip, plus4, true));
        }
//        if (byMail == null)
//            messages.add("Mail Notification preference is required");
//        if (byEmail == null)
//            messages.add("E-Mail Notification preference is required");
        if (messages.size() > 0){
            throw new ValidationException(messages);
        }
    }
    
    
}
