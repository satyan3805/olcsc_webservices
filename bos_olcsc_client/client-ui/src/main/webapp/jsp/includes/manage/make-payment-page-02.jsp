<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<jsp:useBean id="creditCardDelegate" class="com.etcc.csc.delegate.CreditCardDelegate" scope="page"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/litle-api2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jquery-1.7.1.js"></script>
<div id="content-container">

	<div id="content">

		<form id="make-payments"  name="billingInfoForm" action="${pageContext.request.contextPath}/accountPaymentConfirmationDisplay.do" method="post" autocomplete="off">

			<input type="hidden" id="paymentType" name="paymentType" value="${billingInfoForm.paymentType}"/>
                        <input type="hidden" id="ezTagsExist" name="ezTagsExist" value="${ezTagsExist}"/>
                        <input type="hidden" id="mutipleUpload" name="mutipleUpload" value="${mutipleUpload}"/>
                        <c:if test="${changePayMethod or billingInfoForm.paymentType=='credit'}">
                        <input type="hidden" id="pagePageRequestUrl" name="pagePageRequestUrl" value="${LITLE_PAYPAGE_URL}"/>
                		<input type="hidden" id="request$paypageId" name="request$paypageId" value="${LITLE_PAYPAGE_ID}"/>
						<input type="hidden" id="request$merchantTxnId" name="request$merchantTxnId" value="<%=Long.toString((System.currentTimeMillis()))%>"/>
						<input type="hidden" id="request$orderId" name="request$orderId" value="<%=Long.toString((System.currentTimeMillis()))%>"/>
						<input type="hidden" id="request$reportGroup" name="request$reportGroup" value="${LITLE_REPORT_GROUP}"/>

						<input type="hidden" id="response$bin" name="response$bin" readOnly="true"/>
						<input type="hidden" id="response$code" name="response$code" readOnly="true"/>
						<input type="hidden" id="response$message" name="response$message" readOnly="true"/>
						<input type="hidden" id="response$responseTime" name="response$responseTime" readOnly="true"/>
						<input type="hidden" id="response$type" name="response$type" readOnly="true"/>
						<input type="hidden" id="response$litleTxnId" name="response$litleTxnId" readOnly="true"/>
						<input type="hidden" id="response$paypageRegistrationId" name="response$paypageRegistrationId" />
						<input type="hidden" id="paypageRegistrationId" name="paypageRegistrationId" />
                        </c:if>
                        <h1 id="account-activity-transactions">Make a payment</h1>
			<c:if test="${retPageInfo}">
                              <input type="hidden" value="info" name="pageReturn" />
                        </c:if>
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
                                            <html:messages id="msg" message="true" property="makePaymentError">
                                                <dd>${msg}</dd>
                                            </html:messages>
                                        </logic:messagesPresent>
                                    </div>

					<h4>Total payment: $<fmt:formatNumber
                                                value="${billingContext.paymentAmt}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2"/></h4>

					<div <c:if test="${(not changePayMethod) and (not empty billingInfoForm) and (not empty billingInfoForm.paymentType)}">style="display:none"</c:if>>
                                            <dl class="choices">

                                                    <dt>How would you like to pay?</dt>

                                                            <dd><a href="javascript:creditCardLink();">Bill my credit card</a></dd>
                                                            <dd><a href="javascript:withdrawFundLink();">Withdraw funds from my bank</a></dd>

                                            </dl> <!-- end of choices -->
                                        </div>

				</div> <!-- end of section -->

				<div id="credit-card" <c:if test="${changePayMethod or billingInfoForm.paymentType!='credit'}"> style="display:none" </c:if>>

					<div class="section">

						<h2>Credit Card</h2>

						<fieldset>

                                                    <dl>

                                                        <dt class="first-dt-dd-pair"><label for="name-on-credit-card">Name on credit card:</label></dt>
                                                                <dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="name-on-credit-card" name="nameOnCard" value="${billingInfoForm.nameOnCard}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt><label for="card-type">Card Type:</label></dt>
                                                                <dd>
                                                                        *<select id="card-type" name="cardType">
                                                                            <option value=""></option>
                                                                            <c:forEach var="card" items="${creditCardDelegate.creditCardTypes}">
                                                                                <option value="${card.cardCode}" <c:if test="${billingInfoForm.cardType == card.cardCode}">selected</c:if>>${card.cardName}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                </dd>

                                                        <dt><label for="card-number" id="for-card-number">Card Number:</label></dt>
                                                                <dd>*<input type="text" class="textfield" id="card-number" name="cardNumber" value="${billingInfoForm.cardNumber}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt>Card expires on:</dt>
                                                                <dd>
                                                                        <label for="card-expiration-month" class="accessibility">Card expiration month:</label>
                                                                        *<select id="card-expiration-month" name="cardExpirationMonth">
                                                                                <option value=""></option>
                                                                                <c:forEach var="cardMonths" items="${creditCardDelegate.creditCardMonths}">
                                                                                    <option value="${cardMonths.value}"
                                                                                    <c:if test="${billingInfoForm.cardExpirationMonth == cardMonths.value}">
                                                                                        selected
                                                                                    </c:if>
                                                                                    >${cardMonths.label}</option>
                                                                                </c:forEach>
                                                                        </select>

                                                                        <label for="card-expiration-year" class="accessibility">Card expiration year:</label>
                                                                        *<select id="card-expiration-year" name="cardExpirationYear">
                                                                            <option value=""></option>
                                                                            <c:forEach var="expYear" items="${creditCardDelegate.creditCardYears}">
                                                                                <option value="${expYear.value}"
                                                                                    <c:if test="${billingInfoForm.cardExpirationYear == expYear.value}">
                                                                                        selected
                                                                                    </c:if>
                                                                                >${expYear.label}</option>
                                                                            </c:forEach>
                                                                        </select>

                                                                </dd>
                                                </dl>



                                            </fieldset>

                                            <h2>Billing Address</h2>

                                            <fieldset id="billing-address-fields">

                                                <p id="non-us-address-field"><label for="non-us-address"><input type="checkbox" class="checkbox" id="non-us-address" name="nonUsBillingAddress" onclick="javascript:clearUSAddrFields(this)"
                                                    <c:if test='${billingInfoForm.nonUsBillingAddress}'>checked</c:if>
                                                /> My billing address is outside the <acronym title="United States">U.S.</acronym></label></p>
                                                <input type="hidden" name="nonUsBillingAddress" value="false">

                                                <dl>

                                                        <dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
                                                                <dd class="non-us first-dt-dd-pair" id="country-dd">
                                                                        *<select id="country" name="country">
                                                                            <option value=""></option>

                                                        <c:forEach var="rec" items="${countryDelegate.countries}">
                    <option value="${rec.countryCode}"<c:if test="${rec.countryCode == billingInfoForm.country }"> selected </c:if>>${rec.country}</option>
                                                        </c:forEach>

                                                                        </select>
                                                                </dd>

                                                        <dt id="address-line-1-dt"><label for="address-line-1">Address Line 1:</label></dt>
                                                                <dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="addressLine1" value="${billingInfoForm.addressLine1}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt><label for="address-line-2">Address Line 2:</label></dt>
                                                                <dd>&nbsp;<input type="text" class="textfield" id="address-line-2" name="addressLine2" value="${billingInfoForm.addressLine2}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
                                                                <dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-3" name="addressLine3" value="${billingInfoForm.addressLine3}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
                                                                <dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-4" name="addressLine4" value="${billingInfoForm.addressLine4}" onblur="javascript:removeUnwantedChar(this);"/></dd>

                                                        <dt class="domestic"><label for="city">City:</label></dt>
                                                                <dd class="domestic">
                                                                        *<input type="text" class="textfield" id="city" name="city" value="${billingInfoForm.city}" onblur="javascript:removeUnwantedChar(this);"/>
                                                                        <p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if billing to a military address</p>
                                                                </dd>

                                                        <dt class="domestic"><label for="state-province">State/Province:</label></dt>
                                                                <dd class="domestic">
                                                                        *<select id="state-province" name="state">
                                                                            <c:forEach var="state" items="${stateDelegate.states}">
                                                                                <option value="<c:out value="${state.stateCode}"/>"
                                                                                    <c:if test="${((not empty billingInfoForm.state) and (billingInfoForm.state==state.stateCode)) or ((empty billingInfoForm.state) and (state.defaultValueFlag == true))}">
                                                                                        selected
                                                                                    </c:if>
                                                                                >
                                                                                 <c:out value="${state.stateCode}"/>
                                                                                </option>
                                                                            </c:forEach>
                                                                        </select>
                                                                </dd>

                                                        <dt class="domestic"><label for="zip-postal-code">Zip/Postal Code:</label></dt>
                                                                <dd class="domestic">*<input type="text" class="textfield firstElement" id="zip-postal-code" name="zip" value="${billingInfoForm.zip}" onblur="javascript:cleanNumericField(this);"/>
                                                                <label>-</label>
                                                                <input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${billingInfoForm.plus4}" onblur="javascript:cleanNumericField(this);"/>
                                                                </dd>
                                                </dl>

                                             <!--   <p id="primary-card-for-rebilling-field"><label for="primary-card-for-rebilling"><input type="checkbox" class="checkbox" id="primary-card-for-rebilling" name="primary-card-for-rebilling" /> Make this my primary credit card for EZ Account rebilling.</label></p>-->

                                            </fieldset>

					</div> <!-- end of section -->

				</div> <!-- end of credit-card -->

				<div id="withdraw-funds" <c:if test="${changePayMethod or billingInfoForm.paymentType!='eft'}"> style="display:none" </c:if>>

					<div class="section">

						<h2 id="set-up-your-fund-withdrawal">Set up your fund withdrawal</h2>

						<fieldset id="eft-fields">

							<dl>

								<dt class="first-dt-dd-pair">Account type:</dt>
									<dd class="first-dt-dd-pair">
										<ul>
                                                                                    <li><label for="personal-bank-account-1"><input type="radio" class="radio-button" value="PC" id="personal-bank-account-1" name="bankAcctType"
                                                                                        <c:if test="${(empty billingInfoForm.bankAcctType) or (billingInfoForm.bankAcctType=='PC')}">
                                                                                        checked="checked"
                                                                                        </c:if>
                                                                                        />Personal bank account</label></li>

                                                                                    <li><label for="business-bank-account-1"><input type="radio" class="radio-button" value="CC" id="business-bank-account-1" name="bankAcctType"
                                                                                        <c:if test="${billingInfoForm.bankAcctType=='CC'}">
                                                                                        checked="checked"
                                                                                        </c:if>
                                                                                        />Business bank account</label></li>
										</ul>
									</dd>


								<dt><label for="bank-routing-number-1">Routing number:</label></dt>
									<dd>
                                                                                *<input type="text" class="textfield" id="bank-routing-number-1" name="bankRoutingNumber" value="${billingInfoForm.bankRoutingNumber}" onblur="javascript:cleanNumericField(this);"/>
                                                                                <p class="help">The routing number is the first group of digits on your check.</p>
                                                                        </dd>
							</dl>

							<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-routing-number.gif" width="300" height="80" alt="The routing number is 1 to 17 digits between the : symbols." />

							<dl>

								<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Account number:</label></dt>
									<dd class="first-dt-dd-pair">
                                                                                *<input type="text" class="textfield" id="bank-account-number-1" name="bankAcctNumber" value="${billingInfoForm.bankAcctNumber}" onblur="javascript:cleanNumericField(this);" onselect="javascript:unselect();" />
                                                                                <p class="help">The account number is the second group of numbers on your check.</p>
                                                                        </dd>
                                								<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Confirm account number:</label></dt>
									<dd class="first-dt-dd-pair">
                                                                                *<input type="text" class="textfield" id="bank-account-number-2"  value="${billingInfoForm.bankAcctNumber}" onselect="javascript:unselect();" />

                                                                        </dd>
							</dl>

							<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-account-number.gif" width="300" height="80" alt="The account number is 9 digits between the : and ^ symbols." />

						</fieldset>

					</div> <!-- end of section -->

				</div> <!-- end of withdraw-funds -->

					<ul class="form-actions" <c:if test="${changePayMethod or (billingInfoForm.paymentType!='credit' and billingInfoForm.paymentType!='eft')}"> style="display:none" </c:if>>

						<li><img  src="${pageContext.request.contextPath}/meta/media/buttons/review-confirmation-page.gif" alt="Review Confirmation Page" onclick="javascript:doSubmit();"/></li>

					</ul> <!-- end of form-actions -->


			<div id="tertiary-navigation-and-or-page-controls">

				<!--#include virtual="/includes/manage/tertiary-navigation-manage-payments-and-orders.shtml" -->

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

			</form>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->


