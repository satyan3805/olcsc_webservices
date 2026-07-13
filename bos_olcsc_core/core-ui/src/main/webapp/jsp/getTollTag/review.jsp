<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str"%>

<script type="text/javascript">
if(window.history.forward(1) != null)
    window.history.forward(1);
</script>


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
		
                <html:errors/>       
		<br class="clear">
		
			<h2><span>${step} 6:</span> <fmt:message key="review.header"/></h2>
         
			<%-- <p><fmt:message key="review.note1"><fmt:param value="${accountPlan}" /></fmt:message></p> --%>
			<c:if test="${accountPlan == 'C'}">
					<p><bean:message key="review.note1" arg0="EasyZip"/></p>
            </c:if>
            <c:if test="${accountPlan == 'A'}">
					<p><bean:message key="review.note1" arg0="TollTag"/></p>
            </c:if>
            <c:if test="${accountPlan == 'ZP'}">
					<p><bean:message key="review.note1" arg0="ZipPass"/></p>
            </c:if>
                        
<table cellspacing="0" class="confirm">	
			<tr>
                          <th class="title" width="200"><fmt:message key="receipt.acctinfo.header"/></th>
                          <th class="titleright" width="400"><input type="button" class="button" value="<fmt:message key="button.edit"/>" onclick="location.href='GetTollTagContactInfo.do'"/></th>
			</tr>
                        </table>
                        <table cellspacing="0" class="confirm">	
			<tr>
			  <th><fmt:message key="receipt.acctinfo.acctnum"/>:</th>
			  <td>${sessionScope.accountLogin.acctId}</td>
			</tr>

            <c:if test="${sessionScope.TollTagForm.map.accountType == 'C'}">
			<tr>
                          <th><fmt:message key="receipt.tolltagplaninfo.type"/>: </th>
			  <td><fmt:message key="receipt.tolltagplaninfo.corporateaccount"/></td>
			</tr>
            </c:if>
            <c:if test="${sessionScope.TollTagForm.map.accountType == 'P'}">
			<tr>
                          <th><fmt:message key="receipt.tolltagplaninfo.type"/>: </th>
			  <td><fmt:message key="receipt.tolltagplaninfo.personalaccount"/></td>
			</tr>
            </c:if>
            <c:if test="${sessionScope.TollTagForm.map.plan == 'C'}">
            	<tr>
					<th><fmt:message key="receipt.tolltagplaninfo.plan"/>:</th>
						<td><fmt:message key="receipt.tolltagplaninfo.planC"/></td>
				</tr>
            </c:if>
            <c:if test="${sessionScope.TollTagForm.map.plan == 'A'}">
            	<tr>
					<th><fmt:message key="receipt.tolltagplaninfo.plan"/>:</th>
						<td><fmt:message key="receipt.tolltagplaninfo.planA"/></td>
				</tr>
            </c:if>
            <c:if test="${sessionScope.TollTagForm.map.plan == 'ZP'}">
            	<tr>
					<th><fmt:message key="receipt.tolltagplaninfo.plan"/>:</th>
						<td><fmt:message key="receipt.tolltagplaninfo.planZP"/></td>
				</tr>
            </c:if>
			
              <tr>
                   <th><fmt:message key="receipt.acctinfo.userid"/>: </th>
			  <td>${sessionScope.TollTagForm.map.userId}</td>
			</tr>
			<tr>
			  <th><fmt:message key="receipt.acctinfo.name"/>:</th>
			  <td>${sessionScope.TollTagForm.map.firstName}<c:if test="${not empty sessionScope.TollTagForm.map.middleInitial}"> ${sessionScope.TollTagForm.map.middleInitial}</c:if> ${sessionScope.TollTagForm.map.lastName}</td>
			</tr>
                        
                        <c:if test="${not empty sessionScope.TollTagForm.map.companyName}">
                        <tr>
			  <th><fmt:message key="receipt.acctinfo.company"/>:</th>
			  <td>${sessionScope.TollTagForm.map.companyName}</td>
			</tr>
                        </c:if>
                        
			<tr>
                          <th><fmt:message key="receipt.acctinfo.address"/>:</th>
			  <td>${sessionScope.TollTagForm.map.address}</td>
			</tr>
                        
                        <c:if test="${not empty sessionScope.TollTagForm.map.addressLine2}">
                        <tr>
                          <th><fmt:message key="receipt.acctinfo.aptsuite"/>:</th>
			  <td>${sessionScope.TollTagForm.map.addressLine2}</td>
			</tr>
                        </c:if>
                        
			<tr>
                          <th><fmt:message key="receipt.acctinfo.city"/>: </th>
			  <td>${sessionScope.TollTagForm.map.city}</td>
			</tr>
			<tr>
                          <th><fmt:message key="receipt.acctinfo.state"/>: </th>
			  <td>${sessionScope.TollTagForm.map.state}</td>
			</tr>
			<tr>
                          <th><fmt:message key="receipt.acctinfo.zip"/>:</th>
			  <td>${sessionScope.TollTagForm.map.zipcode}<c:if test="${not empty sessionScope.TollTagForm.map.plus4}">-${sessionScope.TollTagForm.map.plus4}</c:if></td>
			</tr>
			<tr>
                          <th><fmt:message key="receipt.acctinfo.homephone"/>:</th>
			  <td>${sessionScope.TollTagForm.map.homePhoneAreaCode}-${sessionScope.TollTagForm.map.homePhoneFirst3}-${sessionScope.TollTagForm.map.homePhoneLast4}</td>
			</tr>
                        <c:if test="${not empty sessionScope.TollTagForm.map.workPhoneAreaCode && not empty sessionScope.TollTagForm.map.workPhoneFirst3 && not empty sessionScope.TollTagForm.map.workPhoneLast4}">
			<tr>
                          <th><fmt:message key="receipt.acctinfo.workphone"/>:</th>
			  <td>${sessionScope.TollTagForm.map.workPhoneAreaCode}-${sessionScope.TollTagForm.map.workPhoneFirst3}-${sessionScope.TollTagForm.map.workPhoneLast4}<c:if test="${not empty sessionScope.TollTagForm.map.workPhoneExt}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ext.: ${sessionScope.TollTagForm.map.workPhoneExt}</c:if></td>
			</tr>
                        </c:if>
			<tr>
                          <th><fmt:message key="receipt.acctinfo.email"/>:</th>
			  <td>${sessionScope.TollTagForm.map.email}</td>
			</tr>
                        
			</table>


