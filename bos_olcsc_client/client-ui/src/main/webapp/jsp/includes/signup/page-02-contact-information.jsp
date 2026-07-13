<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
  <jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
  <jsp:useBean id="countryDelegate"  class="com.etcc.csc.delegate.CountryDelegate" scope="page"/>


  <body onload="javascript:loadingBody()">
  <div id="content-container">

    <div id="content">

      <form method="post" name="contactInfoForm" action="${pageContext.request.contextPath}/signupContactInfo.do" autocomplete="off">
        <c:if test="${(requestScope.fromConfirmation) or (contactInfoForm.fromConfirmation) or (fromConfirmationContact) or (fromConfirmation)}">
           <c:set var="fromConfirmationVal" value="true"/>
        </c:if>
	<input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>
	<h1 id="contact-information">Contact Information</h1>



	<div class="section">

	  <div>
	    <logic:messagesPresent message="false">
	      <dl class="errors"/>
	      <html:messages id="msg" message="false">
		<dd><bean:write name="msg"/></dd>
	      </html:messages>
	    </logic:messagesPresent>
	    <logic:messagesPresent message="true">
	      <dl class="errors"/>
	      <html:messages id="msg" message="true" property="invalidAccount">
		<dd>${msg}</dd>
	      </html:messages>
	    </logic:messagesPresent>
		

	  </div>
	  
	  <h5 id="notation">* Required fields.</h5>

	  <fieldset>

	    <dl>

	      <dt class="first-dt-dd-pair"><label for="first-name">Account Type:</label></dt>
	      <dd class="first-dt-dd-pair">
		<label for="personal" id="personal-label">
		  <input type="radio" class="radio-button" id="personal" name="accountType" value="personal"
		    <c:if test="${empty contactInfoForm}">
		      checked="checked"
		    </c:if>
		    <c:if test="${contactInfoForm.accountType==null}">
		      checked="checked"
		    </c:if>  
		    <c:if test="${contactInfoForm.accountType=='personal'}">
		      checked="checked"
		    </c:if> 
		    /> 
		    Personal
		    <span class="accessibility"> Account</span>
		</label>

		<label for="business" id="business-label">
		  <input type="radio" class="radio-button" id="business" name="accountType" value="business"
		    <c:if test="${(not empty contactInfoForm) and (contactInfoForm.accountType eq'business')}">
		      checked="checked"
		    </c:if>/>
		    Business
		    <span class="accessibility"> Account</span>
		</label>
	      </dd>

	      <dt class="company-name-item"><label for="company-name" id="for-company-name">Company Name:</label></dt>
	      <dd class="company-name-item">&nbsp;<input type="text" class="textfield" id="company-name" name="companyName" value="${contactInfoForm.companyName}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt class="first-name-item"><label for="first-name" id="for-first-name" maxlength="30" onblur="javascript:cleanField(this);">First Name:</label></dt>
	      <dd class="first-name-item">*<input type="text" class="textfield" id="first-name" name="firstName" value="${contactInfoForm.firstName}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt class="last-name-item"><label for="last-name" id="for-last-name" maxlength="40" onblur="javascript:cleanPhoneField(this);">Last Name:</label></dt>
	      <dd class="last-name-item">*<input type="text" class="textfield" id="last-name" name="lastName" value="${contactInfoForm.lastName}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt><label for="primary-phone">Primary Phone:</label></dt>
	      <dd>*<input type="text" class="textfield" id="primary-phone" name="primaryPhone" value="${contactInfoForm.primaryPhone}" onblur="javascript:this.value=formatPhoneNumber(this.value);"/></dd>

	      <dt><label for="alternate-phone">Alternate Phone:</label></dt>
	      <dd><label for="alternate-phone-ext" class="accessibility"></label>&nbsp;<input type="text" class="textfield firstElement" id="alternate-phone" name="alternatePhone" value="${contactInfoForm.alternatePhone}" onblur="javascript:this.value=formatPhoneNumber(this.value);"/>
		<label for="alternate-phone-ext">&nbsp;&nbsp;Ext:</label><input type="text" class="textfield secondElement" id="alternate-phone-ext" name="altPhoneExt" value="${contactInfoForm.altPhoneExt}" onblur="javascript:cleanPhoneField(this);"/>
	      </dd>

	      <dt class="tax-id-number-item">
		<label for="tax-id-number">Tax ID Number:</label>
		<em>- or -</em>
	      </dt>

	      <dd class="tax-id-number-item">**<input type="text" class="textfield" id="tax-id-number" name="taxId" value="${contactInfoForm.taxId}" /></dd>

	      <dt class="drivers-license-number-item"><label for="dlNumber" id="for_dl_number">Driver License Number:</label></dt>
	      <dd class="drivers-license-number-item">
		<label for="drivers-license-state" class="accessibility">Driver License State</label>
		<span id="driver-lic-prefix">*</span><select id="drivers-license-state" name="driversLicState">
		  <c:forEach var="state" items="${stateDelegate.states}">
          <%-- Displaying State name here makes this box too wide for the line, so just use code. --%>
		    <option value="${state.stateCode}"
