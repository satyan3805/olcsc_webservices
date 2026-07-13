<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:forEach items="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
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
        <th class="data-th-left" nowrap><fmt:message key="PaymentInfoForm.confirmInvoice.licPlate"/>/<fmt:message key="receipt.acctinfo.state"/></th>
        <th class="data-th-right" nowrap><fmt:message key="zipcash.detailInvoice.invoiceAmount"/></th>
      </tr>
      <c:forEach items="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
        <c:if test="${invoice.authorized}">
        <tr>
        <td></td>
          <td>${invoice.id}</td>
          <td><fmt:formatDate value="${invoice.invoiceDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
          <td><fmt:formatDate value="${invoice.dueDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
          <td>${invoice.licPlateNumber} (${invoice.licPlateState})</td>
          <td class="align-numbers">
              <fmt:formatNumber value="${invoice.newTrans + invoice.lateFee}"  currencySymbol="$" type="currency"/>
          </td>
        </tr>
        </c:if>
      </c:forEach>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="5" align="right"><strong><fmt:message key="zipcash.detailInvoice.totalLateFee"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${ZIPCASH_PAYMENT_CONTEXT.authorizedInvoiceLateFeeAmount}" currencySymbol="$"  type="currency"/></strong></td>
      </tr>
      <tr>
        <td colspan="5" align="right"><strong><fmt:message key="zipcash.detailInvoice.totalPastDue"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${ZIPCASH_PAYMENT_CONTEXT.authorizedInvoicePastDueAmount 
            + ZIPCASH_PAYMENT_CONTEXT.authorizedInvoicePastDueLateFeeAmount}" currencySymbol="$"  type="currency"/></strong></td>
      </tr>
      <tr>
        <td colspan="5" align="right"><strong><fmt:message key="zipcash.detailInvoice.totalNewTrans"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.authorizedInvoiceNewTransactionAmount}" currencySymbol="$"  type="currency"/></strong></td>
      </tr>
   <tr>
    <td colspan="5" style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="zipcash.detailInvoice.totalInvoice"/>:</strong></td>    <td style="text-align:right; width:80px;text-decoration:overline"><strong><fmt:formatNumber value="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.authorizedInvoiceAmount}" currencySymbol="$" type="currency"/></strong></td>                        
  </tr>

      
 </table>

  
</c:if>