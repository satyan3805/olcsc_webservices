<%@ include file="/jsp/common/Taglibs.jsp" %>

<%-- 
  - Author(s): Adapted from HNTB
  - Date: March 30, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Home page layout.
  --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
<html>
<head>
	<title>NTTA <fmt:message key="label.customerServiceCenter"/></title>
        
	<style type="text/css">
		@import url(${pageContext.request.contextPath}/css/home.css);	
                @import url(${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.home"/>);
	</style>
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <script type="text/javascript">
        if(window.history.forward(1) != null)
            window.history.forward(1);
        </script>
</head>

<body>


        <!-- begin main table -->
        <table id="main" cellspacing="0">
            <tr>
            
                    <!-- begin left column (logo, account login, VeriSign logo) -->
                    <td id="left">
                            <div id="ntta-home"><a href="http://www.ntta.org" title="NTTA Home"><fmt:message key="label.home.ntta"/></a></div>
                            <div id="title-csc"><h1><fmt:message key="label.customerServiceCenter"/></h1></div>
            
                            <div style="padding-top: 5px; padding-left: 10px;">
                                <a href="${pageContext.request.contextPath}/home/updateLanguage.do"><img alt="" src="${pageContext.request.contextPath}<bean:message key="images.languageButton"/>"/></a>
                            </div>
                            
                            <!-- begin user login section-->
                            <div id="login">
                               <tiles:get name="accountLogin"/>        
                            </div>
                            
                            <!--VeriSign "Verify" button. Use smallest logo available (approx. -->
                            <!--<div id="verisign">
                            <a href="#" onclick="javascript: openWin('https://seal.verisign.com/splash?form_file=fdf/splash.fdf&dn=CSC.NTTA.ORG',550,500); return false;"><img src="${pageContext.request.contextPath}/images/common/logos/verisign.gif" width="98" height="59" alt="VeriSign"></a></div>
                            -->
                            <br/>
                            <br/>
                            <p>
                            <script src="https://seal.verisign.com/getseal?host_name=csc.ntta.org&size=M&use_flash=YES&use_transparent=YES&lang=en"></script>
                            </p>

                    </td>
                    <!-- end left column -->
                    <td id="middle">
                        <!-- begin middle column (splash, tabs, content, violation login) -->
                        <table id="body" cellspacing="0">
                            
                            <!-- splash image -->
                            <tr>
				    <td id="splash"><iframe id="splash-iframe" src="<etcc-extended:Translation property="splashPageUrl"/>" frameborder="0" scrolling="no"></iframe></td>
                            </tr>		 	
                            
                            <!-- begin tab navigation -->
                            <tr>		
                                <td id="tabs">
                                    <ul>
                                        <li id="tab-left"></li>
                                       <tiles:get name="tabMenu"/>        
                                    </ul>
                                </td>			
                            </tr>
                            <!-- end tab navigation -->
                            
                            <!--begin main content -->				
                            <tr>
                                    <td id="content">
				    
<%--				    <iframe id="content-container" src="${pageContext.request.contextPath}<tiles:getAsString name="tabContent"/>" frameborder="0" scrolling="no"></iframe>--%>
<%--				    <iframe id="content-container" src="${pageContext.request.contextPath}/jsp/home/tabLayout.jsp?page=<tiles:getAsString name='tabContent'/>" frameborder="0" scrolling="no"></iframe>--%>

				    <iframe id="content-container" name="tabFrame" src="${pageContext.request.contextPath}<tiles:getAsString name="tabContent"/>" frameborder="0"></iframe>
				    
<%--                                    <div id="content-container">
                                       <tiles:get name="tabContent"/>        
                                    </div>
--%>                                    
                                    
                                        <!-- begin Violations login -->				
<%--                                        <div id="violations">
                                           <tiles:get name="violatorLogin"/>
                                        </div>
--%>                                        
                                        <!-- end Violations login -->	
                                    
                                    </td>			
                            </tr>
                            <!--end main content -->				

                            <tr>
                                    <td id="footer"><strong><fmt:message key="label.customerService"/>: <etcc-extended:Translation property="contactPhoneNumber"/></strong>  |  <a href="#" onclick="openWin('<etcc-extended:Translation property="privacyPolicyUrl"/>',800,500);"><fmt:message key="label.privacyPolicy"/></a>  |  <a href="#" onclick="openWin('<etcc-extended:Translation property="supportedBrowserUrl"/>',800,500);"><fmt:message key="label.supportedBrowser"/></a></td>
                            </tr>
                        </table>	
                                                <!-- end middle column -->
                    </td>
                    
                    <!-- begin right shadow -->
                    <td id="right">	
                        <table id="shadow-right" cellspacing="0">
                            <tr>
                                    <td id="shadow-righttop"></td>
                            </tr>
                            <tr>
                                    <td id="shadow-rightbottom"></td>
                            </tr>
                        </table>
                    </td> 
                    <!-- end right shadow -->
            </tr>
            <tr>
                    <td id="shadow-bottom" colspan="3"><div id="shadow-bottomright"></div></td>
            </tr> 
        </table>


</body>
</html>
