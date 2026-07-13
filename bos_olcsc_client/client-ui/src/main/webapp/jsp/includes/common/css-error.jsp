<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
	<div id="content-container">

		<div id="primary-and-secondary-content">

			<div id="primary-content">

			<h1>Our system has detected a security issue with the data you just transmitted.</h1>

		    <p>Please click here to return to <a href="${appDelegate.domainName}"><STRONG>www.HCTRA.org</STRONG></a>,<br />or call Customer Service at 281-875-EASY (3279) -
				<em>M-F 7:30AM - 5:30PM & Sat 8:00AM - 4:30PM</em> for assistance.</p>

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