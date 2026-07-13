<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

<c:choose>
    <c:when test="${not empty sessionScope.billingContext}">
        <c:set var="iniValue">
        	<fmt:formatNumber value="${sessionScope.billingContext.paymentAmt}" 
                        minFractionDigits="2" 
                        maxFractionDigits="2"/>
        </c:set>
    </c:when>
    <c:otherwise>
        <c:set var="iniValue" value ="10.00" />
    </c:otherwise>
</c:choose>

    <div id="content">
                
                

        
                    <form id="make-payments" name="paymentForm" action="${pageContext.request.contextPath}/makeAcctPmtDisplay.do" method="post">

                                    
                            <h1 id="account-activity-transactions">Payments &amp; Orders  &ndash; make a payment</h1>
                            <c:if test="${retPageInfo}">
                              <input type="hidden" value="info" name="pageReturn" />
                            </c:if>
    
                            <div class="section">
                                <logic:messagesPresent message="true">
                                    <dl class="alerts"/>
                                    <dt/>
                                    <html:messages id="msg" message="true" property="alerts">
                                        <dd>${msg}</dd>
                                    </html:messages>
                                </logic:messagesPresent>
                                <logic:messagesPresent message="false">
                                    <dl class="errors"/>
                                    <html:messages id="msg" message="false">
                                        <dd><bean:write name="msg"/></dd>
                                    </html:messages>
                                </logic:messagesPresent>

                            </div> <!-- end of section -->

                            
                            <c:if test="${not (acctInfo.suspensionFlag and acctInfo.violationFlag)}">
                            <h2>EZ TAG Account</h2>
                            <p>You may add to the balance of your EZ TAG Account by making a payment.</p>
    
                            <fieldset class="add-to-ez-account-fields">
                                    <dl>
                                            <dt class="first-dt-dd-pair">
                                                    <label for="how-much-will-you-pay">How much would you like to pay?</label>
                                                    <p class="help">Minimum payment is $1</p>
                                            </dt>
                                                    <dd class="first-dt-dd-pair">
                                                            $ <input type="text" id="payAmount" name="payAmount" value="${iniValue}" style="text-align:right;" onblur="return checkAmt();"/>
                                                    </dd>
                                    </dl>
                            </fieldset>
    
    
    
                            <h2>Total Payment</h2>
                            <p class="total-payment">Total Payment: $<span id="totalSpan">${iniValue}</span></p>
    
                                    <ul class="form-actions">
    
                                            <li><input id="pay-selected-invoices" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/confirm-billing-information.gif" value="Confirm Billing Info" onclick="javascript:doSubmit();return false;"/></li>
    
                                    </ul> <!-- end of form-actions -->
                         </c:if>

            <div id="tertiary-navigation-and-or-page-controls">

                <!--#include virtual="/includes/manage/tertiary-navigation-manage-payments-and-orders.shtml" -->
                                <jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
                                
            </div> <!-- end of tertiary-navigation-and-or-page-controls -->

                    </form>
               

    </div> <!-- end of content -->

    <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Make Payment";
var submitted = false;

    function checkAmt()
    {
        var amt = document.paymentForm.payAmount.value;
        
        amt = amt.replace(/[\,]/g, "");

        //amt = (1*amt).toFixed(2);
        document.paymentForm.payAmount.value = amt;
        document.getElementById("totalSpan").innerHTML = amt;
        return true;
    }
    
    function doSubmit() {
        
        if (!submitted)
        {
            var amt = document.paymentForm.payAmount.value;
            
            amt = amt.replace(/[\,]/g, "");
            
            if (amt == '' || (/^((\d+)||(\d+,\d\d\d))(\.\d+)?$/.test(amt)==false) || amt < 1.00  ) 
            {
                alert("Payment amount is invalid.");
                document.getElementById("payAmount").focus();
                changeTextFieldColor(document.getElementById("payAmount"));
                return false;
            }
            changeTextFieldWhite(document.getElementById("payAmount"));
            amt = (1*amt).toFixed(2);
            document.paymentForm.payAmount.value = amt;
            document.getElementById("totalSpan").innerHTML = amt;
            submitted = true;
            document.forms[0].submit();
            //return true;
        }
    }
    
function changeTextFieldColor(field){
    field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
    field.style.background="#FFFFFF";
}
    
</script>
