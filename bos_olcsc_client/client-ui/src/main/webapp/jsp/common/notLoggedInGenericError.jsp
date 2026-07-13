<%-- 
  - Author(s): Noel Ternida
  - Date: May 16, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Error page for notLoggedIn layout.
  --%>
  
  
<%@ include file="../common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<%--
<br class="clear">

<table>
    <tr>
        <td>
            <img alt="error" src="${pageContext.request.contextPath}/images/Wally_stop_Final.jpg" width="100" height="150"/>
        </td>
        <td valign="top">
            <div id="error">
  
              <bean:message arg0="${appDelegate.contactPhoneNumber}" key="error.generic.message"/>
            </div>
        </td>
    </tr>
</table>
--%>

<div id="content-container">
    <div id="content">
    
        <div id="error">
              <img alt="error" src="${pageContext.request.contextPath}/images/Wally_stop_Final.jpg" width="100" height="150"/>
              <bean:message arg0="${appDelegate.contactPhoneNumber}" key="error.generic.message"/>
        </div>
    </div>
</div>        
