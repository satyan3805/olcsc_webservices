<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${ (sessionScope.PAYMENT_CONTEXT.callBack != 'makePayment') 
	&& (sessionScope.PAYMENT_CONTEXT.originalTagAmount == 0) 
	&& (sessionScope.PAYMENT_CONTEXT.depAmount > 0)}">

    <table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="PaymentInfoForm.caption"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'tagModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">

<table cellspacing="0" class="datapayment">
  <!-- only if cash amount-->
  <c:if test="${sessionScope.PAYMENT_CONTEXT.depAmount > 0}">
      <tr><td>&nbsp;</td></tr>            
      <tr>
        <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.depAmount"/>:</strong></td>
        <td style="text-align:right;">
        $${sessionScope.PAYMENT_CONTEXT.depAmount}
        </td>
      </tr>
      <%--<tr>
        <td style="text-align:right;">
                (<fmt:message key="PaymentInfoForm.note"><fmt:param value="$25"/></fmt:message>)
        </td>
      </tr> --%>
      <tr><td>&nbsp;</td></tr>            
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
</script>

</c:if>