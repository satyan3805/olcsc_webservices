<%@ include file="/jsp/common/Taglibs.jsp" %>
<!-- Reset password functionality through Email added by Nelisa kiboti Aug 13 2014-->
<div id="content-container">
	<div id="content">
		<div class="section">
		<h1 id="identity-confirmed">Password reset email sent</h1>
			<fieldset>

					<div>
						<div>
							<label style="cursor: text;">Please follow the instructions we sent to <span style="color:blue; cursor: text;"><bean:write name="OnlineAccessForm" property="emailAddress" /></span>  to retrieve your username and/or reset your password.</label><br>
							<br>
							<p style="cursor: text;">If you do not receive the password reset email, check your spam folder for an email from <span style="color:blue; cursor: text;"> noreply@hctra.org</span>. If you still have not 
							received the password reset email, you can also reset your login information using your <a style="cursor: pointer;"  href="${appDelegate.domainName}/eztagstore/home/resetPassword1.do">account or tag number</a> or <a style="cursor: pointer;"  href="${appDelegate.domainName}/about_contact">contact us</a> for help.</p>		
						</div>
					</div>
			</fieldset>

		</div> <!-- end of section -->

	</div> <!-- end of content -->

		<p style=" position: absolute; font-size: 1.9em; right: 15px; color: rgb(124,100,160);top:-3.5em;" id="step-1-of-3;">
		Reset login information
	</p>
	

</div> <!-- end of content-container -->

