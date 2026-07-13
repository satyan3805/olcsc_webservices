<%-- 
  - Author(s): Noel Ternida
  - Date: March 29, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Contains user information displayed at the top of the page.
  --%>

<%@ include file="/jsp/common/Taglibs.jsp" %>
		<table cellspacing="0" id="account-info">
		<tr>
			<td class="corner-topleft"></td>
			<td class="topmiddle" colspan="2"></td>
			<td class="corner-topright"></td>
		</tr>
		<tr>
			<td class="left"></td>
			<td class="content col1">
				<!-- account-info data column 1-->
				<span><fmt:message key="userInfo.name"/>:</span> 
                                    <c:out value="${acctInfo.firstName}"/>&nbsp;
                                    <c:out value="${acctInfo.lastName}"/><br>
				<span><fmt:message key="userInfo.company"/>:</span> <c:out value="${acctInfo.companyName}"/><br/>
				<span><fmt:message key="userInfo.driverLicNbr"/>:</span> <c:out value="${acctInfo.driverLicNbr}"/><br>
				<span><fmt:message key="userInfo.driverLicState"/>:</span> <c:out value="${acctInfo.driverLicState}"/><br>
			</td>
			<td class="content col2">
				<!-- account-info data column 2-->
				<span><fmt:message key="userInfo.account"/>:</span> <c:out value="${acctInfo.acctId}"/><br>
				<span><fmt:message key="userInfo.accountType"/>:</span> <c:out value="${acctInfo.acctTypeDescr}"/><br>
				<span><fmt:message key="userInfo.accountBalance"/>:</span> <fmt:formatNumber type="currency" maxFractionDigits="2" value="${acctInfo.balanceAmt}" currencySymbol="$"/><br>
                                <c:if test="${acctInfo.pmtTypeCode=='M'}">
                                  <span><fmt:message key="userInfo.tolltagDep"/>:</span> <fmt:formatNumber type="currency" maxFractionDigits="2" value="${acctInfo.depAmt}" currencySymbol="$"/><br>
                                </c:if>
			</td>
                <td class="right"></td>
		</tr>
		<tr>
			<td class="corner-bottomleft"></td>
			<td class="bottommiddle" colspan="2"></td>
			<td class="corner-bottomright"></td>				
		</tr>
		</table>