<%@ include file="/jsp/common/Taglibs.jsp"%>

<jsp:useBean id="appHelper" class="com.etcc.csc.helper.AppHelper"
	scope="page" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>"></script>
<script type="text/javascript"
	src="<html:rewrite page='/js/calendarPopup.js'/>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/validation.js"></script>


<script type="text/javascript">
if(window.history.forward(1) != null)
    window.history.forward(1);

var cal1 = new CalendarPopup(); 


// This function is a imitation of GetTollTagProcessLoginInfoAction.buildAccountPlan
function alertSelectedAccountPlan(){	
	var plan = "";	
	var isTempAccount = document.getElementById("tempAccountYes").value;
	if(document.getElementById("personalAccountType").checked){
		document.getElementById("accountTypeAlert").value = plan;
		if(document.getElementById("tempAccountYes").checked){
			plan = 'ZipPass';	
		}else if(document.getElementById("tempAccountNo").checked){
			if(document.getElementById("personAirportYes").checked){
				if(document.getElementById("personHighwayYes").checked){
						plan = 'TollTag';
				}else if(document.getElementById("personHighwayNo").checked){
						plan = 'EasyZip';
					}
			}else if(document.getElementById("personAirportNo").checked){
				plan = 'EasyZip';
			}	
		}					
	}else if(document.getElementById("corpAccountType").checked){
		document.getElementById("accountTypeAlert").value = plan;
		if(document.getElementById("corpAirportYes").checked){
			if(document.getElementById("corpHighwayYes").checked){
					plan = 'TollTag';
			}else if(document.getElementById("corpHighwayNo").checked){
					plan = 'EasyZip';
				}
		}else if((document.getElementById("corpAirportNo").checked)) {
			plan = 'EasyZip';
		}
	}
	if(plan != ""){
		document.getElementById("accountTypeAlert").value = '<fmt:message key="getTollTag.accountPlanAlert" />' + " " +  plan;	
	}		
}

function getCurrentDate(){
	var currentTime = new Date();
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	document.getElementById("startDate").value = month + "/" + day + "/" + year;
}

function unCheckPerson(){
		document.getElementById("tempAccountYes").checked = false;
		document.getElementById("tempAccountNo").checked = false;
		document.getElementById("accountTypeAlert").value = "";
		tempPassUnchecked();
	}

function uncheckTempNo(){
	document.getElementById("personAirportYes").checked = false;
	document.getElementById("personAirportNo").checked = false;
	document.getElementById("personHighwayYes").checked = false;
	document.getElementById("personHighwayNo").checked = false;
	document.getElementById("accountTypeAlert").value = "";
}

function uncheckTempYes(){
	document.getElementById("startDate").value = "";
	document.getElementById("endDate").value = "";		
}

function uncheckPersonHighway(){
	document.getElementById("personHighwayYes").checked = false;
	document.getElementById("personHighwayNo").checked = false;
	document.getElementById("accountTypeAlert").value = "";
}

function unCheckCorp(){
	document.getElementById("corpAirportYes").checked = false;
	document.getElementById("corpAirportNo").checked = false;
	document.getElementById("corpHighwayYes").checked = false;
	document.getElementById("corpHighwayNo").checked = false;
	document.getElementById("accountTypeAlert").value = "";
	document.getElementById("taxId").value = "";
	document.getElementById("companyName").value = "";
}

function uncheckCorpHighway(){
	document.getElementById("corpHighwayYes").checked = false;
	document.getElementById("corpHighwayNo").checked = false;
	document.getElementById("accountTypeAlert").value = "";
}

//Defect 1251 changes start
function showLoginPage(){	
	confirmOK('<fmt:message key="acctCardRequestForm.confirmOkText"/>',buildLoginWindowText(),
			'<fmt:message key="acctCardRequestForm.loginDialog.title"/>',
			'<fmt:message key="acctCardRequestForm.loginDialog.loginButton"/>',handleConfirmOK);   
}

function buildLoginWindowText(){
    var confirmText='';    
    confirmText += '	<table width="100%" cellpadding="0" cellspacing="0" >';
    confirmText += ' 	<tr>';
    confirmText += '		<td style="font-weight:bold"><bean:message key="UserForm.userName.displayname"/>:</td>';
    confirmText += '		<td><input  style="border: 1px solid black" type="text" id="userName" name="userName" value= ""/></td>';
    confirmText += '    </tr>'; 
    confirmText += ' 	<tr>';
    confirmText += '		<td style="font-weight:bold"><bean:message key="UserForm.password.displayname"/>:</td>';
    confirmText += '		<td><input  style="border: 1px solid black" type="password" id="pwd" name="pwd" value= "" /></td>';
    confirmText += '    </tr></table>'; 
  
    return confirmText;
}   


