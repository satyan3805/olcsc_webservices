<%@ page import="com.etcc.csc.util.SessionUtil" %>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="ccDelegate"  class="com.etcc.csc.delegate.CreditCardDelegate" scope="page"/>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/litle-api2.js"></script>

<%
//AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
//AccountDelegate acctDel = new AccountDelegate();
//AccountDTO acctInfo = acctDel.getAccount(acctLoginDto, acctLoginDto.getAcctId());
SessionUtil.getAcctDTO(request);
%>
<body onload="loadingBody()" >
<div id="content-container">

	<div id="content">

                <etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changePaymentMethodcc.do" styleId="mainForm">
                <input type="hidden" name="paymentType" value="credit" />
                <!--  For PayPage getting registration id -->
                <input type="hidden" id="pagePageRequestUrl" name="pagePageRequestUrl" value="${LITLE_PAYPAGE_URL}"/>
                <input type="hidden" id="request$paypageId" name="request$paypageId" value="${LITLE_PAYPAGE_ID}"/>
				<input type="hidden" id="request$merchantTxnId" name="request$merchantTxnId" value="<%=Long.toString((System.currentTimeMillis()))%>"/>
				<input type="hidden" id="request$orderId" name="request$orderId" value="<%=Long.toString((System.currentTimeMillis()))%>"/>
				<input type="hidden" id="request$reportGroup" name="request$reportGroup" value="${LITLE_REPORT_GROUP}"/>



				<input type="hidden" id="response$paypageRegistrationId" name="response$paypageRegistrationId" readOnly="true" value=""/>
				<input type="hidden" id="response$bin" name="response$bin" readOnly="true"/>
				<input type="hidden" id="response$code" name="response$code" readOnly="true"/>
				<input type="hidden" id="response$message" name="response$message" readOnly="true"/>
				<input type="hidden" id="response$responseTime" name="response$responseTime" readOnly="true"/>
				<input type="hidden" id="response$type" name="response$type" readOnly="true"/>
				<input type="hidden" id="response$litleTxnId" name="response$litleTxnId" readOnly="true"/>

	<input type="hidden" name="accountClosure" value="${closure || billingInfoForm.accountClosure}" />

		<h1 id="account-information-edit-username">Account Information &ndash; edit billing and payment information</h1>

			<div class="section">
                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changePaymentMethodError">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>

				<c:if test="${billingInfoForm.paymentType == 'credit'}"><p>Your current payment method is to <strong>bill your credit card</strong>.</c:if>
                                If you prefer, you can change your payment method to
                                    <a href="#withdraw-funds-choice" onclick="withdrawFundLink(); return false;" > withdraw funds from your bank</a>.</p>

				<h2 id="payment-information">Billing Information - Credit Card</h2>

				<fieldset>
					<dl>

						<dt class="first-dt-dd-pair"><label for="name-on-credit-card">Name on credit card:</label></dt>
							<dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="name-on-credit-card" name="nameOnCard" value="${billingInfoForm.nameOnCard}" onblur="javascript:removeUnwantedChar(this);"/></dd>

						<dt><label for="card-type">Card Type:</label></dt>
							<dd>
                                                                *<select id="card-type" styleClass="text" name="cardType" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> >
                                                                    <option value=""></option>
                                                                    <c:forEach var="cc" items="${ccDelegate.creditCardTypes}">
                                                                        <option value="${cc.cardCode}"
                                                                            <c:if test="${billingInfoForm.cardType == cc.cardCode}">
                                                                                selected
                                                                            </c:if>
                                                                        >${cc.cardName}</option>
                                                                    </c:forEach>
                                                                </select>
							</dd>

						<dt><label for="card-number" id="for-card-number">Card Number:</label></dt>
							<dd>*<input type="text" class="textfield" id="card-number" name="cardNumber" value="${billingInfoForm.cardNumber}" onblur="javascript:removeUnwantedChar(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt>Card expires on:</dt>
							<dd>
								<label for="card-expiration-month" class="accessibility">Card expiration month:</label>
                                                                *<select styleClass="text" id="card-expiration-month" name="cardExpirationMonth" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>>
                                                                    <option value="">- month -</option>
                                                                    <c:forEach var="cardMonths" items="${ccDelegate.creditCardMonths}">
                                                                        <option value="${cardMonths.value}"
                                                                            <c:if test="${billingInfoForm.cardExpirationMonth == cardMonths.value}">
                                                                                selected
                                                                            </c:if>
                                                                        >${cardMonths.label}</option>
                                                                    </c:forEach>
                                                                </select>

								<label for="card-expiration-year" class="accessibility">Card expiration year:</label>
                                                                <select styleClass="text" id="card-expiration-year" name="cardExpirationYear"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>>
                                                                    <option value="">- year -</option>
                                                                    <c:forEach var="cardYears" items="${ccDelegate.creditCardYears}">
                                                                        <option value="${cardYears.value}"
                                                                        <c:if test="${billingInfoForm.cardExpirationYear == cardYears.value}">
                                                                            selected
                                                                        </c:if>
                                                                        >${cardYears.label}</option>
                                                                    </c:forEach>
                                                                </select>

								<!-- p class="alternate-credit-card-selection"><label for="alternate-credit-card"><input type="checkbox" class="checkbox" id="alternate-credit-card" name="reserveCard"
                                                                    <c:if test='${billingInfoForm.reserveCard}'>checked</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> />
                                                                    I would like to provide a secondary credit card</label></p>
                                                                <input type="hidden" name="reserveCard" value="false"-->
							</dd>

							<dt>CVV:</dt>
								<dd>*<input type="text" class="textfield" id="cvv" name="cvv" value="" max="4" maxlength="4" style="width:35px" /></dd>
					</dl>

					<!-- dl id="alternate-credit-card-fields">

						<dt><label for="name-on-alternate-credit-card">Name on secondary credit card:</label></dt>
							<dd>*<input type="text" class="textfield" id="name-on-alternate-credit-card" name="nameOnReserveCard" value="${billingInfoForm.nameOnReserveCard}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt><label for="alternate-card-type">Secondary Card Type:</label></dt>
							<dd>
                                                                *<select id="alternate-card-type" styleClass="text" name="reserveCardType" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>>
                                                                    <option value=""></option>
                                                                    <c:forEach var="cc" items="${ccDelegate.creditCardTypes}">
                                                                        <option value="${cc.cardCode}"
                                                                            <c:if test="${billingInfoForm.reserveCardType == cc.cardCode}">
                                                                                selected
                                                                            </c:if>
                                                                        >${cc.cardName}</option>
                                                                    </c:forEach>
                                                                </select>
							</dd>

						<dt><label for="alternate-card-number" id="for-alternate-card-number">Secondary Card Number:</label></dt>
							<dd>*<input type="text" class="textfield" id="alternate-card-number" name="reserveCardNumber" value="${billingInfoForm.reserveCardNumber}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt>Secondary Card expires on:</dt>
							<dd>
								<label for="alternate-card-expiration-month" class="accessibility">Card expiration month:</label>
                                                                *<select styleClass="text" id="alternate-card-expiration-month" name="reserveCardExpirationMonth" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> >
                                                                    <option value="">- month -</option>
                                                                    <c:forEach var="cardMonths" items="${ccDelegate.creditCardMonths}">
                                                                        <option value="${cardMonths.value}"
                                                                        <c:if test="${billingInfoForm.reserveCardExpirationMonth == cardMonths.value}">
                                                                            selected
                                                                        </c:if>
                                                                        >${cardMonths.label}</option>
                                                                    </c:forEach>
                                                                </select>

								<label for="alternate-card-expiration-year" class="accessibility">Alternate Card expiration year:</label>
                                                                <select styleClass="text" id="alternate-card-expiration-year" name="reserveCardExpirationYear" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> >
                                                                    <option value="">- year -</option>
                                                                    <c:forEach var="cardYears" items="${ccDelegate.creditCardYears}">
                                                                        <option value="${cardYears.value}"
                                                                        <c:if test="${billingInfoForm.reserveCardExpirationYear == cardYears.value}">
                                                                            selected
                                                                        </c:if>
                                                                        >${cardYears.label}</option>
                                                                    </c:forEach>
                                                                </select>
							</dd>
					</dl-->

				</fieldset>

			</div> <!-- end of section -->

			<div class="section">

				<h2>Billing Address</h2>

				<p>Your mailing address is:</p>

				<address class="mailing-label">
                                        ${acctInfo.firstName} ${acctInfo.middleInitial} ${acctInfo.lastName} <br />
                                        ${acctInfo.addressDisplay}
				</address>

				<p class="billing-address-is-different-from-mailing-selection"><label for="billing-address-is-different-from-mailing">
                                    <input type="checkbox" class="checkbox" id="billing-address-is-different-from-mailing" name="diffBillingAddress"  onclick="javascript:clearAddrFields(this)"
                                    <c:if test='${billingInfoForm.diffBillingAddress}'>checked</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>
                                    /> My billing address is different than my mailing address</label></p>
                                    <input type="hidden" name="diffBillingAddress" value="false">

				<fieldset id="billing-address-fields">

					<p id="non-us-address-field"><label for="non-us-address"><input type="checkbox" class="checkbox" id="non-us-address" name="nonUsBillingAddress"  onclick="javascript:clearUSAddrFields(this)"
                                            <c:if test='${billingInfoForm.nonUsBillingAddress}'>checked</c:if>
                                            <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>/> My billing address is outside the <acronym title="United States">U.S.</acronym></label></p>
                                            <input type="hidden" name="nonUsBillingAddress" value="false">

					<dl>

						<dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
							<dd class="non-us first-dt-dd-pair" id="country-dd">
                                                                <select id="country" name="country" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>>

                                                        <c:forEach var="country" items="${countryDelegate.countries}">
                                                            <option value="<c:out value="${country.countryCode}"/>"
                                                            <c:set var="str1" value="${country.countryCode}" />
                                                            <c:set var="str2" value="${billingInfoForm.country}" />
                                                            <jsp:useBean id="str1" type="java.lang.String" />
                                                            <jsp:useBean id="str2" type="java.lang.String" />
                                                            <c:if test="<%= str1.equalsIgnoreCase(str2) %>" >
                                                                selected
                                                            </c:if>
                                                            >
                                                            <c:out value="${country.country}"/>
                                                            </option>
                                                        </c:forEach>

                                                                </select>
							</dd>

						<dt id="address-line-1-dt"><label for="address-line-1">Address Line 1:</label></dt>
							<dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="addressLine1" value="${billingInfoForm.addressLine1}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> /></dd>

						<dt><label for="address-line-2">Address Line 2:</label></dt>
							<dd>&nbsp;<input type="text" class="textfield" id="address-line-2" name="addressLine2" value="${billingInfoForm.addressLine2}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
							<dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-3" name="addressLine3" value="${billingInfoForm.addressLine3}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>/></dd>

						<dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
							<dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-4" name="addressLine4" value="${billingInfoForm.addressLine4}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>/></dd>

						<dt class="domestic"><label for="city">City:</label></dt>
							<dd class="domestic">
								*<input type="text" class="textfield" id="city" name="city" value="${billingInfoForm.city}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>/>
								<p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if billing to a military address</p>
							</dd>

						<dt class="domestic"><label for="state-province">State/Province:</label></dt>
							<dd class="domestic">
                                                                *<select id="state" name="state" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if>>
                                                                    <c:forEach var="state" items="${stateDelegate.states}">
                                                                        <option value="<c:out value="${state.stateCode}"/>"
                                                                            <c:if test="${  (billingInfoForm.state != null) ? billingInfoForm.state ==  state.stateCode : state.defaultValueFlag == true}">
                                                                                selected
                                                                            </c:if>
                                                                        >
                                                                         <c:out value="${state.stateCode}"/>
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
							</dd>

						<dt class="domestic"><label for="zip-postal-code">Zip/Postal Code:</label></dt>
							<dd class="domestic">*<input type="text" class="textfield firstElement" id="zip-postal-code" name="zip" value="${billingInfoForm.zip}" onblur="javascript:cleanNumericField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> />
                                                        <label>-</label>
                                                        <input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${billingInfoForm.plus4}" onblur="javascript:cleanNumericField(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>/></dd>
					</dl>

				</fieldset>

			</div> <!-- end of section -->


			<!--  Add and Remove Credit Card  -->
			<div class="section" id="creditCardList"> <!-- start of section -->
				<p class="buttons"> <input type="button" class="button" id="addCC" name="addCC" value="Add"/> <input type="button" class="button" id="reset" name="rest" value="Reset"/> </p>
				<label>Credit Card List</label>
				<table class="data-table" id="ccTable">
					<thead>
						<tr>
							<th scope="col">Credit Card Holder</th>
							<th scope="col">Credit Card Type</th>
							<th scope="col">Last 4 digit</th>
							<th scope="col">Expiration Date</th>
							<th scope="col">Default Payment</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${creditCardList}" var="item" varStatus="i">
							<tr>
							<th scope="col">${item.nameOnCard}</th>
							<th scope="col">${item.cardType}</th>
							<th scope="col">${item.cardNbr}</th>
							<th scope="col">${item.cardExpires}</th>
							<th scope="col"><input type="checkbox" id="defaultPayment" <c:if test ="${item.primary == true}">checked</c:if> rowIndex="${i.index}"/></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div> <!-- end of section -->

			<ul class="form-actions">
                                <c:if test="${closure || billingInfoForm.accountClosure}">
                                  <input type="hidden" name="acctInfoRefund" value="true"/>
                                </c:if>
  <li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do<c:if test="${closure || billingInfoForm.accountClosure}">?rf=true&rt=C</c:if>'; return false" title="&rarr; account-information.shtml" /></li>
                                <li><input id="save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="document.forms[0].submit(); return false;" title="&rarr; account-information.shtml" /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<!--#include virtual="/includes/manage/status-bar-manage.shtml" -->
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Credit Card";

function loadingBody(){
	getErrorfields();
}
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function checkForErrors(fieldname){
    var isErrorField = false;
	if(fieldname != null && fieldname != "" && fieldname.length != 0){
		isErrorField = true;
	}
	return isErrorField;
}

function getErrorfields(){


var nameOnCard = '<html:errors property="nameOnCard"/>';
var cardNumber = '<html:errors property="cardNumber"/>';
var nameOnReserveCard ='<html:errors property="nameOnReserveCard"/>';
var reserveCardNumber ='<html:errors property="reserveCardNumber"/>';
var addressLine1 ='<html:errors property="addressLine1"/>';
var addressLine2 ='<html:errors property="addressLine2"/>';
var	addressLine3 ='<html:errors property="addressLine3"/>';
var	addressLine4 ='<html:errors property="addressLine4"/>';
var	city ='<html:errors property="city"/>';
var	zip ='<html:errors property="zip"/>';
var	plus4 ='<html:errors property="plus4"/>';
	 if (checkForErrors(nameOnCard) == true){
		changeTextFieldColor(document.forms[0].nameOnCard);
	 }else{
		 changeTextFieldWhite(document.forms[0].nameOnCard);
	 }
	 if (checkForErrors(cardNumber) == true){
		changeTextFieldColor(document.forms[0].cardNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].cardNumber);
	 }

	  if (checkForErrors(nameOnReserveCard) == true){
		changeTextFieldColor(document.forms[0].nameOnReserveCard);
	 }else{
		 changeTextFieldWhite(document.forms[0].nameOnReserveCard);
	 }
	  if (checkForErrors(reserveCardNumber) == true){
		changeTextFieldColor(document.forms[0].reserveCardNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].reserveCardNumber);
	 }
	  if (checkForErrors(addressLine1) == true){
		changeTextFieldColor(document.forms[0].addressLine1);
	 }else{
		 changeTextFieldWhite(document.forms[0].addressLine1);
	 }
	  if (checkForErrors(addressLine2) == true){
		changeTextFieldColor(document.forms[0].addressLine2);
	 }else{
		 changeTextFieldWhite(document.forms[0].addressLine2);
	 }
	  if (checkForErrors(addressLine3) == true){
		changeTextFieldColor(document.forms[0].addressLine3);
	 }else{
		 changeTextFieldWhite(document.forms[0].addressLine3);
	 }
	  if (checkForErrors(addressLine4) == true){
		changeTextFieldColor(document.forms[0].addressLine4);
	 }else{
		 changeTextFieldWhite(document.forms[0].addressLine4);
	 }
	 if (checkForErrors(city) == true){
		changeTextFieldColor(document.forms[0].city);
	 }else{
		 changeTextFieldWhite(document.forms[0].city);
	 }
	 if (checkForErrors(zip) == true){
		changeTextFieldColor(document.forms[0].zip);
	 }else{
		 changeTextFieldWhite(document.forms[0].zip);
	 }
	 if (checkForErrors(plus4) == true){
		changeTextFieldColor(document.forms[0].plus4);
	 }else{
		 changeTextFieldWhite(document.forms[0].plus4);
	 }
}


