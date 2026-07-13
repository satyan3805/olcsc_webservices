<%@ include file="/jsp/common/Taglibs.jsp" %>
	<div id="content-container">

		<div id="content">

			<form id="violator-summary" action="${pageContext.request.contextPath}/invoiceDisplay.do" method="post">

				<div class="wide-section">

					<h1>Current Invoices <%--and Tolls--%> Summary</h1>

                                            <div class="section" <c:if test="${nopay!='true'}"> style="display:none" </c:if>>
                                                <dl class="errors"/>
                                                <p>Total payment can not be zero.</p>
                                            </div>

<%@ include file="payment-plan-notice.jsp" %>

            <input type="hidden" name="requestedPageNum" value="${allInvoicesForm.requestedPageNum}"/>

				<p>If this vehicle was registered to you when it passed through these toll gates, you are responsible to pay these tolls and fees. If the vehicle was not registered to you, please call Customer Service at <strong><bean:message key="HCTRA.telephone.number" /></strong>.</p>
				<p>Note:  it may take up to five business days for recent violations and/or payments to be reflected on your balance.</p>

				</div> <!-- end of section -->
				<fieldset>

<c:set value="${allInvoicesForm.violatorDTO}" var="violator" />
					<h2>Outstanding Invoices <%--and Tolls--%> for ${violator.licPlateState} ${violator.licPlateNbr}</h2>
					<p class="help">Last updated <fmt:formatDate value="${violator.lastUpdatedDate.time}" type="date" /></p>

					<p>Select the open invoice or tolls for payment options and details.</p>

<table summary="This table summarizes the invoices and uninvoiced tolls for each agency." class="data-table payments">

<%--
  <!-- Uninvoiced violations -->
  <c:set value="Un-invoiced Tolls within 72 hours" var="headerTitle" />
  <c:set value="toll" var="violationType" />
  <c:set value="${violator.uninvoicedViolations}" var="agencyViolations" />
  <%@ include file="violator-summary.jsp" %>
  <!-- END Uninvoiced violations -->
--%>

  <!-- Invoiced violations -->
  <c:set value="Outstanding Invoices" var="headerTitle" />
  <c:set value="invoice" var="violationType" />
  <c:set value="${violator.invoicedViolations}" var="agencyViolations" />
  <%@ include file="violator-summary.jsp" %>
  <!-- END Invoiced violations -->

</table> <!-- end of table -->

				</fieldset>

				<ul class="form-actions">

                                        <c:if test="${not empty allInvoicesForm.returnAction}">
                                            <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                                        </c:if>
				</ul> <!-- end of form-actions -->

                    <input type="hidden" name="returnAction" value="${requestScope.returnAction}"/>
			</form>

		</div> <!--end of content -->

	<p class="progress-bar" id="step-1-of-5">
		Unpaid Tolls and Fees
		<em>Step 1 of 5</em>
	</p>

	</div> <!-- end of content-container -->
        

<script type="text/javascript">
s.events="event2";
s.eVar2="Current Invoices and Tolls Summary";

function goBack() {
    document.forms[0].action = '${pageContext.request.contextPath}/${allInvoicesForm.returnAction}.do';
    document.forms[0].submit();
}

function doSubmit(nextAction) {
    document.forms[0].action = '${pageContext.request.contextPath}/' + nextAction + '.do';
    document.forms[0].submit();
}

</script>
