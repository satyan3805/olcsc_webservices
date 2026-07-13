<%@ include file="/jsp/common/Taglibs.jsp"%>
<%@ include file="/jsp/common/modalInclude.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/calendarPopup.js'/>"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dateUtil.js"></script>

<script type="text/javascript">
var cal1 = new CalendarPopup(); 
</script>
<style type="text/css">
.greyInputBox {
	background-color: #D3D3D3;
}
</style>

<script>
	var numberOfSavedVehicles = 0;
	
</script>
			
<c:set var="step">
	<fmt:message key="label.step" />
</c:set>

<tr>
	<td class="left"></td>
	<td class="content">
		<div class="steps">
			<span><fmt:message key="label.steps" />:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-taken">${step} 4</li>
				<li class="step5-here">${step} 5</li>
				<li class="step6">${step} 6</li>
				<li class="step7">${step} 7</li>
			</ul>
		</div> 
		<etcc-extended:autocompleteOffForm action="/GetTollTagSaveVehicle.do" method="POST">
			<input type="hidden" name="myAction" value=""/>
			<input type="hidden" name="editVehicleKey" value=""/>
			<html:hidden property="dmvAccepted"/>
			<html:hidden property="dmvVehicleId"/>
			<html:hidden property="tagTransferFlag"/>
			
			<br class="clear">
			<h2><font size="3.5"><b><span>${step} 5: </span><fmt:message key="getTolltag.vehicleInfo.header" /></b></font></h2>		
			
			<c:if test="${(accountLogin.minTag == 1) || 
				(accountLogin.minTag == 0 && accountLogin.maxTag != 0)}">
			<%--<c:if test="${accountPlan =='A'}">  --%> 
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
									&nbsp;<fmt:message
									key="getTolltag.vehicleInfo.tagsExists" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						<tr />
						<tr>
							<td>&nbsp;&nbsp;<html:radio styleId="tagExists"
								onclick="enableRequestFields();" property="tagExists" value="N"/>
								&nbsp;<fmt:message
									key="getTolltag.vehicleInfo.tagRequest" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						<tr />
					</table>

				 
				<%--<c:if test="${not empty sessionScope.POS_ID}">
					<input type="hidden" id="tagExists" name="tagExists" value="n" />
				</c:if> --%>
			</c:if>
			<p>
				<br class="clear">
				<fmt:message key="getTolltag.vehicleInfo.note1" />
				<br>
				<br>
				<fmt:message key="required.fields.label" />
			</p>

			<logic:messagesPresent message="false">
				<div id="error">
					<div class="error-img" />
					<ul>
						<html:messages id="msg" message="false">
							<li><bean:write name="msg" /></li>
						</html:messages>
					</ul>
					<br />
				</div>
			</logic:messagesPresent>
			<logic:messagesPresent message="true" property="tagSaveFailed">
				<div id="error">
					<div class="error-img" />
					<ul>
						<html:messages id="msg" message="true" property="tagSaveFailed">
							<li><bean:write name="msg" /></li>
						</html:messages>
					</ul>
					<br />
				</div>
			</logic:messagesPresent>

			<table cellspacing="0" class="form">
				<br/>
				<tr>
					<td>
						<div id="tagActivationDiv" style="display:none">
							<table cellspacing="0" class="form">	
								<tr class="odd-a">
									<th colspan="2"><div id="displayTagNumberHeader">
											<fmt:message key="tagRequestForm.tollTagNumber" /> *
										</div>
									</th>
									<th style="width:100px">&nbsp;</th>
									<th colspan="2"><div id="displayTagNumberConfirmHeader">
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
										<%--
					                          <td>
					                          <div id="displayTagNumberInput1" style=" display: block; font: bold;vertical-align: bottom;">.</div>
					                          </td>
					       				 --%>
										<td>
											<div id="displayTagNumberInput2" style="display: block;">
												<input type="text" id="tollTag" tabindex="2" name="tollTag"
													disabled="true" class="text" size="6" maxlength="10"
													autocomplete="off" onselect="javascript:unselect();"
													onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag3(this);" />
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
										<td>
											<div id="displayTagNumberConfirmInput2" style="display: block;">
												<input type="text" id="tollTagConfirm" tabindex="4"
													name="tollTagConfirm" disabled="true" class="text"
													size="6" maxlength="10" autocomplete="off"
													onselect="javascript:unselect();"
													onblur="cleanUserField(this);cleanAllSpaces(this);padLeadingZeroForTag3(this);"
													onBeforePaste="window.clipboardData.clearData();" />
											</div>
										</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr><td style="width:100%">
					<table cellspacing="0" class="form" border="5" style="width:100%">				
						<tr class="odd-a" style="width:100%">					
							<th><fmt:message key="tagRequestForm.licensePlate.short" /> *</th>							
							<th><fmt:message key="tagRequestForm.state" /> *</th>
							<th nowrap="nowrap"><fmt:message key="tagRequestForm.licensePlateType" /> *</th>
							<c:if test="${accountLogin.acctPlan != 'ZP'}">			
								<th nowrap="nowrap"><fmt:message key="tagRequestForm.ownershipDateStart"></fmt:message><span id="ownershipDateDiv"> *</span></th>
							</c:if>
							<th><fmt:message key="tagRequestForm.year" /> *</th>
							<th><fmt:message key="tagRequestForm.color" /> *</th>
							<th><span class="more-info" title="<fmt:message key='tagRequestForm.make.caption'/>">
								<fmt:message key="tagRequestForm.make" /></span> *</th>
							<th><span class="more-info"
								title="<fmt:message key='tagRequestForm.model.caption'/>"><fmt:message key="tagRequestForm.model" /></span> *</th>
							<th nowrap="nowrap"><fmt:message key="tagRequestForm.vehicleClass" /> *</th>
							<th width="100%"><c:choose>		
									<c:when test="${not empty editVehicleKey}">
										<fmt:message key="label.delete" />?</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose>
							</th>						
						</tr>
		
						<tr class="odd-a">						
							<td><html:text property="licensePlate" tabindex="5"
									styleId="licensePlate" disabled="true" styleClass="text" size="6"
									maxlength="15" style="width:70px"
									onblur="cleanUserField(this);cleanAllSpaces(this);return validateTempLicense();" />
								<html:hidden property="skipDuplicateCheck" />
							</td>							
							<td><html:select property="licensePlateState" tabindex="6"
									styleId="licensePlateState" disabled="true" size="1"
									styleClass="text">
									<html:options collection="states" property="stateCode"
										labelProperty="stateCode" />
								</html:select></td>
							<td><html:select property="licPlateType" tabindex="7" style="width:100px"
									disabled="true" styleId="licPlateType" size="1"
									styleClass="text">
									<html:options collection="licPlateTypes" property="licPlateCode"
										labelProperty="plateTypeDescr" />
								</html:select>
							</td>			
							<c:if test="${accountLogin.acctPlan != 'ZP'}">			
								<td nowrap="nowrap">
									<html:text property="ownerShipDate" tabindex="8"  styleClass="text" styleId="ownerShipDate" 
									style="width:80px"/> 
									<a href="#" onClick="cal1.select(document.TollTagForm.ownerShipDate,'anchor1','MM/dd/yyyy'); return false;" name="anchor1" id="anchor1"> 
										<html:img page="/images/calendar.gif" border="0" style="WIDTH:29px;HEIGHT:18px" /></a>
								</td>
							</c:if>
							<td><html:text property="vehicleYear" tabindex="9" style="width:50px"
									disabled="true" styleId="vehicleYear" size="4" maxlength="4"
									styleClass="text" onblur="cleanNumericField(this)" />
							</td>
							<td><html:text property="vehicleColor" tabindex="10"
									disabled="true" styleId="vehicleColor" size="6" maxlength="20"
									styleClass="text" onblur="cleanUserField(this)" />
							</td>
							<td><html:text property="vehicleMake" tabindex="11"
									disabled="true" styleId="vehicleMake" size="6" maxlength="30"
									styleClass="text" onblur="cleanUserField(this)" />
							</td>
							<td><html:text property="vehicleModel" tabindex="12"
									disabled="true" styleId="vehicleModel" size="6" maxlength="30"
									styleClass="text"
									onblur="this.value = this.value.toUpperCase().replace(/^\s*|\s*$/g, '');" />
							</td>
							<td><html:select property="vehicleClassCode" tabindex="13"
									disabled="true" styleId="vehicleClassCode" size="1"
									onchange="javascript: showWarning();" styleClass="text" style="width:100%">
									<html:options collection="vehicleClasses"
										property="vehicleClassCode" labelProperty="vehicleClassDescr" />
								</html:select></td>
							<td>
								<c:choose>
									<c:when test="${not empty editVehicleKey}">
										<html:checkbox property="deleteVehicle" />										
									</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose>
							</td>
						</tr>	
					</table>
				</td></tr>
				<tr><td>
					<table cellspacing="0" class="form" style="width:100%">
						<c:if test="${accountLogin.acctPlan == 'ZP'}">	
						<tr class="odd-a" >
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;" colspan="2"><fmt:message key="tagRequestForm.vehStartEndDates" /></th>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRentStart" /><span id="vehicleStartDateDiv"> *</span></th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:text property="vehicleStartDate" tabindex="13" size="10" maxlength="10" styleClass="text"/> 
								<a href="#" onClick="cal1.select(document.TollTagForm.vehicleStartDate,'anchor2','MM/dd/yyyy'); return false;"
								name="anchor2" id="anchor2"> <html:img page="/images/calendar.gif" border="0" style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRentEnd" /><span id="vehicleEndDateDiv"> *</span></th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:text property="vehicleEndDate" tabindex="13" size="10" maxlength="10" styleClass="text" /> 
								<a href="#" onClick="cal1.select(document.TollTagForm.vehicleEndDate,'anchor3','MM/dd/yyyy'); return false;"
								name="anchor3" id="anchor3"> <html:img page="/images/calendar.gif" border="0" style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<td width="100%" style="background-color: #f8f5ef; text-align: left;">&nbsp;</td>							
						</tr>
						<tr><td colspan="6">&nbsp;</td></tr>
						</c:if>
						<tr class="odd-a" >
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRent" />?</th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:checkbox tabindex="12" property="rentOrLeasePlate" onclick="return enableLeaseDates();" /></td>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRentStart" /> *</th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:text property="leasedOrRentStartDate" tabindex="13" size="10" maxlength="10" styleClass="text" disabled="true" /> 
								<a href="#" onClick="cal1.select(document.TollTagForm.leasedOrRentStartDate,'anchor4','MM/dd/yyyy'); return false;"
								name="anchor4" id="anchor4"> <html:img page="/images/calendar.gif" border="0" style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRentEnd" /> *</th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:text property="leasedOrRentEndDate" tabindex="13" size="10" maxlength="10" styleClass="text" disabled="true" /> 
								<a href="#" onClick="cal1.select(document.TollTagForm.leasedOrRentEndDate,'anchor5','MM/dd/yyyy'); return false;"
								name="anchor5" id="anchor5"> <html:img page="/images/calendar.gif" border="0" style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<td width="100%" style="background-color: #f8f5ef; text-align: left;">&nbsp;</td>
						</tr>
						<tr><td colspan="6">&nbsp;</td></tr>
						<tr class="odd-a">
							<th nowrap="nowrap" ><fmt:message key="tagRequestForm.tempLicense" />?</th>
							<td nowrap="nowrap" ><html:checkbox tabindex="12" property="tempLicensePlate"
									onclick="return enablePlateExpirationDate( );" />
							</td>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.leaseOrRentEnd" /> *</th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;">
								<html:text property="tempLicensePlateExpireDate" tabindex="13" size="10" maxlength="10" styleClass="text" disabled="true" /> 
								<a href="#" onClick="cal1.select(document.TollTagForm.tempLicensePlateExpireDate,'anchor6','MM/dd/yyyy'); return false;"
									name="anchor6" id="anchor6"><html:img page="/images/calendar.gif" border="2" style="WIDTH: 29px; HEIGHT: 18px" /></a></td>
							<th nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><fmt:message key="tagRequestForm.vinNumber"></fmt:message><span id="vinNumberLblDiv"> *</span></th>
							<td nowrap="nowrap" style="background-color: #f8f5ef; text-align: left;"><html:text property="vinNumber" styleClass="text"/></td>
							<td width="100%" style="background-color: #f8f5ef; text-align: left;">&nbsp;</td>
							
						</tr>
					</table>
				</td></tr>
				<tr>		
					<td>
						<input type="button" class="button"
							value='<fmt:message key="button.save" />' id="addButton"
							onclick="return doValidate(${sessionScope.limit});"  />
						<input type="button" class="button"
							value='<fmt:message key="button.clear" />' id="clearButton"
							onclick="return clearFields();"  />
					</td>
									
				</tr>
			</table>
		</etcc-extended:autocompleteOffForm> 
				
		<br />
		<br />
		<br />
		<br />
		
		<table cellspacing="0" class="form">
		<c:if test="${not empty sessionScope.savedVehicles}">
			<tr class="odd-a">				
				<th valign="top"><fmt:message key="tagRequestForm.licensePlate.short" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.tollTagId" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.state" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.licensePlateType" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.year" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.color" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.make" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.model" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.vehicleClass" />
				</th>
				<th valign="top"><fmt:message key="tagRequestForm.rentalVehicle" />
				</th>
				<%--<th><fmt:message key="tagRequestForm.rentalStart" />
				</th>
				<th><fmt:message key="tagRequestForm.rentalEnd" />
				</th>--%>
				<th valign="top"><fmt:message key="getTolltag.vehicleInfo.temporaryLicNoBr" />
				</th>
				
			</tr>
			</c:if>
			
			<c:forEach items="${sessionScope.savedVehicles}" var="savedVehicle" varStatus="varStatus">
				<script>
				numberOfSavedVehicles = numberOfSavedVehicles + 1;
				</script>
				<TR>					
					<TD><a href="GetTollTagSaveVehicleNoValidate.do?myAction=editVehicle&editVehicleKey=<c:out value="${savedVehicle.key}"/>">
						<c:out value="${savedVehicle.value.licPlate}" /> 
						</a>
					</TD>
					<td><c:choose>							
							<c:when test="${savedVehicle.value.tagExists == 'Y'}">
								<c:out value="${savedVehicle.value.agencyId}" />.<c:out value="${savedVehicle.value.tagId}" />
							</c:when>
							<c:when test="${savedVehicle.value.tagExists == 'N'}">
								<fmt:message key="TollTagForm.vehicleInfo.tagPending" />
							</c:when>
							<c:otherwise>
								<fmt:message key="TollTagForm.vehicleInfo.tagNotApplicable" />
							</c:otherwise>
						</c:choose>
					</td>
					<TD><c:out value="${savedVehicle.value.licState}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.plateTypeDesc}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.vehicleYear}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.vehicleColor}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.vehicleMake}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.vehicleModel}" />
					</TD>
					<TD><c:out value="${savedVehicle.value.vehicleClassDesc}" />
					</TD>
					<td><c:choose>
							<c:when test="${savedVehicle.value.rentalVehicle}">
								<fmt:message key="label.yes.short" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no.short" />
							</c:otherwise>
						</c:choose>
					</td>
					<%--<td><fmt:formatDate
							value="${savedVehicle.value.rentalStart.time}"
							pattern="MM/dd/yyyy" />
					</td>
					<td><fmt:formatDate
							value="${savedVehicle.value.rentalEnd.time}" pattern="MM/dd/yyyy" />
					</td>--%>
					<td><c:choose>
							<c:when test="${savedVehicle.value.temporaryLicPlate}">
								<fmt:message key="label.yes.short" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no.short" />
							</c:otherwise>
						</c:choose>
					</td>
					<%--<td><fmt:formatDate
							value="${savedVehicle.value.plateExpirDate.time}"
							pattern="MM/dd/yyyy" />
					</td> --%>			
				</TR>
			</c:forEach>
		</table>
		<p>
			<a href="GetTollTagPaymentInfo.do" class="prev" id="prevLink"
				onclick="return prevClicked();"><fmt:message
					key="button.previous" />
			</a>


			<c:if test="${not empty sessionScope.savedVehicles }">
				<a href="GetTollTagSaveVehicleNoValidate.do?action=next" class="next"
					onclick="return doSubmit();" id="nextLink"><fmt:message
						key="button.next" />
				</a>
			</c:if>
		</p> <!-- commented the following to get rid of account card funcationality temporarily

             <c:if test="${not empty sessionScope.savedVehicles}">
                        <a href="acctCardRequest.do?forward=requestcard" class="next" onclick="return doSubmit();" id="nextLink"><fmt:message key="button.next"/></a>
                        </c:if>
                        </p>
		

		</td>		-->
	<td class="right"></td>
