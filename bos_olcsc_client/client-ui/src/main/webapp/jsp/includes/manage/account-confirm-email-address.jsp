<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!-- By Idea, for WSR2, Sept, 2008, REM - new page -->

<%@ include file="/jsp/common/Taglibs.jsp" %>


  

  <div id="content-container">
    <div id="content">

      <!-- Upon submit go back to whichever page you came from. -->
      <!--<form method="POST" name="contactInfoForm" action="javascript:history.back();" autocomplete="on">-->
	  <form method="POST" name="emailValidForm" action="${pageContext.request.contextPath}/confirmPrimaryEmail.do" autocomplete="on">
	
	
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

	  <c:set     var='status'   value='${emailValidForm.validationStatus}' /> 
	<c:if test="${status == 1}">
	<p class="help">Please confirm your primary e-mail address <br>	
	  <c:out value="${emailValidForm.primaryEmailAddress}"/> <br>	
    and login to your account.<br>
	 </c:if>
	<c:if test="${status == 0}">
	  <bean:message key="emailValidForm.status0" />
	</c:if>
	<c:if test="${status == 2}">
	   <jsp:forward page="/login.do"/>
	</c:if>
	<c:if test="${status == 3}">
	  <bean:message key="emailValidForm.status3" />
	</c:if>
	<input type="hidden" name="acctId" value="${emailValidForm.accountId}" scope="request"/>
	
	<c:if test="${status == 1}">
	<img src="${pageContext.request.contextPath}/meta/media/buttons/button-confirm.gif"  onclick="javascript:goToConfirmation('<c:out value='${emailValidForm.accountId}'/>');"/>
	 </c:if>
	

	  
	   
	     
	      
	      
		 
		 	  
		  
			
		    
		 
	  	</div> <!-- end of section -->

      </form>

    </div> <!-- end of content -->

   
  </div> <!-- end of content-container -->



  <script type="text/javascript">
    var submitted = false;

    function goToConfirmation(acctId) {
      document.forms[0].action = "${pageContext.request.contextPath}/confirmPrimaryEmail.do?url=confirm&acctId="+acctId;	
	 
      document.forms[0].submit();
    }

    

  </script>
