<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<jsp:useBean id="vehicleDelegate" class="com.etcc.csc.delegate.VehicleDelegate" scope="page"/>

<tiles:useAttribute name="alwaysShowPbp"  id="alwaysShowPbp" classname="java.lang.String"/>
<tiles:useAttribute name="showDeactivateButton" id="showDeactivateButton" classname="java.lang.String"/>
<tiles:useAttribute name="saveAction"  id="saveAction" classname="java.lang.String"/>
<tiles:useAttribute name="licPlateReadOnly"  id="licPlateReadOnly" classname="java.lang.String"/>
<tiles:useAttribute name="violationDisplayAction"  id="violationDisplayAction" classname="java.lang.String"/>
 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  <!-- main calendar program -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar.js"></script>
  <!-- language for the calendar -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/lang/calendar-en.js"></script>
  <!-- the following script defines the Calendar.setup helper function, which makes
       adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/calendar-setup.js"></script>

<div id="content-container">

	<div id="content">

		<form name="tagRequestForm" action="${pageContext.request.contextPath}/${saveAction}" method="post" AUTOCOMPLETE="off">

		<input type="hidden" name="acctTagSeq" value="${tagRequestForm.acctTagSeq}"/>
                <input type="hidden" name="pbpTag" value="${tagRequestForm.pbpTag}"/>
                <input type="hidden" name="allowEditPbpStart" value="${tagRequestForm.allowEditPbpStart}"/>
                <input type="hidden" name="allowEditPbpEnd" value="${tagRequestForm.allowEditPbpEnd}"/>
                <input type="hidden" name="page" value="manage"/>
                <input type="hidden" name="tagTypeCode" value="${tagRequestForm.tagTypeCode}"/>
                <input type="hidden" name="tagStatusFlip" value="${tagRequestForm.tagStatusFlip}"/>

                <h1 id="vehicles-and-ez-tags">
                 <c:set var="instance1" value="${tagRequestForm.pbpTag}"/>

                    <c:choose>
                        <c:when test="${(instance1=='true')||(alwaysShowPbp=='true')}">
                            EZ PLATE Vehicle
                        </c:when>
                        <c:otherwise>
                            EZ TAG Vehicle
                        </c:otherwise>
                    </c:choose>
                </h1>
                <!--  ez_plate movie  -->
                        <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
                            <div id="ez_plate">
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

                    </c:if>
