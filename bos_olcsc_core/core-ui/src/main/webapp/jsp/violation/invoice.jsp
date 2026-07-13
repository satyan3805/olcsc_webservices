<%-- 
  - Author(s): Noel Ternida
  - Date: April 26, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Invoice Search.
  --%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>

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
	<td  id="data" class="content">
		
            <form action="">
                <table cellspacing="0">
                    <tr>
                        <th></th>
                        <th><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceDate"/></th>
                        <th><fmt:message key="PaymentInfoForm.confirmInvoice.dueDate"/></th>
                        <th><fmt:message key="getTolltag.loginInfo.firstName"/></th>
                        <th><fmt:message key="getTolltag.loginInfo.lastName"/></th>
                        <th><fmt:message key="violation.invoice.licplatenbr"/></th>
                        <th><fmt:message key="violation.invoice.licPlateState"/></th>
                        <th><fmt:message key="violation.invoice.invoiceAmt"/></th>
                        <th><fmt:message key="violation.invoice.VEAAmt"/></th>
                        <th><fmt:message key="PaymentInfoForm.confirmPayment.button.next"/></th>
                    </tr>
                    <logic:iterate name="violator" property="invoices" id="invoice" indexId="ctr">
                        <tr <c:if test="${ctr%2 != 0}">class="odd"</c:if> >
                            <td><span style="text-decoration=none"><a href="javascript: updRow(${ctr})"><div id="icon${indexId}">+</div></a></span></td>
                            <td><fmt:formatDate value="${invoice.invoiceDate.time}" pattern="MM/dd/yyyy"/></td>
                            <td><fmt:formatDate value="${invoice.currDueDate.time}" pattern="MM/dd/yyyy"/></td>
                            <td>${violator.firstName}</td>
                            <td>${violator.lastName}</td>
                            <td>${violator.licPlateNbr}</td>
                            <td>${violator.licPlateState}</td>
                            <td>${invoice.invoiceAmount}</td>
                            <td>${invoice.invoiceAmount}</td>
                            <td><input type="checkbox" name="pay${indexId}"/></td>
                        </tr>
                        
                        <tr id="hello${ctr}" style="display:none" <c:if test="${ctr%2 != 0}">class="odd"</c:if>>
                            <td>&nbsp;</td>
                            <td colspan="9">
                                <table cellspacing="0">
                                    <tr>
                                        <th><fmt:message key="PaymentInfoForm.confirmViolation.violationId"/></th>
                                        <th><fmt:message key="PaymentInfoForm.detailInvoice.violationDate"/></th>
                                        <th><fmt:message key="PaymentInfoForm.detailInvoice.violationLane"/></th>
                                    </tr>
                                    <logic:iterate name="invoice" property="violations" id="violation">
                                        <tr>
                                            <td>${violation.violationId}</td>
                                            <td>${violation.date}&nbsp;${violation.time}</td>
                                            <td>${violation.laneDescr}</td>
                                        </tr>                                            
                                    </logic:iterate>
                                </table>
                            </td>
                        </tr>
                    </logic:iterate>
                    <!-- display total -->
                    <tr>
                        <td colspan="7" align="right"><strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceAmount"/></strong></td>
                        <td><fmt:formatNumber value="${violator.totalInvoiceAmount}" type="currency"/></td>
                        <td><fmt:formatNumber value="${violator.totalInvoiceVeaAmount}" type="currency"/></td>
                    </tr>
                </table>

            </form>		
        </td>
		<!-- end tabular data-->
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
    function updRow(row) {
        var obj = document.getElementById("hello" + row).style;
        if (obj.display == 'block') {
            obj.display = 'none';
        } else {
            obj.display = 'block';
        }
/*        if (obj.visibility == 'visible') {
            obj.visibility = 'hidden';
        } else {
            obj.visibility = 'visible';
        }
*/        
    }
</script>