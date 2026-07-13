      <tbody class="expanded">  
      <tr class="invoice-summary">
        <td class="control">[+]</td>
 <%--   <c:choose> 
      <c:when test="${invoice.invoiceDate != null}"> --%>
        <th id="invoice-1" headers="invoice-header"><a href="#date-time-header-1" title="Show details for this invoice">${invoice.name}</a></th>
        <td headers="invoice-header invoice-1 invoice-date-header">
          <fmt:formatDate value="${invoice.issueDate.time}" type="date" />
        </td>
        <td headers="invoice-header invoice-1 due-date-header">
          <fmt:formatDate value="${invoice.dueDate.time}" type="date" />
        </td>
    <%--  </c:when>
       <c:otherwise>
        <th id="invoice-1" headers="invoice-header" colspan="3"><a href="#date-time-header-1" title="Show details">Un-invoiced Toll Transactions within 72 hours</a></th>
      </c:otherwise> 
    </c:choose> --%>
<%--        <td class="currency" style="color:transparent;font-size:1px;width:0;"><fmt:formatNumber value="${invoice.currentAmountDue-invoice.onlineDiscount}" minFractionDigits="2" maxFractionDigits="2"/></td>--%>
        <td headers="invoice-header invoice-1 amount-due-header" class="currency"><span>$</span><fmt:formatNumber value="${invoice.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td headers="invoice-header invoice-1 pay-all-invoices-header" class="pay-all-invoices">
          <label for="payment-state-${invoiceIndex}">Payment Type</label>
          <c:if test="${invoice.currentAmountDue <= 0}">
            <div id="hidshow" style="display:none;">
          </c:if>
            <select name="invoice[${invoiceIndex}].stringPaymentType" id="payment-state-${invoiceIndex}">
              <c:forEach items="${invoice.eligiblePaymentTypes}" var="paymentType">
                <option value="${paymentType}"<c:if test="${invoice.paymentType == paymentType}"> selected="true" </c:if>>${paymentType.display}</option>
              </c:forEach>
            </select>
          <c:if test="${invoice.currentAmountDue <= 0}">
            </div>
          </c:if>
        </td>
        <td headers="invoice-${invoiceIndex} payment-header" class="currency">
          <label for="invoice-${invoice.invoiceId}">partially pay for invoice ${invoice.name}:</label>
          <input type="text" value="<fmt:formatNumber value="${invoice.payment}" minFractionDigits="2" maxFractionDigits="2"/>" class="textfield" id="invoice-${invoice.invoiceId}" name="invoice[${invoiceIndex}].payment"/>
          <%-- Name of the input field is used by JavaScript functions -- name="invoice[${invoiceIndex}].amtDue" --%>
          <input type="hidden" value="<fmt:formatNumber value="${invoice.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/>" class="textfield" id="invoice-due-${invoice.invoiceId}" name="invoice[${invoiceIndex}].amtDue"/>
        </td>
        <td headers="invoice-header">
          <c:if test="${invoice.waiverEligible}">Waiver<br /></c:if>
          <c:if test="${invoice.adjustmentEligible}">Adjustment</c:if>
        </td>
      </tr>

      <!--Invoice details-->
      <tr>
        <td></td>
        <th id="date-time-header-1" class="nested-table-header" headers="invoice-header invoice-1">Date / Time</th>
        <th colspan="3" id="violation-location-header-1" class="nested-table-header" headers="invoice-header invoice-4">Violation Location</th>
        <th id="toll-amount-header-1" class="nested-table-header" headers="invoice-header invoice-1">Toll Amount</th>
        <td colspan="2"></td>
      </tr>

      <c:forEach items="${invoice.violations}" var="violation">
        <tr class="invoice-details-row">
          <td></td>
          <td id="violation-date-time-1-1" headers="invoice-header invoice-1 date-time-header-1">
            <fmt:formatDate value="${violation.dateTime.time}" type="both" dateStyle="SHORT" />
          </td>
          <td colspan="3" headers="invoice-header invoice-1 violation-location-header-1">${violation.location}</td>
          <td headers="invoice-header invoice-1 toll-amount-header-1" class="currency"><span>$</span> <fmt:formatNumber value="${violation.amountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td colspan="2"></td>
        </tr>
      </c:forEach>

  <%-- <c:if test="${invoice.invoiceDate != null}"> --%>
      <tr>
        <td></td>
        <td colspan="2"><a href="javascript:openReceipt('${invoice.agency.code}${invoice.invoiceId}')" class="pdf">download PDF version</a></td>
        <td colspan="2" class="administrative-fee">Administrative Fee:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.administrativeFee}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td colspan="2" class="administrative-fee">Collection Fee:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.collectionFee}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td colspan="2" class="administrative-fee">Other Fee:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.otherFee}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr class="totals">
        <td colspan="3"></td>
        <td colspan="2" class="sub-total">SubTotal Invoice Due:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.invoiceDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
<c:if test="${invoice.onlineDiscountEligible}">
      <tr class="online-discount">
        <td colspan="3"></td>
        <td colspan="2" class="administrative-fee">Online Discount:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${0-invoice.onlineDiscount}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr class="totals online-discount">
        <td colspan="3"></td>
        <td colspan="2" class="sub-total">Total Invoice Due:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.invoiceDue-invoice.onlineDiscount}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
</c:if>
<c:if test="${invoice.waiverEligible}">
      <tr class="fee-waiver">
        <td colspan="3"></td>
        <td colspan="2" class="administrative-fee">Fee Waiver:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${0-invoice.waiver}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr class="totals fee-waiver">
        <td colspan="3"></td>
        <td colspan="2" class="sub-total">Total Invoice Due:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.invoiceDue-invoice.waiver}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
</c:if>
<c:if test="${invoice.adjustmentEligible}">
      <tr class="fee-adjustment">
        <td colspan="3"></td>
        <td colspan="2" class="administrative-fee">Fee Adjustment:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${0-invoice.adjustment}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr class="totals fee-adjustment">
        <td colspan="3"></td>
        <td colspan="2" class="sub-total">Total Invoice Due:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.invoiceDue-invoice.adjustment}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
</c:if>
      <tr>
        <td colspan="3"></td>
        <td colspan="2" class="payment-pending">Payment made online:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${0-invoice.paymentsPending}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td colspan="2" class="amount-paid">Amount Already Paid:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${0-invoice.amountAlreadyPaid}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
      <tr class="totals">
        <td colspan="3"></td>
        <td colspan="2" class="total-amount-due-detail">Total Amount Due:</td>
        <td class="currency"><span>$</span> <fmt:formatNumber value="${invoice.finalAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
        <td colspan="2"></td>
      </tr>
    <%--  </c:if> --%>

      <!-- end of Invoice details-->
    </tbody>
