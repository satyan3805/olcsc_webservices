<%@ include file="/jsp/common/Taglibs.jsp" %>

<style type="text/css">
.showstate{ /*Definition for state toggling image */
cursor:hand;
cursor:pointer;
float: right;
}

.headers{
	background-color: #F8F4ED;
	padding-top: 4px;
	padding-bottom: 4px;
	padding-left: 5px;
	padding-right: 5px;
}

.switchcontent{
	width: auto;
}

.showstate1 {cursor:hand;
cursor:pointer;
float: right;
}
</style>

<c:if test="${empty sessionScope.PAYMENT_CONTEXT.invoices && not empty sessionScope.PAYMENT_CONTEXT.violations && sessionScope.PAYMENT_CONTEXT.tagAmount == 0 && sessionScope.PAYMENT_CONTEXT.callBack != 'makePayment'}">
  <c:set var="violationsOnly" value="true" scope="request"/>
</c:if>
	<tr>
		<td class="topleft"></td>
		<td class="topcenter"></td>
		<td class="topright"></td>
	</tr>

                <tr>
                  <td class="left"></td>
	
                  
<html:form action="ProcessPaymentDetail.do" method="POST" onsubmit="return checkTagAmount();">

<td class="content">
<p><fmt:message key="PaymentInfoForm.header2"/>  
<c:if test="${not empty sessionScope.PAYMENT_CONTEXT.invoices || not empty sessionScope.PAYMENT_CONTEXT.violations}">
<c:choose>
  <c:when test="${requestScope.violationsOnly}">
    <fmt:message key="PaymentInfoForm.violationOnly.note"/>
  </c:when>
  <c:otherwise>
    <strong><fmt:message key="PaymentInfoForm.all.note"/></strong>
    <br/><br/>
    <c:if test="${empty acctInfo}">
		<div style="color:red;font-weight:bold">
			<fmt:message key="PaymentInfoForm.getTollTag">
	    		<fmt:param><a href="#" onclick='javascript: parent.location.href="${pageContext.request.contextPath}/GetTollTagDisplayAgreement.do";'></fmt:param>
	    		<fmt:param></a></fmt:param>
			</fmt:message>
		</div>
	</c:if>
  </c:otherwise>
</c:choose>
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
        <fmt:param>
            <a href='javascript:openWin("DisplayVeaDisclaimer.do",800,600)'><font color='red'>
        </fmt:param>
        <fmt:param>
            </font></a>
        </fmt:param>
    </fmt:message>
    --><br>
<%--    What is a <a href="javascript:openWin('DisplayVeaDisclaimer.do',800,600)"><font color="red">VEA</font></a> and how can I save money by getting a TollTag account?<br>
--%><!--
    <fmt:message key="PaymentInfoForm.vea.note2">
        <fmt:param value='<a href="ViolatorLoginStartPage.do"><font color="red">'/>
        <fmt:param value="</font></a>"/>
    </fmt:message>
--><%--    
Interested in a VEA?  Click <a href="ViolatorLoginStartPage.do"><font color="red">here</font></a> or go back and click "Get a TollTag" button.
--%>
</h3>
</c:if>

<tiles:insert name="vea" ignore="true"/>
<tiles:insert name="invoice" ignore="true"/>
<tiles:insert name="violation" ignore="true"/>
<tiles:insert name="inventory" ignore="true"/>
<tiles:insert name="tag" ignore="true"/>
<%--<tiles:insert name="deposit" ignore="true"/> --%>


<table cellspacing="0" class="datapayment">
<c:if test="${!requestScope.violationsOnly || sessionScope.PAYMENT_CONTEXT.depAmount > 0}">
<tr>
  <td colspan="8"><br><br>&nbsp;<br></td>
</tr>

<tr class="odd">
  <td colspan="7"><strong><fmt:message key="PaymentInfoForm.total.payment"/></strong></td>
<td align="right"><strong><div id="totalPrice">&nbsp;</div></strong></td>
</tr>
</c:if>                            
</table>

<c:if test="${!requestScope.violationsOnly || sessionScope.PAYMENT_CONTEXT.depAmount > 0}">
<p>
  <c:if test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">
    <a href="ViolatorLoginStartPage.do" class="prev" id="prevLink" onclick="return prevClicked();"><fmt:message key="button.previous"/></a> 
  </c:if>
 
  <a href="javascript:actionOnVea(false);document.PaymentDetailForm.submit();" id="nextLink" class="next" onclick="return <c:if test="${sessionScope.accountLogin.loginType == 'IN' || sessionScope.accountLogin.loginType == 'CA'}">verifySelectedItems( ) &&</c:if>checkSubmitted();"><fmt:message key="button.next"/></a>
