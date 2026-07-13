<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${ (sessionScope.PAYMENT_CONTEXT.callBack == 'makePayment')
	 || (sessionScope.PAYMENT_CONTEXT.originalTagAmount > 0)	 
	 || (sessionScope.PAYMENT_CONTEXT.depAmount > 0)
	 || (sessionScope.PAYMENT_CONTEXT.tagSalePrice > 0)}">

	<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="PaymentInfoForm.caption"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'tagModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">


	<div id="tagModule" class="switchcontent">
	
		<c:if test="${requestScope.SuggestedPaymentMessage}">
		<br/><p><fmt:message key="PaymentInfoForm.suggestedPayment"><fmt:param value="$10.00"/></fmt:message></p><br/>
		</c:if>
	
	</div>

<table cellspacing="0" class="datapayment">
  <tr>
    <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.tagAmount"/>:</strong></td>
    <td style="text-align:right; width:80px">
    <strong>$</strong><html:text property="tagAmount" styleClass="text" style="text-align:right" size="5" onblur="return checkTagAmountFormat();"/> 
    </td>
  </tr>
  <!-- only if cash amount-->
  <c:if test="${sessionScope.PAYMENT_CONTEXT.depAmount > 0 || sessionScope.PAYMENT_CONTEXT.tagLostFee > 0 ||
  sessionScope.PAYMENT_CONTEXT.tagSalePrice > 0  }">

  
      <c:if test="${sessionScope.PAYMENT_CONTEXT.depAmount > 0 }">
      <tr>
        <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.depAmount"/>:</strong></td>
        <td style="text-align:right;"><div id="tagDepositAmount">&nbsp;</div>
        </td>
      </tr>
      
       <%--     <tr>
        <td style="text-align:right;">
                (<fmt:message key="PaymentInfoForm.note"><fmt:param value="$25"/></fmt:message>)
        </td>
      </tr> --%>
      
      </c:if>
      
      <c:if test="${sessionScope.PAYMENT_CONTEXT.tagLostFee > 0 }">
      <tr>
        <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.tagLostAmount"/>:</strong></td>
        <td style="text-align:right;">
        $${sessionScope.PAYMENT_CONTEXT.tagLostFee}
        </td>
      </tr>
      </c:if>
          <c:if test="${sessionScope.PAYMENT_CONTEXT.tagSalePrice > 0 }">
      <tr>
        <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.tagSaleAmount"/>:</strong></td>
        <td style="text-align:right;"><div id="tagSalePrice">&nbsp;</td>
      </tr>
      </c:if>
      

      <tr><td>&nbsp;</td></tr>
      <tr>
        <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.total.tagstore"/>:</strong></td>
        <td align="right">
            <div id="totalTagstoreAmount">&nbsp;$<c:out value="${sessionScope.PAYMENT_CONTEXT.originalTagAmount + sessionScope.PAYMENT_CONTEXT.tagSalePrice + sessionScope.PAYMENT_CONTEXT.tagLostFee + sessionScope.PAYMENT_CONTEXT.depAmount}"/></div>
        </td>
      </tr>
    
    </c:if>
</table>


  </td>		
  <td class="panel-right"></td>		
</tr>
<tr>
  <td class="panel-bottomleft"></td>
  <td class="panel-bottomcenter"></td>		
  <td class="panel-bottomright"></td>		
</tr>
</table>
        
<script type="text/javascript">
formatTagAmounts();
function checkTagAmountFormat()
{
    if (document.PaymentDetailForm.tagAmount != null)
    {
        var origTagAmount = ${sessionScope.PAYMENT_CONTEXT.originalTagAmount};
        
        document.PaymentDetailForm.tagAmount.value=document.PaymentDetailForm.tagAmount.value.replace(/\,*/g, "");
        
        if (/^((\d+)||(\d+,\d\d\d))(\.\d\d)?$/.test(document.PaymentDetailForm.tagAmount.value)==false) 
        {
            return false;
        }
        
        updateTotal();
        
        document.PaymentDetailForm.tagAmount.value = (1*document.PaymentDetailForm.tagAmount.value).toFixed(2);
        
        return true;
    }
    else
        return true;
    
}
function formatTagAmounts(){
	if(document.getElementById("tagDepositAmount")){
		document.getElementById("tagDepositAmount").innerHTML="$"+ (1 * ${sessionScope.PAYMENT_CONTEXT.depAmount}).toFixed(2);
	}
	if(document.getElementById("tagSalePrice")){
		document.getElementById("tagSalePrice").innerHTML="$"+ (1 * ${sessionScope.PAYMENT_CONTEXT.tagSalePrice}).toFixed(2);
	}
	if(document.PaymentDetailForm.tagAmount){
		if(document.PaymentDetailForm.tagAmount.value != "" && document.PaymentDetailForm.tagAmount.value * 1 >= 0){
			document.PaymentDetailForm.tagAmount.value=(1 * document.PaymentDetailForm.tagAmount.value).toFixed(2);
		}
	}
	var totTsAmount = ${sessionScope.PAYMENT_CONTEXT.originalTagAmount + sessionScope.PAYMENT_CONTEXT.tagSalePrice + sessionScope.PAYMENT_CONTEXT.tagLostFee + sessionScope.PAYMENT_CONTEXT.depAmount};
	if(document.getElementById("totalTagstoreAmount")){
		document.getElementById("totalTagstoreAmount").innerHTML="$"+ (1 * totTsAmount).toFixed(2);
	}
	
}
</script>

</c:if>	