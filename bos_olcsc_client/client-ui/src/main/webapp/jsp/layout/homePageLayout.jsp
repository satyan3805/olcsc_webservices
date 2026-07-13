<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@page import="com.etcc.csc.util.SessionUtil"%>
<%SessionUtil.setCurrentTabMenuId(session,0);%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>Harris County Toll Road Authority (HCTRA)</title>

	<%@ include file="/jsp/includes/common/head.jsp" %>

</head>

<body id="www-hctra-com" class="home">

<div id="page">

	       
        <jsp:include page="/jsp/includes/home/home.jsp" />
        <tiles:get name="pageNavigation"/>   
        <tiles:get name="commonNavigation"/>
        <jsp:include page="/jsp/includes/common/footer.jsp" />

</div>  <!-- end of page -->

</body>
</html>