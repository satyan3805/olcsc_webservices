<%@ include file="/jsp/common/Taglibs.jsp" %>
<% int eztagCount=0,ezplateCount=0,eztagFleetCount=0;%>
        <div id="content-container">

		<div id="content">
					<h1 id="thank-you-this-page-is-your-receipt">Thank you!</h1>

				<dl class="introduction">

                                        <dt>
                                        <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                              <c:choose>
                                                    <c:when test="${billingContext.tagRequestForm.deliveryMethod eq 'MAIL'}">
                                                      <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                                        <c:choose>
                                                              <c:when test="${ezTagsExist}">
                                                                  <p>Your order has been received. Once processed you should receive your EZ TAG(s) within <b>3-5 business days</b>. EZ TAGs are mailed through the U.S. Postal Service. If you have not received your new tag(s) within this time period, please call Customer Service at <bean:message key="HCTRA.telephone.number" />, <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a> or <a href="${appDelegate.domainName}/about_contact/">contact us by e-mail</a>. Mailed EZ TAG(s) can be activated online by clicking on the "<b>Activate Mailed EZ TAGs</b>" link under "Quick Links" found on <a href="https://www.hctra.org">https://www.hctra.org</a> or by calling 281-875-3279.</p>
                                                                  <c:if test="${billingContext.tagRequestForm.pbpTagsExist}"><p>For EZ Plate customers please verify the temporary license plate number is now listed on your account. </p></c:if>
                                                                  <c:if test="${fleetVeh}"><p>For Fleet customers please verify the temporary license plate number is now listed on your account. </p></c:if>
                                                               </c:when>
                                                               <c:otherwise>
                                                                   <c:choose>
                                                                         <c:when test="${billingContext.tagRequestForm.pbpTagsExist}">
                                                                            <p>Please verify this temporary license plate number is now listed on your account. If it is not listed, please contact Customer Service at 281-875-<b>EASY</b>(3279), <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>.</p>                         
                                                                         </c:when>
                                                                         <c:otherwise>
                                                                            <c:if test="${fleetVeh}">
                                                                              <p>Your order has been received. Please verify that your Fleet vehicle license plate(s) is now listed on your account. If it is not listed, please contact your customer service representative at 281-875-<b>EASY</b> (3279), <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>. </p>
                                                                            </c:if>
                                                                         </c:otherwise>
                                                                   </c:choose>
                                                            </c:otherwise>  
                                                        </c:choose>
                                                     </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                           <p>Your order has been received. Your new tag(s) is on hold for you, and may be picked up at any of our five convenient locations: <a href="${appDelegate.domainName}/about_locations">https://www.hctra.org/about_locations</a>.</p>
                                                           <c:if test="${billingContext.tagRequestForm.pbpTagsExist}"><p>For EZ Plate customers please verify the temporary license plate number is now listed on your account. </p></c:if>
                                                           <c:if test="${fleetVeh}"><p>For Fleet customers please verify the temporary license plate number is now listed on your account. </p></c:if>
                                                    </c:otherwise>
                                              </c:choose>
                                        </c:if>
                                        <p>Please print and retain a copy of this page for your records. </p>
                                        </dt>

						<!-- this first guy is hidden from browsers without JavaScript (and un-hidden via JavaScript -->
						<dd class="print"><a href="#" onclick="javascript: window.print();">Print this document</a></dd>

					<!--	<dd class="pdf"><a href="#">Download a PDF version</a></dd> -->
				</dl>    
                                 <c:if test="${retPageInfo}">
                                  Click <a href="${pageContext.request.contextPath}/accountInformation.do" >here</a> to Return to Account Information
                                 </c:if> 
                                <br/>
                                
         <h3 id="account-information-for">EZ TAG Account Receipt</h3>
       
        <div class="section with-primary-and-secondary-content">
        
              <div class="primary-section-content with-multiple-definition-lists">
          
        <dl>
        
          <dt>Account number: ${acctId}</dt>
          <dd>Payment date: ${dateCreated}</dd>
        </dl>

        </div> <!-- end of primary-section-content -->
       <c:if test="${billingContext.transactionId > -1}">
        <div class="secondary-section-content with-multiple-definition-lists">
          
         <dl>
         
          <dt>
            <c:if test="${ezTagsExist}">
                <div id="ez-tag-or-fleet">
                  Delivery by: ${billingContext.tagRequestForm.deliveryMethod}
                </div>
            </c:if>
          </dt>
          
          <c:choose>
          <c:when test="${billingContext.transactionId > -1}">
          <c:choose>
          <c:when test="${not empty billingContext.tagRequestForm.savedVehicles}">
               <dd>Tag Request Activation Number: ${billingContext.transactionId}</dd>
          </c:when>
           <c:otherwise>
             <dd></dd>
          </c:otherwise>
          </c:choose>
          </c:when>
          <c:otherwise>
             <dd></dd>
          </c:otherwise>
          </c:choose>          
         </dl>
        </div> <!-- end of 1st secondary-section-content -->
       </c:if>
      </div> <!-- end of 1st section with-primary-and-secondary-content -->


                                        <h4>Please Note :</h4>

										<p>Note: It may take up to 24 hours (or one business day) for recent payments to be reflected on your balance.</p>
          <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
            <c:choose>
              <c:when test="${ezTagsExist}">
                <p>You may be charged your rebill amount at the time of tag fulfillment. Your rebill amount may increase based on the total number of tags on your account.</p>
              </c:when>
              <c:otherwise>
                <p>Your Rebill Amount may increase or reduce based on your number of tag(s) and the tag status on your account.</p>
              </c:otherwise>
            </c:choose>
          </c:if>

                                <c:if test="${(not empty billingContext.billingInfoForm) and (billingContext.paymentAmt > 0)}">
                                    <h4 id="billing-information">Billing Information</h4>

                                    <dl id="payment-method" class="immediately-following-an-h4">
                                            <dt>Payment Method: 
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'credit'}">
                                                    <dd>Credit Card</dd>
                                                </c:if>
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'eft'}">
                                                    <dd>Withdraw funds from bank</dd>
                                                </c:if>

                                    </dl> <!-- end of payment-method -->

                                    <dl id="billing-address">
                                            <dt>Billing Detail:
                                                    <dd><etcc-extended:format billingInfo="${billingContext.billingInfoForm}" displayName="false" /></dd>
                                                <c:if test="${billingContext.billingInfoForm.paymentType == 'credit'}">
                                                    <dd>
                                                        <address>
                                                        <etcc-extended:format address="${billingContext.billingInfoForm}" />
                                                        </address>
                                                    </dd>
                                                </c:if>
                                    </dl>
                                </c:if>	

                                  <c:if test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                    <ul class="vehicles">
                                        <c:if test="${ezTagsExist}">                                            
                                            <h4 id="vehicles-added">EZ TAG(s) requested for the following Vehicle(s):</h4>
                                            <div>
                                            <c:forEach items="${billingContext.tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
                                              <c:if test="${not vehicle.pbpTag}">
                                                <c:choose>
                                                    <c:when test="${vehicle.tagTypeCode eq 'F'}">
                                                       <% eztagFleetCount++; %>
                                                    </c:when>
                                                    <c:otherwise>
                                                       <% eztagCount++; %>
                                                    </c:otherwise>
                                                </c:choose>                                                
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
                                                <% ezplateCount++; %>
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
                                                </li>
                                              </c:if>  
                                            </c:forEach>
                                            </div>
                                        </c:if>
                                    </ul> <!-- end of vehicles -->
                                  </c:if>

                                    
                                <c:choose>
                                    <c:when test="${not empty billingContext.tagRequestForm.savedVehicles}">
                                    
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
                                                                 <c:when test="${prevBalanceAmt < 0}">
                                                                     ${prevBalanceAmt}0
                                                                 </c:when>
                                                                 <c:otherwise>
                                                                      <fmt:formatNumber type="currency" maxFractionDigits="2" value="${prevBalanceAmt}"/> 
                                                                 </c:otherwise>
                                                                </c:choose>                                                                                                                                
                                                              </td>
                                                           </tr>
                                                          <c:choose>
                                                                 <c:when test="${billingContext.tagRequestForm.totalAmount > 0}">
                                                                     <tr class="even">                                                                                                        
                                                                              <td colspan="2">Payment to Replenish Account balance</td>                                                               
                                                                        <td class="currency"><span>$</span>
                                                                           <fmt:formatNumber value="${billingContext.tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/>
                                                                        </td>
                                                                     </tr>
                                                                     <tr class="odd">
                                                                        <td colspan="2">Ending Account Balance after Payment Replenishment</td>                                                              
                                                                        <td class="currency"><span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.totalAmount + prevBalanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
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
                                                    <c:if test="${ezTagsExist}">
                  <p style="color:blue;font-weight:bold;">*Activation fee of <span>$</span><fmt:formatNumber value="${billingContext.tagRequestForm.tagSaleAmount}" minFractionDigits="2" maxFractionDigits="2"/> will be charged after the order is fulfilled.</p>
                                                    </c:if>
                                    </div> <!-- end of section -->

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
                                                                            maxFractionDigits="2"/></td>
                                                       </tr>
                                                    </tbody>
                                                </table>
                                    </div> <!-- end of section -->
                                    
                                    </c:otherwise>
                                </c:choose>

		</div> <!-- end of content -->
                
                <jsp:include page="/accountInfo.do"/>

	</div> <!-- end of content-container -->

