/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Stephen Davidson
 *
 */
public class ErrorMessageDTOTest {
    
    private static final Logger logger = Logger.getLogger(ErrorMessageDTOTest.class);

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#ErrorMessageDTO()}.
     */
    @Test
    public void testErrorMessageDTO() {
        ErrorMessageDTO dto = new ErrorMessageDTO();
        assertNull(dto.getKey());
        assertNull(dto.getMessage());
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#ErrorMessageDTO(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testErrorMessageDTOStringString() {
        final String key = "key";
        final String message = "message";
        ErrorMessageDTO dto = new ErrorMessageDTO(key, message);
        assertSame(key, dto.getKey());
        assertSame(message, dto.getMessage());
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#toStringBuilder(com.etcc.csc.dto.ErrorMessageDTO[])}.
     */
    @Test
    public void testToStringBuilderErrorMessageDTOArray() {
        StringBuilder sb = ErrorMessageDTO.toStringBuilder(null);
        assertNotNull("No Stringbuilder created for null parameter.", sb);
        assertEquals("Non-empty Stringbuilder created for a null parameter", 0, sb.length());
        sb = ErrorMessageDTO.toStringBuilder(new ErrorMessageDTO[]{});
        assertNotNull("No Stringbuilder created for empty array.", sb);
        assertEquals("Non-empty Stringbuilder created for a empty array", 0, sb.length());

        final ErrorMessageDTO dto = new ErrorMessageDTO("junit.test", "Test Message");
        sb = ErrorMessageDTO.toStringBuilder(new ErrorMessageDTO[]{dto});
        assertNotNull("No Stringbuilder created for array.", sb);
        final StringBuilder dtoSb = dto.toStringBuilder();
        assertEquals("Empty Stringbuilder created for array", dtoSb.length(), sb.length());
        assertEquals(dtoSb.toString(), sb.toString());
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#toStringBuilder()}.
     */
    @Test
    public void testToStringBuilder() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#toString()}.
     */
    @Test
    public void testToString() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#getKey()}.
     */
    @Test
    public void testGetKey() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#setKey(java.lang.String)}.
     */
    @Test
    public void testSetKey() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#getMessage()}.
     */
    @Test
    public void testGetMessage() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dto.ErrorMessageDTO#setMessage(java.lang.String)}.
     */
    @Test
    public void testSetMessage() {
        logger.warn("Not yet implemented");
    }

}
