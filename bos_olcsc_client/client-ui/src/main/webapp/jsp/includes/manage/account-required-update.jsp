<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate" class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>

<c:set var="requiredUpdateVal" value="${requestScope.requiredUpdate}"/>
<c:if test="${empty requiredUpdateVal}">
    <c:set var="requiredUpdateVal" value="${acctUpdateForm.requiredUpdate}"/>
</c:if>
<div id="content-container">

	<div id="content">

		<etcc-extended:autocompleteOffForm method="POST" action="/accountRequiredUpdate.do" styleId="mainForm">
                <input type="hidden" name="requiredUpdate" value="${requiredUpdateVal}"/>
		<h1 id="account-information-edit-username">Update Account Information</h1>

                        <div class="section">
                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="accountUpdateFailed">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="accountUpdateFailed">
                                            <dd>${msg}</dd>
                                        </html:messages>
                            </logic:messagesPresent>
                        </div>
                        <em><bean:message key="acctUpdateForm.text${requiredUpdateVal}" /></em>
                        <br/>
                        <br/>
                        <div class="section">
				<fieldset <c:if test="${requiredUpdateVal==2 or requiredUpdateVal==4 or requiredUpdateVal==6}">style="display:none;"</c:if>>
                                    <dl>
                                           <dt class="first-dt-dd-pair"><label for="current-password">Current Password:*</label></dt>
                                                    <dd class="first-dt-dd-pair"><input type="password" class="textfield" id="password" name="password" value="${acctUpdateForm.password}"  onselect="javascript:unselect()"/></dd>

                                            <dt><label for="new-password">New Password:*</label></dt>
                                                    <dd>
                                                            <input type="password" class="textfield" id="newPassword" name="newPassword" value="${acctUpdateForm.newPassword}"  onselect="javascript:unselect()"/>
                                                            <p class="help">password must be at least 8 alpha-numeric characters</p>
                                                    </dd>

                                            <dt><label for="confirm-new-password">Confirm New Password:*</label></dt>
                                                    <dd><input type="password" class="textfield" id="confirmNewPassword" name="confirmNewPassword" value="${acctUpdateForm.confirmNewPassword}"  onselect="javascript:unselect()"/></dd>
                                    </dl>
                                </fieldset>

                                <fieldset <c:if test="${requiredUpdateVal==1 or requiredUpdateVal==4 or requiredUpdateVal==5}">style="display:none;"</c:if>>
                                    <dl>
                                            <dt><label for="e-mail-address">E-mail Address:*</label></dt>
                                                    <dd><input type="text" class="textfield" id="e-mail-address" name="emailAddress" maxlength="80" value="${acctUpdateForm.emailAddress}" onblur="javascript:removeUnwantedChar(this);"/>
                                                        <p class="help">This field is case sensitive</p>
                                                    </dd>

                                            <dt><label for="confirm-e-mail-address">Confirm E-mail Address:*</label></dt>
                                                    <dd><input type="text" class="textfield" id="confirm-e-mail-address" name="emailAddress2" maxlength="80" value="${acctUpdateForm.emailAddress2}" onblur="javascript:removeUnwantedChar(this);"/></dd>
                                    </dl>
                                </fieldset>

                                <fieldset <c:if test="${requiredUpdateVal==1 or requiredUpdateVal==2 or requiredUpdateVal==3}">style="display:none;"</c:if>>
                                    <dl>
                                            <dt class="first-dt-dd-pair"><label for="security-question">Security Question:*</label></dt>
                                                    <dd class="first-dt-dd-pair">

                                                            <select name="securityQuestionID" id="security-question">
                                                                <c:forEach var="sq" items="${sqDelegate.securityQuestions}">
                                                                    <option value="<c:out value="${sq.securityQuestionID}"/>"
<c:if test="${acctUpdateForm.securityQuestionID == sq.securityQuestionID}">selected</c:if> ><c:out value="${sq.securityQuestion}"/>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                    </dd>

                                            <dt><label for="security-answer">Your Answer <span class="accessibility">to the security question</span>:*</label></dt>
                                                    <dd><input type="password" maxlength="20" class="textfield" id="security-answer" name="securityQuestionAnswer" value="${acctUpdateForm.securityQuestionAnswer}"  onselect="javascript:unselect()"/></dd>

                                            <dt><label for="confirm-security-answer">Confirm Your Answer <span class="accessibility">to the security question</span>:*</label></dt>
                                                    <dd><input type="password" maxlength="20" class="textfield" id="security-answer2" name="securityQuestionAnswer2" value="${acctUpdateForm.securityQuestionAnswer2}"  onselect="javascript:unselect()"/></dd>
                                    </dl>

				</fieldset>

			</div> <!-- end of section -->

			<ul class="form-actions">
				<li><img class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit(); return false;" /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do" />

</div> <!-- end of content-container -->
<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Required Update";

//var oldEvent = (window.onload) ? window.onload : function () {};
	//window.onload = function () {
		//oldEvent();
		//var foo = document.getElementsByTagName('a');
		//<c:if test="${requiredUpdateVal==1 or requiredUpdateVal==2 or requiredUpdateVal==3}">
		   //for(var i = 0; i < foo.length; i++) {
		      // foo[i].setAttribute('href', '#');
		  // }
		  // </c:if>
		//}

	
function doSubmit()
{
	submitDetails = true;
	warning = false;
    if(validatePwFormat(document.forms[0].password.value)==false)
    {
        alert("The current password you entered is invalid.");
        document.forms[0].password.focus();
        return;
    }

    if(validatePwFormat(document.forms[0].newPassword.value)==false)
    {
        alert("The new password you entered is invalid.");
        document.forms[0].newPassword.focus();
        return;
    }

    if (!cleanSqAnswerField(document.forms[0].securityQuestionAnswer))
     {
        document.forms[0].securityQuestionAnswer.focus();
        return;
     }

    if (document.forms[0].newPassword.value.toUpperCase() != document.forms[0].confirmNewPassword.value.toUpperCase()) {
        alert("'Confirm New Password' should match 'New Password'.");
        document.forms[0].confirmNewPassword.focus();
    }
    if (document.forms[0].newPassword.value!='' && document.forms[0].newPassword.value == document.forms[0].password.value) {
        alert("'New Password' must be different than 'Current Password'.");
        document.forms[0].confirmNewPassword.focus();
    }
    else if (document.forms[0].emailAddress.value != document.forms[0].emailAddress2.value){
        alert("'Confirm Email Address' should match 'Email Address'");
        document.forms[0].emailAddress2.focus();
    }
    else if (document.forms[0].securityQuestionAnswer.value.toUpperCase() != document.forms[0].securityQuestionAnswer2.value.toUpperCase()) {
        alert("'Confirm Security Question Answer' should match 'Security Question Answer'");
        document.forms[0].securityQuestionAnswer2.focus();
    }
    else {
        showLoading();
        document.forms[0].submit();
    }
}
</script>
