/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null)
            return null;
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++)
            encodedValues[i] = cleanXSS(values[i]);
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        value = value.replaceAll("&", "&amp;");
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        //value = value.replaceAll("script", "");
        //    value = value.replaceAll("^((ht|f)tp(s?)\\:\\/\\/|~/|/)?([\\w]+:\\w+@)?([a-zA-Z]{1}([\\w\\-]+\\.)+([\\w]{2,5}))(:[\\d]{1,5})?((/?\\w+/)+|/?)(\\w+\\.[\\w]{3,4})?((\\?\\w+=\\w+)?(&\\w+=\\w+)*)?", "/home.do");
        value = value.replaceAll("(?i)script", "");
        return value;
    }
}
