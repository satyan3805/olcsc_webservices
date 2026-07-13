<%@page import="java.util.*,java.text.*"%>
<%@ include file="/jsp/common/Taglibs.jsp"%>

<table  style="border: 0px;">
	<tr>
		<td class="form">
		<table cellspacing="0">
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
					styleClass="text">
					<html:options collection="licPlateTypes" property="licPlateCode"
						labelProperty="plateTypeDescr" />
				</html:select></td>
				<td nowrap="nowrap"><html:text property="ownerShipDate"
					tabindex="8" styleClass="text" styleId="ownerShipDate" /> <a
					href="#"
					onClick="cal1.select(document.TollTagForm.ownerShipDate,'anchor1','MM/dd/yyyy'); return false;"
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
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table cellspacing="0" style="width: 100%"  style="border: 0px;">
			<tr class="odd-a">
				<th nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
					key="tagRequestForm.leaseOrRent" />?</th>
				<td nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left;"><html:checkbox
					tabindex="12" property="rentOrLeasePlate"
					onclick="return enableLeaseDates();" /></td>
				<th nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
					key="tagRequestForm.leaseOrRentStart" /> *</th>
				<td nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left;"><html:text
					property="leasedOrRentStartDate" tabindex="13" size="10"
					maxlength="10" styleClass="text" disabled="true" /> <a href="#"
					onClick="cal1.select(document.TollTagForm.leasedOrRentStartDate,'anchor2','MM/dd/yyyy'); return false;"
					name="anchor2" id="anchor2"> <html:img
					page="/images/calendar.gif" border="0"
					style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
				<th nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
					key="tagRequestForm.leaseOrRentEnd" /> *</th>
				<td nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left;"><html:text
					property="leasedOrRentEndDate" tabindex="13" size="10"
					maxlength="10" styleClass="text" disabled="true" /> <a href="#"
					onClick="cal1.select(document.TollTagForm.leasedOrRentEndDate,'anchor3','MM/dd/yyyy'); return false;"
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
				<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
					key="tagRequestForm.tempLicense" />?</th>
				<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><html:checkbox tabindex="12"
					property="tempLicensePlate"
					onclick="return enablePlateExpirationDate( );" /></td>
				<th nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
					key="tagRequestForm.leaseOrRentEnd" /> *</th>
				<td nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left;"><html:text
					property="tempLicensePlateExpireDate" tabindex="13" size="10"
					maxlength="10" styleClass="text" disabled="true" /> <a href="#"
					onClick="cal1.select(document.TollTagForm.tempLicensePlateExpireDate,'anchor4','MM/dd/yyyy'); return false;"
					name="anchor4" id="anchor4"><html:img
					page="/images/calendar.gif" border="2"
					style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
				<th nowrap="nowrap"
					style="background-color: #f8f5ef; text-align: left; border: 0px;"><fmt:message
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


<script language="javascript">
	
/*function doValidate(limit) {	
	var returnVal = checkData(limit);
    if (returnVal) {
		//document.TollTagForm.action="/GetTollTagSaveVehicle.do?action=validate";		
		document.TollTagForm.submit();
		return true;
    }
	return false;
}*/

if(document.getElementById('ownerShipDate').value == "") {
	document.getElementById('ownerShipDate').value = getCurrentDayAsString();
}

function showDateRangeAllowedMessage(){
	alert('<fmt:message key="tagRequestForm.warningDateRange"/>');
}

function validateTagInformation(){
	var tollTag = document.getElementById("tollTag");
    var tollTagConfirm = document.getElementById("tollTagConfirm");
    var agencyId = document.getElementById("agencyId");
    var agencyIdConfirm = document.getElementById("agencyIdConfirm");
    
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
    
    return true;
}

function validateVehicleOwnershipStartDate(){
	var startDate = document.getElementById('ownerShipDate');
	var LeasedStartDate =  document.TollTagForm.elements["leasedOrRentStartDate"];
	if(startDate != null){
		if(compareDateObjects(stringToDate(startDate.value), new Date()) == 1){
			alert("<fmt:message key='tagRequestForm.ownershipDateNotValid' />");
			return false;
		}
	}
	if(LeasedStartDate != null){
		if(compareDateObjects(stringToDate(LeasedStartDate.value), new Date()) == 1){
			alert("<fmt:message key='tagRequestForm.ownershipDateNotValid' />");
			return false;
		}
	}
	return true;
}

function checkData(limit) {
	if(!validateExistingTagInfo()){
		return false;
	}
	
	if(!validateVehicleOwnershipStartDate()){
		return false;	
	}
	
    var licPlate = document.TollTagForm.elements["licensePlate"];
    if (!validateMinLength(licPlate, 2)    
        || !validateMaxLength(licPlate, 10)) {
        alert('<fmt:message key="tagRequestForm.licensePlate.mask"/>');
        if(!licPlate.disabled){
        	licPlate.focus();
        }
        return false;
    }
    
    var vehicleYear = document.TollTagForm.elements["vehicleYear"];
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
    var vehicleColor = document.TollTagForm.elements["vehicleColor"];
    if (!validateMinLength(vehicleColor, 3)) {
        alert('<fmt:message key="tagInfo.vehicleColor.error"/>');
        vehicleColor.focus();
        return false;
    }
    var vehicleMake = document.TollTagForm.elements["vehicleMake"];
    if (!validateMinLength(vehicleMake, 2)) {
        alert('<fmt:message key="tagInfo.vehicleMake.error"/>');
        vehicleMake.focus();
        return false;
    }

    var vehicleModel = document.TollTagForm.elements["vehicleModel"];
    if (/^\s*$/.test(vehicleModel.value)) {
        alert('<fmt:message key="tagInfo.vehicleModel.error"/>');
        vehicleModel.focus();
        return false;
    }
    var temporaryLicPlate = document.TollTagForm.elements["tempLicensePlate"];
    if (temporaryLicPlate.checked) {
        var expirationDate = document.TollTagForm.elements["tempLicensePlateExpireDate"];
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
        document.TollTagForm.skipDuplicateCheck.value="true";
    }

    var rentedVehicle=document.TollTagForm.elements["rentOrLeasePlate"];
    if(rentedVehicle.checked){
		return validatedRentedDates();			
    }  else {
	    var vehOwnerShip=document.TollTagForm.elements["ownerShipDate"];
	    if (vehOwnerShip.value == "") {
	    	vehOwnerShip.focus();
	        alert('<fmt:message key="tagInfo.ownershipDate.error.required"/>');	        
	        return false;
	    } else  { 
	    	var isDateValue = isDate(vehOwnerShip.value);
	    	if(!isDateValue){  
	    		vehOwnerShip.focus();
	        	return false;
	    	}
	    }
    }
    return true;
}