<c:if test="${((not empty contactInfoForm.driversLicState) and (contactInfoForm.driversLicState==state.stateCode)) or ((empty contactInfoForm.driversLicState) and (state.defaultValueFlag == true))}">
selected </c:if>>
            <c:out value="${state.stateCode}"/>
		    </option>
		  </c:forEach> 		
		</select>

		<label for="drivers-license-number" class="accessibility">Driver License Number</label>
		<input type="text" class="textfield with-adjacent-form-field" id="drivers-license-number" name="driversLic" value="${contactInfoForm.driversLic}" onblur="javascript:cleanAlphaNumericField(this);" />
		<p class="help">A driver's license number is required to identify the owner of this account.</p>
	      </dd>	    
	      <dd class="add-authorized-contact"><p class="help">Authorized Contacts are individuals you are allowing to access and manage your account.</p></dd>
	    </dl>
		<c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="varStatus" begin="0" end="2">
			<li>
	                      <label for="authorized-contact-${varStatus.index}-full-name"><c:out value="${contact.firstName} ${contact.lastName}" /></label></li>
		</c:forEach>
	<html:hidden property="whichPage" value="contact-info-page"/>
	<c:set var="page"  value="${contactInfoForm.whichPage}" />	
	<c:choose>
		<c:when test="${page == 'incomplete-page'}">		  
			<c:set var="whichPage" value="incomplete-page" scope="session" />
		</c:when>
		<c:otherwise>		  
			<c:set var="whichPage" value="contact-info-page" scope="session" />
		</c:otherwise>
	</c:choose>
    
   
	   <!-- <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/add-another-contact.gif" value="Add another contact" onclick="javascript:addContact('signUp'); return false;"/> -->
	   <a href="javascript:addContact('signUp')" > Add Authorized Contacts </a>
	    <p class="help">List other people who are allowed to make changes to this account <br />(example: Lisa Smith, or Bill Collins)</p>
	    
	    <!-- End of FDD authorized contact implementation section  -->

	  </fieldset>

	</div> <!-- end of section -->

	<div class="section">

	  <!--<div class="section-with-primary-and-secondary-content-interior">-->

	  <!--<div class="primary-section-content">-->

	  <h2>Mailing Address:</h2>

	  <p>We will use this address to mail your EZ TAG Account information and other occasional correspondence.</p>

	  <fieldset>

	    <p id="non-us-address-field"><label for="non-us-address">
		<input type="checkbox" class="checkbox" id="non-us-address" name="nonUSAddress" 
		  <c:if test='${contactInfoForm.nonUSAddress}'>checked</c:if> onclick="javascript:clearUSAddrFields(this);"/>
		  My mailing address is outside the <acronym title="United States">U.S.</acronym>
	      </label></p>

	    <dl>

	      <dt class="non-us first-dt-dd-pair" id="country-dt"><label for="country">Country:</label></dt>
	      <dd class="non-us first-dt-dd-pair" id="country-dd">
		*<select id="country" name="country">
		  <option value=""></option>
		  <c:forEach var="rec" items="${countryDelegate.countries}">
		    <option value="${rec.countryCode}"<c:if test="${contactInfoForm.country == rec.countryCode}"> selected </c:if>>${rec.country}</option>
		  </c:forEach>
		</select>
	      </dd>

	      <dt id="address-line-1-dt"><label for="address-line-1">Address Line 1:</label></dt>
	      <dd id="address-line-1-dd">*<input type="text" class="textfield" id="address-line-1" name="addressLine1" value="${contactInfoForm.addressLine1}" onblur="javascript:removeUnwantedChar(this);"/> </dd>

	      <dt><label for="address-line-2">Address Line 2:</label></dt>
	      <dd>&nbsp;<input type="text" class="textfield" id="address-line-2" name="addressLine2" value="${contactInfoForm.addressLine2}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt class="non-us"><label for="address-line-3">Address Line 3:</label></dt>
	      <dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-3" name="addressLine3" value="${contactInfoForm.addressLine3}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt class="non-us"><label for="address-line-4">Address Line 4:</label></dt>
	      <dd class="non-us">&nbsp;<input type="text" class="textfield" id="address-line-4" name="addressLine4" value="${contactInfoForm.addressLine4}" onblur="javascript:removeUnwantedChar(this);"/></dd>

	      <dt class="domestic"><label for="city">City:</label></dt>
	      <dd class="domestic">
		*<input type="text" class="textfield" id="city" name="city" value="${contactInfoForm.city}" onblur="javascript:removeUnwantedChar(this);"/>
		<p class="help">Enter &ldquo;APO&rdquo; or &ldquo;FFO&rdquo; if mailing to a military address</p>
	      </dd>

	      <dt class="domestic"><label for="state">State:</label></dt>
	      <dd class="domestic">
		*<select id="state" name="state">
		  <c:forEach var="state" items="${stateDelegate.states}">
		    <option value="<c:out value="${state.stateCode}"/>"
		      <c:if test="${((not empty contactInfoForm.state) and (contactInfoForm.state == state.stateCode)) or ((empty contactInfoForm.state) and (state.defaultValueFlag == true))}">
			selected
		      </c:if>
		      >
		      <c:out value="${state.stateCode}"/>
		    </option>
		  </c:forEach>                                                                                     
		</select>
	      </dd>

	      <dt class="domestic"><label for="zip">Zip:</label></dt>
	      <dd class="domestic">*<input type="text" class="textfield firstElement" id="zip" name="zip" value="${contactInfoForm.zip}" onblur="javascript:cleanNumericField(this);"/>
		<label for="plus4">&nbsp;Plus4: </label><input type="text" class="textfield secondElement" id="plus4" name="plus4" value="${contactInfoForm.plus4}" onblur="javascript:cleanNumericField(this);"/>
	      </dd>
	    </dl>

	  </fieldset>	

	</div> <!-- end of section with-primary-and-secondary-content -->

	<div class="section">

	  <h2>Preferences:</h2>

	  <dl>
	    <dt>How would you like to be notified of account updates and other changes?</dt>

	    <dd><label for="by-mail"><input type="checkbox" class="checkbox" id="by-mail" name="byMail" <c:if test="${contactInfoForm.byMail}">checked</c:if>/> by mail</label></dd>
	    <dd><label for="by-e-mail-message"><input type="checkbox" class="checkbox" id="by-e-mail-message" name="byEmail" <c:if test="${(empty contactInfoForm.byEmail) or contactInfoForm.byEmail}">checked="checked"</c:if>/> by e-mail message</label></dd>

	  </dl>
	</div> <!-- end of section -->

	<ul class="form-actions">
	  <c:if test="${fromConfirmationVal}">
	    <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goToConfirmation();"/></li>
	  </c:if>
	  <li><img src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Contact Information" onclick="javascript:return checkData();"/></li>
	</ul> <!-- end of form-actions -->

      </form>

    </div> <!-- end of content -->

    <p class="progress-bar" id="step-2-of-6">
      New EZ TAG Account
      <em>Step 2 of 6</em>
    </p>

  </div> <!-- end of content-container -->
