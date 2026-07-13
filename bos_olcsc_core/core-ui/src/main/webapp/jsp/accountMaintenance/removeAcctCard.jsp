<%@ include file="/jsp/common/Taglibs.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript">


  /*  function removeCard(cardId)
    {
        openWin("${pageContext.request.contextPath}/acctCardRemove.do?cardId="+cardId,320,190);
    }*/
    
    function requestCards()
    {
        window.location.href="${pageContext.request.contextPath}/acctCardRequest.do";
    }

    function removeCard(cardId) {
        day = new Date();
        id = day.getTime();
        URL = "${pageContext.request.contextPath}/acctCardRemove.do?cardId="+cardId;
        eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=320,height=190,left = 480,top = 417');");
    }


</script>
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
        <html:form action="/acctCardRemove" method="POST">
        <html:hidden property="cardId" value="${cardId}"/>
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
 
        
        <table width="100%" cellpadding="0" cellspacing="0" id="data-table">
            <tr>
                <td class="panel-topleft"></td>
                <td class="panel-topcenter"><div><bean:message key="homePage.label5"/></div></td>
                <td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" width="13" height="13" onClick="expandcontent(this, 'sc33')" /></td>
            </tr>
            <tr>
                <td class="panel-left"></td>
                <td class="panel-content">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>
                            <fmt:message key="acctCardRequestForm.note2"/> 
                        </td>
                    </tr>
                    <tr>                   
                        <td>
                            <html:select property="removeStatus">
                                <option value="">Select</option>
                                <html:optionsCollection name="rsnCodes" label="acctCardStatus" value="cardStatus"/>
                            </html:select>
                        </td>
                    </tr>                     
                    <tr> 
                        <td>
                            <fmt:message key="acctCardRequestForm.note3"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value='<fmt:message key="button.removecardConfirm"/>' id="removeConfirmButton" class="button"/>
    
                            <input type="button" value='<fmt:message key="button.cancel"/>' onclick="" id="cancelConfirmButton" class="button"/>                                                    
                        </td>
                    </tr>
                </table>
                
                </td>		
                <td class="panel-right"></td>		
            </tr>
            <tr>
                <td class="panel-bottomleft"></td>
                <td class="panel-bottomcenter"></td>		
                <td class="panel-bottomright"></td>		
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
</script>