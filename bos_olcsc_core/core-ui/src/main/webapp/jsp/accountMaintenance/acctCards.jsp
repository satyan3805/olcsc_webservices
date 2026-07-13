<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<style type="text/css">
	.cardInfoRow {     display:table;     width:100%; } 
</style>

<script type="text/javascript">


 /*   function removeCard(cardId)
    {
        openWin("${pageContext.request.contextPath}/acctCardRemove.do?cardId="+cardId,320,190);
    }*/
    
    function requestCards()
    {
        window.location.href="${pageContext.request.contextPath}/acctCardRequest.do";
    }

 /*   function removeCard(cardId) {
        day = new Date();
        id = day.getTime();
        URL = "${pageContext.request.contextPath}/displayAcctCardRemove.do?cardId="+cardId;
        eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=320,height=190,left = 480,top = 417');");
    }*/

    function removeCard(cardId, acctInventoryId){
           document.getElementById('cardId').value = cardId;
           document.getElementById('accountInventoryId').value = acctInventoryId;           
           confirmOKCancelWide('<fmt:message key="acctCardRequestForm.confirmOkText"/>',buildConfirmWindowText(),handleConfirmOK);
    }
    
    function buildConfirmWindowText(){
        var confirmText='<table width="100%" cellpadding="0" cellspacing="0"><tr><td><fmt:message key="acctCardRequestForm.note2"/>'; 
        confirmText += '</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><html:form action="/acctCardRemove" method="POST">';
        confirmText += ' <select id="removeOptions" name="removeStatus"> ';
        confirmText += '    <option value="">Select</option>';
        <c:forEach var="removeOpt" items="${rsnCodes}">
              confirmText += '  <option value="${removeOpt.cardStatus}">${removeOpt.acctCardStatus}</option>';
        </c:forEach>        
        confirmText += ' </select> ';
        confirmText += '</html:form></td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><FONT COLOR="#800000"><b><fmt:message key="acctCardRequestForm.note3"/></b></FONT></td>';
        confirmText += '</tr></table>';
        
        return confirmText;
    }   
    
   function handleConfirmOK(){
	   var remoptions = document.getElementById('removeOptions');
       var remValue = remoptions.options[remoptions.selectedIndex].value;
	   if(remValue == ''){
			alert('<fmt:message key="tagInfo.removecard.error"/>');
	   }else{
	       this.hide();
	       
	       document.getElementById('removeStatus').value = remValue;
	       document.getElementById('remFrm').submit();
	   }
    }
    
    YAHOO.util.Event.addListener(window, "load", function() {

        new YAHOO.widget.Tooltip("tt_nextTip", { context:"nextTip", text:"<fmt:message key="acctCardRequestForm.whatsnext.tooltip" />", autodismissdelay: 6000, width: 400 });     

    });
    function requestAccountcard(){
    	window.location.href="${pageContext.request.contextPath}/otherItems.do?inventoryAction=getAttrbs&selectedItemId=2";
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
        <html:form styleId="remFrm" action="/acctCardRemove" method="POST">
                <html:hidden styleId="cardId" property="cardId" />
                <html:hidden styleId="removeStatus" property="removeStatus" />
                <html:hidden styleId="accountInventoryId" property="accountInventoryId" />
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
               
        <table cellspacing="0" class="form">
           <!-- <tr>
                <td>&nbsp;</td>
            </tr>       
           <tr>
                <td>&nbsp;</td>
            </tr>  -->  
            <tr>
                <td align="center" colspan="3">
                    <a id="nextTip" href="#"  ><fmt:message key="acctCardRequestForm.whatsnext"/></a>
                      
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
        </table>           
        <table width="100%" cellpadding="0" cellspacing="0" id="data-table">
            <tr>
                <td class="panel-topleft"></td>
                <td class="panel-topcenter"><div><bean:message key="homePage.label4"/></div></td>
                <td class="panel-topright"><img src="${pageContext.request.contextPath}/images/common/buttons/minus.gif" width="13" height="13" onClick="expandcontent(this, 'sc33')" /></td>
            </tr>
            <tr>
                <td class="panel-left"></td>
                <td class="panel-content">
                    <div id="sc33" class="switchcontent" style="width: 100%">

                            <div id="cardInfo">
                                <display:table name="cardInfo" cellspacing="0" class="cardInfoRow"
                                    pagesize="500" requestURI="/acctCardDetail.do" id="cardInfoRow">
                                    <html:hidden property="accountInventoryId"/>
                                    <display:setProperty name="paging.banner.all_items_found" value=""/>
                                    <display:setProperty name="paging.banner.no_items_found">
                                        <fmt:message key="accountHome.noAcctCards"/>
                                    </display:setProperty>
                                    <display:setProperty name="paging.banner.one_item_found" value=""/>
                                    <display:setProperty name="paging.banner.some_items_found" value=""/>
                                    <display:column property="cardIdStr" title="Account Card Number" sortable="false" class="data-nowrap"/>
                                    <display:column property="acctCardStatus" title="Card Status" sortable="false" class="data-nowrap"/>
   									<c:choose>
	                                    <c:when test="${cardInfoRow.cardStatus == 'A'}">
		                                    <display:column  sortable="false" class="data-nowrap">     
			                                	<input type="button" value='<fmt:message key="button.removecard"/>' onclick="removeCard('${cardInfoRow.cardIdStr}', '${cardInfoRow.accountInventoryId}')" id="removeButton" class="button"><br/>
		                                    </display:column>
	                                    </c:when>
	                                    <c:otherwise>
					                        <display:column  sortable="false" class="data-nowrap"> 
		                                    </display:column>
					                    </c:otherwise>
                                    </c:choose>   									
                               </display:table> 
                            </div>
                     

                    </div>
                </td>		
                <td class="panel-right"></td>		
            </tr>
            <tr>
                <td class="panel-bottomleft"></td>
                <td class="panel-bottomcenter"></td>		
                <td class="panel-bottomright"></td>		
            </tr>
            

        </table><br/>
        
        <table>
        	<tr>
        		<td><p><fmt:message key="acctCardRequestForm.note1"><fmt:param value="${maxNumOfAccountCards}"/></fmt:message></p><br/></td>
        	</tr>
	    	<tr>
	        	<td width="10%" nowrap="nowrap">
        			<input name="addVeh" type="button" class="button" <c:if test="${numOfAdditionalAccountCards==0}">
                                                            disabled = "true"
                                                        </c:if>
        			value='<fmt:message key="acctCards.button.requestCards"/>' onclick="requestAccountcard()"/>
        		</td>
        	</tr>
        </table><br/>
        <hr align="left"/>
        <!--            
        <table width="100%" cellpadding="0" cellspacing="0" >
            <tr>
                <td>
                    &nbsp;
                </td>
            </tr>
             <tr>
                <td>
                    <input type="button" value="<fmt:message key="button.requestAcctCards"/>" onclick="requestCards();" class="button"/>
                </td>
            </tr>       
        </table>
        
--><br/><br/><br/><br/>

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