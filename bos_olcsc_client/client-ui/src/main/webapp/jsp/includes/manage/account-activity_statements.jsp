<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

	<form id="data-actions" name="StatementsForm" action="${pageContext.request.contextPath}/accountStatements.do" method="post">

		<div id="content">

			<h1 id="account-activity-statements">Account Activity &ndash; Statements</h1>

                        <logic:messagesPresent message="true" property="viewStatementErrors">
                                    <div class="section">
                                    <dl class="errors"/>
                                    <html:messages id="msg" message="true" property="viewStatementErrors">
                                        <dd><bean:write name="msg"/></dd>
                                    </html:messages>
                                    </div>
                        </logic:messagesPresent>

			<c:if test="${not empty monthList}">
                        <fieldset>
				<dl>
					<dt><label for="statement-month-year">Show<span class="accessibility"> statement month and year</span></label></dt>
						<dd>
							<select id="statement-month-year" name="statementMonth">
                                                            <c:forEach var="dateRec" items="${monthList}">
                                                                <option value="${dateRec.value}"
                                                                <c:if test="${StatementForm.map.statementMonth == dateRec.value}">
                                                                    selected
                                                                </c:if>
                                                                >${dateRec.label}</option>
                                                            </c:forEach>
							</select>

							<input type="submit" value="Display" onclick="javascript: document.StatementsForm.action='${pageContext.request.contextPath}/accountStatements.do';showLoading();"/>
						</dd>
				</dl>
			</fieldset>
                        </c:if>
                        <br>

<div class="section with-primary-and-secondary-content">
<c:if test="${not empty statementMonth}">
 <h2>Monthly Statement for ${statementMonth}</h2>
</c:if> 
 <div class="primary-section-content">
 <c:if test="${not empty accountStatement.tagSummary}">   
        <!-- begin Tag Summary -->
        <h2 id="ez-tag-summary">EZ TAG Summary</h2>
        <display:table name="accountStatement.tagSummary" cellspacing="0" class="data-table"
                pagesize="0" requestURI="${pageContext.request.contextPath}/accountStatements.do" id="tagRecord"
                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="tagSmry">
            <display:column property="tagId" class="${cellStyle}" title="Tag ID" sortable="true" sortProperty="tagId"/>
            <display:column property="description" class="${cellStyle}" title="Description" sortable="true" sortProperty="description"/>
            <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="true" sortProperty="quantity"/>
            <display:column class="currency" headerClass="currency-header" title="Amount" sortable="true" sortProperty="amount">
                <fmt:formatNumber value="${tagSmry.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            </display:column>
        </display:table>
</c:if>

<c:if test="${not empty accountStatement.tagSummaryHAS}">   
    <!-- begin Tag Summary -->
        <h2 id="ez-tag-summary">Houston Airport Summary</h2>
        <display:table name="accountStatement.tagSummaryHAS" cellspacing="0" class="data-table"
                pagesize="0" requestURI="${pageContext.request.contextPath}/accountStatements.do" id="tagRecord"
                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="tagSmryHAS">
            <display:column property="tagId" class="${cellStyle}" title="Tag ID" sortable="true" sortProperty="tagId"/>
            <display:column property="description" class="${cellStyle}" title="Description" sortable="true" sortProperty="description"/>
            <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="true" sortProperty="quantity"/>
            <display:column class="currency" headerClass="currency-header" title="Amount" sortable="true" sortProperty="amount">
                <fmt:formatNumber value="${tagSmryHAS.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            </display:column>
        </display:table>
</c:if>
 </div>
 
 <div class="secondary-section-content">
  <c:if test="${not empty accountStatement.acctSummary}"> 
        <h2 id="account-summary">EZ Account Summary</h2>
        <display:table name="accountStatement.acctSummary" cellspacing="0" class="data-table"
            pagesize="0" requestURI="${pageContext.request.contextPath}/accountStatements.do" id="record"
            decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="acctSmry">
            <display:column property="description"  title="Description" sortable="true" sortProperty="description"/>
            <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="true" sortProperty="quantity"/>
            <display:column class="currency" headerClass="currency-header" title="Amount" sortable="true" sortProperty="amount">
                <fmt:formatNumber value="${acctSmry.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            </display:column>
        </display:table>
