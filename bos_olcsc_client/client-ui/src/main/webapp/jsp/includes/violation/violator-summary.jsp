<%--
- Display the summary by agency.
- @param agencyViolations a ViolationsContainer with violations grouped by agency
- @param violationType String value representing the violation type (e.g. "invoice", "toll")
- @param headerTitle String value displayed in the table header
- @author Milosh Boroyevich
--%>
<c:if test="${!empty agencyViolations}">
  <thead>
    <tr>
      <th>${headerTitle}</th>
      <th>Total Items</th>
      <th class="currency-header" style="white-space: nowrap;"><c:if test="${violationType == 'invoice'}">Amount Due</c:if></th>
      <th></th>
    </tr>
  </thead>

  <tbody style="cursor:auto;">
    <c:forEach items="${agencyViolations}" var="violations" varStatus="agencyVarStatus">
      <tr class="invoice-summary">
        <td headers="invoice-header invoice-1" style="vertical-align:middle;">${violations.agency.display}</td>
        <td headers="invoice-header invoice-1 quantity-header-1" style="vertical-align:middle;">${violations.totalItems}</td>
      <c:choose>
        <c:when test="${violations.currentAmountDue > 0}">
          <td headers="invoice-header invoice-1 amount-due-header" class="currency" style="vertical-align:middle;"><span>$</span><fmt:formatNumber value="${violations.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
        </c:when>
        <c:otherwise>
          <td headers="invoice-header invoice-1"></td>
        </c:otherwise>
      </c:choose>
      <c:if test="${agencyVarStatus.first}">
        <td rowspan="3" headers="invoice-header invoice-1" style="text-align:center;vertical-align:middle;">
          <span class="button-label" onclick="doSubmit('${violationType}Display');" style="text-transform:capitalize;">Pay ${violationType}s</span>
        </td>
<%--        <td rowspan="3" headers="invoice-header invoice-1" onclick="doSubmit('${violationType}Display','${violations.agency}');" style="text-align:center;vertical-align:middle; cursor:pointer;padding:10px 20px;font-weight:bold;font-size:16px;white-space:nowrap;text-transform:capitalize;color:white; background-image:url('${pageContext.request.contextPath}/meta/media/buttons/blank.gif');background-repeat:no-repeat;background-position:center;">Pay ${violationType}s &raquo;</td>--%>
<%--        <td rowspan="3" headers="invoice-header invoice-1" style="text-align:right;vertical-align:middle;">
          <span class="button-label">Pay ${violationType}s</span>
          <input type="image" name="${violationType}" src="${pageContext.request.contextPath}/meta/media/buttons/make-payment.png" value="pay ${violationType}s" onclick="doSubmit('${violationType}Display','${violations.agency}');return false;" title="&rarr; make ${violationType} payment" />
        </td>--%>
      </c:if>
      </tr>
    </c:forEach>

    <c:if test="${violationType == 'invoice'}">
      <tr class="invoice-summary">
        <th colspan="2" id="total-amount-due-header" class="currency-header">Total Amount Due:</th>
        <th class="currency" headers="total-amount-due-header"><span>$</span><fmt:formatNumber value="${violator.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></th>
      </tr>
    </c:if>
  </tbody>

</c:if>
