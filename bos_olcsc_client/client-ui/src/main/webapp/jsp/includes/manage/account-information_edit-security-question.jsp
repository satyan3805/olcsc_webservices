<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate"  class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>

<body onload="loadingBody()">
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeSecQn.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; update security question</h1>
		
                        <div class="section">

                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="changeSecQnError">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changeSecQnError">
                                            <dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                        </div>

			<div class="wide-section">
				<fieldset>
					<dl>
						<dt class="first-dt-dd-pair"><label for="current-password">Current Password:</label></dt>
                                                        <dd class="first-dt-dd-pair">*<input type="password" class="textfield" id="password" name="password" value="${OnlineAccessForm.password}" onselect="javascript:unselect()" /></dd>
					</dl>
				</fieldset>

				<h5>If you forget your password, this information will allow you to log in&hellip;</h5>

				<fieldset>

					<dl>

						<dt class="first-dt-dd-pair"><label for="security-question">Security Question:</label></dt>
							<dd class="first-dt-dd-pair">
					                    <select name="securityQuestionID" id="securityQuestionID" onchange="confirmClear()" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /> >
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

						<dt><label for="security-answer">Your Answer <span class="accessibility">to the security question</span>:</label></dt>
							<dd>*<input type="password" class="textfield" id="securityQuestionAnswer" name="securityQuestionAnswer" value="${OnlineAccessForm.securityQuestionAnswer}" onselect="javascript:unselect()" 
                                                                     <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">
                                                                     readonly
                                                                     </c:if> 
                                                                    /> </dd>

						<dt><label for="confirm-security-answer">Confirm your Answer <span class="accessibility">to the security question</span>:</label></dt>
							<dd>*<input type="password" class="textfield" id="securityQuestionAnswer2" name="securityQuestionAnswer2" value="${OnlineAccessForm.securityQuestionAnswer2}" onselect="javascript:unselect()" 
                                                        <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">readonly</c:if> /></dd>
							
					</dl>

				</fieldset>
		
			</div> <!-- end of section -->
		
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="doSubmit(); return false;"/></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Security Question";

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
  document.forms[0].securityQuestionID.selectedIndex=${OnlineAccessForm.securityQuestionID} - 1;
  return;
}
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
var securityQuestionAnswer = '<html:errors property="securityQuestionAnswer" />';
var securityQuestionAnswer2 ='<html:errors property="securityQuestionAnswer2" />';
	 if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
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


 var error = '${changeSecQnMessage}';
 if (error != "")
 {
	changeTextFieldColor(document.forms[0].password);
	changeTextFieldColor(document.forms[0].securityQuestionAnswer);
	changeTextFieldColor(document.forms[0].securityQuestionAnswer2);	
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
    
    if (!cleanSqAnswerField(document.forms[0].securityQuestionAnswer))
     {
        document.forms[0].securityQuestionAnswer.focus();
		changeTextFieldColor(document.forms[0].securityQuestionAnswer);
        return;
     }
    
    if (document.forms[0].securityQuestionAnswer.value.toUpperCase() != document.forms[0].securityQuestionAnswer2.value.toUpperCase()) {
        alert("'Confirm Your Answer' should match 'Your Answer'.");
		changeTextFieldColor(document.forms[0].securityQuestionAnswer2);
        document.forms[0].securityQuestionAnswer.focus();
    } else {
        document.forms[0].submit();
    }
}
</script>