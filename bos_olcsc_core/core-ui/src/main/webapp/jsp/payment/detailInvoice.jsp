<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>

<c:if test="${not empty sessionScope.PAYMENT_CONTEXT.invoices}">

<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="PaymentInfoForm.detailInvoice.header"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'invoiceModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">


<div id="invoiceModule" class="switchcontent">

<br/>
    <div id="error">
        <fmt:message key="PaymentInfoForm.detailInvoice.note">
            <fmt:param> <etcc-extended:Translation property="contactPhoneNumber"/> </fmt:param>
        </fmt:message>
    </div>

<table cellspacing="0" class="datapayment">



                    <tr>
                        <td></td>
		                <td></td>
		                <td></td>
		                <td></td>
						<td></td>
						<td></td>
						<td></td>
		                <td></td>
                        <td class="align-numbers"><strong><fmt:message key="label.selectAll"/></strong></td>
                        <td class="align-other"><input type="checkbox" name="checkAllInvoice" onclick="selectUnselectInvoice();updateTotal();"/></td>
                    </tr>



      <tr class="odd">
			  <th class="data-th-left"><fmt:message key="PaymentInfoForm.detailInvoice.details"/></th>
              <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceId"/></th>
			  <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceDate"/></th>
              <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.dueDate"/></th> 
              <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.licPlate"/></th>    
              <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.TollDue"/></th>
              <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmInvoice.AdminFeeDue"/></th>                      
              <th id="vea" class="data-th-right" style="text-align:right"><fmt:message key="PaymentInfoForm.detailInvoice.invoiceAmount"/></th>
              <th class="data-th-center" style="text-align:right" nowrap><fmt:message key="PaymentInfoForm.detailInvoice.payNow"/>?</th>                        
       </tr>

  <c:forEach items="${sessionScope.PAYMENT_CONTEXT.invoices}" var="invoice" varStatus="varStatus">
  <tr>
    <td><img src="${pageContext.request.contextPath}/images/common/buttons/plus.gif" onclick="javascript:show_or_hide(this,'${invoice.id}')"/></td>
    <td>${invoice.id}</td>
    <td><fmt:formatDate value="${invoice.invoiceDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
    <td><fmt:formatDate value="${invoice.dueDate.time}" type="date" pattern="MM/dd/yyyy"/></td>
    <td>${invoice.licPlateNumber}</td>
    <td><fmt:formatNumber value="${invoice.tollTrxnAmount}"/></td>
    <td><fmt:formatNumber value="${invoice.retailChargeAmount}"/></td>
   <c:choose>
	<c:when test="${invoice.veaEligible}">
		<td class="align-numbers" id="vea">&nbsp;<fmt:formatNumber value="${invoice.veaAmount}" type="currency" currencySymbol="$"/></td>
	</c:when>    
	<c:otherwise>
        <td class="align-numbers" id="vea"><fmt:formatNumber value="${invoice.amount}" type="currency" currencySymbol="$"/></td>
	</c:otherwise>
	</c:choose>
    <td class="align-other">
	<c:choose>
	<c:when test="${invoice.veaEligible}">
	    <fmt:formatNumber var="amount" value="${invoice.tollTrxnAmount}"/>
	    <fmt:formatNumber var="veaAmount" value="${invoice.veaAmount}"/>
	    <fmt:formatNumber var="feeAmount" value="${invoice.retailChargeAmount}"/>
	</c:when>    
	<c:otherwise>
	    <fmt:formatNumber var="amount" value="${invoice.tollTrxnAmount}"/>
	    <fmt:formatNumber var="veaAmount" value="${invoice.amount}"/>
	    <fmt:formatNumber var="feeAmount" value="${invoice.retailChargeAmount}"/>
	</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${invoice.caAccountId != null}">
			<a href="#" onclick="javascript:showInfo('<fmt:message key="PaymentInfoForm.detailInvoice.popupInvoice"><fmt:param>${invoice.id}</fmt:param><fmt:param>${invoice.caCompany}</fmt:param><fmt:param>'+formatPhoneNumber('${invoice.caPhoneNumber}')+'</fmt:param><fmt:param>${invoice.caAccountId}</fmt:param></fmt:message>');return false;">				
				<fmt:message key="PaymentInfoForm.detailInvoice.payThisBill"/>
			</a>
		</c:when>
		<c:otherwise>
			<html:multibox property="payInvoice" value="${invoice.id}" styleId="${varStatus.index}[**SPLIT**]${amount}[**SPLIT**]${veaAmount}[**SPLIT**]${feeAmount}" onclick="incrementCheck(this.form, this);updateTotal();"/>
		</c:otherwise>
	</c:choose>

	</td>
	    
  </tr>
    

  
  <tr style="display: none;" id="${invoice.id}">
    <td></td>
    <td colspan="7">
    <table>
     <tr> 
       <th class="data-th-left"><fmt:message key="PaymentInfoForm.confirmViolation.violationId"/></th>
       <th class="data-th-left"><fmt:message key="PaymentInfoForm.detailInvoice.violationDate"/></th>
       <th class="data-th-left"><fmt:message key="PaymentInfoForm.detailInvoice.violationLane"/></th>
     </tr>
     <c:forEach items="${invoice.violations}" var="invoiceViolation" varStatus="violationStatus">
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
  </c:forEach>

  <tr>
    <td colspan="7"><br></td>
    <td class="align-subtotal" id="vea">__________<br><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.invoiceAmount}" type="currency" currencySymbol="$"/></td>
<!--    <td class="align-subtotal" id="vea">__________<br><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.invoiceVeaAmount}" type="currency" currencySymbol="$"/></td>-->
    <td></td>
  </tr>
  </table>
  </div>
  
	<table cellspacing="0" class="datapayment">
	  <tr>
	    <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceAmount"/>:</strong></td>
	    <td style="text-align:right; width:80px"><strong><div id="totalInvoiceAuthorizedAmount">&nbsp;</div></strong></td>                        
	  </tr>
	  <tr id="temphidefee">
	      <td style="text-align:right;"><strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceFeeTotal"/>:</strong></td>
	      <td align="right"><strong><div id="totalInvoiceAuthorizedFee">&nbsp;</div></strong></td>                        
	  </tr>               
	  <tr>
	    <td style="text-align:right;text-decoration:overline">&nbsp;&nbsp;&nbsp;&nbsp;<strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceTotal"/>:</strong></td>
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

function selectUnselectInvoice()
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

function formatPhoneNumber(phoneNumber)
{
	if(phoneNumber != ""){
		var areaCode = phoneNumber.substring(0,3);
		var part2 = phoneNumber.substring(3,6);
		var part3 = phoneNumber.substring(6);
		var phoneNumberFormatted = areaCode+"-"+part2+"-"+part3;
		return phoneNumberFormatted;	
		
	} else {
		return "";
	}	
}

function showInfo(msg)
{
	//alert(msg);
	confirmOK('', msg, '<fmt:message key="PaymentInfoForm.detailInvoice.popupHeader"/>', '<fmt:message key="PaymentInfoForm.detailInvoice.popupOK"/>', null);
}
</script>

</c:if>