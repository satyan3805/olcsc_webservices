	<div id="content-container">

		<div id="content">

			<form id="sign-up" action="?" method="post">

				<div class="section">

					<h1 id="manage-your-account">Manage your account</h1>

					<p>Log in to your account online:</p>

					<dl class="errors">
						<dt>Unable to login.</dt>
							<dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd>

					</dl> <!-- end of errors -->

				</div> <!-- end of section -->

				<div class="section">

					<fieldset>

						<dl>

							<dt class="first-dt-dd-pair"><label for="username">Username:</label></dt>
								<dd class="first-dt-dd-pair">
									<input type="text" class="textfield" id="username" name="username" />
									<p class="help">Did you <a href="../reset-login/page-01-account-information.shtml">forget your username?</a></p>
								</dd>

							<dt><label for="password">Password:</label></dt>
								<dd>
									<input type="password" class="textfield" id="password" name="password" />
									<p class="help">Did you <a href="../reset-login/page-01-account-information.shtml">forget your password?</a></p>

								</dd>
						</dl>

						<p><input id="login" type="image" class="image-based submit-button" src="../meta/media/buttons/login.gif" value="Login" onclick="alert('Forward to manage account page.'); return false" title="&rarr; page-01b-set-up-your-online-access.shtml"/></p>

					</fieldset>

					<h2>Online account assistance</h2>

					<p>Did you <a href="../reset-login/page-01-account-information.shtml">forget your username or password?</a></p>
					<p>Would you like to <a href="../setup-online-access/page-01-account-information.shtml">setup online access for an EZ TAG</a> you already have?</p>
					<p>Don&rsquo;t have an EZ TAG yet? <a href="../signup/page-01-set-up-your-online-access.shtml">You can purchase one online.</a></p>

				</div> <!-- end of section -->

			</form>

		</div> <!-- end of content -->

	</div> <!-- end of content-container -->
