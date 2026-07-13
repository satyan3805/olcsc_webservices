<%-- 
  - Author(s): Noel Ternida
  - Date: May 16, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Error page for notLoggedIn layout.
  --%>
  
  
<%@ include file="../common/Taglibs.jsp" %>

<br class="clear">

<table>
    <tr>
        <td>
            <img alt="error" src="${pageContext.request.contextPath}<fmt:message key="images.wallystop"/>" width="100" height="150"/>
        </td>
        <td valign="top">
            <div id="error">
<%--                <html:messages id="msg">
                    <bean:write name="msg"/>
              </html:messages>
--%>           <fmt:message key="error.generic.message"><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>    
            </div>
        </td>
    </tr>
</table>
