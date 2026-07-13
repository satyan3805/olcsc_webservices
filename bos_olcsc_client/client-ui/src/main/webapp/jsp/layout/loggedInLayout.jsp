<%-- 
  - Author(s): Adapted from HNTB
  - Date: March 27, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Layout of the screens for the logged in user.
  --%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>


<tiles:useAttribute name="titleKey" id="titleKey" classname="java.lang.String"/>

<html>
<head>
<%--        <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/index.jsp"/>--%>
	<title>NTTA Customer Service Center</title>
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
	<style type="text/css">	
		@import url(${pageContext.request.contextPath}/css/logged-in.css);
		@import url(${pageContext.request.contextPath}/css/menu.css);
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
    </script>
    
</head>

<body>

<!-- begin main table -->
<table id="main" cellspacing="0">
<tr>
	<td id="title">
		<!-- CSC logo -->
		<div id="title-csc"><a href="#" title="NTTA Customer Service Center home">Customer Service Center</a></div>
	</td>
	<td id="header">
		<!-- welcome -->
		<div id="welcome">
			 <span>You are now logged in.</span>
			 <a href="${pageContext.request.contextPath}/logout.do" id="logout">Log out</a>
		</div>
		<a href="#" id="help" onclick="openWin('${appDelegate.helpUrl}',800,500);">Help</a>
		<br class="clear">
		<!-- help -->

		<!-- begin account info -->
               <tiles:get name="header"/>        
		<!-- end account info -->			
		
	</td>

	<td id="shadow-right" rowspan="7">
		<div id="wrapper">
			<div id="shadow-righttop"></div>
			<div id="shadow-rightbottom"></div>
		</div>
	</td>
	</tr>

<tr>
	<td></td>
	<td id="tabs">
               <tiles:get name="tabMenu"/>        
	</td>
</tr>

<tr>
	<td id="nav-top"></td>
	<td id="content-top"></td>
</tr>
   <!-- begin left navigation -->

<tr>	
    <td id="nav">
       <tiles:get name="menu"/>        
    </td>
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
			<div>
				<span><strong>font-size:</strong></span>
				<a href="#" id="font-small" onClick="fontsize('small');"></a>
				<a href="#" id="font-medium" onClick="fontsize('');"></a>
				<a href="#" id="font-large" onClick="fontsize('large');"></a>		
			</div>
		</td>
		<!-- end font-sizer -->
	</tr>	
            <tiles:get name="content"/>        
<%--        
	<tr>
		<td class="topleft"></td>
		<td class="topcenter"></td>
		<td class="topright"></td>
	</tr>
	<tr id="content-top">
		<td class="left"></td>
		<td class="content"></td>		
		<td class="right"></td>		
	</tr>

	<tr>
		<td class="left"></td>
		<!-- begin options (buttons, pulldowns, etc.) -->
	<!-- end options -->	

	<!-- begin tabular data -->
<!--		<td class="content">-->
                    <tiles:get name="content"/>
<!--                </td>-->
		<!-- end tabular data-->
		<td class="right"></td>
	</tr>	
	<tr id="content-bottom">
		<td class="left"></td>
		<td class="content"></td>		
		<td class="right"></td>		
	</tr>
		
	<tr>
		<td class="bottomleft"></td>
		<td class="bottomcenter"></td>
		<td class="bottomright"></td>
	</tr>
--%>
	</table>
    </td>
</tr>
<!-- end content -->

<tr>
	<td id="nav-bottom"></td>
	<td id="content-bottom"></td>
</tr>

<!-- begin footer -->
<tr>
	<td id="footer" colspan="2">
		<ul>
			<li class="first">Customer Service: <strong>${appDelegate.contactPhoneNumber}</strong></li>
			<li><a href="#" onclick="openWin('${appDelegate.privacyPolicyUrl}',800,500);">Privacy Policy</a></li>
			<li><a href="#" onclick="openWin('${appDelegate.supportedBrowserUrl}',800,500);">Supported Browsers</a></li>			
		</ul>		
	</td>
</tr>
<!-- end footer -->

<tr>
	<td id="shadow-bottom" colspan="4"><div id="shadow-bottomright"></div></td>
</tr>
</table>
<!-- end main table -->

</body>
</html>

<script language="javascript">
var fontSize = readCookie("fontSize");
if (fontSize!=null && readFontSize)
    changeFont(fontSize);
    
</script>