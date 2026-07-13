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

<c:if test="${empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices 
        && not empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.violations}">
  <c:set var="violationsOnly" value="true" scope="request"/>
</c:if>

<c:if test="${empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.invoices 
        && empty sessionScope.ZIPCASH_PAYMENT_CONTEXT.violations}">
  <c:set var="noTransactions" value="true"/>
</c:if>

	<tr>
		<td class="topleft"></td>
		<td class="topcenter"></td>
		<td class="topright"></td>
	</tr>

                <tr>
                  <td class="left"></td>
                  
<html:form action="zipCashProcessPaymentDetail.do" method="POST">

<td class="content"><h2><fmt:message key="PaymentInfoForm.header1"/></h2>
<p><fmt:message key="zipcash.layout.note2"/>  

<c:if test="${!requestScope.violationsOnly && !noTransactions}">
    <br/><strong><fmt:message key="zipcash.layout.note3"/></strong>
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


<tiles:insert name="invoice" ignore="true"/>
<tiles:insert name="violation" ignore="true"/>


<c:if test="${!noTransactions}">
    <table cellspacing="0" class="datapayment">
        <tr class="odd">
          <td width="20">&nbsp;</td>
          <td colspan="5"><strong><fmt:message key="PaymentInfoForm.total.payment"/></strong></td>
          <td align="right"><strong><div id="totalPrice">&nbsp;</div></strong></td>
          <td width="50">&nbsp;</td>
        </tr>
        <%--</c:if>                            --%>
    </table>
</c:if>

<c:if test="${noTransactions}">
    <br/>
    <br/>
    <br/>
    <br/>
    <p>
    <h2><fmt:message key="zipcash.detailInvoice.noTransaction"/></h2>
    </p>
    <br/>
    <br/>
    <br/>
    <br/>
</c:if>

<p>
  <c:if test="${!noTransactions}">
    <a href="javascript:document.PaymentDetailForm.submit();" id="nextLink" class="next" onclick="return verifySelectedItems( ) && checkSubmitted();"><fmt:message key="button.next"/></a>
  </c:if>
</p>
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
updateTotal( );

function updateTotal( )
{
  updateValueByName( "totalInvoiceAuthorizedAmount", getInvoiceTotalNewTransactions() );
  updateValueByName( "totalInvoiceAuthorizedFee", getInvoiceFeeTotal() );
  updateValueByName( "totalInvoiceLateFee", getInvoiceLateFeeTotal() );
  updateValueByName( "totalInvoicePremium", getInvoicePremiumTotal() );
  updateValueByName( "totalInvoicePastDue", getInvoicePastDueTotal() );
  updateValueByName( "totalInvoiceMailHandlingFee", getInvoiceMailHandlingFeeTotal() );
  updateValueByName( "totalInvoiceAuthorized", getInvoiceTotal());
  updateValueByName( "totalViolationAuthorizedAmount", getViolationTotal() );
  updateValueByName( "totalViolationAuthorized", getViolationTotal() );
  updateValueByName( "totalPrice", getTotal() );
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
//  total += getInvoiceFeeTotal( );
//  total += getInvoiceLateFeeTotal( );
//  total += getInvoicePastDueTotal( );
//  total += getInvoiceMailHandlingFeeTotal( );

  total += getViolationTotal( );
  return total;
}

function getInvoiceTotal()
{
  total = 0.0;
    total += 1.0 * getTotalByName( "payInvoice", 1 );
  return total;
}

function getInvoiceTotalNewTransactions()
{
  total = 0.0;
    total += 1.0 * getTotalByName( "payInvoice", 7 );
  return total;
}

function updateValueByName( name, value )
{
  if ( document.getElementById( name ) )
  {
    document.getElementById(name).innerHTML="$"+value.toFixed(2);
  }
}

function getInvoiceFeeTotal()
{
  total = 0.0;
  return total;
}

function getInvoiceLateFeeTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payInvoice", 4 );
  return total;
}

function getInvoicePastDueTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payInvoice", 8 );
  total += 1.0 * getTotalByName( "payInvoice", 9 );
  total += 1.0 * getTotalByName( "payInvoice", 10 );
  return total;
}

function getInvoicePremiumTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payInvoice", 5 );
  return total;
}

function getInvoiceMailHandlingFeeTotal()
{
  total = 0.0;
  total += 1.0 * getTotalByName( "payInvoice", 6 );
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
function verifySelectedItems( )
{
  var payInvoices = document.getElementsByName( "payInvoice" );
  var payViolations = document.getElementsByName( "payViolation" );
  var failed = true;
  if ( payInvoices && payInvoices.length > 0  ) {
    if ( getTotalByName( "payInvoice", 1 ) != 0 ) {
      failed = false;
    }
  }
  if (failed) {
      if (payViolations && payViolations.length > 0) {
        if (getTotalByName("payViolation", 1) != 0) {
            failed = false;
        } 
      }
  }
  if (failed) {
      alert( "<fmt:message key="zipcash.transaction.error"/>" );
      return false;
  } else {
    return true;
  }
}

var submitted = false;

function checkSubmitted()
{
    if (submitted == false)
    {
            document.getElementById("nextLink").className='next-disabled';
            submitted = true;
            return true;
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

</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>