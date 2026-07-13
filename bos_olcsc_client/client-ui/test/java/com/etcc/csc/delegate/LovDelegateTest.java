/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.LovDTO;
import com.etcc.csc.service.LovInterface;

/**
 * Tests for LOV delegate.
 * @author Milosh Boroyevich
 */
public class LovDelegateTest {
	private static final Logger logger = Logger.getLogger(LovDelegateTest.class);

	LovDelegate delegate;

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
        this.delegate = new LovDelegate();
    }

    /**
     * Test for {@link LovDelegate#getLov(String)}.
     * @throws Exception
     */
    @Test
    public void testGetLov() throws Exception {
    	Collection<LovDTO> lov = delegate.getLov(LovInterface.MSTAT_SORT_ACCT_SUM_BY);
    	logger.debug("Retrieved LOV (" + LovInterface.MSTAT_SORT_ACCT_SUM_BY + "): " + lov);
    	assertNotNull("No LOV generated.", lov);
    }
}
