<%-- 
  - Author(s): Noel Ternida
  - Date: May 16, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Error page for expired session.
  --%>
  
<!--  
<%@ include file="../common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<br class="clear">

<table>
    <tr>
        <td>
            <img alt="error" src="${pageContext.request.contextPath}/images/Wally_stop_Final.jpg" width="100" height="150"/>
        </td>
        <td valign="top">
            <div id="error">
<%--                <html:messages id="msg">
                    <bean:write name="msg"/>
              </html:messages>
--%>              
              <bean:message key="error.session.expired.message"/>
              <br/><br/><br/>
              <a href="${pageContext.request.contextPath}/index.jsp"><img src="${pageContext.request.contextPath}/images/common/buttons/button-login.gif" 
                title="Log in to your account"></a>
            </div>
        </td>
    </tr>
</table>

-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ include file="/jsp/common/Taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>Home &mdash; Harris County Toll Road Authority</title>
        
        <jsp:include page="/jsp/includes/common/head.jsp"/>
</head>

<body id="www-hctra-com">
<div id="page">

        <jsp:include page="/jsp/includes/common/error-expiredSession.jsp"/>

        <jsp:include page="/home/loginDisplayPageMenu.do"/>
        <jsp:include page="/home/loginDisplayTabMenu.do"/>
        <jsp:include page="/jsp/includes/common/footer.jsp"/>
	
</div> <!-- end of content-container -->

</body>
</html>
