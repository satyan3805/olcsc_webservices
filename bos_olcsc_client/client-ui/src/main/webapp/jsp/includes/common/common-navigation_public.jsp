<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.SessionUtil"%>
<%@ page import="org.apache.log4j.Logger"%>

<div id="common-navigation">
<%--
	<ul>
		<li id="find-a-store-common-navigation"><a href="#">Find a Store</a></li>
		<li id="contact-us-common-navigation"><a href="#">Contact Us</a></li>
		<li id="logged-out-common-navigation">Already have an EZ Tag? <a href="/login/">Login</a></li>
	</ul>
--%>
<%
try {
    //TODO: Fix exception handling page (as defined in struts.xml).
    // Currently, when an exception occurs here, the App Server goes into an infinite loop
    // and eventually runs out of memory.
    // This has been throwing exceptions during exception handling, so just catch and log.
    %>
	<ul>
        <c:forEach var="tabMenu" items="${tabMenus}">
             <c:choose>
                <c:when test="${!(tabMenu.itemHelpText=='LOGGED-OUT') or (empty sessionScope.acctLoginInfo) or (acctLoginInfo.loginTypeString != 'AC')}">
                    <li id="${fn:toLowerCase(tabMenu.itemHelpText)}-common-navigation">
                        <a href='javascript:updateTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>", ${tabMenu.itemId})'><c:out value="${tabMenu.itemLabel}"/></a>
                    </li>
                </c:when>
                <c:otherwise>
<%-- TODO: investigate this as it keeps throwing ServletException
tiles.InsertTag   (InsertTag.java:922)     - ServletException in '/home/loginDisplayTabMenu.do': unexpected invocation
javax.servlet.ServletException: unexpected invocation
	at com.evermind.server.http.EvermindPageContext.handlePageThrowable(EvermindPageContext.java:899)
	at com.evermind.server.http.EvermindPageContext.handlePageException(EvermindPageContext.java:816)
	at _jsp._includes._common._common_2d_navigation__public._jspService(_common_2d_navigation__public.java:284)
 --%>
                    <li id="logged-in-common-navigation" class="last-child">
                        Logged in as
                        <em>
                            <c:if test="${not empty acctInfo}">
								<etcc-extended:format accountName="${acctInfo}" maxNameLength="15" />
                            </c:if>
                        </em>
                        <a href='javascript:updateTab("/logout.do", "<c:out value="${tabMenu.itemId}"/>", ${tabMenu.itemId})'>LOG OUT</a>
                    </li>
                </c:otherwise>
             </c:choose>
        </c:forEach>
	</ul>
<%
} catch (javax.servlet.ServletException e) {
    Logger.getLogger(getClass()).debug("Common Nav Error.", e);
    e.printStackTrace();
    throw e;
} catch (RuntimeException e) {
    Logger.getLogger(getClass()).debug("Common Nav Error.", e);
    e.printStackTrace();
    throw e;
}
%>
        <jsp:include page="/jsp/includes/common/logo.jsp"/>


</div> <!-- end of common-navigation -->
