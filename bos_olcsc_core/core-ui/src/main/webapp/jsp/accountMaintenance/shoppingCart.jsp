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
    <fmt:message key="shoppingCart.note"/>
    </td>		
    <td class="right"></td>		
</tr>
<tr>
    <td class="left"></td>
    <td class="content" width="100%">
        <html:form action="/ShoppingCartDisplay.do" method="POST"  >
        <html:hidden property="fulfillmentId"/>
	    <c:if test="${not empty shoppingCartForm.savedInventoryItems}">
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
		             
		             <th style="text-align: left;" width="20%"><fmt:message key="otherItemsForm.item.desc"/></th>
		             <th  style="text-align: right;"><fmt:message key="otherItemsForm.item.quantity"/></th>
		             <th  style="text-align: right;"><fmt:message key="otherItemsForm.item.price"/></th>
		             <th  style="text-align: left;"><fmt:message key="otherItemsForm.item.attributes"/></th>   
		             <th style="text-align: center;"><fmt:message key="otherItemsForm.item.remove"/></th>          
		            </tr>
		                                
					<logic:iterate id="savedInventory" name="shoppingCartForm" property="savedInventoryItems" indexId="cnt">
		                 <tr>
		                  <td>
		                      <a HREF="${pageContext.request.contextPath}/otherItems.do?inventoryAction=retriveItem&fulfillmentDetailId=${savedInventory.fulfillmentDetailId}&selectedItemId=${savedInventory.inventoryTypeId}">
			                    <bean:write name="savedInventory" property="itemDscp"/>
		                      </a>
		                  </td>
		                  <html:hidden indexed="true" name="savedInventory" property="fulfillmentDetailId"/>
		                  <td style="text-align: right">
		                  	<bean:write name="savedInventory" property="itemQuantity"/>
		                  </td>
		                  <td  style="text-align: right;">
		                  	<fmt:formatNumber value="${savedInventory.itemPrice}" minFractionDigits="2" maxFractionDigits="2"/>	
		                  </td>
		                  <td  style="text-align: left;"><bean:write name="savedInventory" property="attributes"/></td>
		                  <td  style="TEXT-ALIGN: center"><html:checkbox indexed="true" name="savedInventory" property="isSelected" onclick=""/></td>
		                  <html:hidden indexed="true" name="savedInventory" property="inventoryTypeId"/>
		                  <html:hidden indexed="true" name="savedInventory" property="itemPrice"/>
		                  <html:hidden indexed="true" name="savedInventory" property="attributes"/>
		                  <html:hidden indexed="true" name="savedInventory" property="itemDscp"/>
		                </tr>
		            </logic:iterate>
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
    	<c:if test="${not empty shoppingCartForm.savedTags}">
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
		                                
					<logic:iterate id="savedTag" name="shoppingCartForm" property="savedTags" indexId="cnt">
		                 <tr>
		                  <td>
		                      <a HREF="${pageContext.request.contextPath}/tagRequest.do?inventoryAction=retriveItem&fulfillmentDetailId=${savedInventory.fulfillmentDetailId}&selectedItemId=${savedInventory.inventoryTypeId}">
			                    <bean:write name="savedTag" property="licState"/>
		                      </a>
		                  </td>
		                  <html:hidden indexed="true" name="savedTag" property="fulfillmentDetailId"/>
		                  <td><bean:write name="savedTag" property="licState"/></td>
		                  <td><bean:write name="savedTag" property="vehicleYear"/></td>
		                  <td><bean:write name="savedTag" property="vehicleColor"/></td>
		                  <td><bean:write name="savedTag" property="vehicleMake"/></td>
		                  <td><bean:write name="savedTag" property="vehicleModel"/></td>
		                  <td><html:checkbox indexed="true" name="savedTag" property="isSelected" onclick=""/></td>
		                </tr>
		            </logic:iterate>
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
	    <c:if test="${not empty shoppingCartForm.savedInventoryItems or not empty shoppingCartForm.savedTags}">
	    <table width="100%">
		<tr>
    		<td align="center">
	     			<input type="button" value='<fmt:message key="button.remove.item"/>' onclick="javascript: updateCart();" id="addButton" class="button">
			</td>
		</tr>
      </table>
      <table width="100%">
            <tr>
               <td align="right"><a href="javascript:nextStep();" class="next" id="nextLink" onclick="return doClick();"><fmt:message key="shoppingCart.link.deliveryOption"/></a></td>
           </tr>
      </table>
      </c:if>
    </html:form>
    <br>
    <br>
    <br>
    <br>
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
<script language="javascript">
	function updateCart(){
		document.shoppingCartForm.action ="${pageContext.request.contextPath}/ShoppingCartDisplay.do?cartAction=updateCart";
	    document.shoppingCartForm.submit();
	}
	function nextStep(){
		window.location = "${pageContext.request.contextPath}/deliveryOption.do?fulfillmentId=${shoppingCartForm.fulfillmentId}";
	}

	var nextStepClicked = false;

	function doClick()
	{
	    if (nextStepClicked == false)
	    {
	        document.getElementById('nextLink').className='next-disabled';
	        nextStepClicked = true;
	        return true;
	    }
	    return false;
	}
		
</script>