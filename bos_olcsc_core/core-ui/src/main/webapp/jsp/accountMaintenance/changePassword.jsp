<%@ include file="/jsp/common/Taglibs.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
        <!-- begin tabular data -->
<tiles:useAttribute name="actionName"  id="actionName" classname="java.lang.String"/>         
        <td class="content" >
    
            <html:form action="${actionName}">
                <input type="hidden" name="submitted" value="false"/>
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
                <logic:messagesPresent message="true" property="changePwdFailed">
                    <div id="error">
                        <div class="error-img"/>
                        <ul>
                            <html:messages id="msg" message="true" property="changePwdFailed">
                                <li>
                                    <bean:write name="msg"/>
                                </li>
                            </html:messages>
                        </ul>
                        <br/>
                    </div>
                </logic:messagesPresent>
            <table cellspacing="0" class="form">
            <tr>
                <td style="width:20px">&nbsp;</td>
                <td colspan="2" class="odd">
                        <bean:message key="changePasswordForm.text1"/>
                </td>
            </tr>		
            <logic:notPresent name="passwordAction" >
            <tr>
                    <td style="width:20px">&nbsp;</td>
                    <th><bean:message key="changePasswordForm.userId"/>: *</th>
                    <td> 
                        <html:text property="userId" size="30" maxlength="16" styleClass="text"/>
                    </td>
            </tr>
            <tr>
                    <td style="width:20px">&nbsp;</td>
                    <th><bean:message key="changePasswordForm.oldPassword"/>: *</th>
                    <td>
                        <html:password property="oldPassword" size="30" maxlength="16" styleClass="text"/>
                    </td>
                   
            </tr>
             </logic:notPresent>
             
              <logic:present name="passwordAction" >
              
              <html:hidden name="ChangePasswordForm" property="userId" value="OLCSC_ANONYMOUS"/>
              
               <html:hidden name="ChangePasswordForm" property="oldPassword" value="test"/>
              
              </logic:present>
             
            <tr>
                <td style="width:20px">&nbsp;</td>
                <td colspan="2" class="odd">
                    <bean:message key="changePasswordForm.text2"/>
                </td>
            </tr>
             <tr>
                <td style="width:20px">&nbsp;</td>
                <td colspan="2" class="odd">
                    <bean:message key="changePasswordForm.text3"/>
                </td>
            </tr>
            <tr>
                <td style="width:20px">&nbsp;</td>
                <td colspan="2" class="odd">
                    <bean:message key="changePasswordForm.text4"/>
                </td>
            </tr>
            <tr>
               <td style="width:20px">&nbsp;</td>
                 <td colspan="2" class="odd">
                   <table>
                     <tr>
                      <td>&nbsp;</td><td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example1"/></td>
                      <td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example1.info"/></td>
                     </tr>
                      <tr style="padding:0px 5px 0px 5px;">
                      <td>&nbsp;</td><td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example2"/></td>
                      <td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example2.info"/></td>
                     </tr>
                     <tr>
                      <td>&nbsp;</td><td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example3"/></td>
                      <td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example3.info"/></td>
                     </tr>
                      <tr>
                      <td>&nbsp;</td><td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example4"/></td>
                      <td>&nbsp;</td>
                      <td><bean:message key="changePasswordForm.example4.info"/></td>
                     </tr>
                  </table>
                 </td>
            </tr>
            
            <tr>
                    <td style="width:20px">&nbsp;</td>
                    <th><bean:message key="changePasswordForm.newPassword"/>: *</th>
                    <td>
                        <input type="password" name="newPassword" size="30" maxlength="16" class="text" onselect="javascript:unselect()"/>
                    </td>
            </tr>
            <tr>
                    <td style="width:20px">&nbsp;</td>
                    <th><bean:message key="changePasswordForm.reenterNewPassword"/>: *</th>
                    <td>
                        <input type="password" name="newPassword2" size="30" maxlength="16" class="text" onselect="javascript:unselect()"/>
                    </td>
            </tr>
            <tr>
                    <td colspan="2"></td>
                    <td>
                        <input type="submit" value='<fmt:message key="button.submit"/>' onclick="return doSubmit();" id="submitButton" class="button"><br/>
<%--                        <html:submit styleId="submitButton" 
                            value='<fmt:message key="button.submit"/>' 
                            styleClass="button"  onclick="return doSubmit();" />
--%>                            
                        <br/>
                    </td>
            </tr>			
            </table>	
            </html:form>	
        </td>

<script  language="javascript">   
    
    function doSubmit()
    {
    	if (document.ChangePasswordForm.submitted.value == "false")
        {
            document.ChangePasswordForm.submitted.value = "true";
            document.getElementById("submitButton").disabled = true;
            document.ChangePasswordForm.submit();
            return true;
        }
        return false;   
       
    }

</script>