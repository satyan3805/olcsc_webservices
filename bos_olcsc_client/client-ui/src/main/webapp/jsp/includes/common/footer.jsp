
	<!-- the width & height of this element are set through CSS -->
<%-- <a href="../home.shtml"><img id="logo" src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="Home" /></a> --%>
  <jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
  <div id="footer">

    <ul>
   	<!--RFC20140192 added by nelisakiboti -->
    <li class="first-child nth-child-odd nth-child-1"><a href="https://www.hctra.org/securitypolicy"> Internet Security Policy</a></li>
      <li class="nth-child-even nth-child-2"><a href="https://www.hctra.org/privacy/privacy_policy.html">Privacy Policy</a></li>
      <li class="nth-child-odd nth-child-3"><!--<a href="mailto:webmaster@hctra.com">Webmaster</a>--><a href="${appDelegate.domainName}/about_contact/">Webmaster</a></li>
      <li class="nth-child-even nth-child-4 last-child"><a href="https://www.hctra.org/about_employment/">Employment</a></li>
    </ul>
    
    <div class="vcard">
      <p class="tel">
	Call <span class="type">Customer Service</span> at 
	<span class="value">${appDelegate.contactPhoneNumber}</span> -
	<em>M-F 7:30AM - 5:30PM & Sat 8:00AM - 4:30PM</em>
      </p>
      
      <p class="adr">
	or visit us at one of our convenient storefront <a href="${appDelegate.domainName}/about_locations/">locations</a>.
      </p>
      <p class="copyright"><span class="fn org">Harris County Toll Road Authority</span> &copy; 2007-2010. All Rights Reserved.</p>
    </div>
  </div> <!-- end of footer -->
