/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PersonalInfoResponse;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.TolltagInterface;

public class TolltagDelegate implements TolltagInterface {
    private static final Logger logger = Logger.getLogger(TolltagDelegate.class);

    public AccountLoginDTO createAccount(String firstName, 
                                         String middleInitial, String lastName, 
                                         String companyName, String email, 
                                         String loginId, 
                                         String securityQuestion, 
                                         String securityAnswer, 
                                         String ipAddress, String jsessionId, 
                                         String userEnv) throws EtccErrorMessageException, EtccException {
    	
        logger.debug ("**************===========> 31 - came to TolltagDelegate.createAccount()");
            return stub().createAccount(
                                       firstName, 
                                       middleInitial, 
                                       lastName, 
                                       companyName, 
                                       email, loginId, 
                                       securityQuestion, 
                                       securityAnswer, 
                                       ipAddress, 
                                       jsessionId, 
                                       userEnv);


    }


    public PersonalInfoResponse setPersonalInfo(long accountId, String address, 
                                                String addressLine2, 
                                                String city, String state, 
                                                String zipcode, 
                                                String zipPlus4, 
                                                String homePhoneNumber, 
                                                String workPhoneNumber, 
                                                String workPhoneExt, 
                                                String dlNumber, 
                                                String dlState, 
                                                String monthlyStmtFlag, 
                                                int msId, String dbSessionId, 
                                                String loginId, 
                                                String ipAddress, 
                                                long transactionId, 
                                                boolean checkDuplicateDriverLicense, 
                                                Long posId) throws EtccErrorMessageException, EtccException {
        logger.debug ("**************===========> 32 - came to TolltagDelegate.setPersonalInfo()");
        return stub().setPersonalInfo(
                                                accountId, address, addressLine2,
                                                city, state, zipcode, zipPlus4,
                                                homePhoneNumber, workPhoneNumber,
                                                workPhoneExt, dlNumber, dlState,
                                                monthlyStmtFlag, msId, dbSessionId,
                                                loginId, ipAddress, transactionId,
                                                checkDuplicateDriverLicense, posId);
    
    }

    public void setPaymentInfo(long acctId, String dbSessionId, 
                               String ipAddress, String loginId, 
                               long rtlTrxnId, String name, String adddress, 
                               String addressLine2, String city, String state, 
                               String zipcode, String plus4, String cardNumber, 
                               CreditCardDTO.CreditCardType cardType, String expirationMonth, 
                               String expirationYear) throws EtccErrorMessageException, EtccException{
        
        logger.debug ("**************===========> 33 - came to TolltagDelegate.setPaymentInfo()");
        stub().setPaymentInfo(
                acctId, dbSessionId, ipAddress, loginId,
                rtlTrxnId, name, adddress, addressLine2,
                city, state, zipcode, plus4, cardNumber, 
                cardType, expirationMonth, expirationYear);
    }

    public OlcMarketSourceRecBean[] getMarketSource() throws EtccException{
        logger.debug ("**************===========> 34 - came to TolltagDelegate.getMarketSource()");
        return stub().getMarketSource();
    }

    public double getTagPayInfo(long accountId, String dbSessionId, 
                                String loginId, String ipAddress, 
                                long transactionId, Long posId) throws EtccErrorMessageException, 
                                EtccException{
        logger.debug ("**************===========> 35 - came to TolltagDelegate.getTagPayInfo()");
        return stub().getTagPayInfo(
                accountId, dbSessionId, loginId, ipAddress,
                transactionId, posId);
        // com.etcc.csc.service.TollTagWSSoapHttpPortClient myPort = new com.etcc.csc.service.TollTagWSSoapHttpPortClient();
        // return myPort.getTagPayInfo(
        //  return new TollTag().getTagPayInfo(
    }

    public boolean makePayment(long accountId, String dbSessionId, 
                               String loginId, String ipAddress, 
                               long transactionId, double paymentAmount, 
                               String cardCode, String paymentType, 
                               String cardNumber, String expireMonth, 
                               String expireYear, String nameOnCard, 
                               String address, String address2, String city, 
                               String state, String zipCode, String plus4, 
                               String cardSecurityCode) throws EtccErrorMessageException, 
                               EtccException{
        logger.debug ("**************===========> 36 - came to TolltagDelegate.makePayment()");
        return stub().makePayment(
                            accountId, dbSessionId, loginId, ipAddress,
                            transactionId, paymentAmount, cardCode, paymentType,
                            cardNumber, expireMonth, expireYear, nameOnCard,
                            address, address2, city, state, zipCode, plus4,
                            cardSecurityCode);
    }

    public String loginCreation(BigDecimal accountId, String loginType, 
                                String dbSessionId, String loginId, 
                                String ipAddress, String firstName, 
                                String middleInitial, String lastName, 
                                String email) throws EtccErrorMessageException, 
                                EtccException{
        logger.debug ("**************===========> 37 - came to TolltagDelegate.loginCreation()");
        return stub().loginCreation(
                                        accountId, loginType, dbSessionId, loginId,
                                        ipAddress, firstName, middleInitial, lastName,
                                        email);
    
    /*        return new TollTag().loginCreation(
                                                accountId, loginType, dbSessionId, loginId,
                                                ipAddress, firstName, middleInitial, lastName,
                                                email);
    */
    }
    private TolltagInterface stub() {
        return ServiceFactory.getImplementation(TolltagInterface.class);
    }   

//    private UtilInterface utilStub(){
//        return ServiceFactory.getImplementation(UtilInterface.class);
//    }
    
//    private TolltagWsSoapHttpPortClient stub() throws Exception {
//        TolltagWsSoapHttpPortClient stub = new TolltagWsSoapHttpPortClient();
//        stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "tolltagWsSoapHttpPort");
//        return stub;
//    }   
//
//    private UtilWsSoapHttpPortClient utilStub() throws Exception {
//        UtilWsSoapHttpPortClient stub = new UtilWsSoapHttpPortClient();
//        stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "utilWsSoapHttpPort");
//        return stub;
//    }   

}
