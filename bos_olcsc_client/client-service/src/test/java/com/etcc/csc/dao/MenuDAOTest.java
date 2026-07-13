/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.service.MenuTestImpl;

/**
 * @author Stephen Davidson
 */
public class MenuDAOTest {

    private MenuDAO dao;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dao = new MenuDAOImpl();
    }

//    /**
//     * Test method for {@link MenuDAO#getMenus(String, long, String, String, String)}.
//     * @throws Exception if any errors occur during the test.
//     */
//    @Test
//    public void testGetMenus() throws Exception {
//        MenuItemDTO[] menus = this.dao.getMenus("", 100, "JUnit", "", "JUnit");
//        assertNotNull(menus);
//        //Check that second call does not result in a load.
//        menus = this.dao.getMenus("", 100, "JUnit", "", "JUnit");
//        assertNotNull(menus);
//    }

    /**
     * Test method for {@link MenuDAO#getMenus(com.etcc.csc.dto.MenuItemDTO.MenuCategory)}
     * @throws Exception if any errors occur.
     */
    @Test
    public void testGetMenusByCategory() throws Exception {
        MenuItemDTO[] menus = this.dao.getMenus(MenuItemDTO.MenuCategory.MENU);
        assertNotNull(menus);
        menus = this.dao.getMenus(MenuItemDTO.MenuCategory.OLCSC_LOG);
        assertNotNull(menus);
        //Check that second call does not result in a load.
        menus = this.dao.getMenus(MenuItemDTO.MenuCategory.MENU);
        assertNotNull(menus);
        menus = this.dao.getMenus(MenuItemDTO.MenuCategory.OLCSC_LOG);
        assertNotNull(menus);
    }

    /**
     * Test method for {@link MenuDAO#getMenus(com.etcc.csc.dto.MenuItemDTO.MenuCategory, int)}.
     * 
     * @throws Exception if any errors occur.
     */
    @Test
    public void testGetMenusByParent() throws Exception {
        MenuItemDTO[] menus = this.dao.getMenus(MenuItemDTO.MenuCategory.MENU, 0);
        assertNotNull(menus);
        //Check that second call does not result in a load.
        menus = this.dao.getMenus(MenuItemDTO.MenuCategory.MENU, 0);
        assertNotNull(menus);
    }

    /**
     * DAO Implementation for the test.  Note that a Menu should only be loaded for a User once per VN run.
     */
    static class MenuDAOImpl extends MenuDAO {
        /**
         * Categories of Menus that have been loaded.
         */
        private static final Set<MenuItemDTO.MenuCategory> categories = new TreeSet<MenuItemDTO.MenuCategory>();

//        /** 
//         * @see MenuDAO#loadMenus(String, long, String, String, String)
//         */
//		@SuppressWarnings("deprecation")
//		@Override
//        protected MenuItemDTO[] loadMenus(String sessionId, long accountId, String loginId, String ipAddress,
//                String menuUser) throws EtccException, EtccSecurityException {
//		    if (users.contains(menuUser)){
//		        throw new IllegalArgumentException("Already called for user: " + menuUser);
//		    }//else
//		    users.add(menuUser);
//            final MenuTestImpl impl = new MenuTestImpl();
//            return impl.getMenus(sessionId, accountId, loginId, ipAddress, menuUser);
//        }

		@Override
		protected MenuItemDTO[] loadMenus(String menuUser, MenuItemDTO.MenuCategory category, AccountLoginDTO accountLogin, String ipAddress) throws SQLException {
		    if (categories.contains(category)){
		        throw new IllegalArgumentException("Already called for category: " + category);
		    }//else
            final MenuTestImpl impl = new MenuTestImpl();
            return impl.getMenus(category);
		}

		public MenuItemDTO createObject(Object record) {
            final MenuTestImpl impl = new MenuTestImpl();
            return impl.createObject(record);
		}
    }
}
