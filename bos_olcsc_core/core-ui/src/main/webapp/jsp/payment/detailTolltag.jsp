<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>
<tr>
  <td class="left"></td>
		
<td class="content">
<html:form action="ProcessPaymentDetail.do" method="POST" onsubmit="return checkTagAmount();">

<div class="steps">
			<span><fmt:message key="label.steps"/>:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-taken">${step} 4</li>
				<li class="step5-taken">${step} 5</li>
				<li class="step6-here">${step} 6</li>
				<li class="step7">${step} 7</li>																					
			</ul>
		</div>
		<br class="clear">

<h2><font size="3.5"><b><span>${step} 6: </span><fmt:message key="PaymentInfoForm.header1"/></b></font></h2>
<p><fmt:message key="PaymentInfoForm.detailTolltag.note1"/>  
<c:if test="${not empty sessionScope.PAYMENT_CONTEXT.invoices || not empty sessionScope.PAYMENT_CONTEXT.violations}">
    <fmt:message key="PaymentInfoForm.detailTolltag.note2"/>
</c:if>

<logic:messagesPresent message="false">
  <div id="error">
  <br/>
  <ul>
    <html:messages id="msg" message="false">
    <li><bean:write name="msg"/></li>
    </html:messages>
  </ul>
  <br/>
  </div>
</logic:messagesPresent>


<c:if test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">
<br>
<h3><!--
    <fmt:message key="PaymentInfoForm.vea.note1">
        <fmt:param><a href="javascript:openWin('DisplayVeaDisclaimer.do',800,600)"><font color="red"></fmt:param>
        <fmt:param></font></a></fmt:param>
    </fmt:message>
    <fmt:message key="PaymentInfoForm.vea.note2">
        <fmt:param><a href="ViolatorLoginStartPage.do"><font color="red"></fmt:param>
        <fmt:param></font></a></fmt:param>
    </fmt:message>
--><%--    
What is a <a href="javascript:openWin('DisplayVeaDisclaimer.do',800,600)"><font color="red">VEA</font></a> and how can I save money by getting a TollTag account?<br>
Interested in a VEA?  Click <a href="ViolatorLoginStartPage.do"><font color="red">here</font></a> or go back and click "Get a TollTag" button.
--%>
</h3>
</c:if>

<tiles:insert name="vea" ignore="true"/>
<tiles:insert name="invoice" ignore="true"/>
<tiles:insert name="violation" ignore="true"/>
<tiles:insert name="tag" ignore="true"/>
<%--<tiles:insert name="deposit" ignore="true"/> --%>


<table cellspacing="0" class="datapayment">
<c:if test="${!requestScope.violationsOnly}">
<tr>
  <td colspan="8"><br><br>&nbsp;<br></td>
</tr>

<tr class="odd">
  <td colspan="7"><strong><fmt:message key="PaymentInfoForm.total.payment"/>:</strong></td>
  <td align="right"><strong><div id="totalPrice">&nbsp;</strong></td>
</tr>
</c:if>                            
</table>

<c:if test="${!requestScope.violationsOnly}">
<p>
  <c:if test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">
    <a href="ViolatorLoginStartPage.do" class="prev" id="prevLink" onclick="return prevClicked();"><fmt:message key="button.previous"/></a> 
  </c:if>
  <a href="javascript:actionOnVea(false);document.PaymentDetailForm.submit();" 
    id="nextLink" class="next" onclick="return <c:if test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">verifySelectedItems( ) &&</c:if>checkSubmitted();"><fmt:message key="button.next"/></a> 
</p>
</c:if>

                  
                  </html:form>
</td>		
		<!-- end content -->
		<td class="right"></td>		
	</tr>

<script type="text/javascript">
actionOnVea( getVeaAcceptStatus( ) );
updateTotal( );

function updateTotal( )
{
  updateValueByName( "totalInvoiceAuthorizedAmount", getInvoiceTotal() );
  updateValueByName( "totalInvoiceAuthorizedFee", getInvoiceFeeTotal() );
  updateValueByName( "totalInvoiceAuthorized", getInvoiceTotal() + getInvoiceFeeTotal() );
  updateValueByName( "totalViolationAuthorizedAmount", getViolationTotal() );
  // updateValueByName( "totalViolationAuthorizedFee", getViolationFeeTotal() );
  // updateValueByName( "totalViolationAuthorized", getViolationTotal() + getViolationFeeTotal() );
  updateValueByName( "totalViolationAuthorized", getViolationTotal() );
  updateValueByName( "totalPrice", getTotal() );
}

function getVeaAcceptStatus( )
{
  if ( document.PaymentDetailForm.acceptVea && document.PaymentDetailForm.acceptVea.length > 0 ) 
  {
    return document.PaymentDetailForm.acceptVea[0].checked;
  }
  else
  {
    return false;
  }
}

function incrementCheck(o_form, o_object)
{
  for ( i = 0; i < o_form.payInvoice.length; i++ )
  {
    if ( o_object.checked && 1*o_form.payInvoice[i].id.split("[**SPLIT**]")[0] < 1*o_object.id.split("[**SPLIT**]")[0] )
    {
      o_form.payInvoice[i].checked=true;
    }
    if ( !o_object.checked && 1*o_form.payInvoice[i].id.split("[**SPLIT**]")[0] > 1*o_object.id.split("[**SPLIT**]")[0] )
    {
      o_form.payInvoice[i].checked=false;
    }
  }
}

