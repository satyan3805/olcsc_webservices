<%@ include file="/jsp/common/Taglibs.jsp" %>	
<c:set var="isFleetVeh" value="${false}" />
        <div id="content-container">

		<div id="content">

                                <div class="section">    
                                    <c:if test="${retPageInfo}">
                                       <input type="hidden" value="info" name="pageReturn" />
                                     </c:if>
                                        <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
					<h4>Are the Vehicles and Payment amounts correct ?</h4>
                                        </c:if >
                                        <c:if test="${empty billingContext.tagRequestForm.savedVehicles}">
					   <h4>Total payment:  <fmt:formatNumber 
                                                value="${billingContext.paymentAmt}" 
                                                minFractionDigits="2" 
                                                maxFractionDigits="2"/></h4>
                                        
                                        <h2 id="is-this-correct">Is this Correct?</h2> </c:if >
                                        <div>
                                            <logic:messagesPresent message="false">
                                                <dl class="errors" />
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
                                        <em>Please review everything to make sure it is accurate. 
                                        Once you have verified all the items below are correct, press
                                        <c:choose>
                                            <c:when test="${((billingContext.tagRequestForm.depositAmount + billingContext.tagRequestForm.tagSaleAmount) > 0) or (billingContext.paymentAmt > 0)}">
                                            "Check Out" 
                                            </c:when>
                                            <c:otherwise>
                                            "Check Out"
                                            </c:otherwise>
                                        </c:choose>
                                        to process your order.
                                        </em>
                                        <c:if test="${((billingContext.tagRequestForm.depositAmount + billingContext.tagRequestForm.tagSaleAmount) > 0) or (billingContext.paymentAmt > 0)}">
                                        <p>Note: It may take up to 24 hours (or one business day) for recent payments to be reflected on your balance.</p>
                                        <c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}">
                                             <p>You may be charged your rebill amount at the time of tag fulfillment. Your rebill amount may increase based on the total number of tags on your account.</p>
                                             </c:if>
                                           </c:if>

				</div> <!-- end of section -->
				                                    

<!-- Delivery options -->
<!--  When Fleet tags exists during multiple vehicle upload -->

<c:choose>
 <c:when test="${mutipleUpload eq 'M'}">
<c:if test="${ezTagsExist}">	
<c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}"> 


					<!-- Adding Delivery method to this form --> 
                                        <div id="ez-tag-or-fleet">
					 <h4 id="delivery-method">Choose delivery method</h4> 
					<div class="secondary-section-content">
				     <fieldset>
						
						<input type="radio" class="radio" id="pick-up-in-person1" name="radio_pickup" onclick="javascript:onRadioButtonClick(this, false)" <c:if test="!${tagRequestForm.pickup}">checked="checked"</c:if> value="MAIL">Ship the EZ TAG(s) to my mailing address.&nbsp; I understand my EZ TAG(s) will arrive within 3-5 business days. <br />
						<input type="radio" class="radio" id="pick-up-in-person2" name="radio_pickup" onclick="javascript:onRadioButtonClick(this, true)" <c:if test="${tagRequestForm.pickup}">checked="checked"</c:if> value="PICKUP">Do not ship the EZ TAG(s) to my mailing address. I prefer to pick them up in person.<br />
					 </fieldset>
					 </div>
                     <br />
                                </div>
					</c:if>
</c:if>
</c:when>

<c:otherwise>
		
<c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}"> 


					<!-- Adding Delivery method to this form --> 
                                        <div id="ez-tag-or-fleet">
					 <h4 id="delivery-method">Choose delivery method</h4> 
					<div class="secondary-section-content">
				     <fieldset>
						
						<input type="radio" class="radio" id="pick-up-in-person1" name="radio_pickup" onclick="javascript:onRadioButtonClick(this, false)" <c:if test="!${tagRequestForm.pickup}">checked="checked"</c:if> value="MAIL">Ship the EZ TAG(s) to my mailing address.&nbsp; I understand my EZ TAG(s) will arrive within 3-5 business days. <br />
						<input type="radio" class="radio" id="pick-up-in-person2" name="radio_pickup" onclick="javascript:onRadioButtonClick(this, true)" <c:if test="${tagRequestForm.pickup}">checked="checked"</c:if> value="PICKUP">Do not ship the EZ TAG(s) to my mailing address. I prefer to pick them up in person.<br />
					 </fieldset>
					 </div>
                     <br />
                                </div>
					</c:if>
