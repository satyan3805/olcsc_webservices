<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>	
        <div id="content-container">

		<div id="content">

			<input type="hidden" name="fromConfirmation" value="true"/>

				<h1 id="vehicle-and-payment">Are the Vehicles and Payment amounts correct?</h1>				
				<div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <dt/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <dt/>
                                        <html:messages id="msg" message="true" property="saveFailed">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>                              
                             <p>Please review everything to make sure it is accurate. Once you have verified all the items below are correct, press "Check Out" to process your order.</p>
                             <c:if test="${billingInfoForm.paymentType == 'eft'}">
                                <p>Note: Please allow up to 24 hours after account creation for your account balance to be accurately reflected.</p>
                             </c:if>
                             <p>You may be charged your rebill amount at the time of tag fulfillment.Your rebill amount may increase based on the total number of tags on your account.</p>
                             
                              <form id="edit_vehicle" action="${pageContext.request.contextPath}/signupDeleteVehicle.do?refresh=false&confirmEdit=confirmEdit" method="post">  
								<!-- Delivery options -->

   
                              
					<!-- Adding Delivery method to this form -->
					 <div class="secondary-section-content">
					<c:choose>
					
				    <c:when test="${tagRequestForm.activePbpTagExists and tagRequestForm.ezTagsExist eq 'false'}">
					</c:when>
					<c:otherwise>
					<h4 id="delivery-method">Choose delivery method</h4> 
					 <div class="secondary-section-content">
						   <input type="hidden" name="pickup" value="${tagRequestForm.pickup}"/>
				     <fieldset>
						<input type="radio" class="radio" id="pick-up-in-person1" name="radio_pickup" onClick="javascript:onRadioButtonClick(this, false)"  <c:if test="!${tagRequestForm.pickup}"> checked  </c:if> value="mail">Ship the EZ TAG(s) to my mailing address.&nbsp;I understand my EZ TAG(s) will arrive within 3-5 business days.  <br>
						
						<input type="radio" class="radio" id="pick-up-in-person2" name="radio_pickup" onClick="javascript:onRadioButtonClick(this, true)" <c:if test= "${tagRequestForm.pickup}"> checked </c:if> value="pick">Do not ship the EZ TAG(s) to my mailing address. I prefer to pick them up in person. </br>
					 </fieldset>
					 </div>
					 </c:otherwise>
					 </c:choose>
					 
					</div>

					<!-- End of adding delivery method to this form -->

