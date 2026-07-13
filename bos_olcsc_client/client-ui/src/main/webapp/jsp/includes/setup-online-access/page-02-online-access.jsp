<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate"  class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>
<body onload="loadingBody()" >
	<div id="content-container">

		<div id="content">

			<form id="sign-up" action="${pageContext.request.contextPath}/onlineAccessComplete.do" method="post" AUTOCOMPLETE="off">

				<div class="section">

					<h1 id="online-account-information">Online account information</h1>

					<p>Please provide the following information to setup your online access:</p>
                                        
                                            <logic:messagesPresent message="false">
                                                <dl class="errors"/>
                                                <html:messages id="msg" message="false">
                                                    <dd><bean:write name="msg"/></dd>
                                                </html:messages>
                                            </logic:messagesPresent>
                                            <logic:messagesPresent message="true">
                                                <dl class="errors"/>
                                                <html:messages id="msg" message="true">
                                                    <dd><bean:write name="msg"/></dd>
                                                </html:messages>
                                            </logic:messagesPresent>

				</div> <!-- end of section -->

				<fieldset>

					<dl>

						<dt class="first-dt-dd-pair"><label for="username">Username:</label></dt>
							<dd class="first-dt-dd-pair">
								*<input type="text" maxlength="80" class="textfield" id="username" name="loginId" value="${OnlineAccessForm.loginId}" onblur="javascript:removeUnwantedChar(this);"/>
								<p class="help">username must be at least 6 alpha-numeric characters</p>
							</dd>

						<dt><label for="password">Password:</label></dt>
							<dd>
								*<input type="password" maxlength="30" class="textfield" id="password" name="password" value="${OnlineAccessForm.password}" onselect="javascript:unselect()" />
								<p class="help">password must be at least 8 alpha-numeric characters</p>
							</dd>


						<dt><label for="confirm-password">Confirm Password:</label></dt>
							<dd>*<input type="password" maxlength="30" class="textfield" id="confirm-password" name="confirmPassword" value="${OnlineAccessForm.confirmPassword}" onselect="javascript:unselect()"/></dd>

						<dt><label for="e-mail-address">E-Mail Address:</label></dt>
							<dd>*<input type="text" maxlength="80" class="textfield" id="e-mail-address" name="emailAddress" value="${OnlineAccessForm.emailAddress}" onblur="javascript:removeUnwantedChar(this);"/>
                                                            <p class="help">This field is case sensitive</p>
                                                        </dd>
                                                        
						<dt><label for="confirm-e-mail-address">Confirm E-Mail Address:</label></dt>
							<dd>*<input type="text" maxlength="80" class="textfield" id="confirm-e-mail-address" name="emailAddress2" value="${OnlineAccessForm.emailAddress2}" onblur="javascript:removeUnwantedChar(this);"/></dd>

					</dl>

				</fieldset>

				<h5>If you forget your password, this information will allow you to log in:</h5>

				<fieldset>

					<dl>

						<dt class="first-dt-dd-pair"><label for="security-question">Security Question:</label></dt>
							<dd class="first-dt-dd-pair">
					                    *<select name="securityQuestionID" id="security-question">
                                                                <c:forEach var="sq" items="${sqDelegate.securityQuestions}">
                                                                    <option value="<c:out value="${sq.securityQuestionID}"/>"
                                                                        <c:if test="${OnlineAccessForm.securityQuestionID == sq.securityQuestionID}">
                                                                            selected
                                                                        </c:if>
                                                                    >
                                                                    <c:out value="${sq.securityQuestion}"/>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
							</dd>

						<dt><label for="security-answer">Your Answer<span class="accessibility">to the security question</span>:</label></dt>
							<dd>*<input type="password" maxlength="20" class="textfield" id="security-answer" name="securityQuestionAnswer" value="${OnlineAccessForm.securityQuestionAnswer}" onselect="javascript:unselect()"/></dd>
                                                        
                                                        <dt><label for="security-answer2">Confirm Your Answer<span class="accessibility">to the security question</span>:</label></dt>
                                                            <dd>*<input type="password" maxlength="20" class="textfield" id="security-answer2" name="securityQuestionAnswer2" value="${OnlineAccessForm.securityQuestionAnswer2}" onselect="javascript:unselect()"/></dd>

							<dt><label for="alternate-e-mail-address">Alternate E-Mail Address:</label></dt>
								<dd>&nbsp<input type="text" maxlength="80" class="textfield" id="alternate-e-mail-address" name="alternateEmail" value="${OnlineAccessForm.alternateEmail}" onblur="javascript:removeUnwantedChar(this);"/>
                                                                    <p class="help">This field is case sensitive</p>
                                                                </dd>

							<dt><label for="confirm-alternate-e-mail-address">Confirm Alternate E-Mail Address:</label></dt>
								<dd>&nbsp<input type="text" maxlength="80" class="textfield" id="confirm-alternate-e-mail-address" name="confirmAlternateEmail" value="${OnlineAccessForm.confirmAlternateEmail}" onblur="javascript:removeUnwantedChar(this);"/></dd>

					</dl>

				</fieldset>

				<ul class="form-actions">

					<li><img src="${pageContext.request.contextPath}/meta/media/buttons/complete-online-access.gif" value="Complete Online Access" onClick="doSubmit();"/></li>

				</ul> <!-- end of form-actions -->

			</form>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-2-of-2">
			Setup online access
			<em>Step 2 of 2</em>
		</p>

	</div> <!-- end of content-container -->
  </body>
