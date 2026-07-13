<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

		<div id="primary-and-secondary-content">

			<div id="primary-content">


			<h2><bean:message key="error.session.expired.message"/></h2>

		    <a href="${pageContext.request.contextPath}/index.jsp"><img src="${pageContext.request.contextPath}/meta/media/buttons/login.gif" 
                title="Log in to your account"></a>

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