<!-- Delivery options -->
				
                                <ul class="vehicles">
                                    <c:if test="${tagRequestForm.ezTagsExist}">
									<br>
                                        <h4 id="vehicles-added">EZ TAG(s) requested for the following Vehicle(s):</h4>
                                        <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
                                          <c:if test="${not vehicle.pbpTag}">
                                             <li class="summary">
                                                <p>
                                                    <strong>
                                                        <c:choose>
                                                            <c:when test="${empty vehicle.nickname}">
                                                                ${vehicle.vehicleYear} ${vehicle.vehicleMake} ${vehicle.vehicleModel}    
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${vehicle.nickname}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </strong> 
                                                    &mdash; ${vehicle.vehicleYear} ${vehicle.vehicleColor} ${vehicle.vehicleMake} ${vehicle.vehicleModel}, 
                                                    <span>${vehicle.licState} </span> 
                                                    <c:choose>
                                                        <c:when test="${vehicle.temporaryLicPlate}">
                                                            Temporary
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${vehicle.licPlate}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p class="last-child">
                                                    <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit vehicle" alt="Edit vehicle" title="&rarr; Edit vehicle" onclick="javascript:editThisVehicle(${varStatus.index});return false;" />
                                                     <c:if test="${tagRequestForm.totalTagCount > 1}">
                                                        <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif" value="Delete vehicle" alt="Delete vehicle" title="&rarr; Delete vehicle" onclick="javascript:deleteThisVehicle(${varStatus.index});return false;" />
                                                     </c:if>
                                                </p>
                                            </li>
                                          </c:if>  
                                        </c:forEach>
                                    </c:if>
									</ul>

                                   
                                    <c:if test="${tagRequestForm.pbpTagsExist}">
									 <ul class="vehicles">
									
                                        <h4 id="vehicles-added">EZ Plate(s) requested for the following vehicle(s):</h4>
                                        <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
                                          <c:if test="${vehicle.pbpTag}">
                                             <li class="summary">
                                                <p>
                                                    <strong>
                                                        <c:choose>
                                                            <c:when test="${empty vehicle.nickname}">
                                                                ${vehicle.vehicleYear} ${vehicle.vehicleMake} ${vehicle.vehicleModel}    
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${vehicle.nickname}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </strong> 
                                                    &mdash; ${vehicle.vehicleYear} ${vehicle.vehicleColor} ${vehicle.vehicleMake} ${vehicle.vehicleModel}, 
                                                    <span>${vehicle.licState} </span> 
                                                    <c:choose>
                                                        <c:when test="${vehicle.temporaryLicPlate}">
                                                            Temporary
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${vehicle.licPlate}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p class="last-child">
                                                    <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit vehicle" alt="Edit vehicle" title="&rarr; Edit vehicle" onclick="javascript:editThisVehicle(${varStatus.index});" />
                                                    <c:if test="${tagRequestForm.totalTagCount > 1}">
                                                   <!--     <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif" value="Delete vehicle 1" onclick="javascript:deleteThisVehicle(${varStatus.index});" /> -->
                                                    </c:if>
                                                </p>
                                            </li>
                                          </c:if>  
                                        </c:forEach>
                                    </c:if>
				</ul> <!-- end of vehicles -->
                                <input type="hidden" name="vehicleIndexToModify"/>
                                <input type="hidden" name="deleteVehicle"/>
                             </form>
                                
				<div class="section">					
					<h4 id="total-cost">Total cost:</h4>

						<table id="account-setup-charges" class="data-table">
							<thead>
								<tr>
									<td colspan="3" scope="col">Account setup charges</td>
								</tr>
							</thead>

							<tfoot>
								<tr>
									<td></td>
                                                                        <td>Account setup charges:</td>
									<td class="currency"><span>$</span><fmt:formatNumber value="${tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
								</tr>
							</tfoot>

							<tbody>
								<tr class="odd">
									<td colspan="2">Pre-paid Deposit Balance for <c:if test="${tagRequestForm.ezTagCount > '0' }">${tagRequestForm.ezTagCount} EZ TAG(s) </c:if><c:if test="${tagRequestForm.ezPlateCount > '0' }"> <c:if test="${(tagRequestForm.ezTagCount > '0') and (tagRequestForm.ezPlateCount > '0')}"> and </c:if>${tagRequestForm.ezPlateCount} EZ Plate </c:if></td>
									<td class="currency"><span>$</span>
                                                                            <fmt:formatNumber value="${tagRequestForm.depositAmount}" minFractionDigits="2" maxFractionDigits="2"/>
                                                                        </td>
								</tr>
								<tr class="even">
									<td colspan="2"></td>									
									<td class="currency"><span>$</span></td>
								</tr>
                                                                
							</tbody>
						</table>
                                                 <c:if test="${tagRequestForm.ezTagsExist}">
                                                <p><b style="color:blue"> * Activation fee of <span>$</span><fmt:formatNumber value="${tagRequestForm.tagSaleAmount}" minFractionDigits="2" maxFractionDigits="2"/> will be charged after the order is fulfilled.</b></p>
                                                </c:if>
				</div> <!-- end of section -->
                                
                        
                        <form id="sign-up" action="${pageContext.request.contextPath}/signupActivateAccount.do" method="post">
				 <ul class="form-actions">    
					                                                      
			   <!--  <input type="hidden" id="pick-up-value" name="pickup" value="${pickup}" scope="request"/> 	-->
				 
				 <input type="hidden" id="pickup" name="pickup" value="${pickup}" />
				  
				<li>
					 <table align="right">
					 <tr> <td colspan="20" align="right"> <input id="activate-account" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/checkout.gif" value="Check Out" alt="Check Out" title="This is a one-time payment only. Multiple clicks may result in multiple payments."  onclick="javascript:doSubmit();return false;"/></td></tr>
					 <tr> <td colspan="20" align="right">  <strong>** Please Note:</strong> This is a one-time payment only. <br>Multiple clicks may result in multiple payments. </td></tr>
				</table> </li> </ul> <!-- end of form-actions -->
			</form>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-6-of-6">
			New EZ TAG Account
			<em>Step 6 of 6</em>
		</p>

	</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="scCheckout";
