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

			<div class="section with-primary-and-secondary-content">

				<div class="primary-section-content">

					<img id="logo" src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="HCTRA" />

				</div> <!-- end of primary-section-content -->

				<div class="secondary-section-content">

				</div> <!-- end of secondary-section-content -->

			</div> <!-- end of div.section.with-primary-and-secondary-content -->
			<div class="section with-primary-and-secondary-content">

				<div class="primary-section-content">

					<!-- Contact info using the hcard microformat
							http://microformats.org/wiki/hcard -->				
					<div class="vcard">
                                                <br />  <br />  
						<p class="fn org"><strong>Harris County Toll Road Authority</strong></p>
						<div class="adr">
                                                    <bean:message key="HCTRA.address" /></span>
						</div>
					</div> <!-- end of div.vcard -->

				</div> <!-- end of primary-section-content -->

				<div class="secondary-section-content">
				</div> <!-- end of secondary-section-content -->

			</div> <!-- end of div.section.with-primary-and-secondary-content -->

			<div class="section with-content">

				<div class="content">
    
                                    <p><strong>No monthly invoice found</strong></p>

				</div> 

			</div> 

			<div class="section">

				<!-- Contact info using the hcard microformat
						http://microformats.org/wiki/hcard -->
				<div class="vcard">
                                        <br />  
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
					<li><a style="display:none" href="#" class="pdf">download PDF</a></li>

					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
					<li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

                    </form>
		</div> <!-- end of content -->

		<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
