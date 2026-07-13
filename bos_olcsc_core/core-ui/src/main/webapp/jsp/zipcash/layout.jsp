<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<tiles:useAttribute name="pageTitle"  id="pageTitle" classname="java.lang.String"/>

<html>
<head>
	<title><fmt:message key="${pageTitle}"/></title>
        
        <LINK href="${pageContext.request.contextPath}/css/steps.css" rel=stylesheet type="text/css" >
        <LINK href="${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.steps"/>" rel=stylesheet type="text/css" >
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript"></script>
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
	<!-- end left column -->
        
        <!-- begin left column (logo, account login, VeriSign logo) -->
	<td id="left">
		<div id="ntta-home"><a href="http://www.ntta.org" title="NTTA Home"><fmt:message key="label.home.ntta"/></a></div>
		<div id="title-csc"><h1><fmt:message key="label.customerServiceCenter"/></h1>
		</div>
                <div style="padding-top: 5px; padding-left: 20px;">
                    <a href="javascript: confirmUpdateLang()"><img src="${pageContext.request.contextPath}<bean:message key="images.languageButton"/>" alt=""/></a>
                </div>
                <br/>
                
                  <a href="${pageContext.request.contextPath}/index.jsp" id="button-home"><fmt:message key="label.home"/></a>
 		  <a href="#" id="button-help" onclick="openWin('<etcc-extended:Translation property="helpUrl"/>',800,500);"><fmt:message key="label.help"/></a>
		  <a href="#" id="button-contact" onclick="openWin('${pageContext.request.contextPath}/contactUsPopupDisplay.do',500,350);"><fmt:message key="label.contactUs"/></a>

	    <table id="info" cellspacing="0">
          <tr>
            <td id="info-top" colspan="2"></td>
          </tr>
          <tr>
            <td id="info-content">
            
                            <p>
            <strong><fmt:message key="zipcash.layout.zipcashId"/>:</strong><br>
           ${sessionScope.accountLogin.acctId}<br><br>
            <strong><fmt:message key="zipcash.layout.invoiceId"/>:</strong><br>
            <c:if test="${sessionScope.accountLogin.invoiceId=='' 
                    || sessionScope.accountLogin.invoiceId==0}">
               N/A<br><br>
            </c:if>
            <c:if test="${sessionScope.accountLogin.invoiceId!='' 
                    && sessionScope.accountLogin.invoiceId!=0}">
               ${sessionScope.accountLogin.invoiceId}<br><br>
            </c:if>
     <strong><fmt:message key="tagRequestForm.licensePlate"/>:</strong><br>
                ${sessionScope.accountLogin.licPlate}<br><br>
     <strong><fmt:message key="zipcash.layout.licensePlateState"/>:</strong><br>
                ${sessionScope.accountLogin.licState}
                </p>
</td>
            <td id="info-right"></td>
          </tr>
          <tr>
            <td id="info-bottom" colspan="2"></td>
          </tr>
        </table></td>
	<!-- end left column -->

        
        
	<td id="middle">
		<!-- begin middle column (splash, tabs, content, violation login) -->
		<table id="body" cellspacing="0">
		
		<!-- begin tab navigation -->
		<tr>		
			<td id="tabs">
					<ul>
						<li id="tab-left"></li>
<%--						<li class="zipcash-here"><a href="#"><span><img alt="ZipCash" src="${pageContext.request.contextPath}/images/ZIPCASH_logo.jpg"/></span></a></li>--%>
						<li class="zipcash-here"><a href="#"></a></li>
                                                
					</ul>
			</td>			
		</tr>
		<!-- end tab navigation -->
		
		<!--begin main content -->				
		<tr>
                    <td id="content">
			<h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="zipcash.layout.note1"/></h2>
                        <table cellspacing="0" id="data-table">
                            <tiles:importAttribute name="mainBody" scope="request"/>
                            <logic:iterate id="items" name="mainBody">
                              <tiles:insert beanName="items" flush="false"/>
                            </logic:iterate>
                	</table>			
                    </td>			
		</tr>
		<!--end main content -->				
		
</table>
					<!-- end middle column -->
	</td>
	
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