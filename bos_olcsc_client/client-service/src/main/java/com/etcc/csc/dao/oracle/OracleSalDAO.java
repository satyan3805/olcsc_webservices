package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SalDAO;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.plsql.OLCSC_FRP_API;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N;
import com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_REC_N;
import com.etcc.csc.plsql.OLC_VPS_INV_ARR_N;
import com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_N_ACC;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_D;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_N;
import com.etcc.csc.service.App;
import com.etcc.csc.service.sal.CalculateSalPaymentProcResponse;
import com.etcc.csc.service.sal.CheckSalEligibilityProcResponse;
import com.etcc.csc.service.sal.GetInvoiceViolationsProcResponse;
import com.etcc.csc.service.sal.PostSalCcPaymentProcResponse;
import com.etcc.csc.service.sal.PostSalEftPaymentProcResponse;
import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;
import com.etcc.olcsc.attlas.exceptions.AttlasException;

/**
 * Implementation class for SAL data access
 * 
 */
public class OracleSalDAO extends SalDAO {
	static Logger logger = Logger.getLogger(OracleSalDAO.class.getName());

	public GetInvoiceViolationsProcResponse getInvoiceViolations(
			String violationDocumentId,String dbSessionId) throws Exception {
		GetInvoiceViolationsProcResponse resp = new GetInvoiceViolationsProcResponse();
		List<InvoiceType> invoices = new ArrayList<InvoiceType>();
		List<ViolationType> violations = new ArrayList<ViolationType>();
		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_VPS_INV_ARR_N[] O_VPS_INV_ARR_N = new OLC_VPS_INV_ARR_N[] { new OLC_VPS_INV_ARR_N() };
		boolean isUninnvoice=true;
			if (violationDocumentId == null){
				throw new AttlasException(20014, "INVALID_INVOICE");// ERR_CODE_INVALID_INVOICE
			}
			// Changed procedure  GET_INV_VIOLATIONS_BY_INV_NBR() to GET_INV_VIOLATION_BY_INV_NBR()
			//Push Notification Phase II dbSessionId
			new OLCSC_FRP_API(this.conn).GET_INV_VIOLATION_BY_INV_NBR(violationDocumentId.toString(), O_VPS_INV_ARR_N,O_ERROR_MSG_ARR,dbSessionId);
            
				if (O_VPS_INV_ARR_N[0]!=null && O_VPS_INV_ARR_N[0].getArray().length>0) {
						populateInvoices(O_VPS_INV_ARR_N[0].getArray(), invoices,violations,isUninnvoice);
						resp.setViolations(violations);
				}if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
					ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
					throw new AttlasException(Integer.parseInt(errors[0].getKey()),errors[0].getMessage());
				}
		return resp;
	}

	public CheckSalEligibilityProcResponse checkEligibility(String licPlateNum,
			String jurisdiction, String invoiceNum, Long accountId,String userName,String ipAddress,String sourceUserName,String jSessionId,String dbSessionId) throws AttlasException {

		logger.info("checkEligibility DAO-->"+licPlateNum+"  "+jurisdiction+"  "+invoiceNum+"  "+accountId);
		CheckSalEligibilityProcResponse resp = new CheckSalEligibilityProcResponse();

		String P_LIC_PLATE = licPlateNum;
		String P_LIC_STATE = jurisdiction;
		String P_INVOICE_NO = invoiceNum;
		Long P_ACCOUNT_ID = accountId;
		//PUSH NOTIFICATION PHASE II
		String P_DOC_TYPE = "AC";
		String P_USER = userName;
		String P_IP_ADDRESS = ipAddress;
		String P_SOURCE_USER_NAME = sourceUserName;
		String P_JSESSION_ID =jSessionId;
		String P_AGENCY = null;
		String[] O_SESSION_ID = new String[1];
		
		
		String[] P_IS_ELIGIBLE = new String[1];
		Timestamp P_LAST_UPD_DATE[] = new Timestamp[1];
		
		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_VPS_INV_ARR_N[] O_VPS_INV_ARR_N = new OLC_VPS_INV_ARR_N[] { new OLC_VPS_INV_ARR_N() };
		OLC_VPS_INV_ARR_N_ACC[] O_VPS_INV_ARR_N_ACC = new OLC_VPS_INV_ARR_N_ACC[] { new OLC_VPS_INV_ARR_N_ACC() };
		OLC_UNINVOICED_VIOLS_ARR_N [] O_UNINVOICED_VIOLS_ARR_N = new OLC_UNINVOICED_VIOLS_ARR_N [] { new OLC_UNINVOICED_VIOLS_ARR_N () };
		
		BigDecimal[] P_SERVICE_FEE = new BigDecimal[] { new BigDecimal(0.00) };
		
		Double totalInvTXNAmount;
		boolean isEligibled = false;
		boolean isUninnvoice = false;
		DecimalFormat twoDForm = new DecimalFormat("0.00");

		try {
			//QC_10317 Changes START 
			// added new package to check SalEligibility with accountId
			 if  (null != P_ACCOUNT_ID && P_ACCOUNT_ID > 0){
				 //new implemented logic
				 logger.info("calling OLCSC_FRP_API(this.conn).CHECK_SAL_ELIGIBILITY_ACCT----->  P_ACCOUNT_ID " + P_ACCOUNT_ID);
				//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
				 new OLCSC_FRP_API(this.conn).CHECK_SAL_ELIGIBILITY_ACCT(BigDecimal.valueOf(P_ACCOUNT_ID), 
						 P_IS_ELIGIBLE, P_LAST_UPD_DATE,P_SERVICE_FEE, 
						 O_VPS_INV_ARR_N_ACC,O_UNINVOICED_VIOLS_ARR_N, O_ERROR_MSG_ARR,P_AGENCY, 
						 P_DOC_TYPE,P_IP_ADDRESS,P_USER,P_JSESSION_ID,dbSessionId);
				 	 isEligibled = P_IS_ELIGIBLE[0].equals("Y");// "Y" or "N"
				   //if (isEligibled) {
					 logger.info("checkEligibility with CHECK_SAL_ELIGIBILITY_ACCT  isEligibled : " + isEligibled);
						resp.setIsEligible(isEligibled);
						resp.setLastFrpOn(P_LAST_UPD_DATE[0]);
						
						if (P_SERVICE_FEE[0]!=null)
						 resp.setServiceFee(P_SERVICE_FEE[0].doubleValue());
						
						if (O_VPS_INV_ARR_N_ACC[0]!=null && O_VPS_INV_ARR_N_ACC[0].getArray().length>0) {
							logger.info("In checkEligibility API, CHECK_SAL_ELIGIBILITY_ACCT flow ----> calling populateInvoices_by_acc() : ");
							totalInvTXNAmount = populateInvoices_by_acc(O_VPS_INV_ARR_N_ACC[0].getArray(), resp.getInvoices(), resp.getViolations(),isUninnvoice);
							logger.info("In checkEligibility API, CHECK_SAL_ELIGIBILITY_ACCT flow populated invocies totalInvTXNAmount: " + totalInvTXNAmount);
							resp.setTotalInvoicedAmount(Double.valueOf(twoDForm.format(totalInvTXNAmount)));
							
						}
						if(O_UNINVOICED_VIOLS_ARR_N[0]!=null && O_UNINVOICED_VIOLS_ARR_N[0].getArray().length>0){
							Double totalUnInvTXNAmount = populateUnInvoicedViolations(O_UNINVOICED_VIOLS_ARR_N[0].getArray(),resp.getViolations());
							resp.setTotalUninvoicedAmount(Double.valueOf(twoDForm.format(totalUnInvTXNAmount)));
						}
						/*if(P_IS_ELIGIBLE[0].equals("N")){
							//error message implementation
							if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
								ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
								resp.setErrorMessage(errors[0].getMessage()+"~"+errors[0].getKey());
						    }
						}*/
						
					/*} else {
						resp.setIsEligible(isEligibled);
						logger.info("checkEligibility with CHECK_SAL_ELIGIBILITY_ACCT  isEligibled : " + isEligibled);
						//error message implementation
						if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
							ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
							resp.setErrorMessage(errors[0].getMessage()+"~"+errors[0].getKey());
						
						}
					}*/
			 } else {
				 logger.info("calling OLCSC_FRP_API(this.conn).CHECK_SAL_ELIGIBILITY----->  P_LIC_PLATE " + P_LIC_PLATE +  "P_LIC_STATE " +P_LIC_STATE+ "P_INVOICE_NO "+ P_INVOICE_NO);
				//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
			  new OLCSC_FRP_API(this.conn).CHECK_SAL_ELIGIBILITY(P_LIC_PLATE,
					P_LIC_STATE, P_INVOICE_NO, P_DOC_TYPE,P_USER,P_IP_ADDRESS,P_SOURCE_USER_NAME,P_JSESSION_ID,P_IS_ELIGIBLE, P_LAST_UPD_DATE,
					P_SERVICE_FEE,O_VPS_INV_ARR_N,O_UNINVOICED_VIOLS_ARR_N, O_ERROR_MSG_ARR, P_AGENCY,O_SESSION_ID);
			  	isEligibled = P_IS_ELIGIBLE[0].equals("Y");// "Y" or "N"
			  //PUSH NOTIFICATION PHASE II dbSessionId
				resp.setDbSessionId(O_SESSION_ID[0].toString());
			  	if (isEligibled) {
			  		logger.info("checkEligibility with CHECK_SAL_ELIGIBILITY isEligibled : " + isEligibled);
					resp.setIsEligible(isEligibled);
					resp.setLastFrpOn(P_LAST_UPD_DATE[0]);
					resp.setServiceFee(P_SERVICE_FEE[0].doubleValue());					
					if (O_VPS_INV_ARR_N[0]!=null && O_VPS_INV_ARR_N[0].getArray().length>0) {
						logger.info("In checkEligibility CHECK_SAL_ELIGIBILITY flow ----> calling populateInvoices_by_acc() : ");
						totalInvTXNAmount = populateInvoices(O_VPS_INV_ARR_N[0].getArray(), resp.getInvoices(), resp.getViolations(),isUninnvoice);
						logger.info("In checkEligibility CHECK_SAL_ELIGIBILITY flow ----> populated invocies totalInvTXNAmount: " + totalInvTXNAmount);
						
						resp.setTotalInvoicedAmount(Double.valueOf(twoDForm.format(totalInvTXNAmount)));
						if(O_UNINVOICED_VIOLS_ARR_N[0]!=null && O_UNINVOICED_VIOLS_ARR_N[0].getArray().length>0){
							Double totalUnInvTXNAmount = populateUnInvoicedViolations(O_UNINVOICED_VIOLS_ARR_N[0].getArray(),resp.getViolations());
							logger.info("In checkEligibility CHECK_SAL_ELIGIBILITY flow  ------> populated UnInvoiced Violations totalInvTXNAmount: " + totalInvTXNAmount);
							resp.setTotalUninvoicedAmount(totalUnInvTXNAmount);
						}
					}
					
				//	QC_10317 Changes END
				} else {
					resp.setIsEligible(isEligibled);					
					logger.info("checkEligibility with CHECK_SAL_ELIGIBILITY  isEligibled : " + isEligibled);					
					//error message implementation
					if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
						ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
						resp.setErrorMessage(errors[0].getMessage()+"~"+errors[0].getKey());
					
					}
				}
			 }
		} catch (SQLException se) {
			logger.error("Error in checkEligibility() :" + se.getMessage(), se);
			AttlasException aex = AttlasException.translateException(se);
			throw aex;
		} catch (Exception e) {
			logger.error("General error in checkEligibility() :" + e.getMessage(), e);
			
		}

		return resp;
	}

	private Double populateInvoices(OLC_VPS_INV_REC_N[] O_VPS_INV_REC_H,
			List<InvoiceType> invoices, List<ViolationType> violations, boolean isUnInnvoice)
			throws Exception {
		Double totalInvTXNAmount = 0d;
		String pdfFilePath =null;
		final int length = O_VPS_INV_REC_H.length;
		if (invoices == null) {
			invoices = new ArrayList<InvoiceType>(length);
		}

		//loop for invoice list
		for (int i = 0; i < length; i++) {
			OLC_VPS_INV_REC_N inv = O_VPS_INV_REC_H[i];
			InvoiceType invType = new InvoiceType();
			// naphan
			logger.debug("populateInvoices: invoice id = " + inv.getINVOICE_ID());
			invType.setAgencyId(Long.parseLong(inv.getAGENCY_ID()));
			invType.setAgencyName(inv.getAGENCY_NAME());
			invType.setDocId(inv.getINVOICE_ID());// account_id?
			invType.setViolationDocId(Long.parseLong(inv.getINVOICE_ID()));
			
			//sum of invoice amount
			totalInvTXNAmount+=inv.getINVOICE_AMOUNT() != null ? inv.getINVOICE_AMOUNT().doubleValue() : 0.00;
			
			//QC_10261 changes start here
			   if (inv.getINVOICE_ID()!=null)
			    pdfFilePath =new App().getPdfFilepath(inv.getINVOICE_ID());
			   if (!StringUtil.isEmpty(pdfFilePath)){
				if (inv.getINVOICE_DATE() != null) {
					Calendar invDate = new GregorianCalendar();
					invDate.setTime(inv.getINVOICE_DATE());
					invType.setCreateDateTime(invDate);
				}
				if (inv.getDUE_DATE() != null) {
					Calendar dueDate = new GregorianCalendar();
					dueDate.setTime(inv.getDUE_DATE());
					invType.setDueDateTime(dueDate);
				}
				invType.setStatus("MAILED");//
			}else{
				invType.setCreateDateTime(null);
				invType.setDueDateTime(null);
				invType.setStatus("TO BE MAILED");//
			}
			invType.setBalance(inv.getTOLL_AMT() != null ? inv.getTOLL_AMT().doubleValue() : 0.00);
			invType.setAdminFees(inv.getADMIN_FEES() != null ? inv.getADMIN_FEES().doubleValue() : 0.00);
			invType.setOtherFees(inv.getOTHER_FEES() != null ? inv.getOTHER_FEES().doubleValue() : 0.00);
			invType.setInPayment(true);//
			invType.setWaveFees(true);//
			invType.setAddServiceFee(true);//
			if(isUnInnvoice && inv.getLINE_ITEMS()!=null){
				populateInvoicedViolations(inv.getLINE_ITEMS().getArray(), violations,invType);
			}
			invoices.add(invType);
		}
		return totalInvTXNAmount;
	}
	
	
	
	//OLC_VPS_INV_REC_N_ACC
	private Double populateInvoices_by_acc(OLC_VPS_INV_REC_N_ACC[] O_VPS_INV_REC_H_ACC,
			List<InvoiceType> invoices, List<ViolationType> violations, boolean isUnInnvoice)
			throws Exception {
		Double totalInvTXNAmount = 0d;
		final int length = O_VPS_INV_REC_H_ACC.length;
		String pdfFilePath =null;
		if (invoices == null) {
			invoices = new ArrayList<InvoiceType>(length);
		}

		//loop for invoice list
		for (int i = 0; i < length; i++) {
			OLC_VPS_INV_REC_N_ACC inv = O_VPS_INV_REC_H_ACC[i];
			InvoiceType invType = new InvoiceType();
			// naphan
			logger.debug("populateInvoices: invoice id = " + inv.getINVOICE_ID());
			invType.setAgencyId(Long.parseLong(inv.getAGENCY_ID()));
			invType.setAgencyName(inv.getAGENCY_NAME());
			invType.setDocId(inv.getINVOICE_ID());// account_id?
			invType.setViolationDocId(Long.parseLong(inv.getINVOICE_ID()));
			//QC-10317 Change START
			invType.setJurisdiction(inv.getLIC_STATE().toString());
			invType.setLicPlateNum(inv.getLIC_PLATE().toString());
			//QC-10317 Change END
			//sum of invoice amount
			totalInvTXNAmount+=inv.getINVOICE_AMOUNT() != null ? inv.getINVOICE_AMOUNT().doubleValue() : 0.00;
			//QC_10261 changes start here
			   if (inv.getINVOICE_ID()!=null)
			    pdfFilePath =new App().getPdfFilepath(inv.getINVOICE_ID());
			   if (!StringUtil.isEmpty(pdfFilePath)){
				if (inv.getINVOICE_DATE() != null) {
					Calendar invDate = new GregorianCalendar();
					invDate.setTime(inv.getINVOICE_DATE());
					invType.setCreateDateTime(invDate);
				}
				if (inv.getDUE_DATE() != null) {
					Calendar dueDate = new GregorianCalendar();
					dueDate.setTime(inv.getDUE_DATE());
					invType.setDueDateTime(dueDate);
				}
				invType.setStatus("MAILED");//
			}else{
				invType.setCreateDateTime(null);
				invType.setDueDateTime(null);
				invType.setStatus("TO BE MAILED");//
			}
			invType.setBalance(inv.getTOLL_AMT() != null ? inv.getTOLL_AMT().doubleValue() : 0.00);
			invType.setAdminFees(inv.getADMIN_FEES() != null ? inv.getADMIN_FEES().doubleValue() : 0.00);
			invType.setOtherFees(inv.getOTHER_FEES() != null ? inv.getOTHER_FEES().doubleValue() : 0.00);
			invType.setCollectionFee((inv.getCOLLECTION_FEES() != null)?inv.getCOLLECTION_FEES().floatValue() : 0);
			invType.setInPayment(true);//
			invType.setWaveFees(true);//
			invType.setAddServiceFee(true);//
			if(isUnInnvoice && inv.getLINE_ITEMS()!=null){
				populateInvoicedViolations(inv.getLINE_ITEMS().getArray(), violations,invType);
			}
			invoices.add(invType);
		}
		return totalInvTXNAmount;
	}
	

	private void populateInvoicedViolations(OLC_VPS_INV_REC_D[] array,
			List<ViolationType> violations, InvoiceType invType) throws Exception {

		if (violations == null) {
			violations = new ArrayList<ViolationType>(array.length);
		}

		//loop for un invoice violation list
		for (OLC_VPS_INV_REC_D olcVPSINVRECD : array) {
			ViolationType violType = new ViolationType();
			violType.setAgencyId(invType.getAgencyId());
			violType.setAgencyName(invType.getAgencyName());
			violType.setViolationId(olcVPSINVRECD.getTRANSACTION_ID().longValue());
			if (olcVPSINVRECD.getTRANSACTION_DATE() != null) {
				Calendar violationDate = new GregorianCalendar();
				violationDate.setTime(olcVPSINVRECD.getTRANSACTION_DATE());
				violType.setEventDateTime(violationDate);
			}
			violType.setEventLocation(olcVPSINVRECD.getV_LOCATION());
			violType.setTollAmount(olcVPSINVRECD.getAMOUNT().doubleValue());
			violType.setBalance(olcVPSINVRECD.getAMOUNT().doubleValue());
			//violType.setJurisdiction(olcVPSINVRECD.get)
			//violType.setLicPlateNum(olcVPSINVRECD.get)
		
			violations.add(violType);
		}
	}
	// Created for ViolationType uninvoice
	private Double populateUnInvoicedViolations(OLC_UNINVOICED_VIOLS_REC_N[] array,List<ViolationType> violations) throws Exception {

		Double totalUnInvTXNAmount = 0d;
		if (violations == null) {
			violations = new ArrayList<ViolationType>(array.length);
		}
		//loop for violation list
		for (OLC_UNINVOICED_VIOLS_REC_N  UNINVOICED_VIOLS : array) {
			ViolationType violType = new ViolationType();
			violType.setAgencyId(Long.parseLong(UNINVOICED_VIOLS.getAGENCY_ID()));
			violType.setAgencyName(UNINVOICED_VIOLS.getAGENCY_NAME());
			violType.setViolationId(UNINVOICED_VIOLS.getVIOLATION_ID().longValue());
			if (UNINVOICED_VIOLS.getVIOLATION_DATE_TIME() != null) {
				Calendar violationDate = new GregorianCalendar();
				violationDate.setTime(UNINVOICED_VIOLS.getVIOLATION_DATE_TIME());
				violType.setEventDateTime(violationDate);
			}
			violType.setEventLocation(UNINVOICED_VIOLS.getFULL_LOCATION_NAME());
			violType.setTollAmount(UNINVOICED_VIOLS.getAVI_AMT().doubleValue());
			totalUnInvTXNAmount+=violType.getTollAmount();
			violType.setBalance(UNINVOICED_VIOLS.getCASH_AMT().doubleValue());
			//QC-10317 Change START
			violType.setJurisdiction(UNINVOICED_VIOLS.getLIC_STATE());
			violType.setLicPlateNum(UNINVOICED_VIOLS.getLIC_PLATE());
			//QC-10317 Change END
			violations.add(violType);
		}
		return totalUnInvTXNAmount;
	}

	public CalculateSalPaymentProcResponse calculatePayment(String licPlateNum,
			String jurisdiction, String invoiceNum, List<InvoiceType> invoices,
			List<ViolationType> violations, Long accountId,String dbSessionId) throws AttlasException {

		CalculateSalPaymentProcResponse resp = new CalculateSalPaymentProcResponse();

		//OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_VPS_INV_ARR_N[] O_VPS_INV_ARR_N = new OLC_VPS_INV_ARR_N[] { new OLC_VPS_INV_ARR_N() };
		OLC_VPS_INV_ARR_N_ACC[]  O_VPS_INV_ARR_N_ACC = new  OLC_VPS_INV_ARR_N_ACC[] { new OLC_VPS_INV_ARR_N_ACC()};
		OLC_UNINVOICED_VIOLS_ARR_N [] O_UNINVOICED_VIOLS_ARR_N = new OLC_UNINVOICED_VIOLS_ARR_N [] { new OLC_UNINVOICED_VIOLS_ARR_N () };
		BigDecimal[] O_WAVED_FEES = new BigDecimal[] { new BigDecimal(0) };
		BigDecimal[] O_SAL_PAY_AMOUNT = new BigDecimal[] { new BigDecimal(0) };
		boolean isUnInnvoice=false;
		try {
			//QC_10317 Changes START 
			// added new package to check SalEligibility with accountId
			 if  (null != accountId && accountId > 0){
				 logger.info("calling OLCSC_FRP_API(this.conn).CAL_SAL_PAYMENT_AMOUNT_BY_ACCT ----->  account_Id " + accountId);
				//PUSH NOTIFICATION PHASE II dbSessionId
				 new OLCSC_FRP_API(this.conn).CAL_SAL_PMT_AMT_BY_ACCT(
						 BigDecimal.valueOf(accountId),O_VPS_INV_ARR_N_ACC,O_UNINVOICED_VIOLS_ARR_N,
							O_WAVED_FEES, O_SAL_PAY_AMOUNT,dbSessionId);
				 if (O_VPS_INV_ARR_N_ACC[0]!=null && O_VPS_INV_ARR_N_ACC[0].getArray().length>0) {
						populateInvoices_by_acc(O_VPS_INV_ARR_N_ACC[0].getArray(), invoices, violations,isUnInnvoice);
						logger.info("calling OLCSC_FRP_API.CAL_SAL_PAYMENT_AMOUNT_BY_ACCT ----> populateInvoices_by_acc : invoices " + invoices + "  violations" +violations + "  isUnInnvoice " +isUnInnvoice );
						if(O_UNINVOICED_VIOLS_ARR_N[0]!=null && O_UNINVOICED_VIOLS_ARR_N[0].getArray().length>0){
							populateUnInvoicedViolations(O_UNINVOICED_VIOLS_ARR_N[0].getArray(),violations);
							logger.info("calling OLCSC_FRP_API.CAL_SAL_PAYMENT_AMOUNT_BY_ACCT ----> populateUnInvoicedViolations : violations " +violations );
						}
						resp.getInvoices().addAll(invoices);
						resp.getViolations().addAll(violations);
						resp.setWavedFees(O_WAVED_FEES[0].doubleValue());
						resp.setSalPayAmount(O_SAL_PAY_AMOUNT[0].doubleValue());
					} else {
						logger.debug("No invoice record found for CAL_SAL_PAYMENT_AMOUNT_BY_ACCT");
					}
			 } else {
				 logger.info("calling OLCSC_FRP_API(this.conn).CALCULATE_SAL_PAYMENT_AMOUNT----->  licPlateNum " + licPlateNum +  "jurisdiction " +jurisdiction+ "invoiceNum "+ invoiceNum);
				//PUSH NOTIFICATION PHASE II dbSessionId
				 new OLCSC_FRP_API(this.conn).CALCULATE_SAL_PAYMENT_AMOUNT(
							licPlateNum, jurisdiction, invoiceNum, O_VPS_INV_ARR_N,O_UNINVOICED_VIOLS_ARR_N,
							O_WAVED_FEES, O_SAL_PAY_AMOUNT,dbSessionId);
				 if (O_VPS_INV_ARR_N[0]!=null && O_VPS_INV_ARR_N[0].getArray().length>0) {
						populateInvoices(O_VPS_INV_ARR_N[0].getArray(), invoices, violations,isUnInnvoice);
						logger.info("calling OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT ----> populateInvoices : invoices " + invoices + "  violations" +violations + "  isUnInnvoice " +isUnInnvoice );
						if(O_UNINVOICED_VIOLS_ARR_N[0]!=null && O_UNINVOICED_VIOLS_ARR_N[0].getArray().length>0){
							populateUnInvoicedViolations(O_UNINVOICED_VIOLS_ARR_N[0].getArray(),violations);
							logger.info("calling OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT ----> populateUnInvoicedViolations : violations " +violations );
						}
						resp.getInvoices().addAll(invoices);
						resp.getViolations().addAll(violations);
						resp.setWavedFees(O_WAVED_FEES[0].doubleValue());
						resp.setSalPayAmount(O_SAL_PAY_AMOUNT[0].doubleValue());
					} else {
						logger.debug("No invoice record found for CALCULATE_SAL_PAYMENT_AMOUNT");
					}
			 }
			//QC_10317 Changes END

		} catch (Exception e) {
			logger.error("General error in calculuatePayment() :" + e.getMessage(), e);
			throw new AttlasException(
					Integer.parseInt(AttlasException.ERR_CODE_GENERAL),
					e.getMessage());
		}

		return resp;
	}

	public PostSalCcPaymentProcResponse postCcPayment(String licPlateNum,
			String jurisdiction, String invoiceNum, List<InvoiceType> invoices,
			List<ViolationType> violations, String paymentType,
			String paymentMethod, String expireMonth, String expireYear,
			String fullName, String zipCode, String notes, String emailAddress,String paypageRegistrationId,CreditCardType cardType, Long accountId,String omniToken,String dbSessionId) throws Exception {
		PostSalCcPaymentProcResponse resp = new PostSalCcPaymentProcResponse();

		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_PAYMENT_INFO_REC OLC_PMT_INFO_REC = new OLC_PAYMENT_INFO_REC();
		OLC_VPS_INV_ARR_N[] O_VPS_INV_ARR_N = new OLC_VPS_INV_ARR_N[] { new OLC_VPS_INV_ARR_N() };
		OLC_VPS_INV_ARR_N_ACC[]  O_VPS_INV_ARR_N_ACC = new  OLC_VPS_INV_ARR_N_ACC[] { new OLC_VPS_INV_ARR_N_ACC()};
		BigDecimal[] pmt_amount = new BigDecimal[] { new BigDecimal(0) };
		boolean isUnInnvoice=true;
		// get payment info
		OLC_PMT_INFO_REC = createCCPaymentInfo(paymentType, paymentMethod,
				expireMonth, expireYear, fullName, zipCode, notes,paypageRegistrationId,cardType,omniToken);
		BigDecimal errorCode;

		// call payment
		//QC_10317 Changes START
		//Push Notification Phase II dbSessionId start
		 if  (null != accountId && accountId > 0){
			
			 logger.info("calling OLCSC_FRP_API(this.conn).PAY_INVOICE_BY_ACC_ID----->  account_Id " + accountId);
			//New package added with accountId to
			 errorCode = new OLCSC_FRP_API(this.conn).PAY_INVOICE_BY_ACC_ID(BigDecimal.valueOf(accountId),  OLC_PMT_INFO_REC,emailAddress,
						pmt_amount, O_VPS_INV_ARR_N_ACC, O_ERROR_MSG_ARR,dbSessionId);
		

		 } else {
		
		 errorCode = new OLCSC_FRP_API(this.conn).PAY_INVOICE(
				licPlateNum, jurisdiction, invoiceNum, OLC_PMT_INFO_REC,emailAddress,
				pmt_amount, O_VPS_INV_ARR_N, O_ERROR_MSG_ARR,dbSessionId);
		 }
				if (errorCode.intValue() == 0) {// success
			/*if (O_VPS_INV_ARR_N[0]!=null && O_VPS_INV_ARR_N[0].getArray().length > 0) {
				populateInvoices(O_VPS_INV_ARR_N[0].getArray(), invoices, violations,isUnInnvoice);
			}*/
					
		//QC_10317 Changes END
		//Push Notification Phase II dbSessionId end

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

	public PostSalEftPaymentProcResponse postEftPayment(String licPlateNum,
			String jurisdiction, String invoiceNum, List<InvoiceType> invoices,
			List<ViolationType> violations, String paymentType,
			String paymentMethod, String routingInfo, String accountType,
			String fullName, String notes, String emailAddress, Long accountId,String dbSessionID) throws Exception {
		PostSalEftPaymentProcResponse resp = new PostSalEftPaymentProcResponse();

		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_PAYMENT_INFO_REC OLC_PMT_INFO_REC = new OLC_PAYMENT_INFO_REC();
		OLC_VPS_INV_ARR_N[] O_VPS_INV_ARR_N = new OLC_VPS_INV_ARR_N[] { new OLC_VPS_INV_ARR_N() };
		OLC_VPS_INV_ARR_N_ACC[]  O_VPS_INV_ARR_N_ACC = new  OLC_VPS_INV_ARR_N_ACC[] { new OLC_VPS_INV_ARR_N_ACC()};
		BigDecimal[] pmt_amount = new BigDecimal[] { new BigDecimal(0) };
		boolean isUnInnvoice=true;
		BigDecimal errorCode;
		// get payment info
		OLC_PMT_INFO_REC = createEFTPaymentInfo(paymentType, paymentMethod,
				routingInfo, accountType, fullName, notes);
		
		//QC_10317 Changes START  
		//New package added with accountId 
		// call payment
		//Push Notification Phase II dbSessionId Start
		if  (null != accountId && accountId > 0){
			 
			logger.info("calling OLCSC_FRP_API(this.conn).PAY_INVOICE_BY_ACC_ID----->  account_Id " + accountId);
			 errorCode = new OLCSC_FRP_API(this.conn).PAY_INVOICE_BY_ACC_ID(BigDecimal.valueOf(accountId),  OLC_PMT_INFO_REC,emailAddress,
						pmt_amount, O_VPS_INV_ARR_N_ACC, O_ERROR_MSG_ARR,dbSessionID);
			
		 } else {
		  errorCode = new OLCSC_FRP_API(this.conn).PAY_INVOICE(
				licPlateNum, jurisdiction, invoiceNum, OLC_PMT_INFO_REC,emailAddress,
				pmt_amount, O_VPS_INV_ARR_N, O_ERROR_MSG_ARR,dbSessionID);
		 }
		
		//Push Notification Phase II dbSessionId end
		if (errorCode.intValue() == 0) {// success
			
			/*	if (O_VPS_INV_ARR_N[0]!=null && O_VPS_INV_ARR_N[0].getArray().length > 0) {
				populateInvoices(O_VPS_INV_ARR_N[0].getArray(), invoices, violations,isUnInnvoice);
			}*/
		//QC_10317 Changes END
			List<com.etcc.csc.service.webservice.PaymentType> paymentInfo = new ArrayList<com.etcc.csc.service.webservice.PaymentType>();
			com.etcc.csc.service.webservice.PaymentType pmtResult = new com.etcc.csc.service.webservice.PaymentType();
			pmtResult.setAgencyId(0);
			pmtResult.setAgencyName("");
			pmtResult.setPaymentType(PaymentType.EFT.getDisplay());
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

	private OLC_PAYMENT_INFO_REC createCCPaymentInfo(String paymentType,
			String paymentMethod, String expireMonth, String expireYear,
			String fullName, String zipCode, String notes,String paypageRegistrationId,CreditCardType cardType,String omniToken) throws SQLException {
		
		// initiate
		String tokenFromPA=null;
		try {
			//triPOS phase 2 changes start
			 if (StringUtils.isNotBlank(paypageRegistrationId) && StringUtils.isBlank(omniToken)){
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
				null, null,	StringUtil.extractFirstName(fullName), // FIRST_NAME
				StringUtil.extractFirstName(fullName), // LAST_NAME
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
				StringUtil.extractFirstName(fullName), // LAST_NAME
				null, null, null, null, null, null,// zipcode
				null, null, null, PaymentType.EFT.getCodeString(),// BANK_ACCT_TYPE
				paymentMethod,// BANK_ACCT_NUMBER
				routingInfo,null,null,null);// ROUTING_NBR

		return eftPmtInfo;

	}
}
