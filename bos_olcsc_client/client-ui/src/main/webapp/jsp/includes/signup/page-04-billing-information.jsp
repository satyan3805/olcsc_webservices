<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<jsp:useBean id="creditCardDelegate" class="com.etcc.csc.delegate.CreditCardDelegate" scope="page"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/litle-api2.js"></script>


<body onload="loadingBody()">
	<div id="content-container">

                <etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/signupBillingInfo.do">
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

						<input type="hidden" id="paymentType" name="paymentType" value="${billingInfoForm.paymentType}"/>
                        <div id="primary-and-secondary-content">
				<h1 id="billing-information">Billing Information</h1>
				<div id="primary-content">

                                        <div <c:if test="${(not changePayMethod) and (not empty billingInfoForm) and (not empty billingInfoForm.paymentType)}">style="display:none"</c:if>>
                                        <dl class="choices" id="payment-methods">

						<dt>How would you like to pay?</dt>

							<dd><a  href="javascript:creditCardLink();" >Charge my credit card</a><br />
							<br />
							<!-- Using a credit card for your payment on your account provides additional privileges such as the ability to charge parking at Houston Area Airports. -->
							</dd>
							<dd><a  href="javascript:withdrawFundLink();">Withdraw funds from my bank</a> <br/><br/></dd>

                                                        <dt>Opening Costs</dt>
                                                            <dd>Opening an EZ TAG Account requires a minimum pre-paid deposit of $40 per each three (3) vehicles to a maximum of $600 if you are paying by Credit/Debit Card.</dd>
                                                            <dd>If you prefer to have funds directly deducted from your bank, then a minimum of $80 per each three (3) vehicles to a maximum of $1200 is required.</dd>
                                                            <dd>Additionally we charge a one time activation fee of <strong>$15.00 per EZ TAG for the first 3</strong> and <strong>$10.00 per EZ TAG thereafter</strong>. Learn more <a href="${appDelegate.domainName}/about_faq/ez-tags">About EZ TAG</a>.</dd>

					</dl> <!-- end of choices -->
                                        </div>

                                        <div class="section">
                                            <logic:messagesPresent message="false">
                                                <dl class="errors"/>
                                                <html:messages id="msg" message="false">
                                                    <dd><bean:write name="msg"/></dd>
                                                </html:messages>
                                            </logic:messagesPresent>
                                            <logic:messagesPresent message="true">
                                                <dl class="errors"/>
                                                <html:messages id="msg" message="true" property="billingInfoSaveFailed">
                                                    <dd>${msg}</dd>
                                                </html:messages>
                                            </logic:messagesPresent>
                                        </div>

					<div id="credit-card" <c:if test="${changePayMethod or billingInfoForm.paymentType!='credit'}"> style="display:none" </c:if>>

						<h5>* Required fields.</h5>
                            <div class="section">

							<h2 id="payment-information">Credit Card</h2>

							<fieldset>

								<dl>

									<dt class="first-dt-dd-pair"><label for="name-on-credit-card">Name on credit card:</label></dt>
										<dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="name-on-credit-card" name="nameOnCard" value="${billingInfoForm.nameOnCard}" onblur="javascript:removeUnwantedChar(this);"/></dd>

									<dt><label for="card-type">Card Type:</label></dt>
										<dd>*<select id="card-type" name="cardType">
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
											<!-- p class="alternate-credit-card-selection"><label for="alternate-credit-card"><input type="checkbox" class="checkbox" id="alternate-credit-card" name="reserveCard" <c:if test='${billingInfoForm.reserveCard}'>checked</c:if> /> I would like to provide a secondary credit card</label></p -->
										</dd>
									<dt>CVV:</dt>
										<dd>*<input type="text" class="textfield" id="cvv" name="cvv" value="" max="4" maxlength="4" style="width:35px" /></dd>
								</dl>

								<!-- dl id="alternate-credit-card-fields">

									<dt><label for="name-on-alternate-credit-card">Name on secondary credit card:</label></dt>
										<dd>*<input type="text" class="textfield" id="name-on-alternate-credit-card" name="nameOnReserveCard" value="${billingInfoForm.nameOnReserveCard}" onblur="javascript:removeUnwantedChar(this);"/></dd>

									<dt><label for="alternate-card-type">Secondary Card Type:</label></dt>
										<dd>
											*<select id="alternate-card-type" name="reserveCardType">
                                                                                            <option value=""></option>
                                                                                            <c:forEach var="card" items="${creditCardDelegate.creditCardTypes}">
                                                                                                <option value="${card.cardCode}"
                                                                                                    <c:if test="${billingInfoForm.reserveCardType==card.cardCode}">
                                                                                                        selected
                                                                                                    </c:if>
                                                                                                >${card.cardName}</option>
                                                                                            </c:forEach>
											</select>
										</dd>

									<dt><label for="alternate-card-number" id="for-alternate-card-number">Secondary Card Number:</label></dt>
										<dd>*<input type="text" class="textfield" id="alternate-card-number" name="reserveCardNumber" value="${billingInfoForm.reserveCardNumber}" onblur="javascript:removeUnwantedChar(this);"/></dd>

									<dt>Secondary Card expires on:</dt>
										<dd>
											<label for="alternate-card-expiration-month" class="accessibility">Card expiration month:</label>
											*<select id="alternate-card-expiration-month" name="reserveCardExpirationMonth">
												<option value=""></option>
												<c:forEach var="cardMonths" items="${creditCardDelegate.creditCardMonths}">
                                                                                                    <option value="${cardMonths.value}"
                                                                                                    <c:if test="${billingInfoForm.reserveCardExpirationMonth == cardMonths.value}">
                                                                                                        selected
                                                                                                    </c:if>
                                                                                                    >${cardMonths.label}</option>
                                                                                                </c:forEach>
											</select>

											<label for="alternate-card-expiration-year" class="accessibility">Secondary Card expiration year:</label>
											*<select id="alternate-card-expiration-year" name="reserveCardExpirationYear">
                                                                                            <option value=""></option>
                                                                                            <c:forEach var="expYear" items="${creditCardDelegate.creditCardYears}">
                                                                                                <option value="${expYear.value}"
                                                                                                    <c:if test="${billingInfoForm.reserveCardExpirationYear == expYear.value}">
                                                                                                        selected
                                                                                                    </c:if>
                                                                                                >${expYear.label}</option>
                                                                                            </c:forEach>
											</select>
										</dd>
								</dl-->

							</fieldset>

						</div> <!-- end of section -->

						<div class="section">

							<h2>Credit Card Billing Address</h2>

							<p>Your mailing address is:</p>

							<address class="mailing-label">
  <etcc-extended:format address="${contactInfoForm}" />
							</address>

							<p class="billing-address-is-different-from-mailing-selection">
                                                        <label for="billing-address-is-different-from-mailing">
                                                            <input type="checkbox" class="checkbox" id="billing-address-is-different-from-mailing" name="diffBillingAddress"
                                                                <c:if test="${billingInfoForm.diffBillingAddress}">checked</c:if> /> My billing address is different than my mailing address
                                                        </label>
                                                        </p>

							<fieldset id="billing-address-fields">

								<p id="non-us-address-field"><label for="non-us-address"><input type="checkbox" class="checkbox" id="non-us-address" name="nonUsBillingAddress"
                                                                    <c:if test='${billingInfoForm.nonUsBillingAddress}'>checked</c:if> onclick="javascript:clearUSAddrFields(this);"
                                                                /> My billing address is outside the <acronym title="United States">U.S.</acronym></label></p>

								<dl>

									<dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
										<dd class="non-us first-dt-dd-pair" id="country-dd">
											*<select id="country" name="country">
                                                                                            <option value=""></option>
                                                                                            <c:forEach var="rec" items="${countryDelegate.countries}">
                                                                                                <option value="${rec.countryCode}"
                                                                                                    <c:if test="${billingInfoForm.country == rec.countryCode}">
                                                                                                        selected
                                                                                                    </c:if>
                                                                                                >${rec.country}</option>
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
                                                                                    <label for="plus4">&nbsp;Plus4: </label><input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${billingInfoForm.plus4}" onblur="javascript:cleanNumericField(this);"/>
                                                                                </dd>
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

					</div> <!-- end of credit-card -->

					<div id="withdraw-funds" <c:if test="${changePayMethod or billingInfoForm.paymentType!='eft'}"> style="display:none" </c:if>>

						<h5>* Required fields.</h5>
                                                <div class="section">

							<h2 id="set-up-your-fund-withdrawal">Bank Account</h2>

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
											*<input type="text" class="textfield" id="bank-account-number-1" name="bankAcctNumber" value="${billingInfoForm.bankAcctNumber}" onblur="javascript:cleanNumericField(this);" onselect="javascript:unselect();"/>
											<p class="help">The account number is the second group of numbers on your check.</p>
										</dd>
										<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Confirm account number:</label></dt>
									<dd class="first-dt-dd-pair">
                                                                                *<input type="text" class="textfield" id="bank-account-number-2"  value="${billingInfoForm.bankAcctNumber}" onselect="javascript:unselect();" />

                                                                        </dd>
								</dl>

								<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-account-number.gif" width="300" height="80" alt="The account number is 9 digits between the : and ^ symbols." />

