/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.LovDTO;

/**
 * Test implementation of <tt>LovInterface</tt>.
 * @author Milosh Boroyevich
 * @see LovFactory
 */
public class LovTestImpl implements LovInterface {
    public Collection<LovDTO> getLov(String lovName) throws EtccException {
    	return LovFactory.getStaticLov(lovName);
	}
}
