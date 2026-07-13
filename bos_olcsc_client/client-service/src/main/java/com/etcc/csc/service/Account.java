/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceStatusDTO;
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
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.PersonalInfoUpdateResultDTO;
import com.etcc.csc.dto.RemoveBillingInfoResponse;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SessionDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.TransactionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.dto.ValidateDocDownloadResponse;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.validation.AccountValidator;

/**
 * Copied from com.etcc.csc.ws.account.Account in OLCSCService, then updated during task 488.
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/Account")
@StatelessDeployment(transactionTimeout=60)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Account", targetNamespace = "http://ws.csc.etcc.com/Account")
public class Account implements AccountInterface {

    //@WebMethod(operationName = "accountExists", action = "urn:accountExists")
    //@WebResult(name = "login")
    public AccountLoginDTO accountExists(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId) throws EtccException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.accountExists(accountNumber, tolltagPrefix, tolltagNumber, driverLicenseState,
                driverLicenseNumber, taxId);
    }

    //@WebMethod(operationName = "setupOnlineAccessStep1", action = "urn:setupOnlineAccessStep1")
    //@WebResult(name = "login")
    //Push Notification Phase II sourceUserName
    public AccountLoginDTO setupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {

        validateSetupOnlineAccessStep1(accountNumber, tolltagPrefix, tolltagNumber, driverLicenseState,
                driverLicenseNumber, taxId, ipAddress, sessionId, userEnvDto);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setupOnlineAccessStep1(accountNumber, tolltagPrefix, tolltagNumber, driverLicenseState,
                driverLicenseNumber, taxId, ipAddress, sessionId, userEnvDto);
    }

    //@WebMethod(operationName = "setupOnlineAccess", action = "urn:setupOnlineAccess")
    //@WebResult(name = "login")
    public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccSecurityException, EtccException {

        validateSetupOnlineAccess(acctLoginDto, oasDto);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setupOnlineAccess(acctLoginDto, oasDto);
    }

//    /**
//     * @deprecated {@link #loginAccount} takes over the function of this method.
//     **/
//    @SuppressWarnings("deprecation")
//	//@WebMethod(operationName = "validateAccount", action = "urn:validateAccount")
//    //@WebResult(name = "login")
//    @Deprecated
//    public AccountLoginDTO validateAccount(String userName,
//            @Sensitive String password, String ipAddress) throws EtccException {
//
//        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
//        return accountDao.validateAccount(userName, password, ipAddress);
//
//    }

    //@WebMethod(operationName = "loginAccount", action = "urn:loginAccount")
    //@WebResult(name = "login")
    //Push Notification Phase II sourceUserName
    public AccountLoginDTO loginAccount(String userName, @Sensitive String password, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
    	String regExpPass =new App().getRegExp("pwdsplchars");
        validateLoginAccount(userName, password, ipAddress, sessionId, userEnvDto,regExpPass);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        AccountLoginDTO acctDto = accountDao.loginAccount(userName, password, ipAddress, sessionId, userEnvDto);
        /*            if (acctDto != null) {
                    Session session = new Session();
                    SessionDTO sessionDto = new SessionDTO();
                    sessionDto.setDocumentId(acctDto.getAcctId());
                    sessionDto.setIpAddress(ipAddress);
                    sessionDto.setUserName(userName);
                    sessionDto.setDocType(Constants.LOGIN_TYPE_ACCOUNT);
                    String sessionId = session.makeSession(sessionDto);
                    acctDto.setDbSessionId(sessionId);
                }
        */
        return acctDto;
    }

    //@WebMethod(operationName = "createSession", action = "urn:createSession")
    //@WebResult(name = "login")
    public AccountLoginDTO createSession(long acctId, String ipAddress) throws EtccException {


        AccountLoginDTO result = null;
        SessionDTO sessionDto = new SessionDTO();
        sessionDto.setDocumentId(acctId);
        sessionDto.setIpAddress(ipAddress);
        sessionDto.setUserName(AccountLoginDTO.GENERIC_USER);
        sessionDto.setDocType(AccountLoginDTO.LoginType.AC.toString());
        SessionDAO sessionDao = DAOFactory.getDAOFactory().getDAO(SessionDAO.class);
        String sessionId = sessionDao.makeSession(sessionDto);
        if (sessionId != null) {
            result = new AccountLoginDTO();
            result.setAcctId(acctId);
            result.setLastLoginIp(ipAddress);
            result.setLoginId(AccountLoginDTO.GENERIC_USER);
            result.setLoginType(AccountLoginDTO.LoginType.AC);
            result.setDbSessionId(sessionId);
        }
        return result;
    }

    //@WebMethod(operationName = "getAccount", action = "urn:getAccount")
    //@WebResult(name = "account")
    public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getAccount(acctLoginDto);
    }

    //@WebMethod(operationName = "getAccountTags", action = "urn:getAccountTags")
    //@WebResult(name = "tags")
    public TagDTO[] getAccountTags(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getAccountTags(acctLoginDto);
    }

    //@WebMethod(operationName = "updateAccountTags", action = "urn:updateAccountTags")
    //@WebResult(name = "updateResult")
    public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto, TagDTO[] tags, boolean checkDuplicate)
            throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateAccountTags(acctLoginDto, tags, checkDuplicate);
    }

    //@WebMethod(operationName = "getUserPreference", action = "urn:getUserPreference")
    //@WebResult(name = "userPreference")
    public UserPreferenceResultDTO getUserPreference(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getUserPreference(acctLoginDto);
    }

    //@WebMethod(operationName = "getTollTagUserPreference", action = "urn:getTollTagUserPreference")
    //@WebResult(name = "userPreference")
    public UserPreferenceResultDTO getTollTagUserPreference() throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getTollTagUserPreference();
    }

    //@WebMethod(operationName = "updateUserPreference", action = "urn:updateUserPreference")
    //@WebResult(name = "errors")
    public ErrorMessageDTO[] updateUserPreference(AccountLoginDTO acctLoginDto, AccountIopDTO[] acctIops,
            UserPreferenceDTO[] userPrefs) throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateUserPreference(acctLoginDto, acctIops, userPrefs);
    }

    //@WebMethod(operationName = "updateAccount", action = "urn:updateAccount")
    //@WebResult(name = "errors")
    public ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto, AccountDTO acctDto) throws EtccException,
            EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateAccount(acctLoginDto, acctDto);
    }

    //@WebMethod(operationName = "getLastTransactions", action = "urn:getLastTransactions")
    //@WebResult(name = "transactions")
    public TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getLastTransactions(acctLoginDto);
    }

    //@WebMethod(operationName = "getAlerts", action = "urn:getAlerts")
    //@WebResult(name = "alerts")
    public AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getAlerts(acctLoginDto);
    }

    //@WebMethod(operationName = "isPaymentOwed", action = "urn:isPaymentOwed")
    //@WebResult(name = "paymentOwed")
    public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId, TagDTO[] licensePlates)
            throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.isPaymentOwed(acctLoginDto, transId, licensePlates);
    }

    //@WebMethod(operationName = "isBigAccount", action = "urn:isBigAccount")
    //@WebResult(name = "bigAccount")
    public boolean isBigAccount(long accountId) throws EtccException{
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.isBigAccount(accountId);
    }

    //@WebMethod(operationName = "setupAccountStep1", action = "urn:setupAccountStep1")
    //@WebResult(name = "login")
    //Push Notification Phase II sourceUserName
    public AccountLoginDTO setupAccountStep1(String loginId,
            @Sensitive String password,
            String emailAddress,
            @Sensitive(displayValue = "<QuestionID>") int securityQuestionID,
            @Sensitive(displayValue = "<SecurityAnswer>") String securityQuestionAnswer,
            String alternateEmail, String ipAddress, String sessionID, UserEnvDTO userEnvDto) throws EtccException {
    	String regExpPass =new App().getRegExp("pwdsplchars");
        validateSetupAccountStep1(loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer,
                alternateEmail, ipAddress, sessionID,regExpPass);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setupAccountStep1(loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, ipAddress, sessionID, userEnvDto);
    }

    //@WebMethod(operationName = "updateLoginId", action = "urn:updateLoginId")
    //@WebResult(name = "login")
    public AccountLoginDTO updateLoginId(String sessionID, String ipAddress, String loginId, String oldLoginId,
            @Sensitive String password, String acctID) throws EtccException, EtccSecurityException {
    	String regExpPass =new App().getRegExp("pwdsplchars");
        validateUpdateLoginId(sessionID, ipAddress, loginId, oldLoginId, password, acctID,regExpPass);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateLoginId(sessionID, ipAddress, loginId, oldLoginId, password, acctID);
    }

    @SuppressWarnings("deprecation")
	@Deprecated
	//@WebMethod(operationName = "updatePassword", action = "urn:updatePassword")
    //@WebResult(name = "login")
    public AccountLoginDTO updatePassword(String sessionID, String ipAddress, String loginId,
            @Sensitive(displayValue="<OldPassword>") String oldPassword,
            @Sensitive String password, String acctID) throws EtccException, EtccSecurityException {

    	AccountValidator.validateUpdatePassword(sessionID, ipAddress, loginId, oldPassword, password, acctID);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updatePassword(sessionID, ipAddress, loginId, oldPassword, password, acctID);
    }

    //@WebMethod(operationName = "setPersonalInfo", action = "urn:setPersonalInfo")
    //@WebResult(name = "infoUpdateResult")
    public PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4)
            throws EtccException, EtccSecurityException {
    	//Express AccountChnages
        boolean isExpAccount =new App().isExpressAccount(acctLoginDto.getAcctId());
    	AccountValidator.validateSetPersonalInfo(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
                alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3, address4,
                city, state, zip, country, byMail, byEmail, retailTransId, altPhoneExt, plus4,isExpAccount);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setPersonalInfo(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
                alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3, address4,
                city, state, zip, country, byMail, byEmail, byPhone, retailTransId, altPhoneExt, plus4);
    }

    //@WebMethod(operationName = "updateLoginInfo", action = "urn:updateLoginInfo")
    //@WebResult(name = "dto")
    public ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto, String loginId,
            @Sensitive String password,
            String emailAddress,
            @Sensitive(displayValue = "<QuestionID>") int securityQuestionID,
            @Sensitive(displayValue = "<SecurityAnswer>") String securityQuestionAnswer,
            String alternateEmail, BaseContactInfo contactInfo)
            throws EtccException, EtccSecurityException {

        validateUpdateLoginInfo(acctLoginDto, loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, contactInfo);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateLoginInfo(acctLoginDto, loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, contactInfo);
    }

    @SuppressWarnings("deprecation")
	@Deprecated
	//@WebMethod(operationName = "updateSecQn", action = "urn:updateSecQn")
    //@WebResult(name = "login")
    public AccountLoginDTO updateSecQn(String sessionID, String ipAddress, String loginId,
            @Sensitive String password,
            String acctID,
            @Sensitive(displayValue = "<QuestionID>") int securityQnID,
            @Sensitive(displayValue = "<SecurityAnswer>") String securityAns) throws EtccException, EtccSecurityException {

    	AccountValidator.validateUpdateSecQn(sessionID, ipAddress, loginId, password, acctID, securityQnID, securityAns);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateSecQn(sessionID, ipAddress, loginId, password, acctID, securityQnID, securityAns);
    }

    //@WebMethod(operationName = "updateContactInfo", action = "urn:updateContactInfo")
    //@WebResult(name = "login")
    public AccountLoginDTO updateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
            String alternatePhoneExt) throws EtccSecurityException, EtccException {

        validateUpdateContactInfo(acctID, acctType, sessionID, ipAddress, loginId, emailAddress, altEmailAddress,
                primaryPhone, alternatePhone, alternatePhoneExt);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateContactInfo(acctID, acctType, sessionID, ipAddress, loginId, emailAddress,
                altEmailAddress, primaryPhone, alternatePhone, alternatePhoneExt);
    }

    //@WebMethod(operationName = "setBillingInfo", action = "urn:setBillingInfo")
    //@WebResult(name = "billingInfo")
    public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO)
            throws EtccException, EtccSecurityException {

    	
    	PaymentType paymentType = billingInfoDTO.getBillingType();
    	AccountPaymentMethodDTO paymentMethodDTO = null;
    	String tokenFromPA = null;
    	//Express AccountChnages
        boolean isExpAccount =new App().isExpressAccount(acctLoginDto.getAcctId());
	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
	 	{
	 		AccountCreditCardDTO[] cards = billingInfoDTO.getCards();
	 		if ( cards != null && cards.length > 0 ){
	 			for (  AccountCreditCardDTO billingInfoCard : cards  )
 				{
	 			  // set token if required.
	 				validateSetBillingInfo(acctLoginDto, billingInfoCard,isExpAccount);
	 		 		CCTokenDAOHelper.getInstance().getCCToken(acctLoginDto, billingInfoCard);	
 				}
	 		}	
	 		
	 	}
        else{
        	paymentMethodDTO = billingInfoDTO.getEft();
        	validateSetBillingInfo(acctLoginDto, paymentMethodDTO,isExpAccount);
        }
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        
        return accountDao.setBillingInfo(acctLoginDto, billingInfoDTO);
    }
	/*public BillingInfoResultDTO setBillingInfoCreditCard(
			AccountLoginDTO acctLoginDto, AccountCreditCardDTO paymentMethodDto)
			throws EtccException, EtccSecurityException {
		 validateSetBillingInfo(acctLoginDto, paymentMethodDto);
	        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
	        return accountDao.setBillingInfo(acctLoginDto, paymentMethodDto);
	}
	public BillingInfoResultDTO setBillingInfoEFT(AccountLoginDTO acctLoginDto,
			AccountEFTDTO paymentMethodDto) throws EtccException,
			EtccSecurityException {
		 	validateSetBillingInfo(acctLoginDto, paymentMethodDto);
	        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
	        return accountDao.setBillingInfo(acctLoginDto, paymentMethodDto);
	}*/

	
    
    //@WebMethod(operationName = "makePayment", action = "urn:makePayment")
    //@WebResult(name = "dto")
  /*  public ResultDTO makePayment(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO paymentMethodDTO, String xmlPosting, String emailAddress)
            throws EtccException, EtccSecurityException {

        //validateMakePayment(acctLoginDto, billingInfoDTO, paymentAmt);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.makePayment(acctLoginDto, paymentMethodDTO, xmlPosting, emailAddress);
    }*/
    
    
    
   
	
	
	 public ResultDTO makePayment(AccountLoginDTO loginDTO,
	    		BillingInfoDTO billingInfoDTO, Double rebillAmount,
	    		Double lowBalanceAmount, List<TagDTO> tagList, double paymentAmt)
	    		throws EtccException, EtccSecurityException {
	    	
		 /*	PaymentType paymentType = billingInfoDTO.getBillingType();
		 	AccountPaymentMethodDTO accountPaymentMethodDTO = null;
		 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
		 			accountPaymentMethodDTO = billingInfoDTO.getCards()[0];
	        else
	        	accountPaymentMethodDTO = billingInfoDTO.getEft();
		 */
		 
	    /*	String xmlPosting = createAccountPaymentXML(loginDTO, accountPaymentMethodDTO,
					rebillAmount, depAmount, tagList, paymentAmt);*/
	        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
	        return accountDao.makePayment(loginDTO, billingInfoDTO, rebillAmount, lowBalanceAmount, tagList, paymentAmt);
	    }

   /* public ResultDTO makeAccountPaymentEFT(AccountLoginDTO loginDTO,
    		AccountEFTDTO primaryCard, Double rebillAmount,
    		Double depAmount, List<TagDTO> tagList, double paymentAmt)
    		throws EtccException, EtccSecurityException {
    	
    	String xmlPosting = createAccountPaymentXML(loginDTO, primaryCard,
				rebillAmount, depAmount, tagList, paymentAmt);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.makePayment(loginDTO, primaryCard, xmlPosting, null);
    }
    
    public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO,
    		AccountCreditCardDTO primaryCard, Double rebillAmount,
    		Double depAmount, List<TagDTO> tagList, double paymentAmt)
    		throws EtccException, EtccSecurityException {
    	String xmlPosting = createAccountPaymentXML(loginDTO, primaryCard,
				rebillAmount, depAmount, tagList, paymentAmt);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.makePayment(loginDTO, primaryCard, xmlPosting, null);
    }*/
   
    //@WebMethod(operationName = "getBillingInfo", action = "urn:getBillingInfo")
    //@WebResult(name = "billingInfo")
    public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getBillingInfo(acctLoginDto);
    }

    //@WebMethod(operationName = "updateMailingAddr", action = "urn:updateMailingAddress")
    //@WebResult(name = "login")
    public AccountLoginDTO updateMailingAddress(AccountLoginDTO acctLogin, Address address) throws EtccSecurityException, EtccException {
        validateUpdateMailingAddress(acctLogin, address);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateMailingAddress(acctLogin, address);
    }

    @Deprecated
    //@WebMethod(operationName = "updateMailingAddr", action = "urn:updateMailingAddrStrings")
    //@WebResult(name = "login")
    public AccountLoginDTO updateMailingAddr(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String country, String zip, String plus4) throws EtccSecurityException, EtccException {

        validateUpdateMailingAddr(acctID, acctType, sessionID, ipAddress, loginId, address1, address2, address3,
                address4, city, state, country, zip, plus4);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateMailingAddr(acctID, acctType, sessionID, ipAddress, loginId, address1, address2,
                address3, address4, city, state, country, zip, plus4);
    }

    //@WebMethod(operationName = "updateBillingInfo", action = "urn:updateBillingInfo")
    //@WebResult(name = "login")
    public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfo) throws EtccSecurityException, EtccException {
    	
    	PaymentType paymentType = billingInfo.getBillingType();
    	AccountPaymentMethodDTO paymentMethodDto = null;
    	 boolean isExpAccount =new App().isExpressAccount(acctLogin.getAcctId());
    	// Credit -- validate and generate tocken for credit cards 
	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
	 	{
	 		AccountCreditCardDTO[] cards = billingInfo.getCards();
			if ( cards != null && cards.length > 0 )
 			{
 				for (  AccountCreditCardDTO billingInfoCard : cards  )
 				{
 					AccountValidator.validateUpdateBillingInfo(acctLogin, billingInfoCard,isExpAccount);
 					// set token if required.
 					// if a paypage registration id is sent  , a new taken should be generated.
 					CCTokenDAOHelper.getInstance().getCCToken(acctLogin, billingInfoCard);
 				}
	 	    }
	 	}
		// EFT -- validate bank account info 
        else
        {
        	paymentMethodDto = billingInfo.getEft();
        	AccountValidator.validateUpdateBillingInfo(acctLogin, paymentMethodDto,isExpAccount);
        }
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateBillingInfo(acctLogin, billingInfo);
    }

    //@WebMethod(operationName = "updateBillingAddress", action = "urn:updateBillingAddress")
    //@WebResult(name = "login")
    public AccountLoginDTO updateBillingAddress(AccountLoginDTO acctLogin, Address address)
            throws EtccException, EtccSecurityException {
        validateUpdateBillingAddress(acctLogin, address);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateBillingAddress(acctLogin, address);
    }

    @Deprecated
    //@WebMethod(operationName = "updateBillingAddressStrings", action = "urn:updateBillingAddressStrings")
    //@WebResult(name = "login")
    public AccountLoginDTO updateBillingAddress(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String zip, String plus4, String country) throws EtccSecurityException, EtccException {

        validateUpdateBillingAddress(acctID, acctType, sessionID, ipAddress, loginId, address1, address2, address3,
                address4, city, state, zip, plus4, country);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateBillingAddress(acctID, acctType, sessionID, ipAddress, loginId, address1, address2,
                address3, address4, city, state, zip, plus4, country);
    }

    //@WebMethod(operationName = "updateRebillAmt", action = "urn:updateRebillAmt")
    //@WebResult(name = "login")
    public AccountLoginDTO updateRebillAmt(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccSecurityException, EtccException {

        validateUpdateRebillAmt(acctID, acctType, sessionID, ipAddress, loginId, rebillAmt, lowBalanceLevel);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updateRebillAmt(acctID, acctType, sessionID, ipAddress, loginId, rebillAmt,
                lowBalanceLevel);
    }

    //@WebMethod(operationName = "getAccountStatus", action = "urn:getAccountStatus")
    //@WebResult(name = "account")
    public AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto, AccountDTO accountDTO)
            throws EtccSecurityException, EtccException {

        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.getAccountStatus(acctLoginDto, accountDTO);
    }

    //@WebMethod(operationName = "updatePwEmailSq", action = "urn:updatePwEmailSq")
    //@WebResult(name = "dto")
    public ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto, int updateFlags, String oldPw, String newPw, String emailAddress,
            int sQuestionID, String sAnswer) throws EtccSecurityException, EtccException {
    	String regExpPass =new App().getRegExp("pwdsplchars");
        AccountValidator.validatePasswordEmailSecurity(acctLoginDto, updateFlags, oldPw, newPw, emailAddress, sQuestionID, sAnswer,regExpPass);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.updatePasswordEmailSecurityQA(acctLoginDto, updateFlags, oldPw, newPw, emailAddress, sQuestionID, sAnswer);
    }

    // -- validation wrappers
    private static void validateLoginAccount(String userName, String password, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto,String regExpPass) throws EtccException {
        AccountValidator.validateLoginAccount(userName, password, ipAddress, sessionId, userEnvDto,regExpPass);
    }

    private static void validateSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
        AccountValidator.validateSetupOnlineAccessStep1(accountNumber, tolltagPrefix, tolltagNumber,
                driverLicenseState, driverLicenseNumber, taxId, ipAddress, sessionId, userEnvDto);
    }

    private static void validateSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccException {
        AccountValidator.validateSetupOnlineAccess(acctLoginDto, oasDto);
    }

    private static void validateSetupAccountStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,String regExpPass) throws EtccException {

        AccountValidator.validateSetupAccountStep1(loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, ipAddress, sessionID,regExpPass);
    }

    private static void validateUpdateLoginId(String sessionID, String ipAddress, String loginId, String oldLoginId,
            String password, String acctID,String regExpPass) throws EtccException {

        AccountValidator.validateUpdateLoginId(sessionID, ipAddress, loginId, oldLoginId, password, acctID,regExpPass);
    }

    private static void validateUpdateLoginInfo(AccountLoginDTO acctLoginDto, String loginId, String password,
            String emailAddress, int securityQuestionID, String securityQuestionAnswer, String alternateEmail,
            BaseContactInfo contactInfo)
            throws EtccException {
        if (contactInfo == null)
        AccountValidator.validateUpdateLoginInfo(acctLoginDto, loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail);
        else
        AccountValidator.validateUpdateLoginInfo(acctLoginDto, loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, contactInfo.getFirstName(), contactInfo.getLastName());
    }

    private static void validateUpdateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
            String alternatePhoneExt) throws EtccException {
        AccountValidator.validateUpdateContactInfo(acctID, acctType, sessionID, ipAddress, loginId, emailAddress,
                altEmailAddress, primaryPhone, alternatePhone, alternatePhoneExt);
    }

    private static void validateSetBillingInfo(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO billingInfoDTO,boolean isExpAccount)
            throws EtccException {
        AccountValidator.validateSetBillingInfo(acctLoginDto, billingInfoDTO,isExpAccount);
    }

    private static void validateMakePayment(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO billingInfoDTO, double paymentAmt,boolean isExpAccount)
            throws EtccException {
        AccountValidator.validateMakePayment(acctLoginDto, billingInfoDTO, paymentAmt,isExpAccount);
    }

    private static void validateUpdateMailingAddress(AccountLoginDTO acctLogin, Address address) throws EtccException {
        AccountValidator.validateUpdateMailingAddr(
                Long.toString(acctLogin.getAcctId()),
                AccountLoginDTO.LoginType.AC.toString(),
                acctLogin.getDbSessionId(),
                acctLogin.getLastLoginIp(),
                acctLogin.getLoginId(),
                address.getAddress1(),
                address.getAddress2(),
                address.getAddress3(),
                address.getAddress4(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZip(),
                address.getPlus4());
    }

    private static void validateUpdateMailingAddr(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String country, String zip, String plus4) throws EtccException {
        AccountValidator.validateUpdateMailingAddr(acctID, acctType, sessionID, ipAddress, loginId, address1, address2,
                address3, address4, city, state, country, zip, plus4);
    }

    private void validateUpdateBillingAddress(AccountLoginDTO acctLogin, Address address) throws EtccException {
        AccountValidator.validateUpdateBillingAddress(Long.toString(acctLogin.getAcctId()), "AC",
                acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(),
                acctLogin.getLoginId(), address.getAddress1(),
                address.getAddress2(), address.getAddress3(),
                address.getAddress4(), address.getCity(),
                address.getState(), address.getZip(), address.getPlus4(), address.getCountry());
    }

    private static void validateUpdateBillingAddress(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String zip, String plus4, String country) throws EtccException {
        AccountValidator.validateUpdateBillingAddress(acctID, acctType, sessionID, ipAddress, loginId, address1,
                address2, address3, address4, city, state, zip, plus4, country);
    }

	private static void validateUpdateRebillAmt(String acctID, String acctType, String sessionID, String ipAddress,
			String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccException {
		AccountValidator.validateUpdateRebillAmt(acctID, acctType, sessionID, ipAddress, loginId, rebillAmt,
				lowBalanceLevel);
	}

	public ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto,
			double activationFee, List<TagDTO> tagDTOSs) throws EtccException, EtccSecurityException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.sendWelcomeNotification(acctLoginDto, activationFee, tagDTOSs) ;
	}
	
	//defect 9064 txphung beign
	public ResultDTO validateRoutingNumber(String routingNumber)
			throws EtccException, EtccSecurityException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.validateRoutingNumber(routingNumber);
	}

	//defect 9064 txphung end 
	//Express Enh Account changes: added new method to create account
	public AccountLoginDTO setupAccountWithPlanStep1(String loginId,
            @Sensitive String password,
            String emailAddress,
            @Sensitive(displayValue = "<QuestionID>") int securityQuestionID,
            @Sensitive(displayValue = "<SecurityAnswer>") String securityQuestionAnswer,
            String alternateEmail, String ipAddress, String sessionID, UserEnvDTO userEnvDto,String plan) throws EtccException {

        validateSetupAccountWithPlanStep1(loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer,
                alternateEmail, ipAddress, sessionID,plan);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setupAccountWithPlanStep1(loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, ipAddress, sessionID, userEnvDto,plan);
    }
	
	private static void validateSetupAccountWithPlanStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,String plan) throws EtccException {
		String regExpPass =new App().getRegExp("pwdsplchars");
        AccountValidator.validateSetupAccountWithPlanStep1(loginId, password, emailAddress, securityQuestionID,
                securityQuestionAnswer, alternateEmail, ipAddress, sessionID,plan,regExpPass);
    }
	public AccountDeviceStatusDTO getAccountDeviceStatus(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId, String fingerprintId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAccountDeviceStatus(acctId, acctType, dbSessionId, ipAddress, loginId, fingerprintId);
	}

	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.saveAccountDevice(acctId, acctType, dbSessionId, ipAddress, loginId, fingerprintId);
	}

	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.validateSecurityAnswer(acctId, acctType, dbSessionId, ipAddress, loginId, securityAnswer);
	}
	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAuthenticatedSessionid(acctId, acctType, dbSessionId, ipAddress, loginId);
	}
	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAcctDetailsTwoFa(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action,  String documentType,
			String description,Long accountDocumentId)
			throws EtccException {
		DocumentUploadResponse documentUploadResponse = new DocumentUploadResponse();
		documentUploadResponse.setMessage("Method not implemented");
		documentUploadResponse.setValid(false);
		return documentUploadResponse;
	}

	public GetAccountDocumentResponse getAccountDocument(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAccountDocument(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public GetAccountNotificationsDocumentResponse getAcctNotificationDocuments(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAcctNotificationDocuments(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.removeBillingInfo(acctLoginDto, billingInfoDTO);
	}

	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getAcctountPhoneInfo(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public ResultDTO setContactInfoByContactId(BigDecimal acctId, String docType, String sessionId, String ipAddress,
			String userId, String emailAddress, String altEmail, String homePhoNbr, String workPhoNbr,
			String workPhoExt, String homePhoExt, String MobilePhone, String mobilePhoExt, String smsAlertsOptIn,
			BigDecimal homePhoneId, BigDecimal workPhoneId, BigDecimal mobilePhoneId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.setContactInfoByContactId(acctId, docType, sessionId, ipAddress, userId, emailAddress,
				altEmail, homePhoNbr, workPhoNbr, workPhoExt, homePhoExt, MobilePhone, mobilePhoExt, smsAlertsOptIn,
				homePhoneId, workPhoneId, mobilePhoneId);
	}

	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId, BigDecimal acctTypeId, BigDecimal acctPlanId,
			BigDecimal planDetailId, BigDecimal paymentFormId, BigDecimal vehicleCount) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.getInitialAutochargeAmounts(acctId, acctType, dbSessionId, ipAddress, loginId, acctTypeId,
				acctPlanId, planDetailId, paymentFormId, vehicleCount);
	}

	public ValidateDocDownloadResponse validateDocDownload(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.validateDocDownload(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException {
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.setupOnlineAccessStep1ForCCRMA(accountNumber, tolltagPrefix, tolltagNumber, emailAddress,
				phoneNumber, ipAddress, jsessionId, userEnvDto);
	}
	
	public AccountLoginDTO ccrmaSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException {
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		return accountDao.ccrmaSetupOnlineAccessStep1(accountNumber, tolltagPrefix, tolltagNumber, emailAddress,
				phoneNumber, ipAddress, jsessionId, userEnvDto);
	}
	
    public AccountLoginDTO ccrmaSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccSecurityException, EtccException {

        validateSetupOnlineAccess(acctLoginDto, oasDto);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.ccrmaSetupOnlineAccess(acctLoginDto, oasDto);
    }
    
    public PersonalInfoUpdateResultDTO setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4, String primaryPhoneExt, String smsAlertsOptIn)
            throws EtccException, EtccSecurityException {
    	//Express AccountChnages
        boolean isExpAccount =new App().isExpressAccount(acctLoginDto.getAcctId());
    	AccountValidator.validateSetPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
                alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3, address4,
                city, state, zip, country, byMail, byEmail, retailTransId, altPhoneExt, plus4,isExpAccount, primaryPhoneExt);
        AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
        return accountDao.setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
                alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3, address4,
                city, state, zip, country, byMail, byEmail, byPhone, retailTransId, altPhoneExt, plus4, primaryPhoneExt, smsAlertsOptIn);
    }

}
