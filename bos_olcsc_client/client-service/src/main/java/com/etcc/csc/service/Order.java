/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;

/**
 * Order is the Component that's published for web service order.ws, which provides the following operations:
 * <ul>
 * <li>getOrder
 * <li>getOrders
 * </ul>
 * 
 * @author Wade Wang
 * @since phase 1
 */
@Stateless(name = "com/etcc/Order")
@StatelessDeployment(transactionTimeout = 60)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Order", targetNamespace = "http://ws.csc.etcc.com/Order")
public class Order implements OrderInterface {

    //@WebMethod(operationName = "getOrders", action = "urn:getOrders")
    //@WebResult(name = "orders")
    public Collection<OrderDTO> getOrders(AccountLoginDTO acctLoginDto, boolean pendingOnly) throws EtccException,
            EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        OrderDAO orderDao = daoFactory.getDAO(OrderDAO.class);
        return orderDao.getOrders(acctLoginDto, pendingOnly);
    }

}