</tr>


<script type="text/javascript">
	
	var planCode = '<c:out value="${accountLogin.acctPlan}" />';
	var planStartDate = '<c:out value="${accountLogin.acctStartDate}" />';
	var planEndDate = '<c:out value="${accountLogin.acctEndDate}" />';
	
	var tagRequired = <c:out value="${accountLogin.minTag == 1}" />;
	var tagProhibited = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag == 0}" />;
	var tagOptional = <c:out value="${accountLogin.minTag == 0 && accountLogin.maxTag < 0}" />;
	
	var minVehicle =<c:out value="${accountLogin.minVehicle}" />;
	var maxVehicle =<c:out value="${accountLogin.maxVehicle}" />;

    var cnt = 0;
    var noMatch = false;
    var posFlag = "${sessionScope.POS_ID}";
    var submitted = false;
    
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
	
	var actionCompleted = "${requestScope.actionCompleted}";
	var statusMessage = "${requestScope.statusMessage}";
	var licPlateExistsOnVehicle="${requestScope.licPlateExistsOnVehicle}";
	
    YAHOO.util.Event.onDOMReady(init);

    function init(){
	    if( posFlag != '' ||${sessionScope.accountLogin.minTag>=0}) {<!-- TODO change to mintag>0 -->
	       enableRequestFields();
	    }
	    
	    enableTagFields();
	    enableLeaseDates();
	    enablePlateExpirationDate( );
	    document.TollTagForm.licensePlate.focus();
	    
	    if(actionCompleted == "N"){
	    	alert(statusMessage);
	    }
	    if(licPlateExistsOnVehicle == 'Y'){
	    	alert("<fmt:message key='vehicleInfo.licPlate.exist' />");
	    	return false;
	    }
	    if(validationPassed == "Y"){
	    	var result = confirmOverlapAndAdd();
	    	if (result)
	    	{
	    		confirmTagOverlapAndAdd();
	    	}
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
	    
	    var rentedVehicle=document.TollTagForm.elements["rentOrLeasePlate"];
	    if(rentedVehicle.checked){
	    	 document.getElementById('ownerShipDate').value = "";
	    } else if(document.getElementById('ownerShipDate').value == "") {
	    	document.getElementById('ownerShipDate').value = getCurrentDayAsString();
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
			    			return confirmDmvAndAdd();
	    				}
	    			} else {
		    			document.TollTagForm.tagTransferFlag.value="Y";
		    			return confirmDmvAndAdd();
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
	    		var transactionMessage = "<fmt:message key='tagInfo.licPlate.error.exist5'><fmt:param>${requestScope.transactionAmount}</fmt:param></fmt:message>";	        	
				if(transactionChargesPresent == "Y"){
		    		var acceptedTransactionAmt = confirm(vehicleOverlap + "\n\n"  + transactionMessage + "\n\n"  + confirmAdd);
		    		if(acceptedTransactionAmt){
		    			return confirmTagOverlapAndAdd();    		    			
		    		}
		    		return false;
				} else {		
					var acceptedOverlap = confirm(vehicleOverlap + "\n\n"  + confirmAdd);
		    		if(acceptedOverlap){
		    			return confirmTagOverlapAndAdd();    		    			
		    		}
		    		return false;
				}
    		}
    	} else if(numberOfVehicles == "0"){
			return confirmTagOverlapAndAdd();    	
		}
    	return true;
    }
    
    function confirmTagOverlapAndAdd(){
    	if(numberOfTags == "0" || tagProhibited){
    		return confirmDmvAndAdd();
    	} else if(numberOfTags == "1"){
    		var tagOverlap = "<fmt:message key='tagInfo.tag.error.exist1'/>";
    		var confirmAddTag = "<fmt:message key='tagInfo.licPlate.error.exist2'/>";
    		var tagTransactionMessage = "<fmt:message key='tagInfo.tag.error.exist5'><fmt:param>${requestScope.tagTransactionAmount}</fmt:param></fmt:message>"
        	
   			if(tagTransactionChargesPresent == "Y"){
   	    		var acceptedTagTransactionAmt = confirm(tagOverlap + "\n\n"  + tagTransactionMessage + "\n\n"  + confirmAddTag);
   	    		if(acceptedTagTransactionAmt){
   	    			document.TollTagForm.tagTransferFlag.value="T";
   	    			return confirmDmvAndAdd();    		    			
   	    		}
   			}else {		
   				var acceptedTagOverlap = confirm(tagOverlap + "\n\n"  + confirmAddTag);
   	    		if(acceptedTagOverlap){
   	    			document.TollTagForm.tagTransferFlag.value = "T";
   	    			return confirmDmvAndAdd();    		    			
   	    		}
   			}
    	} else if(numberOfTags != "0" && numberOfTags != "1"){
    		alert("<fmt:message key='tagInfo.tag.error.exist4'><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>");    		
    	}
    	return false;
    }
    
    function confirmDmvAndAdd(){
    	if(dmvInfoPresent == "Y"){
    		var dmvMsg1 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo1"/>';
    		var dmvMsg2 = '<fmt:message key="tagInfo.licPlate.error.dmvInfo2"/>';
    		var dmvAccepted = confirm(dmvMsg1 + "\n\n"  + vehMake  + " " +  vehModel + " " + vehYear + " " + vehVin + "\n\n"  + dmvMsg2);
    		if(dmvAccepted){
    			updateDmvInfo();
    		} else {
    			document.TollTagForm.dmvAccepted.value="false";
    		}
    	}
    	addVehicleWithoutValidation();
    	return false;
    }
    
    function enableTagFields(){    
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
    
    function enableRequestFields() {
        try {
        	document.getElementById('tagActivationDiv').style.display ='none';
          	
            document.getElementById("agencyId").disabled = false;
            document.getElementById("tollTag").disabled = false;
            document.getElementById("tollTagConfirm").disabled = false;
            document.getElementById("agencyIdConfirm").disabled = false;
            
            //M1243 - Fix error request tag
            document.getElementById("tollTag").value = '';
            document.getElementById("tollTagConfirm").value = '';
            
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
    
    function clearClipBoard(){
        clearData(clipboardData);        
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
	
	document.TollTagForm.dmvAccepted.value="true";
	document.TollTagForm.dmvVehicleId.value=vehDmvId;
	
	//document.elementById("addButton").disabled=true;
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

function enableLeaseDates( ) {
  if (document.TollTagForm.rentOrLeasePlate.checked) {
	//showDateRangeAllowedMessage();
    if (checkLeaseLicensePlate(document.TollTagForm.licensePlate.value)) {
    	enableField( document.TollTagForm.leasedOrRentStartDate);
    	enableField( document.TollTagForm.leasedOrRentEndDate);
		
    	if(planCode == 'ZP'){
    		if(document.TollTagForm.vehicleStartDate){
	    		document.TollTagForm.vehicleStartDate.value="";
	        	disableField(document.TollTagForm.vehicleStartDate);	
	        	document.getElementById("vehicleStartDateDiv").style.visibility="hidden";
	    	}
    		if(document.TollTagForm.vehicleEndDate){
    			document.TollTagForm.vehicleEndDate.value="";
	        	disableField(document.TollTagForm.vehicleEndDate);	        	
	        	document.getElementById("vehicleEndDateDiv").style.visibility="hidden";
	    	}
    	} else if(document.TollTagForm.ownerShipDate){
    		document.TollTagForm.ownerShipDate.value="";
        	disableField(document.TollTagForm.ownerShipDate);
        	document.getElementById("ownershipDateDiv").style.visibility="hidden";
    	}
        return true;
    } else {
        document.TollTagForm.licensePlate.focus();
        return false;
    }
  } else {
	  if(planCode == 'ZP'){
		  enableField( document.TollTagForm.vehicleStartDate);
		  enableField( document.TollTagForm.vehicleEndDate);
		  
      	  document.getElementById("vehicleStartDateDiv").style.visibility="hidden";
      	  document.getElementById("vehicleEndDateDiv").style.visibility="hidden";
      	  
	  } else if(document.TollTagForm.ownerShipDate){
		  	enableField( document.TollTagForm.ownerShipDate);
		  	document.getElementById("ownershipDateDiv").style.visibility="visible";
	  }
	  
	  document.TollTagForm.leasedOrRentStartDate.value="";
      document.TollTagForm.leasedOrRentEndDate.value="";
	  disableField(document.TollTagForm.leasedOrRentStartDate);
	  disableField(document.TollTagForm.leasedOrRentEndDate);
      return true;
  }
}

function enableField(obj){
	if(obj){
		obj.disabled=false;
    	obj.className=obj.className.replace("greyInputBox", "");
	}
	
}

function disableField(obj){
	 if(obj){
	 	obj.disabled=true;
     	obj.className=obj.className + " greyInputBox";
	 }
  
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

function doSave(limit) {
    if (checkData(limit)) {
        document.getElementById("saveButton").disabled = true;
        document.TollTagForm.submit();
        return true;
    }
    return false;
}

function validateVehicleOwnershipStartDate(){
	var startDate = document.getElementById('ownerShipDate');
	if(startDate != null){
		if(compareDateObjects(stringToDate(startDate.value), new Date()) == 1){
			alert("<fmt:message key='tagRequestForm.ownershipDateNotValid' />");
			return false;
		}
	}
	return true;
}

function doValidate(limit) {	
	var returnVal = checkData(limit);
	if(!validateVehicleOwnershipStartDate()){
		return false;	
	}
    if (returnVal) {
		<c:if test="${not empty editVehicleKey }">
			if(document.TollTagForm.deleteVehicle.checked){				
				document.TollTagForm.action="GetTollTagSaveVehicle.do?myAction=deleteVehicle";
			} else {
				document.TollTagForm.action="GetTollTagSaveVehicle.do?myAction=saveVehicle";
			}
		</c:if>
		<c:if test="${empty editVehicleKey }">
			document.TollTagForm.action="GetTollTagSaveVehicle.do?myAction=saveVehicle";
		</c:if>
	
		document.TollTagForm.submit();
		return true;
    }
	return false;
}

function addVehicleWithoutValidation(){
	if(showMessages == "Y"){
		<c:if test="${empty editVehicleKey }">
			document.TollTagForm.action="GetTollTagSaveVehicleNoValidate.do?myAction=confirmAddVehicle";
		</c:if>
		<c:if test="${not empty editVehicleKey }">
			document.TollTagForm.action="GetTollTagSaveVehicleNoValidate.do?myAction=confirmEditVehicle";
		</c:if>
		
		if(document.TollTagForm.dmvAccepted.value == "true"){
			document.TollTagForm.vehicleMake.disabled = false;
			document.TollTagForm.vehicleMake.readonly = true;
		
			document.TollTagForm.vehicleModel.disabled = false;
			document.TollTagForm.vehicleModel.readonly = true;
			
			document.TollTagForm.vehicleYear.disabled = false;
			document.TollTagForm.vehicleYear.readonly = true;
			
			document.TollTagForm.vinNumber.disabled = false;
			document.TollTagForm.vinNumber.readonly = true;
		}
		
		document.TollTagForm.submit();
	}
}


function checkData(limit) {
	<c:if test="${empty editVehicleKey }">
		if(maxVehicle > 0 && numberOfSavedVehicles >= maxVehicle){
			alert('<fmt:message key="tagInfo.tollTagMaxVehicles.error"><fmt:param>${accountLogin.maxVehicle}</fmt:param></fmt:message>');
			return false;
		}
	</c:if>
	
    var tollTag = document.getElementById("tollTag");
    var tollTagConfirm = document.getElementById("tollTagConfirm");
    var agencyId = document.getElementById("agencyId");
    var agencyIdConfirm = document.getElementById("agencyIdConfirm");
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
	}
	
	if(tagRequired  && !tagOptionSelected){
		alert('<fmt:message key="tagInfo.tollTagChoiceEmpty.error"/>');
		return false;
	}
    if(currentlyHaveTollTag && document.TollTagForm.tagExists){
        if(tollTag.value == "") {
            alert('<fmt:message key="tagInfo.tollTagEmpty.error"/>');    
            tollTag.focus();
            cnt++;
            noMatch = true;
            return false;
        }
       
        if(tollTagConfirm.value == "") {
            alert('<fmt:message key="tagInfo.tollTagEmpty.error"/>');    
            tollTagConfirm.focus();
            cnt++;
            noMatch = true;
            return false;
        }    
       
        if(tollTag.value != tollTagConfirm.value){
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
        
        if(agencyId.value == "") {
            alert('<fmt:message key="tagInfo.agencyIdEmpty.error"/>');    
            agencyId.focus();
            cnt++;
            noMatch = true;
            return false;
        }
        
        if(agencyIdConfirm.value == "") {
            alert('<fmt:message key="tagInfo.agencyIdEmpty.error"/>');    
            agencyIdConfirm.focus();
            cnt++;
            noMatch = true;
            return false;
        }    
        
        if(agencyId.value != agencyIdConfirm.value){
            if(cnt < 3){
                alert('<fmt:message key="tagInfo.agencyIdMismatch.error"/>');
                agencyId.focus();
                cnt++;
                noMatch = true;
                return false;
            }
        }        
     }
    

    var licPlate = document.TollTagForm.elements["licensePlate"];
    if (!validateMinLength(licPlate, 2)    
        || !validateMaxLength(licPlate, 10)) {
        
        alert('<fmt:message key="tagRequestForm.licensePlate.mask"/>');
        licPlate.focus();
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
 			var dtExpiration = stringToDate(expirationDate.value);
 			var dtCurrent =  new Date();
           
            if (compareDateObjects(stringToDate(expirationDate.value), new Date()) != 1) {
            	//Expiration date must be after current date
                alert('<fmt:message key="tagInfo.expirationDate.error.greater"/>');
                expirationDate.focus();
                return false;
            }
            else if (compareDateObjects(stringToDate(expirationDate.value), maxDate) == 1){
            	//Expiration date must be before date
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
    } else if(planCode == 'ZP'){
		return validatedVehicleDatesForZp();       
	} else {
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

function prevClicked() { 
  //  if (submitted == false)
   // {
        document.getElementById("prevLink").className="prev-disabled";
        submitted = true;
        return true;
    //}
    //return false;
}

function validatedVehicleDatesForZp(){

    var fromDate = document.TollTagForm.elements["vehicleStartDate"];
    var toDate = document.TollTagForm.elements["vehicleEndDate"];
  	  	
    if(fromDate.value == "") {
        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="tagRequestForm.vehStartDate"/>"</fmt:param></fmt:message>');
        fromDate.focus();
        return false
    } else if(toDate.value == "") {
        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="tagRequestForm.vehEndDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } else if (!isDate(fromDate.value)) {
        fromDate.focus();
        return false;
    } else if (!isDate(toDate.value)) {
        toDate.focus();
        return false;
    } else if(compareDateObjects(stringToDate(fromDate.value), stringToDate(toDate.value)) == 1) {    	
    	//if from date is greater than to date, error
        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.vehStartDate"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.vehEndDate"/>"</fmt:param></fmt:message>');
        fromDate.focus();
        return false;
        //M1260 - FromDate must not before current date
    } else if (compareDateObjects(stringToDate(fromDate.value), new Date()) == -1) {
        alert('<fmt:message key="errors.notbefore.current"><fmt:param>"<fmt:message key="tagRequestForm.vehStartDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } 
    
    /*else if (compareDateObjects(stringToDate(fromDate.value), stringToDate(planStartDate)) == -1) {
    	//if from date is before account start date, error
        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.vehStartDate"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctStartDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } else if (compareDateObjects(stringToDate(toDate.value), stringToDate(planStartDate)) == -1) {
    	//if to date is before account start date, error
        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.vehEndDate"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } else if (compareDateObjects(stringToDate(planEndDate), stringToDate(fromDate.value)) == -1) {
    	//if from date is after account end date, error
        alert('<fmt:message key="errors.notlesser"><fmt:param>"<fmt:message key="tagRequestForm.vehStartDate"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } else if (compareDateObjects(stringToDate(planEndDate), stringToDate(toDate.value)) == -1) {
    	//if to date is after account end date, error
        alert('<fmt:message key="errors.notlesser"><fmt:param>"<fmt:message key="tagRequestForm.vehEndDate"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    } else {
    	return true;
    }*/
    
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
	    } else if (!isDate(fromDate.value)) {
	        fromDate.focus();
	        return false;
	    } else if (!isDate(toDate.value)) {
	        toDate.focus();
	        return false;
	    } else if(compareDateObjects(stringToDate(fromDate.value), stringToDate(toDate.value)) == 1) {
	    	//if from date is greater than to date, error
	        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.rentalStart"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param></fmt:message>');
	        fromDate.focus();
	        return false;
	    } else if (compareDateObjects(stringToDate(toDate.value), new Date()) == -1) {
	    	//if to date is before current date, error
	        alert('<fmt:message key="errors.notbefore.current"><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param></fmt:message>');
	        toDate.focus();
	        return false;
	    } else if (dayDifference > 90) {
	    	alert('<fmt:message key="tagRequestForm.alertDateRangeOver90"/>');
	        fromDate.focus();
	        return false;
	    }
	    
	    /*else if(planCode == 'ZP'){
	    	if (compareDateObjects(stringToDate(fromDate.value), stringToDate(planStartDate)) == -1) {
	        	//if from date is before account start date, error
	            alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.rentalStart"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctStartDate"/>"</fmt:param></fmt:message>');
	            toDate.focus();
	            return false;
	        } else if (compareDateObjects(stringToDate(toDate.value), stringToDate(planStartDate)) == -1) {
	        	//if to date is before account start date, error
	            alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
	            toDate.focus();
	            return false;
	        } else if (compareDateObjects(stringToDate(planEndDate), stringToDate(fromDate.value)) == -1) {
	        	//if from date is after account end date, error
	            alert('<fmt:message key="errors.notlesser"><fmt:param>"<fmt:message key="tagRequestForm.rentalStart"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
	            toDate.focus();
	            return false;
	        } else if (compareDateObjects(stringToDate(planEndDate), stringToDate(toDate.value)) == -1) {
	        	//if to date is after account end date, error
	            alert('<fmt:message key="errors.notlesser"><fmt:param>"<fmt:message key="tagRequestForm.rentalEnd"/>"</fmt:param><fmt:param>"<fmt:message key="tagRequestForm.acctEndDate"/>"</fmt:param></fmt:message>');
	            toDate.focus();
	            return false;
	        } else {
	        	return true;
	        }
	    	return true;
	    }*/
	    return true;
}

function showDateRangeAllowedMessage(){
	alert('<fmt:message key="tagRequestForm.warningDateRange"/>');
}

function doSubmit() {
    if (submitted == false) {
        document.getElementById("nextLink").className="next-disabled";
        submitted = true;
        document.TollTagForm.submit();
    }
}

function showWarning(){
    confirmOK('','<fmt:message key="label.vehicleclass.message"><fmt:param value="${tollRateUrl}"/></fmt:message>','<fmt:message key="label.vehicleclass.header"/>','<fmt:message key="label.vehicleclass.OK"/>', null);   
}

function editVehicle(key){
	document.TollTagForm.myAction.value="editVehicle";
	document.TollTagForm.editVehicleKey.value="editVehicle";
	document.TollTagForm.submit();
}

function clearFields(){
	document.TollTagForm.action="GetTollTagSaveVehicleNoValidate.do?myAction=clearVehicle";
	document.TollTagForm.submit();
}

//after the ui is pushed use the padLeadingZeroForTag()
function padLeadingZeroForTag3(ctl) {
//	alert("The input is "+ ctl.value);
	    var s = ctl.value+""; 
	    while (s.length < 8) s = "0" + s; 
	    ctl.value=s;		
    return true;
}

</script>
