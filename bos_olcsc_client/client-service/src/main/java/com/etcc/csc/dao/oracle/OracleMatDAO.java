package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.registry.infomodel.EmailAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.MatDAO;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N;
import com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_REC_N;
import com.etcc.csc.plsql.OLCSC_FRP_API;
import com.etcc.csc.service.mat.CheckMatEligibilityProcResponse;
import com.etcc.csc.service.mat.PostMatCcPaymentProcResponse;
import com.etcc.csc.service.mat.PostMatEftPaymentProcResponse;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;
import com.etcc.olcsc.attlas.exceptions.AttlasException;

/**
 * Implementation class for MAT data access.
 * 
 */
public class OracleMatDAO extends MatDAO {
	static Logger logger = Logger.getLogger(OracleMatDAO.class.getName());

	public CheckMatEligibilityProcResponse checkEligibility(String plate,
			String jurisdiction,String userName,String ipAddress,
			String sourceUserName,String jSessionId,String dbSessionId) throws AttlasException {

		CheckMatEligibilityProcResponse resp = new CheckMatEligibilityProcResponse();

		String P_LIC_PLATE = plate;
		String P_LIC_STATE = jurisdiction;
		//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
		String P_USER = userName;
		String P_IP_ADDRESS = ipAddress;
		String P_DOC_TYPE = "AC";
		String P_SOURCE_USER_NAME = sourceUserName;
		String P_JSESSION_ID = jSessionId;		
		
		String P_AGENCY = null;
		String[] P_IS_ELIGIBLE = new String[1];
		Timestamp P_LAST_UPD_DATE[] = new Timestamp[1];
		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_UNINVOICED_VIOLS_ARR_N[] o_uninvoiced_viols_arr = new OLC_UNINVOICED_VIOLS_ARR_N[] { new OLC_UNINVOICED_VIOLS_ARR_N() };
		BigDecimal[] P_SERVICE_FEE = new BigDecimal[] { new BigDecimal(0.00) };
		BigDecimal[] P_TOTAL_AMOUNT = new BigDecimal[] { new BigDecimal(0.00) };
		BigDecimal[] P_MAT_PAY_AMOUNT = new BigDecimal[] { new BigDecimal(0.00) };
		boolean isEligibled = false;
		String[] O_SESSION_ID = new String[1];

		try {
			//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
			new OLCSC_FRP_API(this.conn).CHECK_MAT_ELIGIBILITY(P_LIC_PLATE,
					P_LIC_STATE,P_DOC_TYPE,P_USER,P_IP_ADDRESS,P_SOURCE_USER_NAME,P_JSESSION_ID,o_uninvoiced_viols_arr, P_IS_ELIGIBLE,P_LAST_UPD_DATE, P_TOTAL_AMOUNT, P_SERVICE_FEE,
					P_MAT_PAY_AMOUNT, O_ERROR_MSG_ARR, P_AGENCY,O_SESSION_ID);

			isEligibled = P_IS_ELIGIBLE[0].equals("Y");// "Y" or "N"
			//PUSH NOTIFICATION PHASE II dbSessionId
			resp.setDbSessionId(O_SESSION_ID[0].toString());
			if (isEligibled) {
				resp.setIsEligible(isEligibled);
				resp.setLastMatOn(P_LAST_UPD_DATE[0]);
				resp.setTotalAmount(P_TOTAL_AMOUNT[0].doubleValue());
				resp.setMatPayAmount(P_MAT_PAY_AMOUNT[0].doubleValue());
				resp.setServiceFee(P_SERVICE_FEE[0].doubleValue());				
				if (o_uninvoiced_viols_arr[0]!=null){
					populateUnInvoicedViolations(o_uninvoiced_viols_arr[0].getArray(), resp.getViolations());
				}
				resp.setErrorMessage("");
			} else {
				resp.setIsEligible(isEligibled);
				if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
					ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
					resp.setErrorMessage(errors[0].getMessage()+"~"+errors[0].getKey());
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AttlasException(Integer.parseInt(AttlasException.ERR_CODE_GENERAL), e.getMessage());
		}

		return resp;
	}

	public PostMatCcPaymentProcResponse postCcPayment(String plate,
			String jurisdiction, List<ViolationType> violations,
			String paymentType, String paymentMethod, String expireMonth,
			String expireYear, String fullName, String zipCode, String notes, String emailAddress,String paypageRegistrationId,CreditCardType cardType,String omniToken,String dbSessionId)
			throws Exception {

		PostMatCcPaymentProcResponse resp = new PostMatCcPaymentProcResponse();

		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_PAYMENT_INFO_REC OLC_PMT_INFO_REC = new OLC_PAYMENT_INFO_REC();
		OLC_UNINVOICED_VIOLS_ARR_N[] o_uninvoiced_viols_arr = new OLC_UNINVOICED_VIOLS_ARR_N[] { new OLC_UNINVOICED_VIOLS_ARR_N() };
		BigDecimal[] pmt_amount = new BigDecimal[] { new BigDecimal(0) };
		// get payment info
		OLC_PMT_INFO_REC = createCCPaymentInfo(paymentType, paymentMethod,
				expireMonth, expireYear, fullName, zipCode, notes,paypageRegistrationId,cardType,omniToken);

		// call payment
		//PUSH NOTIFICATION PHASE II dbSessionId
		BigDecimal errorCode = new OLCSC_FRP_API(this.conn).PAY_TOLL(plate,
				jurisdiction, o_uninvoiced_viols_arr, OLC_PMT_INFO_REC,emailAddress,
				pmt_amount, O_ERROR_MSG_ARR,dbSessionId);

		if (errorCode.intValue() == 0) {// success
			if (o_uninvoiced_viols_arr[0].getArray().length > 0) {
				populateUnInvoicedViolations(o_uninvoiced_viols_arr[0].getArray(), violations);
			}

			List<com.etcc.csc.service.webservice.PaymentType> paymentInfo = new ArrayList<com.etcc.csc.service.webservice.PaymentType>();
			com.etcc.csc.service.webservice.PaymentType pmtResult = new com.etcc.csc.service.webservice.PaymentType();
			pmtResult.setAgencyId(0);
			pmtResult.setAgencyName("");
			pmtResult.setPaymentType(PaymentType.CREDIT.getCodeString());
			pmtResult.setPaymentAmount(pmt_amount[0].doubleValue());
			pmtResult.setPaymentStatus("Success");
			pmtResult.setErrorMessage("");
			paymentInfo.add(pmtResult);

			resp.setPaymentInfo(paymentInfo);
		} else {
			ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
			throw new AttlasException(Integer.parseInt(errors[0].getKey()),errors[0].getMessage());
		}
		return resp;
	}

	public PostMatEftPaymentProcResponse postEftPayment(String plate,
			String jurisdiction, List<ViolationType> violations,
			String paymentType, String paymentMethod, String routingInfo,
			String accountType, String fullName, String notes,  String emailAddress,String dbSessionId) throws Exception {
		PostMatEftPaymentProcResponse resp = new PostMatEftPaymentProcResponse();

		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_PAYMENT_INFO_REC OLC_PMT_INFO_REC = new OLC_PAYMENT_INFO_REC();
		OLC_UNINVOICED_VIOLS_ARR_N[] o_uninvoiced_viols_arr = new OLC_UNINVOICED_VIOLS_ARR_N[] { new OLC_UNINVOICED_VIOLS_ARR_N() };
		BigDecimal[] pmt_amount = new BigDecimal[] { new BigDecimal(0) };

		// get payment info
		OLC_PMT_INFO_REC = createEFTPaymentInfo(paymentType, paymentMethod,
				routingInfo, accountType, fullName, notes);

		// call payment
		//PUSH NOTIFICATION PHASE II dbSessionId
		BigDecimal errorCode = new OLCSC_FRP_API(this.conn).PAY_TOLL(plate,
				jurisdiction, o_uninvoiced_viols_arr, OLC_PMT_INFO_REC,emailAddress, 
				pmt_amount, O_ERROR_MSG_ARR,dbSessionId);

		if (errorCode.intValue() == 0) {// success
			if (o_uninvoiced_viols_arr[0].getArray().length > 0) {
				populateUnInvoicedViolations(o_uninvoiced_viols_arr[0].getArray(), violations);
			}

			List<com.etcc.csc.service.webservice.PaymentType> paymentInfo = new ArrayList<com.etcc.csc.service.webservice.PaymentType>();
			com.etcc.csc.service.webservice.PaymentType pmtResult = new com.etcc.csc.service.webservice.PaymentType();
			pmtResult.setAgencyId(0);
			pmtResult.setAgencyName("");
			pmtResult.setPaymentType(paymentType);
			pmtResult.setPaymentAmount(pmt_amount[0].doubleValue());
			pmtResult.setPaymentStatus("Success");
			pmtResult.setErrorMessage("");
			paymentInfo.add(pmtResult);
			resp.setPaymentInfo(paymentInfo);

		} else {
			ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
			throw new AttlasException(Integer.parseInt(errors[0].getKey()),errors[0].getMessage());
		}
		return resp;
	}

	private void populateUnInvoicedViolations(
			OLC_UNINVOICED_VIOLS_REC_N[] array, List<ViolationType> violations)
			throws Exception {

		if (violations == null) {
			violations = new ArrayList<ViolationType>(array.length);
		}

		for (OLC_UNINVOICED_VIOLS_REC_N olcVIOLSREC : array) {
			ViolationType violType = new ViolationType();
			violType.setAgencyId(Long.parseLong(olcVIOLSREC.getAGENCY_ID()));
			violType.setAgencyName(olcVIOLSREC.getAGENCY_NAME());
			violType.setViolationId(olcVIOLSREC.getVIOLATION_ID().longValue());
			if (olcVIOLSREC.getVIOLATION_DATE_TIME() != null) {
				Calendar violationDate = new GregorianCalendar();
				violationDate.setTime(olcVIOLSREC.getVIOLATION_DATE_TIME());
				violType.setEventDateTime(violationDate);
			}
			violType.setEventLocation(olcVIOLSREC.getVIOLATION_LOCATION());
			violType.setTollAmount(olcVIOLSREC.getCASH_AMT().doubleValue());
			violType.setBalance(olcVIOLSREC.getCASH_AMT().doubleValue());

			violations.add(violType);
		}
	}

	private OLC_PAYMENT_INFO_REC createCCPaymentInfo(String paymentType,
			String paymentMethod, String expireMonth, String expireYear,
			String fullName, String zipCode, String notes,String paypageRegistrationId,CreditCardType cardType,String omniToken) throws SQLException {

		// initiate
		String tokenFromPA=null;
		try {
			  //triPOS phase 2 changes start
			 if (StringUtils.isNotBlank(paypageRegistrationId) && StringUtils.isBlank(omniToken)){
			  //get the token based on paypage
			  tokenFromPA =CCTokenDAOHelper.getInstance().getCCToken(null, cardType.toString(), paypageRegistrationId, paymentMethod.substring(paymentMethod.length() - 4), null);
			 }else if (StringUtils.isBlank(paypageRegistrationId) && StringUtils.isNotBlank(omniToken)){
			   //set the tripos ominiToken
		      tokenFromPA = omniToken;
			 }
			//triPOS phase 2 changes end
		} catch (EtccException e) {
			// TODO Auto-generated catch block
			logger.error(" Error while retrieving the token from createCCPaymentInfo " , e );
		}
		OLC_PAYMENT_INFO_REC ccPmtInfo = new OLC_PAYMENT_INFO_REC(
				PaymentType.CREDIT.getCodeString(),
				null, // PERSON_ID
				paymentMethod.substring(paymentMethod.length() - 4), // CARD_NBR
				WSDateUtil.monthYearToTimestamp(expireMonth + "/" + expireYear), // CARD_EXPIRES
				new BigDecimal(cardType.getType()), // CARD_TYPE
				fullName, // CARD_NAME
				null, null, null, new BigDecimal(tokenFromPA), //Token 
				null, null, StringUtil.extractFirstName(fullName), // FIRST_NAME
				StringUtil.extractLastName(fullName), // LAST_NAME	//defect 10187 txphung
				null, null, null, null, null, zipCode,// zipcode
				null, null, null, null, null, null,null,null,null);

		return ccPmtInfo;

	}

	private OLC_PAYMENT_INFO_REC createEFTPaymentInfo(String paymentType,
			String paymentMethod, String routingInfo, String accountType,
			String fullName, String notes) throws SQLException {

		// initiate
		OLC_PAYMENT_INFO_REC eftPmtInfo = new OLC_PAYMENT_INFO_REC(
				PaymentType.EFT.getCodeString(), null, null, null,
				new BigDecimal(7),// CARD_TYPE
				null, null, null, null, null, null, null,
				StringUtil.extractFirstName(fullName), // FIRST_NAME
				StringUtil.extractLastName(fullName), // LAST_NAME	//defect 10187 txphung
				null, null, null, null, null, null,// zipcode
				null, null, null, PaymentType.EFT.getCodeString(),// BANK_ACCT_TYPE
				paymentMethod,// BANK_ACCT_NUMBER
				routingInfo,null,null,null);// ROUTING_NBR

		return eftPmtInfo;

	}

}
