<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript" src="<html:rewrite page='/js/calendarPopup.js'/>"> </script>
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
   	<html:form action="/tagSave.do" >
    <td id="data" class="content">
    	<br>
        <h2 id="verbiage"><font color="#6e94ab"><b><fmt:message key="AddTag.note"/></b></font></h2>        
        <br> 
        <logic:messagesPresent message="true" property="saveFailed">
            <div id="error">
                <ul>
                    <html:messages id="msg" message="true" property="saveFailed">
                        <li>                                        
                            ${msg}
                        </li>
                    </html:messages>
                </ul>
                <br/>
            </div>
        </logic:messagesPresent>
            <table>
            	<tr>
            		<td>
            				<input type="radio" name="selectVehicle" id="newVehicle" value="newVehicle" onclick="selectNewVehicle();"><b>&nbsp;<fmt:message key="addTag.newVehicle"/></b>
    
            				<input type="radio" name="selectVehicle" id="existingVehicle" value="existingVehicle" onclick="selectExistingVehicle();"/><b>&nbsp;<fmt:message key="addTag.selectExisted"/></b>
            		
            		</td>
            	
            	</tr>
            </table>
            <c:set var="licPltLabel"><fmt:message key="label.license.plate" /></c:set>
            <c:set var="licPltStateLabel"><fmt:message key="label.state" /></c:set>
            <c:set var="yearLabel"><fmt:message key="label.year" /></c:set>
            <c:set var="colorLabel"><fmt:message key="label.color" /></c:set>
            <c:set var="makeLabel"><fmt:message key="label.vehicle.make" /></c:set>
            <c:set var="modelLabel"><fmt:message key="label.vehicle.model" /></c:set>
            <c:set var="vehClassLabel"><fmt:message key="label.vehicle.class" /></c:set>
            
			<div id="taglessVehiclesDiv" style="width:911px;display:none">           		
	            <display:table name="sessionScope.tagLessVehicles" 
	                  pagesize="${maxRows}" 
	                  id="vehicle" excludedParams="*" requestURI="/activateTag.do">	                   
					  <display:setProperty name="basic.msg.empty_list_row" value="<span style='font-size:12px'>No data</span>"/>
	
	           	  	<display:column  sortable="false" title="" >
	           			<input type="radio" name="selectedVehicle" id="selectedVehicle" value="${vehicle.accountVehicleId}" onclick="enableSaveButton();"/>
	          		</display:column>                    	
	                 <display:column title="${licPltLabel}" property="licPlate" sortable="false" />
	                 <display:column title="${licPltStateLabel}" property="licState" sortable="false"/>
	                 <display:column title="${yearLabel}" property="vehicleYear" sortable="false"/>
	                 <display:column title="${colorLabel}" property="vehicleColor" sortable="false" class="data-nowrap"/>
	                 <display:column title="${makeLabel}" property="vehicleMake" sortable="false" class="data-nowrap"/>
	                 <display:column title="${modelLabel}" property="vehicleModel" sortable="false" class="data-nowrap"/>
	                 <display:column title="${vehClassLabel}" property="vehicleClassDesc" sortable="false" class="data-nowrap"/>             		  
	           </display:table>
		   </div>

           <div id="vehicleCreationDiv" style="width:911px;display:none">           		
			<table>
				<tr>
					<td class="form">
					<table cellspacing="0" >
						<tr class="odd-a" style="width: 100%">
							<th><fmt:message key="tagRequestForm.licensePlate.short" /> *</th>
							<th><fmt:message key="tagRequestForm.state" /> *</th>
							<th nowrap="nowrap"><fmt:message
								key="tagRequestForm.licensePlateType" /> *</th>
							<th nowrap="nowrap"><fmt:message
								key="tagRequestForm.ownershipDateStart"></fmt:message><span
								id="ownershipDateDiv"> *</span></th>
							<th><fmt:message key="tagRequestForm.year" /> *</th>
							<th><fmt:message key="tagRequestForm.color" /> *</th>
							<th><span class="more-info"
								title="<fmt:message key='tagRequestForm.make.caption'/>"> <fmt:message
								key="tagRequestForm.make" /></span> *</th>
							<th><span class="more-info"
								title="<fmt:message key='tagRequestForm.model.caption'/>"><fmt:message
								key="tagRequestForm.model" /></span> *</th>
							<th nowrap="nowrap"><fmt:message
								key="tagRequestForm.vehicleClass" /> *</th>
							<th><c:choose>
								<c:when test="${not empty requestScope.vehicleIndex}">
									<fmt:message key="label.delete" />?</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose></th>
						</tr>

						<tr >
							<td><html:text property="licensePlate" tabindex="5"
								styleId="licensePlate" disabled="true" styleClass="text" size="6"
								maxlength="15" style="width:70px"
								onblur="cleanUserField(this);cleanAllSpaces(this);return validateTempLicense();" />
							<html:hidden property="skipDuplicateCheck" /></td>
							<td><html:select property="licensePlateState" tabindex="6"
								styleId="licensePlateState" disabled="true" size="1"
								styleClass="text">
								<html:options collection="states" property="stateCode"
									labelProperty="stateCode" />
							</html:select></td>
							<td><html:select property="licPlateType" tabindex="7"
								style="width:100px" disabled="true" styleId="licPlateType" size="1"
								onchange="javascript: showWarning();" styleClass="text">
								<html:options collection="licPlateTypes" property="licPlateCode"
									labelProperty="plateTypeDescr" />
							</html:select></td>
							<td nowrap="nowrap"><html:text property="ownerShipDate"
								tabindex="8" styleClass="text" styleId="ownerShipDate" /> <a
								href="#"
								onClick="cal1.select(document.tagRequestForm.ownerShipDate,'anchor1','MM/dd/yyyy'); return false;"
								name="anchor1" id="anchor1"> <html:img
								page="/images/calendar.gif" border="0"
								style="WIDTH:29px;HEIGHT:18px" /></a></td>
							<td><html:text property="vehicleYear" tabindex="9"
								style="width:50px" disabled="true" styleId="vehicleYear" size="4"
								maxlength="4" styleClass="text" onblur="cleanNumericField(this)" />
							</td>
							<td><html:text property="vehicleColor" tabindex="10"
								disabled="true" styleId="vehicleColor" size="6" maxlength="20"
								styleClass="text" onblur="cleanUserField(this)" /></td>
							<td><html:text property="vehicleMake" tabindex="11"
								disabled="true" styleId="vehicleMake" size="6" maxlength="30"
								styleClass="text" onblur="cleanUserField(this)" /></td>
							<td><html:text property="vehicleModel" tabindex="12"
								disabled="true" styleId="vehicleModel" size="6" maxlength="30"
								styleClass="text"
								onblur="this.value = this.value.toUpperCase().replace(/^\s*|\s*$/g, '');" />
							</td>
							<td><html:select property="vehicleClassCode" tabindex="13"
								disabled="true" styleId="vehicleClassCode" size="1"
								onchange="javascript: showWarning();" styleClass="text"
								style="width:100%">
								<html:options collection="vehicleClasses"
									property="vehicleClassCode" labelProperty="vehicleClassDescr" />
							</html:select></td>	
							<td><html:hidden property="tagExists" value="N"/></td>		
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td>
					<table cellspacing="0" style="width: 100%">
						<tr class="odd-a">
							<th nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.leaseOrRent" />?</th>
							<td nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><html:checkbox
								tabindex="12" property="rentOrLeasePlate"
								onclick="return enableLeaseDates();" /></td>
							<th nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.leaseOrRentStart" /> *</th>
							<td nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><html:text
								property="leasedOrRentStartDate" tabindex="13" size="10"
								maxlength="10" styleClass="text" disabled="true" /> <a href="#"
								onClick="cal1.select(document.tagRequestForm.leasedOrRentStartDate,'anchor2','MM/dd/yyyy'); return false;"
								name="anchor2" id="anchor2"> <html:img
								page="/images/calendar.gif" border="0"
								style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<th nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.leaseOrRentEnd" /> *</th>
							<td nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><html:text
								property="leasedOrRentEndDate" tabindex="13" size="10"
								maxlength="10" styleClass="text" disabled="true" /> <a href="#"
								onClick="cal1.select(document.tagRequestForm.leasedOrRentEndDate,'anchor3','MM/dd/yyyy'); return false;"
								name="anchor3" id="anchor3"> <html:img
								page="/images/calendar.gif" border="0"
								style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<td width="100%"
								style="background-color: #f8f5ef; text-align: left;">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
						<tr class="odd-a">
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.tempLicense" />?</th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><html:checkbox tabindex="12"
								property="tempLicensePlate"
								onclick="return enablePlateExpirationDate( );" /></td>
							<th nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.leaseOrRentEnd" /> *</th>
							<td nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><html:text
								property="tempLicensePlateExpireDate" tabindex="13" size="10"
								maxlength="10" styleClass="text" disabled="true" /> <a href="#"
								onClick="cal1.select(document.tagRequestForm.tempLicensePlateExpireDate,'anchor4','MM/dd/yyyy'); return false;"
								name="anchor4" id="anchor4"><html:img
								page="/images/calendar.gif" border="2"
								style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<th nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><fmt:message
								key="tagRequestForm.vinNumber"></fmt:message><span
								id="vinNumberLblDiv"> *</span></th>
							<td nowrap="nowrap"
								style="background-color: #f8f5ef; text-align: left;"><html:text styleId="vinNumber" 
								property="vinNumber" styleClass="text" /></td>
							<td width="100%"
								style="background-color: #f8f5ef; text-align: left;">&nbsp;</td>

						</tr>
					</table>
					</td>
				</tr>	
				</table>
    </div> <!-- End of div 'vehicleCreationDiv' -->

	<table>
		<tr>
			<td><div id="saveButtonDiv" style="display:none"><input type="submit" class="button"
				value='<fmt:message key="button.save" />' id="saveButton"
				onclick="return doSave(${sessionScope.limit});"/></div>
			</td>
		</tr>
	 </table>
       
    <c:if test="${not empty tagRequestForm.savedVehicles}">
        <table cellspacing="1" class="form">
            <tr class="odd-a">
             <th><fmt:message key="tagRequestForm.licensePlate.short"/></th>
             <th><fmt:message key="tagRequestForm.tempLicense.short"/></th>
             <th><fmt:message key="tagRequestForm.expires"/></th>
             <th><fmt:message key="tagRequestForm.state"/></th>
             <th><fmt:message key="tagRequestForm.year"/></th>
             <th><fmt:message key="tagRequestForm.color"/></th>
             <th><fmt:message key="tagRequestForm.make"/></th>
             <th><fmt:message key="tagRequestForm.model"/></th>
             <th><fmt:message key="tagRequestForm.vehicleClass"/></th>
            </tr>
                                
            <c:forEach items="${tagRequestForm.savedVehicles}" var="savedVehicle" varStatus="varStatus">
                <tr>
                  <td>
                    <c:if test="${varStatus.index != requestScope.vehicleIndex}">
                      <a HREF="javascript: retrieveVehicle(<c:out value='${varStatus.index}'/>);">
                    </c:if>      
                    <c:out value="${savedVehicle.licPlate}"/>
                    <input type="hidden" name="savedVehicle[${varStatus.index}].licPlate" value="${savedVehicle.licPlate}"/>
                    <c:if test="${varStatus.index != requestScope.vehicleIndex}">      
                      </a>
                    </c:if>
                  </td>
                  <c:choose>
                    <c:when test="${savedVehicle.temporaryLicPlate==true}">
                        <c:set var="isTempPlate" value="Yes"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="isTempPlate" value="No"/>
                    </c:otherwise>
                  </c:choose>
                  <td>${isTempPlate}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].temporaryLicPlate" value="${savedVehicle.temporaryLicPlate==true}"/>
                  </td>
                  <td>
                    ${savedVehicle.plateExpiration}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].plateExpiration" value="${savedVehicle.plateExpiration}"/>
                  </td>
                  <td>${savedVehicle.licState}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].licState" value="${savedVehicle.licState}"/>  
                  </td>
                  <td>${savedVehicle.vehicleYear}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleYear" value="${savedVehicle.vehicleYear}"/> 
                  </td>
                  <td>${savedVehicle.vehicleColor}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleColor" value="${savedVehicle.vehicleColor}"/> 
                  </td>
                  <td>${savedVehicle.vehicleMake}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleMake" value="${savedVehicle.vehicleMake}"/> 
                  </td>
                  <td>${savedVehicle.vehicleModel}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleModel" value="${savedVehicle.vehicleModel}"/> 
                  </td>
                  <td>${savedVehicle.vehicleClassCode}
                    <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleClassCode" value="${savedVehicle.vehicleClassCode}"/> 
                  </td>
                </tr>
                <input type="hidden" name="savedVehicle[${varStatus.index}].plateTypeCode" value="${savedVehicle.plateTypeCode}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].effectiveDateStr" value="${savedVehicle.effectiveDateStr}"/> 
               	<input type="hidden" name="savedVehicle[${varStatus.index}].acctTagSeq" value="${savedVehicle.acctTagSeq}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].checkDup" value="${savedVehicle.checkDup}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].rentalStart" value="${savedVehicle.rentalStart}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].rentalEnd" value="${savedVehicle.rentalEnd}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].tagExists" value="${savedVehicle.tagExists}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].rentalVehicle" value="${savedVehicle.rentalVehicle}"/> 
               <input type="hidden" name="savedVehicle[${varStatus.index}].dmvVehicleId" value="${savedVehicle.dmvVehicleId}"/> 
                <input type="hidden" name="savedVehicle[${varStatus.index}].dmvAccepted" value="${savedVehicle.dmvAccepted}"/> 
            </c:forEach>
            
            <tr>
                <td colspan=10 class="align-right"><a href="javascript:nextStep();" class="next" id="nextLink" onclick="return doClick();"><fmt:message key="tagRequestForm.link.text"/></a></td>
            </tr>
        </table>
    </c:if>
		</td>
		   <html:hidden property="dmvAccepted"/>
           <html:hidden property="dmvVehicleId"/>           
		
