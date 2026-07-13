<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<jsp:include page="/jsp/includes/common/head.jsp" />
<%@ include file="/jsp/common/Taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>Payment Plan Details &mdash; Harris County Toll Road Authority</title>

<!--#include virtual="/includes/violations/head-violations.shtml" -->

</head>

<body id="www-hctra-com" class="account-section violations-section open-violations-section popup-window">

<div id="page">

<div id="content-container">

<div id="content">
<dl class="introduction">
    <dt>Please retain this agreement for your records. Visit our Online EZ TAG Store at <a
        href="http://www.hctra.org"
    >www.hctra.org</a>.</dt>

    <!-- this first guy is hidden from browsers without JavaScript (and un-hidden via JavaScript -->
    <dd class="print"><a href="javascript:window.print();">Print this document</a></dd>

    <!--    <dd class="pdf"><a href="#">Download a PDF version</a></dd>     -->
</dl>

<h1>Payment Plan Report</h1>

<div><logic:messagesPresent message="false">
    <dl class="errors" />
    <html:messages id="msg" message="false">
        <dd><bean:write name="msg" /></dd>
    </html:messages>
</logic:messagesPresent> <logic:messagesPresent message="true">
    <dl class="errors" />
    <html:messages id="msg" message="true" property="paymentPlanError">
        <dd>${msg}</dd>
    </html:messages>
</logic:messagesPresent></div>


<p class="date">Last Updated <fmt:formatDate value="${paymentPlan.lastUpdated.time}" /></p>

<div class="section with-primary-and-secondary-content">

<div class="primary-section-content"><img id="logo"
    src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="HCTRA"
/></div>
<!-- end of primary-section-content -->

<div class="secondary-section-content">

<dl class="page-detail">
    <dt>Plate # ${paymentPlan.licPlate}</dt>
    <dd>1st payment date - <fmt:formatDate value="${paymentPlan.firstPaymentDate.time}" /></dd>
    <dd>1st payment amount - $<fmt:formatNumber value="${paymentPlan.firstPaymentAmt}" minFractionDigits="2"
        maxFractionDigits="2"
    /></dd>
</dl>

</div>
<!-- end of secondary-section-content --></div>
<!-- end of div.section.with-primary-and-secondary-content -->

<div class="section with-primary-and-secondary-content">

<div class="primary-section-content">
<!-- Contact info using the hcard microformat http://microformats.org/wiki/hcard -->
<div class="vcard">
<p class="fn org"><strong>Harris County Toll Road Authority</strong></p>
<div class="adr"><bean:message key="HCTRA.address" /><br /> 
<%--    <p class="street-address">${HCTRA.address}</p>  --%>
<%--    <p><span class="locality">Houston</span>, <span class="region">TX</span> <span class="postal-code">77067-3295</span></p>    --%>
</div>
</div>
<!-- end of div.vcard --></div>
<!-- end of primary-section-content -->

<div class="secondary-section-content">
<!-- Contact info using the hcard microformat http://microformats.org/wiki/hcard -->
<div class="vcard">
<p class="fn"><strong>${paymentPlan.partyName}</strong></p>
<p class="org">${paymentPlan.partyAddress}</p>
<%--
        <p>Group In</p>
        <div class="adr">
                <p class="street-address">4011 Brynmawr</p>
                <p>
                        <span class="locality">Richmond</span>, 
                        <span class="region" title="Texas">TX</span>
                        <span class="postal-code">77469</span>
                </p>
        </div>
        --%>
<p class="tel">${paymentPlan.phoneNumber}</p>

</div>
<!-- end div.vcard --></div>
<!-- end of secondary-section-content --></div>
<!-- end of div.section.with-primary-and-secondary-content --> <!-- Change the Summary Please -->
<table
    summary="This table is a confirmation of the payment information you marked in the previous step. It displays what your amount due is, what past payments you have made, the balance of each invoice, how much you have decided to pay now, and your remaining balance for each invoice."
    class="data-table">

    <thead>
        <tr>
            <th scope="col">due date</th>
            <th scope="col" class="currency-header">amount due</th>
            <th scope="col">pay date</th>
            <th scope="col">pay method</th>
            <th scope="col" class="currency-header">amount</th>
            <th scope="col">clerk</th>
        </tr>
    </thead>

    <tfoot>
        <tr>
            <th scope="row" colspan="4">total plan amount</th>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${paymentPlan.totalPlanAmt}"
                minFractionDigits="2" maxFractionDigits="2"
            /></td>
        </tr>

        <tr>
            <th scope="row" colspan="4">total amount paid</th>
            <td class="currency"><span>$</span> <fmt:formatNumber value="${paymentPlan.totalAmtPaid}"
                minFractionDigits="2" maxFractionDigits="2"
            /></td>
        </tr>

        <tr>
            <th scope="row" colspan="4">total amount due</th>
            <td class="currency"><span>$</span><fmt:formatNumber value="${paymentPlan.totalAmtDue}"
                minFractionDigits="2" maxFractionDigits="2"
            /></td>
        </tr>

        <tr>
            <th scope="row" colspan="4">total delinquent</th>
            <td class="currency"><span>$</span><fmt:formatNumber value="${paymentPlan.totalDeliquent}"
                minFractionDigits="2" maxFractionDigits="2"
            /></td>
        </tr>
    </tfoot>

    <c:forEach items="${paymentPlan.paymentPlanDetails}" var="paymentPlanDetail" varStatus="varStatus">
        <tbody>
            <tr>
                <th scope="row"><fmt:formatDate value="${paymentPlanDetail.dueDate.time}" /></th>
                <td class="currency"><span>$</span> <fmt:formatNumber value="${paymentPlanDetail.amtDue}"
                minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatDate value="${paymentPlanDetail.payDate.time}" /></td>
                <td>${paymentPlanDetail.payMethod}</td>
                <td class="currency"><span>$</span> <fmt:formatNumber value="${paymentPlanDetail.amt}"
                minFractionDigits="2" maxFractionDigits="2"/></td>
                <td>${paymentPlanDetail.displayClerk}</td>
            </tr>
        </tbody>
    </c:forEach>

</table>
<!-- end of confirmation table --></div>
<!-- end of content --></div>
<!-- end of content-container --></div>
<!-- end of page -->

</body>
</html>
