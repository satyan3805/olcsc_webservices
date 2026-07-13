<%@ include file="/jsp/common/Taglibs.jsp" %>

<h2><span>Step 6: </span><fmt:message key="PaymentInfoForm.confirmPayment.header"/></h2>
<p><fmt:message key="PaymentInfoForm.confirmPayment.note"/></p>

<br/>
<logic:messagesPresent message="false">
  <div id="error">
  <%--<div class="error-img"></div>--%>
  <ul>
    <html:messages id="msg" message="false">
    <li><bean:write name="msg" filter="false"/></li>
    </html:messages>
  </ul>
  <br/>
  </div>
</logic:messagesPresent>

<tiles:insert name="method" ignore="true"/>
<tiles:insert name="invoice" ignore="true"/>
<tiles:insert name="inventory" ignore="true"/>
<tiles:insert name="violation" ignore="true"/>
<tiles:insert name="tag" ignore="true"/>
<%--<tiles:insert name="deposit" ignore="true"/> --%>
<tiles:insert name="total" ignore="true"/>
                    
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>
  <a href="DisplayPaymentInfo.do" class="prev" 
    onclick="this.className='prev-disabled';return checkSubmitted();"><fmt:message key="PaymentInfoForm.confirmPayment.button.prev"/></a>
  <a href="FinalizePayment.do" class="next" 
    onclick="this.className='next-disabled';return checkSubmitted();"><fmt:message key="PaymentInfoForm.confirmPayment.button.next"/></a>
</p>

<script type="text/javascript">
if(window.history.forward(1) != null)
    window.history.forward(1);
</script>

<script type="text/javascript">
var submitted = false;

function checkSubmitted()
{
    if (submitted == false)
    {
        submitted = true;
        return true;
    }
    return false;
}
</script>