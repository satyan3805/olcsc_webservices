<%@ include file="/jsp/common/Taglibs.jsp" %>
<br>  
<c:if test="${not empty sessionScope.PAYMENT_CONTEXT.inventoryItems}">
  
<img src="${pageContext.request.contextPath}/images/common/step/arrow-blue.gif" width="17" height="17" align="top" style="padding-right: 20px;"/>
<strong><fmt:message key="otherItemsForm.title"/></strong><br>
    
  <table cellspacing="0" class="datapayment">
      <tr>
        <td width="30">&nbsp;</td>
        <th class="data-th-left" nowrap><fmt:message key="otherItemsForm.item.desc"/></th>
        <th class="data-th-right" nowrap><fmt:message key="otherItemsForm.item.price"/></th>
        <th class="data-th-right"  nowrap><fmt:message key="otherItemsForm.item.quantity"/></th>
        <th class="data-th-left" nowrap><fmt:message key="otherItemsForm.item.attributes"/></th>
        <th class="data-th-right" nowrap><fmt:message key="otherItemsForm.item.total.amount"/></th>
      </tr>
      <c:forEach items="${sessionScope.PAYMENT_CONTEXT.inventoryItems}" var="invItem" varStatus="varStatus">
        <tr>
        <td></td>
          <td>${invItem.itemDscp}</td>
          <td style="text-align: right;"><fmt:formatNumber value="${invItem.itemPrice}" currencySymbol="$" type="currency" /></td>
          <td style="text-align: right;">${invItem.itemQuantity}</td>
          <td>${invItem.attributes}</td>
          <td style="text-align: right;"><fmt:formatNumber value="${invItem.itemTotal}" currencySymbol="$" type="currency" /></td>
        </tr>
      </c:forEach>
      <tr>
        <td colspan="5" align="right"></td>
        <td align="right"></td>
      </tr>

      <tr>
        <td colspan="5" align="right" style="text-align:right;text-decoration:overline"><strong><fmt:message key="otherItemsForm.item.totalInventory"/>:</strong></td>
        <td align="right"><strong><fmt:formatNumber value="${sessionScope.PAYMENT_CONTEXT.totalInventoryPrice}" currencySymbol="$"  type="currency"/></strong></td>
      </tr>
 </table>
 </c:if>
