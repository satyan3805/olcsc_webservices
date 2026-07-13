package com.etcc.pen;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author jason
 */
public class ETCHttpResponse extends HttpServletResponseWrapper {

    public ETCHttpResponse(ServletResponse response) {
        super((HttpServletResponse) response);
    }
    private String contentLoc = null;

    @Override
    public void setHeader(String name, String value) {
//        System.out.println("response header name::" + name + ">value:" + value);
//        if (name.indexOf("Content-Location") >= 0) {
//            System.out.println("set Content-Location::" + value);
//            contentLoc = value;
//        }
        super.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
    }

    @Override
    public void setCharacterEncoding(String enc) {
        super.setCharacterEncoding(enc);
    }

    @Override
    public void addCookie(Cookie c) {
        super.addCookie(c);
    }

    public String getContentLoc() {
        return contentLoc;
    }

    @Override
    public String encodeURL(String url) {
        String tmp = super.encodeURL(url);
        tmp = removeJSessionFromUrl(tmp);
        return tmp;
    }

    @Override
    public String encodeRedirectURL(String url) {
        String tmp = super.encodeRedirectURL(url);
        tmp = removeJSessionFromUrl(tmp);
        return tmp;
    }

    @Override
    public String encodeUrl(String url) {
        String tmp = super.encodeUrl(url);
        tmp = removeJSessionFromUrl(tmp);
        return tmp;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        String tmp = super.encodeRedirectUrl(url);
        tmp = removeJSessionFromUrl(tmp);
        return tmp;
    }

    private String removeJSessionFromUrl(String content_loc) {
        if (content_loc != null) {
            int a = content_loc.indexOf(";jsessionid=");
            if (a >= 0) {
                int b = content_loc.indexOf("?", a + 10);
                if (b >= 0) {
                    content_loc = content_loc.substring(0, a) + content_loc.substring(b);
                } else {
                    content_loc = content_loc.substring(0, a);
                }
            }
        }
        return content_loc;
    }
}
