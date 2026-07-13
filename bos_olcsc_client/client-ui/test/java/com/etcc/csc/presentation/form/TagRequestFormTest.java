/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.util.UIDateUtil;

/**
 * Unit tests for TagRequestForm.
 * @author Milosh Boroyevich
 * @see TagRequestForm
 * @see TagDTO
 */
public class TagRequestFormTest {
    private static final Logger logger = Logger.getLogger(TagRequestFormTest.class);
    TagRequestForm form;
    TagDTO dto;
    private static BeanUtilsBean beanutil;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        beanutil = BeanUtilsBean.getInstance();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.form = new TagRequestForm();
        this.dto = new TagDTO();
    }

    /**
     * Test method for initializing the TagRequestForm using BeanUtilsBean and updatePbpTag().
     * @throws Exception thrown if any exceptions occur during the test.
     * @see BeanUtilsBean
     * @see TagRequestForm#updatePbpTag(TagDTO)
     */
    @Test
    public void testInitialize() throws Exception {
        beanutil.setProperty(dto, "pbpStart", "");
        beanutil.setProperty(dto, "pbpEnd", "");
        form.updatePbpTag(dto);
        logger.debug("form.pbpStart="+form.getPbpStart());
        logger.debug("dto.pbpStartDate="+dto.getPbpStartDate());
        assertNull(dto.getPbpStartDate());
        assertNotNull(form.getPbpStart());
        logger.debug("form.pbpEnd="+form.getPbpEnd());
        logger.debug("dto.pbpEndDate="+dto.getPbpEndDate());
        assertNull(dto.getPbpEndDate());
        assertNotNull(form.getPbpEnd());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        beanutil.setProperty(dto, "pbpStart", UIDateUtil.getMediumDateTime(cal));
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        beanutil.setProperty(dto, "pbpEnd", UIDateUtil.getMediumDateTime(cal));
        form.updatePbpTag(dto);
        logger.debug("form.pbpStart="+form.getPbpStart());
        logger.debug("dto.pbpStartDate="+dto.getPbpStartDate());
        assertNotNull(dto.getPbpStartDate());
        assertNotNull(form.getPbpStart());
        logger.debug("form.pbpEnd="+form.getPbpEnd());
        logger.debug("dto.pbpEndDate="+dto.getPbpEndDate());
        assertNotNull(dto.getPbpEndDate());
        assertNotNull(form.getPbpEnd());
        assertTrue(dto.isPbpTag());
        assertTrue(form.isPbpTag());
    }
}
