<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<jsp:useBean id="countryHelper"  class="com.etcc.csc.helper.CountryHelper" scope="page"/>
<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>

<tr>
		<td class="left"></td>
		<td class="content">
		
		<div class="steps">
			<span><fmt:message key="label.steps"/>:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-here">${step} 3</li>
				<li class="step4">${step} 4</li>
				<li class="step5">${step} 5</li>
				<li class="step6">${step} 6</li>
				<li class="step7">${step} 7</li>																					
			</ul>
		</div>
		
		<br class="clear">
		
			<h2><span>${step} 3:</span> <fmt:message key="accountForm.personalInfo.title"/></h2>

			<p><fmt:message key="required.fields.label"/></p>
                        
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


                        <etcc-extended:autocompleteOffForm action="/GetTollTagProcessContactInfo.do" method="POST">
			<table cellspacing="0" class="form">
			<tr class="odd">
			  <th><fmt:message key="accountForm.address1"/> *</th>
			  <td colspan="5">
                          <html:text property="address" size="50" styleClass="text" maxlength="100" onblur="this.value=this.value.toUpperCase();" tabindex="1"/>
                          </td>
			  </tr>
			<tr>
			  <th><fmt:message key="accountForm.address2"/></th>
			  <td colspan="5">
                            <html:text property="addressLine2" size="50" styleClass="text" maxlength="100" onblur="this.value=this.value.toUpperCase();" tabindex="2"/>
                          </td>
			  </tr>
			<tr class="odd">
				<th><fmt:message key="accountForm.city"/> *</th>
				<th class="left-align" nowrap colspan="5">
                                    <html:text property="city" size="20" styleClass="text" maxlength="20" onblur="this.value=this.value.toUpperCase();" tabindex="3"/> 
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="accountForm.state"/> *
                                    <html:select property="state" styleClass="text" tabindex="4">
                                      <html:options collection="states" property="stateCode" labelProperty="stateCode"/>
                                    </html:select>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="accountForm.zipCode"/> *
                                    <html:text property="zipcode" size="5" styleClass="text" maxlength="5" tabindex="5"  onkeyup="autotab(this, document.TollTagForm.plus4,event)"/> <html:text property="plus4" size="4" styleClass="text" maxlength="4"  tabindex="6" onkeyup="autotab(this, document.TollTagForm.homePhoneAreaCode,event)"/>
				</th>
			
				<tr>
					<th><fmt:message key="accountForm.homePhoNbr" /> *</th>
					<td colspan="5"><html:text property="homePhoneAreaCode"
							size="3" styleClass="text" maxlength="3" tabindex="7"
							onkeyup="autotab(this, document.TollTagForm.homePhoneFirst3,event)" />
						<html:text property="homePhoneFirst3" size="3" styleClass="text"
							maxlength="3" tabindex="8"
							onkeyup="autotab(this, document.TollTagForm.homePhoneLast4,event)" />
						<html:text property="homePhoneLast4" size="4" styleClass="text"
							maxlength="4" tabindex="9"
							onkeyup="autotab(this, document.TollTagForm.workPhoneAreaCode,event)" />
					</td>
				</tr>
				<tr class="odd">
                                <th>
                                    <fmt:message key="accountForm.workPhoNbr"/>
                                </th>
				<th class="left-align" nowrap colspan="5">
                                    <html:text property="workPhoneAreaCode" size="3" styleClass="text" maxlength="3" tabindex="10" onkeyup="autotab(this, document.TollTagForm.workPhoneFirst3,event)"/>
                                    <html:text property="workPhoneFirst3" size="3" styleClass="text" maxlength="3" tabindex="11" onkeyup="autotab(this, document.TollTagForm.workPhoneLast4,event)"/>
                                    <html:text property="workPhoneLast4" size="4" styleClass="text" maxlength="4" tabindex="12" onkeyup="autotab(this, document.TollTagForm.workPhoneExt,event)"/>
                                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <fmt:message key="accountForm.ext"/>.
                                    <html:text property="workPhoneExt" size="6" styleClass="text" maxlength="6"  tabindex="13" onkeyup="autotab(this, document.TollTagForm.driverLicenseNumber,event)"/>
                                </th>
			</tr>
			<tr>
                                <th>
                                    <fmt:message key="getTolltag.agreement.driverLicenseNumber"/> *
                                </th>
				<th class="left-align" nowrap colspan="5">
                                                       <html:text property="driverLicenseNumber" size="20" styleClass="text"
                                                                 maxlength="18" tabindex="17"/>
                                                       <html:hidden property="checkDuplicateDL" value="Y"/>
                                                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="getTolltag.agreement.driverLicenseState"/> *
                                                       <html:select property="driverLicenseState" styleClass="text"
                                                                   tabindex="18">
                                                            <html:options collection="dlStates" property="stateCode"
                                                                     labelProperty="stateCode"/>
                                                       </html:select>
                                                      
                                                         <b>&nbsp;&nbsp;<fmt:message key="getTolltag.agreement.driverLicenseCountry"/> *</b> 
														<html:select property="DLCountry" styleClass="text" size="1" style="width:125px"  onchange="onDlCountryChange(this);"> 
						                                     <html:options collection="country" property="countryCode"
                                                                     labelProperty="countryName"/>						                                   
						                                </html:select>		 
                                </th>
                          </tr>                        
                          <tr class="odd">
				<th><fmt:message key="getTolltag.agreement.note3"/> *</th>
				<td colspan="5" >
					<logic:empty name="TollTagForm" property="hearFrom">
						<select id="hearFrom" name="hearFrom" class="text" tabindex="20">
								<c:forEach var="itr" items="${marketSource}">
									<c:choose>
										<c:when test="${itr.msDescr == 'ACE CASH EXPRESS' }">
											<option value="${itr.msId}" selected="selected">${itr.msDescr}</option>
										</c:when>
										<c:otherwise>
											<option value="${itr.msId}">${itr.msDescr}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
					</logic:empty>
					<logic:notEmpty name="TollTagForm" property="hearFrom">
						<html:select property="hearFrom" styleClass="text" tabindex="20">
	                		<html:options collection="marketSource" property="msId" labelProperty="msDescr"/>	
	                	</html:select>
					</logic:notEmpty>
                </td>
			</tr>						
			</table>
			
