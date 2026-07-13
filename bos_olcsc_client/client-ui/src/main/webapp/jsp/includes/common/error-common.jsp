<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
	<div id="content-container">

		<div id="primary-and-secondary-content">

			<div id="primary-content">

			<h1>Our system is not able to respond at this time.</h1>

		    <p>Please try again in a few minutes,<br />or call Customer Service at 281-875-EASY (3279) <a href="${appDelegate.domainName}/about_locations/" title="locations">during regular business hours</a> for assistance.</p>

			</div> <!-- end of primary content -->

			<div id="secondary-content">

				<div id="secondary-content-interior">

					<h6>Useful Links</h6>
<%try {
    //If AppDelegate is malfunctioning, it can throw one of several exceptions, which then causes an infinit loop.
%>
					<p><a href="${appDelegate.domainName}/about_faq">Frequently Asked Questions</a></p>
					<p><a href="${appDelegate.domainName}/about_contact">Contact Us</a></p>
					<p><a href="${appDelegate.domainName}/about_locations">Find an EZ TAG Store</a></p>
					<p><a href="${appDelegate.domainName}/about_forms">Common forms</a></p>
					<p><a href="${appDelegate.domainName}/tollroads_advisories">Traffic Advisories</a></p>
<% } catch (Exception e){
    //Delegate threw an uncaught exception.  Lets at least log it for reference.
    //If not handled here, we get infinite loops and eventually the AppServer itself crashes.
    e.printStackTrace();

}
    %>

				</div> <!-- end of secondary-content-interior -->

			</div> <!-- end of secondary-content -->


		</div> <!-- end of primary-and-secondary-content -->

	</div> <!-- end of content-container -->