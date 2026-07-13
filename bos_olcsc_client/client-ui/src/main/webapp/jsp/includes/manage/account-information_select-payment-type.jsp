<%@ include file="/jsp/common/Taglibs.jsp" %>

<div id="content-container">

	<div id="content">

                <form id="sign-up" action="${pageContext.request.contextPath}/accountInformation/dispChangePaymentMethod.do" method="post">
                        <input type="hidden" name="paymentType" value="" />

			<div class="section">

				<h1>Payment Method</h1>

                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="paymentTypeError">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>

				<div>
                                <dl class="choices" id="payment-methods">

					<dt>How would you like to pay?</dt>

						<dd><a id="credit-card-link" href="#credit-card-choice" onclick="creditCardLink();">Bill my credit card</a></dd>
						<dd><a id="withdraw-funds-link" href="#withdraw-funds-choice" onclick="withdrawFundLink();">Withdraw funds from my bank</a></dd>

				</dl> <!-- end of choices -->
                                </div>

			</div> <!-- end of section -->
                        
			<ul class="form-actions">
                                <li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
			</ul> <!-- end of form-actions -->

		</form>

	</div> <!--end of content -->
        
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Payment Method";

    function creditCardLink()
    {
        response=confirm('Changing your account payment method from invoiced to credit card will require a minimum deposit balance of $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForCC}" minFractionDigits="2" maxFractionDigits="2"/> which will be replenished when your account balance falls below $<fmt:formatNumber value="${accountForm.reqLowBalLevelForCC}" minFractionDigits="2" maxFractionDigits="2"/>. \n\nIf you agree, please click OK to continue or click CANCEL to return to the previous screen.');
        if (response==true) {
            document.forms[0].paymentType.value="credit";
            changeLink();
        } else {
            return false;
        }
    }
    
    function withdrawFundLink()
    {
        response=confirm('Changing your account payment method from invoiced to direct debit (EFT) will require a minimum deposit balance of $<fmt:formatNumber value="${accountForm.reqMinRebillAmtForEFT}" minFractionDigits="2" maxFractionDigits="2"/> which will be replenished when your account balance falls below $<fmt:formatNumber value="${accountForm.reqLowBalLevelForEFT}" minFractionDigits="2" maxFractionDigits="2"/>. \n\nIf you agree, please click OK to continue or click CANCEL to return to the previous screen.');
        if (response==true) {
            document.forms[0].paymentType.value="eft";
            changeLink();
        } else {
            return false;
        }
    }

    function changeLink()
    {
        //document.forms[0].action= "${pageContext.request.contextPath}/invoicePaymentDisplay.do";
        document.forms[0].submit();
    }

</script>
