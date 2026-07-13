<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<%--
/*Modified to fix DID 3272 - isDate() is declared in both calendarPopup.js and validation.js
But we want the one in validation.js to be declared last, so that the isDate
function in it will be called. Also removed the calendarpopup.js from the included vehicleinfo_include.jsp 
and added it here
 --%>
<script type="text/javascript" src="<html:rewrite page='/js/calendarPopup.js'/>" > </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dateUtil.js"></script>
<script type="text/javascript">

var cal1 = new CalendarPopup(); 
</script>
<style type="text/css">	
.greyInputBox {background: #D3D3D3; }
</style>
<tr>
	<td class="left"></td>
	<td id="data" class="content">
		<etcc-extended:autocompleteOffForm action="/AddVehicleSaveAction.do" method="POST">
			<html:hidden property="dmvAccepted" />
			<html:hidden property="dmvVehicleId" />
			<html:hidden property="tagTransferFlag"/>
			

			<br>
			<logic:messagesPresent message="false">
				<div id="error">
					<div class="error-img"></div>
					<ul>
						<html:messages id="msg" message="false">
							<li><bean:write name="msg" /></li>
						</html:messages>
					</ul>
					<br />
				</div>
			</logic:messagesPresent>
			<logic:messagesPresent message="true" property="saveFailed">
				<div id="error">
					<div class="error-img"></div>
					<ul>
						<html:messages id="msg" message="true" property="saveFailed">
							<li><bean:write name="msg" /></li>
						</html:messages>
					</ul>
					<br />
				</div>
			</logic:messagesPresent>
			<c:if test="${(accountLogin.minTag == 1) || (accountLogin.minTag == 0 && accountLogin.maxTag != 0)}">				
				<br class="clear">
				
					<p>
						<fmt:message key="getTolltag.vehicleInfo.note2" />
						<br>
						<br>
					</p>
					<table bgcolor="#adcfe3">
						<tr>
							<td>&nbsp;</td>
						<tr />
						<tr>
							<td>&nbsp;&nbsp;<html:radio styleId="tagExists"
								onclick="enableActivationFields();" property="tagExists" value="Y"/>
									&nbsp;<fmt:message key="getTolltag.vehicleInfo.tagsExists" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						<tr />
						<tr>
							<td>&nbsp;&nbsp;<html:radio styleId="tagExists"
								onclick="enableRequestFields();" property="tagExists" value="N"/>
								&nbsp;<fmt:message key="getTolltag.vehicleInfo.tagRequest" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						<tr />
					</table>
				 
				 
				<!--<c:if test="${not empty sessionScope.POS_ID}">
					<input type="hidden" id="tagExists" name="tagExists" value="N" />
				</c:if>-->
			</c:if>
			
		 	<div id="tagActivationDiv" style="display:none">
				<table cellspacing="0" class="form" border="1">	
					<tr class="odd-a">
						<th colspan="2" nowrap="nowrap" style="border-bottom:0px"><div id="displayTagNumberHeader">
								<fmt:message key="tagRequestForm.tollTagNumber" /> *
							</div>
						</th>
						<th style="width:100px">&nbsp;</th>
						<th colspan="2" width="100%" ><div id="displayTagNumberConfirmHeader">
								<fmt:message key="tagRequestForm.tollTagNumberRetype" /> *
							</div>
						</th>
					</tr>
					<tr class="odd-a">
						<%-- Add tag authorities of new requirement:dropdown DNT/DFW/TEX --%>
						<td>
							<div id="displayTagNumberInput" style="display:block; font:bold;">
								<html:select property="agencyId" styleClass="text" tabindex="1" style="width:60px"
									styleId="agencyId">
									<html:options collection="tagAuthorities"
										property="tagIdentifier" labelProperty="tagIdentifier" />
								</html:select>&nbsp;.
							</div></td>				
							<td>
								<div id="displayTagNumberInput2" style="display: block;">
									<input type="text" id="tollTag" tabindex="2" name="tollTag"
										class="text" size="6" maxlength="10"
										autocomplete="off" onselect="javascript:unselect();"
										onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag4(this);" />
								</div>
							</td>
							<td>&nbsp;</td>
							<%-- Add tag authorities of new requirement:dropdown DNT/DFW/TEX --%>
							 <td>
					                       <div id="displayTagNumberConfirmInput" style=" display: block;font: bold;">
					                        <html:select property="agencyIdConfirm" styleClass="text" tabindex="3" styleId="agencyIdConfirm">
					                          <html:options collection="tagAuthorities" property="tagIdentifier" labelProperty="tagIdentifier"/>
					                        </html:select>&nbsp;.
					                       </div>
					                    </td>
							<td style="width:100%">
								<div id="displayTagNumberConfirmInput2" style="display: block;">
									<input type="text" id="tollTagConfirm" tabindex="4"
										name="tollTagConfirm" class="text"
										size="6" maxlength="10" autocomplete="off"
										onselect="javascript:unselect();"
										onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag4(this);"
										onBeforePaste="window.clipboardData.clearData();" />
									</div>
								</td>
					</tr>
				</table>
			</div>
			
			<div id="vehicleCreationDiv">
				<jsp:include page="../getTollTag/vehicleInfo_include.jsp"></jsp:include>
			</div>
			<table>
				<tr>
					<td><div id="saveButtonDiv">
							<input type="button" class="button"
								value='<fmt:message key="button.save" />' id="addButton"
								onclick="return doSubmit(${sessionScope.limit});" />
						</div></td>
				</tr>
			</table>

			<br />
			<br />
			<br />
			<br />
		</etcc-extended:autocompleteOffForm> 
	<br>
	<br>
	<br>
	<br></td>
	<td class="right"></td>
</tr>

<tr id="content-bottom">
	<td class="left"></td>
	<td class="content"></td>
	<td class="right"></td>
</tr>

<tr>
	<td class="bottomleft"></td>
	<td class="bottomcenter"></td>
	<td class="bottomright"></td>
</tr>



<script type="text/javascript">

var planCode = '<c:out value="${accountLogin.acctPlan}" />';
var tagRequired = <c:out value="${accountLogin.minTag == 1}" />;
var tagProhibited = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag == 0}" />;
var tagOptional = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag < 0}" />;

var minVehicle =<c:out value="${accountLogin.minVehicle}" />;
var maxVehicle =<c:out value="${accountLogin.maxVehicle}" />;

var validationPassed = "${requestScope.validationPassed}";
var validationMessageFromAPI = "${requestScope.validationMessageFromAPI}";
var numberOfVehicles = "${requestScope.numberOfVehicles}";
var transactionChargesPresent = "${requestScope.transactionChargesPresent}";
var transactionAmount = "${requestScope.transactionAmount}";

var dmvInfoPresent = "${requestScope.dmvInfoPresent}";	
var vehMake = "${requestScope.vehMake}";
var vehModel = "${requestScope.vehModel}";
var vehYear = "${requestScope.vehYear}";
var vehVin = "${requestScope.vehVin}";
var vehDmvId = "${requestScope.vehDmvId}";
var tagExistsOnVehicle = "${requestScope.tagExistsOnVehicle}";
var numberOfTags = "${requestScope.numberOfTags}";
var tagTransactionChargesPresent = "${requestScope.tagTransactionChargesPresent}";
var tagTransactionAmount = "${requestScope.tagTransactionAmount}";
var showMessages = "${requestScope.showMessages}";
	
var selectionType = "${requestScope.selectionType}";
var cnt = 0;

YAHOO.util.Event.onDOMReady(doOnLoadAddVehicle);

function doOnLoadAddVehicle(){
	radioObj = document.TollTagForm.tagExists;
	if(radioObj){
		var radioLength = radioObj.length;
		if(document.TollTagForm.tagExists) {
			radioObj.checked = (radioObj.value == selectionType);
		}
		
		for(var i = 0; i < radioLength; i++) {
			radioObj[i].checked = false;
			if(radioObj[i].value == selectionType) {
				radioObj[i].checked = true;
				document.getElementById('saveButtonDiv').style.display='block';
			}
		}
	}
	
	 enableTagFields();
     enableLeaseDates();
	 enablePlateExpirationDate( );
	
	if(validationPassed == "Y" ){
		confirmOverlapAndAdd();	    	
    } else if(validationPassed == "N" ) {
    	if(validationMessageFromAPI != ""){
    		alert(validationMessageFromAPI);
    	} else if((numberOfVehicles != "0" && numberOfVehicles != "1")){
	    		var msg4 = "<fmt:message key='tagInfo.licPlate.error.exist4'><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>";
	    		alert(msg4);
    	} else if(tagExistsOnVehicle == "N" &&(numberOfTags != "0" && numberOfTags != "1")){
    		var msg4 = "<fmt:message key='tagInfo.tag.error.exist4'><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>";
    		alert(msg4);
    	}
    }
	
	
}

function confirmOverlapAndAdd(){
	document.TollTagForm.tagTransferFlag.value="N";
	if(numberOfVehicles == "1"){  
		if((!tagProhibited || planCode == 'A') && tagExistsOnVehicle == "Y"){	//M6378 - transfer tags for TollTag acct
			var vehicleTagOverlap = "<fmt:message key='tagInfo.licPlateTag.error.exist1'/>";
			var confirmAddVehTagOk = "<fmt:message key='tagInfo.licPlateTag.error.exist2'/>";
			var confirmAddVehTagOR = "<fmt:message key='tagInfo.licPlateTag.error.exist3'/>";
			//var confirmAddVehTagCancel = "<fmt:message key='tagInfo.licPlateTag.error.exist4'/>";
			var confirmAddVehTagCancel = "<fmt:message key='tagInfo.licPlateTag.error.exist6'/>";
			var confirmAddVehOK = "<fmt:message key='tagInfo.licPlateTag.error.exist7'/>";
			var confirmAddVehCancel = "<fmt:message key='tagInfo.licPlateTag.error.exist8'/>";
			
			var acceptedTagAndVehicle = confirm(vehicleTagOverlap + "\n\n"  + confirmAddVehTagOk +   "\n"  + confirmAddVehTagOR + "\n"  + confirmAddVehTagCancel);
    		if(acceptedTagAndVehicle){
				var vehTagTransactionMessage = "<fmt:message key='tagInfo.licPlateTag.error.exist5'><fmt:param>${requestScope.transactionAmount}</fmt:param></fmt:message>"
    			if(transactionChargesPresent == "Y"){
    				var acceptedCharges = confirm(vehTagTransactionMessage);
    				if(acceptedCharges){
		    			document.TollTagForm.tagTransferFlag.value="Y";
		    			confirmDmvAndAdd();
    				}
    			} else {
	    			document.TollTagForm.tagTransferFlag.value="Y";
    				confirmDmvAndAdd();
    			}
    		} else {
    			var acceptedVehicle = confirm(confirmAddVehOK +   "\n\n"  + confirmAddVehTagOR + "\n\n"  + confirmAddVehCancel);
    			if(acceptedVehicle){
    				document.TollTagForm.tagTransferFlag.value="N";
    				return confirmTagOverlapAndAdd();
    			}else{
    				return false;
    			}
    		}
			
		} else {
			var vehicleOverlap = "<fmt:message key='tagInfo.licPlate.error.exist1'/>";
    		var confirmAdd = "<fmt:message key='tagInfo.licPlate.error.exist2'/>";
    		var transactionMessage = "<fmt:message key='tagInfo.licPlate.error.exist5'><fmt:param>${requestScope.transactionAmount}</fmt:param></fmt:message>"
        	
			if(transactionChargesPresent == "Y"){
	    		var acceptedTransactionAmt = confirm(vehicleOverlap + "\n\n"  + transactionMessage + "\n\n"  + confirmAdd);
	    		if(acceptedTransactionAmt){
	    			confirmTagOverlapAndAdd();    		    			
	    		}
			}else {		
				var acceptedOverlap = confirm(vehicleOverlap + "\n\n"  + confirmAdd);
	    		if(acceptedOverlap){
	    			confirmTagOverlapAndAdd();    		    			
	    		}
			}
		}
	} else if(numberOfVehicles == "0"){
		confirmTagOverlapAndAdd();    	
	}
}

function confirmTagOverlapAndAdd(){
	if(numberOfTags == "0"){
		confirmDmvAndAdd();
	} else if(numberOfTags == "1"){
		var tagOverlap = "<fmt:message key='tagInfo.tag.error.exist1'/>";
		var confirmAddTag = "<fmt:message key='tagInfo.licPlate.error.exist2'/>";
		var tagTransactionMessage = "<fmt:message key='tagInfo.tag.error.exist5'><fmt:param>${requestScope.tagTransactionAmount}</fmt:param></fmt:message>"
    	
			if(tagTransactionChargesPresent == "Y"){
	    		var acceptedTagTransactionAmt = confirm(tagOverlap + "\n\n"  + tagTransactionMessage + "\n\n"  + confirmAddTag);
	    		if(acceptedTagTransactionAmt){
	    			document.TollTagForm.tagTransferFlag.value="T";
	    			confirmDmvAndAdd();    		    			
	    		}
			} else {		
				var acceptedTagOverlap = confirm(tagOverlap + "\n\n"  + confirmAddTag);
	    		if(acceptedTagOverlap){
	    			document.TollTagForm.tagTransferFlag.value = "T";
	    			confirmDmvAndAdd();    		    			
	    		}
			}
	} else {
		alert("<fmt:message key='tagInfo.tag.error.exist4'><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>");
	}
}

function confirmDmvAndAdd(){
	if(dmvInfoPresent == "Y"){
		var dmvMsg1 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo1"/>';
		var dmvMsg2 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo2"/>';
		var dmvAccepted = confirm(dmvMsg1 + "\n\n"  + vehMake  + " " +  vehModel + " " + vehYear + " " + vehVin + "\n\n"  + dmvMsg2);
		if(dmvAccepted){
			updateDmvInfo();
			addVehicleWithoutValidation();
		} else {
			document.TollTagForm.dmvAccepted.value="false";
			addVehicleWithoutValidation();
		}
	}  else {
		addVehicleWithoutValidation();
	}
}
 
function addVehicleWithoutValidation(){
	if(showMessages == "Y"){
		if(document.TollTagForm.dmvAccepted.value == "true"){
			enableDmvFields();
		}
		var sourceAccountVehicleId = "${requestScope.sourceAccountVehicleId}";
		var sourceAccountTagId = "${requestScope.sourceAccountTagId}";
		document.TollTagForm.action="AddVehicleSaveAction.do?myAction=confirmAddVehicle&sourceAccountVehicleId=" + sourceAccountVehicleId
				+"&sourceAccountTagId=" + sourceAccountTagId;		
		document.TollTagForm.submit();
	}
}

function enableDmvFields(){
	document.TollTagForm.vehicleMake.disabled = false;
	document.TollTagForm.vehicleMake.readonly = true;
	document.TollTagForm.vehicleModel.disabled = false;
	document.TollTagForm.vehicleModel.readonly = true;
	document.TollTagForm.vehicleYear.disabled = false;
	document.TollTagForm.vehicleYear.readonly = true;
	document.TollTagForm.vinNumber.disabled = false;
	document.TollTagForm.vinNumber.readonly = true;
}


function doSubmit(limit){	
	var  isValid = checkData(limit);
	if(!isValid){
		return false;
	}else{
		var sourceAccountVehicleId = "${requestScope.sourceAccountVehicleId}";
		var sourceAccountTagId = "${requestScope.sourceAccountTagId}";
		document.TollTagForm.action="AddVehicleSaveAction.do?sourceAccountVehicleId=" + sourceAccountVehicleId
				+"&sourceAccountTagId=" + sourceAccountTagId;
		document.TollTagForm.submit();
	}	
	return false;
}


function enableTagFields(){
	 enableVehicleFields();
	  if (document.TollTagForm.tagExists && document.TollTagForm.tagExists[0].checked) {
		  document.getElementById('tagActivationDiv').style.display ='block';
		   var tagId =  "";
		  <logic:notEmpty name="TollTagForm" property="tollTag">
		  	tagId = '<bean:write name="TollTagForm" property="tollTag"/>';
		  </logic:notEmpty>
		  document.getElementById("tollTag").value = tagId;
		  document.getElementById("tollTagConfirm").value = tagId;
	  } else {
		  document.getElementById('tagActivationDiv').style.display ='none';
	  }
}
function enableVehicleFields(){
	     document.getElementById("licensePlate").disabled = false;
    document.getElementById("licensePlateState").disabled = false;
    document.getElementById("licPlateType").disabled = false;
    document.getElementById("vehicleColor").disabled = false;
    document.getElementById("vehicleClassCode").disabled = false;
      

 	 document.getElementById("vehicleYear").disabled = false;
    document.getElementById("vehicleMake").disabled = false;
    document.getElementById("vehicleModel").disabled = false;
    document.getElementById("vinNumber").disabled = false;
}
function enableRequestFields() {
    try {
    	document.getElementById('tagActivationDiv').style.display ='none';
      	
        document.getElementById("agencyId").disabled = false;
        document.getElementById("tollTag").disabled = false;
        document.getElementById("tollTagConfirm").disabled = false;
        document.getElementById("agencyIdConfirm").disabled = false;
        
 	    document.getElementById("licensePlate").disabled = false;
        document.getElementById("licensePlateState").disabled = false;
        document.getElementById("licPlateType").disabled = false;
        document.getElementById("vehicleColor").disabled = false;
        document.getElementById("vehicleClassCode").disabled = false;
          
       /* if(document.TollTagForm.dmvAccepted.value == "true") {
            document.getElementById("vehicleYear").disabled = true;
            document.getElementById("vehicleMake").disabled = true;
            document.getElementById("vehicleModel").disabled = true;
            document.getElementById("vinNumber").disabled = true;
        } else {*/
        	document.getElementById("vehicleYear").disabled = false;
	        document.getElementById("vehicleMake").disabled = false;
	        document.getElementById("vehicleModel").disabled = false;
	        document.getElementById("vinNumber").disabled = false;
        /*}*/
     }catch(e){
    	 
     }
    
   
 }


