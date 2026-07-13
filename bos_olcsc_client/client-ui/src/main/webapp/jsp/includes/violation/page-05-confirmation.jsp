<%@ include file="/jsp/common/Taglibs.jsp" %>

<jsp:useBean id="ccDelegate"  class="com.etcc.csc.delegate.CreditCardDelegate" scope="page"/>

<div id="content-container">

	<div id="content">

		<form id="confirm" action="${pageContext.request.contextPath}/invoicePayment.do" method="post" autocomplete="off">

			<h1>Confirm Payment</h1>

                        <div class="section">
                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                <html:messages id="msg" message="false">
                                    <dd><bean:write name="msg"/></dd>
                                </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true">
                                <dl class="errors"/>
                                <html:messages id="msg" message="true" property="violationError">
                                    <dd>${msg}</dd>
                                </html:messages>
                            </logic:messagesPresent>
                        </div>

                        <input type="hidden" name="requestedPageNum" value="1"/>

                        <div class="section">

<c:set value="${allInvoicesForm.violatorDTO}" var="violator" />
<c:set value="${selectedInvoiceForm.violatorDTO}" var="cart" />
<%@ include file="violator-totals.jsp" %>

					<fieldset>
						<dl>
							<dt><label for="email-address">Please provide an e-mail address if you would like a receipt via e-mail:</label></dt>
								<dd><input type="text" class="textfield" id="email-address" name="billingInfoForm.email" value="${selectedInvoiceForm.billingInfoForm.email}" /></dd>
						</dl>
					</fieldset>

                                        <h2 id="is-this-correct">Is this correct? <em>Please review everything to make sure it is accurate.</em></h2>

					<p>Note:  It may take up to five business days for recent violations and/or payments to be reflected on your balance.</p>

			</div> <!-- end of section -->

<h4 id="total-cost">Payment Summary</h4>
<%@ include file="cart-summary.jsp" %>

				<div class="section">
				<p>If you need to change these payments, <a href="javascript:returnToInvoices()">return to the list of invoices</a>.</p>

					<h4 id="billing-information">Billing Information</h4>

					<dl id="payment-method" class="immediately-following-an-h4">
						<dt>Payment Method: <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your payment method" onclick="javascript:gotoBillingInfo();return false;" /></dt>
	<dd>
  <etcc-extended:format billingInfo="${selectedInvoiceForm.billingInfoForm}" mask="ACCOUNT" displayName="false" />
	</dd>

					</dl> <!-- end of payment-method -->

					<dl id="billing-address">
						<dt>Billing Address: <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your billing address" onclick="javascript:gotoBillingInfo('${selectedInvoiceForm.billingInfoForm.paymentType}');return false;" /></dt>
							<dd>
  <address><etcc-extended:format address="${selectedInvoiceForm.billingInfoForm}" /></address>
							</dd>

					</dl> <!-- end of billing-address -->

			</div> <!-- end of section -->
			<ul class="form-actions">

				<c:if test="${not empty selectedInvoiceForm.returnAction}">
                                    <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                                </c:if>
                                <li>
                                 <table align="right">
					                                                       <tr> <td colspan="20" align="right">  <input id="continue" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/checkout.gif" value="Check Out" alt="Check Out" title="This is a one-time payment only. Multiple clicks may result in multiple payments." onclick="javascript:doSubmit(); return false;"/> </td></tr>
					       					<tr> <td align="right"><strong>** Please Note:</strong> This is a one-time payment only. <br> Multiple clicks may result in multiple payments.  </td></tr>
				   </table>
                               </li>

			</ul> <!-- end of form-actions -->

		</form>

	</div> <!-- end of content -->

	<p class="progress-bar" id="step-4-of-4">
		Unpaid Tolls and Fees
		<em>Step 4 of 4</em>
	</p>

</div> <!-- end of content-container -->


<script type="text/javascript">
var submitted = false;
s.events="scCheckout";
s.products=";Violation Invoice Payment - "+ "${selectedInvoiceForm.billingInfoForm.paymentType}" + ";1;" + "${cart.payment}";

function gotoBillingInfo(paymentType)
{
    var desUrl ="${pageContext.request.contextPath}/returnToInvoicePaymentDisplay.do";
    if (paymentType != 'credit' && paymentType != 'eft' )
        desUrl = desUrl + "?changePayMethod=y"

    document.forms[0].action=desUrl;
    document.forms[0].submit();
}


function goBack()
{
    document.forms[0].action = '${pageContext.request.contextPath}/${selectedInvoiceForm.returnAction}.do';
    document.forms[0].submit();
}

function doSubmit() {
    if (!submitted) {

       // Grey's out and disables the Check out button

    	document.getElementById("continue").disabled= true;
    	document.getElementById("continue").style.opacity="0.3";
		document.getElementById("continue").style.filter = "alpha(opacity=40)";


		//document.getElementById("activate-account").style.filter = "gray()";
    	//document.getElementById("continue").disabled=true;
    	//document.getElementById("continue").src="${pageContext.request.contextPath}/meta/media/buttons/checkout_grey.gif";
        submitted = true;
        document.forms[0].submit();
    }
}

function returnToInvoices() {
    document.forms[0].action = '${pageContext.request.contextPath}/invoiceDisplay.do';
    document.forms[0].submit();
}
</script>
