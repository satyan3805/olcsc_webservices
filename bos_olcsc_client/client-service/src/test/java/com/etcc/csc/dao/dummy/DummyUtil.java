/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.HashMap;
import java.util.Map;

/**
 * Dummy utility class.
 * @author Milosh Boroyevich
 */
public class DummyUtil {
    private DummyUtil() { }

    /**
     * Returns the DB type mappings.
     * Used reflexively by DAO Factory.
     * @return the DB type mappings
     * @see com.etcc.csc.dao.DAOFactory#invokeUtility(String)
     */
    public static Map<String, Class<?>> getDbTypeMap() {
        Map<String, Class<?>> theTypeMap = new HashMap<String, Class<?>>();
        theTypeMap.put("DUMMY_UTIL", DummyUtil.class);
        return theTypeMap;
    }
}
