package com.etcc.csc.presentation.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class AmountValidator {
    public AmountValidator() {
    }
    
     public static boolean validPayAmount( Object bean,
                                                     ValidatorAction va,
                                                     Field field,
                                                     ActionMessages errors,
                                                     Validator validator,
                                                     HttpServletRequest request )
     {
       String payAmount =
           ValidatorUtils.getValueAsString( bean,
                                         field.getVarValue( "payAmount" ) );
       
        if ((payAmount == null) || (payAmount.trim().length() == 0)) {
            errors.add(
                field.getKey(),
                Resources.getActionMessage(validator, request, va, field));
            return false;
        }
        
        double payAmountDouble = 0;
        
        try {
            payAmountDouble = Double.parseDouble(payAmount);
        } catch (NumberFormatException nfe) {
            errors.add(
                field.getKey(),
                Resources.getActionMessage(validator, request, va, field));
            return false;
        }
        
        double minAmountDouble = 1.00;
        
        if (payAmountDouble >= minAmountDouble) {
            return true;
        }

         errors.add(
             field.getKey(),
             Resources.getActionMessage(validator, request, va, field));
         return false;
     }


    public static boolean validRebillAmt( Object bean,
                                                    ValidatorAction va,
                                                    Field field,
                                                    ActionMessages errors,
                                                    Validator validator,
                                                    HttpServletRequest request )
    {
      String rebillAmt =
          ValidatorUtils.getValueAsString( bean,
                                        field.getVarValue( "rebillAmt" ) );
    
        String reqMinRebillAmt =
            ValidatorUtils.getValueAsString( bean,
                                          field.getVarValue( "reqMinRebillAmt" ) );
      
       if ((rebillAmt == null) || (rebillAmt.trim().length() == 0)) {
           errors.add(
               field.getKey(),
               Resources.getActionMessage(validator, request, va, field));
           return false;
       }
       
       double rebillAmtDouble = 0;
       double reqMinRebillAmtDouble = 0;
       
       try {
           rebillAmtDouble = Double.parseDouble(rebillAmt);
           reqMinRebillAmtDouble = Double.parseDouble(reqMinRebillAmt);
       } catch (NumberFormatException nfe) {
           errors.add(
               field.getKey(),
               Resources.getActionMessage(validator, request, va, field));
           return false;
       }
       
       if (rebillAmtDouble >= reqMinRebillAmtDouble) {
           return true;
       }

        errors.add(
            field.getKey(),
            Resources.getActionMessage(validator, request, va, field));
        return false;
    }
     
}