</html:form>
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

<script language="javascript">
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
var selectionType = "${requestScope.selectionType}";
var vehClassCode = "${requestScope.vehClassCode}";
var afterConfirmAddVehicle = "${requestScope.afterConfirmAddVehicle}";

var cnt = 0;
YAHOO.util.Event.onDOMReady(init);

function init(){
	radioObj = document.tagRequestForm.selectVehicle;
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
	
	
	if (selectionType == 'existingVehicle') {
		document.getElementById("saveButton").disabled = true;
		selectExistingVehicle();
		
	}if (selectionType == 'newVehicle') {
		
		selectNewVehicle();
		enableLeaseDates();
	    enablePlateExpirationDate( );
	}
	
	if(validationPassed == "Y" ){
		confirmOverlapAndAdd();	    	
    } else if(validationPassed == "N" ) {
    	if(validationMessageFromAPI != ""){
    		alert(validationMessageFromAPI);
    	} else if(!(numberOfVehicles == "0" || numberOfVehicles == "1")){
    		var msg4 = "<fmt:message key='tagInfo.licPlate.error.exist4'><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>";
    		alert(msg4);
    	}
    }
//if date is blank put current date
	if(document.getElementById('ownerShipDate').value == "") {
		document.getElementById('ownerShipDate').value = getCurrentDayAsString();
		}	
}

