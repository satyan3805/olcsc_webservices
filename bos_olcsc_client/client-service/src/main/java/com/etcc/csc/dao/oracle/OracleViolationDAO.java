/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.cart.ShoppingCart;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dao.oracle.util.PersonAndAddressDAOHelper;
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
import com.etcc.csc.dto.OlcAccountPlateRec;
import com.etcc.csc.dto.OlcInvPmtRec;
import com.etcc.csc.dto.OlcOpenFeeRec;
import com.etcc.csc.dto.OlcPaymentInfoRec;
import com.etcc.csc.dto.OlcPmtPlanHstTypeRec;
import com.etcc.csc.dto.OlcPmtPlanInstTypeRec;
import com.etcc.csc.dto.OlcPmtPlanTypeRec;
import com.etcc.csc.dto.OlcUninvPmtRec;
import com.etcc.csc.dto.OlcVpsAccountConvRec;
import com.etcc.csc.dto.OlcVpsInvRec;
import com.etcc.csc.dto.OlcVpsInvTypeRec;
import com.etcc.csc.dto.OlcVpsPlateAgcyRec;
import com.etcc.csc.dto.OlcVpsPlateTypeRec;
import com.etcc.csc.dto.OlcVpsUninvoicedViolsRec;
import com.etcc.csc.dto.PaymentPlanDTO;
import com.etcc.csc.dto.PaymentPlanDTO.PaymentPlanDetailDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SearchOpenViolationResponse;
import com.etcc.csc.dto.ViolationDTO;
import com.etcc.csc.dto.ViolationDetails;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.payment.checkoutxml.CartItemXmlDetails;
import com.etcc.csc.payment.checkoutxml.CartXML;
import com.etcc.csc.plsql.OLCSC_INV_VPS;
import com.etcc.csc.plsql.OLCSC_LOGIN;
import com.etcc.csc.plsql.OLCSC_PAYMENT;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_INV_PMT_ARR;
import com.etcc.csc.plsql.OLC_INV_PMT_REC;
import com.etcc.csc.plsql.OLC_OPEN_FEE_ARR;
import com.etcc.csc.plsql.OLC_OPEN_FEE_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.plsql.OLC_UNINV_PMT_ARR;
import com.etcc.csc.plsql.OLC_UNINV_PMT_REC;
import com.etcc.csc.plsql.OLC_VPS_BILLING_INFO_REC;
import com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR;
import com.etcc.csc.plsql.OLC_VPS_INVOICES_REC;
import com.etcc.csc.plsql.OLC_VPS_INV_ARR_D;
import com.etcc.csc.plsql.OLC_VPS_INV_ARR_H;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_D;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_H;
import com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_ARR;
import com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_REC;
import com.etcc.csc.plsql.OLC_VPS_UNINVOICED_VIOLS_REC;
import com.etcc.csc.plsql.OLC_PMT_PLAN_PMT_HST_TYPE_REC;
import com.etcc.csc.plsql.OLC_VPS_ACCOUNT_CONV_REC;
import com.etcc.csc.plsql.OLC_VPS_PLATE_AGCY_REC;
import com.etcc.csc.plsql.OLC_VPS_PLATE_TYPE_ARR;
import com.etcc.csc.plsql.OLC_VPS_INV_TYPE_ARR;
import com.etcc.csc.plsql.OLC_VPS_UNINVOICED_VIOLS_ARR;
import com.etcc.csc.plsql.OLC_PMT_PLAN_TYPE_ARR;
import com.etcc.csc.plsql.OLC_PMT_PLAN_INST_PMT_HST_ARR;
import com.etcc.csc.plsql.OLC_PMT_PLAN_INST_TYPE_ARR;
import com.etcc.csc.plsql.OLC_VPS_PLATE_AGCY_ARR;
import com.etcc.csc.plsql.OLC_PMT_PLAN_INST_TYPE_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_PLATE_REC;
import com.etcc.csc.plsql.OLC_VPS_PLATE_TYPE_REC;
import com.etcc.csc.plsql.OLC_VPS_INV_TYPE_REC;
import com.etcc.csc.plsql.OLC_PMT_PLAN_TYPE_REC;
import com.etcc.csc.service.App;
import com.etcc.csc.util.CartItemTypeEnum;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;

import oracle.xdb.XMLType;
/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Copied from OLCSCService com.etcc.csc.dao.OracleNewViolationDAO.
public class OracleViolationDAO extends ViolationDAO {
	
    private static final Logger logger = Logger.getLogger(OracleViolationDAO.class);
    /*public ViolatorDTO makePayment(ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress) throws EtccException, EtccSecurityException {
        int result = -2;
        try {
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            AccountLoginDTO accountLogin = violatorDTO.getAccountLoginDTO();
            long accountId = accountLogin.getAcctId();
            PaymentType paymentType = paymentMethod.getPaymentType();
            final boolean debugEnabled = logger.isDebugEnabled();
            final boolean infoEnabled = logger.isInfoEnabled();
            if (debugEnabled) {
                StringBuilder sb = new StringBuilder("new OLCSC_PAYMENT(conn).");
                switch (paymentType) {
                case CREDIT:
                    sb.append("MAKE_CC_VPC_PAYMENT(");
                	break;
                case EFT:
                    sb.append("MAKE_EFT_VPC_PAYMENT(");
                	break;
                }
                sb.append("P_DOC_ID=");
                sb.append((accountId > 0 ? Long.valueOf(accountId) : accountLogin.getInvoiceId()));
                sb.append(", P_DOC_TYPE=");
                sb.append((accountId > 0 ? AccountLoginDTO.LoginType.AC : AccountLoginDTO.LoginType.IN));
                sb.append(", OLC_VPS_INVOICES_ARR origin=");
                sb.append(toStringBuilder(violatorDTO.getShoppingCart().getAllInvoices(), violatorDTO));
                switch (paymentType) {
                case CREDIT:
					  // 20130201  Sending Address1, zipcode and plus4 for OLVPS CC payments
                	AccountCreditCardDTO ccPayment = (AccountCreditCardDTO) paymentMethod;
                    sb.append(", P_CARD_TYPE=").append(ccPayment.getCardType());
                    sb.append(", P_CARD_NUMBER length=**").append(ccPayment.getCardNbr().length());
                    sb.append(", P_ZIP_CODE=").append(ccPayment.getZipCode());
                    sb.append(", P_PLUS4=").append(ccPayment.getPlus4());
                    sb.append(", P_ADDRESS_LINE1=").append(ccPayment.getAddress1());
                    sb.append(", P_EXPIRATION_DATE=****");
                	break;
                case EFT:
                	AccountEFTDTO eftPayment = (AccountEFTDTO) paymentMethod;
                    sb.append(", I_BANK_ACCOUNT_TYPE=").append(eftPayment.getAccountType());
                    sb.append(", I_ROUTING_NUMBER length=**").append(eftPayment.getRoutingNumber().length());
                    sb.append(", I_BANK_ACCT_NUMBER length=**").append(eftPayment.getAccountNumber().length());
                	break;
                }
                sb.append(", P_SESSION_ID=").append(accountLogin.getDbSessionId());
                sb.append(", P_IP_ADDRESS=").append(ipAddress);
                sb.append(", P_USER=").append(accountLogin.getLoginId());
                sb.append(", confirmationNumber, O_ERROR_MSG_ARR, Y, __AGENCY__");
                sb.append(')');
                logger.debug("makePayment: " + sb.toString());
            }

            // process all shopping cart bins
            BigDecimal confirmationNumber[] = new BigDecimal[1];
            for (CartItem.ItemType binType : CartItem.ItemType.values()) {
            	BigDecimal ret = null;
            	CartList bin = violatorDTO.getShoppingCart().getBin(binType);
            	if (bin != null && !bin.isEmpty()) {
            		switch (binType) {
            		case HCTRA:
            		case FORT_BEND:
            		    // HCTRA & FORT_BEND are handled the same
            			@SuppressWarnings("unchecked")
            			OLC_VPS_INVOICES_ARR oracleInvoices = convertInvoices((Collection<InvoiceDTO>)bin.collection(), violatorDTO);
            			String agency = binType.getAgency().getCode();
            			logger.debug("makePayment.agency=" + agency);
            			//String postingXML = generatePostingXML(paymentMethod,violatorDTO);
						// select the payment method
        			    //XMLType xmlPosting = XMLType.createXML(conn, postingXML);
        			    OLC_PAYMENT_INFO_REC PAYMENT_INFO_ARR = convertToPaymentInfo(paymentMethod);
						ret = new OLCSC_PAYMENT(this.conn).MAKE_PAYMENT(new BigDecimal((accountId>0?Long.toString(accountId):accountLogin.getInvoiceId())),
        			    		(accountId>0?AccountLoginDTO.LoginType.AC:AccountLoginDTO.LoginType.IN).toString(),
        			    		accountLogin.getDbSessionId(), accountLogin.getLastLoginIp(),
        			    		accountLogin.getLoginId(),
        	                    null, null,"Y", PAYMENT_INFO_ARR, "N", O_ERROR_MSG_ARR);

						break;
            		case ACCOUNT:
            		    // TODO: new account creation not supported
            		case TAG:
                        // TODO: new tag addition not supported
            		default:
            			logger.error("Unsupported cart items encountered: " + binType);
            		}
            		if (ret != null) {
            			result = ret.intValue();
            			if (infoEnabled)
            				logger.info("makePayment.result=" + result);
            			if (result <= 0) {
            				logger.info("makePayment Error encountered.");
            			}
            		} else {
            			// null return value indicates an error???
            			logger.warn("makePayment.ret=" + ret);
            			result = 0;
            		}
            		if (result > 0) {
            			//success: set the confirmation number and continue processing
            			String cc = null;
            			if (confirmationNumber != null && confirmationNumber.length > 0)
            				cc = confirmationNumber[0].toString();
            			bin.setConfirmationCode(cc);
            		} else if (result < 0) {
            			logger.info("Security Error encountered.");
            			throw new EtccSecurityException("Security exception in makePayment (" + result + ").");
            		} else { //error (result == 0)
            			// STOP payment processing on first error
            			violatorDTO.addErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR, binType.getDisplay()));
            			if (debugEnabled) {
            				StringBuilder sb = new StringBuilder("makePayment[");
            				sb.append("docid=").append((accountId>0?Long.valueOf(accountId):accountLogin.getInvoiceId()))
            				.append(";doctype=").append((accountId>0?AccountLoginDTO.LoginType.AC:AccountLoginDTO.LoginType.IN));
            				switch (paymentType) {
            				case CREDIT:
            					AccountCreditCardDTO ccPayment = (AccountCreditCardDTO) paymentMethod;
            					sb.append(";cardCode=").append(ccPayment.getCardType())
            					.append(";cardNumber=****") + ccDTO.getCardNbr()
            					.append(";cardExpires=").append(WSDateUtil.monthYearToTimestamp(ccPayment.getCardExpires()));
            					break;
            				case EFT:
            					AccountEFTDTO eftPayment = (AccountEFTDTO) paymentMethod;
            					sb.append(";acctType=").append(eftPayment.getAccountType())
            					.append(";routingNumber=****") + eftDTO.getRoutingNumber()
            					.append(";acctNumber=****"); + eftDTO.getAccountNumber()
            					break;
            				}
            				sb.append(";dbsessionid=").append(accountLogin.getDbSessionId())
            				.append(";ipAddress=").append(ipAddress)
            				.append(";loginId=").append(accountLogin.getLoginId())
            				.append("]");
            				logger.debug(sb.toString());
            			}
        				if (logger.isTraceEnabled())
		                    logger.trace("makePayment.invoices=" + violatorDTO.getAllInvoices());
        				if (debugEnabled)
        					logger.debug("makePayment.errors=" + ErrorMessageDTO.toStringBuilder(violatorDTO.getErrors()));
            			break;  // on error, stop processing bins
            		}
            	}
            }
        } catch (Exception e) {
        	String message = "Exception in OracleViolationDAO.makePayment:" + e.getMessage();
            throw new EtccException(message, e);
        }
        return violatorDTO;
    }*/



	private OLC_PAYMENT_INFO_REC convertToPaymentInfo(
			AccountPaymentMethodDTO paymentMethod) throws SQLException {

	   OLC_PAYMENT_INFO_REC billingRec = new OLC_PAYMENT_INFO_REC();

		if (paymentMethod.getPaymentType() == PaymentType.EFT)
		{
			AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethod;
			billingRec.setPMT_TYPE(PaymentType.EFT.getCodeString());
			billingRec.setBANK_ACCT_TYPE(eftDto.getAccountType().getCode());
	        billingRec.setROUTING_NBR(eftDto.getRoutingNumber());
	        billingRec.setBANK_ACCT_NUMBER(eftDto.getAccountNumber());
		}
		else if (paymentMethod.getPaymentType() == PaymentType.CREDIT)
		{

		  AccountCreditCardDTO ccDto = (AccountCreditCardDTO) paymentMethod;
		  billingRec = new OLC_PAYMENT_INFO_REC(
                      PaymentType.CREDIT.getCodeString(),null,
                      ccDto.getCardNbr().substring(ccDto.getCardNbr().length()-4),WSDateUtil.monthYearToTimestamp(ccDto.getCardExpires()),
                      new BigDecimal(ccDto.getCardType().getType()),ccDto.getCardType().name(),
                      null, ccDto.isPrimary()?"Y":"N", "Y",new BigDecimal(ccDto.getToken()),
                      null,null,ccDto.getNameOnCard().split(" ")[0],
                      ccDto.getNameOnCard().split(" ")[1],ccDto.getAddress1(),ccDto.getAddress2(),ccDto.getCity(),ccDto.getState(),ccDto.getCountry(),ccDto.getZipCode(),
                      ccDto.getPlus4(),null,null,null,null,null,null,null,null);
		}
		return billingRec;
	}

	public PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID, String licPlate, String sessionID, String ipAddress, long acctID) throws EtccException, EtccSecurityException {
        try {
            PaymentPlanDTO paymentPlanDTO = new PaymentPlanDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            Timestamp[] lastUpdate = new Timestamp[1];
            Timestamp[] firstPaymentDate = new Timestamp[1];
            BigDecimal[] firstPaymentAmt = new BigDecimal[1];
            String[] partyName = new String[1];
            String[] partyAddress = new String[1];
            String[] phoneNumber = new String[1];
            BigDecimal[] totalPlanAmt = new BigDecimal[1];
            BigDecimal[] totalAmtPaid = new BigDecimal[1];
            BigDecimal[] totalAmtDue = new BigDecimal[1];
            BigDecimal[] totalDeliquent = new BigDecimal[1];
            OLC_VPS_PAY_PLAN_ARR[] O_VPS_PAY_PLAN_ARR =
                new OLC_VPS_PAY_PLAN_ARR[] { new OLC_VPS_PAY_PLAN_ARR() };
            final boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled){
                StringBuilder sb = new StringBuilder(512);
                sb.append("new OLCSC_INV_VPS(conn).PAYMENT_PLAN(");
                sb.append("sessionID=").append(sessionID);
                sb.append(", ipAddress=").append(ipAddress);
                sb.append(", licKey=").append(licKey);
                sb.append(", invoiceID=").append(invoiceID);
                sb.append(", licPlate=").append(licPlate);
                sb.append(", lastUpdate=").append(lastUpdate);
                sb.append(", firstPaymentDate=").append(firstPaymentDate);
                sb.append(", firstPaymentAmt=").append(firstPaymentAmt);
                sb.append(", partyName=").append(partyName);
                sb.append(", partyAddress=").append(partyAddress);
                sb.append(", phoneNumber=").append(phoneNumber);
                sb.append(", totalPlanAmt=").append(totalPlanAmt);
                sb.append(", totalAmtPaid=").append(totalAmtPaid);
                sb.append(", totalAmtDue=").append(totalAmtDue);
                sb.append(", totalDeliquent=").append(totalDeliquent);
                sb.append(", O_VPS_PAY_PLAN_ARR, O_ERROR_MSG_ARR)");
                logger.debug("getPaymentPlan: " + sb.toString());
            }
            BigDecimal result = new OLCSC_INV_VPS(this.conn).PAYMENT_PLAN(
            		/*String P_SESSION*/sessionID,
            		/*String P_IP_ADDRESS*/ipAddress,
            		/*String P_LIC_KEY*/licKey,
            		/*String P_INVOICE*/invoiceID,
            		/*String P_LIC_PLATE*/licPlate,
            		/*java.sql.Timestamp P_LAST_UPDATED[]*/lastUpdate,
            		/*java.sql.Timestamp P_FIRST_PAYMENT_DATE[]*/firstPaymentDate,
            		/*java.math.BigDecimal P_FIRST_PAYMENT_AMT[]*/firstPaymentAmt,
            		/*String P_PARTY_NAME[]*/partyName,
            		/*String P_PARTY_ADDRESS[]*/partyAddress,
            		/*String P_PHONE_NUMBER[]*/phoneNumber,
            		/*java.math.BigDecimal P_TOTAL_PLAN_AMT[]*/totalPlanAmt,
            		/*java.math.BigDecimal P_TOTAL_AMT_PAID[]*/totalAmtPaid,
            		/*java.math.BigDecimal P_TOTAL_AMT_DUE[]*/totalAmtDue,
            		/*java.math.BigDecimal P_TOTAL_DELIQUENT[]*/totalDeliquent,
            		/*OLC_VPS_PAY_PLAN_ARR P_PAY_DETAILS[]*/O_VPS_PAY_PLAN_ARR,
            		/*OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[]*/O_ERROR_MSG_ARR);

            int ret = result.intValue();
            if (logger.isInfoEnabled())
                logger.info("getPaymentPlan.ret=" + ret);
            if (ret == 0) {
                paymentPlanDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (debugEnabled) {
                    logger.debug("getPaymentPlan.result=" + result);
                    logger.debug("getPaymentPlan.licKey=" + licKey + ";invoiceID=" + invoiceID + ";licPlate=" + licPlate + ";sessionID=" + sessionID + ";ipAddress=" + ipAddress + ";acctID=" + acctID);
                    logger.debug("getPaymentPlan.error=" + ErrorMessageDTO.toStringBuilder(paymentPlanDTO.getErrors()));
                }
            } else if (ret == -1) {
                throw new EtccSecurityException("Security exception in getPaymentPlan");
            } else {
                paymentPlanDTO.setLicPlate(licPlate);
                paymentPlanDTO.setLastUpdated(WSDateUtil.timestampToCalendar(lastUpdate[0]));
                if (firstPaymentDate[0] != null)
                    paymentPlanDTO.setFirstPaymentDate(WSDateUtil.timestampToCalendar(firstPaymentDate[0]));
                if (firstPaymentAmt[0] != null)
                    paymentPlanDTO.setFirstPaymentAmt(firstPaymentAmt[0].toString());
                if (partyName[0] != null)
                    paymentPlanDTO.setPartyName(partyName[0]);
                if (partyAddress[0] != null)
                    paymentPlanDTO.setPartyAddress(partyAddress[0]);
                if (phoneNumber[0] != null)
                    paymentPlanDTO.setPhoneNumber(phoneNumber[0]);
                if (totalPlanAmt[0] != null)
                    paymentPlanDTO.setTotalPlanAmt(totalPlanAmt[0].toString());
                if (totalAmtPaid[0] != null)
                    paymentPlanDTO.setTotalAmtPaid(totalAmtPaid[0].toString());
                if (totalAmtDue[0] != null)
                    paymentPlanDTO.setTotalAmtDue(totalAmtDue[0].toString());
                if (totalDeliquent[0] != null)
                    paymentPlanDTO.setTotalDeliquent(totalDeliquent[0].toString());

                populatePaymentPlanDetails(O_VPS_PAY_PLAN_ARR[0].getArray() , paymentPlanDTO);
            }
            return paymentPlanDTO;
        } catch (SQLException e) {
        	String message = "Exception in OracleNewViolationDAO.getPaymentPlan:";
            EtccException etccException = new EtccException(message + e.getMessage(), e);
            throw etccException;
        }
    }

    @Override
	protected ViolatorDTO loadInvoices(ViolatorDTO violatorDTO, String ipAddress, String licPlate, String licState, AgencyEnum agency) throws SQLException, EtccSecurityException {
    	AccountLoginDTO accountLoginDTO = violatorDTO.getAccountLoginDTO();
    	if (accountLoginDTO == null) {
    		logger.debug("loadInvoices.accountLoginDTO == null");
    		return violatorDTO;
    	}
    	OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
    	OLC_VPS_INV_ARR_H[] O_VPS_INV_ARR_H = new OLC_VPS_INV_ARR_H[] { new OLC_VPS_INV_ARR_H() };
    	String[] firstName = new String[1];
    	String[] lastName = new String[1];
    	Timestamp updatedTimestamp[] = new Timestamp[1];
    	String[] PayPlanExists = new String[1];
    	long accountId = accountLoginDTO.getAcctId();
    	String invoiceId = accountLoginDTO.getInvoiceId();
    	final boolean debugEnabled = logger.isDebugEnabled();
    	if (debugEnabled) {
    		StringBuilder sb = new StringBuilder("new OLCSC_INV_VPS(conn).GET_VPS_INVOICE(");
    		sb.append("sessionId=").append(accountLoginDTO.getDbSessionId());
    		sb.append(", ipAddress=").append(ipAddress);
    		sb.append(", P_USER_ID=").append(accountLoginDTO.getLoginId());
    		sb.append(", P_DOC_ID=");
    		sb.append(accountId > 0 ? Long.valueOf(accountId) : invoiceId);
    		sb.append(", P_DOC_TYPE=");
    		sb.append((accountId > 0 ? AccountLoginDTO.LoginType.AC : AccountLoginDTO.LoginType.IN));
    		sb.append(", licPlate=").append(licPlate);
    		sb.append(", licState=").append(licState);
    		sb.append(", invoiceId=").append(invoiceId);
    		sb.append(", firstName=").append(firstName);
    		sb.append(", lastName=").append(lastName);
    		sb.append(", updatedTimestamp=").append(updatedTimestamp);
    		sb.append(", O_VPS_INV_ARR_H").append(", O_ERROR_MSG_ARR");
    		sb.append(", PayPlanExists, true");
    		sb.append(", agency=").append(agency);
    		sb.append(')');
    		logger.debug("loadInvoices: " + sb.toString());
    	}
    	BigDecimal ret = new OLCSC_INV_VPS(this.conn).GET_VPS_INVOICE(
    			/*sessionId*/
    			accountLoginDTO.getDbSessionId(),
    			/*P_USER_ID*/
    			ipAddress, accountLoginDTO.getLoginId(),
    			/*P_DOC_ID*/
    			(accountId>0?new BigDecimal(accountId):new BigDecimal(invoiceId)),
    			/*P_DOC_TYPE*/
    			(accountId>0?AccountLoginDTO.LoginType.AC:AccountLoginDTO.LoginType.IN).toString(),
    			licPlate, licState,
    			invoiceId, firstName, lastName, updatedTimestamp,
    			O_VPS_INV_ARR_H, O_ERROR_MSG_ARR,
    			/*String P_PAY_PLAN_EXISTS[]*/
    			PayPlanExists,
    			true,
    			(agency == null ? null : agency.getCode()));
    	int result = ret.intValue();
    	if (logger.isInfoEnabled())
    		logger.info("loadInvoices.result=" + result);
    	if (result == 0) {
    		violatorDTO.addErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
    		if (debugEnabled) {
    			logger.debug("loadInvoices.result=" + result);
    			logger.debug("loadInvoices.sessionid=" + accountLoginDTO.getDbSessionId() + ";ipAddress=" + ipAddress + ";loginid=" + accountLoginDTO.getLoginId()
    					+ ";docid=" + (accountId > 0?Long.valueOf(accountId):invoiceId)
    					+ ";doctype=" + (accountId > 0?AccountLoginDTO.LoginType.AC :AccountLoginDTO.LoginType.IN)
    					+ ";licPlate=" + licPlate + ";licState=" + licState + ";invoiceid=" + invoiceId);
    			logger.debug("loadInvoices.error=" + ErrorMessageDTO.toStringBuilder(violatorDTO.getErrors()));
    		}
    	} else if (result < 0) {
    		throw new EtccSecurityException("Security exception in loadInvoices");
    		//naphan_2451
    	} else if (O_VPS_INV_ARR_H[0].getArray().length > 0){
    		violatorDTO.setLicPlateNbr(licPlate);
    		violatorDTO.setLicPlateState(licState);
    		violatorDTO.setFirstName(firstName[0]);
    		violatorDTO.setLastName(lastName[0]);
    		if (updatedTimestamp[0] != null) {
    			GregorianCalendar lastUpdatedDateTime = new GregorianCalendar();
    			lastUpdatedDateTime.setTime(updatedTimestamp[0]);
    			violatorDTO.setLastUpdatedDate(lastUpdatedDateTime);
    		}
    		violatorDTO.setPayPlanExists(PayPlanExists[0].equals("Y"));
    		populateInvoices(O_VPS_INV_ARR_H[0].getArray(), violatorDTO);
    	}
    	return violatorDTO;
    }

    public DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoiceDTO) throws EtccException,
            EtccSecurityException {
        try {
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            //oracle.sql.BLOB P_INVOICE_DOC[] = new oracle.sql.BLOB[1];
            String P_INVOICE_DOC[] = new String[1];
            AccountLoginDTO accountLogin = violatorDTO.getAccountLoginDTO();
            long accountId = accountLogin.getAcctId();
            boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled) {
                StringBuilder sb = new StringBuilder();
                sb.append("new OLCSC_INV_VPS(conn).GET_INVOICE_DOC(sessionId=");
                sb.append(accountLogin.getDbSessionId());
                sb.append(", ipAddress=").append(accountLogin.getLastLoginIp());
                sb.append(", P_USER_ID=").append(accountLogin.getLoginId());
                sb.append(", P_DOC_ID=").append(
                        accountId > 0 ? new BigDecimal(accountId) : new BigDecimal(accountLogin.getInvoiceId()));
                sb.append(", P_DOC_TYPE=").append(
                        accountId > 0 ? AccountLoginDTO.LoginType.AC : AccountLoginDTO.LoginType.IN);
                sb.append(", licPlate=").append(violatorDTO.getLicPlateNbr());
                sb.append(", licState=").append(violatorDTO.getLicPlateState());
                sb.append(", invoiceNumber=").append(invoiceDTO.getInvoiceId());
                sb.append(", P_INVOICE_DOC, O_ERROR_MSG_ARR, fromGui=").append(true);
                sb.append(", agency=").append(invoiceDTO.getAgency());
                sb.append(')');
                logger.debug("getInvoiceDoc: " + sb.toString());
            }
            BigDecimal result = new OLCSC_INV_VPS(this.conn).GET_INVOICE_DOC(accountLogin.getDbSessionId(),
                    accountLogin.getLastLoginIp(), accountLogin.getLoginId(),
                    (accountId > 0 ? new BigDecimal(accountId) : new BigDecimal(accountLogin.getInvoiceId())),
                    (accountId > 0 ? AccountLoginDTO.LoginType.AC : AccountLoginDTO.LoginType.IN).toString(),
                    violatorDTO.getLicPlateNbr(), violatorDTO.getLicPlateState(), invoiceDTO.getInvoiceId(),
                    P_INVOICE_DOC, O_ERROR_MSG_ARR, true, invoiceDTO.getAgency().getCode());

            if (logger.isDebugEnabled())
                logger.info("getInvoiceDoc.result=" + result);
            DocumentDTO doc;
            switch (result.intValue()) {
                case 0: {
                    doc = new DocumentDTO();
                    doc.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                    if (debugEnabled) {
                        logger.debug("getInvoiceDoc.result=" + result);
                        logger.debug("getInvoiceDoc.invoiceNumber=" + invoiceDTO.getInvoiceId()
                                + ";fromGui=true;dbsessionid=" + accountLogin.getDbSessionId()
                                + ";lastLoginIp=" + accountLogin.getLastLoginIp()
                                + ";loginId=" + accountLogin.getLoginId()
                                + ";docid="
                                + (accountId > 0 ? new BigDecimal(accountId) : new BigDecimal(accountLogin
                                        .getInvoiceId()))
                                + ";docType="
                                + (accountId > 0 ? AccountLoginDTO.LoginType.AC : AccountLoginDTO.LoginType.IN)
                                + ";licPlateNbr=" + violatorDTO.getLicPlateNbr()
                                + ";licState=" + violatorDTO.getLicPlateState());
                        logger.debug("getInvoiceDoc.error=" + ErrorMessageDTO.toStringBuilder(invoiceDTO.getErrors()));
                    }
                    break;
                }
                case -1:
                    throw new EtccSecurityException("Security exception in getInvoiceDoc");
                default:
                	doc = new DocumentDTO(getBinaryData(P_INVOICE_DOC[0]));
                    //doc = new DocumentDTO(P_INVOICE_DOC[0]);
            }
            return doc;
        } catch (SQLException e) {
            throw new EtccException("Exception in OracleNewViolationDAO.getInvoiceDoc:" + e.getMessage(), e);
        }
    }

