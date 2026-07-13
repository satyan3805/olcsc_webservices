<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.enums.InventoryTypeEnum" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>

<jsp:useBean id="appHelper"  class="com.etcc.csc.helper.AppHelper" scope="page"/>

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
<tr>
    <td class="left"></td>
    <td class="content">
        
        <html:form action="/otherItems.do" method="POST" >
        <html:hidden property="currentNumOfAcctCardsAllowed" styleId="currentNumOfAcctCardsAllowed"/>
        <c:if test="${empty otherItemsForm.addedInvItemList}"> 
	        <p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="otherItemsForm.note"/>
	        <fmt:message key="required.fields.label"/></p>
	    </c:if>    
        	<table cellspacing="1" class="form"  align="center" width="50%">        		
	        	<tr>	        		
	        		<td  class="text-data">
        			<table width="100%">
        				<tr>
        					<c:choose>
        					<c:when test="${not empty otherItemsForm.addedInvItemList}">
        					<th width="30%"><fmt:message key="otherItemsForm.ItemType"/></th>
        					</c:when>
        					<c:otherwise>
        					<th width="30%"><fmt:message key="otherItemsForm.ItemType"/><font color="red"> *</font></th>
							</c:otherwise> 
							</c:choose>       					
        					<td>
        					<c:choose>
        					<c:when test="${otherItemsForm.inventoryAction == 'retriveItem'}">
        						<html:hidden name="otherItemsForm" property="selectedItemId" />
       							<html:hidden name="otherItemsForm" property="selectedItem" />
       							<html:hidden name="otherItemsForm" property="fulfillmentDetailId" />
 				             	<bean:write name="otherItemsForm" property="selectedItem" />
         					</c:when>
        					<c:otherwise>
	        					<html:select property="selectedItemId" size="1" styleClass="text" onchange="javascript: getItemAttrb();">        						
					            	 <html:option value="0"><fmt:message key="otherItemsForm.item.selectItem"/></html:option> 
						 			 <html:optionsCollection property="itemlist" label="itemDscp" value="inventoryTypeId" />     
				             	</html:select>             	  
				             	<html:hidden property="selectedItem"/>
        					</c:otherwise>
        					</c:choose>
        					</td>
        				</tr>        				
        			 </table>
	        		 <div id="priceAndQty">
	        		 	<table width="100%">
	        		 		<tr>
			             		<html:hidden property="itemPrice"/>
	        		 			<th width="30%"><fmt:message key="otherItemsForm.item.price"/></th>
	        		 			<td>
	        		 			    <fmt:formatNumber value="${otherItemsForm.itemPrice}" minFractionDigits="2" maxFractionDigits="2"/>
	        		 			
	        		 				
	        		 			</td>
	        		 		</tr>
	        		 		<tr>
	        		 			<th><fmt:message key="otherItemsForm.item.quantity"/><font color="red"> *</font></th>
	        		 			<td>
	        		 			<html:text property="itemQuantity" styleClass="text" maxlength="3"></html:text>
	        		 			</td>
	        		 			
	        		 		</tr>
	        		 	</table>	        		 	
	        		 </div>	
		            <c:set var="attrCount">${otherItemsForm.count}</c:set>
		             <c:if test="${otherItemsForm.count > 0}">
	                 <input type="hidden" name="cnt" id="cnt" value="${otherItemsForm.count}"/>
	                 <table width="100%">
			             <logic:iterate id="attrbArry" name="otherItemsForm" property="attrbArry" indexId="idx">   
				             <tr>
				                <th  width="30%">
					                <bean:write name="attrbArry" property="attrbPromptText"/>							  	
				                </th>              
				                <td>
									<html:hidden indexed="true" name="attrbArry" property="attrbId"/>										
									<html:hidden indexed="true" name="attrbArry" property="isDisplyedInDropDown"/>										
				                  	<c:choose>
										<c:when test="${attrbArry.isDisplyedInDropDown == 'Y'}">
											<c:set var="temp" value="${attrbArry.attrbValueAsList}"/>          
											      
						                	<html:select indexed="true" name="attrbArry"  styleClass="text" property="inventoryTypeAttrValueId" 
						                			onchange="javascript:setAttrbValueText(${idx});">			                		
						                		<html:options collection="temp" property="value" labelProperty="label"/>
						                	</html:select>
						                	<html:hidden indexed="true" name="attrbArry" property="attrbValue"/>
										</c:when>
										
										<c:otherwise>
											<c:set var="maxLength" value=""/>
											<c:if test="${attrbArry.maxLength > 0}">
												<c:set var="maxLength" value="${attrbArry.maxLength}"/>
											</c:if>
											<c:set var="onblurJS" value=""/>
											<c:if test="${attrbArry.attrbType != null}">
												<c:if test="${attrbArry.attrbType == 'NUMBER'}">
													<c:set var="onblurJS" value="javascript: checkIfInteger(this.value)"/>
												</c:if>
											</c:if>
											<html:text indexed="true" name="attrbArry" styleClass="text" property="attrbValue" 
											maxlength="${maxLength}"/>
											<html:hidden indexed="true" name="attrbArry" property="attrbType"/>	
											<html:hidden indexed="true" name="attrbArry" property="attrbPromptText"/>										
										</c:otherwise>
									</c:choose>
									<html:hidden indexed="true" name="attrbArry" property="attrbName"/>										
				                </td>
				              </tr>            
				             
						</logic:iterate>
					</table>
           			 </c:if>     
	        		</td>
	        	</tr>
	        	<tr>
	            <c:choose>
   				<c:when test="${otherItemsForm.inventoryAction == 'retriveItem'}">
		    		<td align="right">
			     			<input type="button" value='Update Cart' onclick="javascript: updateCart();" id="addButton" class="button">
					</td>
				</c:when>
				<c:otherwise>
		    		<td align="center">
  						<c:if test="${not empty otherItemsForm.inventoryAction}">
			     			<input type="button" value='<fmt:message key="button.add"/>' onclick="javascript: addToList();" id="addButton" class="button"/>
			     		</c:if>
					</td>
				</c:otherwise>
	            </c:choose>
       		</tr>
            </table>
			<c:if test="${otherItemsForm.inventoryAction != 'retriveItem'}">
            <c:if test="${not empty otherItemsForm.addedInvItemList}"> 
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
		        	<table id="data" class="content" id="data-table" width="100%">
		       		<tr class="yes-no">
		             <th style="text-align: left;"><fmt:message key="otherItemsForm.item.desc"/></th>
		             <th style="text-align: right;" width="10%"><fmt:message key="otherItemsForm.item.quantity"/></th>
		             <th style="text-align: right;"><fmt:message key="otherItemsForm.item.price"/></th>
		             <th style="text-align: left;"><fmt:message key="otherItemsForm.item.attributes"/></th>             
		             <th style="text-align: center;"><fmt:message key="otherItemsForm.item.remove"/></th>             
			        </tr>
	           		<logic:present  name="otherItemsForm" property="addedInvItemList" > 
					<logic:iterate id="inventoryItem" name="otherItemsForm" property="addedInvItemList" indexId="cnt">
	                <tr> 
	                  <td><bean:write name="inventoryItem" property="itemDscp"/></td>
	                  <td>
	                  	<html:text indexed="true" name="inventoryItem" property="itemQuantity" styleId="inventoryItem${cnt}itemQuantity" styleClass="text" maxlength="3" style="TEXT-ALIGN:right"/>
	                  	<html:hidden indexed="true" name="inventoryItem" property="previousItemQty" styleId="inventoryItem${cnt}previousItemQty"/>
	                  </td>
	                  <td style="TEXT-ALIGN: right">
	                	<fmt:formatNumber value="${inventoryItem.itemPrice}" minFractionDigits="2" maxFractionDigits="2"/>	                  	
	                  </td>
	                  <td><bean:write name="inventoryItem" property="attributes"/></td>
	                  <td style="TEXT-ALIGN: center"><html:checkbox indexed="true" name="inventoryItem" property="isSelected"  styleId="inventoryItem${cnt}isSelected" onclick=""/></td>
	                  <html:hidden indexed="true" name="inventoryItem" property="inventoryTypeId" styleId="inventoryItem${cnt}inventoryTypeId"/>
	                  <html:hidden indexed="true" name="inventoryItem" property="itemPrice"/>
	                  <html:hidden indexed="true" name="inventoryItem" property="attributes"/>
	                  <html:hidden indexed="true" name="inventoryItem" property="itemDscp"/>
	            	  <c:if test="${not empty inventoryItem.inventoryItemAttrbs}"> 
	                  <logic:iterate id="itemAttrb" name="inventoryItem" property="inventoryItemAttrbs" indexId="ct">
	                   		<html:hidden property="inventoryItem[${cnt}].itemAttrb[${ct}].attrbId"/>
	                   		<html:hidden property="inventoryItem[${cnt}].itemAttrb[${ct}].attrbName"/>
	                   		<html:hidden property="inventoryItem[${cnt}].itemAttrb[${ct}].attrbValue"/>
	                   		<html:hidden property="inventoryItem[${cnt}].itemAttrb[${ct}].inventoryTypeAttrValueId"/>
	                  </logic:iterate>
	                  </c:if>
	                </tr>
	            	</logic:iterate>  
	            	</logic:present>
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
				<br>
            	<table align="center">
            	<tr>
				    <td align="center">
				     	<input type="button" value='<fmt:message key="button.update.changes"/>' onclick="javascript: updateChanges();"
										  id="updateButton" class="button"/> <br/>
					</td>		
				</tr> 
            
            </table>
            	<table align="right">
            	<tr>
				    <td width="5%"></td>		
									
					<td align="right"><a href="#" class="next" id="nextLink" onclick="javascript: addToCart();"><fmt:message key="button.next"/></a></td>
				</tr> 
            
            </table>
            </div>
           	</c:if> 
            </c:if>
    	</html:form>
    
    </td>		
    <td class="right"></td>		
