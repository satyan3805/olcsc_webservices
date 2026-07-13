<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

	<form id="data-actions" name="summaryForm" action="${pageContext.request.contextPath}/accountYearlySummary.do" method="post">

		<div id="content">

			<h1 id="account-activity-yearly-summary">Account Activity &ndash; Yearly Summary</h1>

			<logic:messagesPresent message="true" property="viewYearlySummaryErrors">
                            <div class="section">
                            <dl class="errors"/>
                            <html:messages id="msg" message="true" property="viewYearlySummaryErrors">
                                <dd><bean:write name="msg"/></dd>
                            </html:messages>
                            </div>
                        </logic:messagesPresent>
                        
                        <div class="section">
                                <c:if test="${not empty summaryYears}">
                                    <fieldset>
                                            <dl>
                                                    <dt><label for="summary-year">Show<span class="accessibility"> summary year</span></label></dt>
                                                    <dd>
                                                            <select id="summary-year" name="summaryYear">
                                                                <c:forEach var="year" items="${summaryYears}">
                                                                    <option value="${year.value}"
                                                                        <c:if test="${year.value == summaryYear}">
                                                                            selected
                                                                        </c:if>
                                                                    >${year.label}</option>
                                                                </c:forEach>
                                                                    <!--
                                                                    <option value="00">- year -</option>
                                                                    <option value="2000">2000</option>
                                                                    <option value="2001">2001</option>
                                                                    <option value="2002">2002</option>
                                                                    <option value="2003">2003</option>
                                                                    <option value="2004">2004</option>
                                                                    <option value="2005" selected="selected">2005</option>
                                                                    -->
                                                            </select>
    
                                                            <input type="submit" value="Display" onclick="javascript: document.summaryForm.action='${pageContext.request.contextPath}/accountYearlySummary.do';showLoading();" />
                                                    </dd>
                                            </dl>
                                    </fieldset>
                                </c:if>
                                
                                <c:if test="${not empty accountSummary.tagSummary}">   
                                    <br><br>
                                    <!-- begin Tag Summary -->
                                        <h2 id="ez-tag-summary">EZ TAG Summary for ${summaryYear}</h2>
                                        <display:table name="accountSummary.tagSummary" cellspacing="0" class="data-table"
                                                pagesize="0" requestURI="${pageContext.request.contextPath}/accountYearlySummary.do" id="tagRecord"
                                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator">
                                            <display:column property="tagId" class="${cellStyle}" title="Tag ID" sortable="false"/>
                                            <display:column property="description" class="${cellStyle}" title="Description" sortable="false"/>
                                            <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="false"/>
                                            <display:column class="currency" headerClass="currency-header" title="Amount" sortable="false">
                                             <fmt:formatNumber value="${tagRecord.amount}" minFractionDigits="2" maxFractionDigits="2"/> 
                                            </display:column>
                                        </display:table>
                                </c:if>
                                
                                <c:if test="${not empty accountSummary.acctSummary}"> 
                                    <br><br>
                                    <h2 id="account-summary">Account Summary ${summaryYear}</h2>
                                    <display:table name="accountSummary.acctSummary" cellspacing="0" class="data-table"
                                        pagesize="0" requestURI="${pageContext.request.contextPath}/accountYearlySummary.do" id="record"
                                        decorator="com.etcc.csc.decorator.RowStyleTableDecorator">
                                        <display:column property="description"  title="Description" sortable="false"/>
                                        <display:column property="quantity" class="quantity" headerClass="quantity-header" title="Quantity" sortable="false"/>
                                        <display:column class="currency" headerClass="currency-header" title="Amount" sortable="false">
                                            <fmt:formatNumber value="${record.amount}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </display:column>
                                    </display:table>
                                </c:if>

			</div> <!-- end of div.section  -->


			<div id="tertiary-navigation-and-or-page-controls">

                                <jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
				<ul id="page-controls">

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
                                         <li><a href="#0" class="pdf" title="PDF report" onclick="javascipt:downloadPDF();">download into PDF</a></li>
                                         <li><a href="#1" class="excel" title="Excel report" onclick="javascipt:downloadExcel();">download into Excel</a></li>
                                        <li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

		</form>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
    function printPreview()
    {
        var actionstring = "${pageContext.request.contextPath}/accountYearlySummary.do";
        <c:if test="${not preview}">
            actionstring = actionstring + "?preview=Y";
        </c:if>
        document.summaryForm.action = actionstring;
        document.summaryForm.submit();
    }
    
function downloadPDF()
    {      
            var actionstring = "${pageContext.request.contextPath}/accountYearlySummary.do";           
               <c:if test="${not pdf}">
                actionstring = actionstring + "?pdf=Y"; 
                </c:if>
                document.summaryForm.action = actionstring;
                //alert("actionstring :"+actionstring);
                
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
                             if (acrobat.version.toString().length == 1) 
                                acrobat.version+='.0';
                
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
                //    {
                    document.summaryForm.submit();
                    
                //    }
           
    }
    
function downloadExcel()
    {      
            var actionstring = "${pageContext.request.contextPath}/accountYearlySummary.do";           
               <c:if test="${not excel}">
                actionstring = actionstring + "?excel=Y"; 
                </c:if>
                document.summaryForm.action = actionstring;               
                document.summaryForm.submit();     
           
    }
</script>