//    private byte[] convert(oracle.sql.BLOB theInput) throws SQLException, EtccException {
//        if (theInput == null) {
//            logger.debug("convert(oracle.sql.BLOB).theInput=null");
//            return null;
//        }
//        /*long theInputLength = theInput.length();
//        if (theInputLength > Integer.MAX_VALUE) {
//            String msg = "convert(oracle.sql.BLOB).oracle.sql.BLOB is too big: " + theInputLength + "(>" + Integer.MAX_VALUE + ")";
//            logger.error(msg);
//            throw new EtccException(msg);
//        }
//        String theInputLengthStr = Long.toString(theInputLength);
//        logger.info("convert(oracle.sql.BLOB).oracle.sql.BLOB.length()=" + theInputLength);
//        int theOutputLength = Integer.parseInt(theInputLengthStr);
//        //byte[] theOutput = theInput.getBytes(startPos, theOutputLength); // causes error in Oracle Blob implementation
//        byte[] theOutput = new byte[theOutputLength];
//        long startPos = Long.parseLong("0");
//        int readCount = theInput.getBytes(startPos, theOutputLength, theOutput); // java.sql.SQLException: Invalid argument(s) in call: getBytes()
//        logger.info("convert(oracle.sql.BLOB).readCount=" + readCount);
//        if (readCount != theOutputLength) {
//            String msg = "convert(oracle.sql.BLOB).readCount (" + readCount + ") does not match expected count (" + theOutputLength + ")";
//            logger.error(msg);
//            throw new EtccException(msg);
//        }
//        logger.info("convert(oracle.sql.BLOB).byte[].length=" + theOutput.length);
//        return theOutput;*/
//        InputStream theInputStream = theInput.getBinaryStream();
//        ByteArrayOutputStream theOutputStream = new ByteArrayOutputStream();
//        copyStream(theInputStream, theOutputStream);
//        byte[] theOutput = theOutputStream.toByteArray();
//        if (logger.isInfoEnabled())
//            logger.info("convert(oracle.sql.BLOB).byte[].length=" + theOutput.length);
//        return theOutput;
//    }

//    private static void copyStream(InputStream ins, OutputStream ops) throws EtccException {
//        try {
//            byte[] buffer = new byte[10000];
//            int count = -1;
//            do {
//                count = ins.read(buffer, 0, buffer.length);
//                if (count > 0) {
//                    ops.write(buffer, 0, count);
//                }
//            } while (count > 0);
//            ops.flush();
//        } catch (Throwable t) {
//            String msg = "copyStream failed: " + t.getMessage();
//            throw new EtccException(msg);
//        }
//    }

    private byte[] getBinaryData(String path) throws EtccException {

    	FileInputStream fileInputStream=null;
        File file = new File(path);

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
        	fileInputStream = new FileInputStream(file);
        	fileInputStream.read(bFile);
        	fileInputStream.close();

        }catch(Exception e){
        	e.printStackTrace();
        }
        finally
        {
        	if(fileInputStream!=null)
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return bFile;
	}


	public ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod, String email) throws EtccException, EtccSecurityException {
        try {
            PaymentType paymentType = paymentMethod.getPaymentType();
        	boolean cc = (paymentType == PaymentType.CREDIT);
        	boolean eft = (paymentType == PaymentType.EFT);
        	long accountId = violator.getAccountLoginDTO().getAcctId();
            String P_DOC_ID =// violator.getAccountLoginDTO().getInvoiceId(),
            (accountId>0?""+accountId:violator.getAccountLoginDTO().getInvoiceId());
            String P_DOC_TYPE = // AccountLoginDTO.LoginType.IN,
            (accountId>0?AccountLoginDTO.LoginType.AC:AccountLoginDTO.LoginType.IN).toString();
            String P_EMAIL_ADDRESS = email;
            String P_NAME_ON_CARD = (cc?((AccountCreditCardDTO)paymentMethod).getNameOnCard():null);
            String P_ADDRESS1 = paymentMethod.getAddress1();
            String P_ADDRESS2 = paymentMethod.getAddress2();
            String P_ADDRESS3 = paymentMethod.getAddress3();
            String P_ADDRESS4 = paymentMethod.getAddress4();
            String P_CITY = paymentMethod.getCity();
            String P_STATE = paymentMethod.getState();
            String P_ZIP_CODE = paymentMethod.getZip();
            String P_PLUS4 = paymentMethod.getPlus4();
            String P_COUNTRY = paymentMethod.getCountry();
            String P_BANK_ACCT_TYPE = (eft?((AccountEFTDTO)paymentMethod).getAccountType().getCode():null);
            String P_BANK_ACCT_NBR = (eft?((AccountEFTDTO)paymentMethod).getAccountNumber():null);
            String P_ROUTING_NBR = (eft?((AccountEFTDTO)paymentMethod).getRoutingNumber():null);
            ShoppingCart cart = violator.getShoppingCart();
            java.math.BigDecimal P_PAST_PAYMENTS = new BigDecimal(violator.getPastPayments());
            java.math.BigDecimal P_AMT_DUE = new BigDecimal(violator.getCurrentAmountDue());
            java.math.BigDecimal P_PMT_AMT = new BigDecimal(violator.getPayment());
            java.math.BigDecimal P_BAL_YET_DUE = new BigDecimal(violator.getRemainingBalance());
            // HACK: for displaying subtotal rows (add invoices with descriptive IDs)
            // INVOICE_ID in OLC_VPS_INVOICES_REC has a max of 20 char
            List<InvoiceDTO> invoices = cart.getAllInvoices();
            InvoiceDTO subtotal = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
            //subtotal.setInvoice("12345678901234567890");
            subtotal.setInvoiceId("Subtotal for Payment");
            subtotal.setInvoiceDue(cart.getAmountDue());
            subtotal.setAmountAlreadyPaid(cart.getPastPayments());
            subtotal.setPayment(cart.getPayment());
            invoices.add(subtotal);
            subtotal = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
            subtotal.setInvoiceId("Subtotal for Others");
            subtotal.setInvoiceDue(violator.getAmountDue() - cart.getAmountDue());
            subtotal.setAmountAlreadyPaid(violator.getPastPayments() - cart.getPastPayments());
            subtotal.setPayment(violator.getPayment() - cart.getPayment());
            invoices.add(subtotal);
            OLC_VPS_INVOICES_ARR P_VPS_INVOICES_ARR = convertInvoices(invoices, violator);
            String P_CARD_TYPE = (cc?((AccountCreditCardDTO)paymentMethod).getCardCode():null);
            String P_CARD_NUMBER = (cc?((AccountCreditCardDTO)paymentMethod).getCardNbr():null);
            java.sql.Timestamp P_EXPIRATION_DATE = (cc?WSDateUtil.monthYearToTimestamp(((AccountCreditCardDTO)paymentMethod).getCardExpires()):null);
            String P_SESSION_ID = violator.getAccountLoginDTO().getDbSessionId();
            String P_IP_ADDRESS = violator.getAccountLoginDTO().getLastLoginIp();
            String P_USER = violator.getAccountLoginDTO().getLoginId();
            String P_CALLED_FROM_GUI = "Y";

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled()) {
                logger.debug("OLVPS_PAYMENT.VPS_PMT_EMAIL(P_DOC_ID=" + P_DOC_ID + ", P_DOC_TYPE=" + P_DOC_TYPE +
                ", P_EMAIL_ADDRESS=" + P_EMAIL_ADDRESS + ", P_NAME_ON_CARD=" + P_NAME_ON_CARD +
                ", P_ADDRESS1=" + P_ADDRESS1 + ", P_ADDRESS2=" + P_ADDRESS2 +
                ", P_ADDRESS3=" + P_ADDRESS3 + ", P_ADDRESS4=" + P_ADDRESS4 + ", P_CITY=" + P_CITY +
                ", P_STATE=" + P_STATE + ", P_ZIP_CODE=" + P_ZIP_CODE + ", P_PLUS4=" + P_PLUS4 +
                ", P_COUNTRY=" + P_COUNTRY + ", P_BANK_ACCT_TYPE=" + P_BANK_ACCT_TYPE +
                ", P_BANK_ACCT_NBR=****,  P_ROUTING_NBR=****, P_AMT_DUE=" + P_AMT_DUE + ", P_PMT_AMT=" + P_PMT_AMT +
                ", P_PAST_PAYMENTS=" + P_PAST_PAYMENTS + ", P_BAL_YET_DUE=" + P_BAL_YET_DUE +
                " ,P_VPS_INVOICES_ARR, P_CARD_TYPE=" + P_CARD_TYPE + ", P_CARD_NUMBER=**** , P_EXPIRATION_DATE=**/**, P_SESSION_ID=" + P_SESSION_ID +
                ", P_IP_ADDRESS=" + P_IP_ADDRESS + ", P_USER=" + P_USER +
                ", O_ERROR_MSG_ARR, P_CALLED_FROM_GUI=" + P_CALLED_FROM_GUI + ')');
            }
//            BigDecimal result = new OLVPS_PAYMENT(this.conn).VPS_PMT_EMAIL(
//                P_DOC_ID, P_DOC_TYPE, P_EMAIL_ADDRESS, P_NAME_ON_CARD, P_ADDRESS1, P_ADDRESS2, P_ADDRESS3,
//                P_ADDRESS4, P_CITY, P_STATE, P_ZIP_CODE, P_PLUS4, P_COUNTRY, P_BANK_ACCT_TYPE, P_BANK_ACCT_NBR,
//                P_ROUTING_NBR, P_AMT_DUE, P_PMT_AMT, P_PAST_PAYMENTS, P_BAL_YET_DUE,P_VPS_INVOICES_ARR,
//                P_CARD_TYPE, P_CARD_NUMBER, P_EXPIRATION_DATE, P_SESSION_ID, P_IP_ADDRESS, P_USER,
//                O_ERROR_MSG_ARR, P_CALLED_FROM_GUI);
            BigDecimal result  = new BigDecimal(-1);
            int ret = result.intValue();
            if (logger.isInfoEnabled())
                logger.info("emailReceipt.ret=" + ret);
            if (ret == 0) {
                violator.addErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (logger.isDebugEnabled()) {
                    logger.debug("emailReceipt.ret=" + ret);
                    logger.debug("emailReceipt.docid=" + (accountId>0?""+accountId:violator.getAccountLoginDTO().getInvoiceId())
                                + ";doctype=" + (accountId>0?AccountLoginDTO.LoginType.AC:AccountLoginDTO.LoginType.IN)
                                + ";email=" + email + ";nameOnCard=" + (cc?((AccountCreditCardDTO)paymentMethod).getNameOnCard():null)
                                + ";address1=" +paymentMethod.getAddress1()
                                + ";address2=" + paymentMethod.getAddress2()
                                + ";address3=" + paymentMethod.getAddress3()
                                + ";address4=" + paymentMethod.getAddress4()
                                + ";city=" + paymentMethod.getCity()
                                + ";state=" + paymentMethod.getState()
                                + ";zip=" + paymentMethod.getZipCode()
                                + ";plus4=" + paymentMethod.getPlus4()
                                + ";country=" + paymentMethod.getCountry()
                                + ";bankAccType=" + (eft?((AccountEFTDTO)paymentMethod).getAccountType():null)
                                + ";bankAccNum=****"/* + (eft?((AccountEFTDTO)paymentMethod).getAccountNumber():null)*/
                                + ";routingNum=****"/* + (eft?((AccountEFTDTO)paymentMethod).getRoutingNumber():null)*/
                                + ";amtDue=" + violator.getAmountDue()
                                + ";pmtAmt=" + violator.getPayment()
                                + ";pastPmt=" + violator.getPastPayments()
                                + ";baldue=" + violator.getRemainingBalance()
                                + ";cardType=" + (cc?((AccountCreditCardDTO)paymentMethod).getCardType():null)
                                + ";cardNum=****"/* + (cc?((AccountCreditCardDTO)paymentMethod).getCardNbr():null)*/
                                + ";expDate=" + (cc?WSDateUtil.monthYearToTimestamp(((AccountCreditCardDTO)paymentMethod).getCardExpires()):null)
                                + ";dbSessionId=" + violator.getAccountLoginDTO().getDbSessionId()
                                + ";ipAddress=" + violator.getAccountLoginDTO().getLastLoginIp()
                                + ";loginId=" + violator.getAccountLoginDTO().getLoginId());
                    logger.debug("emailReceipt.error=" + ErrorMessageDTO.toStringBuilder(violator.getErrors()));
                }
            } else if (ret == -1) {
               throw new EtccSecurityException("Security exception in emailReceipt");
            }
            return violator;

        } catch (SQLException e) {
            throw new EtccException("Exception in OracleNewViolationDAO.emailReceipt:" + e.getMessage(), e);
        }
    }

    @Override 
    protected AccountLoginDTO loginVPS(String sessionId, String ipAddress, String invoiceId,
                                    String licPlate, String licState, ViolatorDTO violatorDTO,String sourceUserName,String dbSessionId) throws SQLException {
    	String[] O_SESSION_ID = new String[] {new String()};
    	BigDecimal[] O_VIOLATOR_ID = new BigDecimal[] {new BigDecimal(0)};
    	OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
    	String dummy = null;
    	//Pay installment enhancement changes 
    	/*int paymentPlanIdDec;
    	if(paymentPlanId != null && StringUtils.isNotEmpty(paymentPlanId)){
    		paymentPlanIdDec=Integer.parseInt(paymentPlanId); 		
    	}else{
    		paymentPlanIdDec = 0;	
    	}*/
    	//Pay installment enhancement changes
    	BigDecimal ret = new OLCSC_LOGIN(this.conn).LOGIN_VPS(
    			/*"OLCSC_USER"*//*userid*/ AccountLoginDTO.GENERIC_USER, sessionId,
    			ipAddress, dummy /*P_BROWSER_TYPE*/, dummy /*P_BROWSER_VERSION*/,
    			dummy /*P_OS_TYPE*/, dummy /*P_OS_VERSION*/,
    			dummy /*P_USER_ENV_ATTRIBUTE1*/, dummy /*P_USER_ENV_ATTRIBUTE2*/,
    			dummy /*P_USER_ENV_ATTRIBUTE3*/, dummy /*P_USER_ENV_ATTRIBUTE4*/,
    			dummy /*P_USER_ENV_ATTRIBUTE5*/, invoiceId,
    			licPlate, licState, /*pay installment*/new BigDecimal(0),/*P II */sourceUserName,O_VIOLATOR_ID,
    			O_SESSION_ID, O_ERROR_MSG_ARR);
    	int result = ret.intValue();

    	AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
    	if ((result == 0) || (result == -1)) {
    		violatorDTO.addErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
    		if (logger.isDebugEnabled()) {
    			logger.debug("loginVPS.result=" + result);
    			logger.debug("loginVPS.sessionId=" + sessionId + ";ipAddress=" + ipAddress + ";invoiceId=" + invoiceId + ";licPlate=" + licPlate + ";licState=" + licState);
    			logger.debug("loginVPS.error=" + ErrorMessageDTO.toStringBuilder(violatorDTO.getErrors()));
    	      
    		}
    		/*pay installment*/
    		if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
				ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
				accountLoginDTO.setErrors(errors);
			
			}/*pay installment*/
    	} else {
    		accountLoginDTO = new AccountLoginDTO();
    		accountLoginDTO.setDbSessionId(O_SESSION_ID[0]);
    		accountLoginDTO.setLoginId(AccountLoginDTO.GENERIC_USER);
    		accountLoginDTO.setLastLoginIp(ipAddress);
    		accountLoginDTO.setViolatorId(O_VIOLATOR_ID[0].longValue());
    		//accountLoginDTO.setAcctId(O_VIOLATOR_ID[0].longValue());
    		if(invoiceId != null && StringUtils.isNotEmpty(invoiceId)){
    		   accountLoginDTO.setInvoiceId(invoiceId);
    		}
    		//Pay installment enhancement changes
    		/*if(paymentPlanId != null && StringUtils.isNotEmpty(paymentPlanId)){
    		   accountLoginDTO.setPaymentPlanId(paymentPlanId);
    		}*/
    		//Pay installment enhancement changes
    	}
    	return accountLoginDTO;
    }
    
    
    @Override 
    protected AccountLoginDTO loginVPS1(String sessionId, String ipAddress,
                                    String licPlate, String licState, String paymentPlanId,ViolatorDTO violatorDTO,String sourceUserName,String dbSessionId) throws SQLException {
    	String[] O_SESSION_ID = new String[] {new String()};
    	BigDecimal[] O_VIOLATOR_ID = new BigDecimal[] {new BigDecimal(0)};
    	OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
    	String dummy = null;
    	//Pay installment enhancement changes 
    	int paymentPlanIdDec;
    	if(paymentPlanId != null && StringUtils.isNotEmpty(paymentPlanId)){
    		paymentPlanIdDec=Integer.parseInt(paymentPlanId); 		
    	}else{
    		paymentPlanIdDec = 0;	
    	}
    	//Pay installment enhancement changes
    	BigDecimal ret = new OLCSC_LOGIN(this.conn).LOGIN_VPS(
    			/*"OLCSC_USER"*//*userid*/ AccountLoginDTO.GENERIC_USER, sessionId,
    			ipAddress, dummy /*P_BROWSER_TYPE*/, dummy /*P_BROWSER_VERSION*/,
    			dummy /*P_OS_TYPE*/, dummy /*P_OS_VERSION*/,
    			dummy /*P_USER_ENV_ATTRIBUTE1*/, dummy /*P_USER_ENV_ATTRIBUTE2*/,
    			dummy /*P_USER_ENV_ATTRIBUTE3*/, dummy /*P_USER_ENV_ATTRIBUTE4*/,
    			dummy /*P_USER_ENV_ATTRIBUTE5*/, null,
    			licPlate, licState, /*pay installment*/new BigDecimal(paymentPlanIdDec),/*P II */sourceUserName,O_VIOLATOR_ID,
    			O_SESSION_ID, O_ERROR_MSG_ARR);
    	int result = ret.intValue();

    	AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
    	if ((result == 0) || (result == -1)) {
    		violatorDTO.addErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
    		if (logger.isDebugEnabled()) {
    			logger.debug("loginVPS.result=" + result);
    			logger.debug("loginVPS.sessionId=" + sessionId + ";ipAddress=" + ipAddress + ";paymentPlanId=" + paymentPlanId + ";licPlate=" + licPlate + ";licState=" + licState);
    			logger.debug("loginVPS.error=" + ErrorMessageDTO.toStringBuilder(violatorDTO.getErrors()));
    	      
    		}
    		/*pay installment*/
    		if(O_ERROR_MSG_ARR[0]!=null && O_ERROR_MSG_ARR[0].getArray().length>0){
				ErrorMessageDTO[] errors = OracleUtil.convertToMessages(O_ERROR_MSG_ARR);
				accountLoginDTO.setErrors(errors);
			
			}/*pay installment*/
    	} else {
    		accountLoginDTO = new AccountLoginDTO();
    		accountLoginDTO.setDbSessionId(O_SESSION_ID[0]);
    		accountLoginDTO.setLoginId(AccountLoginDTO.GENERIC_USER);
    		accountLoginDTO.setLastLoginIp(ipAddress);
    		accountLoginDTO.setViolatorId(O_VIOLATOR_ID[0].longValue());
    		//accountLoginDTO.setAcctId(O_VIOLATOR_ID[0].longValue());
    		 //Pay installment enhancement changes
    		if(paymentPlanId != null && StringUtils.isNotEmpty(paymentPlanId)){
    		   accountLoginDTO.setPaymentPlanId(paymentPlanId);
    		}
    		//Pay installment enhancement changes
    	}
    	return accountLoginDTO;
    }

    void populateInvoices(OLC_VPS_INV_REC_H[] O_VPS_INV_REC_H, ViolatorDTO violatorDTO) throws SQLException {
        final int length = O_VPS_INV_REC_H.length;
        String pdfFilePath =null;
        ArrayList<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>(length);
        logger.info("OracleNewViolationDAO - populateInvoices: entering logic");
        for (int i = 0; i < length; i++) {
            logger.trace("OracleNewViolationDAO - populateInvoices: value of i = " + i);
            OLC_VPS_INV_REC_H inv =  O_VPS_INV_REC_H[i];
            InvoiceDTO invoiceDTO = new InvoiceDTO(AgencyEnum.valueOfCode(inv.getAGENCY_ID()));
            invoiceDTO.setInvoiceId(inv.getINVOICE_ID());
            logger.debug("OracleNewViolationDAO - populateInvoices: invoice date = " + inv.getINVOICE_DATE());
            logger.info("OracleNewViolationDAO - populateInvoices: due date = " + inv.getDUE_DATE());
          //QC_10261 changes start here
            if (inv.getINVOICE_ID()!=null){
				try {
					pdfFilePath =new App().getPdfFilepath(inv.getINVOICE_ID());
				} catch (EtccException e) {
					// TODO Auto-generated catch block
					logger.error("OracleNewViolationDAO - populateInvoices: pdfFilePath",e);
				}
            }
			if (!StringUtil.isEmpty(pdfFilePath)){
				if (inv.getINVOICE_DATE() != null) {
	                Calendar invDate = new GregorianCalendar();
	                invDate.setTime(inv.getINVOICE_DATE());
	                invoiceDTO.setInvoiceDate(invDate);
	            }
				if (inv.getDUE_DATE() != null) {
	                Calendar dueDate = new GregorianCalendar();
	                dueDate.setTime(inv.getDUE_DATE());
	                invoiceDTO.setDueDate(dueDate);
	            }
			}else{
				 invoiceDTO.setInvoiceDate(null);
				 invoiceDTO.setDueDate(null);
			}
//            invoiceDTO.setAmtDue(
//                    //inv.getINVOICE_AMOUNT().floatValue()
//                    (inv.getINVOICE_AMOUNT() != null?inv.getINVOICE_AMOUNT().floatValue() : 0)
//                    - ((inv.getAMOUNT_PAID() != null)?inv.getAMOUNT_PAID().floatValue() : 0)
//                    - ((inv.getPAYMENT_MADE_ONLINE() != null)?inv.getPAYMENT_MADE_ONLINE().floatValue() : 0));
            invoiceDTO.setAdministrativeFee((inv.getADMIN_FEES() != null)?inv.getADMIN_FEES().floatValue() : 0);
            invoiceDTO.setCollectionFee((inv.getCOLLECTION_FEES() != null)?inv.getCOLLECTION_FEES().floatValue() : 0);
            invoiceDTO.setOtherFee((inv.getOTHER_FEES() != null)?inv.getOTHER_FEES().floatValue() : 0);
            invoiceDTO.setInvoiceDue(inv.getINVOICE_AMOUNT() != null?inv.getINVOICE_AMOUNT().floatValue() : 0);
            invoiceDTO.setPaymentsPending((inv.getPAYMENT_MADE_ONLINE() != null)?inv.getPAYMENT_MADE_ONLINE().floatValue() : 0);
            invoiceDTO.setAmountAlreadyPaid((inv.getAMOUNT_PAID() != null)?inv.getAMOUNT_PAID().floatValue() : 0);
            populateViolations(inv.getLINE_ITEMS().getArray(), invoiceDTO);
            invoices.add(invoiceDTO);
        }
        violatorDTO.addInvoices(invoices);
    }

    private void populateViolations(OLC_VPS_INV_REC_D[] violations, InvoiceDTO invoiceDTO) throws SQLException {
    	logger.info("Inside populateViolations");
        ArrayList<ViolationDTO> violationsDTO = new ArrayList<ViolationDTO>(violations.length);
        for (OLC_VPS_INV_REC_D olcVPSINVRECD : violations) {
            ViolationDTO violationDTO = new ViolationDTO();
            violationDTO.setViolationId(olcVPSINVRECD.getTRANSACTION_ID().intValue());
            if (olcVPSINVRECD.getTRANSACTION_DATE() != null) {
                Calendar violationDate = new GregorianCalendar();
                violationDate.setTime(olcVPSINVRECD.getTRANSACTION_DATE());
                violationDTO.setDateTime(violationDate);
            }
            violationDTO.setLocation(olcVPSINVRECD.getV_LOCATION());
            violationDTO.setAmountDue(olcVPSINVRECD.getAMOUNT().floatValue());
            logger.info("olcVPSINVRECD.getTRANSACTION_ID() --->"+olcVPSINVRECD.getTRANSACTION_ID().intValue());
            violationsDTO.add(violationDTO);
        }
        logger.info("populateViolations violationsDTO.size -->"+violationsDTO.size());
        invoiceDTO.setViolations(violationsDTO);
    }

    private OLC_VPS_INVOICES_ARR convertInvoices(Collection<InvoiceDTO> invoices, ViolatorDTO violatorDTO) throws SQLException {
        OLC_VPS_INVOICES_REC[] oraInvoices = new OLC_VPS_INVOICES_REC[invoices.size()];
        int index = 0;
        for (InvoiceDTO inv : invoices) {
            OLC_VPS_INVOICES_REC oraInvRec = new OLC_VPS_INVOICES_REC();
//            oraInvRec.setINVOICE_ID(inv.getInvoiceId());
//            oraInvRec.setINVOICE_DATE(WSDateUtil.calendarToTimestamp(inv.getInvoiceDate()));
//            oraInvRec.setDUE_DATE(WSDateUtil.calendarToTimestamp(inv.getDueDate()));
            //oraInvRec.setFIRST_NAME(inv.get );
            //oraInvRec.setLAST_NAME();
//            oraInvRec.setLIC_PLATE(violatorDTO.getLicPlateNbr());
            oraInvRec.setLIC_STATE(violatorDTO.getLicPlateState());
            oraInvRec.setINVOICE_AMOUNT(new BigDecimal(inv.getInvoiceDue()));
            //oraInvRec.setTOLL_AMT();
            //oraInvRec.setADMIN_FEES();
            //oraInvRec.setCOLLECTION_FEES();
            //oraInvRec.setOTHER_FEES();
//            DecimalFormat formatter = new DecimalFormat("#####.00");
            float amtPaid = inv.getAmountAlreadyPaid() + inv.getPaymentsPending();
//            String amtPaidStr = formatter.format(amtPaid);
//            oraInvRec.setAMOUNT_PAID(new BigDecimal(amtPaid));
            //oraInvRec.setAMOUNT_PENDING(new BigDecimal(inv.getAmtDue()));
//            String amtDueStr = formatter.format(inv.getFinalAmountDue());
//            oraInvRec.setAMOUNT_OWED(new BigDecimal(inv.getFinalAmountDue()));
//            String paymentStr = formatter.format(inv.getPayment());
//            oraInvRec.setAMOUNT_FOR_NEW_PMT(new BigDecimal(inv.getPayment()));

            //oraInvRec.setAMOUNT_FOR_NEW_PMT(new BigDecimal(inv.getPayment()));
            /*logger.debug("getOLC_VPS_INVOICES_ARR." + index + ":invid=" + oraInvRec.getINVOICE_ID() + ";licplate=" + oraInvRec.getLIC_PLATE()
                        + ";licstate=" + oraInvRec.getLIC_STATE() + ";invamt=" + oraInvRec.getINVOICE_AMOUNT() + ";payamt=" + oraInvRec.getAMOUNT_FOR_NEW_PMT()
                        + ";amtowed=" + oraInvRec.getAMOUNT_OWED() + ";amtpaid=" + oraInvRec.getAMOUNT_PAID());*/
            oraInvoices[index++]=oraInvRec;
            if (logger.isDebugEnabled()){
                StringBuilder sb = new StringBuilder();
                sb.append("getOLC_VPS_INVOICES_ARR.oraInvRec=[");
                sb.append("INVOICE_ID=").append(inv.getInvoiceId());
                sb.append(", INVOICE_DATE=").append(display(inv.getInvoiceDate()));
                sb.append(", DUE_DATE=").append(display(inv.getDueDate()));
                sb.append(", LIC_PLATE=").append(violatorDTO.getLicPlateNbr());
                sb.append(", LIC_STATE=").append(violatorDTO.getLicPlateState());
                sb.append(", INVOICE_AMOUNT=").append(inv.getInvoiceDue());
                sb.append(", AMOUNT_PAID=").append(amtPaid);
                sb.append(", AMOUNT_OWED=").append(inv.getFinalAmountDue());
                sb.append(", AMOUNT_FOR_NEW_PMT=").append(inv.getPayment());
                sb.append(']');

                logger.debug(sb.toString());
            }
        }
        OLC_VPS_INVOICES_ARR OLC_VPS_INVOICES_ARR_PARAM = new OLC_VPS_INVOICES_ARR();
        OLC_VPS_INVOICES_ARR_PARAM.setArray(oraInvoices);

        return OLC_VPS_INVOICES_ARR_PARAM;
    }

    private void populatePaymentPlanDetails(OLC_VPS_PAY_PLAN_REC[] payPlanArr, PaymentPlanDTO paymentPlanDTO) throws SQLException {
        logger.debug("populatePaymentPlanDetails: entering...");
        PaymentPlanDetailDTO[] paymentPlanDetails = new PaymentPlanDetailDTO[payPlanArr.length];
        int idx = 0;
        for (OLC_VPS_PAY_PLAN_REC payPlanRec : payPlanArr) {
            PaymentPlanDetailDTO paymentPlanDetail = paymentPlanDTO.new PaymentPlanDetailDTO();
            if (payPlanRec.getDUE_DATE() != null) {
                paymentPlanDetail.setDueDate(WSDateUtil.timestampToCalendar(payPlanRec.getDUE_DATE()));
                logger.debug("populatePaymentPlanDetails: DueDate set to " + display(paymentPlanDetail.getDueDate()));
            }
            if (payPlanRec.getAMOUNT_DUE() != null) {
                paymentPlanDetail.setAmtDue(payPlanRec.getAMOUNT_DUE().floatValue());
                logger.debug("populatePaymentPlanDetails: AmountDue is set to " + paymentPlanDetail.getAmtDue());
            }
            if (payPlanRec.getPAY_DATE() != null) {
                paymentPlanDetail.setPayDate(WSDateUtil.timestampToCalendar(payPlanRec.getPAY_DATE()));
                logger.debug("populatePaymentPlanDetails: PayDate is set to " + display(paymentPlanDetail.getPayDate()));
            }
            if (payPlanRec.getPAY_METHOD() != null) {
                paymentPlanDetail.setPayMethod(payPlanRec.getPAY_METHOD());
            }
            if (payPlanRec.getAMOUNT() != null) {
                paymentPlanDetail.setAmt(payPlanRec.getAMOUNT().floatValue());
            }
            paymentPlanDetail.setClerk(payPlanRec.getCLERK());
            paymentPlanDetails[idx++] = paymentPlanDetail;
        }
        paymentPlanDTO.setPaymentPlanDetails(paymentPlanDetails);
    }
