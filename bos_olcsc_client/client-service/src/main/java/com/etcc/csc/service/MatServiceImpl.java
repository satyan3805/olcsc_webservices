package com.etcc.csc.service;


import java.util.regex.Pattern;

import javax.ejb.Local;

import org.apache.log4j.Logger;

import com.etcc.csc.common.ErrorMessageEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.MatDAO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.service.mat.CheckMatEligibilityProcResponse;
import com.etcc.csc.service.mat.CheckMatEligibilityResponseType;
import com.etcc.csc.service.mat.CheckMatEligibilityType;
import com.etcc.csc.service.mat.PostMatCcPaymentProcResponse;
import com.etcc.csc.service.mat.PostMatCcPaymentResponseType;
import com.etcc.csc.service.mat.PostMatCcPaymentType;
import com.etcc.csc.service.mat.PostMatEftPaymentProcResponse;
import com.etcc.csc.service.mat.PostMatEftPaymentResponseType;
import com.etcc.csc.service.mat.PostMatEftPaymentType;
import com.etcc.csc.service.sal.PostSalCcPaymentType;
import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.olcsc.attlas.exceptions.AttlasException;


/**
 * Concrete implementation of the MatService integrated with calling FRP API procedures.
 *
 */
@Local
public class MatServiceImpl implements MatService {
	
	private static final String CC_NUM_PATTERN = "^[0-9 ]*$";
	private static final String CC_NUM_MASK_PATTERN = ".*[0-9]{4}$";
	private static final Pattern ccNumPattern = Pattern.compile(CC_NUM_PATTERN);
	private static final Pattern CcNumMaskPattern = Pattern.compile(CC_NUM_MASK_PATTERN);
	private static final String PLATE_IS_NOT_ELIGIBLE = "The given plate is NOT eligible to use the Miss-A-Toll Service.";
	private static final String UNINVOICED_TXN_NOT_FOUND = "There is no outstanding un-invoiced transaction associated to this plate number.";
	private static String[] errorParts=null;
    //private static String errorMsg=null;
    private static String errorMessage=null;
    private static String errorCode=null;
	
	static Logger logger = Logger.getLogger(MatServiceImpl.class.getName());
	
	public CheckMatEligibilityResponseType checkMatEligibility(CheckMatEligibilityType request)  throws EtccException{
		CheckMatEligibilityResponseType resp = new CheckMatEligibilityResponseType();
		resp.setResponse(new ResponseType());
		
		try {
			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			} else if (StringUtil.isEmpty(request.getLicPlateNum())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
			} else if (StringUtil.isEmpty(request.getJurisdiction())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
			}
			//PUSH NOTIFICATION PHASE II Adding userName,ipAddress,sourceUserName,jSessionId
			CheckMatEligibilityProcResponse procResp = getMatDao().checkEligibility(request.getLicPlateNum(), request.getJurisdiction(),request.getUserName(),request.getIpAddress(),request.getSourceUserName(),request.getjSessionId(),request.getDbSessionId());
			resp.setIsEligible(procResp.getIsEligible());
			//PUSH NOTIFICATION PHASE II Adding dbSessionId
			resp.setDbSessionId(procResp.getDbSessionId());
			//isEligible
			if(procResp.getIsEligible()) {
				resp.setMessage(procResp.getErrorMessage());
				resp.setLastMatPerformedDate(CoreDateUtil.dateToCalendar(procResp.getLastMatOn()));
				resp.setTotalAmount(procResp.getTotalAmount());
				resp.setMatPayAmount(procResp.getMatPayAmount());
				resp.setServiceFee(procResp.getServiceFee());				
				//found un-invoiced associated to given plate
				if (procResp.getViolations().size() > 0){
					resp.getViolations().addAll(procResp.getViolations());
				} else {
					//resp.setMessage(UNINVOICED_TXN_NOT_FOUND);
					resp.getResponse().setErrMsg(procResp.getErrorMessage());
				}
				// set response to indicate success
				resp.getResponse().setErrCode(0);
				resp.getResponse().setErrMsg("");
			 } else {	//not eligible
				//resp.setMessage(procResp.getErrorMessage());
				 if (procResp.getErrorMessage()!=null){
					  errorParts = procResp.getErrorMessage().split("~");
	                  errorMessage =errorParts[0];
	                  errorCode =errorParts[1];
					 resp.getResponse().setErrMsg(errorMessage);
					 resp.getResponse().setErrCode(Integer.parseInt(errorCode));
				}
			}
			
			
		} catch (AttlasException ex) {
				logger.error(String.format("%s: AttlasException - Error calling checkMatEligibility: %s", "MAT_ERR", ex.getMessage(), ex));
				resp.getResponse().setErrCode(ex.getErrCode());
				resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex ) {
				logger.error(String.format("%s: Error calling checkMatEligibility: %s", "MAT_ERR", ex.getMessage(), ex));
				resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
				resp.getResponse().setErrMsg(ex.getMessage());
		}		
		
