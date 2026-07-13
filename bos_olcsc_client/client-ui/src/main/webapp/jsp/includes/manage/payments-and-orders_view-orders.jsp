<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

		<div id="content">

			<h1 id="account-activity-transactions">Payments &amp; Orders &ndash; view orders</h1>

			<div>
<c:if test="${hasPending}">
    <p>Your order has been received.
			<c:choose>
			<c:when test="deliveryMethod eq 'PICKUP'">
Once processed you can come and pick it up from the nearest location.
			</c:when>
			<c:otherwise>
Once processed you should receive your EZ TAG(s) within <b>${orderShippingTime}</b>. EZ TAGs are mailed through regular US Postal Service. If you have not received your new tag(s) within this time period, please call Customer Service at <bean:message key="HCTRA.telephone.number" /> <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a> or <a href="${appDelegate.domainName}/about_contact/">contact us by e-mail</a>.
			</c:otherwise>
			</c:choose>
    </p>
 </c:if>
 
  <c:choose>
  <c:when test="${noOrders}">
  
  
  </c:when>
  <c:otherwise>
  <c:if test ="${hasPending == 'false'}">
  
                                <p><em>Have a question about any of the items listed below?</em></p>
                                <p>Contact us by <a href="${appDelegate.domainName}/about_contact/">email</a> or call Customer Service at ${appDelegate.contactPhoneNumber} <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>. </p>
   </c:if>                               
                                
   </c:otherwise>      
    </c:choose>                        
			</div>

                        <display:table name="orders" cellspacing="0" class="data-table" pagesize="${appDelegate.tablePageSize}" requestURI="${pageContext.request.contextPath}/accountViewOrders.do" id="order" decorator="com.etcc.csc.decorator.RowStyleTableDecorator">
                            <display:setProperty name="basic.empty.showtable" value="true" />
                            <display:setProperty name="paging.banner.no_items_found" value="" />
                            <display:column title="Order Date" sortable="false">
                                <fmt:formatDate value="${order.orderDate.time}" dateStyle="SHORT" />
                            </display:column>
                            <display:column property="transactionId" title="TAG Request Activation Number"  sortable="false"/>
                            <display:column property="fullTagId" title="Tag ID"  sortable="false"/>
                            <display:column property="licPlate" title="License Plate" sortable="false"/>
                            <display:column property="type" title="Order" sortable="false"/>
                            <display:column property="qty" title="Qty" sortable="false"/>
                            <display:column property="status" title="Order Status" sortable="false"/>
                            <display:column title="Status Date" sortable="false">
                                    <fmt:formatDate value="${order.statusDate.time}" dateStyle="SHORT" />
                            </display:column>
                        </display:table>
                                
			<%--
                        <table summary="At some point" class="data-table transactions">
				<thead>
					<tr>
						<th scope="col">Order Date</th>
						<th scope="col">EZ TAG #</th>
						<th scope="col">Lic Plate</th>
						<th scope="col">State</th>
						<th scope="col">Type</th>
						<th scope="col">Quantity</th>
						<th scope="col" >Status</th>
						<th scope="col">Status Date</th>
					</tr>
				</thead>
				<tbody>
					<tr class="transaction-type-ez-tag">
						<th scope="row" class="date-2006-09-25">09/25/2006</th>
						<td>HCTR12345678</td>
						<td>ABC123</td>
						<td>TX</td>
						<td>EZ TAG</td>
						<td>1</td>
						<td>MAILED</td>
						<td class="date-2006-09-25">09/25/2006</td>
					</tr>
				</tbody>

			</table>
                        --%>

			<div id="tertiary-navigation-and-or-page-controls">

                                <jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

	</div> <!-- end of content -->

        <jsp:include page="/accountInfo.do"/>
        
</div> <!-- end of content-container -->
