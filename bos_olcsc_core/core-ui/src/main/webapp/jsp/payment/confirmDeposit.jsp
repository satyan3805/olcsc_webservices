<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${ sessionScope.PAYMENT_CONTEXT.tagAmount == 0 && sessionScope.PAYMENT_CONTEXT.depAmount > 0}">
<br>
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong><fmt:message key="PaymentInfoForm.confirmDeposit.header"/></strong><br> 	                 

<table cellspacing="0" class="datapayment">
  <c:if test="${sessionScope.PAYMENT_CONTEXT.depAmount > 0}">
      <tr>
        <td>&nbsp;</td>
        <td></td>
        <td></td>
        <td style="text-align:right;">
            <strong>
                <fmt:message key="PaymentInfoForm.confirmDeposit.label"/>:
            </strong>
        </td>
        <td style="text-align:right; width:80px;"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.depAmount}" currencySymbol="$" type="currency"/></strong></td>
      </tr>
    </c:if>
</table>

</c:if>