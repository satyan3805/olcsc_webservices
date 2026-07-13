<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:forEach items="${sessionScope.PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
  <c:if test="${invoice.authorized}">
    <c:set var="invoiceExists" value="true"/>
  </c:if>
</c:forEach>

<c:if test="${invoiceExists}">
<br>  
  
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong><fmt:message key="PaymentInfoForm.confirmInvoice.header"/></strong><br>
    
  <table cellspacing="0" class="datapayment">
      <tr>
        <td width="30">&nbsp;</td>
        <th class="data-th-left" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceId"/></th>
        <th class="data-th-left" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceDate"/></th>
        <th class="data-th-left" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.dueDate"/></th>
        <th class="data-th-left" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.licPlate"/></th>
        <th class="data-th-right" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.amount"/></th>
      </tr>
      <c:forEach items="${sessionScope.PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
        <c:if test="${invoice.authorized}">
        <tr>
        <td></td>
          <td>${invoice.id}</td>
          <td><fmt:formatDate value="${invoice.invoiceDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
          <td><fmt:formatDate value="${invoice.dueDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
          <td>${invoice.licPlateNumber}</td>
          <td class="align-numbers">
            <c:choose>
            <c:when test="${sessionScope.PAYMENT_CONTEXT.veaAccepted && invoice.veaEligible}">
              <fmt:formatNumber value="${invoice.veaAmount}" currencySymbol="$" type="currency"/>
            </c:when>
            <c:otherwise>
              <fmt:formatNumber value="${invoice.amount}"  currencySymbol="$" type="currency"/>
            </c:otherwise>
            </c:choose>
          </td>
        </tr>
        </c:if>
      </c:forEach>

	  <tr>
        <td colspan="5" align="right"><strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceAmount"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedInvoiceAmount}" currencySymbol="$" type="currency"/></strong></td>
      </tr>
      
      <tr>
        <td colspan="5" align="right"><strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceFeeTotal"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedInvoiceFeeAmount}" currencySymbol="$"  type="currency"/></strong></td>
      </tr>
      
   <tr>
    <td colspan="5" style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceTotal"/>:</strong></td>
    <td style="text-align:right; width:80px;text-decoration:overline"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.authorizedInvoiceAmount + sessionScope.PAYMENT_CONTEXT.authorizedInvoiceFeeAmount}" currencySymbol="$" type="currency"/></strong></td>                        
  </tr>

      
 </table>

  
</c:if>