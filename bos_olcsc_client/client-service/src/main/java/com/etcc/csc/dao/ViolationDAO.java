/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import oracle.xdb.XMLType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.oracle.OracleUtil;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountLoginDTO.LoginType;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.payment.checkoutxml.CartItemXmlDetails;
import com.etcc.csc.payment.checkoutxml.CartXML;
import com.etcc.csc.plsql.OLCSC_PAYMENT;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.service.ViolationInterface;
import com.etcc.csc.util.CartItemTypeEnum;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;

/**
 * Data Access Object for implementing violation processing functions and procedures.
 * Database-specific methods should be implemented in child classes.
 * @author Milosh Boroyevich
 */
public abstract class ViolationDAO extends BaseDAO implements ViolationInterface {
    private static final Logger logger = Logger.getLogger(ViolationDAO.class);
	private static final int MAX_STRING_INVOICE_COUNT = 2;

    protected abstract ViolatorDTO loadInvoices(ViolatorDTO violatorDTO, String ipAddress,
    		String licPlate, String licState, AgencyEnum agency) throws EtccSecurityException, SQLException;

	protected abstract AccountLoginDTO loginVPS(String sessionId, String ipAddress,
			String invoiceId, String licPlate, String licState, ViolatorDTO violatorDTO, String sourceUserName, String dbSessionId) throws SQLException;
	
	protected abstract AccountLoginDTO loginVPS1(String sessionId, String ipAddress,
			 String licPlate, String licState, String paymentPlanId,ViolatorDTO violatorDTO, String sourceUserName, String dbSessionId) throws SQLException;

	/**
	 * @see #loginVPS(String, String, String, String, String, ViolatorDTO)
	 * @see #loadInvoices(ViolatorDTO, String, String, String, AgencyEnum)
	 */
	public ViolatorDTO loginViolator(AccountLoginDTO accountLoginDTO, String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String licPlate, String licState,  String dbSessionId)
			throws EtccException, EtccSecurityException {
		try {
			logger.trace("ViolationDAO - loginViolator: entering.... " );
			ViolatorDTO violatorDTO = new ViolatorDTO();
			final boolean debugEnabled = logger.isDebugEnabled();
			if (debugEnabled)
				logger.debug("loginViolator.sessionId=" + sessionId + ";ipAddress=" + ipAddress + ";invoiceId=" + invoiceId + ";licPlate=" + licPlate + ";licState=" + licState + ";acctLoginDto=" + accountLoginDTO);

			//if (accountLoginDTO == null) {
			accountLoginDTO = this.loginVPS(sessionId, ipAddress, invoiceId, licPlate, licState, violatorDTO,userEnvDto.getSourceUserName(),dbSessionId);
			/*} else {
				accountLoginDTO.setInvoiceId(invoiceId);
				accountLoginDTO.setLoginType(LoginType.IN);
			}*/
			if(accountLoginDTO != null){ 
			   violatorDTO.setAccountLoginDTO(accountLoginDTO);
			}
			invoiceId = accountLoginDTO.getInvoiceId();
			if(invoiceId != null && StringUtils.isNotEmpty(invoiceId)){
			     violatorDTO = loadInvoices(violatorDTO, ipAddress, licPlate, licState, null);
			} 
			if (debugEnabled){
				logger.debug("loginViolator.violatorDTO = " + violatorDTO);
			}
			return violatorDTO;
		} catch (SQLException sqle) {
			String message = "Exception in ViolationDAO.loginViolator: " + sqle.getMessage();
			throw new EtccException(message, sqle);
		}
	}
	
	
	
	public ViolatorDTO loginViolatorPmtPlan(AccountLoginDTO accountLoginDTO, String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String licPlate, String licState,  String paymentPlanId, String dbSessionId)
			throws EtccException, EtccSecurityException {
		try {
			logger.trace("ViolationDAO - loginViolatorPmtPlan: entering.... " );
			ViolatorDTO violatorDTO = new ViolatorDTO();
			final boolean debugEnabled = logger.isDebugEnabled();
			if (debugEnabled)
				logger.debug("loginViolatorPmtPlan.sessionId=" + sessionId + ";ipAddress=" + ipAddress + ";paymentPlanId=" + paymentPlanId + ";licPlate=" + licPlate + ";licState=" + licState + ";acctLoginDto=" + accountLoginDTO);

			//if (accountLoginDTO == null) {
			accountLoginDTO = this.loginVPS1(sessionId, ipAddress, licPlate, licState, paymentPlanId, violatorDTO,userEnvDto.getSourceUserName(),dbSessionId);
			/*} else {
				accountLoginDTO.setInvoiceId(invoiceId);
				accountLoginDTO.setLoginType(LoginType.IN);
			}*/
			if(accountLoginDTO != null){ 
			   violatorDTO.setAccountLoginDTO(accountLoginDTO);
			}
			String invoiceId  = accountLoginDTO.getInvoiceId();
			if(invoiceId != null && StringUtils.isNotEmpty(invoiceId)){
			     violatorDTO = loadInvoices(violatorDTO, ipAddress, licPlate, licState, null);
			} 
			if (debugEnabled){
				logger.debug("loginViolatorPmtPlan.violatorDTO = " + violatorDTO);
			}
			return violatorDTO;
		} catch (SQLException sqle) {
			String message = "Exception in ViolationDAO.loginViolator: " + sqle.getMessage();
			throw new EtccException(message, sqle);
		}
	}

	/**
	 * @see #loadInvoices(ViolatorDTO, String, String, String, AgencyEnum)
	 */
	public ViolatorDTO getInvoices(AccountLoginDTO accountLoginDTO, String ipAddress, String licPlate,
			String licState, AgencyEnum agency) throws EtccException, EtccSecurityException {
		try {
			ViolatorDTO violatorDTO = new ViolatorDTO();
			violatorDTO.setAccountLoginDTO(accountLoginDTO);
			return loadInvoices(violatorDTO, ipAddress, licPlate, licState, agency);
		} catch (SQLException e) {
			String message = "Exception in ViolationDAO.getInvoices: " + e.getMessage();
			throw new EtccException(message, e);
        }
	}

	protected StringBuilder toStringBuilder(Collection<InvoiceDTO> invoices, ViolatorDTO violatorDTO) {
	    StringBuilder sb = toStringBuilder(invoices);
	    sb.append(", ");
	    sb.append(violatorDTO.toString());
	    return sb;
	}

	/**
	 * This service creates a string representation of the first few invoices.
	 * Sometimes the number of invoices is so high that it causes out of memory
	 * exception on oracle applications server. Therefore only the first few
	 * invoices are handled here.
	 */
	protected StringBuilder toStringBuilder(Collection<InvoiceDTO> invoices) {
	    StringBuilder sb = new StringBuilder("Collection<InvoiceDTO>=");
	    sb.append(StringUtil.toStringBuilder(invoices, MAX_STRING_INVOICE_COUNT));
	    return sb;
	}

	protected String display(Calendar calendar) {
		if (calendar == null)
			return NULL_STRING;
//        String aPattern = "yyyy-MM-dd HH:mm:ss"; // 2001-07-04 12:08:56
		return WSDateUtil.getISO8061DateTime(calendar.getTime());
	}

	
}
