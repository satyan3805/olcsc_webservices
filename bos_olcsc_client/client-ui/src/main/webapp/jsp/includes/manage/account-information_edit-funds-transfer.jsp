<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changePaymentMethodeft.do" styleId="mainForm">
                <input type="hidden" name="paymentType" value="eft" />

		<h1 id="account-information-edit-username">Account Information &ndash; edit billing and payment information</h1>
		
			<div class="section">
				
                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="changePaymentMethodError">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>
                                
                                <c:if test="${billingInfoForm.paymentType == 'eft'}"><p>Your current payment method is to <strong>withdraw funds from your bank</strong>.</c:if>
                                If you prefer, you can change your payment method to <a href="#credit-card-choice" onclick="creditcardLink();" >bill your credit card</a>.</p>

				<h2 id="set-up-your-fund-withdrawal">Billing Information - Electronic Funds Transfer</h2>

				<fieldset id="eft-fields">

					<dl>

						<dt class="first-dt-dd-pair">Account type:</dt>
							<dd class="first-dt-dd-pair">
								<ul>
                                                                        <li><label for="personal-bank-account-1"><input type="radio" class="radio-button" value="PC" id="personal-bank-account-1" name="bankAcctType" <c:if test="${billingInfoForm.bankAcctType=='PC' || billingInfoForm.bankAcctType==null}">checked="checked"</c:if> />Personal bank account</label></li>

                                                                        <li><label for="business-bank-account-1"><input type="radio" class="radio-button" value="CC" id="business-bank-account-1" name="bankAcctType" <c:if test="${billingInfoForm.bankAcctType=='CC'}">checked="checked"</c:if> />Business bank account</label></li>
								</ul>
							</dd>


						<dt><label for="bank-routing-number-1">Routing number:</label></dt>
							<dd>
								*<input type="text" class="textfield" id="bank-routing-number-1" name="bankRoutingNumber" value="${billingInfoForm.bankRoutingNumber}" onblur="javascript:cleanNumericField(this);"/>
								<p class="help">The routing number is the first group of digits on your check.</p>
							</dd>
					</dl>

					<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-routing-number.gif" width="300" height="80" alt="The routing number is 1 to 17 digits between the : symbols." />

					<dl>

						<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Account number:</label></dt>
							<dd class="first-dt-dd-pair">
								*<input type="text" class="textfield" id="bank-account-number-1" name="bankAcctNumber" value="${billingInfoForm.bankAcctNumber}" onblur="javascript:cleanNumericField(this);" onselect="javascript:unselect();"/>
								<p class="help">The account number is the second group of numbers on your check.</p>
							</dd>
							<dt class="first-dt-dd-pair"><label for="bank-account-number-1">Confirm account number:</label></dt>
									<dd class="first-dt-dd-pair">
                                                                                *<input type="text" class="textfield" id="bank-account-number-2"  value="${billingInfoForm.bankAcctNumber}" onselect="javascript:unselect();" />
                                                                              
                                                                        </dd>   
					</dl>

					<img src="${pageContext.request.contextPath}/meta/media/sign-up-process/thumbnail-account-number.gif" width="300" height="80" alt="The account number is 9 digits between the : and ^ symbols." />

				</fieldset>

			</div> <!-- end of section -->
		
			<ul class="form-actions">
                                <li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
                                <li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:doSubmit();" /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<!--#include virtual="/includes/manage/status-bar-manage.shtml" -->
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit EFT Account Info";

function creditcardLink() {
    response=confirm('Changing your account payment method from a direct debit (EFT) to credit card will decrease your minimum deposit balance from $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForEFT}" minFractionDigits="2" maxFractionDigits="2"/> to $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForCC}" minFractionDigits="2" maxFractionDigits="2"/> which is replenished when your account balance falls at or below $<fmt:formatNumber value="${accountForm.reqLowBalLevelForCC}" minFractionDigits="2" maxFractionDigits="2"/>. \n\nIf you agree, please click OK to continue or click CANCEL to return to the previous screen.');
    if (response==true) {
        document.forms[0].paymentType.value="credit";
        document.forms[0].action= "${pageContext.request.contextPath}/accountInformation/dispChangePaymentMethod.do";
        document.forms[0].submit();
    } else {
        return false;
    }
}

function doSubmit(){
	
	<c:if test="${billingInfoForm.paymentType=='eft'}">
	if (document.getElementById("bank-account-number-2").value.toUpperCase() != document.getElementById("bank-account-number-1").value.toUpperCase()) {
        alert("'Confirm Bank Account Number' should match 'Bank Account Number'");
        changeTextFieldWhite(document.getElementById("bank-account-number-1"));
		changeTextFieldColor(document.getElementById("bank-account-number-2"));
		return;
	}
	</c:if>

        document.forms[0].submit();

}
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}
</script>