<!--  ez_plate movie  -->
                <c:if test="${not empty dupLic}">
                    <div class="section">
                        <dt/>
                        <dl class="alerts"/>
                        <dd>Our records indicate this license plate (${dupLic}) is already in use by another account. Please verify your license plate number</dd>
                    </div>
                </c:if>
                <div class="section">

                    <logic:messagesPresent message="true" property="alerts">
                        <dl class="alerts"/>
                        <dt/>
                        <html:messages id="msg" message="true" property="alerts">
                            <dd>${msg}</dd>
                        </html:messages>
                    </logic:messagesPresent>

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


			<ul class="vehicles">

                            <li class="edit">

        <div class="edit-area">

                <p>
                        Enter the following information about this vehicle.

                       <!-- <em>(or press &ldquo;Add Billing Information&rdquo; if all vehicles have been added)</em>-->
                </p>



                <fieldset>

                        <h2>License Plate</h2>

                        <dl>

                                <dt class="first-dt-dd-pair"><label for="license-plate-state"><span class="accessibility">License Plate </span>State:</label></dt>
                                        <dd class="first-dt-dd-pair">
                                                *<select id="license-plate-state" name="state" <c:if test="${(tagRequestForm.pbpTag) and (not alwaysShowPbp)}"> readonly style="background-color:rgb(221,221,221);color: rgb(140,140,140);"</c:if> >
                                                    <c:choose>
                                                        <c:when test="${(tagRequestForm.pbpTag) and (not alwaysShowPbp)}">
                                                            <option value="<c:out value="${tagRequestForm.state}"/>" ><c:out value="${tagRequestForm.state}"/></option>
                                                        </c:when>
                                                        <c:otherwise>
                                                             <c:forEach var="state" items="${stateDelegate.states}">
                                                             <option value="<c:out value="${state.stateCode}"/>"
                                                            <c:if test="${((not empty tagRequestForm.state) and (tagRequestForm.state == state.stateCode)) or ((empty tagRequestForm.state) and (state.defaultValueFlag == true))}">
                                                                selected
                                                            </c:if>
                                                        >
                                                         <c:out value="${state.stateCode}"/>
                                                        </option>
                                                    </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                        </dd>

                                <dt><label for="license-number">License Plate Number:</label></dt>
                                        <dd>
                                                *<input type="text" class="textfield" id="license-number" name="licensePlate" tabIndex=1 value="${tagRequestForm.licensePlate}" <c:if test="${(tagRequestForm.pbpTag) and (not alwaysShowPbp)}"> readonly style="background-color:rgb(221,221,221);color: rgb(140,140,140);"</c:if> onblur="javascript:cleanField(this);toUpper(this);"  <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly </c:if>/>

                                                <p class="temporary-license-plate-selection" <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">style="display:none;"</c:if>><label for="temporary-license-plate"><input type="checkbox" class="checkbox" id="temporary-license-plate" name="tempLicense" <c:if test='${tagRequestForm.tempLicense}'>checked</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly </c:if>/> This is a temporary license plate</label></p>

                                                <p class="help">A temporary license plate will only be honored for the first 30 days. Update your vehicle infomation when you permanent plates arrive to avoid toll violation charges.</p>
                                        </dd>
                                 <dt></dt><dd></dd>
                        </dl>


                </fieldset>

                <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
                    <fieldset>
                        <dt>This is an EZ Plate vehicle effective:</dt>
                        <dl id="pbpSection">
                        <label for="startDateTime">Start Date/Time: </label> *<input type="text" id="startDateTime" size="20" name="pbpStart" value="${tagRequestForm.pbpStart}" <c:if test="${not tagRequestForm.allowEditPbpStart}">readonly style="background-color:rgb(221,221,221);color: rgb(140,140,140);"</c:if> onChange="javascript:startDateTextChanged();"/>&nbsp; &nbsp;<c:if test="${tagRequestForm.allowEditPbpStart}"><input type="image" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/images/icn_cal.gif" alt="Select start date from calendar" id="startDateCalTrigger"  name="startDateCalTrigger" /></c:if>
                            &nbsp; &nbsp; and to be effective for
                            <select id="days-pbp-effective" name="days-pbp-effective" onChange="javascript: updateEndDate();" <c:if test="${not tagRequestForm.allowEditPbpEnd}">readonly style="background-color:rgb(221,221,221);color: rgb(140,140,140);" disabled</c:if>> <!-- Up to 45days class="textfield format-m-d-y" class="textfield format-m-d-y" -->
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
                        <label for="endDateTime">End Date/Time: </label>&nbsp; &nbsp;<input type="text" onChange="javascript:endDateTextChanged();" id="endDateTime" name="pbpEnd" size="20" value="${tagRequestForm.pbpEnd}" <c:if test="${not tagRequestForm.allowEditPbpEnd}">readonly style="background-color:rgb(221,221,221);color: rgb(140,140,140);"</c:if>/>&nbsp; &nbsp;<c:if test="${tagRequestForm.allowEditPbpEnd}"><input type="image" src="${pageContext.request.contextPath}/meta/behavior/jscalendar-1.0/images/icn_cal.gif" alt="Select end date from calendar" id="endDateCalTrigger" name="endDateCalTrigger" /></c:if>
                            <input type="hidden" id="endDateSaved" name="endDateSaved" value="${tagRequestForm.pbpEnd}"/>
                            <input type="hidden" id="startDateSaved" name="startDateSaved" value="${tagRequestForm.pbpStart}"/>
                        </dl>
                    </fieldset>
                </c:if>
                <br/>
                <fieldset>
                        <h2>Vehicle Information</h2>
                        <dl>

                                <dt class="first-dt-dd-pair"><span id="TollRates" href="#TollRates" onmouseover="javascript:hoverbox_show(this);return false;" onmouseout="javascript:hoverbox_hide();return false;"><img src="${pageContext.request.contextPath}/meta/media/common/AxleTypeOnly_Small.jpg" title="Toll Rates By Axle" /></span></dt>

                                        <dd class="first-dt-dd-pair">
                                                <ul>


                                                        <li><label for="vehicle-axles-2"><input type="radio" class="radio-button" value="2" id="vehicle-axles-2" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '2' or (empty tagRequestForm.vehicleClass)}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');"/>2-axles (most passenger vehicles)</label></li>
                                                        <li>
                                                            &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="checkbox"  name="motorcycle" <c:if test='${tagRequestForm.motorcycle}'>checked</c:if> disabled/> Motorcycle
                                                        </li>

                                                        <li><label for="vehicle-axles-3"><input type="radio" class="radio-button" value="3" id="vehicle-axles-3" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '3'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if> />3-axles</label></li>

                                                        <li><label for="vehicle-axles-4"><input type="radio" class="radio-button" value="4" id="vehicle-axles-4" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '4'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if> />4-axles</label></li>

                                                        <li><label for="vehicle-axles-5"><input type="radio" class="radio-button" value="5" id="vehicle-axles-5" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '5'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if> />5-axles</label></li>

                                                        <li><label for="vehicle-axles-6-plus"><input type="radio" class="radio-button" value="6" id="vehicle-axles-6-plus" name="vehicleClass" <c:if test="${tagRequestForm.vehicleClass == '6'}">checked="checked"</c:if> onclick="javascript:toggleMotorcycle('n');" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if> />6 or more axles</label></li>
                                                </ul>
                                                <p class="help">Don't worry if you occasionally pull a trailer! Most toll lanes have Automatic Vehicle Classification (AVC) technology to count the number of axles on each vehicle and charge the appropriate toll.</p>

                                        </dd>
