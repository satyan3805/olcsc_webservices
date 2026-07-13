<%@ include file="/jsp/common/Taglibs.jsp" %>
<%session.removeAttribute("preferencesForm"); %>
	<div id="content-container">

		<div id="content">

			<form name="preferencesForm" action="${pageContext.request.contextPath}/accountInformation/preferenceUpdate.do" method="post">

				<h1 id="account-information">Account Information</h1>
                                <br/><br/>
                                <div class="section">
                                    <logic:messagesPresent message="true" property="alerts">
                                        <dl class="alerts"/>
                                        <dt/>
                                        <html:messages id="msg" message="true" property="alerts">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div> <!-- end of section -->

				<div class="section with-primary-and-secondary-content">
                    					<c:if test="${headAuth}">
                                          <div id="authAlertDiv">
                                            <logic:messagesPresent message="true" property="authAlerts">
                                                 <dl class="alerts"/>
                                                 <dt/>
                                                 <html:messages id="msg" message="true" property="authAlerts">
                                                    <dd>${msg}</dd>
                                                 </html:messages>
                                            </logic:messagesPresent>
                                          </div>
                                        </c:if>

					<%@ include file="/jsp/includes/manage/account-information-active-inactive-tags.jsp" %>

					<div class="primary-section-content with-multiple-definition-lists">

						<h4 id="personal-information">Contact Information</h4>

						<dl id="login-information" class="immediately-following-an-h4">

							<dt>User name: </dt>
                                <dd>${acctLoginInfo.loginId}</dd>
								<dd>I want to ...</dd>
								<dd>
								<c:choose>
								<c:when test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">
								change my user name
								</c:when>
								<c:otherwise>
								<a href="${pageContext.request.contextPath}/accountInformation/dispChangeUserName.do">change my user name</a>
								</c:otherwise>
								</c:choose>
								</dd>
								<dd>
								<c:choose>
								<c:when test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">
								change my password
								</c:when>
								<c:otherwise>
								<a href="${pageContext.request.contextPath}/accountInformation/dispChangePassword.do">change my password</a>
								</c:otherwise>
								</c:choose>
								</dd>
								<dd>
								<c:choose>
								<c:when test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">
								update my security question
								</c:when>
								<c:otherwise>
								<a href="${pageContext.request.contextPath}/accountInformation/dispChangeSecQn.do">update my security question</a>
								</c:otherwise>
								</c:choose>
								</dd>

								<dd>
                                                                <c:if test="${acctInfo.revenueAccount}" >
                                                                   <c:if test="${(billingInfo.billingType != 'INVOICE')}">
								<c:choose>
								<c:when test="${acctLoginInfo.acctActivity eq 'I'}" >close my account
								</c:when>
								<c:otherwise>
								<a title="Close Account" href="${pageContext.request.contextPath}/accountInformation.do?accountClosure=true">close my account</a>
								</c:otherwise>
								</c:choose>
                                                                </c:if>
                                                                 </c:if>

								</dd>

						</dl> <!-- end of login-information -->

						<dl id="user-information">

							<dt>${acctInfo.firstName} ${acctInfo.lastName}
                                                            <c:if test="${!((acctInfo.suspensionFlag) and (acctInfo.violationFlag))}">
															<c:if test="${sessionScope.acctLoginInfo.acctActivity ne 'I'}">
                                                                <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your personal information" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation/dispChangeContactInfo.do'; return false" />
																</c:if>
                                                            </c:if>
                                                            </dt>
								<dd>${acctInfo.emailAddress}</dd>
								<dd>${acctInfo.homePhoNbr}</dd>
								<dd>${acctInfo.workPhoNbr}<c:if test="${acctInfo.workPhoExtSet}"> x${acctInfo.workPhoExt}</c:if></dd>
								<dd>${acctInfo.driverLicState} DL: ${acctInfo.driverLicDisplay}</dd>

						</dl> <!-- end of user-information -->

						<dl id="mailing-address">

							<dt>Mailing Address:
                                                            <c:if test="${!((acctInfo.suspensionFlag) and (acctInfo.violationFlag))}">
															<c:if test="${sessionScope.acctLoginInfo.acctActivity ne 'I'}">
                                                                <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your mailing address" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation/dispChangeMailingAddr.do'; return false;" />
																</c:if>
                                                            </c:if>
                                                        </dt>
								<dd>
									<address>
