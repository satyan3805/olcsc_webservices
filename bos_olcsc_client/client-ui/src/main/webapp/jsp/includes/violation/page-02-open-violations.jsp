<%@ include file="/jsp/common/Taglibs.jsp" %>
	<div id="content-container">

		<div id="content">

			<form id="invoice-details" action="${pageContext.request.contextPath}/selectedInvoice.do" method="post" autocomplete="off">
                                
				<div class="wide-section">

					<h1>Current Invoices</h1>
                                        
                                            <div class="section" <c:if test="${nopay!='true'}"> style="display:none" </c:if>>
                                                <dl class="errors"/>
                                                <p>Total payment can not be zero.</p>
                                            </div>

<%@ include file="payment-plan-notice.jsp" %>

                                        <input type="hidden" name="requestedPageNum" value="0"/>
                                        <input type="hidden" name="currentPageNum" value="${selectedInvoiceForm.currentPageNum}"/>
                                        <input type="hidden" name="noInvoicesInCurrentPage" value="${selectedInvoiceForm.noInvoicesInCurrentPage}"/>

				<p>If this vehicle was registered to you when it passed through these toll gates, you are responsible to pay these tolls and fees. If the vehicle was not registered to you, please call Customer Service at <strong><bean:message key="HCTRA.telephone.number" /></strong>.</p>
				<p>Note: It may take up to five business days for recent violations and/or payments to be reflected on your balance.</p>

				</div> <!-- end of section -->
				<fieldset>

<c:set value="${selectedInvoiceForm.violatorDTO}" var="violator" />
					<h2>Outstanding Invoices for ${violator.licPlateState} ${violator.licPlateNbr}</h2>
                    <p class="help">Last updated <fmt:formatDate value="${violator.lastUpdatedDate.time}" type="date" /></p>

					<p>Select the invoices you would like to pay now online.<br />You are still responsible for invoices that you choose not to pay now.</p>


<c:if test="${violator.waiverEligible}">
<p class="discount-control">
<input id="pay-all-waive" type="checkbox" name="invoicePaymentTypeOverride" value="WAIVE" onclick="AccountSection.updateTableSelections(this, AccountSection.findNearestTable(this));" />
<label for="pay-all-waive">You are eligible to apply a waiver of administrative fees for the indicated invoices.</label><br />
<span class="help">Note: You may accept a waiver of your eligible invoices a maximum of two (2) times within a twelve (12) month period.</span>
</p>
</c:if>

<c:if test="${violator.adjustmentEligible}">
<p class="discount-control">
<input id="pay-all-adjust" type="checkbox" name="invoicePaymentTypeOverride" value="ADJUST" onclick="AccountSection.updateTableSelections(this, AccountSection.findNearestTable(this));" />
<label for="pay-all-adjust">You are eligible to apply an adjustment of administrative fees for the indicated invoices.</label><br />
<span class="help">Note: There is no limit to the number of times you may accept an adjustment of your eligible invoices.</span>
</p>
</c:if>
					<ul class="table-controls">
						<li class="expand-control"><a href="#">expand all</a></li>
						<li class="collapse-control"><a href="#">collapse all</a></li>
<!-- the "download all PDFs" function is now out of scope -->
<!--						<li class="pdf"><a href="#">download all invoices as PDF</a></li> -->
					</ul>


                                        <span class="pagebanner">${selectedInvoiceForm.totalItemsFound} items found, ${selectedInvoiceForm.totalNumOfPages} pages total</span>
                                        <span class="pagelinks">[
                                                <c:if test="${selectedInvoiceForm.currentPageNum != 1}">
                                                <a href="javascript:gotoPage(1)">
                                                </c:if>
                                                First
                                                <c:if test="${selectedInvoiceForm.currentPageNum != 1}">
                                                </a>
                                                </c:if>
                                                /
                                                <c:if test="${selectedInvoiceForm.currentPageNum != 1}">
                                                <a href="javascript:gotoPage(${selectedInvoiceForm.currentPageNum-1})">
                                                </c:if>
                                                Prev
                                                <c:if test="${selectedInvoiceForm.currentPageNum != 1}">
                                                </a>
                                                </c:if>
                                                ]
                                                ${selectedInvoiceForm.pageSelector}
                                                [
                                                <c:if test="${selectedInvoiceForm.currentPageNum != selectedInvoiceForm.totalNumOfPages}">
                                                <a href="javascript:gotoPage(${selectedInvoiceForm.currentPageNum+1})">
                                                </c:if>
                                                Next
                                                <c:if test="${selectedInvoiceForm.currentPageNum != selectedInvoiceForm.totalNumOfPages}">
                                                </a>
                                                </c:if>
                                                /
                                                <c:if test="${selectedInvoiceForm.currentPageNum != selectedInvoiceForm.totalNumOfPages}">
                                                <a href="javascript:gotoPage(${selectedInvoiceForm.totalNumOfPages})">
                                                </c:if>
                                                Last
                                                <c:if test="${selectedInvoiceForm.currentPageNum != selectedInvoiceForm.totalNumOfPages}">
                                                </a>
                                                </c:if>
                                                ]
                                        </span>


