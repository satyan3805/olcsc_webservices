<%@ include file="/jsp/common/Taglibs.jsp" %>

<script type="text/javascript">

var pickupHours = '${sessionScope.PAYMENT_CONTEXT.pickupLocationHours}';
pickupHours = pickupHours.replace(/#/g,"<br/>");
</script>

<tr>
  <td class="topleft"></td>
  <td class="topcenter"></td>
  <td class="topright"></td>
</tr>

<tr>
  <td class="left"></td>
  <td class="content">
     <div style="text-align:right;">
      <a href="DisplayPaymentReport.do?format=PDF" target="_blank"><fmt:message key="label.pdf"/></a>&nbsp;
      <a href="DisplayPaymentReport.do?format=HTML" target="_blank"><fmt:message key="label.printerFriendly"/></a>
     </div>
	 
     <h2><fmt:message key="PaymentInfoForm.receipt.header"/></h2>
     <!-- ****** Changes for DEFECT # 1091 - START ****** -->
     <c:if test="${sessionScope.PAYMENT_CONTEXT.pickupLocationId != null}">
	 	<div style="font-weight:bold;color:green">Fulfillment was successful.</div><br/>
	 	The location days/hours of operation are <br/>
		<div id="pickupLocationHoursDiv"></div><br/>
	 	The address is <c:out value="${sessionScope.PAYMENT_CONTEXT.pickupLocationAddress}"></c:out>.<br/><br/>
	 </c:if>
	 <!-- ****** Changes for DEFECT # 1091 - END ****** -->
	 
     <c:if test="${sessionScope.PAYMENT_CONTEXT.paymentAmount == 0}">
  		 <p><fmt:message key="PaymentInfoForm.receipt.noPaymentMsg"/>
     </c:if>
     
     <c:if test="${sessionScope.PAYMENT_CONTEXT.paymentAmount > 0}">
	      <p><fmt:message key="PaymentInfoForm.receipt.note1"/>
	      <c:if test="${sessionScope.PAYMENT_CONTEXT.postingStatus == 'P'}">
	        <fmt:message key="PaymentInfoForm.receipt.note2"/>
	      </c:if>
	      <br><fmt:message key="PaymentInfoForm.receipt.note3"/>
     </c:if>
     <p>&nbsp;</p>

  <tiles:insert name="method" ignore="true"/>
  <tiles:insert name="invoice" ignore="true"/>
  <tiles:insert name="inventory" ignore="true"/>
  <tiles:insert name="violation" ignore="true"/>
  <tiles:insert name="tag" ignore="true"/>
  <tiles:insert name="deposit" ignore="true"/>
  <tiles:insert name="total" ignore="true"/>
  <br><br><br><br><br><br><br><br><br><br>
  </td>
  <td class="right"></td>
</tr>

<tr>
  <td class="bottomleft"></td>
  <td class="bottomcenter"></td>
  <td class="bottomright"></td>
</tr>

<script type="text/javascript">
if(pickupHours != ""){
	document.getElementById("pickupLocationHoursDiv").innerHTML = pickupHours;	
}
</script>