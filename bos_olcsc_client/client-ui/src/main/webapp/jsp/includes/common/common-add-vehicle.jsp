<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<jsp:useBean id="vehicleDelegate" class="com.etcc.csc.delegate.VehicleDelegate" scope="page"/>
<tiles:useAttribute name="noteVerbiage"  id="noteVerbiage" classname="java.lang.String"/>
<tiles:useAttribute name="showPbp" id="showPbp" classname="java.lang.String"/>
<tiles:useAttribute name="saveAction"  id="saveAction" classname="java.lang.String"/>
<tiles:useAttribute name="delAction"  id="delAction" classname="java.lang.String"/>
<tiles:useAttribute name="pageTitle"  id="pageTitle" classname="java.lang.String"/>

  <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  <!-- main calendar program -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar.js"></script>
  <!-- language for the calendar -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/lang/calendar-en.js"></script>
  <!-- the following script defines the Calendar.setup helper function, which makes
       adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar-setup.js"></script>

<% 
if(request.getAttribute("tagRequestFormRequest")!= null){
TagRequestForm reqform1 =(TagRequestForm) request.getAttribute("tagRequestFormRequest");
%>
<c:set  var ="tagRequestForm" value="<%=reqform1%>"/>
<%}
 boolean flag=true;
%>

<input type="hidden" name="vehicleIndexToModify" 
    <c:choose>
        <c:when test="${empty tagRequestForm}">
            value="-1"
        </c:when>
        <c:otherwise>
            value="${tagRequestForm.vehicleIndexToModify}"
        </c:otherwise>
    </c:choose>
/>
<input type="hidden" name="deleteVehicle" value="false"/>
<input type="hidden" name="makeEditvalue" value="${tagRequestForm.make}"/>

<input type="hidden" name="acctTagSeq" 
    <c:choose>
        <c:when test="${empty tagRequestForm}">
            value="0"
        </c:when>
        <c:otherwise>
            value="${tagRequestForm.acctTagSeq}"
        </c:otherwise>
    </c:choose>
/>
<input type="hidden" name="tagAmount" 
    <c:choose>
        <c:when test="${empty tagRequestForm}">
            value="0"
        </c:when>
        <c:otherwise>
            value="${tagRequestForm.tagAmount}"
        </c:otherwise>
    </c:choose>
/>

<input type="hidden" id="pbpTag" name="pbpTag" value="${tagRequestForm.pbpTag}"/>
<input type="hidden" id="radioValue" name="radioValue" value="${radioValue}"/>

<% String whichVehPage = request.getParameter("whichVehPage");
if(session.getAttribute("whichVehPage")== null){
  session.setAttribute("whichVehPage",whichVehPage);
}
  whichVehPage = (String)session.getAttribute("whichVehPage");

%>
<c:set var="whichVehPage"  value ="<%= whichVehPage%>" />

<h1 id="vehicle-information">
                       
                    <c:choose>
                        <c:when test="${whichVehPage eq 'plate'}">
                            EZ PLATE Vehicle
                        </c:when>
                        <c:otherwise>
                            EZ TAG Vehicle
                        </c:otherwise>
                    </c:choose>
 </h1>

 <!-- <h1 id="vehicle-information">${pageTitle}${tagRequestForm.pbpTag}${tagRequestForm.activePbpTagExists}</h1> -->

<div id="ez_plate" style="display:none;">
                                  <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="710" height="225" id="mymovie" align="">
                                  <param name="movie" value="${pageContext.request.contextPath}/meta/media/flash/ez_plate.swf">
                                  <embed src="${pageContext.request.contextPath}/meta/media/flash/ez_plate.swf" quality="high"  width="710" height="225"  name="mymovie" align="" type="application/x-shockwave-flash"  >
                                    </embed>
                                    </object>
                                  <br />
                                  <font face="Arial" color="red"><strong>
                                   Note:When using EZ Plate, customers will be charged the cash rate.  EZ Plate will NOT work at any other tolling locations, such as cash/coin lanes, gated lanes or other Texas toll roads.  Users will be required to pay cash at all gated full-service exit and entrance ramps.
                                  </strong></font>
                                  <br />
</div>

<!-- <c:if test="${not empty dupLic}">
    <div class="section">
        <dt/>
        <dl class="alerts"/>
        <dd>Our records indicate this license plate (${dupLic}) is already in use by another account. Please verify your license plate number</dd>
    </div>
</c:if> -->

   <div class="section">
        <logic:messagesPresent message="true" property="alerts">
            <dl class="alerts"/>
            <dt/>
            <html:messages id="msg" message="true" property="alerts">
                <dd>${msg}</dd>
            </html:messages>
        </logic:messagesPresent>
    
    <logic:messagesPresent message="false">
        <dl class="errors">
        <dt>
        <html:messages id="msg" message="false">
            <dd><bean:write name="msg"/></dd>
        </html:messages>
        </dt>
        </dl>
    </logic:messagesPresent>
    <logic:messagesPresent message="true" property="saveFailed">
        <dl class="errors">
        <dt>
        <html:messages id="msg" message="true" property="saveFailed">
            <dd>${msg}</dd>
        </html:messages>
        </dt>
        </dl>
    </logic:messagesPresent>
   </div>
<c:set var="ezPlateExists" value="${tagRequestForm.pbpTag}"/> 

 
<c:choose>
 <c:when test="${not empty whichVehPage and whichVehPage eq 'manage'}" >

 </c:when>
 <c:otherwise>
	<table>	
        <tr>
        <td id="EztagType-eztag-td">  <input type="radio" id="EztagType-eztag" name="EztagType" value="ezTagvehicle" onkeypress="javascript:disableEnterKeytemp('false');" onclick="setAsPbpTag(false);" >EZ TAG Vehicle </input>  </td>

		  <c:set var="isthereAnEZPlate" value="${tagRequestForm.pbpTag}" />              
                

		  <c:if test="${tagRequestForm.activePbpTagExists eq 'false' }" >
                  <input type="hidden" id="ezTagOrEZPlate" name="ezTagOrEZPlate" value="${tagRequestForm.ezTagOrEZPlate}"/>
	<td id="EztagType-ezplate-td">	  <input type="radio" id="EztagType-ezplate" name="EztagType" value="pbpvehicle" onkeypress="javascript:disableEnterKeytemp('true');" onclick="setAsPbpTag(true);">EZ Plate vehicle &nbsp; &nbsp; </input> <a   href="###" title="${tagRequestForm.ezTagOrPlate}" > Do I need EZ TAG or EZ Plate?</a> </td>
		 </c:if>
                 <td id="EztagType-edit-ezplate-td" style="display:none;"><input type="radio" id="EztagType-ezplate-edit" onkeypress="javascript:disableEnterKeytemp('temp');" name="EztagTypeAdditionaledit" checked="checked" value="">EZ Plate vehicle &nbsp; &nbsp; </input></td>
                 </tr>
           </table>     
		 