<!-- this requirement was deleted. I've kept the code here in case it comes back

								<p class="alternate-eft-account-selection"><label for="alternate-eft-account"><input type="checkbox" class="checkbox" id="alternate-eft-account" name="alternate-eft-account" /> I would like to provide an alternate account</label></p>

								<dl id="alternate-eft-account-fields">

									<dt class="first-dt-dd-pair">Alternate Account type:</dt>
										<dd class="first-dt-dd-pair">
											<ul>
												<li><label for="personal-bank-account-2"><input type="radio" class="radio-button" value="personal-bank-account" id="personal-bank-account-2" name="bank-account-type-2" checked="checked"/>Personal bank account</label></li>

												<li><label for="business-bank-account-2"><input type="radio" class="radio-button" value="business-bank-account" id="business-bank-account-2" name="bank-account-type-2" />Business bank account</label></li>
											</ul>
										</dd>

									<dt><label for="bank-routing-number-2">Alternate routing number:</label></dt>
										<dd>
											<input type="text" class="textfield" id="bank-routing-number-2" name="bank-account-number-2" />
										</dd>

									<dt><label for="bank-account-number-2">Alternate account number:</label></dt>
										<dd>
											<input type="text" class="textfield" id="bank-account-number-2" name="bank-account-number-2" />
										</dd>
								</dl>
