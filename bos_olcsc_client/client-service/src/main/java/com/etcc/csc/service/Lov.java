/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.LovDTO;

/**
 * @author Milosh Boroyevich
 */
@Stateless(name="com/etcc/Lov")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Lov", targetNamespace = "http://ws.csc.etcc.com/Lov")
public class Lov implements LovInterface {

    //@WebMethod(operationName = "getLov", action = "urn:getLov")
    //@WebResult(name = "lov")
    public Collection<LovDTO> getLov(String lovName) throws EtccException {
    	Collection<LovDTO> col = LovFactory.getStaticLov(lovName);
    	return col;
    }

}
