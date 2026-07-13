/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Stephen Davidson
 *
 */
public class TagDTOTest {
    
    private static final String TAG_PLATE = "JUNIT";
    private static final String TAG_STATE = "TX";
    
    TagDTO dto;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dto = new TagDTO();
        this.dto.setLicPlate(TAG_PLATE);
        this.dto.setLicState(TAG_STATE);
    }

    /**
     * Test method for {@link TagDTO#equals(Object)}.
     */
    @Test
    public void testEqualsTagDTO() {
        TagDTO testDto = new TagDTO();
        testDto.setLicPlate(TAG_PLATE);
        testDto.setLicState(TAG_STATE);
        assertEquals(this.dto, testDto);
        testDto.setLicPlate("MA");
        assertFalse(this.dto.equals(testDto));
        testDto = this.dto;
        assertEquals(this.dto, testDto);
    }
}