</c:otherwise>
</c:choose>

			 

					<!-- End of adding delivery method to this form -->

<!-- Delivery options -->
                                   <c:if test="${(not empty billingContext.billingInfoForm) and (billingContext.paymentAmt > 0)}">		
                                   <div class="section">
                                    <h4>Billing Information</h4>
                                   </div>
                                    <dl id="payment-method" class="immediately-following-an-h4">
                                            <dt>Payment Method: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" alt="Edit Payment Method" title="&rarr; Edit Payment Method" value="Edit your payment method" onclick="javascript:gotoBillingInfo();" /></dt>
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'credit'}">
                                                    <dd>Credit Card</dd>
                                                </c:if>
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'eft'}">
                                                    <dd>Withdraw funds from bank</dd>
                                                </c:if>

                                    </dl> <!-- end of payment-method -->

                                    <dl id="billing-address">
                                            <dt>Billing Detail: <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" alt="Edit Billing address" title="&rarr; Edit Billing address" value="Edit your billing address" onclick="javascript:gotoBillingInfo('${billingContext.billingInfoForm.paymentType}');" /></dt>
                                                    <dd><etcc-extended:format billingInfo="${billingContext.billingInfoForm}" mask="ACCOUNT" displayName="false" /></dd>
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'credit'}">
                                                    <dd>
                                                        <address>
                                                        <etcc-extended:format address="${billingContext.billingInfoForm}" />
                                                        </address>
                                                    </dd>
                                                </c:if>
                                    </dl>
                                                </c:if>

                              <c:choose>  
                                  <c:when test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                  <form id="edit_vehicle" action="${pageContext.request.contextPath}/acctModifyTagFromConfirmation.do" method="post">  
                                    <ul class="vehicles">
                                        <c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}">
                                            <div>
                                            <h4 id="vehicles-added">EZ TAG(s) requested for the following Vehicle(s):</h4>
                                            <c:forEach items="${billingContext.tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
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
                                                    <c:choose>
                                                      <c:when test="${mutipleUpload eq 'M'}">
                                                        <c:if test="${vehicle.tagTypeCode eq 'F'}">
                  <c:set var="isFleetVeh" value="${true}" />
                                                        </c:if>
                                                      </c:when>
                                                      <c:otherwise>
                                                          <p class="last-child">
                                                              <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit vehicle" alt="Edit vehicle" title="&rarr; Edit vehicle" onclick="javascript:editThisVehicle(${varStatus.index});return false;" />
                                                              <c:if test="${billingContext.tagRequestForm.totalTagCount > 1}">
                                                                 <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif" value="Delete vehicle" alt="Delete vehicle" title="&rarr; Delete vehicle" onclick="javascript:deleteThisVehicle(${varStatus.index});return false;" />
                                                        </c:if>
                                                    </p>
                                                    </c:otherwise>
                                                  </c:choose>
                                                </li>
                                              </c:if>
                                            </c:forEach>
                                            </div>
                                        </c:if>

                                        <c:if test="${billingContext.tagRequestForm.pbpTagsExist}">
                                            <div>
                                            <h4 id="vehicles-added">EZ Plate(s) requested for the following vehicle(s):</h4>
                                            <c:forEach items="${billingContext.tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
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
                                                         <c:if test="${billingContext.tagRequestForm.totalTagCount > 1}">
                                                          <!--  <input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif" value="Delete vehicle 1" onclick="javascript:deleteThisVehicle(${varStatus.index});return false;" /> -->
                                                        </c:if>
                                                    </p>
                                                </li>
                                              </c:if>  
                                            </c:forEach>
                                            </div>
                                        </c:if>
                                    </ul> <!-- end of vehicles -->
                                    <input type="hidden" name="vehicleIndexToModify"/>
                                    <input type="hidden" name="deleteVehicle"/>
                                     <c:if test="${retPageInfo}">
                                       <input type="hidden" value="info" name="pageReturn" />
                                     </c:if>
                                 </form>

                                     <div class="section">
                                            <h4 id="total-cost">Payments:</h4>
                                                    <table id="account-setup-charges" class="data-table">
                                                        <tfoot> <tr> <td colspan="2">Total Payment:</td>
                                                            <td class="currency"><span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></td></tr>
                                                        </tfoot>
                                                        <tbody>
                                                           <tr class="odd">
                                                                    <td colspan="2">Starting EZ TAG Account Balance</td>
                                                              <td class="currency"><span>$</span>
                                                              <c:choose>
                                                                 <c:when test="${acctInfo.balanceAmt < 0}">
                                                                     ${acctInfo.balanceAmt}0
                                                                </c:when>
                                                                <c:otherwise>
                                                                      <fmt:formatNumber type="currency" maxFractionDigits="2" value="${acctInfo.balanceAmt}"/> 
                                                                </c:otherwise>
                                                              </c:choose>
                                                              </td>
                                                           </tr>
                                                           <c:choose>
                                                                 <c:when test="${billingContext.tagRequestForm.totalAmount > 0}">
                                                                     <tr class="even">                                                                                                        
                                                                              <td colspan="2">Payment to Replenish Account balance</td>
                                                                        <td class="currency"><span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                                                     </tr>
                                                                     <tr class="odd">
                                                                        <td colspan="2">Ending Account Balance after Payment Replenishment</td>
                                                                        <td class="currency"><span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.totalAmount + acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                                                     </tr>
                                                                </c:when>
                                                               <c:otherwise>
                                                                    <tr class="even">                                                                                                        
                                                                            <td colspan="3"></td>
                                                                   </tr>
                                                               </c:otherwise>
                                                           </c:choose>
                                                        </tbody>
                                                    </table>
      <c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}">
  <p style="color:blue;font-weigth:bold;">
