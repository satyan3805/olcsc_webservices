<%-- 
  - Author(s): Noel Ternida
  - Date: April 24, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Velcro request acknowledgment.
  --%>
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
        <!-- begin options (buttons, pulldowns, etc.) -->
        <!-- end options -->	

        <!-- begin tabular data -->
    <td class="content">
        <logic:messagesPresent message="false">
            <div id="error">
                <div class="error-img"/>
                <ul>
                    <html:messages id="msg" message="false">
                        <li>                                        
                            <bean:write name="msg"/>
                        </li>
                    </html:messages>
                </ul>
                <br/>
            </div>
        </logic:messagesPresent>
        <logic:messagesPresent message="true">
            <div id="error">
                <div class="error-img"/>
                <ul>
                    <html:messages id="msg" message="true">
                        <li>                                        
                            ${msg}
                        </li>
                    </html:messages>
                </ul>
                <br/>
            </div>
        </logic:messagesPresent>
    
    
        <html:form action="/velcroSubmit">
            <html:hidden property="editMode"/>
            <html:hidden property="name"/>
            <html:hidden property="acctId"/>
            <html:hidden property="address1"/>
            <html:hidden property="address2"/>
            <html:hidden property="city"/>
            <html:hidden property="state"/>
            <html:hidden property="zipCode"/>
            <html:hidden property="plus4"/>
            <html:hidden property="activeTolltag"/>
            <html:hidden property="requestDate"/>
            <html:hidden property="printMode"/>
            <html:hidden property="orderQty"/>
            
            <c:if test="${velcroForm.map.printMode == false}">
                <div style="text-align:right;">
                    <a href="javascript: showHtml()"><fmt:message key="label.printerFriendly"/></a>&nbsp;
                    <a href="javascript: showPdf()"><fmt:message key="label.pdf"/></a>
                </div>
            </c:if>
            <br/>
            
            <div id="success">            
                <bean:message key="velcroForm.receiptMsg1"/>
            </div>
            <br/>
            <br/>
            
            <table cellspacing="0" class="form" width="100%">
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.name"/>:</th>
                        <td>
                                ${velcroForm.map.name}
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.acctId"/>:</th>
                        <td>
                                ${velcroForm.map.acctId}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.address1"/>:</th>
                        <td>
                                ${velcroForm.map.address1}
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.address2"/>: </th>
                        <td  colspan="3">
                                ${velcroForm.map.address2}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.city"/>: </th>
                        <td>
                            ${velcroForm.map.city}
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.state"/>: </th>
                        <td>
                            ${velcroForm.map.state}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.zipCode"/>: </th>
                        <td>
                            ${velcroForm.map.zipCode}
                            <c:if test="${velcroForm.map.plus4 != ''}">
                                -${velcroForm.map.plus4}
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.requestDate"/>: </th>
                        <td>
                            ${velcroForm.map.requestDate}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.requestQty"/>: </th>
                        <td>
                                ${velcroForm.map.orderQty}
                        </td>
                    </tr>
                    </table>	
                    <br/>
            <c:if test="${velcroForm.map.printMode == false}">
                <br/>
                <p>
                    <bean:message key="velcroForm.receiptMsg2" arg0='${pageContext.request.contextPath}/personalInfoDisplay.do'/>
                </p>
                <br/>
            </c:if>
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
    function showPdf() {
        var win = window.open ('${pageContext.request.contextPath}/velcroPDFReceipt.do', 
            '_blank', 'location=no,scrollbars=yes,status=yes,width=500,height=500,menubar=yes,toolbar=yes');
    }

    function showHtml() {
        document.velcroForm.printMode.value = true;
        var win = window.open ('', 
            '_velcro', 'location=no,scrollbars=yes,status=yes,width=500,height=500,menubar=yes,toolbar=yes');
        document.velcroForm.action = '${pageContext.request.contextPath}/velcroHtmlReceipt.do';
        document.velcroForm.target = "_velcro";
        document.velcroForm.submit();
    }
</script>