/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.PaymentDAO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;
import com.etcc.csc.dto.bean.OlcUninvoicedViolsRecBean;
import com.etcc.csc.dto.bean.OlcVpsInvoicesRecBean;
import com.etcc.csc.dto.PaymentDetail;
import com.etcc.csc.dto.PaymentResponse;
import com.etcc.csc.dto.Violation;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @deprecated no longer in use
 */
@Deprecated
@Stateless(name="com/etcc/Payment")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Payment", targetNamespace = "http://ws.csc.etcc.com/Payment")
public class Payment implements PaymentInterface {

    @SuppressWarnings("deprecation")
	@Deprecated
    public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
                                          String loginId, long docId,
                                          String docType, String licPlate,
                                          String licState,
                                          BigDecimal transactionId) throws EtccException, EtccErrorMessageException {
                                          
        return paymentDAO().getPaymentDetail(dbSessionId, ipAddress, loginId, docId,
                                          docType, licPlate, licState, transactionId);
    }

    @SuppressWarnings("deprecation")
	@Deprecated
    public PaymentResponse combinedPayment(long docId, String docType,
                                           String dbSessionId,
                                           String ipAddress, String loginId,
                                           BigDecimal transactionId,
                                           BigDecimal tagAmount,
                                           BigDecimal totalAmount,
                                           String dlState, String dlNumber,
                                           String email, boolean updatePmtInfo,
                                           String cardCode, String paymentType,
                                           String cardNumber,
                                           String expireMonth,
                                           String expireYear,
                                           String nameOnCard, String address,
                                           String address2, String city,
                                           String state, String zipCode,
                                           String plus4,
                                           String cardSecurityCode,
                                           Invoice[] invoices,
                                           Violation[] violations,
                                           boolean veaAccepted) throws EtccErrorMessageException,
                                           EtccException {
    	return paymentDAO().combinedPayment(docId, docType, dbSessionId, ipAddress, loginId,
                                           transactionId, tagAmount, totalAmount,
                                           dlState, dlNumber, email, updatePmtInfo,
                                           cardCode, paymentType, cardNumber,
                                           expireMonth, expireYear, nameOnCard, address,
                                           address2, city, state, zipCode,
                                           plus4, cardSecurityCode, invoices,
                                           violations, veaAccepted);
    }

    //@WebMethod(operationName = "veaAutoComments", action = "urn:veaAutoComments")
    //@WebResult(name = "boolean")
    public boolean veaAutoComments(long docId, String docType,
                                   String sessionId, String ipAddress,
                                   String userId, String veaName,
                                   OlcVpsInvoicesRecBean[] invoices) throws EtccException {
        return paymentDAO().veaAutoComments(docId, docType, sessionId, ipAddress,
                                   userId, veaName, invoices);
    }

    //@WebMethod(operationName = "autoComments", action = "urn:autoComments")
    //@WebResult(name = "boolean")
    public boolean autoComments(long docId, String docType, String sessionId,
                                String ipAddress, String userId,
                                long paymentId, String payerName,
                                double payAmt, String failedFlag,
                                OlcVpsInvoicesRecBean[] invoices,
                                OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
        return paymentDAO().autoComments(docId, docType, sessionId, ipAddress, userId,
                                paymentId, payerName, payAmt, failedFlag,
                                invoices, uninvoiced);
    }

    //@WebMethod(operationName = "veaExists", action = "urn:veaExists")
    //@WebResult(name = "boolean")
    public boolean veaExists(BigDecimal docId, String docType,
                             String dbSessionId, String ipAddress,
                             String loginId, String licPlate,
                             String licState) throws EtccException {
        return paymentDAO().veaExists(docId, docType, dbSessionId, ipAddress,
                             loginId, licPlate, licState);
    }

    @SuppressWarnings("deprecation")
	@Deprecated
    public OlcPaymentInfoRecBean getPaymentInfo(BigDecimal docId,
                                                   String docType,
                                                   String dbSessionId,
                                                   String ipAddress,
                                                   String loginId) throws EtccErrorMessageException,
                                                   EtccException {
        return paymentDAO().getPaymentInfo(docId, docType, dbSessionId, ipAddress, loginId);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public boolean postViolations(BigDecimal docId, String docType,
                                  String dbSessionId, String ipAddress,
                                  String loginId,
                                  Violation[] violations) throws EtccErrorMessageException,
                                  EtccException {
        return paymentDAO().postViolations(docId, docType, dbSessionId, ipAddress,
                                  loginId, violations);
    }

    //@WebMethod(operationName = "getPaymentReceipt", action = "urn:getPaymentReceipt")
    //@WebResult(name = "string")
    public String getPaymentReceipt(BigDecimal docId, String docType,
                                    String dbSessionId, String ipAddress,
                                    String loginId, BigDecimal paymentId,
                                    String reportFormat) throws EtccErrorMessageException,
                                    EtccException {
        return paymentDAO().getPaymentReceipt(docId, docType, dbSessionId, ipAddress,
                                    loginId, paymentId, reportFormat);
    }

    //@WebMethod(operationName = "getPaymentLimit", action = "urn:getPaymentLimit")
    //@WebResult(name = "decimal")
    public BigDecimal getPaymentLimit() throws EtccException {
        return paymentDAO().getPaymentLimit();
    }

    private PaymentDAO paymentDAO() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PaymentDAO dao = daoFactory.getDAO(PaymentDAO.class);
        return dao;
    }
}
