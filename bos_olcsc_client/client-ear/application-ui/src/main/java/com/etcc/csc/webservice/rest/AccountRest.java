package com.etcc.csc.webservice.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.GetAccountPhoneInfoResponse;
import com.etcc.csc.dto.GetInitialAutoChargeAmountsResponse;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.PersonalInfoUpdateResultDTO;
import com.etcc.csc.dto.RemoveBillingInfoResponse;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.service.App;
import com.etcc.csc.validation.AccountValidator;
import com.etcc.csc.webservice.rest.dto.GetAcctountPhoneInfoRequest;
import com.etcc.csc.webservice.rest.dto.GetInitialAutoChargeAmountsRequest;
import com.etcc.csc.webservice.rest.dto.LoginAccountRequest;
import com.etcc.csc.webservice.rest.dto.MakePaymentRequest;
import com.etcc.csc.webservice.rest.dto.RemoveBillingInfoRequest;
import com.etcc.csc.webservice.rest.dto.SendWelcomeNotificationRequest;
import com.etcc.csc.webservice.rest.dto.SetBillingInfoRequest;
import com.etcc.csc.webservice.rest.dto.SetContactInfoByIdRequest;
import com.etcc.csc.webservice.rest.dto.SetPersonalInfoRequest;
import com.etcc.csc.webservice.rest.dto.SetupAccountWithPlanStep1Request;
import com.etcc.csc.webservice.rest.dto.SetupOnlineAccessRequest;
import com.etcc.csc.webservice.rest.dto.SetupOnlineAccessStepOneRequest;
import com.etcc.csc.webservice.rest.dto.UpdateBillingAddressRequest;
import com.etcc.csc.webservice.rest.dto.UpdateContactInfoRequest;
import com.etcc.csc.webservice.rest.dto.UpdateMailingAddressRequest;
import com.etcc.csc.webservice.rest.dto.UpdatePasswordEmailSecurityQARequest;
import com.etcc.csc.webservice.rest.dto.UpdateRebillAmtRequest;

@Path("/account")
public class AccountRest {

	private static final Logger logger = Logger.getLogger(AccountRest.class);

	@POST
	@Path("/setupAccountWithPlanStep1")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO setupAccountWithPlanStep1(SetupAccountWithPlanStep1Request setupAccountWithPlanStep1Request)
			throws EtccException {

		final String loginId = setupAccountWithPlanStep1Request.getLoginId();
		final String password = setupAccountWithPlanStep1Request.getPassword();
		final String emailAddress = setupAccountWithPlanStep1Request.getEmailAddress();
		final int securityQuestionID = setupAccountWithPlanStep1Request.getSecurityQuestionID();
		final String securityQuestionAnswer = setupAccountWithPlanStep1Request.getSecurityQuestionAnswer();
		final String alternateEmail = setupAccountWithPlanStep1Request.getAlternateEmail();
		final String ipAddress = setupAccountWithPlanStep1Request.getIpAddress();
		final String sessionID = setupAccountWithPlanStep1Request.getSessionID();
		final UserEnvDTO userEnvDto = setupAccountWithPlanStep1Request.getUserEnvDto();
		final String plan = setupAccountWithPlanStep1Request.getPlan();

		logger.info("loginId ["+loginId+"] SetupAccountWithPlanStep1Request ["+setupAccountWithPlanStep1Request+"]");

		validateSetupAccountWithPlanStep1(loginId, password, emailAddress, securityQuestionID, securityQuestionAnswer,
				alternateEmail, ipAddress, sessionID, plan);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final AccountLoginDTO setupAccountWithPlanStep1Response = accountDao.setupAccountWithPlanStep1(loginId,
				password, emailAddress, securityQuestionID, securityQuestionAnswer, alternateEmail, ipAddress,
				sessionID, userEnvDto, plan);

		logger.info("loginId ["+loginId+"] setupAccountWithPlanStep1Response ["+setupAccountWithPlanStep1Response+"]");

		return setupAccountWithPlanStep1Response;
	}