function enableActivationFields()
{
    try
    {
        document.getElementById('tagActivationDiv').style.display ='block';

        document.getElementById("licensePlate").disabled = false;
        document.getElementById("licensePlateState").disabled = false;
        document.getElementById("licPlateType").disabled = false;
        document.getElementById("vehicleColor").disabled = false;
        document.getElementById("vehicleClassCode").disabled = false; 

       /* if(document.TollTagForm.dmvAccepted.value == "true") {
            document.getElementById("vehicleYear").disabled = true;
            document.getElementById("vehicleMake").disabled = true;
            document.getElementById("vehicleModel").disabled = true;
            document.getElementById("vinNumber").disabled = true;
        } else {*/
        	document.getElementById("vehicleYear").disabled = false;
	            document.getElementById("vehicleMake").disabled = false;
	            document.getElementById("vehicleModel").disabled = false;
	            document.getElementById("vinNumber").disabled = false;
        /* } */
    }catch(e){
    }


}   


function updateDmvInfo() {
	document.TollTagForm.vehicleMake.disabled = false;
	document.TollTagForm.vehicleMake.value = vehMake;
	document.TollTagForm.vehicleMake.disabled = true;
	
	document.TollTagForm.vehicleModel.disabled = false;
	document.TollTagForm.vehicleModel.value = vehModel;
	document.TollTagForm.vehicleModel.disabled = true;
	
	document.TollTagForm.vehicleYear.disabled = false;
	document.TollTagForm.vehicleYear.value = vehYear;
	document.TollTagForm.vehicleYear.disabled = true;
	
	document.TollTagForm.vinNumber.disabled = false;
	document.TollTagForm.vinNumber.value = vehVin;
	document.TollTagForm.vinNumber.disabled = true;

	//document.TollTagForm.vehicleColor.disabled = false;
	//document.TollTagForm.vehicleColor.value = vehColor;
	//document.TollTagForm.vehicleColor.disabled = true;
	//document.TollTagForm.dmvAccepted.value="true";
	
	document.TollTagForm.dmvAccepted.value="true";
} 


