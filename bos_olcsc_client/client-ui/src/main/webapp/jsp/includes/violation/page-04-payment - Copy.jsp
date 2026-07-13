<%@ include file="/jsp/common/Taglibs.jsp" %>

<jsp:useBean id="ccDelegate"  class="com.etcc.csc.delegate.CreditCardDelegate" scope="page"/>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>

<div id="content-container">

	<div id="content">

                <form id="payment" action="${pageContext.request.contextPath}/violationPaymentMethod.do" method="post" autocomplete="off">
                        <input type="hidden" name="paymentType" value="${selectedInvoiceForm.billingInfoForm.paymentType}" />
                        <input type="hidden" name="changePayMethod" value="${changePayMethod}" />

			<div class="section">

				<h1>Payment Method</h1>

                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="true">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>

<c:set value="${selectedInvoiceForm.violatorDTO.shoppingCart}" var="cart" />
<c:set value="${allInvoicesForm.violatorDTO}" var="violator" />
<%@ include file="violator-totals.jsp" %>

				<div <c:if test="${(not changePayMethod) and (not empty selectedInvoiceForm.billingInfoForm) and (not empty selectedInvoiceForm.billingInfoForm.paymentType)}">style="display:none"</c:if>>
                                <dl class="choices" id="payment-methods">

					<dt>How would you like to pay?</dt>

						<dd><a id="credit-card-link" href="#credit-card-choice" onclick="changeLink('credit');return false;">Bill my credit card</a></dd>
                                             
						   <dd><a id="withdraw-funds-link" href="#withdraw-funds-choice" onclick="changeLink('eft');return false;">Withdraw funds from my bank</a></dd>
                                           

				</dl> <!-- end of choices -->
                                </div>

			</div> <!-- end of section -->

                        <div id="credit-card" <c:if test="${changePayMethod or selectedInvoiceForm.billingInfoForm.paymentType!='credit'}"> style="display:none" </c:if>>

				<div class="section">

					<h2>Credit Card</h2>

					<fieldset>

						<dl>

							<dt class="first-dt-dd-pair"><label for="name-on-credit-card">Name on credit card:</label></dt>
								<dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="name-on-credit-card" name="billingInfoForm.nameOnCard" value="${selectedInvoiceForm.billingInfoForm.nameOnCard}" /></dd>

							<dt><label for="card-type">Card Type:</label></dt>
								<dd>
                                                                    *<select styleClass="text" id ="card-type" name="billingInfoForm.cardType">
                                                                        <option value=""></option>
                                                                        <c:forEach var="cc" items="${ccDelegate.creditCardTypes}">
          <option value="${cc.cardCode}"
          <c:if test="${selectedInvoiceForm.billingInfoForm.cardTypeEnum == cc.cardType}">selected</c:if>
          >${cc.cardName}</option>
                                                                        </c:forEach>
                                                                    </select>
								</dd>

							<dt><label for="card-number" id="for-card-number">Card Number:</label></dt>
								<dd>*<input type="text" class="textfield" id="txt-card-number" name="billingInfoForm.cardNumber" value="${selectedInvoiceForm.billingInfoForm.cardNumber}" /></dd>

							<dt>Card expires on:</dt>
								<dd>
									<label for="card-expiration-month" class="accessibility">Card expiration month:</label>

                                                                        *<select styleClass="text" id="card-expiration-month" name="billingInfoForm.cardExpirationMonth">
                                                                            <option value="">- month -</option>
                                                                            <c:forEach var="cardMonths" items="${ccDelegate.creditCardMonths}">
                                                                                <option value="${cardMonths.value}"
                                                                                <c:if test="${selectedInvoiceForm.billingInfoForm.cardExpirationMonth == cardMonths.value}">
                                                                                    selected
                                                                                </c:if>
                                                                                >${cardMonths.label}</option>
                                                                            </c:forEach>
                                                                        </select>

									<label for="card-expiration-year" class="accessibility">Card expiration year:</label>
                                                                        <select styleClass="text" id="card-expiration-year" name="billingInfoForm.cardExpirationYear">
                                                                            <option value="00">- year -</option>
                                                                            <c:forEach var="cardYears" items="${ccDelegate.creditCardYears}">
                                                                                <option value="${cardYears.value}"
                                                                                <c:if test="${selectedInvoiceForm.billingInfoForm.cardExpirationYear == cardYears.value}">
                                                                                    selected
                                                                                </c:if>
                                                                                >${cardYears.label}</option>
                                                                            </c:forEach>
                                                                        </select>

								</dd>

						</dl>

					</fieldset>

				</div> <!-- end of section -->

			</div> <!-- end of credit-card -->

                        <div id="withdraw-funds" <c:if test="${changePayMethod or selectedInvoiceForm.billingInfoForm.paymentType!='eft'}"> style="display:none" </c:if>>

				<div class="section">

					<h2 id="set-up-your-fund-withdrawal">Bank Account</h2>

					<fieldset id="eft-fields">

						<dl>

							<dt class="first-dt-dd-pair">Account type:</dt>
								<dd class="first-dt-dd-pair">
									<ul>
										<li><label for="personal-bank-account-1"><input type="radio" class="radio-button" value="PC" id="personal-bank-account-1" name="billingInfoForm.bankAcctType" <c:if test="${selectedInvoiceForm.billingInfoForm.bankAcctType=='PC' || selectedInvoiceForm.billingInfoForm.bankAcctType==null}">checked="checked"</c:if> />Personal bank account</label></li>

										<li><label for="business-bank-account-1"><input type="radio" class="radio-button" value="CC" id="business-bank-account-1" name="billingInfoForm.bankAcctType" <c:if test="${selectedInvoiceForm.billingInfoForm.bankAcctType=='CC'}">checked="checked"</c:if> />Business bank account</label></li>
									</ul>
								</dd>


							<dt><label for="bank-routing-number-1">Routing number:</label></dt>
								<dd>
									*<input type="text" class="textfield" id="bank-routing-number-1" name="billingInfoForm.bankRoutingNumber" value="${selectedInvoiceForm.billingInfoForm.bankRoutingNumber}" />
									<p class="help">The routing number is the first group of digits on your check.</p>
								</dd>
						</dl>

						<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-routing-number.gif" width="300" height="80" alt="The routing number is 1 to 17 digits between the : symbols." />

						<dl>

							<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Account number:</label></dt>
								<dd class="first-dt-dd-pair">
									*<input type="text" class="textfield" id="bank-account-number-1" name="billingInfoForm.bankAcctNumber" value="${selectedInvoiceForm.billingInfoForm.bankAcctNumber}" onblur="javascript:cleanNumericField(this);" onselect="javascript:unselect();"/>
									<p class="help">The account number is the second group of numbers on your check.</p>
								</dd>
								<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Confirm account number:</label></dt>
									<dd class="first-dt-dd-pair">
                                                                                *<input type="text" class="textfield" id="bank-account-number-2"  value="${selectedInvoiceForm.billingInfoForm.bankAcctNumber}" onselect="javascript:unselect();" />
                                                                              
                                                                        </dd>
						</dl>

						<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-account-number.gif" width="300" height="80" alt="The account number is 9 digits between the : and ^ symbols." />

					</fieldset>

				</div> <!-- end of section -->

			</div> <!-- end of withdraw-funds -->

				<!-- This contains the elements common to both the credit-card and withdraw-funds choices -->
                                <div id="common-section" class="common section" <c:if test="${changePayMethod or (empty selectedInvoiceForm.billingInfoForm) or (selectedInvoiceForm.billingInfoForm.paymentType!='eft')}"> style="display:none" </c:if>>

					<h2>Billing Address</h2>

					<fieldset id="billing-address-fields">

						<p id="non-us-address-field"><label for="non-us-address">
                                                    <input type="checkbox" class="checkbox" id="non-us-address" name="billingInfoForm.nonUsBillingAddress" onclick="javascript:clearUSAddrFields(this)"
