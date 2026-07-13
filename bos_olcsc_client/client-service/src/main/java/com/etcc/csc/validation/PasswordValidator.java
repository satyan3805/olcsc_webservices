/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.util.StringUtil;

public abstract class PasswordValidator extends BaseValidator{

    /**
    * This method validate Password: minLength,maxLength,regularExpression
    * @param password
    * @return
    */
    public static boolean validatePassword(String password) throws EtccException{
            int minlengthConfig = 8;
            int maxlengthConfig = 30;
            String passwordRegExp="^[0-9a-zA-Z\\._\\-@]*$";
            // ^[0-9a-zA-Z\._\-@]*$
//            String passwordRuleText="Password rule is, ";
            
            if ((password==null||password==""))
            {
                throw new EtccException("Invalid Password, Please try again");
            }
            else if ((!validateMinLength(password.length(),minlengthConfig)))
            {
                 throw new EtccException("Invalid Password. Password must be at least "+minlengthConfig + " characters in length. Please try again.");
            }
            else if  ((!validateMaxLength(password.length(),maxlengthConfig)))
            {
                 throw new EtccException("Invalid Password.  Password should be less than " + maxlengthConfig +" characters in length. Please try again.");  
            }
            else if (!password.matches(passwordRegExp)){
                 throw new EtccException("Invalid Password. " + " Please try again."); 
            }           
            return true;
    }
    public static boolean validateLoginId(String loginId) throws EtccException{
        String RegExp = "^([0-9]+[a-zA-Z]+[a-zA-Z0-9]*)$|^([a-zA-Z]+[0-9]+[a-zA-Z0-9]*)$";
        
        if (StringUtil.isEmpty(loginId)){
            throw new EtccException("Username is required.");
        }else if (  (!loginId.toLowerCase().matches(RegExp))||(loginId.length()<=5) ){
            throw new EtccException("Username must be at least 6 alpha-numeric characters");
        } else {
            return true;
        }
    }

    public static boolean validateAccountId(long acctId) throws EtccException{
        
        if (acctId==0){
            throw new EtccException("Account number is required.");
        } else {
            return true;
        }
    }
    
    
    
}


