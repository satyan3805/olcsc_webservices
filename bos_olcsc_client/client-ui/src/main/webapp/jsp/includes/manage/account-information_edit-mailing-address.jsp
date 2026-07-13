<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>
<body onload ="loadingBody()">
<div id="content-container">

	<div id="content">
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeMailingAddr.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit mailing information</h1>
                
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
                                        <html:messages id="msg" message="true" property="editMailingAddressError">						
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>

			<p>We will use this address to mail your EZ TAG Account information and other occasional correspondence.</p>

			<fieldset>

				<p id="non-us-address-field"><label for="non-us-address">
                                    <input type="checkbox" class="checkbox" id="non-us-address" name="nonUSAddress" onclick="javascript:clearUSAddrFields(this)"
                                        <c:if test='${OnlineAccessForm.nonUSAddress}'>checked</c:if>  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /> 
                                        My mailing address is outside the <acronym title="United States">U.S.</acronym></label>
                                </p>
                                <input type="hidden" name="nonUSAddress" value="false">
                                <input type="hidden" name="accountClosure" value="${closure || OnlineAccessForm.accountClosure}" />

				<dl>

					<dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
						<dd class="non-us first-dt-dd-pair" id="country-dd">
                                                    <select id="country" name="country" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if>>
                                                        <c:forEach var="country" items="${countryDelegate.countries}">
                                                            <option value="<c:out value="${country.countryCode}"/>"
                                                            <c:set var="str1" value="${country.countryCode}" />
                                                            <c:set var="str2" value="${OnlineAccessForm.country}" />
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
						<dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="address1" value="${OnlineAccessForm.address1}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

					<dt><label for="address-line-2">Address Line 2:</label></dt>
						<dd>&nbsp<input type="text" class="textfield" id="address-line-2" name="address2" value="${OnlineAccessForm.address2}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> /></dd>

					<dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
						<dd class="non-us">&nbsp<input type="text" class="textfield" id="address-line-3" name="address3" value="${OnlineAccessForm.address3}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> /></dd>

					<dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
						<dd class="non-us">&nbsp<input type="text" class="textfield" id="address4" name="address4" value="${OnlineAccessForm.address4}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> /></dd>

					<dt class="domestic"><label for="city">City:</label></dt>
						<dd class="domestic">
							*<input type="text" class="textfield" id="city" name="city" value="${OnlineAccessForm.city}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> />
							<p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if mailing to a military address</p>
						</dd>

					<dt class="domestic"><label for="state">State:</label></dt>
						<dd class="domestic">
							*<select id="state" name="state" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly  </c:if> >
                                                            <c:forEach var="state" items="${stateDelegate.states}">
                                                                <option value="<c:out value="${state.stateCode}"/>"
                                                                    <c:if test="${  (OnlineAccessForm.state != null) ? OnlineAccessForm.state ==  state.stateCode : state.defaultValueFlag == true}">
                                                                        selected
                                                                    </c:if>
                                                                >
                                                                 <c:out value="${state.stateCode}"/>
                                                                </option>
                                                            </c:forEach> 		
							</select>
						</dd>

					<dt class="domestic"><label for="zip">Zip:</label></dt>
						<dd class="domestic">*<input type="text" class="textfield firstElement" id="zip" name="zip" value="${OnlineAccessForm.zip}" onblur="javascript:cleanNumericField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly </c:if> />
                                                <label>-</label>
                                                <input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${OnlineAccessForm.plus4}" onblur="javascript:cleanNumericField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">disabled="disabled"  </c:if> /></dd>
				</dl>

			</fieldset>

		</div> <!-- end of section -->
		
			<ul class="form-actions">
                                <c:if test="${closure || OnlineAccessForm.accountClosure}">
                                  <input type="hidden" name="acctInfoRefund" value="true"/>
                                </c:if>
  <li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do<c:if test="${closure || OnlineAccessForm.accountClosure}">?rf=true&rt=K</c:if>'; return false" title="&rarr; account-information.shtml" /></li>
  <li><input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();"/></li>
                                
			</ul> <!-- end of form-actions -->

<!--                <input type="hidden" name="city" value="${OnlineAccessForm.city}">
                <input type="hidden" name="state" value="${OnlineAccessForm.state}">
                <input type="hidden" name="zip" value="${OnlineAccessForm.zip}">
                <input type="hidden" name="country" value="${OnlineAccessForm.country}">
                <input type="hidden" name="address3" value="${OnlineAccessForm.address3}">
                <input type="hidden" name="address4" value="${OnlineAccessForm.address4}">
-->
		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Mailing Address";


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


var address1 ='<html:errors property="address1"/>';
var address2 ='<html:errors property="address2"/>';
var	address3 ='<html:errors property="address3"/>';
var	address4 ='<html:errors property="address4"/>';
var	city ='<html:errors property="city"/>';
var	zip ='<html:errors property="zip"/>';
var	plus4 ='<html:errors property="plus4"/>';
	
	  if (checkForErrors(address1) == true){
		changeTextFieldColor(document.forms[0].address1);
	 }else{
		 changeTextFieldWhite(document.forms[0].address1);
	 }
	  if (checkForErrors(address2) == true){
		changeTextFieldColor(document.forms[0].address2);
	 }else{
		 changeTextFieldWhite(document.forms[0].address2);
	 }
	  if (checkForErrors(address3) == true){
		changeTextFieldColor(document.forms[0].address3);
	 }else{
		 changeTextFieldWhite(document.forms[0].address3);
	 }
	  if (checkForErrors(address4) == true){
		changeTextFieldColor(document.forms[0].address4);
	 }else{
		 changeTextFieldWhite(document.forms[0].address4);
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
            document.forms[0].address3.value="";
            document.forms[0].address4.value="";
        }
    }
    
    function doSubmit()
    {
        document.forms[0].submit();
    }
</script>