function getTotal()
{
  total = 0.0;
  total += getInvoiceTotal( );
  total += getInvoiceFeeTotal( );
  total += getViolationTotal( );
  // total += getViolationFeeTotal( );
  total += getTotalTagAmount();
  return total;
}

function getInvoiceTotal()
{
  total = 0.0;
  if ( getVeaAcceptStatus( ) )
  {
    total += 1.0 * getTotalByName( "payInvoice", 2 );
  }
  else
  {
    total += 1.0 * getTotalByName( "payInvoice", 1 );
  }
  return total;
}

function updateValueByName( name, value )
{
  if ( document.getElementById( name ) )
  {
    document.getElementById(name).innerHTML="$"+ (1 * value).toFixed(2);
  }
}

function getInvoiceFeeTotal()
{
  total = 0.0;
  if ( getVeaAcceptStatus( ) )
  {
    total += 1.0 * getTotalByName( "payInvoice", 3 );
  }
  return total;
}

function getViolationTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payViolation", 1 );
  return total;
}

function getViolationFeeTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payViolation", 2 );
  return total;
}

function getTotalByName( name, splitIndex )
{
  var total = 0.0;
  var elements = document.getElementsByName( name );
  if ( elements )
  {
    for ( i = 0; i < elements.length; i++ )
    {
      if (elements[i].checked)
      {
        total += 1.0*elements[i].id.split("[**SPLIT**]")[splitIndex];
      }
    }
  }
  return total;
}

function getTotalTagAmount()
{
  total = 0.0;
  if (document.PaymentDetailForm.tagAmount)
  {
    total += 1.0*document.PaymentDetailForm.tagAmount.value;
  } 
  <c:if test="${sessionScope.PAYMENT_CONTEXT.depAmount != null}">
      total += ${sessionScope.PAYMENT_CONTEXT.depAmount};
  </c:if>
  <c:if test="${sessionScope.PAYMENT_CONTEXT.tagSalePrice != null}">
  		total += ${sessionScope.PAYMENT_CONTEXT.tagSalePrice};
  </c:if>
  return total;
}

function show_or_hide(curobj,id) 
{ 
  if (document.getElementById(id).style.display == "none")
  {
    document.getElementById(id).style.display = '';
    curobj.src = "${pageContext.request.contextPath}/images/common/buttons/minus.gif";
  } 
  else 
  {
    document.getElementById(id).style.display = 'none';
    curobj.src = "${pageContext.request.contextPath}/images/common/buttons/plus.gif";
  }	
}

function actionOnVea(accepted)
{
  var elements = document.getElementsByName( "payInvoice" );
  if ( elements && elements.length > 0 )
  {
    if ( accepted )
    {
      document.PaymentDetailForm.checkAllInvoice.checked = true;
      document.PaymentDetailForm.checkAllInvoice.disabled = true;
    }
    else
    {
      document.PaymentDetailForm.checkAllInvoice.disabled = false;
    }
    
    for ( i = 0; i < elements.length; i++ )
    {
      if ( accepted )
      {
        elements[i].checked = true;
        elements[i].disabled = true;
      }
      else
      {
        elements[i].disabled = false;
      }
    }
  }
}

function verifySelectedItems( )
{
  var payInvoices = document.getElementsByName( "payInvoice" );
  if ( payInvoices && payInvoices.length > 0  )
  {
    if ( getTotalByName( "payInvoice", 1 ) == 0 )
    {
      alert( "<fmt:message key='PaymentInfoForm.detailTolltag.error.invoice'/>" );
      return false;
    }
    else
    {
      return true;
    }
  }
}

var submitted = false;

function checkSubmitted()
{
    if (submitted == false)
    {
        if (checkTagAmount())
        {
            document.getElementById("nextLink").className='next-disabled';
            submitted = true;
            return true;
        }
        else
            return false;
    }
    return false;
}

function prevClicked()
{   
    if (submitted == false)
    {
        document.getElementById("prevLink").className='prev-disabled';
        submitted = true;
        return true;
    }
    return false;
}

function checkTagAmount()
{
    if (document.PaymentDetailForm.tagAmount != null)
    {
        var origTagAmount = ${sessionScope.PAYMENT_CONTEXT.originalTagAmount};
        
        document.PaymentDetailForm.tagAmount.value=document.PaymentDetailForm.tagAmount.value.replace(/\,*/g, "");
        
        if (/^((\d+)||(\d+,\d\d\d))(\.\d+)?$/.test(document.PaymentDetailForm.tagAmount.value)==false) 
        {
            alert("<fmt:message key='PaymentInfoForm.detailTolltag.error'/>");
            document.PaymentDetailForm.tagAmount.focus();
            return false;
        }
        
//        if (origTagAmount < 40)
//            origTagAmount = 40;
            
        if (document.PaymentDetailForm.tagAmount.value < origTagAmount)
        {    
            alert("<fmt:message key='PaymentInfoForm.detailTolltag.minimum'/> \$" + origTagAmount);
            document.PaymentDetailForm.tagAmount.focus();
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>