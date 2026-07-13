/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.validation.BaseValidator;

public class CreditCardValidator extends BaseValidator {
    private static final Logger logger = Logger.getLogger(CreditCardValidator.class);

    public static final String FIELD_CC_TYPE = "creditCardType";
    public static final String FIELD_CC_NUMBER = "creditCardNumber";
    public static final String FIELD_CC_SECURITY_CODE = "creditCardSecurityCode";
    public static final String FIELD_CC_EXPIRE_MONTH = "creditCardExpirationMonth";
    public static final String FIELD_CC_EXPIRE_YEAR = "creditCardExpirationYear";

    private CreditCardValidator() { }

    public static boolean validateCreditCardNumberWithType( Object bean,
                                                            ValidatorAction va,
                                                            Field field,
                                                            ActionMessages errors,
                                                            Validator validator,
                                                            HttpServletRequest request )
    {
        String paymentType = ValidatorUtils.getValueAsString(bean, field.getVarValue("type"));
        if (PaymentType.valueOfCode(paymentType) != PaymentType.CREDIT) {
            return true;
        }
        return validateCreditCardNumber(bean, va, field, errors, validator, request);
    }

    public static boolean validateCreditCardNumber( Object bean,
                                                    ValidatorAction va,
                                                    Field field,
                                                    ActionMessages errors,
                                                    Validator validator,
                                                    HttpServletRequest request )
    {
        // TODO: determine cause of field.getVarValue() returning null when validating form submission for /signupBillingInfo (see validation.xml)
//        03/30/2010 14:12:43[ERROR] ValidatorAction: Unhandled exception thrown during validation: No name specified
//        java.lang.IllegalArgumentException: No name specified
        if (logger.isTraceEnabled()) {
            logger.trace("validateCreditCardNumber.bean=" + bean);
            logger.trace("validateCreditCardNumber.field.vars=" + field.getVars());
        }
        String creditCardType =
            ValidatorUtils.getValueAsString(bean, field.getVarValue(FIELD_CC_TYPE));
        String creditCardNumber =
            ValidatorUtils.getValueAsString(bean, field.getVarValue(FIELD_CC_NUMBER));

        if (StringUtil.isEmpty(creditCardNumber))
            return true;

        if (!isValidCreditCard(creditCardNumber, convertTypeCode(creditCardType))) {
            errors.add(field.getKey(),
                       Resources.getActionMessage(validator, request, va, field));
            return false;
        }
        return true;
    }

    /**
     * Checks that the passed in month-year combination is after current date
     */
    public static boolean validateExpirationYearMonth(  Object bean,
                                                        ValidatorAction va,
                                                        Field field,
                                                        ActionMessages errors,
                                                        Validator validator,
                                                        HttpServletRequest request)
    {
        String year =
            ValidatorUtils.getValueAsString( bean, field.getVarValue( "year" ) );
        String month =
            ValidatorUtils.getValueAsString( bean, field.getVarValue( "month" ) );

        if ( StringUtil.isEmpty(month) || StringUtil.isEmpty(year) )
            return true;

        int intYear;
        int intMonth;
        try {
            intYear = Integer.parseInt(year);
            intMonth = Integer.parseInt(month);
        } catch (NumberFormatException e) {
            return true;
        }

        if ( !afterCurrentDate( intYear, intMonth )) {
            errors.add(
                    field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
            return false;
        }
        return true;
    }

    public static boolean validateExpirationYearMonthWithType(  Object bean,
                                                                ValidatorAction va,
                                                                Field field,
                                                                ActionMessages errors,
                                                                Validator validator,
                                                                HttpServletRequest request)
    {
        String paymentType = ValidatorUtils.getValueAsString(bean, field.getVarValue("type"));
        if (PaymentType.valueOfCode(paymentType) != PaymentType.CREDIT) {
            return true;
        }
        return validateExpirationYearMonth(bean, va, field, errors, validator, request);
    }

    private static boolean afterCurrentDate( int year, int month ) {
      Calendar currentDate = new GregorianCalendar();
      int currentYear = currentDate.get( Calendar.YEAR );
      int currentMonth = currentDate.get( Calendar.MONTH ) + 1;
      return year > currentYear || ( year == currentYear && month >= currentMonth );
    }

    /**
     * @param creditCardCode
     * @return
     * @see CreditCardType#valueOfCode(String)
     */
    private static CreditCardType convertTypeCode( String creditCardCode ) {
        CreditCardType cctype = null;
        String errMsg = "Invalid credit card type encountered.";
        try {
            cctype = CreditCardType.valueOfCode(creditCardCode);
        } catch(IllegalArgumentException e) {
            logger.warn(errMsg, e);
        } catch(NullPointerException e) {
            logger.info(errMsg, e);
        }
        return cctype;
    }
}
