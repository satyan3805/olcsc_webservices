<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.SessionUtil"%>
<c:set var="tabMenuId">
    <%=SessionUtil.getCurrentTabMenuId(session)%>
</c:set>
<html:form action="/home/updateTabMenu">
    <html:hidden property="url"/>
    <html:hidden property="menu"/>
    <html:hidden property="secure"/>
</html:form>

	<ul id="primary-navigation">
<!--		<li id="home-primary-navigation"><a href="/home.shtml">Home</a></li>
		<li id="my-ez-tag-primary-navigation"><a href="/login/page-01-login.shtml">My EZ TAG</a></li>
		<li id="toll-road-info-primary-navigation"><a href="/toll-road-info/index.shtml">Toll Road Info</a></li>
		<li id="about-hctra-primary-navigation"><a href="/about-hctra/index.shtml">About HCTRA</a></li>
-->
        <c:forEach var="tabMenu" items="${OLCSC_PAGE}" varStatus="varStatus">
            <li id="${tabMenu.itemHelpText}-primary-navigation" 
                <c:if test="${varStatus.index==0}">
                    class="first-child" 
                </c:if>
            >
                <a href='javascript:updateTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>", "<c:out value="${tabMenu.shiftReq}"/>")'
                >
                    <span>
                    <c:out value="${tabMenu.itemLabel}"/>
                    </span>
                </a>
            </li>
        </c:forEach>
	</ul>
        
<script type="text/javascript">
    function updateTab(url, menu, secure) {
            var menuForm = document.tabMenuForm;
            menuForm.url.value = url;
            menuForm.menu.value = menu;
            menuForm.secure.value = secure;
            menuForm.submit();
    }
</script>        
