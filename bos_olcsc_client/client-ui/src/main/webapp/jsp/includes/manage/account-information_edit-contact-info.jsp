<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<body onload="loadingBody()">
<div id="content-container">
	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeContactInfo.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit contact information</h1>
		
                        <div class="section">

                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="changeContactInfoError">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changeContactInfoError">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                            </logic:messagesPresent>
                        </div>
                        
                        <div class="section">

			<p>You may change your email address and phone numbers:</p>

			<fieldset>
				
				<input type="hidden" name="acctTypeCode" value="${OnlineAccessForm.acctTypeCode}">
                                <input type="hidden" name="acctTypeDescr" value="${OnlineAccessForm.acctTypeDescr}">
                                <input type="hidden" name="companyName" value="${OnlineAccessForm.companyName}">
                                <input type="hidden" name="firstName" value="${OnlineAccessForm.firstName}">
                                <input type="hidden" name="lastName" value="${OnlineAccessForm.lastName}">
                                <input type="hidden" name="companyTaxId" value="${OnlineAccessForm.companyTaxId}">
                                <input type="hidden" name="driverLicState" value="${OnlineAccessForm.driverLicState}">
                                <input type="hidden" name="driverLicDisplay" value="${OnlineAccessForm.driverLicDisplay}">
                                
                                <dl>
					<dt><label for="e-mail-address">E-Mail Address:</label></dt>
						<dd>*
						<c:choose>
						<c:when test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> 
						<input type="text" class="textfield" id="emailAddress" name="emailAddress" value="${OnlineAccessForm.emailAddress}" onblur="javascript:removeUnwantedChar(this);" readonly/>
						</c:when>
						<c:otherwise>
						<input type="text" class="textfield" id="emailAddress" name="emailAddress" value="${OnlineAccessForm.emailAddress}" onblur="javascript:removeUnwantedChar(this);" />
						</c:otherwise>
						</c:choose>
                                                    <p class="help">This field is case sensitive</p>
                                                </dd>

					<dt class="first-dt-dd-pair"><label for="confirm-e-mail-address">Confirm E-Mail Address:</label></dt>
						<dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="emailAddress2" name="emailAddress2" value="${OnlineAccessForm.emailAddress2}" onblur="javascript:removeUnwantedChar(this);"   <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">disabled="disabled"  </c:if> /></dd>

					<dt><label for="e-mail-address">Alternate E-Mail Address:</label></dt>
						<dd>&nbsp<input type="text" class="textfield" id="alternateEmail" name="alternateEmail" value="${OnlineAccessForm.alternateEmail}" onblur="javascript:removeUnwantedChar(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly  </c:if> />
                                                    <p class="help">This field is case sensitive</p>
                                                </dd>

					<dt class="first-dt-dd-pair"><label for="confirm-e-mail-address">Confirm Alternate E-Mail Address:</label></dt>
						<dd class="first-dt-dd-pair">&nbsp<input type="text" class="textfield" id="confirmAlternateEmail" name="confirmAlternateEmail" value="${OnlineAccessForm.confirmAlternateEmail}" onblur="javascript:removeUnwantedChar(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">disabled="disabled"  </c:if> /></dd>

					<dt class="first-dt-dd-pair"><label for="primary-phone">
                                            <c:choose>
                                                <c:when test="${(not empty OnlineAccessForm.companyName)}">
                                                     Contact Primary Phone:
                                                </c:when>
                                                <c:otherwise>
                                                     Primary Phone:*
                                                </c:otherwise>
                                            </c:choose>
                                            </label></dt>
						<dd class="first-dt-dd-pair">*<input type="text" class="textfield" id="primary-phone" name="primaryPhone" value="${OnlineAccessForm.primaryPhone}" onblur="javascript:this.value=formatPhoneNumber(this.value);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

					<dt class="first-dt-dd-pair"><label for="alternate-phone">
                                            <c:choose>
                                                <c:when test="${(not empty OnlineAccessForm.companyName)}">
                                                    Contact Alternate Phone:
                                                </c:when>
                                                <c:otherwise>
                                                    Alternate Phone:
                                                </c:otherwise>
                                            </c:choose>
                                        </label></dt>
                                                <dd class="first-dt-dd-pair">&nbsp<input type="text" class="textfield firstElement" id="alternate-phone" name="alternatePhone" value="${OnlineAccessForm.alternatePhone}" onblur="javascript:this.value=formatPhoneNumber(this.value);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> />
                                                <label >Ext:</label>
                                                <input type="text" class="textfield secondElement" id="alternate-phone-ext" name="alternatePhoneExt" value="${OnlineAccessForm.alternatePhoneExt}" onblur="javascript:cleanNumericField(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>
                                </dl>
			</fieldset>

			<p>For your protection we do not allow first name, last name, or driver license number to be changed online. To change these items, please fax a copy of the account owner's driver license and contact information to 713-437-4147. If further assistance is needed, contact Customer Service at 281-875-EASY(3279) during <a href="${appDelegate.domainName}/about_locations/" title="locations">regular business hours.</a></p>

			<fieldset>

				<dl>

					<dt class="account-type-item first-dt-dd-pair"><label for="account-type" id="for-first-name">Account Type:</label></dt>
						<dd class="account-type-item first-dt-dd-pair"><input type="text" class="textfield" id="account-type" name="acctTypeDescr" value="${OnlineAccessForm.acctTypeDescr}" disabled="disabled"/></dd>

                                        <c:if test="${(not empty OnlineAccessForm.companyName)}">
                                            <dt class="company-name-item"><label for="company-name" id="company-name">Company Name:</label></dt>
                                            <dd class="company-name-item"><input type="text" class="textfield" id="company-name" name="companyName" value="${OnlineAccessForm.companyName}" disabled="disabled"/></dd>
                                        </c:if>

					<dt class="first-name-item"><label for="first-name" id="for-first-name">
                                            <c:choose>
                                               <c:when test="${(not empty OnlineAccessForm.companyName)}">
                                                    Contact First Name:
                                                </c:when>
                                                <c:otherwise>
                                                    First Name:
                                                </c:otherwise>
                                            </c:choose>
                                        </label></dt>
						<dd class="first-name-item"><input type="text" class="textfield" id="first-name" name="firstName" value="${OnlineAccessForm.firstName}" disabled="disabled"/></dd>

					<dt class="last-name-item"><label for="last-name" id="for-last-name">
                                            <c:choose>
                                                <c:when test="${(not empty OnlineAccessForm.companyName)}">
                                                    Contact Last Name:
                                                </c:when>
                                                <c:otherwise>
                                                    Last Name:
                                                </c:otherwise>
                                            </c:choose>
                                        </label></dt>
						<dd class="last-name-item"><input type="text" class="textfield" id="last-name" name="lastName" value="${OnlineAccessForm.lastName}" disabled="disabled"/></dd>

                                        <c:if test="${(not empty OnlineAccessForm.companyName)and (not empty OnlineAccessForm.companyTaxId)}">
                                            <dt class="tax-id-item"><label for="tax-id" id="tax-id">Tax ID Number:</label>
                                            </dt>
                                            <dd class="tax-id-item"><input type="text" class="textfield" id="tax-id" name="companyTaxId" value="${OnlineAccessForm.companyTaxId}" disabled="disabled"/></dd>
                                        </c:if>

					<c:if test="${(not empty OnlineAccessForm.driverLicState) and (not empty OnlineAccessForm.driverLicDisplay)}">
                                        <dt class="drivers-license-number-item">Driver License Number:</dt>
						<dd class="drivers-license-number-item">
							<label for="drivers-license-state" class="accessibility">Driver License State</label>
                                                        <select id="drivers-license-state" name="driverLicState" disabled="disabled">
                                                            <c:forEach var="state" items="${stateDelegate.states}">
                                                                <option value="<c:out value="${state.stateCode}"/>"
                                                                <c:if test="${state.stateCode == OnlineAccessForm.driverLicState}">
                                                                    selected
                                                                </c:if>
                                                                >
                                                                <c:out value="${state.stateCode}"/>
                                                                </option>
                                                            </c:forEach>
                                                        </select>

							<label for="drivers-license-number" class="accessibility">Driver License Number</label>
							<input type="text" class="textfield with-adjacent-form-field" id="drivers-license-number" name="driverLicDisplay" value="${OnlineAccessForm.driverLicDisplay}" disabled="disabled"/>

							<p class="help">A driver license number is required to identify the single person in a household or organization that owns the account.</p>
						</dd>
                                        </dt>
                                        </c:if>
				</dl>

			</fieldset>

		</div> <!-- end of section -->
		
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();" /></li>

			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Contact Info";


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


