<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<div id="content-container">
     <div id="content">
            <form id="sign-up" action="${pageContext.request.contextPath}/mailedTagActivationLogin.do" name="mailedTagActivationForm" method="post" autocomplete="off">           

                            

                                    <h1>Mailed EZ TAG(s) Activation</h1>
                                   <div class="section">                                
                                                                          
                                    <logic:messagesPresent message="false">
                                        <dl class="errors">
                                        <dt>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                        </dt>
                                        </dl>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true" property="saveFailed">
                                        <dl class="errors">
                                        <dt>
                                        <html:messages id="msg" message="true" property="saveFailed">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                        </dt>
                                        </dl>
                                    </logic:messagesPresent> 
                                    
                                    <logic:messagesPresent message="true" property="alerts">
                                        <dl class="alerts"/>
                                        <dt/>
                                        <html:messages id="msg" message="true" property="alerts">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <h5 id="notation">* All fields Required.</h5>
                                 <fieldset> 
                                      
                                        
                                               <dl width="100%">                                                  
                                                 <dt width="50%">
                                                 <label>TAG Request Activation Number:</label>
                                                 </dt>
                                                  <dd >                                             
                                                      
                                                    <span id="tag-activation-prefix">*</span>                                                     
                                                    <input type="text" class="textfield with-adjacent-form-field" id="tag-activation-number" name="tagActivationNbr" value="${mailedTagActivationForm.map.tagActivationNbr}" />
                                                    <p class="help">this activation number appears on your fulfillment receipt</p>
                                                  </dd> 
                                                                                       
                                               
                                              

	      <dt width="50%">
                            
                             <label>Driver License Number:</label>
              </dt>
	      <dd class="drivers-license-number-item">                
		<label for="drivers-license-state" class="accessibility">Driver License State</label>
		<span id="driver-lic-prefix">*</span><select id="drivers-license-state" name="driverLicenseState">
		  <c:forEach var="state" items="${stateDelegate.states}">
		    <option value="<c:out value="${state.stateCode}"/>"
		     <c:if test="${(state.defaultValueFlag == true)}">
			                                     selected
		                                         </c:if>
		      >
		      <c:out value="${state.stateCode}"/>
		    </option>
		  </c:forEach> 		
		</select>

		<label for="drivers-license-number" class="accessibility">Driver License Number</label>
		<input type="text" class="textfield with-adjacent-form-field" id="drivers-license-number" name="driverLicenseNumber" value="${mailedTagActivationForm.map.driverLicenseNumber}" onblur="javascript:cleanAlphaNumericField(this);" />		
                <p><span style=" font-family:'Arial'; color:#ff0000; font-size:13px; "><b>OR</b></span></p>
	        <p></p>
               <p><span style=" font-family:'Arial'; color:#000000; font-size:13px; "><b>TAX ID</b></span></p>
              <span id="driver-lic-prefix">*</span><input type="text" class="textfield" id="tax-id-number" name="taxId" value="${mailedTagActivationForm.map.taxId}" />
              <p class="help">Driver License or Tax Id is required to activate mailed TAG(s)</p></dd>                              
                                               
                                                  
                                    </dl>                
                                   

                            </fieldset>
                      </div> <!-- end of section -->         
            
                    <ul class="form-actions">
                            <c:if test="${returnAction}">
	                      <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  alt="Cancel" title="&rarr; Cancel" onclick="javascript:goToReturn();"/></li>
                            </c:if>
                            <c:if test="${(returnAction eq false) or (empty returnAction)}">
                               <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  alt="Cancel" title="&rarr; Cancel" onclick="javascript:goToHome();"/></li>
                            </c:if>
                            <li><input id="Submit" type="image" alt="Submit" src="${pageContext.request.contextPath}/meta/media/buttons/submit.gif" value="Submit" alt="Submit" title="&rarr; Submit" onclick="javascript:return chkData();"/></li>

                    </ul> <!-- end of form-actions -->
            
                    <input type="hidden" name="page" value="page1"/>
            </form>
       </div> <!-- end of content -->
    </div> <!-- end of content-container -->
        
        
