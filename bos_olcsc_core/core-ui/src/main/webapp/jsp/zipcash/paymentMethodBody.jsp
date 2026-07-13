<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.HttpsImport"%>

<etcc-extended:autocompleteOffForm action="zipCashProcessPaymentInfo.do" method="POST">
       <html:hidden property="billingFname"/>
        <html:hidden property="billingLname"/>
        <html:hidden property="billingAddress"/>
        <html:hidden property="billingAddressLine2"/>
        <html:hidden property="billingCity"/>
        <html:hidden property="billingState"/>
        <html:hidden property="billingZipcode"/>
        <html:hidden property="billingPlus4"/>
        <html:hidden property="creditCardNumber"/>
       <html:hidden property="creditCardType"/>
       <html:hidden property="creditCardExpirationDate"/>
       <html:hidden property="billingPhone"/>
       <html:hidden property="billingPhoneExtn"/>
        <html:hidden property="tokenID"/>
                <html:hidden property="savePayment"/>
    <html:hidden property="accountBillingMethodId"/>
                      	<c:if test="${not empty urlVal}">
           <% out.println(HttpsImport.importUrl((String)request.getAttribute("urlVal")));%> 
		</c:if>			

                        <p><a href="zipCashDisplayPaymentDetail.do" class="prev" onclick="this.className='prev-disabled'; return checkSubmitted();"><fmt:message key="button.back"/></a>
			<a href="javascript:document.PaymentInfoForm.submit();" class="next" tabindex="14" onclick="this.className='next-disabled';return checkSubmitted();"><fmt:message key="button.next"/></a></p>
			</etcc-extended:autocompleteOffForm>
<script type="text/javascript">
if(document.getElementById('save')!=null)
	document.getElementById('save').className += " button";
if(document.getElementById('clear')!=null)
	document.getElementById('clear').className += " button";

var submitted = false;


function checkSubmitted()
{
    if (document.PaymentInfoForm.creditCardNumber.value != "" &&
            document.PaymentInfoForm.creditCardNumber.value.search(/^\*\*\*\*\*\*-\d{4}$/) != 0) {
        document.PaymentInfoForm.creditCardNumberNew.value = 
            document.PaymentInfoForm.creditCardNumber.value;
    } else {
        document.PaymentInfoForm.creditCardNumberNew.value = '';
    }
    if (submitted == false)
    {
        submitted = true;
        return true;
    }
    return false;
}

function toggleSelection(selected)
{
    var paymentMethodExist = '${not empty PAYMENT_CONTEXT.paymentMethod}';
    var cardNumber = '';
    if (paymentMethodExist)
        cardNumber = '${PAYMENT_CONTEXT.paymentMethod.cardNumber}' ;
        
    if (selected == 'Y' && cardNumber == '')
    {
        document.getElementById("convertMsg").style.display='';  
    }
    else
    {
        document.getElementById("convertMsg").style.display='none'; 
    }
}

function onCreditCardTypeChange()
{
  document.PaymentInfoForm.creditCardNumber.value = "";
}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>