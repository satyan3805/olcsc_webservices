<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="sqDelegate"  class="com.etcc.csc.delegate.SecurityQuestionDelegate" scope="page"/>
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeAuthorizedContacts.do" styleId="mainForm">

		<h1 id="account-information-edit-authorized-contacts">Account Information &ndash; update authorized contacts</h1>
		
                        <div class="section">

                            <logic:messagesPresent message="false">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="changeAuthorizedContactsError">
                                <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changeAuthorizedContactsError">
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

				<h5>List other people who are allowed to make changes to this account&hellip;</h5>

				<fieldset>
                                <!-- Authorized Contacts -->
                                <dl>
                                    <dt>Authorized Contacts:</dt>
                                    <dd>
                                        <ul>
                                            <li>
                                                <label for="authorized-contact-0-first-name">FirstName:</label><br/>
                                                <input class="textfield" id="authorized-contact-0-first-name" name="authorizedContact[0].firstName" value="${contactInfoForm.authorizedContacts[0].firstName}" type="text"/><br/>
                                                <label for="authorized-contact-0-last-name">Last Name:</label><br/>
                                                <input class="textfield" id="authorized-contact-0-last-name" name="authorizedContact[0].lastName" value="${contactInfoForm.authorizedContacts[0].lastName}" type="text"/><br/>
                                                <label for="authorized-contact-0-password">Password:</label><br/>
                                                <input class="textfield authorized-contact-password" id="authorized-contact-0-password" name="authorizedContact[0].password" value="${contactInfoForm.authorizedContacts[0].password}" type="text"/><br/>
                                                <p class="help authorized-contact-separator">password must be at least 8 alpha-numeric characters</p>
                                            </li>
                                            <c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="varStatus" begin="1">
                                                <li>
                                                    <label for="authorized-contact-${varStatus.index}-first-name">First Name:</label><br/>
                                                    <input class="textfield" id="authorized-contact-${varStatus.index}-first-name" name="authorizedContact[${varStatus.index}].firstName" value="${contact.firstName}" type="text"/><br/>
                                                    <label for="authorized-contact-${varStatus.index}-last-name">Last Name:</label><br/>
                                                    <input class="textfield" id="authorized-contact-${varStatus.index}-last-name" name="authorizedContact[${varStatus.index}].lastName" value="${contact.lastName}" type="text"/><br/>
                                                    <label for="authorized-contact-${varStatus.index}-password">Password:</label><br/>
                                                    <input class="textfield" id="authorized-contact-${varStatus.index}-password" name="authorizedContact[${varStatus.index}].password" value="${contact.password}" type="text"/><br/>
                                                    <p class="help">password must be at least 8 alpha-numeric characters</p>
                                                    <p class="help authorized-contact-separator">
                                                    <input type="image" class="image-based submit-button delete-this-contact" src="${pageContext.request.contextPath}/meta/media/buttons/delete-contact.gif" value="Delete contact ${varStatus.index}" alt="Delete contact" title=" Delete contact from account" onclick="javascript:deleteContact('${varStatus.index}'); return false;"/>
                                                    </p>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/add-another-contact.gif" value="Add another contact" alt="Add another contact for your account" title="&rarr; Add another contact" onclick="javascript:addContact(); return false;"/>
                                        <p class="help">List other people who are allowed to make changes to this account <br />(example: Lisa Smith, or Bill Collins)</p>
                                    </dd>
                                </dl>
				</fieldset>
		
			</div> <!-- end of section -->
		
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" alt="Cancel to not to save changes" title="&rarr; Cancel do not save changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" alt="Save Changes" title="&rarr; Save Changes" onclick="doSubmit(); return false;"/></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Authorized Contacts";

function addContact()
{
    document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContactChange.do";
    document.forms[0].submit();
}

function deleteContact(index)
{
    if (index == 0) 
        return;
    document.forms[0].action = "${pageContext.request.contextPath}/deleteAuthorizedContactChange.do?index="+index;
    document.forms[0].submit();
}

function doSubmit()
{
    if(validatePwFormat(document.forms[0].password.value)==false)
    {
        alert("The current password you entered is invalid.");
        document.forms[0].password.focus();
        return;
    }
    document.forms[0].submit();
}
</script>