<%@ page import="com.etcc.csc.presentation.form.TagRequestForm" %>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<% 
if(request.getAttribute("tagRequestFormRequest")!= null){
TagRequestForm reqform =(TagRequestForm) request.getAttribute("tagRequestFormRequest");
%>
<c:set  var ="tagRequestForm" value="<%=reqform%>"/>
<%}%>

<div id="content-container">

	<div id="content">

        <h1 id="manage-your-account">Make Payment using my EZ Account</h1>

<p>The vehicle with outstanding violations is not present on your account.  Please add it now to continue.</p>

              <form name="tagRequestForm" action="${pageContext.request.contextPath}/violationAddVehicle.do" method="post">
                <input type="hidden" name="totalAmount" value="${tagRequestForm.totalAmount}"/>
                <input type="hidden" name="tagSaleAmount" value="${tagRequestForm.tagSaleAmount}"/>
                <input type="hidden" name="depositAmount" value="${tagRequestForm.depositAmount}"/>
                <input type="hidden" name="retailTransId" value="${tagRequestForm.retailTransId}"/>
                <input type="hidden" name="page" value="violationAddVehicle"/>

              <tiles:get name="vehicleDetail"/> 

                <ul class="form-actions">
                  <li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Add Vehicle" onclick="return doSubmit();" /></li>
                </ul> <!-- end of form-actions -->
              </form>

	</div> <!-- end of content -->

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->


<script type="text/javascript">
loadingBody();
var submitted = false;

function doSubmit() {
        var tagForm = document.tagRequestForm;
        var licPlate = tagForm.licensePlate.value;
        var color = tagForm.color.value;
        var make = tagForm.make.value; 
        var year = tagForm.year.value;
        var model = tagForm.model.value;
        if (modified && (licPlate != '' || color != '' || make != '' || year != '' || model != ''))
        {
            if (!submitted)
            {
                submitted = true;
                document.tagRequestForm.submit();
            }
        }
// TODO: generate error messages for user to know why it failed!!!
return true;
        //return false;
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

function loadingBody() {
  getErrorfields();
  checkforDefault();
  document.getElementById("EztagType-tr").style.display = 'none';
  //document.getElementById("license-plate-state").setAttribute('disabled','disabled');
  //document.getElementById("license-number").setAttribute('disabled','disabled');
  document.getElementById("license-plate-state").disabled = true;
  document.getElementById("license-number").disabled = true;
  var tempPlateSection = document.getElementById("temporary-license-plate-section");
  //tempPlateSection.disabled = true;
  tempPlateSection.style.display = 'none';
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
  
function getErrorfields() {
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