<etcc-extended:format address="${acctInfo}" displayName="false" />
									</address>
								</dd>

						</dl> <!-- end of mailing-address -->
						<!-- Adding Authorized Contacts Link -->
						<dl id="update-authorized-contacts">
							<c:set var="whichPage" value="update-authorized-contact-page" scope="session" />
							<dt>Authorized Contacts:
							<%--<a href="${pageContext.request.contextPath}/addAuthorizedContact.do">update authorized contacts</a>--%>
							<c:if test="${!(acctInfo.suspensionFlag and acctInfo.violationFlag)
							and (sessionScope.acctLoginInfo.acctActivity ne 'I')
							and not sessionScope.pwdAuthAlert}">
							   <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit Authorized Contacts" onclick="document.location.href='${pageContext.request.contextPath}/addAuthorizedContact.do?method=get&preq=true'; return false;" />
							</c:if>
							 </dt>
  <dd>
	<c:forEach items="${contactInfoForm.authorizedContacts}" var="contact" varStatus="contactStatus">
		<c:if test="${contactStatus.index == 2 && !contactStatus.last}">
			<div id="authorized-contacts" style="display:none;">
		</c:if>
		${contact.firstName} ${contact.lastName}<br />
		<c:if test="${contactStatus.last && contactStatus.index > 2}">
			</div>
            <a id="toggle-authorized-contacts" href="#" onclick="return toggleAuthContacts(this);">more...</a>
		</c:if>
	</c:forEach>
  </dd>
</dl>
						<!-- End of adding Authorized Contacts Link -->

					</div> <!-- end of primary-section-content -->

					<div class="secondary-section-content with-multiple-definition-lists">
						<c:if test="${acctInfo.revenueAccount}" >
						<h4 id="billing-information">Billing Information</h4>

						<dl id="payment-method" class="immediately-following-an-h4">
							<dt>Payment Method:
                                                            <c:if test="${!((acctInfo.suspensionFlag) and (acctInfo.violationFlag))}">
															<c:if test="${sessionScope.acctLoginInfo.acctActivity ne 'I'}">
                                                                <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your payment method" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation/dispChangePaymentMethod.do'; return false;" />
															</c:if>
                                                            </c:if>
                                                        </dt>
      <dd>
      <etcc-extended:format billingInfo="${billingInfo}" mask="ALL" />
      </dd>

						</dl> <!-- end of payment-method -->

						<c:if test="${(billingInfo.creditCard)}">
                                                <dl id="billing-address">
							<dt>Billing Address:
                                                            <c:if test="${!((acctInfo.suspensionFlag) and (acctInfo.violationFlag))}">
															<c:if test="${sessionScope.acctLoginInfo.acctActivity ne 'I'}">
                                                                <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your billing address" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation/dispChangeBillingAddress.do'; return false;" />
															</c:if>
                                                            </c:if>
                                                        </dt>
                                                        <dd>
                                                                <address>