<script type="text/javascript">
var submitted = false;



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



var loginId = '<html:errors property="loginId"/>';
var password ='<html:errors property="password"/>';
var confirmPassword = '<html:errors property="confirmPassword"/>';
var emailAddress = '<html:errors property="emailAddress"/>';
var	emailAddress2 = '<html:errors property="emailAddress2"/>';
var	securityQuestionAnswer = '<html:errors property="securityQuestionAnswer"/>'; 
var	securityQuestionAnswer2 = '<html:errors property="securityQuestionAnswer2"/>'; 
var	alternateEmail = '<html:errors property="alternateEmail"/>';
var confirmAlternateEmail = '<html:errors property="confirmAlternateEmail"/>';

if (checkForErrors(loginId) == true){
		changeTextFieldColor(document.forms[0].loginId);
	 }else{
		 changeTextFieldWhite(document.forms[0].loginId);
	 }

	  if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
	 }

	if (checkForErrors(confirmPassword) == true){
		changeTextFieldColor(document.forms[0].confirmPassword);
	 }else{
		 changeTextFieldWhite(document.forms[0].confirmPassword);
	 }	

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

	 if (checkForErrors(securityQuestionAnswer) == true){
		changeTextFieldColor(document.forms[0].securityQuestionAnswer);
	 }else{
		 changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
	 }	

	 if (checkForErrors(securityQuestionAnswer2) == true){
		changeTextFieldColor(document.forms[0].securityQuestionAnswer2);
	 }else{
		 changeTextFieldWhite(document.forms[0].securityQuestionAnswer2);
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
}

function clearFormFieldErrors()
{
	changeTextFieldWhite(document.forms[0].loginId);	
	changeTextFieldWhite(document.forms[0].password);	
	changeTextFieldWhite(document.forms[0].confirmPassword);
	changeTextFieldWhite(document.forms[0].emailAddress);
	changeTextFieldWhite(document.forms[0].emailAddress2);
	changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
	changeTextFieldWhite(document.forms[0].securityQuestionAnswer2);
	changeTextFieldWhite(document.forms[0].alternateEmail);
	changeTextFieldWhite(document.forms[0].confirmAlternateEmail);
}

function doSubmit()
{
	clearFormFieldErrors();
    if (!submitted)
    {
        if (!cleanPwdField(document.forms[0].password))
        {
            changeTextFieldColor(document.forms[0].password);
            document.forms[0].password.focus();
            return;
        }
            
         if (!cleanSqAnswerField(document.forms[0].securityQuestionAnswer))
         {
            changeTextFieldColor(document.forms[0].securityQuestionAnswer);
            document.forms[0].securityQuestionAnswer.focus();
            return;
         }
        
        if (document.forms[0].password.value.toUpperCase() != document.forms[0].confirmPassword.value.toUpperCase()) {
            alert("'Confirm Password' should match Password.");
			changeTextFieldColor(document.forms[0].confirmPassword);
            document.forms[0].confirmPassword.focus();
        } else if (document.forms[0].emailAddress.value != document.forms[0].emailAddress2.value) {
            alert("'Confirm E-mail Address' should match 'E-mail Address'.");
			changeTextFieldColor(document.forms[0].emailAddress2);
            document.forms[0].emailAddress2.focus();
            return false;
        } else if (document.forms[0].securityQuestionAnswer.value.toUpperCase() != document.forms[0].securityQuestionAnswer2.value.toUpperCase()) {
            alert("'Confirm Your Answer' should match 'Your Answer'.");
			changeTextFieldColor(document.forms[0].securityQuestionAnswer2);
            document.forms[0].securityQuestionAnswer2.focus();
        } else if (document.forms[0].alternateEmail.value != document.forms[0].confirmAlternateEmail.value) {
            alert("'Confirm Alternate E-mail Address' should match 'Alternate E-mail Address'.");
			changeTextFieldColor(document.forms[0].confirmAlternateEmail);
            document.forms[0].confirmAlternateEmail.focus();
        } else if (document.forms[0].alternateEmail.value != '' && document.forms[0].alternateEmail.value == document.forms[0].emailAddress.value) {
            alert("'Alternate E-mail Address' cannot be the same as 'Email Address'. ");
			changeTextFieldColor(document.forms[0].alternateEmail);
            document.forms[0].alternateEmail.focus();
        } else {
            submitted = true;
            document.forms[0].submit();
        }
    }
}
</script>