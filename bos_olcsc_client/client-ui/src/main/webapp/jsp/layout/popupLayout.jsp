<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ page import="com.etcc.csc.util.SessionUtil"%>
<tiles:useAttribute name="bodyClass"  id="bodyClass" classname="java.lang.String"/>
<tiles:useAttribute name="title" id="title" classname="java.lang.String"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>${title}Harris County Toll Road Authority</title>
        
     
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/defaults.css" media="all" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/typography.css" media="all" />

<!-- Pocket IE properly ignores the media type "screen" if it's capitalized:
	http://urlgreyhot.com/personal/weblog/css_for_windows_mobile_pocket_pc_internet_explorer -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/colors.css" media="handheld,projection" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/layout.css" media="Screen,projection,print" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/handheld.css" media="handheld" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/print.css" media="Screen,print" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/datepicker.css" media="Screen,projection,print" />


<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/offspring.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/prototype.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/domready-onload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/scriptaculous.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/unFocusHistory-p.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/event-selectors.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/account-section.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/table-sorting.js"></script>

<%--
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/loading-message.js"></script>
--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/validation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/s_code.js"></script>

<script type="text/javascript">
function showLoading() {
/*
    //document.getElementById("page").style.display = 'none';
    document.getElementById("loading").style.display='';
*/
}
</script>
</head>

<c:set var="acctMgmtTabMenuId">
    <%=SessionUtil.getCurrentAcctMgmtTabMenuId(session)%>
</c:set>

<html:form action="/updateAcctMgmtTabMenu">
    <html:hidden property="url"/>
    <html:hidden property="menu"/>
</html:form>

<body id="www-hctra-com" class="${bodyClass}">
<div id="page">
         
    <tiles:get name="content"/> 
    <tiles:get name="footer"/>        
</div>  <!-- end of page -->

<div id="loading" style="display:none;">
    <p>Please wait while the page loads&hellip;</p>
</div>

</body>
</html>
