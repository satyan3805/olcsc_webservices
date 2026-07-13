<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${not empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.violations}">

	<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="zipcash.detailViolation.header"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'violationModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">

<div id="violationModule" class="switchcontent">

<br/>
    <div id="error">
        <fmt:message key="zipcash.detailViolation.note1">
            <fmt:param> <etcc-extended:Translation property="contactPhoneNumber"/></fmt:param> 
        </fmt:message>
    </div>

<table cellspacing="0" class="datapayment">

                    <tr>
                          <td></td>
                          <td></td>
                          <td></td>
			  <td></td>
                          <td></td>
                          <td class="align-numbers"><strong><fmt:message key="label.selectAll"/></strong></td>
                          <td class="align-other"><input type="checkbox" name="checkAllViolation" onclick="selectUnselectViolation();updateTotal();"/></td>
                        </tr>


                    <tr class="odd">
                          <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationId"/></th>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.location"/></th> 
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.date"/></th>    
			  <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.licPlate"/>/<fmt:message key="receipt.acctinfo.state"/></th>                  
                          <th class="data-th-left"><fmt:message key="tagInfo.status"/></th>                           
                          <th class="data-th-right" style="text-align:right"><fmt:message key="PaymentInfoForm.confirmViolation.amount"/></th>
                          <th class="data-th-center" nowrap><fmt:message key="zipcash.detailViolation.payNow"/></th> 
                        </tr>
                          
<c:forEach items="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.violations}" var="violation" varStatus="varStatus">
    <tr>
    <td>${violation.id}</td>
    <td nowrap><a class="info" href="#">${violation.location}<span>${violation.locationDesc}</span></a></td>
    <td nowrap><fmt:formatDate value="${violation.timestamp.time}" pattern="MM/dd/yyyy HH:mm"/></td>
    <td>${violation.licPlate}(${violation.licState})</td>
    <td>${violation.status}</td>
    <td class="align-numbers"><fmt:formatNumber value="${violation.cashAmount}" type="currency" currencySymbol="$"/></td>
    <td class="align-other"><fmt:formatNumber var="amount" value="${violation.cashAmount}"/><fmt:formatNumber var="feeAmount" value="${violation.onlineFee}"/><html:multibox property="payViolation" value="${violation.id}" styleId="${varStatus.index}[**SPLIT**]${amount}[**SPLIT**]${feeAmount}" onclick="updateTotal();"/></td>
  </tr>
</c:forEach>
  <tr>
    <td colspan="5"></td>
    <td class="align-subtotal">__________<br><fmt:formatNumber value="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.violationAmount}" type="currency" currencySymbol="$"/></td>
    <td></td>
  </tr>
</table>
  
  </div>

<br/>
<table cellspacing="0" class="datapayment">
<%-- 
  <tr>
    <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.detailViolation.uninvoicedTotal"/>:</strong></td>
    <td style="text-align:right; width:80px"><strong><div id="totalViolationAuthorizedAmount"><fmt:formatNumber value="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.violationAmount}" type="currency" currencySymbol="$"/></div></strong></td>
  </tr>
--%>  
  <tr>
    <td style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="zipcash.detailInvioce.totalViolationAuthorized"/>:</strong></td>
    <td style="text-align:right; width:80px;text-decoration:overline"><strong><div id="totalViolationAuthorized">&nbsp;</div></strong></td>                        
  </tr>

</table>

<table cellspacing="0" class="datapayment">
<tr>
<td colspan="2">
<font class="font-small"><fmt:message key="zipcash.detailViolation.note"/></font>
</td>
</tr>
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

var elements = document.getElementsByName( "payViolation" );
if ( elements )
{
  for ( i = 0; i < elements.length; i++ )
  {
    if ( elements[i].checked )
    {
      document.PaymentDetailForm.checkAllViolation.checked = true;  
    }
  }
}

function selectUnselectViolation(checked)
{
  var checked = document.PaymentDetailForm.checkAllViolation.checked;
  var elements = document.getElementsByName( "payViolation" );
  if ( elements )
  {
    for ( i = 0; i < elements.length; i++ )
    {
      elements[i].checked = checked;
    }
  }

}

</script>

</c:if>