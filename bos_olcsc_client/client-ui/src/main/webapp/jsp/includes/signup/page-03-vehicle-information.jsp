<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<c:if test="${(requestScope.fromConfirmation) or (tagRequestForm.fromConfirmation)}">
  <c:set var="fromConfirmationVal" value="true"/>
</c:if>
<div id="content-container">

<div id="content">

<etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/signupVehicleInfo.do"> 
<input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>
<input type="hidden" name="page" value="signup"/>

<tiles:get name="vehicleDetail"/> 



<ul class="form-actions">
<c:if test="${not fromConfirmationVal}">
  <li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel" alt="Cancel do not save changes" onclick="javascript:return cancelEdit();"/></li>
</c:if>
<li><input type="image" value="Add Billing Information" src=
           <c:choose>
  <c:when test="${fromConfirmationVal}">
    "${pageContext.request.contextPath}/meta/media/buttons/continue.gif" 
  </c:when>
  <c:otherwise>
    "${pageContext.request.contextPath}/meta/media/buttons/continue.gif"
  </c:otherwise>
  </c:choose>
title="Continue To Add Billing Information" alt="Continue To Add Billing Information" onclick="javascript: return gotoPayment();"/></li>

</ul> <!-- end of form-action -->

</etcc-extended:autocompleteOffForm>

</div> <!-- end of content -->

<p class="progress-bar" id="step-3-of-6">
  New EZ TAG Account
  <em>Step 3 of 6</em>
</p>

</div> <!-- end of content-container -->

<script type="text/javascript">
  var submitted = false;
  document.tagRequestForm.licensePlate.focus();
  //    function saveVehicle()
  //    {
  ////        if (document.tagRequestForm.tempLicense.value == 'true')
  ////        {
  ////            document.tagRequestForm.licensePlate.value = 'TEMP';
  ////        }
  ////        alert(document.tagRequestForm.licensePlate.value);
  //        document.tagRequestForm.action="${pageContext.request.contextPath}/signupSaveVehicle.do";
  //        document.tagRequestForm.submit();
  //    }

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
      document.tagRequestForm.action = "${pageContext.request.contextPath}/signupSaveVehicleAndContinue.do?pbp="+document.getElementById('pbp').value;
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
        document.tagRequestForm.action = "${pageContext.request.contextPath}/signupVehicleInfo.do";
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
       <c:if test="${(empty tagRequestForm.savedVehicles) or (flag)}">
            alert('No vehicle has been added to your account. Please enter vehicle information.');
            return false;
        </c:if>
        
      if (modified)
    {
      alert('Would you please click on the "Save Vehicle Information" to save the changes to your account before proceeding to "Add Billing Information"?');
      return false;
    }
        
    return true;
  }
    
  function payViolations()
  {
    /*document.tagRequestForm.action="${pageContext.request.contextPath}/violatorLoginDisplay.do?returnAction=signupVehicleInfoDisplay";
    if (!submitted)
    {
      submitted = true;
      document.tagRequestForm.submit();
    }*/
    var URL = "${pageContext.request.contextPath}/violatorLoginDisplay.do";
    day = new Date();
    id = day.getTime();
    eval("page" + id + " = window.open(URL, '" + id + "', '');");
        
        
  }
    
  function cancelEdit()
  {
    document.tagRequestForm.reset();
    modified = false;
    document.tagRequestForm.vehicleIndexToModify.value = -1;
    document.tagRequestForm.licensePlate.value = "";
    document.tagRequestForm.color.value="";
    document.tagRequestForm.make.value="";
    document.tagRequestForm.year.value="";
    document.tagRequestForm.model.value="";
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
         if ((checkForErrors(licensePlate) == true) || (checkForErrors(tempLicense) == true)){
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
