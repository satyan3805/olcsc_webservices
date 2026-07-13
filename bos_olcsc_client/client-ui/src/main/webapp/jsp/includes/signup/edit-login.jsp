
<!-- created by Mark Kraemer on 10/02/2006 -->
<!-- provides required fields for creating/editing login credentials -->
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate"  class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>

<div>
<logic:messagesPresent message="false">
    <dl id="errors" class="errors"/>
    <html:messages id="msg" message="false">
        <dd><bean:write name="msg"/></dd>
    </html:messages>
</logic:messagesPresent>
<logic:messagesPresent message="true">
    <dl id="errors" class="errors"/>
    <html:messages id="msg" message="true" property="invalidAccountInfo">
        <dd>${msg}</dd>
    </html:messages>
</logic:messagesPresent>
</div>

<h5>* Required fields.</h5>
<fieldset>

	<dl>

		<dt class="first-dt-dd-pair"><label for="username">Username:</label></dt>
			<dd class="first-dt-dd-pair">
				*<input type="text" class="textfield" id="username" name="loginId" maxlength="80" value="${OnlineAccessForm.loginId}" onblur="javascript:removeUnwantedChar(this);" tabindex="1"/>
				<p class="help">username must be at least 6 alpha-numeric characters</p>
			</dd>

		<dt><label for="password">Password:</label></dt>
			<dd>
				*<input type="password" class="textfield" id="password" name="password" maxlength="30" value="${OnlineAccessForm.password}" onselect="javascript:unselect()" onblur="javascript:removeUnwantedChar(this);" tabindex="2"/>
				<p class="help">password must be at least 8 alpha-numeric characters</p>
			</dd>


		<dt><label for="confirm-password">Confirm Password:</label></dt>
			<dd>*<input type="password" class="textfield" id="confirm-password" name="confirmPassword" maxlength="30" value="${OnlineAccessForm.confirmPassword}" onselect="javascript:unselect()" onblur="javascript:removeUnwantedChar(this);" tabindex="3"/></dd>

		<dt><label for="e-mail-address">E-mail Address:</label></dt>
			<dd>*<input type="text" class="textfield" id="e-mail-address" name="emailAddress" maxlength="80" value="${OnlineAccessForm.emailAddress}" onblur="javascript:removeUnwantedChar(this);" tabindex="4"/>
                        <p class="help">This field is case sensitive</p>
                        </dd>

		<dt><label for="confirm-e-mail-address">Confirm E-mail Address:</label></dt>
			<dd>*<input type="text" class="textfield" id="confirm-e-mail-address" name="emailAddress2" maxlength="80" value="${OnlineAccessForm.emailAddress2}" onblur="javascript:removeUnwantedChar(this);" tabindex="5"/></dd>

	</dl>

</fieldset>

<h5>By providing us with an email address, you will enable email confirmation and communications related to your account activity</h5>

<fieldset>

	<dl>

		<dt class="first-dt-dd-pair"><label for="security-question">Security Question:</label></dt>
			<dd class="first-dt-dd-pair">
                                <select name="securityQuestionID" id="security-question" tabindex="6">
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
			<dd>*<input type="password" maxlength="20" class="textfield" id="security-answer" name="securityQuestionAnswer" value="${OnlineAccessForm.securityQuestionAnswer}" onselect="javascript:unselect()" onblur="javascript:removeUnwantedChar(this);" tabindex="7"/></dd>

		<dt><label for="confirm-security-answer">Confirm Your Answer<span class="accessibility">to the security question</span>:</label></dt>
			<dd>*<input type="password" maxlength="20" class="textfield" id="security-answer2" name="securityQuestionAnswer2" value="${OnlineAccessForm.securityQuestionAnswer2}" onselect="javascript:unselect()" onblur="javascript:removeUnwantedChar(this);" tabindex="8"/></dd>

		<dt><label for="alternate-e-mail-address">Alternate E-Mail Address:</label></dt>
			<dd>&nbsp;<input type="text" class="textfield" maxlength="80" id="alternate-e-mail-address" name="alternateEmail" value="${OnlineAccessForm.alternateEmail}" onblur="javascript:removeUnwantedChar(this);" tabindex="9"/>
                        <p class="help">This field is case sensitive</p>
                        </dd>

		<dt><label for="confirm-alternate-e-mail-address">Confirm Alternate E-Mail Address:</label></dt>
			<dd>&nbsp;<input type="text" class="textfield" maxlength="80" id="confirm-alternate-e-mail-address" name="confirmAlternateEmail" value="${OnlineAccessForm.confirmAlternateEmail}" onblur="javascript:removeUnwantedChar(this);" tabindex="10"/></dd>


	</dl>

</fieldset>