function validatedRentedDates(){

	    var fromDate = document.TollTagForm.elements["leasedOrRentStartDate"];
	    var toDate = document.TollTagForm.elements["leasedOrRentEndDate"];
	    var millisecondsFromDate = new Date(fromDate.value).getTime();
	    var milliseccondsToDate = new Date(toDate.value).getTime();
	    var dayDifference = Math.floor((milliseccondsToDate - millisecondsFromDate)/1000/60/60/24);
	  
	    if(fromDate.value == "") {
	    	alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="tagRequestForm.rentalStart"/>"</fmt:param></fmt:message>');
	        fromDate.focus();
	        return false
	    } else if(toDate.value == "") {
	    	alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param></fmt:message>');
	        toDate.focus();
	        return false;
	        //Modified to fix DID 3272 - Uncommented isDate() validations
	    } else if (!isDate(fromDate.value)) {
	        fromDate.focus();
	        return false;
	    } else if (!isDate(toDate.value)) {
	        toDate.focus();
	        return false; 
	      //Modified to fix DID 3272 - ends
	    } else if(compareDateObjects(stringToDate(fromDate.value), stringToDate(toDate.value)) == 1) {
	    	alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.rentalStart"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param></fmt:message>');
	        fromDate.focus();
	        return false;
	    } else if (compareDateObjects(stringToDate(toDate.value), new Date()) == -1) {
	    	alert('<fmt:message key="errors.notbefore.current"><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param></fmt:message>');
	        toDate.focus();
	        return false;
	    } else if (dayDifference > 90) {
	    	alert('<fmt:message key="tagRequestForm.alertDateRangeOver90"/>');
	        fromDate.focus();
	        return false;
	    }	    
	    return true;
}

function validateTempLicense() {
    if (document.TollTagForm.tempLicensePlate.checked){
        if (checkTempLicensePlate(document.TollTagForm.licensePlate.value))
        {   //Rite Task 1539  kcollier do not convert temp lic plate to expire date
           //  document.TollTagForm.tempLicensePlateExpireDate.value = convertTempLicenseToDate(document.TollTagForm.licensePlate.value);
            return true;
        } else {
            document.TollTagForm.licensePlate.focus();
            return false;
        }
    }
    return true;
}

function enableLeaseDates( ) {
	    if (document.TollTagForm.rentOrLeasePlate.checked) {
		  //showDateRangeAllowedMessage(); 
	    if (checkLeaseLicensePlate(document.TollTagForm.licensePlate.value)) {
	    	enableField( document.TollTagForm.leasedOrRentStartDate);
	    	enableField( document.TollTagForm.leasedOrRentEndDate);

	    	document.TollTagForm.ownerShipDate.value="";
	        disableField(document.TollTagForm.ownerShipDate);
	        document.getElementById("ownershipDateDiv").style.visibility="hidden";
	        return true;
	    } else {
	        document.TollTagForm.licensePlate.focus();
	        return false;
	    }
	  } else {
		  enableField( document.TollTagForm.ownerShipDate);
		  document.getElementById("ownershipDateDiv").style.visibility="visible";
		  
		  document.TollTagForm.leasedOrRentStartDate.value="";
	      document.TollTagForm.leasedOrRentEndDate.value="";
		  disableField(document.TollTagForm.leasedOrRentStartDate);
		  disableField(document.TollTagForm.leasedOrRentEndDate);
	      return true;
	  }
	}


function enablePlateExpirationDate( ) {
	  if (document.TollTagForm.tempLicensePlate.checked) {
	    if (checkTempLicensePlate(document.TollTagForm.licensePlate.value)) {
	    	enableField(document.TollTagForm.tempLicensePlateExpireDate);
	    	document.getElementById("vinNumberLblDiv").style.visibility="visible";
	    	//enableField(document.TollTagForm.vinNumber);
		 // Rite Task 1539 kcollier
	     //   document.TollTagForm.tempLicensePlateExpireDate.value=convertTempLicenseToDate(document.TollTagForm.licensePlate.value);
	        return true;
	    } else {
	        document.TollTagForm.licensePlate.focus();
	        return false;
	    }
	  } else {
		    document.TollTagForm.tempLicensePlateExpireDate.value="";
			disableField(document.TollTagForm.tempLicensePlateExpireDate);
			document.getElementById("vinNumberLblDiv").style.visibility="hidden";
			//disableField(document.TollTagForm.vinNumber);
	    
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
    confirmOK('','<fmt:message key="label.vehicleclass.message"><fmt:param value="${tollRateUrl}"/></fmt:message>','<fmt:message key="label.vehicleclass.header"/>','<fmt:message key="label.vehicleclass.OK"/>', null);   
}
var cal1 = new CalendarPopup(); 
</script>