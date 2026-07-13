<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

	<div id="content">

		<h1 id="account-summary">Account Summary</h1>
		
		<div class="section with-primary-and-secondary-content">
                        <div class="section">
                            <logic:messagesPresent message="true">
                                <dl class="alerts"/>
                                <dt/>
                                <html:messages id="msg" message="true" property="alerts">
                                    <dd>${msg}</dd>
                                </html:messages>
                            </logic:messagesPresent>                           
                        </div>

			<div class="primary-section-content">
	
				<h2>Account Activity</h2>			
				
	
				<!-- This is a placeholder image for what will be a dynamically generated graph by the backend. -->
				<!--    <img src="${pageContext.request.contextPath}/meta/media/development/placeholder-account-summary-activity.png" alt="Graph: tolls paid over the past year" width="325" height="160" />    -->
                                <img src="${pageContext.request.contextPath}/servlet/DisplayChart?filename=${fileName}" width=350 height=200 border=0 align="center" usemap="#${fileName}"/>
                                
                                <ul>
					<c:if test="${not empty daily}">
                                            <li>Average <em>daily</em> usage: <strong>$<fmt:formatNumber value="${daily}" minFractionDigits="2" maxFractionDigits="2"/></strong></li>
                                        </c:if>
                                        <c:if test="${not empty monthly}">
                                            <li>Average <em>monthly</em> usage: <strong>$<fmt:formatNumber value="${monthly}" minFractionDigits="2" maxFractionDigits="2"/></strong></li>
                                        </c:if>
				</ul>
	
			</div> <!-- end of primary-section-content -->
			
			<div class="secondary-section-content">
				<c:if test="${acctInfo.revenueAccount}" >
				<h2>Account Status</h2>
				<b>Please Note: Transactions may take several business days to post to your EZ TAG Account.</b>
				<p>
					<!-- This is a placeholder image for what will be a dynamically generated graph by the backend. -->
                                     <!--   <Applet code="AccountStatusApplet.class" width="55" height="230" ></Applet>-->
                                        
                                       <c:if test="${type == 1 || type == 3}">
                                           <div style="border-left: 1px solid gray;border-right: 1px solid gray;width:40px;float:left;display: inline;">
                                                <div style="height:80px;">&nbsp;</div>
                                                <c:if test="${type == 1}">
                                                    <div style="background-color:rgb(98, 126, 155);height:${height2}px;font-size:75%;color:white;vertical-align: top;text-align:center">$<fmt:formatNumber value="${acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></div>
                                                </c:if>
                                                <div style="background-color:rgb(98, 126, 155);height:${height1}px; border-bottom: 1px solid gray; border-top: dashed red;color:white;vertical-align: top;text-align:center;font-size:75%;">$<fmt:formatNumber value="${acctInfo.lowBalLevel}" minFractionDigits="2" maxFractionDigits="2"/></div>
                                            </div>
                                        </c:if>
                                        
                                        <c:if test="${type == 2}">
                                            <div style="border-left: 1px solid gray;border-right: 1px solid gray;width:40px;float:left;display: inline;">
                                                <div style="height:80px;">&nbsp;</div>
                                                <div style="width:40px; height:${height2}px; border-top:dashed rgb(98, 126, 155);color:black;vertical-align:top;text-align:center;font-size:60%;">$<fmt:formatNumber value="${acctInfo.lowBalLevel}" minFractionDigits="2" maxFractionDigits="2"/></div>
                                                <div style="background-color:red;height:${height1}px;font-size:60%;color:white;vertical-align: top;text-align:center; border-bottom: 1px solid black;">$<fmt:formatNumber value="${acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/></div>
                                            </div>
                                        </c:if>
                                     
                                        <div style="width:82%;float:right;display: inline;">
					As of ${currentDate}.<br/>
                                        
					Your current remaining balance is:<br/>
					<strong>$<fmt:formatNumber value="${acctInfo.balanceAmt}" minFractionDigits="2" maxFractionDigits="2"/><br/><br/></strong>
                                        
					<c:if test="${not empty acctType}">
                                        Your ${acctType} ending in ${acctNumber} will be billed:<br/>
					<strong>$<fmt:formatNumber value="${acctInfo.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/><br/><br/></strong>
	
					when your remaining balance reaches<br/>
					<strong>$<fmt:formatNumber value="${acctInfo.lowBalLevel}" minFractionDigits="2" maxFractionDigits="2"/><br/><br/></strong>
                                        </c:if>
	
					<span><c:if test="${not empty rebillDate}">Your account was last billed on ${rebillDate}.</c:if> Would you like to increase your <a href="${pageContext.request.contextPath}/accountInformation/dispChangeRebillAmt.do">EZ TAG Account minimum balance</a>?</span><br><br>
                                        <span><a href="${appDelegate.domainName}/about_faq/billing#how-is-my-minimum-account-balance-determined">How is the minimum account balance determined</a>?</span>
                                        </div>
				</p>
			</c:if>
			</div> <!-- end of secondary-section-content -->
			

			
		</div> <!-- end of section with-primary-and-secondary-content -->
                
		<h2 id="you-can">You can &hellip;</h2>
		<ul class="account-summary-actions">
			<li class="change-info"><a href="${pageContext.request.contextPath}/viewTransactionsMenuAdjustment.do?sysParamName=OLCSC_ACCOUNT_ACTIVITY_MENU_LABEL">View transactions</a></li>
			<li class="change-info"><a href="${pageContext.request.contextPath}/viewStatementsMenuAdjustment.do?sysParamName=OLCSC_ACCOUNT_ACTIVITY_MENU_LABEL">View monthly statements</a></li>
                        <li class="print"><a href="${pageContext.request.contextPath}/viewReceiptsMenuAdjustment.do?sysParamName=OLCSC_ACCOUNT_ACTIVITY_MENU_LABEL">Print a receipt</a></li>

			
			<c:if test="${acctInfo.revenueAccount and acctLoginInfo.acctActivity ne 'I' }" >
			<li class="add-ez-tag">
			<% session.setAttribute("whichVehPage" , null); %>
			<a href="${pageContext.request.contextPath}/accountVehicleAddDisplay.do?whichVehPage=tag">Get another EZ TAG (for a new vehicle)</a>
			
			<!--<a href="${pageContext.request.contextPath}/accountVehicleAddDisplay.do?activePbpTagExists=true">Get another EZ TAG (for a new vehicle)</a>-->
			</li>
			
			</c:if>
			<c:if test="${acctInfo.revenueAccount and acctLoginInfo.acctActivity ne 'I'}" >
			<li class="make-payment"><a href="${pageContext.request.contextPath}/makePaymentMenuAdjustment.do?sysParamName=OLCSC_MAKE_PAYMENT_MENU_LABEL">Make a payment</a></li></c:if>
		</ul>
		
					
		<div id="tertiary-navigation-and-or-page-controls">

			<p>Welcome back. <c:if test="${not empty lastLoginDate}">You last logged in on ${lastLoginDate}</c:if></p>

		</div> <!-- end of tertiary-navigation-and-or-page-controls -->
		
	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
<c:choose>
    <c:when test="${setEvent2}">
        s.events="event2";
        s.eVar2="EZ Account - Log In";        
    </c:when>
    <c:otherwise>
        <c:if test="${setEvent3}">
            s.events="event3";            
        </c:if>
    </c:otherwise>
</c:choose>

<c:if test="${closeAccountCheck}">
     s.events="event12 : ${closeAccountAcctId}";
     s.products =",;EZ Account - Close Account;" + ";Close Account Number - " + "${closeAccountAcctId}";
     s.eVar2="EZ Account - Close Account";
</c:if>
</script>