s.products=";New Acct Tags - "+ "${billingInfoForm.paymentType}" + ";" + "${tagRequestForm.firstTierEZTag}" + ";" + "${tagRequestForm.tagSaleAmount}" + ",;New Acct Payments - " +  "${billingInfoForm.paymentType}" + ";1;" + "${tagRequestForm.depositAmount}";
s.zip="${contactInfoForm.zip}";
s.state="${contactInfoForm.state}";
s.eVar1="${acctId}";
<c:choose>
    <c:when test="${contactInfoForm.accountType == 'personal'}">
        s.eVar3="Regular";
    </c:when>
    <c:otherwise>
        s.eVar3="Corporate";
    </c:otherwise>
</c:choose>

var submitted = false;
    function gotoOnlineAccess()
    {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].action="${pageContext.request.contextPath}/signup.do?refresh=false";
            document.forms[0].submit();
        }
    }
        
    function deleteThisVehicle(index)
    {
        if (!submitted)
        {
            submitted = true;
            document.forms["edit_vehicle"].vehicleIndexToModify.value=index;
            document.forms["edit_vehicle"].deleteVehicle.value="true";
            document.forms["edit_vehicle"].submit();
        }
    }
    
    function editThisVehicle(index)
    {
        if (!submitted)
        {
            submitted = true;
            document.forms["edit_vehicle"].vehicleIndexToModify.value=index;
            document.forms["edit_vehicle"].submit();
        }
    }
    
    function setDeliveryOption() {  //devlivery options selections
        var exists = false;
    	for (counter = 1; counter <=2; counter++) {
    		var radio_name = "pick-up-in-person" + counter;
    		var radio_button = document.getElementById(radio_name);
    		if(radio_button != null || radio_button != 'undefined'){
                      exists = true;
    		  if(radio_button.checked){
                        document.forms["sign-up"].pickup.value = (counter != 1);
                        return true;
    		  }
                    }
    	}
        // only return false if the radio button exists and it's not checked
    	return !exists;
    }
    
    function activateAccount()
    {

		 if (!submitted)
        {
        
                           var isDeliveryMethodExists = false;
                                     
                            <c:choose>					
				            <c:when test="${tagRequestForm.activePbpTagExists and tagRequestForm.ezTagsExist eq 'false'}">
                                              isDeliveryMethodExists = true;
					    </c:when>
					    <c:otherwise>
                                               isDeliveryMethodExists = false;
                                            </c:otherwise>
                                      </c:choose>             
			if(isDeliveryMethodExists == false)
			{
				//alert("I am in delivery false");
				if(isRadioButtonChecked() == true){
					submitted = true;	

					document.getElementById("activate-account").disabled= true;
					document.getElementById("activate-account").style.opacity="0.3";
					document.getElementById("activate-account").style.filter = "alpha(opacity=40)";

					
					//document.getElementById("activate-account").style.filter = "gray()";
					//document.getElementById("activate-account").enabled=false;
					//document.getElementById("activate-account").src="${pageContext.request.contextPath}/meta/media/buttons/checkout_grey.gif";
					//alert("I am in checked radio button");

					//document.forms["sign-up"].submit();
					//alert("document.pickup " + document.forms[0].pickup.value);
					//document.forms["sign-up"].action = "${pageContext.request.contextPath}/signupActivateAccount.do?delivery=true";
					document.forms["sign-up"].action = "${pageContext.request.contextPath}/signupActivateAccount.do?delivery=" + document.forms[0].pickup.value;
					document.forms["sign-up"].submit();
					return true;
				}else{
				//	alert("Please provide a valid EZ Tag delivery method");
					return false;
				}
			}else{
				submitted = true;	
				document.getElementById("activate-account").disabled= true;
				document.getElementById("activate-account").style.opacity="0.3";
				document.getElementById("activate-account").style.filter = "alpha(opacity=40)";

				
				//document.getElementById("activate-account").style.filter = "gray()";
				//document.getElementById("activate-account").disabled=true;
				//document.getElementById("activate-account").src="${pageContext.request.contextPath}/meta/media/buttons/checkout_grey.gif";
				//alert("I am in checked radio button");
				document.forms["sign-up"].action = "${pageContext.request.contextPath}/signupActivateAccount.do?delivery=false";
				document.forms["sign-up"].submit();
				return true;
			}
        }
    }

    function doSubmit()
    {
	    
    <c:if test="${tagRequestForm.ezTagsExist}">
        if(!setDeliveryOption()) {
          alert("Please provide a valid EZ Tag delivery method");
          return false;
        }
    </c:if>

    
			if (!submitted)
			{
				document.getElementById("activate-account").disabled= true;
				document.getElementById("activate-account").style.opacity="0.3";
				document.getElementById("activate-account").style.filter = "alpha(opacity=40)";   

				//document.getElementById("activate-account").disabled= true;
				//document.getElementById("activate-account").style.filter = "gray()";

				
				//document.getElementById("activate-account").src="${pageContext.request.contextPath}/meta/media/buttons/checkout_grey.gif";
				submitted = true;
				document.forms["sign-up"].submit();
			}
    }

	/*function onRadioButtonClick(radioObj, pickUpFlag){
		radioObj.checked = true;
		if( pickUpFlag == true){
			//alert("i am in true");
			<c:set var="pickup" value="true"/>
				//document.tagRequestForm.pickup.value = true;
				//document.forms["sign-up"].pickup.value = true;
			  //document.forms["edit_delivery"].pickup.value = true;

			
				//alert("form values " + <c:out value="${pickup}"/> + " hidden form value ...." + document.forms["edit_vehicle"].pickup.value)
	    }
		else{
		//	alert("i am in false");
			<c:set var="pickup" value="false"/>
				//document.tagRequestForm.pickup.value = false;
				//document.forms["sign-up"].pickup.value = false;
				//alert("form values " + <c:out value="${pickup}"/> + "hidden form value " + document.forms["edit_vehicle"].pickup.value);
		}
		//alert("Pick up variable Set ..." + <c:out value="${tagRequestForm.pickup}"/>);
	} */



	function onRadioButtonClick(radioObj, pickUpFlag) {
		radioObj.checked = true;
		if( pickUpFlag == true){
			//alert("i am in true")
			<c:set var="pickup" value="true"/>	
			document.getElementById("ezpick").value = true;
	    }
		else{	
			//alert("I am in false")
			<c:set var="pickup" value="false"/>	
			document.getElementById("ezpick").value = false;
			}
		//alert("Document pickup " +  document.getElementById("ezpick").value)
}

/* function isRadioButtonChecked()//devlivery options selections
{
    
    
	//alert("radio checking ....." + radio_button.value);
	for (counter = 1; counter <=2; counter++)
	{
		var radio_name = "pick-up-in-person" + counter;
		var radio_button = document.getElementById(radio_name);
		if (radio_form.radio_button[counter].checked){	
		  if(radio_button.checked){
			//alert("radio checking ....." + counter);
			if(counter == 1){
				document.forms[0].pickup.value = false;
			}else{
				document.forms[0].pickup.value = true;
			}
			
				return true;
			
		}		
	}
	return (false);
} -*/


	function isRadioButtonChecked()//devlivery options selections
	{
		//alert("radio checking ....." + radio_button.value)
		for (counter = 1; counter <=2; counter++)
		{
			var radio_name = "pick-up-in-person" + counter;
			var radio_button = document.getElementById(radio_name);
			if(radio_button != null || radio_button != 'undefined'){	
			  if(radio_button.checked){
				//alert("radio checking ....." + counter)
			    return true;
			  }
	                }
		}
		return (false);
	}



	
</script>