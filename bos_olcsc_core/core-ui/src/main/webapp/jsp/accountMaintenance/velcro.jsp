<%-- 
  - Author(s): Noel Ternida
  - Date: April 21, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Get Velcro page.
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
    <td class="content">
        

         
       <logic:messagesPresent>
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
            </div>
        </logic:messagesPresent>
        
    	<logic:messagesNotPresent>
    	<c:if test="${velcroForm.map.editMode == true}">
            <p><fmt:message key="required.fields.label"/></p>
            <br/>
        </c:if>
        
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
            <html:hidden property="maxAllowed"/>
            <html:hidden property="activeTolltag"/>
            
            <table cellspacing="0" class="form" width="100%">
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.name"/>:</th>
                        <td colspan="3" class="text-data">
                                ${velcroForm.map.name}
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.acctId"/>:</th>
                        <td colspan="3" class="text-data">
                                ${velcroForm.map.acctId}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.address1"/>:</th>
                        <td colspan="3" class="text-data">
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
                        <td colspan="3" class="text-data">
                            ${velcroForm.map.city}
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.state"/>: </th>
                        <td colspan="3" class="text-data">
                            ${velcroForm.map.state}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.zipCode"/>: </th>
                        <td colspan="3" class="text-data">
                            ${velcroForm.map.zipCode}
                            <c:if test="${velcroForm.map.plus4 != null}">
                                -${velcroForm.map.plus4}
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th><fmt:message key="velcroForm.tolltags"/>: </th>
                        <td colspan="3" class="text-data">
                            ${velcroForm.map.activeTolltag}
                        </td>
                    </tr>
                    <tr class="odd">
                        <th><fmt:message key="velcroForm.orderQty"/>: *</th>
                        <td colspan="3" class="text-data">
                            <c:if test="${velcroForm.map.editMode == true}">
                                <html:text property="orderQty" styleClass="text" size="3" maxlength="2" onblur="cleanNumericField(this)"/> 
                            </c:if>
                            <c:if test="${velcroForm.map.editMode == false}">
                                ${velcroForm.map.orderQty}
                            </c:if>
                        </td>
                    </tr>
                    </table>	
                    <br/>
                    <p class="note">
                        <bean:message key="velcroForm.note1"/>
                    </p>
                    <br/>
                    <c:if test="${velcroForm.map.editMode == true}">
                      <input name="Submit3" type="submit" class="button" 
                        value='<fmt:message key="button.submit.request"/>' 
                        onclick="javascript: this.disabled='true'; document.velcroForm.submit();">
                    </c:if>
                </p>
        </html:form>	
        </logic:messagesNotPresent>	
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
    <c:if test="${velcroForm.map.editMode == true}">
    	if(document.velcroForm && document.velcroForm.orderQty) {
        	document.velcroForm.orderQty.focus();
    	}
    </c:if>
</script>
