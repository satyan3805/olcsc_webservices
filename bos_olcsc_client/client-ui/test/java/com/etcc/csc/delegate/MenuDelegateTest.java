/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.MenuItemDTO;

/**
 * Tests the MenuDelegate using WebServices.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class MenuDelegateTest {
	private static final Logger logger = Logger.getLogger(MenuDelegateTest.class);

    MenuDelegate delegate;
    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new MenuDelegate();
    }

    /**
     * Test method for {@link MenuDelegate#getMenus(MenuItemDTO.MenuCategory)}.
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testGetMenusByCategory() throws Exception {
        MenuItemDTO[] menus = this.delegate.getMenus(MenuItemDTO.MenuCategory.MENU);
        assertNotNull(menus);
    }

    /**
     * Test method for {@link MenuDelegate#getMenus(MenuItemDTO.MenuCategory, int)}.
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testGetMenusByParentFlat() throws Exception {
    	logger.debug("testGetMenusByParentFlat()");
        // Test a flat menu (with parent in different category)
        MenuItemDTO[] parents = this.delegate.getMenus(MenuItemDTO.MenuCategory.OLCSC_LOG);
        assertNotNull(parents);
        MenuItemDTO[] menus = this.delegate.getMenus(MenuItemDTO.MenuCategory.OLCSC_TTRY);
        assertNotNull(menus);
        for (MenuItemDTO parent : parents) {
        	int parentId = parent.getItemId();
        	MenuItemDTO[] subMenus = this.delegate.getMenus(MenuItemDTO.MenuCategory.OLCSC_TTRY, parentId);
        	logger.debug("MenuDelegate.getMenus() submenu for " + parentId + ": " + Arrays.toString(subMenus));
        	if (subMenus != null)
        		for (MenuItemDTO sm : subMenus)
        			assertEquals(sm.getParentId(), parentId);
        }
    }
    /**
     * Test method for {@link MenuDelegate#getMenus(MenuItemDTO.MenuCategory, int)}.
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testGetMenusByParentHierarchical() throws Exception {
        MenuItemDTO[] menus = this.delegate.getMenus(MenuItemDTO.MenuCategory.MENU);
    	logger.debug(Arrays.toString(menus));
        assertNotNull(menus);
        for (MenuItemDTO parent : menus) {
        	int parentId = parent.getItemId();
        	MenuItemDTO[] subMenus = this.delegate.getMenus(MenuItemDTO.MenuCategory.MENU, parentId);
        	logger.debug("MenuDelegate.getMenus() submenu for " + parentId + ": " + Arrays.toString(subMenus));
        	if (subMenus == null) {
        		assertNull(parent.getMenuItems());
        	} else {
        		logger.trace("submenus.length="+subMenus.length+"; menu.getMenuItemsSize()="+parent.getMenuItemsSize());
        		assertEquals(subMenus.length, parent.getMenuItemsSize());
        		for (MenuItemDTO sm : subMenus)
        			assertEquals(sm.getParentId(), parentId);
        	}
        }
    }

    /**
     * Test method for {@link MenuItemDTO#filterMenus(MenuItemDTO[], int)}
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testMenuItemFilterFlat() throws Exception {
    	logger.debug("testMenuItemFilterFlat()");
        // Test a flat menu (with parent in different category)
        MenuItemDTO[] parents = this.delegate.getMenus(MenuItemDTO.MenuCategory.OLCSC_LOG);
        assertNotNull(parents);
        MenuItemDTO[] menus = this.delegate.getMenus(MenuItemDTO.MenuCategory.OLCSC_TTRY);
        assertNotNull(menus);
        for (MenuItemDTO parent : parents) {
        	int parentId = parent.getItemId();
        	MenuItemDTO[] subMenus = MenuItemDTO.filterMenus(menus, parentId);
        	logger.debug("Filtered submenu for " + parentId + ": " + Arrays.toString(subMenus));
        	if (subMenus != null)
        		for (MenuItemDTO sm : subMenus)
        			assertEquals(sm.getParentId(), parentId);
        }
    }
    /**
     * Test method for {@link MenuItemDTO#filterMenus(MenuItemDTO[], int)}
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testMenuItemFilterHierarchical() throws Exception {
    	logger.debug("testMenuItemFilterHierarchical()");
        // Test a hierarchical menu
        MenuItemDTO[] menuHierarchy = this.delegate.getMenus(MenuItemDTO.MenuCategory.MENU);
        assertNotNull(menuHierarchy);
        for (MenuItemDTO parent : menuHierarchy) {
        	int parentId = parent.getItemId();
        	MenuItemDTO[] subMenus = MenuItemDTO.filterMenus(menuHierarchy, parentId);
        	logger.debug("Filtered submenu for " + parentId + ": " + Arrays.toString(subMenus));
        	if (subMenus != null) {
        		assertEquals(subMenus.length, parent.getMenuItemsSize());
        		for (MenuItemDTO sm : subMenus)
        			assertEquals(sm.getParentId(), parentId);
        	} else {
        		assertNull(parent.getMenuItems());
        	}
        }
    }

    /**
     * Test method for {@link MenuItemDTO#findChildMenuItems(MenuItemDTO[], int)}
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testMenuItemFindChildren() throws Exception {
    	logger.debug("testMenuItemFindChildren()");
        // Test a hierarchical menu
        MenuItemDTO[] menus = this.delegate.getMenus(MenuItemDTO.MenuCategory.MENU);
        assertNotNull(menus);
        for (MenuItemDTO parent : menus) {
        	int parentId = parent.getItemId();
        	MenuItemDTO[] subMenus = MenuItemDTO.findChildMenuItems(menus, parentId);
        	logger.debug("Find Child Menu submenu for " + parentId + ": " + Arrays.toString(subMenus));
        	if (subMenus == null) {
        		assertNull(parent.getMenuItems());
        	} else {
        		assertEquals(subMenus.length, parent.getMenuItemsSize());
        		for (MenuItemDTO sm : subMenus)
        			assertEquals(sm.getParentId(), parentId);
        	}
        }
    }
    
    @Test(expected=NullPointerException.class)
    public void testGetMenusMenuCategoryNPE() throws Exception{
        MenuItemDTO[] menu = this.delegate.getMenus(null);
        assertNotNull(menu);
    }

}
