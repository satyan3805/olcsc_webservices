<%@ include file="/jsp/common/Taglibs.jsp" %>
	<div id="content-container">

		<div id="content">

			<form id="account-option" action="${pageContext.request.contextPath}/invoiceDisplay.do" method="post" autocomplete="off">
            <input type="hidden" name="nextResult" value="success"/>

				<div class="wide-section">

					<h1>Account Payment Options</h1>

				<p>Please specify whether you want to use your EZ account to make a payment.</p>
				<p>Choose one of the options below:</p>
				</div> <!-- end of section -->

<fieldset>

<table summary="This table presents account payment options." class="data-table">

  <thead>
    <tr>
      <th>Yes, I have an EZ Account</th>
      <th>No, I don't have an EZ Account</th>
    </tr>
  </thead>

    <tbody>
      <tr>
        <td>&rarr; I have an EZ Tag and want to pay using my account.
          <span class="button-label" name="login" onclick="doSubmit(this);">Pay with my EZ Account</span>
        </td>
        <td>&rarr; I want to get an EZ Tag Account to reduce my payment.
          <span class="button-label" name="signup" onclick="doSubmit(this);">Get an EZ Account</span>
        </td>
      </tr>
      <tr>
        <td>
        </td>
        <td>&rarr; I don't want an EZ Tag.  I just want to make a payment.
          <span class="button-label" name="success" onclick="doSubmit(this);">Just pay Invoice</span>
        </td>
      </tr>

<!--
      <tr>
        <td>&rarr; I want to login and pay with my EZ Tag Account.
          <input type="button" name="login" value="Pay with my EZ Account" onclick="return doSubmit(this);" title="&rarr; Pay with my EZ Account" />
        </td>
        <td>&rarr; I want to get an EZ Tag Account to reduce my payment.
          <input type="button" name="signup" value="Get an EZ Account" onclick="return doSubmit(this);" title="&rarr; Get an EZ Account" />
        </td>
      </tr>
      <tr>
        <td>
        </td>
        <td>&rarr; I don't want an EZ Tag.  I just want to make a payment.
          <input type="button" name="pay" value="Just pay Invoice" onclick="return doSubmit(this);" title="&rarr; Just pay Invoice" />
        </td>
      </tr>
-->
    </tbody>

</table> <!-- end of table -->

				</fieldset>

                    <input type="hidden" name="returnAction" value="${requestScope.returnAction}"/>
			</form>

		</div> <!--end of content -->

<!-- MB 2009-12-17 - removing status as per MWK on 10/16/2006
	<p class="progress-bar" id="step-1-of-3">
		Pay Invoices
		<em>Step 1 of 3</em>
	</p>
-->
	</div> <!-- end of content-container -->


<script type="text/javascript">
s.events="event2";
s.eVar2="Account Payment Options";

function goBack() {
    document.forms[0].action = '${pageContext.request.contextPath}/${requestScope.returnAction}.do';
    document.forms[0].submit();
}

function doSubmit(button) {
    var action;
    var name = button.name;
    if (name == undefined)
        name = button.getAttribute('name');
/*
    switch (name) {
      case 'login':
        action = "violationLoginAccount";
        break;
      case 'signup':
        action = "violationSignupDisplay";
        break;
      case 'success':
        action = "invoiceDisplay";
        break;
    }
    document.forms[0].action = '${pageContext.request.contextPath}/'+action+'.do';
*/
    document.forms[0].nextResult.value = name;
    document.forms[0].submit();
    return false;
}
</script>