function confirmOverlapAndAdd(){
	var msg1 = "<fmt:message key='tagInfo.licPlate.error.exist1'/>";
	var msg2 = "<fmt:message key='tagInfo.licPlate.error.exist2'/>";
	var msg5 = "<fmt:message key='tagInfo.licPlate.error.exist5'><fmt:param>${requestScope.transactionAmount}</fmt:param></fmt:message>"
	if(numberOfVehicles == "1"){
		if(transactionChargesPresent == "Y"){
			var acceptedTransactionAmt = confirm(msg1 + msg5 + msg2);
    		if(acceptedTransactionAmt){
    			confirmDmvAndAdd();    		    	
    			//addVehicleWithoutValidation();    		    			
    		}
		} else {
			var acceptedOverlap = confirm(msg1 + msg2);
    		if(acceptedOverlap){
    			confirmDmvAndAdd();    		    	
    			//addVehicleWithoutValidation();    		    			
    		}
		}		
	} else if(dmvInfoPresent == "Y" && numberOfVehicles == "0"){
		confirmDmvAndAdd();    	
		//addVehicleWithoutValidation();    	
	}
}

 function confirmDmvAndAdd(){
	if(dmvInfoPresent == "Y"){
		var dmvMsg1 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo1"/>';
		var dmvMsg2 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo2"/>';
		var dmvAccepted = confirm(dmvMsg1 + "\n"  + vehMake  + " " +  vehModel + " " + vehYear + " " + vehVin + "\n" + dmvMsg2);
		if(dmvAccepted){
			updateDmvInfo();
			addVehicleWithoutValidation();
		} else {
			addVehicleWithoutValidation();
		}
	}  else {
		addVehicleWithoutValidation();
	}
}
    
