/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.util.SessionUtil;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class POSLocatorServlet extends HttpServlet {
    private static final long serialVersionUID = -4236312743868459769L;
    private static final Logger logger = Logger.getLogger(POSLocatorServlet.class);
    private String loginUri;

    @Override
    public void init() throws ServletException {
      loginUri = getServletConfig().getInitParameter("loginUri");
      loadLog4j(getServletContext());
    }

    @Override
    public void service(HttpServletRequest request, 
                        HttpServletResponse response) throws ServletException, 
                                                             IOException 
    {
        String requestURI = request.getRequestURI( ).trim( );
        logger.info( "requestURI:" + requestURI );
        Long POSId = new AppDelegate( ).getPOSId( requestURI );
        logger.info( "POSId:" + POSId );
        SessionUtil.setPosId( request, response, POSId );
        request.getRequestDispatcher( loginUri ).forward(request, response);
    }

    private void loadLog4j(ServletContext context) {
           ClassLoader loader = Thread.currentThread().getContextClassLoader();
           PropertyConfigurator.configure(loader.getResource("log4j.properties"));
           logger.info("loadLog4j");
       }
}
