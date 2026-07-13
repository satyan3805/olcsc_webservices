<%@ include file="/jsp/common/Taglibs.jsp" %>
<body onLoad="javascript:loadingBody()">
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeUserName.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit username</h1>
		
                        <div class="section">

                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">                                           
											<dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="changeUsernameMessage">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changeUsernameMessage">							  
                                            <dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                        </div>

			<div class="section">
		
				<fieldset>

					<dl>

						<dt class="first-dt-dd-pair"><label for="current-password">Current Password:</label></dt>
							<dd class="first-dt-dd-pair">*<input type="password" class="textfield" id="password" name="password" value="${OnlineAccessForm.password}" onselect="javascript:unselect()" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /></dd>


						<dt><label for="username">Username:</label></dt>
							<dd>
								*<input type="text" class="textfield" id="loginId" name="loginId" value="${OnlineAccessForm.loginId}" maxlength="80" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if>/>
								<p class="help">username must be at least 6 alpha-numeric characters</p>
							</dd>
				</fieldset>
		
			</div> <!-- end of section -->
		
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do?cancel=true'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();" title="&rarr; account-information.shtml" /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->
        
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit User Name";

function doSubmit()
{
    if(validatePwFormat(document.forms[0].password.value)==false)
    {
        alert("The password you entered is invalid.");
		document.forms[0].password.style.background="#00FFCC";
        document.forms[0].password.focus();
        return;
    }
    document.forms[0].submit();
}

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


var password = '<html:errors property="password"/>';
var loginId = '<html:errors property="loginId"/>';
	 if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
	 }
	 if (checkForErrors(loginId) == true){
		changeTextFieldColor(document.forms[0].loginId);
	 }else{
		 changeTextFieldWhite(document.forms[0].loginId);
	 }

 var error = '${changeUsernameMessage}';
 if (error != "")
 {
	
	changeTextFieldColor(document.forms[0].password);
	changeTextFieldColor(document.forms[0].loginId);
 }
}
</script>