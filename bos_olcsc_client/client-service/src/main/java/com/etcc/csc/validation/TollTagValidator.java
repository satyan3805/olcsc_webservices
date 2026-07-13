/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.util.StringUtil;

//Copied from OLCSCService com.etcc.csc.service.validation.TollTagValidator
public abstract class TollTagValidator extends BaseValidator {

    public static void validateCreateAccount (
    		String firstName, String middleInitial, String lastName, String companyName, String email,
            String loginId, String securityQuestion, String securityAnswer, String ipAddress, String jsessionId,
            String userEnv
        ) throws EtccException 
    {	
    	if (StringUtil.isEmpty (firstName) || !firstName.matches("^[a-zA-Z ,.-]*$")) 		
			throw new EtccException("Invalid firstName (it is a required field)"); 
    	if (!StringUtil.isEmpty (middleInitial) && !middleInitial.matches("^[a-zA-Z]*$")) 		
			throw new EtccException("Invalid middleInitial"); 
    	if (StringUtil.isEmpty (lastName) || !lastName.matches("^[a-zA-Z ,.-]*$")) 		
			throw new EtccException("Invalid lastName (it is a required field)"); 
    	if (StringUtil.isEmpty (securityAnswer)) 		
			throw new EtccException("Invalid securityAnswer (it is a required field)");  	
    	if (StringUtil.isEmpty (loginId) || !loginId.matches("^\\w{6,30}$")) // loginId is userId
			throw new EtccException("Invalid loginId (it is a required field)");
    	
    	if (StringUtil.isEmpty (email) || !isValidEmail(email))
			throw new EtccException("Invalid email (it is a required field)"); 
    }
    
    
    public static void validatePersonalInfo (
    		long accountId, String address, String addressLine2, String city, String state, 
            String zipcode, String zipPlus4, String homePhoneNumber, String workPhoneNumber, String workPhoneExt, 
            String dlNumber,String dlState, String monthlyStmtFlag, int msId, String dbSessionId, 
            String loginId, String ipAddress, long transactionId, boolean checkDuplicateDriverLicense, Long posId
        ) throws EtccException 
    {	
    	if (StringUtil.isEmpty (address) || !address.matches("^[0-9a-zA-Z ,#.-]*$")) 		
			throw new EtccException("Invalid address (line 1)"); 
    	if (!StringUtil.isEmpty (addressLine2) && !addressLine2.matches("^[0-9a-zA-Z ,#.-]*$")) 		
			throw new EtccException("Invalid addressLine2"); 
    	if (StringUtil.isEmpty (city)  || !city.matches("^[a-zA-Z ,._-]*$")) 		
			throw new EtccException("Invalid city"); 
    	
    	if (!isMinLength (zipcode, 5, true)  || !zipcode.matches("^[0-9]*$") ) 		
			throw new EtccException("Invalid zipcode"); 
    	if (zipPlus4 != null && (!isMinLength (zipPlus4, 4, false) || !zipPlus4.matches("^[0-9]*$")) ) 		
			throw new EtccException("Invalid zipcodePlus4");    
    	if (StringUtil.isEmpty (homePhoneNumber)  || !homePhoneNumber.matches("^[0-9]*$")) 		
			throw new EtccException("Invalid homePhoneNumber"); 
    	if (!StringUtil.isEmpty (workPhoneNumber) && !workPhoneNumber.matches("^[0-9]*$")) 		
			throw new EtccException("Invalid workPhoneNumber");    	
    	if (!StringUtil.isEmpty (workPhoneExt) && !workPhoneExt.matches("^[0-9]*$")) 		
			throw new EtccException("Invalid workPhoneExt");    	
    	
    	if (StringUtil.isEmpty (dlNumber) || !dlNumber.matches("^[0-9a-zA-Z]*$") ) 		
			throw new EtccException("Invalid dlNumber"); 
    	   	
    }
    
    public static void validatePaymentInfo (
    		long acctId, String dbSessionId, String ipAddress, String loginId, long rtlTrxnId, 
            String name, String address, String addressLine2, String city, String state,
            String zipCode, String zipPlus4, String cardNumber, CreditCardDTO.CreditCardType cardType, String expirationMonth,
            String expirationYear      
        ) throws EtccException 
    {	
    	if (StringUtil.isEmpty (name) || !name.matches("^[a-zA-Z ,.-]*$")) 		
			throw new EtccException("Invalid name"); 
    	if (StringUtil.isEmpty (address) || !address.matches("^[0-9a-zA-Z ,#.-]*$")) 		
			throw new EtccException("Invalid address (line 1)"); 
    	if (!StringUtil.isEmpty (addressLine2) && !addressLine2.matches("^[0-9a-zA-Z ,#.-]*$")) 		
			throw new EtccException("Invalid addressLine2"); 
    	if (StringUtil.isEmpty (city)  || !city.matches("^[a-zA-Z ,._-]*$")) 		
			throw new EtccException("Invalid city"); 
    	
    	if (!isMinLength (zipCode, 5, true) || !zipCode.matches("^[0-9]*$"))	
			throw new EtccException("Invalid zipcode"); 
    	if (zipPlus4 != null && (!isMinLength (zipPlus4, 4, false) || !zipPlus4.matches("^[0-9]*$")) ) 		
			throw new EtccException("Invalid zipcodePlus4");

    	// TODO - card number validation - is this enough? 
    	/* it implements the following logic
        <field property="creditCardNumber" depends="required,validCreditCardNumber,creditCard">
        <msg name="required" key="TollTagForm.creditCardNumber.required"/>
        <msg name="validCreditCardNumber" key="TollTagForm.creditCardNumber.valid"/>
        <msg name="creditCard" key="TollTagForm.creditCardNumber.valid"/>
        <var><var-name>creditCardType</var-name><var-value>creditCardType</var-value></var> 
        <var><var-name>creditCardNumber</var-name><var-value>creditCardNumber</var-value></var> 
      </field>
      
      <field property="creditCardExpirationMonth" depends="required,validateExpirationYearMonth">
        <msg name="required" key="TollTagForm.creditCardExpirationMonth.required"/>
        <msg name="validateExpirationYearMonth" key="TollTagForm.creditCardExpirationMonth.valid"/>
        <var><var-name>year</var-name><var-value>creditCardExpirationYear</var-value></var> 
        <var><var-name>month</var-name><var-value>creditCardExpirationMonth</var-value></var> 
      </field>
    	 */
    	if (StringUtil.isEmpty (cardNumber) || !isValidCreditCard(cardNumber, cardType))
			throw new EtccException("Invalid cardNumber/cardCode"); 
    	if (StringUtil.isEmpty (expirationMonth) || !expirationMonth.matches("^[0-9]{2}$")) // month has to be 2 digits
			throw new EtccException("Invalid card expirationMonth");    	
    	if (StringUtil.isEmpty (expirationYear) || !expirationYear.matches("^[0-9]*$")) 		
			throw new EtccException("Invalid card expirationYear"); 
    }


}


