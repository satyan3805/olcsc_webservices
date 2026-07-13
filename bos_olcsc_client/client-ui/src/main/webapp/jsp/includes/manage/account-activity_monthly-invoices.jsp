<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

        	<div id="content">
                    <form id="data-actions" action="${pageContext.request.contextPath}/accountMonthlyInvoices.do" method="post">

			<h1 id="account-activity-monthly-invoices">Account Activity &ndash; monthly invoices</h1>

			<div class="section">
			  <logic:messagesPresent message="true" property="viewMonthlyInvoiceErrors">
                            <div class="section">
                            <dl class="errors"/>
                            <html:messages id="msg" message="true" property="viewMonthlyInvoiceErrors">
                                <dd><bean:write name="msg"/></dd>
                            </html:messages>
                            </div>
                          </logic:messagesPresent>
			</div>

			<fieldset>
				<dl>
					<dt><label for="statement-month-year">Show<span class="accessibility"> statement month and year</span></label></dt>
						<dd>
							<select id="statement-month-year" name="statementMonth">
                                                            <option value="00">- month -</option>
                                                            <c:forEach var="dateRec" items="${monthList}">
                                                                <option value="${dateRec.value}"
                                                                <c:if test="${monthlyInvoices.statementMonth == dateRec.value}">
                                                                    selected
                                                                </c:if>
                                                                >${dateRec.label}</option>
                                                            </c:forEach>
							</select>
							<input type="submit" value="Display" onclick="javascript:document.forms[0].action='${pageContext.request.contextPath}/accountMonthlyInvoices.do';"/>
						</dd>
				</dl>
			</fieldset>
			
<c:if test="${(not empty monthlyInvoices.invoiceDetails) and (not empty monthlyInvoices.invoiceSummary)}">		

			<div class="section with-primary-and-secondary-content">

		<!-- 		<div class="primary-section-content">

					<img id="logo" src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="HCTRA" />

				</div> end of primary-section-content -->

				<div class="secondary-section-content">

					<dl class="page-detail">
						<dt>Monthly EZ TAG Invoice</dt>
							<dd>Invoice # ${monthlyInvoices.invoiceNumber}</dd>
							<dd>Invoice Period - ${monthlyInvoices.stmtPeriod}</dd>
						<!-- <dd><fmt:formatDate value="${monthlyInvoices.invoiceDate.time}" type="both" /></dd> -->	
						
						<c:if test="${monthlyInvoices.dueMonth!=null}">
						
						 <dd>${monthlyInvoices.dueMonth} $<fmt:formatNumber value="${monthlyInvoices.dueAmount}" minFractionDigits="2" maxFractionDigits="2"/></dd>
						
						</c:if> 
						 										
					</dl>

				</div> <!-- end of secondary-section-content -->

			</div> <!-- end of div.section.with-primary-and-secondary-content -->
			<div class="section with-primary-and-secondary-content">

				<div class="primary-section-content">

					<!-- Contact info using the hcard microformat
							http://microformats.org/wiki/hcard -->				
					<div class="vcard">
						<p class="fn org"><strong>Harris County Toll Road Authority</strong></p>
						<div class="adr">
                                                    <bean:message key="HCTRA.address" />
						</div>
					</div> <!-- end of div.vcard -->

				</div> <!-- end of primary-section-content -->

				<div class="secondary-section-content">

					<div class="vcard">
						<p class="n">
							<strong><span class="given-name">${monthlyInvoices.firstName}</span>
							<span class="family-name">${monthlyInvoices.lastName}</span></strong>
						</p>
						<p>Account Number ${monthlyInvoices.acctId}</p>
<%--
						<!--    <div class="adr">   -->
							<p>${acctInfo.addressDisplay}</p>
                                                        <!--
                                                        <p class="street-address">${monthlyInvoices.address1}</p>
							<p>
								<span class="locality">${monthlyInvoices.city}</span>, 
								<span class="region">${monthlyInvoices.state}</span> 
								<span class="postal-code">${monthlyInvoices.zip}</span>
							</p>
                                                        -->
						<!--    </div>  -->