</body>
  <script type="text/javascript">
    var submitted = false;
    document.contactInfoForm.firstName.focus();


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

var companyName = '<html:errors property="companyName"/>';
var firstName = '<html:errors property="firstName"/>';
var lastName ='<html:errors property="lastName"/>';
var primaryPhone = '<html:errors property="primaryPhone" />';
var alternatePhone ='<html:errors property="alternatePhone"/>';
var altPhoneExt= '<html:errors property="altPhoneExt"/>';
var taxId = '<html:errors property="taxId"/>';
var driversLic = '<html:errors property="driversLic"/>';
var addressLine1 = '<html:errors property="addressLine1"/>';
var city = '<html:errors property="city"/>';
var zip = '<html:errors property="zip"/>';
var plus4 = '<html:errors property="plus4"/>';
var temp= '<html:errors />';
var nonUsAddrCityAndZip =  '<html:errors property="nonUSAddress"/>';
var fousfield = true;

//alert("temp:"+temp);

 if (checkForErrors(companyName) == true){
		changeTextFieldColor(document.forms[0].companyName);
                if(fousfield){
                document.forms[0].companyName.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].companyName);
	 }
 if (checkForErrors(firstName) == true){
		changeTextFieldColor(document.forms[0].firstName);
                if(fousfield){
                document.forms[0].firstName.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].firstName);
	 }
