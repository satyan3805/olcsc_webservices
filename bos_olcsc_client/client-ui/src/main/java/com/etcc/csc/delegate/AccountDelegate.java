/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.math.BigDecimal;
import java.util.List;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
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
import com.etcc.csc.presentation.EtccActionException;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.DtoUtil;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;
import com.sun.istack.Nullable;

/**
 * UI Wrapper for the Account Service Implementation.
 * @author akoladia
 * @author Tamas Szundy
 * @author Lakshmi Harish
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AccountDelegate implements AccountInterface {
    private static final Logger logger = Logger.getLogger(AccountDelegate.class);

    public static final String LOGIN_RESULT_SUCCESS = "success";
    public static final String LOGIN_RESULT_FAILURE = "failure";
    public static final String LOGIN_RESULT_INVALID = "invalidAccount";
    public static final String LOGIN_RESULT_CHANGE_PASS = "changePassword";
    public static final String LOGIN_RESULT_SIGNUP_INCOMPLETE = "signupNotComplete";

    public AccountLoginDTO accountExists(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId) throws EtccException {

        AccountLoginDTO loginDto = stub().accountExists(
                accountNumber, tolltagPrefix, tolltagNumber, driverLicenseState, driverLicenseNumber, taxId);
        return loginDto;
    }

    public AccountLoginDTO setupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, String driverLicenseNumber, String taxId, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {

        AccountLoginDTO loginDto = stub().setupOnlineAccessStep1(
                accountNumber, tolltagPrefix, tolltagNumber, driverLicenseState, driverLicenseNumber, taxId, ipAddress,
                sessionId, userEnvDto);
        return loginDto;
    }

    public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccSecurityException, EtccException {
        return stub().setupOnlineAccess(acctLoginDto, oasDto);
    }

//    public AccountLoginDTO validateAccount(String userName, String password, String ipAddress) throws EtccException {
//        AccountLoginDTO loginDto = stub().validateAccount(userName, password, ipAddress);
//        return loginDto;
//    }

    /**
     * Verifies the account login returning a string value that represents the result.
     * Note: possible return values are <i>success</i>, <i>failure</i>,
     * <i>changePassword</i> and <i>signupNotComplete</i>.
     * This method only performs presentation-layer logic.
     * @param acctLogin the account login object
     * @return string specifying the result of the verification
     * @throws EtccActionException when the account is invalid
     * @throws EtccException if <tt>getAcctDTO</tt> throws an exception
     * @throws EtccSecurityException if <tt>getAcctDTO</tt> throws an exception
     * @see SessionUtil#getAcctDTO
     * @author Milosh Boroyevich
     */
    // Extracted from AuthenticateUserAction
    public String verifyLogin(AccountLoginDTO acctLogin, HttpServletRequest request, HttpServletResponse response) throws EtccActionException, EtccSecurityException, EtccException {
//    	HttpServletRequest request = ServletActionContext.getRequest();
//    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpSession session = request.getSession();
        String result = LOGIN_RESULT_FAILURE;
    	logger.info("***login action.acctid=" + acctLogin.getAcctId());
    	logger.info("***login action.errors=" + acctLogin.getErrors());
    	if (acctLogin.getAcctId() == 0 || acctLogin.getErrors() != null) {
    		//make sure the tab menu is at the right position
    		String label = new AppDelegate().getMyEZTAGMenuLabel();
    		int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
    		SessionUtil.setCurrentTabMenuId(session,  menuId);
    		if (!acctLogin.isLocked()){
    			throw new EtccActionException(LOGIN_RESULT_INVALID);
    		} else {
    			ActionMessage msg = DtoUtil.createActionMessage("login.accountLocked.error", new AppDelegate().getContactPhoneNumber(), request.getContextPath()+"/home/resetPassword.do");
    			throw new EtccActionException(LOGIN_RESULT_INVALID, msg);
    		}
    	} else {
    		logger.info("Login verified: " + acctLogin);
    		if (acctLogin.isPwChangeRequired()) {
    			result = LOGIN_RESULT_CHANGE_PASS;
    		} else {
    			//Begin Pending to Close and Close acct
    			if ( "C".equalsIgnoreCase(acctLogin.getAcctActivity( ) ) ) {
    				result = LOGIN_RESULT_FAILURE;
    			} else {
    				//Ending Pending to close
    				if ( "P".equalsIgnoreCase(acctLogin.getAcctActivity( ) ) ) {
    					result = LOGIN_RESULT_SIGNUP_INCOMPLETE;
    				} else {
    					result = LOGIN_RESULT_SUCCESS;
    				}
    			}//closed acct
    		}
    		acctLogin.setLastLoginIp(HttpDataUtil.getClientIpAddress( request ));
    		SessionUtil.setSessionAccountLogin(session, acctLogin);
    		HttpDataUtil.setDbSessionIdCookie(response, acctLogin.getDbSessionId());
    	}
    	return result;
    }

    /**
     * Convenience method which extracts data from the request before performing account login.
     * @param userName
     * @param password
     * @param request
     * @return
     * @throws EtccException
     * @see #loginAccount(String, String, String, String, UserEnvDTO)
     */
	public AccountLoginDTO loginAccount(String userName, String password, HttpServletRequest request) throws EtccException {
//    	HttpServletRequest request = ServletActionContext.getRequest();
        String ipAddress = HttpDataUtil.getClientIpAddress(request);
		String sessionId = request.getSession().getId();
        UserEnvDTO userEnvDto = HttpDataUtil.getUserAgentAttributes(request);
		return this.loginAccount(userName, password, ipAddress, sessionId , userEnvDto);
	}

	public AccountLoginDTO loginAccount(String userName, String password, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
		return stub().loginAccount(userName == null ? null : userName.toUpperCase(),
						password, ipAddress, sessionId, userEnvDto);
    }

    public AccountLoginDTO createSession(long acctId, String ipAddress) throws EtccException {
        AccountLoginDTO loginDto = stub().createSession(acctId, ipAddress);
        return loginDto;
    }

    /**
     * Login an account and return the corresponding account.
     * @param acctLoginDto account login object to use for creating the corresponding account
     * @param acctId <i>ignored</i>
     * @return the account corresponding to the account login
     * @deprecated use {@link #getAccount(AccountLoginDTO)} instead
     */
    @Deprecated
    public AccountDTO getAccount(AccountLoginDTO acctLoginDto, long acctId) throws EtccException, EtccSecurityException {
    	return getAccount(acctLoginDto);
    }

    public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
    	return stub().getAccount(acctLoginDto);
    }

    public TagDTO[] getAccountTags(AccountLoginDTO acctLoginDto) throws EtccSecurityException, EtccException {
        return stub().getAccountTags(acctLoginDto);
    }

    public UserPreferenceResultDTO getUserPreference(AccountLoginDTO acctLoginDto) throws EtccSecurityException, EtccException {
    	UserPreferenceResultDTO upDto = stub().getUserPreference(acctLoginDto);
    	return upDto;
    }

    public UserPreferenceResultDTO getTollTagUserPreference() throws EtccSecurityException, EtccException {
    	UserPreferenceResultDTO uprDto = stub().getTollTagUserPreference();
    	return uprDto;
    }

    public ErrorMessageDTO[] updateUserPreference(AccountLoginDTO acctLoginDto, AccountIopDTO[] acctIops,
    		UserPreferenceDTO[] userPrefs) throws EtccSecurityException, EtccException {
    	return stub().updateUserPreference(acctLoginDto, acctIops, userPrefs);
    }

    public ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto, AccountDTO acctDto) throws EtccSecurityException, EtccException {
    	return stub().updateAccount(acctLoginDto, acctDto);
    }

    public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto, TagDTO[] tags, boolean checkDuplicate)
            throws EtccSecurityException, EtccException {
    	TagUpdateResultDTO updatedTags = stub().updateAccountTags(acctLoginDto, tags, checkDuplicate);
    	return updatedTags;
   }

    public TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto) throws EtccSecurityException, EtccException {
    	return stub().getLastTransactions(acctLoginDto);
    }

    public AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto) throws EtccSecurityException, EtccException {
    	return stub().getAlerts(acctLoginDto);
    }

    public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId, TagDTO[] licPlates) throws EtccException,
            EtccSecurityException {
    	return stub().isPaymentOwed(acctLoginDto, transId, licPlates);
    }

    public boolean isBigAccount(long accountId) throws EtccException {
    	return stub().isBigAccount(accountId);
    }

    public AccountLoginDTO setupAccountStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,UserEnvDTO userEnvDto) throws EtccException {
    	AccountLoginDTO loginDto = stub().setupAccountStep1(
    			loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer, alternateEmail,
    			ipAddress, sessionID,userEnvDto);
    	return loginDto;
    }

    public AccountLoginDTO updateLoginId(String sessionID, String ipAddress, String loginId, String oldLoginId,
            String password, String acctID) throws EtccException, EtccSecurityException {
    	AccountLoginDTO loginDto = stub().updateLoginId(
    			sessionID, ipAddress, loginId, oldLoginId, password, acctID);
    	return loginDto;
    }

    @SuppressWarnings("deprecation")
	@Deprecated
	public AccountLoginDTO updatePassword(String sessionID, String ipAddress, String loginId, String oldPassword,
            String password, String acctID) throws EtccException, EtccSecurityException {
    	AccountLoginDTO loginDto = stub().updatePassword(
    			sessionID, ipAddress, loginId, oldPassword, password, acctID);
    	return loginDto;
    }

    /**
     * @see #setPersonalInfo(AccountLoginDTO, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, boolean, boolean, boolean, Long, String, String)
     */
    public PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto, ContactInfoForm contactForm)
            throws EtccException, EtccSecurityException {
        return setPersonalInfo(
                acctLoginDto,
                contactForm.getAccountType(),
                contactForm.getCompanyName(),
                contactForm.getFirstName(),
                contactForm.getLastName(),
                contactForm.getPrimaryPhone(),
                contactForm.getAlternatePhone(),
                contactForm.getTaxId(),
                contactForm.getDriversLicState(),
                contactForm.getDriversLic(),
                contactForm.getAddressLine1(),
                contactForm.getAddressLine2(),
                contactForm.getAddressLine3(),
                contactForm.getAddressLine4(),
                contactForm.getCity(),
                contactForm.getState(),
                contactForm.getZip(),
                contactForm.getCountry(),
                contactForm.isByMail(),
                contactForm.isByEmail(),
                contactForm.isByPhone(),
                contactForm.getRetailTransId(),
                contactForm.getAltPhoneExt(),
                contactForm.getPlus4()
        );
    }

    /**
     * @see #setPersonalInfo(AccountLoginDTO, ContactInfoForm)
     */
    public PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4)
            throws EtccException, EtccSecurityException {
    	PersonalInfoUpdateResultDTO result = stub()
            .setPersonalInfo(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
    			alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3,
    			address4, city, state, zip, country, byMail, byEmail, byPhone, retailTransId, altPhoneExt,
    			plus4);
    	return result;
     }

    public ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto, String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, BaseContactInfo contactInfo)
    throws EtccException, EtccSecurityException {
    	return stub().updateLoginInfo(acctLoginDto,
    			loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer,
    			alternateEmail, contactInfo);
    }

    @SuppressWarnings("deprecation")
	@Deprecated
	public AccountLoginDTO updateSecQn(String sessionID, String ipAddress, String loginId, String password,
            String acctID, int securityQnID, String securityAns) throws EtccException, EtccSecurityException {
    	AccountLoginDTO loginDto = stub().updateSecQn(sessionID,
    			ipAddress, loginId, password, acctID, securityQnID, securityAns);
    	return loginDto;
    }

    public AccountLoginDTO updateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
            String alternatePhoneExt) throws EtccSecurityException, EtccException {
    	AccountLoginDTO login = stub().updateContactInfo(acctID,
    			acctType, sessionID, ipAddress, loginId, emailAddress, altEmailAddress, primaryPhone,
    			alternatePhone, alternatePhoneExt);
    	return login;
    }

    /*public BillingInfoResultDTO setBillingInfoEFT(AccountLoginDTO acctLoginDto, AccountEFTDTO billingInfoDto)
    throws EtccException, EtccSecurityException {
    	logger.debug("**************===========> Before in AccountDelegate.setBillingInfo");
    	BillingInfoResultDTO billingResult = stub().setBillingInfoEFT(acctLoginDto, billingInfoDto);
    	logger.debug("**************===========> After in AccountDelegate.setBillingInfo");
    	return billingResult;
    }
    public BillingInfoResultDTO setBillingInfoCreditCard(AccountLoginDTO acctLoginDto, AccountCreditCardDTO billingInfoDto)
    	    throws EtccException, EtccSecurityException {
    	    	logger.debug("**************===========> Before in AccountDelegate.setBillingInfo");
    	    	BillingInfoResultDTO billingResult = stub().setBillingInfoCreditCard(acctLoginDto, billingInfoDto);
    	    	logger.debug("**************===========> After in AccountDelegate.setBillingInfo");
    	    	return billingResult;
    	    }
*/
   /* public ResultDTO makePayment(AccountLoginDTO acctLoginDto, AccountPaymentMethodDTO paymentMethodDTO, String xmlPosting, String emailAddress)
            throws EtccException, EtccSecurityException {
    	return stub().makePayment(acctLoginDto, paymentMethodDTO, xmlPosting, emailAddress);
   }*/
    
  /*  public ResultDTO makeInvoicePayment(AccountLoginDTO acctLoginDto,AccountPaymentMethodDTO paymentMethodDTO, ViolatorDTO violatorDTO,String email)
            throws EtccException, EtccSecurityException {
    	
   }*/
    
    

   /* public BaseDTO makeAccountPayment(AccountLoginDTO loginDTO,
			AccountPaymentMethodDTO primaryCard, Double rebillAmount,
			Double depositAmount, List<TagDTO> savedVehicles, Double paymentAmt)  throws EtccException, EtccSecurityException {
    	return stub().makeAccountPayment(loginDTO, primaryCard, rebillAmount, depositAmount, savedVehicles, paymentAmt);
	}*/
    
    /*public ResultDTO makeAccountPayment(AccountLoginDTO loginDTO,
    		BillingInfoDTO billingInfoDTO, Double rebillAmount,
    		Double depAmount, List<TagDTO> tagList, double paymentAmt)  throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return stub().makePayment(loginDTO, billingInfoDTO, rebillAmount, depAmount, tagList, paymentAmt);
	}*/
    
    public ResultDTO makePayment(AccountLoginDTO loginDTO,
    		BillingInfoDTO billingInfoDTO, Double rebillAmount,
    		Double lowBalanceAmount, List<TagDTO> tagList, double paymentAmt)
    		throws EtccException, EtccSecurityException {
    	// TODO Auto-generated method stub
    	return stub().makePayment(loginDTO, billingInfoDTO, rebillAmount, lowBalanceAmount, tagList, paymentAmt);
    }
    
	/*public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO,
			AccountCreditCardDTO primaryCard, Double rebillAmount,
			Double depAmount, List<TagDTO> tagList, double paymentAmt)  throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return stub().makeAccountPaymentCreditCard(loginDTO, primaryCard, rebillAmount, depAmount, tagList, paymentAmt);
	}
	
	public ResultDTO makeAccountPaymentEFT(AccountLoginDTO loginDTO,
			AccountEFTDTO primaryCard, Double rebillAmount,
			Double depAmount, List<TagDTO> tagList, double paymentAmt)  throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return stub().makeAccountPaymentEFT(loginDTO, primaryCard, rebillAmount, depAmount, tagList, paymentAmt);
	}*/
   
    
    public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
    	BillingInfoDTO bInfo = stub().getBillingInfo(acctLoginDto);
    	return bInfo;
   }

    /**
     * Update mailing address (using safer parameters).
     * @param acctLogin account login details
     * @param address the new address details
     * @return the updated account login.
     * @throws EtccSecurityException
     * @throws EtccException
     * @see #updateMailingAddr(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
     */
    public AccountLoginDTO updateMailingAddress(AccountLoginDTO acctLogin, Address address) throws EtccSecurityException, EtccException {
        return stub().updateMailingAddress(acctLogin, address);
    }

    /**
     * @see #updateMailingAddress(AccountLoginDTO, Address)
     * @deprecated
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public AccountLoginDTO updateMailingAddr(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String country, String zip, String plus4) throws EtccSecurityException, EtccException {
    	AccountLoginDTO login = stub().updateMailingAddr(acctID,
    			acctType, sessionID, ipAddress, loginId, address1, address2, address3, address4, city, state,
    			country, zip, plus4);
    	return login;
    }

    public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfo) throws EtccException, EtccSecurityException {
    	AccountLoginDTO login = stub().updateBillingInfo(acctLogin,
    			billingInfo);
    	return login;
    }

    public AccountLoginDTO updateBillingAddress(AccountLoginDTO acctLogin, Address address) throws EtccException, EtccSecurityException {
        return stub().updateBillingAddress(acctLogin, address);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public AccountLoginDTO updateBillingAddress(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String zip, String plus4, String country) throws EtccSecurityException, EtccException {
    	AccountLoginDTO login = stub().updateBillingAddress(
    			acctID, acctType, sessionID, ipAddress, loginId, address1, address2, address3, address4, city,
    			state, zip, plus4, country);
    	return login;
    }

    public AccountLoginDTO updateRebillAmt(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccSecurityException, EtccException {
    	AccountLoginDTO login = stub().updateRebillAmt(acctID,
    			acctType, sessionID, ipAddress, loginId, rebillAmt, lowBalanceLevel);
    	return login;
    }

    public AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto, AccountDTO accountDto) throws EtccException,
            EtccSecurityException {
        try {
    	AccountDTO retVal = stub().getAccountStatus(acctLoginDto, accountDto);
        return retVal;
        } catch (RuntimeException e){
            logger.error("Exception getting account status: " + e.getMessage(), e);
            throw e;
        } catch (EtccSecurityException e){
            logger.error("Exception getting account status: " + e.getMessage(), e);
            throw e;
        } catch (EtccException e){
            logger.error("Exception getting account status: " + e.getMessage(), e);
            throw e;
        }
    }

    public ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto, int updateFlags, String oldPw, String newPw, String emailAddress,
            int sQuestionID, String sAnswer) throws EtccSecurityException, EtccException {
    	return stub().updatePasswordEmailSecurityQA(acctLoginDto,
    			updateFlags, oldPw, newPw, emailAddress, sQuestionID, sAnswer);
    }



    public ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto,
			double activationFee, List<TagDTO> tagDtos) throws EtccException, EtccSecurityException{
    	return stub().sendWelcomeNotification(acctLoginDto, activationFee, tagDtos);
	}

	private AccountInterface stub() {
    	return ServiceFactory.getImplementation(AccountInterface.class);
    }

	public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto,
			BillingInfoDTO billingInfoDTO) throws EtccException,
			EtccSecurityException {
		
		BillingInfoResultDTO resultInfo = null;
        resultInfo = stub().setBillingInfo(acctLoginDto,billingInfoDTO);
	 	
		/*if (paymentMethodDto instanceof AccountCreditCardDTO)
        {
          resultInfo = setBillingInfoCreditCard(acctLoginDto, (AccountCreditCardDTO)paymentMethodDto);
        } else {
    		resultInfo = setBillingInfoEFT(acctLoginDto, (AccountEFTDTO)paymentMethodDto);
    	}*/
		
		return resultInfo;
	}

	//defect 9064 txphung beign
	public ResultDTO validateRoutingNumber(String routingNumber)
			throws EtccException, EtccSecurityException {
		return stub().validateRoutingNumber(routingNumber);
	}

	//defect 9064 txphung end
    //Express Account changes
	public AccountLoginDTO setupAccountWithPlanStep1(String loginId, String password, String emailAddress,
            int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,UserEnvDTO userEnvDto,String plan) throws EtccException {
    	AccountLoginDTO loginDto = stub().setupAccountWithPlanStep1(
    			loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer, alternateEmail,
    			ipAddress, sessionID,userEnvDto,plan);
    	return loginDto;
    }

	public AccountDeviceStatusDTO getAccountDeviceStatus(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId, String fingerprintId) throws EtccException {
		final AccountDeviceStatusDTO accountDeviceStatus = stub().getAccountDeviceStatus(acctId, acctType, dbSessionId,
				ipAddress, loginId, fingerprintId);
		return accountDeviceStatus;
	}

	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException {
		final ResultDTO saveAccountDeviceResultDto = stub().saveAccountDevice(acctId, acctType, dbSessionId, ipAddress,
				loginId, fingerprintId);
		return saveAccountDeviceResultDto;
	}

	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException {
		final ResultDTO validateSecurityAnswerResultDto = stub().validateSecurityAnswer(acctId, acctType, dbSessionId,
				ipAddress, loginId, securityAnswer);
		return validateSecurityAnswerResultDto;
	}

	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		final AuthenticatedSessionidDTO authenticatedSessionid = stub().getAuthenticatedSessionid(acctId, acctType,
				dbSessionId, ipAddress, loginId);
		return authenticatedSessionid;
	}

	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException {
		final AccountDTO acctDetailsTwoFa = stub().getAcctDetailsTwoFa(acctId, acctType, dbSessionId, ipAddress,
				loginId);
		return acctDetailsTwoFa;
	}

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, String documentType,
			String description, Long accountDocumentId)
			throws EtccException {
		final DocumentUploadResponse documentUploadResponse = stub().uploadDocument(acctId, acctType, dbSessionId, ipAddress, loginId, fileLocation, fileName, action, documentType,
				description,accountDocumentId);
		return documentUploadResponse;
	}

	public GetAccountDocumentResponse getAccountDocument(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		final GetAccountDocumentResponse accountDocument = stub().getAccountDocument(acctId, acctType, dbSessionId, ipAddress, loginId);
		return accountDocument;
	}

	public GetAccountNotificationsDocumentResponse getAcctNotificationDocuments(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId) throws EtccException {
		return stub().getAcctNotificationDocuments(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException {
		return stub().removeBillingInfo(acctLoginDto, billingInfoDTO);
	}

	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return stub().getAcctountPhoneInfo(acctId, acctType, dbSessionId, ipAddress, loginId);
	}

	public ResultDTO setContactInfoByContactId(BigDecimal acctId, String docType, String sessionId, String ipAddress,
			String userId, String emailAddress, String altEmail, String homePhoNbr, String workPhoNbr,
			String workPhoExt, String homePhoExt, String MobilePhone, String mobilePhoExt, String smsAlertsOptIn,
			BigDecimal homePhoneId, BigDecimal workPhoneId, BigDecimal mobilePhoneId) throws EtccException {
		return stub().setContactInfoByContactId(acctId, docType, sessionId, ipAddress, userId, emailAddress, altEmail,
				homePhoNbr, workPhoNbr, workPhoExt, homePhoExt, MobilePhone, mobilePhoExt, smsAlertsOptIn, homePhoneId,
				workPhoneId, mobilePhoneId);
	}

	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId, BigDecimal acctTypeId, BigDecimal acctPlanId,
			BigDecimal planDetailId, BigDecimal paymentFormId, BigDecimal vehicleCount) throws EtccException {
		return stub().getInitialAutochargeAmounts(acctId, acctType, dbSessionId, ipAddress, loginId, acctTypeId, acctPlanId, planDetailId, paymentFormId, vehicleCount);

	}

	public ValidateDocDownloadResponse validateDocDownload(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		return stub().validateDocDownload(acctId, acctType, dbSessionId, ipAddress, loginId);
	}
	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException {
		return stub().setupOnlineAccessStep1ForCCRMA(accountNumber, tolltagPrefix, tolltagNumber, emailAddress,
				phoneNumber, ipAddress, jsessionId, userEnvDto);
	}
	public AccountLoginDTO ccrmaSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
			String emailAddress, String phoneNumber, String ipAddress, String jsessionId, UserEnvDTO userEnvDto)
			throws EtccException {
		return stub().ccrmaSetupOnlineAccessStep1(accountNumber, tolltagPrefix, tolltagNumber, emailAddress,
				phoneNumber, ipAddress, jsessionId, userEnvDto);
	}
	public AccountLoginDTO ccrmaSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
			throws EtccSecurityException, EtccException {
		return stub().ccrmaSetupOnlineAccess(acctLoginDto, oasDto);
	}

	public PersonalInfoUpdateResultDTO setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto,
			String acctType, String companyName, String firstName, String lastName, String primaryPhone,
			String alternatePhone, String taxId, String driverLicState, String driverLicNumber, String address1,
			String address2, String address3, String address4, String city, String state, String zip, String country,
			boolean byMail, boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4,
			String primaryPhoneExt, String smsAlertsOptIn) throws EtccException, EtccSecurityException {
		return stub().setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(acctLoginDto, acctType, companyName, firstName,
				lastName, primaryPhone, alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2,
				address3, address4, city, state, zip, country, byMail, byEmail, byPhone, retailTransId, altPhoneExt,
				plus4, primaryPhoneExt, smsAlertsOptIn);
	}
}
