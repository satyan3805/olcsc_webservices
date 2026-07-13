package com.etcc.csc.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import org.apache.commons.validator.util.ValidatorUtils;

public class StrutsValidator {
    public StrutsValidator() {
    }
    
    public static boolean validateTwoFields(
    Object bean,
    ValidatorAction va,
    Field field,
    ActionMessages errors,
    Validator validator,
    HttpServletRequest request) {
/*        Object bean,
        ValidatorAction va, 
        Field field,
        ActionErrors errors,
        HttpServletRequest request, 
        ServletContext application) {*/
    
        String value = ValidatorUtils.getValueAsString(
            bean, 
            field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(
            bean, 
            sProperty2);

        if (!GenericValidator.isBlankOrNull(value)) {
           try {
              if (!value.equals(value2)) {
                  errors.add(
                      field.getKey(),
                      Resources.getActionMessage(validator, request, va, field));
/*              
                 errors.add(field.getKey(),
                    Resources.getActionError(
                        application,
                        request,
                        va,
                        field));
*/
                 return false;
              }
           } catch (Exception e) {
/*                 errors.add(field.getKey(),
                    Resources.getActionError(
                        application,
                        request,
                        va,
                        field));
*/
                 String msg = "Two fields error for field " + field.getKey() 
                    + " - " + e;
                 errors.add(field.getKey(), new ActionMessage(msg + " - " 
                    + e, false));

                 return false;
           }
        }

        return true;
    } 
    
    public static boolean validateTwoFieldsDifferent(
        Object bean,
        ValidatorAction va,
        Field field,
        ActionMessages errors,
        Validator validator,
        HttpServletRequest request) {
        
        String value = ValidatorUtils.getValueAsString(
            bean, 
            field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(
            bean, 
            sProperty2);
        
        if (!GenericValidator.isBlankOrNull(value)) {
            try
            {
                if(value.equals(value2)) {
                    errors.add(
                        field.getKey(),
                        Resources.getActionMessage(validator, request, va, field));
                    return false;
                }
            }catch(Exception e) {
                String msg = "Two fields different error for field " + field.getKey() 
                   + " - " + e;
                errors.add(field.getKey(), new ActionMessage(msg + " - " 
                   + e, false));
                return false;
            }
        }
        
        return true;
        
    }
    
    public static boolean validateTwoFieldsIgnoreCase(
    Object bean,
    ValidatorAction va,
    Field field,
    ActionMessages errors,
    Validator validator,
    HttpServletRequest request) {
    /*        Object bean,
        ValidatorAction va, 
        Field field,
        ActionErrors errors,
        HttpServletRequest request, 
        ServletContext application) {*/
    
        String value = ValidatorUtils.getValueAsString(
            bean, 
            field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(
            bean, 
            sProperty2);

        if (!GenericValidator.isBlankOrNull(value)) {
           try {
              if (!value.equalsIgnoreCase(value2)) {
                  errors.add(
                      field.getKey(),
                      Resources.getActionMessage(validator, request, va, field));
    /*
                 errors.add(field.getKey(),
                    Resources.getActionError(
                        application,
                        request,
                        va,
                        field));
    */
                 return false;
              }
           } catch (Exception e) {
    /*                 errors.add(field.getKey(),
                    Resources.getActionError(
                        application,
                        request,
                        va,
                        field));
    */
                 String msg = "Two fields error for field " + field.getKey() 
                    + " - " + e;
                 errors.add(field.getKey(), new ActionMessage(msg + " - " 
                    + e, false));

                 return false;
           }
        }

        return true;
    } 
}
