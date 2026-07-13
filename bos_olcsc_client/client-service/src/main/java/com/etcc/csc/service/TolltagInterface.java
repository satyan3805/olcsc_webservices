/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PersonalInfoResponse;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;

import java.math.BigDecimal;

import javax.ejb.Local;

//Copied from com.etcc.csc.service.TolltagInterface in OLCSCService module.
@Local
public interface TolltagInterface extends ServiceInterface {

    public AccountLoginDTO createAccount(String firstName, 
                                         String middleInitial, String lastName, 
                                         String companyName, String email, 
                                         String loginId, 
                                         String securityQuestion, 
                                         String securityAnswer, 
                                         String ipAddress, String jsessionId, 
                                         String userEnv) throws EtccErrorMessageException, 
                                                                EtccException;

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
                                                Long posId) throws EtccErrorMessageException, 
                                                EtccException;

    public void setPaymentInfo(long acctId, String dbSessionId, 
                               String ipAddress, String loginId, 
                               long rtlTrxnId, String name, String adddress, 
                               String addressLine2, String city, String state, 
                               String zipcode, String plus4, String cardNumber, 
                               CreditCardDTO.CreditCardType cardType, String expirationMonth, 
                               String expirationYear) throws EtccErrorMessageException, 
                               EtccException;

    public double getTagPayInfo(long accountId, String dbSessionId, 
                                String loginId, String ipAddress, 
                                long transactionId, Long posId) throws EtccErrorMessageException, 
                                EtccException;

    public boolean makePayment(long accountId, String dbSessionId, 
                               String loginId, String ipAddress, 
                               long transactionId, double paymentAmount, 
                               String cardCode, String paymentType, 
                               String cardNumber, String expireMonth, 
                               String expireYear, String nameOnCard, 
                               String address, String address2, String city, 
                               String state, String zipCode, String plus4, 
                               String cardSecurityCode) throws EtccErrorMessageException, 
                               EtccException;


    public String loginCreation(BigDecimal accountId, String loginType, 
                              String dbSessionId, String loginId, 
                              String ipAddress, String firstName, 
                              String middleInitial, String lastName, 
                              String email) throws EtccErrorMessageException, 
                              EtccException;

    // refactored from UtilInterface
    public OlcMarketSourceRecBean[] getMarketSource() throws EtccException;
}
