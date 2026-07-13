<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Problem</title>
  </head>
  <body>
    <div id="error">
<%--        <html:messages id="msg">
            <bean:write name="msg"/>
      </html:messages>
--%>      
      <bean:message arg0="${appDelegate.contactPhoneNumber}" key="error.generic.message"/>
    </div>
  </body>
</html>