function addVehicleWithoutValidation(){
	if(document.tagRequestForm.dmvAccepted.value == "true"){			
		enableDmvFields();
	}
	
	document.tagRequestForm.vehicleColor.disabled = false;
	document.tagRequestForm.vehicleColor.readonly = true;
	document.tagRequestForm.vehicleClassCode.disabled = false;
	document.tagRequestForm.vehicleClassCode.readonly = true;
	document.tagRequestForm.licPlateType.disabled = false;
	document.tagRequestForm.licPlateType.readonly = true;
	document.tagRequestForm.licensePlate.disabled = false;
	document.tagRequestForm.licensePlate.readonly = true;
	document.tagRequestForm.vehicleMake.disabled = false;
	document.tagRequestForm.vehicleMake.readonly = true;
	document.tagRequestForm.vehicleModel.disabled = false;
	document.tagRequestForm.vehicleModel.readonly = true;	
	document.tagRequestForm.vehicleYear.disabled = false;
	document.tagRequestForm.vehicleYear.readonly = true;	
	document.tagRequestForm.vinNumber.disabled = false;
	document.tagRequestForm.vinNumber.readonly = true;
	
	document.tagRequestForm.action="${pageContext.request.contextPath}/tagSave.do?myAction=confirmAddVehicle";		
	document.tagRequestForm.submit();
}