if (checkForErrors(lastName) == true){
		changeTextFieldColor(document.forms[0].lastName);
                if(fousfield){
                document.forms[0].lastName.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].lastName);
	 }
	 if (checkForErrors(primaryPhone) == true){
		changeTextFieldColor(document.forms[0].primaryPhone);
                if(fousfield){
                document.forms[0].primaryPhone.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].primaryPhone);
	 }
if (checkForErrors(alternatePhone) == true){
		changeTextFieldColor(document.forms[0].alternatePhone);
                if(fousfield){
                document.forms[0].alternatePhone.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].alternatePhone);
	 }
	 if (checkForErrors(altPhoneExt) == true){
		changeTextFieldColor(document.forms[0].altPhoneExt);
                if(fousfield){
                document.forms[0].altPhoneExt.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].altPhoneExt);
	 }
if (checkForErrors(taxId) == true){
		changeTextFieldColor(document.forms[0].taxId);
                if(fousfield){
                document.forms[0].taxId.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].taxId);
	 }
if (checkForErrors(driversLic) == true){
		changeTextFieldColor(document.forms[0].driversLic);
                if(fousfield){
                document.forms[0].driversLic.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].driversLic);
	 }
if (checkForErrors(addressLine1) == true){
		changeTextFieldColor(document.forms[0].addressLine1);
                if(fousfield){
                document.forms[0].addressLine1.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].addressLine1);
	 }
	 if (checkForErrors(city) == true){
		changeTextFieldColor(document.forms[0].city);
                if(fousfield){
                document.forms[0].city.focus();
                fousfield = false;
                }
	 }else{                
		 changeTextFieldWhite(document.forms[0].city);
	 }
	  if (checkForErrors(zip) == true){
		changeTextFieldColor(document.forms[0].zip);
                if(fousfield){
                document.forms[0].zip.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].zip);
	 }
	 if (checkForErrors(plus4) == true){
		changeTextFieldColor(document.forms[0].plus4);
                if(fousfield){
                document.forms[0].plus4.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].plus4);
	 }
         