function handleConfirmOK(){
	
	var userNameValue = document.getElementById('userName').value;
	if (userNameValue == '') {
		//user name is a required field to the auth-action
		//missing it causes a full-html error page poped up to user
		return;
	}
	var passwordValue = document.getElementById('pwd').value;
	if (passwordValue == '') {
		//password is also required, just as username
		return;
	}
	var param = 'ajax=true';
	param += '&userName=' + userNameValue;
	param += '&password=' + passwordValue;
	param += '&ajaxWithoutBR=true';
	var url = '${pageContext.request.contextPath}/AuthenticateUser.do?';
	makeAjaxCall(url, param, processLoginValidation);
	
    
}

function processLoginValidation(text){
	if("success" == text){
		var url="${pageContext.request.contextPath}/GetTollTagContactInfo.do";
		window.location=url; 
	} else {
		alert(text);
	}
}

function makeAjaxCall(url, param, callbackFunc){
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
  		xmlhttp=new XMLHttpRequest();
  	}else  {// code for IE6, IE5
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
	xmlhttp.onreadystatechange=
		function()  {
			if (xmlhttp.readyState==4 && xmlhttp.status==200)    {
  				callbackFunc(xmlhttp.responseText);
    		}
  		};
	xmlhttp.open("GET",url+param,false);
	//xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	xmlhttp.send();
}
//Defect 1251 changes end

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
				<li class="step2-here">${step} 2</li>
				<li class="step3">${step} 3</li>
				<li class="step4">${step} 4</li>
				<li class="step5">${step} 5</li>
				<li class="step6">${step} 6</li>
				<li class="step7">${step} 7</li>
			</ul>
		</div> <br class="clear">

		<h2>
			<span>${step} 2:</span>
			<fmt:message key="getTollTag.loginInfo.header" />
		</h2>
		<!-- Defect 1251 changes start -->
		<p>
			<fmt:message key="getTollTag.accountInfo.alreadyStarted" >
				<fmt:param value='<a href="#" id="showLoginPage" onclick="showLoginPage();">'/>
				<fmt:param value="</a>"/>
			</fmt:message>
		</p>
		<!-- Defect 1251 changes end -->

		<p>
			<fmt:message key="required.fields.label" />
		</p> <logic:messagesPresent message="false">
			<div id="error">
				<div class="error-img" />
				<ul>
					<html:messages id="msg" message="false">
						<li><bean:write name="msg" /></li>
					</html:messages>
				</ul>
				<br />
			</div>
		</logic:messagesPresent> <etcc-extended:autocompleteOffForm
			action="/GetTollTagProcessLoginInfo.do" method="POST">
			<%--<html:hidden property="hidden" name="useDefault"/>--%>
			<html:hidden property="submitted" styleId="submitted" />
			<table cellspacing="0" class="form">
				<tr>
					<td colspan="6">
						<h3>
							<fmt:message key="getTollTag.accountInfo.header" />
						</h3></td>
				</tr>
				
				<tr class="odd" align="left">

					<th colspan="6" style="text-align: left;"><fmt:message
							key="getTollTag.selectAccountType" />&nbsp;* <html:radio
							property="accountType" styleId="personalAccountType" value="P"
							onclick="unCheckPerson(); personSelected(); alertSelectedAccountPlan();" tabindex="1">

							<fmt:message key="getTollTag.personalAccountType" />
						</html:radio> <html:radio property="accountType" styleId="corpAccountType"
							value="C" onclick="unCheckCorp(); corpSelected(); alertSelectedAccountPlan();" tabindex="2">
							<fmt:message key="getTollTag.corporateAccountType" />
						</html:radio>
					</th>
				</tr>


					<td colspan="6">
						<div id="personalDiv" style="display: none;">
							<table>
								<tr>
									<th style="text-align: left;"><fmt:message
											key="getTollTag.tempAcQuestion" />&nbsp;* </th>
									<td><html:radio property="tempAccount"
											styleId="tempAccountYes" value="Y" onclick="uncheckTempYes(); tempPassSelected(); alertSelectedAccountPlan(); getCurrentDate()"> <fmt:message key="label.yes"/></html:radio>
										
										<html:radio property="tempAccount" styleId="tempAccountNo"
											value="N" onclick=" uncheckTempNo(); tempPassDeSelected(); alertSelectedAccountPlan();"> <fmt:message key="label.no"/></html:radio> 
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div id="tempPassDiv" style="display: none;">
											<table style="position: relative;">
												<tr style="background-color: #F1F6F7;">
													<th><fmt:message key="getTollTag.tempPassStartDate" />
													</th>
													<td>:&nbsp;<html:text property="startDate" styleId="startDate"
															styleClass="text" style="width:80px;" /> <a href="#"
														onClick="cal1.select(document.TollTagUserPreferenceForm.startDate,'anchor1','MM/dd/yyyy'); return false;"
														name="anchor1" id="anchor1"> <html:img
																page="/images/calendar.gif" border="0"
																style="WIDTH: 29px; HEIGHT: 18px" />
													</a> MM/dd/yyyy</td>
												</tr>
												<tr>
													<th><fmt:message key="getTollTag.tempPassEndDate" />
													</th>
													<td>:&nbsp;<html:text property="endDate" styleId="endDate"
															styleClass="text" style="width:80px;" /> <a href="#"
														onClick="cal1.select(document.TollTagUserPreferenceForm.endDate,'anchor2','MM/dd/yyyy');return false;"
														name="anchor2" id="anchor2"> <html:img
																page="/images/calendar.gif" border="0"
																style="WIDTH: 29px; HEIGHT: 18px" />
													</a> MM/dd/yyyy</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>

								<tr>
									<td colspan="2">

										<div id="personalAirportDiv" style="display: none;">
											<table>
												<tr style="background-color: #F1F6F7;">

													<th><fmt:message key="getTollTag.airPortParkingQuest" />&nbsp;* 
													</th>
													<td><html:radio property="personAirportRadio"
															styleId="personAirportYes" value="Y"
															onclick="uncheckPersonHighway(); personAirportSelected(); alertSelectedAccountPlan();"> <fmt:message key="label.yes"/></html:radio> <html:radio
															property="personAirportRadio" styleId="personAirportNo"
															value="N" onclick="personAirportDeSelected(); alertSelectedAccountPlan();"> <fmt:message key="label.no"/></html:radio>
													</td>
												</tr>
											</table>
										</div></td>
								</tr>

								<tr>
									<td colspan="2">
										<div id="personalHighwayDiv" style="display: none;">
											<table style="position: relative;">
												<tr style="background-color: #F1F6F7;">

													<th><fmt:message key="getTollTag.txTollRoadQuest" />&nbsp;* 
													</th>
													<td><html:radio property="personHighwayRadio" onclick="alertSelectedAccountPlan();"
															styleId="personHighwayYes" value="Y"> <fmt:message key="label.yes"/></html:radio>
														<html:radio property="personHighwayRadio" onclick="alertSelectedAccountPlan();"
															styleId="personHighwayNo" value="N"> <fmt:message key="label.no"/></html:radio> 
													</td>
												</tr>
											</table>
										</div></td>
								</tr>

							</table>
						</div></td>
				</tr>
				<tr>
					<td align="left" colspan="6">
						<div id="corpDiv" style="display: none; position: relative">
							<table style="position: relative;">
								<tr style="background-color: #F1F6F7;">
									<td style="text-align: left;"><fmt:message
											key="getTolltag.corp.taxId" />: *</td>
									<td><html:text property="taxId" styleId="taxId" styleClass="text" /></td>
								</tr>
								<tr style="background-color: #F1F6F7;">
									<td style="text-align: left;"><fmt:message
											key="getTolltag.corp.companyName" />: *</td>
									<td><html:text property="companyName" styleId="companyName" styleClass="text" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div id="corpAirportDiv" style="display: none;">
											<table>
												<tr style="background-color: #F1F6F7;">
													<th><fmt:message key="getTollTag.airPortParkingQuest" />&nbsp;*
													</th>
													<td><html:radio property="corpAirportRadio"
															styleId="corpAirportYes" value="Y"
															onclick="uncheckCorpHighway(); corpAirportSelected(); alertSelectedAccountPlan();"> <fmt:message key="label.yes"/></html:radio> <html:radio
															property="corpAirportRadio" styleId="corpAirportNo"
															value="N" onclick="corpAirportDeSelected();alertSelectedAccountPlan();"> <fmt:message key="label.no"/></html:radio>
													</td>
												</tr>
											</table>
										</div></td>
								</tr>

								<tr>
									<td colspan="2">
										<div id="corpHighwayDiv" style="display: none;">
											<table style="position: relative;">
												<tr style="background-color: #F1F6F7;">
													<th><fmt:message key="getTollTag.txTollRoadQuest" />&nbsp;*</th>
													<td><html:radio property="corpHighwayRadio" onclick="alertSelectedAccountPlan();"
															styleId="corpHighwayYes" value="Y"> <fmt:message key="label.yes"/></html:radio> <html:radio
															property="corpHighwayRadio" styleId="corpHighwayNo" onclick="alertSelectedAccountPlan();"
															value="N"> <fmt:message key="label.no"/></html:radio> 
													</td>
												</tr>
											</table>
										</div></td>
								</tr>
							</table>
						</div></td>
				</tr>
				<tr>
					<td colspan="6">
						<input type="text" size="80" id="accountTypeAlert" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td colspan="6">						
							<h3><fmt:message key="getTollTag.personInfo.header" />
						</h3></td>
				</tr>

				<tr class="odd">
					<th><fmt:message key="getTolltag.loginInfo.firstName" /> *</th>
					<td><html:text property="firstName" size="20"
							styleClass="text" maxlength="50"
							onblur="this.value=this.value.toUpperCase();" tabindex="3" />
					</td>
					<th><fmt:message key="getTolltag.loginInfo.middleInitial" />
					</th>
					<td><html:text property="middleInitial" styleClass="text"
							size="1" maxlength="1"
							onblur="this.value=this.value.toUpperCase();" tabindex="4" />
					</td>
					<th nowrap="nowrap"><fmt:message
							key="getTolltag.loginInfo.lastName" /> *</th>
					<td><html:text property="lastName" size="20" styleClass="text"
							maxlength="50" onblur="this.value=this.value.toUpperCase();"
							tabindex="5" />
					</td>
				</tr>


				<tr>
					<th><fmt:message key="getTolltag.loginInfo.email" /> *</th>
					<td colspan="5"><html:text property="email" size="60"
							styleClass="text" maxlength="100" tabindex="6" /> <!-- <input type="text" name="email" size="60" maxlength="100" value="<c:out value="${TollTagUserPreferenceForm.email}"/>" class="text" tabindex="6" autocomplete="off" onselect="javascript:unselect();"/> -->
					</td>
				</tr>

				<tr class="odd">
					<th><fmt:message key="getTolltag.loginInfo.emailVerify" /> *</th>
					<td colspan="5"><html:text property="emailVerify" size="60"
							styleClass="text" maxlength="100" tabindex="7" /> <!--  <input type="text" name="emailVerify" size="60" maxlength="100"  value="<c:out value="${TollTagUserPreferenceForm.email}"/>" class="text" tabindex="7" autocomplete="off" onselect="javascript:unselect();"/> -->
					</td>
				</tr>

				<tr>
					<th><fmt:message key="getTolltag.loginInfo.userId" /> *</th>
					<td colspan="5"><html:text property="userId" size="20"
							maxlength="15" styleClass="text"
							onblur="this.value=this.value.toUpperCase();" tabindex="8" /></td>
				</tr>

				<tr class="odd">
					<th><fmt:message key="getTolltag.loginInfo.password" /> *</th>
					<td colspan="5"><html:password property="password" size="20"
							maxlength="16" styleClass="text" tabindex="9" /></td>
				</tr>
				<tr>

					<th><bean:message key="getTolltag.loginInfo.password2" />: *</th>
					<td colspan="5"><input type="password" name="password2"
						size="30" maxlength="16" class="text"
						onselect="javascript:unselect()" tabindex="10" /> &nbsp; &nbsp;
						&nbsp; <a href="#"
						onclick="openWin('${pageContext.request.contextPath}/jsp/common/password-tips.jsp',800,500);">
							<fmt:message key="getTolltag.loginInfo.password.tips" /> </a></td>
				</tr>
				
				<logic:iterate id="securityQuestionAnswers"
					name="TollTagUserPreferenceForm" property="listOfQuestionsnAnswers"
					indexId="ctr">
					<c:if test="${(ctr+1)%2==1}">
						<tr class="odd">
					</c:if>
					<c:if test="${(ctr+1)%2==0}">
						<tr>
					</c:if>

					<th><fmt:message key="acctsecurityQuestForm.securityQuestion">
							<fmt:param value="${ctr+1}" />
						</fmt:message> <font color="black">*</font></th>
					<td colspan="5"><html:select name="securityQuestionAnswers"
							property="questionId" styleClass="text" indexed="true" tabindex="${11+(3*ctr) + 0}">
							<c:forEach var="sq"
								items="${TollTagUserPreferenceForm.listOfQuestions}">
								<option value="${sq.questionId}"
									<c:if test="${securityQuestionAnswers.questionId == sq.questionId}"> selected
                    </c:if>>${sq.transSecurityQuestion}</option>
							</c:forEach>
						</html:select></td>
					</tr>
					<c:if test="${(ctr+1)%2==1}">
						<tr>
					</c:if>
					<c:if test="${(ctr+1)%2==0}">
						<tr class="odd">
					</c:if>

					<th><bean:message
							key="acctsecurityQuestForm.securityQuestionAnswer" /> <font
						color="black">*</font>
					</th>
					<td colspan="5"><html:password name="securityQuestionAnswers"
							property="answer" size="30" styleClass="text" tabindex="${11+(3*ctr) + 1}"
							indexed="true" /></td>
					</tr>
					<c:if test="${(ctr+1)%2==1}">
						<tr class="odd">
					</c:if>
					<c:if test="${(ctr+1)%2==0}">
						<tr>
					</c:if>
					<th><bean:message
							key="acctsecurityQuestForm.securityQuestionAnswer2" /> <font
						color="black">*</font>
					</th>
					<td colspan="5"><html:password name="securityQuestionAnswers"
							property="answer2" size="30" styleClass="text" tabindex="${11+(3*ctr) + 2}"
							indexed="true" /></td>
					</tr>
					<html:hidden name="securityQuestionAnswers"
						property="securityQuestionOrder" indexed="true"></html:hidden>
				</logic:iterate>




			</table>
			<br />
			<h3>
				<fmt:message key="getTollTag.userPreferenceInfo.header" />
			</h3>

			<table cellspacing="0" class="form yes-no-table">
				<tr class="yes-no">
					<th>&nbsp;</th>
					<th><strong><fmt:message key="label.yes" />
					</strong>
					</th>
					<th><strong><fmt:message key="label.no" />
					</strong>
					</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				<logic:iterate id="userPreference" name="TollTagUserPreferenceForm"
					property="userPreferences" indexId="ctr">
					<input type="hidden" name="userPreference[${ctr}].prefId"      value="${userPreference.prefId}" />
					<input type="hidden" name="userPreference[${ctr}].prefValue"   value="${userPreference.prefValue}" />
					<input type="hidden" name="userPreference[${ctr}].prefType"    value="${userPreference.prefType}" />
					<input type="hidden" name="userPreference[${ctr}].displayDesc" value="${userPreference.displayDesc}" />
					<tr <c:if test="${ctr % 2 !=0}">class="odd"</c:if>>
						<th><c:out value="${userPreference.displayDesc}" /></th>
						<c:if test="${empty userPreference.userPrefValues}">
							<td><input tabindex="${20+(ctr*3) + 0}" type="radio" name="userPreference[${ctr}].selected" value="true" onclick="javascript: enableDevice(${ctr});"
								<c:if test="${TollTagUserPreferenceForm.useDefault}">
                                <c:if test="${userPreference.prefValue == 'Y'}">
                                    checked="checked"
                                </c:if>
								</c:if>
								<c:if test="${!TollTagUserPreferenceForm.useDefault}">
									<c:if test="${userPreference.selected}">
		                                    checked="checked"
		                                </c:if>
								</c:if>
								/>
						</td>
						<td><input type="radio" tabindex="${20+(ctr*3) + 1}"
							name="userPreference[${ctr}].selected" value="false"
							onclick="javascript: disableDevice(${ctr});"
							<c:if test="${TollTagUserPreferenceForm.useDefault}">
                                <c:if test="${userPreference.prefValue == 'N'}">
                                    checked="checked"
                                </c:if>
								</c:if> <c:if test="${!TollTagUserPreferenceForm.useDefault}">
										<c:if test="${!userPreference.selected}">
		                                    checked="checked"
		                                </c:if>
									</c:if>
							 /></td>
						</c:if>
						<c:if test="${!empty userPreference.userPrefValues}">
							<td colspan="2"><select tabindex="${20+(ctr*3) + 2}"
								name="userPreference[${ctr}].selectedValue">
									<c:forEach var="upValue"
										items="${userPreference.userPrefValues}">
										<option value='<c:out value="${upValue.upValue}"/>'
											<c:if test="${upValue.defaultFlag}">selected</c:if>>
											<c:out value="${upValue.displayUpValue}" />
										</option>

									</c:forEach>
							</select></td>
						</c:if>
						<%--
                <c:if test="${userPreference.steId == 2}">
                    <td><input type="text" name="userPreference[${ctr}].selectedDeviceValue" size="18" class="text" readonly="true"/></td>
                </c:if>
                --%>
					</tr>
				</logic:iterate>
			</table>
			<!-- table for account plan details -->
			<table cellspacing="0" class="form">

				<tr>
			</table>
			<div class="textlabel">
				<p>
					* -
					<fmt:message key="userPref.note2" />
					<br> &nbsp;&nbsp;&nbsp;&nbsp;
					<fmt:message key="userPref.note3">
						<fmt:param>
							<fmt:formatNumber value="${appHelper.notificationFee}"
								minFractionDigits="2" maxFractionDigits="2" />
						</fmt:param>
					</fmt:message>
				</p>
				<p>
					** -
					<fmt:message key="userPref.note4" />
				</p>
			</div>
			<br />
			<p>
				<a href="GetTollTagDisplayAgreement.do?fresh=false" class="prev"
					id="prevLink" onclick="return prevClicked();"><fmt:message
						key="button.previous" />
				</a> <a href="#" class="next" onclick="doSubmit();" tabindex="40"
					id="nextLink"><fmt:message key="button.next" />
				</a>
			</p>
			<html:hidden property="plan" styleId="accountPlan" />
		</etcc-extended:autocompleteOffForm></td>
	<td class="right"></td>
