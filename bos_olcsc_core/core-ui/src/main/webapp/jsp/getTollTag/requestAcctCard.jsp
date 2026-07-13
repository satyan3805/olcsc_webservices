<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>

<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>

<tr>
    <td class="left"></td>
    <td id="data" class="content">
        <html:form action="/acctCardSave" method="POST">
        <html:hidden property="forward" value="${forward}" />
        <div class="steps">
                <span><fmt:message key="label.steps"/>:</span>
                <ul>
                        <li class="step1-taken">${step} 1</li>
                        <li class="step2-taken">${step} 2</li>
                        <li class="step3-taken">${step} 3</li>
                        <li class="step4-taken">${step} 4</li>
                        <li class="step5-here">${step} 5</li>
                        <li class="step6">${step} 6</li>
                        <li class="step7">${step} 7</li>																					
                </ul>
        </div>
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
		<br class="clear">
                	<h2><span>${step} 5B:</span> <fmt:message key="getTolltag.vehicleInfo.cardRequest.header"/></h2>
             
               <tr>
                    <td colspan="3">
                        <fmt:message key="acctCardRequestForm.note1"/>
                    </td>
                </tr>  
            </table>
     
            <table cellspacing="0"  class="form">
     
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
    
          <%--              <input type="submit" value='<fmt:message key="button.submit.request"/>' onclick="return doSave();" id="saveButton" class="button"><br/>
              --%>    </td>
                </tr>
            </table>
	
        
<br/><br/><br/><br/>

    </html:form>
    <br>
    <br>
    <br>
    <br>
                <p>
                <a href="GetTollTagVehicleInfo.do" class="prev" id="prevLink" onclick="return prevClicked();"><fmt:message key="button.previous"/></a>

                
                     <a href="GetTollTagReview.do" class="next" onclick="return doSubmit();" id="nextLink"><fmt:message key="button.next"/></a>
               
                </p>                
    
    </td>		
    <td class="right"></td>		
</tr>
        


<script language="javascript">
    YAHOO.util.Event.addListener(window, "load", function() {
        new YAHOO.widget.Tooltip("tt_nextTip", { width: 400, height: 120, context:"nextTip", text:"<fmt:message key="acctCardRequestForm.whatsnext.tooltip" />", autodismissdelay: 6000 });     
    });
</script>
