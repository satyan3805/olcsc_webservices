/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.service.MenuTestImpl;

/**
 * @author Stephen Davidson
 */
public class DummyMenuDAO extends MenuDAO {
//	@Override
//    protected MenuItemDTO[] loadMenus(String sessionId, long accountId, String loginId, String ipAddress,
//            String menuUser) throws EtccException, EtccSecurityException {
//        final MenuTestImpl impl = new MenuTestImpl();
//        return impl.getMenus(sessionId, accountId, loginId, ipAddress, menuUser);
//        //end loadMenus
//
//    }

	@Override
	protected MenuItemDTO[] loadMenus(String menuUser, MenuItemDTO.MenuCategory category, AccountLoginDTO accountLogin, String ipAddress) {
        final MenuTestImpl impl = new MenuTestImpl();
        return impl.getMenus(category);
    }

	public MenuItemDTO createObject(Object o) {
		final MenuTestImpl impl = new MenuTestImpl();
		return impl.createObject(o);
	}
}