<c:if test='${selectedInvoiceForm.billingInfoForm.nonUsBillingAddress}'>checked</c:if> />
                                                        My billing address is outside the <acronym title="United States">U.S.</acronym>
                                                    </label></p>
                                                <input type="hidden" name="billingInfoForm.nonUsBillingAddress" value="false" />

						<dl>

							<dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
								<dd class="non-us first-dt-dd-pair" id="country-dd">
                                                                <select id="country" name="billingInfoForm.country">
                                                                    <option value=""></option>
                                                                    <c:forEach var="country" items="${countryDelegate.countries}">
                                                                        <option value="<c:out value="${country.country}"/>"
                                                                        <c:if test="${country.country == selectedInvoiceForm.billingInfoForm.country}">
                                                                            selected
                                                                        </c:if>
                                                                        >
                                                                        <c:out value="${country.country}"/>
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>							
								</dd>

							<dt id="address-line-1-dt"><label for="address-line-1">Address Line 1:</label></dt>
								<dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="billingInfoForm.addressLine1" value="${selectedInvoiceForm.billingInfoForm.addressLine1}" /></dd>

							<dt><label for="address-line-2">Address Line 2:</label></dt>
								<dd>&nbsp<input type="text" class="textfield" id="address-line-2" name="billingInfoForm.addressLine2" value="${selectedInvoiceForm.billingInfoForm.addressLine2}" /></dd>

							<dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
								<dd class="non-us">&nbsp<input type="text" class="textfield" id="address-line-3" name="billingInfoForm.addressLine3" value="${selectedInvoiceForm.billingInfoForm.addressLine3}" /></dd>

							<dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
								<dd class="non-us">&nbsp<input type="text" class="textfield" id="address-line-4" name="billingInfoForm.addressLine4" value="${selectedInvoiceForm.billingInfoForm.addressLine4}" /></dd>

							<dt class="domestic"><label for="city">City:</label></dt>
								<dd class="domestic">
									*<input type="text" class="textfield" id="city" name="billingInfoForm.city" value="${selectedInvoiceForm.billingInfoForm.city}" />
									<p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if billing to a military address</p>
								</dd>

							<dt class="domestic"><label for="state-province">State/Province:</label></dt>
								<dd class="domestic">
                                                                *<select id="state" name="billingInfoForm.state">
                                                                    <c:forEach var="state" items="${stateDelegate.states}">
                                                                        <option value="<c:out value="${state.stateCode}"/>"
                                                                            <c:if test="${  (selectedInvoiceForm.billingInfoForm.state != null) ? selectedInvoiceForm.billingInfoForm.state ==  state.stateCode : state.defaultValueFlag == true}">
                                                                                selected
                                                                            </c:if>
                                                                        >
                                                                         <c:out value="${state.stateCode}"/>
                                                                        </option>
                                                                    </c:forEach> 		
                                                                </select>
								</dd>

							<dt class="domestic"><label for="zip-postal-code">Zip/Postal Code:</label></dt>
								<dd class="domestic">*<input type="text" class="textfield firstElement" id="zippostalcode" name="billingInfoForm.zip" value="${selectedInvoiceForm.billingInfoForm.zip}" />
                                                                <label>-</label>
                                                                <input type="text" class="textfield secondElement" id="plus4" name="billingInfoForm.plus4" value="${selectedInvoiceForm.billingInfoForm.plus4}" />
                                                                </dd>
						</dl>
					</fieldset>

				</div> <!-- end of common-elements -->

			 <div <c:if test="${not((not changePayMethod) and (not empty selectedInvoiceForm.billingInfoForm) and (not empty selectedInvoiceForm.billingInfoForm.paymentType))}">style="display:none"</c:if>>
                            <ul class="form-actions">
                                <c:if test="${not empty selectedInvoiceForm.returnAction}">
                                    <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                                </c:if>
				<li><img id="review-confirmation-page" src="${pageContext.request.contextPath}/meta/media/buttons/review-confirmation-page.gif"  onclick="doSubmit();" title="&rarr; page-05-confirmation.shtml" /></li>
                            </ul> <!-- end of form-actions -->
                         </div>

		</form>

	</div> <!--end of content -->

	<p class="progress-bar" id="step-3-of-4">
		Unpaid Tolls and Fees
		<em>Step 3 of 4</em>
	</p>

