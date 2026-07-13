/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.dto.MenuItemDTO;

/**
 * Defines methods that need to be implemented by the Menu classes.
 * From the original OLSCSService project.
 * @author Milosh Boroyevich
 * @author Stephen Davidson
 */
@Local
public interface MenuInterface extends ServiceInterface {
    /**
     * Retrieves the available menus given a menu category.  Note: returns the
     * same result as invoking {@link #getMenus(MenuItemDTO.MenuCategory, int)}
     * with a parent id of <i>0</i>.
     * @param category menu category to retrieve
     * @return The menu items for the category (never <tt>null</tt>)
     * @throws NullPointerException if category is <tt>null</tt>
     * @see #getMenus(MenuItemDTO.MenuCategory, int)
     */
    MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category) throws NullPointerException;

    /**
     * Retrieves all menus with the specified parent menu id within the
     * specified category, regardless of whether the parent exists in the
     * specified category.
     * Note: a parent id with value <= 0 will return all menus in the entire category.
     * @param category menu category to retrieve
     * @param parentItemId used to get the sub-menu within the specified category
     * @return The menu items for the parent within the category (never <tt>null</tt>)
     * @throws NullPointerException if category is <tt>null</tt>
     * @see #getMenus(MenuItemDTO.MenuCategory)
     * @see MenuItemDTO#findChildMenuItems(MenuItemDTO[], int)
     */
    MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category, int parentItemId) throws NullPointerException;
}
