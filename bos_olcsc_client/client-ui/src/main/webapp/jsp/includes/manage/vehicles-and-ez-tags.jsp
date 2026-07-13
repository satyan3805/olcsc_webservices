<%--
<script type="text/javascript"
src="${pageContext.request.contextPath}/meta/behavior/loading-message.js"></script> 
--%>

<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content-container">

	<form id="data-actions" name="tagRequestForm" action="${pageContext.request.contextPath}/accountVehiclesAndTags.do" method="post">

		<div id="content">

			<h1 id="vehicles-and-ez-tags">Vehicles &amp; EZ TAGs</h1>
                        
                        <div class="section">
                            <logic:messagesPresent message="true" property="alerts">
                                <dl class="alerts"/>
                                <dt/>
                                <html:messages id="msg" message="true" property="alerts">
                                    <dd>${msg}</dd>
                                </html:messages>
                            </logic:messagesPresent>
                        </div> <!-- end of section -->


				<!-- Included for activation and Inactivation of EZTAG alerts -->
				
				<div class="section">
                    <logic:messagesPresent message="false">
                        <dl class="errors"/>
                        <dt/>
                        <html:messages id="msg" message="false">
                            <dd><bean:write name="msg"/></dd>
                        </html:messages>
                    </logic:messagesPresent>
                    <logic:messagesPresent message="true" property="saveFailed">
                        <dl class="errors"/>
                        <dt/>
                        <html:messages id="msg" message="true" property="saveFailed">
                            <dd>${msg}</dd>
                        </html:messages>
                    </logic:messagesPresent>
                </div>
				<!-- End of Activation and Inactivation of EZTag Alerts -->

			<div class="section">

				<c:if test="${((accountTagsCount >= 10) or (not empty searchString)) and (not preview)}">
                                    <fieldset>
                                            <dl>
                                                    <dt><label for="license-number">Quickly identify/find a vehicle:</label></dt>
                                                            <dd>
                                                                    <input type="text" class="textfield with-adjacent-form-field" id="license-number" name="searchString" value="${searchString}" onblur="javascript:removeUnwantedChar(this);"/>
                                                                    <input type="button" value="Search" onclick="javascript:doSubmit();"/>
                                                                    <p class="help">Enter a license plate, EZ TAG ID, vehicle nickname, or any other description of the vehicle you&rsquo;re looking for.</p>
                                                            </dd>
                                            </dl>
                                    </fieldset>
                                </c:if>

			</div> <!-- end of div.section -->

			<ul class="table-info">
  <li>EZ TAG vehicle(s)</li>

  <c:if test="${not (acctInfo.suspensionFlag and acctInfo.violationFlag)}">
    <c:if test="${acctInfo.revenueAccount}" >								
      <c:if test="${(acctLoginInfo.acctActivity ne 'I')}">
        <% session.setAttribute("whichVehPage" , null); %>
        <li class="add-item"><a href="${pageContext.request.contextPath}/accountManageVehicleAddDisplay.do?whichVehPage=tag&activePbpTagExists=${activePbpTagExists}">add a permanent vehicle</a></li>
        <c:if test="${uploadAccess}"><li><a href="#1" onclick="javascript:doUpload(); return false;">Multiple Vehicle Upload</a></li></c:if>
      </c:if>
    </c:if>
  </c:if>
			</ul>

                        <display:table name="accountTags" cellspacing="1" class="data-table" pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/accountVehiclesAndTags.do" id="record" sort="list" decorator="com.etcc.csc.decorator.RowStyleTableDecorator">
                            <display:column title="Nickname"  property="nickname" sortable="true"/>
                            <display:column title="EZ TAG #" property="fullTagId" sortable="true"/>
                            <c:choose>
                                <c:when test="${preview or sessionScope.acctLoginInfo.acctActivity eq 'I'}">
                                    <display:column title="Lic Plate" property="licPlate" sortable="true"/>
                                </c:when>
                                <c:otherwise>
                                    <display:column title="Lic Plate" property="licPlateLink" sortable="true"/>
                                </c:otherwise>
                            </c:choose>
                            <display:column title="State" property="licState" sortable="true"/>
                            <display:column title="Year" property="vehicleYear" sortable="true"/>
                            <display:column title="Make" property="vehicleMake" sortable="true"/>
                            <display:column title="Model" property="vehicleModel" sortable="true"/>
                            <display:column title="Color" property="vehicleColor" sortable="true"/>
							 <c:choose>
                                <c:when test="${preview or sessionScope.acctLoginInfo.acctActivity eq 'I'}">
                                    <display:column title="Tag Status" property="tagStatusDesc" sortable="true" />	
                                </c:when>
                                <c:otherwise>
                                    <display:column title="Tag Status" property="tagStatusLink" sortable="true" />	
                                </c:otherwise>
                            </c:choose>						 
							
                        </display:table>
                        
                        <br/><br/>
                        
                        <ul class="table-info">
                                
				<li>EZ Plate vehicle(s)</li>
                                <c:if test="${(not acctInfo.suspensionFlag) and (not activePbpTagExists)}">
				<!--<li class="add-item"><a href="${pageContext.request.contextPath}/accountVehicleAddDisplay.do?activePbpTagExists=${activePbpTagExists}">add a vehicle</a></li>-->
								<c:if test="${acctInfo.revenueAccount}" >
								<c:if test="${(acctLoginInfo.acctActivity ne 'I')}">
                                <!--<li class="add-item"><a href="${pageContext.request.contextPath}/AccountVehicleAddPbpDisplay.do">add a temporary vehicle</a></li>-->
								 <li class="add-item"><a href="${pageContext.request.contextPath}/accountManageVehicleAddDisplay.do?whichVehPage=plate">add a temporary vehicle</a></li>
								</c:if>
								</c:if>
                                </c:if>
			</ul>
                        
                        <display:table name="pbpTags" cellspacing="1" class="data-table"
                                pagesize="50" requestURI="${pageContext.request.contextPath}/accountVehiclesAndTags.do" id="record" sort="list"
                                decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="pbpTags">
                            <display:column title="Nickname"  property="nickname" sortable="true"/>
                            <display:column title="EZ TAG #" property="fullTagId" sortable="true"/>
                            <c:choose>
								   <c:when test="${preview or sessionScope.acctLoginInfo.acctActivity eq 'I'}">							
                                    <display:column title="Lic Plate" property="licPlate" sortable="true"/>
                                </c:when>
                                <c:otherwise>
                                    <display:column title="Lic Plate" property="licPlateLink" sortable="true"/>
                                </c:otherwise>
                            </c:choose>
                            <display:column title="State" property="licState" sortable="true"/>
                            <display:column title="Year" property="vehicleYear" sortable="true"/>
                            <display:column title="Make" property="vehicleMake" sortable="true"/>
                            <display:column title="Color" property="vehicleColor" sortable="true"/>
                            
                            <display:column title="Start Date/Time" property="pbpStart" sortable="false"/>
                            <display:column title="End Date/Time" property="pbpEnd" sortable="false"/>
							<display:column title="Tag Status" property="tagStatusLink"  sortable="true" />	
                            
                        </display:table>
                        
			<div id="tertiary-navigation-and-or-page-controls">

				<ul id="page-controls">
                                    <!--
					<li><a href="#" class="pdf">download PDF</a></li>
					<li><a href="#" class="excel">download Excel</a></li>
                                    -->

					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
				 <!--	<li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
                                -->
                                    <c:if test="${preview}">
                                        <li><a href="#" class="print" onclick="javascript: window.print(); return false;">print</a></li>
                                    </c:if>
                                    <li><a href="#" 
                                            <c:choose>
                                                <c:when test="${preview}">
                                                    class="print-preview"
                                                </c:when>
                                                <c:otherwise>
                                                    class="print-all" 
                                                </c:otherwise>
                                            </c:choose>
                                            onclick="javascript:printPreview(); return false;">
                                        <c:choose>
                                            <c:when test="${preview}">
                                                turn off print preview
                                            </c:when>
                                            <c:otherwise>
                                                print all tags
                                            </c:otherwise>
                                        </c:choose>
                                    </a></li>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

		</div> <!-- end of content -->

	</form>

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->


