/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.filter;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.delegate.DbLoggerDelegate;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Filters the requests that go through the application to see if the user is
 * already logged in or not.
 */
public final class AuthFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    private FilterConfig filterConfig = null;
    private String loginUri;
    private String expiredSessionUri;
    private String confirmPrimaryEmailUri;
    private String mailedTagActivationUri;
    private String mailedTagActivationDetailUri;

    private LinkedList<Pattern> excludePatterns;
    private DbLoggerDelegate dbLoggerDel;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.loginUri = filterConfig.getInitParameter("loginUri");
        this.expiredSessionUri = filterConfig.getInitParameter("expiredSessionUri");
        this.confirmPrimaryEmailUri = filterConfig.getInitParameter("confirmPrimaryEmailUri");
        this.mailedTagActivationUri = filterConfig.getInitParameter("mailedTagActivationUri");
        this.mailedTagActivationDetailUri = filterConfig.getInitParameter("mailedTagActivationDetailUri");
        @SuppressWarnings("unchecked")
        Enumeration<String> en = filterConfig.getInitParameterNames();
        this.excludePatterns = new LinkedList<Pattern>();
        while (en.hasMoreElements()) {
            String paramName = en.nextElement();
            if (paramName.startsWith("excludePattern")) {
                String paramValue = filterConfig.getInitParameter(paramName);
                // compile the pattern only this once
                Pattern excludePattern = Pattern.compile(paramValue);
                this.excludePatterns.add(excludePattern);
            }
        }
        this.dbLoggerDel = new DbLoggerDelegate();
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (logger.isTraceEnabled()) {
            logger.trace("***************************************");
            logger.trace("Request Headers:");
            
            HttpServletRequest httpRequest = (HttpServletRequest) request;    
            HttpSession session = httpRequest.getSession(true);
            
            for (@SuppressWarnings("unchecked")
            Enumeration<String> e = httpRequest.getHeaderNames(); e.hasMoreElements();) {
                String header = e.nextElement();
                logger.trace(header + '=' + httpRequest.getHeader(header));
            }
            logger.trace("***************************************");
            logger.trace("REQUEST_URI=" + httpRequest.getRequestURI());
            // TODO: remove for release build
            //logger.trace("!!!REMOVE THIS LOG!!! Request Parameters=" + request.getParameterMap());
        }

        if (this.filterConfig == null)
            return;

        HttpServletRequest httpReq = (HttpServletRequest) request;
        String requestUri = httpReq.getRequestURI();
        if (!(requestUri.endsWith(this.mailedTagActivationDetailUri)
                || requestUri.endsWith(this.mailedTagActivationUri)
                || requestUri.endsWith(this.confirmPrimaryEmailUri))) {
            if (!isFilteredRequest(httpReq)) {
                chain.doFilter(request, response);
                return;
            }
            if (!requestUri.endsWith(this.loginUri)) {
                try {
                    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(httpReq.getSession(true));
                    if (SessionUtil.isSessionExpired(httpReq)) {
                        httpReq.getRequestDispatcher(this.expiredSessionUri).forward(request, response);
                    } else if (!acctLoginDto.getLastLoginIp().equals(HttpDataUtil.getClientIpAddress(httpReq))) {

                        final String msg = "IP address is different for this session\n"
                                + "orig ip/new ip/acct id/session id: " + acctLoginDto.getLastLoginIp() + '/'
                                + HttpDataUtil.getClientIpAddress(httpReq) + '/' + acctLoginDto.getAcctId() + '/'
                                + httpReq.getSession().getId();

                        // TODO: Either use a Database Logger as an Appender(preferred), or run everything
                        // through DBLogger.
                        logger.info(msg);
                        this.dbLoggerDel.logSecurityViolation(msg);

                        httpReq.getRequestDispatcher(this.loginUri).forward(request, response);
                    } else {
                        // validate db session id
                        String cookieDbSessionId = HttpDataUtil.getDbSessionIdCookie(httpReq);
                        if (cookieDbSessionId == null || !cookieDbSessionId.equals(acctLoginDto.getDbSessionId())) {

                            String msg = "DbSessionId is different for this session\n"
                                    + "ip/acct id/session id/cookieDbSessionId/sysDbSessionId: "
                                    + acctLoginDto.getLastLoginIp() + '/' + acctLoginDto.getAcctId() + '/'
                                    + httpReq.getSession().getId() + '/' + cookieDbSessionId + '/'
                                    + acctLoginDto.getDbSessionId();

                            // TODO: Either use a Database Logger as an Appender(preferred), or run everything
                            // through DBLogger.
                            logger.info(msg);
                            this.dbLoggerDel.logSecurityViolation(msg);

                            httpReq.getRequestDispatcher(this.loginUri).forward(request, response);
                        }
                    }
                } catch (Exception e) {
                    // Very bad place for an exception to occur, so catch them here, and log them.
                    logger.info("Error validating account login.", e);
                    httpReq.getRequestDispatcher(this.loginUri).forward(request, response);
                }
            }
        }// end of if (!....
        chain.doFilter(request, response);
    }

    private boolean isFilteredRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        Iterator<Pattern> patternIter = this.excludePatterns.iterator();
        while (patternIter.hasNext()) {
            Pattern p = patternIter.next();
            Matcher m = p.matcher(path);
            if (m.find()) {
                return false;
            }
        }
        return true;
    }
}
