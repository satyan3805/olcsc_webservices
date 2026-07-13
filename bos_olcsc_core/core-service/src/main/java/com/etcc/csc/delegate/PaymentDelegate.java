package com.etcc.csc.delegate;

import java.math.BigDecimal;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.datatype.OlcUninvoicedViolsRecBean;
import com.etcc.csc.datatype.OlcVpsInvoicesRecBean;
import com.etcc.csc.datatype.PaymentDetail;
import com.etcc.csc.datatype.PaymentResponse;
import com.etcc.csc.datatype.Violation;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.plsql.OlcPaymentInfoRec;
import com.etcc.csc.service.PaymentInterface;


public class PaymentDelegate extends Delegate implements PaymentInterface{
    
    PaymentInterface payment = (PaymentInterface)getServiceObject(ServiceObjectEnum.PAYMENT);

    public PaymentDelegate() {
        super(PaymentDelegate.class);
    }

    public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress, 
                                          String loginId, long docId, 
                                          String docType, String licPlate, 
                                          String licState, 
                                          BigDecimal transactionId) throws EtccErrorMessageException, 
                                                                           Exception {
        PaymentDetail pd = payment.getPaymentDetail(dbSessionId, ipAddress, loginId, 
                                        docId, docType, licPlate, licState, 
                                        transactionId);
        return pd;
    }// end of getPaymentDetail()

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
                                                                       Exception {
        
        return payment.combinedPayment(docId, docType, dbSessionId, ipAddress, 
                                       loginId, transactionId, tagAmount, 
                                       totalAmount, dlState, dlNumber, email, 
                                       updatePmtInfo, cardCode, paymentType, 
                                       cardNumber, expireMonth, expireYear, 
                                       nameOnCard, address, address2, city, 
                                       state, zipCode, plus4, cardSecurityCode, 
                                       invoices, violations, veaAccepted);
    }// end of combinedPayment()


    public boolean veaAutoComments(long docId, String docType, 
                                   String sessionId, String ipAddress, 
                                   String userId, String veaName, 
                                   OlcVpsInvoicesRecBean[] invoices) throws EtccException {
        try {
            
            return payment.veaAutoComments(docId, docType, sessionId, 
                                              ipAddress, userId, veaName, 
                                              invoices);
        } catch (Exception ex) {
			logger.error("Error in veaAutoComments() @ PaymentDelegate ", ex);        	
            throw new EtccException(ex);
        }// end of try-catch()
    }// end of veaAutoComments()

    public boolean autoComments(long docId, String docType, String sessionId, 
                                String ipAddress, String userId, 
                                long paymentId, String payerName, 
                                double payAmt, String failedFlag, 
                                OlcVpsInvoicesRecBean[] invoices, 
                                OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
        try {
            return payment.autoComments(docId, docType, sessionId, 
                                           ipAddress, userId, paymentId, 
                                           payerName, payAmt, failedFlag, 
                                           invoices, uninvoiced);
        } catch (Exception ex) {
			logger.error("Error in autoComments() @ PaymentDelegate ", ex);        	
            throw new EtccException(ex);
        }// end of try-catch()
    }// end of autoComments()


    public boolean veaExists(BigDecimal docId, String docType, 
                             String dbSessionId, String ipAddress, 
                             String loginId, String licPlate, 
                             String licState) throws Exception {

        return payment.veaExists(docId, docType, dbSessionId, ipAddress, 
                                 loginId, licPlate, licState);
    }// end of veaExists()


    public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, 
                                                   String docType, 
                                                   String dbSessionId, 
                                                   String ipAddress, 
                                                   String loginId,
                                                   long eventId) throws EtccErrorMessageException, 
                                                                          Exception {
        return payment.getPaymentInfo(docId, docType, dbSessionId, ipAddress, 
                                      loginId,eventId);
    }// end of getPaymentInfo()

    public boolean postViolations(BigDecimal docId, String docType, 
                                  String dbSessionId, String ipAddress, 
                                  String loginId, 
                                  Violation[] violations) throws EtccErrorMessageException, 
                                                                 Exception {
        return payment.postViolations(docId, docType, dbSessionId, ipAddress, loginId, 
                                       violations);
    }// end of postViolations()

    public String getPaymentReceipt(BigDecimal docId, String docType, 
                                    String dbSessionId, String ipAddress, 
                                    String loginId, BigDecimal paymentId, 
                                    String reportFormat) throws EtccErrorMessageException, 
                                                                Exception {
        
        return payment.getPaymentReceipt(docId, docType, dbSessionId, ipAddress, 
                                          loginId, paymentId, reportFormat);
    }// end of getPaymentReceipt()

    public BigDecimal getPaymentLimit() throws Exception {
        return payment.getPaymentLimit();
    }// end of getPaymentLimit()

    public boolean updateSessionId(BigDecimal docId, String docType,
            String dbSessionId, String ipAddress, String loginId, 
            String paymentId) throws EtccErrorMessageException, Exception {
        
        return payment.updateSessionId(docId,
                 docType, dbSessionId, ipAddress, loginId, paymentId);
    }// end of updateSessionId()
    
    public byte makePayment(AccountLoginDTO acctLoginDto, String postingXML,
            long eventId) throws EtccErrorMessageException, Exception {
        return payment.makePayment(acctLoginDto, postingXML, eventId);
     }// end of makePayment()
    
}// end of PaymentDelegate Class