</c:otherwise>
</c:choose>
<ul class="vehicles">

<input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/big-green-arrow.gif" onclick="javascript:enterPress(); return false;" style="display:none">
<c:if test="${not empty tagRequestForm.savedVehicles}">
        <!-- note to self: the <span></span> bits here are for CSS-targeting later -->
        <% flag=false; %>
                 <c:if test="${tagRequestForm.ezTagsExist}">
                    <div>
                    <p><img src="${pageContext.request.contextPath}/meta/media/buttons/big-green-arrow.gif">&nbsp; &nbsp;You have requested EZ TAG(s) for the following vehicle(s):</img></p>
                    
                    <c:if test="${showPbp}">
                    <p>Activation Fee to be applied for ${tagRequestForm.ezTagCount} additional EZ TAG(s):<strong> $<fmt:formatNumber value="${tagRequestForm.tagSaleAmount}" minFractionDigits="2" maxFractionDigits="2"/></strong>
                       <c:if test="${tagRequestForm.depositAmount > 0}">&nbsp;&nbsp;Minimum Pre-paid Deposit: <strong>$<fmt:formatNumber value="${tagRequestForm.depositAmount}" minFractionDigits="2" maxFractionDigits="2"/></strong> </c:if>
                       <c:if test="${tagRequestForm.totalAmount > 0}"> &nbsp;&nbsp;Total payment:<strong> $<fmt:formatNumber value="${tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></strong></c:if></p>
                    </c:if>
                    
                    
                    <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
                        <c:if test="${not vehicle.pbpTag}">
                        <li class="summary">
                        <dl class="fee-summary">
                        <dt>
                            <strong>
                                <c:choose>
                                    <c:when test="${empty vehicle.nickname}">
                                        ${vehicle.vehicleYear} ${vehicle.vehicleMake} ${vehicle.vehicleModel}    
                                    </c:when>
                                    <c:otherwise>
                                        ${vehicle.nickname}
                                    </c:otherwise>
                                </c:choose>
                            </strong> 
                            &mdash; ${vehicle.vehicleYear} ${vehicle.vehicleColor} ${vehicle.vehicleMake} ${vehicle.vehicleModel}, 
                            <span>${vehicle.licState} License</span> 
                            <c:choose>
                                <c:when test="${vehicle.temporaryLicPlate}">
                                    Temporary
                                </c:when>
                                <c:otherwise>
                                    ${vehicle.licPlate}
                                </c:otherwise>
                            </c:choose>
                            
                                    <input type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit vehicle ${varStatus.index}"   onclick="javascript:editVehicle(${varStatus.index}); return false;" />
                                     
                                              <input type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif"  value="Delete vehicle ${varStatus.index}"  onclick="javascript:deleteCurrentVehicle(${varStatus.index});return false;" /> <!-- here 1-->
                                                                         
                        </dt>
                        <input type="hidden" value="${vehicle.licPlate}" name="savedVehicle[${varStatus.index}].licPlate"/>
                        <input type="hidden" value="${vehicle.licState}" name="savedVehicle[${varStatus.index}].licState"/>
                        <input type="hidden" value="${vehicle.temporaryLicPlate}" name="savedVehicle[${varStatus.index}].temporaryLicPlate"/>
                        <input type="hidden" value="${vehicle.vehicleYear}" name="savedVehicle[${varStatus.index}].vehicleYear"/>
                        <input type="hidden" value="${vehicle.vehicleColor}" name="savedVehicle[${varStatus.index}].vehicleColor"/>
                        <input type="hidden" value="${vehicle.vehicleMake}" name="savedVehicle[${varStatus.index}].vehicleMake"/>
                        <input type="hidden" value="${vehicle.vehicleModel}" name="savedVehicle[${varStatus.index}].vehicleModel"/>
                        <input type="hidden" value="${vehicle.vehicleClassCode}" name="savedVehicle[${varStatus.index}].vehicleClassCode"/>
                        <input type="hidden" value="${vehicle.nickname}" name="savedVehicle[${varStatus.index}].nickname"/>
                        <input type="hidden" value="${vehicle.motorcycle}" name="savedVehicle[${varStatus.index}].motorcycle"/>
                        <input type="hidden" value="${vehicle.acctTagSeq}" name="savedVehicle[${varStatus.index}].acctTagSeq"/>
                        <input type="hidden" value="${vehicle.tagAmount}" name="savedVehicle[${varStatus.index}].tagAmount"/>
                        <input type="hidden" value="${vehicle.pbpStart}" name="savedVehicle[${varStatus.index}].pbpStart"/>
                        <input type="hidden" value="${vehicle.pbpEnd}" name="savedVehicle[${varStatus.index}].pbpEnd"/>
                        <input type="hidden" value="${vehicle.pbpTag}" name="savedVehicle[${varStatus.index}].pbpTag"/>
                    <!--    <input type="hidden" value="${vehicle.tagTypeCode}" name="savedVehicle[${varStatus.index}].tagTypeCode"/>  -->
                      </dl> <!-- end of fee-summary -->
                      <br/>
                     </li>
                        </c:if>                
                    </c:forEach>
                    </div>
                </c:if>
                
                <c:if test="${tagRequestForm.activePbpTagExists or (not empty editActivePbpTagExists and editActivePbpTagExists)}">
                    <div>
                    <c:if test="${tagRequestForm.activePbpTagExists}">
                    <p><img src="${pageContext.request.contextPath}/meta/media/buttons/big-green-arrow.gif">&nbsp; &nbsp;You have requested the following vehicle(s) to be added as EZ Plate(s):</img></p>
                    </c:if>
                    <c:if test="${showPbp and (tagRequestForm.ezTagCount <=0)}">
                    <p>&nbsp;&nbsp;Minimum Pre-paid Deposit: <strong>$<fmt:formatNumber value="${tagRequestForm.depositAmount}" minFractionDigits="2" maxFractionDigits="2"/></strong>
                       &nbsp;&nbsp;Total payment:<strong> $<fmt:formatNumber value="${tagRequestForm.totalAmount}" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
                    </c:if>
                    <c:forEach items="${tagRequestForm.savedVehicles}" var="vehicle" varStatus="varStatus">
                        <c:if test="${vehicle.pbpTag}">
                        <li class="summary">
                        <dl class="fee-summary">
                        <dt>
                            <strong>
                                <c:choose>
                                    <c:when test="${empty vehicle.nickname}">
                                        ${vehicle.vehicleYear} ${vehicle.vehicleMake} ${vehicle.vehicleModel}    
                                    </c:when>
                                    <c:otherwise>
                                        ${vehicle.nickname}
                                    </c:otherwise>
                                </c:choose>
                            </strong> 
                            &mdash; ${vehicle.vehicleYear} ${vehicle.vehicleColor} ${vehicle.vehicleMake} ${vehicle.vehicleModel}, 
                            <span>${vehicle.licState} License</span> 
                            <c:choose>
                                <c:when test="${vehicle.temporaryLicPlate}">
                                    Temporary
                                </c:when>
                                <c:otherwise>
                                    ${vehicle.licPlate}
                                </c:otherwise>
                            </c:choose>
                            
                                    <input type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit vehicle ${varStatus.index}"  onclick="javascript:editVehicle(${varStatus.index}); return false;" />                                    
                                 <!--   <input type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/delete.gif" value="Delete vehicle ${varStatus.index}" onclick="javascript:deleteCurrentVehicle(${varStatus.index});return false;" /> -->
                        </dt>
                        <input type="hidden" value="${vehicle.licPlate}" name="savedVehicle[${varStatus.index}].licPlate"/>
                        <input type="hidden" value="${vehicle.licState}" name="savedVehicle[${varStatus.index}].licState"/>
                        <input type="hidden" value="${vehicle.temporaryLicPlate}" name="savedVehicle[${varStatus.index}].temporaryLicPlate"/>
                        <input type="hidden" value="${vehicle.vehicleYear}" name="savedVehicle[${varStatus.index}].vehicleYear"/>
                        <input type="hidden" value="${vehicle.vehicleColor}" name="savedVehicle[${varStatus.index}].vehicleColor"/>
                        <input type="hidden" value="${vehicle.vehicleMake}" name="savedVehicle[${varStatus.index}].vehicleMake"/>
                        <input type="hidden" value="${vehicle.vehicleModel}" name="savedVehicle[${varStatus.index}].vehicleModel"/>
                        <input type="hidden" value="${vehicle.vehicleClassCode}" name="savedVehicle[${varStatus.index}].vehicleClassCode"/>
                        <input type="hidden" value="${vehicle.nickname}" name="savedVehicle[${varStatus.index}].nickname"/>
                        <input type="hidden" value="${vehicle.motorcycle}" name="savedVehicle[${varStatus.index}].motorcycle"/>
                        <input type="hidden" value="${vehicle.acctTagSeq}" name="savedVehicle[${varStatus.index}].acctTagSeq"/>
                        <input type="hidden" value="${vehicle.tagAmount}" name="savedVehicle[${varStatus.index}].tagAmount"/>
                        <input type="hidden" value="${vehicle.pbpStart}" name="savedVehicle[${varStatus.index}].pbpStart"/>
                        <input type="hidden" value="${vehicle.pbpEnd}" name="savedVehicle[${varStatus.index}].pbpEnd"/>
                        <input type="hidden" value="${vehicle.pbpTag}" name="savedVehicle[${varStatus.index}].pbpTag"/>
                      <!--  <input type="hidden" value="${vehicle.tagTypeCode}" name="savedVehicle[${varStatus.index}].tagTypeCode"/>  -->
                      </dl> <!-- end of fee-summary -->
                      <br/>
                     </li>
                        </c:if>                
                    </c:forEach>
                    </div>
                </c:if>                

