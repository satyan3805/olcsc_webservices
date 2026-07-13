<%@ include file="/jsp/common/Taglibs.jsp" %>

<div id="content-container">
    <form id="data-actions" action="${pageContext.request.contextPath}/accountReceiptsResult.do" method="post">
	<div id="content">

		<input type="hidden" name="startDate" value="${startDate}">
                <input type="hidden" name="endDate" value="${endDate}">
                <input type="hidden" name="transactionID" value="${transactionID}">
                <input type="hidden" name="HASFlag" value="${HASFlag}">

          <div class="section">
            <logic:messagesPresent message="true" property="viewReceiptDetailErrors">
              <div class="section">
                <dl class="errors"/>
                <html:messages id="msg" message="true" property="viewReceiptDetailErrors">
                  <dd><bean:write name="msg"/></dd>
                </html:messages>
              </div>
            </logic:messagesPresent>
          </div>

                <div class="section" <c:if test="${accountReceiptDetails.transactionType!='ACCOUNT'}"> style="display:none" </c:if>>
                    <h1 id="account-activity-receipts">Account Activity &ndash; Receipts</h1>

                    <p class="return-to"><a href="javascript:returnToReceiptResult()">Return to receipt list</a></p>
                </div>

                <div class="section with-primary-and-secondary-content" <c:if test="${accountReceiptDetails.transactionType!='ACCOUNT'}"> style="display:none" </c:if>>

			<div class="primary-section-content">

				<img id="logo" src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="HCTRA" />

			</div> <!-- end of primary-section-content -->

			<div class="secondary-section-content">

				<dl class="page-detail">
					<dt>Receipt</dt>
						<dd>Transaction # ${accountReceiptDetails.transactionID}</dd>
                        <dd><fmt:formatDate value="${accountReceiptDetails.transactionDate}" type="both" /></dd>
						<dd>CSR ID: ${accountReceiptDetails.csrID}</dd>
				</dl>

			</div> <!-- end of secondary-section-content -->

		</div> <!-- end of div.section.with-primary-and-secondary-content -->
		<div class="section with-primary-and-secondary-content" <c:if test="${accountReceiptDetails.transactionType!='ACCOUNT'}"> style="display:none" </c:if>>

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

				<!-- Contact info using the hcard microformat
						http://microformats.org/wiki/hcard -->
				<div class="vcard">
					<p class="n">
						<!--    <strong><span class="given-name">Dwane</span>
						<span class="additional-name">A</span>.
						<span class="family-name">Wanek</span></strong> -->
                                                <strong>${acctInfo.name}</strong>
					</p>
                                        <p>${acctInfo.companyName}</p>
					<p>Account Number: ${acctInfo.acctId}</p>
<%--
					<!--    <div class="adr">
						<p class="street-address">7503 Hunters Point Drive</p>
						<p>
							<span class="locality">Sugarland</span>,
							<span class="region">TX</span>
							<span class="postal-code">77479</span>
						</p>
					</div>  -->
                                        <p>${acctInfo.addressDisplay}</p>
