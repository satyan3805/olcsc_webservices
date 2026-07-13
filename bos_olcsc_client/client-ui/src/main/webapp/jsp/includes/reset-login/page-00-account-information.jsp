<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@page import="com.etcc.csc.util.SessionUtil"%>
<%SessionUtil.resetCurrentTabMenuId(session);%>
<body onload="loadingBody()">
<div id="content-container">
	<div id="content">
	<!-- Reset password functionality through Email added by Nelisa kiboti Aug 13 2014-->
    	<etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/retrieveEmailAddress.do">
    	<h1 id="identity-confirmed">Forgot your username or password?</h1>
		<div class="section">
		<c:if test="${not empty onlineAccessForm.violationMessage}">
		<dl>
		<dd>${onlineAccessForm.violationMessage}</dd>
		</dl>
		</c:if>
				<logic:messagesPresent message="false">
            		<dl class="errors"/>
                		<html:messages id="msga" message="false">
                			<dd>${msga}</dd>
                		</html:messages>
                </logic:messagesPresent>
                <logic:messagesPresent message="true" property="invalidAccountInfo">
                	<dl class="errors"/>
                		<html:messages id="msgb" message="true" property="invalidAccountInfo">
                			<dd id="info">${msgb}</dd>
                		</html:messages>
               </logic:messagesPresent>
		</div> 

		<div class="section">
		
						<div><label style="margin-left:15px; font-size: 15px;" >Select an option below to reset your login information:</label></div><br>
						<div>
							<input type="radio"  style="margin: 0 10px 0 20px; outline: none;" name="resetPassword" id="emailReset" <c:if test="${showContactLink ne true}">
                                                            checked="checked"
                                                        </c:if> value="Using your email address."><label style="font-size: 15px;">Using your email address</label><br />
						</div><br>
						<div style="margin: 0 10px 0 45px;">
							<label>Please enter the email address associated with your EZ TAG account.</label><br>
							<input type="hidden" id="hiddenEmailAddressId" value="<bean:write name="OnlineAccessForm" property="emailAddress" />"/> 
							<html:text property="emailAddress"  styleClass="${showContactLink? 'textfield2':'textfield'}" styleId="emaildAddressId" onkeydown="checkTheRadioAgain()" onblur="javascript:removeUnwantedChar(this);"/><br>
							<label>We will send you a link that will allow you to reset your login information.</label>	<br>
						</div><br>
						
						<div><label style="margin-left:15px; font-weight:bold; font-size: 15px;" >OR</label></div><br>
                                        
						<div><input style="margin: 0 10px 0 20px;" type="radio" name="resetPassword"  id="accountReset" ><label style="font-size: 15px;">Using your EZ TAG account information</label><br />
						</div><br>
						<div style="margin: 0 10px 0 45px;">
							<label >Reset your login information by providing your EZ TAG account number or tag number.</label><br><br>
						</div>
		

		</div> <!-- end of section -->

		<ul class="form-actions">

			<li><img src="${pageContext.request.contextPath}/meta/media/buttons/submit.gif" alt="Submit" onclick="return doSubmit();"/></li>

		</ul> 
		<div class="section">
			<c:if test="${showContactLink eq true}">
				<label>If you are unable to reset your login information using the options above, <a href="https://www.hctra.org/about_contact">contact us</a>  for assistance.</label>
			</c:if>
		</div>
        </etcc-extended:autocompleteOffForm>
         

	</div>
	 
		<p style=" position: absolute; font-size: 1.9em; right: 15px; color: rgb(124,100,160);top:-3.5em;" id="step-1-of-3;">
		Reset login information
	</p>

</div> 
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="Reset Login and Password";

function loadingBody(){
	getErrorfields();
}
function changeTextFieldColor(field){
	field.style.border="3px solid red";
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
	var emaildAddressId ='<html:errors property="emailAddress"/>';
	var emaildAdd =document.getElementById('hiddenEmailAddressId').value;
	var div = document.getElementById('info');
	//var errormsg= "Sorry, we can't find this email address on an account.  Please try again, or select the option to reset your login using your EZ TAG account information.-20391";
	//if(div.innerHTML != null){
		//document.getElementById('emaildAddressId').value= "";
	//}else if(div.innerHTML == errormsg){
		//document.getElementById('emaildAddressId').value= emaildAdd;
	//document.getElementById("test1").setAttribute("name","file-upload_" + $count);
	//}
		  if (checkForErrors(emaildAddressId) == true){
			changeTextFieldColor(document.forms[0].emaildAddressId);
		 }else{
			 changeTextFieldWhite(document.forms[0].emaildAddressId);
		 }
		  
	}

function checkTheRadioAgain(){
	document.getElementById("emailReset").setAttribute("checked","checked");
}
	function doSubmit() {
    	if(document.getElementById('emailReset').checked) {
    			var email= document.getElementById('emaildAddressId').value;
    		    	 document.forms[0].submit();
    		    	 return true;
    		    }else if(document.getElementById('accountReset').checked) {
    		window.location.href = '${pageContext.request.contextPath}/home/resetPassword1.do';
    		}else{
        		alert("Please select option to reset password");
    		}
    }
    	
</script>
