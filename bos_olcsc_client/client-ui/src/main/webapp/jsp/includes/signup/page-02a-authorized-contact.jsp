<%@ include file="/jsp/common/Taglibs.jsp" %>

<div id="content-container">  
  
  <c:if test="${(requestScope.fromConfirmation) or (contactInfoForm.fromConfirmation)}">
    <c:set var="fromConfirmationVal" value="true"/>
  </c:if>

  <c:if test="${(sessionScope.whichPage != '')}">
    <!--	 <c:out value="${whichPage}" />  -->
  </c:if>
  
  <c:choose>
    <c:when test="${(sessionScope.whichPage eq 'update-authorized-contact-page')}" >
       <div id="content">
    </c:when>
  <c:otherwise>
   <div id="primary-and-secondary-content">
</c:otherwise>
</c:choose>
      <!-- Upon submit go back to whichever page you came from. -->
      <!--<form method="POST" name="contactInfoForm" action="javascript:history.back();" autocomplete="on">-->
<form method="POST" name="contactInfoForm" action="${pageContext.request.contextPath}/authorizedContactsInfo.do" autocomplete="on">
	<input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>
        <input type="hidden" name="fromConfirmationContact" value="${fromConfirmationContact}"/>
        <input type="hidden" name="byEmail" value="${contactInfoForm.byEmail}"/>
        <input type="hidden" name="byMail" value="${contactInfoForm.byMail}"/>
        <c:choose>
		<c:when test="${preq}">
		  <h1 id="authorized-contacts"> Account Information - update authorized Contacts </h1> 
		</c:when>
		<c:otherwise>
		 <h1 id="authorized-contacts"> Authorized Contact(s): </h1> 
		</c:otherwise>
        </c:choose>	
	
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
<h5>* Required fields.</h5>
<c:if test="${preq}">
<table width="100%">
<tr width="100%"> 
<td align="left" width="50%" style="color:#8A0808"><b><label for="account_pwd" syle="color:red">Account Password</label></b></td>
<td align="left" width="40%"></td>
</tr>
<input type="hidden" name="preq" value="${preq}"/>
<tr width="100%">	
		<td align="left" width="50%" style="color:#8A0808"><label for="password">*Please enter your account password:</label></td>			
		<td align="left" width="40%">*<input type="password" class="textfield" id="password" name="password" maxlength="30" value="${pwd}" onselect="javascript:unselect()" onblur="javascript:removeUnwantedChar(this);" onfocus="javascript:getfocus(this);"/></td>				
</tr>
</table>
</c:if>
          
	  
	  <p class="help">Authorized Contacts are individuals you are allowing to access and manage your account.</p>
          
	  
	      <!-- This is the FDD driven implementation of the authorized contact feature  -->
	      <!--  By Idea, for WSR2, Sept, 2008, REM   -->
	      
	      <% int i=0;%>

		 <table width="1500px">	
		  <!-- Additional Authorized User Code  Begins-->
                  
		  <c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="varStatus" begin="0" end="${contactInfoForm.authContactsLimit}">
		  <tr width="800px">
		      <td align="left" width="80px"><label for="authorized-contact-${varStatus.index}-first-name">First Name:</label></td>
		      <td align="left" width="80px">*<input class="textfield" id="authorized-contact-${varStatus.index}-first-name" name="authorizedContact[${varStatus.index}].firstName" maxlength="40" onblur="javascript:changeToUpperCaseAlpha(this,'First Name');"  onfocus="javascript:getfocus(this);" value="${contact.firstName}" type="text" /></td>
		      <td align="left" width="80px"><label for="authorized-contact-${varStatus.index}-last-name">Last Name:</label></td>
		      <td align="left" width="80px">*<input class="textfield" id="authorized-contact-${varStatus.index}-last-name" name="authorizedContact[${varStatus.index}].lastName" maxlength="20" onblur="javascript:changeToUpperCaseAlpha(this,'Last Name');" value="${contact.lastName}" type="text"/></td>
		      <td align="left" width="80px"><label for="authorized-contact-${varStatus.index}-password">Access Code:</label></td>
		      <td align="left" width="80px">*<input class="textfield" id="authorized-contact-${varStatus.index}-password" name="authorizedContact[${varStatus.index}].password" onblur="javascript:changeToUpperCaseAlphaNumeric(this,'Access Code');" value="${contact.password}" type="text"   title="Access Code is between 6 to 12 characters" /></td>
			 
		     <td align="left" width="">  
			<input type="image" class="image-based submit-button delete-this-contact" src="${pageContext.request.contextPath}/meta/media/buttons/delete-contact-small.gif" value="Delete contact ${varStatus.index}" alt="Delete contact" title=" Delete contact from account" onclick="javascript:deleteContact('${varStatus.index}'); return false;" /></td></tr> 
                      <% i++;%>  
		  </c:forEach>
		  </table>                  
		  <html:hidden property="whichPage" value="${whichPage}" />
		<!-- Additional Authorized User Code Ends -->
                <% if(i<20){%>
		<input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/add-another-contact.gif" value="Add another contact" alt="Add another contact for your account" title="&rarr; Add another contact" onclick="javascript:addContact();return false;"/>
		 <%}%>
		
	      
	    <!-- End of FDD authorized contact implementation section  -->

	  	</div> <!-- end of section -->

	<ul class="form-actions">        
	         
		      <li><div id="cancel-button" ><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  alt="Cancel to not to save changes" title="&rarr; Cancel do not save changes" onclick="javascript:cancelButton();"/></div></li>	 
		  
          
	  <li><img id="save-changes" src=
	      <c:choose>
		<c:when test="${fromConfirmationVal}">
		  "${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" 
		</c:when>
		<c:otherwise>
		 "${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" 
		</c:otherwise>
	      </c:choose>
	      value="Save Changes" alt="Save Changes" title="&rarr; Save Changes" onclick="javascript:return goToConfirmation();return false;"/></li>

	</ul> <!-- end of form-actions -->

      </form>

    </div> <!-- end of content -->