<p>
  <a href="#" class="next" onclick="javascript: doSubmit()" tabindex="21" id="nextLink"><fmt:message key="button.next"/></a>
</p>
			</etcc-extended:autocompleteOffForm>


		</td>		
		<td class="right"></td>		
	</tr>

<script type="text/javascript">        
<!--

var dupDLFlag = "${requestScope.alertDuplicateDL}";
if (dupDLFlag=="Y")
{
  var result = confirm("<fmt:message key='getTolltag.agreement.driverLicense.duplicate1'/> ${sessionScope.TollTagForm.map.driverLicenseNumber} <fmt:message key='getTolltag.agreement.driverLicense.duplicate2'/>");
  if (result)
  {
    document.TollTagForm.checkDuplicateDL.value="N";
    document.TollTagForm.submit();
  }
}

document.TollTagForm.address.focus();

var submitted = false;
    function doSubmit() {
        if (validateLicNbr()) {
            if (submitted == false)
            {
            	document.getElementById("nextLink").className="next-disabled";
                submitted = true;
                document.TollTagForm.submit();
            }
        }
    }

    function validateLicNbr() {
        if (document.TollTagForm.driverLicenseState.value == "TX") {
            document.TollTagForm.driverLicenseNumber.value = document.TollTagForm.driverLicenseNumber.value.replace(/\D/g, "").replace(/\s*/g, "");
            if (document.TollTagForm.driverLicenseNumber.value.length != 8) {
                alert('<fmt:message key="getTolltag.agreement.driverLicense.error"/>');
                document.TollTagForm.driverLicenseNumber.focus();
                return false;
            }
        }
        return true;
    }

    function GetXmlHttpObject()
    { 
	      var objXMLHttp=null;
	      if (window.XMLHttpRequest) objXMLHttp=new XMLHttpRequest();
	      else if (window.ActiveXObject) objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
	      return objXMLHttp;
    }
        

    var lovReq = GetXmlHttpObject();

    function postCustomizedLovRequest(url, param, callbackFunc)
    {
        if (lovReq.readyState == 4 || lovReq.readyState == 0) {
    	lovReq.open("POST", url, false);
        lovReq.onreadystatechange = function(){if (lovReq.readyState == 4) {var dataFeed = lovDecoder(lovReq); callbackFunc(dataFeed);}};
    	
    	//lovReq.onreadystatechange=callbackFunc;    	
    	lovReq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    	lovReq.send(param);
        }
    }


    function onDlCountryChange(obj)
    {
        postCustomizedLovRequest('<%=request.getContextPath()%>/ajax_request_handler_action.do', 'sql=country_state_lov&country_code=' + obj.value, refleshDlState);
    }

    function refleshDlState(arr)
    {
    	 var elem = document.getElementById('driverLicenseState');
         refleshSelection(elem, arr);
    }

    function refleshSelection(objSelect, arr)
    {
       
        var i = 0;
        
        if(objSelect==null)
        {
            return;
        }
        objSelect.options.length = 0;
        
        if( (null==arr) || (null==arr.length) )
        {
            return;
        }
        
        for(i = 0; i<arr.length; i++)
        {
           objSelect.options[objSelect.options.length] = new Option(arr[i].value, arr[i].name); 
        }
    }

    function AjaxData(name, value)
    {
        this.name = name;
        this.value = value;
    }
    
    function lovDecoder(xmlRequest)
    {
        var dataArray = null;
        
        if (xmlRequest.readyState == 4) {
        
            var xmldoc = xmlRequest.responseXML;
            var message_nodes = xmldoc.getElementsByTagName("item");
            var n_messages = message_nodes.length
            
            if(n_messages>0)
            {
                dataArray = new Array(n_messages);
            }
            
            for (i = 0; i < n_messages; i++) {
                var name_node = message_nodes[i].getElementsByTagName("name");
                var value_node = message_nodes[i].getElementsByTagName("value");
                if(value_node[0].firstChild!=null)
                {
                    dataArray[i] = new AjaxData(name_node[0].firstChild.nodeValue, value_node[0].firstChild.nodeValue);
                }else
                {
                    dataArray[i] = new AjaxData(name_node[0].firstChild.nodeValue, '');
                }
                
            }

        }
        
        return dataArray;
    }
//-->
</script>        
