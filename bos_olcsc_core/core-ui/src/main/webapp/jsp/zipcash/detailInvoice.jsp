<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${not empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices}">

<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="zipcash.detailInvoice.header"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'invoiceModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">


<div id="invoiceModule" class="switchcontent">

<br/>
    <div id="error">
        <fmt:message key="zipcash.detailInvoice.note">
            <fmt:param> <etcc-extended:Translation property="contactPhoneNumber"/> </fmt:param>
        </fmt:message>
    </div>

<table cellspacing="0" width="95%" class="datapayment">



                    <tr>
                          <td colspan="6">&nbsp;</td>
                          <td colspan="2" class="align-numbers"><strong><fmt:message key="label.selectAll"/></strong></td>
                          <td class="align-other"><input type="checkbox" name="checkAllInvoice" onclick="selectUnselectInvoice();updateTotal();"/></td>
                        </tr>



 <tr class="odd">
			  <th class="data-th-left"><fmt:message key="PaymentInfoForm.detailInvoice.details"/></th>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceId"/></th>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceState"/></th>
			 			  <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceDate"/></th>
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.dueDate"/></th> 
                          <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.licPlate"/>/<fmt:message key="receipt.acctinfo.state"/></th>                           
                          <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.lateFee"/></th>                           
                          <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.newTrans"/></th>                           
                          <th class="data-th-right" style="text-align:right"><fmt:message key="zipcash.detailInvoice.invoiceAmount"/></th>                          
                          <th class="data-th-center" style="text-align:right" nowrap="true"><fmt:message key="PaymentInfoForm.detailInvoice.payNow"/>?</th>                        
                        </tr>

  <c:forEach items="${sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
  <tr>
    <td><img src="${pageContext.request.contextPath}/images/common/buttons/plus.gif" onclick="javascript:show_or_hide(this,'${invoice.id}')"/></td>
    <td>${invoice.id}</td>
    <td><fmt:formatDate value="${invoice.invoiceDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
    <td><fmt:formatDate value="${invoice.dueDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
    <td>${invoice.licPlateNumber}(${invoice.licPlateState})</td>
    <td class="align-numbers"><fmt:formatNumber value="${invoice.lateFee==null?0:invoice.lateFee}" type="currency" currencySymbol="$"/></td>
    <td class="align-numbers"><fmt:formatNumber value="${invoice.newTrans==null?0:invoice.newTrans}" type="currency" currencySymbol="$"/></td>
    <td class="align-numbers"><fmt:formatNumber value="${invoice.newTrans + invoice.lateFee}" type="currency" currencySymbol="$"/></td>
    <td class="align-other">
        <c:set var="amount" value="${invoice.amount}"/>
        <c:set var="veaAmount" value="${invoice.amount}"/>
        <c:set var="feeAmount" value="${invoice.onlineFee}"/>
        <c:set var="lateFee" value="${invoice.lateFee}"/>
        <c:set var="premium" value="${invoice.premium}"/>
        <c:set var="mailHandlingFee" value="${invoice.mailHandlingFee}"/>
        <c:set var="newTrans" value="${invoice.newTrans}"/>
        <c:set var="pastDue" value="${invoice.pastDueAmount}"/>
        <c:set var="pastDueLateFee" value="${invoice.pastDueLateFeeAmount}"/>
        <c:set var="pastDueMailFee" value="${invoice.pastDueMailFeeAmount}"/>
        <html:multibox property="payInvoice" value="${invoice.id}" styleId="${varStatus.index}[**SPLIT**]${amount}[**SPLIT**]${veaAmount}[**SPLIT**]${feeAmount}[**SPLIT**]${lateFee}[**SPLIT**]${premium}[**SPLIT**]${mailHandlingFee}[**SPLIT**]${newTrans}[**SPLIT**]${pastDue}[**SPLIT**]${pastDueLateFee}[**SPLIT**]${pastDueMailFee}" onclick="incrementCheck(this.form, this);updateTotal();"/>
    </td>
    
  </tr>
  
  <tr style="display: none;" id="${invoice.id}">
    <td></td>
    <td colspan="8">
        <table>
            <tr><td>
                <c:set var="endCtr" value="${invoice.violationSize div 2}"/>
                <c:if test="${invoice.violationSize % 2 > 0}">
                    <c:set var="endCtr" value="${endCtr + 1}"/>
                </c:if>
                <table cellspacing="0" cellpadding="0">
                 <tr>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationId"/></th>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationDate"/></th>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationLane"/></th>
                 </tr>
                 <c:forEach items="${invoice.violations}" var="invoiceViolation" varStatus="violationStatus" end="${endCtr-1}">
                 <tr>
                   <td>${invoiceViolation.id}</td>
                   <td><fmt:formatDate value="${invoiceViolation.timestamp.time}" pattern="MM/dd/yyyy HH:mm"/></td>
                   <td><a class="info" href="#">${invoiceViolation.location}<span>${invoiceViolation.locationDesc}</span></a></td>
                 </tr>
                 </c:forEach>
                 <tr>
                   <td>&nbsp;</td>
                   <td></td>
                   <td></td>
                 </tr>
            
                </table>
            </td>
            <td>
                <table cellspacing="0" cellpadding="0">
                 <tr>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationId"/></th>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationDate"/></th>
                   <th class="data-th-left"><fmt:message key="zipcash.detailInvoice.violationLane"/></th>
                 </tr>
                 <c:forEach items="${invoice.violations}" var="invoiceViolation" varStatus="violationStatus" begin="${endCtr}">
                 <tr>
                   <td>${invoiceViolation.id}</td>
                   <td><fmt:formatDate value="${invoiceViolation.timestamp.time}" pattern="MM/dd/yyyy HH:mm"/></td>
                   <td><a class="info" href="#">${invoiceViolation.location}<span>${invoiceViolation.locationDesc}</span></a></td>
                 </tr>
                 </c:forEach>
                 <tr>
                   <td>&nbsp;</td>
                   <td></td>
                   <td></td>
                 </tr>
            
                </table>
            </td>
        </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td colspan="5"></td>
    <td colspan="2" class="align-subtotal" nowrap="true"><fmt:message key="zipcash.detailInvoice.invoiceAmount"/>:</td>
    <td class="align-subtotal"><fmt:formatNumber value="${invoice.newTrans + invoice.lateFee}" type="currency" currencySymbol="$"/></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="5"></td>
    <td colspan="2" class="align-subtotal"><fmt:message key="zipcash.detailInvoice.pastDue"/>:</td>
    <td class="align-subtotal"><fmt:formatNumber value="${(invoice.pastDueAmount==null?0:invoice.pastDueAmount)
        + (invoice.pastDueLateFeeAmount==null?0:invoice.pastDueLateFeeAmount)}" type="currency" currencySymbol="$"/></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="5"></td>
    <td colspan="2" style="text-align:right;text-decoration:overline">&nbsp;&nbsp;<fmt:message key="PaymentInfoForm.detailInvoice.invoiceAmount"/>:</td>
    <td style="text-align:right;text-decoration:overline"><fmt:formatNumber value="${invoice.amount}" type="currency" currencySymbol="$"/></td>
    <td></td>
  </tr>
  <tr><td colspan="9">&nbsp;</td></tr>
  </c:forEach>
  </table>
  </div>
