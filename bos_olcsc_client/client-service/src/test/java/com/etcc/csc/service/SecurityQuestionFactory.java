/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * Generates the Security Questions for the Dummy/Test ServiceFactory.
 * @author Stephen Davidson
 *
 */
public class SecurityQuestionFactory {
    
    static final Collection<SecurityQuestionDTO> questions = generateQuestions();
    
    private static Collection<SecurityQuestionDTO> generateQuestions(){
        final int size = 3;
        Collection<SecurityQuestionDTO> questions = new ArrayList<SecurityQuestionDTO>(size);
        for(int idx = 0; idx < size; idx++){
            SecurityQuestionDTO question = new SecurityQuestionDTO();
            question.setSecurityQuestion("Security Question #" + idx);
            question.setSecurityQuestionID(idx);
            questions.add(question);
        }
        return Collections.unmodifiableCollection(questions);
    }
    
    
    static void loadImpl(ServiceFactoryTestImpl f, final SecurityQuestionInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).getSecurityQuestions();
                will(returnValue(questions));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
