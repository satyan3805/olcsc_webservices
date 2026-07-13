/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.service.CreditCardInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.UIDateUtil;

public class CreditCardDelegate implements CreditCardInterface {
    private static final Logger logger = Logger.getLogger(CreditCardDelegate.class);

    public AccountCreditCardDTO getAccountCreditCard(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {
        try {
            logger.debug("**************===========> 21 - came to CreditCardDelegate.getAccountCreditCard()");
            return ServiceFactory.getImplementation(CreditCardInterface.class).getAccountCreditCard(acctLoginDto);
        } catch (EtccSecurityException e){
            logger.error("getAccountCreditCard: " + e.getMessage(), e);
            throw e;
        } catch (EtccException e){
            logger.error("getAccountCreditCard: " + e.getMessage(), e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("getAccountCreditCard: " + e.getMessage(), e);
            throw e;
        }
    }

    public PaymentResultDTO updateAccountCreditCard(AccountLoginDTO acctLoginDto,
            AccountCreditCardDTO acctCreditCardDto, boolean fromPaymentScreen) throws EtccException,
            EtccSecurityException {

        try {
            logger.debug("**************===========> 22 - came to CreditCardDelegate.updateAccountCreditCard()");
            return ServiceFactory.getImplementation(CreditCardInterface.class).updateAccountCreditCard(acctLoginDto,
                    acctCreditCardDto, fromPaymentScreen);
        } catch (EtccSecurityException e){
            logger.error("updateAccountCreditCard: " + e.getMessage(), e);
            throw e;
        } catch (EtccException e){
            logger.error("updateAccountCreditCard: " + e.getMessage(), e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("updateAccountCreditCard: " + e.getMessage(), e);
            throw e;
        }
    }

    public Collection<CreditCardDTO> getCreditCardTypes() throws EtccException {
        try {
            return ServiceFactory.getImplementation(CreditCardInterface.class).getCreditCardTypes();
        } catch (EtccException e){
            logger.error("getCreditCardTypes: " + e.getMessage(), e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("getCreditCardTypes: " + e.getMessage(), e);
            throw e;
        }
    }

    public Collection<LabelValueBean> getCreditCardYears() {
        return UIDateUtil.getCreditCardExpYears(10);
    }

    public Collection<LabelValueBean> getCreditCardMonths() {
        return UIDateUtil.getCreditCardMonths();
    }

    /**
     * Returns the credit card name corresponding to the specified card type.
     * @param cardType the card type
     * @return the credit card name corresponding to the specified card type
     * @throws EtccException
     * @see #getCreditCardTypes()
     * @see CreditCardDTO.CreditCardType#getDisplay()
     */
    // TODO: What is the difference between this and CreditCardDTO.CreditCardType.getDisplay()
    public String getCreditCardName(CreditCardDTO.CreditCardType cardType) throws EtccException {
        return getCreditCardName(cardType, getCreditCardTypes());
    }

    /**
     * Returns the credit card name corresponding to the specified code.
     * @param creditCardCode single-character code represented as a string
     * @return the credit card name corresponding to the specified code
     * @throws EtccException
     * @see #getCreditCardTypes()
     * @see CreditCardDTO.CreditCardType#getDisplay()
     */
    // TODO: What is the difference between this and CreditCardDTO.CreditCardType.getDisplay()
    public String getCreditCardName(String creditCardCode) throws EtccException {
        return getCreditCardName(creditCardCode, getCreditCardTypes());
    }

    public static String getCreditCardName(CreditCardDTO.CreditCardType cardType, Collection<CreditCardDTO> creditCards) {
        for (CreditCardDTO cc : creditCards) {
            if (cc.getCardType() == cardType) {
                return (cc.getCardName());
            }
        }
        return "";
    }

    public static String getCreditCardName(String creditCardCode, Collection<CreditCardDTO> creditCards) {
        try {
            CreditCardDTO.CreditCardType cardType = CreditCardDTO.CreditCardType.valueOfCode(creditCardCode);
            return getCreditCardName(cardType, creditCards);
        } catch(IllegalArgumentException e) {
            logger.info(e);
        }
        return "";
    }
}
