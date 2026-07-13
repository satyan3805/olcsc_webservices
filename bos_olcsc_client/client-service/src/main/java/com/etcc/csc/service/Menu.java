/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dto.MenuItemDTO;

/**
 * Menu is the Component that's published for web service menu.ws, which provides the following operations:
 * <ul>
 * <li>getMenus
 * <li>getMenusByParent
 * </ul>
 * 
 * @author Wade Wang
 * @author Milosh Boroyevich
 * @author Stephen Davidson
 * @since phase 1
 */
@Stateless(name="com/etcc/Menu")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Menu", targetNamespace = "http://ws.csc.etcc.com/Menu")
public class Menu implements MenuInterface {
    private static final Logger logger = Logger.getLogger(Menu.class);

    //@WebMethod(operationName = "getMenusByCategory", action = "urn:getMenusByCategory")
    //@WebResult(name = "menus")
    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category) throws NullPointerException {
        MenuDAO menuDao = getMenuDao();
        return (menuDao == null ? new MenuItemDTO[0] : menuDao.getMenus(category));
    }

    //@WebMethod(operationName = "getMenusByParent", action = "urn:getMenusByParent")
    //@WebResult(name = "menus")
    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category, int parentItemId) throws NullPointerException {
        MenuDAO menuDao = getMenuDao();
        return (menuDao == null ? new MenuItemDTO[0] : menuDao.getMenus(category, parentItemId));
    }

    private MenuDAO getMenuDao() {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        try {
            return daoFactory.getDAO(MenuDAO.class);
        } catch (EtccSysException e) {
            logger.fatal("System error retrieving menus: " + e.getMessage(), e);
        }
        return null;
    }
}
