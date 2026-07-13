<%@ include file="/jsp/common/Taglibs.jsp" %>
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

<jsp:useBean id="appHelper"  class="com.etcc.csc.helper.AppHelper" scope="page"/>

<c:set var="maxRows" value="50"/>
<c:set var="licPlate"><fmt:message key="tagInfo.licPlate"/></c:set>
<c:set var="licState"><fmt:message key="tagInfo.licState"/></c:set>
<c:set var="vehicleYear"><fmt:message key="tagInfo.vehicleYear"/></c:set>
<c:set var="vehicleColor"><fmt:message key="tagInfo.vehicleColor"/></c:set>
<c:set var="vehicleMake"><fmt:message key="tagInfo.vehicleMake"/></c:set>
<c:set var="vehicleModel"><fmt:message key="tagInfo.vehicleModel"/></c:set>
<c:set var="vehicleClassDesc"><fmt:message key="tagInfo.vehicleClassDesc"/></c:set>
<tr>
    <td class="topleft"></td>
    <td class="topcenter"></td>
    <td class="topright"></td>
</tr>
<tr id="content-top">
    <td class="left"></td>
    <td class="content"></td>		
    <td class="right"></td>		
</tr>
<tr>
    <td class="left"></td>
    <td  id="data" class="content">
    <html:form action="/saveActivateTag.do" >
    	<br>
        <h2 id="verbiage"  ><font color="#6e94ab"><b><fmt:message key="AddTag.note"/></b></font></h2>        
        <br> 
            <logic:messagesPresent message="false">
                <div id="error">
                    <div class="error-img"></div>
                    <ul>
                        <html:messages id="msg" message="false">
                            <li>
                                <bean:write name="msg"/>
                            </li>
                        </html:messages>
                    </ul>
                    <br/>
                </div>
            </logic:messagesPresent>
            <logic:messagesPresent message="true" property="saveFailed">
                <div id="error">
                    <div class="error-img"></div>
                    <ul>
                        <html:messages id="msg" message="true" property="saveFailed">
                            <li>
                                <bean:write name="msg"/>
                            </li>
                        </html:messages>
                    </ul>
                    <br/>
                </div>
            </logic:messagesPresent>
            <table>
            	<tr>
            		<td>
            			<input type="radio" name="selectVehicle" id="newVehicle" value="newVehicle" onclick="return selectNewVehicle();"><b>&nbsp;<fmt:message key="addTag.newVehicle"/></b>
            			<input type="radio" name="selectVehicle" id="existingVehicle" value="existingVehicle" onclick="return selectExistingVehicle();"/><b>&nbsp;<fmt:message key="addTag.selectExisted"/></b>
            		</td>
            	</tr>
            </table>
        
            
            <div id="taglessVehiclesDiv" style="width:911px;display:none">
            	<display:table name="tagLessVehicles" 
                  pagesize="${maxRows}" 
                  id="vehicle" excludedParams="*" requestURI="/activateTag.do">	
                <display:setProperty name="paging.banner.placement">bottom</display:setProperty>                   
           	  	<display:column  sortable="false" title="" >
           			<input type="radio" name="selectedVehicle" id="selectedVehicle" value="${vehicle.accountVehicleId}" onclick="enableSaveButton();"/>
          		</display:column>                    	
                 <display:column property="licPlate" title='${licPlate}' sortable="false" />
                 <display:column property="licState" title='${licState}' sortable="false"/>
                 <display:column property="vehicleYear" title='${vehicleYear}' sortable="false"/>
                 <display:column property="vehicleColor" title='${vehicleColor}' sortable="false" class="data-nowrap"/>
                 <display:column property="vehicleMake" title='${vehicleMake}' sortable="false" class="data-nowrap"/>
                 <display:column property="vehicleModel" title='${vehicleModel}' sortable="false" class="data-nowrap"/>
                 <display:column property="vehicleClassDesc" title='${vehicleClassDesc}' sortable="false" class="data-nowrap"/>             		  
           		</display:table>     
            </div>
            
            
           <br/>
           <html:hidden property="dmvAccepted"/>
           <html:hidden property="dmvVehicleId"/>      
           <html:hidden property="tagTransferFlag"/>
                
           <div id="tagActivationDiv" style="width:911px;display:none">
           		<table cellspacing="0" class="form" width="50%">	
					<tr class="odd-a">
						<th colspan="2"><div id="displayTagNumberHeader">
								<fmt:message key="tagRequestForm.tollTagNumber" /> *
							</div>
						</th>
						<th colspan="2"><div id="displayTagNumberConfirmHeader">
								<fmt:message key="tagRequestForm.tollTagNumberRetype" /> *
							</div>
						</th>
					</tr>
					<tr class="odd-a">
						<%-- Add tag authorities of new requirement:dropdown DNT/DFW/TEX --%>
						<td width="25%">
							<div id="displayTagNumberInput" style="display:block; font:bold;">
								<html:select property="agencyId" styleClass="text" tabindex="1" style="width:60px"
									styleId="agencyId">
									<html:options collection="tagAuthorities"
										property="tagIdentifier" labelProperty="tagIdentifier" />
								</html:select>&nbsp;.
								<input type="text" id="tollTag" tabindex="2" name="tollTag"
										class="text" size="10" maxlength="10"
										 onselect="javascript:unselect();"
										onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag2(this);" />
							</div>
						</td>
							<%--
		                          <td>
		                          <div id="displayTagNumberInput1" style=" display: block; font: bold;vertical-align: bottom;">.</div>
		                          </td>
		       				 --%>
						<td>
							
						</td>
						<%-- Add tag authorities of new requirement:dropdown DNT/DFW/TEX --%>
						 <td>
	                          <div id="displayTagNumberConfirmInput" style=" display: block;font: bold;">
		                          <html:select property="agencyIdConfirm" styleClass="text" tabindex="3" styleId="agencyIdConfirm">
		                            <html:options collection="tagAuthorities" property="tagIdentifier" labelProperty="tagIdentifier"/>
		                          </html:select>&nbsp;.
		                          <input type="text" id="tollTagConfirm" tabindex="4"
									name="tollTagConfirm" class="text"
									size="10" maxlength="10" 
									onselect="javascript:unselect();"
									onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag2(this);"
									onBeforePaste="window.clipboardData.clearData();" />
	                          </div>
	                          
	                       </td>
						<td>
							
						</td>
					</tr>
		           
				</table>
           </div>
           
           <div id="vehicleCreationDiv" style="width:911px;display:none">           		
           		<jsp:include page="../getTollTag/vehicleInfo_include.jsp"></jsp:include>
           </div>
          <table>
          	<tr>
				<td><div id="saveButtonDiv" style="display:none"><input type="submit" class="button"
					value='<fmt:message key="button.save" />' id="addButton"
					onclick="return doSubmit(${sessionScope.limit});"/></div>
				</td>
			</tr>
          </table>
        
