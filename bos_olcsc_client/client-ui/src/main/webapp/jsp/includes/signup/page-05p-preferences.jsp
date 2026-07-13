<%@ include file="/jsp/common/Taglibs.jsp" %>
	<div id="content-container">

		<div id="content">

                        <etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/signupPreferenceUpdate.do">

				<h1 id="preferences">Preferences</h1>
                                
                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="true" property="prefUpdateFailed">
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>

				<div class="section">

					<h2 id="toll-road-interoperability">Toll Road Interoperability</h2>

											<p><strong>NTTA:</strong> The EZ TAG will allow you non-stop passage on toll roads in and around the Dallas/Ft. Worth Metroplex including: Dallas North Tollway, President George Bush Turnpike, Addison Airport Toll Tunnel and Mountain Creek Lake Bridge. <em>EZ TAGs are NOT operable at Dallas&#39; airports</em>.</p>


											<p><strong>Central Texas Turnpike Authority:</strong> Opened to traffic in Fall 2006, Austin now hosts several bypass toll roads and is constructing more. Current roadways include: SH 130, SH 45 North and Loop 1.</p>
											
											<p><strong>METRO HOT Lanes:</strong> Your EZ TAG will allow you to access METRO's HOT (HOV) lanes during specified hours, as a single occupancy vehicle for a toll.</p>
											
											<p><strong>Fort Bend Grand Parkway Toll Road Authority:</strong> Your EZ TAG will allow you access to toll lanes operated by the Fort Bend Parkway Toll Road Authority.</p>

