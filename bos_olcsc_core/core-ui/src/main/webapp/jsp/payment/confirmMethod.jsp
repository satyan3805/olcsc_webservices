<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str"%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.presentation.datatype.PaymentContext,com.etcc.csc.presentation.datatype.CreditCard" %>
<%@ page import="java.text.DateFormat,java.util.Date,java.text.SimpleDateFormat" %>

<br>
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong><fmt:message key="PaymentInfoForm.confirmMethod.header"/></strong><br> 	              

<table cellspacing="0" class="datapayment">
  <tr>
    <td width="30">&nbsp;</td>
    <td><fmt:message key="receipt.paymentinfo.cardtype"/>: </td>
    <td>${sessionScope.PAYMENT_CONTEXT.paymentMethod.cardTypeName}</td>
    <td width="30">&nbsp;</td>
    <td><fmt:message key="receipt.paymentinfo.cardnum"/>: </td>
    <td>************${sessionScope.PAYMENT_CONTEXT.paymentMethod.cardNumber}</td>
  </tr>
  <tr>
    <td width="30">&nbsp;</td>
    <td><fmt:message key="creditCardForm.nameOnCard"/>: </td>
    <td>${sessionScope.PAYMENT_CONTEXT.paymentMethod.billingFname} ${sessionScope.PAYMENT_CONTEXT.paymentMethod.billingLname}</td>
    <td width="30">&nbsp;</td>
    <td><fmt:message key="receipt.paymentinfo.cardexp"/>: </td>
    <td>
    	<%			    
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date expDate = df.parse((((PaymentContext)session.getAttribute("PAYMENT_CONTEXT")).getPaymentMethod()).getCardExpiryDate());
				pageContext.setAttribute("expDate",expDate);
		%>
		<fmt:formatDate type="date" pattern="MM/yyyy" value="${expDate}" />
    </td>
  </tr>
</table>