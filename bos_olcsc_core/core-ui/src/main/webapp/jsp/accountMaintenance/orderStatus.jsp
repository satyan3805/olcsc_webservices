<%-- 
  - Author(s): Noel Ternida
  - Date: April 24, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Order Status.
  --%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<jsp:useBean id="appHelper"  class="com.etcc.csc.helper.AppHelper" scope="page"/>

<c:set var="orderDate"><fmt:message key="viewOrder.orderDate"/></c:set> 
<c:set var="fulfillmetId"><fmt:message key="viewOrder.fulfillmetId"/></c:set>
<c:set var="fulFillmentType"><fmt:message key="viewOrder.fulfillmetType"/></c:set>
<c:set var="fulFillmentstatus"><fmt:message key="viewOrder.fulfillmetStatus"/></c:set>
<c:set var="deliveryMethod"><fmt:message key="viewOrder.deliveryMethod"/></c:set>
<c:set var="address"><fmt:message key="viewOrder.address"/></c:set>
<c:set var="statusChangedDate"><fmt:message key="viewOrder.statusChangedDate"/></c:set>
<c:set var="status"><fmt:message key="viewOrder.status"/></c:set>



<c:set var="status"><fmt:message key="viewOrder.status"/></c:set>
<c:set var="statusDate"><fmt:message key="viewOrder.statusDate"/></c:set>

	<tr>
		<td class="topleft"></td>
		<td class="topcenter"></td>
		<td class="topright"></td>
	</tr>
	<tr id="content-top">
		<td class="left"></td>
		<td class="content"></td>		
		<td class="right"></td>		
	</tr>
		<!-- begin options (buttons, pulldowns, etc.) -->
	<!-- end options -->	

	<tr>
		<td class="left"></td>
		<!-- begin options (buttons, pulldowns, etc.) -->
	<!-- end options -->	

	<!-- begin tabular data -->
	<td class="content" width="100%">
		<div id="invItemsBubble" class="switchcontent">
			<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
			<tr>
				<td class="panel-topleft"></td>
				<td class="panel-topcenter"><div><fmt:message key="app.orderStatus.title"/></div></td>
				<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'invItemsBubble')"/></td>
			</tr>
			<tr>
				<td class="panel-left"></td>
				<td class="panel-content" width="100%">
				<table width="100%">
			       <tr class="yes-no">
			            <th><strong>${fulfillmetId}</strong></th>
			            <th><strong>${orderDate}</strong></th>
			            <th><strong>${status}</strong></th>
			            <th><strong>${statusChangedDate}</strong></th>
			            <th><strong>${deliveryMethod}</strong></th>
			            <th><strong>${address}</strong></th>
			            <th width="18%"><strong><fmt:message key="app.orderStatus.title.action"/></strong></th>
			        </tr>
				<logic:iterate id="orderDTO" name="orders" indexId="ctr">
				<c:set var="addressId"><bean:write name="orderDTO" property="addrId"/></c:set>
				<c:set var="pickupLocationId"><bean:write name="orderDTO" property="pickupLocationId"/></c:set>
				<c:set var="deliveryMethod"><bean:write name="orderDTO" property="deliveryMethod"/> </c:set>
				<c:set var="isPrepaid"><bean:write name="orderDTO" property="isPrepaid"/></c:set>
				<bean:define id="fulfillmentId" name="orderDTO" property="fulfillmentId"/>
				<c:choose>
					<c:when test="${orderDTO.status == 'Requested' || orderDTO.status == 'REQUESTED' 
					|| orderDTO.status == 'SOLICITADO'}">
						<c:set var="isActive" value="true"/>
					</c:when>
					<c:otherwise>
						<c:set var="isActive" value="false"/>
					</c:otherwise>
				</c:choose>
				<c:set var="parentFulfillmentId"><bean:write name="orderDTO" property="topParentFulfillmentId"/> </c:set>
					 <c:choose>
					 <c:when test="${orderDTO.level == 2}">
						<tr id="${parentFulfillmentId}" style="display:none;">
					 </c:when>
					 <c:otherwise>
					 	<tr>
					 </c:otherwise>
					 </c:choose>
					<bean:define id="orderDate" name="orderDTO" property="orderDate"/>
					 <td><a href="${pageContext.request.contextPath}/orderStatus.do?formAction=displayFulfillmentDetails&fulfillmentId=${fulfillmentId}">
					 	<bean:write name="orderDTO" property="fulfillmentId"/>
					 </a>
					 <logic:equal name="orderDTO" property="level" value="1">
					 <img src="${pageContext.request.contextPath}/images/common/icons/expand_window.png" width="13" height="13" onClick="showChildFulFillments(this, '${fulfillmentId}')"/>
					 </logic:equal>
					 </td>
					 <td><fmt:formatDate value="${orderDate.time}" pattern="MM/dd/yyyy HH:mm:ss"/> </td>
					 <td><bean:write name="orderDTO" property="status"/></td>
					 <logic:present name="orderDTO" property="statusDate">
					  	<bean:define id="statusChangedDate" name="orderDTO" property="statusDate"/>
					 	<td><fmt:formatDate value="${statusChangedDate.time}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
					 </logic:present>
					 <logic:notPresent name="orderDTO" property="statusDate">
					 	<td></td>
					 </logic:notPresent>
					 <td>
					 <!-- KhoiNN modified
					 	delivery method 'MAILED' is hard-coded in the package Olcsc_Acct_Mgmt.Get_Fulfillment_Stat(),
					 	so its the best way to modify the method in the jsp which is faster than
					 	modifying the package -->
					 <c:choose>
					 	<c:when test="${deliveryMethod == 'MAILED' || deliveryMethod == 'M'}">
					 		MAIL
					 	</c:when>
					 	<c:otherwise>
					 		<bean:write name="orderDTO" property="deliveryMethod"/>
					 	</c:otherwise>
					 </c:choose>					 
					 </td>
					 <td><c:choose>
					 	<c:when test="${deliveryMethod == 'MAILED' || deliveryMethod == 'ENVIADO'}">
						    <bean:write name="orderDTO" property="delAdd1"/><br>
						 	<c:set var="addr2"><bean:write name="orderDTO" property="delAdd2"/> </c:set>
						 	<c:if test="${not empty addr2}"> ${addr2}<br></c:if>
						 	<bean:write name="orderDTO" property="city"/>  <bean:write name="orderDTO" property="state"/> <bean:write name="orderDTO" property="zipCode"/>
					 	</c:when>
					 	<c:otherwise>
					 		<bean:write name="orderDTO" property="location"/>
					 	</c:otherwise>
					 	</c:choose>
					 </td>
					 <td>
					 <c:if test="${isActive}">
					   <a href="#" onclick="cancelOrder('${fulfillmentId}', '${isPrepaid }');"><fmt:message key="app.orderStatus.link.cancelOrder"/></a> <br>
					  <a href="${pageContext.request.contextPath}/deliveryOption.do?formAction=updateDeliveryMethod&fulfillmentId=${fulfillmentId}&selectedAddrId=${addressId}&selectedLocationId=${pickupLocationId}&deliveryOption=${deliveryMethod}"><fmt:message key="app.orderStatus.link.updateDelivery"/></a>
					 </c:if>
					 </td> 
					</tr>	
				</logic:iterate>
				</table>
				<td class="panel-right"></td>		
				</tr>
				<tr>
					<td class="panel-bottomleft"></td>
					<td class="panel-bottomcenter"></td>		
					<td class="panel-bottomright"></td>		
				</tr>
				</table>
		      </div>
	</td>
	<td class="right"></td>	
	</tr>	
	<tr id="content-bottom">
		<td class="left"></td>
		<td class="content"></td>		
		<td class="right"></td>		
	</tr>
		
	<tr>
		<td class="bottomleft"></td>
		<td class="bottomcenter"></td>
		<td class="bottomright"></td>
	</tr>
        
