<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

	<div id="content-container">

		<div id="primary-and-secondary-content">

			<div id="primary-content">

			<h1>The page you requested cannot be found</h1>

                        <p>If you typed the URL yourself, please make sure the spelling is correct.</p>
                        <p>If you clicked a link to get here, there may be a problem with the link. Try using your browser's "Back" button and select a different link on that page.</p>
                        <p>If you are having trouble finding what you're looking for, try using search at the top of this page.</p>

			</div> <!-- end of primary content -->

			<div id="secondary-content">

				<div id="secondary-content-interior">

					<h6>Useful Links</h6>

					<p><a href="${appDelegate.domainName}/about_faq">Frequently Asked Questions</a></p>
					<p><a href="${appDelegate.domainName}/about_contact">Contact Us</a></p>
					<p><a href="${appDelegate.domainName}/about_locations">Find an EZ TAG Store</a></p>
					<p><a href="${appDelegate.domainName}/about_forms">Common forms</a></p>
					<p><a href="${appDelegate.domainName}/tollroads_advisories">Traffic Advisories</a></p>


				</div> <!-- end of secondary-content-interior -->

			</div> <!-- end of secondary-content -->


		</div> <!-- end of primary-and-secondary-content -->

	</div> <!-- end of content-container -->