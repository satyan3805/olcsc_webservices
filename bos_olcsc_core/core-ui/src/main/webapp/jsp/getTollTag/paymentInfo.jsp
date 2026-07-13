<%@ include file="/jsp/common/Taglibs.jsp" %>

<%@ page import="com.etcc.csc.util.HttpsImport"%>
<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>

<tr>
		<td class="left"></td>
		<td class="content">
		
		<div class="steps">
			<span><fmt:message key="label.steps"/>:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-here">${step} 4</li>
				<li class="step5">${step} 5</li>
				<li class="step6">${step} 6</li>
				<li class="step7">${step} 7</li>																					
			</ul>
		</div>
		
		<br class="clear">
		
			<h2><span>${step} 4:</span> <fmt:message key="PaymentInfoForm.paymentMethod.header"/></h2>

			<p><fmt:message key="required.fields.label"/></p>
                        
                            <logic:messagesPresent message="false">
        <div id="error">
            <div class="error-img"/>
            <ul>
                <html:messages id="msg" message="false">
                    <li>
                        <bean:write name="msg"/>
                    </li>
                </html:messages>
            </ul>
            <br/>
        </div>
    </logic:messagesPresent>
	<c:if test="${not empty urlVal}">
		 <% out.println(HttpsImport.importUrl((String)request.getAttribute("urlVal")));%> 
	</c:if>
		<etcc-extended:autocompleteOffForm action="/GetTollTagProcessPaymentInfo.do" method="POST">
         <p><a href="GetTollTagContactInfo.do" class="prev" id="prevLink" onclick="return prevClicked();"><fmt:message key="button.previous"/></a>
		<c:if test="${not empty requestScope.isSaved}">
			<a href="#" class="next" onclick="doSubmit();" tabindex="12" id="nextLink"><fmt:message key="button.next"/></a>
		</c:if>
		</p>
		</etcc-extended:autocompleteOffForm>
			

		</td>		
		<td class="right"></td>		
	</tr>
        
<script type="text/javascript">
<!--

//document.TollTagForm.billingName.focus();
if(document.getElementById('save')!=null)
	document.getElementById('save').className += " button";
if(document.getElementById('clear')!=null)
	document.getElementById('clear').className += " button";		

var submitted = false;
function doSubmit()
{
    if (submitted == false)
    {
        document.getElementById("nextLink").className="next-disabled";
        submitted = true;
        document.TollTagForm.submit();
    }

}

function prevClicked()
{
    if (submitted == false)
    {
        document.getElementById("prevLink").className="prev-disabled";
        submitted = true;
        return true;
    }
    return false;
}

function onCreditCardTypeChange()
{
  document.TollTagForm.creditCardNumber.value = "";
}

//-->
</script>                