package com.etcc.csc.util;

import org.apache.struts.action.DynaActionForm;

public final class ActionFormUtil {

    /**
     * Restrict public access
     */
    private ActionFormUtil() {
    }


    public static String getString(DynaActionForm form, String propertyName) {
        Object value = form.get(propertyName);
        return (value == null) ? null : (String)value;
    }

    public static boolean getBoolean(DynaActionForm form, 
                                     String propertyName) {
        Object value = form.get(propertyName);
        return (value == null) ? false : ((Boolean)value).booleanValue();
    }

}