<etcc-extended:format addressContainer="${billingInfo}" displayName="false" />
                                                                </address>
                                                        </dd>
						</dl>
                                                </c:if>

						<c:if test="${(billingInfo.billingType != 'INVOICE')}">
                                                <dl class="rebill-ammount">
							<dt>Rebill Amount:
                                                            <c:if test="${!((acctInfo.suspensionFlag) and (acctInfo.violationFlag))}">
															<c:if test="${sessionScope.acctLoginInfo.acctActivity ne 'I'}">
                                                                <input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/edit.gif" value="Edit your preferences" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation/dispChangeRebillAmt.do'; return false;" />
															</c:if>
                                                            </c:if>
                                                        </dt>
                         <c:if test="${(billingInfo.billingType  == 'EFT')}">


								<dd>Your EFT will be re-billed $<fmt:formatNumber value="${acctInfo.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/></dd>

						  </c:if>


						  <c:if test="${(billingInfo.billingType  == 'CREDIT')}">


								<dd>Your Credit Card will be re-billed $<fmt:formatNumber value="${acctInfo.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/></dd>

						  </c:if>

						<dd>any time your balance reaches $<fmt:formatNumber value="${acctInfo.lowBalLevel}" minFractionDigits="2" maxFractionDigits="2"/> or below.</dd>
						</dl> <!-- end of rebill-amount -->
                                                </c:if>
					</c:if>
					</div> <!-- end of secondary-section-content -->

				</div> <!-- end of section with-primary-and-secondary-content -->

				<div class="section">

					<h2 id="preferences">Preferences</h2>
						<c:if test="${acctInfo.revenueAccount}" >
						<h3 id="toll-road-interoperability">Toll Road Interoperability</h3>

						<p><strong>NTTA:</strong> The EZ TAG will allow you non-stop passage on toll roads in and around the Dallas/Ft. Worth Metroplex including: Dallas North Tollway, President George Bush Turnpike, Addison Airport Toll Tunnel and Mountain Creek Lake Bridge. <em>EZ TAGs are NOT operable at Dallas&#39; airports</em>.</p>

						<p><strong>Central Texas Turnpike Authority:</strong> Opened to traffic in Fall 2006, Austin now hosts several bypass toll roads and is constructing more. Current roadways include: SH 130, SH 45 North and Loop 1.</p>

						<p><strong>METRO HOT Lanes:</strong> Your EZ TAG will allow you to access METRO's HOT (HOV) lanes during specified hours, as a single occupancy vehicle for a toll.</p>

						<p><strong>Fort Bend Grand Parkway Toll Road Authority:</strong> Your EZ TAG will allow you access to toll lanes operated by the Fort Bend Grand Parkway Toll Road Authority.</p>

						<!--<p><strong>Houston Airport System Parking:</strong> Starting Monday, June 21, 2010, EZ TAGs will not raise entry gates at airport parking locations. Click <a href="${appDelegate.domainName}/about_news/ez-tag-airport-parking-program-comes-to-an-end/" id="hctra_HAS_news">here</a> for more information.</p> -->
						<p>The boxes checked below represent facilities that will recognize and bill your EZ TAG(s). Please uncheck the facilities and road systems for which you do not want to use your EZ TAG(s).</p>

						<table class="interoperability data-table">
							<thead>
                                                                <tr>
                                                                        <!--<td></td>
                                                                        <td></td> -->
                                                                        <!-- <c:if test="${preferencesDTO.houstonAirportAvailable}">
                                                                            <th scope="col"><a id="houston-airport-system" href="http://www.fly2houston.com/" target="_blank"><span></span>Houston Airport System</a></th>
                                                                        </c:if> -->
                                                                        <th scope="col"><a id="ntta" href="http://www.ntta.org/" target="_blank"><span></span>NTTA</a></th>
                                                                        <th scope="col"><a id="tx-dot" href="http://www.txtag.org/" target="_blank"><span></span>Texas DOT</a></th>
                                                                        <th scope="col"><a id="ctrma"  href="http://www.mobilityauthority.com/" target="_blank"><span></span>CTRMA</a></th>
																		<th scope="col"><a id="metro"  href="http://www.ridemetro.org/HotLanes" target="_blank"><span></span>METRO</a></th>
																		<th scope="col"><a id="fbgptra"  href="http://www.fbctra.com/" target="_blank"><span></span>FBGPTRA</a></th>
                                                                </tr>
                                                        </thead>
                                                        <tbody>
                                                                <tr class="select-all">
                                                                        <!--<th scope="row"><em>select all</em></th>
                                                                        <td></td>-->
                                                                        <c:forEach var="iop" items="${preferencesDTO.iopSettings}" varStatus="varStatus">

                                                                              <c:if test="${(iop.agcyAbbrev!='HAS') && (not empty iop.agcyAbbrev)}">
                                                                            <td scope="col">
                                                                            <input type="checkbox" class="checkbox" id="iop${varStatus.index}"
                                                                            name="iop${varStatus.index}" <c:if test="${iop.active}">checked="checked"</c:if>
                                                                            <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if>/>
                                                                            <input type="hidden" id="iopPreference[${varStatus.index}].acctId" name="iopPreference[${varStatus.index}].acctId" value="${iop.acctId}"/>
                                                                            <input type="hidden" name="iopPreference[${varStatus.index}].agcyId" value="${iop.agcyId}"/>
                                                                            <input type="hidden" name="iopPreference[${varStatus.index}].agcyAbbrev" value="${iop.agcyAbbrev}"/>
                                                                            <input type="hidden" name="iopPreference[${varStatus.index}].agcyName" value="${iop.agcyName}"/>
                                                                            </td>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <!--
                                                                        <td scope="col"><input type="checkbox" class="checkbox" name="iop1" checked="checked" /></td>
                                                                        <td scope="col"><input type="checkbox" class="checkbox" name="iop2" checked="checked" /></td>
                                                                        <td scope="col"><input type="checkbox" class="checkbox" name="iop3" checked="checked" /></td>
                                                                        -->
                                                                </tr>
                                                        </tbody>
						</table>
						</c:if>
						<h3 id="hctra-ez-tag-communication-preferences">Communication Preferences</h3>

						<p>We want to stay in touch in ways that you find helpful. Select from the options below and click the &ldquo;Save Changes&rdquo; button when you are finished. We'll send you an e-mail confirming your selections.</p>

						<p>Please note you <em>cannot</em> opt-out of account essential e-mails including, but not limited to, EZ TAG order status, low-balance, and negative-balance messages.</p>

						<dl>
							<dt>I would like monthly statements:</dt>

    <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
    <c:if test="${preference.section==1 && preference.notificationType != 'ER'}">
      <c:if test="${acctInfo.revenueAccount}" >
        <dd>
        <input type="checkbox" class="checkbox" id="pref${varStatus.index}"
        name="pref${varStatus.index}" <c:if test="${preference.active}">checked="checked"</c:if>
        <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if>/> ${preference.description}
        <input type="hidden" id="userPreference[${varStatus.index}].acctId" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
        <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
        <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
        <input type="hidden" name="userPreference[${varStatus.index}].section" value="1"/>
      </dd>
      </c:if>
      <c:if test="${not acctInfo.revenueAccount && varStatus.index ne '2'}">
          <dd>
          <input type="checkbox" class="checkbox" id="pref${varStatus.index}" name="pref${varStatus.index}" <c:if test="${preference.active}">checked="checked"</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if>/> ${preference.description}
          <input type="hidden" id="userPreference[${varStatus.index}].acctId" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
          <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
          <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
          <input type="hidden" name="userPreference[${varStatus.index}].section" value="1"/>
        </dd>
      </c:if>
                                                                </c:if>
                                                            </c:forEach>
						</dl>

						<dl>
							<dt>I would like alerts via e-mail on the following topics:</dt>
                                                            <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
                                                                <c:if test="${preference.section==2}">
                                                                    <dd><input type="checkbox" class="checkbox" id="pref${varStatus.index}" name="pref${varStatus.index}" <c:if test="${preference.active}">checked="checked"</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}">disabled </c:if>/> ${preference.description}
                                                                        <input type="hidden" id="userPreference[${varStatus.index}].acctId" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
                                                                        <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
                                                                        <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
                                                                        <input type="hidden" name="userPreference[${varStatus.index}].section" value="2"/>
                                                                    </dd>
                                                                </c:if>
                                                            </c:forEach>
						</dl>

						<dl>
							<dt>I would like e-mail messages from HCTRA in the following format:</dt>
                                                            <dd><label for="html-messages"><input type="radio" class="radio-button" id="html-messages" name="emailFormat" value="HTML" <c:if test="${preferencesDTO.emailFormat=='HTML'}">checked="checked"</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if>/> Send HTML messages (pictures as well as text).</label></dd>
                                                            <dd><label for="text-only-messages"><input type="radio" class="radio-button" id="text-only-messages" name="emailFormat" value="TEXT" <c:if test="${preferencesDTO.emailFormat=='TEXT'}">checked="checked"</c:if> <c:if test="${sessionScope.acctLoginInfo.acctActivity eq 'I'}"> disabled </c:if>/> Send text-only messages.</label></dd>
						</dl>

                                                <dl>
                                                    <dd>
                                                        Disclaimer: The Harris County Toll Road wants you to benefit from up-to-date toll road information that is essential to you as an EZ TAG account-holder.
                                                        Therefore, we reserve the right to occasionally distribute to you via e-mail vital information regarding EZ TAG Accounts, stores, and services.
                                                    </dd>
                                                </dl>


					</div>  <!-- end of section -->

					<ul class="form-actions">
						<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="javascript:document.preferencesForm.reset();return false"/></li>

						<li><input id="save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:preferencesSave();" /></li>
					</ul> <!-- end of form-action -->

			</form>

			<div id="tertiary-navigation-and-or-page-controls">

				<ul id="page-controls">

					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
					<li><a style="display:none" href="#" class="print">print</a></li>
					<li><a style="display:none" href="#" class="print-preview">print preview</a></li>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->

		</div> <!-- end of content -->

		<jsp:include page="/accountInfo.do"/>

	</div> <!-- end of content-container -->

