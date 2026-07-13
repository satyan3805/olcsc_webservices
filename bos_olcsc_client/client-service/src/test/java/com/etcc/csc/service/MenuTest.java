/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.service;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.dto.MenuItemDTO.MenuCategory;

/**
 * @author Stephen Davidson
 *
 */
public class MenuTest {

    MenuInterface menuService;

    private static boolean initialized = false;
    
    /**
     * Do the initial class level setup.  Called by the setupBeforeClass method in the child classes.
     * @throws Exception If any exceptions occur during setup.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initialized) {
            SetupFactories.setUpBeforeClass();
            initialized = true;
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.menuService = new Menu();
    }

    /**
     * Test method for {@link com.etcc.csc.service.Menu#getMenus(com.etcc.csc.dto.MenuItemDTO.MenuCategory)}.
     */
    @Test
    public void testGetMenusMenuCategory() throws Exception{
        MenuItemDTO[] menu = this.menuService.getMenus(MenuCategory.MENU);
        assertNotNull(menu);
    }

    @Test(expected=NullPointerException.class)
    public void testGetMenusMenuCategoryNPE() throws Exception{
        MenuItemDTO[] menu = this.menuService.getMenus(null);
        assertNotNull(menu);
    }
    /**
     * Test method for {@link Menu#getMenus(MenuCategory, int)}.
     */
    @Test
    public void testGetMenusMenuCategoryParent() throws Exception {
        Logger.getLogger(MenuTest.class).warn("Not yet implemented");
    }
}