</c:if>  

<c:if test="${not empty accountStatement.acctSummaryHAS}"> 
        <h2 id="account-summary">Houston Airport Activity</h2>
        <display:table name="accountStatement.acctSummaryHAS" cellspacing="0" class="data-table"
            pagesize="0" requestURI="${pageContext.request.contextPath}/accountStatements.do" id="record"
            decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="acctSmryHAS">
            <display:column property="description"  title="Description" sortable="true" sortProperty="description"/>
            <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="true" sortProperty="quantity"/>
            <display:column property="amount" class="currency" headerClass="currency-header" title="Amount" sortable="true" sortProperty="amount">
                <fmt:formatNumber value="${acctSmryHAS.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            </display:column>
        </display:table>
</c:if>
 </div>
</div> 

  <c:if test="${accountStatement.records!=null}">
    <br>
    <h2>Statement Detail - Harris County Toll Road Authority</h2>
    <!-- begin statement for current month -->
<table cellspacing = "0" class="data-table transactions">
<thead>
    <tr>
      <th id="sno-header">#</th>
      <th id="txn-date-header">Transaction Date/Time</th>
      <th id="post-date-header">Posted Date/Time</th>
<%--      <!-- This column is for calculating subtotals using JavaScript. -->
      <th style="width:0;" width="0"></th>--%>
      <th id="tagid-header" >Vehicle</th>
      <th id="vehicle-class-code-header" >Axle Count</th>
      <th id="location-header" >Location</th>
      <th id="description-header" >Description</th>
      <th id="amount-header" class="currency-header">Amount</th>
    </tr>
  </thead>

<c:forEach items="${accountStatement.records}" var="statement" varStatus="varStatus">
  <c:choose>
  <c:when test="${statement.description != null}">
  <tr>
  <td>${statement.serialNumber}</td>
  <td>
    <fmt:formatDate value="${statement.transactionDate.time}" type="both" dateStyle="SHORT" />    
  </td>
  <td>
    <fmt:formatDate value="${statement.postedDate.time}" type="both" dateStyle="SHORT" />    
  </td>
  <td>${statement.tagId}</td>
  <td>${statement.vehicleClassCode}</td>
  <td>${statement.location}</td>
  <td>${statement.description}</td>
  <td>
  <fmt:formatNumber value="${statement.amount}" minFractionDigits="2" maxFractionDigits="2"/>
  </td>
 </tr> 
 </c:when>
 <c:otherwise>
   <tr>
   <td colspan = "8">
     <b>${statement.laneFullName}</b>
   </td>   
  </tr>
  </c:otherwise>
  </c:choose>
  </c:forEach>
  </table>
  </c:if>
   
  
    <!-- end statement for current month -->

<c:if test="${not empty accountStatement.recordsHAS}">
<br>
<h2>Statement Detail - Houston Airport System</h2>
<!-- begin statement for current month -->
<display:table name="accountStatement.recordsHAS" cellspacing="0" sort="list" class="data-table transactions"
            pagesize="0" requestURI="${pageContext.request.contextPath}/accountStatements.do" id="record"
            decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="stmtHAS">
        <display:column title="#"  property="serialNumber" group="1" sortable="false" class="hideColumn" headerClass="hideColumn"/>
    <display:column title="Transaction Date/Time" property="strTrxnDate" sortable="true" sortProperty="transactionDate">
        <fmt:formatDate value="${stmtHAS.transactionDate.time}" type="both" dateStyle="SHORT" />
    </display:column>
    <display:column title="Posted Date/Time" sortable="true" sortProperty="postedDate">
        <fmt:formatDate value="${stmtHAS.postedDate.time}" type="both" dateStyle="SHORT" />
    </display:column>
    <display:column property="tagId" title="Vehicle" sortable="false"/>
    <display:column property="location" title="Location" sortable="false"/>
    <display:column property="description" title="Description" sortable="false"/>
    <display:column style="text-align:right;" title="Amount" sortable="false">
        <fmt:formatNumber value="${stmtHAS.amount}" minFractionDigits="2" maxFractionDigits="2"/>
    </display:column>
    
    <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFoundHAS} {1} found</span>' />
    <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFoundHAS} {1} found, {5} pages total</span>' />
