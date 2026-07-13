<%@ include file="/jsp/common/Taglibs.jsp" %>
<%--
- Generate a table to present the cart items grouped by bin.  The table header
- is repeated for each bin.  The subtotal cart and user/violator summaries and
- total summary are added after all cart bins are displayed.
- @param cart CartSummary object (to display all items and cart subtotals)
- @param violator CartSummary object (to display user/violator subtotals)
- @author Milosh Boroyevich
--%>

      <table summary="This table is a confirmation of the payment information you marked in the previous step. It displays what your amount due is, what past payments you have made, the balance of each item, how much you have decided to pay now, and your remaining balance for each item." class="data-table payment-confirmation">

        <tfoot>
      <tr class="header">
        <th colspan="3" scope="row">Summary for all Invoicing Agencies</th>
        <th scope="col" class="currency-header">invoice due</th>
        <th scope="col" class="currency-header">past payments</th>
        <th scope="col" class="currency-header">amount due</th>
<c:if test="${cart.discount > 0}">
        <th scope="col" class="currency-header">discounts</th>
</c:if>
        <th scope="col" class="currency-header pay-now-header-foot">pay now</th>
        <th scope="col" class="currency-header">remaining balance</th>
      </tr>

          <tr style="white-space:nowrap;">
            <th colspan="3" scope="row" class="totals-row-header">Subtotals for this Payment:</th>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${cart.amountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${0-cart.pastPayments}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${cart.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <c:if test="${cart.discount > 0}">
              <td class="currency"><span>$</span> <fmt:formatNumber value="${0-cart.discount}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </c:if>
            <td class="currency pay-now-content"><span>$</span> <fmt:formatNumber value="${0-cart.payment}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${cart.remainingBalance}" minFractionDigits="2" maxFractionDigits="2"/></td>
          </tr>

          <tr style="white-space:nowrap;">
            <th colspan="3" scope="row" class="totals-row-header">Subtotals for Unselected Invoices:</th>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.amountDue - cart.amountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${0-(violator.pastPayments - cart.pastPayments)}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.currentAmountDue - cart.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <c:if test="${cart.discount > 0}">
              <td class="currency"><span>$</span> <fmt:formatNumber value="${0-(violator.discount - cart.discount)}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </c:if>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${0-(violator.payment - cart.payment)}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.remainingBalance - cart.remainingBalance}" minFractionDigits="2" maxFractionDigits="2"/></td>
          </tr>

          <tr style="white-space:nowrap;">
            <th colspan="3" scope="row" class="totals-row-header">Totals:</th>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.amountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${0-violator.pastPayments}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <c:if test="${violator.discount > 0}">
              <td class="currency"><span>$</span> <fmt:formatNumber value="${0-violator.discount}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </c:if>
            <td class="currency pay-now-content"><span>$</span> <fmt:formatNumber value="${0-violator.payment}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${violator.remainingBalance}" minFractionDigits="2" maxFractionDigits="2"/></td>
          </tr>
        </tfoot>


        <c:forEach items="${cart.bins}" var="bin">
<c:if test="${not empty bin}">
    <thead>
      <tr>
        <th scope="col">
			${bin[0].itemType.agency.display}
        </th>
        <th scope="col">invoice date</th>
        <th scope="col">due date</th>
        <th scope="col" class="currency-header">invoice due</th>
        <th scope="col" class="currency-header">past payments</th>
        <th scope="col" class="currency-header">amount due</th>
<c:if test="${cart.discount > 0}">
        <th scope="col" class="currency-header">discount</th>
</c:if>
        <th scope="col" class="currency-header pay-now-header">pay now</th>
        <th scope="col" class="currency-header">remaining balance</th>
      </tr>
    </thead>
    <tbody>
              <c:forEach items="${bin}" var="item">
        <tr>
          <c:choose>
            <c:when test="${item.issueDate == null}">
              <th scope="row" colspan="3">${item.name}</th>
            </c:when>
            <c:otherwise>
              <th scope="row">${item.name}</th>
              <td><fmt:formatDate value="${item.issueDate.time}" type="date" /></td>
              <td><fmt:formatDate value="${item.dueDate.time}" type="date" /></td>
            </c:otherwise>
          </c:choose>
          <td class="currency"><span>$</span> <fmt:formatNumber value="${item.amountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td class="currency"><span>$</span> <fmt:formatNumber value="${0-item.pastPayments}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td class="currency"><span>$</span> <fmt:formatNumber value="${item.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
<c:if test="${cart.discount > 0}">
          <td class="currency"><span>$</span> <fmt:formatNumber value="${0-item.discount}" minFractionDigits="2" maxFractionDigits="2"/></td>
</c:if>
          <td class="currency pay-now-content"><span>$</span> <fmt:formatNumber value="${0-item.payment}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td class="currency"><span>$</span> <fmt:formatNumber value="${item.remainingBalance}" minFractionDigits="2" maxFractionDigits="2"/></td>
        </tr>
              </c:forEach>
    </tbody>
</c:if>
        </c:forEach>

      </table> <!-- end of confirmation table -->
