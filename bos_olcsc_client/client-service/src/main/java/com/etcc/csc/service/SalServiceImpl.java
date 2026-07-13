package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.etcc.csc.common.ErrorMessageEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.SalDAO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.service.sal.CalculateSalPaymentProcResponse;
import com.etcc.csc.service.sal.CalculateSalPaymentResponseType;
import com.etcc.csc.service.sal.CalculateSalPaymentType;
import com.etcc.csc.service.sal.CheckSalEligibilityProcResponse;
import com.etcc.csc.service.sal.CheckSalEligibilityResponseType;
import com.etcc.csc.service.sal.CheckSalEligibilityType;
import com.etcc.csc.service.sal.GetInvoiceViolationsProcResponse;
import com.etcc.csc.service.sal.GetInvoiceViolationsResponseType;
import com.etcc.csc.service.sal.GetInvoiceViolationsType;
import com.etcc.csc.service.sal.PostSalCcPaymentProcResponse;
import com.etcc.csc.service.sal.PostSalCcPaymentResponseType;
import com.etcc.csc.service.sal.PostSalCcPaymentType;
import com.etcc.csc.service.sal.PostSalEftPaymentProcResponse;
import com.etcc.csc.service.sal.PostSalEftPaymentResponseType;
import com.etcc.csc.service.sal.PostSalEftPaymentType;
import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.olcsc.attlas.exceptions.AttlasException;

public class SalServiceImpl implements SalService {

	private static final String CC_NUM_PATTERN = "^[0-9 ]*$";
	private static final String CC_NUM_MASK_PATTERN = ".*[0-9]{4}$";
	private static final Pattern ccNumPattern = Pattern.compile(CC_NUM_PATTERN);
	private static final Pattern CcNumMaskPattern = Pattern.compile(CC_NUM_MASK_PATTERN);
	private static String[] errorParts=null;
    //private static String errorMsg=null;
    private static String errorMessage=null;
    private static String errorCode=null;
    boolean accountIdPresent = false;
	boolean invoicePresent = false;
	
	static Logger logger = Logger.getLogger(SalServiceImpl.class.getName());

	public CheckSalEligibilityResponseType checkSalEligibility(
			CheckSalEligibilityType request) throws EtccException {
		CheckSalEligibilityResponseType resp = new CheckSalEligibilityResponseType();
		resp.setResponse(new ResponseType());
		CheckSalEligibilityProcResponse procResp;
		InvoiceType iType= new InvoiceType();
		try {
			
			//input validation
			if (request == null) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			} 
			//QC_10317 Changes START ----------- 
			if(request.getAccountId()> 0 && Long.valueOf(request.getAccountId())!=null){
				logger.info("Calling checkSalEligibility() with accountId and accountIdPresent : " +accountIdPresent);
				accountIdPresent = true;
			} else {
				
				if(!StringUtil.isEmpty(request.getLicPlateNum()) && !StringUtil.isEmpty(request.getJurisdiction()) && !StringUtil.isEmpty(request.getInvoiceNum())){
						logger.info(" Calling checkSalEligibility() with licPlateNum, jurisdiction and invoiceNum  && invoicePresent : " +invoicePresent);
						invoicePresent = true;
				} else if (StringUtil.isEmpty(request.getLicPlateNum())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
				} else if (StringUtil.isEmpty(request.getJurisdiction())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
				} else if (StringUtil.isEmpty(request.getInvoiceNum())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getMessage());// ERR_CODE_INVALID_INVOICE
				} 
			}
			
