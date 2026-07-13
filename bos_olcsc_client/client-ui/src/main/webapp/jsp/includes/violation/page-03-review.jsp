<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

	<div id="content">

		<form id="selected-invoices" action="${pageContext.request.contextPath}/invoicePaymentDisplay.do" method="post" autocomplete="off">

			<div class="wide-section">

				<h1>Make A Payment</h1>

<c:set value="${allInvoicesForm.violatorDTO}" var="violator" />
<%-- The cart has not been updated yet, so display the review information based on what's selected on the violator. --%>
<c:set value="${selectedInvoiceForm.violatorDTO}" var="cart" />
<%@ include file="violator-totals.jsp" %>

					<h2 id="is-this-correct">Is this correct?</h2>
					<p>You indicated on the previous screen that these are the payments you would like to make against the selected invoices. <a href="javascript:returnToInvoices()">Return to the list of invoices</a> if you would like to make any changes before you pay.</p>

			</div> <!-- end of section -->
                        <input type="hidden" name="requestedPageNum" value="${allInvoicesForm.requestedPageNum}"/>

<h4 id="total-cost">Selected Invoices</h4>			
<%@ include file="cart-summary.jsp" %>

			<div class="section">
				<p>If you need to change these payments, <a href="javascript:returnToInvoices()">return to the list of invoices</a>.</p>
			</div> <!-- end of section -->

			<ul class="form-actions">

				<c:if test="${not empty selectedInvoiceForm.returnAction}">
                                    <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                                </c:if>
                                <li><input id="continue" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/review_summary_button.gif" value="Pay Selected Invoices" onclick="document.forms[0].submit(); return false;" title="&rarr; page-04-payment.shtml" /></li>

			</ul> <!-- end of form-actions -->

		</form>

	</div> <!-- end of content -->

	<p class="progress-bar" id="step-2-of-4">
		Unpaid Tolls and Fees
		<em>Step 2 of 4</em>
	</p>

</div> <!-- end of content-container -->

<script type="text/javascript">
function goBack()
{
    document.forms[0].action = '${pageContext.request.contextPath}/${selectedInvoiceForm.returnAction}.do';
    document.forms[0].submit();
}

function returnToInvoices() {
    document.forms[0].action = '${pageContext.request.contextPath}/invoiceDisplay.do';
    document.forms[0].submit();
}
</script>
