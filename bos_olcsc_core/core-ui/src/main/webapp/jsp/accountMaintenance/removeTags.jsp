<%@ include file="/jsp/common/Taglibs.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/validation.js"></script>
<c:set var="maxRows" value="${appHelper.tablePageSize}" />
<c:set var="licPlate">
	<fmt:message key="tagInfo.licPlate" />
</c:set>
<c:set var="licState">
	<fmt:message key="tagInfo.licState" />
</c:set>
<c:set var="vehicleYear">
	<fmt:message key="tagInfo.vehicleYear" />
</c:set>
<c:set var="vehicleColor">
	<fmt:message key="tagInfo.vehicleColor" />
</c:set>
<c:set var="vehicleMake">
	<fmt:message key="tagInfo.vehicleMake" />
</c:set>
<c:set var="vehicleModel">
	<fmt:message key="tagInfo.vehicleModel" />
</c:set>
<c:set var="makeSelection">
	<fmt:message key="tagInfo.makeSelection" />
</c:set>
<c:set var="tagId">
	<fmt:message key="tagInfo.tagId" />
</c:set>

<c:set var="vehicleClassDesc">
	<fmt:message key="tagInfo.vehicleClassDesc" />
</c:set>

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
	<!-- begin options (buttons, pulldowns, etc.) -->
	<!-- end options -->

	<!-- begin tabular data -->
	<td id="data" class="content">
	<html:form action="/removeTag" method="POST">
		<br>
		<h2 id="verbiage"><font color="#6e94ab"><b><fmt:message
			key="removeTag.note1" />
		<fmt:message key="removeTag.note2" /></b></font></h2><br><br>

			<logic:messagesPresent message="false">
                <div id="error">
                    <div class="error-img"/>
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
		
			
			<div id="vehicleSelectionDiv">
				<display:table
					name="tagedVehicles" pagesize="${maxRows}" id="vehicle"
					excludedParams="*" requestURI="/removeTag">
					<display:column sortable="false" title="">
						<input type="radio" name="selectedVehicle" id="selectedVehicle"
							value="${vehicle.acctTagSeq}" />
					</display:column>
					<display:column title='${tagId}' sortable="false">
						<c:out value="${vehicle.agencyId}"></c:out>.<c:out
							value="${vehicle.tagId}"></c:out>
						<input type="hidden" id="${vehicle.acctTagSeq}-tagTypeCode" name="tagTypeCode" value="${vehicle.tagTypeCode}" />							
					</display:column>
					<display:column property="licPlate" title='${licPlate}'
						sortable="false" />
					<display:column property="licState" title='${licState}'
						sortable="false" />
					<display:column property="vehicleYear" title='${vehicleYear}'
						sortable="false" />
					<display:column property="vehicleColor" title='${vehicleColor}'
						sortable="false" class="data-nowrap" />
					<display:column property="vehicleMake" title='${vehicleMake}'
						sortable="false" class="data-nowrap" />
					<display:column property="vehicleModel" title='${vehicleModel}'
						sortable="false" class="data-nowrap" />
					<display:column property="vehicleClassDesc"
						title='${vehicleClassDesc}' sortable="false" class="data-nowrap" />
				</display:table>
			</div>
			<c:if test="${tagedVehicles != null}">
				<table style="border: 0px;">
				<tr>
					<td></td>
					<td></td>
					<td><input name="submit1" id="removeBtn" type="button"
						class="button"
						value='<fmt:message key="deactivate.tagInfo.button.removeTag"/>'
						onclick="showRemoveReason();"> &nbsp;&nbsp;&nbsp; <input
						name="submit2" type="button" class="button"
						value='<fmt:message key="deactivate.tagInfo.button.Deactivate"/>'
						onclick="submitDeActivation();"></td>
				</tr>
	
				<tr>
					<td colspan="3">
					<div id="removeReasonDiv" style="display: none">
					<table class="form" style="border: 0px;">
						<tr>
							<td align="left"><fmt:message key="tagRemoveForm.selectTagRemovalReason" />
							&nbsp;&nbsp;
							<html:select property="reasonCode">
								<option value="L" selected="selected"><fmt:message key="label.tag.status.lost" /></option>
								<option value="S"><fmt:message key="label.tag.status.stolen" /></option>
								<option value="D"><fmt:message key="label.tag.status.defective" /></option>
							</html:select>
							</td>
						</tr>
						<tr />
						<tr />
						<tr>
							<td align="right"><input name="submit3" type="button" class="button"
								value='<fmt:message key="button.submit"/>'
								onclick="submitRemoval();"></td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
			</table>
		</c:if>

		<html:hidden property="tagId" styleId="tagId" />
		<html:hidden property="task" styleId="task" />
		<html:hidden property="flagType" styleId="flagType" />
	</html:form></td>
	<td class="right"></td>
