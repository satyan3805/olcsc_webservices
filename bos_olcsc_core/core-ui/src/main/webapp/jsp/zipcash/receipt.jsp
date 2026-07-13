<%@ include file="/jsp/common/Taglibs.jsp" %>

<tr>
  <td class="topleft"></td>
  <td class="topcenter"></td>
  <td class="topright"></td>
</tr>

<tr>
  <td class="left"></td>
  <td class="content">
    <div style="text-align:right;">
      <a href="zipCashDisplayPaymentReport.do?format=PDF" target="_blank"><fmt:message key="label.pdf"/></a>&nbsp;
      <a href="zipCashDisplayPaymentReport.do?format=HTML" target="_blank"><fmt:message key="label.printerFriendly"/></a>
    </div>

    <h2><fmt:message key="PaymentInfoForm.receipt.header"/></h2>
      <p><fmt:message key="PaymentInfoForm.receipt.note1"/>
      <c:if test="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.postingStatus == 'P'}">
        <fmt:message key="PaymentInfoForm.receipt.note2"/>
      </c:if>
      <br><fmt:message key="PaymentInfoForm.receipt.note3"/>
      <p>&nbsp;</p>

  <tiles:insert name="method" ignore="true"/>
  <tiles:insert name="invoice" ignore="true"/>
  <tiles:insert name="violation" ignore="true"/>
  <tiles:insert name="total" ignore="true"/>
  <br><br><br><br>
  </td>
  <td class="right"></td>
</tr>

<tr>
  <td class="bottomleft"></td>
  <td class="bottomcenter"></td>
  <td class="bottomright"></td>
</tr>