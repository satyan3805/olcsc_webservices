/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.LovDTO;
import com.etcc.csc.service.LovInterface;
import com.etcc.csc.service.ServiceFactory;

public class LovDelegate implements LovInterface {
    public Collection<LovDTO> getLov(String s) throws EtccException, EtccSecurityException {
    	return ServiceFactory.getImplementation(LovInterface.class).getLov(s);
    }
}