var emailAddress = '<html:errors property="emailAddress"/>';
var emailAddress2 = '<html:errors property="emailAddress2" />';
var alternateEmail ='<html:errors property="alternateEmail" />';
var confirmAlternateEmail ='<html:errors property="confirmAlternateEmail" />';
var primaryPhone ='<html:errors property="primaryPhone" />';
var alternatePhone ='<html:errors property="alternatePhone" />';
var alternatePhoneExt ='<html:errors property="alternatePhoneExt" />';

	 if (checkForErrors(emailAddress) == true){
		changeTextFieldColor(document.forms[0].emailAddress);
	 }else{
		 changeTextFieldWhite(document.forms[0].emailAddress);
	 }
	 if (checkForErrors(emailAddress2) == true){
		changeTextFieldColor(document.forms[0].emailAddress2);
	 }else{
		 changeTextFieldWhite(document.forms[0].emailAddress2);
	 }
	 if (checkForErrors(alternateEmail) == true){
		changeTextFieldColor(document.forms[0].alternateEmail);
	 }else{
		 changeTextFieldWhite(document.forms[0].alternateEmail);
	 }
	 if (checkForErrors(confirmAlternateEmail) == true){
		changeTextFieldColor(document.forms[0].confirmAlternateEmail);
	 }else{
		 changeTextFieldWhite(document.forms[0].confirmAlternateEmail);
	 }
	  if (checkForErrors(primaryPhone) == true){
		changeTextFieldColor(document.forms[0].primaryPhone);
	 }else{
		 changeTextFieldWhite(document.forms[0].primaryPhone);
	 }
	 if (checkForErrors(alternatePhone) == true){
		changeTextFieldColor(document.forms[0].alternatePhone);
	 }else{
		 changeTextFieldWhite(document.forms[0].alternatePhone);
	 }
	 if (checkForErrors(alternatePhoneExt) == true){
		changeTextFieldColor(document.forms[0].alternatePhoneExt);
	 }else{
		 changeTextFieldWhite(document.forms[0].alternatePhoneExt);
	 }
}


