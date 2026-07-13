<%@ include file="/jsp/common/Taglibs.jsp" %>
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
    <td class="content">
        <c:if test="${tagRequestForm.printMode == false}">
            <div style="text-align:right;">
                <a href="javascript: showHtml()">
                    <fmt:message key="label.printerFriendly"/>
                </a>&nbsp;
                <a href="javascript: showPdf()">
                    <fmt:message key="label.pdf"/>
                </a>
            </div>
            <br/>
            <br/>
        </c:if>
      <html:form action="/tagHtmlReceipt.do" method="POST"  >
        <table cellspacing="1" class="form">
            <tr class="odd-a">
             <td width="10px"/>
             <th><fmt:message key="tagRequestForm.licensePlate.short"/></th>
             <th><fmt:message key="tagRequestForm.state"/></th>
             <th><fmt:message key="tagRequestForm.year"/></th>
             <th><fmt:message key="tagRequestForm.color"/></th>
             <th><fmt:message key="tagRequestForm.make"/></th>
             <th><fmt:message key="tagRequestForm.model"/></th>
            </tr>
                                
            <c:forEach items="${tagRequestForm.savedVehicles}" var="savedVehicle" varStatus="varStatus">
                <tr>
                  <td width="10px"/>
                  <td>${savedVehicle.licPlate}</td>
                  <td>${savedVehicle.licState}</td>
                  <td>${savedVehicle.vehicleYear}</td>
                  <td>${savedVehicle.vehicleColor}</td>
                  <td>${savedVehicle.vehicleMake}</td>
                  <td>${savedVehicle.vehicleModel}</td>
                </tr>
                 <input type="hidden" name="savedVehicle[${varStatus.index}].licPlate" value="${savedVehicle.licPlate}"/>
                 <input type="hidden" name="savedVehicle[${varStatus.index}].temporaryLicPlate" value="${savedVehicle.temporaryLicPlate==true}"/>
                 <input type="hidden" name="savedVehicle[${varStatus.index}].plateExpiration" value="${savedVehicle.plateExpiration}"/>
                 <input type="hidden" name="savedVehicle[${varStatus.index}].licState" value="${savedVehicle.licState}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleYear" value="${savedVehicle.vehicleYear}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleColor" value="${savedVehicle.vehicleColor}"/>
                 <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleMake" value="${savedVehicle.vehicleMake}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleModel" value="${savedVehicle.vehicleModel}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].vehicleClassCode" value="${savedVehicle.vehicleClassCode}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].acctTagSeq" value="${savedVehicle.acctTagSeq}"/> 
                 <input type="hidden" name="savedVehicle[${varStatus.index}].checkDup" value="${savedVehicle.checkDup}"/> 
            </c:forEach>
            <tr style="height:10px"><td>&nbsp;</td></tr>
            <tr class="odd-a">
                <td width="10px"/>
                <td colspan=6><strong><bean:message key="tagRequestForm.receiptMsg"/></strong></td>
            </tr>
            <tr style="height:50px"><td>&nbsp;</td></tr>
<%--            
            <c:if test="${tagRequestForm.printMode == false}">
                <tr>
                    <td width="10px"/>
                    <td colspan=6>
                        <a href="javascript: showHtml()">Printer-friendly Version</a>&nbsp;
                        <a href="javascript: showPdf()">Download in PDF</a>
                    </td>
                </tr>
            </c:if>
--%>            
        </table>
        <html:hidden property="retailTranId"/>
        </html:form>    
    </td>
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
function showHtml()
{
    var win = window.open ('', 
            '_tagHtmlReceipt', 'location=no,scrollbars=yes,status=yes,width=800,height=500,menubar=yes,toolbar=yes');
        document.tagRequestForm.target = "_tagHtmlReceipt";
        document.tagRequestForm.action = "${pageContext.request.contextPath}/tagHtmlReceipt.do?format=html";
        document.tagRequestForm.submit();
}

function showPdf()
{
    var win = window.open ('', 
            '_tagPdfReceipt', 'location=no,scrollbars=yes,status=yes,width=700,height=500,menubar=yes,toolbar=yes');
        document.tagRequestForm.target = "_tagPdfReceipt";
        document.tagRequestForm.action = "${pageContext.request.contextPath}/tagPdfReceipt.do";
        document.tagRequestForm.submit();
}
</script>