--%>
<etcc-extended:format address="${acctInfo}" displayName="false" />
                                                <p>${acctInfo.homePhoNbr}</p>
					</div> <!-- end of div.vcard -->

				</div> <!-- end of secondary-section-content -->

			</div> <!-- end of div.section.with-primary-and-secondary-content -->
		    <h2>Invoice summary</h2>
			<div class="section">
                                <c:choose>
                                  <c:when test="${monthlyInvoices.invoiceSummary!=null}">
                                    <!--    <h2>Invoice summary</h2>    -->
                                    <!-- begin invoice details -->
                                    <display:table name="monthlyInvoices.invoiceSummary" cellspacing="0" sort="list" class="data-table transactions"
                                                pagesize="0" requestURI="${pageContext.request.contextPath}/accountMonthlyInvoices.do" id="invsumrec"
                                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="invsum">
                                        <display:column title="Description" property="description" sortable="false" />
                                        <display:column title="Quantity" property="quantity" sortable="false"/>
                                        <display:column class="currency" style="text-align:right;" title="Amount" sortable="false">
            									<fmt:formatNumber value="${invsum.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            							</display:column>
                                        <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
                                        <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
                                        <display:footer>
                                            <tr>
                                                <td/>
                                                <th>Ending balance</th>
                                                <td class="currency">$<fmt:formatNumber value="${monthlyInvoices.invSumEndingBalance}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                            </tr>
                                        </display:footer>
                                    </display:table>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${itemsFound == '0'}">
                                            <h2>No invoice summary found</h2>  
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>    

				<table class="data-table">
					<thead>
						<tr>
							<th scope="col">Balance Summary</th>
							<th scope="col" class="currency-header">Amount</th>
						</tr>
					</thead>

					<tbody>
						<tr>
							<th scope="row">Balance requirement</th>
							<td class="currency">$<fmt:formatNumber value="${monthlyInvoices.balSumBalanceRequirement}" minFractionDigits="2" maxFractionDigits="2"/> <!-- (3 months&rsquo; usage) --></td>
						</tr>
						<tr>
							<th scope="row">Current balance</th>
							<td class="currency">$<fmt:formatNumber value="${monthlyInvoices.balSumCurrentBalance}" minFractionDigits="2" maxFractionDigits="2"/> <!-- (as of 12/02/2004) --></td>
						</tr>
						
						<tr>
							<th scope="row">Replenishment amount</th>
							<td class="currency">$<fmt:formatNumber value="${monthlyInvoices.balSumReplenishmentAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
						</tr>
					</tbody>
				</table>

                        <c:choose>
                          <c:when test="${monthlyInvoices.outstandingInvoices!=null}">
                            <!-- begin statement for current month -->
                            <display:table name="monthlyInvoices.outstandingInvoices" cellspacing="0" sort="list" class="data-table transactions"
                                        pagesize="0" requestURI="${pageContext.request.contextPath}/accountMonthlyInvoices.do" id="outstandinvrec"
                                        decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="outstandinv">
                                <display:column title="Outstanding invoices" property="description" sortable="false" />
                                <display:column class="currency" style="text-align:right;" title="Amount" sortable="false">
            									<fmt:formatNumber value="${outstandinv.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            					</display:column>
                                <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
                                <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
                                                                                           
                            </display:table>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${itemsFound == '0'}">
                                    <h2>No outstanding invoices found</h2>  
                                </c:if>
                            </c:otherwise>
                        </c:choose>    

			
<table class="data-table">
<tbody>
						<tr>
							<th scope="row">TOTAL OUTSTANDING AMOUNT</th>
							<td class="currency">$<fmt:formatNumber value="${monthlyInvoices.totalAmtDue}" minFractionDigits="2" maxFractionDigits="2"/> <!-- (3 months&rsquo; usage) --></td>
						</tr>
</tbody>
</table>

</div> <!-- end of div.section -->
						
<!--  <table>

<tr>
      <th>TOTAL OUTSTANDING AMOUNT</th>
      <td class="currency">$<fmt:formatNumber value="${monthlyInvoices.totalAmtDue}" minFractionDigits="2" maxFractionDigits="2"/></td>
      </tr>	
                                    
</table>		

-->
			
<b>If Amount Due is less than Replenishment Amount, please pay Replenishment Amount.</b> 

