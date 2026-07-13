<%@ include file="/jsp/common/Taglibs.jsp" %>

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
				<li class="step4-taken">${step} 4</li>
				<li class="step5-taken">${step} 5</li>
				<li class="step6-here">${step} 6</li>
				<li class="step7">${step} 7</li>																					
			</ul>
		</div>
		
		<br class="clear">
                
                
		
   <h2><span>${step} 6: </span><fmt:message key="PaymentInfoForm.paymentMethod.header"/></h2>
   
         <p><fmt:message key="label.fillInFields"/></p>
         <p><fmt:message key="required.fields.label"/></p>
      
  
<logic:messagesPresent message="false">
        <div id="error">
            <div class="error-img"></div>
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


<tiles:insert name="paymentInfo" ignore="true"/>  

		</td>		
		<td class="right"></td>		
	</tr>