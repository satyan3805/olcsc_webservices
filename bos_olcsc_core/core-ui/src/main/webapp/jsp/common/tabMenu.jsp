<%-- 
  - Author(s): Noel Ternida
  - Date: March 27, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: JSP fragment of the menu page.
  --%>

<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.SessionUtil"%>
<c:set var="tabMenuId">
    <%=SessionUtil.getCurrentTabMenuId(session)%>
</c:set>
<html:form action="/updateTabMenu">
    <html:hidden property="url"/>
    <html:hidden property="menu"/>
</html:form>
		<ul>
                        <c:forEach var="tabMenu" items="${tabMenus}">
                            <li
                                <c:if test="${tabMenuId == tabMenu.itemId}">
                                    class="here"
                                </c:if>
                            >
                                <a href='javascript:updateTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>")'><span><c:out value="${tabMenu.itemLabel}"/></span></a>
                            </li>
                        </c:forEach>

		</ul>

<script language="javascript">
    function updateTab(url, menu) {
        var form = document.tabMenuForm;
        form.url.value = url;
        form.menu.value = menu;
        form.submit();
    }
</script>