<%@ include file="/jsp/common/Taglibs.jsp" %>

	<p class="status-bar">
		<a href="${pageContext.request.contextPath}/myAccountHome.do" title="View Account Summary">
			<strong>
<etcc-extended:format accountName="${acctInfo}" delimiter=" / " />
                        </strong>
			<em>EZ TAG Account: <strong><c:out value="${acctInfo.acctId}"/></strong></em>
			<em class="balance">Balance: <strong><fmt:formatNumber type="currency" maxFractionDigits="2" value="${acctInfo.balanceAmt}"/></strong>&nbsp;&nbsp;as of ${currentDate}</em>
		</a>
	</p>
        
<script language="JavaScript">
 s.zip="${acctInfo.zipCode}";
 s.state="${acctInfo.state}";
 s.eVar1="${acctInfo.acctId}";
 s.eVar3="${acctInfo.acctTypeDescr}";
</script> 
