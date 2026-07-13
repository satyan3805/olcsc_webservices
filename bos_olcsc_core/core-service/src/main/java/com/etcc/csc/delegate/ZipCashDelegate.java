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
import com.etcc.csc.plsql.OlcPaymentInfoRec;
import com.etcc.csc.service.ZipCashInterface;


public class ZipCashDelegate extends Delegate implements ZipCashInterface{
    
    ZipCashInterface zipCash = (ZipCashInterface)getServiceObject(ServiceObjectEnum.ZIPCASH);

    public ZipCashDelegate() {
        super(ZipCashDelegate.class);
    
    
    }
    
    public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress, 
                                          String loginId, long docId, 
                                          String docType, String licPlate, 
                                          String licState, 
                                          BigDecimal transactionId) throws EtccErrorMessageException, 
                                                                           Exception {
        return zipCash.getPaymentDetail(dbSessionId, ipAddress, loginId, 
                                        docId, docType, licPlate, licState, 
                                        transactionId);
    
    }

    public PaymentResponse zipCashCombinedPayment(long acctId, long docId, String docType, 
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
        
        return zipCash.zipCashCombinedPayment(acctId, docId, docType, 
                                              dbSessionId, ipAddress, loginId, 
                                              transactionId, tagAmount, 
                                              totalAmount, dlState, dlNumber, 
                                              email, updatePmtInfo, cardCode, 
                                              paymentType, cardNumber, expireMonth, 
                                              expireYear, nameOnCard, address, 
                                              address2, city, state, zipCode, 
                                              plus4, cardSecurityCode, invoices, 
                                              violations, veaAccepted);
    }


   

    public boolean autoComments(long docId, String docType, String sessionId, 
                                String ipAddress, String userId, 
                                long paymentId, String payerName, 
                                double payAmt, String failedFlag, 
                                OlcVpsInvoicesRecBean[] invoices, 
                                OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
        try {
            return zipCash.autoComments(docId, docType, sessionId, 
                                           ipAddress, userId, paymentId, 
                                           payerName, payAmt, failedFlag, 
                                           invoices, uninvoiced);
        } catch (Exception e) {
            logger.error("Error in autoComments " + e, e);
            throw new EtccException(e);
        }
    }

    
    public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, 
                                                   String docType, 
                                                   String dbSessionId, 
                                                   String ipAddress, 
                                                   String loginId,
                                                   long eventId) throws EtccErrorMessageException, 
                                                                          Exception {
        
        return zipCash.getPaymentInfo(docId, docType, dbSessionId, ipAddress, loginId,eventId);
    }
   
    public String getPaymentReceipt(BigDecimal docId, String docType, 
                                    String dbSessionId, String ipAddress, 
                                    String loginId, BigDecimal paymentId, 
                                    String reportFormat) throws EtccErrorMessageException, 
                                                                Exception {
        
        return zipCash.getPaymentReceipt(docId, docType, dbSessionId, ipAddress, 
                                         loginId, paymentId, reportFormat);

    }

    public BigDecimal getPaymentLimit() throws Exception {
        return zipCash.getPaymentLimit();
    }

    public boolean updateSessionId(BigDecimal docId, String docType,
            String dbSessionId, String ipAddress, String loginId, 
            String paymentId) throws EtccErrorMessageException, Exception {
    //        return DAOFactory.getDAOFactory().getPaymentDAO().updateSessionId(docId,
    //            docType, dbSessionId, ipAddress, loginId, paymentId);
        return false;
    }
    
}