<script type="text/javascript"> 
 var submitted = false;
function goToReturn()
 {
   document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";        
   document.forms[0].submit();
 }
 function goToHome()
 {
   document.mailedTagActivationForm.action = "${pageContext.request.contextPath}";        
   document.forms[0].submit();
 }
 
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}
function chkData(){
  var activationNbr = document.forms[0].tagActivationNbr.value;
  if((activationNbr.trim())==""){
   changeTextFieldColor(document.forms[0].tagActivationNbr);
   document.forms[0].tagActivationNbr.focus();
   alert("Please enter TAG Request Activation Number");
   return false;
  }else
    changeTextFieldWhite(document.forms[0].tagActivationNbr)
  
 if(validateLicNbrAndTaxId())
 { 
     if (!submitted) {     
        submitted = true;
        document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/mailedTagActivationLogin.do";
        <c:if test="${returnAction}">
           document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/mailedTagActivationLogin.do?statuschange=true";
        </c:if>                
        document.forms[0].submit();
        return true;
    }
 }
 return false;
}
function validateLicNbrAndTaxId() {
      var driversLicNbr = document.forms[0].driverLicenseNumber.value;
      var state = document.forms[0].driverLicenseState.value;
      var taxid = document.forms[0].taxId.value;

      if(driversLicNbr.trim()=="" && taxid.trim()=="")
      {
       alert("Enter either DL number or TAX ID."); 
       changeTextFieldColor(document.forms[0].driverLicenseNumber);
       changeTextFieldColor(document.forms[0].taxId);
       document.forms[0].driverLicenseNumber.focus();
       return false;
      }else{
         changeTextFieldWhite(document.forms[0].driverLicenseNumber);
         changeTextFieldWhite(document.forms[0].taxId);
      }
      if(driversLicNbr.trim() != ""){
      if (state == "TX") {
        if ( ! /^[0123][\d]{7}$/.test(driversLicNbr) ) {
            alert('Texas DL number is invalid. Please re-enter.');
			changeTextFieldColor(document.forms[0].driverLicenseNumber);	
            document.forms[0].driverLicenseNumber.focus();
            return false;
        }
        return true;
      }
      if (   state=="AL" || state=="AK" || state=="AR" || state=="CO"
          || state=="CT" || state=="DE" || state=="GA" || state=="IL"
          || state=="IN" || state=="LA" || state=="MS" || state=="NV"
          || state=="NM" || state=="NC" || state=="OR" || state=="PA"
          || state=="SC" || state=="SD" || state=="TN" || state=="UT"
          || state=="WY" ) {
            if ( driversLicNbr.trim() != "" && !validateNumeric(driversLicNbr)) {
              alert('DL number is invalid. Please re-enter.');
			  changeTextFieldColor(document.forms[0].driverLicenseNumber);
              document.forms[0].driverLicenseNumber.focus();
              return false;
        }       
        return true;
      }
      if (   state=="-" || state=="AS" || state=="AZ" || state=="AA"
          || state=="AE" || state=="AP" || state=="CA" || state=="FM"
          || state=="FL" || state=="GU" || state=="HI" || state=="ID"
          || state=="IA" || state=="KS" || state=="KY" || state=="ME"
          || state=="MH" || state=="MD" || state=="MA" || state=="MI"
          || state=="MN" || state=="MO" || state=="MT" || state=="NE"
          || state=="NH" || state=="NJ" || state=="NY" || state=="ND"
          || state=="MP" || state=="OH" || state=="OK" || state=="PW"
          || state=="PR" || state=="RI" || state=="RI" || state=="VT"
          || state=="VI" || state=="VA" || state=="DC" || state=="WA"
          || state=="WA" || state=="WV" || state=="WI" ) {
            if ( ! /[A-Za-z0-9]+/.test(driversLicNbr) ) {
              alert('DL number is invalid. Please re-enter.');
			  changeTextFieldColor(document.forms[0].driverLicenseNumber);
              document.forms[0].driverLicenseNumber.focus();
              return false;
        }       
        return true;
       } 
     }
     if(taxid.trim()!=""){
       return true;
     }
    }
</script>