function enableDmvFields(){
	document.tagRequestForm.vehicleMake.disabled = false;
	document.tagRequestForm.vehicleMake.readonly = true;
	document.tagRequestForm.vehicleModel.disabled = false;
	document.tagRequestForm.vehicleModel.readonly = true;
	document.tagRequestForm.vehicleYear.disabled = false;
	document.tagRequestForm.vehicleYear.readonly = true;
	document.tagRequestForm.vinNumber.disabled = false;
	document.tagRequestForm.vinNumber.readonly = true;
}

function updateDmvInfo() {
	document.tagRequestForm.vehicleMake.disabled = false;
	document.tagRequestForm.vehicleMake.value = vehMake;
	document.tagRequestForm.vehicleMake.disabled = true;
	
	document.tagRequestForm.vehicleModel.disabled = false;
	document.tagRequestForm.vehicleModel.value = vehModel;
	document.tagRequestForm.vehicleModel.disabled = true;
	
	document.tagRequestForm.vehicleYear.disabled = false;
	document.tagRequestForm.vehicleYear.value = vehYear;
	document.tagRequestForm.vehicleYear.disabled = true;
	
	document.tagRequestForm.vinNumber.disabled = false;
	document.tagRequestForm.vinNumber.value = vehVin;
	document.tagRequestForm.vinNumber.disabled = true;
	
	//document.tagRequestForm.vehicleColor.disabled = false;
	//document.tagRequestForm.vehicleColor.value = vehColor;
	//document.tagRequestForm.vehicleColor.disabled = true;
	//document.tagRequestForm.dmvAccepted.value="true";
	
	document.tagRequestForm.dmvAccepted.value="true";
}
var cal1 = new CalendarPopup(); 
var dupFlag = "${dupPlate}";
if (dupFlag=="Y")
{
   var result = confirm('<fmt:message key="getTolltag.vehicleInfo.licensePlate.duplicate"><fmt:param value="${tagRequestForm.licensePlateState}"/><fmt:param value="${tagRequestForm.licensePlate}"/></fmt:message>');
   if (result)
   {
        var form = document.tagRequestForm;
        document.getElementById("checkDup").value=false;
        form.submit();
   }
/*   else
   {
        alert("cancelled");
   }
*/   
}

 var tempLicenseOnLoad = ${tagRequestForm.tempLicensePlate};
 document.tagRequestForm.tempLicensePlateExpireDate.disabled = tempLicenseOnLoad?false:true;

function toUpper(ctl)
{
    var ctlValue = ctl.value;
    ctl.value = ctlValue.toUpperCase();
    return true;
}

function tmpLicenseClick(ctl)
{
    var expires = document.tagRequestForm.tempLicensePlateExpireDate;
    if (ctl.checked)
    {
        if (checkTempLicensePlate(document.tagRequestForm.licensePlate.value)) 
        {
            expires.disabled = false;
            var licPlate = document.tagRequestForm.elements["tempLicensePlate"];
             //Rite Task 1539  kcollier do not convert temp lic plate to expire date
           // expires.value = convertTempLicenseToDate(licPlate.value);
            expires.focus();
            return true;
        }
        else
        {
            document.tagRequestForm.licensePlate.focus();
            return false;
        }
    }
    else
    {
        expires.value = "";
        expires.disabled =true;
        return true;
    }
        
}

function validateTempLicense()
{
    if (document.tagRequestForm.tempLicensePlate.checked)
    {
        if (checkTempLicensePlate(document.tagRequestForm.licensePlate.value))
        {  //Rite Task 1539  kcollier do not convert temp lic plate to expire date
          //  document.tagRequestForm.licenseExpiration.value = convertTempLicenseToDate(document.tagRequestForm.licensePlate.value);
            return true;
        }
        else
        {
            document.tagRequestForm.licensePlate.focus();
            return false;
        }
    }
    return true;
}



