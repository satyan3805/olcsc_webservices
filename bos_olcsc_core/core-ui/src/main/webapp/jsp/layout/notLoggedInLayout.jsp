<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>
<%@ taglib uri="/WEB-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>

<jsp:useBean id="sessionUtil"  class="com.etcc.csc.util.SessionUtil" scope="page"/>


<tiles:useAttribute name="NotLoggedInTitleKey"  id="NotLoggedInTitleKey" classname="java.lang.String"/>
<tiles:useAttribute name="tabTitleKey"  id="tabTitleKey" classname="java.lang.String"/>
<tiles:useAttribute name="invoiceInfo"  id="invoiceInfo" classname="java.lang.String"/>

<html>
<head>
	<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/> 
        <title><bean:message key="${NotLoggedInTitleKey}"/></title>
        <LINK href="${pageContext.request.contextPath}/css/steps.css" rel="stylesheet" type="text/css" />
        <LINK href="${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.steps"/>" rel="stylesheet" type="text/css" >
	<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/openWin.js" ></script>
        <script type="text/javascript">
            function updateLang(){
                location.href="${pageContext.request.contextPath}/externUpdateLanguage.do";
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
	<!-- begin left column (logo, account login, VeriSign logo) -->
	<td id="left">
		<div id="ntta-home"><a href="http://www.ntta.org" title="NTTA Home"><fmt:message key="label.home.ntta"/></a></div>
		<div id="title-csc"><h1><fmt:message key="label.customerServiceCenter"/></h1>
		</div>

                <div style="padding-top: 5px; padding-left: 20px;">
                    <a href="javascript:confirmUpdateLang()"><img alt="" src="${pageContext.request.contextPath}<bean:message key="images.languageButton"/>"/></a>
                </div>
                <br/>
                <br/>
                
		<a href="${pageContext.request.contextPath}/index.jsp" id="button-home"><fmt:message key="label.home"/></a>

		<a href="#" id="button-help" onclick="openWin('<etcc-extended:Translation property="helpUrl"/>',800,500);"><fmt:message key="label.help"/></a>
		<a href="#" id="button-contact" onclick="openWin('${pageContext.request.contextPath}/contactUsPopupDisplay.do',500,350);"><fmt:message key="label.contactUs"/></a>
                <c:if test="${not empty invoiceInfo}">
                    <tiles:get name="invoiceInfo"/>
                </c:if>
	</td>
	<!-- end left column -->
	
	<td id="middle">
		<!-- begin middle column (splash, tabs, content, violation login) -->
		<table id="body" cellspacing="0">
		
                    <!-- begin tab navigation -->
                    <tr>		
                        <td id="tabs">
                            <ul>
                                <li id="tab-left"></li>
                                <li class="here"><a href="#"><span><bean:message key="${tabTitleKey}"/></span></a></li>
                            </ul>
                        </td>			
                    </tr>
                    <!-- end tab navigation -->
                    
                    <!--begin main content -->				
                    <tr style="height:530px">
                        <td id="content">
                            <table cellspacing="0" id="data-table">
                            <tr>	
                            <tr>
                                    <td class="topleft"></td>
                                    <td class="topcenter"></td>
                                    <td class="topright"></td>
                            </tr>
                            <tr>
                                    <td class="left"></td>
                                    <td class="content">
                                        <tiles:get name="content"/>
                                    </td>		
                                    <td class="right"></td>		
                            </tr>
                                    
                            <tr>
                                    <td class="bottomleft"></td>
                                    <td class="bottomcenter"></td>
                                    <td class="bottomright"></td>
                            </tr>
                            </table>			

                        </td>			
                    </tr>
		<!--end main content -->				
                </table>
                
	</td>
        <!-- end middle column -->
	
	<!-- begin right shadow -->
	<td id="right" rowspan="2">	
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
	<td id="footer" colspan="2"><strong><fmt:message key="label.customerService"/>: <etcc-extended:Translation property="contactPhoneNumber"/></strong>  |  <a href="#" onclick="openWin('<etcc-extended:Translation property="privacyPolicyUrl"/>',800,500);"><fmt:message key="label.privacyPolicy"/></a>  |  <a href="#" onclick="openWin('<etcc-extended:Translation property="supportedBrowserUrl"/>',800,500);"><fmt:message key="label.supportedBrowser"/></a></td>
    </tr>
    
    <tr>
	<td id="shadow-bottom" colspan="3"><div id="shadow-bottomright"></div></td>
    </tr> 
</table>

</body>
</html>
