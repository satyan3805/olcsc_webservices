<%@ include file="/jsp/common/Taglibs.jsp" %>

<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<div id="content-container">

        <div id="content">

                <form id="sign-up" action="${pageContext.request.contextPath}/myAccountHome.do?setEvent2=true&setResetActivity=successful" method="post">

                        <div class="section">

                                <h1 id="your-login-information-has-been-reset-successfully">Your login information has been successfully reset</h1>

                                <p>A confirmation message has been mailed to your email address.</p>

                                <p>If you need further assistance, please contact us at 281-875-EASY (3279), <a href="${appDelegate.domainName}/about_locations/"> during regular business hours</a>.</p>

                        </div> <!-- end of section -->

                        <ul class="form-actions">

                                <!--    <li><input id="continue" type="image" class="image-based submit-button" src="../meta/media/buttons/continue.gif" value="Continue" onclick="alert('Forward to manage account page.'); return false" title="&rarr; page-01b-set-up-your-online-access.shtml" /></li>  -->
                                <!--    <li><input id="continue" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="document.forms['mainForm'].submit(); return false" title="&rarr; page-04-confirmation.shtml" /></li> -->
                                <li><input id="continue" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Continue" title="&rarr; page-01b-set-up-your-online-access.shtml" /></li>

                        </ul> <!-- end of form-actions -->

                </form>

        </div> <!-- end of content -->

        <p class="progress-bar" id="step-4-of-3">
                Reset login information
                <em>Complete</em>
        </p>

</div> <!-- end of content-container -->

<script type="text/javascript">
s.events="event3";
s.eVar2="Reset Login and Password";
</script>
