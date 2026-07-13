<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
	<div id="content-container">

		<div id="primary-and-secondary-content">

			<div id="primary-content">

                                <form id="login" name="accountUserForm" action="${pageContext.request.contextPath}/authenticateUser.do" method="post">
                                        <fieldset>


                                            <h1 id="login-to-my-ez-tag">Login to My EZ TAG</h1>

                                            <div style="color:yellow">
                                                <c:set var="acctErrorImgCtr" value="0"/>
                                                <logic:messagesPresent message="false" property="userName">
                                                    <c:set var="acctErrorImgCtr" value="1"/>
                                                       <dt>Unable to login.</dt>
                                                           <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="false" property="userName">
                                                                <dd><bean:write name="msg"/></dd>
                                                            </html:messages>
                                                </logic:messagesPresent>

                                                <logic:messagesPresent message="false" property="password">
                                                    <c:if test="${acctErrorImgCtr == 0}">
                                                       <dt>Unable to login.</dt>
                                                    </c:if>
                                                           <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="false" property="password">
                                                                <dd><bean:write name="msg"/></dd>
                                                            </html:messages>
                                                </logic:messagesPresent>

                                                <logic:messagesPresent message="true"  property="invalidAccount">
                                                    <c:if test="${acctErrorImgCtr == 0}">
                                                        <dt>Unable to login.</dt>
                                                    </c:if>
                                                    <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="true" property="invalidAccount">
                                                                <dd>${msg}</dd>
                                                            </html:messages>
                                                </logic:messagesPresent>
                                        </div>



						<dl>
							<dt><label for="username">Username</label></dt>
								<dd><input type="text" class="textfield" name="userName" id="username" /></dd>

							<dt><label for="password">Password</label></dt>
								<dd><input type="password" class="textfield" name="password" id="password" /></dd>
						</dl>

						<input type="image" class="iamge-based submit-button" value="Login" src="${pageContext.request.contextPath}/meta/media/home/login.gif" onclick="javascript:doLogin();"/>

						<ul>
							<li><a href="${pageContext.request.contextPath}/home/resetPassword.do">Forget your password?</a></li>
							<li><a href="${pageContext.request.contextPath}/home/onlineAccessAccountInfo.do">First time to login?</a></li>
						</ul>

					</fieldset>

				</form>

                                <h1 id="primary-marketing"><a href="${pageContext.request.contextPath}/signup.do" title="Save time &amp; money. Use it anywhere in Texas. Get an EZ TAG."><img src="${pageContext.request.contextPath}/meta/media/home/primary-marketing.jpg" width="465" height="186" alt="Save time &amp; money. Use it anywhere in Texas. Get an EZ TAG." /></a></h1>

			</div> <!-- end of primary-content -->


			<div id="secondary-content">

				<div id="secondary-content-interior">


					<div id="news-and-information-section">

						<h2><a id="news-and-information" href="#"><span></span>News &amp; Information</a><a class="rss" href="#" title="Subscribe to our RSS feed"><span></span>Subscribe to our RSS feed</a></h2>

						<ul>
							<li class="alert">
								<a href="#"><strong>October 12, 2006</strong>
								Right Lane shut down for repair on Sam Houston Bridge.</a>
							</li>

							<li class="alert">
								<a href="#"><strong>October 10, 2006</strong>
								Hardy North Toll Plaza down to 2 lanes due to flooding.</a>
							</li>

							<li class="info">
								<a href="#"><strong>October 9, 2006</strong>
								Your EZ TAG now works at <acronym title="Houston Airport System">HAS</acronym> Airports with registration. Online registration is required.</a>
							</li>
						</ul>

					</div> <!-- end of news-and-information-section -->


					<div id="promo">

						<h2 id="secondary-marketing"><a href="#"><span></span>Houston Airport System Parking</a></h2>
                                                <p><a href="#"><img src="${pageContext.request.contextPath}/meta/media/home/secondary-marketing.jpg" width="299" height="157" alt="You can now use your EZ TAG to park at any Houston Airport System Airport. Find out more." /></a></p>

					</div> <!-- end of promo -->


					<div id="quick-links-section">

						<h2 id="quick-links"><span></span>Quick Links</h2>

						<ul>
							<li class="alert"><a href="${pageContext.request.contextPath}/violatorLoginDisplay.do">Unpaid Tolls &amp; Violations</a></li>

							<li><a href="${pageContext.request.contextPath}/mailedTagActivationDisplay.do">Activate Mailed EZ TAGs</a></li>
                                                        <li><a href="#">Road Map</a></li>
							<li><a href="#">Current Rates</a></li>
							<li><a href="#">Roadside Assistance</a></li>
							<li><a href="#">EZ TAG Store Locations</a></li>
							<li><a href="#">Current Projects</a></li>
							<li><a href="#">Airport Parking</a></li>
						</ul>

					</div> <!-- end of quick-links-section -->


				</div> <!-- end of secondary-content-interior -->

			</div> <!-- end of secondary-content -->

		</div> <!-- end of primary-and-secondary-content -->

	</div> <!-- end of content-container -->


<script type="text/javascript">

    function doLogin()
    {
        var strAction = "${pageContext.request.contextPath}/authenticateUserHomepage.do";
        <c:if test="${appDelegate.switchProtocol=='Y' or appDelegate.switchProtocol=='y'}">
          strAction = "https://${appDelegate.domainName}" + "${pageContext.request.contextPath}/AuthenticateUser.do";;
        </c:if>

        document.accountUserForm.action = strAction;

    }

</script>