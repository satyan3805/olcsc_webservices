<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title>Vehicle Information &mdash; Harris County Toll Road Authority</title>

	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/defaults.css" media="all" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/typography.css" media="all" />

<!-- Pocket IE properly ignores the media type "screen" if it's capitalized:
	http://urlgreyhot.com/personal/weblog/css_for_windows_mobile_pocket_pc_internet_explorer -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/colors.css" media="Screen,handheld,projection" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/layout.css" media="Screen,projection,print" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/handheld.css" media="handheld" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/print.css" media="print" />

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/offspring.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/prototype.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/domready-onload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/scriptaculous.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/unFocusHistory-p.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/event-selectors.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/common.js"></script>

<!-- for IE -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/meta/media/common/favicon.ico" type="image/x-icon" />

<!-- for standards compliant browsers -->
<link rel="icon" href="${pageContext.request.contextPath}/meta/media/common/favicon.gif" type="image/gif" />



	<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/account-section.js"></script>

		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/meta/css/ibox.css" media="screen,projection" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/meta/behavior/ibox.js"></script>


</head>

<tiles:useAttribute name="backAction"  id="backAction" classname="java.lang.String"/>
<tiles:useAttribute name="entryPoint"  id="entryPoint" classname="java.lang.String"/>
<tiles:useAttribute name="includeHiddenFields"  id="includeHiddenFields" classname="java.lang.String"/>

<body id="www-hctra-com" class="account-section sign-up-section vehicle-information-section immediate-lightbox">

	<div id="ibox">

		<div id="ibox_w" ></div>
		<div id="ibox_progress" ></div>

		<div id="ibox_wrapper">

			<div id="ibox_content">

				<div id="outstanding-tolls">
                                    <etcc-extended:autocompleteOffForm method="POST" name="tagRequestForm" styleId="mainForm" action="/signupVehicleInfoDisplay.do">
					<div class="outstanding-tolls-content">

						<h1>Current Toll Violation Invoices</h1>

						<p>The license plate <strong>${tagRequestForm.licensePlate}</strong> has outstanding tolls and fees on record.<br />
						If you think this is an error, check the license plate number carefully. Capital O and the number 0 (zero) as well as the letter I and the number 1 (one) are often hard to distinguish.</p>

						<h2 class="if-you-choose-to-purchase-a-ez-tag">If you choose to purchase an EZ TAG for this vehicle, you must pay all current outstanding tolls and fees.</h2>
                                                <br/><br/>
						<ul class="form-actions">
							<li class="first-child">
							<!--	<label for="remove-this-vehicle">I do not want to pay these tolls/fees now.</label> -->
                                                                <label>&nbsp;</label>
                                                                <input type="image" class="image-based submit-button remove-this-vehicle" id="remove-this-vehicle" name="remove-this-vehicle" value="Remove this vehicle from this list" src="${pageContext.request.contextPath}/meta/media/buttons/return-to-vehicle-info.gif" onclick="javascript:goBack();" />
                                                                
							</li>

							<li class="last-child">
								<input type="image" class="image-based submit-button i-will-pay-these" id="i-will-pay-these" name="i-will-pay-these" value="I will pay these tolls / fees" src="${pageContext.request.contextPath}/meta/media/buttons/review-tolls-and-fees.gif" onclick="javascript:payInvoices();"/>
                                                                <label><strong>Note: Invoice number is required</strong></label>
							</li>

						</ul> <!--end of form-actions -->
                                                
                                                <br/><br/>
                                                <p>To dispute these charges or set up a payment plan, please contact customer service at 281-875-EASY (3279) or visit an <a href="#">EZ TAG store in person</a>.</p>

					</div> <!-- end of outstanding-tolls content -->
                                    </etcc-extended:autocompleteOffForm>
				</div> <!-- end of outstanding-tolls -->

			</div> <!-- end of ibox_content -->

			<div id="ibox_footer_wrapper">

				<div id="ibox_close" style="float:right;">
					<a id="ibox_close_a" href="javascript:void(null);" >Click here to close</a>
				</div> <!-- end of ibox_close -->
				<div id="ibox_footer">&nbsp;</div>

			</div> <!-- end of ibox_footer_wrapper -->

		</div> <!-- end of ibox_wrapper -->

	</div> <!-- end of ibox -->

</body>
<script type="text/javascript">

function goBack()
{
    document.tagRequestForm.action = '${pageContext.request.contextPath}/${backAction}';
    document.tagRequestForm.submit();
}

function payInvoices()
{
    document.tagRequestForm.action = '${pageContext.request.contextPath}/violatorLoginDisplay.do?entryPoint=${entryPoint}';
    document.tagRequestForm.submit();
}

</script>
</html>

