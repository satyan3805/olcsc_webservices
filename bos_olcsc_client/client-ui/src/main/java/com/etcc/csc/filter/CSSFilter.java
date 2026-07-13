/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Filters the requests that go through the application to prevent cross site scripting.
 */
public final class CSSFilter implements Filter {
    private FilterConfig filterConfig = null;
    private ArrayList<String> specialCharacters;
    private static final Logger logger = Logger.getLogger(CSSFilter.class);
    private String redirectPage = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        redirectPage = filterConfig.getInitParameter("RedirectPage");
        @SuppressWarnings("unchecked")
        Enumeration<String> en = filterConfig.getInitParameterNames();
        specialCharacters = new ArrayList<String>();
        while(en.hasMoreElements()) {
          String paramName = en.nextElement();
          String paramValue = filterConfig.getInitParameter(paramName);
          if( paramName.startsWith("Character")) {
            specialCharacters.add(paramValue);
          }
        }        
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
        if (filterConfig != null) {
        	HttpServletRequest httpReq = (HttpServletRequest) request;
        	if(isSecure(httpReq)) {
        		//  chain.doFilter(request, response);
        		logger.trace("entering XSS filter");
        		chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
        		logger.trace("exiting XSS filter");
        	} else {
        		//httpReq.getRequestDispatcher(redirectPage).forward(request, response);
        		httpReq.getSession().getServletContext().getRequestDispatcher(redirectPage).forward(request, response);
        	}
        }
    }

    private boolean isSecure(HttpServletRequest request) {
    	for (String specialChar : specialCharacters) {
    	    @SuppressWarnings("unchecked")
    		Enumeration<String> formVarNames = request.getParameterNames();
    		while (formVarNames.hasMoreElements()) {
    			String formVarName = formVarNames.nextElement();
    			String formVarVal = request.getParameter(formVarName);
    			if ((formVarVal.indexOf(specialChar)) != -1) {
    				if(!((specialChar.equals("(") || specialChar.equals(")")) && (formVarName.contains("iopPreference[") || formVarName.contains("userPreference[")))) {
    					logger.error("***Insecure input for " + formVarName + ".");
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }    
}
