/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.cart.CartList;
import com.etcc.csc.cart.ShoppingCart;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;

import com.etcc.csc.dto.CheckEligibilityResponseDTO;
import com.etcc.csc.dto.DocumentDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.GetViolationResponseDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.MakeViolationPaymentRequest;
import com.etcc.csc.dto.PaymentPlanDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SearchOpenViolationResponse;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolationDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.validation.CreditCardValidator;
import com.etcc.csc.validation.ValidationException;

/**
 * Test implementation of <tt>ViolationInterface</tt>.
 * @author Milosh Boroyevich
 */
public class ViolationTestImpl implements ViolationInterface {
	private static final Logger logger = Logger.getLogger(ViolationTestImpl.class);
	private static final int NUMBER_OF_INVOICE_COLLECTIONS_TO_ADD = 3;

	/**
	 * Dummy document with value for test purposes.
	 */
	public static final DocumentDTO testDocument = new DocumentDTO();

	static {
	    testDocument.setDocument("Test Document".getBytes());
	}

	public ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod, String email)
			throws EtccException, EtccSecurityException {
		ShoppingCart cart = violator.getShoppingCart();
		logger.debug("Processing e-mail receipt for shopping cart: " + cart);
		switch (paymentMethod.getPaymentType()) {
		case CREDIT:
			logger.info("Generating FAKE e-mail (" + email + ") receipt for CC payment: " + paymentMethod);
			break;
		case EFT:
			logger.info("Generating FAKE e-mail (" + email + ") receipt for EFT payment: " + paymentMethod);
			break;
		default:
			logger.warn("Unhandled payment method: " + paymentMethod);
			return null;
		}
		return violator;
	}

	public DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoiceDTO)
			throws EtccException, EtccSecurityException {
	    return testDocument;
	}

	/**
	 * @see #newTestViolatorDTO(AccountLoginDTO)
	 * @see #newInvoices(int)
	 * @see #applyDiscountEligibility(ViolatorDTO)
	 */
	public ViolatorDTO getInvoices(AccountLoginDTO accountLoginDto,
			String ipAddress, String licPlate, String licState, AgencyEnum agency)
			throws EtccException, EtccSecurityException {
		ViolatorDTO violator = newTestViolatorDTO(accountLoginDto);
		for (int i = 0; i < NUMBER_OF_INVOICE_COLLECTIONS_TO_ADD; i++)
			violator.addInvoices(newInvoices(i));
		if (licPlate.equals(AccountFactory.LICENSE_PLATE))
			violator = applyDiscountEligibility(violator);
		logger.debug("getInvoices() : Created Violator: " + violator.toStringBuilder(1,0).toString());
		return violator.calculatePreliminaryTotals();
	}

	public PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID,
			String licPlate, String sessionID, String ipAddress, long acctID)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see #loginVPS(AccountLoginDTO, String, String, String, String, String)
	 * @see #newTestViolatorDTO(AccountLoginDTO)
	 * @see #newInvoices(int)
	 * @see #newViolations()
	 * @see #applyDiscountEligibility(ViolatorDTO)
	 */
    public ViolatorDTO loginViolator(AccountLoginDTO acctLoginDto,
			String sessionId, String ipAddress, UserEnvDTO userEnvDto,
			String invoiceId, String licPlate, String licState, String dbSessionId)
			throws EtccException {
		ViolatorDTO violator = newTestViolatorDTO(loginVPS(acctLoginDto, sessionId, ipAddress, invoiceId, licPlate,licState, null,dbSessionId));
		for (int i = 0; i < NUMBER_OF_INVOICE_COLLECTIONS_TO_ADD; i++)
			violator.addInvoices(newInvoices(i));
		violator.addViolations(AgencyEnum.AGENCY_HARRIS_COUNTY, newViolations());
		if (licPlate.equals(AccountFactory.LICENSE_PLATE))
			violator = applyDiscountEligibility(violator);
		logger.debug("loginViolator() : Created Violator: " + violator.toStringBuilder(1,0).toString());
		return violator.calculatePreliminaryTotals();
	}

	public ViolatorDTO makePayment(ViolatorDTO violatorDTO,
			AccountPaymentMethodDTO paymentMethod, String ipAddress)
			throws EtccException {
		String confirmationCode = null;
		switch (paymentMethod.getPaymentType()) {
		case CREDIT:
			logger.warn("Making FAKE Credit Card payment...success... :)");
			AccountCreditCardDTO ccPayment = (AccountCreditCardDTO) paymentMethod;
			CreditCardValidator validator = new CreditCardValidator(ccPayment);
			try {
				validator.isValid();
				confirmationCode = "FAKE-CC-" + ccPayment.getCardType().getCode();
			} catch (ValidationException e) {
				String message = "CC validation failed: " + e;
				logger.info(message);
				violatorDTO.addError(new ErrorMessageDTO(null, message));
				violatorDTO.clearFailedPayments();
			}
			break;
		case EFT:
			logger.warn("Making FAKE EFT payment...success... :)");
			confirmationCode = "FAKE-EFT";
			break;
		default:
			String message = "Unhandled payment method: " + paymentMethod;
			logger.warn(message);
			violatorDTO.addError(new ErrorMessageDTO(paymentMethod.getPaymentType().getCodeString(), message));
		}
		if (!violatorDTO.hasErrors())
			for (CartList bin : violatorDTO.getShoppingCart().getBins())
				bin.setConfirmationCode(confirmationCode);
		return violatorDTO;
	}

	AccountLoginDTO loginVPS(AccountLoginDTO acctLoginDto, String sessionId, String ipAddress, String invoiceId, String licPlate, String licState,String paymentPlanId, String dbSessionId) {
		if (acctLoginDto == null) {
			acctLoginDto = new AccountLoginDTO();
	        acctLoginDto.setLoginId(AccountLoginDTO.GENERIC_USER);
			acctLoginDto.setLoginType(AccountLoginDTO.LoginType.IN);
		}
		acctLoginDto.setDbSessionId(sessionId);
        acctLoginDto.setLastLoginIp(ipAddress);
        acctLoginDto.setInvoiceId(invoiceId);
        // TODO: verify that license plate should be added
        acctLoginDto.setLicPlate(licPlate);
        acctLoginDto.setLicState(licState);
        acctLoginDto.setPaymentPlanId(paymentPlanId);
        
		return acctLoginDto;
    }

	private ViolatorDTO applyDiscountEligibility(ViolatorDTO violator) {
		List<InvoiceDTO> invoices = violator.getAllInvoices();
		for (int i = 0; i < invoices.size(); i++) {
			InvoiceDTO invoice = invoices.get(i);
			switch (i) {
			case 0: // waiver & adjustment eligible
				invoice.setAdjustmentEligible(true);
			case 1: // only waiver eligible
				invoice.setWaiverEligible(true);
			case 2:
				invoice.setOnlineDiscountEligible(true);
				break;
			default:
				return violator;
			}
		}
		return violator;
	}

	/**
	 * Create a new violator without invoices or violations.
	 * @param acctLoginDto logged-in account
	 * @return the new violator
	 * @see #newInvoices
	 * @see #newViolations
	 */
	private ViolatorDTO newTestViolatorDTO(AccountLoginDTO acctLoginDto/*, String licPlate, String licState*/) {
		ViolatorDTO result = new ViolatorDTO();
		result.setAccountLoginDTO(acctLoginDto);
		result.setFirstName(AccountFactory.ACCOUNT_NAME_FIRST);
		result.setLastName(AccountFactory.ACCOUNT_NAME_LAST);
		String licPlate = acctLoginDto.getLicPlate();
		String licState = acctLoginDto.getLicState();
		result.setLicPlateNbr(licPlate);
		result.setLicPlateState(licState);
		Calendar cal = Calendar.getInstance();
		result.setLastUpdatedDate(cal);
		return result;
	}

	List<InvoiceDTO> newInvoices(int offset) {
		List<InvoiceDTO> result = new ArrayList<InvoiceDTO>(3);
		Calendar cal = null;
		InvoiceDTO invoice = null;
		ViolationDTO violation = null;
		// BEGIN Invoice 1
		invoice = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
		result.add(invoice);
		invoice.setInvoiceId("040"+Integer.toString(22046939+offset*100));
		cal = Calendar.getInstance();
		cal.set(2004, 8, 28);
		invoice.setInvoiceDate(cal);
		cal = Calendar.getInstance();
		cal.set(2004, 9, 12);
		invoice.setDueDate(cal);
		invoice.setAdministrativeFee(30.00f);
		invoice.setInvoiceDue(33.75f);
		// Toll 1
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("SHT-SAMS-09-South");
		cal = Calendar.getInstance();
		cal.set(2004, 8, 19);
		violation.setDateTime(cal);
		// Toll 2
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("SHT-SAMS-10-South");
		cal = Calendar.getInstance();
		cal.set(2004, 8, 19);
		violation.setDateTime(cal);
		// Toll 3
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("SHT-SAMS-11-South");
		cal = Calendar.getInstance();
		cal.set(2004, 8, 20);
		violation.setDateTime(cal);
		// END Invoice 1
		// BEGIN Invoice 2
		invoice = new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND);
		result.add(invoice);
		invoice.setInvoiceId("06"+Integer.toString(2049678+offset*100));
		cal = Calendar.getInstance();
		cal.set(2004, 9, 18);
		invoice.setInvoiceDate(cal);
		cal = Calendar.getInstance();
		cal.set(2004, 10, 1);
		invoice.setDueDate(cal);
		invoice.setAdministrativeFee(30.00f);
		invoice.setInvoiceDue(37.50f);
		invoice.setPaymentsPending(40.00f);
		// Toll 1
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-08-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 2
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-09-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 3
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-10-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 4
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-11-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 5
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-12-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 6
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("FT-BEND-13-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 10);
		violation.setDateTime(cal);
		// END Invoice 2
		// BEGIN Invoice 3
		invoice = new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND);
		result.add(invoice);
		invoice.setInvoiceId("06"+Integer.toString(2086281+offset*100));
		invoice.setInvoiceDue(67.50f);
		cal = Calendar.getInstance();
		cal.set(2004, 9, 18);
		invoice.setInvoiceDate(cal);
		cal = Calendar.getInstance();
		cal.set(2004, 10, 1);
		invoice.setDueDate(cal);
		invoice.setAdministrativeFee(15.00f);
		invoice.setCollectionFee(50.00f);
		// Toll 1
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(0.75f);
		violation.setLocation("FT-BEND-10-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 2
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(0.75f);
		violation.setLocation("FT-BEND-11-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 9);
		violation.setDateTime(cal);
		// Toll 3
		violation = new ViolationDTO();
		invoice.addViolation(violation);
		violation.setAmountDue(1.00f);
		violation.setLocation("FT-BEND-12-South");
		cal = Calendar.getInstance();
		cal.set(2004, 9, 10);
		violation.setDateTime(cal);
		// END Invoice 3
		return result;
	}

	private List<ViolationDTO> newViolations() {
		List<ViolationDTO> result = new ArrayList<ViolationDTO>(3);
		Calendar cal = null;
		ViolationDTO violation = null;
		// Uninvoiced Toll 1
		violation = new ViolationDTO();
		result.add(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("SHT-SAMS-09-South");
		cal = Calendar.getInstance();
		cal.set(2004, 10, 19);
		violation.setDateTime(cal);
		// Uninvoiced Toll 2
		violation = new ViolationDTO();
		result.add(violation);
		violation.setAmountDue(1.25f);
		violation.setLocation("SHT-SAMS-09-South");
		cal = Calendar.getInstance();
		cal.set(2004, 10, 19);
		violation.setDateTime(cal);
		// Uninvoiced Toll 3
		violation = new ViolationDTO();
		result.add(violation);
		violation.setAmountDue(1.00f);
		violation.setLocation("SHT-SAMSE-06-East");
		cal = Calendar.getInstance();
		cal.set(2004, 10, 20);
		violation.setDateTime(cal);
		return result;
	}

//  private ViolatorDTO getTestViolatorDTO() {
//  ViolatorDTO violatorDTO = new ViolatorDTO();
//  violatorDTO.setFirstName("VFirst");
//  violatorDTO.setLastName("VLast");
//  violatorDTO.setLicPlateState("OK");
//  violatorDTO.setLicPlateNbr("MWK009");
//  violatorDTO.setStrLastUpdatedDate("3:40 PM CST 01/12/2007");
//
//  Collection invoices = getTestInvoices();
//  violatorDTO.setInvoices(invoices);
//  
//  violatorDTO.setTotalInvoiceDue(getTotalInvoiceDue(violatorDTO.getInvoices()));
//  violatorDTO.setTotalPastPayments(getTotalPastPayment(violatorDTO.getInvoices()));
//  violatorDTO.setTotalAmtDue(getTotalAmtDue(violatorDTO.getInvoices()));
//  violatorDTO.setTotalPayment(getTotalPayment(violatorDTO.getInvoices()));
//  violatorDTO.setTotalRemainingBalance(getTotalRemainingBalance(violatorDTO.getInvoices()));
//
//  return violatorDTO;        
//}
//
//private Collection getTestInvoices() {
//  Collection invoices = new ArrayList();
//
//  InvoiceDTO invoiceDTO = new InvoiceDTO();
//  invoiceDTO.setStrDueDate("11/30/2006");
//  invoiceDTO.setStrInvoiceDate("10/01/2006");
//  invoiceDTO.setInvoiceId("12345");
//  invoiceDTO.setAmtDue((float)22.50);
//  invoiceDTO.setAdministrativeFee((float)15.00);
//  invoiceDTO.setTotalInvoiceDue((float)25.50);
//  invoiceDTO.setPaymentMadeOnline((float)1.00);
//  invoiceDTO.setAmtAlreadyPaid((float)2.00);
//  invoiceDTO.setPayment((float)22.50);
//  
//  List violations = new ArrayList();
//  ViolationDTO violation =  new ViolationDTO();
//  violation.setStrDateTime("09/01/2002 09:22AM");
//  violation.setLocation("SAM HOUSTON-SOUTH LaneL 09");
//  violation.setAmtDue((float)5.00);
//  violations.add(violation);
//  
//  violation =  new ViolationDTO();
//  violation.setStrDateTime("09/01/2002 02:25PM");
//  violation.setLocation("SAM HOUSTON-SOUTH LaneL 10");
//  violation.setAmtDue((float)5.50);
//  violations.add(violation);
//  
//  invoiceDTO.setViolations(violations);
//  invoices.add(invoiceDTO);
//  
//  invoiceDTO = new InvoiceDTO();
//  invoiceDTO.setStrDueDate("12/30/2006");
//  invoiceDTO.setStrInvoiceDate("11/10/2006");
//  invoiceDTO.setAmtDue((float)25.00);
//  invoiceDTO.setAdministrativeFee((float)18.00);
//  invoiceDTO.setTotalInvoiceDue((float)26.60);
//  invoiceDTO.setPaymentMadeOnline((float)0.60);
//  invoiceDTO.setAmtAlreadyPaid((float)1.00);
//  invoiceDTO.setPayment((float)25.00);
//  
//  violations = new ArrayList();
//  violation =  new ViolationDTO();
//
//  violation.setStrDateTime("10/01/2002 07:22AM");
//  violation.setLocation("SAM HOUSTON-SOUTH LaneL 11");
//  violation.setAmtDue((float)3.00);
//  violations.add(violation);
//  
//  violation =  new ViolationDTO();
//  violation.setStrDateTime("10/05/2002 10:27PM");
//  violation.setLocation("SAM HOUSTON-SOUTH LaneL 12");
//  violation.setAmtDue((float)5.60);
//  violations.add(violation);
//  
//  invoiceDTO.setViolations(violations);
//  invoiceDTO.setInvoiceId("12346");
//  invoices.add(invoiceDTO);
//  
//  return invoices;
//}
	
	public ResultDTO makeInvoicePaymentCreditCard(AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			BigDecimal paymentAmount, String email) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultDTO makeInvoicePaymentEFT(AccountLoginDTO acctLoginDto,
			AccountEFTDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			BigDecimal paymentAmount, String email) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultDTO makePayment(ViolatorDTO violatorDTO,
			BillingInfoDTO billingInfoDTO, BigDecimal amount,
			String email) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ViolatorDTO loginViolatorPmtPlan(AccountLoginDTO acctLoginDto,
			String sessionId, String ipAddress, UserEnvDTO userEnvDto,
			String licPlate, String licState, String paymentPlanId,
			String dbSessionId) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}


	public CheckEligibilityResponseDTO checkEligibility(String licPlate, String licState, String invoiceNo, Long accountId, String ipAddress,
			String jsessionId, String sourceUserName, String user, String sessionId, Integer PmtPlanId, String source)
			throws EtccException {
		return null;
		
	}

	public SearchOpenViolationResponse searchOpenViolations(String licPlate, String licState) throws EtccException {
		return null;
	}

	public GetViolationResponseDTO getViolationsForCCRMA(String licPlate, String licState, String invoiceNo, Long accountId,
			String ipAddress, String jsessionId, String sourceUserName, String user, String sessionId,
			Integer PmtPlanId) throws Exception {
		return null;
	}

	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {
		return null;
	}
}
