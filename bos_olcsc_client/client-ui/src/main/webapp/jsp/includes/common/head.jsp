<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
                         response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                         response.setHeader("Expires", "-1"); //prevents caching at the proxy server
                         response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
                          %>
   

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/defaults.css" media="all" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/typography.css" media="all" />

<!-- Pocket IE properly ignores the media type "screen" if it's capitalized:
	http://urlgreyhot.com/personal/weblog/css_for_windows_mobile_pocket_pc_internet_explorer -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/colors.css" media="Screen,handheld,projection" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/layout.css" media="Screen,projection,print" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/handheld.css" media="handheld" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/print.css" media="print" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/datepicker.css" media="Screen,projection,print" />

<!-- for IE -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/meta/media/common/favicon.ico" type="image/x-icon" />

<!-- for standards compliant browsers -->
<link rel="icon" href="${pageContext.request.contextPath}/meta/media/common/favicon.gif" type="image/gif" />
