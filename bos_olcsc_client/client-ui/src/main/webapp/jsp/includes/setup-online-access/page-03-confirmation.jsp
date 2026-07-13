<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

	<div id="content">

		<form id="sign-up" action="${pageContext.request.contextPath}/myAccountHome.do?setEvent2=true&setResetActivity=successful" method="post">

			<div class="section">

				<h1 id="congratulations">Congratulations!</h1>

				<p>Your online access setup is complete.</p>

				<p>A confirmation message has been sent to your e-mail address.</p>

				<p>If you need further assistance, please contact us at 281-875-EASY (3279), <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>.</p>

			</div> <!-- end of section -->

			<ul class="form-actions">

				<li><input id="continue" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Continue" title="&rarr; page-01b-set-up-your-online-access.shtml" /></li>

			</ul> <!-- end of form-actions -->

		</form>

	</div> <!-- end of content -->

	<p class="progress-bar" id="step-3-of-2">
		Setup online access
		<em>Complete</em>
	</p>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event3,event42:${Acctid}";
s.eVar1="${Acctid}";
s.eVar2="Setup Online Account - LogIn";
</script>