</div> <!-- end of content-container -->

<script type="text/javascript">
loadingBody();
function doSubmit()
{
	<c:if test="${selectedInvoiceForm.billingInfoForm.paymentType=='eft'}">
	if (document.getElementById("bank-account-number-2").value.toUpperCase() != document.getElementById("bank-account-number-1").value.toUpperCase()) {
        alert("'Confirm Bank Account Number' should match 'Bank Account Number'");
        changeTextFieldWhite(document.getElementById("bank-account-number-1"));
		changeTextFieldColor(document.getElementById("bank-account-number-2"));
		return ;
	}
	</c:if>
	document.forms[0].submit();
	return false;
}
function loadingBody(){
	getErrorfields();
}

function checkForErrors(fieldname){
    var isErrorField = false;
	if(fieldname != null && fieldname != "" && fieldname.length != 0){
		isErrorField = true;
	}
	return isErrorField;
}

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}
function getErrorfields()
{
var nameOnCard = '<html:errors property="billingInfoForm.nameOnCard"/>';
var cardNumber = '<html:errors property="billingInfoForm.cardNumber"/>';
var cardType = '<html:errors property="billingInfoForm.cardType"/>';
var cardExpirationMonth = '<html:errors property="billingInfoForm.cardExpirationMonth"/>';
var cardExpirationYear = '<html:errors property="billingInfoForm.cardExpirationYear"/>';
var bankRoutingNumber = '<html:errors property="billingInfoForm.bankRoutingNumber"/>';
var bankAcctNumber = '<html:errors property="billingInfoForm.bankAcctNumber"/>';
var addressLine1='<html:errors property="billingInfoForm.addressLine1"/>';
var city='<html:errors property="billingInfoForm.city"/>';
var zip='<html:errors property="billingInfoForm.zip"/>';
var plus4='<html:errors property="billingInfoForm.plus4"/>';

     if (checkForErrors(nameOnCard) == true)
     {
	    changeTextFieldColor(document.getElementById("name-on-credit-card"));
	 }else{
		 changeTextFieldWhite(document.getElementById("name-on-credit-card"));
	 }

    if (checkForErrors(cardNumber) == true){
		
		changeTextFieldColor(document.getElementById("txt-card-number"));
	 }else{
		 changeTextFieldWhite(document.getElementById("txt-card-number"));
	 }
    
    if (checkForErrors(cardType) == true){
		
		changeTextFieldColor(document.getElementById("card-type"));
	 }else{
		 changeTextFieldWhite(document.getElementById("card-type"));
	 }
    
    if (checkForErrors(cardExpirationMonth) == true){
		
		changeTextFieldColor(document.getElementById("card-expiration-month"));
	 }else{
		 changeTextFieldWhite(document.getElementById("card-expiration-month"));
	 }
    
    if (checkForErrors(cardExpirationYear) == true){
		
		changeTextFieldColor(document.getElementById("card-expiration-year"));
	 }else{
		 changeTextFieldWhite(document.getElementById("card-expiration-year"));
	 }

    
    if (checkForErrors(bankRoutingNumber) == true){
		
		changeTextFieldColor(document.getElementById("bank-routing-number-1"));
	 }else{
		 changeTextFieldWhite(document.getElementById("bank-routing-number-1"));
	 }
    
if (checkForErrors(bankAcctNumber) == true){
		
		changeTextFieldColor(document.getElementById("bank-account-number-1"));
	 }else{
		 changeTextFieldWhite(document.getElementById("bank-account-number-1"));
	 }
    
if (checkForErrors(bankAcctNumber) == true){
	
	changeTextFieldColor(document.getElementById("bank-account-number-2"));
 }else{
	 changeTextFieldWhite(document.getElementById("bank-account-number-2"));
 }

  
    
	if (checkForErrors(addressLine1) == true){
		changeTextFieldColor(document.getElementById("address-line-1"));
	 }else{
		 changeTextFieldWhite(document.getElementById("address-line-1"));
	 }	 
	  if (checkForErrors(city) == true){
		changeTextFieldColor(document.forms[0].city);
	 }else{
		 changeTextFieldWhite(document.forms[0].city);
	 }
	  if (checkForErrors(zip) == true){
		//changeTextFieldColor(document.forms[0].zip);
		changeTextFieldColor(document.getElementById("zippostalcode"));
	 }else{
		 //changeTextFieldWhite(document.forms[0].zip);
		 changeTextFieldWhite(document.getElementById("zippostalcode"));
	 }
	  if (checkForErrors(plus4) == true){
		 changeTextFieldColor(document.getElementById("plus4"));
		//changeTextFieldColor(document.forms[0].plus4);
	 }else{
		// changeTextFieldWhite(document.forms[0].plus4);
		  changeTextFieldWhite(document.getElementById("plus4"));
	 }
	
}

    function changeLink(payment)
    {
        document.forms[0].paymentType.value = payment;
        document.forms[0].action = "${pageContext.request.contextPath}/invoicePaymentDisplay.do";
        document.forms[0].submit();
    }
    
    function goBack()
    {
        document.forms[0].action = '${pageContext.request.contextPath}/${selectedInvoiceForm.returnAction}.do';
        document.forms[0].submit();
    }
    
    function clearUSAddrFields(chk) {
        document.forms[0].country.selectedIndex=0;
        if (chk.checked) {
            document.forms[0].city.value="";
            document.forms[0].zippostalcode.value="";
            document.forms[0].plus4.value="";
        } else {
            document.forms[0].addressline3.value="";
            document.forms[0].addressline4.value="";
        }
    }
    function changeTextFieldColor(field){
    	field.style.background="#00FFCC";
    }

    function changeTextFieldWhite(field){
    	field.style.background="#FFFFFF";
    }
</script>