<script type="text/javascript">
<c:if test="${setEvent3}">
    <c:choose>
      <c:when test="${activationOfInactive}">    
         s.events= "event3,event14";
         s.eVar14="Activation of Inactive Tags";
         s.products =";Activation of Inactive Tags - Vehicles and EzTags";
     </c:when>
     <c:otherwise>
       s.events= "event3";
     </c:otherwise>
  </c:choose>   
</c:if>
var submitted=false;
function closureAlert()
{
<c:if test="${closureAlert}">
  var answer = confirm("Since There is no active tags. Do you want to close this account ? Click 'OK' to confirm or Click 'Cancel' to cancel the action.");
		if(answer){
			document.tagRequestForm.action = "${pageContext.request.contextPath}/accountInformation.do?accountClosure=true";
                        document.tagRequestForm.submit();
		}else{
			return;
		}
    </c:if>
}
function goToMailedTagActivation()
{
	 document.tagRequestForm.action = "${pageContext.request.contextPath}/mailedTagActivationDisplay.do?statuschange=true";
	 document.tagRequestForm.submit();
}
function goToChangeTagStatus(idx,desc,tagId)
{
	if(!submitted){
		submitted=true;
		if(desc == 'Active'){
			var answer = confirm("Do you want to Inactivate this vehicle with TAG# "+tagId+" ? Click 'OK' to confirm or Click 'Cancel' to cancel the action.");
			if(answer){
				document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehicleEditDisplay.do?statuschange=true&index="+idx;
	                        document.tagRequestForm.submit();
			}else{
				submitted=false;
				return;
			}
		}else {
				if(desc == 'Inactive'){
				var answer = confirm("Do you want to Activate this vehicle with TAG# "+tagId+" ? Click 'OK' to confirm or Click 'Cancel' to cancel the action.");
				if(answer){
					document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehicleEditDisplay.do?statuschange=true&index="+idx;
	                                document.tagRequestForm.submit();
				}else{
					submitted=false;
					return;
				}
			}
		} 	
	} else {
		
		alert("Please wait until previous process is completed.");
	}
    
	//document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehicleEditDisplay.do?statuschange=true&index="+idx;
   // document.tagRequestForm.submit();
}
function goToEdit(idx)
{
    document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehicleEditDisplay.do?index="+idx;
    document.tagRequestForm.submit();
}

function printPreview()
{
    var actionstring = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";
    <c:if test="${(not preview)}">
        actionstring = actionstring + "?preview=Y";
    </c:if>
    
    document.tagRequestForm.action = actionstring;
    document.tagRequestForm.submit();
}

function doSubmit()
{
    document.tagRequestForm.submit();
}

function doUpload()
    {     
      document.tagRequestForm.action = "${pageContext.request.contextPath}/accountUploadMultipleVehicle.do?upload=y";
      document.tagRequestForm.submit();   
      
    }

closureAlert();
</script>

