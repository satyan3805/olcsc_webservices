package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.service.SecurityQuestionInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SecurityQuestionDelegate extends Delegate implements SecurityQuestionInterface {

    SecurityQuestionInterface sqi = (SecurityQuestionInterface)getServiceObject(ServiceObjectEnum.SECURITY_QUESTION);
    public SecurityQuestionDelegate() {
        super(SecurityQuestionDelegate.class);
    }

    public ArrayList getSecurityQuestions(String locale) throws EtccException{
        try {
            return sqi.getSecurityQuestions(locale);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getSecurityQuestions: " + t, t);
        }
    }
    
    public ArrayList getSecurityQuestionsnAnswers(AccountLoginDTO acctLoginDto,String locale) throws EtccException{
        try {
            return sqi.getSecurityQuestionsnAnswers(acctLoginDto,locale);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getSecurityQuestions and Answers: " + t, t);
        }
    }
    
   public  String getSysParam(String paramName) throws EtccException {
       try {
           return sqi.getSysParam(paramName);
       } catch (Throwable t) {
           logger.error(t);
           throw new EtccException("Error running getSecurityQuestions and Answers: " + t, t);
       }
	   
   }
   
   public  int saveAnswers(AccountLoginDTO acctLoginDto,List<SecurityQuestionDTO> listOfQuestionsnAnswers) throws EtccException {
       try {
           return sqi.saveAnswers(acctLoginDto,listOfQuestionsnAnswers);
       } catch (Throwable t) {
           logger.error(t);
           throw new EtccException("Error running getSecurityQuestions and Answers: " + t, t);
       }
	   
   }
   
    
}
