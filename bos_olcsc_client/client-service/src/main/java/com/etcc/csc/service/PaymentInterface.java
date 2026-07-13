/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.PaymentDetail;
import com.etcc.csc.dto.PaymentResponse;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;
import com.etcc.csc.dto.bean.OlcUninvoicedViolsRecBean;
import com.etcc.csc.dto.bean.OlcVpsInvoicesRecBean;

import java.math.BigDecimal;

import javax.ejb.Local;

/**
 * Payment interface based on PaymentInterface from OLCSCService.
 */
@Local
public interface PaymentInterface extends ServiceInterface {

    @Deprecated
    public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
                                          String loginId, long docId,
                                          String docType, String licPlate,
                                          String licState,
                                          BigDecimal transactionId) throws EtccException, EtccErrorMessageException ;

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
                                           EtccException ;

    public boolean veaAutoComments(long docId, String docType,
                                   String sessionId, String ipAddress,
                                   String userId, String veaName,
                                   OlcVpsInvoicesRecBean[] invoices) throws EtccException ;

    public boolean autoComments(long docId, String docType, String sessionId,
                                String ipAddress, String userId,
                                long paymentId, String payerName,
                                double payAmt, String failedFlag,
                                OlcVpsInvoicesRecBean[] invoices,
                                OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException ;

    public boolean veaExists(BigDecimal docId, String docType,
                             String dbSessionId, String ipAddress,
                             String loginId, String licPlate,
                             String licState) throws EtccException ;

    @Deprecated
    public OlcPaymentInfoRecBean getPaymentInfo(BigDecimal docId,
                                                   String docType,
                                                   String dbSessionId,
                                                   String ipAddress,
                                                   String loginId) throws EtccErrorMessageException,
                                                   EtccException ;

    @Deprecated
    public boolean postViolations(BigDecimal docId, String docType,
                                  String dbSessionId, String ipAddress,
                                  String loginId,
                                  Violation[] violations) throws EtccErrorMessageException,
                                  EtccException ;

    public String getPaymentReceipt(BigDecimal docId, String docType,
                                    String dbSessionId, String ipAddress,
                                    String loginId, BigDecimal paymentId,
                                    String reportFormat) throws EtccErrorMessageException,
                                    EtccException ;

    public BigDecimal getPaymentLimit() throws EtccException ;
}
