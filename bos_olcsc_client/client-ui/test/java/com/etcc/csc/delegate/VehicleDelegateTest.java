/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.service.VehicleFactory;

/**
 * @author Stephen Davidson
 *
 */
public class VehicleDelegateTest  {
    
    VehicleDelegate delegate;

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
        this.delegate = new VehicleDelegate();
    }

    /**
     * Test method for {@link com.etcc.csc.delegate.VehicleDelegate#getVehicleClasses()}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetVehicleClasses() throws Exception {
        Collection<VehicleClassDTO> classes = this.delegate.getVehicleClasses();
        assertNotNull(classes);
        assertEquals("Incorrect number of test vehicle classes returned", VehicleFactory.TOTAL_CLASSES, classes.size());
    }

    /**
     * Test method for {@link com.etcc.csc.delegate.VehicleDelegate#getVehicleMakes()}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetVehicleMakes() throws Exception {
        Collection<VehicleMakeDTO> makes = this.delegate.getVehicleMakes();
        assertNotNull(makes);
        assertEquals("Incorrect number of test vehicle makes returned", VehicleFactory.TOTAL_MAKES, makes.size());
    }
}
