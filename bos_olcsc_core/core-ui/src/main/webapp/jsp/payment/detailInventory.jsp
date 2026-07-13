<%@ include file="/jsp/common/Taglibs.jsp" %>
<c:if test="${sessionScope.PAYMENT_CONTEXT.callBack == 'shoppingCartCheckOut' }">
<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
	<tr>
		<td class="panel-topleft"></td>
		<td class="panel-topcenter"><div><fmt:message key="otherItemsForm.title"/></div></td>
		<td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" onclick="javascript:show_or_hide(this,'invoiceModule')"/></td>
	</tr>
	<tr>
		<td class="panel-left"></td>
		<td class="panel-content">
		<div id="invoiceModule" class="switchcontent">
		
		
			<table cellspacing="0" width="95%" class="datapayment">
			
		 		<tr class="odd">
					  <th class="data-th-left"><fmt:message key="otherItemsForm.item.desc"/></th>
		              <th class="data-th-left"><fmt:message key="otherItemsForm.item.attributes"/></th>
		              <th style="text-align: right;"><fmt:message key="otherItemsForm.item.price"/></th>
		              <th style="text-align: right;"><fmt:message key="otherItemsForm.item.quantity"/></th>
				</tr>
				<c:forEach items="${sessionScope.PAYMENT_CONTEXT.inventoryItems}" var="inventory" varStatus="varStatus">
		  	    <tr>
			        <td>${inventory.itemDscp}</td>
			        <td>${inventory.attributes}</td>
			        <td style="text-align: right;"><fmt:formatNumber value="${inventory.itemPrice}" currencySymbol="$" type="currency" /></td>
			        <td style="text-align: right;">${inventory.itemQuantity}</td>
		        </tr>
		        </c:forEach>
		        <tr></tr>
		        <tr>
			        <td class="odd" colspan="4" style="text-align:right;"><b><fmt:message key="otherItemsForm.item.totalInventory"/>
			        	:  <fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.totalInventoryPrice}" currencySymbol="$" type="currency" />
			        </b></td>
		      	</tr>
		        </table>
		  </div>
   </td>		
		<td class="panel-right"></td>		
	</tr>
	<tr>
		<td class="panel-bottomleft"></td>
		<td class="panel-bottomcenter"></td>		
		<td class="panel-bottomright"></td>		
	</tr>
	</table>
	</c:if>
                          