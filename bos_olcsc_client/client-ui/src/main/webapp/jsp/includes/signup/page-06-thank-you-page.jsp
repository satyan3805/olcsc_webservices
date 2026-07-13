<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<% int eztagCount=0,ezplateCount=0;%>

<div id="content-container">

  <div id="content">

    <form id="sign-up" action="${pageContext.request.contextPath}/signupManageAccount.do" method="post">

      <h1 id="thank-you-this-page-is-your-receipt">Thank you. <em>This page is your receipt</em></h1>

      <dl class="introduction">
        <dt>   <c:choose>
               <c:when test="${tagRequestForm.ezTagsExist}">
                       <c:choose>
                             <c:when test="${tagRequestForm.deliveryMethod eq 'MAIL'}">
                                   <p>Your order has been received. Once processed you should receive your EZ TAG(s) within <b>3-5 business days</b>. EZ TAGs are mailed through the US Postal Service. If you have not received your new tag(s) within this time period, please contact Customer Service at <bean:message key="HCTRA.telephone.number" />  or <a href="${appDelegate.domainName}/about_contact/" title="e-mail">e-mail</a> or visit an EZ TAG store<a href="${appDelegate.domainName}/about_locations/" title="locations"> location</a> during<a href="${appDelegate.domainName}/about_locations/" title="locations"> regular business hours</a>. Mailed EZ TAG(s) can be activated online by clicking on the "<b>Activate Mailed EZ TAGs</b>" link under "Quick Links" found on <a href="https://www.hctra.org" title="https://www.hctra.org">https://www.hctra.org</a> or by calling Customer Service.</p>
                                   <c:if test="${tagRequestForm.pbpTagsExist}">
                                   <p>For EZ Plate customers please verify the temporary license plate number is now listed on your account. </p>
                                   </c:if>
                             </c:when>
                             <c:otherwise>
                                 <c:if test="${tagRequestForm.pickup}">
                                   <p>Your order has been received. Your new tag(s) is on hold for you, and may be picked up at any of our five convenient locations: <a href="${appDelegate.domainName}/about_locations" title="Store Locations">https://www.hctra.org/about_locations</a>.</p>
                                 </c:if>
                                 <c:if test="${tagRequestForm.pbpTagsExist}"><p>For EZ Plate customers please verify the temporary license plate number is now listed on your account. </p></c:if>
                             </c:otherwise>
                       </c:choose>
               </c:when>
               <c:otherwise>
                     <c:if test="${tagRequestForm.pbpTagsExist}">
                        <p>Please verify this temporary license plate number is now listed on your account. If it is not listed, please call Customer Service at 281-875-<b>EASY</b> (3279) <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>.</p>
                      </c:if>
               </c:otherwise>
          </c:choose>
          <c:if test="${billingInfoForm.paymentType == 'eft'}">
             <p>Note: Please allow up to 24 hours after account creation for your account balance to be accurately reflected.</p>
          </c:if>
           <p>Please print and retain a copy of this page for your records. You may now manage your <a href="#" title="Manage Ez Account" onclick="javascript:document.forms[0].submit();">EZ TAG Account</a> online.</p></dt>

        <!-- this first guy is hidden from browsers without JavaScript (and un-hidden via JavaScript -->
        <dd class="print"><a href="#" title="Print this document" onclick="javascript: window.print();">Print this document</a></dd>

        <!--	<dd class="pdf"><a href="#">Download a PDF version</a></dd> -->
      </dl>


      <h3 id="account-information-for">EZ TAG Account Receipt</h3>

      <div class="section with-primary-and-secondary-content">

        <div class="primary-section-content with-multiple-definition-lists">

        <dl>

          <dt>Account number: ${OnlineAccessForm.acctId}</dt>
          <dd> Account opened: ${dateCreated}</dd>
        </dl>

        </div> <!-- end of primary-section-content -->

        <div class="secondary-section-content with-multiple-definition-lists">

         <dl>
         <c:choose>
               <c:when test="${tagRequestForm.ezTagsExist}">
                 <dt>Delivery by: ${tagRequestForm.deliveryMethod}</dt>
               </c:when>
               <c:otherwise>
                   <c:if test="${tagRequestForm.pbpTagsExist}">
                    <dt></dt>
                   </c:if>
               </c:otherwise>
         </c:choose>
          <c:choose>
               <c:when test="${contactInfoForm.retailTransId > -1}">
                <dd>Tag Request Activation Number: ${contactInfoForm.retailTransId} </dd>
               </c:when>
               <c:otherwise>
                  <dd></dd>
               </c:otherwise>
         </c:choose>

         </dl>
        </div> <!-- end of 1st secondary-section-content -->

      </div> <!-- end of 1st section with-primary-and-secondary-content -->

      <div class="section with-primary-and-secondary-content">

              <div class="primary-section-content with-multiple-definition-lists">

          <h4 id="personal-information">Personal Information</h4>

          <dl id="user-information" class="immediately-following-an-h4">

            <dt>${contactInfoForm.firstName} ${contactInfoForm.lastName}</dt>
            <dd>${OnlineAccessForm.emailAddress}</dd>
            <dd>${contactInfoForm.primaryPhone}</dd>
            <dd>${contactInfoForm.alternatePhone}</dd>
            <dd>${contactInfoForm.driversLicState} DL: ${contactInfoForm.maskedDriversLic}</dd>

          </dl> <!-- end of user-information -->

          <dl id="mailing-address">

            <dt>Mailing Address: </dt>
            <dd>
              <address>
  <etcc-extended:format address="${contactInfoForm}" displayName="false" />
              </address>
            </dd>
          </dl> <!-- end of mailing-address -->

          <dl class="selections">

            <dt>Preferences: </dt>
            <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
              <c:if test="${preference.active}">
                <dd>${preference.description}</dd>
              </c:if>
            </c:forEach>
            <!--
                                                                <c:if test="${contactInfoForm.byEmail and contactInfoForm.byMail}">
                                                                    <dd>Notify me by e-mail and regular mail</dd>
                                                                </c:if>
                                                                <c:if test="${contactInfoForm.byEmail and !contactInfoForm.byMail}">
                                                                    <dd>Notify me by e-mail</dd>
                                                                </c:if>
                                                                <c:if test="${!contactInfoForm.byEmail and contactInfoForm.byMail}">
                                                                    <dd>Notify me by regular mail</dd>
                                                                </c:if>

                                                                <c:if test="${billingInfoForm.mailMonthlyStatement}">
                                                                    <dd>Send me an itemized monthly statement by mail</dd>
                                                                </c:if>
                                                            -->

          </dl> <!-- end of selections -->

        </div> <!-- end of primary-section-content -->

        <div class="secondary-section-content with-multiple-definition-lists">

          <h4 id="billing-information">Billing Information</h4>

          <dl id="payment-method" class="immediately-following-an-h4">
            <dt>Payment Method: </dt>
            <c:if test="${billingInfoDTO.creditCard}">
              <dd>Credit Card</dd>

            </c:if>

            <!-- c:if test="${billingInfoForm.paymentType == 'eft'}">
              <dd>Withdraw funds from bank</dd>
            <!--/c:if-->

          </dl> <!-- end of payment-method -->

          <dl id="billing-address">
            <dt>Billing Detail: </dt>
            <dd><etcc-extended:format billingInfo="${billingInfoDTO}" displayName="false" /></dd>
            <c:if test="${billingInfoDTO.creditCard}">
              <dd>
                <address>
                  <c:choose>
                    <c:when test="${billingInfoDTO.diffBillingAddress}">
  <etcc-extended:format address="${billingInfoDTO}" displayName="false" />
                    </c:when>
                    <c:otherwise>
  <etcc-extended:format address="${contactInfoForm}" displayName="false" />
                    </c:otherwise>
                  </c:choose>
                </address>
              </dd>
            </c:if>
          </dl>

        </div> <!-- end of secondary-section-content -->

      </div> <!-- end of section with-primary-and-secondary-content -->

      <ul class="vehicles">

        <c:if test="${tagRequestForm.ezTagsExist}">
          <h4 id="vehicles-added">EZ TAG(s) requested for the following Vehicle(s):</h4>
          <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
            <c:if test="${not vehicle.pbpTag}">
            <% eztagCount++; %>
              <li>
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
        </c:if>
        </ul>
		<ul class="vehicles">
        <c:if test="${tagRequestForm.pbpTagsExist}">
        <br />
          <h4 id="vehicles-added">EZ Plate(s) requested for the following vehicle(s):</h4>
          <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
            <c:if test="${vehicle.pbpTag}">
              <% ezplateCount++; %>
              <li>
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
        </c:if>
      </ul> <!-- end of vehicles -->

      <div class="section">

        <h4 id="total-cost">Total cost:</h4>

        <table id="account-setup-charges" class="data-table">
          <thead>
            <tr>
              <td colspan="3" scope="col">Account setup charges</td>
            </tr>
          </thead>

          <tbody>
           <tr class="odd">
              <td colspan="2">Starting Account Balance </td>
              <td class="currency"><span>$</span><fmt:formatNumber value="0.00" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr>
            <tr class="even">
              <td colspan="2">Pre-paid Deposit Balance for <c:if test="${tagRequestForm.ezTagCount > '0' }">${tagRequestForm.ezTagCount} EZ TAG(s) </c:if><c:if test="${tagRequestForm.ezPlateCount > '0' }"> <c:if test="${(tagRequestForm.ezTagCount > '0') and (tagRequestForm.ezPlateCount > '0')}"> and </c:if>${tagRequestForm.ezPlateCount} EZ Plate </c:if></td>
              <td class="currency"><span>$</span><fmt:formatNumber value="${tagRequestForm.depositAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr>
          </tbody>

          <tfoot>
            <tr>
              <td></td>
              <td>Account setup charges:</td>
              <td class="currency"><span>$</span><fmt:formatNumber value="${tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr>
          </tfoot>
        </table>
         <c:if test="${tagRequestForm.ezTagsExist}">
          <p><b style="color:blue"> * Activation fee of <span>$</span><fmt:formatNumber value="${tagRequestForm.tagSaleAmount}" minFractionDigits="2" maxFractionDigits="2"/> will be charged after the order is fulfilled.</b></p>
        </c:if>
      </div> <!-- end of section -->

      <p>Expect an email validation request.  Please contact us if this request is not promptly received.</p>

      <ul class="form-actions">

        <li><input id="manage-your-account" type="image" class="image-based manage-your-account-button" src="${pageContext.request.contextPath}/meta/media/buttons/manage-your-account.gif" value="manage-your-account" title="manage your account" onclick="javascript:gotoAcctMgmt();return false;"/></li>

      </ul> <!-- end of form-actions -->

    </form>

  </div> <!-- end of content -->


  <p class="progress-bar" id="step-7-of-7">
    New EZ TAG Account
    <em>step-7-of-7</em>
  </p>

</div> <!-- end of content-container -->

<script type="text/javascript">

<%
       String appendProductEvents1="";
       String appendProductEvents2="";
        if(eztagCount > 0 && ezplateCount > 0)
       {
        %>
        s.events="purchase,event3,event10:${OnlineAccessForm.acctId},event9:${tagRequestForm.retailTransId},event11:${tagRequestForm.retailTransId}";
        <%
        appendProductEvents1="event9="+eztagCount;
        appendProductEvents2 ="event11="+ezplateCount;
       } else if(eztagCount > 0)
       {       %>
        s.events="purchase,event3,event10:${OnlineAccessForm.acctId},event9:${tagRequestForm.retailTransId}";
        <%
        appendProductEvents1="event9="+eztagCount;
       } else if(ezplateCount > 0){
       %>
        s.events="purchase,event3,event10:${OnlineAccessForm.acctId},event11:${tagRequestForm.retailTransId}";
        <%
        appendProductEvents2="event11="+ezplateCount;
       } else {
       %>
        s.events="purchase,event3,event10:${OnlineAccessForm.acctId}";
        <%
       }
       %>

  s.products=";New Acct Tags - "+ "${billingInfoForm.paymentType}" + ";" + "${tagRequestForm.firstTierEZTag}" + ";" + "${tagRequestForm.tagSaleAmount}" + ";" + "<%=appendProductEvents1%>" +","+";New Acct Payments - " +  "${billingInfoForm.paymentType}" + ";1;" + "${tagRequestForm.depositAmount}"+";<%=appendProductEvents2%>";
  s.purchaseID="${tagRequestForm.retailTransId}";

  var submitted = false;

  function gotoAcctMgmt()
  {
    if (!submitted)
    {
      submitted = true;
      document.forms["sign-up"].submit();
    }
  }
</script>
