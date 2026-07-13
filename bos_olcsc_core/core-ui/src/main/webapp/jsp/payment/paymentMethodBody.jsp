<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.HttpsImport"%>
  <div id="pci_web_ui">
      	<c:if test="${not empty urlVal}">
 		   <% out.println(HttpsImport.importUrl((String)request.getAttribute("urlVal")));%> 
		</c:if>		
  </div>	

<etcc-extended:autocompleteOffForm action="DisplayPaymentInfo.do?action=next" method="POST">
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
			
                        
                       

<c:if test="${sessionScope.accountLogin.loginType == 'AC' && sessionScope.PAYMENT_CONTEXT.callBack != 'GetTollTag'}">
  <p><html:radio property="savePayment" value=""  onclick="javascript:toggleSelection('N');" tabindex="12"/> <fmt:message key="PaymentInfoForm.paymentMethod.note1"/></p>
  <p><html:radio property="savePayment" value="Y" onclick="javascript:toggleSelection('Y');" tabindex="13"/> <fmt:message key="PaymentInfoForm.paymentMethod.note2"/></p>
  <div style="display:none;" id="convertMsg">
      <div id="error" class="error">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="PaymentInfoForm.paymentMethod.note3"/>
      </div>
  </div>
</c:if>

            <p><a href="DisplayPaymentDetail.do" class="prev" onclick="this.className='prev-disabled'; return checkSubmitted();">
            	<fmt:message key="button.back"/>
            </a>
            <c:if test="${not empty requestScope.isSaved}">
				<a href="javascript:document.PaymentInfoForm.submit();" class="next" tabindex="14" onclick="this.className='next-disabled';return checkSubmitted();">
				<fmt:message key="button.next"/>
				</a>
			</c:if>
			</p>
</etcc-extended:autocompleteOffForm>
<script type="text/javascript">
if(document.getElementById('save')!=null)
	document.getElementById('save').className += " button";
if(document.getElementById('clear')!=null)
	document.getElementById('clear').className += " button";		

var submitted = false;
<c:if test="${sessionScope.accountLogin.loginType == 'AC' && sessionScope.PAYMENT_CONTEXT.callBack != 'GetTollTag'}">

if (document.PaymentInfoForm.savePayment[1].checked)
{
    toggleSelection('Y');
}
</c:if>

<c:if test="${requestScope.fromBadCCEditScreen == 'true'}">
document.PaymentInfoForm.savePayment[0].checked = false;
document.PaymentInfoForm.savePayment[0].disabled = true;
document.PaymentInfoForm.savePayment[1].checked = true;
</c:if>
function checkSubmitted()
{
    if (document.PaymentInfoForm.creditCardNumber.value != "" &&
            document.PaymentInfoForm.creditCardNumber.value.search(/^\*\*\*\*\*\*-\d{4}$/) != 0) {
        //document.PaymentInfoForm.creditCardNumberNew.value = 
        //    document.PaymentInfoForm.creditCardNumber.value;
    } else {
        //document.PaymentInfoForm.creditCardNumberNew.value = '';
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
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>