-->

							</fieldset>


						</div> <!-- end of section -->

					</div> <!-- end of withdraw-funds -->

					<!-- This contains the elements common to both the credit-card and withdraw-funds choices -->
					<div id="common-section" class="common section" <c:if test="${changePayMethod or (empty billingInfoForm) or (empty billingInfoForm.paymentType)}"> style="display:none" </c:if>>
<%--
						<fieldset id="monthly-statements-charge">
<!-- this business rule has changed. There is no charge for mailing monthly statements now.
							<p>Monthly statements <strong>are available online at no charge</strong>. However, you can have printed monthly statements mailed to you for $1.50 per 3 tags.</p>
-->
							<p><label for="mail-me-printed-monthly-statements" id="for-mail-me-printed-monthly-statements"><input type="checkbox" class="checkbox" id="mail-me-printed-monthly-statements" name="mailMonthlyStatement" <c:if test='${billingInfoForm.mailMonthlyStatement}'>checked</c:if>/>Please mail me printed monthly statements.</label></p>

						</fieldset>
--%>
                                                <br/><br/>
						<fieldset class="legal">

							<h2>Legal:</h2>
							<c:if test="${changePayMethod or billingInfoForm.paymentType=='credit'}">
							<p>Before moving forward, please review the <a class="legal-document" rel="license" href="${pageContext.request.contextPath}/signupAgreement.do">EZ Agreement</a>. Here&rsquo;s a short summary of the major points in the agreement:</p>
							</c:if>
							<c:if test="${changePayMethod or billingInfoForm.paymentType=='eft'}">
							<p>Before moving forward, please review the <a class="legal-document" rel="license" href="javascript:openAgreWindow('${pageContext.request.contextPath}/signupAgreement.do')">EZ Agreement</a>. Here&rsquo;s a short summary of the major points in the agreement:</p>
							</c:if>
							<ul>
								<li>You must <strong>maintain updated credit card (account number/expiration date)</strong> or other payment information to maintain an active account.</li>
								<li>You must <strong>maintain updated license plate information</strong> to maintain an active account.</li>
								<li>You must <strong>maintain updated address and other contact information</strong>.</li>
								<li>You must notify HCTRA immediately if the EZ TAG is lost, stolen, or damaged.</li>
								<li>It is your responsibility to <strong>maintain an EZ TAG Account balance</strong> sufficient to cover all toll transactions and other authorized transactions. <strong>If the County is unable to charge or debit your account and the EZ TAG balance falls below zero, continued use of your EZ TAG may result in violations.</strong> Then your account will be suspended until violation issues are resolved and updated account information is received by the County.</li>
							</ul>

							<p>If you have questions, please <a rel="external" href="${appDelegate.domainName}/about_contact/">e-mail support</a>.</p>

							<p id="agree-to-license-agreement-field"><label for="agree-to-license-agreement" id="for-agree-to-license-agreement"><input type="checkbox" class="checkbox" id="agree-to-license-agreement" name="agreementAccepted" <c:if test='${billingInfoForm.agreementAccepted}'>checked</c:if> /> I agree to the EZ Agreement</label></p>

						</fieldset> <!-- end of legal -->

					</div> <!-- end of common-elements -->

				</div> <!-- end of primary-content -->

				<div id="secondary-content">

					<div id="secondary-content-interior">

						<h6>EZ TAG Questions and Answers</h6>
                                                    <p class="introduction">Wondering how an EZ TAG Account works? Here are answers to some of our most often asked questions:</p>

                                                    <dl class="faq">
                                                            <dt id="what-am-i-paying-for" class="first-dt-dd-pair">What am I paying for?</dt>
                                                                    <dd class="first-dt-dd-pair">
                                                                            <ul>
                                                                                    <li><p>no stopping</p></li>
                                                                                    <li><p>no waiting in line</p></li>
                                                                                    <li><p>no fumbling for loose change</p></li>
                                                                                    <li><p>lower tolls</p></li>
                                                                            </ul>
                                                                    </dd>


                                                            <dt id="how-does-it-work">How does it work?</dt>
                                                                    <dd>
                                                                            <p>Tolls are automatically deducted from your EZ TAG Account balance every time you pass through a toll lane using your EZ TAG.</p>
                                                                            <p>When your EZ TAG Account balance reaches 25% or below of the pre-paid deposit, a replenishment amount equivalent to the pre-paid deposit amount shall be automatically charged against your Credit/Debit Card (or bank account).</p>
                                                                    </dd>

                                                            <dt id="do-i-need-more-than-one-ez-tag-account">Do I need more than one EZ TAG Account?</dt>
                                                                    <dd>
                                                                            <p>No. You can manage as many vehicles as you like using one EZ TAG Account.</p>
                                                                    </dd>

                                                            <dt id="is-this-web-site-secure">Is this website secure?</dt>
                                                                    <dd>
                                                                            <p>This site is tested and certified daily to pass the FBI/SANS Internet Security Test.</p>
                                                                            <!-- START SCANALERT CODE -->
                                                                         <!--   <p><a target="_blank" href="https://www.scanalert.com/RatingVerify?ref=hctra.org"><img width="115" height="32" src="https://images.scanalert.com/meter/hctra.org/12.gif" alt="HACKER SAFE certified sites prevent over 99.9% of hacker crime." /></a></p>
                                                                            <!-- END SCANALERT CODE -->

																		<!-- McAfee Secure Trustmark for www.hctra.org -->
																			<a target="_blank" href="https://www.mcafeesecure.com/verify?host=www.hctra.org"><img class="mfes-trustmark mfes-trustmark-hover" border="0" src="//cdn.ywxi.net/meter/www.hctra.org/101.gif" width="125" height="55" title="McAfee SECURE sites help keep you safe from identity theft, credit card fraud, spyware, spam, viruses and online scams" alt="McAfee SECURE sites help keep you safe from identity theft, credit card fraud, spyware, spam, viruses and online scams" oncontextmenu="alert('Copying Prohibited by Law - McAfee Secure is a Trademark of McAfee, Inc.'); return false;"></a>
																		<!-- End McAfee Secure Trustmark -->




                                                                    </dd>

                                                    </dl> <!-- end of faq -->

					</div> <!-- end of secondary-content-interior -->

				</div> <!-- end of secondary-content -->

			</div> <!-- end of primary-and-secondary-content -->

			<div <c:if test="${billingInfoForm.paymentType!='eft' and billingInfoForm.paymentType!='credit'}"> style="display:none" </c:if>>
                        <ul class="form-actions">

				<li><input id="review-confirmation-page" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/review-confirmation-page.gif" value="Review Confirmation Page" onclick="javascript:doSubmit(); return false;"/></li>

			</ul> <!-- end of form-actions -->
                        </div>

		</etcc-extended:autocompleteOffForm>

		<p class="progress-bar" id="step-4-of-6">
			New EZ TAG Account
			<em>Step 4 of 6</em>
		</p>

	</div> <!-- end of content-container -->