</c:if>
<br/>
<li class="edit">
        <div class="edit-area">

                <c:if test="${fn:length(tagRequestForm.savedVehicles) eq 0}">
                	<p>
                        Enter the following information about this vehicle. 
                        <em>${noteVerbiage}</em>                      
                	</p>
                </c:if>
				<c:if test="${fn:length(tagRequestForm.savedVehicles) gt 0}">
				 	<p>
                        To add another vehicle, fill out the form below.
                        <em>${noteVerbiage}</em>                      
                	</p>
				</c:if>

                
                
                <fieldset>

                        <h2>License Plate</h2>

                        <dl>

                                <dt class="first-dt-dd-pair"><label for="license-plate-state"><span class="accessibility">License Plate </span>State:</label></dt>
                                        <dd class="first-dt-dd-pair">
                                                *<select id="license-plate-state" name="state" onkeypress="javascript:disableEnterKey();" onchange="javascript:fieldChanged();">
                                                    <c:forEach var="state" items="${stateDelegate.states}">
                                                        <option value="<c:out value="${state.stateCode}"/>"
                                                            <c:if test="${((not empty tagRequestForm.state) and (tagRequestForm.state == state.stateCode)) or ((empty tagRequestForm.state) and (state.defaultValueFlag == true))}">
                                                                selected
                                                            </c:if>
                                                        >
                                                         <c:out value="${state.stateCode}"/>
                                                        </option>
                                                    </c:forEach> 	                                                                                        
                                                </select>
                                        </dd>

                                <dt><label for="license-number">License Plate Number:</label></dt>
                                         <dd>
                                                *<input type="text" class="textfield" id="license-number" onkeypress="javascript:disableEnterKey();" name="licensePlate" value="${tagRequestForm.licensePlate}" onchange="javascript:cleanField(this);toUpper(this);fieldChanged();"/>

                                                <p class="temporary-license-plate-selection" id="temporary-license-plate-section"><label for="temporary-license-plate"><input type="checkbox" class="checkbox" id="temporary-license-plate" name="tempLicense" <c:if test='${tagRequestForm.tempLicense}'>checked</c:if>  onchange="javascript:fieldChanged();"/> This is a temporary license plate</label></p>

                                                <p class="help" id="temporary-license-plate-help">A temporary license plate will only be honored for the first 30 days. Update your vehicle information when your permanent plates arrive to avoid toll violation charges.</p>
                                        </dd>
                        </dl>


                </fieldset>
