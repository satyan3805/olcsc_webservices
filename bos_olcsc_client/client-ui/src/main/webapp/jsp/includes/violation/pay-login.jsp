<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="tagDelegate"  class="com.etcc.csc.delegate.TagDelegate" scope="page"/>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
    <div id="content-container">

      <div id="content">

        <h1 id="manage-your-account">Make Payment using my EZ Account</h1>

        <form name="pay-login" action="${pageContext.request.contextPath}/violationLoginAccount.do" method="post">

        <div id="login-online" class="section">

          <p>Validate your account:</p>

          <div style="display:none;">
            <dl class="errors">
              <dt>Unable to validate.</dt>
              <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd>
            </dl> <!-- end of errors -->
          </div>

          <fieldset>

            <dl>
              <dt class="first-dt-dd-pair"><label for="userName">Username:</label></dt>
            <dd class="first-dt-dd-pair">
            <input type="text" class="textfield" id="userName" name="userName" />
          </dd>
            <dt><label for="password">Password:</label></dt>
            <dd>
            <input type="password" class="textfield" id="password" name="password" />
          </dd>
          </dl>

          </fieldset>

        <ul class="form-actions">
          <li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/validate-account.gif" value="Pay using EZ Account" onclick="return doSubmit();" /></li>
      </ul> <!-- end of form-actions -->

          <p>If your account is not set up for online access, you can <a href="#" onclick="return switchLogin('login-tag');">validate using an existing EZ TAG and Driver License</a>.</p>

        </div> <!-- end of section -->


        <div id="login-tag" class="section" style="display:none;">

          <p>For security purposes, please provide the following information:</p>

          <div style="display:none;">
            <dl class="errors">
              <dt>Unable to validate.</dt>
              <dd>The data provided does not match.<br /> Please check for accuracy and try again.</dd>
            </dl> <!-- end of errors -->
          </div>

          <fieldset>

            <dl>

              <dt class="first-dt-dd-pair combo-field-first-dt-dd-pair"><label for="account-number">Account number:</label><br />
            <em>- or -</em>
          </dt>
            <dd class="first-dt-dd-pair combo-field-first-dt-dd-pair">
            <input type="text" class="textfield" id="account-number" name="acctId" value="${OnlineAccessForm.acctId}" onblur="javascript:cleanNumericField(this);"/>
            <p class="help">your account number can be found on your statement here:
              <img src="${pageContext.request.contextPath}/meta/media/login/invoice-thumbnail.gif" alt="" />
            </p>
          </dd>

            <dt class="ez-tag-number-item combo-field-second-dt-dd-pair">EZ TAG Number:<br /></dt>
            <dd class="combo-field-second-dt-dd-pair">
            <label for="ez-tag-number" class="accessibility">EZ TAG Authority</label>
            <select id="ez-tag-number" name="tagIdentifier">
	    <c:forEach var="agency" items="${tagDelegate.tagAuthorities}">
              <option value="<c:out value="${agency.barcodePrefix}"/>"
              <c:if test="${((not empty OnlineAccessForm.tagIdentifier) and (OnlineAccessForm.tagIdentifier == agency.barcodePrefix))}">
                selected
              </c:if>
              >
              <c:out value="${agency.tagIdentifier}"/>
              </option>
              </c:forEach>
            </select>

              <label for="ez-tag-number" class="accessibility">EZ TAG Number</label>
              <input type="text" name="tagId" class="textfield with-adjacent-form-field" maxlength="13" id="ez-tag-number" onblur="javascript:removeUnwantedChar(this);" />

                  <p class="help">example: XXXX-99999999<br />
                    the EZ TAG number can be found on your EZ TAG sticker here:
                    <img src="${pageContext.request.contextPath}/meta/media/login/thumbnail-EZTAG-sticker.gif" alt="" /></p>
                  <p class="help">Need help finding the tag number on an <a class="ez-tag-references" href="${pageContext.request.contextPath}/home/tagReferencesDisplay.do">older EZ TAG device</a>?</p>
                </dd>


              <dt class="drivers-license-number-item combo-field-first-dt-dd-pair">Driver License Number:<br />
              <dd class="drivers-license-number-item combo-field-first-dt-dd-pair">
              <label for="drivers-license-state" class="accessibility">Driver License State</label>
              <select id="drivers-license-state" name="driverLicState">
              <c:forEach var="state" items="${stateDelegate.states}">
                <option value="<c:out value="${state.stateCode}"/>"
                <c:if test="${((not empty OnlineAccessForm.driverLicState) and (OnlineAccessForm.driverLicState == state.stateCode)) or ((empty OnlineAccessForm.driverLicState) and (state.defaultValueFlag == true))}">
                  selected
                </c:if>
                >
                <c:out value="${state.stateCode}"/>
                </option>
                </c:forEach> 		
              </select>

                <label for="drivers-license-number" class="accessibility">Driver License Number</label>
                <input type="text" class="textfield with-adjacent-form-field" id="drivers-license-number" name="driverLicNbr" value="${OnlineAccessForm.driverLicNbr}" onblur="javascript:removeUnwantedChar(this);"/>

                <p class="help">Your Driver License number helps verify your identity.</p>
              </dd>

          </fieldset>

        <ul class="form-actions">
          <li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/validate-account.gif" value="Pay using EZ Account" onclick="return doSubmit();" /></li>
      </ul> <!-- end of form-actions -->

          <p>I want to <a href="#" onclick="return switchLogin('login-online');">validate with my username and password</a>.</p>

        </div> <!-- end of section -->

      </form>

      </div> <!-- end of content -->

    </div> <!-- end of content-container -->