</tr>
        

        
<tr>
    <td class="bottomleft"></td>
    <td class="bottomcenter"></td>
    <td class="bottomright"></td>
</tr>

<script language="javascript">
String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
}

showHidepriceAndQty();
setAllAttrbValueText(${attrCount});
function addToCart(){
	//alert("hello" +document.otherItemsForm.action);
	document.otherItemsForm.action ="${pageContext.request.contextPath}/otherItems.do?inventoryAction=addToCart";
    document.otherItemsForm.submit();
}
function updateCart(){
	//alert("hello" +document.otherItemsForm.action);
	 var selectedItemQuantity = document.otherItemsForm.elements["itemQuantity"].value.trim();
	 if (selectedItemQuantity == '' || selectedItemQuantity == '0') {
		 confirmOK('','<fmt:message key="otherItemsForm.item.quantity.validation"/>', '','<fmt:message key="button.ok"/>');
		 return;
	 }
	document.otherItemsForm.action ="${pageContext.request.contextPath}/otherItems.do?inventoryAction=updateCart";
    document.otherItemsForm.submit();
}
function updateChanges() {
	if(validateChanges()){
		document.otherItemsForm.action ="${pageContext.request.contextPath}/otherItems.do?inventoryAction=updateList";
	    document.otherItemsForm.submit();
	}
}
function validateChanges(){
	var hasAcctCard = false;
	var netAcctCardChanges = 0;
	
	for(var i=0; i< ${otherItemsForm.addedInvItemListSize} ; i++){
		var currentQtyElemValue = document.getElementById('inventoryItem'+i+'itemQuantity').value;
		if(currentQtyElemValue == null || currentQtyElemValue == ''){
			confirmOK('','<fmt:message key="otherItemsForm.item.quantity.validation"/>', '','<fmt:message key="button.ok"/>');
			return false;
		}
		var inventoryTypeId = document.getElementById('inventoryItem'+i+'inventoryTypeId').value;
		if(inventoryTypeId == '<%= InventoryTypeEnum.ACCOUNT_CARD.getId() %>'){
			hasAcctCard = true;
			var reqToRemove = document.getElementById('inventoryItem'+i+'isSelected').checked;
			var previousQty = new Number(document.getElementById('inventoryItem'+i+'previousItemQty').value).valueOf();
			if(reqToRemove){
				netAcctCardChanges -= previousQty;
			}else{
				netAcctCardChanges += (new Number(currentQtyElemValue).valueOf() - previousQty);
			}
		}
	}
	if(hasAcctCard && netAcctCardChanges != 0){
		var currentNumOfAcctCardsAllowed = new Number(document.otherItemsForm.currentNumOfAcctCardsAllowed.value).valueOf();
		if(currentNumOfAcctCardsAllowed < netAcctCardChanges){
			confirmOK('','<fmt:message key="otherItemsForm.item.quantity.exceedMax"><fmt:param value="${numOfAdditionalAccountCards}"/></fmt:message>', '','<fmt:message key="button.ok"/>');
			return false;
		}
	}
	return true;
}
function getItemAttrb(){
	document.otherItemsForm.action ="${pageContext.request.contextPath}/otherItems.do?inventoryAction=getAttrbs";
    document.otherItemsForm.submit();
}
function setAllAttrbValueText(idx) {
	 for (i=0; i<idx; i++) {
		 setAttrbValueText(i)
	 }
}
function setAttrbValueText(idx) {
	var ele = 'attrbArry'+'[' + idx + ']' + '.inventoryTypeAttrValueId'
	var attrbValueTextElem = document.otherItemsForm.elements[ele];
	if (attrbValueTextElem != null) {
		 var attrbValueTextIndex = attrbValueTextElem.selectedIndex;
		 if (attrbValueTextIndex != null) {
			 var attrbValueText = attrbValueTextElem.options[attrbValueTextIndex].text;
			 document.otherItemsForm.elements['attrbArry'+'[' + idx + ']' + '.attrbValue'].value = attrbValueText;
		 }
	 }
}
function addToList(){	
	 var selectedItem = document.otherItemsForm.elements["selectedItemId"];
	 var selectedItemIndex = selectedItem.selectedIndex;
	 if(selectedItemIndex == 0){
		return;
	 }
	 var selectedItemDesc = selectedItem.options[selectedItemIndex].text;
	 document.otherItemsForm.elements["selectedItem"].value=selectedItemDesc;
	 
	 var selectedItemQuantity = document.otherItemsForm.elements["itemQuantity"].value.trim();
	 var maxInvRequestCount = '${MAX_INVENTORY_REQUEST_COUNT}';
	 if(Number(selectedItemQuantity) > Number(maxInvRequestCount)) {
		var msg = '<fmt:message key="otherItemsForm.item.quantity.maximumMsg"/>'+maxInvRequestCount;
		confirmOK('',msg, '','<fmt:message key="button.ok"/>');
		return false;
	 }
	 
	 if (selectedItemQuantity == '' || selectedItemQuantity == '0') {
		 confirmOK('','<fmt:message key="otherItemsForm.item.quantity.validation"/>', '','<fmt:message key="button.ok"/>');
		 return;
	 }
	 document.otherItemsForm.elements["itemQuantity"].value = selectedItemQuantity;
	 	 
	 if((selectedItem.value == '<%= InventoryTypeEnum.ACCOUNT_CARD.getId()%>') && (selectedItemQuantity>document.otherItemsForm.currentNumOfAcctCardsAllowed.value)){
		 confirmOK('','<fmt:message key="otherItemsForm.item.quantity.exceedMax"><fmt:param value="${numOfAdditionalAccountCards}"/></fmt:message>', '','<fmt:message key="button.ok"/>');
		 return;
	 }
	 var elLength = document.otherItemsForm.elements.length;
	 for (i=0; i<elLength; i++)
     {   
	    var elmName = otherItemsForm.elements[i].name;	    
	    if(elmName.indexOf('attrbType') != -1){
	    	var elmValue = document.otherItemsForm.elements[elmName].value; 
	    	if(elmValue == 'NUMBER'){
		    	var strIndex = elmName.substring(elmName.indexOf('[')+1, elmName.indexOf(']'))
		    	var temp = 'attrbArry['+strIndex+'].attrbValue';
		    	var numberEle = document.otherItemsForm.elements[temp].value;
		    	if(!isInteger(numberEle)){
			    	var fieldName = 'attrbArry['+strIndex+'].attrbPromptText';
			    	var msg = document.otherItemsForm.elements[fieldName].value + '<fmt:message key="errors.long"/>';
			    	confirmOK('',msg, '','<fmt:message key="button.ok"/>');
		    		return;
		    	}
		    	
	    	}
	    }
	    
     }
	document.otherItemsForm.action ="${pageContext.request.contextPath}/otherItems.do?inventoryAction=addToList";
    document.otherItemsForm.submit();
}

function showHidepriceAndQty(){
	var selectedItemIdElem = document.otherItemsForm.elements["selectedItemId"];
	if (selectedItemIdElem != null) {
		var selectedItem = document.otherItemsForm.elements["selectedItemId"].value;
		if(selectedItem != null && selectedItem !=0){
			document.getElementById('priceAndQty').style.display = ''; 
 			if (document.otherItemsForm.itemQuantity.value == 0) {
 				document.otherItemsForm.itemQuantity.value = ''
 			}
		}else{
			document.getElementById('priceAndQty').style.display = 'none'; 
		}	
	}
}

</script>