<table summary="This table breaks out in detail your unpaid tolls and fees. Information is shown by invoice number. The detail shows when and where you incurred an infraction, how much the fine is, any fees applied, any payments you have made so far, and your total left to pay. You also have the ability to select too pay the invoice in full, specify an amount you wish to pay, or not to pay at this time." class="data-table payments">

  <tfoot>
    <tr>
      <td></td>
      <th colspan="3" id="total-amount-due-header" class="currency-header">Sub-total Amount Due:</th>
      <th class="currency" headers="total-amount-due-header"><span>$</span><fmt:formatNumber value="${violator.currentAmountDue}" minFractionDigits="2" maxFractionDigits="2"/></th>
      <th id="total-payment-header" class="currency-header">Sub-total Payment:</th>
      <th class="currency" headers="total-payment-header"><span>$</span> <fmt:formatNumber value="${violator.payment}" minFractionDigits="2" maxFractionDigits="2"/></th>
      <td></td>
    </tr>
  </tfoot>

  <!-- Invoices -->
  <c:set value="${null}" var="agency" />
  <c:forEach items="${violator.allInvoices}" var="invoice" varStatus="varStatus">
    <c:if test="${agency != invoice.agency}">
      <%-- Show the header for each agency. --%>
      <c:set value="${invoice.agency}" var="agency" />
  <thead>
    <tr>
      <td></td>
      <th id="invoice-header">${agency.display} Invoice</th>
      <th id="invoice-date-header">Invoice Date</th>
      <th id="due-date-header">Due Date</th>
<%--      <!-- This column is for calculating subtotals using JavaScript. -->
      <th style="width:0;" width="0"></th>--%>
      <th id="amount-due-header" class="currency-header">Amount Due</th>

      <!-- The link here is set to display:none by default, so that users without
      JavaScript don't even see it. Naturally, it's unhidden via JavaScript. -->
      <th id="pay-all-invoices-header" class="currency-header"><a href="#" style="display:none;">Pay All in Full</a></th>
      <th id="payment-header" class="currency-header">Payment</th>
      <th id="eligible-header" style="width:0;" width="0"><!--Eligible Discounts--></th>
    </tr>
  </thead>
    </c:if>
    <c:set value="${varStatus.index}" var="invoiceIndex" />
    <%@ include file="invoice-details.jsp" %>
  </c:forEach>
  <!-- END Invoices -->

</table> <!-- end of payments table -->

				</fieldset>

				<ul class="form-actions">

                                        <c:if test="${not empty selectedInvoiceForm.returnAction}">
                                            <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                                        </c:if>
                                        <li><input id="pay-selected-invoices" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/pay-selected-invoices.gif" value="Pay Selected Invoices" onclick="doSubmit();return false;" title="&rarr; page-03-confirmation" /></li>

				</ul> <!-- end of form-actions -->

                    <input type="hidden" name="returnAction" value="${requestScope.returnAction}"/>
			</form>

		</div> <!--end of content -->

<!-- MWK - 10/16/2006 - removing status for the moment -->
		<p class="progress-bar" id="step-1-of-4">
			Unpaid Tolls and Fees
			<em>Step 1 of 4</em>
		</p>

	</div> <!-- end of content-container -->


<script type="text/javascript">
s.events="event2";
s.eVar2="Unpaid Tolls and Violations";

function openReceipt(invid)
{
    var contextPath = "${pageContext.request.contextPath}";
    window.open(contextPath+"/openInvoicePDF.do?invoice_number=" + invid,"","width=800,height=500,resizable");    
}

function goBack()
{
    document.forms[0].action = '${pageContext.request.contextPath}/${selectedInvoiceForm.returnAction}.do';
    document.forms[0].submit();
}

function gotoPage(num) {
    if (!payExceeds() || confirm('Payment amount exceeds amount due. Do you want to continue?')) {
        document.forms[0].requestedPageNum.value=num;
        document.forms[0].action = '${pageContext.request.contextPath}/invoiceDisplay.do';
        document.forms[0].submit();
    }
}

function doSubmit() {
    if (!payExceeds() || confirm('Payment amount exceeds amount due. Do you want to continue?')) {
        document.forms[0].submit();
    }
    return false;
}

function payExceeds() {
    var exceeds = false;
    for (var i = 0; i < document.forms[0].noInvoicesInCurrentPage.value; i++) {
        // MB 2010-03-12: strip out the ',' thousands separator.
        var amtDue = parseFloat(document.forms[0].elements['invoice['+i+'].amtDue'].value.replace(/,/g,''));
        amtDue = (amtDue > 0 ? amtDue : 0);
        if (parseFloat(document.forms[0].elements['invoice['+i+'].payment'].value) > amtDue) {
            exceeds = true;
        }
    }
    return exceeds;
}

</script>