</p>
</c:if>
</td>
                  
                  </html:form>
                  <td class="right"></td>
                </tr>

	<tr>
		<td class="bottomleft"></td>
		<td class="bottomcenter"></td>
		<td class="bottomright"></td>
	</tr>

<script type="text/javascript">

window.onload = function(){
	var autochargeAmtChange = "<%=request.getAttribute("autochargeAmountChange")%>";
	if(autochargeAmtChange != null && autochargeAmtChange != 'null'){
		alert(autochargeAmtChange);
	}
}
actionOnVea( getVeaAcceptStatus( ) );
updateTotal( );

function updateTotal( )
{
  updateValueByName( "totalInvoiceAuthorizedAmount", getInvoiceTotal());
  updateValueByName( "totalInvoiceAuthorizedFee", getInvoiceFeeTotal() );
  updateValueByName( "totalInvoiceAuthorized", getInvoiceTotal() + getInvoiceFeeTotal() );
  updateValueByName( "totalViolationAuthorizedAmount", getViolationTotal() );
  updateValueByName( "totalTagstoreAmount", getTotalTagAmount() );
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
	window.grandTotal = 0.0;
	total = 0.0;
  	total += getInvoiceTotal( );
  	total += getInvoiceFeeTotal( );
  	total += getViolationTotal( );
  	// total += getViolationFeeTotal( );
  	total += getTotalTagAmount();
  	total+=getTotalInventoryamount();
  	window.grandTotal = total;
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
    var amount = numberFormat(value.toFixed(2));
    document.getElementById(name).innerHTML="$"+amount;
  }
}

function getInvoiceFeeTotal()
{
  total = 0.0;
  //if ( getVeaAcceptStatus( ) )
  //{
    total += 1.0 * getTotalByName( "payInvoice", 3 );
  //}
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
         var value = elements[i].id.split("[**SPLIT**]")[splitIndex];
         if (value.indexOf(",") != -1){
            objRegExp = /\)|\(|[,]/g;
            value = value.replace(objRegExp,'');
         }
         total += 1.0*value;
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
  <c:if test="${sessionScope.PAYMENT_CONTEXT.tagLostFee != null}">
  total += ${sessionScope.PAYMENT_CONTEXT.tagLostFee};
</c:if>
<c:if test="${sessionScope.PAYMENT_CONTEXT.tagSalePrice != null}">
total += ${sessionScope.PAYMENT_CONTEXT.tagSalePrice};
</c:if>
  return total;
  
}

function getTotalInventoryamount()
{
	total=0.0;
	<c:if test="${sessionScope.PAYMENT_CONTEXT.totalInventoryPrice != null}">
     total=total+${sessionScope.PAYMENT_CONTEXT.totalInventoryPrice};
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

function checkTagAmount() {
    if (document.PaymentDetailForm.tagAmount != null) {
        var origTagAmount = '${sessionScope.PAYMENT_CONTEXT.originalTagAmount}';
        var totalTagAmount = window.grandTotal;
        document.PaymentDetailForm.tagAmount.value = document.PaymentDetailForm.tagAmount.value.replace(/\,*/g, "");
        if (totalTagAmount <= 0) {        	
            alert("<fmt:message key='PaymentInfoForm.detailTolltag.error.enterAmount'/>");
	        document.PaymentDetailForm.tagAmount.focus();
    	    return false;
        }
        if (/^((\d+)||(\d+,\d\d\d))(\.\d+)?$/.test(document.PaymentDetailForm.tagAmount.value)==false) {
            alert("<fmt:message key='PaymentInfoForm.detailTolltag.error'/>");
            document.PaymentDetailForm.tagAmount.focus();
            return false;
        }
//        if (origTagAmount < 40) origTagAmount = 40;
        if (document.PaymentDetailForm.tagAmount.value < origTagAmount) {    
            alert("<fmt:message key='PaymentInfoForm.detailTolltag.minimum'/> \$" + origTagAmount);
            document.PaymentDetailForm.tagAmount.focus();
            return false;
        }
        updateTotal();
        document.PaymentDetailForm.tagAmount.value = (1*document.PaymentDetailForm.tagAmount.value).toFixed(2);
        return true;
    }
    else {
    	return true;
    }
}

function numberFormat(nStr){ 
    nStr += '';    
    x = nStr.split('.');   
    x1 = x[0];    
    x2 = x.length > 1 ? '.' + x[1] : '';    
    var rgx = /(\d+)(\d{3})/;    
    while (rgx.test(x1))        
      x1 = x1.replace(rgx, '$1' + ',' + '$2');
    return  x1 + x2;
}

</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>