<c:if test="${monthlyInvoices.suspensionDate!=null}">
<c:forEach items="${monthlyInvoices.suspensionDate}" var="suspension" varStatus="varStatus">
<br> <b> ${suspension.description} </b>
</c:forEach>
  
  </c:if>
						
			<!-- Invoice details - start -->
	
			
<h2>Invoice details</h2>     
<c:choose>   
 <c:when test="${monthlyInvoices.invoiceDetails!=null}">
   <table cellspacing = "0" class="data-table transactions">
		 <thead>
    <tr>
      <th id="txn-date-header">Transaction Date/Time</th>
      <th id="post-date-header">Posted Date/Time</th>
<%--      <!-- This column is for calculating subtotals using JavaScript. -->
      <th style="width:0;" width="0"></th>--%>
      <th id="location-header" >Location</th>
      <th id="tagid-header" >Vehicle</th>
      <th id="vehicle-class-code-header" >Axle Count</th>
            <th id="description-header" >Type</th>
      <th id="amount-header" class="currency-header">Amount</th>
    </tr>
  </thead>

<c:forEach items="${monthlyInvoices.invoiceDetails}" var="statement" varStatus="varStatus">
  <c:choose>
  <c:when test="${statement.vehicleNickName!= 'NONE'}">
  <tr>  
  <td>
    <fmt:formatDate value="${statement.trxnDate.time}" type="both" dateStyle="SHORT" />    
  </td>
  <td>
    <fmt:formatDate value="${statement.postedDate.time}" type="both" dateStyle="SHORT" />    
  </td>
  <td>${statement.locationName}</td>
  <td>${statement.vehicleNickName}</td>
  <td>${statement.vehicleClassCode}</td>
  <td>${statement.transType}</td>
  <td>
  <fmt:formatNumber value="${statement.amount}" minFractionDigits="2" maxFractionDigits="2"/>
  </td>
 </tr> 

 </c:when>
 <c:otherwise>
   <tr>
   <td colspan = "7">
     <b>${statement.locationName}</b>
   </td>   
  </tr>

  </c:otherwise>
  </c:choose>
  </c:forEach>
  </table>
  
   </c:when>
   <c:otherwise>
   <h2>No invoice details found</h2>  
   </c:otherwise>
   </c:choose>    
  
  
  </c:if>
  

   
 <!-- Invoice details - end -->                      
                               
                                
			<div class="section">

				<!-- Contact info using the hcard microformat
						http://microformats.org/wiki/hcard -->
				<div class="vcard">
					<p class="org fn accessibility">Harris County Toll Road Authority</p>
					<div class="tel">
						<p>Contact <span class="type">Customer Service</span>:</p>
						<p><span class="value"><bean:message key="HCTRA.telephone.number" /></span> <span class="hours"><bean:message key="HCTRA.custservice.hours" /></span></p>
					</div>
					<p><a class="url" href="http://www.hctra.org" title="Harris County Toll Road Authority website">www.hctra.org</a></p>
				</div> <!-- end of div.vcard -->

			</div> <!-- end of div.section  -->

			<div id="tertiary-navigation-and-or-page-controls">

				<jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>

				<ul id="page-controls">
					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
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
    
function downloadPDF()
    {      
            var actionstring = "${pageContext.request.contextPath}/accountMonthlyInvoices.do";           
               <c:if test="${not pdf}">
                actionstring = actionstring + "?pdf=Y&p_invoice_id="+ "${monthlyInvoices.invoiceNumber}"; 
                </c:if>
                document.forms[0].action = actionstring;
             //   alert("actionstring :"+actionstring);
                
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
                //  if(!acrobat.installed)
                //  {
                //   window.open('http://www.adobe.com/products/acrobat/readstep2.html');
               //   }
               //   else
                //    {
                    document.forms[0].submit();
                    
                //    }
           
    }

    function downloadExcel()
    {      
               var actionstring = "${pageContext.request.contextPath}/accountMonthlyInvoices.do";           
               <c:if test="${not excel}">
                actionstring = actionstring + "?excel=Y&p_invoice_id="+ "${monthlyInvoices.invoiceNumber}"; 
                </c:if>
                document.forms[0].action = actionstring;                
                document.forms[0].submit();
           
    }
</script>
