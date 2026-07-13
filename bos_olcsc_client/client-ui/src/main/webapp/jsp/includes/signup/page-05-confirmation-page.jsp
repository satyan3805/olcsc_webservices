<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="content-container">

		<div id="content">

			<input type="hidden" name="fromConfirmation" value="true"/>
			<html:hidden property="whichPage" value="confirm-page"  />
			<c:set var="whichPage" value="confirm-page" scope="session" />

				<h1 id="is-this-correct">Is this correct? <em>Please review everything to make sure it is accurate.</em></h1>

				<div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <dt/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <dt/>
                                        <html:messages id="msg" message="true" property="saveFailed">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>
                                <p>Once you have verified all the items below are correct, press "Yes! This is Correct" to process your order.</p>

				<h3 id="account-information-for">
					Username: <em>${OnlineAccessForm.loginId}</em>
					<input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit-password.gif" value="Change your password or user id" onclick="javascript:gotoOnlineAccess();" />
				</h3>

                                <p>
					Email Address: ${OnlineAccessForm.emailAddress}
				</p>

				<div class="section with-primary-and-secondary-content">

					<div class="primary-section-content with-multiple-definition-lists">

						<h4 id="personal-information">Contact Information</h4>

						<dl id="user-information" class="immediately-following-an-h4">

							<dt>${contactInfoForm.firstName} ${contactInfoForm.lastName}<input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your personal information" onclick="javascript:gotoContactInfo();" /></dt>
								<dd>${contactInfoForm.primaryPhone}</dd>
								<dd>${contactInfoForm.alternatePhone}</dd>
								<dd>${contactInfoForm.driversLicState} DL: ${contactInfoForm.maskedDriversLic}</dd>

						</dl> <!-- end of user-information -->

						<dl id="mailing-address">

							<dt>Mailing Address: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your mailing address" onclick="javascript:gotoContactInfo();" /></dt>
								<dd>
									<address>
									  <etcc-extended:format address="${contactInfoForm}" displayName="false" />
									</address>
								</dd>

						</dl> <!-- end of mailing-address -->

						<dl class="selections">

							<dt>Preferences: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your preferences" onclick="javascript:gotoPreferences();" /></dt>
                                                            <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
                                                                <c:if test="${preference.active}">
                                                                <dd>${preference.description}</dd>
                                                                </c:if>
                                                            </c:forEach>
					</dl>
					<dl class="not-selections">
					<dt>Additional Preferences are available:</dt>

							<c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">

							<c:choose>
								 <c:when test="${preference.active}">
							     </c:when>
								 <c:otherwise>
								 <c:if test="${fn:startsWith(preference.description, 'E-Mail me a receipt when I use my EZ TAG for parking')==false}">


									<dd>${preference.description}</dd> </c:if>
								</c:otherwise>
							</c:choose>
                           </c:forEach>
                                                            <!--
                                                                <c:if test="${contactInfoForm.byEmail and contactInfoForm.byMail}">
                                                                    <dd>Notify me by e-mail and regular mail</dd>
                                                                </c:if>
                                                                <c:if test="${contactInfoForm.byEmail and !contactInfoForm.byMail}">
                                                                    <dd>Notify me by e-mail</dd>
                                                                </c:if>
                                                                <c:if test="${!contactInfoForm.byEmail and contactInfoForm.byMail}">
                                                                    <dd>Notify me by regular mail</dd>
                                                                </c:if>

                                                                <c:if test="${billingInfoForm.mailMonthlyStatement}">
                                                                    <dd>Send me an itemized monthly statement by mail</dd>
                                                                </c:if>
                                                            -->

						</dl> <!-- end of selections -->

					</div> <!-- end of primary-section-content -->

					<div class="secondary-section-content with-multiple-definition-lists">

						<h4 id="billing-information">Billing Information</h4>

						<dl id="payment-method" class="immediately-following-an-h4">
							<dt>Payment Method: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your payment method" onclick="javascript:gotoBillingInfo();" /></dt>
                                                            <c:if test="${billingInfoForm.paymentType == 'credit'}">
                                                                <dd>Credit Card</dd>

                                                            </c:if>

                                                            <c:if test="${billingInfoForm.paymentType == 'eft'}">
                                                                <dd>Withdraw funds from bank</dd>
                                                            </c:if>

						</dl> <!-- end of payment-method -->

                                                <dl id="billing-address">
							<dt>Billing Detail: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your billing address" onclick="javascript:gotoBillingInfo('${billingInfoForm.paymentType}');" /></dt>
	<dd>
	  <etcc-extended:format billingInfo="${billingInfoDTO}" mask="ACCOUNT" displayName="false" />
	</dd>
	<c:if test="${billingInfoDTO.creditCard}">
								<dd>
									<address>
										<c:choose>
			<c:when test="${billingInfoDTO.diffBillingAddress}">
				<etcc-extended:format address="${billingInfoDTO}" displayName="false" />
			</c:when>
			<c:otherwise>
				<etcc-extended:format address="${contactInfoForm}" displayName="false" />
			</c:otherwise>
		                                </c:choose>
									</address>
								</dd>
	</c:if>
						</dl>

					</div> <!-- end of secondary-section-content -->
					<!-- Lakshmi Harish Start -->
					<div class="secondary-section-content with-multiple-definition-lists">


					<h4 id="authorized-contact">Authorized Contacts</h4>
					<dl>
					<dt>Edit Contacts: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your preferences" onclick="javascript:gotoAuthorizedContacts('confirm');" /></dt>
						</dl>
					  <c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="varStatus" begin="0" end="2">
		  	     <label for="authorized-contact-${varStatus.index}">
				 <li><c:out value="${contact.firstName} ${contact.lastName}" /></li>
				 </label>
		          </c:forEach>

					<c:set var="whichPage" value="confirm-page" scope="session" />

					</div><!-- Secondary  ends-->

					<div class="secondary-section-content with-multiple-definition-lists">

					<h4 id="general Communications">General Communications</h4>
					<dl>
					<dt> * Credit Card expiry notes </dt>
					<dt> * No/Low balance notes </dt>
					<dt> * Suspend account notes </dt>
					</dl>

					</div> <!-- secondary ends -->
					<!-- Lakshmi Harish Ends -->
				</div> <!-- end of section with-primary-and-secondary-content -->


			<!-- Have to remove this piece of code -->
			 <form id="sign-up" action="${pageContext.request.contextPath}/signupPaymentAndDelivery.do" method="post">
				<ul class="form-actions">

					<li><input id="activate-account" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/button-correct.gif" value="Is this correct" onclick="javascript:activateAccount();return false;"/></li>

				</ul> <!-- end of form-actions -->
			</form>

			<!-- end of remove -->
		</div> <!-- end of content -->

		<p class="progress-bar" id="step-5-of-6">
			New EZ TAG Account
			<em>Step 5 of 6</em>
		</p>

	</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="scCheckout";