<c:choose>
<c:when test="${(sessionScope.whichPage ne 'update-authorized-contact-page')}" >
    <p class="progress-bar" id="step-2-of-6">
      New EZ TAG Account
      <em>Step 2 of 6</em>
    </p>
</c:when>
<c:otherwise>
<jsp:include page="/accountInfo.do"/>
</c:otherwise>
</c:choose>
  


  </div> <!-- end of content-container -->

<script type="text/javascript">
    var submitted = false;
    var focusValue = true;
    var spaceCheck = false;
     hideCancelButton();  
function getfocus(obj){
//alert("getfocus" + obj);
if(focusValue){
  if(trimSpace(obj.value).length > 0){
      focusChange();}
   focusValue = false;
   }
} 

function hideCancelButton(){
    var whichPage = document.contactInfoForm.whichPage.value;
     // alert("whichPage "+whichPage);
       if(whichPage == null || whichPage == "contact-info-page" || whichPage == "incomplete-page"  ){
          document.getElementById('cancel-button').style.display='none';
       }
    }    
    
function cancelButton()
	{

		var whichPage = document.contactInfoForm.whichPage.value;
		//alert("whichPage" + fromWhere + "  " + whichPage)
		
		if(whichPage == null || whichPage == "contact-info-page" || whichPage == "incomplete-page"  ){			
                       
			 document.contactInfoForm.action ="${pageContext.request.contextPath}/authorizedContactsInfo.do?cancel=true";
	                 document.contactInfoForm.submit();
			
		}else{
			 if(whichPage != null && whichPage == "confirm-page")
			{
				 document.contactInfoForm.action ="${pageContext.request.contextPath}/authorizedContactsInfo.do?cancel=true";
	                         document.contactInfoForm.submit();
				
			}else{
				if(whichPage != null && whichPage == "update-authorized-contact-page")
				{
					 document.contactInfoForm.action ="${pageContext.request.contextPath}/authorizedContactsInfo.do?cancel=true";
	                                 document.contactInfoForm.submit();
					
				}
			}
		}
	}
function addContact() {
	  
      document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do?method=add";
      document.forms[0].submit();
    }

function addAuthorizedContact() {
	  
      document.forms[0].action = "${pageContext.request.contextPath}/addAuthorizedContact.do";
      document.forms[0].submit();
    }

function deleteContact(index) {
      //if (index == 0) return;
	  
      document.forms[0].action = "${pageContext.request.contextPath}/deleteAuthorizedContact.do?index="+index;
      document.forms[0].submit();
    }
	
