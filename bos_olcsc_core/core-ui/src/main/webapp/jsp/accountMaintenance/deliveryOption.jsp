<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>



<html:form action="/deliveryOption.do" method="POST"  >
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
    <td class="content" width="100%">
        <html:hidden property="fulfillmentId" />
	        <p><fmt:message key="deliveryOption.note"/>
	        </p>
	        <br>       
	    	<html:radio property="deliveryOption" value="M" onclick="displayAddMPDiv(this.value)"><b><fmt:message key="deliveryOption.mail"/></b></html:radio>
	    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<html:radio property="deliveryOption" value="P" onclick="displayAddMPDiv(this.value)"><b><fmt:message key="deliveryOption.pickUp"/></b></html:radio>
	    	<br><br>
	    	<div id="mailAddDiv">
				<table cellspacing="1" class="form">
					<tr valign="top">
						<th class="field-label" width="150px"><fmt:message
								key="deliveryOption.fulfillment.createnewaddress" />
						</th>
						<td colspan="2"><input type="checkbox" name="isNewAddress"
							value="Y" id="isNewAddress"
							style="BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: none;"
							onclick="isNewAddressChanged();" /></td>
					</tr>
				</table>
				<div id="selectAddressDiv">
				<table cellspacing="1" class="form">
					<tr valign="top">
						<th class="field-label" width="150px"><fmt:message
								key="deliveryOption.fulfillment.shippingaddress" />
								<font color="red"> *</font>
						</th>
						<td colspan="2"><html:select property="selectedAddrId"
								 style="width: 350px">
									
									<html:options collection="addresses" property="addressId"
										labelProperty="label" />
								
							</html:select></td>
					</tr>
				</table>
				</div>
		    </div>
		    <div id="newAddressDiv">
					<table cellspacing="1" class="form">
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.firstname" />: <font color="red"> *</font>
							</th>
							<td><html:text property="firstName"  styleClass="text"
									style=" width:200px" />
							</td>
						</tr>
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.lastname" />: <font color="red"> *</font>
							</th>
							<td><html:text property="lastName"  styleClass="text"
									style=" width:200px" />
							</td>
						</tr>
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.address1" />: <font color="red"> *</font>
							</th>
							<td><html:text property="address1"  styleClass="text"
									style=" width:200px" maxlength="240" />
							</td>
						</tr>
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.address2" />
							</th>
							<td><html:text property="address2"  styleClass="text"
									style=" width:200px" maxlength="240" />
							</td>
						</tr>
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.city" />: <font color="red"> *</font>
							</th>
							<td><html:text property="city"  styleClass="text"
									style=" width:200px" maxlength="240" />
							</td>
						</tr>
		
						<tr>
							<th width="150px"><fmt:message
									key="deliveryOption.fulfillment.state" />: <font color="red"> *</font>
							</th>
							<td><html:select property="state" 
									style="width:60px; margin-right:6px;">
									<c:set var="temp" value="${requestScope.states}"/>          
									<html:options collection="temp" property="stateCode"
										labelProperty="stateCode" />
								</html:select> <strong><fmt:message key="deliveryOption.fulfillment.zip" />: <font color="red"> *</font>
							</strong> <html:text property="zip" style=" width:111px"  styleClass="text"
									maxlength="5" />
							</td>
						</tr>
					</table>
			</div>
	    
	    	
	      	<div id="pickupDiv">
			<table cellspacing="1" class="form">
				<tr valign="top">
					<th width="150px"><fmt:message
							key="deliveryOption.fulfillment.pickuplocation" />
							<font color="red"> *</font>
					</th>
					<td colspan="2"><html:select property="selectedLocationId"
							style="width:200px;">
									<html:option value=""><fmt:message key="label.select.location"/></html:option>
									<html:options collection="posLocations" property="pickupLocationId"
										labelProperty="posName" />
						</html:select>
					</td>
				</tr>
			</table>
		</div>    	
    <br>
    <br>
    <br>
    <br>
    </td>		
    <td class="right"></td>		
</tr>
        
<tr id="content-bottom">
    <td class="left"></td>
    <td class="content" align="center">
    	<logic:equal name="deliveryOptionForm" property="formAction" value="updateDeliveryMethod">
     	 <input type="button" value='<fmt:message key="button.save"/>' onclick="javascript: saveDeliveryMethod();"
			id="saveButton" class="button">
    	</logic:equal>
   		<logic:notEqual name="deliveryOptionForm" property="formAction" value="updateDeliveryMethod">
     	 <input type="button" value='<fmt:message key="button.checkout"/>' onclick="javascript: proceddToCheckout();"
			id="saveButton" class="button">
		</logic:notEqual>
	
    </td>		
    <td class="right"></td>		
</tr>
        
