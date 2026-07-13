<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@page import="com.etcc.csc.util.SessionUtil"%>
<c:set var="acctMgmtTabMenuId">
    <%=SessionUtil.getCurrentAcctMgmtTabMenuId(session)%>
</c:set>

<html:form action="/updateAcctMgmtTabMenu">
    <html:hidden property="url"/>
    <html:hidden property="menu"/>
</html:form>

        <ul id="secondary-navigation">
        <!--
            <li id="account-summary-secondary-navigation" class="first-child"><a href="/manage/account-summary.shtml">Account Summary</a></li>
            <li id="account-activity-secondary-navigation"><a href="/manage/account-activity_transactions.shtml">Account Activity</a></li>
            <li id="account-information-secondary-navigation"><a href="/manage/account-information.shtml">Account Information</a></li>
            <li id="vehicles-and-ez-tags-secondary-navigation"><a href="/manage/vehicles-and-ez-tags.shtml">Vehicles &amp; EZ TAGs</a></li>
            <li id="payments-and-orders-secondary-navigation"><a href="/manage/payments-and-orders_view-orders.shtml">Payments &amp; Orders</a></li>
        -->
            <c:forEach var="tabMenu" items="${OLCSC_LOG}" varStatus="varStatus">
                <li id="${fn:toLowerCase(tabMenu.itemHelpText)}-secondary-navigation"
                <c:if test="${varStatus.index==0}">
                    class="first-child"
                </c:if>
                >
                    <a href='javascript:updateAcctMgmtTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>")'
                        <c:if test="${tabMenuId == tabMenu.itemId}">
                            style="background-color: white"
                        </c:if>
                    >
                    <c:out value="${tabMenu.itemLabel}"/>
                    </a>
                </li>
            </c:forEach>
	</ul>

<script language="javascript">
    function updateAcctMgmtTab(url, menu) {
        showLoading();
        var form = document.acctMgmtTabMenuForm;
        form.url.value = url;
        form.menu.value = menu;
        form.submit();
    }
</script>