</display:table>
</c:if>


<div id="tertiary-navigation-and-or-page-controls">

				<jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
				<ul id="page-controls">
                                        <li><a href="#0" class="pdf" title="PDF report" onclick="javascipt:downloadPDF();">download into PDF</a></li>
                                         <li><a href="#1" class="excel" title="Excel report" onclick="javascipt:downloadExcel();">download into Excel</a></li>
                                        <li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
                                    

					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
                                       <%-- 
					<c:if test="${preview}">
                                            <li><a href="#" class="print" onclick="javascript: window.print();">print</a></li>
                                        </c:if>
					<li><a href="#" class="print-preview" onclick="javascript:printPreview();">
                                            <c:choose>
                                                <c:when test="${preview}">
                                                    turn off print preview
                                                </c:when>
                                                <c:otherwise>
                                                    print preview
                                                </c:otherwise>
                                            </c:choose>
                                        </a></li>
                                        --%>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

		

	</div> <!-- end of content -->
        </form>
	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
    function printPreview()
    {
        var actionstring = "${pageContext.request.contextPath}/accountStatements.do";
        <c:if test="${not preview}">
            actionstring = actionstring + "?preview=Y";
        </c:if>
        document.StatementsForm.action = actionstring;
        document.StatementsForm.submit();
        
    }
    
    function downloadPDF()
    {      
            var actionstring = "${pageContext.request.contextPath}/accountStatements.do";           
               <c:if test="${not pdf}">
                actionstring = actionstring + "?pdf=Y"; 
                </c:if>
                document.StatementsForm.action = actionstring;
               // alert("actionstring :"+actionstring);
                
                var acrobat=new Object();
                
                acrobat.installed=false;
                acrobat.version='0.0';
                
                if (navigator.plugins && navigator.plugins.length)
                {
                for ( var x = 0, l = navigator.plugins.length; x < l; ++x )
                {
                if (navigator.plugins[x].description.indexOf('Adobe Acrobat') != -1)
                {
                acrobat.version=parseFloat(navigator.plugins[x].description.split('Version ')[1]);
                
                if (acrobat.version.toString().length == 1) acrobat.version+='.0';
                
                acrobat.installed=true;
                break;
                }
                }
                }
                else if (window.ActiveXObject)
                {
                for (x=6; x<10; x++)
                {
                try
                {
                oAcro=eval("new ActiveXObject('PDF.PdfCtrl."+x+"');");
                if (oAcro)
                {
                acrobat.installed=true;
                acrobat.version=x+'.0';
                }
                }
                catch(e) {}
                }
                
                try
                {
                oAcro4=new ActiveXObject('PDF.PdfCtrl.1');
                if (oAcro4)
                {
                acrobat.installed=true;
                acrobat.version='4.0';
                }
                }
                catch(e) {}
                
                try
                {
                oAcro9=new ActiveXObject('AcroPDF.PDF.1');
                if (oAcro9)
                {
                acrobat.installed=true;
                acrobat.version='9.0';
                }
                }
                catch(e) {}
                
                }
                 if (navigator.plugins != null && navigator.plugins.length > 0) {
                  for (i=0; i < navigator.plugins.length; i++ ) {
                     var plugin = navigator.plugins[i];        
                     if (plugin.name.indexOf("Adobe Acrobat") != -1) {
                                       acrobat.installed=true;
                                        break;
                     }
                  }
                } 
                 // if(!acrobat.installed)
                //  {
                //   window.open('http://www.adobe.com/products/acrobat/readstep2.html');
                //  }
                //  else
                 //   {
                    document.StatementsForm.submit();
                    
                 //   }
           
    }
    
function downloadExcel()
{     
             var actionstring = "${pageContext.request.contextPath}/accountStatements.do";   
               
            <c:if test="${not excel }">
                actionstring = actionstring + "?excel=Y";
            </c:if>                                       
                document.StatementsForm.action = actionstring;               
                document.StatementsForm.submit();            
}
</script>
