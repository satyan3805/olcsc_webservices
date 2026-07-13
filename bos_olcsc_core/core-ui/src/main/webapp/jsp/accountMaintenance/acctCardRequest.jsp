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
    <td class="content">
        <html:form action="/acctCardSave" method="POST">

        <br> 
            <logic:messagesPresent message="true" property="success">
                <div id="success">
                    <ul>
                        <html:messages id="message" message="true" property="success">
                            <li>
                                ${message}
                            </li>
                        </html:messages>
                    </ul>
                    <br/>
                </div>
            </logic:messagesPresent>
            <logic:messagesPresent message="true" property="infoError">
                <div id="error">
                    <div class="error-img"/>
                    <ul>
                        <html:messages id="message" message="true" property="infoError">
                            <li>
                                ${message}
                            </li>
                        </html:messages>
                    </ul>
                    <br/>
                </div>
            </logic:messagesPresent>
             <table cellspacing="0"  class="form">
               <tr>
                    <td colspan="3">
                        <fmt:message key="acctCardRequestForm.note1"/>
                    </td>
                </tr>  
            </table>
     
            <table cellspacing="0"  class="form" border="0">
     
               <tr>
                    <td>&nbsp;</td>
                </tr>   
                <tr class="odd-a">
                    <td align="center" colspan="3">
                        <a  id="nextTip" href="#" ><fmt:message key="acctCardRequestForm.whatsnext"/></a>
                          
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr class="odd-a">
                 <td width="10px"/>
                 <td>
                   <b> <fmt:message key="acctCardRequestForm.numOfTollTags"/>&nbsp;<c:out value="${cardInfo.numOfTags}"/> </b>
                 </td>
                 <td width="10px"/>
                 <td align="left">
                    <b><fmt:message key="acctCardRequestForm.currentCards"/>&nbsp;<c:out value="${cardInfo.numOfCards}"/> </b>
                 </td>               
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>

                <tr class="odd-a">
                     <c:if test="${cardInfo.numOfAdditionalCards > 0}">
                            <td align="center" colspan="2">
                                <b><fmt:message key="acctCardRequestForm.additionalCards"/></b>
                            </td>
                            <td>                        
                            <html:select property="numCardRequested">
                                <html:options name="numAddiCards" />
                            </html:select>
                            </td>                            
                     </c:if>
                     <%--
                     <c:if test="${cardInfo.numOfAdditionalCards <= 0}">
                            <td align="center" colspan="2">
                                <b><fmt:message key="acctCardRequestForm.additionalCards"/></b>
                            </td>
                            <td>                        
                            <html:select property="numCardRequested">
                                <html:option value="1"/>
                            </html:select>
                            </td>                            
                     </c:if>           
--%>
                </tr>
                <tr class="odd-a">
                  <td width="10px"/>
                </tr>  
			
                <tr>
                  <td width="10px"/>
                  <td colspan="3">
                     <c:if test="${cardInfo.numOfAdditionalCards == 0 || cardInfo.disableBtn == 1}">                
                       <input type="submit" value='<fmt:message key="button.submit.request"/>' onclick="return doSave();" id="saveButton" disabled="true" class="button"><br/>
                    </c:if>
                     <c:if test="${cardInfo.numOfAdditionalCards > 0 && cardInfo.disableBtn != 1}">  
                        <input type="submit" value='<fmt:message key="button.submit.request"/>' onclick="return doSave();" id="saveButton" class="button"><br/>
                    </c:if>                  
                  </td>
                </tr>
            </table>
	
        
<br/><br/><br/><br/>

    </html:form>
    <br>
    <br>
    <br>
    <br>
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

<script language="javascript">
    YAHOO.util.Event.addListener(window, "load", function() {

        new YAHOO.widget.Tooltip("tt_nextTip", { width: 400, height: 120, context:"nextTip", text:"<fmt:message key="acctCardRequestForm.whatsnext.tooltip" />", autodismissdelay: 6000 });     

    });
</script>