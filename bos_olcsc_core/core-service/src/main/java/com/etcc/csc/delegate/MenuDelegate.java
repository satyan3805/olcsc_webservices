package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.service.MenuInterface;

import java.util.Collection;


public class MenuDelegate extends Delegate implements MenuInterface {
    
    MenuInterface menu = (MenuInterface)getServiceObject(ServiceObjectEnum.MENU);
    
    public MenuDelegate() {
        super(MenuDelegate.class);
    }

    public Collection getMenus(String sessionId, long accountId, String loginId,
        String ipAddress, String menuUser, String locale) throws EtccException, 
        EtccSecurityException {
        
        try {
           return menu.getMenus(sessionId, accountId, loginId,
                ipAddress, menuUser, locale);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getMenus: " + t, t);
        }
    }

    public Collection getMenusByCategory(AccountLoginDTO acctLoginDto, 
        String menuUser, String category) throws EtccException, 
        EtccSecurityException {
        
        try {
            
            return menu.getMenusByCategory(acctLoginDto, menuUser, category);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getMenusByCategory: " 
                + t, t);
        }
    }
    
    public Collection getMenusByCategoryLocale(AccountLoginDTO acctLoginDto, 
        String menuUser, String category, String locale) throws EtccException, 
        EtccSecurityException {
        
        try {
            
            return menu.getMenusByCategoryLocale(acctLoginDto, menuUser, category, 
                                locale);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getMenusByCategory: " 
                + t, t);
        }
    }

    public Collection getMenusByParent(AccountLoginDTO acctLoginDto, 
        String menuUser, String category, long parentItemId) 
        throws EtccException, EtccSecurityException {
        
        try {
                        
            return menu.getMenusByParent(acctLoginDto, menuUser, category, parentItemId);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getMenusByParent: " + t, t);
        }
    }
    
    public Collection getMenusByParentLocale(AccountLoginDTO acctLoginDto, 
        String menuUser, String category, long parentItemId, String locale) 
        throws EtccException, EtccSecurityException {
        
        try {
            
            return menu.getMenusByParentLocale(acctLoginDto, 
                                    menuUser, category, parentItemId, locale);
            
        } catch (Throwable t) {
            throw new EtccException("Error running getMenusByParent: " + t, t);
        }
    }
    
   
}