<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>tabLayout</title>
        <LINK href="${pageContext.request.contextPath}/css/logged-in.css" rel=stylesheet type="text/css" >
	<LINK href="${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.loggedIn"/>" rel=stylesheet type="text/css" >		
        <style type="text/css">
	* {  margin: 0; padding: 0; border: 0; }
	body { background: #f1ebdd;  font: 12px/16px Arial, sans-serif; color: #35322e; }
	h2 { font: bold 16px/20px Arial, sans-serif; color: #5a7f96; padding: 0 0 5px 0; }
	p { padding: 0 0 5px 0; }
	a:link { color: #0d3853; }
	a:visited { color: #0d3853; }
	a:hover { color: #df2d04; }
	a:active {  color: #df2d04; }
	ul { margin-left: 0; float: left; }
	li {  list-style-type: disc; padding-left:0; margin-left: 20px;  }
	</style>
  </head>
  <body>
    <div="center">
        <table width="80%" border="0" cellspacing="0">
            <tr><td>
              <tiles:get name="tabContent"/>        
            </td></tr>
        </table>
    </div>
  </body>
</html>