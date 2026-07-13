<%@ include file="/jsp/common/Taglibs.jsp" %>

<tr>
  <td class="topleft"></td>
  <td class="topcenter"></td>
  <td class="topright"></td>
</tr>
<tr>
		<td class="left"></td>
		<td class="content">
		
		<br class="clear">
                
                
		
  <h2><span>Step 6: </span><fmt:message key="PaymentInfoForm.paymentMethod.header"/></h2>
   
         <p><fmt:message key="label.fillInFields"/></p>
         <p><fmt:message key="required.fields.label"/></p>
      
  
<logic:messagesPresent message="false">
  <div id="error">
  <%--<div class="error-img"></div>--%>
  <ul>
    <html:messages id="msg" message="false">
    <li><bean:write name="msg"/></li>
    </html:messages>
  </ul>
  <br/>
  </div>
</logic:messagesPresent>

<tiles:insert name="zipcash.paymentInfo" ignore="true"/>  


		</td>		
		<td class="right"></td>		
	</tr>

<tr>
  <td class="bottomleft"></td>
  <td class="bottomcenter"></td>
  <td class="bottomright"></td>
</tr>