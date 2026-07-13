<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str"%>

 
 <c:set var="step">
    <fmt:message key="label.step"/>
</c:set>


<tr>
		<td class="left"></td>
		<td class="content">
		<!-- begin content -->
		
		<div class="steps">
			<span><fmt:message key="label.steps"/>:</span>
			<ul>
				<li class="step1-taken">${step}${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-taken">${step} 4</li>
				<li class="step5-taken">${step} 5</li>
				<li class="step6-taken">${step} 6</li>
				<li class="step7-here">${step} 7</li>																					
			</ul>
		</div>
		
		<br class="clear">
		
		<h2><span>${step} 7:</span> <fmt:message key="receipt.header"/></h2>
			
		<p><strong><fmt:message key="receipt.congratulations"/></strong></p>
			<ul class="content">
				<li><fmt:message key="receipt.note1"/></li>
				<li><fmt:message key="receipt.note2"/></li>
				<li><fmt:message key="receipt.note3"/></li>
				<li><fmt:message key="receipt.note4"/></li>
				<li><fmt:message key="receipt.note5"/></li>
			</ul>
			
		<p><strong><fmt:message key="receipt.note6"/></strong></p>
<br>


		<!-- Account Information -->
		<table cellspacing="0" class="confirm">
		<tr>
                  <th class="title" width="600"><fmt:message key="receipt.acctinfo.header"/></th>
		</tr>
                </table>
                
                <table cellspacing="0" class="confirm">
			<tr>
			  <th><fmt:message key="receipt.acctinfo.acctnum"/>:</th>
			  <td>${sessionScope.accountLogin.acctId}</td>
			</tr>
                        <tr>
                          <th><fmt:message key="receipt.acctinfo.userid"/>: </th>
			  <td>${sessionScope.accountLogin.loginId}</td>
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
                  <th class="title" width="600"><fmt:message key="receipt.paymentinfo.header"/></th>
		</tr>
                </table>

                 
	    <table cellspacing="0" class="confirm">
			<tr>
				<td><fmt:message key="receipt.paymentinfo.cardtype"/>:<br>${requestScope.viewHelper.creditCardName}</td>
				<td><fmt:message key="receipt.paymentinfo.cardnum"/>:<br>xxxxxxxxxxxx-<str:substring start="-4">${sessionScope.TollTagForm.map.creditCardNumber}</str:substring></td>
				<td><fmt:message key="receipt.paymentinfo.cardexp"/>:<br>${sessionScope.TollTagForm.map.creditCardExpirationMonth}/${sessionScope.TollTagForm.map.creditCardExpirationYear}</td>				
			</tr>			
		</table>
		
		<!-- Vehicle Information -->

                 		<table cellspacing="0" class="confirm">
		<tr>
                  <th class="title" width="600"><fmt:message key="receipt.vehicleinfo.header"/></th>
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
				<td><strong><fmt:message key="receipt.vehicleinfo.classVehicle"/>:</strong></td>
			</tr>			
                        
                        <c:forEach items="${sessionScope.savedVehicles}" var="savedVehicle" varStatus="varStatus">
<TR>
  <TD>${savedVehicle.value.licPlate}</TD>
                            <td><c:choose><c:when test="${savedVehicle.value.temporaryLicPlate}"><fmt:message key="receipt.vehicleinfo.tempYes"/></c:when><c:otherwise><fmt:message key="receipt.vehicleinfo.tempNo"/></c:otherwise></c:choose></td>
  <TD>${savedVehicle.value.licState}</TD>
  <TD>${savedVehicle.value.vehicleYear}</TD>
  <TD>${savedVehicle.value.vehicleColor}</TD>
  <TD>${savedVehicle.value.vehicleMake}</TD>
  <TD>${savedVehicle.value.vehicleModel}</TD>
  <TD>${savedVehicle.value.vehicleDescr}</TD>
</TR>
</c:forEach>
		</table>

		<p><strong><em><fmt:message key="receipt.note7"/> ${requestScope.viewHelper.vehicleCount} <c:choose><c:when test="${requestScope.viewHelper.vehicleCount > 1}"><fmt:message key="receipt.vehicles"/></c:when><c:otherwise><fmt:message key="receipt.vehicle"/></c:otherwise></c:choose> <fmt:formatNumber value="${requestScope.viewHelper.paymentAmount}" type="currency"/></em></strong></p>
		<p><a href="myAccountHome.do"><img alt="<fmt:message key="receipt.logintext"/>" src="${pageContext.request.contextPath}<fmt:message key="images.loginbutton"/>" /></a></p>

		<!-- end content -->
		</td>		
		
		<td class="right"></td>		
	</tr>
        
        
        
<script type="text/javascript">
                document.all.msgFrame.src='https://help.ntta.org/FrontPage/HotTopic_Sp/ConfirmationPageSpecialMessage';
</script>