<script type="text/javascript">
var activeSection;
loadingBody();

function loadingBody(){
  activeSection = document.getElementById('login-online');
  getErrorfields();
}
function switchLogin(sectionId) {
  activeSection.style.display = 'none';
  activeSection = document.getElementById(sectionId);
  activeSection.style.display = '';
  return false;
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

function getErrorfields() {
	var acctIdTagId = '<html:errors property="acctId"/>';
	var drvIdTaxId = '<html:errors property="driverLicNbr"/>';

	  if (checkForErrors(acctIdTagId) == true){		 
		changeTextFieldColor(document.forms[0].acctId);
	 }else{		 
		 changeTextFieldWhite(document.forms[0].acctId);
	 }
	  
	  if (checkForErrors(acctIdTagId) == true){
		changeTextFieldColor(document.forms[0].tagId);
	 }else{
		 changeTextFieldWhite(document.forms[0].tagId);
	 }
	    if (checkForErrors(drvIdTaxId) == true){
		changeTextFieldColor(document.forms[0].driverLicNbr);
	 }else{
		 changeTextFieldWhite(document.forms[0].driverLicNbr);
	 }
}

function changeFormFieldsWhite() {
	changeTextFieldWhite(document.forms[0].acctId);
	changeTextFieldWhite(document.forms[0].tagId);
	changeTextFieldWhite(document.forms[0].driverLicNbr);
}
    function doSubmit() {
	changeFormFieldsWhite();
        if (checkDriverLicense()) {
            document.forms[0].submit();
            return true;
        } else {
            return false;
        }
    }
    
    function checkDriverLicense() {
//         if (document.forms[0].driverLicState.value != "WA"  &&
//            document.forms[0].driverLicState.value != "-") {
//            
//            if (document.forms[0].driverLicNbr.value != '' && 
//                !validateNumeric(document.forms[0].driverLicNbr.value))
//            {
//                alert('Driver License number is invalid. Please re-enter.');
//                document.forms[0].driverLicNbr.focus();
//                return false;
//            }
//        }
//            
        if(/^[0-9a-zA-Z]*$/.test(document.forms[0].driverLicNbr.value)==false)
        {
            alert("DL number is invalid. Please re-enter.");
			changeTextFieldColor(document.forms[0].driverLicNbr); 
            document.forms[0].driverLicNbr.focus();
            return false;
        }
            
            
        if (document.forms[0].driverLicState.value == "TX") {
            if (document.forms[0].driverLicNbr.value != ''
                    && (document.forms[0].driverLicNbr.value.length != 8 
                        ||!validateNumeric(document.forms[0].driverLicNbr.value))) {
                alert('Texas DL number is invalid. Please re-enter.');
				changeTextFieldColor(document.forms[0].driverLicNbr); 
                document.forms[0].driverLicNbr.focus();
                return false;
            }
        }
        
        return true;
    }

</script>
