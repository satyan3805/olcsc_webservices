<%-- 
  - Author(s): Noel Ternida
  - Date: May 16, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Error page for expired session.
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
--%>              
              <bean:message key="error.session.expired.message"/>
              <br/><br/><br/>
              <a href="${pageContext.request.contextPath}/index.jsp"><img src="${pageContext.request.contextPath}<fmt:message key="images.loginbutton"/>" 
                title="<bean:message key="label.login"/>"></a>
            </div>
        </td>
    </tr>
</table>