function doSave(limit)
{ 

   var existingVehicle = document.getElementById("existingVehicle").checked;
   var isSelected = false;
   if(existingVehicle){
	    
		var taglessVehicles = document.tagRequestForm.selectedVehicle;
		if(taglessVehicles.length == undefined){
			isSelected = taglessVehicles.checked;
		}else{
			for(var x=0;x<taglessVehicles.length;x++){
				if(taglessVehicles[x].checked){
					isSelected = true;
					break;
				}
			}
   		}

		if(!isSelected ){
			alert('Please select a vehicle');
			document.getElementById('saveButton').disabled = true;
			return false;
		}
	    document.getElementById("saveButton").disabled = true;
        document.tagRequestForm.submit();
        return true;

   } else { // new vehicle

	   if (checkData(limit))
    {
        document.getElementById("saveButton").disabled = true;
        document.tagRequestForm.submit();
        return true;
    }

   }
    
    return false;
}

function retrieveVehicle(index)
{
    document.tagRequestForm.action ="${pageContext.request.contextPath}/tagRetrieve.do?vehicleIndex="+index;
    document.tagRequestForm.submit();
}

function nextStep()
{
    document.tagRequestForm.action ="${pageContext.request.contextPath}/tagSave.do?myAction=requestTollTag";
    document.tagRequestForm.submit();
}

var nextStepClicked = false;

function doClick()
{
    if (nextStepClicked == false)
    {
        document.getElementById('nextLink').className='next-disabled';
        nextStepClicked = true;
        return true;
    }
    return false;
}

function showWarning(){
     confirmOK('','<fmt:message key="label.vehicleclass.message"><fmt:param value="${appHelper.tollRateUrl}"/></fmt:message>','<fmt:message key="label.vehicleclass.header"/>','<fmt:message key="label.vehicleclass.OK"/>', null);   
}