		return resp;
	}
	
	public PostMatCcPaymentResponseType postMatCcPayment(PostMatCcPaymentType request, String emailAddress)  throws EtccException{
		PostMatCcPaymentResponseType resp = new PostMatCcPaymentResponseType();
		resp.setResponse(new ResponseType());
		
		try {
			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			}	
			else if (StringUtil.isEmpty(request.getLicPlateNum())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
			}
			else if (StringUtil.isEmpty(request.getJurisdiction())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
			}
			else if (StringUtil.isEmpty(request.getCardNumber()) || !CcNumMaskPattern.matcher(request.getCardNumber()).matches()){
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getMessage());
			}
			else if (StringUtil.isEmpty(request.getExpireMonth())|| StringUtil.isEmpty(request.getExpireYear())){
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_EXPIRE_DATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_EXPIRE_DATE.getMessage());
			}
			/*else if (StringUtil.isEmpty(request.getPaymentType()) || !isPaymentTypeValid(request)) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getMessage());
			}else if (StringUtil.isEmpty(request.getPaypageRegistrationId())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_PAYPAGEREGISTRATION_ID.getCode(), ErrorMessageEnum.ERR_CODE_PAYPAGEREGISTRATION_ID.getMessage());
			}*/else if (request.getCardType()==null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARDTYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARDTYPE.getMessage());
			}else if(request.getCardType()!=null && !request.getCardType().toString().equals(StringUtil.AMERICANEXPRESS) && validateCardExpiryDate(request)) {
				throw new AttlasException(ErrorMessageEnum.EXPIRATION_DATE_PAS.getCode(), ErrorMessageEnum.EXPIRATION_DATE_PAS.getMessage());
			}
			//PUSH NOTIFICATION PHASE II Adding dbSessionId
			PostMatCcPaymentProcResponse procResp = getMatDao().postCcPayment(
					request.getLicPlateNum(),
					request.getJurisdiction(),
					request.getViolations(),
					request.getPaymentType(),
					request.getCardNumber(), // in this context card number = payment method
					request.getExpireMonth(), 
					request.getExpireYear(),
					request.getFullName(), 
					request.getZipCode(),
					request.getNotes(), emailAddress,request.getPaypageRegistrationId(),request.getCardType(),request.getOmniToken(),request.getDbSessionId());
			
			// set response to indicate success
			resp.getPaymentInfo().addAll(procResp.getPaymentInfo());
			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
		} catch (AttlasException ex) {
			logger.error(String.format("%s: AttlasException - Error calling postMatCcPayment: %s", "MAT_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		}
		catch (Exception ex ) {
			logger.error(String.format("%s: Error calling PostMatCcPayment: %s", "MAT_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}
		
		return resp;
	}
	//PUSH NOTIFICATION PHASE II Adding dbSessionId
	public PostMatEftPaymentResponseType postMatEftPayment(PostMatEftPaymentType request, String emailAddress)  throws EtccException{
		PostMatEftPaymentResponseType resp = new PostMatEftPaymentResponseType();
		resp.setResponse(new ResponseType());
		
		try {
			//input validation
			if (request == null) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_GENERAL.getCode(), "No request initiated");
			}	
			else if (StringUtil.isEmpty(request.getLicPlateNum())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE.getMessage());// ERR_CODE_INVALID_PLATE
			}
			else if (StringUtil.isEmpty(request.getJurisdiction())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PLATE_STATE.getMessage());// ERR_CODE_INVALID_PLATE_STATE
			}
			else if (StringUtil.isEmpty(request.getAccountNumber()) || !ccNumPattern.matcher(request.getAccountNumber()).matches()){
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_CARD_NUMBER.getMessage());
			}
			/*else if (StringUtil.isEmpty(request.getAccountType()) || !isBankAccountTypeValid(request)) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_PAYMENT_TYPE.getMessage());
			}*/
			else if (StringUtil.isEmpty(request.getRoutingInfo())) {
				throw new AttlasException(ErrorMessageEnum.ERR_CODE_INVALID_ROUTING_INFO.getCode(), ErrorMessageEnum.ERR_CODE_INVALID_ROUTING_INFO.getMessage());
			}
			//PUSH NOTIFICATION PHASE II Adding dbSessionId
			PostMatEftPaymentProcResponse procResp = getMatDao().postEftPayment(
					request.getLicPlateNum(),
					request.getJurisdiction(), 
					request.getViolations(),
					PaymentType.EFT.getCodeString(),
					request.getAccountNumber(), // in this context, account number = payment method
					request.getRoutingInfo(), 
					request.getAccountType(),
					request.getFullName(), 
					request.getNotes(), emailAddress,request.getDbSessionId());
			
			// set response to indicate success
			resp.getPaymentInfo().addAll(procResp.getPaymentInfo());
			resp.getResponse().setErrCode(0);
			resp.getResponse().setErrMsg("");
		} catch (AttlasException ex) {
			logger.error(String.format("%s: AttlasException - Error calling postMatEftPayment: %s", "MAT_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(ex.getErrCode());
			resp.getResponse().setErrMsg(ex.getErrMsg());
		} catch (Exception ex ) {
			logger.error(String.format("%s: Error calling PostMatEftPayment: %s", "MAT_ERR", ex.getMessage(), ex));
			resp.getResponse().setErrCode(Integer.parseInt(AttlasException.ERR_CODE_GENERAL));
			resp.getResponse().setErrMsg(ex.getMessage());
		}
		
		return resp;
	}

	/*private boolean isBankAccountTypeValid(PostMatEftPaymentType request) {
		return request.getAccountType().equals("E");
	}
	
	private boolean isPaymentTypeValid(PostMatCcPaymentType request) {
		return request.getPaymentType().equals("C");
	}*/
	private boolean validateCardExpiryDate(PostMatCcPaymentType request) {
		String expiryDate=null;
			if(request.getExpireMonth()!=null && request.getExpireYear()!=null){
				expiryDate =request.getExpireMonth()+"/"+request.getExpireYear();
			}
		return StringUtil.validateCardExpiryDate(expiryDate);
	}

	public MatDAO getMatDao() throws EtccException {
		return DAOFactory.getDAOFactory().getDAO(MatDAO.class);
	}
	
}