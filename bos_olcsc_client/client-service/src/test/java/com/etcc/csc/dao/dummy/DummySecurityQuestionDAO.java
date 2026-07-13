/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.ArrayList;
import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * @author Stephen Davidson
 *
 */
public class DummySecurityQuestionDAO extends SecurityQuestionDAO {
    /** 
     * @see com.etcc.csc.dao.SecurityQuestionDAO#loadSecurityQuestions()
     */
    @Override
    protected Collection<SecurityQuestionDTO> loadSecurityQuestions() throws EtccException {
        final Collection<SecurityQuestionDTO> questions = new ArrayList<SecurityQuestionDTO>(3);
        int i = 1;
        questions.add(new SecurityQuestionDTO(i++, "Mother's name"));
        questions.add(new SecurityQuestionDTO(i++, "Dog's name"));
        questions.add(new SecurityQuestionDTO(i++, "Cat's name"));
        return questions;
    }
}