/*    public ResultDTO makeInvoicePaymentCreditCard(AccountLoginDTO acctLoginDto,
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
	}*/
    public ResultDTO makePayment( ViolatorDTO violatorDTO,
			BillingInfoDTO billingInfoDTO, BigDecimal paymentAmount,
			 String email)   throws EtccException, EtccSecurityException {

		ResultDTO resultDTO = null;
		AccountLoginDTO acctLoginDto = violatorDTO.getAccountLoginDTO();
        try {
        	String tokenFromPA = null;
        	PaymentType paymentType = billingInfoDTO.getBillingType();
		 	AccountPaymentMethodDTO paymentMethodDTO = null;
		 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
		 	{
		 		paymentMethodDTO = billingInfoDTO.getCards()[0];
		 		//added year and month validation
		 		AccountCreditCardDTO ccDTO = (AccountCreditCardDTO) paymentMethodDTO;
		 		if(ccDTO.getCardType()!=null && !ccDTO.getCardType().toString().equals(StringUtil.AMERICANEXPRESS)){
		 		if(ccDTO.getCardExpires()!=null && ccDTO.getCardExpires().length()>0){
		 			if(StringUtil.validateCardExpiryDate(ccDTO.getCardExpires().toString())){
		 				resultDTO =new ResultDTO();
		 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
		 				return resultDTO;
		 			}
		 		 }
		 		}
		 		// set token if required.
		 		CCTokenDAOHelper.getInstance().getCCToken(acctLoginDto, paymentMethodDTO);
		 		if (paymentMethodDTO.getAccountBillingMethodId()==null){
		 			// need to implement
		 			Long person_id =PersonAndAddressDAOHelper.getInstance().createPersons(this.conn,paymentMethodDTO.getName(),acctLoginDto.getAcctId());
		 			if (person_id!=null && person_id>0){
			 			Long address_id=PersonAndAddressDAOHelper.getInstance().createAddress(this.conn,person_id,paymentMethodDTO);
			 			paymentMethodDTO.setPersonId(person_id);
			 			paymentMethodDTO.setAddressId(address_id);
		 			}
		 		}
		 	
		 	}
		 	else {  //EFT 
				paymentMethodDTO = billingInfoDTO.getEft();								
				Collection<InvoiceDTO> invoices = violatorDTO.getAllInvoices();
				Map<Long,List<InvoiceDTO>> groupInvoiceByPerson = new HashMap<Long,List<InvoiceDTO>>();				
				for (InvoiceDTO invoice : invoices)
				{
					Long personId = PersonAndAddressDAOHelper.getInstance().getPersonId(this.conn, invoice.getInvoiceId());
					List<InvoiceDTO> groupInvoices = groupInvoiceByPerson.get(personId);
					if ( groupInvoices == null)
					{
						groupInvoices = new ArrayList<InvoiceDTO>();
						groupInvoiceByPerson.put(personId, groupInvoices);
					}
					groupInvoices.add(invoice);					
				}
				// If the selected invoices not from the same person we could not process the invoices.
				if (groupInvoiceByPerson.size() != 1)
				{
					resultDTO =new ResultDTO();
	 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getInvoicePaymentErrorMessage()));
	 				return resultDTO;
				}
				else
				{
					Long personId = groupInvoiceByPerson.keySet().iterator().next();
					paymentMethodDTO.setPersonId(personId);		 				
		 			Long address_id=PersonAndAddressDAOHelper.getInstance().createAddress(this.conn,personId,paymentMethodDTO);			 		
			 		paymentMethodDTO.setAddressId(address_id);		 			
				}								
			}
		 	DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);

	    	Long[] ids =appDao.getAccountPostingAndShiftId(acctLoginDto, 1L);

	    	//Long[] ids =ServiceFactory.getImplementation(AppInterface.class).getAccountPostingAndShiftId(acctLoginDto,1L);

			String postingXML = generatePostingXML(paymentMethodDTO,violatorDTO,ids,paymentAmount);


            String docId = acctLoginDto.getAcctId()>0 ? acctLoginDto.getAcctId() + "": acctLoginDto.getInvoiceId();
            String docType =  acctLoginDto.getAcctId()>0 ? AccountLoginDTO.LoginType.AC.toString() : AccountLoginDTO.LoginType.IN.toString();
            String sessionId = acctLoginDto.getDbSessionId();
            String ipAddress = acctLoginDto.getLastLoginIp();
            String userId = acctLoginDto.getLoginId();
            if (logger.isDebugEnabled())
            	if (paymentMethodDTO != null )
            	{
            		logger.debug("billingInfoDto:" + paymentMethodDTO.toString());
            	}

            OLC_PAYMENT_INFO_REC PAYMENT_INFO_ARR = convertBillingDtoToArray(paymentMethodDTO);
            
            logger.debug("PAYMENT_INFO_ARR.getADDRESS_1  -->"+PAYMENT_INFO_ARR.getADDRESS_1());
            logger.debug("PAYMENT_INFO_ARR.getADDRESS_2  -->"+PAYMENT_INFO_ARR.getADDRESS_2());
            logger.debug("PAYMENT_INFO_ARR.getCITY()  -->"+PAYMENT_INFO_ARR.getCITY());
            logger.debug("PAYMENT_INFO_ARR.getCOUNTRY  -->"+PAYMENT_INFO_ARR.getCOUNTRY());
            

            //BigDecimal[] RTL_TRXN_ID = new BigDecimal[1];
            //RTL_TRXN_ID[0] = new BigDecimal(paymentMethodDTO.getTransactionId());

            BigDecimal[] PMT_AMT = new BigDecimal[1];
            DecimalFormat formatter = new DecimalFormat("#####.00");
            //String paymentAmtS = formatter.format(paymentAmt);
            //PMT_AMT[0] = new BigDecimal(paymentAmtS);

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            if (logger.isDebugEnabled())
                logger.debug("OLCSC_ACCT_MGMT.MAKE_PAYMENT(docId=" + docId + ", docType=" + docType + ", sessionId="
                        + sessionId + ", ipAddress=" + ipAddress + ", userId=" + userId + ", RTL_TRXN_ID="
                       + ", PMT_AMT=" + PMT_AMT[0] + ", PAYMENT_INFO_ARR, N, O_ERROR_MSG_ARR)");
            XMLType xmlPosting = XMLType.createXML(conn, postingXML);
            int result = new OLCSC_PAYMENT(this.conn).MAKE_PAYMENT(docId, docType, sessionId, ipAddress, userId,
                    xmlPosting, email, null,"Y", PAYMENT_INFO_ARR, "N", O_ERROR_MSG_ARR).intValue();
            //int result = -1;
            if (result == -1) {
                throw new EtccSecurityException("security exception in accountDAO.makePayment()");
            }

            resultDTO = new ResultDTO();
            if (result == 0) {
                logger.error("makePayment.result=" + result);
                logger.error("makePayment.acctLoginDto=" + acctLoginDto + ";billingInfoDto=" + paymentMethodDTO
                        + ";paymentAmt=" + PMT_AMT[0]);
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (resultDTO.hasErrors()) {
                    logger.error("makePayment.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
                }
//            } else {
//                if (billingInfoDto.getTransactionId() == -1) {
//                    if (logger.isDebugEnabled())
//                        logger.debug("makePayment: transaction Id = " + RTL_TRXN_ID[0].longValue());
//                    billingInfoDto.setTransactionId(RTL_TRXN_ID[0].longValue());
//                }
            }
        } catch (SQLException ex) {
            throw new EtccException("Exception in accountDAO.makePayment " + ex, ex);
        }
        return resultDTO;
	}
	 private OLC_PAYMENT_INFO_REC convertBillingDtoToArray(AccountPaymentMethodDTO paymentMethodDTO) throws SQLException {
	    	if (paymentMethodDTO == null )
	    	{
	    		return new OLC_PAYMENT_INFO_REC(null,null,null,null,null,null,null,null,null,null,null,
	    				null,null,null,null,null,null,null,
	    				null,null,null,null,null,null,null, null,null,null,null);
	    		
	    		

	    	}
	        if (paymentMethodDTO instanceof AccountCreditCardDTO)
	            return convertCCDtoToArray(paymentMethodDTO);
//	        else
	        return convertEftDtoToArray(paymentMethodDTO);
	    }

	    private OLC_PAYMENT_INFO_REC convertCCDtoToArray(AccountPaymentMethodDTO paymentMethod) throws SQLException {
	        OLC_PAYMENT_INFO_ARR paymentArr = null;

	        final boolean debugEnabled = logger.isDebugEnabled();
//	        OLC_PAYMENT_INFO_REC[] recArr = new OLC_PAYMENT_INFO_REC[billingInfoDTO.getCards().length];

	        AccountCreditCardDTO ccDto = (AccountCreditCardDTO) paymentMethod;
	                //logger.debug("convertCCDtoToArray(AccountCreditCardDTO[]).ccCol.length is " + billingInfoDTO.getCards().length);

	            // Iterator iter = ccCol.iterator();
	            // int i = 0;
	            // while (iter.hasNext()) {
	           //for (int i = 0; i < ccCol.length; i++) {
	                // AccountCreditCardDTO ccDto = (AccountCreditCardDTO)iter.next();
	        		BigDecimal accountBillingMethodId = ccDto.getAccountBillingMethodId() != null ? new BigDecimal(ccDto.getAccountBillingMethodId()) :null;

	                return new OLC_PAYMENT_INFO_REC(
	                        PaymentType.CREDIT.getCodeString(),null,
	                        ccDto.getCardNbr().substring(ccDto.getCardNbr().length()-4),WSDateUtil.monthYearToTimestamp(ccDto.getCardExpires()),
	                        new BigDecimal(ccDto.getCardType().getType()),ccDto.getCardType().name(),
	                        accountBillingMethodId,ccDto.isPrimary()? "Y":"N","Y",new BigDecimal(ccDto.getToken()),
	                        null,null,StringUtil.extractFirstName(ccDto.getNameOnCard()),
	                        StringUtil.extractLastName(ccDto.getNameOnCard()),
	                        ccDto.getAddress1(),ccDto.getAddress2(),ccDto.getCity(),ccDto.getState(),ccDto.getCountry(),ccDto.getZipCode(),
	                        ccDto.getPlus4(),null,null,null,null,null,null,null,null);

//	                if (debugEnabled) {
//	                    StringBuilder sb = new StringBuilder();
//	                    sb.append("convertCCDtoToArray(AccountCreditCardDTO[]).billingRec No");
//	                    sb.append(i);
//	                    sb.append("=[");
//	                    sb.append(ccDto.toStringBuilder());
//	                    sb.append("]");
//	                    logger.debug(ccDto);
//	                }
	            //}
	            //paymentArr = new OLC_PAYMENT_INFO_ARR(recArr);



	    }

	    private OLC_PAYMENT_INFO_REC convertEftDtoToArray(AccountPaymentMethodDTO paymentMethod) throws SQLException {
	        OLC_PAYMENT_INFO_ARR paymentArr = null;

	        AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethod;
	        if (eftDto != null) {
	           // OLC_PAYMENT_INFO_REC[] recArr = new OLC_PAYMENT_INFO_REC[1];
	            OLC_PAYMENT_INFO_REC billingRec = new OLC_PAYMENT_INFO_REC();
	            billingRec.setPMT_TYPE(PaymentType.EFT.getCodeString());
	            billingRec.setBANK_ACCT_TYPE(eftDto.getAccountType().getCode());
	            billingRec.setROUTING_NBR(eftDto.getRoutingNumber());
	            billingRec.setBANK_ACCT_NUMBER(eftDto.getAccountNumber());
	            billingRec.setADDRESS_1(eftDto.getAddress1());
	            billingRec.setADDRESS_2(eftDto.getAddress2());
	            billingRec.setCITY(eftDto.getCity());
	            billingRec.setSTATE(eftDto.getState());
	            billingRec.setCOUNTRY(eftDto.getCountry());
	            billingRec.setZIPCODE(eftDto.getZipCode());
	            
	            logger.debug("billingRec  -->"+billingRec);
	            
	            return billingRec;
	            //paymentArr = new OLC_PAYMENT_INFO_ARR(recArr);
	        }
	        return null;
	    }
	 private String generatePostingXML(AccountPaymentMethodDTO paymentMethod,
				ViolatorDTO violatorDTO, Long ids[], BigDecimal paymentAmount) throws NumberFormatException, EtccException  {

	    	AccountLoginDTO accountLoginDTO = violatorDTO.getAccountLoginDTO();
	    	List<CartXML> carts = new ArrayList<CartXML>();

	    	violatorDTO.getAllInvoices();

	    	//List<InvoiceDTO> allInvoices = violatorDTO.getShoppingCart().getAllInvoices();
	    	for (InvoiceDTO dto : violatorDTO.getAllInvoices())
	    	{
	    		Long invoiceId=0L;
	    		if (dto.getPayment()>0)
	    		{
		    		CartItemXmlDetails cartItem = new CartItemXmlDetails();
		    		cartItem.setItemType(CartItemTypeEnum.PAY_INVOICE.getValue());
		    		if (dto.getInvoiceId()!=null){
			    		invoiceId = new App().getInvoiceId(dto.getInvoiceId());
			    		cartItem.setInvoiceId(invoiceId);
			    		//dto.setInvoiceId(invoiceId.toString());
		    		}else{
		    			cartItem.setInvoiceId(invoiceId);
		    		}
		    		cartItem.setAmount(Double.valueOf(dto.getPayment()));
		    		carts.add(cartItem);
	    		}
	    	}
	    	String xml = null;
			try {
				xml = CartUtil.getInstance().generatecheckoutxml(accountLoginDTO.getViolatorId(), carts, paymentMethod,
						paymentAmount, ids);
			} catch (Exception e) {
			}
			return xml;
		}
	 
	public CheckEligibilityResponseDTO checkEligibility(final String licPlate, final String licState,
			final String invoiceNo, Long accountId, final String ipAddress, final String jsessionId,
			final String sourceUserName, final String user, final String sessionId, final Integer PmtPlanId,
			final String source) throws EtccException {

		CallableStatement cstmt = null;
		CheckEligibilityResponseDTO checkEligibilityResponse = null;
		try {
			cstmt = this.conn.prepareCall(
					"{ call OLCSC_VIOLATION_MGMT_API.check_eligibility(? , ?, ? , ?, ? , ?, ?, ? , ?, ? , ?, ? , ?, ?)}");

			setTypeMap();

			cstmt.setString(1, licPlate);
			cstmt.setString(2, licState);
			cstmt.setString(3, invoiceNo);
			cstmt.setLong(4, accountId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, jsessionId);
			cstmt.setString(7, sourceUserName);
			cstmt.setString(8, user);
			cstmt.registerOutParameter(9, Types.ARRAY, "OL_OWNER.OLC_VPS_PLATE_TYPE_ARR");
			cstmt.registerOutParameter(10, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(11, sessionId);
			cstmt.registerOutParameter(11, Types.VARCHAR);
			cstmt.registerOutParameter(12, Types.NUMERIC);
			if (PmtPlanId != null) {
				cstmt.setInt(13, PmtPlanId);
			} else {
				cstmt.setObject(13, null, Types.NUMERIC);
			}
			cstmt.setString(14, source);

			cstmt.execute();

			checkEligibilityResponse = new CheckEligibilityResponseDTO();
			checkEligibilityResponse.setSessionId(cstmt.getString(11));
			checkEligibilityResponse.setAccountId(cstmt.getLong(12));

			final Array plateTypeArr = (Array) cstmt.getObject(9);

			if (plateTypeArr != null) {
				final List<OlcVpsPlateTypeRec> olcVpsPlateTypeList = getOlcVpsPlateTypeList(plateTypeArr);
				checkEligibilityResponse.getOlcVpsPlateTypeRecList().addAll(olcVpsPlateTypeList);
			} else {
				final Array errors = (Array) cstmt.getObject(8);
				checkEligibilityResponse.setErrors(OracleUtil.convertToMessages(errors));
			}

		} catch (Exception exception) {
			throw new EtccException(
					"Exception in OLCSC_VIOLATION_MGMT_API.check_eligibility:	" + exception.getMessage(), exception);

		} finally {
			close(cstmt);
		}

		return checkEligibilityResponse;

	}

	private List<OlcVpsPlateTypeRec> getOlcVpsPlateTypeList(final Array plateTypeArr) throws SQLException {
		
		final List<OlcVpsPlateTypeRec> olcVpsPlateTypeRecList = new ArrayList<OlcVpsPlateTypeRec>();
			
		final Object[] plateTypeObjectArr = (Object[]) plateTypeArr.getArray();
		
		for (int i = 0; i < plateTypeObjectArr.length; i++) {
			
			if(plateTypeObjectArr[i] instanceof Struct) {
				
				final Struct plateTypeStruct = (Struct) plateTypeObjectArr[i];
				final Object[] plateTypeStructAttributes = plateTypeStruct.getAttributes();

				final OlcVpsPlateTypeRec olcVpsPlateTypeRec = new OlcVpsPlateTypeRec();
				
				olcVpsPlateTypeRec.setDiscountRule((String) plateTypeStructAttributes[0]);
				olcVpsPlateTypeRec.setTtlAmountDue((BigDecimal) plateTypeStructAttributes[1]);
				
				final Array invTypeArr = (Array) plateTypeStructAttributes[2];
				final List<OlcVpsInvTypeRec> olcVpsInvTypeRecList = getInvTypeList(invTypeArr); ;
				olcVpsPlateTypeRec.getInvTypeArr().addAll(olcVpsInvTypeRecList);
				
				olcVpsPlateTypeRec.setLicPlate((String) plateTypeStructAttributes[3]);
				olcVpsPlateTypeRec.setLicState((String) plateTypeStructAttributes[4]);
				olcVpsPlateTypeRec.setOrigInvFeeAmount((BigDecimal) plateTypeStructAttributes[5]);
				olcVpsPlateTypeRec.setOpenInvFeeAmount((BigDecimal) plateTypeStructAttributes[6]);
				olcVpsPlateTypeRec.setOrigInvAmount((BigDecimal) plateTypeStructAttributes[7]);
				olcVpsPlateTypeRec.setOpenInvAmount((BigDecimal) plateTypeStructAttributes[8]);
				olcVpsPlateTypeRec.setOrigPlateAmount((BigDecimal) plateTypeStructAttributes[9]);
				olcVpsPlateTypeRec.setOrigUninvAmount((BigDecimal) plateTypeStructAttributes[10]);
				olcVpsPlateTypeRec.setOpenUninvAmount((BigDecimal) plateTypeStructAttributes[11]);
				olcVpsPlateTypeRec.setTotalPaidAmount((BigDecimal) plateTypeStructAttributes[12]);
				olcVpsPlateTypeRec.setTotalExcAmount((BigDecimal) plateTypeStructAttributes[13]);
				olcVpsPlateTypeRec.setPlateDueAfterExc((BigDecimal) plateTypeStructAttributes[14]);
				olcVpsPlateTypeRec.setPlateDueBeforeExc((BigDecimal) plateTypeStructAttributes[15]);
				
				final Array uninvTypeArray = (Array) plateTypeStructAttributes[16];
				final List<OlcVpsUninvoicedViolsRec> olcVpsUninvoicedViolsRecList = getUninvTypeList(uninvTypeArray);
				olcVpsPlateTypeRec.getUninvTypeArr().addAll(olcVpsUninvoicedViolsRecList);
				
				olcVpsPlateTypeRec.setServiceFee((BigDecimal) plateTypeStructAttributes[17]);
				olcVpsPlateTypeRec.setAccountVehicleId((BigDecimal) plateTypeStructAttributes[18]);
				
				
				final Array pmtPlanTypeArray = (Array) plateTypeStructAttributes[19];
				final List<OlcPmtPlanTypeRec> paymetPlanTypeRecords = getPaymentPlanTypeList(pmtPlanTypeArray);
				olcVpsPlateTypeRec.getPmtPlanTypeArr().addAll(paymetPlanTypeRecords);
				
				olcVpsPlateTypeRec.setInvTollOpenAmount((BigDecimal) plateTypeStructAttributes[20]);
				olcVpsPlateTypeRec.setUnivTollOpenAmount((BigDecimal) plateTypeStructAttributes[21]);
				olcVpsPlateTypeRec.setTtlTollOpenAmount((BigDecimal) plateTypeStructAttributes[22]);
				
				OlcVpsAccountConvRec vpsAccountConRecord = null;
				
				if(plateTypeStructAttributes[23] instanceof Struct) {
					
					final Struct vpsAccountConStruct = (Struct) plateTypeStructAttributes[23];
					vpsAccountConRecord = getVpsAccountConRecord(vpsAccountConStruct);
				}
				else if(plateTypeStructAttributes[23] instanceof OLC_VPS_ACCOUNT_CONV_REC) {
					final OLC_VPS_ACCOUNT_CONV_REC oracleOlcVpsAccountConvRec = (OLC_VPS_ACCOUNT_CONV_REC) plateTypeStructAttributes[23];
					vpsAccountConRecord = getVpsAccountConRecordByOracleClassType(oracleOlcVpsAccountConvRec);
				}
				
				olcVpsPlateTypeRec.setVpsAccountConvRec(vpsAccountConRecord);
				
				final Array vpsPlateTypeAgcyRecArray = (Array) plateTypeStructAttributes[24]; 
				List<OlcVpsPlateAgcyRec> olcVpsPlateAgcyRecList = getPlateAgencyRecords(vpsPlateTypeAgcyRecArray);
				olcVpsPlateTypeRec.getVpsPlateAgcyRec().addAll(olcVpsPlateAgcyRecList);
				
				olcVpsPlateTypeRecList.add(olcVpsPlateTypeRec);
				
			}else if(plateTypeObjectArr[i] instanceof OLC_VPS_PLATE_TYPE_REC) {
				
				final OLC_VPS_PLATE_TYPE_REC oracleOlcVpsPlateTypeRec = (OLC_VPS_PLATE_TYPE_REC) plateTypeObjectArr[i];
				
				final OlcVpsPlateTypeRec olcVpsPlateTypeRec = new OlcVpsPlateTypeRec();
				
				olcVpsPlateTypeRec.setDiscountRule(oracleOlcVpsPlateTypeRec.getDISCOUNT_RULE());
				olcVpsPlateTypeRec.setTtlAmountDue(oracleOlcVpsPlateTypeRec.getTTL_AMOUNT_DUE());
				
				final OLC_VPS_INV_TYPE_ARR invTypeArr = oracleOlcVpsPlateTypeRec.getINV_TYPE_ARR();
				final List<OlcVpsInvTypeRec> olcVpsInvTypeRecList = getInvTypeListByOracleClassType(invTypeArr);
				olcVpsPlateTypeRec.getInvTypeArr().addAll(olcVpsInvTypeRecList);
				
				olcVpsPlateTypeRec.setLicPlate(oracleOlcVpsPlateTypeRec.getLIC_PLATE());
				olcVpsPlateTypeRec.setLicState(oracleOlcVpsPlateTypeRec.getLIC_STATE());
				olcVpsPlateTypeRec.setOrigInvFeeAmount(oracleOlcVpsPlateTypeRec.getORIG_INV_FEE_AMOUNT());
				olcVpsPlateTypeRec.setOpenInvFeeAmount(oracleOlcVpsPlateTypeRec.getOPEN_INV_FEE_AMOUNT());
				olcVpsPlateTypeRec.setOrigInvAmount(oracleOlcVpsPlateTypeRec.getORIG_INV_AMOUNT());
				olcVpsPlateTypeRec.setOpenInvAmount(oracleOlcVpsPlateTypeRec.getOPEN_INV_AMOUNT());
				olcVpsPlateTypeRec.setOrigPlateAmount(oracleOlcVpsPlateTypeRec.getORIG_PLATE_AMOUNT());
				olcVpsPlateTypeRec.setOrigUninvAmount(oracleOlcVpsPlateTypeRec.getORIG_UNINV_AMOUNT());
				olcVpsPlateTypeRec.setOpenUninvAmount(oracleOlcVpsPlateTypeRec.getOPEN_UNINV_AMOUNT());
				olcVpsPlateTypeRec.setTotalPaidAmount(oracleOlcVpsPlateTypeRec.getTOTAL_PAID_AMOUNT());
				olcVpsPlateTypeRec.setTotalExcAmount(oracleOlcVpsPlateTypeRec.getTOTAL_EXC_AMOUNT());
				olcVpsPlateTypeRec.setPlateDueAfterExc(oracleOlcVpsPlateTypeRec.getPLATE_DUE_AFTER_EXC());
				olcVpsPlateTypeRec.setPlateDueBeforeExc(oracleOlcVpsPlateTypeRec.getPLATE_DUE_BEFORE_EXC());
				
				final OLC_VPS_UNINVOICED_VIOLS_ARR uninvTypeArray = oracleOlcVpsPlateTypeRec.getUNINV_TYPE_ARR();
				final List<OlcVpsUninvoicedViolsRec> olcVpsUninvoicedViolsRecList = getUninvTypeListByOracleClassType(uninvTypeArray);
				olcVpsPlateTypeRec.getUninvTypeArr().addAll(olcVpsUninvoicedViolsRecList);
				
				olcVpsPlateTypeRec.setServiceFee(oracleOlcVpsPlateTypeRec.getSERVICE_FEE());
				olcVpsPlateTypeRec.setAccountVehicleId(oracleOlcVpsPlateTypeRec.getACCOUNT_VEHICLE_ID());
				
				
				final OLC_PMT_PLAN_TYPE_ARR pmtPlanTypeArray = oracleOlcVpsPlateTypeRec.getPMT_PLAN_TYPE_ARR();
				final List<OlcPmtPlanTypeRec> paymetPlanTypeRecords = getPaymentPlanTypeListByOracleClassType(pmtPlanTypeArray);
				olcVpsPlateTypeRec.getPmtPlanTypeArr().addAll(paymetPlanTypeRecords);
				
				olcVpsPlateTypeRec.setInvTollOpenAmount(oracleOlcVpsPlateTypeRec.getINV_TOLL_OPEN_AMOUNT());
				olcVpsPlateTypeRec.setUnivTollOpenAmount(oracleOlcVpsPlateTypeRec.getUNIV_TOLL_OPEN_AMOUNT());
				olcVpsPlateTypeRec.setTtlTollOpenAmount(oracleOlcVpsPlateTypeRec.getTTL_TOLL_OPEN_AMOUNT());
				
				final OLC_VPS_ACCOUNT_CONV_REC oracleOlcVpsAccountConvRec = (OLC_VPS_ACCOUNT_CONV_REC)oracleOlcVpsPlateTypeRec.getVPS_ACCOUNT_CONV_REC();
				final OlcVpsAccountConvRec vpsAccountConRecord= getVpsAccountConRecordByOracleClassType(oracleOlcVpsAccountConvRec);
				
				olcVpsPlateTypeRec.setVpsAccountConvRec(vpsAccountConRecord);
				
				final OLC_VPS_PLATE_AGCY_ARR vpsPlateTypeAgcyRecArray = oracleOlcVpsPlateTypeRec.getVPS_PLATE_AGCY_REC();
				List<OlcVpsPlateAgcyRec> olcVpsPlateAgcyRecList = getPlateAgencyRecordsByOracleClassType(vpsPlateTypeAgcyRecArray);
				olcVpsPlateTypeRec.getVpsPlateAgcyRec().addAll(olcVpsPlateAgcyRecList);
				
				olcVpsPlateTypeRecList.add(olcVpsPlateTypeRec);
				
			}else {
				throw new SQLException("Unknown DB Class "+plateTypeObjectArr[i].getClass().getCanonicalName());
			}
			
		}
		return olcVpsPlateTypeRecList;
		
	}

	private List<OlcVpsPlateAgcyRec> getPlateAgencyRecords(final Array vpsPlateTypeAgcyRecArray) throws SQLException {
		
		final List<OlcVpsPlateAgcyRec> olcVpsPlateAgcyRecList = new ArrayList<OlcVpsPlateAgcyRec>();
		
		if(vpsPlateTypeAgcyRecArray != null) {
			final Object[] vpsPlateTypeAgcyRecObjectArray = (Object[]) vpsPlateTypeAgcyRecArray.getArray();
			
			for (int j = 0; j < vpsPlateTypeAgcyRecObjectArray.length; j++) {
		
				if(vpsPlateTypeAgcyRecObjectArray[j] instanceof Struct) {
					
					final Struct vpsPlateTypeAgcyRecStruct = (Struct) vpsPlateTypeAgcyRecObjectArray[j];
					
					final Object[] vpsPlateTypeAgcyRecStructAttributes = vpsPlateTypeAgcyRecStruct.getAttributes();
					
					final OlcVpsPlateAgcyRec olcVpsPlateAgcyRec = new OlcVpsPlateAgcyRec();

					olcVpsPlateAgcyRec.setAgencyId((BigDecimal) vpsPlateTypeAgcyRecStructAttributes[0]);
					olcVpsPlateAgcyRec.setDiscountRule((String) vpsPlateTypeAgcyRecStructAttributes[1]);
					olcVpsPlateAgcyRec.setTtlAmountDue((BigDecimal) vpsPlateTypeAgcyRecStructAttributes[2]);
					olcVpsPlateAgcyRec.setTotalExcAmount((BigDecimal) vpsPlateTypeAgcyRecStructAttributes[3]);
					olcVpsPlateAgcyRec.setTtlAmountDueAfterDisc((BigDecimal) vpsPlateTypeAgcyRecStructAttributes[4]);
					olcVpsPlateAgcyRec.setStatusReason((String) vpsPlateTypeAgcyRecStructAttributes[5]);
					olcVpsPlateAgcyRec.setServiceFee((BigDecimal) vpsPlateTypeAgcyRecStructAttributes[6]);
					
					olcVpsPlateAgcyRecList.add(olcVpsPlateAgcyRec);
					
				}else if(vpsPlateTypeAgcyRecObjectArray[j] instanceof OLC_VPS_PLATE_AGCY_REC) {
					
					final OLC_VPS_PLATE_AGCY_REC oracleOlcVpsPlateAgcyRec = (OLC_VPS_PLATE_AGCY_REC) vpsPlateTypeAgcyRecObjectArray[j];
					
					final OlcVpsPlateAgcyRec olcVpsPlateAgcyRec = new OlcVpsPlateAgcyRec();
					
					olcVpsPlateAgcyRec.setAgencyId(oracleOlcVpsPlateAgcyRec.getAGENCY_ID());
					olcVpsPlateAgcyRec.setDiscountRule(oracleOlcVpsPlateAgcyRec.getDISCOUNT_RULE());
					olcVpsPlateAgcyRec.setTtlAmountDue(oracleOlcVpsPlateAgcyRec.getTTL_AMOUNT_DUE());
					olcVpsPlateAgcyRec.setTotalExcAmount(oracleOlcVpsPlateAgcyRec.getTOTAL_EXC_AMOUNT());
					olcVpsPlateAgcyRec.setTtlAmountDueAfterDisc(oracleOlcVpsPlateAgcyRec.getTTL_AMOUNT_DUE_AFTER_DISC());
					olcVpsPlateAgcyRec.setStatusReason(oracleOlcVpsPlateAgcyRec.getSTATUS_REASON());
					olcVpsPlateAgcyRec.setServiceFee(oracleOlcVpsPlateAgcyRec.getSERVICE_FEE());
					
					olcVpsPlateAgcyRecList.add(olcVpsPlateAgcyRec);
					
				}else {
					throw new SQLException("Unknown DB Class "+vpsPlateTypeAgcyRecObjectArray[j].getClass().getCanonicalName());
					
				}
			}
			
		}
		return olcVpsPlateAgcyRecList;
	}

	private OlcVpsAccountConvRec getVpsAccountConRecord(final Struct vpsAccountConStruct) throws SQLException {
		
		OlcVpsAccountConvRec olcVpsAccountConvRec = null;
		if(vpsAccountConStruct != null) {
			
			final Object[] vpsAccountConStructAttributes = (Object[]) vpsAccountConStruct.getAttributes();
			
			olcVpsAccountConvRec = new OlcVpsAccountConvRec();
			
			olcVpsAccountConvRec.setPersonId((BigDecimal) vpsAccountConStructAttributes[0]);
			olcVpsAccountConvRec.setAddressId((BigDecimal) vpsAccountConStructAttributes[1]);
			olcVpsAccountConvRec.setAcctConvElig((String) vpsAccountConStructAttributes[2]);
			olcVpsAccountConvRec.setTtlAmtDueForConversion((BigDecimal) vpsAccountConStructAttributes[3]);
			olcVpsAccountConvRec.setInitialPrePaidBalance((BigDecimal) vpsAccountConStructAttributes[4]);
			olcVpsAccountConvRec.setTagActivationFee((BigDecimal) vpsAccountConStructAttributes[5]);
			olcVpsAccountConvRec.setAcctConvDiscPct((BigDecimal) vpsAccountConStructAttributes[6]);
			olcVpsAccountConvRec.setTotalDiscountedAmount((BigDecimal) vpsAccountConStructAttributes[7]);
		}
		
		return olcVpsAccountConvRec;
	}

	private List<OlcPmtPlanTypeRec> getPaymentPlanTypeList(final Array pmtPlanTypeArray) throws SQLException {
		
		final List<OlcPmtPlanTypeRec> paymetPlanTypeRecords = new ArrayList<OlcPmtPlanTypeRec>();

		if(pmtPlanTypeArray !=null) {
			
			final Object[] pmtPlanTypeObjectArray = (Object[]) pmtPlanTypeArray.getArray();
			
			for (int j = 0; j < pmtPlanTypeObjectArray.length; j++) {
			
				if(pmtPlanTypeObjectArray[j] instanceof Struct) {
					
					final Struct pmtPlanTypeStruct = (Struct) pmtPlanTypeObjectArray[j];
					final Object[] pmtPlanTypeStructAttributes = pmtPlanTypeStruct.getAttributes();
					
					final OlcPmtPlanTypeRec olcPmtPlanTypeRec = new OlcPmtPlanTypeRec();
					
					olcPmtPlanTypeRec.setPaymentPlanId((BigDecimal) pmtPlanTypeStructAttributes[0]);
					olcPmtPlanTypeRec.setAccountId((BigDecimal) pmtPlanTypeStructAttributes[1]);
					olcPmtPlanTypeRec.setPlanStatusCode((String) pmtPlanTypeStructAttributes[2]);
					olcPmtPlanTypeRec.setInstallmentFreqTypeCode((String) pmtPlanTypeStructAttributes[3]);
					olcPmtPlanTypeRec.setPaymentPlanNumber((String) pmtPlanTypeStructAttributes[4]);
					olcPmtPlanTypeRec.setPlanDiscountAmount((BigDecimal) pmtPlanTypeStructAttributes[5]);
					olcPmtPlanTypeRec.setPlanSettlementAmount((BigDecimal) pmtPlanTypeStructAttributes[6]);
					olcPmtPlanTypeRec.setPlanTotalAmount((BigDecimal) pmtPlanTypeStructAttributes[7]);
					olcPmtPlanTypeRec.setUncollectableAmount((BigDecimal) pmtPlanTypeStructAttributes[8]);
					olcPmtPlanTypeRec.setNumberOfInstallments((BigDecimal) pmtPlanTypeStructAttributes[9]);
					olcPmtPlanTypeRec.setFirstPaymentDate((Date) pmtPlanTypeStructAttributes[10]);
					olcPmtPlanTypeRec.setRebillFailedCount((BigDecimal) pmtPlanTypeStructAttributes[11]);
					olcPmtPlanTypeRec.setOpenAmount((BigDecimal) pmtPlanTypeStructAttributes[12]);
					olcPmtPlanTypeRec.setPaidAmount((BigDecimal) pmtPlanTypeStructAttributes[13]);
					olcPmtPlanTypeRec.setStatusDate((Date) pmtPlanTypeStructAttributes[14]);
					olcPmtPlanTypeRec.setAgcyId((BigDecimal) pmtPlanTypeStructAttributes[15]);
					olcPmtPlanTypeRec.setAccountBillingMethodId((BigDecimal) pmtPlanTypeStructAttributes[16]);
					olcPmtPlanTypeRec.setBillingFullName((String) pmtPlanTypeStructAttributes[17]);
					olcPmtPlanTypeRec.setBillingPaymentForm((String) pmtPlanTypeStructAttributes[18]);
					olcPmtPlanTypeRec.setBillingEftActTypeId((BigDecimal) pmtPlanTypeStructAttributes[19]);
					olcPmtPlanTypeRec.setBillingCardType((String) pmtPlanTypeStructAttributes[20]);
					
					final Array pmtPlanPmtTypeArray = (Array) pmtPlanTypeStructAttributes[21] ;
					final List<OlcPaymentInfoRec> olcPaymentInfoRecList = getpmtPlanPmtTypeList(pmtPlanPmtTypeArray);
					olcPmtPlanTypeRec.getPmtPlanPmtTypeArr().addAll(olcPaymentInfoRecList);
					
					final Array pmtPlanHistTypeArray = (Array) pmtPlanTypeStructAttributes[22];
					final List<OlcPmtPlanHstTypeRec> olcPmtPlanHstTypeRecList = getpmtPlanHistTypeList(pmtPlanHistTypeArray);
					olcPmtPlanTypeRec.getPmtPlanHistTypeArr().addAll(olcPmtPlanHstTypeRecList);
					
					final Array pmtPlanInstTypeArr = (Array) pmtPlanTypeStructAttributes[23];
					final List<OlcPmtPlanInstTypeRec> olcPmtPlanInstTypeRecList = getPmtPlanInstTypeList(pmtPlanInstTypeArr);
					olcPmtPlanTypeRec.getPmtPlanInstTypeArr().addAll(olcPmtPlanInstTypeRecList);
					
					paymetPlanTypeRecords.add(olcPmtPlanTypeRec);
					
				}else if(pmtPlanTypeObjectArray[j] instanceof OLC_PMT_PLAN_TYPE_REC) {
					
					final OLC_PMT_PLAN_TYPE_REC oracleOlcPmtPlanTypeRec = (OLC_PMT_PLAN_TYPE_REC) pmtPlanTypeObjectArray[j];
					
					final OlcPmtPlanTypeRec olcPmtPlanTypeRec = new OlcPmtPlanTypeRec();
					
					olcPmtPlanTypeRec.setPaymentPlanId(oracleOlcPmtPlanTypeRec.getPAYMENT_PLAN_ID());
					olcPmtPlanTypeRec.setAccountId(oracleOlcPmtPlanTypeRec.getACCOUNT_ID());
					olcPmtPlanTypeRec.setPlanStatusCode(oracleOlcPmtPlanTypeRec.getPLAN_STATUS_CODE());
					olcPmtPlanTypeRec.setInstallmentFreqTypeCode(oracleOlcPmtPlanTypeRec.getINSTALLMENT_FREQ_TYPE_CODE());
					olcPmtPlanTypeRec.setPaymentPlanNumber(oracleOlcPmtPlanTypeRec.getPAYMENT_PLAN_NUMBER());
					olcPmtPlanTypeRec.setPlanDiscountAmount(oracleOlcPmtPlanTypeRec.getPLAN_DISCOUNT_AMOUNT());
					olcPmtPlanTypeRec.setPlanSettlementAmount(oracleOlcPmtPlanTypeRec.getPLAN_SETTLEMENT_AMOUNT());
					olcPmtPlanTypeRec.setPlanTotalAmount(oracleOlcPmtPlanTypeRec.getPLAN_TOTAL_AMOUNT());
					olcPmtPlanTypeRec.setUncollectableAmount(oracleOlcPmtPlanTypeRec.getUNCOLLECTABLE_AMOUNT());
					olcPmtPlanTypeRec.setNumberOfInstallments(oracleOlcPmtPlanTypeRec.getNUMBER_OF_INSTALLMENTS());
					
					if(oracleOlcPmtPlanTypeRec.getFIRST_PAYMENT_DATE() !=null) {
						
						olcPmtPlanTypeRec.setFirstPaymentDate(new Date (oracleOlcPmtPlanTypeRec.getFIRST_PAYMENT_DATE().getTime()));
					}
					
					olcPmtPlanTypeRec.setRebillFailedCount(oracleOlcPmtPlanTypeRec.getREBILL_FAILED_COUNT());
					olcPmtPlanTypeRec.setOpenAmount(oracleOlcPmtPlanTypeRec.getOPEN_AMOUNT());
					olcPmtPlanTypeRec.setPaidAmount(oracleOlcPmtPlanTypeRec.getPAID_AMOUNT());
					
					if(oracleOlcPmtPlanTypeRec.getSTATUS_DATE() !=null) {
						
						olcPmtPlanTypeRec.setStatusDate(new Date(oracleOlcPmtPlanTypeRec.getSTATUS_DATE().getTime()));
					}
					
					olcPmtPlanTypeRec.setAgcyId(oracleOlcPmtPlanTypeRec.getAGCY_ID());
					olcPmtPlanTypeRec.setAccountBillingMethodId(oracleOlcPmtPlanTypeRec.getACCOUNT_BILLING_METHOD_ID());
					olcPmtPlanTypeRec.setBillingFullName(oracleOlcPmtPlanTypeRec.getBILLING_FULL_NAME());
					olcPmtPlanTypeRec.setBillingPaymentForm(oracleOlcPmtPlanTypeRec.getBILLING_PAYMENT_FORM());
					olcPmtPlanTypeRec.setBillingEftActTypeId(oracleOlcPmtPlanTypeRec.getBILLING_EFT_ACT_TYPE_ID());
					olcPmtPlanTypeRec.setBillingCardType(oracleOlcPmtPlanTypeRec.getBILLING_CARD_TYPE());
					
					final OLC_PAYMENT_INFO_ARR pmtPlanPmtTypeArray = oracleOlcPmtPlanTypeRec.getPMT_PLAN_PMT_TYPE_ARR() ;
					final List<OlcPaymentInfoRec> olcPaymentInfoRecList = getpmtPlanPmtTypeListByOracleClassType(pmtPlanPmtTypeArray);
					olcPmtPlanTypeRec.getPmtPlanPmtTypeArr().addAll(olcPaymentInfoRecList);
					
					final OLC_PMT_PLAN_INST_PMT_HST_ARR pmtPlanHistTypeArray = oracleOlcPmtPlanTypeRec.getPMT_PLAN_HIST_TYPE_ARR();
					final List<OlcPmtPlanHstTypeRec> olcPmtPlanHstTypeRecList = getpmtPlanHistTypeListByOracleClassType(pmtPlanHistTypeArray);
					olcPmtPlanTypeRec.getPmtPlanHistTypeArr().addAll(olcPmtPlanHstTypeRecList);
					
					final OLC_PMT_PLAN_INST_TYPE_ARR pmtPlanInstTypeArr = oracleOlcPmtPlanTypeRec.getPMT_PLAN_INST_TYPE_ARR();
					final List<OlcPmtPlanInstTypeRec> olcPmtPlanInstTypeRecList = getPmtPlanInstTypeListByOracleClassType(pmtPlanInstTypeArr);
					olcPmtPlanTypeRec.getPmtPlanInstTypeArr().addAll(olcPmtPlanInstTypeRecList);
					
					paymetPlanTypeRecords.add(olcPmtPlanTypeRec);
					
				}else {
					throw new SQLException("Unknown DB Class "+pmtPlanTypeObjectArray[j].getClass().getCanonicalName());
				}				
			}
			
		}
		return paymetPlanTypeRecords;
	}

	private List<OlcPmtPlanInstTypeRec> getPmtPlanInstTypeList(final Array pmtPlanInstTypeArr)
			throws SQLException {
		final List<OlcPmtPlanInstTypeRec> olcPmtPlanInstTypeRecList = new ArrayList<OlcPmtPlanInstTypeRec>();
		if(pmtPlanInstTypeArr != null) {
			
			final Object[] pmtPlanInstTypeObjectArray = (Object[]) pmtPlanInstTypeArr.getArray();
			
			for (int k = 0; k < pmtPlanInstTypeObjectArray.length; k++) {
				
				if(pmtPlanInstTypeObjectArray[k] instanceof Struct) {
					
					final Struct pmtPlanInstTypeStruct = (Struct) pmtPlanInstTypeObjectArray[k];
					final Object[] pmtPlanInstTypeStructAttributes = pmtPlanInstTypeStruct.getAttributes();
					
					final OlcPmtPlanInstTypeRec olcPmtPlanInstTypeRec = new OlcPmtPlanInstTypeRec();
					
					olcPmtPlanInstTypeRec.setPaymentPlanInstallmentId((BigDecimal) pmtPlanInstTypeStructAttributes[0]);
					olcPmtPlanInstTypeRec.setPlanInstallmentStatusCode((String) pmtPlanInstTypeStructAttributes[1]);
					olcPmtPlanInstTypeRec.setPaymentPlanId((BigDecimal) pmtPlanInstTypeStructAttributes[2]);
					olcPmtPlanInstTypeRec.setItemsPaymentId((BigDecimal) pmtPlanInstTypeStructAttributes[3]);
					olcPmtPlanInstTypeRec.setIsFuture((String) pmtPlanInstTypeStructAttributes[4]);
					olcPmtPlanInstTypeRec.setInstallmentNumber((BigDecimal) pmtPlanInstTypeStructAttributes[5]);
					olcPmtPlanInstTypeRec.setDueDate((Date) pmtPlanInstTypeStructAttributes[6]);
					olcPmtPlanInstTypeRec.setInstallmentAmount((BigDecimal) pmtPlanInstTypeStructAttributes[7]);
					olcPmtPlanInstTypeRec.setOpenAmount((BigDecimal) pmtPlanInstTypeStructAttributes[8]);
					olcPmtPlanInstTypeRec.setPaidAmount((BigDecimal) pmtPlanInstTypeStructAttributes[9]);
					olcPmtPlanInstTypeRec.setRecordVersion((BigDecimal) pmtPlanInstTypeStructAttributes[10]);
					olcPmtPlanInstTypeRec.setStatusDate((Date) pmtPlanInstTypeStructAttributes[11]);
					olcPmtPlanInstTypeRec.setTollAmount((BigDecimal) pmtPlanInstTypeStructAttributes[12]);
					olcPmtPlanInstTypeRec.setFeeAmount((BigDecimal) pmtPlanInstTypeStructAttributes[13]);
					olcPmtPlanInstTypeRec.setCreatedBy((String) pmtPlanInstTypeStructAttributes[14]);
					olcPmtPlanInstTypeRec.setDateCreated((Date) pmtPlanInstTypeStructAttributes[15]);
					olcPmtPlanInstTypeRec.setModifiedBy((String) pmtPlanInstTypeStructAttributes[16]);
					olcPmtPlanInstTypeRec.setDateModified((Date) pmtPlanInstTypeStructAttributes[17]);
					olcPmtPlanInstTypeRec.setPlanInstallmentTypeId((BigDecimal) pmtPlanInstTypeStructAttributes[18]);
					olcPmtPlanInstTypeRec.setLastCartId((BigDecimal) pmtPlanInstTypeStructAttributes[19]);
					olcPmtPlanInstTypeRec.setUncollectableAmount((BigDecimal) pmtPlanInstTypeStructAttributes[20]);
					olcPmtPlanInstTypeRec.setCancelledAmount((BigDecimal) pmtPlanInstTypeStructAttributes[21]);
					
					olcPmtPlanInstTypeRecList.add(olcPmtPlanInstTypeRec);
					
				}else if(pmtPlanInstTypeObjectArray[k] instanceof OLC_PMT_PLAN_INST_TYPE_REC) {
					
					final OLC_PMT_PLAN_INST_TYPE_REC oracleOlcPmtPlanInstTypeRec = (OLC_PMT_PLAN_INST_TYPE_REC) pmtPlanInstTypeObjectArray[k];
					
					final OlcPmtPlanInstTypeRec olcPmtPlanInstTypeRec = new OlcPmtPlanInstTypeRec();
					
					olcPmtPlanInstTypeRec.setPaymentPlanInstallmentId(oracleOlcPmtPlanInstTypeRec.getPAYMENT_PLAN_INSTALLMENT_ID());
					olcPmtPlanInstTypeRec.setPlanInstallmentStatusCode(oracleOlcPmtPlanInstTypeRec.getPLAN_INSTALLMENT_STATUS_CODE());
					olcPmtPlanInstTypeRec.setPaymentPlanId(oracleOlcPmtPlanInstTypeRec.getPAYMENT_PLAN_ID());
					olcPmtPlanInstTypeRec.setItemsPaymentId(oracleOlcPmtPlanInstTypeRec.getITEMS_PAYMENT_ID());
					olcPmtPlanInstTypeRec.setIsFuture(oracleOlcPmtPlanInstTypeRec.getIS_FUTURE());
					olcPmtPlanInstTypeRec.setInstallmentNumber(oracleOlcPmtPlanInstTypeRec.getINSTALLMENT_NUMBER());
					
					if(oracleOlcPmtPlanInstTypeRec.getDUE_DATE() !=null) {
						
						olcPmtPlanInstTypeRec.setDueDate(new Date(oracleOlcPmtPlanInstTypeRec.getDUE_DATE().getTime()));
					}
					
					olcPmtPlanInstTypeRec.setInstallmentAmount(oracleOlcPmtPlanInstTypeRec.getINSTALLMENT_AMOUNT());
					olcPmtPlanInstTypeRec.setOpenAmount(oracleOlcPmtPlanInstTypeRec.getOPEN_AMOUNT());
					olcPmtPlanInstTypeRec.setPaidAmount(oracleOlcPmtPlanInstTypeRec.getPAID_AMOUNT());
					olcPmtPlanInstTypeRec.setRecordVersion(oracleOlcPmtPlanInstTypeRec.getRECORD_VERSION());
					
					if(oracleOlcPmtPlanInstTypeRec.getSTATUS_DATE() !=null) {
						
						olcPmtPlanInstTypeRec.setStatusDate(new Date(oracleOlcPmtPlanInstTypeRec.getSTATUS_DATE().getTime()));
					}
					
					olcPmtPlanInstTypeRec.setTollAmount(oracleOlcPmtPlanInstTypeRec.getTOLL_AMOUNT());
					olcPmtPlanInstTypeRec.setFeeAmount(oracleOlcPmtPlanInstTypeRec.getFEE_AMOUNT());
					olcPmtPlanInstTypeRec.setCreatedBy(oracleOlcPmtPlanInstTypeRec.getCREATED_BY());
					
					if(oracleOlcPmtPlanInstTypeRec.getDATE_CREATED() !=null) {
						
						olcPmtPlanInstTypeRec.setDateCreated(new Date(oracleOlcPmtPlanInstTypeRec.getDATE_CREATED().getTime()));
					}
					
					olcPmtPlanInstTypeRec.setModifiedBy(oracleOlcPmtPlanInstTypeRec.getMODIFIED_BY());
					
					if(oracleOlcPmtPlanInstTypeRec.getDATE_MODIFIED() !=null) {
						
						olcPmtPlanInstTypeRec.setDateModified(new Date(oracleOlcPmtPlanInstTypeRec.getDATE_MODIFIED().getTime()));
					}
					
					olcPmtPlanInstTypeRec.setPlanInstallmentTypeId(oracleOlcPmtPlanInstTypeRec.getPLAN_INSTALLMENT_TYPE_ID());
					olcPmtPlanInstTypeRec.setLastCartId(oracleOlcPmtPlanInstTypeRec.getLAST_CART_ID());
					olcPmtPlanInstTypeRec.setUncollectableAmount(oracleOlcPmtPlanInstTypeRec.getUNCOLLECTABLE_AMOUNT());
					olcPmtPlanInstTypeRec.setCancelledAmount(oracleOlcPmtPlanInstTypeRec.getCANCELLED_AMOUNT());
					
					olcPmtPlanInstTypeRecList.add(olcPmtPlanInstTypeRec);
					
				}else {
					throw new SQLException("Unknown DB Class "+pmtPlanInstTypeObjectArray[k].getClass().getCanonicalName());
				}
			}
		}
		return olcPmtPlanInstTypeRecList;
	}

	private List<OlcPmtPlanHstTypeRec> getpmtPlanHistTypeList(final Array pmtPlanHistTypeArray)
			throws SQLException {
		
		final List<OlcPmtPlanHstTypeRec> olcPmtPlanHstTypeRecList = new ArrayList<OlcPmtPlanHstTypeRec>();

		if(pmtPlanHistTypeArray != null) {
			
			final Object[] pmtPlanHistTypeObjectArray = (Object[]) pmtPlanHistTypeArray.getArray();
			
			for (int k = 0; k < pmtPlanHistTypeObjectArray.length; k++) {
				
				if(pmtPlanHistTypeObjectArray[k] instanceof Struct) {
					
					final Struct pmtPlanHistTypeStruct = (Struct)pmtPlanHistTypeObjectArray[k];
					final Object[] pmtPlanHistTypeStructAttributes = pmtPlanHistTypeStruct.getAttributes();

					final OlcPmtPlanHstTypeRec olcPmtPlanHstTypeRec = new OlcPmtPlanHstTypeRec();
					
					olcPmtPlanHstTypeRec.setPaymentId((BigDecimal) pmtPlanHistTypeStructAttributes[0]);
					olcPmtPlanHstTypeRec.setPaymentForm((String) pmtPlanHistTypeStructAttributes[1]);
					olcPmtPlanHstTypeRec.setPaymentType((String) pmtPlanHistTypeStructAttributes[2]);
					olcPmtPlanHstTypeRec.setLast4((String) pmtPlanHistTypeStructAttributes[3]);
					olcPmtPlanHstTypeRec.setPaymentDate((Date) pmtPlanHistTypeStructAttributes[4]);
					olcPmtPlanHstTypeRec.setDueDate((Date) pmtPlanHistTypeStructAttributes[5]);
					olcPmtPlanHstTypeRec.setInstallmentNumber((BigDecimal) pmtPlanHistTypeStructAttributes[6]);
					olcPmtPlanHstTypeRec.setInstallmentStatus((String) pmtPlanHistTypeStructAttributes[7]);
					olcPmtPlanHstTypeRec.setPaymentAmount((BigDecimal) pmtPlanHistTypeStructAttributes[8]);
					olcPmtPlanHstTypeRec.setInstallmentAmount((BigDecimal) pmtPlanHistTypeStructAttributes[9]);
					olcPmtPlanHstTypeRec.setPaidAmount((BigDecimal) pmtPlanHistTypeStructAttributes[10]);
					olcPmtPlanHstTypeRec.setOpenAmount((BigDecimal) pmtPlanHistTypeStructAttributes[11]);
					
					olcPmtPlanHstTypeRecList.add(olcPmtPlanHstTypeRec);
					
				}else if(pmtPlanHistTypeObjectArray[k] instanceof OLC_PMT_PLAN_PMT_HST_TYPE_REC) {
					
					final OLC_PMT_PLAN_PMT_HST_TYPE_REC oracleOlcPmtPlanPmtHstTypeRec = (OLC_PMT_PLAN_PMT_HST_TYPE_REC) pmtPlanHistTypeObjectArray[k];
					
					final OlcPmtPlanHstTypeRec olcPmtPlanHstTypeRec = new OlcPmtPlanHstTypeRec();
					
					olcPmtPlanHstTypeRec.setPaymentId(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_ID());
					olcPmtPlanHstTypeRec.setPaymentForm(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_FORM());
					olcPmtPlanHstTypeRec.setPaymentType(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_TYPE());
					olcPmtPlanHstTypeRec.setLast4(oracleOlcPmtPlanPmtHstTypeRec.getLAST4());
					
					if(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_DATE() !=null) {
						
						olcPmtPlanHstTypeRec.setPaymentDate(new Date(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_DATE().getTime()));
					}
					
					if(oracleOlcPmtPlanPmtHstTypeRec.getDUE_DATE() !=null) {
						
						olcPmtPlanHstTypeRec.setDueDate(new Date(oracleOlcPmtPlanPmtHstTypeRec.getDUE_DATE().getTime()));
					}
					
					olcPmtPlanHstTypeRec.setInstallmentNumber(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_NUMBER());
					olcPmtPlanHstTypeRec.setInstallmentStatus(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_STATUS());
					olcPmtPlanHstTypeRec.setPaymentAmount(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_AMOUNT());
					olcPmtPlanHstTypeRec.setInstallmentAmount(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_AMOUNT());
					olcPmtPlanHstTypeRec.setPaidAmount(oracleOlcPmtPlanPmtHstTypeRec.getPAID_AMOUNT());
					olcPmtPlanHstTypeRec.setOpenAmount(oracleOlcPmtPlanPmtHstTypeRec.getOPEN_AMOUNT());
					
					olcPmtPlanHstTypeRecList.add(olcPmtPlanHstTypeRec);
					
				}else {
					throw new SQLException("Unknown DB Class "+(pmtPlanHistTypeObjectArray[k].getClass().getCanonicalName()));
				}
				
			}
			
		}
		return olcPmtPlanHstTypeRecList;
	}

	private List<OlcPaymentInfoRec> getpmtPlanPmtTypeList(final Array pmtPlanPmtTypeArray)
			throws SQLException {
		
		final List<OlcPaymentInfoRec> olcPaymentInfoRecList = new ArrayList<OlcPaymentInfoRec>();
		
		if(pmtPlanPmtTypeArray != null) {
			final Object[] pmtPlanPmtTypeObjectArray = (Object[]) pmtPlanPmtTypeArray.getArray();
			
			for (int k = 0; k < pmtPlanPmtTypeObjectArray.length; k++) {
				
				if(pmtPlanPmtTypeObjectArray[k] instanceof Struct) {
					
					final Struct pmtPlanPmtTypeStuct = (Struct) pmtPlanPmtTypeObjectArray[k];
					
					final Object[] pmtPlanPmtTypeStuctAttributes = pmtPlanPmtTypeStuct.getAttributes();
					
					final OlcPaymentInfoRec olcPaymentInfoRec = new OlcPaymentInfoRec();
					
					olcPaymentInfoRec.setPmtType((String) pmtPlanPmtTypeStuctAttributes[0]);
					olcPaymentInfoRec.setPersonId((BigDecimal) pmtPlanPmtTypeStuctAttributes[1]);
					olcPaymentInfoRec.setCardNbr((String) pmtPlanPmtTypeStuctAttributes[2]);
					olcPaymentInfoRec.setCardExpires((Date) pmtPlanPmtTypeStuctAttributes[3]);
					olcPaymentInfoRec.setCardType((BigDecimal) pmtPlanPmtTypeStuctAttributes[4]);
					olcPaymentInfoRec.setCardName((String) pmtPlanPmtTypeStuctAttributes[5]);
					olcPaymentInfoRec.setAccountBillingMethodId((BigDecimal) pmtPlanPmtTypeStuctAttributes[6]);
					olcPaymentInfoRec.setIsDefaultBillingMethod((String) pmtPlanPmtTypeStuctAttributes[7]);
					olcPaymentInfoRec.setIsActive((String) pmtPlanPmtTypeStuctAttributes[8]);
					olcPaymentInfoRec.setToken((BigDecimal) pmtPlanPmtTypeStuctAttributes[9]);
					olcPaymentInfoRec.setAddressId((BigDecimal) pmtPlanPmtTypeStuctAttributes[10]);
					olcPaymentInfoRec.setPhoneId((BigDecimal) pmtPlanPmtTypeStuctAttributes[11]);
					olcPaymentInfoRec.setFirstName((String) pmtPlanPmtTypeStuctAttributes[12]);
					olcPaymentInfoRec.setLastName((String) pmtPlanPmtTypeStuctAttributes[13]);
					olcPaymentInfoRec.setAddress1((String) pmtPlanPmtTypeStuctAttributes[14]);
					olcPaymentInfoRec.setAddress2((String) pmtPlanPmtTypeStuctAttributes[15]);
					olcPaymentInfoRec.setCity((String) pmtPlanPmtTypeStuctAttributes[16]);
					olcPaymentInfoRec.setState((String) pmtPlanPmtTypeStuctAttributes[17]);
					olcPaymentInfoRec.setCountry((String) pmtPlanPmtTypeStuctAttributes[18]);
					olcPaymentInfoRec.setZipcode((String) pmtPlanPmtTypeStuctAttributes[19]);
					olcPaymentInfoRec.setPlus4((String) pmtPlanPmtTypeStuctAttributes[20]);
					olcPaymentInfoRec.setPhoneNbr((String) pmtPlanPmtTypeStuctAttributes[21]);
					olcPaymentInfoRec.setPhoneExtn((String) pmtPlanPmtTypeStuctAttributes[22]);
					olcPaymentInfoRec.setBankAcctType((String) pmtPlanPmtTypeStuctAttributes[23]);
					olcPaymentInfoRec.setBankAcctNumber((String) pmtPlanPmtTypeStuctAttributes[24]);
					olcPaymentInfoRec.setRoutingNbr((String) pmtPlanPmtTypeStuctAttributes[25]);
					olcPaymentInfoRec.setIsBlocked((String) pmtPlanPmtTypeStuctAttributes[26]);
					olcPaymentInfoRec.setBillingType((String) pmtPlanPmtTypeStuctAttributes[27]);
					olcPaymentInfoRec.setBillingPriority((BigDecimal) pmtPlanPmtTypeStuctAttributes[28]);
					
					olcPaymentInfoRecList.add(olcPaymentInfoRec);
					
				}
				else if (pmtPlanPmtTypeObjectArray[k] instanceof OLC_PAYMENT_INFO_REC) {
					
					final OLC_PAYMENT_INFO_REC oracleOlcPaymentInfoRec = (OLC_PAYMENT_INFO_REC) pmtPlanPmtTypeObjectArray[k];
					
					final OlcPaymentInfoRec olcPaymentInfoRec = new OlcPaymentInfoRec();
					
					olcPaymentInfoRec.setPmtType(oracleOlcPaymentInfoRec.getPMT_TYPE());
					olcPaymentInfoRec.setPersonId(oracleOlcPaymentInfoRec. getPERSON_ID());
					olcPaymentInfoRec.setCardNbr(oracleOlcPaymentInfoRec.getCARD_NBR());
					
					if(oracleOlcPaymentInfoRec.getCARD_EXPIRES() !=null) {
						
						olcPaymentInfoRec.setCardExpires(new Date(oracleOlcPaymentInfoRec.getCARD_EXPIRES().getTime()));
					}
					
					olcPaymentInfoRec.setCardType(oracleOlcPaymentInfoRec.getCARD_TYPE());
					olcPaymentInfoRec.setCardName(oracleOlcPaymentInfoRec.getCARD_NAME());
					olcPaymentInfoRec.setAccountBillingMethodId(oracleOlcPaymentInfoRec.getACCOUNT_BILLING_METHOD_ID());
					olcPaymentInfoRec.setIsDefaultBillingMethod(oracleOlcPaymentInfoRec.getIS_DEFAULT_BILLING_METHOD());
					olcPaymentInfoRec.setIsActive(oracleOlcPaymentInfoRec.getIS_ACTIVE());
					olcPaymentInfoRec.setToken(oracleOlcPaymentInfoRec.getTOKEN());
					olcPaymentInfoRec.setAddressId(oracleOlcPaymentInfoRec.getADDRESS_ID());
					olcPaymentInfoRec.setPhoneId(oracleOlcPaymentInfoRec.getPHONE_ID());
					olcPaymentInfoRec.setFirstName(oracleOlcPaymentInfoRec.getFIRST_NAME());
					olcPaymentInfoRec.setLastName(oracleOlcPaymentInfoRec.getLAST_NAME());
					olcPaymentInfoRec.setAddress1(oracleOlcPaymentInfoRec.getADDRESS_1());
					olcPaymentInfoRec.setAddress2(oracleOlcPaymentInfoRec.getADDRESS_2());
					olcPaymentInfoRec.setCity(oracleOlcPaymentInfoRec.getCITY());
					olcPaymentInfoRec.setState(oracleOlcPaymentInfoRec.getSTATE());
					olcPaymentInfoRec.setCountry(oracleOlcPaymentInfoRec.getCOUNTRY());
					olcPaymentInfoRec.setZipcode(oracleOlcPaymentInfoRec.getZIPCODE());
					olcPaymentInfoRec.setPlus4(oracleOlcPaymentInfoRec.getPLUS4());
					olcPaymentInfoRec.setPhoneNbr(oracleOlcPaymentInfoRec.getPHONE_NBR());
					olcPaymentInfoRec.setPhoneExtn(oracleOlcPaymentInfoRec.getPHONE_EXTN());
					olcPaymentInfoRec.setBankAcctType(oracleOlcPaymentInfoRec.getBANK_ACCT_TYPE());
					olcPaymentInfoRec.setBankAcctNumber(oracleOlcPaymentInfoRec.getBANK_ACCT_NUMBER());
					olcPaymentInfoRec.setRoutingNbr(oracleOlcPaymentInfoRec.getROUTING_NBR());
					olcPaymentInfoRec.setIsBlocked(oracleOlcPaymentInfoRec.getIS_BLOCKED());
					olcPaymentInfoRec.setBillingType(oracleOlcPaymentInfoRec.getBILLING_TYPE());
					olcPaymentInfoRec.setBillingPriority(oracleOlcPaymentInfoRec.getBILLING_PRIORITY());
					
					olcPaymentInfoRecList.add(olcPaymentInfoRec);
					
				}else {
					throw new SQLException("Unknown DB Class " + pmtPlanPmtTypeObjectArray[k].getClass().getCanonicalName());
				}
				
			}
			
		}
		return olcPaymentInfoRecList;
	}

	private List<OlcVpsUninvoicedViolsRec> getUninvTypeList(final Array uninvTypeArray)
			throws SQLException {
		
		final List<OlcVpsUninvoicedViolsRec> olcVpsUninvoicedViolsRecList = new ArrayList<OlcVpsUninvoicedViolsRec>();
		
		if(uninvTypeArray !=null) {
			
			final Object [] uninvTypeObjectArray = (Object [])uninvTypeArray.getArray();
			
			for (int j = 0; j < uninvTypeObjectArray.length; j++) {
				

				if(uninvTypeObjectArray[j] instanceof Struct) {
					
					final Struct uninvTypeStruct = (Struct) uninvTypeObjectArray[j];
					final Object[] uninvTypeStructAttributes = uninvTypeStruct.getAttributes();
					
					final OlcVpsUninvoicedViolsRec olcVpsUninvoicedViolsRec = new OlcVpsUninvoicedViolsRec();
					
					olcVpsUninvoicedViolsRec.setAgencyId((BigDecimal) uninvTypeStructAttributes[0]);
					olcVpsUninvoicedViolsRec.setAgencyName((String) uninvTypeStructAttributes[1]);
					olcVpsUninvoicedViolsRec.setViolationId((BigDecimal) uninvTypeStructAttributes[2]);
					olcVpsUninvoicedViolsRec.setViolatorId((BigDecimal) uninvTypeStructAttributes[3]);
					olcVpsUninvoicedViolsRec.setFullLocationName((String) uninvTypeStructAttributes[4]);
					olcVpsUninvoicedViolsRec.setViolationDateTime((Date) uninvTypeStructAttributes[5]);
					olcVpsUninvoicedViolsRec.setStatus((String) uninvTypeStructAttributes[6]);
					olcVpsUninvoicedViolsRec.setLicPlate((String) uninvTypeStructAttributes[7]);
					olcVpsUninvoicedViolsRec.setLicState((String) uninvTypeStructAttributes[8]);
					olcVpsUninvoicedViolsRec.setOrigTollAmt((BigDecimal) uninvTypeStructAttributes[9]);
					olcVpsUninvoicedViolsRec.setOpenTollAmt((BigDecimal) uninvTypeStructAttributes[10]);
					olcVpsUninvoicedViolsRec.setPaidTollAmt((BigDecimal) uninvTypeStructAttributes[11]);
					
					olcVpsUninvoicedViolsRecList.add(olcVpsUninvoicedViolsRec);
					
				}
				else if(uninvTypeObjectArray[j] instanceof OLC_VPS_UNINVOICED_VIOLS_REC) {
					
					final OLC_VPS_UNINVOICED_VIOLS_REC oracleOlcVpsUninvoicedViolsRec = (OLC_VPS_UNINVOICED_VIOLS_REC) uninvTypeObjectArray[j];
					
					final OlcVpsUninvoicedViolsRec olcVpsUninvoicedViolsRec = new OlcVpsUninvoicedViolsRec();
					
					olcVpsUninvoicedViolsRec.setAgencyId(oracleOlcVpsUninvoicedViolsRec.getAGENCY_ID());
					olcVpsUninvoicedViolsRec.setAgencyName(oracleOlcVpsUninvoicedViolsRec.getAGENCY_NAME());
					olcVpsUninvoicedViolsRec.setViolationId(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_ID());
					olcVpsUninvoicedViolsRec.setViolatorId(oracleOlcVpsUninvoicedViolsRec.getVIOLATOR_ID());
					olcVpsUninvoicedViolsRec.setFullLocationName(oracleOlcVpsUninvoicedViolsRec.getFULL_LOCATION_NAME());
					
					if(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_DATE_TIME() !=null) {
						
						olcVpsUninvoicedViolsRec.setViolationDateTime(new Date(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_DATE_TIME().getTime()));
					}
					
					olcVpsUninvoicedViolsRec.setStatus(oracleOlcVpsUninvoicedViolsRec.getSTATUS());
					olcVpsUninvoicedViolsRec.setLicPlate(oracleOlcVpsUninvoicedViolsRec.getLIC_PLATE());
					olcVpsUninvoicedViolsRec.setLicState(oracleOlcVpsUninvoicedViolsRec.getLIC_STATE());
					olcVpsUninvoicedViolsRec.setOrigTollAmt(oracleOlcVpsUninvoicedViolsRec.getORIG_TOLL_AMT());
					olcVpsUninvoicedViolsRec.setOpenTollAmt(oracleOlcVpsUninvoicedViolsRec.getOPEN_TOLL_AMT());
					olcVpsUninvoicedViolsRec.setPaidTollAmt(oracleOlcVpsUninvoicedViolsRec.getPAID_TOLL_AMT());
					
					olcVpsUninvoicedViolsRecList.add(olcVpsUninvoicedViolsRec);
					
				}else {
					throw new SQLException("Unknown DB Class " + uninvTypeObjectArray[j].getClass().getCanonicalName());
				}
			}
			
		}
		
		return olcVpsUninvoicedViolsRecList;
	}

	private List<OlcVpsInvTypeRec> getInvTypeList(final Array invTypeArr) throws SQLException {
		
		final List<OlcVpsInvTypeRec> olcVpsInvTypeRecList = new ArrayList<OlcVpsInvTypeRec>();
		
		if (invTypeArr != null) {
			
			final Object[] invTypeObjArr = (Object[]) invTypeArr.getArray();
			
			for (int j = 0; j < invTypeObjArr.length; j++) {
				

				if(invTypeObjArr[j] instanceof Struct) {
					
					final Struct invTypeStruct = (Struct) invTypeObjArr[j];
					
					final Object[] invTypeObjectAttributes = invTypeStruct.getAttributes();

					final OlcVpsInvTypeRec olcVpsInvTypeRec = new OlcVpsInvTypeRec();
					
					olcVpsInvTypeRec.setInvoiceId((BigDecimal) invTypeObjectAttributes[0]);
					olcVpsInvTypeRec.setInvoiceNumber((String) invTypeObjectAttributes[1]);
					olcVpsInvTypeRec.setInvoiceDate((Date) invTypeObjectAttributes[2]);
					olcVpsInvTypeRec.setLicPlate((String) invTypeObjectAttributes[3]);
					olcVpsInvTypeRec.setLicState((String) invTypeObjectAttributes[4]);
					olcVpsInvTypeRec.setDueDate((Date) invTypeObjectAttributes[5]);
					olcVpsInvTypeRec.setAgencyId((String) invTypeObjectAttributes[6]);
					olcVpsInvTypeRec.setAgencyName((String) invTypeObjectAttributes[7]);
					olcVpsInvTypeRec.setInvEscStCd((String) invTypeObjectAttributes[8]);
					olcVpsInvTypeRec.setOpenTollAmount((BigDecimal) invTypeObjectAttributes[9]);
					olcVpsInvTypeRec.setOrigTollAmount((BigDecimal) invTypeObjectAttributes[10]);
					olcVpsInvTypeRec.setOpenAdminFees((BigDecimal) invTypeObjectAttributes[11]);
					olcVpsInvTypeRec.setOrigAdminFees((BigDecimal) invTypeObjectAttributes[12]);
					olcVpsInvTypeRec.setOpenCollectionFees((BigDecimal) invTypeObjectAttributes[13]);
					olcVpsInvTypeRec.setOrigCollectionFees((BigDecimal) invTypeObjectAttributes[14]);
					olcVpsInvTypeRec.setOpenOtherFees((BigDecimal) invTypeObjectAttributes[15]);
					olcVpsInvTypeRec.setOrigOtherFees((BigDecimal) invTypeObjectAttributes[16]);
					olcVpsInvTypeRec.setInvoiceAmt((BigDecimal) invTypeObjectAttributes[17]);
					olcVpsInvTypeRec.setInvPaidAmount((BigDecimal) invTypeObjectAttributes[18]);
					olcVpsInvTypeRec.setInvOpenAmount((BigDecimal) invTypeObjectAttributes[19]);
					olcVpsInvTypeRec.setInvExcAmount((BigDecimal) invTypeObjectAttributes[20]);
					olcVpsInvTypeRec.setInvFeeOpenAmt((BigDecimal) invTypeObjectAttributes[21]);
					olcVpsInvTypeRec.setInvFeeExcAmt((BigDecimal) invTypeObjectAttributes[22]);
					olcVpsInvTypeRec.setInvDueAfterExcAmt((BigDecimal) invTypeObjectAttributes[23]);
					
					final Array lineItemsArray = (Array) invTypeObjectAttributes[24];
					final List<OlcVpsInvRec> olcVpsInvRecList = getInvoiceLineItems(lineItemsArray);
					olcVpsInvTypeRec.getLineItems().addAll(olcVpsInvRecList);
					
					olcVpsInvTypeRec.setDiscountRule((String) invTypeObjectAttributes[25]);
					olcVpsInvTypeRec.setAcctConvExcAmt((BigDecimal) invTypeObjectAttributes[26]);
					olcVpsInvTypeRec.setInvDueAfterAcctConvExAmt((BigDecimal) invTypeObjectAttributes[27]);
					olcVpsInvTypeRec.setInvoiceFineAmount((BigDecimal) invTypeObjectAttributes[28]);
					
					final Array openFeeArray =  (Array)invTypeObjectAttributes[29];
					final List<OlcOpenFeeRec> invoiceOpenFeeRecords = getInvoiceOpenFeeeArray(openFeeArray);
					olcVpsInvTypeRec.getOpenFeeArr().addAll(invoiceOpenFeeRecords);
					
					olcVpsInvTypeRecList.add(olcVpsInvTypeRec);
					
				}else if(invTypeObjArr[j] instanceof OLC_VPS_INV_TYPE_REC) {
					
					final OLC_VPS_INV_TYPE_REC oracleOlcVpsInvTypeRec = (OLC_VPS_INV_TYPE_REC) invTypeObjArr[j];
					
					final OlcVpsInvTypeRec olcVpsInvTypeRec = new OlcVpsInvTypeRec();
					
					olcVpsInvTypeRec.setInvoiceId(oracleOlcVpsInvTypeRec.getINVOICE_ID());
					olcVpsInvTypeRec.setInvoiceNumber(oracleOlcVpsInvTypeRec.getINVOICE_NUMBER());
					
					if(oracleOlcVpsInvTypeRec.getINVOICE_DATE() !=null) {
						
						olcVpsInvTypeRec.setInvoiceDate(new Date(oracleOlcVpsInvTypeRec.getINVOICE_DATE().getTime()));
					}
					
					olcVpsInvTypeRec.setLicPlate(oracleOlcVpsInvTypeRec.getLIC_PLATE());
					olcVpsInvTypeRec.setLicState(oracleOlcVpsInvTypeRec.getLIC_STATE());
					
					if(oracleOlcVpsInvTypeRec.getDUE_DATE( )!=null) {
						
						olcVpsInvTypeRec.setDueDate(new Date (oracleOlcVpsInvTypeRec.getDUE_DATE().getTime()));
					}
					
					olcVpsInvTypeRec.setAgencyId(oracleOlcVpsInvTypeRec.getAGENCY_ID());
					olcVpsInvTypeRec.setAgencyName(oracleOlcVpsInvTypeRec.getAGENCY_NAME());
					olcVpsInvTypeRec.setInvEscStCd(oracleOlcVpsInvTypeRec.getINV_ESC_ST_CD());
					olcVpsInvTypeRec.setOpenTollAmount(oracleOlcVpsInvTypeRec.getOPEN_TOLL_AMOUNT());
					olcVpsInvTypeRec.setOrigTollAmount(oracleOlcVpsInvTypeRec.getORIG_TOLL_AMOUNT());
					olcVpsInvTypeRec.setOpenAdminFees(oracleOlcVpsInvTypeRec.getOPEN_ADMIN_FEES());
					olcVpsInvTypeRec.setOrigAdminFees(oracleOlcVpsInvTypeRec.getORIG_ADMIN_FEES());
					olcVpsInvTypeRec.setOpenCollectionFees(oracleOlcVpsInvTypeRec.getOPEN_COLLECTION_FEES());
					olcVpsInvTypeRec.setOrigCollectionFees(oracleOlcVpsInvTypeRec.getORIG_COLLECTION_FEES());
					olcVpsInvTypeRec.setOpenOtherFees(oracleOlcVpsInvTypeRec.getOPEN_OTHER_FEES() );
					olcVpsInvTypeRec.setOrigOtherFees(oracleOlcVpsInvTypeRec.getORIG_OTHER_FEES());
					olcVpsInvTypeRec.setInvoiceAmt(oracleOlcVpsInvTypeRec.getINVOICE_AMT());
					olcVpsInvTypeRec.setInvPaidAmount(oracleOlcVpsInvTypeRec.getINV_PAID_AMOUNT());
					olcVpsInvTypeRec.setInvOpenAmount(oracleOlcVpsInvTypeRec.getINV_OPEN_AMOUNT());
					olcVpsInvTypeRec.setInvExcAmount(oracleOlcVpsInvTypeRec.getINV_EXC_AMOUNT());
					olcVpsInvTypeRec.setInvFeeOpenAmt(oracleOlcVpsInvTypeRec.getINV_FEE_OPEN_AMT());
					olcVpsInvTypeRec.setInvFeeExcAmt(oracleOlcVpsInvTypeRec.getINV_FEE_EXC_AMT());
					olcVpsInvTypeRec.setInvDueAfterExcAmt(oracleOlcVpsInvTypeRec.getINV_DUE_AFTER_EXC_AMT());
					
					final OLC_VPS_INV_ARR_D lineItemsArray = oracleOlcVpsInvTypeRec.getLINE_ITEMS();
					final List<OlcVpsInvRec> olcVpsInvRecList = getInvoiceLineItemsByClassType(lineItemsArray);
					olcVpsInvTypeRec.getLineItems().addAll(olcVpsInvRecList);
					
					olcVpsInvTypeRec.setDiscountRule(oracleOlcVpsInvTypeRec.getDISCOUNT_RULE());
					olcVpsInvTypeRec.setAcctConvExcAmt(oracleOlcVpsInvTypeRec.getACCT_CONV_EXC_AMT());
					olcVpsInvTypeRec.setInvDueAfterAcctConvExAmt(oracleOlcVpsInvTypeRec.getINV_DUE_AFTER_ACCT_CONV_EX_AMT());
					olcVpsInvTypeRec.setInvoiceFineAmount(oracleOlcVpsInvTypeRec. getINVOICE_FINE_AMOUNT());
					
					final OLC_OPEN_FEE_ARR openFeeArray =  oracleOlcVpsInvTypeRec.getOPEN_FEE_ARR();
					final List<OlcOpenFeeRec> invoiceOpenFeeRecords = getInvoiceOpenFeeeArrayByOracleClassType(openFeeArray);
					olcVpsInvTypeRec.getOpenFeeArr().addAll(invoiceOpenFeeRecords);
					
					olcVpsInvTypeRecList.add(olcVpsInvTypeRec);
					
				}else {
					throw new SQLException("Unknown DB Class "+invTypeObjArr[j].getClass().getCanonicalName());
				}
			
			}
			
		}
		return olcVpsInvTypeRecList;
	}

	private List<OlcOpenFeeRec> getInvoiceOpenFeeeArray(Array openFeeArray) throws SQLException {
		final List<OlcOpenFeeRec> invoiceOpenFeeRecords = new ArrayList<OlcOpenFeeRec>();
		if(openFeeArray !=null) {
			final Object[] openFeeObjectArray = (Object[]) openFeeArray.getArray();
			
			for (int k = 0; k < openFeeObjectArray.length; k++) {
				
				if(openFeeObjectArray[k] instanceof Struct) {
					
					final Struct openFeeStruct = (Struct) openFeeObjectArray[k];
					final Object[] openFeeStructAttributes = openFeeStruct.getAttributes();
					
					final OlcOpenFeeRec openFeeRec = new OlcOpenFeeRec();
					
					openFeeRec.setFeeCode((String) openFeeStructAttributes[0]);
					openFeeRec.setFeeDueOpenAmt((BigDecimal) openFeeStructAttributes[1]);
					openFeeRec.setFeeDiscAmt((BigDecimal) openFeeStructAttributes[2]);
					openFeeRec.setFeeDueAfterDiscAmt((BigDecimal) openFeeStructAttributes[3]);
					openFeeRec.setRetailDetailId((BigDecimal) openFeeStructAttributes[4]);
					openFeeRec.setFeeGrpId((BigDecimal) openFeeStructAttributes[5]);
					
					invoiceOpenFeeRecords.add(openFeeRec);
					
				}else if(openFeeObjectArray[k] instanceof OLC_OPEN_FEE_REC) {
					
					final OLC_OPEN_FEE_REC olcOpenFeeRec = (OLC_OPEN_FEE_REC) openFeeObjectArray[k];
					
					final OlcOpenFeeRec openFeeRec = new OlcOpenFeeRec();
					
					openFeeRec.setFeeCode(olcOpenFeeRec.getFEE_CODE());
					openFeeRec.setFeeDueOpenAmt(olcOpenFeeRec.getFEE_DUE_OPEN_AMT());
					openFeeRec.setFeeDiscAmt(olcOpenFeeRec.getFEE_DISC_AMT());
					openFeeRec.setFeeDueAfterDiscAmt(olcOpenFeeRec.getFEE_DUE_AFTER_DISC_AMT());
					openFeeRec.setRetailDetailId(olcOpenFeeRec.getRETAIL_DETAIL_ID());
					openFeeRec.setFeeGrpId(olcOpenFeeRec.getFEE_GRP_ID());
					
					invoiceOpenFeeRecords.add(openFeeRec);
					
				}else {
					throw new SQLException("Unknown DB Class " + openFeeObjectArray[k].getClass().getCanonicalName());
				}
				
			}
			
		}
		return invoiceOpenFeeRecords;
	}

	private List<OlcVpsInvRec> getInvoiceLineItems(final Array lineItemsArray) throws SQLException {

		final List<OlcVpsInvRec> olcVpsInvRecList = new ArrayList<OlcVpsInvRec>();

		if (lineItemsArray != null) {

			final Object[] lineItemsObjectArray = (Object[]) lineItemsArray.getArray();

			for (int k = 0; k < lineItemsObjectArray.length; k++) {

				if (lineItemsObjectArray[k] instanceof Struct) {

					final Struct lineItemsStruct = (Struct) lineItemsObjectArray[k];
					final Object[] lineItemsStructAttributes = lineItemsStruct.getAttributes();

					final OlcVpsInvRec olcVpsInvRec = new OlcVpsInvRec();
					
					olcVpsInvRec.setTransactionId((BigDecimal) lineItemsStructAttributes[0]);
					olcVpsInvRec.setInvoiceNumber((String) lineItemsStructAttributes[1]);
					olcVpsInvRec.setAmount((BigDecimal) lineItemsStructAttributes[2]);
					olcVpsInvRec.setTransactionDate((Date) lineItemsStructAttributes[3]);
					olcVpsInvRec.setStatus((String) lineItemsStructAttributes[4]);
					olcVpsInvRec.setvLocation((String) lineItemsStructAttributes[5]);
					olcVpsInvRec.setOrigTollAmount((BigDecimal) lineItemsStructAttributes[6]);

					olcVpsInvRecList.add(olcVpsInvRec);
					
				} else if (lineItemsObjectArray[k] instanceof OLC_VPS_INV_REC_D) {

					final OLC_VPS_INV_REC_D olcVpsInvRecD = (OLC_VPS_INV_REC_D) lineItemsObjectArray[k];

					final OlcVpsInvRec olcVpsInvRec = new OlcVpsInvRec();
					olcVpsInvRec.setTransactionId(olcVpsInvRecD.getTRANSACTION_ID());
					olcVpsInvRec.setInvoiceNumber(olcVpsInvRecD.getINVOICE_NUMBER());
					olcVpsInvRec.setAmount(olcVpsInvRecD.getAMOUNT());
					
					if(olcVpsInvRecD.getTRANSACTION_DATE() !=null) {
						
						olcVpsInvRec.setTransactionDate(new Date(olcVpsInvRecD.getTRANSACTION_DATE().getTime()));
					}
					olcVpsInvRec.setStatus(olcVpsInvRecD.getSTATUS());
					olcVpsInvRec.setvLocation(olcVpsInvRecD.getV_LOCATION());
					olcVpsInvRec.setOrigTollAmount(olcVpsInvRecD.getORIG_TOLL_AMOUNT());
					
					olcVpsInvRecList.add(olcVpsInvRec);
					
				} else {
					throw new SQLException("Unknown DB Class " + lineItemsObjectArray[k].getClass().getCanonicalName());
				}

			}
		}

		return olcVpsInvRecList;
	}
	
	public SearchOpenViolationResponse searchOpenViolations(String licPlate, String licState) throws EtccException {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		SearchOpenViolationResponse searchOpenViolationResponse = null;
		try {
			cstmt = this.conn.prepareCall("{ call get_transaction_dtls_for_portal(?, ?, ?)}");

			cstmt.setString(1, licPlate);
			cstmt.setString(2, licState);
			cstmt.registerOutParameter(3, Types.REF_CURSOR);

			cstmt.execute();
			searchOpenViolationResponse = new SearchOpenViolationResponse();

			rs = (ResultSet) cstmt.getObject(3);
			if (rs != null) {
				final List<ViolationDetails> violationDetailsList = new ArrayList<ViolationDetails>();

				while (rs.next()) {
					final ViolationDetails violationDetails = new ViolationDetails();
					violationDetails.setAgencyName(rs.getString(1));
					violationDetails.setPlateNumber(rs.getString(2));
					violationDetails.setJurisdiction(rs.getString(3));
					violationDetails.setType(rs.getString(4));
					violationDetails.setLocation(rs.getString(5));
					violationDetails.setTollCharge(rs.getBigDecimal(6));
					violationDetails.setInvoiceNumber(rs.getBigDecimal(7));
					violationDetails.setTransactionId(rs.getBigDecimal(8));
					violationDetails.setTimeStamp(rs.getString(9));

					violationDetailsList.add(violationDetails);

				}
				searchOpenViolationResponse.getViolationDetails().addAll(violationDetailsList);
			}

		} catch (Exception exception) {
			throw new EtccException("Exception in get_transaction_dtls_for_portal:	" + exception.getMessage(),
					exception);

		} finally {
			close(rs);
			close(cstmt);
		}
		return searchOpenViolationResponse;
	}
	
	public GetViolationResponseDTO getViolationsForCCRMA(final String licPlate, final String licState,
			final String invoiceNo, final Long accountId, final String ipAddress, final String jsessionId,
			final String sourceUserName, final String user, final String sessionId, final Integer PmtPlanId)
			throws Exception {
		CallableStatement cstmt = null;
		GetViolationResponseDTO getViolationResponseDTO = null;

		try {
			cstmt = this.conn.prepareCall(
					"{call OL_OWNER.OLCSC_VIOLATION_MGMT_API.get_violations(? , ?, ? , ?, ? , ?, ?, ? , ?, ? , ?, ? , ?, ?)}");

			setTypeMap();

			cstmt.setString(1, licPlate);
			cstmt.setString(2, licState);
			cstmt.setString(3, invoiceNo);

			if (accountId != null) {

				cstmt.setLong(4, accountId);
			} else {
				cstmt.setObject(4, null);
			}

			cstmt.setString(5, ipAddress);
			cstmt.setString(6, jsessionId);
			cstmt.setString(7, sourceUserName);
			cstmt.setString(8, user);
			cstmt.registerOutParameter(9, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_PLATE_ARR");
			cstmt.registerOutParameter(10, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.setString(11, sessionId);
			cstmt.registerOutParameter(11, Types.VARCHAR);

			cstmt.registerOutParameter(12, Types.NUMERIC);
			cstmt.registerOutParameter(13, Types.STRUCT, "OL_OWNER.OLC_VPS_ACCOUNT_CONV_REC");

			if (PmtPlanId != null) {

				cstmt.setInt(14, PmtPlanId);
			} else {
				cstmt.setObject(14, null);
			}

			cstmt.execute();

			getViolationResponseDTO = new GetViolationResponseDTO();

			getViolationResponseDTO.setSessionId(cstmt.getString(11));
			getViolationResponseDTO.setAccountId(cstmt.getLong(12));

			final Array accountPlateArr = (Array) cstmt.getObject(9);

			if (accountPlateArr != null) {
				final List<OlcAccountPlateRec> accountPlateRecordList = getAccountPlateList(accountPlateArr);
				getViolationResponseDTO.getAccountPlateArr().addAll(accountPlateRecordList);

				for (OlcAccountPlateRec olcAccountPlateRec : getViolationResponseDTO.getAccountPlateArr()) {

					final List<OlcVpsPlateTypeRec> olcPlateTypeArr = olcAccountPlateRec.getOlcPlateTypeArr();

					for (OlcVpsPlateTypeRec olcVpsPlateTypeRec : olcPlateTypeArr) {

						final List<OlcVpsInvTypeRec> invTypeArr = olcVpsPlateTypeRec.getInvTypeArr();
						final List<Integer> indexList = new ArrayList<Integer>();

						for (int i = 0; i < invTypeArr.size(); i++) {
							final OlcVpsInvTypeRec olcVpsInvTypeRec = invTypeArr.get(i);

							if ("CC_CA".equalsIgnoreCase(olcVpsInvTypeRec.getInvEscStCd())) {
								indexList.add(i);
							}
						}
						for (int i = 0; i < indexList.size(); i++) {
							olcVpsPlateTypeRec.getInvTypeArr().remove(i);
							getViolationResponseDTO.setInCollection(true);
						}
					}
				}

			}

			if (cstmt.getObject(13) != null) {

				OlcVpsAccountConvRec vpsAccountConRecord = null;

				if (cstmt.getObject(13) instanceof Struct) {

					final Struct vpsAccountConStruct = (Struct) cstmt.getObject(13);
					vpsAccountConRecord = getVpsAccountConRecord(vpsAccountConStruct);
				} else if (cstmt.getObject(13) instanceof OLC_VPS_ACCOUNT_CONV_REC) {
					final OLC_VPS_ACCOUNT_CONV_REC oracleOlcVpsAccountConvRec = (OLC_VPS_ACCOUNT_CONV_REC) cstmt
							.getObject(13);
					vpsAccountConRecord = getVpsAccountConRecordByOracleClassType(oracleOlcVpsAccountConvRec);
				}

				getViolationResponseDTO.setVpsAccountConvRec(vpsAccountConRecord);

			}

			final Array errors = (Array) cstmt.getObject(10);
			if (errors != null) {
				final Object[] errObjArray = (Object[]) errors.getArray();
				if (errObjArray != null && errObjArray.length > 0) {

					getViolationResponseDTO.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("OLCSC_VIOLATION_MGMT_API.get_violations.acctId=" + accountId);
					logger.error("OLCSC_VIOLATION_MGMT_API.get_violations.error="
							+ ErrorMessageDTO.toStringBuilder(getViolationResponseDTO.getErrors()));
				}
			}

		} finally {
			close(cstmt);
		}

		return getViolationResponseDTO;

	}

	public List<OlcAccountPlateRec> getAccountPlateList(final Array accountPlateArr) throws SQLException {
		final List<OlcAccountPlateRec> accountPlateRecList = new ArrayList<OlcAccountPlateRec>();

		final Object[] accountPlateRecObjArr = (Object[]) accountPlateArr.getArray();

		for (Object object : accountPlateRecObjArr) {

			if(object instanceof Struct) {
				
				final Struct accountPlateRecStruct = (Struct) object;
				final Object[] accountPlateRecAattributes = accountPlateRecStruct.getAttributes();
								
				final OlcAccountPlateRec accountPlateRec = new OlcAccountPlateRec();

				accountPlateRec.setDiscountRule((String) accountPlateRecAattributes[0]);
				accountPlateRec.setAccountId((BigDecimal) accountPlateRecAattributes[1]);
				accountPlateRec.setAccountPlan((String) accountPlateRecAattributes[2]);
				accountPlateRec.setTtlAmountDue((BigDecimal) accountPlateRecAattributes[3]);
				accountPlateRec.setOrigInvFeeAmount((BigDecimal) accountPlateRecAattributes[4]);
				accountPlateRec.setOpenInvFeeAmount((BigDecimal) accountPlateRecAattributes[5]);
				accountPlateRec.setOrigInvAmount((BigDecimal) accountPlateRecAattributes[6]);
				accountPlateRec.setOpenInvAmount((BigDecimal) accountPlateRecAattributes[7]);
				accountPlateRec.setOrigUninvAmount((BigDecimal) accountPlateRecAattributes[8]);
				accountPlateRec.setOpenUninvAmount((BigDecimal) accountPlateRecAattributes[9]);
				accountPlateRec.setTotalPaidAmount((BigDecimal) accountPlateRecAattributes[10]);
				accountPlateRec.setTotalExcAmount((BigDecimal) accountPlateRecAattributes[11]);
				accountPlateRec.setAccountDueAfterExc((BigDecimal) accountPlateRecAattributes[12]);
				accountPlateRec.setAccountDueBeforeExc((BigDecimal) accountPlateRecAattributes[13]);
				accountPlateRec.setServiceFee((BigDecimal) accountPlateRecAattributes[14]);
				accountPlateRec.setInvTollOpenAmount((BigDecimal) accountPlateRecAattributes[15]);
				accountPlateRec.setUnivTollOpenAmount((BigDecimal) accountPlateRecAattributes[16]);
				accountPlateRec.setTtlTollOpenAmount((BigDecimal) accountPlateRecAattributes[17]);
				
				final Array olcVpsPlateTypeArray = (Array) accountPlateRecAattributes[18];
				if (olcVpsPlateTypeArray != null) {
					accountPlateRec.getOlcPlateTypeArr().addAll(getOlcVpsPlateTypeList(olcVpsPlateTypeArray));
				}
				
				accountPlateRec.setSessionId((String) accountPlateRecAattributes[19]);
				
				accountPlateRecList.add(accountPlateRec);
				
			}else if(object instanceof OLC_ACCOUNT_PLATE_REC) {
				
				final OLC_ACCOUNT_PLATE_REC oracleOlcAaccountPlateRec = (OLC_ACCOUNT_PLATE_REC) object;
				
				final OlcAccountPlateRec accountPlateRec = new OlcAccountPlateRec();
								
				accountPlateRec.setDiscountRule(oracleOlcAaccountPlateRec.getDISCOUNT_RULE());
				accountPlateRec.setAccountId(oracleOlcAaccountPlateRec.getACCOUNT_ID());
				accountPlateRec.setAccountPlan(oracleOlcAaccountPlateRec.getACCOUNT_PLAN());
				accountPlateRec.setTtlAmountDue(oracleOlcAaccountPlateRec.getTTL_AMOUNT_DUE());
				accountPlateRec.setOrigInvFeeAmount(oracleOlcAaccountPlateRec.getORIG_INV_FEE_AMOUNT());
				accountPlateRec.setOpenInvFeeAmount(oracleOlcAaccountPlateRec.getOPEN_INV_FEE_AMOUNT());
				accountPlateRec.setOrigInvAmount(oracleOlcAaccountPlateRec.getORIG_INV_AMOUNT() );
				accountPlateRec.setOpenInvAmount(oracleOlcAaccountPlateRec.getOPEN_INV_AMOUNT());
				accountPlateRec.setOrigUninvAmount(oracleOlcAaccountPlateRec. getORIG_UNINV_AMOUNT());
				accountPlateRec.setOpenUninvAmount(oracleOlcAaccountPlateRec.getOPEN_UNINV_AMOUNT());
				accountPlateRec.setTotalPaidAmount(oracleOlcAaccountPlateRec.getTOTAL_PAID_AMOUNT());
				accountPlateRec.setTotalExcAmount(oracleOlcAaccountPlateRec.getTOTAL_EXC_AMOUNT());
				accountPlateRec.setAccountDueAfterExc(oracleOlcAaccountPlateRec.getACCOUNT_DUE_AFTER_EXC());
				accountPlateRec.setAccountDueBeforeExc(oracleOlcAaccountPlateRec.getACCOUNT_DUE_BEFORE_EXC());
				accountPlateRec.setServiceFee(oracleOlcAaccountPlateRec.getSERVICE_FEE());
				accountPlateRec.setInvTollOpenAmount(oracleOlcAaccountPlateRec.getINV_TOLL_OPEN_AMOUNT());
				accountPlateRec.setUnivTollOpenAmount(oracleOlcAaccountPlateRec.getUNIV_TOLL_OPEN_AMOUNT());
				accountPlateRec.setTtlTollOpenAmount(oracleOlcAaccountPlateRec.getTTL_TOLL_OPEN_AMOUNT());
				
				final OLC_VPS_PLATE_TYPE_ARR oracleOlcVpsPlateTypeArr= oracleOlcAaccountPlateRec.getOLC_PLATE_TYPE_ARR();
				
				if (oracleOlcVpsPlateTypeArr != null) {
					accountPlateRec.getOlcPlateTypeArr().addAll(getOlcVpsPlateTypeListByOracleClassType(oracleOlcVpsPlateTypeArr));
				}
				
				accountPlateRec.setSessionId(oracleOlcAaccountPlateRec.getSESSION_ID());
				
				accountPlateRecList.add(accountPlateRec);
				
			}else {
				throw new SQLException("Unknown DB Class "+object.getClass().getCanonicalName());
			}
			

		}

		return accountPlateRecList;
	}
	
	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {

		ResultDTO resultDTO = null;
		try {

			Map<String, Class<?>> setTypeMap = setTypeMap();
			setTypeMap.put("OL_OWNER.OLC_VPS_BILLING_INFO_REC", OLC_VPS_BILLING_INFO_REC.class);
			setTypeMap.put("OL_OWNER.OLC_INV_PMT_ARR", OLC_INV_PMT_ARR.class);
			setTypeMap.put("OLC_INV_PMT_REC", OLC_INV_PMT_REC.class);
			setTypeMap.put("OL_OWNER.OLC_UNINV_PMT_ARR", OLC_UNINV_PMT_ARR.class);
			setTypeMap.put("OL_OWNER.OLC_UNINV_PMT_REC", OLC_UNINV_PMT_REC.class);
			setTypeMap.put("OL_OWNER.OLC_OPEN_FEE_ARR", OLC_OPEN_FEE_ARR.class);
			setTypeMap.put("OL_OWNER.OLC_OPEN_FEE_REC", OLC_OPEN_FEE_REC.class);
			setTypeMap.put("OL_OWNER.OLC_VPS_INV_ARR_D", OLC_VPS_INV_ARR_D.class);
			setTypeMap.put("OL_OWNER.OLC_VPS_INV_REC_D", OLC_VPS_INV_REC_D.class);

			final AccountLoginDTO acctLoginDto = makeViolationPaymentRequest.getAcctLoginDto();

			final PaymentType paymentType = makeViolationPaymentRequest.getBillingInfoDTO().getBillingType();
			AccountPaymentMethodDTO paymentMethodDTO = null;

			if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())) {
				paymentMethodDTO = makeViolationPaymentRequest.getBillingInfoDTO().getCards()[0];
				// added year and month validation
				AccountCreditCardDTO ccDTO = (AccountCreditCardDTO) paymentMethodDTO;
				if (ccDTO.getCardType() != null && !ccDTO.getCardType().toString().equals(StringUtil.AMERICANEXPRESS)) {
					if (ccDTO.getCardExpires() != null && ccDTO.getCardExpires().length() > 0) {
						if (StringUtil.validateCardExpiryDate(ccDTO.getCardExpires().toString())) {
							resultDTO = new ResultDTO();
							resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
							return resultDTO;
						}
					}
				}
				// set token if required.
				CCTokenDAOHelper.getInstance().getCCToken(acctLoginDto, paymentMethodDTO);

			} else { // EFT
				paymentMethodDTO = makeViolationPaymentRequest.getBillingInfoDTO().getEft();

				// QC_9289 blocking payment if bank account no **** and acc billing method is
				// null
				AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethodDTO;
				if (eftDto.getAccountBillingMethodId() == null && eftDto.getAccountNumber() != null
						&& eftDto.getAccountNumber().contains("*")) {
					resultDTO = new ResultDTO();
					resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getBankAccountErrorMessage()));
					return resultDTO;
				}

			}

			final OLC_VPS_BILLING_INFO_REC billingInfoRec = convertToOlcVpsBillingInfoRec(paymentMethodDTO);

			final OLC_INV_PMT_ARR invoiceArray = new OLC_INV_PMT_ARR(
					convertToOlcInvPmtRecArr(makeViolationPaymentRequest.getInvoicePaymentArray()));

			final OLC_UNINV_PMT_ARR unInvoiceArray = new OLC_UNINV_PMT_ARR(
					convertToOlcUninvPmtRec(makeViolationPaymentRequest.getUninvoicePaymentArray()));

			final OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

			final String[] sessionIdArr = new String[] { acctLoginDto.getDbSessionId() };

			final int result = new OLCSC_PAYMENT(this.conn).MAKE_VIOLATION_PAYMENT(new BigDecimal(acctLoginDto.getAcctId()),
					makeViolationPaymentRequest.getAccountVehicleId(), makeViolationPaymentRequest.getEmailAddress(),
					makeViolationPaymentRequest.getEventId(), acctLoginDto.getLastLoginIp(), sessionIdArr,
					makeViolationPaymentRequest.getUiCall(), acctLoginDto.getLoginId(),
					makeViolationPaymentRequest.getAddressId(), makeViolationPaymentRequest.getDeliveryMethod(),
					makeViolationPaymentRequest.getIsMotorCycle(), makeViolationPaymentRequest.getLicPlate(),
					makeViolationPaymentRequest.getLicState(), billingInfoRec,
					makeViolationPaymentRequest.getSourceUserName(), makeViolationPaymentRequest.getTagActivationFee(),
					makeViolationPaymentRequest.getInitialPrepaidBal(),
					makeViolationPaymentRequest.getConvertViolator(), makeViolationPaymentRequest.getDeliveryMode(),
					makeViolationPaymentRequest.getTotalPaidAmount(),
					makeViolationPaymentRequest.getTotalDiscountAmount(), makeViolationPaymentRequest.getStatusReason(),
					makeViolationPaymentRequest.getServiceFeePaid(), invoiceArray, unInvoiceArray, O_ERROR_MSG_ARR,
					makeViolationPaymentRequest.getNewLogin()).intValue();

			logger.debug("makeViolationPayment.result=" + result);

			resultDTO = new ResultDTO();

			if (result == 0) {
				resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
				if (resultDTO.hasErrors()) {
					logger.error(
							"makeViolationPayment.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
				}

			}
			
		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_PAYMENT.MAKE_VIOLATION_PAYMENT:	" + exception.getMessage(),
					exception);

		}

		return resultDTO;

	}

	private OLC_VPS_BILLING_INFO_REC convertToOlcVpsBillingInfoRec(AccountPaymentMethodDTO paymentMethod)
			throws SQLException, EtccException {

		final OLC_VPS_BILLING_INFO_REC billingRec = new OLC_VPS_BILLING_INFO_REC();

		if (paymentMethod.getPaymentType() == PaymentType.EFT) {

			final AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethod;

			BigDecimal accountBillingMethodId = eftDto.getAccountBillingMethodId() != null
					? new BigDecimal(eftDto.getAccountBillingMethodId())
					: null;
			billingRec.setPMT_TYPE(PaymentType.EFT.getCodeString());
			billingRec.setBANK_ACCOUNT_TYPE_ID(getBankAccountTypeId(eftDto.getAccountType().getCode()));
			billingRec.setROUTING_NUMBER(eftDto.getRoutingNumber());
			billingRec.setBANK_ACCOUNT_NUMBER(eftDto.getAccountNumber());
			billingRec.setNAME_ON_BANK_ACCOUNT(eftDto.getNameOnBankAccount());
		} else if (paymentMethod.getPaymentType() == PaymentType.CREDIT) {

			AccountCreditCardDTO ccDto = (AccountCreditCardDTO) paymentMethod;

			BigDecimal acctId = new BigDecimal(ccDto.getAcctId());
			BigDecimal accountBillingMethodId = ccDto.getAccountBillingMethodId() != null
					? new BigDecimal(ccDto.getAccountBillingMethodId())
					: null;

			BigDecimal personId = ccDto.getPersonId() != null ? new BigDecimal(ccDto.getPersonId()) : null;

			billingRec.setACCOUNT_ID(acctId);
			billingRec.setACCOUNT_BILLING_METHOD_ID(accountBillingMethodId);
			billingRec.setPERSON_ID(personId);
			billingRec.setPMT_TYPE(PaymentType.CREDIT.getCodeString());
			billingRec.setCREDIT_CARD_TYPE_ID(String.valueOf(ccDto.getCardType().getType()));
			billingRec.setNAME_ON_CARD(ccDto.getNameOnCard());
			billingRec.setCC_NUMBER(ccDto.getCardNbr());
			billingRec.setLAST4(ccDto.getCardNbr().substring(ccDto.getCardNbr().length() - 4));

			String cardExpires = ccDto.getCardExpires();
			String[] cardMonthYearArr = cardExpires.split("/");
			billingRec.setCC_EXP_MO(new BigDecimal(cardMonthYearArr[0]));
			billingRec.setCC_EXP_YEAR(new BigDecimal(cardMonthYearArr[1]));

			billingRec.setTOKEN(ccDto.getToken() == 0 ? null : new BigDecimal(ccDto.getToken()));
			//QC  15421 : Added address information for VPS payment
			billingRec.setADDRESS_1(ccDto.getAddress1());
			billingRec.setADDRESS_2(ccDto.getAddress2());
			billingRec.setCITY(ccDto.getCity());
			billingRec.setSTATE(ccDto.getState());
			billingRec.setCOUNTRY(ccDto.getCountry());
			billingRec.setZIPCODE(ccDto.getZip());
			billingRec.setPLUS4(ccDto.getPlus4());
		}
		return billingRec;
	}

	private OLC_INV_PMT_REC[] convertToOlcInvPmtRecArr(List<OlcInvPmtRec> olcInvPmtRecList) throws SQLException {

		OLC_INV_PMT_REC[] olcInvPmtArr = null;

		if (olcInvPmtRecList != null && !olcInvPmtRecList.isEmpty()) {
			olcInvPmtArr = new OLC_INV_PMT_REC[olcInvPmtRecList.size()];

			for (int i = 0; i < olcInvPmtRecList.size(); i++) {
				OlcInvPmtRec olcInvPmtRec = olcInvPmtRecList.get(i);

				OLC_INV_PMT_REC oracleOlcInvPmtRec = new OLC_INV_PMT_REC();

				oracleOlcInvPmtRec.setACCOUNT_ID(olcInvPmtRec.getAccountId());
				oracleOlcInvPmtRec.setINVOICE_ID(olcInvPmtRec.getInvoiceId());
				oracleOlcInvPmtRec.setINVOICE_PAID_AMT(olcInvPmtRec.getInvoicePaidAmt());
				oracleOlcInvPmtRec.setINVOICE_DISC_AMT(olcInvPmtRec.getInvoiceDiscAmt());

				OLC_OPEN_FEE_ARR olcOpenFeeArr = new OLC_OPEN_FEE_ARR(
						convertToInvoiceFeeArr(olcInvPmtRec.getInvoiceFeeArr()));
				oracleOlcInvPmtRec.setINVOICE_FEE_ARR(olcOpenFeeArr);

				oracleOlcInvPmtRec.setDISCOUNT_RULE(olcInvPmtRec.getDiscountRule());

				OLC_VPS_INV_ARR_D lineItemArray = new OLC_VPS_INV_ARR_D(
						convertToLineItemsArr(olcInvPmtRec.getLineItems()));
				oracleOlcInvPmtRec.setLINE_ITEMS(lineItemArray);

				olcInvPmtArr[i] = oracleOlcInvPmtRec;

			}
		}

		return olcInvPmtArr;

	}

	private OLC_OPEN_FEE_REC[] convertToInvoiceFeeArr(List<OlcOpenFeeRec> invoiceFeeList) throws SQLException {

		OLC_OPEN_FEE_REC[] openFeeRecArray = null;

		if (invoiceFeeList != null && !invoiceFeeList.isEmpty()) {
			openFeeRecArray = new OLC_OPEN_FEE_REC[invoiceFeeList.size()];

			for (int i = 0; i < invoiceFeeList.size(); i++) {
				OlcOpenFeeRec olcOpenFeeRec = invoiceFeeList.get(i);

				OLC_OPEN_FEE_REC oracleOlcOpenFeeRec = new OLC_OPEN_FEE_REC();
				oracleOlcOpenFeeRec.setFEE_CODE(olcOpenFeeRec.getFeeCode());
				oracleOlcOpenFeeRec.setFEE_DUE_OPEN_AMT(olcOpenFeeRec.getFeeDueOpenAmt());
				oracleOlcOpenFeeRec.setFEE_DISC_AMT(olcOpenFeeRec.getFeeDiscAmt());
				oracleOlcOpenFeeRec.setFEE_DUE_AFTER_DISC_AMT(olcOpenFeeRec.getFeeDueAfterDiscAmt());
				oracleOlcOpenFeeRec.setRETAIL_DETAIL_ID(olcOpenFeeRec.getRetailDetailId());
				oracleOlcOpenFeeRec.setFEE_GRP_ID(olcOpenFeeRec.getFeeGrpId());

				openFeeRecArray[i] = oracleOlcOpenFeeRec;
			}
		}

		return openFeeRecArray;

	}

	private OLC_VPS_INV_REC_D[] convertToLineItemsArr(List<OlcVpsInvRec> lineItems) throws SQLException {

		OLC_VPS_INV_REC_D[] lineItemArray = null;
		if (lineItems != null && !lineItems.isEmpty()) {

			lineItemArray = new OLC_VPS_INV_REC_D[lineItems.size()];

			for (int i = 0; i < lineItems.size(); i++) {
				OlcVpsInvRec olcVpsInvRec = lineItems.get(i);
				OLC_VPS_INV_REC_D olcVpsInvRecD = new OLC_VPS_INV_REC_D();

				olcVpsInvRecD.setTRANSACTION_ID(olcVpsInvRec.getTransactionId());
				olcVpsInvRecD.setINVOICE_NUMBER(olcVpsInvRec.getInvoiceNumber());
				olcVpsInvRecD.setAMOUNT(olcVpsInvRec.getAmount());
				if(olcVpsInvRec.getTransactionDate() != null) {
					
					olcVpsInvRecD.setTRANSACTION_DATE(new java.sql.Timestamp(olcVpsInvRec.getTransactionDate().getTime()));
				}
				olcVpsInvRecD.setSTATUS(olcVpsInvRec.getStatus());
				olcVpsInvRecD.setV_LOCATION(olcVpsInvRec.getvLocation());
				olcVpsInvRecD.setORIG_TOLL_AMOUNT(olcVpsInvRec.getOrigTollAmount());

				lineItemArray[i] = olcVpsInvRecD;
			}
		}

		return lineItemArray;
	}

	private OLC_UNINV_PMT_REC[] convertToOlcUninvPmtRec(List<OlcUninvPmtRec> olcUninvPmtRecList) throws SQLException {

		OLC_UNINV_PMT_REC[] olcUninvPmtRecArr = null;

		if (olcUninvPmtRecList != null && !olcUninvPmtRecList.isEmpty()) {

			olcUninvPmtRecArr = new OLC_UNINV_PMT_REC[olcUninvPmtRecList.size()];

			for (int i = 0; i < olcUninvPmtRecArr.length; i++) {

				OlcUninvPmtRec olcUninvPmtRec = olcUninvPmtRecList.get(i);

				OLC_UNINV_PMT_REC oracleOlcUninvPmtRec = new OLC_UNINV_PMT_REC();

				oracleOlcUninvPmtRec.setACCOUNT_ID(olcUninvPmtRec.getAccountId());
				oracleOlcUninvPmtRec.setVIOLATION_ID(olcUninvPmtRec.getViolationId());
				oracleOlcUninvPmtRec.setTOLL_PAID_AMOUNT(olcUninvPmtRec.getTollPaidAmount());

				olcUninvPmtRecArr[i] = oracleOlcUninvPmtRec;
			}

		}
		return olcUninvPmtRecArr;

	}

	private BigDecimal getBankAccountTypeId(String bankTypeCode) throws EtccException {
		if (bankTypeCode.equalsIgnoreCase("PC")) {
			return new BigDecimal(1);
		} else if (bankTypeCode.equalsIgnoreCase("PS")) {
			return new BigDecimal(2);
		} else if (bankTypeCode.equalsIgnoreCase("CC")) {
			return new BigDecimal(3);
		} else if (bankTypeCode.equalsIgnoreCase("CS")) {
			return new BigDecimal(4);
		} else {
			throw new EtccException("Invalid Bank Type Code" + bankTypeCode);
		}
	}

	private List<OlcVpsInvRec> getInvoiceLineItemsByClassType(final OLC_VPS_INV_ARR_D lineItemsArray)
			throws SQLException {

		final List<OlcVpsInvRec> olcVpsInvRecList = new ArrayList<OlcVpsInvRec>();

		if (lineItemsArray != null) {

			final OLC_VPS_INV_REC_D[] lineItemsObjectArray = lineItemsArray.getArray();

			for (int k = 0; k < lineItemsObjectArray.length; k++) {

				final OLC_VPS_INV_REC_D olcVpsInvRecD = (OLC_VPS_INV_REC_D) lineItemsObjectArray[k];

				final OlcVpsInvRec olcVpsInvRec = new OlcVpsInvRec();
				olcVpsInvRec.setTransactionId(olcVpsInvRecD.getTRANSACTION_ID());
				olcVpsInvRec.setInvoiceNumber(olcVpsInvRecD.getINVOICE_NUMBER());
				olcVpsInvRec.setAmount(olcVpsInvRecD.getAMOUNT());

				if (olcVpsInvRecD.getTRANSACTION_DATE() != null) {

					olcVpsInvRec.setTransactionDate(new Date(olcVpsInvRecD.getTRANSACTION_DATE().getTime()));
				}

				olcVpsInvRec.setStatus(olcVpsInvRecD.getSTATUS());
				olcVpsInvRec.setvLocation(olcVpsInvRecD.getV_LOCATION());
				olcVpsInvRec.setOrigTollAmount(olcVpsInvRecD.getORIG_TOLL_AMOUNT());

				olcVpsInvRecList.add(olcVpsInvRec);

			}
		}

		return olcVpsInvRecList;
	}

	private OlcVpsAccountConvRec getVpsAccountConRecordByOracleClassType(
			final OLC_VPS_ACCOUNT_CONV_REC oracleOlcVpsAccountConvRec) throws SQLException {

		OlcVpsAccountConvRec olcVpsAccountConvRec = null;
		if (oracleOlcVpsAccountConvRec != null) {

			olcVpsAccountConvRec = new OlcVpsAccountConvRec();

			olcVpsAccountConvRec.setPersonId(oracleOlcVpsAccountConvRec.getPERSON_ID());
			olcVpsAccountConvRec.setAddressId(oracleOlcVpsAccountConvRec.getADDRESS_ID());
			olcVpsAccountConvRec.setAcctConvElig(oracleOlcVpsAccountConvRec.getACCT_CONV_ELIG());
			olcVpsAccountConvRec.setTtlAmtDueForConversion(oracleOlcVpsAccountConvRec.getTTL_AMT_DUE_FOR_CONVERSION());
			olcVpsAccountConvRec.setInitialPrePaidBalance(oracleOlcVpsAccountConvRec.getINITIAL_PRE_PAID_BALANCE());
			olcVpsAccountConvRec.setTagActivationFee(oracleOlcVpsAccountConvRec.getTAG_ACTIVATION_FEE());
			olcVpsAccountConvRec.setAcctConvDiscPct(oracleOlcVpsAccountConvRec.getACCT_CONV_DISC_PCT());
			olcVpsAccountConvRec.setTotalDiscountedAmount(oracleOlcVpsAccountConvRec.getTOTAL_DISCOUNTED_AMOUNT());
		}

		return olcVpsAccountConvRec;
	}

	private List<OlcVpsPlateTypeRec> getOlcVpsPlateTypeListByOracleClassType(OLC_VPS_PLATE_TYPE_ARR olcVpsPlateTtypeArr)
			throws SQLException {

		final List<OlcVpsPlateTypeRec> olcVpsPlateTypeRecList = new ArrayList<OlcVpsPlateTypeRec>();

		for (OLC_VPS_PLATE_TYPE_REC oracleOlcVpsPlateTypeRec : olcVpsPlateTtypeArr.getArray()) {

			final OlcVpsPlateTypeRec olcVpsPlateTypeRec = new OlcVpsPlateTypeRec();

			olcVpsPlateTypeRec.setDiscountRule(oracleOlcVpsPlateTypeRec.getDISCOUNT_RULE());
			olcVpsPlateTypeRec.setTtlAmountDue(oracleOlcVpsPlateTypeRec.getTTL_AMOUNT_DUE());

			final OLC_VPS_INV_TYPE_ARR olcVpsInvTypeArr = oracleOlcVpsPlateTypeRec.getINV_TYPE_ARR();
			final List<OlcVpsInvTypeRec> olcVpsInvTypeRecList = getInvTypeListByOracleClassType(olcVpsInvTypeArr);
			;
			olcVpsPlateTypeRec.getInvTypeArr().addAll(olcVpsInvTypeRecList);

			olcVpsPlateTypeRec.setLicPlate(oracleOlcVpsPlateTypeRec.getLIC_PLATE());
			olcVpsPlateTypeRec.setLicState(oracleOlcVpsPlateTypeRec.getLIC_STATE());
			olcVpsPlateTypeRec.setOrigInvFeeAmount(oracleOlcVpsPlateTypeRec.getORIG_INV_FEE_AMOUNT());
			olcVpsPlateTypeRec.setOpenInvFeeAmount(oracleOlcVpsPlateTypeRec.getOPEN_INV_FEE_AMOUNT());
			olcVpsPlateTypeRec.setOrigInvAmount(oracleOlcVpsPlateTypeRec.getORIG_INV_AMOUNT());
			olcVpsPlateTypeRec.setOpenInvAmount(oracleOlcVpsPlateTypeRec.getOPEN_INV_AMOUNT());
			olcVpsPlateTypeRec.setOrigPlateAmount(oracleOlcVpsPlateTypeRec.getORIG_PLATE_AMOUNT());
			olcVpsPlateTypeRec.setOrigUninvAmount(oracleOlcVpsPlateTypeRec.getORIG_UNINV_AMOUNT());
			olcVpsPlateTypeRec.setOpenUninvAmount(oracleOlcVpsPlateTypeRec.getOPEN_UNINV_AMOUNT());
			olcVpsPlateTypeRec.setTotalPaidAmount(oracleOlcVpsPlateTypeRec.getTOTAL_PAID_AMOUNT());
			olcVpsPlateTypeRec.setTotalExcAmount(oracleOlcVpsPlateTypeRec.getTOTAL_EXC_AMOUNT());
			olcVpsPlateTypeRec.setPlateDueAfterExc(oracleOlcVpsPlateTypeRec.getPLATE_DUE_AFTER_EXC());
			olcVpsPlateTypeRec.setPlateDueBeforeExc(oracleOlcVpsPlateTypeRec.getPLATE_DUE_BEFORE_EXC());

			final OLC_VPS_UNINVOICED_VIOLS_ARR olcVpsUninvoicedViolsArr = oracleOlcVpsPlateTypeRec.getUNINV_TYPE_ARR();
			final List<OlcVpsUninvoicedViolsRec> olcVpsUninvoicedViolsRecList = getUninvTypeListByOracleClassType(
					olcVpsUninvoicedViolsArr);
			olcVpsPlateTypeRec.getUninvTypeArr().addAll(olcVpsUninvoicedViolsRecList);

			olcVpsPlateTypeRec.setServiceFee(oracleOlcVpsPlateTypeRec.getSERVICE_FEE());
			olcVpsPlateTypeRec.setAccountVehicleId(oracleOlcVpsPlateTypeRec.getACCOUNT_VEHICLE_ID());

			final OLC_PMT_PLAN_TYPE_ARR olcPmtPlanTypeArr = oracleOlcVpsPlateTypeRec.getPMT_PLAN_TYPE_ARR();
			final List<OlcPmtPlanTypeRec> paymetPlanTypeRecords = getPaymentPlanTypeListByOracleClassType(
					olcPmtPlanTypeArr);
			olcVpsPlateTypeRec.getPmtPlanTypeArr().addAll(paymetPlanTypeRecords);

			olcVpsPlateTypeRec.setInvTollOpenAmount(oracleOlcVpsPlateTypeRec.getINV_TOLL_OPEN_AMOUNT());
			olcVpsPlateTypeRec.setUnivTollOpenAmount(oracleOlcVpsPlateTypeRec.getUNIV_TOLL_OPEN_AMOUNT());
			olcVpsPlateTypeRec.setTtlTollOpenAmount(oracleOlcVpsPlateTypeRec.getTTL_TOLL_OPEN_AMOUNT());

			olcVpsPlateTypeRec.setVpsAccountConvRec(
					getVpsAccountConRecordByOracleClassType(oracleOlcVpsPlateTypeRec.getVPS_ACCOUNT_CONV_REC()));

			final OLC_VPS_PLATE_AGCY_ARR olcVpsPlateAgcyArr = oracleOlcVpsPlateTypeRec.getVPS_PLATE_AGCY_REC();

			List<OlcVpsPlateAgcyRec> olcVpsPlateAgcyRecList = getPlateAgencyRecordsByOracleClassType(
					olcVpsPlateAgcyArr);
			olcVpsPlateTypeRec.getVpsPlateAgcyRec().addAll(olcVpsPlateAgcyRecList);

			olcVpsPlateTypeRecList.add(olcVpsPlateTypeRec);

		}

		return olcVpsPlateTypeRecList;
	}

	private List<OlcVpsInvTypeRec> getInvTypeListByOracleClassType(final OLC_VPS_INV_TYPE_ARR invTypeArr)
			throws SQLException {

		final List<OlcVpsInvTypeRec> olcVpsInvTypeRecList = new ArrayList<OlcVpsInvTypeRec>();

		if (invTypeArr != null) {

			final OLC_VPS_INV_TYPE_REC[] invTypeObjArr = invTypeArr.getArray();

			for (int j = 0; j < invTypeObjArr.length; j++) {

				final OLC_VPS_INV_TYPE_REC oracleOlcVpsInvTypeRec = invTypeObjArr[j];

				final OlcVpsInvTypeRec olcVpsInvTypeRec = new OlcVpsInvTypeRec();

				olcVpsInvTypeRec.setInvoiceId(oracleOlcVpsInvTypeRec.getINVOICE_ID());
				olcVpsInvTypeRec.setInvoiceNumber(oracleOlcVpsInvTypeRec.getINVOICE_NUMBER());

				if (oracleOlcVpsInvTypeRec.getINVOICE_DATE() != null) {

					olcVpsInvTypeRec.setInvoiceDate(new Date(oracleOlcVpsInvTypeRec.getINVOICE_DATE().getTime()));
				}

				olcVpsInvTypeRec.setLicPlate(oracleOlcVpsInvTypeRec.getLIC_PLATE());
				olcVpsInvTypeRec.setLicState(oracleOlcVpsInvTypeRec.getLIC_STATE());

				if (oracleOlcVpsInvTypeRec.getDUE_DATE() != null) {

					olcVpsInvTypeRec.setDueDate(new Date(oracleOlcVpsInvTypeRec.getDUE_DATE().getTime()));
				}

				olcVpsInvTypeRec.setAgencyId(oracleOlcVpsInvTypeRec.getAGENCY_ID());
				olcVpsInvTypeRec.setAgencyName(oracleOlcVpsInvTypeRec.getAGENCY_NAME());
				olcVpsInvTypeRec.setInvEscStCd(oracleOlcVpsInvTypeRec.getINV_ESC_ST_CD());
				olcVpsInvTypeRec.setOpenTollAmount(oracleOlcVpsInvTypeRec.getOPEN_TOLL_AMOUNT());
				olcVpsInvTypeRec.setOrigTollAmount(oracleOlcVpsInvTypeRec.getORIG_TOLL_AMOUNT());
				olcVpsInvTypeRec.setOpenAdminFees(oracleOlcVpsInvTypeRec.getOPEN_ADMIN_FEES());
				olcVpsInvTypeRec.setOrigAdminFees(oracleOlcVpsInvTypeRec.getORIG_ADMIN_FEES());
				olcVpsInvTypeRec.setOpenCollectionFees(oracleOlcVpsInvTypeRec.getOPEN_COLLECTION_FEES());
				olcVpsInvTypeRec.setOrigCollectionFees(oracleOlcVpsInvTypeRec.getORIG_COLLECTION_FEES());
				olcVpsInvTypeRec.setOpenOtherFees(oracleOlcVpsInvTypeRec.getOPEN_OTHER_FEES());
				olcVpsInvTypeRec.setOrigOtherFees(oracleOlcVpsInvTypeRec.getORIG_OTHER_FEES());
				olcVpsInvTypeRec.setInvoiceAmt(oracleOlcVpsInvTypeRec.getINVOICE_AMT());
				olcVpsInvTypeRec.setInvPaidAmount(oracleOlcVpsInvTypeRec.getINV_PAID_AMOUNT());
				olcVpsInvTypeRec.setInvOpenAmount(oracleOlcVpsInvTypeRec.getINV_OPEN_AMOUNT());
				olcVpsInvTypeRec.setInvExcAmount(oracleOlcVpsInvTypeRec.getINV_EXC_AMOUNT());
				olcVpsInvTypeRec.setInvFeeOpenAmt(oracleOlcVpsInvTypeRec.getINV_FEE_OPEN_AMT());
				olcVpsInvTypeRec.setInvFeeExcAmt(oracleOlcVpsInvTypeRec.getINV_FEE_EXC_AMT());
				olcVpsInvTypeRec.setInvDueAfterExcAmt(oracleOlcVpsInvTypeRec.getINV_DUE_AFTER_EXC_AMT());

				final List<OlcVpsInvRec> olcVpsInvRecList = getInvoiceLineItemsByClassType(
						oracleOlcVpsInvTypeRec.getLINE_ITEMS());
				olcVpsInvTypeRec.getLineItems().addAll(olcVpsInvRecList);

				olcVpsInvTypeRec.setDiscountRule(oracleOlcVpsInvTypeRec.getDISCOUNT_RULE());
				olcVpsInvTypeRec.setAcctConvExcAmt(oracleOlcVpsInvTypeRec.getACCT_CONV_EXC_AMT());
				olcVpsInvTypeRec
						.setInvDueAfterAcctConvExAmt(oracleOlcVpsInvTypeRec.getINV_DUE_AFTER_ACCT_CONV_EX_AMT());
				olcVpsInvTypeRec.setInvoiceFineAmount(oracleOlcVpsInvTypeRec.getINVOICE_FINE_AMOUNT());

				final OLC_OPEN_FEE_ARR openFeeArray = oracleOlcVpsInvTypeRec.getOPEN_FEE_ARR();
				final List<OlcOpenFeeRec> invoiceOpenFeeRecords = getInvoiceOpenFeeeArrayByOracleClassType(
						openFeeArray);
				olcVpsInvTypeRec.getOpenFeeArr().addAll(invoiceOpenFeeRecords);

				olcVpsInvTypeRecList.add(olcVpsInvTypeRec);
			}

		}
		return olcVpsInvTypeRecList;
	}

	private List<OlcOpenFeeRec> getInvoiceOpenFeeeArrayByOracleClassType(final OLC_OPEN_FEE_ARR openFeeArray)
			throws SQLException {

		final List<OlcOpenFeeRec> invoiceOpenFeeRecords = new ArrayList<OlcOpenFeeRec>();

		if (openFeeArray != null) {
			final OLC_OPEN_FEE_REC[] openFeeObjectArray = openFeeArray.getArray();

			for (int k = 0; k < openFeeObjectArray.length; k++) {
				final OLC_OPEN_FEE_REC olcOpenFeeRec = (OLC_OPEN_FEE_REC) openFeeObjectArray[k];

				final OlcOpenFeeRec openFeeRec = new OlcOpenFeeRec();

				openFeeRec.setFeeCode(olcOpenFeeRec.getFEE_CODE());
				openFeeRec.setFeeDueOpenAmt(olcOpenFeeRec.getFEE_DUE_OPEN_AMT());
				openFeeRec.setFeeDiscAmt(olcOpenFeeRec.getFEE_DISC_AMT());
				openFeeRec.setFeeDueAfterDiscAmt(olcOpenFeeRec.getFEE_DUE_AFTER_DISC_AMT());
				openFeeRec.setRetailDetailId(olcOpenFeeRec.getRETAIL_DETAIL_ID());
				openFeeRec.setFeeGrpId(olcOpenFeeRec.getFEE_GRP_ID());

				invoiceOpenFeeRecords.add(openFeeRec);
			}

		}
		return invoiceOpenFeeRecords;
	}

	private List<OlcVpsUninvoicedViolsRec> getUninvTypeListByOracleClassType(
			final OLC_VPS_UNINVOICED_VIOLS_ARR uninvTypeArray) throws SQLException {

		final List<OlcVpsUninvoicedViolsRec> olcVpsUninvoicedViolsRecList = new ArrayList<OlcVpsUninvoicedViolsRec>();

		if (uninvTypeArray != null) {

			final OLC_VPS_UNINVOICED_VIOLS_REC[] uninvTypeObjectArray = uninvTypeArray.getArray();

			for (int j = 0; j < uninvTypeObjectArray.length; j++) {

				final OLC_VPS_UNINVOICED_VIOLS_REC oracleOlcVpsUninvoicedViolsRec = (OLC_VPS_UNINVOICED_VIOLS_REC) uninvTypeObjectArray[j];

				final OlcVpsUninvoicedViolsRec olcVpsUninvoicedViolsRec = new OlcVpsUninvoicedViolsRec();

				olcVpsUninvoicedViolsRec.setAgencyId(oracleOlcVpsUninvoicedViolsRec.getAGENCY_ID());
				olcVpsUninvoicedViolsRec.setAgencyName(oracleOlcVpsUninvoicedViolsRec.getAGENCY_NAME());
				olcVpsUninvoicedViolsRec.setViolationId(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_ID());
				olcVpsUninvoicedViolsRec.setViolatorId(oracleOlcVpsUninvoicedViolsRec.getVIOLATOR_ID());
				olcVpsUninvoicedViolsRec.setFullLocationName(oracleOlcVpsUninvoicedViolsRec.getFULL_LOCATION_NAME());
				
				if(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_DATE_TIME() !=null) {
					
					olcVpsUninvoicedViolsRec.setViolationDateTime(
							new Date(oracleOlcVpsUninvoicedViolsRec.getVIOLATION_DATE_TIME().getTime()));
				}
				
				olcVpsUninvoicedViolsRec.setStatus(oracleOlcVpsUninvoicedViolsRec.getSTATUS());
				olcVpsUninvoicedViolsRec.setLicPlate(oracleOlcVpsUninvoicedViolsRec.getLIC_PLATE());
				olcVpsUninvoicedViolsRec.setLicState(oracleOlcVpsUninvoicedViolsRec.getLIC_STATE());
				olcVpsUninvoicedViolsRec.setOrigTollAmt(oracleOlcVpsUninvoicedViolsRec.getORIG_TOLL_AMT());
				olcVpsUninvoicedViolsRec.setOpenTollAmt(oracleOlcVpsUninvoicedViolsRec.getOPEN_TOLL_AMT());
				olcVpsUninvoicedViolsRec.setPaidTollAmt(oracleOlcVpsUninvoicedViolsRec.getPAID_TOLL_AMT());

				olcVpsUninvoicedViolsRecList.add(olcVpsUninvoicedViolsRec);
			}

		}

		return olcVpsUninvoicedViolsRecList;
	}

	private List<OlcPmtPlanTypeRec> getPaymentPlanTypeListByOracleClassType(
			final OLC_PMT_PLAN_TYPE_ARR pmtPlanTypeArray) throws SQLException {

		final List<OlcPmtPlanTypeRec> paymetPlanTypeRecords = new ArrayList<OlcPmtPlanTypeRec>();

		if (pmtPlanTypeArray != null) {

			final OLC_PMT_PLAN_TYPE_REC[] pmtPlanTypeObjectArray = pmtPlanTypeArray.getArray();

			for (int j = 0; j < pmtPlanTypeObjectArray.length; j++) {

				final OLC_PMT_PLAN_TYPE_REC oracleOlcPmtPlanTypeRec = pmtPlanTypeObjectArray[j];

				final OlcPmtPlanTypeRec olcPmtPlanTypeRec = new OlcPmtPlanTypeRec();

				olcPmtPlanTypeRec.setPaymentPlanId(oracleOlcPmtPlanTypeRec.getPAYMENT_PLAN_ID());
				olcPmtPlanTypeRec.setAccountId(oracleOlcPmtPlanTypeRec.getACCOUNT_ID());
				olcPmtPlanTypeRec.setPlanStatusCode(oracleOlcPmtPlanTypeRec.getPLAN_STATUS_CODE());
				olcPmtPlanTypeRec.setInstallmentFreqTypeCode(oracleOlcPmtPlanTypeRec.getINSTALLMENT_FREQ_TYPE_CODE());
				olcPmtPlanTypeRec.setPaymentPlanNumber(oracleOlcPmtPlanTypeRec.getPAYMENT_PLAN_NUMBER());
				olcPmtPlanTypeRec.setPlanDiscountAmount(oracleOlcPmtPlanTypeRec.getPLAN_DISCOUNT_AMOUNT());
				olcPmtPlanTypeRec.setPlanSettlementAmount(oracleOlcPmtPlanTypeRec.getPLAN_SETTLEMENT_AMOUNT());
				olcPmtPlanTypeRec.setPlanTotalAmount(oracleOlcPmtPlanTypeRec.getPLAN_TOTAL_AMOUNT());
				olcPmtPlanTypeRec.setUncollectableAmount(oracleOlcPmtPlanTypeRec.getUNCOLLECTABLE_AMOUNT());
				olcPmtPlanTypeRec.setNumberOfInstallments(oracleOlcPmtPlanTypeRec.getNUMBER_OF_INSTALLMENTS());

				if (oracleOlcPmtPlanTypeRec.getFIRST_PAYMENT_DATE() != null) {

					olcPmtPlanTypeRec
							.setFirstPaymentDate(new Date(oracleOlcPmtPlanTypeRec.getFIRST_PAYMENT_DATE().getTime()));
				}

				olcPmtPlanTypeRec.setRebillFailedCount(oracleOlcPmtPlanTypeRec.getREBILL_FAILED_COUNT());
				olcPmtPlanTypeRec.setOpenAmount(oracleOlcPmtPlanTypeRec.getOPEN_AMOUNT());
				olcPmtPlanTypeRec.setPaidAmount(oracleOlcPmtPlanTypeRec.getPAID_AMOUNT());

				if (oracleOlcPmtPlanTypeRec.getSTATUS_DATE() != null) {

					olcPmtPlanTypeRec.setStatusDate(new Date(oracleOlcPmtPlanTypeRec.getSTATUS_DATE().getTime()));
				}

				olcPmtPlanTypeRec.setAgcyId(oracleOlcPmtPlanTypeRec.getAGCY_ID());
				olcPmtPlanTypeRec.setAccountBillingMethodId(oracleOlcPmtPlanTypeRec.getACCOUNT_BILLING_METHOD_ID());
				olcPmtPlanTypeRec.setBillingFullName(oracleOlcPmtPlanTypeRec.getBILLING_FULL_NAME());
				olcPmtPlanTypeRec.setBillingPaymentForm(oracleOlcPmtPlanTypeRec.getBILLING_PAYMENT_FORM());
				olcPmtPlanTypeRec.setBillingEftActTypeId(oracleOlcPmtPlanTypeRec.getBILLING_EFT_ACT_TYPE_ID());
				olcPmtPlanTypeRec.setBillingCardType(oracleOlcPmtPlanTypeRec.getBILLING_CARD_TYPE());

				final OLC_PAYMENT_INFO_ARR pmtPlanPmtTypeArray = oracleOlcPmtPlanTypeRec.getPMT_PLAN_PMT_TYPE_ARR();
				final List<OlcPaymentInfoRec> olcPaymentInfoRecList = getpmtPlanPmtTypeListByOracleClassType(
						pmtPlanPmtTypeArray);
				olcPmtPlanTypeRec.getPmtPlanPmtTypeArr().addAll(olcPaymentInfoRecList);

				final OLC_PMT_PLAN_INST_PMT_HST_ARR pmtPlanHistTypeArray = oracleOlcPmtPlanTypeRec
						.getPMT_PLAN_HIST_TYPE_ARR();
				final List<OlcPmtPlanHstTypeRec> olcPmtPlanHstTypeRecList = getpmtPlanHistTypeListByOracleClassType(
						pmtPlanHistTypeArray);
				olcPmtPlanTypeRec.getPmtPlanHistTypeArr().addAll(olcPmtPlanHstTypeRecList);

				final OLC_PMT_PLAN_INST_TYPE_ARR pmtPlanInstTypeArr = oracleOlcPmtPlanTypeRec
						.getPMT_PLAN_INST_TYPE_ARR();
				final List<OlcPmtPlanInstTypeRec> olcPmtPlanInstTypeRecList = getPmtPlanInstTypeListByOracleClassType(
						pmtPlanInstTypeArr);
				olcPmtPlanTypeRec.getPmtPlanInstTypeArr().addAll(olcPmtPlanInstTypeRecList);

				paymetPlanTypeRecords.add(olcPmtPlanTypeRec);

			}

		}
		return paymetPlanTypeRecords;
	}

	private List<OlcPaymentInfoRec> getpmtPlanPmtTypeListByOracleClassType(
			final OLC_PAYMENT_INFO_ARR pmtPlanPmtTypeArray) throws SQLException {

		final List<OlcPaymentInfoRec> olcPaymentInfoRecList = new ArrayList<OlcPaymentInfoRec>();

		if (pmtPlanPmtTypeArray != null) {
			final OLC_PAYMENT_INFO_REC[] pmtPlanPmtTypeObjectArray = pmtPlanPmtTypeArray.getArray();

			for (int k = 0; k < pmtPlanPmtTypeObjectArray.length; k++) {

				final OLC_PAYMENT_INFO_REC oracleOlcPaymentInfoRec = (OLC_PAYMENT_INFO_REC) pmtPlanPmtTypeObjectArray[k];

				final OlcPaymentInfoRec olcPaymentInfoRec = new OlcPaymentInfoRec();

				olcPaymentInfoRec.setPmtType(oracleOlcPaymentInfoRec.getPMT_TYPE());
				olcPaymentInfoRec.setPersonId(oracleOlcPaymentInfoRec.getPERSON_ID());
				olcPaymentInfoRec.setCardNbr(oracleOlcPaymentInfoRec.getCARD_NBR());

				if (oracleOlcPaymentInfoRec.getCARD_EXPIRES() != null) {

					olcPaymentInfoRec.setCardExpires(new Date(oracleOlcPaymentInfoRec.getCARD_EXPIRES().getTime()));
				}

				olcPaymentInfoRec.setCardType(oracleOlcPaymentInfoRec.getCARD_TYPE());
				olcPaymentInfoRec.setCardName(oracleOlcPaymentInfoRec.getCARD_NAME());
				olcPaymentInfoRec.setAccountBillingMethodId(oracleOlcPaymentInfoRec.getACCOUNT_BILLING_METHOD_ID());
				olcPaymentInfoRec.setIsDefaultBillingMethod(oracleOlcPaymentInfoRec.getIS_DEFAULT_BILLING_METHOD());
				olcPaymentInfoRec.setIsActive(oracleOlcPaymentInfoRec.getIS_ACTIVE());
				olcPaymentInfoRec.setToken(oracleOlcPaymentInfoRec.getTOKEN());
				olcPaymentInfoRec.setAddressId(oracleOlcPaymentInfoRec.getADDRESS_ID());
				olcPaymentInfoRec.setPhoneId(oracleOlcPaymentInfoRec.getPHONE_ID());
				olcPaymentInfoRec.setFirstName(oracleOlcPaymentInfoRec.getFIRST_NAME());
				olcPaymentInfoRec.setLastName(oracleOlcPaymentInfoRec.getLAST_NAME());
				olcPaymentInfoRec.setAddress1(oracleOlcPaymentInfoRec.getADDRESS_1());
				olcPaymentInfoRec.setAddress2(oracleOlcPaymentInfoRec.getADDRESS_2());
				olcPaymentInfoRec.setCity(oracleOlcPaymentInfoRec.getCITY());
				olcPaymentInfoRec.setState(oracleOlcPaymentInfoRec.getSTATE());
				olcPaymentInfoRec.setCountry(oracleOlcPaymentInfoRec.getCOUNTRY());
				olcPaymentInfoRec.setZipcode(oracleOlcPaymentInfoRec.getZIPCODE());
				olcPaymentInfoRec.setPlus4(oracleOlcPaymentInfoRec.getPLUS4());
				olcPaymentInfoRec.setPhoneNbr(oracleOlcPaymentInfoRec.getPHONE_NBR());
				olcPaymentInfoRec.setPhoneExtn(oracleOlcPaymentInfoRec.getPHONE_EXTN());
				olcPaymentInfoRec.setBankAcctType(oracleOlcPaymentInfoRec.getBANK_ACCT_TYPE());
				olcPaymentInfoRec.setBankAcctNumber(oracleOlcPaymentInfoRec.getBANK_ACCT_NUMBER());
				olcPaymentInfoRec.setRoutingNbr(oracleOlcPaymentInfoRec.getROUTING_NBR());
				olcPaymentInfoRec.setIsBlocked(oracleOlcPaymentInfoRec.getIS_BLOCKED());
				olcPaymentInfoRec.setBillingType(oracleOlcPaymentInfoRec.getBILLING_TYPE());
				olcPaymentInfoRec.setBillingPriority(oracleOlcPaymentInfoRec.getBILLING_PRIORITY());

				olcPaymentInfoRecList.add(olcPaymentInfoRec);

			}

		}
		return olcPaymentInfoRecList;
	}

	private List<OlcPmtPlanHstTypeRec> getpmtPlanHistTypeListByOracleClassType(
			final OLC_PMT_PLAN_INST_PMT_HST_ARR pmtPlanHistTypeArray) throws SQLException {

		final List<OlcPmtPlanHstTypeRec> olcPmtPlanHstTypeRecList = new ArrayList<OlcPmtPlanHstTypeRec>();

		if (pmtPlanHistTypeArray != null) {

			final OLC_PMT_PLAN_PMT_HST_TYPE_REC[] pmtPlanHistTypeObjectArray = pmtPlanHistTypeArray.getArray();

			for (int k = 0; k < pmtPlanHistTypeObjectArray.length; k++) {

				final OLC_PMT_PLAN_PMT_HST_TYPE_REC oracleOlcPmtPlanPmtHstTypeRec = (OLC_PMT_PLAN_PMT_HST_TYPE_REC) pmtPlanHistTypeObjectArray[k];

				final OlcPmtPlanHstTypeRec olcPmtPlanHstTypeRec = new OlcPmtPlanHstTypeRec();
				olcPmtPlanHstTypeRec.setPaymentId(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_ID());
				olcPmtPlanHstTypeRec.setPaymentForm(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_FORM());
				olcPmtPlanHstTypeRec.setPaymentType(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_TYPE());
				olcPmtPlanHstTypeRec.setLast4(oracleOlcPmtPlanPmtHstTypeRec.getLAST4());

				if (oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_DATE() != null) {

					olcPmtPlanHstTypeRec
							.setPaymentDate(new Date(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_DATE().getTime()));
				}

				if (oracleOlcPmtPlanPmtHstTypeRec.getDUE_DATE() != null) {

					olcPmtPlanHstTypeRec.setDueDate(new Date(oracleOlcPmtPlanPmtHstTypeRec.getDUE_DATE().getTime()));
				}

				olcPmtPlanHstTypeRec.setInstallmentNumber(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_NUMBER());
				olcPmtPlanHstTypeRec.setInstallmentStatus(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_STATUS());
				olcPmtPlanHstTypeRec.setPaymentAmount(oracleOlcPmtPlanPmtHstTypeRec.getPAYMENT_AMOUNT());
				olcPmtPlanHstTypeRec.setInstallmentAmount(oracleOlcPmtPlanPmtHstTypeRec.getINSTALLMENT_AMOUNT());
				olcPmtPlanHstTypeRec.setPaidAmount(oracleOlcPmtPlanPmtHstTypeRec.getPAID_AMOUNT());
				olcPmtPlanHstTypeRec.setOpenAmount(oracleOlcPmtPlanPmtHstTypeRec.getOPEN_AMOUNT());

				olcPmtPlanHstTypeRecList.add(olcPmtPlanHstTypeRec);

			}

		}
		return olcPmtPlanHstTypeRecList;
	}

	private List<OlcPmtPlanInstTypeRec> getPmtPlanInstTypeListByOracleClassType(
			final OLC_PMT_PLAN_INST_TYPE_ARR pmtPlanInstTypeArr) throws SQLException {

		final List<OlcPmtPlanInstTypeRec> olcPmtPlanInstTypeRecList = new ArrayList<OlcPmtPlanInstTypeRec>();

		if (pmtPlanInstTypeArr != null) {

			final OLC_PMT_PLAN_INST_TYPE_REC[] pmtPlanInstTypeObjectArray = pmtPlanInstTypeArr.getArray();

			for (int k = 0; k < pmtPlanInstTypeObjectArray.length; k++) {

				final OLC_PMT_PLAN_INST_TYPE_REC oracleOlcPmtPlanInstTypeRec = (OLC_PMT_PLAN_INST_TYPE_REC) pmtPlanInstTypeObjectArray[k];

				final OlcPmtPlanInstTypeRec olcPmtPlanInstTypeRec = new OlcPmtPlanInstTypeRec();

				olcPmtPlanInstTypeRec
						.setPaymentPlanInstallmentId(oracleOlcPmtPlanInstTypeRec.getPAYMENT_PLAN_INSTALLMENT_ID());
				olcPmtPlanInstTypeRec
						.setPlanInstallmentStatusCode(oracleOlcPmtPlanInstTypeRec.getPLAN_INSTALLMENT_STATUS_CODE());
				olcPmtPlanInstTypeRec.setPaymentPlanId(oracleOlcPmtPlanInstTypeRec.getPAYMENT_PLAN_ID());
				olcPmtPlanInstTypeRec.setItemsPaymentId(oracleOlcPmtPlanInstTypeRec.getITEMS_PAYMENT_ID());
				olcPmtPlanInstTypeRec.setIsFuture(oracleOlcPmtPlanInstTypeRec.getIS_FUTURE());
				olcPmtPlanInstTypeRec.setInstallmentNumber(oracleOlcPmtPlanInstTypeRec.getINSTALLMENT_NUMBER());

				if (oracleOlcPmtPlanInstTypeRec.getDUE_DATE() != null) {

					olcPmtPlanInstTypeRec.setDueDate(new Date(oracleOlcPmtPlanInstTypeRec.getDUE_DATE().getTime()));
				}

				olcPmtPlanInstTypeRec.setInstallmentAmount(oracleOlcPmtPlanInstTypeRec.getINSTALLMENT_AMOUNT());
				olcPmtPlanInstTypeRec.setOpenAmount(oracleOlcPmtPlanInstTypeRec.getOPEN_AMOUNT());
				olcPmtPlanInstTypeRec.setPaidAmount(oracleOlcPmtPlanInstTypeRec.getPAID_AMOUNT());
				olcPmtPlanInstTypeRec.setRecordVersion(oracleOlcPmtPlanInstTypeRec.getRECORD_VERSION());

				if (oracleOlcPmtPlanInstTypeRec.getSTATUS_DATE() != null) {

					olcPmtPlanInstTypeRec
							.setStatusDate(new Date(oracleOlcPmtPlanInstTypeRec.getSTATUS_DATE().getTime()));
				}

				olcPmtPlanInstTypeRec.setTollAmount(oracleOlcPmtPlanInstTypeRec.getTOLL_AMOUNT());
				olcPmtPlanInstTypeRec.setFeeAmount(oracleOlcPmtPlanInstTypeRec.getFEE_AMOUNT());
				olcPmtPlanInstTypeRec.setCreatedBy(oracleOlcPmtPlanInstTypeRec.getCREATED_BY());

				if (oracleOlcPmtPlanInstTypeRec.getDATE_CREATED() != null) {

					olcPmtPlanInstTypeRec
							.setDateCreated(new Date(oracleOlcPmtPlanInstTypeRec.getDATE_CREATED().getTime()));
				}

				olcPmtPlanInstTypeRec.setModifiedBy(oracleOlcPmtPlanInstTypeRec.getMODIFIED_BY());

				if (oracleOlcPmtPlanInstTypeRec.getDATE_MODIFIED() != null) {

					olcPmtPlanInstTypeRec
							.setDateModified(new Date(oracleOlcPmtPlanInstTypeRec.getDATE_MODIFIED().getTime()));
				}

				olcPmtPlanInstTypeRec
						.setPlanInstallmentTypeId(oracleOlcPmtPlanInstTypeRec.getPLAN_INSTALLMENT_TYPE_ID());
				olcPmtPlanInstTypeRec.setLastCartId(oracleOlcPmtPlanInstTypeRec.getLAST_CART_ID());
				olcPmtPlanInstTypeRec.setUncollectableAmount(oracleOlcPmtPlanInstTypeRec.getUNCOLLECTABLE_AMOUNT());
				olcPmtPlanInstTypeRec.setCancelledAmount(oracleOlcPmtPlanInstTypeRec.getCANCELLED_AMOUNT());

				olcPmtPlanInstTypeRecList.add(olcPmtPlanInstTypeRec);

			}

		}
		return olcPmtPlanInstTypeRecList;
	}

	private List<OlcVpsPlateAgcyRec> getPlateAgencyRecordsByOracleClassType(
			final OLC_VPS_PLATE_AGCY_ARR vpsPlateTypeAgcyRecArray) throws SQLException {

		final List<OlcVpsPlateAgcyRec> olcVpsPlateAgcyRecList = new ArrayList<OlcVpsPlateAgcyRec>();

		if (vpsPlateTypeAgcyRecArray != null) {
			final OLC_VPS_PLATE_AGCY_REC[] vpsPlateTypeAgcyRecObjectArray = vpsPlateTypeAgcyRecArray.getArray();

			for (int j = 0; j < vpsPlateTypeAgcyRecObjectArray.length; j++) {

				final OLC_VPS_PLATE_AGCY_REC oracleOlcVpsPlateAgcyRec = (OLC_VPS_PLATE_AGCY_REC) vpsPlateTypeAgcyRecObjectArray[j];

				final OlcVpsPlateAgcyRec olcVpsPlateAgcyRec = new OlcVpsPlateAgcyRec();

				olcVpsPlateAgcyRec.setAgencyId(oracleOlcVpsPlateAgcyRec.getAGENCY_ID());
				olcVpsPlateAgcyRec.setDiscountRule(oracleOlcVpsPlateAgcyRec.getDISCOUNT_RULE());
				olcVpsPlateAgcyRec.setTtlAmountDue(oracleOlcVpsPlateAgcyRec.getTTL_AMOUNT_DUE());
				olcVpsPlateAgcyRec.setTotalExcAmount(oracleOlcVpsPlateAgcyRec.getTOTAL_EXC_AMOUNT());
				olcVpsPlateAgcyRec.setTtlAmountDueAfterDisc(oracleOlcVpsPlateAgcyRec.getTTL_AMOUNT_DUE_AFTER_DISC());
				olcVpsPlateAgcyRec.setStatusReason(oracleOlcVpsPlateAgcyRec.getSTATUS_REASON());
				olcVpsPlateAgcyRec.setServiceFee(oracleOlcVpsPlateAgcyRec.getSERVICE_FEE());

				olcVpsPlateAgcyRecList.add(olcVpsPlateAgcyRec);

			}

		}
		return olcVpsPlateAgcyRecList;
	}
	
}
