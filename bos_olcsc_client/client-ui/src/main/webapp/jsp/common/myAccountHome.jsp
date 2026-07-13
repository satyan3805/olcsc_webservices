<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
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
        <!-- begin options (buttons, pulldowns, etc.) -->
        <!-- end options -->	
	<!-- begin tabular data -->
<%--        <td id="data" class="content">--%>
        <td class="content">
		
	<!-- IMPORTANT: If any of the cells below have no data, make sure to insert an &nbsp; (e.g. see Account Summary below)
	otherwise the cell will be invisible, and the border will break in that cell. -->

        <table width="100%" cellpadding="0" cellspacing="0" id="data-table">
            <tr>
                <td class="panel-topleft"></td>
                <td class="panel-topcenter"><div><bean:message key="homePage.label"/></div></td>
                <td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" width="13" height="13" onClick="expandcontent(this, 'sc33')" /></td>
            </tr>
            <tr>
                <td class="panel-left"></td>
                <td class="panel-content">
                    <div id="sc33" class="switchcontent">
                        <c:if test="${alerts != null}">
                            <div id="alert">
                                <display:table name="alerts" cellspacing="0" 
                                    pagesize="500" requestURI="/myAccountHome.do" id="alertRow">
                                    <display:setProperty name="paging.banner.all_items_found" value=""/>
                                    <display:setProperty name="paging.banner.no_items_found" value="No alerts at this time."/>
                                    <display:setProperty name="paging.banner.one_item_found" value=""/>
                                    <display:setProperty name="paging.banner.some_items_found" value=""/>
                                    <display:column title="" sortable="false">
                                        ${alertRow.alertMsg}
                                    </display:column>
                                </display:table>
                            </div>
                        </c:if>
                        <c:if test="${alerts == null}">
                            No alerts at this time.
                        </c:if>
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

<%----%>
        <p>&nbsp;</p>
        
	  <!-- end of panel -->
	<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
            <tr>
                <td class="panel-topleft"></td>
                <td class="panel-topcenter"><div><bean:message key="homePage.lastTransactions.label" arg0="${appDelegate.homePageDaysBack}"/></div></td>
                <td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" width="13" height="13" onClick="expandcontent(this, 'sc34')" /></td>
            </tr>
            <tr>
            <td class="panel-left"></td>
            <td class="panel-content">
                <div id="sc34" class="switchcontent">
                    <c:if test="${trans != null}">
                        <display:table name="trans" cellspacing="0" cellpadding="0"
                            pagesize="10" requestURI="/account/lastTransaction.do" id="tran"
                            decorator="com.etcc.csc.decorator.GroupRowColorTableDecorator"
                            defaultorder="descending" defaultsort="1">
                            <display:setProperty name="paging.banner.all_items_found" value=""/>
                            <display:setProperty name="paging.banner.no_items_found" value="No current transactions to display."/>
                            <display:setProperty name="paging.banner.one_item_found" value=""/>
                            <display:setProperty name="paging.banner.some_items_found" value=""/>
                            <display:column property="serialNum" title="#"  group="1" sortable="false" class="hideColumn" headerClass="hideColumn"/>
                            <display:column title="Transaction Date/Time" sortable="false" class="data-nowrap">
                                <fmt:formatDate value="${tran.transactionDate.time}" type="both" />
                            </display:column>
                            <display:column title="Posted Date/Time" sortable="false" class="data-nowrap">
                                <fmt:formatDate value="${tran.postedDate.time}" type="both" />
                            </display:column>
                            <display:column property="tagId" title="Tag ID"  sortable="false"  class="data-nowrap"/>
                              <display:column property="licPlate" title="License Plate" sortable="false"  class="data-nowrap"/>
                              <display:column title="Lane" sortable="false"  class="data-nowrap">
                                <c:choose>
                                    <c:when test="${not empty tran.laneDescription}">
                                        <a class="info" href="#" onclick="javascript: return false;">${tran.lane}<span>${tran.laneDescription}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        ${tran.lane} 
                                    </c:otherwise>
                                </c:choose>
                              </display:column>
                              <display:column property="direction" title="Dir" sortable="false" class="data-nowrap"/>
                              <display:column property="location" title="Location" sortable="false" class="data-nowrap"/>
                              <display:column property="transTypeDescr" title="Description" sortable="false" class="data-nowrap"/>
                            <display:column class="align-right" headerClass="align-right" title="Amount" sortable="false">
                                <fmt:formatNumber value="${tran.amount}" minFractionDigits="2" maxFractionDigits="2"/>
                            </display:column>
                        </display:table>
                    </c:if>
                    <c:if test="${trans == null}">
                        <bean:message key="homePage.lastTransactions.noRecords"/>
                    </c:if>
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

	<p>&nbsp;</p>
	<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
      <tr>
        <td class="panel-topleft"></td>
        <td class="panel-topcenter"><div><bean:message key="homePage.orders.label"/></div></td>
        <td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" width="13" height="13" onClick="expandcontent(this, 'sc35')" /></td>
      </tr>
      <tr>
        <td class="panel-left"></td>
        <td class="panel-content">
            <div id="sc35" class="switchcontent">
                <display:table name="pendingOrders" cellspacing="0" 
                    pagesize="500" requestURI="/myAccountHome.do" id="order">
                    <display:setProperty name="paging.banner.all_items_found" value=""/>
                    <display:setProperty name="paging.banner.no_items_found" value="No pending orders."/>
                    <display:setProperty name="paging.banner.one_item_found" value=""/>
                    <display:setProperty name="paging.banner.some_items_found" value=""/>
                    <display:column title="Order Date" sortable="false">
                        <fmt:formatDate value="${order.orderDate.time}" />
                    </display:column>
                    <display:column property="fullTagId" title="Tag ID"  sortable="false"/>
                    <display:column property="licPlate" title="License Plate" sortable="false"/>
                    <display:column property="type" title="Order" sortable="false"/>
                    <display:column property="qty" title="Qty" sortable="false"/>
                    <display:column property="status" title="Order Status" sortable="false"/>
                </display:table>
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
    
        <p/>
        <p/>
        <p/>
        <p/>
        <p/>
        <p/>

		</td>		
		<!-- end tabular data-->
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

<script type="text/javascript">
    changeFont('-small');
    readFontSize = false;
</script>
