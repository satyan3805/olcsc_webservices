<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

        <form id="data-actions" action="${pageContext.request.contextPath}/accountReceiptsResult.do" method="post">
            <div id="content">

			<h1 id="account-activity-receipts">Account Activity &ndash; Receipts</h1>

                        <div class="section">
                            <logic:messagesPresent message="false">
                            <dl class="errors"/>
                                    <html:messages id="msg" message="false">
                                        <dd><bean:write name="msg"/></dd>
                                    </html:messages>
                            </logic:messagesPresent>
                            <logic:messagesPresent message="true" property="incorrectDates">
                            <dl class="errors"/>
                                    <html:messages id="msg" message="true" property="incorrectDates">
                                        <dd><bean:write name="msg"/></dd>
                                    </html:messages>
                            </logic:messagesPresent>
                        </div>

                        <p>Please provide a date range:</p>
                        <fieldset>

                                <ul>
					<li>
						<dl>
							<dt><label for="from-date">From<span class="accessibility"> date</span></label></dt>
							<dd><input type="text" class="date textfield format-m-d-y" id="from-date" name="startDate" value="${startDate}"/></dd>

							<dt><label for="through-date">through<span class="accessibility"> date</span></label></dt>
							<dd><input type="text" class="date textfield format-m-d-y" id="through-date" name="endDate" value="${endDate}" /></dd>
						</dl>
					</li>
					<li>
						<input type="submit" class="submit-button" value="Search" onclick="javascript:return validateDates();"/>
					</li>
				</ul>

			</fieldset>
                        </td><td width="40%"><b>Please Note: Transactions may take several business days to post to your EZ TAG Account.</b></td></tr></table>

                        <input type="hidden" name="transactionID" >
                        <input type="hidden" name="HASFlag" >
                        <div class="section">
                    <c:if test="${(not empty accountReceipts)}">

                        <c:choose>
                          <c:when test="${accountReceipts.accountReceipts!=null}">
                            <!-- begin statement for current month -->
                            <display:table name="accountReceipts.accountReceipts" cellspacing="0" sort="list" class="data-table receipts" pagesize="0" requestURI="${pageContext.request.contextPath}/accountReceiptsResult.do" id="accrecrec" decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="accrec">

                                <display:column title="Date" sortable="true">
            <a href="javascript:openReceiptDetail('${accrec.transactionID}', '${accrec.HASFlag}')" title="View receipt details"><fmt:formatDate value="${accrec.transactionDate.time}" dateStyle="SHORT" /></a>
                                </display:column>

                                <display:column title="Transaction" property="transactionID" sortable="false" />
                                <display:column title="Amount" sortable="false">
                                    <fmt:formatNumber value="${accrec.transactionAmt}" minFractionDigits="2" maxFractionDigits="2"/>
                                </display:column>
                                <display:column title="Type" property="transactionType" sortable="false" />
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
                    </c:if>
                    </div>

			<div id="tertiary-navigation-and-or-page-controls">
				<jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

                </div> <!-- end of content -->
            </form>

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">

function openReceiptDetail(transactionID, HASFlag)
{
    document.forms[0].transactionID.value = transactionID;
    document.forms[0].HASFlag.value = HASFlag;
    document.forms[0].action = "${pageContext.request.contextPath}/accountReceiptsDetail.do";
    document.forms[0].submit();
}

function validateDates()
{
    var fromDate = document.forms[0].elements["startDate"];
    var toDate = document.forms[0].elements["endDate"];

    var minStartDate =  new Date();

    if(fromDate.value == "")
    {
        changeTextFieldColor(fromDate);
        alert("\"From Date\" must be provided.");
        fromDate.focus();
        return false
    }
    else if(toDate.value == "")
    {
        changeTextFieldColor(toDate);
        alert("\"To Date\" must be provided.");
        toDate.focus();
        return false;
    }
    else if (!isDate(fromDate.value))
    {
        changeTextFieldColor(fromDate);
        fromDate.focus();
        return false;
    }
    else if (!isDate(toDate.value))
    {
        toDate.focus();
        return false;
    }
    else if(compareDates(stringToDate(fromDate.value),
            stringToDate(toDate.value)) == 1)
    {
        changeTextFieldColor(fromDate);
        alert("\"From Date\" should not be greater than \"To Date\".");
        fromDate.focus();
        return false;
    }
    else if (compareDates(stringToDate(toDate.value), new Date()) == 1)
    {
        changeTextFieldColor(toDate);
        alert("\"To Date\" should not be greater than current date.");
        toDate.focus();
        return false;
    }
    changeTextFieldWhite(fromDate);
    changeTextFieldWhite(toDate);
    showLoading();
    return true;
}

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}
</script>
