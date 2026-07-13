<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<%@ include file="/jsp/common/modalInclude.jsp" %>
<%@ taglib uri="/WEB-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>

<tiles:useAttribute name="invoiceInfo"  id="invoiceInfo" classname="java.lang.String"/>
<html>
<head>
	<title><fmt:message key="title.violationStartPage"/></title>
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
        <LINK href="${pageContext.request.contextPath}/css/steps.css" rel=stylesheet type="text/css" >
        <LINK href="${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.steps"/>" rel=stylesheet type="text/css" >
	
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
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
                    <a href="javascript: confirmUpdateLang()"><img alt="" src="${pageContext.request.contextPath}<bean:message key="images.languageButton"/>"/></a>
                    <br/><br/><br/>
                </div>
                
		<p><a href="${pageContext.request.contextPath}" id="button-home"><fmt:message key="label.home"/></a>

		  <a href="#" id="button-help" onclick="openWin('<etcc-extended:Translation property="helpUrl"/>',800,500);"><fmt:message key="label.help"/></a>
		  <a href="#" id="button-contact" onclick="openWin('${pageContext.request.contextPath}/contactUsPopupDisplay.do',500,350);"><fmt:message key="label.contactUs"/></a>
		</p>
		
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
                                    <li class="here"><a href="#"><span><fmt:message key="PaymentInfoForm.layout.secondNotices"/></span></a></li>
                            </ul>
			</td>			
		</tr>
		<!-- end tab navigation -->
		
		<!--begin main content -->				
		<tr>
                    <td id="content">			
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td colspan=2>
                            <bean:message key="violation.start.intro"/>
                        </td>
                    </tr>
                     <tr>
                        <td colspan=2>
                          &nbsp;
                        </td>
                    </tr>
                    <!-- Removed for defect # 356 
                    <tr>
                        <td colspan=2>
                           <font color="red"><bean:message key="label.vea.warning"/></font>
                        </td>
                    </tr> -->

                    <tr>
                      <td><table cellspacing="0" id="data-table" style="width:320px;">
                        <tr>
                        <tr>
                          <td class="topleft"></td>
                          <td class="topcenter"></td>
                          <td class="topright"></td>
                        </tr>
                        <tr>
                          <td class="left"></td>
                          <td class="content">
                            <h2><bean:message key="violation.start.haveAccount"/></h2>

                            <br> <p><strong><bean:message key="violation.start.chooseOption"/></strong></p>
                                    <style>	.option {  padding-left: 40px; margin-bottom: 10px; background: url(${pageContext.request.contextPath}/images/common/step/arrow-blue.gif) no-repeat 5px 8px ; }
                                            hr { border-bottom: 1px solid #e6d5c9; height: 1px; margin: 0 0 10px 40px; }
                                    </style>
                                    <div class="option">
                                        <bean:message key="violation.start.haveOnlineAccess"/>
                                        
                                        <etcc-extended:autocompleteOffForm  action="/AuthenticateViolator.do" styleId="loginForm" >
                                            <table cellspacing="0" class="form">
                                                <c:set var="acctErrorImgCtr" value="0"/>
                                                <logic:messagesPresent message="false" property="userName">
                                                    <c:set var="acctErrorImgCtr" value="1"/>
                                                    <div id="error">
                                                        <div class="error-img"/>
                                                        <ul>
                                                            <html:messages id="msg" message="false" property="userName">
                                                                <li>
                                                                    <bean:write name="msg"/>
                                                                </li>
                                                            </html:messages>
                                                        </ul>
                                                    </div>
                                                </logic:messagesPresent>
                                                <logic:messagesPresent message="false"  property="password">
                                                    <div id="error">
                                                        <c:if test="${acctErrorImgCtr == 0}">
                                                        <div class="error-img"/>
                                                        </c:if>
                                                        <ul>
                                                            <html:messages id="msg" message="false" property="password">
                                                                <li>
                                                                    <bean:write name="msg"/>
                                                                </li>
                                                            </html:messages>
                                                        </ul>
                                                    </div>
                                                </logic:messagesPresent>
                                                <logic:messagesPresent message="true"  property="invalidAccount">
                                                    <div id="error">
                                                        <c:if test="${acctErrorImgCtr == 0}">
                                                        <div class="error-img"/>
                                                        </c:if>
                                                        <ul>
                                                            <html:messages id="msg" message="true" property="invalidAccount">
                                                                <li>
                                                                    <bean:write name="msg"/>
                                                                </li>
                                                            </html:messages>
                                                        </ul>
                                                    </div>
                                                </logic:messagesPresent>
                                              <tr>
                                                <th><bean:message key="UserForm.userName.displayname"/>:</th>
                                                <td colspan="5"><html:text property="userName" size="9" styleClass="text" maxlength="16"/></td>
                                              </tr>
                                              <tr>
                                                <th nowrap><bean:message key="UserForm.password.displayname"/>: </th>
                                                <td colspan="5"><html:password property="password" size="8" styleClass="text" maxlength="16" /></td>
                                              </tr>
                                              <tr>
                                                <th nowrap>&nbsp;</th>
                                                <td colspan="5"><input name="Submit4" type="submit" class="button" value="<fmt:message key="button.login"/>" onclick="this.disabled='true';document.forms['loginForm'].submit();"></td>
                                              </tr>
                                              <tr>
                                                <td colspan="6"><a href="${pageContext.request.contextPath}/home/displayPasswordRetrieval.do"><fmt:message key="text.passwordRetrieval.pageTitle"/></a></td>
                                              </tr>
                                            </table>
                                        </etcc-extended:autocompleteOffForm >
                                    
                                    </div>
                                    
                                    <div class="text-or" style="padding-bottom: 10px; margin-left: 80px;"></div>
                                    <html:form action="/home/onlineAccessAccountInfo.do" styleId="onlineAccess">
                                    <div class="option">
                                        <p><bean:message key="violation.start.wantOnlineAccess"/></p>
                                        <p><input name="Submit" type="submit" class="button" value="<fmt:message key="button.setupOnline"/>" onclick="this.disabled='true'; document.forms['onlineAccess'].submit();"></p>
                                    </div>
                                    </html:form>
                          </td>
                          <td class="right"></td>
                        </tr>
                        <tr>
                          <td class="bottomleft"></td>
                          <td class="bottomcenter"></td>
                          <td class="bottomright"></td>
                        </tr>
                      </table></td>
                      <td><table cellspacing="0" id="data-table" style="width:320px;">
                        <tr>
                        <tr>
                          <td class="topleft"></td>
                          <td class="topcenter"></td>
                          <td class="topright"></td>
                        </tr>
                        <tr>
                          <td class="left"></td>
                          <td class="content">
                            <h2><bean:message key="violation.start.notHaveAccount"/></h2>
                            <p><strong><bean:message key="violation.start.chooseOption"/></strong></p>
                            <style>
                                .option {  padding-left: 40px; margin-bottom: 10px; background: url(${pageContext.request.contextPath}/images/common/step/arrow-blue.gif) no-repeat 5px 8px ; }
                                hr { border-bottom: 1px solid #e6d5c9; height: 1px; margin: 0 0 10px 40px; }
                            </style>
                            <html:form action="/GetTollTagDisplayAgreement.do" styleId="getTollTag">
                                <div class="option">
                                    <bean:message key="violation.start.wantTTAccount"/>
                                    <p><input name="Submit2" type="submit" class="button" value="<fmt:message key="button.getATollTag"/>" onclick="this.disabled='true'; document.forms['getTollTag'].submit();">
                                </div>
                            </html:form>
                            <div style="height:135px">&nbsp;</div>
                            <div class="text-or" style="padding-bottom: 10px; margin-left: 80px;"></div>
                            <html:form action="/InitializePayment.do" styleId="payInvoice"> 
                                <div class="option">								
                                    <p><bean:message key="violation.start.payInvoice"/></p>
                                    <div style="height:20px">&nbsp;</div>
                                    <p><input name="Submit3" type="submit" class="button" value="<fmt:message key="button.justPayInvoice"/>" onclick="this.disabled='true'; document.forms['payInvoice'].submit();"></p>
                                </div>
                            </html:form>
                          </td>
                          <td class="right"></td>
                        </tr>
                        <tr>
                          <td class="bottomleft"></td>
                          <td class="bottomcenter"></td>
                          <td class="bottomright"></td>
                        </tr>
                      </table></td>
                    </tr>
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