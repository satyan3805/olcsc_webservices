package com.etcc.csc.util;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * Wrapper class for managing cache.
 */
public class CacheAdmin {
    private static GeneralCacheAdministrator admin;
    private static CacheAdmin cacheAdmin;

    private CacheAdmin() {
        admin = new GeneralCacheAdministrator(
            PropertyUtil.loadProperties("oscache.properties"));
    }
    
    public static synchronized CacheAdmin getInstance() {
        if (cacheAdmin == null) {
            cacheAdmin = new CacheAdmin();
        }
        return cacheAdmin;
    }
    
    public Object getFromCache(String key) {
        try {
            return admin.getFromCache(key);
        } catch (NeedsRefreshException nfe) {
            return null;
        }
    }
    
    public Object getFromCache(String key, int refreshPeriod) {
        try {
            return admin.getFromCache(key, refreshPeriod);
        } catch (NeedsRefreshException nfe) {
            return null;
        }
    }
    public Object getFromCache(String key, int refreshPeriod, 
        String cronExpression) {
            
        try {
            return admin.getFromCache(key, refreshPeriod, cronExpression);
        } catch (NeedsRefreshException nfe) {
            return null;
        }
    }
    public void putInCache(String key, Object content) {
        admin.putInCache(key, content);
    }
    public void removeEntry(String key) {
        admin.removeEntry(key);
    }
    public void cancelUpdate(String key) {
        admin.cancelUpdate(key);
    }
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(); 
    }
}
