/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * @author Stephen Davidson
 *
 */
public class SecurityQuestionDelegateTest {
    SecurityQuestionDelegate delegate;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new SecurityQuestionDelegate();
    }

    /**
     * Test method for {@link com.etcc.csc.delegate.SecurityQuestionDelegate#getSecurityQuestions()}.
     * @throws Exception If any errors occur.
     */
    @Test
    public void testGetSecurityQuestions() throws Exception {
        final Collection<SecurityQuestionDTO> questions = this.delegate.getSecurityQuestions();
        assertNotNull(questions);
        assertEquals("Question count mismatch", 3, questions.size());
    }

}
