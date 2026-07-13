<%@ include file="/jsp/common/Taglibs.jsp"%>

<%System.out.println("***********************Inside receiptTollTag jsp......................"); %>
<c:set var="step">
	<fmt:message key="label.step" />
</c:set>

<tr>
	<td class="left"></td>
	<td class="content">
		<!-- begin content -->

		<div class="steps">
			<span><fmt:message key="label.steps" />:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-taken">${step} 4</li>
				<li class="step5-taken">${step} 5</li>
				<li class="step6-taken">${step} 6</li>
				<li class="step7-here">${step} 7</li>
			</ul>
		</div> <br class="clear"> <!-- start of TRACK_Northtexastollwayassociation/Trackit_Secure pixel tag -->

		<c:if test="${!empty sessionScope.PAYMENT_CONTEXT.trackLink}">
			<img SRC="<c:out value="${sessionScope.PAYMENT_CONTEXT.trackLink}"/>"
				style="display: none" /></img>
		</c:if> <!-- end of TRACK_Northtexastollwayassociation/Trackit_Secure pixel tag -->
		<h2>
			<span>${step} 7:</span>
			<fmt:message key="PaymentInfoForm.receiptTolltag.header" />
		</h2>

		<p>
			<strong><fmt:message
					key="PaymentInfoForm.receiptTolltag.note1" />
			</strong>
		</p>
		<ul class="content">
			<!-- <li><fmt:message key="PaymentInfoForm.receiptTolltag.note2"/></li> -->
				<c:if test="${sessionScope.TollTagForm.map.plan != 'C'}">
				<li><fmt:message key="PaymentInfoForm.receiptTolltag.note3" />
					<c:if test="${sessionScope.PAYMENT_CONTEXT.postingStatus == 'P'}">
						<fmt:message key="PaymentInfoForm.receiptTolltag.note4" />
					</c:if></li>
				<li><c:if test="${empty sessionScope.POS_ID}">
						<fmt:message key="PaymentInfoForm.receiptTolltag.note5" />
					</c:if>
			<c:if test="${not empty sessionScope.POS_ID}">
					<fmt:message key="PaymentInfoForm.receiptTolltag.note5.pos" />
				</c:if>
			</li>
			</c:if>

			<li><c:if test="${empty sessionScope.POS_ID}">
					<fmt:message key="PaymentInfoForm.receiptTolltag.note6" />
				</c:if> <c:if test="${not empty sessionScope.POS_ID}">
					<fmt:message key="PaymentInfoForm.receiptTolltag.note6.pos" />
				</c:if></li>
			<li><fmt:message key="PaymentInfoForm.receiptTolltag.note7" />
			</li>
		</ul> <br> <iframe id="msgFrame" name="msgFrame"
			src="<etcc-extended:Translation property="step7MessageUrl"/>"
			width="500" height="100" frameborder=0></iframe> <br>

		<p>
			<strong><fmt:message
					key="PaymentInfoForm.receiptTolltag.note8" />
			</strong>
		</p> <br>

		<div style="text-align: right;">
			<a href="DisplayPaymentReport.do?format=PDF" target="_blank"><fmt:message
					key="label.pdf" />
			</a>&nbsp; <a href="DisplayPaymentReport.do?format=HTML" target="_blank"><fmt:message
					key="label.printerFriendly" />
			</a>
		</div> <tiles:insert name="method" ignore="true" /> <tiles:insert
			name="invoice" ignore="true" /> <tiles:insert name="violation"
			ignore="true" /> <tiles:insert name="tag" ignore="true" /> <tiles:insert
			name="total" ignore="true" />

		<p>
			<a href="myAccountHome.do"><img
				alt="<fmt:message key="label.login"/>"
				src="${pageContext.request.contextPath}<fmt:message key="images.loginbutton"/>" />
			</a>
		</p> <!-- end content --></td>

	<td class="right"></td>
</tr>