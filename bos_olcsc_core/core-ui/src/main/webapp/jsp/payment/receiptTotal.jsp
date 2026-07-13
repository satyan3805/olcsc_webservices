<%@ include file="/jsp/common/Taglibs.jsp" %>

<br>
<%--<strong>Total Charged:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.paymentAmount}" type="currency"/></strong>--%>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td><img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/></td>
        <td>
            <table cellspacing="0" class="datapayment" width="100%">
              <tr>
                <td width="30">&nbsp;</td>
                <td></td>
                <td></td>
                <td></td>
                <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.receiptTotal.note1"/>:</strong></td>
                <td style="text-align:right; width:80px;"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.paymentAmount}" type="currency" currencySymbol="$"/></strong></td>
              </tr>
            </table>
        </td>
    </tr>
</table>