*Activation fee of <span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.tagSaleAmount}" minFractionDigits="2" maxFractionDigits="2"/> will be charged after the order is fulfilled.
  </p>
                                                           </c:if>
                                    </div> <!-- end of section -->

                                        <input type="hidden" name="retailTransId" value="${retailTransId}">

                                  </c:when>
                                  <c:otherwise>
                                
                                    <div class="section">
                                        <h4 id="total-cost">Payments:</h4>
                                                <table id="account-setup-charges" class="data-table">
                                                    <tfoot> <tr> <td colspan="2">Total Payment:</td>
                                                        <td class="currency"><span>$</span><fmt:formatNumber 
                                                                    value="${billingContext.paymentAmt}" 
                                                                    minFractionDigits="2" 
                                                                    maxFractionDigits="2"/></td></tr>
                                                    </tfoot>
                                                    <tbody>
                                                       <tr class="odd">
                                                          <td colspan="2">EZ TAG Account payment</td>
                                                          <td class="currency"><span>$</span><fmt:formatNumber
                                                                     value="${billingContext.paymentAmt}" 
                                                                     minFractionDigits="2" 
                                                                     maxFractionDigits="2"/>
                                                          </td>
                                                       </tr>
                                                    </tbody>
                                                </table>
                                    </div> <!-- end of section -->

                                    <p id="need-to-change-any-payments">If you need to change any of these payments, return to the <a href="${pageContext.request.contextPath}/accountMakePaymentFromConfirmation.do">make a payment</a> screen.</p>
                                  </c:otherwise>
                              </c:choose>

                        <!-- form changes -->
			<form id="account-payment-confirm" action="${pageContext.request.contextPath}/acctPaymentConfirm.do" method="post">
					<input type="hidden" id="pickup" name="pickup" value="${pickup}" />
				<ul class="form-actions">
                                    <c:if test="${retPageInfo}">
                                       <input type="hidden" value="info" name="pageReturn" />
                                     </c:if>
                                        <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                           <input type="hidden" id="acct-balnace-amt" name="prevBalanceAmt" value="${acctInfo.balanceAmt}"/>
                                            <input type="hidden" id="eztags-exist" name="ezTagsExist" value="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}"/>
                                            <input type="hidden" id="mutipleUpload" name="mutipleUpload" value="${mutipleUpload}"/> 
                                            <input type="hidden" id="fleet-veh-exist" name="fleetVeh" value="${isFleetVeh}"/>
                                        </c:if>
					<li>
                                            <c:choose>
                                                <c:when test="${((billingContext.tagRequestForm.depositAmount + billingContext.tagRequestForm.tagSaleAmount) > 0) or (billingContext.paymentAmt > 0)}">
                                                 <table align="right"> 
					                                                       <tr> <td colspan="20" align="right">  <input id="activate-account" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/checkout.gif" value="Check Out" alt="Check Out" title="This is a one-time payment only. Multiple clicks may result in multiple payments. " onclick="javascript:doSubmit(); return false;"/> </td></tr>
					       					<tr> <td align="right"><strong>** Please Note:</strong> This is a one-time payment only. <br> Multiple clicks may result in multiple payments.   </td></tr>
				   </table> 
                                                </c:when>
                                                <c:otherwise>
                                                    <table align="right"> 
                                                    <tr> <td colspan="20" align="right"> <input id="activate-account" type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/checkout.gif" value="Check Out" alt="Check Out" title="This is a one-time payment only. Multiple clicks may result in multiple payments. " onclick="javascript:doSubmit(); return false;"/> </td></tr>
                                                    <tr> <td align="right"><strong>** Please Note:</strong> This is a one-time payment only. <br> Multiple clicks may result in multiple payments.   </td></tr>
                                                    </table> 
                                                </c:otherwise>
                                            </c:choose>

                                        </li>

				</ul> <!-- end of form-actions -->
			</form>

		</div> <!-- end of content -->
                <jsp:include page="/accountInfo.do"/>

	</div> <!-- end of content-container -->