</body>


<script type="text/javascript">
var submitted = false;

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
function getErrorfields(){
var nameOnCard = '<html:errors property="nameOnCard"/>';
var cardType = '<html:errors property="cardType"/>';
var cardNumber = '<html:errors property="cardNumber"/>';
var cardExpirationMonth = '<html:errors property="cardExpirationMonth"/>';
var cardExpirationYear = '<html:errors property="cardExpirationYear"/>';
var nameOnReserveCard='<html:errors property="nameOnReserveCard"/>';
var reserveCardType = '<html:errors property="reserveCardType"/>';
var reserveCardNumber='<html:errors property="reserveCardNumber"/>';
var reserveCardExpirationMonth = '<html:errors property="reserveCardExpirationMonth"/>';
var reserveCardExpirationYear = '<html:errors property="reserveCardExpirationYear"/>';
var addressLine1='<html:errors property="addressLine1"/>';
var addressLine2='<html:errors property="addressLine2"/>';
var addressLine3='<html:errors property="addressLine3"/>';
var addressLine4='<html:errors property="addressLine4"/>';
var city='<html:errors property="city"/>';
var zip='<html:errors property="zip"/>';
var plus4='<html:errors property="plus4"/>';
var bankRoutingNumber='<html:errors property="bankRoutingNumber"/>';
var bankAcctNumber='<html:errors property="bankAcctNumber"/>';

if (checkForErrors(nameOnCard) == true){
		changeTextFieldColor(document.forms[0].nameOnCard);
	 }else{
		 changeTextFieldWhite(document.forms[0].nameOnCard);
	 }
if (checkForErrors(cardType) == true){
		changeTextFieldColor(document.forms[0].cardType);
	 }else{
		 changeTextFieldWhite(document.forms[0].cardType);
	 }

if (checkForErrors(cardNumber) == true){
		changeTextFieldColor(document.forms[0].cardNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].cardNumber);
	 }
