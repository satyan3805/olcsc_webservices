<c:if test="${selectedInvoiceForm.violatorDTO.payPlanExists}">
  <dl class="alerts">
    <dt>These invoices are associated with a payment plan.</dt>
    <dd>The invoices for the license plate <strong>${selectedInvoiceForm.violatorDTO.licPlateNbr}</strong> as registered to <strong>${selectedInvoiceForm.violatorDTO.firstName} ${selectedInvoiceForm.violatorDTO.lastName}</strong> are associated with a payment plan</a>. You may review the <a href="${pageContext.request.contextPath}/paymentPlan.do" onClick="openNewWin(this.href);return false;">payment plan details</a>.<br /><br />
      You may still make additional payments to outstanding invoices listed below.</dd>
  </dl>
</c:if>
