<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:choose>
<c:when test="${sessionScope.PAYMENT_CONTEXT.callBack == 'GetTollTag'}">
  <tiles:insert name="tolltag" ignore="true"/>
</c:when>
<c:when test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">
  <tiles:insert name="violator" ignore="true"/>
</c:when>
<c:otherwise>
  <tiles:insert name="account" ignore="true"/>
</c:otherwise>
</c:choose>
