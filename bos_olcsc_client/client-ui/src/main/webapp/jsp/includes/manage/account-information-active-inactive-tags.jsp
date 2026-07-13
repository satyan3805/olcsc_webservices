<c:if test="${(not empty accountclosure) and (accountclosure eq true)}">
		<div id="account-closure-main">
					<h4 id="account-closure">Account Closure</h4>
					</div>
   <div id="closeAlertDiv" class="section">
      <logic:messagesPresent message="true" property="closeAlert">
           <dl class="alerts"/>
           <dt/>
           <html:messages id="msg" message="true" property="closeAlert">
              <dd>${msg}</dd>
           </html:messages>
      </logic:messagesPresent>
    </div>
    <c:if test="${(not empty activeTags) and (activeTags) and (not empty accountclosure) and (accountclosure eq true)}" > <!--checking active tags starts -->

		
					<div>
					<c:if test="${(empty Done) or (Done eq false)}">
					<div id="yellow" style="position:relative; width:400px; text-align:left; font-family:Arial;background-image:url(meta/media/common/alert-image.png);background-repeat: no-repeat; padding-left:40px;" ><br />
						All active tags will be inactivated ...... <br />
						<a href= "#" title="Cancel inactivation of all tags" onClick="cancel()">Cancel</a>&nbsp;&nbsp;&nbsp;<a href="#" title="Continue for terms and conditions" onclick ="showTerms()">Continue</a><br /><br />
					</div>

					<div id="agreement" style="position:relative;width:600px; height:40px;text-align:left ; font-family:Arial;background-repeat: no-repeat;display:none" >
					Before Closing your account please read and accept <a href="#" title="Check terms and conditions" onClick="popUp('${pageContext.request.contextPath}/jsp/common/termsAndConditions.jsp');">Account Closure Terms And Conditions </a>
					</div>
					<div id="agreement-image" style="position:relative;width:700px; height:308px;text-align:left ; font-family:Arial;background-image:url(meta/media/common/terms-condition.png);display:none;background-repeat: no-repeat;">
					<label><br />
					<div style="color:green;font-style:bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Termination of Agreement/Ownership of an EZ TAG Account</div>		  <br /><br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HCTRA or the Customer may terminate this EZ Agreement at any time<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;upon giving the other party written notice of theintent to<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;terminate. It is the Customer�s responsibility to confirm this<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;notice has been received by HCTRA. Any unusedportion of the pre-paid<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EZ TAG Account balance will be returned to our Customer within forty-five<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(45) business days from the date the EZ TAG Account is deactivated and<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;closed by HCTRA. HCTRA may deactivate an EZ TAG Account for<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;non-compliance of any terms contained in this EZ Agreement. Continued<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attempted use of a deactivated EZ TAG Account will result in violations!<br />
					 
					 <br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="Do not aceept terms and conditions" onClick="showNonRefund()">I do not Accept </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <c:choose>
					 <c:when test="${acctInfo.balanceAmt > 0}">
					 <a href="#" title="Accept terms and conditions" onClick="showrefund();">Accept </a>
					 </c:when>
					 <c:when test="${acctInfo.balanceAmt == 0}">
					  <a href="#" title="Accept terms and conditions" onClick="dontshowrefund();">Accept </a>
					 </c:when>
					 <c:when test="${acctInfo.balanceAmt == 0}">
					  <a href="#" title="Accept terms and conditions" onClick="dontshowrefund();">Accept </a>
					 </c:when>
					 </c:choose>
					</label></div>
					</c:if>
					
					<div id="agreement-image-text" style="position:relative;width:700px; height:308px;text-align:left ; font-family:Arial;display:none;" >					
					 </div>	


					 <div id="refund" style="position:relative;width:600px;text-align:left ; font-family:Arial;background-repeat: no-repeat;<c:if test="${empty acctInfoRefund}">display:none;</c:if>" >
					 <c:choose>
					 <c:when test="${acctInfo.balanceAmt > 0}">
					Your account shows a balance of <strong>$<fmt:formatNumber value="${acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></strong> as of ${currentDate}.<br />
<p>Please select refund method below and click "Continue".</p>
						<!-- radio button -->
						<div>
						<c:choose>
						  <c:when test="${billingInfo.creditCard}">
							<input type="radio" name="paymentType" value="C" onClick="alertChange('credit-card-alert','check-alert')" <c:if test="${not empty paymenttype and paymenttype eq 'creditC'}">checked</c:if>> Credit Card </input>
							<input type="radio" name="paymentType" value="O" onClick="alertChange('check-alert','credit-card-alert')" <c:if test="${empty paymenttype or paymenttype eq 'checkV'}">checked </c:if>> Check </input>
							<br />
                                                        <div id="credit-card-alert" <c:if test="${empty paymenttype or paymenttype eq 'checkV'}"> style="display:none;" </c:if>>
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCredit}<br />
                                                        </div>
                                                        <div id="check-alert" <c:if test="${not empty paymenttype and paymenttype eq 'creditC'}">style="display:none;" </c:if> >
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCheck}<br />
                                                        </div>
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTags();" />
							<br />
						  </c:when>
						  <c:otherwise>
							<input type="radio" name="paymentType" value="E" checked> Check </input><br />
							<!--<input type="radio" name="paymentType" value="EFT"> EFT </input> <br />-->
                                                        <div id="check-alert">
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCheck}<br />
                                                        </div>
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsEFT();" /><br />
						  </c:otherwise>
						</c:choose>
						</div>
						<!-- radio button -->
					</c:when>
                                         <c:when test="${acctInfo.balanceAmt == 0}">
                                         As your current account balance is ZERO, no refund is posted for your account. Click <strong>Continue</strong> to complete the account closure process. <br />					
						<!-- radio button -->
						<div><br />
						<c:if test="${(billingInfo.creditCard)}">
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsZero('CO');" /><br />
                                                </c:if>
						<c:if test="${(billingInfo.creditCard) eq false}">
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsZero('EO');" /><br />
                                                </c:if>
						</div>
						<!-- radio button -->
					</c:when>
                                        </c:choose>
					</div>
					</div>
					
