	<div id="content-container">

		<div id="content">

			<form id="sign-up" action="?" method="post">

				<h1 id="is-this-correct">Is this correct? <em>Please review everything to make sure it is accurate.</em></h1>

				<h3 id="account-information-for">
					Username: <em>'ssumers'</em>
					<input type="image" class="image-based submit-button" src="../meta/media/buttons/edit-password.gif" value="Change your password or user id" onclick="document.location.href='page-01-set-up-your-online-access.shtml'; return false;" />
				</h3>

				<div class="section with-primary-and-secondary-content">

					<div class="primary-section-content with-multiple-definition-lists">

						<h4 id="personal-information">Personal Information</h4>

						<dl id="user-information" class="immediately-following-an-h4">

							<dt>Scott Summers <input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit your personal information" onclick="document.location.href='page-01-set-up-your-online-access.shtml'; return false;" /></dt>
								<dd>ssummers@gmail.com</dd>
								<dd>(905) 555-1212</dd>
								<dd>(905) 555-1220</dd>
								<dd>TX DL: 18923408</dd>

						</dl> <!-- end of user-information -->

						<dl id="mailing-address">

							<dt>Mailing Address: <input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit your mailing address" onclick="document.location.href='page-02-contact-information.shtml'; return false;" /></dt>
								<dd>
									<address>
										4317 Main St<br />
										Houston, TX 73456
									</address>
								</dd>

						</dl> <!-- end of mailing-address -->

						<dl class="selections updated">

							<dt>You said: <input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit your preferences" onclick="document.location.href='page-05p-preferences.shtml'; return false;" /></dt>
								<dd>Notify me by e-mail and regular mail</dd>
								<dd>Send me an itemized monthly statement by mail (costs $1.50 per month)</dd>

						</dl> <!-- end of selections -->

					</div> <!-- end of primary-section-content -->

					<div class="secondary-section-content with-multiple-definition-lists">

						<h4 id="billing-information">Billing Information</h4>

						<dl id="payment-method" class="immediately-following-an-h4">
							<dt>Payment Method: <input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit your payment method" onclick="document.location.href='page-04-billing-information.shtml#credit-card'; return false;" /></dt>
								<dd>MasterCard ***-4138</dd>
								<dd>Exp: 04/2008</dd>

						</dl> <!-- end of payment-method -->

						<dl id="billing-address">
							<dt>Billing Address: <input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit your billing address" onclick="document.location.href='page-04-billing-information.shtml#credit-card'; return false;" /></dt>
								<dd>
									<address>
										Scott Summers<br />
										4317 Main St<br />
										Houston, TX 73456
									</address>
								</dd>

						</dl>

					</div> <!-- end of secondary-section-content -->

				</div> <!-- end of section with-primary-and-secondary-content -->


				<h4 id="vehicles-added">Vehicles added:</h4>

				<ul class="vehicles">

					<li>
						<p>
							<strong>Sally&rsquo;s minivan</strong> &mdash; 2006 Charcoal Grey Toyota Sienna, <span>TX </span> MWK007

								<input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit vehicle 1" onclick="document.location.href='page-03c-vehicle-information.shtml'; return false;" />
								<input type="image" class="image-based submit-button" src="../meta/media/buttons/delete.gif" value="Delete vehicle 1" onclick="return false;" />

						</p>
					</li>

					<li>
						<p>
							<strong>Jim&rsquo;s Stingray</strong> &mdash; 1964 Red Chevrolet Corvette, <span>TX </span> JDK234

								<input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit vehicle 2" onclick="document.location.href='page-03c-vehicle-information.shtml'; return false;" />
								<input type="image" class="image-based submit-button delete" src="../meta/media/buttons/delete.gif" value="Delete vehicle 2" />

						</p>
					</li>

					<li>
						<p>
							<strong>Libby&rsquo;s MINI</strong> &mdash; 2004 British Racing Green MINI Cooper, <span>TX </span> EGK321

								<input type="image" class="image-based submit-button" src="../meta/media/buttons/edit.gif" value="Edit vehicle 3" onclick="document.location.href='page-03c-vehicle-information.shtml'; return false;" />
								<input type="image" class="image-based submit-button delete" src="../meta/media/buttons/delete.gif" value="Delete vehicle 3" />

						</p>
					</li>

				</ul> <!-- end of vehicles -->

				<div class="section">

					<h4 id="total-cost">Total cost:</h4>

					<table id="account-setup-charges" class="data-table">
						<thead>
							<tr>
								<td colspan="3" scope="col">Account setup charges</td>
							</tr>
						</thead>

						<tfoot>
							<tr>
								<td colspan="2">Account setup charges:</td>
								<td class="currency"><span>$</span>105.00</td>
							</tr>
						</tfoot>

						<tbody>
							<tr class="odd">
								<td colspan="2">Initial minimum EZ TAG account balance</td>
								<td class="currency"><span>$</span>40.00</td>
							</tr>
							<tr class="even">
								<td>First three EZ TAG stickers</td>
								<td>3 @ $15 each</td>
								<td class="currency"><span>$</span>45.00</td>
							</tr>
							<tr class="odd">
								<td>Remaining EZ TAG stickers</td>
								<td>2 @ $10 each</td>
								<td class="currency"><span>$</span>20.00</td>
							</tr>
						</tbody>
					</table>

					<h5 id="other-charges">Other charges</h5>

					<table class="data-table other-charges-table">
						<thead>
							<tr>
								<td colspan="5">TX MWK007 &mdash; Sally&rsquo;s minivan</td>
							</tr>
							<tr>
								<th></th>
								<th scope="col">ID</th>
								<th scope="col">Date</th>
								<th scope="col">Due Date</th>
								<th></th>
							</tr>
						</thead>

						<tfoot>
							<tr>
								<td colspan="4">TX MWK007 invoices paid:</td>
								<td class="currency"><span>$</span>79.74</td>
							</tr>
							<tr class="final-sum">
								<td colspan="4">total cost:</td>
								<td class="currency"><span>$</span>184.74</td>
							</tr>
						</tfoot>

						<tbody>

							<tr class="odd">
								<th scope="row">outstanding invoices</th>
								<td>1409795</td>
								<td class="date">03/23/06</td>
								<td class="date">04/27/06</td>
								<td class="currency"><span>$</span>34.45</td>
							</tr>
							<tr class="even">
								<th></th>
								<td>3543326</td>
								<td class="date">04/26/06</td>
								<td class="date">05/16/06</td>
								<td class="currency"><span>$</span>22.34</td>
							</tr>
							<tr class="odd">
								<th scope="row"></th>
								<td>11409795</td>
								<td class="date">03/23/06</td>
								<td class="date">04/05/06</td>
								<td class="currency"><span>$</span>12.45</td>
							</tr>
							<tr class="even">
								<th></th>
								<td>13543326</td>
								<td class="date">04/26/06</td>
								<td class="date">05/03/06</td>
								<td class="currency"><span>$</span>10.50</td>
							</tr>
						</tbody>
					</table>

				</div> <!-- end of section -->

				<ul class="form-actions">

					<li><input id="activate-account" type="image" class="image-based submit-button" src="../meta/media/buttons/activate-account.gif" value="Activate Account" onclick="document.location.href='page-06-thank-you-page.shtml'; return false" /></li>

				</ul> <!-- end of form-actions -->

			</form>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-4-of-4">
			New EZ TAG Account
			<em>Step 4 of 4</em>
		</p>

	</div> <!-- end of content-container -->