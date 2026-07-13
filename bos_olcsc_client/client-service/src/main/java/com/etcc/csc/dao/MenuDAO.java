/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.service.MenuInterface;

/**
 * Contains methods for retrieving and manipulating menus.
 */
//TODO: JMX Bean - load from cache.
public abstract class MenuDAO extends BaseDAO implements MenuInterface, MenuItemDTO.MenuItemBuilder {
    private static final Logger logger = Logger.getLogger(MenuDAO.class);

    private static final String MENU_USER = "OLCSC_USER";

    private static Map<MenuItemDTO.MenuCategory, MenuItemDTO[]> menuCategoryCache = new HashMap<MenuItemDTO.MenuCategory, MenuItemDTO[]>();
    private static Date lastLoad;
    private static Date expires = new Date(); //Expire immediately.

//    @Deprecated
//    protected abstract MenuItemDTO[] loadMenus(String sessionId, long accountId, String loginId, String ipAddress,
//            String menuUser) throws EtccException, EtccSecurityException;

    /**
     * Retrieves menu items for the specified menu user, category and user account.
     * @param menuUser the application user connecting to the DB
     * @param category the menu category requested (can be <tt>null</tt>)
     * @param accountLogin used for authenticating the logged-in user (can be <tt>null</tt>)
     * @param ipAddress IP address of the logged-in user (can be <tt>null</tt>)
     * @return A collection of MenuItemDTO.
     * @throws SQLException if any errors occur while loading the menus
     */
    protected abstract MenuItemDTO[] loadMenus(String menuUser,
    		MenuItemDTO.MenuCategory category,
    		AccountLoginDTO accountLogin, String ipAddress)
    		throws SQLException;

    //Allow lookup of the Menu by user -- not currently needed.
//    public final MenuItemDTO[] getMenus(String sessionId, long accountId, String loginId, String ipAddress,
//            String menuUser) throws EtccException, EtccSecurityException {
//        MenuItemDTO[] menuItems = loadMenus(sessionId, accountId, loginId, ipAddress, menuUser);
//        return menuItems;
//    }

    public final MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category) throws NullPointerException {
        expires = checkAndClearCache(expires, menuCategoryCache);
        MenuItemDTO[] menu = menuCategoryCache.get(category);
        if (menu == null) {
            try {
				if (logger.isDebugEnabled()){
					logger.debug("Menu DAO: Cache was last loaded: " + lastLoad);
					logger.debug("Loading User Menus for user/category: " + MENU_USER + '/' + category);
				}
				menu = loadMenus(MENU_USER, category, null, null);
				if (logger.isTraceEnabled()){
					logger.trace("Loaded User Menus for user/category: " + MENU_USER + '/' + category + '\n' + menu);
				}
				menuCategoryCache.put(category, menu);
				lastLoad = new Date();
            } catch (SQLException e) {
				logger.fatal("Error getting menus for category (" + category + "): " + e.getMessage(), e);
			}
        }
        return menu;
    }

    public final MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category, int parentItemId) throws NullPointerException {
        MenuItemDTO[] menus = getMenus(category);
        return MenuItemDTO.filterMenus(menus, parentItemId);
    }

    /**
     * Build the menus from the SQL array returned by the database.
     * Note: on error (<tt>RuntimeException</tt> or <tt>SQLException</tt>)
     * this method returns an empty array.
     * @param menus a SQL array of objects representing the menu items
     * @return tree of menu items or an empty array on error
     * @see #buildMenus(Object[])
     */
    protected final MenuItemDTO[] buildMenus(Array menus) {
    	if (menus != null)
			try {
				return buildMenus((Object[]) menus.getArray());
    		} catch (SQLException e) {
    			logger.error("Cannot build menu: " + e.getMessage(), e);
			}
    	return new MenuItemDTO[0];
    }

    /**
     * Build the menus from the array of objects.
     * Note: on error (<tt>RuntimeException</tt>) this method returns an empty array.
     * @param menus array of objects representing the menu items
     * @return tree of menu items or an empty array on error
     * @see MenuItemDTO#buildMenus(Object[], MenuItemDTO.MenuItemBuilder)
     */
    protected final MenuItemDTO[] buildMenus(Object[] menus) {
    	if (menus != null)
    		try {
    			return MenuItemDTO.buildMenus(menus, this);
    		} catch(RuntimeException e) {
    			// The error handler screens attempt to build menus
    			// If there's an error in the menu and the exception propagates,
    			// the error screens will cause further errors and result in an infinite loop
    			logger.error("Cannot build menu: " + e.getMessage(), e);
			}
    	return new MenuItemDTO[0];
    }
}
