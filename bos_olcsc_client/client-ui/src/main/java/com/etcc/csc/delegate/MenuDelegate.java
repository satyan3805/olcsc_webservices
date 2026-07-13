/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.service.MenuInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * UI Delegate for the Menu object.
 */
public class MenuDelegate implements MenuInterface {
    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category) throws NullPointerException {
        return stub().getMenus(category);
    }

    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category, int parentItemId) throws NullPointerException {
        return stub().getMenus(category, parentItemId);
    }

    private MenuInterface stub() {
        return ServiceFactory.getImplementation(MenuInterface.class);
    }
}
