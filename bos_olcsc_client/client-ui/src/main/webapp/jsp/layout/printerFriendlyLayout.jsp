<%-- 
  - Author(s): Noel Ternida
  - Date: April 26, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Layout for printing a page.
  --%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>


<html>
<head>
	<title>NTTA Customer Service Center</title>
	<style type="text/css">	
		@import url(${pageContext.request.contextPath}/css/printerFriendly.css);
	</style>
	
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript">
        </script>
</head>

<body>
     <div style="text-align:center"> 
        <br/>
        <input name="print" type="button" class="button" value="Print" onclick="javascript: window.print()"/>&nbsp;&nbsp;
        <input name="close" type="button" class="button" value="Close Window" onclick="javascript: window.close()"/>
        <hr/>
        <br/>
        <table cellspacing="0">
            <tiles:get name="content"/>        
        </table>
        
    </div>

</body>
</html>
