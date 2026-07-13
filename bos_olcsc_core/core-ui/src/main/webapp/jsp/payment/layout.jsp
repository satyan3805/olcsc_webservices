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
                    if (tdClassName != null && tdClassName.indexOf("content")==0){
                            tds[i].className = styleName;
                    }
            }
    
            styleName = "text"+size;
    
            var inputs = table.getElementsByTagName("input");
            for (var i=0; i<inputs.length;i++)
            {
                    var inputClassName = inputs[i].className;
                    if (inputClassName != null && inputClassName.indexOf("text")==0){
                            inputs[i].className = styleName;
                    }
            }
            
            var selects = table.getElementsByTagName("select");
            for (var i=0; i<selects.length;i++)
            {
                    var selectClassName = selects[i].className;
                    if (selectClassName != null && selectClassName.indexOf("text")==0){
                            selects[i].className = styleName;
                    }
            }

        }
        

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
		<div id="ntta-home"><a href="http://www.ntta.org" title="<fmt:message key="label.home.ntta"/>"><fmt:message key="label.home.ntta"/></a></div>
		<div id="title-csc"><h1><fmt:message key="label.customerServiceCenter"/></h1>
		</div>
                
                <div style="padding-top: 5px; padding-left: 20px;">
                    <a href="javascript: confirmUpdateLang()"><img src="${pageContext.request.contextPath}<bean:message key="images.languageButton"/>" alt=""/></a>
                </div>
                <br/><br/>
                
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
            <c:if test="${sessionScope.accountLogin.loginType=='IN'}">
                <strong><fmt:message key="PaymentInfoForm.confirmInvoice.invoiceId"/>:</strong><br>
            </c:if>
            <c:if test="${sessionScope.accountLogin.loginType=='CA'}">
                <strong><fmt:message key="PaymentInfoForm.layout.collection"/>:</strong><br>
            </c:if>
           ${sessionScope.accountLogin.invoiceId}<br><br>
     <strong><fmt:message key="tagRequestForm.licensePlate"/>:</strong><br>
                ${sessionScope.accountLogin.licPlate}
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
						<li class="here"><a href="#"><span><fmt:message key="PaymentInfoForm.layout.violations"/></span></a></li>
					</ul>
			</td>			
		</tr>
		<!-- end tab navigation -->
		
		<!--begin main content -->				
		<tr>
			<td id="content">
			<table cellspacing="0" id="data-table" width="760">
			<tr>
			<td colspan="4" id="font-sizer">
			<h2 style="float: left; margin: 0 20px 0 5px; padding: 0; color: #6d91a7; font-size: 20px; font-weight: normal;"><fmt:message key="PaymentInfoForm.header1"/></h2>
				<div>
				<span><strong><fmt:message key="label.fontsize"/>:</strong></span>
				<a href="#" id="font-small" onClick="fontsize('small');"></a>
				<a href="#" id="font-medium" onClick="fontsize('');"></a>
				<a href="#" id="font-large" onClick="fontsize('large');"></a>		
			</div>
			</td>
			</tr>	
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