function isDuplicate(fname , lname)
	{
	var fldTxtName="";
	var cnt = 0;
	for (var i=0;i< document.forms["contactInfoForm"].elements.length;i=i+1) {
		
			fldTxtName = document.forms["contactInfoForm"].elements[i].name;		  
			if(fldTxtName.indexOf("authorizedContact[") != -1)
			{
				var myTextFieldFName = document.forms["contactInfoForm"].elements[i].value;
				var myTextFieldLName = document.forms["contactInfoForm"].elements[i+1].value;
				var myTextFieldPName = document.forms["contactInfoForm"].elements[i+2].value;
                                
                                myTextFieldFName = trimSpace(myTextFieldFName);
                                myTextFieldLName = trimSpace( myTextFieldLName);
                                myTextFieldPName = trimSpace(myTextFieldPName);
                                
				if(myTextFieldFName == fname && myTextFieldLName ==lname )
				{
					//return true;
					cnt++;
				}
				i=i+2;
			}// if fldTxt
	}//for
	if(cnt > 1){
		return true;
	}else
	return false;
    }

function checkDuplicateList()
	{
		var fldTxtName="";
		for (var i=0;i< document.forms["contactInfoForm"].elements.length;i=i+1) {
			
				fldTxtName = document.forms["contactInfoForm"].elements[i].name;		  
				if(fldTxtName.indexOf("authorizedContact[") != -1)
				{
					var myTextFieldFName = document.forms["contactInfoForm"].elements[i].value;
					var myTextFieldLName = document.forms["contactInfoForm"].elements[i+1].value;
					var myTextFieldPName = document.forms["contactInfoForm"].elements[i+2].value;
                                        
                                        myTextFieldFName = trimSpace(myTextFieldFName);
                                        myTextFieldLName = trimSpace( myTextFieldLName);
                                        myTextFieldPName = trimSpace(myTextFieldPName);
                                        
					if(isDuplicate(myTextFieldFName,myTextFieldLName) == true) return true;
					i=i+2;

				}
		}
		return false;
      }
	
 
function trim(s)
{
	var l=0; var r=s.length -1;
	while(l < s.length && s[l] == ' ')
	{	l++; }
	while(r > l && s[r] == ' ')
	{	r-=1;	}
	return s.substring(l, r+1);
}

function trimSpace(stringToTrim) {
      if(stringToTrim !=null)
	return stringToTrim.replace(/^\s+|\s+$/g,"");
      else
        return stringToTrim;
}

function checkForBlanks(){	
	var fldTxtName="";
	var isFieldsEmpty = true;
	//alert("checkBlanks " + document.forms["contactInfoForm"].elements)		
	for (var i=0;i< document.forms["contactInfoForm"].elements.length;i=i+1) {
		
			fldTxtName = document.forms["contactInfoForm"].elements[i].name;
		  // alert("Outside fldTxtName " + fldTxtName + " "  + fldTxtName.indexOf("authorizedContact["));
			if(fldTxtName.indexOf("authorizedContact[") != -1)
			{
				//alert("Inside " +  fldTxtName + document.getElementByName(fldTxtName));
				var myTextFieldFName = document.forms["contactInfoForm"].elements[i].value;
				var myTextFieldLName = document.forms["contactInfoForm"].elements[i+1].value;
				var myTextFieldPName = document.forms["contactInfoForm"].elements[i+2].value;
				//alert("FName " + myTextFieldFName + "LName" + myTextFieldLName + "PName" + myTextFieldPName)
				//i=i+2;
                                myTextFieldFName = trimSpace(myTextFieldFName);
                                myTextFieldLName = trimSpace( myTextFieldLName);
                                myTextFieldPName = trimSpace(myTextFieldPName);
                               
                                if(myTextFieldFName =="" || myTextFieldLName=="" || myTextFieldPName=="" || myTextFieldFName.length <= 0 || myTextFieldLName <=0 || myTextFieldPName <=0){
                                   document.forms["contactInfoForm"].elements[i].focus();
                                   alert("Please enter values for all the fields");                       
			           isFieldsEmpty = false;
			           return isFieldsEmpty;
                                }                               
			
			if((myTextFieldFName == null && myTextFieldFName == "") && ((myTextFieldLName == null || myTextFieldLName == "") && (myTextFieldPName == null || myTextFieldPName == ""))){
			 document.forms["contactInfoForm"].elements[i].focus();
                        alert("Please enter values for all the fields");                       
			isFieldsEmpty = false;
			return isFieldsEmpty;
			}
			//This is if first name is not null
			
			if((myTextFieldFName != null && myTextFieldFName != "") && ((myTextFieldLName == null || myTextFieldLName == "") || (myTextFieldPName == null || myTextFieldPName == ""))){
			 document.forms["contactInfoForm"].elements[i].focus();
                        alert("Please enter values for all the fields");                       
			isFieldsEmpty = false;
			return isFieldsEmpty;
			}
			if((myTextFieldLName != null && myTextFieldLName != "") && ((myTextFieldFName == null || myTextFieldFName == "") || (myTextFieldPName == null || myTextFieldPName == ""))){
			//document.getElementById(document.forms[0].elements[i-1].name).focus();
			 document.forms["contactInfoForm"].elements[i].focus();
                        alert("Please enter values for all the fields");
			isFieldsEmpty = false;
			return isFieldsEmpty;
			}
			if((myTextFieldPName != null && myTextFieldPName != "") && ((myTextFieldFName == null || myTextFieldFName == "") || (myTextFieldLName == null || myTextFieldLName == ""))){
                        //document.getElementById(document.forms[0].elements[i].name).focus();
			document.forms["contactInfoForm"].elements[i+1].focus();
			alert("Please enter values for all the fields");
			isFieldsEmpty = false;
			return isFieldsEmpty;
			}
		
			
			if(myTextFieldPName != null && myTextFieldPName != "" && (myTextFieldPName.length < 6 || myTextFieldPName.length > 12)){
                         //document.getElementById(document.forms[0].elements[i].name).focus();
			 document.forms["contactInfoForm"].elements[i+2].focus();
			 alert("Access Code needs to be between 6 to 12 characters");
			 isFieldsEmpty = false;
			 return isFieldsEmpty;
			}
			i=i+2;
			}
            }
	
      return isFieldsEmpty;
    }
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function changeToUpperCaseAlphaNumeric(ctl,fld){
    if(alphanumeric(ctl.value)){
       if(spaceCheck){
          changeTextFieldColor(ctl);
          alert("Please enter a value in "+fld+" or delete any preceding space(s)");
          ctl.focus();
          spaceCheck = false;
          return false;
       }else{
      ctl.value = ctl.value.toUpperCase();
      changeTextFieldWhite(ctl);
      return true;
       }
      }else
      {
       if(spaceCheck){
          changeTextFieldColor(ctl);
          alert("Please enter a value in "+fld+" or delete any preceding space(s)");
          ctl.focus();
          spaceCheck = false;
          return false;
       }else{
      changeTextFieldColor(ctl);
      alert("Please enter only valid Characters and Numbers");
      ctl.focus();
      return false;
      }
      }
}

