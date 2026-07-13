<%@ include file="/jsp/common/Taglibs.jsp" %>
<table id="info" cellspacing="0">
<tr>
                <td id="info-top" colspan="2"></td>
</tr>
<tr>
        <td id="info-content">
                <p>
            <c:if test="${accountLogin.loginType=='IN'}">
                         <strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceId"/>:</strong><br>
                 ${accountLogin.invoiceId}<br>
                 <br>
            </c:if>
            <c:if test="${accountLogin.loginType=='CA'}">
                <strong><fmt:message key="PaymentInfoForm.layout.collection"/>:</strong><br>
                 ${accountLogin.invoiceId}<br>
                 <br>
            </c:if>
                <strong><fmt:message key="accountHome.licPlate"/>:</strong><br>
                ${accountLogin.licPlate}
                </p>
        </td>
        <td id="info-right"></td>
</tr>
<tr>
        <td id="info-bottom" colspan="2"></td>
</tr>		
</table>