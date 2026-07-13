/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import javax.servlet.http.HttpServletRequest;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AppDelegate;

/**
 * SSL utility class.
 */
public class SslUtil {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HTTP_PORT_PARAM = "listenPort_http";
    public static final String HTTPS_PORT_PARAM = "listenPort_https";
    private static String HTTP_PORT = null;
    private static String HTTPS_PORT = null;
    private static final String STD_HTTP_PORT = "80";
    private static final String STD_HTTPS_PORT = "443";
    private static  String SERVER_NAME = null;

   
    static public String getRedirectString(HttpServletRequest request,
            boolean isSecure, String actionUrl) throws EtccException{
            
        initializeSchemePorts();

        // get the scheme we want to use for this page and
        // get the scheme used in this request
        String desiredScheme = isSecure? HTTPS: HTTP;
        String usingScheme = request.getScheme();
        String previousDesiredScheme = SessionUtil.getProtocal(request.getSession());
        
        if (previousDesiredScheme == null)
            previousDesiredScheme = HTTP;

        // Determine the port number we want to use
        // and the port number we used in this request
        String desiredPort = isSecure? HTTPS_PORT: HTTP_PORT;
        String usingPort = String.valueOf(request.getServerPort());
        
        System.out.println("desiredScheme:" + desiredScheme);
        System.out.println("previousDesiredScheme:" + previousDesiredScheme);
        System.out.println("usingScheme:" + usingScheme);
        System.out.println("desiredPort:" + desiredPort);
        System.out.println("usingPort:" + usingPort);

        String urlString = null;

        // Must also check ports, because of IE multiple redirect problem
        //TODO: test test test!!!!!! removed port comparison for hctra. 
//        if ( !desiredScheme.equals(previousDesiredScheme) /*|| !desiredPort.equals(usingPort)*/) {
//
//            urlString = buildNewUrlString(request,
//                    desiredScheme,
//                    usingScheme,
//                    desiredPort,
//                    usingPort,
//                    actionUrl);
                    
            urlString = buildUrlString(request,
                      desiredScheme,
                      usingScheme,
                      desiredPort,
                      usingPort,
                      actionUrl);
                      
            SessionUtil.setProtocal(request.getSession(),desiredScheme);
//            // Temporarily store attributes in session
//            RequestUtil.stowRequestAttributes(request);
//          }else{
////            // Retrieve attributes from session
////            RequestUtil.reclaimRequestAttributes(request);
//        }
        System.out.println("urlString:" + urlString);
        return urlString;        
            
    }


    /**
     * Initializes the port numbers based on the port init parameters as defined
     * in web.xml
     */
    private static void initializeSchemePorts() throws EtccException{

        AppDelegate appDel = new AppDelegate();
        if( HTTP_PORT == null){
            String portNumber = appDel.getHttpPort();
            HTTP_PORT = (portNumber == null? STD_HTTP_PORT: portNumber);
        }
        if( HTTPS_PORT == null){
            String portNumber = appDel.getHttpsPort();
            HTTPS_PORT = (portNumber == null? STD_HTTPS_PORT: portNumber);
        }
        
        if (SERVER_NAME == null){
           SERVER_NAME = appDel.getDomainName();
        }
    }

    
    /**
     * Builds the URL that we will redirect to
     */
    private static String buildUrlString (HttpServletRequest request,
            String desiredScheme,
            String usingScheme,
            String desiredPort,
            String usingPort,
            String actionUrl){
            
        StringBuilder sb = new StringBuilder();
        sb.append(desiredScheme);
        sb.append("://");
        sb.append(SERVER_NAME);
        
        if((!(STD_HTTPS_PORT.equals(desiredPort) && HTTPS.equals(desiredScheme)))
                && (!(STD_HTTP_PORT.equals(desiredPort) && HTTP.equals(desiredScheme)))){
            sb.append(":");
            sb.append(desiredPort);
        }
        
        sb.append(request.getContextPath());
        sb.append(actionUrl);
        
       return sb.toString();     
    }
    
    
//    /**
//     * Builds the URL that we will redirect to
//     */
//    private static String buildNewUrlString(HttpServletRequest request,
//            String desiredScheme,
//            String usingScheme,
//            String desiredPort,
//            String usingPort,
//            String actionUrl){
//
//        StringBuilder url = HttpUtils.getRequestURL(request);
//        
//        
//        url.replace(0, usingScheme.length(), desiredScheme );
//
//        // Find the port used within the URL string
//        int startIndex = url.toString().indexOf(usingPort);
//
//        if( startIndex == -1 ){  // Port not found in URL
//            if((!(STD_HTTPS_PORT.equals(desiredPort) && HTTPS.equals(desiredScheme)))
//                    && (!(STD_HTTP_PORT.equals(desiredPort) && HTTP.equals(desiredScheme)))){
//                startIndex = url.toString().indexOf("/", url.toString().indexOf("/", url.toString().indexOf("/")+1)+1);
//                url.insert(startIndex, ":" + desiredPort);
//            }
//        }else{  // Port found in URL
//            if((STD_HTTPS_PORT.equals(desiredPort) && HTTPS.equals(desiredScheme))
//                    || (STD_HTTP_PORT.equals(desiredPort) && HTTP.equals(desiredScheme))){
//                url.delete(startIndex-1, startIndex + usingPort.length());
//            } else {  // desired port is not a default port
//                // Replace requested port with desired port number in URL string
//                url.replace(startIndex, startIndex + usingPort.length(), desiredPort );
//            }
//        }
//        
//        int lastSlashIndex = url.indexOf("/home/");
//        if (lastSlashIndex == -1)
//            lastSlashIndex = url.lastIndexOf("/");
//        if (lastSlashIndex != -1)
//            url.replace(lastSlashIndex, url.length(),actionUrl);
//        
//
//////         add query string, if any
////        String queryString = request.getQueryString();
////        if (queryString != null && queryString.length() != 0 ) {
////            url.append("?"+queryString);
////        }else{
////            queryString =  RequestUtil.getRequestParameters( request );
////            if (queryString != null && queryString.length() != 0 ) {
////                url.append("?"+queryString);
////            }
////        }
//
//        return url.toString();
//    }
    
}
