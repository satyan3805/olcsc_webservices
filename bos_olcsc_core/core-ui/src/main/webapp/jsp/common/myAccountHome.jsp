<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appHelper"  class="com.etcc.csc.helper.AppHelper" scope="page"/>

<c:set var="transactionDate"><fmt:message key="accountHome.transactionDate"/></c:set>
<c:set var="postedDate"><fmt:message key="accountHome.postedDate"/></c:set>
<c:set var="tagId"><fmt:message key="accountHome.tagId"/></c:set>
<c:set var="licPlate"><fmt:message key="accountHome.licPlate"/></c:set>
<c:set var="lane"><fmt:message key="accountHome.lane"/></c:set>
<c:set var="dir"><fmt:message key="accountHome.dir"/></c:set>
<c:set var="location"><fmt:message key="accountHome.location"/></c:set>
<c:set var="description"><fmt:message key="accountHome.transTypeDescr"/></c:set>
<c:set var="amount"><fmt:message key="accountHome.amount"/></c:set>
<c:set var="orderDate"><fmt:message key="accountHome.orderDate"/></c:set>
<c:set var="orderType"><fmt:message key="accountHome.order"/></c:set>
<c:set var="qty"><fmt:message key="accountHome.qty"/></c:set>
<c:set var="status"><fmt:message key="accountHome.status"/></c:set>

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
                                    <display:setProperty name="paging.banner.no_items_found">
                                        <fmt:message key="accountHome.noAlert"/>
                                    </display:setProperty>
                                    <display:setProperty name="paging.banner.one_item_found" value=""/>
                                    <display:setProperty name="paging.banner.some_items_found" value=""/>
                                    <display:column title="" sortable="false">
                                        ${alertRow.alertMsg}
                                    </display:column>
                                </display:table>
                            </div>
                        </c:if>
                        <c:if test="${alerts == null}">
                            <fmt:message key="accountHome.noAlert"/>
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
        
	  <!-- end of panel -->
	<table width="100%" cellpadding="0" cellspacing="0" id="data-table">
            <tr>
                <td class="panel-topleft"></td>
                <td class="panel-topcenter"><div><bean:message key="homePage.lastTransactions.label" arg0="${appHelper.homePageDaysBack}"/></div></td>
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
                            <display:setProperty name="paging.banner.no_items_found">
                                <fmt:message key="accountHome.noTransaction"/>
                            </display:setProperty>
                            <display:setProperty name="paging.banner.one_item_found" value=""/>
                            <display:setProperty name="paging.banner.some_items_found" value=""/>
                            <display:column property="serialNum" title="#"  group="1" sortable="false" class="hideColumn" headerClass="hideColumn"/>
                            <display:column title="${transactionDate}" sortable="false" class="data-nowrap">
                                <fmt:formatDate value="${tran.transactionDate.time}" pattern="MM/dd/yyyy HH:mm"/>
                            </display:column>
                            <display:column title="${postedDate}" sortable="false" class="data-nowrap">
                                <fmt:formatDate value="${tran.postedDate.time}" pattern="MM/dd/yyyy HH:mm"/>
                            </display:column>
                               <display:column property="licPlate" title="${licPlate}" sortable="false"  class="data-nowrap"/>
                            <display:column property="tagId" title="${tagId}"  sortable="false"  class="data-nowrap"/>
                           
                              <display:column title="${lane}" sortable="false"  class="data-nowrap">
                                <c:choose>
                                    <c:when test="${not empty tran.laneDescription}">
                                        <a class="info" href="#" onclick="javascript: return false;">${tran.lane}<span>${tran.laneDescription}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        ${tran.lane} 
                                    </c:otherwise>
                                </c:choose>
                              </display:column>
                              <display:column property="direction" title="${dir}" sortable="false" class="data-nowrap"/>
                              <display:column property="location" title="${location}" sortable="false" class="data-nowrap"/>
                              <display:column property="transTypeDescr" title="${description}" sortable="false" class="data-nowrap"/>
                            <display:column class="align-right" headerClass="align-right" title="${amount}" sortable="false">
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
                <display:table name="pendingOrders" cellspacing="0" style="width:100%"
                    pagesize="500" requestURI="/myAccountHome.do" id="order">
                    <display:setProperty name="paging.banner.all_items_found" value=""/>
                    <display:setProperty name="paging.banner.no_items_found">   
                        <fmt:message key="accountHome.noOrder"/>
                    </display:setProperty>
                    <display:setProperty name="paging.banner.one_item_found" value=""/>
                    <display:setProperty name="paging.banner.some_items_found" value=""/>
                    <display:column title="${orderDate}" sortable="false">
                        <fmt:formatDate value="${order.orderDate.time}" pattern="MM/dd/yyyy"/>
                    </display:column>
                      <display:column property="licPlate" title="${licPlate}" sortable="false"/>
                    <display:column property="fullTagId" title="${tagId}"  sortable="false"/>
                     <display:column property="type" title="${orderType}" sortable="false"/>
                    <display:column property="qty" title="${qty}" sortable="false"/>
                    <display:column property="status" title="${status}" sortable="false"/>
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