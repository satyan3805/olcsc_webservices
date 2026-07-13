package com.etcc.csc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.UserEnvDTO;

public class HttpDataUtil {
	private static final Logger logger = Logger.getLogger(HttpDataUtil.class);
	/**
	 * Constructor.  Should not be instantiated.
	 */
    private HttpDataUtil() {
    }

    /**
     * Retrieves the system information of a user.
     * @param request
     * @return Returns a UserEnvDTO containing the user info.
     */
    public static UserEnvDTO getUserAgentAttributes(HttpServletRequest request) {
        UserEnvDTO userEnvDto = new UserEnvDTO();
        String userEnv = request.getHeader("USER-AGENT");
        
        logger.debug( userEnv );

        StringTokenizer st = new StringTokenizer(userEnv, "();");
        ArrayList<String> attributes = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            attributes.add(st.nextToken().toString().trim());
        }
        userEnvDto.setAttribute1(userEnv);
        if (attributes.size() > 1) {
            userEnvDto.setAttribute2(attributes.get(1));
        }
        if (attributes.size() > 2) {
            userEnvDto.setAttribute3(attributes.get(2));
        }
        if (attributes.size() > 3) {
            userEnvDto.setAttribute4(attributes.get(3));
        }
        if (attributes.size() > 4) {
            userEnvDto.setAttribute5(attributes.get(4));
        }

        if (userEnv.indexOf("MSIE") > 0) {
            // Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)
            // process IE
            userEnvDto.setBrowserType(attributes.get(2));
            userEnvDto.setBrowserVersion(attributes.get(2));
            userEnvDto.setOsType(attributes.get(3));
            userEnvDto.setOsVersion(attributes.get(3));
        } else if (userEnv.indexOf("Firefox") > 0) {
            // process firefox
            // Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.0.1) Gecko/20060111 Firefox/1.5.0.1
            userEnvDto.setBrowserType("Firefox");
            if (attributes.size() > 6) 
            {
              userEnvDto.setBrowserVersion(attributes.get(6));
            }
            userEnvDto.setOsType(attributes.get(3));
            userEnvDto.setOsVersion(attributes.get(3));
        }
        return userEnvDto;
    }

    public static void setDbSessionIdCookie(HttpServletResponse response, 
                                            String value) {
        try {
            if (response != null) {
                Cookie cookie = new Cookie("OLCSC", value);
                cookie.setSecure(new AppDelegate().secureCookies());
                cookie.setPath("/eztagstore");
                response.addCookie(cookie);
            }
        } catch (EtccException e) {
            e.printStackTrace();
        }
    }

    public static void removeCookies(HttpServletResponse response) {
        if (response != null) {
            Cookie dbSessionCookie = new Cookie("OLCSC", "");
            dbSessionCookie.setMaxAge(0);
            response.addCookie(dbSessionCookie);

            Cookie sessionCookie = new Cookie("JSESSIONID", "");
            sessionCookie.setMaxAge(0);
            response.addCookie(sessionCookie);
            
            Cookie acctIdCookie = new Cookie("UserInfo", "");
            acctIdCookie.setPath("/");
            acctIdCookie.setMaxAge(0);
            response.addCookie(acctIdCookie);
        }
    }

    public static String getDbSessionIdCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie i: cookies) {
                if (i.getName().equals("OLCSC")) {
                    return i.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Gets the IP Address of the Client.
     * @param request The HTTP Request from the client.
     * @return The IP address the client.
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("CLIENTIP");
        if (clientIp != null && clientIp.trim().length() > 0){
            String forwardedIp = request.getHeader("X-FORWARDED-FOR");
            if (!clientIp.equals(forwardedIp)){
                logger.error("Forwarded & ClientIP Addresses do not match: " +
                		"\nX-FORWARDED-FOR = " + forwardedIp +
                		"\nCLIENTIP = " + clientIp);
            }
            return clientIp;
        } //else 
        String forwardedIp = request.getHeader("X-FORWARDED-FOR");
        if (forwardedIp != null && forwardedIp.trim().length() > 0){
            logger.error("Misconfigured forwarder/load-balancer - CLIENTIP not set");
            return forwardedIp;
        }//else
        logger.info("Direct connect from: " + request.getRemoteAddr());
        return request.getRemoteAddr();
    }
    
    public static void setUserInfoCookie(HttpServletResponse response, 
                                            String value) {
        try {
            if (response != null) {
                Cookie cookie = new Cookie("UserInfo", value);
                cookie.setSecure(new AppDelegate().secureCookies());
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        } catch (EtccException e) {
            e.printStackTrace();
        }
    }
    
    public static String createUserInfoCookie(AccountDTO acctDTO) {
        String delimiter = "|";
        StringBuilder sb =  new StringBuilder(acctDTO.getFirstName()==null?"":acctDTO.getFirstName());
        sb.append(" ");
        sb.append(acctDTO.getLastName()==null?"":acctDTO.getLastName());
        sb.append(delimiter);
        sb.append(acctDTO.getCompanyName()==null?"":acctDTO.getCompanyName());
        sb.append(delimiter);
        sb.append(acctDTO.getAcctId());
        sb.append(delimiter);
        //sb.append(StringUtil.currencyFormat.format(acctDTO.getBalanceAmt()));
        sb.append(StringUtil.decimalFormat.format(acctDTO.getBalanceAmt())); 
       // sb.append(acctDTO.getBalanceAmt());
        sb.append(delimiter);
        sb.append(UIDateUtil.getISO8061Date(new Date()));
        sb.append(delimiter);
        sb.append(acctDTO.getEmailAddress());
        
        return sb.toString();
    }
}
