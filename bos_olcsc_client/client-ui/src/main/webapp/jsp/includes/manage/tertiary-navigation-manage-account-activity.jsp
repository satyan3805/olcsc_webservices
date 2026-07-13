<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.SessionUtil"%>

<ul id="tertiary-navigation">
<%--	<li id="transactions-tertiary-navigation">View: <a href="account-activity_transactions.shtml">Transactions</a></li>
	<li id="statements-tertiary-navigation"><span class="accessibility">View: </span><a href="account-activity_statements.shtml">Statements</a></li>
	<li id="yearly-summary-tertiary-navigation"><span class="accessibility">View: </span><a href="account-activity_yearly-summary.shtml">Yearly Summary</a></li>
	<li id="receipts-tertiary-navigation"><span class="accessibility">View: </span><a href="account-activity_receipts.shtml">Receipts</a></li>
		<li id="monthly-invoices-tertiary-navigation"><span class="accessibility">View: </span><a href="account-activity_monthly-invoices.shtml">Monthly Invoices</a></li>
--%>
    <% SessionUtil.getAcctDTO(request);%>

    <c:forEach var="tabMenu" items="${tertiaryMenus}" varStatus="varStatus">
      <c:if test="${(tabMenu.itemLabel != 'Monthly Invoices') || ((acctInfo.pmtTypeCode == 'I') and (acctInfo.acctTypeCode=='C'))}">
        <li id="${fn:toLowerCase(tabMenu.itemHelpText)}-tertiary-navigation" <c:if test="${varStatus.index==0}">class="first-child"</c:if> >
          <span class="accessibility">View: </span>
          <c:if test="${acctInfo.revenueAccount eq true}" >
            <a href='javascript:updateTertiaryTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>")'>
              <c:out value="${tabMenu.itemLabel}"/>
            </a>
          </c:if>

          <c:if test="${acctInfo.revenueAccount eq false}">
            <c:set var="tablabel" value="${tabMenu.itemLabel}"/>
            <c:if test="${tablabel eq 'View Orders'}">
              <a href='javascript:updateTertiaryTab("<c:out value="${tabMenu.itemUrl}"/>", "<c:out value="${tabMenu.itemId}"/>")'>
              <c:out value="${tabMenu.itemLabel}"/>
              </a>
            </c:if>
          </c:if>
        </li>
      </c:if>
    </c:forEach>
</ul>

<script language="javascript">
    function updateTertiaryTab(url, menu) {
//        alert("Here: url="+url + " menu=" + menu );
        showLoading();
        var form = document.acctMgmtTabMenuForm;
        form.action='${pageContext.request.contextPath}/updateTertiaryTabMenu.do';
        form.url.value = url;
        form.menu.value = menu;
        form.submit();
    }
</script>