<table cellspacing="0" cellpadding="0" class="datapayment">
			<tr id="temphidefee">
                          <td style="text-align:right;" nowrap="true"><strong><fmt:message key="zipcash.detailInvoice.totalLateFee"/>:</strong></td>
                          <td  align="right"><strong><div id="totalInvoiceLateFee">&nbsp;</div></strong></td>                        
                      </tr>
			<tr id="temphidefee">
                          <td style="text-align:right;" nowrap="true"><strong><fmt:message key="zipcash.detailInvoice.totalPastDue"/>:</strong></td>
                          <td align="right"><strong><div id="totalInvoicePastDue">&nbsp;</div></strong></td>                        
                      </tr>                   
                      <tr>
                        <td style="text-align:right;" nowrap="true"><strong><fmt:message key="zipcash.detailInvoice.totalNewTrans"/>:</strong></td>
                        <td style="text-align:right; width:80px"><strong><div id="totalInvoiceAuthorizedAmount">&nbsp;</div></strong></td>                        
                      </tr>
  <tr>
    <td style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="zipcash.detailInvoice.totalInvoice"/>:</strong></td>
    <td style="text-align:right; width:80px;text-decoration:overline"><strong><div id="totalInvoiceAuthorized">&nbsp;</div></strong></td>                        
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

var elements = document.getElementsByName( "payInvoice" );
if ( elements )
{
  for ( i = 0; i < elements.length; i++ )
  {
    if ( elements[i].checked )
    {
      document.PaymentDetailForm.checkAllInvoice.checked = true;  
    }
  }
}

function selectUnselectInvoice(checked)
{
  var checked = document.PaymentDetailForm.checkAllInvoice.checked;
  var elements = document.getElementsByName( "payInvoice" );
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