<c:choose>
 <c:when test="${not empty whichVehPage and whichVehPage eq 'manage' }" >

 </c:when>
 <c:otherwise>
	<div id="pbpDiv" style="display:none;">
	<div id="pbpDiv1" style="display:none;">
	  <fieldset>
	    <dt>Make this an EZ Plate vehicle effective:</dt>
	    <dl id="pbpSection" >
	      <label for="startDateTime">Start Date/Time: </label> *<input type="text" id="startDateTime" name="pbpStart" size="20" value="${tagRequestForm.pbpStart}" onchange="javascript: startDateTextChanged(); fieldChanged();" />&nbsp; &nbsp;<input type="image" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/images/icn_cal.gif" alt="Select start date from calendar" id="startDateCalTrigger"  name="startDateCalTrigger" />
		and to be effective for
		<select id="days-pbp-effective" name="days-pbp-effective" onChange="javascript: updateEndDate(); fieldChanged();"> <!-- Up to 45days class="textfield format-m-d-y" class="textfield format-m-d-y" -->
		  <option value="1">1</option><option value="2">2</option>
		  <option value="3" selected="selected">3</option><option value="4">4</option><option value="5">5</option>
		  <option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option>
		  <option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option>
                  <option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option>
		  <option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option>
                  <option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option>
		  <option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option>
		  <option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option>
		  <option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option>
		</select> days.<br>
			<div id="calendar-container"></div>
                <label for="endDateTime">End Date/Time: </label>&nbsp; &nbsp;<input type="text"  id="endDateTime" name="pbpEnd" size="20" value="${tagRequestForm.pbpEnd}" onchange="javascript: endDateTextChanged(); fieldChanged();" />&nbsp; &nbsp;<input type="image" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/images/icn_cal.gif" alt="Select end date from calendar" id="endDateCalTrigger" name="endDateCalTrigger" />
	    </dl>
	  </fieldset>
	  </div>
	</div>
	<br/>
</c:otherwise>
</c:choose>
                <fieldset>
                        <h2>Vehicle Information</h2>

                        <dl>

                                <dt class="first-dt-dd-pair">Vehicle Classification:  <span id="TollRates" href="#TollRates" onmouseover="javascript:hoverbox_show(this);return false;" onmouseout="javascript:hoverbox_hide();return false;"><img src="${pageContext.request.contextPath}/meta/media/common/AxleTypeOnly_Small.jpg" title="Toll Rates By Axle" /></span></dt>
                                       <div id="activeDiv" >                                           
                                         </div>
                                        <dd class="first-dt-dd-pair">
                                                <ul>
                                                        

                                                        <li><label for="vehicle-axles-2"><input type="radio" class="radio-button" value="2" onkeypress="javascript:disableEnterKey();" id="vehicle-axles-2" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '2' or (empty tagRequestForm.vehicleClass)}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('y');fieldChanged();"/>2-axles (most passenger vehicles)</label></li>
                                                        <li>   
                                                            &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="checkbox"  onkeypress="javascript:disableEnterKey();" name="motorcycle" <c:if test='${tagRequestForm.motorcycle}'>checked</c:if> onchange="javascript:fieldChanged();"/> Motorcycle
                                                        </li>

                                                        <li><label for="vehicle-axles-3"><input type="radio" class="radio-button" onkeypress="javascript:disableEnterKey();" value="3" id="vehicle-axles-3" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '3'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');fieldChanged();"/>3-axles</label>

                                                        <label for="vehicle-axles-4"><input type="radio" class="radio-button" value="4" onkeypress="javascript:disableEnterKey();" id="vehicle-axles-4" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '4'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');fieldChanged();"/>4-axles</label>

                                                        <label for="vehicle-axles-5"><input type="radio" class="radio-button" value="5" onkeypress="javascript:disableEnterKey();" id="vehicle-axles-5" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '5'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');fieldChanged();"/>5-axles</label>

                                                        <label for="vehicle-axles-6-plus"><input type="radio" class="radio-button" value="6" onkeypress="javascript:disableEnterKey();" id="vehicle-axles-6-plus" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '6'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');fieldChanged();"/>6 or more</label></li>
                                                </ul>
                                                <p class="help">Don't worry if you occasionally pull a trailer! Most toll lanes have Automatic Vehicle Classification (AVC) technology to count the number of axles on each vehicle and charge the appropriate toll.</p>

                                        </dd>
</dl></fieldset><fieldset><dl>
                                <dt><label for="year">Year:</label></dt>
                                        <dd>
                                                *<select id="year" name="year" onkeypress="javascript:disableEnterKey();" onchange="javascript:fieldChanged();">
                                                    <option value=""></option>
                                                    <c:forEach var="vehicleYear" items="${appDelegate.vehicleYears}">
                                                        <option value="${vehicleYear.value}"
                                                            <c:if test="${tagRequestForm.year == vehicleYear.value}">
                                                                selected
                                                            </c:if>
                                                        >${vehicleYear.label}</option>
                                                    </c:forEach>
                                                </select>
                                        </dd>

                                <dt><label for="color">Color:</label></dt>
                                        <dd>*<input type="text" class="textfield" onkeypress="javascript:disableEnterKey();" id="color" name="color" value="${tagRequestForm.color}"  onchange="javascript:fieldChanged();"/></dd>

                                <dt><label for="make">Make:</label></dt>
                                        <dd> *<select id="make" name="make" onkeypress="javascript:disableEnterKey();" onchange="javascript:fieldChanged();">
                                                        <option value=""></option>
                                                        <c:forEach var="make" items="${vehicleDelegate.vehicleMakes}">
                                                            <option value="${make.vehicleMake}"
                                                                <c:if test="${tagRequestForm.make == make.vehicleMake}">
                                                                    selected
                                                                </c:if>
                                                            >
                                                            ${make.vehicleMake}
                                                            </option>
                                                        </c:forEach>
                                                </select>
                                        </dd>
  
                                <dt><label for="model">Model:</label></dt>
                                        <dd>
                                                *<input type="text" class="textfield" id="model" onkeypress="javascript:disableEnterKey();" name="model" value="${tagRequestForm.model}" onblur="javascript:cleanField(this);" onchange="javascript:fieldChanged();"/>
                                        </dd>

                                <dt><label for="nickname">Vehicle&rsquo;s nickname:</label></dt>
                                        <dd>
                                                &nbsp;<input type="text" class="textfield" onkeypress="javascript:disableEnterKey();" id="nickname" name="nickname"  onchange="javascript:fieldChanged();" value="${tagRequestForm.nickname}" onblur="javascript:removeUnwantedChar(this);"/>                                                
                                                <p class="help">example: &ldquo;My car&rdquo;, &ldquo;Fleet number 214&rdquo;, and<br />please no special characters</p>
                                        </dd>
                                        
                                
                        </dl>
                </fieldset>
          <input type="hidden"  id="pbp" name="pbp"  value="${tagRequestForm.pbpTag}" />
												
                         
	<br/>

                    <p align="center"><input type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/add-another-vehicle.gif" value="Save Vehicle Information" onclick="javascript:saveVehicle(); return false;"/></p>
                

        </div> <!-- end of edit-area -->

</li> <!-- end of vehicle -->
 

</ul> 
<script type="text/javascript">
var modified = false;

