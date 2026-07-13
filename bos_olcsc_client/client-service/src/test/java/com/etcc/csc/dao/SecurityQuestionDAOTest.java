/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.junit.Test;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * @author Stephen Davidson
 *
 */
public class SecurityQuestionDAOTest {
    
    static Collection<SecurityQuestionDTO> testQuestions = Arrays.asList(new SecurityQuestionDTO(1, "Question 1"));

    /**
     * Test method for {@link com.etcc.csc.dao.SecurityQuestionDAO#getSecurityQuestions()}.
     * @throws Exception if any exceptions occur during the test.
     */
    @Test
    public void testGetSecurityQuestions() throws Exception {
        //Check to see if initial load works.
        final SecurityQuestionDAOTestImpl dao = new SecurityQuestionDAOTestImpl();
        dao.initialSanityCheck();
        final Collection <SecurityQuestionDTO> questions = dao.getSecurityQuestions();
        assertEquals(testQuestions, questions);
        dao.finalSanityCheck();
        //end testGetSecurityQuestions
    }
    
    private class SecurityQuestionDAOTestImpl extends SecurityQuestionDAO {
        
        /**
         * Constructor.
         */
        protected SecurityQuestionDAOTestImpl(){
            //end <init>
        }

        /**
         * @see com.etcc.csc.dao.SecurityQuestionDAO#loadSecurityQuestions()
         */
        @Override
        protected Collection<SecurityQuestionDTO> loadSecurityQuestions() throws EtccException {
            return testQuestions;
            //end loadSecurityQuestions
        }
        
        protected void initialSanityCheck(){
            assertTrue("Security quesions not empty", questions.isEmpty());
            assertNull(lastLoad);
            assertNull(expires);
        }
        
        protected void finalSanityCheck(){
            assertFalse(questions.isEmpty());
            assertNotNull(lastLoad);
            assertNotNull(expires);
            final Calendar now = Calendar.getInstance();
            assertTrue("Last load (" + lastLoad + ") is after now( " + now + ')', lastLoad.before(now) || lastLoad.equals(now));
            assertTrue("Load Expires (" + expires + ") is before now( " + now + ')', expires.after(now.getTime()));
        }
    }

}//SecurityQuestionDAOTest
