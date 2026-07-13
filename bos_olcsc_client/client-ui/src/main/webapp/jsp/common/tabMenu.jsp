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
<html:form action="/home/updateTabMenu">
    <html:hidden property="url"/>
    <html:hidden property="menu"/>
</html:form>
		<ul>
<!--                
			<li><a href="javascript:updateTab('MYACCT', '/testViewStatements.do')"><span>MY ACCOUNT</span></a></li>
			<li class="here"><a href="javascript:updateTab('ACCTHIST', '/testViewStatements.do')"><span>ACCOUNT HISTORY</span></a></li>
			<li><a href="javascript:updateTab('CONTACT', '/testViewStatements.do')"><span>CONTACT US</span></a></li>		
-->
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