/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.math.BigDecimal;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.PaymentDetail;
import com.etcc.csc.dto.PaymentResponse;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;
import com.etcc.csc.dto.bean.OlcUninvoicedViolsRecBean;
import com.etcc.csc.dto.bean.OlcVpsInvoicesRecBean;
import com.etcc.csc.service.PaymentInterface;

/*import com.etcc.csc.oracle.wsclient.PaymentWsSoapHttpPortClient;
 import com.etcc.csc.util.DtoUtil;
 import com.etcc.csc.util.WsClient;*/

/**
 * @deprecated use {@link AccountDelegate#makePayment(com.etcc.csc.dto.AccountLoginDTO, com.etcc.csc.dto.BillingInfoDTO, double)}
 */
@Deprecated
public class PaymentDelegate implements PaymentInterface {

	@SuppressWarnings("deprecation")
	@Deprecated
    public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
                                          String loginId, long docId,
                                          String docType, String licPlate,
                                          String licState,
                                          BigDecimal transactionId) throws EtccErrorMessageException {
        // Not used
        throw new EtccErrorMessageException("Web Service Not Available");
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
                                           boolean veaAccepted) 
	throws EtccErrorMessageException, EtccException {
        // Not used
        throw new EtccException("Web Service Not Available");
    }

	@Deprecated
    public boolean veaAutoComments(long docId, String docType,
                                   String sessionId, String ipAddress,
                                   String userId, String veaName,
                                   OlcVpsInvoicesRecBean[] invoices) throws EtccException {
        // Not used
        throw new EtccException("Web Service Not Available");
    }

	@Deprecated
    public boolean autoComments(long docId, String docType, String sessionId,
                                String ipAddress, String userId,
                                long paymentId, String payerName,
                                double payAmt, String failedFlag,
                                OlcVpsInvoicesRecBean[] invoices,
                                OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
        // Not used
        throw new EtccException("Web Service Not Available");
    }

	@Deprecated
    public boolean veaExists(BigDecimal docId, String docType,
                             String dbSessionId, String ipAddress,
                             String loginId, String licPlate,
                             String licState) throws EtccException {
        // Not used
        throw new EtccException("Web Service Not Available");
    }


	@SuppressWarnings("deprecation")
	@Deprecated
    public OlcPaymentInfoRecBean getPaymentInfo(BigDecimal docId,
                                                   String docType,
                                                   String dbSessionId,
                                                   String ipAddress,
                                                   String loginId) throws EtccErrorMessageException {
        throw new EtccErrorMessageException("Payment.getPaymentInfo Web Service not Available");
        /*com.etcc.csc.payment.types.OlcPaymentInfoRecBean aPaymentInfo = null;
        aPaymentInfo = stub().getPaymentInfo(docId, docType, dbSessionId, ipAddress, loginId);
        return convert(aPaymentInfo);*/
    }
    
	@SuppressWarnings("deprecation")
	@Deprecated
    public boolean postViolations(BigDecimal docId, String docType,
                                  String dbSessionId, String ipAddress,
                                  String loginId,
                                  Violation[] violations) throws EtccErrorMessageException{
        throw new EtccErrorMessageException("Payment.postViolations Web Service not Available");
        /*boolean posted = stub().postViolations(docId, docType, dbSessionId, ipAddress, loginId, convert(violations));
        return posted;*/
    }
    
	@Deprecated
    public String getPaymentReceipt(BigDecimal docId, String docType,
                                    String dbSessionId, String ipAddress,
                                    String loginId, BigDecimal paymentId,
                                    String reportFormat) throws EtccErrorMessageException{
        // Not used
        throw new EtccErrorMessageException("Web Service Not Available");
    }

	@Deprecated
    public BigDecimal getPaymentLimit() throws EtccErrorMessageException {
        // Not used
        throw new EtccErrorMessageException("Web Service Not Available");
    }
    
     /*private PaymentWsSoapHttpPortClient stub() throws Exception {
         PaymentWsSoapHttpPortClient stub = new PaymentWsSoapHttpPortClient();
         stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "paymentWsSoapHttpPort");
         return stub;
     }*/

    /*private OLC_PAYMENT_INFO_RECBean convert(com.etcc.csc.payment.types.OlcPaymentInfoRecBean theInput) {
        if (theInput == null)
            return null;
        OLC_PAYMENT_INFO_RECBean theOutput = new OLC_PAYMENT_INFO_RECBean();
        theOutput.setADDRESS1(theInput.getAddress1());
        theOutput.setADDRESS2(theInput.getAddress2());
        theOutput.setBANK_ACCT_NUMBER(theInput.getBankAcctNumber());
        theOutput.setBANK_ACCT_TYPE(theInput.getBankAcctType());
        theOutput.setCARD_CODE(theInput.getCardCode());
        theOutput.setCARD_EXPIRES(theInput.getCardExpires());
        theOutput.setCARD_NBR(theInput.getCardNbr());
        theOutput.setCARD_SEC_CODE(theInput.getCardSecCode());
        theOutput.setCITY(theInput.getCity());
        theOutput.setNAME_ON_CARD(theInput.getNameOnCard());
        theOutput.setPLUS4(theInput.getPlus4());
        theOutput.setPMT_TYPE(theInput.getPmtType());
        theOutput.setROUTING_NBR(theInput.getRoutingNbr());
        theOutput.setSTATE(theInput.getState());
        theOutput.setZIP_CODE(theInput.getZipCode());
		return theOutput;
    }*/

    /*private com.etcc.csc.payment.types.Violation[] convert(Violation[] theInput) {
        if (theInput == null)
            return null;
        ArrayList<com.etcc.csc.payment.types.Violation> theOutput = null;
        theOutput = new ArrayList<com.etcc.csc.payment.types.Violation>(theInput.length);
        for (int i = 0; i < theInput.length; i++) {
            theOutput.add(convert(theInput[i]));
        }
        return theOutput.toArray(new com.etcc.csc.payment.types.Violation[theOutput.size()]);
    }*/
    
    /*private com.etcc.csc.payment.types.Violation convert(Violation theInput) {
        if (theInput == null)
            return null;
        com.etcc.csc.payment.types.Violation theOutput = new com.etcc.csc.payment.types.Violation();
        String theNonCopiedProps = DtoUtil.copySimpleProperties(theOutput, theInput);
        return theOutput;
    }*/
}
