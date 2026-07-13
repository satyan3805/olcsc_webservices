<%@ include file="/jsp/common/Taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>NTTA Customer Service Center</title>
	
        <LINK href="${pageContext.request.contextPath}/css/logged-in.css" rel=stylesheet type="text/css" >
        
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript"></script>
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
			 <a href="#" id="logout">Log out</a>
		</div>
		<a href="#" id="help">Help</a>
		<br class="clear">
		<!-- help -->

		<!-- begin account info -->
		<table cellspacing="0" id="account-info">
		<tr>
			<td class="corner-topleft"></td>
			<td class="topmiddle" colspan="2"></td>
			<td class="corner-topright"></td>
		</tr>
		<tr>
			<td class="left"></td>
			<td class="content col1">
				<!-- account-info data column 1-->
				Name: Ramakrishna Venkataraman<br>
				Company: Price, Waterhouse, Cooper <br>
				Driver's License: 13370536<br>
				Driver's License State: Texas<br>
			</td>
			<td class="content col2">
				<!-- account-info data column 2-->
				Account: 1234567890<br>
				Account Type: Primary<br>
				Account Balance: $33.25<br>
			</td>
			<td class="right"></td>
		</tr>
		<tr>
			<td class="corner-bottomleft"></td>
			<td class="bottommiddle" colspan="2"></td>
			<td class="corner-bottomright"></td>				
		</tr>
		</table>
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
		<ul>
			<li><a href="#"><span>MY ACCOUNT</span></a></li>
			<li class="here"><a href="#"><span>ACCOUNT HISTORY</span></a></li>
			<li><a href="#"><span>CONTACT US</span></a></li>		
		</ul>
	</td>
</tr>

<tr>
	<td id="nav-top"></td>
	<td id="content-top"></td>
</tr>

<!-- begin left navigation -->

<!--	note: class "here" refers to the current page. 
		Any list item with class "here" that is not a hyperlink, 
		must have a <span> tag -->
<tr>	
	<td id="nav">
		<ul class="level1">
			<li class="first here"><span>View transactions</span></li>
			<li><a href="#">View statements</a></li>
			<li><a href="#">View summary</a></li>	
		</ul>
	</td>
<!-- end left navigation -->
	
<!-- begin content -->
	<td id="content">
	
	<table cellspacing="0" id="data-table">
	<tr>	
		<td colspan="4" id="font-sizer">
			<div>
				<span><strong>font-size:</strong></span>
				<a href="#" id="font-small"></a>
				<a href="#" id="font-medium"></a>
				<a href="#" id="font-large"></a>		
			</div>
		</td>
	</tr>	
	<tr>
		<td class="topleft"></td>
		<td class="topcenter" colspan="2"></td>
		<td class="topright"></td>
	</tr>
	<tr id="content-top">
		<td class="left"></td>
		<td class="content" colspan="2"></td>		
		<td class="right"></td>		
	</tr>
	

	<tiles:importAttribute name="mainBody" scope="request"/>
      <logic:iterate id="items" name="mainBody">
          <tiles:insert beanName="items" flush="false"/>
        </logic:iterate>

	<tr id="content-bottom">
		<td class="left"></td>
		<td class="content" colspan="2"></td>		
		<td class="right"></td>		
	</tr>
		
	<tr>
		<td class="bottomleft"></td>
		<td class="bottomcenter" colspan="2"></td>
		<td class="bottomright"></td>
	</tr>
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
			<li class="first">Customer Service: <strong><etcc-extended:Translation property="homepageContactPhone"/></strong></li>
			<li><a href="#">Privacy Policy</a></li>
			<li><a href="#">Supported Browsers</a></li>			
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