<script type="text/javascript">
var submitted = false;

    function creditCardLink()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action= "${pageContext.request.contextPath}/paymentBillingInfoDisplay.do?type=credit";
            document.forms[0].submit();
        }
    }

    function withdrawFundLink()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action= "${pageContext.request.contextPath}/paymentBillingInfoDisplay.do?type=eft";
            document.forms[0].submit();
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

    function doSubmit()
    {
    	<c:if test="${billingInfoForm.paymentType=='eft'}">
    	if (document.getElementById("bank-account-number-2").value.toUpperCase() != document.getElementById("bank-account-number-1").value.toUpperCase()) {
            alert("'Confirm Bank Account Number' should match 'Bank Account Number'");
            changeTextFieldWhite(document.getElementById("bank-account-number-1"));
    		changeTextFieldColor(document.getElementById("bank-account-number-2"));
    		return;
    	}
    	</c:if>
        if (!submitted)
        {

        	<c:if test="${billingInfoForm.paymentType=='credit'}">
        	doPaypageSubmit(function(respObj){
        		submitted = true;
        		document.getElementById("paypageRegistrationId").value = respObj.paypageRegistrationId;
                document.forms[0].submit();
        	});
        	</c:if>
        	<c:if test="${billingInfoForm.paymentType=='eft'}">
        		submitted = true;
            	document.forms[0].submit();
            </c:if>

        }
    }
    function changeTextFieldColor(field){
    	field.style.background="#00FFCC";
    }

    function changeTextFieldWhite(field){
    	field.style.background="#FFFFFF";
    }
</script>