s.products=";New Acct Tags - "+ "${billingInfoForm.paymentType}" + ";" + "${tagRequestForm.firstTierEZTag}" + ";" + "${tagRequestForm.tagSaleAmount}" + ",;New Acct Payments - " +  "${billingInfoForm.paymentType}" + ";1;" + "${tagRequestForm.depositAmount}";
s.zip="${contactInfoForm.zip}";
s.state="${contactInfoForm.state}";
s.eVar1="${acctId}";
<c:choose>
    <c:when test="${contactInfoForm.accountType == 'personal'}">
        s.eVar3="Regular";
    </c:when>
    <c:otherwise>
        s.eVar3="Corporate";
    </c:otherwise>
</c:choose>

var submitted = false;
    function gotoOnlineAccess()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action="${pageContext.request.contextPath}/signup.do?refresh=false";
            document.forms[0].submit();
        }
    }

    function gotoContactInfo()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action="${pageContext.request.contextPath}/signupContactInfoDisplay.do";
            document.forms[0].submit();
        }
    }

    function gotoPreferences()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action="${pageContext.request.contextPath}/signupPreferencesDisplay.do";
            document.forms[0].submit();
        }
    }

function gotoAuthorizedContacts(pageValue){
	if (!submitted)
        {
            submitted = true;
			document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do?page="+ pageValue + "&method=get";
			document.forms[0].submit();
        }
}


    function deleteThisVehicle(index)
    {
        if (!submitted)
        {
            submitted = true;
            document.forms["edit_vehicle"].vehicleIndexToModify.value=index;
            document.forms["edit_vehicle"].deleteVehicle.value="true";
            document.forms["edit_vehicle"].submit();
        }
    }

    function editThisVehicle(index)
    {
        if (!submitted)
        {
            submitted = true;
            document.forms["edit_vehicle"].vehicleIndexToModify.value=index;
            document.forms["edit_vehicle"].submit();
        }
    }

    function gotoBillingInfo(paymentType)
    {
        if (!submitted)
        {
            submitted = true;
            var desUrl ="${pageContext.request.contextPath}/signupBillingInfoBack.do";
            if (paymentType != 'credit' && paymentType != 'eft' )
                desUrl = desUrl + "?changePayMethod=y"

            document.forms[0].action=desUrl;
            document.forms[0].submit();
        }
    }

    function activateAccount()
    {
        if (!submitted)
        {
            submitted = true;
			document.forms["sign-up"].submit();

        }
    }

</script>
