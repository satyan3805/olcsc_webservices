/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.util.StringUtil;

/**
 * Validates Credit Cards.  Based on CreditCardValidator from OLCSCService.
 * @author Stephen Davidson
 */
public class CreditCardValidator extends BaseValidator {

	private AccountCreditCardDTO cc;

	/**
	 * Constructor.
	 * @param acctCreditCardDto The Credit Card to validate.
	 */
    public CreditCardValidator(AccountCreditCardDTO acctCreditCardDto) {
    	this.cc = acctCreditCardDto;
    }

    @Override
    public boolean isValid() throws ValidationException {
    	//  <form name="/creditCardInfoUpd">
    	if (StringUtil.isEmpty (this.cc.getNameOnCard()))
    		throw new ValidationException("Invalid Credit Card Information. Name on Card is a required field");
    	if (this.cc.getCardType() == null)
    		throw new ValidationException("Invalid Credit Card Information. Credit Card Code is a required field");
    	if (StringUtil.isEmpty (this.cc.getCardNbr()))
    		throw new ValidationException("Invalid Credit Card Information. Credit Card Number is a required field");
        
    	// TODO - verify that the line below correctly implements the following rule from validation.xml for 'CardNbrNew' field
    	/* 	<field property="cardNbrNew" depends="creditCard">
        		<msg name="creditCard" key="creditCardForm.cardNbr.invalid"/>
      		</field>
    	 */    	
    	if (!StringUtil.isEmpty (this.cc.getCardNbrNew())
    			&& !ccNumPattern.matcher(this.cc.getCardNbrNew()).matches())
    		throw new ValidationException("Invalid New Credit Card Information. Credit Card Number New is not valid");

    	if (StringUtil.isEmpty (this.cc.getCardExpires()))
    		throw new ValidationException("Invalid Credit Card Information. Card Expiration Information is a required field");
    	if (StringUtil.isEmpty (this.cc.getAddress1()))
    		throw new ValidationException("Invalid Credit Card Information. Address is a required field");
    	if (StringUtil.isEmpty (this.cc.getCity()))
    		throw new ValidationException("Invalid Credit Card Information. City is a required field");

    	if (!isIntegerString (this.cc.getZipCode(), 5, true))
    		throw new ValidationException("Invalid Credit Card Information. ZipCode field size must at least be 5 digits");

    	if (!isIntegerString (this.cc.getPlus4(), 4, false))
    		throw new ValidationException("Invalid Credit Card Information. ZipCodePlus4 field size must at least be 4 digits, if present");
    	return true;
    }
}
