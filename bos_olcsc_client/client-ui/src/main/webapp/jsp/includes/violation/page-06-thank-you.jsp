<%@ include file="/jsp/common/Taglibs.jsp" %>

<div id="content-container">

        <div id="content">

                <form id="thank-you" action="${pageContext.request.contextPath}/violatorSummary.do" method="post">

                        <h1>Thank you</h1>

                        <div class="section">
            <logic:messagesPresent message="true" property="alerts">
              <dl class="alerts"><dt>
                <html:messages id="msg" message="true" property="alerts">
                  <dd>${msg}</dd>
                </html:messages>
              </dt></dl>
            </logic:messagesPresent>
<!-- 1 -->
                            <logic:messagesPresent message="false">
                                <dl class="errors"><dt>
                                <html:messages id="msg" message="false">
                                    <dd><bean:write name="msg"/></dd>
                                </html:messages>
                                </dt></dl>
                            </logic:messagesPresent>
<!-- 2
                            <logic:messagesPresent message="true">
                                <dl class="errors"><dt>
                                <html:messages id="msg" message="true">
                                    <dd>${msg}</dd>
                                </html:messages>
                                </dt></dl>
                            </logic:messagesPresent>
3 -->
                        </div>

                        <div class="section">
  <dl class="introduction">
    <dt>Please retain a copy of this receipt for your records.</dt>
    <dd class="print"><a href="javascript:window.print();">Print this document</a></dd>
<%-- MB: Receipt is automatically sent if e-mail provided on prior screen
    <dd class="email" <c:if test="${(empty selectedInvoiceForm.billingInfoForm.email)}">style="display:none"</c:if>> <a href="javascript:emailViolation();" >Email me this receipt</a></dd>
--%>
  </dl>
                        </div> <!-- end of section -->

<c:set value="${allInvoicesForm.violatorDTO}" var="violator" />
<c:set value="${selectedInvoiceForm.violatorDTO}" var="cart" />
<jsp:useBean id="paymentDate" class="java.util.Date" />
<%@ include file="violator-totals.jsp" %>

<%@ include file="cart-summary.jsp" %>

                                <div class="section">

                                        <h4 id="billing-information">Billing Information</h4>

                                        <dl id="payment-method" class="immediately-following-an-h4">
						<dt>Payment Method: </dt>
							<dd>
  <etcc-extended:format billingInfo="${selectedInvoiceForm.billingInfoForm}" mask="ALL" displayName="false" />
                                                        </dd>

                                        </dl> <!-- end of payment-method -->

                                        <dl id="billing-address">
                                                <dt>Billing Address:</dt>
                                                        <dd>
  <address><etcc-extended:format address="${selectedInvoiceForm.billingInfoForm}" /></address>
                                                        </dd>

                                        </dl> <!-- end of billing-address -->

                        </div> <!-- end of section -->

    <ul class="form-actions">
<c:if test="${violator.remainingBalance > 0}">
      <li><a href="javascript:returnToSummary()"><img class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/return-to-invoices.gif" alt="Return" /></a></li>
</c:if>
      <li><a href="javascript:returnToAccount()"><img class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/manage-your-account.gif" alt="Manage Account" /></a></li>
  <!--    <li><a href="${pageContext.request.contextPath}/"><img class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/manage-your-account.gif" alt="Manage Account" /></a></li> -->
    </ul>

                </form>

        </div> <!-- end of content -->

	<p class="progress-bar" id="step-5-of-4">
		Unpaid Tolls and Fees
		<em>Complete</em>
	</p>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="purchase,event3";
s.products=";Violation Invoice Payment - "+ "${selectedInvoiceForm.billingInfoForm.paymentType}" + ";1;" + "${cart.payment}";
s.purchaseID="${confirmationNumber}";

function goBack() {
    document.forms[0].action = '${pageContext.request.contextPath}/${selectedInvoiceForm.returnAction}.do';
    document.forms[0].submit();
}

function emailViolation() {
        /*
        var contextPath = "${pageContext.request.contextPath}";
        window.open(contextPath+"/emailViolationPaymentReceipt.do","","width=800,height=500,resizable");
        */
    document.forms[0].action = '${pageContext.request.contextPath}/emailViolationPaymentReceipt.do';
    document.forms[0].submit();
}

function returnToSummary() {
    alert('Note:  It may take up to five (5) business days for recent unpaid tolls, violations, and/or payments to be reflected on your balance.');
    document.forms[0].submit();
}

function returnToAccount() {
    alert('Note:  It may take up to five (5) business days for recent unpaid tolls, violations, and/or payments to be reflected on your balance.');
	document.forms[0].action = '${pageContext.request.contextPath}';
    document.forms[0].submit();
}

</script>