	@POST
	@Path("/setPersonalInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public PersonalInfoUpdateResultDTO setPersonalInfo(SetPersonalInfoRequest setPersonalInfoRequest)
			throws EtccException, EtccSecurityException {

		final AccountLoginDTO acctLoginDto = setPersonalInfoRequest.getAcctLoginDto();
		final String acctType = setPersonalInfoRequest.getAcctType();
		final String companyName = setPersonalInfoRequest.getCompanyName();
		final String firstName = setPersonalInfoRequest.getFirstName();
		final String lastName = setPersonalInfoRequest.getLastName();
		final String primaryPhone = setPersonalInfoRequest.getPrimaryPhone();
		final String alternatePhone = setPersonalInfoRequest.getAlternatePhone();
		final String taxId = setPersonalInfoRequest.getTaxId();
		final String driverLicState = setPersonalInfoRequest.getDriverLicState();
		final String driverLicNumber = setPersonalInfoRequest.getDriverLicNumber();
		final String address1 = setPersonalInfoRequest.getAddress1();
		final String address2 = setPersonalInfoRequest.getAddress2();
		final String address3 = setPersonalInfoRequest.getAddress3();
		final String address4 = setPersonalInfoRequest.getAddress4();
		final String city = setPersonalInfoRequest.getCity();
		final String state = setPersonalInfoRequest.getState();
		final String zip = setPersonalInfoRequest.getZip();
		final String country = setPersonalInfoRequest.getCountry();
		final boolean byMail = setPersonalInfoRequest.isByMail();
		final boolean byEmail = setPersonalInfoRequest.isByEmail();
		final boolean byPhone = setPersonalInfoRequest.isByPhone();
		final Long retailTransId = setPersonalInfoRequest.getRetailTransId();
		final String altPhoneExt = setPersonalInfoRequest.getAltPhoneExt();
		final String plus4 = setPersonalInfoRequest.getPlus4();
		final String primaryPhoneExt = setPersonalInfoRequest.getPrimaryPhoneExt();
		final String smsAlertsOptIn = setPersonalInfoRequest.getSmsAlertsOptIn();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] setPersonalInfoRequest ["+setPersonalInfoRequest+"]");

		// Express AccountChnages
		boolean isExpAccount = new App().isExpressAccount(acctLoginDto.getAcctId());
		AccountValidator.validateSetPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(acctLoginDto, acctType, companyName, firstName, lastName, primaryPhone,
				alternatePhone, taxId, driverLicState, driverLicNumber, address1, address2, address3, address4, city,
				state, zip, country, byMail, byEmail, retailTransId, altPhoneExt, plus4, isExpAccount, primaryPhoneExt);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final PersonalInfoUpdateResultDTO setPersonalInfoResponse = accountDao.setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(acctLoginDto, acctType,
				companyName, firstName, lastName, primaryPhone, alternatePhone, taxId, driverLicState, driverLicNumber,
				address1, address2, address3, address4, city, state, zip, country, byMail, byEmail, byPhone,
				retailTransId, altPhoneExt, plus4, primaryPhoneExt, smsAlertsOptIn);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] setPersonalInfoResponse ["+setPersonalInfoResponse+"]");

