<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<%@ include file="/jsp/common/Taglibs.jsp" %>

<div id="content-container">

        <div id="content">

                <form id="sign-up" action="${pageContext.request.contextPath}/accountHome.do" method="post">

                        <div class="section">

                                <h1 id="unable-to-reset-login">Unable to reset login information</h1>

                                <p>Please contact Customer Service at 281-875-EASY (3279) M-F 7:30AM - 5:30PM & Sat 8:00AM - 4:30PM or <a href="${appDelegate.domainName}/about_contact/">Contact Us</a></p>

                        </div> <!-- end of section -->

                        <ul class="form-actions">

                                <li><input id="continue" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Continue" title="&rarr; page-01b-set-up-your-online-access.shtml" /></li>

                        </ul> <!-- end of form-actions -->

                </form>

        </div> <!-- end of content -->

        <p class="progress-bar" id="step-4-of-3">
                Reset login information
                <em>Step 4 of 3</em>
        </p>

</div> <!-- end of content-container -->