--%>
<etcc-extended:format address="${acctInfo}" displayName="false" />
                                        <p>${acctInfo.homePhoNbr}</p>
				</div> <!-- end of div.vcard -->

			</div> <!-- end of secondary-section-content -->

		</div> <!-- end of div.section.with-primary-and-secondary-content -->

		<div class="section with-primary-and-secondary-content" <c:if test="${accountReceiptDetails.transactionType!='ACCOUNT'}"> style="display:none" </c:if>>

			<div class="primary-section-content">
                                <c:choose>
                                  <c:when test="${accountReceiptDetails.accountTransactions!=null}">
                                    <h2>Account Transactions</h2>
                                    <display:table name="accountReceiptDetails.accountTransactions" cellspacing="0" sort="list" class="data-table transactions"
                                                pagesize="0" id="accttrans"
                                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="accttrans">
                                        <display:column title="Description" property="description" sortable="false" />
                                        <display:column class="currency" headerClass="currency-header" title="Amount" sortable="false">
                                            <fmt:formatNumber value="${accttrans.amt}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </display:column>
                                        <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
                                        <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
                                    </display:table>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${itemsFound == '0'}">
                                            <h2>No account transactions found</h2>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>


                                <c:choose>
                                  <c:when test="${accountReceiptDetails.pmtDetails!=null}">
                                    <h2>Payment Detail</h2>
                                    <display:table name="accountReceiptDetails.pmtDetails" cellspacing="0" sort="list" class="data-table transactions"
                                                pagesize="0" id="pmtdetail"
                                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="pmtdetail">
                                        <display:column title="Description" property="description" sortable="false" />
                                        <display:column class="currency" headerClass="currency-header" title="Amount" sortable="false">
                                            <fmt:formatNumber value="${pmtdetail.amt}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </display:column>
                                        <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
                                        <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
                                    </display:table>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${itemsFound == '0'}">
                                            <h2>No payment details found</h2>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>

			</div> <!-- end of primary-section-content -->

			<div class="secondary-section-content">

				<!-- The deposit transactions are deprecated and is only here for older accounts
					new accounts won't have this information | 11/15/2006 - JA -->

                                <c:choose>
                                  <c:when test="${accountReceiptDetails.depositTransactions!=null}">
                                    <h2>Deposit Transactions</h2>
                                    <!-- begin invoice details -->
                                    <display:table name="accountReceiptDetails.depositTransactions" cellspacing="0" sort="list" class="data-table transactions"
                                                pagesize="0" id="recdetail"
                                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="recdetail">
                                        <display:column title="Description" property="description" sortable="false" />
                                        <display:column class="currency" headerClass="currency-header" title="Amount" sortable="false">
                                            <fmt:formatNumber value="${recdetail.amt}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </display:column>
                                        <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
                                        <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
                                    </display:table>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${itemsFound == '0'}">
                                            <h2>No deposit transactions found</h2>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>

			</div> <!-- end of secondary-section-content -->

		</div> <!-- end of div.section.with-primary-and-secondary-content -->

                <div class="section" <c:if test="${accountReceiptDetails.transactionType!='PARKING'}"> style="display:none" </c:if>>
                    <h1 id="account-activity-receipts">HAS Parking Receipt</h1>

                    <p class="return-to"><a href="javascript:returnToReceiptResult()">Return to receipt list</a></p>
                </div>

                <div class="section" <c:if test="${accountReceiptDetails.transactionType!='PARKING'}"> style="display:none" </c:if>>

				<img  src="${pageContext.request.contextPath}/meta/media/common/HAS.jpg" alt="HAS" />

		</div>


                <div class="section" <c:if test="${accountReceiptDetails.transactionType!='PARKING'}"> style="display:none" </c:if>>
                    <br />  <br />   <br />
                    <table class="data-table transactions" cellspacing="0" id="parkingdetail">
                    <tbody>
                    <tr class="transaction-type-toll">
                    <td>Entry</td>
                    <td><fmt:formatDate value="${accountReceiptDetails.parkTransaction.entryDate}" type="both" dateStyle="SHORT" /></td>
                    <td>Lane</td>
                    <td>${accountReceiptDetails.parkTransaction.entryLane}</td></tr>
                    <tr class="transaction-type-toll">
                    <td>Exit</td>
                    <td><fmt:formatDate value="${accountReceiptDetails.parkTransaction.exitDate}" type="both" dateStyle="SHORT" /></td>
                    <td>Lane</td>
                    <td>${accountReceiptDetails.parkTransaction.exitLane}</td></tr>
                    <tr class="transaction-type-toll">
                    <td>EZ TAG Number</td>
                    <td>${accountReceiptDetails.parkTransaction.ezTagNum}</td>
                    <td></td>
                    <td></td></tr>
                    <tr class="transaction-type-toll">
                    <td>Parking Fee</td>
                    <td>$<fmt:formatNumber value="${accountReceiptDetails.parkTransaction.parkingFee}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td></td>
                    <td></td></tr>
                    <tr class="transaction-type-toll">
                    <td>License plate</td>
                    <td>${accountReceiptDetails.parkTransaction.licPlate}</td>
                    <td>License state</td>
                    <td>${accountReceiptDetails.parkTransaction.licState}</td></tr>
                    </tbody></table>
                </div>


                <div class="section" <c:if test="${accountReceiptDetails.transactionType!='PARKING'}"> style="display:none" </c:if>>
                    <br /> <br />
                    <p>Thank you for parking at the Houston Airport System</p>
                    <!--    <input type="submit" name="Print receipt" />                -->
                </div>

		<div class="section">

			<!-- Contact info using the hcard microformat
					http://microformats.org/wiki/hcard -->
			<div class="vcard">
				<p class="org fn accessibility">Harris County Toll Road Authority</p>
				<div class="tel">
					<p>Contact <span class="type">Customer Service</span>:</p>
					<p><span class="value">281-875-EASY (3279) during </span> <span class="hours"><a href="${appDelegate.domainName}/about_locations/">normal business hours</a>.</span></p>

				</div>
				<p><a class="url" href="http://www.hctra.org" title="Harris County Toll Road Authority website">www.hctra.org</a></p>
			</div> <!-- end of div.vcard -->

		</div> <!-- end of div.section  -->

		<div id="tertiary-navigation-and-or-page-controls">

			<!--#include virtual="/includes/manage/tertiary-navigation-manage-account-activity.shtml" -->
		    <jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
			<ul id="page-controls">
				       <li><a href="#0" class="pdf" title="PDF report" onclick="javascipt:downloadPDF();">download into PDF</a></li>
                                       <li><a href="#1" class="excel" title="Excel report" onclick="javascipt:downloadExcel();">download into Excel</a></li>

				<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
					<li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
			</ul>

		</div> <!-- end of tertiary-navigation-and-or-page-controls -->

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>
    </form>

</div> <!-- end of content-container -->

<script type="text/javascript">

function returnToReceiptResult()
{
    document.forms[0].action = "${pageContext.request.contextPath}/accountReceiptsResult.do";
    document.forms[0].submit();
}

function downloadPDF()
    {
            var actionstring = "${pageContext.request.contextPath}/accountReceiptsDetail.do";
               <c:if test="${not pdf}">
                actionstring = actionstring + "?pdf=Y";
                </c:if>
                document.forms[0].action = actionstring;
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
                //  if(!acrobat.installed)
               //   {
                //   window.open('http://www.adobe.com/products/acrobat/readstep2.html');
                //  }
               //   else
               //     {
                    document.forms[0].submit();

               //     }

    }

    function downloadExcel()
    {
            var actionstring = "${pageContext.request.contextPath}/accountReceiptsDetail.do";
               <c:if test="${not excel}">
                actionstring = actionstring + "?excel=Y";
                </c:if>
                document.forms[0].action = actionstring;
                document.forms[0].submit();

    }
</script>
