package com.etcc.pen;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jason
 */
public class PenHelper {

    public static boolean secureRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean valid = KeywordFilter.validateInputParameters(req);
        if (!valid) {
        	resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + req.getContextPath() + "/jsp/common/expiredSessionError.jsp");
            //
        	//resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getLocalPort() + "/" + req.getContextPath() + "/jsp/common/expiredSessionError.jsp");
        	return false;
        }
        //addSecurityHeaders(resp);
        //   secureCookie(req, resp, https_flag);
//        removeJSessionFromUrl(resp);
        return true;
    }

    public static void secureResponse(HttpServletRequest req, HttpServletResponse resp, String https_flag) throws IOException {
        addSecurityHeaders(req, resp);
        secureCookie(req, resp, https_flag);
//        removeJSessionFromUrl(resp);
    }

    private static void addSecurityHeaders(HttpServletRequest req, HttpServletResponse resp) {
        //System.out.println("print req uri:" + req.getRequestURI() + " content Type:" + resp.getContentType());
        resp.addHeader("X-Frame-Options", "SAMEORIGIN");
        resp.addHeader("x-xss-protection", "1; mode=block");
        resp.addHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
        String userAgent = req.getHeader("User-Agent").toUpperCase();
        //System.out.println("print useragent:" + userAgent);
        boolean oldIE = false;
        if (userAgent.indexOf("MSIE") > 0) {
            if (userAgent.indexOf("MSIE 8") > 0 || userAgent.indexOf("MSIE 7") > 0 || userAgent.indexOf("MSIE 6") > 0) {
                oldIE = true;
            }
        }
        if (oldIE) {
            resp.addHeader("Cache-Control", "private");
        } else {
            resp.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            resp.addHeader("Pragma", "no-cache");
        }
        resp.setCharacterEncoding("UTF-8");
    }

    public static void secureCookie(HttpServletRequest req, HttpServletResponse resp, String https_flag) {
        //if (resp.containsHeader("SET-COOKIE")) {

        String setCookie = "JSESSIONID=" + req.getSession().getId() + "; Path=" + req.getContextPath()
                + "; HttpOnly";
        if ("Y".equals(https_flag)) {
            setCookie += "; secure";
        }
        resp.addHeader("SET-COOKIE", setCookie);
        //resp.setHeader("SET-COOKIE", setCookie);
        // System.out.println("print HttpOnly setCookie name::" + setCookie);
        //}
    }
    /*      public static void secureCookie(HttpServletRequest req, HttpServletResponse resp, String https_flag) {
        
     Cookie[] cookies = req.getCookies();        if (cookies != null) {
     for (int i = 0; i < cookies.length; i++) {
     boolean cookieChanged = false;
     if ("Y".equals(https_flag) && !cookies[i].getSecure()) {
     cookies[i].setSecure(true);
     cookieChanged = true;
     }
     String name = cookies[i].getName();
     String val = cookies[i].getValue();
     System.out.println("print cookie name::" + name);
     if (val.indexOf("HttpOnly") < 0) {
     if (name.toUpperCase().indexOf("JSESSIONID") >= 0 || name.toUpperCase().indexOf("ORA_WX_SESSION") >= 0
     || name.toUpperCase().indexOf("ORACLE.UIX") >= 0) {
     cookies[i].setValue(val + "; HttpOnly=y");
     cookieChanged = true;
     System.out.println("print HttpOnly cookie name::" + name);
     }
     }
     if (cookieChanged) {
     resp.addCookie(cookies[i]);
     }
     }
     }

     }
     * */

    private static void removeJSessionFromUrl(HttpServletResponse resp) {
        ETCHttpResponse eresp = (ETCHttpResponse) resp;
        String content_loc = eresp.getContentLoc();
        if (content_loc != null) {
            int a = content_loc.indexOf(";jsessionid=");
            if (a >= 0) {
                int b = content_loc.indexOf("?", a + 10);
                if (b >= 0) {
                    content_loc = content_loc.substring(0, a) + content_loc.substring(b);
                } else {
                    content_loc = content_loc.substring(0, a);
                }
                resp.setHeader("Content-Location", content_loc);
            }
        }
    }
}
