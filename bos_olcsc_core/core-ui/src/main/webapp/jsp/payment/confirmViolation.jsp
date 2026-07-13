<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:forEach items="${sessionScope.PAYMENT_CONTEXT.violations}" var="violation" varStatus="varStatus">
<c:if test="${violation.authorized}">
  <c:set var="violationExists" value="true"/>
</c:if>
</c:forEach>

<c:if test="${violationExists}">
<br>
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong><fmt:message key="PaymentInfoForm.confirmViolation.header"/></strong><br>
<table cellspacing="0" class="datapayment">
                        <tr>
                          <td width="30">&nbsp;</td>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.violationId"/></th>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.location"/></th> 
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.date"/></th>                 
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.licPlate"/></th>                           
                          <th class="data-th-right"><fmt:message key="PaymentInfoForm.confirmViolation.amount"/></th>                          
                       </tr>
<c:forEach items="${sessionScope.PAYMENT_CONTEXT.violations}" var="violation" varStatus="varStatus">
  <c:if test="${violation.authorized}">
    <tr>
    <td></td>
    <td>${violation.id}</td>
    <td>${violation.location}</td>
    <td><fmt:formatDate value="${violation.timestamp.time}" pattern="MM/dd/yyyy HH:mm"/></td>
    <td>${violation.licPlate}</td>
    <td class="align-numbers"><fmt:formatNumber value="${violation.cashAmount}"  currencySymbol="$" type="currency"/></td>
  </tr>
  </c:if>
</c:forEach>

<%--
<tr>
  <td colspan="4" align="right"><strong>Violation Fee Total:</strong></td>
  <td align="right"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedViolationFeeAmount}" type="currency"/></strong></td>                        
</tr>

<tr>
  <td colspan="5" align="right"><strong><fmt:message key="PaymentInfoForm.confirmViolation.authorizedViolationAmount"/>:</strong></td>
  <td align="right"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedViolationAmount}" type="currency" currencySymbol="$"/></strong></td>                        
</tr>
--%>
<tr>
  <td colspan="5" style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="PaymentInfoForm.confirmViolation.totalAmount"/>:</strong></td>
  <td style="text-align:right; width:80px;text-decoration:overline"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedViolationAmount + sessionScope.PAYMENT_CONTEXT.authorizedViolationFeeAmount}" type="currency" currencySymbol="$"/></strong></td>                        
</tr>

          </table> 
                    
</c:if>