<script type="text/javascript">
 <%   
       String appendProductEvents=""; 
      // String strEzTagCount="";
     //  String strEzPlateCount="";
     // String strFleetCount="";
        if(eztagCount > 0 && ezplateCount > 0)
       { 
        %>
        s.events="purchase,event3,event9:${billingContext.transactionId},event11:${billingContext.transactionId}";
        <%
       // appendProductEvents="; ; ; ; event9="+eztagCount + ", ; ; ; ; event11="+ezplateCount;
        appendProductEvents="event9="+eztagCount+",;EzPlate Addition - "+ezplateCount+";;;event11="+ezplateCount;        
       } else if(eztagCount > 0)
       {
       %>
        s.events="purchase,event3,event9:${billingContext.transactionId}";
        <%
      //  appendProductEvents="; ; ; ;event9="+eztagCount;
        appendProductEvents="event9="+eztagCount;
       } else if(ezplateCount > 0){
       %>
        s.events="purchase,event3,event11:${billingContext.transactionId}";
        <%
       // appendProductEvents="; ; ; ; event11="+ezplateCount;
        appendProductEvents="event11="+ezplateCount;
       }else
       {
       %>
        s.events="purchase,event3";
        <%
       }       
       if(eztagFleetCount > 0){ %>
         s.events = s.events + ",event15:${billingContext.transactionId}";
         <%
         if(appendProductEvents.length() >0){
           appendProductEvents= appendProductEvents +",;Fleet Vehicles - "+eztagFleetCount +"; ; ;event15="+eztagFleetCount;
          // strFleetCount = ";Fleet Vehicles - "+eztagFleetCount +"; ; ;event15="+eztagFleetCount;
           } else{
           appendProductEvents= ",;Fleet Vehicles - "+eztagFleetCount +"; ; ;event15="+eztagFleetCount;
           //strFleetCount = ";Fleet Vehicles - "+eztagFleetCount +"; ; ;event15="+eztagFleetCount;
           }
       }
       %>
       
       
s.purchaseID="${billingContext.transactionId}";
var pType = "${billingContext.billingInfoForm.paymentType }";
if (pType == null || pType == '')
{
    pType="pmt_not_req";
}
<c:choose>  
<c:when test="${not empty billingContext.tagRequestForm.savedVehicles}">
    s.products=";Additional Acct Tags - "+ pType + ";${billingContext.tagRequestForm.totalTagCount};${billingContext.tagRequestForm.tagSaleAmount};<%=appendProductEvents%>";
    <c:if test="${(billingContext.tagRequestForm.totalAmount - billingContext.tagRequestForm.tagSaleAmount) >0.0}">
        s.products = s.products + ",;Additional Acct Payment - " + pType + ";1;${billingContext.tagRequestForm.totalAmount - billingContext.tagRequestForm.tagSaleAmount}";
    </c:if>
</c:when>
<c:otherwise>
    s.products=";Additional Acct Payment - " + pType + ";1;<fmt:formatNumber value="${billingContext.paymentAmt}" minFractionDigits="2" maxFractionDigits="2"/>";
</c:otherwise>
</c:choose>
</script>