function withdrawFundLink() {
    response=confirm('Changing your account payment method from a credit card to direct debit (EFT) will increase your required minimum deposit balance from $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForCC}" minFractionDigits="2" maxFractionDigits="2"/> to $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForEFT}" minFractionDigits="2" maxFractionDigits="2"/> which will be replenished when your account balance falls below $<fmt:formatNumber value="${accountForm.reqLowBalLevelForEFT}" minFractionDigits="2" maxFractionDigits="2"/>. \n\nIf you agree, please click OK to continue or click CANCEL to return to the previous screen.');
    if (response==true) {
        document.forms[0].paymentType.value="eft";
        document.forms[0].action= "${pageContext.request.contextPath}/accountInformation/dispChangePaymentMethod.do";
        document.forms[0].submit();
    } else {
        return false;
    }
}

function clearUSAddrFields(chk) {
    if (chk.checked) {
        document.forms[0].city.value="";
        document.forms[0].zip.value="";
        document.forms[0].plus4.value="";
        document.forms[0].country.selectedIndex=0;
    } else {
        document.forms[0].addressLine3.value="";
        document.forms[0].addressLine4.value="";
    }
}

function clearAddrFields(chk) {
    if (chk.checked) {
        document.forms[0].addressLine1.value="";
        document.forms[0].addressLine2.value="";
        document.forms[0].addressLine3.value="";
        document.forms[0].addressLine4.value="";
        document.forms[0].city.value="";
        document.forms[0].zip.value="";
        document.forms[0].plus4.value="";
        document.forms[0].country.selectedIndex=0;
    }
}

</script>