<script type="text/javascript">
var submitted = false;
var pType = "${billingContext.billingInfoForm.paymentType }";
if (pType == null || pType == '')
{
    pType="pmt_not_req";
}
   s.events="scCheckout";
   <c:choose>  
    <c:when test="${not empty billingContext.tagRequestForm.savedVehicles}">
        s.products=";Additional Acct Tags - "+ pType + ";${billingContext.tagRequestForm.totalTagCount};${billingContext.tagRequestForm.tagSaleAmount}";
        <c:if test="${(billingContext.tagRequestForm.totalAmount - billingContext.tagRequestForm.tagSaleAmount) >0.0}">
            s.products = s.products + ",;Additional Acct Payment - " + pType + ";1;${billingContext.tagRequestForm.totalAmount - billingContext.tagRequestForm.tagSaleAmount}";
        </c:if>
    </c:when>
    <c:otherwise>
        s.products=";Additional Acct Payment - " + pType + ";1;<fmt:formatNumber value="${billingContext.paymentAmt}" minFractionDigits="2" maxFractionDigits="2"/>";
    </c:otherwise>
    </c:choose>
    
    function deleteThisVehicle(index)
    {
        if (!submitted)
        {
            sumbitted = true;
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
            document.forms["edit_vehicle"].deleteVehicle.value="false";
            document.forms["edit_vehicle"].submit();
        }
    }
    
    function gotoBillingInfo(paymentType)
    {
        if (!submitted)
        {
            submitted = true;
            var desUrl ="${pageContext.request.contextPath}/paymentBillingInfoDisplay.do";
            if (paymentType == 'credit' || paymentType == 'eft' )
                desUrl = desUrl + "?type=" + paymentType;
                
            document.forms[0].action=desUrl;
            document.forms[0].submit();    
        }
    }

function setDeliveryOption() {  //delivery options selections
    var exists = false;
	for (counter = 1; counter <=2; counter++) {
		var radio_name = "pick-up-in-person" + counter;
		var radio_button = document.getElementById(radio_name);
		if(radio_button != null || radio_button != 'undefined'){
                  exists = true;
		  if(radio_button.checked){
                    document.forms["account-payment-confirm"].pickup.value = (counter != 1);
                    return true;
		  }
                }
	}
    // only return false if the radio button exists and it's not checked
	return !exists;
}

function doSubmit()

{
<c:choose>
<c:when test="${mutipleUpload eq 'M'}">	
<c:if test="${ezTagsExist}">	
<c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}"> 
		if(!setDeliveryOption()) {
    		alert("Please provide a valid EZ Tag delivery method");
    	return false;
  }
</c:if>
</c:if> 	
</c:when>
<c:otherwise>
	
    <c:if test="${ezTagsExist || billingContext.tagRequestForm.ezTagsExist}">
        if(!setDeliveryOption()) {
          alert("Please provide a valid EZ Tag delivery method");
          return false;
        }
    </c:if>
 </c:otherwise>
 </c:choose>  
    

      
	if (!submitted)
	{
		        //account-payment-confirm.activate-account.disabled = true;
				//document.getElementById("activate-account").disabled= true;
				//document.getElementById("activate-account").style.filter = "gray()";
				//document.getElementById("activate-account").src="${pageContext.request.contextPath}/meta/media/buttons/checkout_grey.gif";
				
				
				document.getElementById("activate-account").disabled= true;
				document.getElementById("activate-account").style.opacity="0.3";
				document.getElementById("activate-account").style.filter = "alpha(opacity=40)";
								
				submitted = true;
				document.forms["account-payment-confirm"].submit();
	}
    }

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

function isRadioButtonChecked()//delivery options selections
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