<tr>
    <td class="bottomleft"></td>
    <td class="bottomcenter"></td>
    <td class="bottomright"></td>
</tr>
</html:form>
<script language="javascript">
	<c:choose>
	<c:when test="${deliveryOptionForm.deliveryOption == 'M'}">
		displayAddMPDiv("M");
	</c:when>
	<c:otherwise>
		displayAddMPDiv("P");
	</c:otherwise>
	</c:choose>
	isNewAddressChanged();
	
	function proceddToCheckout(){
		var pickupLocationId = document.deliveryOptionForm.selectedLocationId.value;
		var actionReq = "${pageContext.request.contextPath}/deliveryOption.do?formAction=updateDeliveryForCheckout";
		
		if(window.selectedDeliveryLocationId == "P"){
			if(pickupLocationId == "" && document.getElementById('isNewAddress').value!="Y")
			{
				alert("<fmt:message key='TollTagForm.error.missingPickupLocation'/>");
				return false;
			}
			document.deliveryOptionForm.action = actionReq;
			document.deliveryOptionForm.submit();
		} else {
			// valdiate new address fields
			validateAndForward(actionReq);
		}
	}
	
	function saveDeliveryMethod(){	
		var pickupLocationId = document.deliveryOptionForm.selectedLocationId.value;
		var selectOption = window.selectedDeliveryLocationId;
		var actionReq = "${pageContext.request.contextPath}/deliveryOption.do?formAction=updateDelivery";
		
		if (selectOption == 'P'){
			if(pickupLocationId == "")
			{
				alert("<fmt:message key='TollTagForm.error.missingPickupLocation'/>");
				return false;
			}
			document.deliveryOptionForm.action = actionReq;
			document.deliveryOptionForm.submit();
			
		} else {
			// valdiate new address fields
			validateAndForward(actionReq);	
		}
	}
	
	function validateAndForward(actionReq){
		if (document.getElementById('isNewAddress').value=="Y") {
            required = validateAddress();
            if (required != "") {
               alert("The following data is required for a new address: \n" + required);
               return;
            }
            if (!isAlpha(document.deliveryOptionForm.firstName.value)){
            	var msg = "<fmt:message key='TollTagForm.firstName.mask'/>";
            	alert(msg);
            	return false;
            }
            if (!isAlpha(document.deliveryOptionForm.lastName.value)){
            	var msg = "<fmt:message key='TollTagForm.lastName.mask'/>";
            	alert(msg);
            	return false;
            }
            if (!isNumeric(document.deliveryOptionForm.zip.value) || document.deliveryOptionForm.zip.value.length<5){
            	var msg = "<fmt:message key='TollTagForm.zipcode.mask'/>";
            	alert(msg);
            	return false;
            }            
        }
		document.deliveryOptionForm.action = actionReq;
		document.deliveryOptionForm.submit();
	}

	function validateAddress() {
		 var required = "";
	     if (document.deliveryOptionForm.firstName.value == "") {
	         required = required + "First Name\n";
	     }
	     if (document.deliveryOptionForm.lastName.value == "") {
	         required = required + "Last Name\n";
	     }
	     if (document.deliveryOptionForm.address1.value == "") {
	         required = required + "<fmt:message
				key="deliveryOption.fulfillment.address1" />\n";
	     }
	     if (document.deliveryOptionForm.city.value == "") {
	         required = required + "City\n";
	     }
	     if (document.deliveryOptionForm.zip.value == "") {
	         required = required + "Zip Code\n";
	     }
	     return required;
	} 
	 
	function displayAddMPDiv(value){
		window.selectedDeliveryLocationId = value;
		if (value == "M") {
			showdiv("mailAddDiv");
	    	hidediv("pickupDiv");	    	 
			isNewAddressChanged();	
	     } else {
	    	 showdiv("pickupDiv");
	    	 hidediv("mailAddDiv");	    	 
			 hidediv("newAddressDiv");
		 }
	}

	function isNewAddressChanged() {
		 if (document.getElementById('isNewAddress').checked == true) {
				showdiv("newAddressDiv");
				hidediv("selectAddressDiv");
				document.getElementById('isNewAddress').value="Y";

		 } else {
				hidediv("newAddressDiv");
				showdiv("selectAddressDiv");
				document.getElementById('isNewAddress').value="N";
		 }
	 }

	 function hidediv(divSection) 
	 { 
	 	document.getElementById(divSection).style.display = 'none'; 
	 } 
	 function showdiv(divSection) 
	 { 
		document.getElementById(divSection).style.display = ''; 
	 } 

	 function isAlpha(value) {
		var reg = /^[A-z]+$/;
		return reg.test(value);
	 }
	 
	 function isNumeric(value) {
		 var reg = /^[0-9]*$/;
		 return reg.test(value);
	 }

</script>