</c:if>
					
					<!--checking active tags ends -->

					<!--checking active tags false starts -->
					<c:if test="${(not empty activeTags) and (activeTags eq false) and (not empty accountclosure) and (accountclosure eq true)}" > 

					<div>
					<c:if test="${(empty Done) or (Done eq false)}" >
					<div id="agreement" style="position:relative;width:600px; height:40px;text-align:left ; font-family:Arial;background-repeat: no-repeat;" >
					Before Closing your account please read and accept <a href="#" title="Check terms and conditions" onClick=" popUp('${pageContext.request.contextPath}/jsp/common/termsAndConditions.jsp');">Account Closure Terms And Conditions </a>
					</div>
					
					<div id="agreement-image" style="position:relative;width:700px; height:308px;text-align:left ; font-family:Arial;background-image:url(meta/media/common/terms-condition.png);display:none;background-repeat: no-repeat;" >
					<label><br />
					<div style="color:green;font-style:bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Termination of Agreement/Ownership of an EZ TAG Account</div>		  <br /><br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HCTRA or the Customer may terminate this EZ Agreement at any time<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;upon giving the other party written notice of theintent to<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;terminate. It is the Customer�s responsibility to confirm this<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;notice has been received by HCTRA. Any unusedportion of the pre-paid<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EZ TAG Account balance will be returned to our Customer within forty-five<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(45) business days from the date the EZ TAG Account is deactivated and<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;closed by HCTRA. HCTRA may deactivate an EZ TAG Account for<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;non-compliance of any terms contained in this EZ Agreement. Continued<br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attempted use of a deactivated EZ TAG Account will result in violations!<br />
					 
					 <br />
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="Do not aceept terms and conditions" onClick="showNonRefund()">I do not Accept </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <c:choose>
					 <c:when test="${acctInfo.balanceAmt > 0}">
					 <a href="#" title="Accept terms and conditions" onClick="showrefund();">Accept </a>
					 </c:when>
					 <c:when test="${acctInfo.balanceAmt == 0}" >
					<a href="#" title="Accept terms and conditions" onClick="dontshowrefund();">Accept </a>
					 </c:when>					  
					 </c:choose>

					</label>
					</c:if>

					</div>


					<div id="agreement-image-text" style="position:relative;width:700px; height:308px;text-align:left ; font-family:Arial;display:none;" >					
					 </div>	


                                        <div id="refund" style="position:relative;width:600px; text-align:left ; font-family:Arial;background-repeat: no-repeat;<c:if test="${empty acctInfoRefund}">display:none;</c:if>" >
					<c:choose>
					<c:when test="${acctInfo.balanceAmt > 0}">
					Your account shows a balance of <strong>$<fmt:formatNumber value="${acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></strong> as of ${currentDate}.Please select refund method below and click "Continue". <br />
						<!-- radio button -->
						<div>
						<c:choose>
						<c:when test="${(billingInfo.creditCard)}">
							<input type="radio" name="paymentType" value="C" onClick="alertChange('credit-card-alert','check-alert')" <c:if test="${not empty paymenttype and paymenttype eq 'creditC'}">checked</c:if>> Credit Card </input>
							<input type="radio" name="paymentType" value="O" onClick="alertChange('check-alert','credit-card-alert')" <c:if test="${empty paymenttype or paymenttype eq 'checkV'}">checked </c:if>> Check </input><br />
                                                        
                                                         <div id="credit-card-alert" <c:if test="${empty paymenttype or paymenttype eq 'checkV'}"> style="display:none;" </c:if>>
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCredit}<br />
                                                        </div>
                                                        <div id="check-alert" <c:if test="${not empty paymenttype and paymenttype eq 'creditC'}">style="display:none;" </c:if>>
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCheck}<br />
                                                        </div>                                                        
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTags();" /><br />
                                                        <div></div>
						</c:when>
						<c:otherwise>
							<input type="radio" name="paymentType" value="E" checked> Check </input><br />
							<!--<input type="radio" name="paymentType" value="EFT"> EFT </input> <br /> -->
                                                         <div id="check-alert">
                                                          &nbsp;&nbsp;&nbsp;&nbsp;${closeAlertTextForCheck}<br />
                                                        </div>
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsEFT();" /><br />
                                                        <div></div>
						</c:otherwise>
						</c:choose>
						</div>
						<!-- radio button -->
					</c:when>
                                        <c:when test="${acctInfo.balanceAmt == 0}">
					As your current account balance is ZERO, no refund is posted for your account. Click <strong>Continue</strong> to complete the account closure process. <br />
						<!-- radio button -->
						<div><br />
						<c:if test="${(billingInfo.creditCard)}">
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsZero('CO');" /><br />
                                                        <div></div>
						</c:if>
						<c:if test="${(billingInfo.creditCard) eq false}">
							<input type="button" value="Continue" alt="Continue close account" title="&rarr; Continue close account" onClick="inactivateEZTagsZero('EO');" /><br />
                                                        <div></div>
							</c:if>
						</div>
						<!-- radio button -->
					</c:when>                                       
					</c:choose>
					</div>					
					<c:if test="${(empty Done) or (Done eq false)}" >
					</div>
					</c:if>
					</c:if>
					
<br />					<!--checking active tags ends -->
</c:if>