<br/><br/><br/><br/>
    
    </html:form>
    <br>
    <br>
    <br>
    <br>
    </td>		
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

<script language="javascript"><!--

	var selectionType = "${requestScope.selectionType}";	
	
	var planCode = '<c:out value="${accountLogin.acctPlan}" />';
	var tagRequired = <c:out value="${accountLogin.minTag == 1}" />;
	var tagProhibited = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag == 0}" />;
	var tagOptional = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag < 0}" />;
	
	var minVehicle = <c:out value="${accountLogin.minVehicle}" />;
	var maxVehicle = <c:out value="${accountLogin.maxVehicle}" />;

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
	var licPlateExisting = "${requestScope.licPlateExisting}";
	var cnt = 0;
	YAHOO.util.Event.onDOMReady(doOnLoadActivateTag);

	function doOnLoadActivateTag(){

		radioObj = document.TollTagForm.selectVehicle;
		var radioLength = radioObj.length;
		
		if(radioLength == undefined) {
			radioObj.checked = (radioObj.value == selectionType);
		}
		
		for(var i = 0; i < radioLength; i++) {
			radioObj[i].checked = false;
			if(radioObj[i].value == selectionType) {
				radioObj[i].checked = true;
				document.getElementById('saveButtonDiv').style.display='block';
			}
		}

		// By default select new vehicle, when loading the page
		if(selectionType == ''){
			radioObj[0].checked = true;
			selectionType = 'newVehicle';
		}

		if (selectionType == 'existingVehicle') {	
			document.getElementById("addButton").disabled = true;			
			enableRequestFields();
			selectExistingVehicle();
			
		} if (selectionType == 'newVehicle') {
			
			selectNewVehicle();
			enableRequestFields();
			enableActivationFields();
			enableLeaseDates();
		    enablePlateExpirationDate();		    
		}
		
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
	    		if(tagExistsOnVehicle == "Y"){
	    			var vehicleTagOverlap = "<fmt:message key='tagInfo.licPlateTag.error.exist1'/>";
					var confirmAddVehTagOk = "<fmt:message key='tagInfo.licPlateTag.error.exist2'/>";
					var confirmAddVehTagOR = "<fmt:message key='tagInfo.licPlateTag.error.exist3'/>";
					var confirmAddVehTagCancel = "<fmt:message key='tagInfo.licPlateTag.error.exist4'/>";
					
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
		    			document.TollTagForm.tagTransferFlag.value = "N";
		    			confirmTagOverlapAndAdd();
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
		    		if(licPlateExisting == 'Y'){
		    			alert("<fmt:message key='vehicleInfo.licPlate.exist'/>");
		    			return false;
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
	   			}else {		
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
    		document.TollTagForm.action="AddVehicleSaveAction.do?myAction=confirmAddVehicle" + 
    		"&sourceAccountVehicleId=" + sourceAccountVehicleId + "&sourceAccountTagId=" + sourceAccountTagId;	
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
	
	function doSubmit(limit){	
		var selectedTagObj = document.TollTagForm.selectVehicle;
		var chosen = false;		
		var selectedVal;
   		len = selectedTagObj.length;
   		if(len == undefined){
   			if(selectedTagObj.checked){
   				chosen = true;
   			}
   		}
		for (i = 0; i <len; i++) {
	   		if (selectedTagObj[i].checked) {		   		
	   			chosen = true;
	   			selectedVal = selectedTagObj[i].value;	   			
	   		}
   		}     		
		
		
   		if(chosen){	   	
		   		if(selectedVal == 'existingVehicle'){
		   			//validate if a vahicle is selected or not
		   	   		var selectedVehicleObj = document.TollTagForm.selectedVehicle;
		   			if(selectedVehicleObj == undefined || selectedVehicleObj == null){
		   				alert('<fmt:message key="tagInfo.tollTagSelectedVehicleEmpty.error"/>');
		   				return false;
		   			}

		   	   		len = selectedVehicleObj.length;
		   	   		chosenVeh=false;
			   	   	if(len == undefined){
			   			if(selectedVehicleObj.checked){
			   				chosenVeh = true;
			   			}
			   		}   	
			   	   	for (i = 0; i <len; i++) {
				   		if (selectedVehicleObj[i].checked) {		   		
				   			chosenVeh = true;		   				
				   		}
			   		}     	
			   		if(chosenVeh){
			   			isValid = validateExistingTagInfo();					
						if(!isValid){
							return false;
						}else{
							document.TollTagForm.action += '?vehicleExists=' + selectedVal;
							return true;
						}	
			   		}else {
						alert('Please select a vehicle');
			   		}		
		   		}
		   		if(selectedVal == 'newVehicle'){
		   			isValid = checkData(limit);
					if(!isValid){
						return false;
					}else{
						document.TollTagForm.action += '?vehicleExists=' + selectedVal;
						return true;
					}	
					//validate all
					//return doValidate(${sessionScope.limit});
		   		}
	   		
	   		
   		}else{
			alert('<fmt:message key="tagInfo.licPlate.error.exist3"/>');
   		}
		
		return false;
	}
	
	function enableRequestFields() {
		
	    try {
	    	var tagId =  "";
	   		<logic:notEmpty name="TollTagForm" property="tollTag">
	   		  	tagId = '<bean:write name="TollTagForm" property="tollTag"/>';
	   		</logic:notEmpty>
	    	document.getElementById("tollTag").value = tagId;
	   		document.getElementById("tollTagConfirm").value = tagId;
	    	document.getElementById('tagActivationDiv').style.display ='';
            document.getElementById('vehicleCreationDiv').style.display ='none';
            document.getElementById('saveButtonDiv').style.display='block';
    	
	    }catch(e){
	    }
	}

	
	function enableActivationFields()
    {
        try
        {
            document.getElementById('tagActivationDiv').style.display ='';
            document.getElementById('vehicleCreationDiv').style.display ='';
            document.getElementById('saveButtonDiv').style.display='block';
            /*
            document.getElementById('displayTagNumberHeader').style.display ='none';
            document.getElementById('displayTagNumberConfirmHeader').style.display ='block';
            document.getElementById('displayTagNumberInput').style.display ='block';
            document.getElementById('displayTagNumberConfirmInput').style.display ='block';
            //document.getElementById('displayTagNumberInput1').style.display ='block';
            //document.getElementById('displayTagNumberConfirmInput1').style.display ='block';
            document.getElementById('displayTagNumberInput2').style.display ='block';
            document.getElementById('displayTagNumberConfirmInput2').style.display ='block'; */
    
            document.getElementById("licensePlate").disabled = false;
            document.getElementById("licensePlateState").disabled = false;
            document.getElementById("licPlateType").disabled = false;
            document.getElementById("vehicleYear").disabled = false;
            document.getElementById("vehicleColor").disabled = false;
            document.getElementById("vehicleMake").disabled = false;
            document.getElementById("vehicleModel").disabled = false;   
            document.getElementById("vehicleClassCode").disabled = false; 
            //document.getElementById("tollTag").className = "text";
            //document.getElementById("tollTagConfirm").className = "text";
        }catch(e){
        }
    }

	/*function selectNewVehicle(){
		var noOfVehicles = '<c:out value="${noOfVehiclesInAccount}" />' * 1;		

		if(maxVehicle > 0 && noOfVehicles >= maxVehicle){
			alert('<fmt:message key="tagInfo.tollTagMaxVehicles.error"><fmt:param>${accountLogin.maxVehicle}</fmt:param></fmt:message>');
			return false;
		} else {
			enableActivationFields();
			return true;
		}
	}*/
	
	function selectExistingVehicle() {
	    try {
	    	document.getElementById('addButton').disabled = true;
	    	if(document.getElementById('selectedVehicle') != null && document.getElementById('selectedVehicle').checked){
	    		document.getElementById('addButton').disabled= false;
	    	}
	        document.getElementById('vehicleCreationDiv').style.display ='none';
	        document.getElementById('taglessVehiclesDiv').style.display='block';
	        document.getElementById('saveButtonDiv').style.display='block';
	    }catch(e){
	    }
	}
	
	function selectNewVehicle()
	{
	    document.getElementById('vehicleCreationDiv').style.display ='block';
	    document.getElementById('tagActivationDiv').style.display = 'block';
		document.getElementById('taglessVehiclesDiv').style.display='none';
	    document.getElementById('saveButtonDiv').style.display='block';
	    document.getElementById('addButton').disabled = false;

	    document.getElementById("licensePlate").disabled = false;
	    document.getElementById("licensePlateState").disabled = false;
	    document.getElementById("licPlateType").disabled = false;
	    document.getElementById("vehicleYear").disabled = false;
	    document.getElementById("vehicleColor").disabled = false;
	    document.getElementById("vehicleMake").disabled = false;
	    document.getElementById("vehicleModel").disabled = false;   
	    document.getElementById("vehicleClassCode").disabled = false; 
	    var noOfVehicles = '<c:out value="${noOfVehiclesInAccount}" />' * 1;		

		if(maxVehicle > 0 && noOfVehicles >= maxVehicle){
			alert('<fmt:message key="tagInfo.tollTagMaxVehicles.error"><fmt:param>${accountLogin.maxVehicle}</fmt:param></fmt:message>');
			return false;
		} else {
			enableActivationFields();
			return true;
		}
	 }  
	
	function validateExistingTagInfo(){	
       return validateTagInformation();     
	}
	
// after the ui is pushed use the padLeadingZeroForTag()
	function padLeadingZeroForTag2(ctl) {
//		alert("The input is "+ ctl.value);
		    var s = ctl.value+""; 
		    while (s.length < 8) s = "0" + s; 
		    ctl.value=s;		
	    return true;
	}
	
	function enableSaveButton(){
			document.getElementById('addButton').disabled = false;
	}
--></script>
