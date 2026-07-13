<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252" isErrorPage="true" import="java.io.CharArrayWriter, java.io.PrintWriter"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title><bean:message key="error.generic.pagetitle"/></title>
  </head>
  <body>
    <div id="error">
        <%--  <html:messages id="msg"> <bean:write name="msg"/> </html:messages> --%>  
        <fmt:message key="error.generic.message">
            <fmt:param>
                <etcc-extended:Translation property="contactPhoneNumber"/>
            </fmt:param>
        </fmt:message> 
    </div>
    
    <% if (exception != null) {
             CharArrayWriter charArrayWriter = new CharArrayWriter(); 
             PrintWriter printWriter = new PrintWriter(charArrayWriter, true); 
             exception.printStackTrace(printWriter); 
             out.println("<!-- " + charArrayWriter.toString() + "--> <br/>");
     }  %>
  </body>
</html>