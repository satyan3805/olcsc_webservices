<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>
<body onload="loadingBody()" >
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeBillingAddress.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit billing address</h1>
		
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
                                        <html:messages id="msg" message="true" property="editBillingAddressError">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>
                                
                                <!--
				<p>Your mailing address is:</p>

				<address class="mailing-label">
					Scott Summers<br />
					4317 Main St<br />
					Houston, TX 73456
				</address>

				<p class="billing-address-is-different-from-mailing-selection"><label for="billing-address-is-different-from-mailing">
                                    <input type="checkbox" class="checkbox" id="billing-address-is-different-from-mailing" name="diffBillingAddress" 
                                    <c:if test='${billingInfoForm.diffBillingAddress}'>checked</c:if>
                                    /> My billing address is different than my mailing address</label></p>
                                    <input type="hidden" name="diffBillingAddress" value="false">
                                -->

				<fieldset id="billing-address-fields">

					<p id="non-us-address-field"><label for="non-us-address"><input type="checkbox" class="checkbox" id="non-us-address" name="nonUsBillingAddress"  onclick="javascript:clearUSAddrFields(this)"
                                            <c:if test='${billingInfoForm.nonUsBillingAddress}'>checked</c:if>
                                   <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>          /> My billing address is outside the <acronym title="United States">U.S.</acronym></label></p>
                                            <input type="hidden" name="nonUsBillingAddress" value="false">

					<dl>

						<dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
							<dd class="non-us first-dt-dd-pair" id="country-dd">
                                                                <select id="country" name="country" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>>
                                                                
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
							<dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="addressLine1" value="${billingInfoForm.addressLine1}" onblur="javascript:removeUnwantedCharReq(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt><label for="address-line-2">Address Line 2:</label></dt>
							<dd>&nbsp<input type="text" class="textfield" id="address-line-2" name="addressLine2" value="${billingInfoForm.addressLine2}" onblur="javascript:removeUnwantedCharReq(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> /></dd>

						<dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
							<dd class="non-us">&nbsp<input type="text" class="textfield" id="address-line-3" name="addressLine3" value="${billingInfoForm.addressLine3}" onblur="javascript:removeUnwantedCharReq(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> /></dd>

						<dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
							<dd class="non-us">&nbsp<input type="text" class="textfield" id="address-line-4" name="addressLine4" value="${billingInfoForm.addressLine4}" onblur="javascript:removeUnwantedCharReq(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

						<dt class="domestic"><label for="city">City:</label></dt>
							<dd class="domestic">
								*<input type="text" class="textfield" id="city" name="city" value="${billingInfoForm.city}" onblur="javascript:removeUnwantedCharReq(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> />
								<p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if billing to a military address</p>
							</dd>

						<dt class="domestic"><label for="state-province">State/Province:</label></dt>
							<dd class="domestic">
                                                                *<select id="state" name="state" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> >
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
							<dd class="domestic">*<input type="text" class="textfield firstElement" id="zip-postal-code" name="zip" value="${billingInfoForm.zip}" onblur="javascript:cleanNumericField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> />
                                                <label>-</label>
                                                <input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${billingInfoForm.plus4}" onblur="javascript:cleanNumericField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /></dd>
					</dl>

				</fieldset>

			</div> <!-- end of section -->
		
			<ul class="form-actions">   
                               
                                  <li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
												
                                <li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();"/></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<!--#include virtual="/includes/manage/status-bar-manage.shtml" -->
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Billing Address";

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


var addressLine1 ='<html:errors property="addressLine1"/>';
var addressLine2 ='<html:errors property="addressLine2"/>';
var	addressLine3 ='<html:errors property="addressLine3"/>';
var	addressLine4 ='<html:errors property="addressLine4"/>';
var	city ='<html:errors property="city"/>';
var	zip ='<html:errors property="zip"/>';
var	plus4 ='<html:errors property="plus4"/>';
	
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
    document.forms[0].submit();
}
</script>