</tr>

<script type="text/javascript">

function submitRemoval(){
	confirmOKCancel('','<fmt:message key="deactivate.tagInfo.warning.removal"/>','<fmt:message key="label.warning"/>','<fmt:message key="button.ok"/>','<fmt:message key="button.cancel"/>', processRemoval);
}

function processRemoval(){
	var chk=false;
  	var	radios=document.tagRemoveForm.elements["selectedVehicle"];
  	if(radios.length==undefined){
		chk = radios.checked;	
		document.getElementById("tagId").value=radios.value;	
	}else{
	 	for(var i=0; i<document.tagRemoveForm.elements["selectedVehicle"].length; i++) {
		 	if(radios[i].checked){
				chk=true;	
				document.getElementById("tagId").value=radios[i].value;
		 	}
		}
	}
	if(chk){		
		document.getElementById('task').value = "remove";
		document.getElementById('flagType').value ="R";		
		document.tagRemoveForm.submit();
	} else {
		confirmOK('','<fmt:message key="removeTag.error.exist6"/>', '','<fmt:message key="button.ok"/>');
	}
	return false;		
}

function showRemoveReason()
{
		document.getElementById('removeReasonDiv').style.display = "block";
		document.getElementById('removeBtn').disabled="disabled";
}

function submitDeActivation()
{
	confirmOKCancel('','<fmt:message key="deactivate.tagInfo.warning.deactivate"/>','<fmt:message key="label.warning"/>','<fmt:message key="button.ok"/>','<fmt:message key="button.cancel"/>', processDeactivation);
}

function processDeactivation(){
	var chk=false;
	radios=document.tagRemoveForm.elements["selectedVehicle"];
	
	if(radios.length==undefined){
		chk = radios.checked;	
		document.getElementById("tagId").value=radios.value;	
		
	}else{
	 	for(var i=0; i<document.tagRemoveForm.elements["selectedVehicle"].length; i++) {
		 	if(radios[i].checked){
				chk=true;	
				document.getElementById("tagId").value=radios[i].value;
		 	}
		}
	}
	if(chk){
		var tagTypeCode = (document.getElementById(document.getElementById("tagId").value+"-tagTypeCode").value);
		if(tagTypeCode != 'S'){
			confirmOK('','<fmt:message key="tagInfo.tagType.error"/>', '','<fmt:message key="button.ok"/>');
		}else{	
			document.getElementById('task').value = "deActivate";
			document.getElementById('flagType').value ="D";
			document.tagRemoveForm.submit();
		}
	} else {
		confirmOK('','<fmt:message key="removeTag.error.exist6"/>', '','<fmt:message key="button.ok"/>');
	}	
	return false;	
}

var acctTagSeq = '<%= request.getParameter("acctTagSeq")%>';
function init(){
    var radios=document.tagRemoveForm.elements["selectedVehicle"];
    if(radios.length === undefined) //check if user had selected an option
    {
        radios.checked = "checked";
    }
    else{
        for(var i=0; i<radios.length; i++) {
            if(radios[i].value == acctTagSeq){
                radios[i].checked = "checked";
                break;
            }
        }
    }
}
window.onload = init;

</script>


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