<div id="activeDiv" >
                                         </div></dl></fieldset>
                                         <fieldset><dl>
                                <dt><label for="year">Year:</label></dt>
                                        <dd>
                                                *<select id="year" name="year">
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
                                        <dd>*<input type="text" class="textfield" id="color" name="color" value="${tagRequestForm.color}" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly </c:if> /></dd>

                                <dt><label for="make">Make:</label></dt>
                                        <dd>
                                                *<select id="make" name="make">
                                                        <option value="${tagRequestForm.make}" >${tagRequestForm.make}</option>
                                                        <c:forEach var="make" items="${vehicleDelegate.vehicleMakes}">
                                                            <option value="${make.vehicleMake}">
                                                            ${make.vehicleMake}
                                                            </option>
                                                        </c:forEach>
                                                 </select>
                                        </dd>

                                <dt><label for="model">Model:</label></dt>
                                        <dd>
                                                *<input type="text" class="textfield" id="model" name="model" value="${tagRequestForm.model}" onblur="javascript:cleanUserField(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly </c:if> />
                                        </dd>

                                <dt><label for="nickname">Vehicle&rsquo;s nickname:</label></dt>
                                        <dd>
                                                &nbsp;<input type="text" class="textfield" id="nickname" name="nickname" value="${tagRequestForm.nickname}" onblur="javascript:removeUnwantedChar(this);" <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> readonly </c:if> />
                                                <p class="help">example: &ldquo;My car&rdquo;, &ldquo;Fleet number 214&rdquo;, and<br />please no special characters</p>
                                        </dd>


                        </dl>
                </fieldset>

                <br/>
                <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
                 <c:if test="${showDeactivateButton}">
                        <p align="center"><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/deactivate-ez-plate-vehicle.gif" value="inactivate" onclick="javascript:inactiveTag();return false;"/></p>
                    </c:if>
                 <br/>
                </c:if>

        </div> <!-- end of edit-area -->

</li> <!-- end of vehicle -->


			</ul>

			<ul class="form-actions">

			<li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="cancel" onclick="javascript:gotoDisplayVehicle();return false;"/></li>
                        <li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" alt="Save Changes"  onclick="javascript:doSubmit();return false;"/></li>

			</ul> <!-- end of form-action -->

		</form>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">


  // define reference to the hidden div element
var div;