<script>
var contractsymbol='${pageContext.request.contextPath}/images/common/icons/expand_window.png' //Path to image to represent contract state.
var expandsymbol='${pageContext.request.contextPath}/images/common/icons/collapse_window.png' //Path to image to represent expand state.

var selectedFulfillmentId = null;
var isPrePaidOrder = null;

function cancelOrder(fulfillmentId, isPrepaid){
	selectedFulfillmentId= fulfillmentId;
	isPrePaidOrder=isPrepaid;
	confirmOKCancel('','<fmt:message key="label.orderStatus.cancel.order.alert.msg"/>','<fmt:message key="label.warning"/>','<fmt:message key="button.ok"/>','<fmt:message key="button.cancel"/>', confirmCancelOrder);
}
function confirmCancelOrder(){
	window.location='${pageContext.request.contextPath}/updateOrder.do?formAction=cancelOrder&fulfillmentId='+selectedFulfillmentId+'&isPrepaid='+isPrePaidOrder;
}
function showChildFulFillments(curobj,id) {
	  var arrElements = document.getElementsByTagName("tr");

	  // walk through the array
	  for (var a = 0; a < arrElements.length; a++){
		  if (arrElements[a].id == id) {
			  if (arrElements[a].style.display == "none")
			  { 
				arrElements[a].style.display = '';
			    curobj.src = expandsymbol;
			  } 
			  else 
			  {
				  arrElements[a].style.display = 'none';
			      curobj.src = contractsymbol;
			  } 
		  }
	  };
	
}
</script>