<script type="text/javascript">
<c:if test="${setEvent3}">
    s.events="event3";
</c:if>

<c:if test="${sitePrefonLoad}">
var acNum="";
s.events="";
<c:choose>
<c:when test="${preferencesDTO.houstonAirportAvailable}">
if(document.getElementById("iop0")!='undefined')
  {
    if(document.getElementById("iop0").checked)
     {

     acNum=document.getElementById("iopPreference[0].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event34";
     else
       s.events = "event34";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event35";
     else
       s.events = "event35";
   }
  }
  if(document.getElementById("iop1")!='undefined')
  {
    if(document.getElementById("iop1").checked)
     {

     acNum=document.getElementById("iopPreference[1].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event36";
     else
       s.events = "event36";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event37";
     else
       s.events = "event37";
   }
  }
 if(document.getElementById("iop2")!='undefined')
  {
    if(document.getElementById("iop2").checked)
     {

     acNum=document.getElementById("iopPreference[2].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event38";
     else
       s.events = "event38";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event39";
     else
       s.events = "event39";
   }
  }
 if(document.getElementById("iop3")!='undefined')
  {
    if(document.getElementById("iop3").checked)
     {

     acNum=document.getElementById("iopPreference[3].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event40";
     else
       s.events = "event40";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event41";
     else
       s.events = "event41";
   }
  }
</c:when>
<c:otherwise>
if(document.getElementById("iop0")!='undefined')
  {
    if(document.getElementById("iop0").checked)
     {

     acNum=document.getElementById("iopPreference[0].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event36";
     else
       s.events = "event36";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event37";
     else
       s.events = "event37";
   }
  }
  if(document.getElementById("iop1")!='undefined')
  {
    if(document.getElementById("iop1").checked)
     {

     acNum=document.getElementById("iopPreference[1].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event38";
     else
       s.events = "event38";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event39";
     else
       s.events = "event39";
   }
  }
  if(document.getElementById("iop2")!='undefined')
  {
    if(document.getElementById("iop2").checked)
     {

     acNum=document.getElementById("iopPreference[2].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event40";
     else
       s.events = "event40";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event41";
     else
       s.events = "event41";
   }
  }
</c:otherwise>
</c:choose>
  if(document.getElementById("pref0")!='undefined')
  {
    if(document.getElementById("pref0").checked)
     {

     acNum=document.getElementById("userPreference[0].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event16";
     else
       s.events = "event16";
   } else
   {
     if((s.events).length >0)
        s.events = s.events + ",event17";
     else
       s.events = "event17";
   }
  }
  if(document.getElementById("pref1")!='undefined')
  {
    if(document.getElementById("pref1").checked)
    {
     acNum=document.getElementById("userPreference[1].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event18";
     else
        s.events = "event18";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event19";
     else
        s.events = "event19";
    }
  }
  <c:choose>
  <c:when test="${prefChange}" >
  if(document.getElementById("pref2")!='undefined')
  {
    if(document.getElementById("pref2").checked)
    {
     acNum=document.getElementById("userPreference[2].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event32";
     else
        s.events = "event32";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event33";
     else
        s.events = "event33";
    }
  }
 if(document.getElementById("pref3")!='undefined')
  {
    if(document.getElementById("pref3").checked)
    {
     acNum=document.getElementById("userPreference[3].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event20";
     else
        s.events = "event20";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event21";
     else
        s.events = "event21";
    }
  }
 if(document.getElementById("pref4")!='undefined')
  {
    if(document.getElementById("pref4").checked)
    {
     acNum=document.getElementById("userPreference[4].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event22";
     else
        s.events = "event22";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event23";
     else
        s.events = "event23";
    }
  }
 if(document.getElementById("pref5")!='undefined')
  {
    if(document.getElementById("pref5").checked)
    {
     acNum=document.getElementById("userPreference[5].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event24";
     else
        s.events = "event24";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event25";
     else
        s.events = "event25";
    }
  }
  if(document.getElementById("pref6")!='undefined')
  {
    if(document.getElementById("pref6").checked)
    {
     acNum=document.getElementById("userPreference[6].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event26";
     else
        s.events = "event26";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event27";
     else
        s.events = "event27";
    }
  }
  if(document.getElementById("pref7")!='undefined')
  {
    if(document.getElementById("pref7").checked)
    {
     acNum=document.getElementById("userPreference[7].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event28";
     else
        s.events = "event28";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event29";
     else
        s.events = "event29";
    }
  }
  </c:when>
  <c:otherwise>
if(document.getElementById("pref2")!='undefined')
  {
    if(document.getElementById("pref2").checked)
    {
     acNum=document.getElementById("userPreference[2].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event20";
     else
        s.events = "event20";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event21";
     else
        s.events = "event21";
    }
  }
 if(document.getElementById("pref3")!='undefined')
  {
    if(document.getElementById("pref3").checked)
    {
     acNum=document.getElementById("userPreference[3].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event22";
     else
        s.events = "event22";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event23";
     else
        s.events = "event23";
    }
  }
 if(document.getElementById("pref4")!='undefined')
  {
    if(document.getElementById("pref4").checked)
    {
     acNum=document.getElementById("userPreference[4].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event24";
     else
        s.events = "event24";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event25";
     else
        s.events = "event25";
    }
  }
  if(document.getElementById("pref5")!='undefined')
  {
    if(document.getElementById("pref5").checked)
    {
     acNum=document.getElementById("userPreference[5].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event26";
     else
        s.events = "event26";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event27";
     else
        s.events = "event27";
    }
  }
  if(document.getElementById("pref6")!='undefined')
  {
    if(document.getElementById("pref6").checked)
    {
     acNum=document.getElementById("userPreference[6].acctId").value;
     if((s.events).length >0)
        s.events = s.events + ",event28";
     else
        s.events = "event28";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event29";
     else
        s.events = "event29";
    }
  }
  </c:otherwise>
  </c:choose>
   if(document.getElementById("html-messages").checked)
    {
     if((s.events).length >0)
        s.events = s.events + ",event30";
     else
        s.events = "event30";
    }else
    {
     if((s.events).length >0)
        s.events = s.events + ",event31";
     else
        s.events = "event31";
    }
s.products =";Preferences changes;Accountnumber-"+acNum;
s.eVar2="EZ Account - Preferences";
s.eVar18=""+acNum;
</c:if>
function get_radio_value()
{
for (var i=0; i < document.forms[0].paymentType.length; i++)
   {
   if (document.forms[0].paymentType[i].checked)
      {
      var rad_val = document.forms[0].paymentType[i].value;
	  return rad_val;
      }
   }
}
function inactivateEZTagsEFT()
{
	//return;

		var refund = 'E';

		var balAmt = parseFloat('<c:out value="${acctInfo.balanceAmt}"/>');
		//alert(" balAmt" + balAmt)
		 if(balAmt > 0){
			document.forms[0].action = "${pageContext.request.contextPath}/accountInactivateAllVehicles.do?refund="+refund;
			document.forms[0].submit();
		}else{
			return;
		}
}

function inactivateEZTags()
{
	//return;
	//alert( get_radio_value());
		var refund = get_radio_value();

		var balAmt = parseFloat('<c:out value="${acctInfo.balanceAmt}"/>');
		//alert(" balAmt" + balAmt)
		 if(balAmt > 0){
			document.forms[0].action = "${pageContext.request.contextPath}/accountInactivateAllVehicles.do?refund="+refund;
			document.forms[0].submit();
		}else{
			return;
		}
}
function inactivateEZTagsZero(val)
{
               var refund=val;
		var balAmt = parseFloat('<c:out value="${acctInfo.balanceAmt}"/>');

		 if(balAmt == 0){
			document.forms[0].action = "${pageContext.request.contextPath}/accountInactivateAllVehicles.do?refund="+refund;
			document.forms[0].submit();
		}else{
			return;
		}
}

function popUp(URL) {
	day = new Date();
	id = day.getTime();
	eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=700,height=350');");
}
function dontshowrefund(){
	document.getElementById("agreement").style.display = "none";
	document.getElementById("agreement-image").style.display = "none";
	document.getElementById("agreement-image-text").style.display = "none";
	document.getElementById("refund").style.display = "";
	resizeText(-1);
	//document.forms[0].action = "${pageContext.request.contextPath}/accountInactivateAllVehicles.do?refund="+refund;
	//document.forms[0].submit();
}

function dontshowrefundFornegativeBal(){
	document.getElementById("agreement").style.display = "none";
	document.getElementById("agreement-image").style.display = "none";
	document.getElementById("agreement-image-text").style.display = "none";
	document.getElementById("refund").style.display = "";
	//document.forms[0].action = "${pageContext.request.contextPath}/accountInactivateAllVehicles.do?refund="+refund;
	//document.forms[0].submit();
}

function showrefund(){

	resizeText(-1);
	document.getElementById("agreement").style.display = "none";
	document.getElementById("agreement-image").style.display = "none";
	document.getElementById("agreement-image-text").style.display = "none";
	document.getElementById("refund").style.display = "";


}

function resizeText(multiplier) {

	  document.body.style.fontSize =  "75%";
}


function alertChange(alertID1,alertID2)
{
   document.getElementById(alertID1).style.display = "";
   document.getElementById(alertID2).style.display = "none";
}

function toggleAuthContacts(link) {
    var e = document.getElementById("authorized-contacts");
    if (e.style.display == "none") {
        e.style.display = "";
        link.innerHTML = "less...";
    } else {
        e.style.display = "none";
        link.innerHTML = "more...";
    }
    return false;
}
</script>