// set reference to the div element after page is loaded
window.onload = function (){
  div = document.getElementById('activeDiv');
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
  //div.style.top  = (oTop) - 204 + 'px';
  //div.style.right = (oRight) + 2 + 'px';
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


<c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
var oneMinute = (60*1000);
var oneHour = Date.HOUR;
var oneDay = (oneHour*24);
var fortyFiveDays = 45;
var  now = new Date();
var startDate = null;
var endDate = null;
<c:choose>
<c:when test="${not tagRequestForm.allowEditPbpStart}">
var startNotEarlierThan = new Date(document.getElementById("startDateTime").value);
var startNotLaterThan = new Date(document.getElementById("startDateTime").value);
startNotLaterThan.setDate(startNotLaterThan.getDate() + fortyFiveDays-1);
var endNotLaterThan = new Date(document.getElementById("startDateTime").value);
var endNotEarlierThan = new Date(document.getElementById("startDateTime").value);
endNotLaterThan.setDate(endNotLaterThan.getDate() + fortyFiveDays);
</c:when>
<c:otherwise>
 var startNotEarlierThan = new Date();
var startNotLaterThan = new Date();
startNotLaterThan.setDate(startNotLaterThan.getDate() + fortyFiveDays-1);
var endNotLaterThan = new Date();
var endNotEarlierThan = new Date();
endNotLaterThan.setDate(endNotLaterThan.getDate() + fortyFiveDays);
</c:otherwise>
</c:choose>
var defaultEffectiveDays = 1;
var selectDaysIdxOffset = 1;
var dateFormatStr = "%m/%d/%Y %I:%M %p";
var curtDate = new Date();

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

function startDateTextChanged() {

    var msTilEndDate = null;
    var nbrDaysEffective = null;

    var dateStr = document.getElementById("startDateTime").value
    var changedTo = new Date(dateStr);
    if ( isNaN(changedTo.getDate()) ) {
      alert ("Date entered was invalid.  Please enter a valid date.");
      setStartDate(new Date(document.getElementById("startDateSaved").value));
    } else {
      if ( changedTo.getTime() < now.getTime() ) {
          alert ("Start date may not be earlier than current time");
          setStartDate(new Date(document.getElementById("startDateSaved").value));
      } else {
        if ( changedTo.getTime() > startNotLaterThan.getTime() ) {
          alert ("Start date may not be beyond 44 days from now.  Not later then " + startNotLaterThan);
          setStartDate(new Date(document.getElementById("startDateSaved").value));
        } else {
          if ( changedTo.getTime() < (endDate.getTime() + oneMinute)
            || changedTo.getTime() > endDate.getTime() ) {
            if((changedTo.getTime() > endDate.getTime()) || (changedTo.getTime() == (endDate.getTime()))){
                alert ("Effective days may not be less than Start date and time. End date adjusted.");
                setStartDate(changedTo);
                endDate.setDate(startDate.getDate());
                endDate.setTime(startDate.getTime() + oneMinute);
                setEndDate(endDate)
                msTilEndDate = endDate.getTime() - startDate.getTime();
                nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
                document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
            }else
            {
            setStartDate(changedTo);
            endDateTextChangedVal();
            msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
            }
          }else {
            setStartDate(changedTo);
            endDateTextChangedVal();
            msTilEndDate = endDate.getTime() - startDate.getTime();
            nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
            document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective-selectDaysIdxOffset);
          }
        }
      }
    }
  }

