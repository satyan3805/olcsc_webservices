<%@ include file="/jsp/common/Taglibs.jsp" %>
<body onload="loadingBody()">
	<div id="content-container">

		<div id="content">

			<!--    <form id="sign-up" action="${pageContext.request.contextPath}/onlineAccessUserInfo.do" method="post">   -->
                        <etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/onlineAccessUserInfo.do">

				<div class="section">

					<h1 id="ez-tag-account-information">EZ TAG Account information</h1>

					<p>For security purposes, please provide the following information:</p>

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
				</div> <!-- end of section -->

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

<<dt class="ez-tag-number-item combo-field-second-dt-dd-pair">EZ TAG Number:<br />
</dt>
<dd class="combo-field-second-dt-dd-pair">
	<label for="ez-tag-number" class="accessibility">EZ TAG Authority</label>
	<select id="ez-tag-number" name="tagIdentifier">
	    <c:forEach var="agency" items="${tagAuthorities}">
		<option value="<c:out value="${agency.barcodePrefix}"/>"
                    <c:if test="${((not empty OnlineAccessForm.tagIdentifier) and (OnlineAccessForm.tagIdentifier == agency.tagIdentifier))}">
			selected
		    </c:if>>
		    <c:out value="${agency.barcodePrefix}"/>
		</option>
	    </c:forEach>
	</select>

	<label for="ez-tag-number" class="accessibility">EZ TAG Number</label>
	<html:text property="tagId" styleClass="textfield with-adjacent-form-field" maxlength="13" styleId="ez-tag-number" onblur="javascript:removeUnwantedChar(this);"/>

	<p class="help">example: XXXX-99999999<br />
			the EZ TAG number can be found on your EZ TAG sticker here:
			<img src="${pageContext.request.contextPath}/meta/media/login/thumbnail-EZTAG-sticker.gif" alt="" /></p>
		<p class="help">Need help finding the tag number on an <a class="ez-tag-references" href="${pageContext.request.contextPath}/home/tagReferencesDisplay.do">older EZ TAG device</a>?</p>
</dd>


							<dt class="drivers-license-number-item combo-field-first-dt-dd-pair">Driver License Number:<br />
									<em>- or -</em></dt>
								<dd class="drivers-license-number-item combo-field-first-dt-dd-pair">
									<label for="drivers-license-state" class="accessibility">Driver License State</label>
									<select id="drivers-license-state" name="driverLicState">
                                                                            <c:forEach var="state" items="${states}">
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

									<p class="help">Your Driver's License or Tax ID number help verify your identity.</p>
								</dd>

								<dt class="tax-id-number-item combo-field-second-dt-dd-pair">
									<label for="tax-id-number">Tax ID Number:</label>
								</dt>

									<dd class="tax-id-number-item combo-field-second-dt-dd-pair"><input type="text" class="textfield" id="tax-id-number" name="companyTaxId" value="${OnlineAccessForm.companyTaxId}" onblur="javascript:removeUnwantedChar(this);"/></dd>
					</dl>

				</fieldset>


				<ul class="form-actions">

					<li><img src="${pageContext.request.contextPath}/meta/media/buttons/setup-online-access.gif" onclick="return doSubmit();" value="Setup Online Access" /></li>

				</ul> <!-- end of form-actions -->

			<!--    </form> -->
                        </etcc-extended:autocompleteOffForm>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-1-of-2">
			Setup online access
			<em>Step 1 of 2</em>
		</p>

	</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="Set Up Online Access";

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
	   if (checkForErrors(drvIdTaxId) == true){
		changeTextFieldColor(document.forms[0].companyTaxId);
	 }else{
		 changeTextFieldWhite(document.forms[0].companyTaxId);
	 }
}

function clearFormFields()
{
	changeTextFieldWhite(document.forms[0].acctId);
	changeTextFieldWhite(document.forms[0].tagId);
	changeTextFieldWhite(document.forms[0].driverLicNbr);
	changeTextFieldWhite(document.forms[0].companyTaxId);

}
    function doSubmit() {
		clearFormFields();
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