function selectExistingVehicle() {
    try {
    	document.getElementById('saveButton').disabled = true;
    	if(document.getElementById('selectedVehicle') != null && document.getElementById('selectedVehicle').checked){
    		document.getElementById('saveButton').disabled= false;
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
	document.getElementById('taglessVehiclesDiv').style.display='none';
    document.getElementById('saveButtonDiv').style.display='block';
    document.getElementById('saveButton').disabled = false;
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
 }   
function validateExistingTagInfo(){
	var tollTag = document.getElementById("tollTag");
    var tollTagConfirm = document.getElementById("tollTagConfirm");
    var agencyId = document.getElementById("agencyId");
    var agencyIdConfirm = document.getElementById("agencyIdConfirm");
    var existingVehicle = document.getElementById("existingVehicle");
    var tagExists = document.getElementById("tagExists");
    var currentlyHaveTollTag = false;
    var tagOptionSelected = false;
    
	if(document.tagRequestForm.tagExists){
	    for (var i=0; i < document.tagRequestForm.tagExists.length; i++) {   
			    
	       if (document.tagRequestForm.tagExists[i].checked) {
	          var tag_val = document.tagRequestForm.tagExists[i].value;
	          if(tag_val == 'Y') {
	        	  currentlyHaveTollTag = true;
	        	  tagOptionSelected = true;
	          }
	          
	          if(tag_val == 'N') {
	        	  tagOptionSelected = true;
	          }
	       }
	    }
	}	

    if(currentlyHaveTollTag && document.tagRequestForm.tagExists){
        if(tollTag != null && tollTag.value == "") {
            alert('<fmt:message key="tagInfo.tollTagEmpty.error"/>');    
            tollTag.focus();
            cnt++;
            noMatch = true;
            return false;
        }
        
        if(tollTagConfirm != null && tollTagConfirm.value == "") {
            alert('<fmt:message key="tagInfo.tollTagEmpty.error"/>');    
            tollTagConfirm.focus();
            cnt++;
            noMatch = true;
            return false;
        }    
        if(tollTag != null && tollTagConfirm != null && tollTag.value != tollTagConfirm.value){
            if(cnt < 3){        
                alert('<fmt:message key="tagInfo.tagMismatch.error"/>');
                tollTag.focus();
                cnt++;
                noMatch = true;
                return false;
            } else{
                alert('<fmt:message key="tagInfo.tomanyattempts.error"/>'); 
                tollTag.focus();
                cnt++;
                noMatch = true;
                return false;
            
            }
        }
        
        if(agencyId != null && agencyId.value == "") {
            alert('<fmt:message key="tagInfo.agencyIdEmpty.error"/>');    
            agencyId.focus();
            cnt++;
            noMatch = true;
            return false;
        }
        
        if(agencyIdConfirm != null && agencyIdConfirm.value == "") {
            alert('<fmt:message key="tagInfo.agencyIdEmpty.error"/>');    
            agencyIdConfirm.focus();
            cnt++;
            noMatch = true;
            return false;
        }    
        
        if(agencyId != null && agencyIdConfirm != null && agencyId.value != agencyIdConfirm.value){
            if(cnt < 3){
                alert('<fmt:message key="tagInfo.agencyIdMismatch.error"/>');
                agencyId.focus();
                cnt++;
                noMatch = true;
                return false;
            }
        }        
     }
    return true;
	
}

function checkData(limit) {
	if(!validateExistingTagInfo()){
		return false;
	}
	
    var licPlate = document.tagRequestForm.elements["licensePlate"];
    if (!validateMinLength(licPlate, 2)    
        || !validateMaxLength(licPlate, 10)) {
        alert('<fmt:message key="tagRequestForm.licensePlate.mask"/>');
        if(!licPlate.disabled){
        	licPlate.focus();
        }
        return false;
    }
    
    var vehicleYear = document.tagRequestForm.elements["vehicleYear"];
    if (!validateMinLength(vehicleYear, 4)    
        || !validateMaxLength(vehicleYear, 4)) {
        
        alert('<fmt:message key="tagInfo.vehicleYear.error"/>');
        vehicleYear.focus();
        return false;
    }
    var maxYear = new Date().getFullYear() + 1;
    var minYear = 1900;
    if (vehicleYear.value < minYear || vehicleYear.value > maxYear) {
        alert('<fmt:message key="tagRequestForm.year.error"/>');
        vehicleYear.select();
        vehicleYear.focus();
        return false;
    }
    var vehicleColor = document.tagRequestForm.elements["vehicleColor"];
    if (!validateMinLength(vehicleColor, 3)) {
        alert('<fmt:message key="tagInfo.vehicleColor.error"/>');
        vehicleColor.focus();
        return false;
    }
    var vehicleMake = document.tagRequestForm.elements["vehicleMake"];
    if (!validateMinLength(vehicleMake, 2)) {
        alert('<fmt:message key="tagInfo.vehicleMake.error"/>');
        vehicleMake.focus();
        return false;
    }

    var vehicleModel = document.tagRequestForm.elements["vehicleModel"];
    if (/^\s*$/.test(vehicleModel.value)) {
        alert('<fmt:message key="tagInfo.vehicleModel.error"/>');
        vehicleModel.focus();
        return false;
    }
    var temporaryLicPlate = document.tagRequestForm.elements["tempLicensePlate"];
    if (temporaryLicPlate.checked) {
        var expirationDate = document.tagRequestForm.elements["tempLicensePlateExpireDate"];
        if (expirationDate.value == "") {
            alert('<fmt:message key="tagInfo.expirationDate.error.required"/>');
            expirationDate.focus();
            return false;
        } else if (!isDate(expirationDate.value)) {
            expirationDate.focus();
            return false;
        } else {
            var maxDate =  new Date();
            maxDate.setDate(maxDate.getDate() + limit); 

            // validate if date is greater than today
            if (compareDates(stringToDate(expirationDate.value), 
                new Date()) != 1) {
                
                alert('<fmt:message key="tagInfo.expirationDate.error.greater"/>');
                expirationDate.focus();
                return false;
            }
            else if (compareDates(stringToDate(expirationDate.value), 
                maxDate) == 1){
                alert('<fmt:message key="tagInfo.expirationDate.error.limit"/>');
                expirationDate.focus();
                return false;
            }
        }
        document.tagRequestForm.skipDuplicateCheck.value="true";
    }

    var rentedVehicle=document.tagRequestForm.elements["rentOrLeasePlate"];
    if(rentedVehicle.checked){
		validatedRentedDates();       
    } else {
	    var vehOwnerShip=document.tagRequestForm.elements["ownerShipDate"];
	    if (vehOwnerShip.value == "") {
	    	vehOwnerShip.focus();
	        alert('<fmt:message key="tagInfo.ownershipDate.error.required"/>');	        
	        return false;
	    } else  { 
	    	var isDateValue = true//isDate(vehOwnerShip.value);
	    	if(!isDateValue){  
		    	alert("not a valid date "+vehOwnerShip.value);  
	    		alert('<fmt:message key="tagInfo.ownershipDate.error.required"/>');	        
	    		vehOwnerShip.focus();
	        	return false;
	    	}
	    }
    }
    return true;
}

function validatedRentedDates(){

	   var fromDate = document.tagRequestForm.elements["leasedOrRentStartDate"];
	    var toDate = document.tagRequestForm.elements["leasedOrRentEndDate"];
	  
	    if(fromDate.value == "") {
	        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.fromDate"/>"</fmt:param></fmt:message>');
	        fromDate.focus();
	        return false
	    } else if(toDate.value == "") {
	        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
	        toDate.focus();
	        return false;
	    } else if (!isDate(fromDate.value)) {
	        fromDate.focus();
	        return false;
	    } else if (!isDate(toDate.value)) {
	        toDate.focus();
	        return false;
	    } else if(compareDates(stringToDate(fromDate.value), stringToDate(toDate.value)) == 1) {
	        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="label.fromDate"/>"</fmt:param><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
	        fromDate.focus();
	        return false;
	    } else if (compareDates(stringToDate(toDate.value), new Date()) == 1) {
	        alert('<fmt:message key="errors.notgreater.current"><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
	        toDate.focus();
	        return false;
	    }
	    return true;
}

function validateTempLicense() {
    if (document.tagRequestForm.tempLicensePlate.checked){
        if (checkTempLicensePlate(document.tagRequestForm.licensePlate.value))
        {   //Rite Task 1539  kcollier do not convert temp lic plate to expire date
           //  document.tagRequestForm.tempLicensePlateExpireDate.value = convertTempLicenseToDate(document.tagRequestForm.licensePlate.value);
            return true;
        } else {
            document.tagRequestForm.licensePlate.focus();
            return false;
        }
    }
    return true;
}

function enableLeaseDates( ) {
	  if (document.tagRequestForm.rentOrLeasePlate.checked) {
	    if (checkLeaseLicensePlate(document.tagRequestForm.licensePlate.value)) {
	    	enableField( document.tagRequestForm.leasedOrRentStartDate);
	    	enableField( document.tagRequestForm.leasedOrRentEndDate);

	    	document.tagRequestForm.ownerShipDate.value="";
	        disableField(document.tagRequestForm.ownerShipDate);
	        document.getElementById("ownershipDateDiv").style.visibility="hidden";
	        return true;
	    } else {
	        document.tagRequestForm.licensePlate.focus();
	        return false;
	    }
	  } else {
		  enableField( document.tagRequestForm.ownerShipDate);
		  document.getElementById("ownershipDateDiv").style.visibility="visible";
		  
		  document.tagRequestForm.leasedOrRentStartDate.value="";
	      document.tagRequestForm.leasedOrRentEndDate.value="";
		  disableField(document.tagRequestForm.leasedOrRentStartDate);
		  disableField(document.tagRequestForm.leasedOrRentEndDate);
	      return true;
	  }
	}


function enablePlateExpirationDate( ) {
	  if (document.tagRequestForm.tempLicensePlate.checked) {
	    if (checkTempLicensePlate(document.tagRequestForm.licensePlate.value)) {
	    	enableField(document.tagRequestForm.tempLicensePlateExpireDate);
	    	document.getElementById("vinNumberLblDiv").style.visibility="visible";
	    	//enableField(document.tagRequestForm.vinNumber);
		 // Rite Task 1539 kcollier
	     //   document.tagRequestForm.tempLicensePlateExpireDate.value=convertTempLicenseToDate(document.tagRequestForm.licensePlate.value);
	        return true;
	    } else {
	        document.tagRequestForm.licensePlate.focus();
	        return false;
	    }
	  } else {
		    document.tagRequestForm.tempLicensePlateExpireDate.value="";
			disableField(document.tagRequestForm.tempLicensePlateExpireDate);
			document.getElementById("vinNumberLblDiv").style.visibility="hidden";
			//disableField(document.tagRequestForm.vinNumber);
	    
	   		return true;
	  }
	}

function enableField(obj){
	obj.disabled=false;
    obj.className=obj.className.replace("greyInputBox", "");
	
}

function disableField(obj){
	 obj.disabled=true;
     obj.className=obj.className + " greyInputBox";
  
}

function showWarning(){
    confirmOK('','<fmt:message key="label.vehicleclass.message"><fmt:param value="${appHelper.tollRateUrl}"/></fmt:message>','<fmt:message key="label.vehicleclass.header"/>','<fmt:message key="label.vehicleclass.OK"/>', null);   
}
var cal1 = new CalendarPopup(); 

if(afterConfirmAddVehicle == 'true'){

	document.tagRequestForm.licensePlate.value = "";
	document.tagRequestForm.licPlateType.value = "";
	document.tagRequestForm.ownerShipDate.value = "";
	document.tagRequestForm.vehicleYear.value = "";
	document.tagRequestForm.vehicleColor.value = "";
	document.tagRequestForm.vehicleMake.value = "";
	document.tagRequestForm.vehicleModel.value = "";
	document.tagRequestForm.vinNumber.value = "";
}

function enableSaveButton(){
		document.getElementById('saveButton').disabled = false;
}
</script>