function updateEndDate() {
    var effectiveDaysSelect = document.getElementById("days-pbp-effective");
    var days = eval(effectiveDaysSelect.selectedIndex + selectDaysIdxOffset);
    var d = new Date(document.getElementById("startDateTime").value);
    d.setDate(d.getDate() + days);
    document.getElementById("endDateTime").value = getFormattedDate(d);
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
 function setStartDate(aDate) {
    startDate = aDate;
    document.getElementById("startDateTime").value = getFormattedDate(aDate);
    document.getElementById("startDateSaved").value = document.getElementById("startDateTime").value
  }

  function setEndDate(aDate) {
    endDate = aDate;
    document.getElementById("endDateTime").value = getFormattedDate(aDate);
    document.getElementById("endDateSaved").value = document.getElementById("endDateTime").value;
  }


function endDateTextChanged() {

    var dateStr = document.getElementById("endDateTime").value
    var changedTo = new Date(dateStr);
    if ( isNaN(changedTo.getDate()) ) {
      alert ("Date entered was invalid.  Please enter a valid date.");
      setEndDate(new Date(document.getElementById("endDateSaved").value));
    } else if ( (changedTo.getTime()+oneMinute) < (endNotEarlierThan.getTime()+oneMinute) ) {
        alert ("Cannot change end date to be earlier than start date and time.");
        setEndDate(new Date(document.getElementById("endDateSaved").value));
        var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
      } else if ( (changedTo.getTime()+oneMinute) < (curtDate.getTime()+oneMinute) ) {
         alert ("Cannot change end date to be earlier than Current date and time.");
        setEndDate(new Date(document.getElementById("endDateSaved").value));
        var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
      }else{
        if ( changedTo.getTime() > endNotLaterThan.getTime() ) {
          alert ("Cannot change end date beyond 45 days from now.  Not later then " + endNotLaterThan);
          setEndDate(new Date(document.getElementById("endDateSaved").value));
          var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
        }else {
          setEndDate(changedTo);
          var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          if(nbrDaysEffective <= 0)
          {
               alert("End date should be greater than Start date. End date adjusted.");
               endDate.setDate(startDate.getDate());
               endDate.setTime(startDate.getTime() + oneMinute);
               setEndDate(endDate);
               msTilEndDate = endDate.getTime() - startDate.getTime();
               nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          }
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
        }
      }
  }

  function endDateTextChangedVal() {

    var dateStr = document.getElementById("endDateTime").value
    var changedTo = new Date(dateStr);

      if ( (changedTo.getTime()+oneMinute) < (endNotEarlierThan.getTime()+oneMinute) ) {
        alert ("End date cannot be earlier than start date and time. End date adjusted");
         var e = new Date();
         e.setDate(eval(startDate.getDate() + 3));
         e.setTime(eval(startDate.getTime()));
         setEndDate(e);
        var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
      } else {
        if ( changedTo.getTime() > endNotLaterThan.getTime() ) {
          alert ("End date should not be beyond 45 days from now.  Not later then " + endNotLaterThan +". End date adjusted");
         var e = new Date();
         e.setDate(eval(startDate.getDate() + 3));
         e.setTime(eval(startDate.getTime()));
         setEndDate(e);
        }else {
          setEndDate(changedTo);
          var msTilEndDate = endDate.getTime() - startDate.getTime();
          var nbrDaysEffective = Math.ceil(msTilEndDate/oneDay);
          document.getElementById("days-pbp-effective").selectedIndex = nbrDaysEffective-selectDaysIdxOffset;
        }
    }
  }
</c:if>
var submitted = false;
s.events="event2";
<c:choose>
<c:when test="${alwaysShowPbp}">
    s.eVar2="EZ Account - Add EZ Plate";
</c:when>
<c:otherwise>
    <c:choose>
        <c:when test="${tagRequestForm.pbpTag}">
            s.eVar2="EZ Account - Edit EZ Tag";
        </c:when>
        <c:otherwise>
            s.eVar2="EZ Account - Edit EZ Plate";
        </c:otherwise>
    </c:choose>
</c:otherwise>
</c:choose>
document.tagRequestForm.licensePlate.focus();


    function gotoDisplayVehicle()
    {
        if (!submitted)
        {
            submitted = true;
            document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";
            document.tagRequestForm.submit();
        }
    }

    function toggleMotorcycle(enabled)
    {
        document.tagRequestForm.motorcycle.disabled = (enabled == 'y')?false:true;
        if (enabled != 'y')
            document.tagRequestForm.motorcycle.checked='';
    }

    function inactiveTag()
    {
        if (!submitted)
        {
            submitted = true;
            document.tagRequestForm.nickname.disabled=true;
            document.tagRequestForm.action = "${pageContext.request.contextPath}/accountEditVehicle.do?inactive=y";
            document.tagRequestForm.submit();
        }

    }

    function payViolations()
    {
        if (!submitted)
        {
            submitted = true;
            document.tagRequestForm.action="${pageContext.request.contextPath}/${violationDisplayAction}";
            document.tagRequestForm.submit();
        }
    }


    function validateDates()
    {
        <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
            var startDate = document.tagRequestForm.elements.pbpStart;
            var endDate = document.tagRequestForm.elements.pbpEnd;

            if (startDate.value != '')
            {
                if(!isDateTime(startDate.value))
                {
                    startDate.focus();
                    return false;
                }
                else if (compareDates(stringToDateTime(startDate.value),
                        new Date()) == -1)
                {
                    <c:if test="${alwaysShowPbp or tagRequestForm.allowEditPbpStart}">
                        alert("\"Start Date/Time\" cannot be a past date.");
                        startDate.focus();
                        return false;
                    </c:if>
                }

                if(endDate.value!='' && !isDateTime(endDate.value))
                {
                    endDate.focus();
                    return false;
                }
                else if (endDate.value != '' &&
                        compareDates(stringToDateTime(startDate.value), stringToDateTime(endDate.value)) == 1)
                {

                    alert("\"End Date/Time\" should be greater than \"Start Date/Time\".");
                    endDate.focus();
                    return false;
                }
                else if (endDate.value != '' &&
                        compareDates(stringToDateTime(endDate.value), new Date()) == -1)
                {
                        <c:if test="${alwaysShowPbp or tagRequestForm.allowEditPbpEnd}">
                            alert("\"End Date/Time\" cannot be a past date.");
                            endDate.focus();
                            return false;
                        </c:if>
                }

            }
        </c:if>

        return true;

    }

    function doSubmit()
    {
        if (!submitted)
        {
            //if (validateDates())
            //{
                submitted = true;
                <c:if test="${added}">
                  document.tagRequestForm.action="${pageContext.request.contextPath}/${saveAction}?added=true";
                </c:if>
                document.tagRequestForm.submit();
           // }
        }
    }

loadingBody();

function loadingBody(){
		getErrorfields();
                <c:if test="${tagRequestForm.pbpTag or alwaysShowPbp}">
                startDate = new Date(document.getElementById("startDateTime").value);
                endDate  = new Date(document.getElementById("endDateTime").value);
                setStartDate(startDate);
                setEndDate(endDate);
                 var msTilEndDate1 = endDate.getTime() - startDate.getTime();
                 var nbrDaysEffective1 = Math.ceil(msTilEndDate1/oneDay);
                 document.getElementById("days-pbp-effective").selectedIndex = eval(nbrDaysEffective1-selectDaysIdxOffset);
                 </c:if>
	}

function checkForErrors(fieldname){
   var isErrorField = false;
     if(fieldname != null && fieldname != "" && fieldname.length != 0){
        isErrorField = true;
       }
   return isErrorField;
}

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
       if(typeof feild == "[object]"){
	  field.style.background="#FFFFFF";
          }
}

