package com.etcc.pen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author jason
 */
public class ETCHttpRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private String remoteAddress;

     
    
    public ETCHttpRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        value = escape(paramName, value);
        return value;
    }

    @Override
    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);
        for (int index = 0; index < values.length; index++) {
            values[index] = escape(paramName, values[index]);
        }
        return values;
    }

    private static String escape(String paramName, String val) {
    	if (paramName.equals("subMenu"))
    		return Sanitizer.santizeForHTMLAttribute(val);
    	
        return Sanitizer.santize(val);
    }

    @Override
    public String getRemoteAddr() {
        if (remoteAddress == null) {
            remoteAddress = super.getRemoteAddr();
            // System.out.println("remoteAddress 1:" + request.getRemoteAddr() + "<");
            // System.out.println("remoteAddress 2:" + remoteAddress + "<");
            String xip = request.getHeader("X-Forwarded-For");
            if (!isGoodIp(xip)) {
                xip = request.getHeader("X_Forwarded_For");
                if (!isGoodIp(xip)) {
                    xip = request.getHeader("Proxy-Client-IP");
                    if (!isGoodIp(xip)) {
                        xip = request.getHeader("CLIENTIP");
                    }
                }
            }
            if (isGoodIp(xip)) {
                remoteAddress = xip;
            }
            //System.out.println("xip 4:" + xip + "<");
        }
        //System.out.println("remoteAddress final:" + remoteAddress + "<");
        return remoteAddress;
    }

    private boolean isGoodIp(String xip) {
        if (xip == null || xip.trim().length() < 8) {
            return false;
        } else {
            return true;
        }
    }
}
