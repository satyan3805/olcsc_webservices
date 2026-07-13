<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>



<tr>
    <td class="topleft"></td>
    <td class="topcenter"></td>
    <td class="topright"></td>
</tr>
<tr id="content-top">
    <td class="left"></td>
    <td class="content">
    <fmt:message key="otherItemsForm.orderDetails"/>: <c:out value="${fulfillmentId }"/>
    </td>		
    <td class="right"></td>		
</tr>
<tr>
    <td class="left"></td>
    <td class="content" width="100%">
		<div id="invItemsBubble" class="switchcontent">
			<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
			<tr>
				<td class="panel-topleft"></td>
				<td class="panel-topcenter"><div><fmt:message key="otherItemsForm.title"/></div></td>
				<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'invItemsBubble')"/></td>
			</tr>
			<tr>
				<td class="panel-left"></td>
				<td class="panel-content">
		        <table cellspacing="1" class="form" width="100%">
		            <tr class="odd-a">
		             
		             <th width="20%"><fmt:message key="otherItemsForm.item.desc"/></th>
		             <th style="text-align: right;"><fmt:message key="otherItemsForm.item.quantity"/></th>
		             <th style="text-align: right;"><fmt:message key="otherItemsForm.item.price"/></th>
		             <th><fmt:message key="otherItemsForm.item.attributes"/></th>   
		            </tr>
		                                
					<c:forEach var="savedInventory" items="${savedInventoryItems}" >
		                 <tr>
		                  <td><c:out value="${savedInventory.itemDscp}"/></td>
		                  <td style="text-align: right;"><c:out value="${savedInventory.itemQuantity}"/></td>
		                  <td style="text-align: right;"><c:out value="${savedInventory.itemPrice}"/></td>
		                  <td><c:out value="${savedInventory.attributes}"/></td>
		                </tr>
		            </c:forEach>
		        </table>
   				</td>		
				<td class="panel-right"></td>		
				</tr>
				<tr>
					<td class="panel-bottomleft"></td>
					<td class="panel-bottomcenter"></td>		
					<td class="panel-bottomright"></td>		
				</tr>
				</table>
		      </div>
    	<c:if test="${not empty savedTags}">
		<div id="tagItemsBubble" class="switchcontent">
			<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
			<tr>
				<td class="panel-topleft"></td>
				<td class="panel-topcenter"><div><fmt:message key="tagRequest.Form.title"/></div></td>
				<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'tagItemsBubble')"/></td>
			</tr>
			<tr>
				<td class="panel-left"></td>
				<td class="panel-content">
		        <table cellspacing="1" class="form" width="100%">
		            <tr class="odd-a">
		             
		             <th width="20%"><fmt:message key="tagRequestForm.licensePlate.short"/></th>
		             <th><fmt:message key="tagRequestForm.state"/></th>
		             <th><fmt:message key="tagRequestForm.year"/></th>
		             <th><fmt:message key="tagRequestForm.color"/></th>   
		             <th><fmt:message key="tagRequestForm.make"/></th>          
		             <th><fmt:message key="tagRequestForm.model"/></th>          
		            </tr>
		                                
					<c:forEach items="savedTags" var="tag">
		                 <tr>
		                  <td><c:out value="${tag.licPlate}"/></td>
		                  <td><c:out value="${tag.licState}"/></td>
		                  <td><c:out value="${tag.vehicleYear}"/></td>
		                  <td><c:out value="${tag.vehicleColor}"/></td>
		                  <td><c:out value="${tag.vehicleMake}"/></td>
		                  <td><c:out value="${tag.vehicleModel}"/></td>
		                </tr>
		            </c:forEach>
		        </table>
   				</td>		
				<td class="panel-right"></td>		
				</tr>
				<tr>
					<td class="panel-bottomleft"></td>
					<td class="panel-bottomcenter"></td>		
					<td class="panel-bottomright"></td>		
				</tr>
				</table>
		      </div>
    	</c:if>
    <br>
    <br>
    <br>
    <br>
	    <table width="100%">
		<tr>
    		<td align="center">
	     			<input type="button" value='Back' onclick="javascript:callOrderStatus();" id="backButton" class="button">
			</td>
		</tr>
      </table>
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
function callOrderStatus(){
	window.location ="${pageContext.request.contextPath}/orderStatus.do";
}

</script>