function validateExistingTagInfo(){
	var tollTag = document.getElementById("tollTag");
    var tollTagConfirm = document.getElementById("tollTagConfirm");
    var agencyId = document.getElementById("agencyId");
    var agencyIdConfirm = document.getElementById("agencyIdConfirm");
    var vehicleExists = document.getElementById("vehicleExists");
    var tagExists = document.getElementById("tagExists");
    var currentlyHaveTollTag = false;
    var tagOptionSelected = false;
    
	if(document.TollTagForm.tagExists){
	    for (var i=0; i < document.TollTagForm.tagExists.length; i++) {
	       if (document.TollTagForm.tagExists[i].checked) {
	          var tag_val = document.TollTagForm.tagExists[i].value;
	          if(tag_val == 'Y') {
	        	  currentlyHaveTollTag = true;
	        	  tagOptionSelected = true;
	          }
	          
	          if(tag_val == 'N') {
	        	  tagOptionSelected = true;
	          }
	       }
	    }
	    
	    if(tagRequired  && !tagOptionSelected){
			alert('<fmt:message key="tagInfo.tollTagChoiceEmpty.error"/>');
			return false;
		}
	}
	

    if(currentlyHaveTollTag && document.TollTagForm.tagExists){
    	return validateTagInformation();     
    }
    return true;
	
}

//after the ui is pushed use the padLeadingZeroForTag()
function padLeadingZeroForTag4(ctl) {
//	alert("The input is "+ ctl.value);
	    var s = ctl.value+""; 
	    while (s.length < 8) s = "0" + s; 
	    ctl.value=s;		
    return true;
}

</script>