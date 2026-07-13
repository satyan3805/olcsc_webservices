<%-- 
  - Author(s): Adapted from HNTB
  - Date: March 27, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Layout of the screens for the logged in user.
  --%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>

<%@ include file="/jsp/common/modalInclude.jsp" %>
<tiles:useAttribute name="titleKey" id="titleKey" classname="java.lang.String"/>

<html>
<head>
                <%
            response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
            response.setHeader("Pragma","no-cache"); //HTTP 1.0
            response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
            %>
<%--        <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/index.jsp"/>--%>
	<title>NTTA <fmt:message key="label.customerServiceCenter"/></title>
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
	<style type="text/css">	
		@import url(${pageContext.request.contextPath}/css/logged-in.css);
                @import url(${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.loggedIn"/>);
		@import url(${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.menu"/>);
	</style>
	
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript">
            var contractsymbol='${pageContext.request.contextPath}/images/common/buttons/minus.gif' //Path to image to represent contract state.
            var expandsymbol='${pageContext.request.contextPath}/images/common/buttons/plus.gif' //Path to image to represent expand state.
        </script>            
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/switch_content.js"></script>
    <style type="text/css">
    
    .showstate{ /*Definition for state toggling image */
    cursor:hand;
    cursor:pointer;
    float: right;
    }
    
    .headers{
            background-color: #F8F4ED;
            padding-top: 4px;
            padding-bottom: 4px;
            padding-left: 5px;
            padding-right: 5px;
    }
    
    .switchcontent{
            width: auto;
    }
    .option {  padding-left: 40px; margin-top: 10px; background: url(${pageContext.request.contextPath}/images/common/step/arrow-blue.gif) no-repeat 0px 5px ; }
    hr { border-bottom: 1px solid #e6d5c9; height: 1px; margin: 0 0 10px 40px; }
    /* Next and Previous buttons (with green arrow) */
    a.next { display: block; float: right; height: 30px; font-weight: bold; line-height: 30px; padding: 0 30px 0 0; background: url(${pageContext.request.contextPath}/images/common/step/step-next.gif) no-repeat right top;  }
    a.prev { display: block; float: left; height: 30px; font-weight: bold; line-height: 30px; padding: 0 0 0 30px; background: url(${pageContext.request.contextPath}/images/common/step/step-prev.gif) no-repeat left top; }
    
    .showstate1 {cursor:hand;
    cursor:pointer;
    float: right;
    }
    </style>
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/cookie.js" ></script>
	<script type="text/javascript">
                var readFontSize = true;
                function fontsize(size)
                {
                    if (size != null && size.length>0)
                        size = "-"+size;
              
                    changeFont(size);
                    createCookie("fontSize", size);           
            
                }
                
                function changeFont(size)
                {
                    var styleName = "content"+size;
                    var table = document.getElementById("data-table");
                    var tds = table.getElementsByTagName("td");
                    
                    for (var i=0; i<tds.length;i++)
                    {
                            var tdClassName = tds[i].className;
                            if (tdClassName != null && tdClassName.indexOf("content")==0)
                                    tds[i].className = styleName;
                    }
            
                    styleName = "text"+size;
            
                    var inputs = table.getElementsByTagName("input");
                    for (var i=0; i<inputs.length;i++)
                    {
                            var inputClassName = inputs[i].className;
                            if (inputClassName != null && inputClassName.indexOf("text")==0)
                                    inputs[i].className = styleName;
                    }
                    
                    var selects = table.getElementsByTagName("select");
                    for (var i=0; i<selects.length;i++)
                    {
                            var selectClassName = selects[i].className;
                            if (selectClassName != null && selectClassName.indexOf("text")==0)
                                    selects[i].className = styleName;
                    }

                }
                
                function updateLang(){
                    location.href="${pageContext.request.contextPath}/updateLanguage.do";
                }
                function confirmUpdateLang(){
                    confirmOKCancel('','<fmt:message key="label.updateLang.note1"/>','<fmt:message key="label.updateLang.header"/>','<fmt:message key="label.updateLang.OK"/>','<fmt:message key="label.updateLang.CANCEL"/>', updateLang);   
                }
        
    </script>
    
</head>

<body>

<!-- begin main table -->
<table id="main" cellspacing="0">
	

<tr>	
   
<!-- end left navigation -->
	
<!-- begin content -->
    <td id="content">

	<!-- begin actual content -->
	<table cellspacing="0" id="data-table" width="760">
	<tr>	
	
		<!-- begin font-sizer. This will allow users to increase or decrease the font-size of the content 
		by using JavaScript to load stylesheets that change the font-size only. -->
		<td colspan="4" id="font-sizer">
                    <c:if test="${titleKey != null && titleKey != ''}">
			<h1 id="page-title">
                            <bean:message key="${titleKey}"/>
                        </h1>		
                    </c:if>			
		</td>
		<!-- end font-sizer -->
	</tr>	
            <tiles:get name="content"/>        

	</table>
    </td>
</tr>
<!-- end content -->


</table>
<!-- end main table -->

</body>
</html>

<script language="javascript">
var fontSize = readCookie("fontSize");
if (fontSize!=null && readFontSize)
    changeFont(fontSize);
    
</script>