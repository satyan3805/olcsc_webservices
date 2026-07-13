<%@ include file="/jsp/common/Taglibs.jsp" %>

<body onload="loadingBody()" >
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changePassword.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit password</h1>
		
                        <div class="section">

                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">										
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="changePasswordError">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changePasswordError">							
                                            <dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                        </div>
                        
                        <div class="section">
		
				<fieldset>

					<dl>

						<dt class="first-dt-dd-pair"><label for="current-password">Current Password:</label></dt>
							<dd class="first-dt-dd-pair">*<input type="password" class="textfield" id="password" name="password" value="${OnlineAccessForm.password}" onselect="javascript:unselect()" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /> </dd>
							
						<dt><label for="new-password">New Password:</label></dt>
							<dd>
								*<input type="password" class="textfield" id="newPassword" name="newPassword" value="${OnlineAccessForm.newPassword}" onselect="javascript:unselect()" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /> 
								<p class="help">password must be at least 8 characters</p>
							</dd>

						<dt><label for="confirm-new-password">Confirm New Password:</label></dt>
							<dd>*<input type="password" class="textfield" id="confirmNewPassword" name="confirmNewPassword" value="${OnlineAccessForm.confirmNewPassword}" onselect="javascript:unselect()" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /></dd>

					</dl>

				</fieldset>
		
			</div> <!-- end of section -->
		
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();" /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->
</body>
	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Password";

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}
function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

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
function getErrorfields()
{
	var password = '<html:errors property="password"/>';
	var newPassword = '<html:errors property="newPassword"/>';
	var confirmNewPassword = '<html:errors property="confirmNewPassword"/>';
	 if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
	 }
	 if (checkForErrors(newPassword) == true){
		changeTextFieldColor(document.forms[0].newPassword);
	 }else{
		 changeTextFieldWhite(document.forms[0].newPassword);
	 }
	  if (checkForErrors(confirmNewPassword) == true){
		changeTextFieldColor(document.forms[0].confirmNewPassword);
	 }else{
		 changeTextFieldWhite(document.forms[0].confirmNewPassword);
	 }
	//alert("Password errors" + password);

	 var error = '${changePasswordMessage}';
 if (error != '')
 {

	//alert("There is an error in the page" + error);
	changeTextFieldColor(document.forms[0].password);
	changeTextFieldColor(document.forms[0].newPassword);
 }
}
function doSubmit()
{
	
    if(validatePwFormat(document.forms[0].password.value)==false)
    {
        alert("The current password you entered is invalid.");
		changeTextFieldColor(document.forms[0].password);
        document.forms[0].password.focus();
        return;
    }
    
    if(validatePwFormat(document.forms[0].newPassword.value)==false)
    {
        alert("The new password you entered is invalid.");
		changeTextFieldColor(document.forms[0].newPassword);
        document.forms[0].newPassword.focus();
        return;
    }
    
    if (document.forms[0].newPassword.value.toUpperCase() != document.forms[0].confirmNewPassword.value.toUpperCase()) {
        alert("'Confirm New Password' should match 'New Password'.");
		changeTextFieldColor(document.forms[0].confirmNewPassword);
        document.forms[0].confirmNewPassword.focus();
    } else {
        document.forms[0].submit();
    }
}
</script>