<%@ page import="com.etcc.csc.presentation.form.TagRequestForm" %>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<% 
if(request.getAttribute("tagRequestFormRequest")!= null){
TagRequestForm reqform =(TagRequestForm) request.getAttribute("tagRequestFormRequest");
%>
<c:set  var ="tagRequestForm" value="<%=reqform%>"/>
<%}%>

<c:if test="${(requestScope.fromConfirmation) or (tagRequestForm.fromConfirmation)}">
    <c:set var="fromConfirmationVal" value="true"/>
</c:if>

<c:if test="${(requestScope.activePbpTagExists) or (tagRequestForm.activePbpTagExists)}">
    <c:set var="activePbpTagExistsVal" value="true" scope="request"/>
</c:if>

<div id="content-container">

	<div id="content">

                <etcc-extended:autocompleteOffForm action="/accountSaveVehicle.do" method="post">
              <!-- <form name="tagRequestForm" action="${pageContext.request.contextPath}/accountSaveVehicle.do" method="post">-->
                    <input type="hidden" name="totalAmount" value="${tagRequestForm.totalAmount}"/>
                    <input type="hidden" name="tagSaleAmount" value="${tagRequestForm.tagSaleAmount}"/>
                    <input type="hidden" name="depositAmount" value="${tagRequestForm.depositAmount}"/>
                    <input type="hidden" name="retailTransId" value="${tagRequestForm.retailTransId}"/>
                    <input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>
                    <input type="hidden" name="activePbpTagExists" value="${activePbpTagExistsVal}"/>
                    <input type="hidden" name="page" value="manage"/>

		<!--<h1 id="vehicles-and-ez-tags">Vehicles &amp; EZ TAGs</h1>-->

                        <tiles:get name="vehicleDetail"/> 

			<ul class="form-actions">
                           <c:choose>
                            <c:when test="${(fromConfirmationVal)}">
                                <li><input id="save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel" onclick="javascript:cancelContinue();" /></li>
                            </c:when>
                            <c:otherwise>
                                <li><input id="save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel" onclick="javascript:cancelAddTags();" /></li>
                            </c:otherwise>
                        </c:choose>
                                <li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="continue" onclick="javascript:return gotoPayment();return false;" /></li>
			</ul> <!-- end of form-action -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Add EZ Tag";
document.tagRequestForm.licensePlate.focus();
var submitted = false;

function gotoPayment()
    {
        var tagForm = document.tagRequestForm;
        var licPlate = tagForm.licensePlate.value;
        var color = tagForm.color.value;
        var make = tagForm.make.value; 
        var year = tagForm.year.value;
        var model = tagForm.model.value;
        if (modified && (licPlate != '' || color != '' || make != '' || year != '' || model != ''))
        {
            document.tagRequestForm.action = "${pageContext.request.contextPath}/accountSaveVehicleAndContinue.do?veh=y&showContinueMsg=1";
            if (!submitted)
            {
                submitted = true;
                document.tagRequestForm.submit();
            }
            return false;
        }
        else
        {
        
            if (checkVehicleList())
            {
                document.tagRequestForm.action = "${pageContext.request.contextPath}/addTagPaymentDisplay.do?veh=y";
                if (!submitted)
                {
                    submitted = true;
                    document.tagRequestForm.submit();
                }
                return false;
            }
            else
                return false;
        }
    }
    
    function checkVehicleList()
    {
        <c:if test="${empty tagRequestForm.savedVehicles}">
            alert("No vehicle has been added to your account.");
            return false;
        </c:if>
        return true;
    }
    
    function cancelAddTags()
    {
        document.tagRequestForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";
        document.tagRequestForm.submit();
    }
    
    function payViolations()
    {
        document.tagRequestForm.action="${pageContext.request.contextPath}/violationDisplayFromAddTags.do";
        document.tagRequestForm.submit();
    }
     function cancelContinue()
    {
      if (checkVehicleList())
            {
                document.tagRequestForm.action = "${pageContext.request.contextPath}/addTagPaymentDisplay.do?veh=y";
                if (!submitted)
                {
                    submitted = true;
                    document.tagRequestForm.submit();
                }
                return false;
            }
            else
                return false;
    }
    
          
loadingBody();
  
function loadingBody(){
		getErrorfields();                
		checkforDefault();
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
	field.style.background="#FFFFFF";
}
  
function getErrorfields(){

var state = '<html:errors property="state"/>';
var licensePlate = '<html:errors property="licensePlate"/>';
var pbpStart ='<html:errors property="pbpStart"/>';
var pbpEnd = '<html:errors property="pbpEnd" />';
var color= '<html:errors property="color"/>';
var year= '<html:errors property="year"/>';
var make = '<html:errors property="make"/>';
var model = '<html:errors property="model"/>';
var tempLicense = '<html:errors property="tempLicense"/>';
var fousfield = true;
 
var tagForm = document.tagRequestForm;

         if (checkForErrors(state) == true){
		changeTextFieldColor(tagForm.state);
                if(fousfield){
                tagForm.state.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.state);
	 }
         if (checkForErrors(licensePlate) == true){
		changeTextFieldColor(tagForm.licensePlate);
                 if(fousfield){
                tagForm.licensePlate.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.licensePlate);
	 }
          if (checkForErrors(pbpStart) == true){
		changeTextFieldColor(tagForm.pbpStart);
                 if(fousfield){
                tagForm.pbpStart.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.pbpStart);
	 }
         if (checkForErrors(pbpEnd) == true){
		changeTextFieldColor(tagForm.pbpEnd);
                 if(fousfield){
                tagForm.pbpEnd.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.pbpEnd);                 
	 } 
         if (checkForErrors(year) == true){
		changeTextFieldColor(tagForm.year);
                if(fousfield){
                tagForm.year.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.year);
	 }
         if (checkForErrors(color) == true){
		changeTextFieldColor(tagForm.color);
                if(fousfield){
                tagForm.color.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.color);
	 }
         if (checkForErrors(make) == true){
		changeTextFieldColor(tagForm.make);
                if(fousfield){
                tagForm.make.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.make);
	 }
          if (checkForErrors(model) == true){
		changeTextFieldColor(tagForm.model);
                if(fousfield){
                tagForm.model.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(tagForm.model);
	 }         
         
 
}

</script>