function alphanumeric(alphane)
{
	var numaric = alphane;
	for(var j=0; j < numaric.length; j++)
		{
		  var alphaa = numaric.charAt(j);
		  var hh = alphaa.charCodeAt(0);
                  if(j==0 && (hh == 32 || hh == 9))
                   {
                     spaceCheck = true;
                   }
		  if((hh > 47 && hh < 58) || (hh > 64 && hh<91) || (hh > 96 && hh<123))
		  {
		  }
		else{
			 return false;
		  }
		}
 return true;
}
function changeToUpperCaseAlpha(ctl,fld){
    var tmp = alphaCheck(ctl.value);
    //alert(tmp);
    //alert(spaceCheck);
    if(tmp){
       if(spaceCheck){
          changeTextFieldColor(ctl);
          alert("Please enter a value in "+fld+" or delete any preceding space(s)");
          ctl.focus();
          spaceCheck = false;
          return false;
       }else {
      ctl.value = ctl.value.toUpperCase(); 
      changeTextFieldWhite(ctl);
      return true;
        }
      }else
      { 
      if(spaceCheck){
          changeTextFieldColor(ctl);
          alert("Please enter a value in "+fld+" or delete any preceding space(s)");
          ctl.focus();
          spaceCheck = false;
          return false;
       }else{
      changeTextFieldColor(ctl);
      alert("Please enter only valid Characters");
      ctl.focus();
      return false;
        }
      }
}