function clearColorOnFields()
{

changeTextFieldWhite(document.forms[0].emailAddress);
changeTextFieldWhite(document.forms[0].emailAddress2);
changeTextFieldWhite(document.forms[0].alternateEmail);
changeTextFieldWhite(document.forms[0].confirmAlternateEmail);
changeTextFieldWhite(document.forms[0].primaryPhone);
changeTextFieldWhite(document.forms[0].alternatePhone);
changeTextFieldWhite(document.forms[0].alternatePhoneExt);

}

function doSubmit()
{
	clearColorOnFields();
	if (document.forms[0].emailAddress.value != document.forms[0].emailAddress2.value) {
        alert("'Confirm E-Mail Address' should match 'E-Mail Address'.");
		changeTextFieldColor(document.forms[0].emailAddress2);
        document.forms[0].emailAddress2.focus();
    } else if (document.forms[0].alternateEmail.value != document.forms[0].confirmAlternateEmail.value) {
		alert("'Confirm Alternate E-Mail Address' should match 'Alternate E-Mail Address'.");
		changeTextFieldColor(document.forms[0].alternateEmail);
        document.forms[0].alternateEmail.focus();
    } else if (document.forms[0].alternateEmail.value != '' && document.forms[0].alternateEmail.value == document.forms[0].emailAddress.value) {
        alert("'Alternate E-Mail Address' cannot be the same as 'E-Mail Address'. ");
		changeTextFieldColor(document.forms[0].alternateEmail);
        document.forms[0].alternateEmail.focus();
    } else if (document.forms[0].alternatePhone.value != '' && document.forms[0].alternatePhone.value==document.forms[0].primaryPhone.value){
        alert("'Alternate Phone' cannot be the same as 'Primary Phone'.");
		changeTextFieldColor(document.forms[0].alternatePhone);
        document.forms[0].alternatePhone.focus();
        return false;
    } else if (checkPhoneField(document.forms[0].primaryPhone) && checkPhoneField(document.forms[0].alternatePhone) 
                && confirmPhoneNumbers()) {
        document.forms[0].submit();
    }
}

function checkPhoneField(elem) {
    if (elem.value == '') return true;
    if (/^[0-9]+[0-9\-]*[0-9]+$/.test(elem.value)) {
        return true;
    } else {
        alert('Please provide a valid phone number');
		changeTextFieldColor(elem);
        elem.focus();
        return false;
    }
}

function confirmPhoneNumbers() {
    var result = true;
    if (document.forms[0].primaryPhone.value != '') {
        var cleanPrimaryPhone = document.forms[0].primaryPhone.value.replace(/[\-]/g, "");
        if (cleanPrimaryPhone.length != 10) {
            result = confirm("Your primary phone number exceeds 10-digits for a valid U.S. phone number. Please click 'Cancel' to correct your entry or 'OK' to continue.");
            if (!result) {
				changeTextFieldColor(document.forms[0].primaryPhone);
                document.forms[0].primaryPhone.focus();
            }
        }
    }

    if (result && document.forms[0].alternatePhone.value != '') {
        var cleanAlternatePhone = document.forms[0].alternatePhone.value.replace(/[\-]/g, "");
        if (cleanAlternatePhone.length != 10) {
            result = confirm("Your alternate phone number exceeds 10-digits for a valid U.S. phone number. Please click 'Cancel' to correct your entry or 'OK' to continue.");
            if (!result) {
				changeTextFieldColor(document.forms[0].alternatePhone);
                document.forms[0].alternatePhone.focus();
            }
        }
    }
    
    return result;
}
</script>