function disableEnterKeytemp(str) 
{ 
   var key;

   if(window.event){
     key = window.event.keyCode; //IE
   if(key == 13){
     window.event.keyCode = 0;}else if(str == 'false'){setAsPbpTag(false)}else if (str == 'true'){setAsPbpTag(true)}
   }else {
     key = e.which; //firefox
   if(key == 13){
     e.which = 0;}else if(str == 'false'){setAsPbpTag(false)}else if (str == 'true'){setAsPbpTag(true)}
   }
}
function enterPress(){
   saveVehicle();
}
function fieldChanged()
{
    modified = true;
}

function toggleMotorcycle(enabled)
{
    document.tagRequestForm.motorcycle.disabled = (enabled == 'y')?false:true;
    if (enabled != 'y')
        document.tagRequestForm.motorcycle.checked='';  
}
 
function saveVehicle()
{

    document.tagRequestForm.action="${pageContext.request.contextPath}/${saveAction}?pbp=" + document.getElementById('pbp').value;
    if (!submitted)
    {
        submitted = true;

		document.tagRequestForm.submit();
    }
}

    function deleteCurrentVehicle(index)
    {
        
		document.tagRequestForm.deleteVehicle.value="true";
        document.tagRequestForm.vehicleIndexToModify.value=index;
		document.tagRequestForm.vehicleIndexToModify.value
        document.tagRequestForm.action="${pageContext.request.contextPath}/${delAction}";
        if (!submitted)
        {
            submitted = true;
            document.tagRequestForm.submit();
        }
        
    }

    function editVehicle(index)
    {    
        document.tagRequestForm.licensePlate.value=document.tagRequestForm.elements["savedVehicle[" + index + "].licPlate"].value;
        
        if (document.tagRequestForm.elements["savedVehicle[" + index + "].temporaryLicPlate"].value == 'true' )
        {
            document.tagRequestForm.licensePlate.value = 'TEMP';
            document.tagRequestForm.tempLicense.checked='checked';
        }
        else
        {
            document.tagRequestForm.tempLicense.checked='';
        }
        
        document.tagRequestForm.state.value=document.tagRequestForm.elements["savedVehicle[" + index + "].licState"].value;
        document.tagRequestForm.year.value=document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleYear"].value;
        
        var which = document.getElementById('make');
        for(i=0;i<which.length;i++)
         {
            if(((which.options[i].value).toUpperCase())== ((document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleMake"].value).toUpperCase()))
            {
              which.options[i].selected = true;
              document.tagRequestForm.make.value = which.options[i].value;
              break;
            }
          }

        
       // document.tagRequestForm.make.value=document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleMake"].value;
        document.tagRequestForm.model.value=document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleModel"].value;
        document.tagRequestForm.color.value=document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleColor"].value;
        document.tagRequestForm.nickname.value=document.tagRequestForm.elements["savedVehicle[" + index + "].nickname"].value;
        document.tagRequestForm.acctTagSeq.value=document.tagRequestForm.elements["savedVehicle[" + index + "].acctTagSeq"].value;
        document.tagRequestForm.tagAmount.value=document.tagRequestForm.elements["savedVehicle[" + index + "].tagAmount"].value;
       // document.tagRequestForm.tagTypeCode.value=document.tagRequestForm.elements["savedVehicle[" + index + "].tagTypeCode"].value;
        
        
        var classCode = document.tagRequestForm.elements["savedVehicle[" + index + "].vehicleClassCode"].value;
        for (var i=0; i<document.tagRequestForm.vehicleClass.length; i++)
        {
            if (classCode == document.tagRequestForm.vehicleClass[i].value)
                document.tagRequestForm.vehicleClass[i].checked="checked";
        }
        
        if (document.tagRequestForm.elements["savedVehicle[" + index + "].motorcycle"].value == 'true' )
        {
            document.tagRequestForm.motorcycle.checked='checked';    
        }
        else
            document.tagRequestForm.motorcycle.checked='';  
            
        document.tagRequestForm.motorcycle.disabled = (classCode==2)?false:true;
        
       
        if (document.tagRequestForm.elements["savedVehicle[" + index + "].pbpTag"].value == 'true')
        {
            document.tagRequestForm.pbpTag.checked = 'checked';
            document.tagRequestForm.pbpStart.value = document.tagRequestForm.elements["savedVehicle[" + index + "].pbpStart"].value;
            document.tagRequestForm.pbpEnd.value = document.tagRequestForm.elements["savedVehicle[" + index + "].pbpEnd"].value;
            document.getElementById("startDateTime").value=document.tagRequestForm.elements["savedVehicle[" + index + "].pbpStart"].value;
            document.getElementById("endDateTime").value=document.tagRequestForm.elements["savedVehicle[" + index + "].pbpEnd"].value;
            //alert("start:" +document.tagRequestForm.pbpStart.value );            
         startDate = new Date(document.getElementById("startDateTime").value);
         endDate  = new Date(document.getElementById("endDateTime").value);  
         document.getElementById("startDateTime").value=getFormattedDate(new Date(document.getElementById("startDateTime").value));
         document.getElementById("endDateTime").value=getFormattedDate(new Date(document.getElementById("endDateTime").value));
          var msTilEndDate1 = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective1 = Math.ceil(msTilEndDate1/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective1-selectDaysIdxOffset);
         setAsPbpTag(true);         
       //  document.getElementById("EztagType-eztag").style.display = 'none';
         document.getElementById("EztagType-eztag-td").style.display = 'none';
         if(document.getElementById("ezTagOrEZPlate")!=null){
         document.getElementById("EztagType-ezplate-td").style.display = '';
       //  document.getElementById("EztagType-ezplate").style.display = '';         
         } else  document.getElementById("EztagType-edit-ezplate-td").style.display = '';
        }
        else
        {
            document.tagRequestForm.pbpTag.checked = '';   
            document.tagRequestForm.pbpStart.value='';
            document.tagRequestForm.pbpStart.value='';
            setAsPbpTag(false);
            document.getElementById("EztagType-eztag-td").style.display = '';
           // document.getElementById("EztagType-eztag").style.display = '';
            if(document.getElementById("ezTagOrEZPlate")!=null){
               // document.getElementById("EztagType-ezplate").style.display = 'none';
                 document.getElementById("EztagType-ezplate-td").style.display = 'none';
            }else document.getElementById("EztagType-edit-ezplate-td").style.display = 'none';
        }
        
        changeTagType();
       
       
        document.tagRequestForm.vehicleIndexToModify.value=index;
    }
    
    
    function checkPbpLicPlate()
    {
        if (document.tagRequestForm.pbpTag.checked && document.tagRequestForm.licensePlate.value=='TEMP')
        {
            alert("A valid license plate number is required for an EZ Plate vehicle. Please enter a valid license plate number or request an EZ TAG for the vehicle by removing the Start and End dates.");
            return false;
        }
        
        return true;
    }

function changeTagType()
{
     if (document.tagRequestForm.pbpTag.checked)
    {
      
        document.getElementById('pbpDiv').style.display='block';
        document.getElementById('pbpDiv1').style.display='block';         
        document.getElementById('pbpSection').style.display="";       
        document.getElementById('ez_plate').style.display='';
		
    }
    else{  
        document.getElementById('pbpSection').style.display="none";
        document.getElementById('pbpDiv1').style.display='none';
        document.getElementById('pbpDiv').style.display='none';
        document.getElementById('ez_plate').style.display='none';
        document.tagRequestForm.pbpStart.value = "";
        document.tagRequestForm.pbpEnd.value = "";
    }
}
//var elm =  document.getElementById('pbpTag');

	// if(elm.value == 'false'){	      
	//	document.getElementById('pbpSection').style.display="none";
	 //}
	 //document.tagEztagType

	 //alert(document.forms["tagRequestForm"].EztagType);

	 if(document.forms["tagRequestForm"].EztagType != null && document.forms["tagRequestForm"].EztagType[0] != null){
		 //alert()
		 if( document.forms["tagRequestForm"].EztagType[0].checked == 'true')
		 document.getElementById('pbpTag').style.display='none';
	 }

var page = '<c:out value="${whichVehPage}" />';

//alert(" load page " + page + " form field " + document.forms["tagRequestForm"].EztagType[1].checked + "check " + document.forms["tagRequestForm"].EztagType[0].checked)
//if(page == '' && (document.forms["tagRequestForm"].EztagType[1] != 'undefined' && document.forms["tagRequestForm"].EztagType != null && (document.forms["tagRequestForm"].EztagType[1] != null))){//change1

	//alert("I am caledar load")


var tagType = "EZTag";
var showPbp = false;
var ezTagsExist = false;
var oneMinute = (60*1000);
var oneHour = Date.HOUR;
var oneDay = (oneHour*24);
var fortyFiveDays = 45;
var now = null;
var startDate = null;
var endDate = null;
var startNotEarlierThan = null;
var startNotLaterThan = null;
var endNotLaterThan = null;
var endNotEarlierThan = null;
var prevEnddate = null;
var defaultEffectiveDays = 1;
var selectDaysIdxOffset = 1;
var dateFormatStr = "%m/%d/%Y %I:%M %p";

  Calendar.setup(
    {
      inputField   : "startDateTime",
      ifFormat     : dateFormatStr,
      button       : "startDateCalTrigger",
      align        : "tR",                 // Top of and to the right.
      weekNumbers  : false,                // Don't show week numbers.
      showsTime    : true,                 // Select time, as well.
      timeFormat   : "12",                 // Show time in AM/PM format.
      singleClick  : false,                // Require double click to close.
      step         : 1                     // Show every year in yr drop-down.
    }
  );

  Calendar.setup(
    {
      inputField   : "endDateTime",
      ifFormat     : dateFormatStr,
      button       : "endDateCalTrigger",
      align        : "bR",                 // Bottom of and to the right.
      weekNumbers  : false,                // Don't show week numbers.
      showsTime    : true,                 // Select time, as well.
      timeFormat   : "12",                 // Show time in AM/PM format.
      singleClick  : false,                // Require double click to close.
      step         : 1                     // Show every year in yr drop-down.
    }
  );
//}
  window.onload = initStartEnd;

  <%@ page import="com.etcc.csc.presentation.form.TagRequestForm" %>
  <%@ page import="java.util.List" %>
  function initStartEnd() {	
	
    document.getElementById('pbpDiv').style.display='none';  
    now = new Date();
    startNotEarlierThan = new Date();
    startNotLaterThan = new Date();
    startNotLaterThan.setDate(startNotLaterThan.getDate() + fortyFiveDays-1);
    endNotLaterThan = new Date();
    endNotLaterThan.setDate(endNotLaterThan.getDate() + fortyFiveDays);
    var s = new Date();
    endNotEarlierThan = new Date();
    endNotEarlierThan.setDate(s.getDate() + defaultEffectiveDays);
    //setStartDate(s);
    var e = new Date();
    e.setDate(eval(e.getDate() + defaultEffectiveDays + 2));
    //setEndDate(e);
        
    if(page == '' && (document.forms["tagRequestForm"].EztagType[1] != 'undefined' && (document.forms["tagRequestForm"].EztagType[1] != null ))) { //change2
    if(document.getElementById("ezTagOrEZPlate")!=null){

  if(document.getElementById("ezTagOrEZPlate").value!='pbpvehicle'){
      setStartDate(s);
       setEndDate(e);
       }
       }
       endDateTextChanged();
       endNotEarlierThan.setDate(s.getDate());
       endNotEarlierThan.setTime(s.getTime() + oneMinute);
       document.getElementById('pbpDiv').style.display='none';
       document.getElementById('ez_plate').style.display='none';
      }else{
            //alert("Loading in the else")
            if(document.forms["tagRequestForm"].EztagType != null && document.forms["tagRequestForm"].EztagType.checked != null ){ if(document.forms["tagRequestForm"].EztagType.checked == true || document.forms["tagRequestForm"].EztagType.checked == 'true'){
		document.getElementById('pbpDiv').style.display='none';
                document.getElementById('ez_plate').style.display='none';
		}
            }
	}	
	if(document.forms["tagRequestForm"].EztagType[0] != null){ if(document.forms["tagRequestForm"].EztagType[0].checked != null){ if(document.forms["tagRequestForm"].EztagType[0].checked == true || document.forms["tagRequestForm"].EztagType[0].checked == 'true'){
	document.getElementById('pbpDiv').style.display='none';
        document.getElementById('ez_plate').style.display='none';
	  }
        }
	}
	//alert("End of loading")
	document.getElementById('pbpDiv').style.display='none';
        document.getElementById('ez_plate').style.display='none';
	var browserName=navigator.appName;
	//alert("browser " + browserName )
		var s = new String(browserName);
		s= s.trim();
	if (s == "Microsoft Internet Explorer"){		
		document.getElementById('pbpDiv1').style.display='none';
                document.getElementById('ez_plate').style.display='none';
	}
        if(document.getElementById("ezTagOrEZPlate")!=null){
       if(document.getElementById("ezTagOrEZPlate").value=='pbpvehicle'){
  
          document.getElementById('pbpDiv').style.display='block';
	  document.getElementById('pbpDiv1').style.display='block';
          document.getElementById('ez_plate').style.display='block';
          }
          }
  }

  function setAsPbpTag(doIt) {
   //alert("DOIT ..." + doIt)
	if (doIt == true) {
      document.getElementById('pbpTag').checked="checked";
      document.getElementById('pbpDiv').style.display='';
	  document.getElementById('pbpDiv1').style.display='';
          document.getElementById('ez_plate').style.display='';
          document.getElementById('license-number').disabled=false;
          document.getElementById('temporary-license-plate').checked=false;
      document.getElementById('temporary-license-plate-section').style.display='none';  
      document.getElementById('temporary-license-plate-help').style.display='none'; 
	  document.getElementById('pbp').value = true;
	  
	  document.getElementById("vehicle-information").innerText = "EZ PLATE Vehicle";
	  
          if(document.tagRequestForm.licensePlate.value =='TEMP')
             document.tagRequestForm.licensePlate.value ='';
       
          // var s = new Date();   
         //  setStartDate(s);
         
          now = new Date();
    startNotEarlierThan = new Date();
    startNotLaterThan = new Date();
    startNotLaterThan.setDate(startNotLaterThan.getDate() + fortyFiveDays-1);
    endNotLaterThan = new Date();
    endNotLaterThan.setDate(endNotLaterThan.getDate() + fortyFiveDays);
    var s = new Date();
    endNotEarlierThan = new Date();
    endNotEarlierThan.setDate(s.getDate());
    endNotEarlierThan.setTime(s.getTime() + oneMinute);
     if(document.getElementById("startDateTime").value =='')
        {
    setStartDate(s);
    var e = new Date();
    e.setDate(e.getDate() + defaultEffectiveDays +2);
    setEndDate(e);
    //endDateTextChanged();
    msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
        } 
//        else{
//    now = new Date(document.getElementById("startDateTime").value);
//    startNotEarlierThan = new Date(document.getElementById("startDateTime").value);
//    startNotLaterThan = new Date(document.getElementById("startDateTime").value);
//    startNotLaterThan.setDate(startNotLaterThan.getDate() + fortyFiveDays-1);
//    endNotLaterThan = new Date(document.getElementById("startDateTime").value);
//    endNotLaterThan.setDate(endNotLaterThan.getDate() + fortyFiveDays);
//    var s = new Date(document.getElementById("startDateTime").value);
//    endNotEarlierThan = new Date(document.getElementById("startDateTime").value);
//    endNotEarlierThan.setDate(s.getDate());
//    endNotEarlierThan.setTime(s.getTime() + oneMinute);
//  //  setStartDate(s);
//  //  var e = new Date(document.getElementById("startDateTime").value);
//  //  e.setDate(e.getDate() + defaultEffectiveDays +2);
//  //  setEndDate(e);
//    //endDateTextChanged();
//        }
        if(document.getElementById("ezTagOrEZPlate")!=null){
         document.getElementById("ezTagOrEZPlate").value="pbpvehicle";
          document.forms["tagRequestForm"].EztagType[1].checked= true;
          }
	 
    } else {
      document.getElementById('pbpTag').checked="";
      document.getElementById('pbpDiv').style.display='none';
	  document.getElementById('pbpDiv1').style.display='none';
          document.getElementById('ez_plate').style.display='none';
          document.getElementById('temporary-license-plate-section').style.display=''; 
      document.getElementById('temporary-license-plate').style.display='';
	  document.getElementById('pbp').value = false;
          if( document.getElementById("ezTagOrEZPlate")!=null)
        document.getElementById("ezTagOrEZPlate").value="ezTagvehicle";
        document.getElementById("EztagType-eztag").checked = true;
        
        document.getElementById("vehicle-information").innerText = "EZ TAG Vehicle";
        
        
    }
  }

  function setStartDate(aDate) {
    startDate = aDate;
    document.getElementById("startDateTime").value = getFormattedDate(aDate);
  }

  function setEndDate(aDate) {
    endDate = aDate;
    document.getElementById("endDateTime").value = getFormattedDate(aDate);
  }

  function startDateTextChanged() {
    var msTilEndDate = null;
    var nbrDaysEffective = null;
	
    var dateStr = document.getElementById("startDateTime").value
    var changedTo = new Date(dateStr);
    if ( isNaN(changedTo.getDate()) ) {
      setStartDate(startDate);
      alert ("Date entered was invalid.  Please enter a valid date.");
    } else {
      if ( (compareDates(changedTo,now)<=0)&&(changedTo.getTime() < now.getTime()) ) {
          setStartDate(startDate);
          alert ("Start date may not be earlier than current time");
      } else {
        if ( changedTo.getTime() > startNotLaterThan.getTime() ) {
          setStartDate(startDate);
          alert ("Start date may not be beyond 44 days from now.  Not later then " + startNotLaterThan);
        } else {
          if ( changedTo.getTime() < (endDate.getTime() + oneMinute)
            || changedTo.getTime() > endDate.getTime() ) {
            if((changedTo.getTime() > endDate.getTime()) || (changedTo.getTime() == (endDate.getTime()))){
            setStartDate(changedTo);
            endDate.setDate(startDate.getDate()); 
            endDate.setTime(startDate.getTime() + oneMinute); 
            setEndDate(endDate);
            alert ("Effective days may not be less than Start date and time.  End date adjusted.");
            msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
            }
            else
            {
            setStartDate(changedTo);
            endNotEarlierThan = new Date();
            endNotEarlierThan.setDate(startDate.getDate());
            endNotEarlierThan.setTime(startDate.getTime() + oneMinute);
            msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
            }
          }  else {
            setStartDate(changedTo);
            endNotEarlierThan = new Date();
             endNotEarlierThan.setDate(startDate.getDate());
            endNotEarlierThan.setTime(startDate.getTime() + oneMinute);
            msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
          }
        }
      }
    }
	
  };

  function endDateTextChanged() {
    var dateStr = document.getElementById("endDateTime").value
    var changedTo = new Date(dateStr);
    if ( isNaN(changedTo.getDate()) ) {
      setEndDate(endDate);
      alert ("Date entered was invalid.  Please enter a valid date.");
    } else {
      if ( (changedTo.getTime()+oneMinute) < (endNotEarlierThan.getTime()+oneMinute) ) {
        setEndDate(endDate);
        alert ("Cannot change end date to be earlier than start date and time.");
        var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
      } else {
        if ( changedTo.getTime() > endNotLaterThan.getTime() ) {
        if(prevEnddate == null)
            setEndDate(endDate);
        else
           {
           setEndDate(prevEnddate);
           prevEnddate =null;
           }
          var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
          alert ("Cannot change end date beyond 45 days from now.  Not later then " + endNotLaterThan);
        } else {
          setEndDate(changedTo);
          var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
        }
      }
    }
  };

  function updateEndDate() {
    var effectiveDaysSelect = document.getElementById("days-pbp-effective");    
    var days = eval(effectiveDaysSelect.selectedIndex + selectDaysIdxOffset);   
    var d = new Date(document.getElementById("startDateTime").value); 
    d.setDate(d.getDate() + days);
    prevEnddate = new Date(document.getElementById("endDateTime").value); 
    setEndDate(d);
    endDateTextChanged();
  }

  function getFormattedDate(aDate) {
  
    var mm = aDate.getMonth() +1;
    var dd = aDate.getDate();
    var yyyy = aDate.getFullYear();
    var hh = aDate.getHours();
    var MM = aDate.getMinutes();    
    var AM_PM;

    if (mm < 10) mm = "0"+mm;
    if (dd < 10) dd = "0"+dd;
    if (hh < 12) {
      AM_PM = "AM";
    if (hh == 0) hh = 12;
    else if (hh < 10) hh = "0"+hh;
      
    } else if(hh == 12){
      AM_PM = "PM";
      hh = (hh);
    } else {
      AM_PM = "PM";
      hh = (hh-12);
    }
    if (MM < 10) MM = "0"+MM;

    return mm+"/"+dd+"/"+yyyy+" "+hh+":"+MM+" "+AM_PM;
  }

  function checkIt() {
    alert ("tagType="+tagType + "\nshowPbp=" +showPbp);	  
  }
function checkforDefault(){
if(document.getElementById("ezTagOrEZPlate")!=null){

  if(document.getElementById("ezTagOrEZPlate").value=='pbpvehicle'){
         startDate = new Date(document.getElementById("startDateTime").value);
         endDate  = new Date(document.getElementById("endDateTime").value);
          setStartDate(startDate);
          setEndDate(endDate);
          document.getElementById('pbpDiv').style.display='block';
	  document.getElementById('pbpDiv1').style.display='block';
          document.getElementById('ez_plate').style.display='block';
         setAsPbpTag(true);
         var msTilEndDate1 = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective1 = Math.ceil(msTilEndDate1/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective1-selectDaysIdxOffset);
         // document.getElementById("EztagType-eztag").style.display='none';
          document.getElementById("EztagType-eztag-td").style.display='none';
          
  }else{
  if(document.getElementById("radioValue").value=='true'){
     document.getElementById("EztagType-ezplate-td").style.display='none';
     }
     setAsPbpTag(false);}
}else setAsPbpTag(false);

 var which = document.getElementById('make');
 //alert(""+document.tagRequestForm.makeEditvalue.value);
        for(i=0;i<which.length;i++)
         {
            if(((which.options[i].value).toUpperCase())== ((document.tagRequestForm.makeEditvalue.value).toUpperCase()))
            {
              which.options[i].selected = true;
              document.tagRequestForm.make.value = which.options[i].value;
              break;
            }
          }

}
function cancelSubmit()
{
  return false;
}
</script>
<STYLE type="text/css">
/* hover box */  
   #activeDiv{   
 position: absolute;   
 width: 185px;   
 height: 90px;   
 visibility: hidden;   
} 
 </STYLE>

<script type='text/javascript'>  
 checkforDefault();
// define reference to the hidden div element  
var div;  
  
// set reference to the div element after page is loaded  
window.onload = function (){  
		  div = document.getElementById('activeDiv'); 
	var instance = "<c:out value='${whichVehPage}'/>";
	  //alert("hi"+instance);
	/**  if((document.getElementById("ezTagOrEZPlate")!=null) && (document.getElementById("ezTagOrEZPlate").value =='ezTagvehicle') && (instance== 'plate')){
	   //{
	       document.getElementById('pbpDiv').style.display='block';
	    document.getElementById('pbpDiv1').style.display='block';
	            document.getElementById('ez_plate').style.display='block';
	            document.getElementById('EztagType-ezplate').checked=true;;
	          setAsPbpTag(true);
	     }  **/
	
    // D13868 08/19/2013 vgovindaswamy EZPlate Null value issue
   	
	//var pbpFlag =   "<c:out value='${tagRequestForm.activePbpTagExists}'/>"; 
	 	 
    var pbpStart =  "<c:out value='${tagRequestForm.pbpStart}'/>";
       
	var ezTagEZPlate =   "<c:out value='${tagRequestForm.ezTagOrEZPlate}'/>"; 
	   
    if (((document.getElementById("ezTagOrEZPlate") == null)) && ((pbpStart.length > 0)) && ((ezTagEZPlate == null) || (ezTagEZPlate == '')))
          {
    	  
    	   document.getElementById('pbpDiv').style.display='block';
	       document.getElementById('pbpDiv1').style.display='block';
	       document.getElementById('ez_plate').style.display='block';
		   document.getElementById('EztagType-edit-ezplate-td').checked=true;
		   document.getElementById("EztagType-eztag-td").style.display = 'none'; 
		   document.getElementById("EztagType-edit-ezplate-td").style.display = ''; 
	       setAsPbpTag(true); 	   
          }
            
              
	    if((document.getElementById("ezTagOrEZPlate")!=null) && (document.getElementById("ezTagOrEZPlate").value =='ezTagvehicle') && (instance== 'plate')){
		   document.getElementById('pbpDiv').style.display='block';
	       document.getElementById('pbpDiv1').style.display='block';
	       document.getElementById('ez_plate').style.display='block';
	       document.getElementById('EztagType-ezplate').checked=true;
	       setAsPbpTag(true);
	     } 
		 
	}  
 
  
// show hoverbox  
function hoverbox_show(obj){  
  // define oTop and oLeft variables  
  src="${pageContext.request.contextPath}/meta/media/common/AxleTypeOnly_Large.jpg"
 // alert(src);
  type="";
  var oTop  = 0;  
  var oRight = 0;  
  // find object position on the page  
 // do {
  oRight+=obj.offsetLeft; 
  oTop+=obj.offsetTop
  //} while (obj=obj.offsetParent);  
  // set the position of invisible div  
 // div.style.top  = (oTop) - 204 + 'px';  
 // div.style.right = (oRight) + 2 + 'px';  
  div.style.left = '-35%';
  // put iframe with img in div element (this will work in any browser)  
  if (type == 'iframe')  
    div.innerHTML = '<iframe marginwidth="0" marginheight="0" frameborder="no" ' +  
                    'width="100%" height="100%" scrolling="no" src="'+src+'">' +  
                    '</iframe>';  
  // or put img HTML in the div element (IE6 has problem with select overlay)  
  else  
    div.innerHTML = '<img src="'+src+'"/>';  
  // show hoverbox  
  div.style.visibility = 'visible';  
}  
  
// hide hoverbox (hide div element)  
function hoverbox_hide(){  
  div.style.visibility = 'hidden';  
} 

 
</script>