</tr>

<script type="text/javascript">//document.TollTagUserPreferenceForm.firstName.focus();
//document.TollTagUserPreferenceForm.useDefault.value = false;
//-->

var submitted = false;
alertSelectedAccountPlan();
if('${TollTagUserPreferenceForm.accountType}'=='P'){
	personSelected();
	if('${TollTagUserPreferenceForm.tempAccount}'=='Y'){
		tempPassSelected();
		
	}else if('${TollTagUserPreferenceForm.tempAccount}'=='N'){
		tempPassDeSelected();
		
	}
	if('${TollTagUserPreferenceForm.personAirportRadio}'=='Y'){
		personAirportSelected();
	}else if('${TollTagUserPreferenceForm.personAirportRadio}'=='N'){
		personAirportDeSelected();
	}
}else if ('${TollTagUserPreferenceForm.accountType}'=='C'){
	corpSelected();
	if('${TollTagUserPreferenceForm.corpAirportRadio}'=='Y'){
		corpAirportSelected();
	}else if('${TollTagUserPreferenceForm.corpAirportRadio}'=='N'){
		corpAirportDeSelected();
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function doSubmit()
{
	var taxVal = trim(document.getElementById("taxId").value);

	if(document.getElementById("personalAccountType").value=="C"){
		if(document.getElementById("taxId").value==""){
		alert('<fmt:message key="TollTagForm.taxId.required"/>');
		return false;
		}
	
		if(document.getElementById("taxId").value.indexOf("-")!=-1){
		alert('<fmt:message key="TollTagForm.taxId.hyphen"/>');
		return false;
		}
		
		//M1224 validate blank and digits for Tax Id
		if((taxVal.length!=9) || /\s/g.test(taxVal) || isNaN(taxVal)){
		alert('<fmt:message key="TollTagForm.taxId.length"/>');
		return false;
		}
	}
	
	document.getElementById("taxId").value = taxVal;
	
	if((document.getElementById("personalAccountType").value=="C")&&(document.getElementById("companyName").value=="")){
		alert('<fmt:message key="TollTagForm.companyName.required"/>');
		return false;
	}

	var tempAcctVal = isSelected('tempaccount');
	var tempAccountSelected = document.getElementById("tempAccount").checked
	 if((!tempAcctVal) &&(document.getElementById("personalAccountType").value=="P")){
		alert("<fmt:message key='TollTagForm.requiredQuestions.unanswered'/>");
		return false;
	} 
	 var perAirportVal = isSelected('personAirportRadio');
	 var personAirportSelected = document.getElementById("personAirportRadio").checked
	 if((!perAirportVal) &&(!tempAccountSelected) &&(document.getElementById("personalAccountType").value=="P")){
			alert("<fmt:message key='TollTagForm.requiredQuestions.unanswered'/>");
			return false;
		}
	 var personHighwayVal = isSelected('personHighwayRadio');
	 if((!personHighwayVal) &&(personAirportSelected) &&(document.getElementById("personalAccountType").value=="P")){
			alert("<fmt:message key='TollTagForm.requiredQuestions.unanswered'/>");
			return false;
		} 
	 
	 var corpAirportVal = isSelected('corpAirportRadio');
	 var corpAirportSelected = document.getElementById("corpAirportRadio").checked
	 if((!corpAirportVal) &&(document.getElementById("personalAccountType").value=="C")){
			alert("<fmt:message key='TollTagForm.requiredQuestions.unanswered'/>");
			return false;
		} 
	 var corpHighwayVal = isSelected('corpHighwayRadio');
	 if((!corpHighwayVal) &&(corpAirportSelected)&&(document.getElementById("personalAccountType").value=="C")){
			alert("<fmt:message key='TollTagForm.requiredQuestions.unanswered'/>");
			return false;
		} 
	if(document.getElementById("tempAccount").checked)
	{
		var fromDate = document.getElementById("startDate").value;
		var toDate = document.getElementById("endDate").value;		
		
		if(!validateDates())
			return false;
		 
		var daysBetween = days_between(new Date(toDate),new Date(fromDate));
		var zipPassDateRange=${ZIPPASS_DATERANGE_WARNING};
		
		if(daysBetween > zipPassDateRange)
		{
			var dayRangeAccepted = confirm('<fmt:message key="zipPass.dayRange.warning"/>' + " " + '<fmt:message key="tagInfo.licPlate.error.exist2"/>');
			if(!dayRangeAccepted){
				document.getElementById("startDate").focus();
				return false;
			}
		}		
	}
	
	document.getElementById("submitted").value='true';
	document.TollTagUserPreferenceForm.submit();
	return;
}
function isSelected(name){
	 var radio = document.getElementsByName(name);
	
	  for (i=0;i<radio.length;i++){
	    if (radio[i].checked==true){
	     return true;
	    }	   
	  }
	 return false;

}

function days_between(date1, date2) {
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24
    // Convert both dates to milliseconds
    var date1_ms = date1.getTime()
    var date2_ms = date2.getTime()
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms)

    // Convert back to days and return
    return difference_ms/ONE_DAY

}

function checkData2()
{

    var newPassword = document.TollTagUserPreferenceForm.password;
    var newPassword2 = document.TollTagUserPreferenceForm.password2;
    var userId = document.TollTagUserPreferenceForm.userId;

    if (!validateMinLength(userId,1))
    {
        alert('<fmt:message key="changePasswordForm.userId.error"/>');
        userId.focus();
        return false;
    }
    
 if (userId.value.toUpperCase() != loginId.toUpperCase())
    {
        alert("User Id is invalid.")
        userId.focus();
        return false;
    }

    
    
    if (cleanPwdField(newPassword))
    {

        
        if (newPassword.value!=newPassword2.value)
        {
            alert('<fmt:message key="changePasswordForm.newPassword.error.unmatched"/>');
            newPassword2.focus();
            return false;
        }
        
        return true;
    }
    else
    {
        newPassword.focus();
        return false;
    }
}

function prevClicked()
{
    if (submitted == false)
    {
        document.getElementById("prevLink").className="prev-disabled";
        submitted = true;
        return true;
    }
    return false;
}

function checkData()
{

    if (document.TollTagUserPreferenceForm.firstName.value == "") {

        alert("<fmt:message key='TollTagForm.firstName.required'/>");
        document.TollTagUserPreferenceForm.firstName.focus();
        return false;
    }
    else if (document.TollTagUserPreferenceForm.lastName.value == "") {
        alert("<fmt:message key='TollTagForm.lastName.required'/>");
        document.TollTagUserPreferenceForm.lastName.focus();
        return false;
    }
    else if (document.TollTagUserPreferenceForm.email.value == "") {
        alert("<fmt:message key='TollTagForm.email.required'/>");
        document.TollTagUserPreferenceForm.email.focus();
        return false;
    }
    else if (document.TollTagUserPreferenceForm.email.value != 
        document.TollTagUserPreferenceForm.emailVerify.value) {
        alert("<fmt:message key='TollTagForm.emailVerify.identical'/>");
        document.TollTagUserPreferenceForm.emailVerify.focus();
        return false;
    } 
    else if (document.TollTagUserPreferenceForm.userId.value == "") {
        alert("<fmt:message key='TollTagForm.userId.required'/>");
        document.TollTagUserPreferenceForm.userId.focus();
        return false;
    }
 
    return true;
}


function disableDevice(ctr) { 
}

function enableDevice(ctr) {
        if (document.TollTagUserPreferenceForm.email.value == "") {
            alert("<fmt:message key='TollTagForm.email.required'/>");
            var yesNo = document.TollTagUserPreferenceForm.elements["userPreference[" + ctr + "].selected"];
            if (yesNo) {
                    yesNo[0].checked = "";
                if (ctr==4)
                    yesNo[1].checked = "";
                else
                    yesNo[1].checked = "true";
            }
            document.TollTagUserPreferenceForm.email.focus();
        }
}

function personSelected()
{
document.getElementById("personalAccountType").value = "P";	
document.getElementById("corpDiv").style.display = "none";
document.getElementById("corpDiv").style.height="0px";
document.getElementById('personalDiv').style.display = "block";
}
function corpSelected(){
document.getElementById('personalAccountType').value = "C";
document.getElementById("personalDiv").style.height="0px";
document.getElementById('personalDiv').style.display = "none";
document.getElementById('corpDiv').style.display = "block";
document.getElementById('corpAirportDiv').style.display = "block";
}
 
function tempPassSelected(){
document.getElementById('personalAirportDiv').style.display = "none";
document.getElementById("personalAirportDiv").style.height="0px";
document.getElementById('personalHighwayDiv').style.display = "none";
document.getElementById("personalHighwayDiv").style.height="0px";
document.getElementById('tempPassDiv').style.display = "block";
}

function tempPassDeSelected(){
document.getElementById('tempPassDiv').style.display = "none";
document.getElementById("tempPassDiv").style.height="0px";
document.getElementById('personalAirportDiv').style.display = "block";
}

function personAirportSelected(){
document.getElementById('tempPassDiv').style.display = "none";
document.getElementById("tempPassDiv").style.height="0px";
document.getElementById('personalHighwayDiv').style.display = "block";
}

function personAirportDeSelected(){
document.getElementById('personalHighwayDiv').style.display = "none";
document.getElementById("personalHighwayDiv").style.height="0px";
}

function corpAirportSelected(){
document.getElementById('corpHighwayDiv').style.display = "block";
}

function corpAirportDeSelected(){
document.getElementById('corpHighwayDiv').style.display = "none";
document.getElementById("corpHighwayDiv").style.height="0px";
}

function tempPassUnchecked(){
	document.getElementById('personalAirportDiv').style.display = "none";
	document.getElementById("personalAirportDiv").style.height="0px";
	document.getElementById('personalHighwayDiv').style.display = "none";
	document.getElementById("personalHighwayDiv").style.height="0px";
	document.getElementById('tempPassDiv').style.display = "block";
	document.getElementById('tempPassDiv').style.display = "none";
	document.getElementById("tempPassDiv").style.height="0px";
	}
	
function validateDates()
{
	
    var fromDate = document.TollTagUserPreferenceForm.elements["startDate"];
    var toDate = document.TollTagUserPreferenceForm.elements["endDate"];
    
    if(fromDate.value == "")
    {
        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.fromDate"/>"</fmt:param></fmt:message>');
        fromDate.focus();
        return false
    }
    else if(toDate.value == "")
    {
        alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    }
   
    else if (!isDate(fromDate.value))
    {
        fromDate.focus();
        return false;
    }
    else if (!isDate(toDate.value))
    {
        toDate.focus();
        return false;
    }
    else if(compareDates(stringToDate(fromDate.value), 
            stringToDate(toDate.value)) == 1)
    {
        alert('<fmt:message key="errors.notgreater"><fmt:param>"<fmt:message key="label.fromDate"/>"</fmt:param><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
        fromDate.focus();
        return false;
    }
    /*
    M5222-Pass End Date should not be before the current Date
    */
    else if (compareDates(stringToDate(toDate.value), new Date()) == -1)
    {
        alert('<fmt:message key="errors.notbefore.current"><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
        toDate.focus();
        return false;
    }

    return true;
}

function verfiyAndSelectPlan()
{

	var plan;

	if(!((document.getElementById("personalAccountType").checked)||(document.getElementById("corpAccountType").checked)))
	{
		 alert("<fmt:message key='TollTagForm.accountType.Required'/>");
	 	return false;	
	} 
	if(document.getElementById("personalAccountType").checked){

		if(!((document.getElementById("personTempYes").checked)||(document.getElementById("personTempNo").checked))){
		
			alert("<fmt:message key='TollTagForm.accountType.makeTempChoice'/>");
	 		return false;
		}
		if(document.getElementById("personTempYes").checked)
			{
				var fromDate = document.getElementById("startDate");
				var toDate = document.getElementById("endDate");
				
				if(fromDate.value == "")
				{
					alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.fromDate"/>"</fmt:param></fmt:message>');
					fromDate.focus();
					return false
				}
				else if(toDate.value == "")
				{
					alert('<fmt:message key="errors.required"><fmt:param>"<fmt:message key="label.toDate"/>"</fmt:param></fmt:message>');
					toDate.focus();
					return false;
				}
				if(!validateDates()){
					return false;
				}
				plan='zipPass';
				return true;
		}
		if(document.getElementById("personTempNo").checked)
		{

			if(!((document.getElementById("personAirportYes").checked)||(document.getElementById("personAirportNo").checked)))
			{
				document.getElementById('personalAirportDiv').style.display = "block";
				alert("<fmt:message key='TollTagForm.accountType.makeAirportParkingChoice'/>");
				return false;
					
			}

			if(document.getElementById("personAirportNo").checked){

			plan='C';
			
			}

			if(document.getElementById("personAirportYes").checked)
			{

				if(!((document.getElementById("personHighwayYes").checked)||(document.getElementById("personHighwayNo").checked)))
				{
					document.getElementById('personalHighwayDiv').style.display = "block";
					alert("<fmt:message key='TollTagForm.accountType.makeTollWayChoice'/>");
					return false;
					
				}

				if(document.getElementById("personHighwayYes").checked){
				plan='A';
				} else {
				plan='C';
				}
			}
		
		}

		
	}

	if(document.getElementById("corpAccountType").checked){

			if(!((document.getElementById("corpAirportYes").checked)||(document.getElementById("corpAirportNo").checked)))
			{
				document.getElementById('corpAirportDiv').style.display = "block";
				alert("<fmt:message key='TollTagForm.accountType.makeAirportParkingChoice'/>");
				return false;
					
			}
			if(document.getElementById("corpAirportNo").checked){

			plan='C';
			
			}

			if(document.getElementById("corpAirportYes").checked)
			{

				if(!((document.getElementById("corpHighwayYes").checked)||(document.getElementById("corpHighwayNo").checked)))
				{
					document.getElementById('corpHighwayDiv').style.display = "block";
					alert("<fmt:message key='TollTagForm.accountType.makeTollWayChoice'/>");
					return false;
					
				}

				if(document.getElementById("corpHighwayYes").checked){
				plan='A';
				} else {
				plan='C';
				}
		
		}

	}

	document.getElementById("accountPlan").value=plan;
	return true;
}


</script>