function getErrorfields(){

var state = '<html:errors property="state"/>';
var licensePlate = '<html:errors property="licensePlate"/>';
var pbpStart ='<html:errors property="pbpStart"/>';
var pbpEnd = '<html:errors property="pbpEnd" />';
var color= '<html:errors property="color"/>';
var make = '<html:errors property="make"/>';
var model = '<html:errors property="model"/>';
var tempLicense = '<html:errors property="tempLicense"/>';


var tagForm = document.tagRequestForm;

         if (checkForErrors(state) == true){
		changeTextFieldColor(tagForm.state);
	 }else{
		 changeTextFieldWhite(tagForm.state);
	 }
         if (checkForErrors(licensePlate) == true){
		changeTextFieldColor(tagForm.licensePlate);
	 }else{
		 changeTextFieldWhite(tagForm.licensePlate);
	 }
          if (checkForErrors(tempLicense) == true){
		changeTextFieldColor(tagForm.licensePlate);
	 }else{
		 changeTextFieldWhite(tagForm.licensePlate);
	 }
          if (checkForErrors(pbpStart) == true){
		changeTextFieldColor(tagForm.pbpStart);
	 }else{
		 changeTextFieldWhite(tagForm.pbpStart);
	 }
         if (checkForErrors(pbpEnd) == true){
		changeTextFieldColor(tagForm.pbpEnd);
	 }else{
		 changeTextFieldWhite(tagForm.pbpEnd);
	 }
         if (checkForErrors(color) == true){
		changeTextFieldColor(tagForm.color);
	 }else{
		 changeTextFieldWhite(tagForm.color);
	 }
         if (checkForErrors(make) == true){
		changeTextFieldColor(tagForm.make);
	 }else{
		 changeTextFieldWhite(tagForm.make);
	 }
          if (checkForErrors(model) == true){
		changeTextFieldColor(tagForm.model);
	 }else{
		 changeTextFieldWhite(tagForm.model);
	 }


}

 function axleRate() {
    window.open("https://www.hctra.org/files/Legend.pdf");
    return true;
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