<!-- Payment Information -->
			<table cellspacing="0" class="confirm">	
			<tr>
                          <th class="title" width="200"><fmt:message key="receipt.paymentinfo.header"/></th>
                          <th class="titleright" width="400"><input type="button" class="button" value="<fmt:message key="button.edit"/>" onclick="location.href='GetTollTagPaymentInfo.do'"/></th>
			</tr>
                        </table>
                        
                        <table cellspacing="0" class="confirm">	
			<tr>
				<td><fmt:message key="receipt.paymentinfo.cardtype"/>:<br>${requestScope.viewHelper.creditCardName}</td>
				<td><fmt:message key="receipt.paymentinfo.cardnum"/>:<br>xxxxxxxxxxxx-${requestScope.viewHelper.creditCardLast4}</td>
				<td><fmt:message key="receipt.paymentinfo.cardexp"/>:<br>${requestScope.viewHelper.creditCardExpiration}</td>				
			</tr>			
                        
                        <%--
                         <tr>
                          <td colspan="3">Prepaid account balance for ${requestScope.viewHelper.vehicleCount} vehicle<c:if test="${requestScope.viewHelper.vehicleCount > 1}">s</c:if>: <fmt:formatNumber value="${requestScope.viewHelper.paymentAmount}" type="currency"/></td>
			</tr>
                         --%>
                         
			</table>

			
			  <!-- Vehicle Information -->
                          
                        <table cellspacing="0" class="confirm">	
			<tr>
                          <th class="title" width="200"><fmt:message key="receipt.vehicleinfo.header"/></th>
                          <th class="titleright" width="400"><input type="button" class="button" value="<fmt:message key="button.edit"/>" onclick="location.href='GetTollTagVehicleInfo.do'"/></th>
			</tr>
                        </table>
                        
                        <table cellspacing="0" class="confirm">	
			<tr>
				<td><strong><fmt:message key="receipt.vehicleinfo.licplate"/>:</strong></td>
				<td><strong><fmt:message key="receipt.vehicleinfo.tempplate"/>?</strong></td>
				<td><strong><fmt:message key="receipt.vehicleinfo.state"/>:</strong></td>				
				<td><strong><fmt:message key="receipt.vehicleinfo.year"/>:</strong></td>				
				<td><strong><fmt:message key="receipt.vehicleinfo.color"/>:</strong></td>				
				<td><strong><fmt:message key="receipt.vehicleinfo.make"/>:</strong></td>				
				<td><strong><fmt:message key="receipt.vehicleinfo.model"/>:</strong></td>				
				<td><strong><fmt:message key="receipt.vehicleinfo.class"/>:</strong></td>
			</tr>			
                        
                        <c:forEach items="${sessionScope.savedVehicles}" var="savedVehicle" varStatus="varStatus">
<TR>
  <TD>${savedVehicle.value.licPlate}</TD>
  <td><c:choose><c:when test="${savedVehicle.value.temporaryLicPlate}"><fmt:message key="receipt.vehicleinfo.tempYes"/></c:when><c:otherwise><fmt:message key="receipt.vehicleinfo.tempNo"/></c:otherwise></c:choose></td>
  <TD><c:out value="${savedVehicle.value.licState}"/></TD>
  <TD><c:out value="${savedVehicle.value.vehicleYear}"/></TD>
  <TD><c:out value="${savedVehicle.value.vehicleColor}"/></TD>
  <TD><c:out value="${savedVehicle.value.vehicleMake}"/></TD>
  <TD><c:out value="${savedVehicle.value.vehicleModel}"/></TD>
  <%-- <TD><c:out value="${savedVehicle.value.vehicleDescr}"/></TD> --%>
  <TD><c:out value="${savedVehicle.value.vehicleClassDesc}"/></TD>
</TR>
</c:forEach>

                        
			</table>						


			<ul class="content">
				<li><fmt:message key="review.note2"/></li>
				<li><fmt:message key="review.note3"/></li>
				<li><fmt:message key="review.note4"/></li>
			</ul>


<html:form action="/GetTollTagProcessReview.do" method="POST">
<html:hidden property="password" value="${sessionScope.accountLogin.password}"/>
			<p>
                                  <a href="GetTollTagVehicleInfo.do" class="prev" id="prevLink" onclick="return prevClicked();"><fmt:message key="button.previous"/></a>
                                  <a href="#" class="next" onclick="doSubmit();" id="nextLink"><fmt:message key="button.next"/></a>
			</p>

			</html:form>

		</td>		
		<td class="right"></td>		
	</tr>

<script type="text/javascript">
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
</script>