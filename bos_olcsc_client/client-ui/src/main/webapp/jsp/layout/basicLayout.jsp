<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ include file="/jsp/common/Taglibs.jsp"%>
<%@ page errorPage="/jsp/common/commonError.jsp"%>

<tiles:useAttribute name="bodyClass" id="bodyClass" classname="java.lang.String" />
<tiles:useAttribute name="backAllowed" id="backAllowed" classname="java.lang.String" />
<tiles:useAttribute name="title" id="title" classname="java.lang.String" />
<tiles:useAttribute name="disableenter" id="disableenter" classname="java.lang.String" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>${title}${empty title ? "" : " - "}Harris County Toll Road Authority</title>
<jsp:include page="/jsp/includes/common/head.jsp" />
<script>
function backButtonOverride(){
  // Work around a Safari bug
  // that sometimes produces a blank page
  setTimeout("backButtonOverrideBody()", 1);

}

function backButtonOverrideBody()
{
  // Works if we backed up to get here
  try {
    history.forward();
  } catch (e) {
    // OK to ignore
  }
  // Every tenth-second, try again. The only
  // guaranteed method for Opera, Firefox,
  // and Safari, which don't always call
  // onLoad but *do* resume any timers when
  // returning to a page
  setTimeout("backButtonOverrideBody()", 50);
}

function showLoading() {
/*
    document.getElementById("loading").style.display='';
*/
}
function disableEnterKey()
{
   var key;

   if(window.event){
     key = window.event.keyCode; //IE
   if(key == 13){
     window.event.keyCode = 0;}
   }else {
     key = e.which; //firefox
   if(key == 13){
     e.which = 0;}
   }
}
</script>


</head>

<body id="www-hctra-com" class="${bodyClass}"
    <c:if test="${backAllowed!='true'}">
    onLoad="javascript:backButtonOverride();"
</c:if>
    <c:if test="${disableenter!='true'}">
    onKeyPress="javascript:disableEnterKey();"
</c:if>
>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
  var pjquery = jQuery.noConflict();//pjquery will represent jquery framework now on-wards
</script> 

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/offspring.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/prototype.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/domready-onload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/scriptaculous.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/unFocusHistory-p.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/event-selectors.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/validation.js"></script>

<%--
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/builder.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/controls.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/dragdrop.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/effects.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/loading-message.js"></script>
--%>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/table-sorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/account-section.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/s_code.js"></script>


<div id="page">
	<tiles:get name="content" />
	<tiles:get name="pageNavigation" />
	<tiles:get name="commonNavigation" />
	<tiles:get name="footer" />
</div>
<!-- end of page -->

<div id="loading" style="display: none;">
<p>Please wait while the page loads&hellip;</p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/omniture_common.js"></script>
</body>
</html>
