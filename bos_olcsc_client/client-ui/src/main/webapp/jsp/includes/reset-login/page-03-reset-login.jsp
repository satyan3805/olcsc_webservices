<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate"  class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>

	<div id="content-container">

		<div id="content">

<!--			<form id="sign-up" action="?" method="post">        -->
                    <etcc-extended:autocompleteOffForm method="POST" action="/resetLogin.do" styleId="mainForm">

				<div class="section">

					<h1 id="identity-confirmed">Identity confirmed</h1>

					<p>For your protection, please provide a new password.</p>
                                
                                        <logic:messagesPresent message="false">
                                            <dl class="errors"/>
                                                <html:messages id="msg" message="false">
                                                    <dd><bean:write name="msg"/></dd>
                                                </html:messages>
                                        </logic:messagesPresent>
                                        <logic:messagesPresent message="true">
                                            <dl class="errors"/>
                                            <html:messages id="msg" message="true" property="onlineAccessSetupError">
                                                <dd>${msg}</dd>
                                            </html:messages>
                                        </logic:messagesPresent>

                                </div>

                                <!--    <div class="section">   -->

					<fieldset>

						<dl>

							<dt class="first-dt-dd-pair"><label for="username">Username:</label></dt>
								<dd class="first-dt-dd-pair">
                                                                        <!--
									<input type="text" class="textfield" id="username" name="username" value="${passwordRetrievalDTO.userID}" />
									<p class="help">you may change your username<br />username must be at least 6 alpha-numeric characters</p>
                                                                        -->
                                                                        <dd class="first-dt-dd-pair">${passwordRetrievalDTO.userID}</dd>
								</dd>

							<dt><label for="password">New Password:</label></dt>
								<dd>
									*<input type="password" class="textfield" maxlength="30" id="password" name="password" value="${OnlineAccessForm.password}" onselect="javascript:unselect()"/>
									<p class="help">Password must be at least 8 alpha-numeric characters.</p>
								</dd>


							<dt><label for="confirm-password">Confirm New Password:</label></dt>
								<dd>*<input type="password" class="textfield" maxlength="30" id="confirm-password" name="confirmPassword" value="${OnlineAccessForm.confirmPassword}" onselect="javascript:unselect()"/></dd>

					</fieldset>

					<h5>If you forget your password again, this information will allow you to log in:</h5>

					<fieldset>

						<dl>

						<dt class="first-dt-dd-pair"><label for="security-question">New Security Question:</label></dt>
							<dd class="first-dt-dd-pair">
					                    <select name="securityQuestionID" id="securityQuestionID" onchange="confirmClear()">
                                                                <c:forEach var="sq" items="${sqDelegate.securityQuestions}">
                                                                    <option value="<c:out value="${sq.securityQuestionID}"/>"
                                                                        <c:if test="${passwordRetrievalDTO.securityQuestionID == sq.securityQuestionID}">
                                                                            selected
                                                                        </c:if>
                                                                    >
                                                                    <c:out value="${sq.securityQuestion}"/>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
							</dd>

                                                        <dt><label for="security-answer">Your Answer <span class="accessibility">to the security question</span>:</label></dt>
							<dd>*<input type="password" maxlength="20" class="textfield" id="security-answer" name="securityQuestionAnswer" value="${passwordRetrievalDTO.securityQuestionAnswer}" onselect="javascript:unselect()"/></dd>
                                                        
                                                        <dt><label for="security-answer2">Confirm Your Answer <span class="accessibility">to the security question</span>:</label></dt>
                                                        <dd>*<input type="password" maxlength="20" class="textfield" id="security-answer2" name="securityQuestionAnswer2" value="${passwordRetrievalDTO.securityQuestionAnswer}" onselect="javascript:unselect()"/></dd>

					</fieldset>

				<!--    </div>  --> <!-- end of section -->

				<ul class="form-actions">

					<li><img class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="doSubmit(); return false" /></li>

				</ul> <!-- end of form-actions -->

			</etcc-extended:autocompleteOffForm>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-3-of-3">
			Reset login information
			<em>Step 3 of 3</em>
		</p>

	</div> <!-- end of content-container -->
        
        
<script type="text/javascript">
loadingBody();
function loadingBody(){
	getErrorfields();
}

function clearfields(){

document.forms[0].securityQuestionAnswer.value ="";
document.forms[0].securityQuestionAnswer2.value ="";

}

function confirmClear(){
var answer = confirm("Do you want to change the security question? Click 'OK' to confirm and provide new answer or Click 'Cancel' to cancel the action.");
if(answer){
 clearfields();
 }
else
{
  document.forms[0].securityQuestionID.selectedIndex=${passwordRetrievalDTO.securityQuestionID} - 1;
  return;
}
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
var confirmPassword='<html:errors property="confirmPassword"/>';
var securityQuestionAnswer= '<html:errors property="securityQuestionAnswer"/>';
var securityQuestionAnswer2 = '<html:errors property="securityQuestionAnswer2"/>';

	 
	 if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
	 }

	if (checkForErrors(confirmPassword) == true){
		changeTextFieldColor(document.forms[0].confirmPassword);
	 }
	 else{
		 changeTextFieldWhite(document.forms[0].confirmPassword);
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
	
	 

}

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function doSubmit()
{
    if (!cleanPwdField(document.forms[0].password))
    {
        changeTextFieldColor(document.forms[0].password);
        document.forms[0].password.focus();
        return;
    }else
        changeTextFieldWhite(document.forms[0].password);
        
    if (!cleanSqAnswerField(document.forms[0].securityQuestionAnswer))
    {
        changeTextFieldColor(document.forms[0].securityQuestionAnswer);
        document.forms[0].securityQuestionAnswer.focus();
        return;
    }else
        changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
    
    if (document.forms[0].password.value.toUpperCase() != document.forms[0].confirmPassword.value.toUpperCase()) {
        alert("'Confirm Password' should match Password.");
        changeTextFieldWhite(document.forms[0].password);
        changeTextFieldColor(document.forms[0].confirmPassword);
        document.forms[0].confirmPassword.focus();
    } else if (document.forms[0].securityQuestionAnswer.value.toUpperCase() != document.forms[0].securityQuestionAnswer2.value.toUpperCase()) {
        alert("'Confirm Your Answer' should match 'Your Answer'.");
         changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
         changeTextFieldColor(document.forms[0].securityQuestionAnswer2);
        document.forms[0].securityQuestionAnswer2.focus();
    } else {
        document.forms[0].submit();
    }
}
</script>