if(checkForErrors(nonUsAddrCityAndZip) && nonUsAddrCityAndZip.match("City") != null && nonUsAddrCityAndZip.match("City")=='City'){
    changeTextFieldColor(document.forms[0].city);
     if(fousfield){
                document.forms[0].city.focus();
                fousfield = false;
                }
}
if(checkForErrors(nonUsAddrCityAndZip) && nonUsAddrCityAndZip.match("Zip")!=null && nonUsAddrCityAndZip.match("Zip")=='Zip'){
    changeTextFieldColor(document.forms[0].zip);
     if(fousfield){
                document.forms[0].zip.focus();
                fousfield = false;
                }
}

}

    function addContact(pageValue) {
	 // alert("")
	  var page = String("<c:out value='${contactInfoForm.whichPage}' />");
	  
	  if(page != null && page == "incomplete-page"){
		  document.contactInfoForm.whichPage.value="incomplete-page";
		document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do?method=get";
		document.forms[0].submit();
	  }else{
		document.contactInfoForm.whichPage.value="contact-info-page";
		 document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do?page="+pageValue;
		document.forms[0].submit();
	  }
    }

    function addAuthorizedContact() {
      document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do";
      document.forms[0].submit();
    }

    function deleteContact(index) {
      if (index == 0) return;
      document.forms[0].action = "${pageContext.request.contextPath}/deleteAuthorizedContact.do?index="+index;
      document.forms[0].submit();
    }

    function checkData() {
      if (validateLicNbrOrTaxId() && checkAltPhone() 
          && checkPhoneField(document.forms[0].primaryPhone) && checkPhoneField(document.forms[0].alternatePhone)
          && confirmPhoneNumbers()) {
            if (!submitted) {
              submitted = true;
              document.forms[0].submit();
              return true;
            }
      }
      return false;
    }
    
    function validateLicNbrOrTaxId(){
         if(document.getElementById('business').checked == true){
         var driversLicNbr = document.contactInfoForm.driversLic.value;
         var taxIdNbr = document.contactInfoForm.taxId.value;
            if(driversLicNbr !=null && driversLicNbr != '')
              {
                return  validateLicNbr();
              }else if(taxIdNbr !=null && taxIdNbr != '')
                {                  
                  return true;
                }else {
                    alert('Please Enter either TaxId or DL number.');
                      changeTextFieldColor(document.contactInfoForm.driversLic);	
                      document.contactInfoForm.driversLic.focus();
                      changeTextFieldColor(document.contactInfoForm.driversLic);
                    return false;
                }
            return true;
        }else return validateLicNbr();
    }

    function validateLicNbr() {
      var driversLicNbr = document.contactInfoForm.driversLic.value;
      var state = document.contactInfoForm.driversLicState.value;
     
      if (state == "TX") {
        if ( ! /^[0123][\d]{7}$/.test(driversLicNbr) ) {
            alert('Texas DL number is invalid. Please re-enter.');
			changeTextFieldColor(document.contactInfoForm.driversLic);//Lakshmi	
            document.contactInfoForm.driversLic.focus();
            return false;
        }
        return true;
      }
      if (   state=="AL" || state=="AK" || state=="AR" || state=="CO"
          || state=="CT" || state=="DE" || state=="GA" || state=="IL"
          || state=="IN" || state=="LA" || state=="MS" || state=="NV"
          || state=="NM" || state=="NC" || state=="OR" || state=="PA"
          || state=="SC" || state=="SD" || state=="TN" || state=="UT"
          || state=="WY" ) {
            if ( driversLicNbr != "" && !validateNumeric(driversLicNbr)) {
              alert('DL number is invalid. Please re-enter.');
			  changeTextFieldColor(document.contactInfoForm.driversLic);//Lakshmi
              document.contactInfoForm.driversLic.focus();
              return false;
        }
        return true;
      }
      if (   state=="-" || state=="AS" || state=="AZ" || state=="AA"
          || state=="AE" || state=="AP" || state=="CA" || state=="FM"
          || state=="FL" || state=="GU" || state=="HI" || state=="ID"
          || state=="IA" || state=="KS" || state=="KY" || state=="ME"
          || state=="MH" || state=="MD" || state=="MA" || state=="MI"
          || state=="MN" || state=="MO" || state=="MT" || state=="NE"
          || state=="NH" || state=="NJ" || state=="NY" || state=="ND"
          || state=="MP" || state=="OH" || state=="OK" || state=="PW"
          || state=="PR" || state=="RI" || state=="RI" || state=="VT"
          || state=="VI" || state=="VA" || state=="DC" || state=="WA"
          || state=="WA" || state=="WV" || state=="WI" ) {
            if ( ! /[A-Za-z0-9]+/.test(driversLicNbr) ) {
              alert('DL number is invalid. Please re-enter.');
			  changeTextFieldColor(document.contactInfoForm.driversLic);//Lakshmi
              document.contactInfoForm.driversLic.focus();
              return false;
        }
        return true;
      }
    }


    function checkZipandPlus4() {
      if (!document.contactInfoForm.nonUSAddress.checked && document.contactInfoForm.zip.value != '' && document.contactInfoForm.zip.value.length != 5) {
        alert('The zip code is invalid. It should always be comprised of 5 digits');
		changeTextFieldColor(document.contactInfoForm.zip);//Lakshmi
        document.contactInfoForm.zip.focus();
        return false;
      }
      if (!document.contactInfoForm.nonUSAddress.checked && document.contactInfoForm.plus4.value != '' && document.contactInfoForm.plus4.value.length != 4) {
        alert('The plu4 is invalid. It should always be comprised of 4 digits');
		changeTextFieldColor(document.contactInfoForm.plus4);//Lakshmi
        document.contactInfoForm.plus4.focus();
        return false;
      }
      return true;
    }


    function checkPhoneField(elem) {
      if (elem.value == '') return true;
      if (/^[0-9]+[0-9\-]*[0-9]+$/.test(elem.value)) {
        return true;
      } else {
        alert('Please provide a valid phone number');
		changeTextFieldColor(document.contactInfoForm.elem);//Lakshmi
        elem.focus();
        return false;
     }
    }

    function confirmPhoneNumbers() {
      var result = true;
      if (document.forms[0].primaryPhone.value != '') {
        var cleanPrimaryPhone = document.forms[0].primaryPhone.value.replace(/[\-]/g, "");
        if (cleanPrimaryPhone.length > 10) {
          result = confirm("Your primary phone number exceeds 10-digits for a valid U.S. phone number. Please click 'Cancel' to correct your entry or 'OK' to continue.");
          if (!result) {
			  changeTextFieldColor(document.contactInfoForm.primaryPhone);//Lakshmi
            document.forms[0].primaryPhone.focus();
          }
        }
      }
      if (result && document.forms[0].alternatePhone.value != '') {
        var cleanAlternatePhone = document.forms[0].alternatePhone.value.replace(/[\-]/g, "");
        if (cleanAlternatePhone.length > 10) {
          result = confirm("Your alternate phone number exceeds 10-digits for a valid U.S. phone number. Please click 'Cancel' to correct your entry or 'OK' to continue.");
          if (!result) {
			  changeTextFieldColor(document.contactInfoForm.alternatePhone);//Lakshmi
          document.forms[0].alternatePhone.focus();
          }
        }
      }
      return result;
    }

    function checkAltPhone() {
      if (document.contactInfoForm.alternatePhone.value != '' && document.contactInfoForm.alternatePhone.value==document.contactInfoForm.primaryPhone.value) {
        alert("'Alternate Phone' cannot be the same as 'Primary Phone'.");
		changeTextFieldColor(document.contactInfoForm.alternatePhone);//Lakshmi
        document.contactInfoForm.alternatePhone.focus();
        return false;
      }
    return true;
    }

    function goToConfirmation() {
      if (!submitted) {
        submitted = true;
        document.contactInfoForm.action ="${pageContext.request.contextPath}/signupConfirmationDisplay.do";
        document.contactInfoForm.submit();
      }
    }

    function clearUSAddrFields(chk) {
      if (chk.checked) {
        document.contactInfoForm.city.value="";
        document.contactInfoForm.zip.value="";
        document.contactInfoForm.plus4.value="";
        document.contactInfoForm.country.selectedIndex=0;
      } else {
        document.contactInfoForm.addressLine3.value="";
        document.contactInfoForm.addressLine4.value="";
      }
    }
 

  </script>
<!--
/*
    function displayAuthorizedAgents() {
    authorizedAgentList = getAuthorizedAgents();
    nbrAuthorizedAgents = AuthorizedAgentsList().size;

      if (nbrAuthorizedAgents != 0) {
        Create a window of size nbrAuthorizedAgents;
        field titles are: First Name (20), Last Name(25), Password(15);
        populate the fields of the window;
        display populated window;
      }
    }

from driversLic validation:
driversLicNbr != '' && driversLicNbr.length != 8
      // write and install validateAlphanumeric fuction
            if ( driversLicNbr != "" && !validateAlphanumeric(driversLicNbr)) {

*/
-->

    <!--
    /*
    function validateTaxId() {
    if (document.contactInfoForm.taxId.value != ''
    && (document.contactInfoForm.taxId.value.length < 9 
    || document.contactInfoForm.taxId.value.length > 11)) {
    alert('Company Tax ID is invalid. Please re-enter.');
    document.contactInfoForm.taxId.focus();
    return false;
    } else {
    return true;
    }
    }
    */
    -->
