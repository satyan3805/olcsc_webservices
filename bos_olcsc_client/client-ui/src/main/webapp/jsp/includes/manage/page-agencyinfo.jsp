<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="tagDelegate"  class="com.etcc.csc.delegate.TagDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>Agency Information &mdash; Harris County Toll Road Authority</title>
        
        <jsp:include page="/jsp/includes/common/head.jsp"/>
</head>

<body id="www-hctra-com" class="account-section sign-up-section popup-window">
<div id="page">
	<div id="content-container">

		<div id="content">

			<dl class="introduction">
			<!--	<dt>Please retain this agreement for your records. Visittest1 our Online EZ TAG Store at <a href="${appDelegate.domainName}">HCTRA.org</a>.</dt> -->

					<!-- this first guy is hidden from browsers without JavaScript (and un-hidden via JavaScript -->
				<!--	<dd class="print" style="display:none;"><a href="#">Print this document</a></dd>

					<dd class="pdf"><a href="${appDelegate.domainName}/file_download/20/EZ+Agreement+Form.pdf">Download a PDF version</a></dd> -->
			</dl>
                        
                        ${tagDelegate.agencyInfo}
                        
                         
		</div> <!-- end of content -->

	</div> <!-- end of content-container -->
</div>  <!-- end of page -->

</body>
</html>