function alphaCheck(alpha)
{
	var numaric = alpha;
	for(var j=0; j < numaric.length; j++)
		{   
		  var alphaa = numaric.charAt(j);
		  var hh = alphaa.charCodeAt(0);
                  if(j==0 && (hh == 32 || hh == 9))
                   {
                     spaceCheck = true;
                   }
		  if((hh > 64 && hh<91) || (hh > 96 && hh<123) || hh==32)
		  {
		  }
		else{
			 return false;
		  }
		}
 return true;
}
function goToConfirmation() {
      
			    
		var locURL = location.href;
		var  attachURL= locURL.substring(0,locURL.indexOf("/eztagstore")) + "/eztagstore/signupAccountInfo.do";
                <c:if test="${preq}">
                    var pwdVal= document.getElementById('password').value;
                    if(pwdVal !=null && (pwdVal.length <= 0)){
                         changeTextFieldColor(document.getElementById('password'));
                         alert("Please provide a valid Password");                         
                         document.getElementById('password').focus();
                         return false;
                      }else { changeTextFieldWhite(document.getElementById('password')); }
                </c:if>
		
		if(checkForBlanks() == false){	
			
                var fldTxtName="";	        
	        for (var i = 0; i < document.forms["contactInfoForm"].elements.length; i=i+1) {
		
			fldTxtName = document.forms["contactInfoForm"].elements[i].name;
			if(fldTxtName.indexOf("authorizedContact[") != -1)
			{
				//alert("I am inside")
				var myTextFieldFName = document.forms["contactInfoForm"].elements[i];
				var myTextFieldLName = document.forms["contactInfoForm"].elements[i+1];
				var myTextFieldPName = document.forms["contactInfoForm"].elements[i+2]; 
				i=i+2;			
			        //This is if first name is not null
				if((myTextFieldFName != null && trimSpace(myTextFieldFName.value) != "") ){
                                        changeTextFieldWhite(myTextFieldFName);
				  }else
                                        changeTextFieldColor(myTextFieldFName);
				if((myTextFieldLName != null && trimSpace(myTextFieldLName.value) != "") ){
					changeTextFieldWhite(myTextFieldLName);
				  }else
                                        changeTextFieldColor(myTextFieldLName);
				if((myTextFieldPName != null && trimSpace(myTextFieldPName.value) != "") ){
                                    if((trimSpace(myTextFieldPName.value).length < 6 || trimSpace(myTextFieldPName.value).length > 12)){
                                                    changeTextFieldColor(myTextFieldPName);
					}else
                                                    changeTextFieldWhite(myTextFieldPName);
			          }else
                                     changeTextFieldColor(myTextFieldPName);			
			}// if(fldTxtName.indexOf("authorizedContact[") != -1)
		     }//for end
                    return false;
			} // end if(checkForBlanks() == false)
		//}
		//alert("Now passed the checkblanks")
                if(checkDuplicateList() == true){
			alert("Note: There are duplicate authorized contacts. Please edit your information.")
			return false;
		}
		var fromWhere = document.contactInfoForm.fromConfirmation.value;
		var whichPage = document.contactInfoForm.whichPage.value;
		//alert("whichPage" + fromWhere + "  " + whichPage)
		
		if(whichPage == null || whichPage == "contact-info-page" || whichPage == "incomplete-page"  ){                        
			document.contactInfoForm.action ="${pageContext.request.contextPath}/authorizedContactsInfo.do?addContacts=true&pgVal=true";
			document.contactInfoForm.submit();
			
		}else{
			 if(whichPage != null && whichPage == "confirm-page")
			{
				document.contactInfoForm.action ="${pageContext.request.contextPath}/authorizedContactsInfo.do?addContacts=true&pgVal=true";
				document.contactInfoForm.submit();				
			}else{
				if(whichPage != null && whichPage == "update-authorized-contact-page")
				{
					document.contactInfoForm.action ="${pageContext.request.contextPath}/updateAuthContacts.do";
					document.contactInfoForm.submit();
				}
			}
		}
		return true;
    }
    
function focusChange(){
 //alert("In focusChange");
     <c:choose>
        <c:when test="${pwdAlert}">
		  var pwdVal= document.getElementById('password');                    
                  changeTextFieldColor(document.getElementById('password'));
                  document.getElementById('password').focus();
          </c:when>
          <c:otherwise>
		for (var i=0;i< document.forms["contactInfoForm"].elements.length;i=i+1) {
		
			fldTxtName = document.forms["contactInfoForm"].elements[i].name;		  
			if(fldTxtName.indexOf("authorizedContact[") != -1)
			{	
				var myTextFieldFName = document.forms["contactInfoForm"].elements[i].value;
				var myTextFieldLName = document.forms["contactInfoForm"].elements[i+1].value;
				var myTextFieldPName = document.forms["contactInfoForm"].elements[i+2].value;
				
                                myTextFieldFName = trimSpace(myTextFieldFName);
                                myTextFieldLName = trimSpace( myTextFieldLName);
                                myTextFieldPName = trimSpace(myTextFieldPName);
                               
                                if(myTextFieldFName =="" && myTextFieldLName =="" && myTextFieldPName==""){
                                   document.forms["contactInfoForm"].elements[i].focus();
                                   break;
                                }
                          }
                       }
                       
          </c:otherwise>
      </c:choose>
  } 
  </script>