			if(accountIdPresent || invoicePresent){
				logger.info("checkEligibility() SalServiceImpl----->"+request.getLicPlateNum()+"  "+request.getJurisdiction()+"  "+ request.getInvoiceNum()+ " " +request.getAccountId());
					//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
					procResp = getSalDao().checkEligibility(request.getLicPlateNum(), request.getJurisdiction(), request.getInvoiceNum(), request.getAccountId()
							,request.getUserName(),request.getIpAddress(),request.getSourceUserName(),request.getjSessionId(),request.getDbSessionId());
			
			//isEligble
			if(Long.valueOf(request.getAccountId())!=null && request.getAccountId() >0){
				resp.setIsEligible(procResp.getIsEligible());
				resp.setMessage(resp.getMessage());
				resp.setLastSalPerformedDate(CoreDateUtil.dateToCalendar(procResp.getLastFrpOn()));
				resp.setTotalInvoicedAmount(procResp.getTotalInvoicedAmount());
				resp.setTotalUninvoicedAmount(procResp.getTotalUninvoicedAmount());
				resp.setServiceFee(procResp.getServiceFee());
				resp.setInvoices(procResp.getInvoices());
				resp.setViolations(procResp.getViolations());
				// set response to indicate success
				if (procResp.getErrorMessage()!=null){
					  errorParts = procResp.getErrorMessage().split("~");
	                  errorMessage =errorParts[0];
	                  errorCode =errorParts[1];
				 resp.getResponse().setErrMsg(errorMessage);
				 resp.getResponse().setErrCode(Integer.parseInt(errorCode));
				}else{
					resp.getResponse().setErrCode(0);
					resp.getResponse().setErrMsg("");
				}
				
			} else{
				//PUSH NOTIFICATION PHASE II Adding dbSessionId
				resp.setDbSessionId(procResp.getDbSessionId());
				if (procResp.getIsEligible()) {
					resp.setIsEligible(procResp.getIsEligible());
					resp.setMessage(resp.getMessage());
					resp.setLastSalPerformedDate(CoreDateUtil.dateToCalendar(procResp.getLastFrpOn()));
					resp.setTotalInvoicedAmount(procResp.getTotalInvoicedAmount());
					resp.setTotalUninvoicedAmount(procResp.getTotalUninvoicedAmount());
					resp.setServiceFee(procResp.getServiceFee());
					resp.setInvoices(procResp.getInvoices());
					resp.setViolations(procResp.getViolations());
					// set response to indicate success
					resp.getResponse().setErrCode(0);
					resp.getResponse().setErrMsg("");
				} else {
					resp.setIsEligible(procResp.getIsEligible());
					//Error
						if (procResp.getErrorMessage()!=null){
							  errorParts = procResp.getErrorMessage().split("~");
			                  errorMessage =errorParts[0];
			                  errorCode =errorParts[1];
						 resp.getResponse().setErrMsg(errorMessage);
						 resp.getResponse().setErrCode(Integer.parseInt(errorCode));
						}
					} //QC_10317 Changes END 
			}
		 }
		} catch (AttlasException ex) {
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex) {
			logger.error(String.format("%s: Error calling CheckSalEligibility: %s", "SAL_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}
		return resp;
	}

	public CalculateSalPaymentResponseType calculateSalPayment(
			CalculateSalPaymentType request) throws EtccException {
		List<InvoiceType> invoices = new ArrayList<InvoiceType>();
		List<ViolationType> violations = new ArrayList<ViolationType>();

		CalculateSalPaymentResponseType resp = new CalculateSalPaymentResponseType();
		resp.setResponse(new ResponseType());
		CalculateSalPaymentProcResponse procResp;

		try {
			
			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			} 
			//QC_10317 Changes START 
			if(request.getAccountId()> 0 && Long.valueOf(request.getAccountId())!=null){
				logger.info("Calling calculateSalPayment() with accountId and accountIdPresent : " +accountIdPresent);
				accountIdPresent = true;
			} else {
				
				if(!StringUtil.isEmpty(request.getLicPlateNum()) && !StringUtil.isEmpty(request.getJurisdiction()) && !StringUtil.isEmpty(request.getInvoiceNum())){
					logger.info("Calling calculateSalPayment() with licPlateNum, jurisdiction and invoiceNum  && invoicePresent : " +invoicePresent);
					invoicePresent = true;
				} else if(request.getAccountId()<= 0 ){
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_ACCOUNT_ID.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_ACCOUNT_ID.getMessage());			
				} else if (StringUtil.isEmpty(request.getLicPlateNum())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
			    } else if (StringUtil.isEmpty(request.getJurisdiction())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
			    } else if (StringUtil.isEmpty(request.getInvoiceNum())) {
						throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getMessage());// ERR_CODE_INVALID_INVOICE
				}
				
			}
			
			if(accountIdPresent || invoicePresent){
				logger.info("calculateSalPayment SalServiceImpl-->"+request.getLicPlateNum()+"  "+request.getJurisdiction()+"  "+ request.getInvoiceNum()+ " " +request.getAccountId());
				//PUSH NOTIFICATION PHASE II Adding dbSessionId
				procResp = getSalDao().calculatePayment(request.getLicPlateNum(),
					request.getJurisdiction(), request.getInvoiceNum(), invoices, violations, request.getAccountId(),request.getDbSessionId());
				//QC_10317 Changes END 

			// set response to indicate success
			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
			if (procResp.getInvoices().size() > 0) {
				resp.getViolations().addAll(procResp.getViolations());
				resp.getInvoices().addAll(procResp.getInvoices());
				resp.setSalPayAmount(procResp.getSalPayAmount());
				resp.setWavedFees(procResp.getWavedFees());
				

			} else {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getMessage());// ERR_CODE_INVALID_INVOICE
			}
			}
		} catch (AttlasException ex) {
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex) {
			logger.error(String.format("%s: Error calling CalculateSalPayment: %s", "SAL_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}

		return resp;
	}

	public PostSalCcPaymentResponseType postSalCcPayment(
			PostSalCcPaymentType request, String emailAddress) throws EtccException {
		List<InvoiceType> invoices = new ArrayList<InvoiceType>();
		List<ViolationType> violations = new ArrayList<ViolationType>();
		PostSalCcPaymentResponseType resp = new PostSalCcPaymentResponseType();
		resp.setResponse(new ResponseType());
		PostSalCcPaymentProcResponse procResp;

		try {
			
			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			} 
			//QC_10317 Changes START
			if(request.getAccountId()> 0 && Long.valueOf(request.getAccountId())!=null){
				logger.info("Calling postSalCcPayment() with accountId and accountIdPresent : " +accountIdPresent);
				accountIdPresent = true;
			} else {
				if(!StringUtil.isEmpty(request.getLicPlateNum()) && !StringUtil.isEmpty(request.getJurisdiction()) && !StringUtil.isEmpty(request.getInvoiceNum())){
					logger.info(" Calling checkSalEligibility() with licPlateNum, jurisdiction and invoiceNum  && invoicePresent : " +invoicePresent);
					invoicePresent = true;
				} else if(StringUtil.isEmpty(request.getLicPlateNum())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());
				} else if (StringUtil.isEmpty(request.getJurisdiction())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());
				} else if (StringUtil.isEmpty(request.getInvoiceNum())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getMessage());
				} else if (StringUtil.isEmpty(request.getCardNumber())
						|| !CcNumMaskPattern.matcher(request.getCardNumber()).matches()) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getMessage());
				} else if (StringUtil.isEmpty(request.getExpireMonth())
						|| StringUtil.isEmpty(request.getExpireYear())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_EXPIRE_DATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_EXPIRE_DATE.getMessage());
				}/* else if (StringUtil.isEmpty(request.getPaymentType())
						|| !isPaymentTypeValid(request)) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getMessage());
				}else if (StringUtil.isEmpty(request.getPaypageRegistrationId())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_PAYPAGEREGISTRATION_ID.getCode(), ErrorMessageEnum.ERR_CODE_PAYPAGEREGISTRATION_ID.getMessage());
				}*/else if (request.getCardType()==null) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARDTYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARDTYPE.getMessage());
				}else if(request.getCardType()!=null && !request.getCardType().toString().equals(StringUtil.AMERICANEXPRESS)  && validateCardExpiryDate(request)){
					throw new AttlasException(ErrorMessageEnum.EXPIRATION_DATE_PAS.getCode(), ErrorMessageEnum.EXPIRATION_DATE_PAS.getMessage());
				}
			}
				
			if(accountIdPresent || invoicePresent){
				logger.info("postSalCcPayment SalServiceImpl-->"+request.getLicPlateNum()+"  "+request.getJurisdiction()+"  "+ request.getInvoiceNum()+ " " +request.getAccountId());
					
				//QC_10317 Changes END
				//PUSH NOTIFICATION PHASE II Adding dbSessionId
			 procResp = getSalDao().postCcPayment(
					request.getLicPlateNum(),
					request.getJurisdiction(),
					request.getInvoiceNum(),
					invoices,
					violations,
					request.getPaymentType(),
					request.getCardNumber(), // in this context card number =
												// payment method
					request.getExpireMonth(), request.getExpireYear(),
					request.getFullName(), request.getZipCode(),
					request.getNotes(), emailAddress,request.getPaypageRegistrationId(),request.getCardType(),	request.getAccountId(),request.getOmniToken(),request.getDbSessionId());

			// set response to indicate success.
			resp.getPaymentInfo().addAll(procResp.getPaymentInfo());
			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
			}
		} catch (AttlasException ex) {
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex) {
			logger.error(String.format("%s: Error calling PostSalCcPayment: %s", "SAL_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}

		return resp;
	}

	public PostSalEftPaymentResponseType postSalEftPayment(
			PostSalEftPaymentType request, String emailAddress) throws EtccException {
		List<InvoiceType> invoices = new ArrayList<InvoiceType>();
		List<ViolationType> violations = new ArrayList<ViolationType>();
		PostSalEftPaymentProcResponse procResp;
		PostSalEftPaymentResponseType resp = new PostSalEftPaymentResponseType();
		resp.setResponse(new ResponseType());

		try {

			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			} 
			//QC_10317 Changes START 
			if(request.getAccountId()> 0 && Long.valueOf(request.getAccountId())!=null){
				logger.info("Calling postSalEftPayment() with accountId and accountIdPresent : " +accountIdPresent);
				accountIdPresent = true;
			} else {
				
				if(!StringUtil.isEmpty(request.getLicPlateNum()) && !StringUtil.isEmpty(request.getJurisdiction()) && !StringUtil.isEmpty(request.getInvoiceNum())){
						logger.info(" Calling postSalEftPayment() with licPlateNum, jurisdiction and invoiceNum  && invoicePresent : " +invoicePresent);
						invoicePresent = true;
			
				} else if (StringUtil.isEmpty(request.getLicPlateNum())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
				} else if (StringUtil.isEmpty(request.getJurisdiction())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
				} else if (StringUtil.isEmpty(request.getInvoiceNum())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_INVOICE.getMessage());// ERR_CODE_INVALID_INVOICE
				} else if (StringUtil.isEmpty(request.getBankAccountNumber())
						|| !ccNumPattern.matcher(request.getBankAccountNumber())
								.matches()) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getMessage());
				} /*else if (StringUtil.isEmpty(request.getBankAccountType())
						|| !isBankAccountTypeValid(request)) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getMessage());
				}*/ else if (StringUtil.isEmpty(request.getRoutingInfo())) {
					throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_ROUTING_INFO.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_ROUTING_INFO.getMessage());
				}
			}
			
			if(accountIdPresent || invoicePresent){
				logger.info("postSalEftPayment() SalServiceImpl----->"+request.getLicPlateNum()+"  "+request.getJurisdiction()+"  "+ request.getInvoiceNum()+ " " +request.getAccountId());
				//PUSH NOTIFICATION PHASE II Adding dbSessionId
			 procResp = getSalDao()
					.postEftPayment(
							request.getLicPlateNum(),
							request.getJurisdiction(),
							request.getInvoiceNum(),
							invoices,
							violations,
							PaymentType.EFT.getCodeString(),
							request.getBankAccountNumber(), // in this context,
							// account number =
							// payment method
							request.getRoutingInfo(),
							request.getBankAccountType(),
							request.getFullName(), request.getNotes(), emailAddress, request.getAccountId(),request.getDbSessionId() );

			resp.getPaymentInfo().addAll(procResp.getPaymentInfo());
			// set response to indicate success
			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
			} //QC_10317 Changes END  
		} catch (AttlasException ex) {
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex) {
			logger.error(String.format(
					"%s: Error calling PostSalEftPayment: %s", "SAL_ERR",
					ex.getMessage(), ex));
			resp.getResponse().setErrCode(
					Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}

		return resp;
	}

	/*private boolean isBankAccountTypeValid(PostSalEftPaymentType request) {
		return request.getBankAccountType().equals("E");
	}

	private boolean isPaymentTypeValid(PostSalCcPaymentType request) {
		return request.getPaymentType().equals("C");
	}*/
	private boolean validateCardExpiryDate(PostSalCcPaymentType request) {
		String expiryDate=null;
			if(request.getExpireMonth()!=null && request.getExpireYear()!=null){
				expiryDate =request.getExpireMonth()+"/"+request.getExpireYear();
			}
		return StringUtil.validateCardExpiryDate(expiryDate);
	}

	public GetInvoiceViolationsResponseType getInvoiceViolations(
			GetInvoiceViolationsType request) throws EtccException {
		GetInvoiceViolationsResponseType resp = new GetInvoiceViolationsResponseType();

		resp.setResponse(new ResponseType());

		try {
			GetInvoiceViolationsProcResponse procRes = getSalDao()
					.getInvoiceViolations(request.getViolationDocumentId(),request.getDbSessionId());
			resp.setViolations(procRes.getViolations());

			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
		} catch (AttlasException ex) {
			logger.error(String.format("%s: Error calling GetInvoiceViolations: %s", "SAL_ERR",	ex.getMessage(), ex));
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex) {
			logger.error(String.format("%s: Error calling GetInvoiceViolations: %s", "SAL_ERR",	ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}

		return resp;
	}

	public SalDAO getSalDao() throws EtccException {
		return DAOFactory.getDAOFactory().getDAO(SalDAO.class);
	}

}