<!--  											<p><strong>Houston Airport System Parking:</strong> Beginning in early 2007, the EZ TAG will be an easier alternative to entering and exiting AND paying for parking at City of Houston parking garages and lots at Bush Intercontinental Airport and Hobby Airport. These areas include Terminal parking garages A/B, C and D/E at Bush, terminal parking at Hobby and Parking Cents long term, remote lots at Bush and Hobby. Regular parking rates [hyperlink to parking page on HAS site] will apply. In order to experience this benefit, you must have a valid credit card associated with your EZ Account. The parking charges will be charged directly to the credit card and NOT deducted from your EZ Account balance.
					</p>-->
											<p>The boxes checked below represent facilities that will recognize and bill your EZ TAG(s). Please uncheck the facilities and road systems for which you do not want to use your EZ TAG(s).</p>

											<table class="interoperability data-table">
												<thead>
													<tr>
														<!--<td></td>
														<td></td> -->
                                                                                                        <!--         <c:if test="${preferencesDTO.houstonAirportAvailable}">
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
                                                                                                                    <td scope="col"><input type="checkbox" class="checkbox" name="iop${varStatus.index}" <c:if test="${iop.active}">checked="checked"</c:if> />
                                                                                                                    <input type="hidden" name="iopPreference[${varStatus.index}].acctId" value="${iop.acctId}"/>
                                                                                                                    <input type="hidden" name="iopPreference[${varStatus.index}].agcyId" value="${iop.agcyId}"/>
                                                                                                                    <input type="hidden" name="iopPreference[${varStatus.index}].agcyAbbrev" value="${iop.agcyAbbrev}"/>
                                                                                                                    <input type="hidden" name="iopPreference[${varStatus.index}].agcyName" value="${iop.agcyName}"/>
                                                                                                                    </td> </c:if>
                                                                                                                </c:forEach>
                                                                                                                <!--
														<td scope="col"><input type="checkbox" class="checkbox" name="iop1" checked="checked" /></td>
														<td scope="col"><input type="checkbox" class="checkbox" name="iop2" checked="checked" /></td>
														<td scope="col"><input type="checkbox" class="checkbox" name="iop3" checked="checked" /></td>
                                                                                                                -->
													</tr>
												</tbody>
											</table>

				</div> <!-- end of section -->

				<div class="section">

					<h2 id="hctra-ez-tag-communication-preferences">HCTRA EZ-TAG Communication Preferences</h2>

					<p>We want to stay in touch in ways that you find helpful. Select from the options below and click the &ldquo;Save Changes&rdquo; button when you are finished. We'll send you an e-mail confirming your selections.</p>

					<p>Please note you <em>cannot</em> opt-out of account essential e-mails including, but not limited to, EZ TAG order status, low-balance, and negative-balance messages.</p>

					<dl>
						<dt>I would like monthly statements:</dt>
                                                        <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
                                                             <!-- 
															 <c:if test="${preference.section==1}">
                                                                <dd> 
                                                                    <input type="checkbox" class="checkbox" name="pref${varStatus.index}"                                                                      
                                                                      <c:if test="${preference.active}">checked="checked"</c:if>/> ${preference.description}
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].section" value="1"/>
                                                                      
                                                                </dd>
                                                            </c:if>     
                                                            -->
                                                            
                                                                                                                      
                                                            <c:set var="theString" value="${preference.description}"/> 
                                                            <c:set var="isHobby" value="${!fn:contains(theString, 'Hobby airports')}"/>                                                              
                                                            <c:if test="${preference.section==1 && isHobby}">                                                                  
                                                                <dd> 
                                                                    <input type="checkbox" class="checkbox" name="pref${varStatus.index}"                                                                      
                                                                      <c:if test="${preference.active}">checked="checked"</c:if>/> ${preference.description}
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].section" value="1"/>
                                                                      
                                                                </dd>
                                                             
                                                             
                                                            </c:if>
                                                            
                                                            
                                                            
                                                            
                                                                                                            
                                                        </c:forEach>
                                                        <!--
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Mail me printed monthly statements.</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> An e-mail alert when a new monthly statement is available online</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> An e-mail alert new monthly invoice is available online [this only appears for corporate invoice patrons]</dd>
                                                        -->

					</dl>


					<dl>
						<dt>I would like alerts via e-mail on the following topics:</dt>
                                                        <c:forEach var="preference" items="${preferencesDTO.preferences}" varStatus="varStatus">
                                                            <c:if test="${preference.section==2}">
                                                                <dd><input type="checkbox" class="checkbox" name="pref${varStatus.index}" <c:if test="${preference.active}">checked="checked"</c:if>/> ${preference.description}
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].acctId" value="${preference.acctId}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].notificationType" value="${preference.notificationType}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].description" value="${preference.description}"/>
                                                                    <input type="hidden" name="userPreference[${varStatus.index}].section" value="2"/>
                                                                </dd>
                                                            </c:if>
                                                        </c:forEach>
                                                        <!--
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Changes in tolls and rates</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Research surveys (a great opportunity to tell us how well we meet your needs)</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Planned ramp and lane improvements and/or closures (engineering and construction)</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Legal notices about the tollway and/or EZ-TAG&rsquo;s terms and conditions. (Please note: if you choose not to receive legal notices by e-mail, you will need to check our Web site to stay updated on policy changes.)</dd>
							<dd><input type="checkbox" class="checkbox" name="alerts" /> Press releases and general customer communications</dd>
                                                        -->
					</dl>

					<dl>
						<dt>I would like e-mail messages from HCTRA in the following format:</dt>

							<dd><label for="html-messages"><input type="radio" class="radio-button" id="html-messages" name="emailFormat" value="HTML" <c:if test="${preferencesDTO.emailFormat=='HTML'}">checked="checked"</c:if>/> Send HTML messages (pictures as well as text).</label></dd>
							<dd><label for="text-only-messages"><input type="radio" class="radio-button" id="text-only-messages" name="emailFormat" value="TEXT" <c:if test="${preferencesDTO.emailFormat=='TEXT'}">checked="checked"</c:if>/> Send text-only messages.</label></dd>
					</dl>

				</div> <!-- end of section -->


				<ul class="form-actions">

				<li><input id="cancel-do-not-save-changes" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="javascript:goToConfirmation();return false;"/></li>

				<li><input id="save-changes" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="javascript:saveChanges();return false;"/></li>

				</ul> <!-- end of form-action -->

			</etcc-extended:autocompleteOffForm>

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-4-of-4">
			Preferences
			<em>Step 4 of 4</em>
		</p>

	</div> <!-- end of content-container -->
        
<script type="text/javascript">   
var submitted = false;
function goToConfirmation()
{
    if (!submitted)
    {
        submitted = true;
        document.preferencesForm.action ="${pageContext.request.contextPath}/signupConfirmationDisplay.do";
        document.preferencesForm.submit();
    }
}

function saveChanges()
{
    if (!submitted)
    {
        submitted = true;
        document.preferencesForm.submit();
    }
}

</script>
