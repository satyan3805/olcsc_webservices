<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!-- By Idea, for WSR2, Sept, 2008, REM - new page -->

<%@ include file="/jsp/common/Taglibs.jsp" %>


  <c:if test="${(requestScope.fromConfirmation) or (contactInfoForm.fromConfirmation)}">
    <c:set var="fromConfirmationVal" value="true"/>
  </c:if>

  <div id="content-container">
    <div id="content">

      <!-- Upon submit go back to whichever page you came from. -->
      <form method="POST" name="contactInfoForm" action="javascript:history.back();" autocomplete="off">
	<input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>
	<h1 id="authorized-contacts">Authorized Contacts : Add, change, or delete</h1>
	<p class="help">Authorized Contacts are individuals you are allowing to access and manage your account.</p>

	<div class="section">
	  <div>
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
	  </div>
	  
	  <fieldset>
	    <dl>
	      <!-- This is the FDD driven implementation of the authorized contact feature  -->
	      <!--  By Idea, for WSR2, Sept, 2008, REM   -->
	      <dt>Authorized Contacts:</dt>
	      <dd>
		<ul>
		  <li>
		    <label for="authorized-contact-0-first-name">First Name:</label>
		    <input class="textfield" id="authorized-contact-0-first-name" name="authorizedContact[0].firstName" value="${contactInfoForm.authorizedContacts[0].firstName}" type="text"/>
		    <label for="authorized-contact-0-last-name">Last Name:</label>
		    <input class="textfield" id="authorized-contact-0-last-name" name="authorizedContact[0].lastName" value="${contactInfoForm.authorizedContacts[0].lastName}" type="text"/>
		    <label for="authorized-contact-0-password">Password:</label>
		    <input class="textfield authorized-contact-password" id="authorized-contact-0-password" name="authorizedContact[0].password" value="${contactInfoForm.authorizedContacts[0].password}" type="text"/>
		    <p class="help authorized-contact-separator">password must be at least 8 alpha-numeric characters</p>
		  </li>
		  <c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="varStatus" begin="1">
		    <li>
		      <label for="authorized-contact-${varStatus.index}-first-name">First Name:</label>
		      <input class="textfield" id="authorized-contact-${varStatus.index}-first-name" name="authorizedContact[${varStatus.index}].firstName" value="${contact.firstName}" type="text"/>
		      <label for="authorized-contact-${varStatus.index}-last-name">Last Name:</label>
		      <input class="textfield" id="authorized-contact-${varStatus.index}-last-name" name="authorizedContact[${varStatus.index}].lastName" value="${contact.lastName}" type="text"/>
		      <label for="authorized-contact-${varStatus.index}-password">Password:</label>
		      <input class="textfield" id="authorized-contact-${varStatus.index}-password" name="authorizedContact[${varStatus.index}].password" value="${contact.password}" type="text"/>
		      <p class="help">password must be at least 8 alpha-numeric characters</p>
		      <p class="help authorized-contact-separator">
			<input type="image" class="image-based submit-button delete-this-contact" src="${pageContext.request.contextPath}/meta/media/buttons/delete-contact.gif" value="Delete contact ${varStatus.index}" onclick="javascript:deleteContact('${varStatus.index}'); return false;"/>
		      </p>
		    </li>
		  </c:forEach>
		</ul>
		<input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/add-another-contact.gif" value="Add another contact" onclick="javascript:addContact(); return false;"/>
		<p class="help">List other people who are allowed to make changes to this account <br />(example: Lisa Smith, or Bill Collins)</p>
	      </dd>
	    </dl>
	    <!-- End of FDD authorized contact implementation section  -->

	  </fieldset>
	</div> <!-- end of section -->

	<ul class="form-actions">
	  <c:if test="${fromConfirmationVal}">
	    <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  onclick="javascript:goToConfirmation();"/></li>
	  </c:if>
	  <li><img src=
	      <c:choose>
		<c:when test="${fromConfirmationVal}">
		  "${pageContext.request.contextPath}/meta/media/buttons/continue.gif" 
		</c:when>
		<c:otherwise>
		  "${pageContext.request.contextPath}/meta/media/buttons/continue.gif" 
		</c:otherwise>
	      </c:choose>
	      value="Add Vehicles" onclick="javascript:return checkData();"/></li>

	</ul> <!-- end of form-actions -->

      </form>

    </div> <!-- end of content -->

    <p class="progress-bar" id="step-2-of-4">
      New EZ TAG Account
      <em>Step 2 of 4</em>
    </p>

  </div> <!-- end of content-container -->

<!--
    function displayAuthorizedAgents() {
    authorizedAgentList = getAuthorizedAgents();
    nbrAuthorizedAgents = AuthorizedAgentsList().size;

      if (nbrAuthorizedAgents != 0) {
        Create a window of size nbrAuthorizedAgents;
        field titles are: First Name (20), Last Name(25), Password(15);
        populate the fields of the window;
        if nbrAuthorizedAgents > 3) { show scrollbar; }
        display populated window;
      }
    }
-->

  <script type="text/javascript">
    var submitted = false;

    function addContact() {
      document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do";
      document.forms[0].submit();
    }

    function addAuthorizedContact() {
      document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do";
      document.forms[0].submit();
    }

    function deleteContact(index) {
      if (index == 0) return;
      document.forms[0].action = "${pageContext.request.contextPath}/deleteAuthorizedContact.do?index="+index;
      document.forms[0].submit();
    }

    function goToConfirmation() {
      if (!submitted) {
        submitted = true;
        document.contactInfoForm.action ="${pageContext.request.contextPath}/signupConfirmationDisplay.do";
        document.contactInfoForm.submit();
      }
    }

  </script>
