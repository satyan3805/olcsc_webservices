<%@ include file="/jsp/common/Taglibs.jsp" %>

<br>

<%--
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong>Total to be Charged:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.paymentAmount}" type="currency"/></strong>
--%>
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
                <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.total.payment"/>:</strong></td>
                <td style="text-align:right; width:80px;"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.paymentAmount}"  currencySymbol="$" type="currency"/></strong></td>
              </tr>
            </table>
        </td>
    </tr>
</table>