<%--
<script type="text/javascript"
src="${pageContext.request.contextPath}/meta/behavior/loading-message.js"></script> 
--%>

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

	<form id="data-actions" name="mailedTagActivationForm" action="${pageContext.request.contextPath}/mailedTagActivationLogin.do" method="post">
        <input type="hidden" name="transId" value="${transId}" />
        <input type="hidden" name="tagActNbr" value="${tagActNbr}" />

		<div id="content">

			<h1 id="vehicles-and-ez-tags">Vehicles &amp; EZ TAGs</h1>
                        
                        <div class="section">
                            <logic:messagesPresent message="true">
                                <dl class="alerts"/>
                                <dt/>
                                <html:messages id="msg" message="true" property="alerts">
                                    <dd>${msg}</dd>
                                </html:messages>
                            </logic:messagesPresent>
                        </div> <!-- end of section -->

			

			<ul class="table-info">
                                 <li><b>Below are the list of EZ TAGs associated with the TAG Activation Number# ${tagActNbr}</b></li>                              
                               
			</ul>
                                                
                        <display:table name="mailedTags" cellspacing="1" class="data-table"
                            pagesize="50" requestURI="${pageContext.request.contextPath}/mailedTagActivationLogin.do" id="ezTag"
                             decorator="com.etcc.csc.decorator.RowStyleTableDecorator" sort="list">
                            <display:setProperty name="basic.empty.showtable" value="true" />
                            <display:setProperty name="paging.banner.no_items_found" value="" />
                            <display:column title="Nickname"  property="nickname" sortable="true"/>
                            <display:column title="EZ TAG #" property="fullTagId" sortable="true"/>                            
                            <display:column title="Lic Plate" property="licPlateLink" sortable="true"/>                               
                            <display:column title="State" property="licState" sortable="true"/>
                            <display:column title="Year" property="vehicleYear" sortable="true"/>
                            <display:column title="Make" property="vehicleMake" sortable="true"/>
                            <display:column title="Model" property="vehicleModel" sortable="true"/>
                            <display:column title="Color" property="vehicleColor" sortable="true"/>						
							
                        </display:table>
                        
                        <br/><br/> 
                        
                      <c:if test="${confirm eq 'true'}">
                        <ul >
                                 <li><input type="checkbox" class="checkbox" id="mailtag-checkbox" name="mailtagcheckbox"/>
                                 By confirming these activations you are accepting responsibility for any costs or charges attached to this account.</li>                               
                               
			</ul>
                     </c:if>  			
		</div> <!-- end of content -->
                <ul class="form-actions">  
                <c:if test="${returnAction}">
	                <input type="hidden" name="statuschange" value="true" /> 
                        <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  alt="Cancel" title="&rarr; Cancel" onclick="javascript:goToReturn();"/></li>
                </c:if>
                  <c:if test="${confirm eq 'true'}"> 
                           <c:if test="${(returnAction eq false) or (empty returnAction)}">	                     
                               <li><img src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  alt="Cancel" title="&rarr; Cancel" onclick="javascript:goToHome();"/></li>
                           </c:if>
                            <li><input id="continue" type="image" alt="continue" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Continue" alt="Continue" title="&rarr; Continue" onclick="javascript:doSubmit(); return false;" /></li>
                   
                  </c:if> 
                   </ul> <!-- end of form-actions -->
	</form>
</div> <!-- end of content-container -->


<script type="text/javascript">
<c:if test="${confirm eq 'false'}">
s.events="event13:${transId}";
s.eVar2="Mailed EzTags Activation";
s.products =";Mailed EzTags Activation - " + "${mailedTagsCount}"+ ";;;" +"event13 =" +"${mailedTagsCount}";
</c:if>

function goToReturn()
{
   document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";        
   document.mailedTagActivationForm.submit();
 }
 
function goToHome()
 {
   document.mailedTagActivationForm.action = "${appDelegate.domainName}";        
   document.forms[0].submit();
 }
function changeStatus()
{
	return false;
}
function goToEdit(idx)
{
    document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do?index="+idx;
    document.mailedTagActivationForm.submit();
}

function doSubmit()
            {                        
                    if (!document.mailedTagActivationForm.mailtagcheckbox.checked)
                    {
                        alert("Please confirm your acceptance.");
                        return;
                    }               
                    document.mailedTagActivationForm.action = "${pageContext.request.contextPath}/mailedTagActivationLogin.do?page=page2";
                    document.mailedTagActivationForm.submit();               
            }
</script>