if (checkForErrors(cardExpirationMonth) == true){
		changeTextFieldColor(document.forms[0].cardExpirationMonth);
	 }else{
		 changeTextFieldWhite(document.forms[0].cardExpirationMonth);
	 }

if (checkForErrors(cardExpirationYear) == true){
		changeTextFieldColor(document.forms[0].cardExpirationYear);
	 }else{
		 changeTextFieldWhite(document.forms[0].cardExpirationYear);
	 }

if (checkForErrors(nameOnReserveCard) == true){
		changeTextFieldColor(document.forms[0].nameOnReserveCard);
	 }else{
		 changeTextFieldWhite(document.forms[0].nameOnReserveCard);
	 }

if (checkForErrors(reserveCardType) == true){
		changeTextFieldColor(document.forms[0].reserveCardType);
	 }else{
		 changeTextFieldWhite(document.forms[0].reserveCardType);
	 }

if (checkForErrors(reserveCardNumber) == true){
		changeTextFieldColor(document.forms[0].reserveCardNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].reserveCardNumber);
	 }

if (checkForErrors(reserveCardExpirationMonth) == true){
		changeTextFieldColor(document.forms[0].reserveCardExpirationMonth);
	 }else{
		 changeTextFieldWhite(document.forms[0].reserveCardExpirationMonth);
	 }
if (checkForErrors(reserveCardExpirationYear) == true){
		changeTextFieldColor(document.forms[0].reserveCardExpirationYear);
	 }else{
		 changeTextFieldWhite(document.forms[0].reserveCardExpirationYear);
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
	 if (checkForErrors(bankRoutingNumber) == true){
		changeTextFieldColor(document.forms[0].bankRoutingNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].bankRoutingNumber);
	 }
	 if (checkForErrors(bankAcctNumber) == true){
		changeTextFieldColor(document.forms[0].bankAcctNumber);
	 }else{
		 changeTextFieldWhite(document.forms[0].bankAcctNumber);
	 }
}

function creditCardLink()
{
    if (!submitted)
    {
        submitted = true;
        document.forms[0].paymentType.value="credit";
        changeLink();
    }
}

function withdrawFundLink()
{
    if (!submitted)
    {
        submitted = true;
        document.forms[0].paymentType.value="eft";
        changeLink();
    }
}

function changeLink()
{
    document.forms[0].action= "${pageContext.request.contextPath}/signupBillingInfoDisplay.do";
    document.forms[0].submit();
}

function clearUSAddrFields(chk) {
    if (chk.checked) {
        document.billingInfoForm.city.value="";
        document.billingInfoForm.zip.value="";
        document.billingInfoForm.plus4.value="";
        document.billingInfoForm.country.selectedIndex=0;
    } else {
        document.billingInfoForm.addressLine3.value="";
        document.billingInfoForm.addressLine4.value="";
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
        if (!document.billingInfoForm.agreementAccepted.checked)
        {
            alert("Please confirm your acceptance of the EZ agreement.");
            return;
        }

        submitted = true;
        document.billingInfoForm.action = 'signupBillingInfo.do';
        document.billingInfoForm.submit();
    }
}
function openAgreWindow(url)
{

    window.open(url,"","width=800,height=500,scrollbars=1,resizable");
}
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

</script>