		return setPersonalInfoResponse;

	}

	@POST
	@Path("/setBillingInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO setBillingInfo(SetBillingInfoRequest setBillingInfoRequest)
			throws EtccException, EtccSecurityException {

		final AccountLoginDTO acctLogin = setBillingInfoRequest.getAcctLoginDto();
		final BillingInfoDTO billingInfo = setBillingInfoRequest.getBillingInfoDTO();

		logger.info("AcctId ["+ acctLogin.getAcctId()+"] LoginId ["+acctLogin.getLoginId()+"] setBillingInfoRequest ["+setBillingInfoRequest+"]");
		
		PaymentType paymentType = billingInfo.getBillingType();
		AccountPaymentMethodDTO paymentMethodDto = null;
		boolean isExpAccount = new App().isExpressAccount(acctLogin.getAcctId());
		// Credit -- validate and generate tocken for credit cards
		if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())) {
			AccountCreditCardDTO[] cards = billingInfo.getCards();
			if (cards != null && cards.length > 0) {
				for (AccountCreditCardDTO billingInfoCard : cards) {
					AccountValidator.validateUpdateBillingInfo(acctLogin, billingInfoCard, isExpAccount);
					// set token if required.
					// if a paypage registration id is sent , a new taken should be generated.
					CCTokenDAOHelper.getInstance().getCCToken(acctLogin, billingInfoCard);
				}
			}
		}
		// EFT -- validate bank account info
		else {
			paymentMethodDto = billingInfo.getEft();
			AccountValidator.validateUpdateBillingInfo(acctLogin, paymentMethodDto, isExpAccount);
		}
		AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		AccountLoginDTO setBillingInfoResponse = accountDao.updateBillingInfo(acctLogin, billingInfo);

		logger.info("AcctId ["+acctLogin.getAcctId()+"] LoginId ["+acctLogin.getLoginId()+"] setBillingInfoResponse ["+setBillingInfoResponse+"]");

		return setBillingInfoResponse;
	}

	@POST
	@Path("/makePayment")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO makePayment(MakePaymentRequest makePaymentRequest) throws EtccException, EtccSecurityException {

		final AccountLoginDTO loginDTO = makePaymentRequest.getLoginDTO();
		final BillingInfoDTO billingInfoDTO = makePaymentRequest.getBillingInfoDTO();
		final Double rebillAmount = makePaymentRequest.getRebillAmount();
		final Double lowBalanceAmount = makePaymentRequest.getLowBalanceAmount();
		final List<TagDTO> tagList = makePaymentRequest.getTagList();
		final double paymentAmt = makePaymentRequest.getPaymentAmt();

		logger.info("AcctId ["+loginDTO.getAcctId()+"] LoginId ["+loginDTO.getLoginId()+"] makePaymentRequest ["+makePaymentRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final ResultDTO makePaymentResponse = accountDao.makePayment(loginDTO, billingInfoDTO, rebillAmount,
				lowBalanceAmount, tagList, paymentAmt);

		logger.info("AcctId ["+loginDTO.getAcctId()+"] LoginId ["+loginDTO.getLoginId()+"] makePaymentResponse["+makePaymentResponse+"]");

		return makePaymentResponse;
	}

	@POST
	@Path("/sendWelcomeNotification")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO sendWelcomeNotification(SendWelcomeNotificationRequest sendWelcomeNotificationRequest)
			throws EtccException, EtccSecurityException {
		final AccountLoginDTO acctLoginDto = sendWelcomeNotificationRequest.getAcctLoginDto();
		final double activationFee = sendWelcomeNotificationRequest.getActivationFee();
		final List<TagDTO> tagDTOSs = sendWelcomeNotificationRequest.getTagDTOSs();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] sendWelcomeNotificationRequest ["+sendWelcomeNotificationRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final ResultDTO sendWelcomeNotificationResponse = accountDao.sendWelcomeNotification(acctLoginDto,
				activationFee, tagDTOSs);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] sendWelcomeNotificationResponse ["+sendWelcomeNotificationResponse+"]");

		return sendWelcomeNotificationResponse;
	}

	@POST
	@Path("/loginAccount")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO loginAccount(LoginAccountRequest loginAccountRequest) throws EtccException {

		final String userName = loginAccountRequest.getUserName();
		final String password = loginAccountRequest.getPassword();
		final String ipAddress = loginAccountRequest.getIpAddress();
		final String sessionId = loginAccountRequest.getSessionId();
		final UserEnvDTO userEnvDto = loginAccountRequest.getUserEnvDto();

		logger.info("userName ["+userName+"] loginAccountRequest ["+loginAccountRequest+"]");

		final String regExpPass = new App().getRegExp("pwdsplchars");
		validateLoginAccount(userName, password, ipAddress, sessionId, userEnvDto, regExpPass);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final AccountLoginDTO acctDto = accountDao.loginAccount(userName, password, ipAddress, sessionId, userEnvDto);

		logger.info("userName ["+userName+"] loginAccountResponse ["+acctDto+"]");

		return acctDto;
	}

	@POST
	@Path("/updateRebillAmt")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO updateRebillAmt(UpdateRebillAmtRequest updateRebillAmtRequest)
			throws EtccSecurityException, EtccException {

		final String acctID = updateRebillAmtRequest.getAcctID();
		final String acctType = updateRebillAmtRequest.getAcctType();
		final String sessionID = updateRebillAmtRequest.getSessionID();
		final String ipAddress = updateRebillAmtRequest.getIpAddress();
		final String loginId = updateRebillAmtRequest.getLoginId();
		final BigDecimal rebillAmt = updateRebillAmtRequest.getRebillAmt();
		final BigDecimal lowBalanceLevel = updateRebillAmtRequest.getLowBalanceLevel();

		logger.info("AcctId ["+acctID+"] LoginId ["+loginId+"] updateRebillAmtRequest ["+updateRebillAmtRequest+"]");

		validateUpdateRebillAmtForCCRMA(acctID, acctType, sessionID, ipAddress, loginId, rebillAmt, lowBalanceLevel);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final AccountLoginDTO updateRebillAmtResponse = accountDao.updateRebillAmt(acctID, acctType, sessionID,
				ipAddress, loginId, rebillAmt, lowBalanceLevel);

		logger.info("AcctId ["+acctID+"] LoginId ["+loginId+"] updateRebillAmtResponse ["+updateRebillAmtResponse+"]");
		return updateRebillAmtResponse;
	}

	@POST
	@Path("/updateContactInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest)
			throws EtccSecurityException, EtccException {

		final String acctID = updateContactInfoRequest.getAcctID();
		final String acctType = updateContactInfoRequest.getAcctType();
		final String sessionID = updateContactInfoRequest.getSessionID();
		final String ipAddress = updateContactInfoRequest.getIpAddress();
		final String loginId = updateContactInfoRequest.getLoginId();
		final String emailAddress = updateContactInfoRequest.getEmailAddress();
		final String altEmailAddress = updateContactInfoRequest.getAltEmailAddress();
		final String primaryPhone = updateContactInfoRequest.getPrimaryPhone();
		final String alternatePhone = updateContactInfoRequest.getAlternatePhone();
		final String alternatePhoneExt = updateContactInfoRequest.getAlternatePhoneExt();
		final String primaryPhoneExt = updateContactInfoRequest.getPrimaryPhoneExt();
		final String mobilePhone = updateContactInfoRequest.getMobilePhone();
		final String mobilePhoneExt = updateContactInfoRequest.getMobilePhoneExt();
		final String smsAlertsOptIn = updateContactInfoRequest.getSmsAlertsOptIn();

		logger.info("AcctId ["+acctID+"] LoginId ["+loginId+"] updateContactInfoRequest ["+updateContactInfoRequest+"]");

		validateUpdateContactInfo(acctID, acctType, sessionID, ipAddress, loginId, emailAddress, altEmailAddress,
				primaryPhone, alternatePhone, alternatePhoneExt, primaryPhoneExt, mobilePhone, mobilePhoneExt);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final AccountLoginDTO updateContactInfoResponse = accountDao.updateContactInfo(acctID, acctType, sessionID,
				ipAddress, loginId, emailAddress, altEmailAddress, primaryPhone, alternatePhone, alternatePhoneExt);

		logger.info("AcctId ["+acctID+"] LoginId ["+loginId+"] updateContactInfoResponse ["+updateContactInfoResponse+"]");

		return updateContactInfoResponse;
	}

	@POST
	@Path("/updateMailingAddress")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO updateMailingAddress(UpdateMailingAddressRequest updateMailingAddressRequest)
			throws EtccSecurityException, EtccException {

		logger.info("AcctId ["+updateMailingAddressRequest.getAcctId()+"] LoginId ["+updateMailingAddressRequest.getLoginId()+"] updateMailingAddressRequest ["+updateMailingAddressRequest+"]");
		validateUpdateMailingAddress(updateMailingAddressRequest);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final AccountLoginDTO updateMailingAddressResponse = accountDao.updateMailingAddr(
				Long.toString(updateMailingAddressRequest.getAcctId()),
                AccountLoginDTO.LoginType.AC.toString(), updateMailingAddressRequest.getDbSessionId(),
				updateMailingAddressRequest.getLastLoginIp(), updateMailingAddressRequest.getLoginId(),
				updateMailingAddressRequest.getAddress1(), updateMailingAddressRequest.getAddress2(),
				updateMailingAddressRequest.getAddress3(), updateMailingAddressRequest.getAddress4(),
				updateMailingAddressRequest.getCity(), updateMailingAddressRequest.getState(),
				updateMailingAddressRequest.getCountry(), updateMailingAddressRequest.getZip(),
				updateMailingAddressRequest.getPlus4());

		logger.info("AcctId ["+updateMailingAddressRequest.getAcctId()+"] LoginId ["+updateMailingAddressRequest.getLoginId()+"] updateMailingAddressResponse ["+updateMailingAddressResponse+"]");

		return updateMailingAddressResponse;
	}

	@POST
	@Path("/updatePasswordEmailSecurityQA")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO updatePasswordEmailSecurityQA(
			UpdatePasswordEmailSecurityQARequest updatePasswordEmailSecurityQARequest)
			throws EtccSecurityException, EtccException {

		final AccountLoginDTO acctLoginDto = updatePasswordEmailSecurityQARequest.getAcctLoginDto();
		final int updateFlags = updatePasswordEmailSecurityQARequest.getUpdateFlags();
		final String oldPw = updatePasswordEmailSecurityQARequest.getOldPw();
		final String newPw = updatePasswordEmailSecurityQARequest.getNewPw();
		final String emailAddress = updatePasswordEmailSecurityQARequest.getEmailAddress();
		final int sQuestionID = updatePasswordEmailSecurityQARequest.getsQuestionID();
		final String sAnswer = updatePasswordEmailSecurityQARequest.getsAnswer();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] updatePasswordEmailSecurityQARequest ["+updatePasswordEmailSecurityQARequest+"]");

		final String regExpPass = new App().getRegExp("pwdsplchars");
		AccountValidator.validatePasswordEmailSecurity(acctLoginDto, updateFlags, oldPw, newPw, emailAddress,
				sQuestionID, sAnswer, regExpPass);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final ResultDTO updatePasswordEmailSecurityQAResponse = accountDao.updatePasswordEmailSecurityQA(acctLoginDto,
				updateFlags, oldPw, newPw, emailAddress, sQuestionID, sAnswer);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] updatePasswordEmailSecurityQAResponse ["+updatePasswordEmailSecurityQAResponse+"]");
		return updatePasswordEmailSecurityQAResponse;
	}

	@POST
	@Path("/getAccount")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] acctLoginDto ["+acctLoginDto+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final AccountDTO account = accountDao.getAccount(acctLoginDto);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] getAccountResponse ["+account+"]");
		return account;
	}

	@POST
	@Path("/getBillingInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] acctLoginDto ["+acctLoginDto+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final BillingInfoDTO billingInfo = accountDao.getBillingInfo(acctLoginDto);
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] billingInfo ["+billingInfo+"]");

		return billingInfo;
	}

	private static void validateSetupAccountWithPlanStep1(String loginId, String password, String emailAddress,
			int securityQuestionID, String securityQuestionAnswer, String alternateEmail, String ipAddress,
			String sessionID, String plan) throws EtccException {
		final String regExpPass = new App().getRegExp("pwdsplchars");
		AccountValidator.validateSetupAccountWithPlanStep1(loginId, password, emailAddress, securityQuestionID,
				securityQuestionAnswer, alternateEmail, ipAddress, sessionID, plan, regExpPass);
	}

	private static void validateUpdateRebillAmtForCCRMA(String acctID, String acctType, String sessionID,
			String ipAddress, String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccException {

		AccountValidator.validateUpdateRebillAmtForCCRMA(acctID, acctType, sessionID, ipAddress, loginId, rebillAmt,
				lowBalanceLevel);

	}

	private static void validateLoginAccount(String userName, String password, String ipAddress, String sessionId,
			UserEnvDTO userEnvDto, String regExpPass) throws EtccException {
		AccountValidator.validateLoginAccount(userName, password, ipAddress, sessionId, userEnvDto, regExpPass);
	}

	private static void validateUpdateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
			String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
			String alternatePhoneExt, String primaryPhoneExt, String mobilePhone, String mobilePhoneExt)
			throws EtccException {
		AccountValidator.validateUpdateContactInfo(acctID, acctType, sessionID, ipAddress, loginId, emailAddress,
				altEmailAddress, primaryPhone, alternatePhone, alternatePhoneExt);
	}

	private static void validateUpdateMailingAddress(UpdateMailingAddressRequest updateMailingAddressRequest)
			throws EtccException {
		AccountValidator.validateUpdateMailingAddr(Long.toString(updateMailingAddressRequest.getAcctId()),
				AccountLoginDTO.LoginType.AC.toString(), updateMailingAddressRequest.getDbSessionId(),
				updateMailingAddressRequest.getLastLoginIp(), updateMailingAddressRequest.getLoginId(),
				updateMailingAddressRequest.getAddress1(), updateMailingAddressRequest.getAddress2(),
				updateMailingAddressRequest.getAddress3(), updateMailingAddressRequest.getAddress4(),
				updateMailingAddressRequest.getCity(), updateMailingAddressRequest.getState(),
				updateMailingAddressRequest.getCountry(), updateMailingAddressRequest.getZip(),
				updateMailingAddressRequest.getPlus4());
	}
	
	@POST
	@Path("/removeBillingInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public RemoveBillingInfoResponse removeBillingInfo(RemoveBillingInfoRequest removeBillingInfoRequest)
			throws EtccException {
		final AccountLoginDTO acctLoginDto = removeBillingInfoRequest.getAcctLoginDto();
		final BillingInfoDTO billingInfoDTO = removeBillingInfoRequest.getBillingInfoDTO();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] removeBillingInfo ["+removeBillingInfoRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final RemoveBillingInfoResponse removeBillingInfo = accountDao.removeBillingInfo(acctLoginDto, billingInfoDTO);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] RemoveBillingInfoResponse ["+removeBillingInfo+"]");

		return removeBillingInfo;

	}
	
	@POST
	@Path("/getAcctountPhoneInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(GetAcctountPhoneInfoRequest getAcctountPhoneInfoRequest)
			throws EtccException {

		final Long acctId = getAcctountPhoneInfoRequest.getAcctId();
		final String acctType = getAcctountPhoneInfoRequest.getLoginType();
		final String dbSessionId = getAcctountPhoneInfoRequest.getDbSessionId();
		final String ipAddress = getAcctountPhoneInfoRequest.getIpAddress();
		final String loginId = getAcctountPhoneInfoRequest.getLoginId();

		logger.info("AcctId ["+acctId+"] LoginId ["+loginId+"] GetAcctountPhoneInfoRequest ["+getAcctountPhoneInfoRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final GetAccountPhoneInfoResponse acctountPhoneInfo = accountDao.getAcctountPhoneInfo(acctId, acctType,
				dbSessionId, ipAddress, loginId);

		logger.info("AcctId ["+acctId+"] LoginId ["+loginId+"] GetAccountPhoneInfoResponse ["+acctountPhoneInfo+"]");

		return acctountPhoneInfo;

	}

	@POST
	@Path("/setContactInfoByContactId")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO setContactInfoByContactId(SetContactInfoByIdRequest setContactInfoByIdRequest)
			throws EtccException {

		logger.info("AcctId ["+setContactInfoByIdRequest.getAcctId()+"] LoginId ["+setContactInfoByIdRequest.getLoginId()+"] SetContactInfoByIdRequest ["+setContactInfoByIdRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		ResultDTO setContactInfoByIdResponse = accountDao.setContactInfoByContactId(
				setContactInfoByIdRequest.getAcctId(), setContactInfoByIdRequest.getLoginType(),
				setContactInfoByIdRequest.getDbSessionId(), setContactInfoByIdRequest.getIpAddress(),
				setContactInfoByIdRequest.getLoginId(), setContactInfoByIdRequest.getEmailAddress(),
				setContactInfoByIdRequest.getAltEmail(), setContactInfoByIdRequest.getHomePhoNbr(),
				setContactInfoByIdRequest.getWorkPhoNbr(), setContactInfoByIdRequest.getWorkPhoExt(),
				setContactInfoByIdRequest.getHomePhoExt(), setContactInfoByIdRequest.getMobilePhone(),
				setContactInfoByIdRequest.getMobilePhoExt(), setContactInfoByIdRequest.getSmsAlertsOptIn(),
				setContactInfoByIdRequest.getHomePhoneId(), setContactInfoByIdRequest.getWorkPhoneId(),
				setContactInfoByIdRequest.getMobilePhoneId());

		logger.info("AcctId ["+setContactInfoByIdRequest.getAcctId()+"] LoginId ["+setContactInfoByIdRequest.getLoginId()+"] SetContactInfoByIdResponse ["+setContactInfoByIdResponse+"]");

		return setContactInfoByIdResponse;

	}

	@POST
	@Path("/setupOnlineAccess")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO setupOnlineAccess(SetupOnlineAccessRequest setupOnlineAccessRequest)
			throws EtccSecurityException, EtccException {

		final AccountLoginDTO acctLoginDto = setupOnlineAccessRequest.getAcctLoginDto();
		final OnlineAccessSetupDTO oasDto = setupOnlineAccessRequest.getOnlineAccessDTO();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] SetupOnlineAccessRequest ["+setupOnlineAccessRequest+"]");

		//validateSetupOnlineAccess(acctLoginDto, oasDto);

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		AccountLoginDTO setupOnlineAccess = accountDao.setupOnlineAccess(acctLoginDto, oasDto);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] setupOnlineAccessResponse ["+setupOnlineAccess+"]");

		return setupOnlineAccess;
	}

	private static void validateSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
			throws EtccException {
		AccountValidator.validateSetupOnlineAccess(acctLoginDto, oasDto);
	}
	
	@POST
	@Path("/getInitialAutochargeAmounts")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(
			GetInitialAutoChargeAmountsRequest getInitialAutoChargeAmountsRequest) throws EtccException {

		final Long acctId = getInitialAutoChargeAmountsRequest.getAcctId();
		final String acctType = getInitialAutoChargeAmountsRequest.getLoginType();
		final String dbSessionId = getInitialAutoChargeAmountsRequest.getDbSessionId();
		final String ipAddress = getInitialAutoChargeAmountsRequest.getIpAddress();
		final String loginId = getInitialAutoChargeAmountsRequest.getLoginId();
		final BigDecimal acctTypeId = getInitialAutoChargeAmountsRequest.getAcctTypeId();
		final BigDecimal acctPlanId = getInitialAutoChargeAmountsRequest.getAcctPlanId();
		final BigDecimal planDetailId = getInitialAutoChargeAmountsRequest.getPlanDetailId();
		final BigDecimal paymentFormId = getInitialAutoChargeAmountsRequest.getPaymentFormId();
		final BigDecimal vehicleCount = getInitialAutoChargeAmountsRequest.getVehicleCount();

		logger.info("AcctId ["+acctId+"] LoginId ["+loginId+"] GetInitialAutoChargeAmountsRequest ["+getInitialAutoChargeAmountsRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final GetInitialAutoChargeAmountsResponse getInitialAutoChargeAmountsResponse = accountDao
				.getInitialAutochargeAmounts(acctId, acctType, dbSessionId, ipAddress, loginId, acctTypeId, acctPlanId,
						planDetailId, paymentFormId, vehicleCount);
				

		logger.info("AcctId ["+acctId+"] LoginId ["+loginId+"] GetInitialAutoChargeAmountsResponse ["+getInitialAutoChargeAmountsResponse+"]");

		return getInitialAutoChargeAmountsResponse;

	}
	
	@POST
	@Path("/setupOnlineAccessStep1")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(
			SetupOnlineAccessStepOneRequest setupOnlineAccessStepOneRequest) throws EtccException {

		logger.info("SetupOnlineAccessStepOneRequest ["+setupOnlineAccessStepOneRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final AccountLoginDTO accountLoginDTO = accountDao.ccrmaSetupOnlineAccessStep1(
				setupOnlineAccessStepOneRequest.getAccountNumber(), setupOnlineAccessStepOneRequest.getTolltagPrefix(),
				setupOnlineAccessStepOneRequest.getTolltagNumber(), setupOnlineAccessStepOneRequest.getEmailAddress(),
				setupOnlineAccessStepOneRequest.getPhoneNumber(), setupOnlineAccessStepOneRequest.getIpAddress(),
				setupOnlineAccessStepOneRequest.getJsessionId(), setupOnlineAccessStepOneRequest.getUserEnvDto());

		logger.info("SetupOnlineAccessStepOneResponse ["+accountLoginDTO+"]");

		return accountLoginDTO;

	}
	
	@POST
	@Path("/ccrmaSetupOnlineAccess")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO ccrmaSetupOnlineAccess(SetupOnlineAccessRequest setupOnlineAccessRequest)
			throws EtccSecurityException, EtccException {

		final AccountLoginDTO acctLoginDto = setupOnlineAccessRequest.getAcctLoginDto();
		final OnlineAccessSetupDTO oasDto = setupOnlineAccessRequest.getOnlineAccessDTO();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] SetupOnlineAccessRequest ["+setupOnlineAccessRequest+"]");

		//validateSetupOnlineAccess(acctLoginDto, oasDto);

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		AccountLoginDTO setupOnlineAccess = accountDao.ccrmaSetupOnlineAccess(acctLoginDto, oasDto);

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] setupOnlineAccessResponse ["+setupOnlineAccess+"]");

		return setupOnlineAccess;
	}
	
	@POST
	@Path("/updateBillingAddress")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountLoginDTO updateBillingAddress(UpdateBillingAddressRequest updateBillingAddressRequest)
			throws EtccSecurityException, EtccException {

		logger.info("AcctId [" + updateBillingAddressRequest.getAcctId() + "] LoginId ["
				+ updateBillingAddressRequest.getLoginId() + "] updateBillingAddressRequest ["
				+ updateBillingAddressRequest + "]");
		validateUpdateBillingAddress(updateBillingAddressRequest);
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);

		final AccountLoginDTO updateBillingAddressResponse = accountDao.updateBillingAddress(
				Long.toString(updateBillingAddressRequest.getAcctId()), AccountLoginDTO.LoginType.AC.toString(),
				updateBillingAddressRequest.getDbSessionId(), updateBillingAddressRequest.getLastLoginIp(),
				updateBillingAddressRequest.getLoginId(), updateBillingAddressRequest.getAddress1(),
				updateBillingAddressRequest.getAddress2(), updateBillingAddressRequest.getAddress3(),
				updateBillingAddressRequest.getAddress4(), updateBillingAddressRequest.getCity(),
				updateBillingAddressRequest.getState(), updateBillingAddressRequest.getZip(),
				updateBillingAddressRequest.getPlus4(), updateBillingAddressRequest.getCountry());

		logger.info("AcctId [" + updateBillingAddressRequest.getAcctId() + "] LoginId ["
				+ updateBillingAddressRequest.getLoginId() + "] updateBillingAddressResponse ["
				+ updateBillingAddressResponse + "]");

		return updateBillingAddressResponse;
	}

	private static void validateUpdateBillingAddress(UpdateBillingAddressRequest updateBillingAddressRequest)
			throws EtccException {
		AccountValidator.validateUpdateBillingAddress(Long.toString(updateBillingAddressRequest.getAcctId()),
				AccountLoginDTO.LoginType.AC.toString(), updateBillingAddressRequest.getDbSessionId(),
				updateBillingAddressRequest.getLastLoginIp(), updateBillingAddressRequest.getLoginId(),
				updateBillingAddressRequest.getAddress1(), updateBillingAddressRequest.getAddress2(),
				updateBillingAddressRequest.getAddress3(), updateBillingAddressRequest.getAddress4(),
				updateBillingAddressRequest.getCity(), updateBillingAddressRequest.getState(),
				updateBillingAddressRequest.getZip(), updateBillingAddressRequest.getPlus4(),
				updateBillingAddressRequest.getCountry());
	}

}
