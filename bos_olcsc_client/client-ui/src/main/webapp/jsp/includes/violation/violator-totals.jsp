<%-- Created: Mon Jan 25 15:49:06 CST 2010 --%>
<%--
- Display the violator totals based on pending cart payments and discounts.
- @param cart CartSummary object (to display all items and cart subtotals)
- @param violator CartSummary object (to display user/violator subtotals)
- @param paymentDate java.util.Date object which (if not null) is displayed as the payment date
- @author Milosh Boroyevich
--%>

    <h4>Total payment
      <c:if test="${paymentDate != null}">
          on <fmt:formatDate value="${paymentDate}" type="date" />
      </c:if>
      : $<fmt:formatNumber value="${cart.payment}" minFractionDigits="2" maxFractionDigits="2"/></h4>
    <h3>The balance due after this payment is processed will be $<fmt:formatNumber value="${violator.currentAmountDue-cart.discount-cart.payment}" minFractionDigits="2" maxFractionDigits="2"/></h3>
