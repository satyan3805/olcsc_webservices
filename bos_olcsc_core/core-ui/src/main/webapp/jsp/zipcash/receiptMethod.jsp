<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str"%>
<%@ include file="/jsp/common/Taglibs.jsp" %>

<br>
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>

<strong><fmt:message key="PaymentInfoForm.receiptMethod.cardTypeName"/>:</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.ZIPCASH_PAYMENT_CONTEXT.paymentMethod.cardTypeName} <fmt:message key="PaymentInfoForm.confirmMethod.note"/> <str:substring start="-4">${sessionScope.ZIPCASH_PAYMENT_CONTEXT.paymentMethod.cardNumber}</str:substring>

<br>