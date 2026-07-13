<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="stateDelegate"  class="com.etcc.csc.delegate.StateDelegate" scope="page"/>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<div id="content-container">

            <form id="login-violator" action="${pageContext.request.contextPath}/violatorLogin.do" method="post" autocomplete="off">

            <div id="primary-and-secondary-content">
                    
                    <div id="primary-content">

                            <div class="section">

                                    <h1>Toll Violations</h1>
                                    
                                    <div>
                                        <logic:messagesPresent message="false">
                                            <dl class="errors"/>
                                            <html:messages id="msg" message="false">
                                                <dd><bean:write name="msg"/></dd>
                                            </html:messages>
                                        </logic:messagesPresent>
                                        <logic:messagesPresent message="true">
                                            <dl class="errors"/>
                                            <html:messages id="msg" message="true" property="violationError">
                                                <dd>${msg}</dd>
                                            </html:messages>
                                        </logic:messagesPresent>
                                    </div>
                                    <h2 id="Violation-def">What is a Toll Violation?</h2>
                                    <p>Pursuant to the Texas Transportation Code, Chapter 284, the vehicle owner or
                                       renter of record (not necessarily the driver) of a vehicle that passes through a toll
									   facility is responsible for payment of all tolls. Failure to pay tolls is against the law
									   and results in a violation invoice and/or referral to a collection firm. Toll
									   violations may also result in issuance of a traffic citation, independent of an
									   invoice, which carries a significant fine plus court costs.</p>  
                                    <h2 id="pay-violations-online">Pay your Violation Invoices Online</h2>
                                    <p>In most cases you may pay your violation invoices online. Provide the information listed below to continue.</p>

                                    <p id="unpaid-violation-image-harris">Be sure to use the license plate and invoice number listed on your invoice.
<a href="${pageContext.request.contextPath}/home/sampleInvoiceDisplay.do" onClick="return openNewWin(this.href, 'olvps_sample_invoice');">Help me find this information on my invoice.</a></p>

                            <fieldset>
                                    <dl>
                                                    <dt class="first-dt-dd-pair"><label for="license-plate-state">License Plate State:</label></dt>
                                                            <dd>
                                                                    <select id="license-plate-state" name="licPlateState" tabindex="1">
                                                                        <c:forEach var="state" items="${stateDelegate.states}">
                                                                            <option value="<c:out value="${state.stateCode}"/>"
                                                                                <c:if test="${state.defaultValueFlag == true}">
                                                                                    selected
                                                                                </c:if>
                                                                            >
                                                                             <c:out value="${state.stateName}"/>
                                                                            </option>
                                                                        </c:forEach> 										
                                                                    </select>
                                                            </dd>



                                                    <dt class="first-dt-dd-pair"><label for="license-number">License Plate Number:</label></dt>
                                                            <dd class="first-dt-dd-pair">
                                                                    <input type="text" class="textfield" id="license-number" name="licPlateNbr" value="${violatorForm.map.licPlateNbr}" tabindex="2" />
                                                                    <p class="help">Be sure to use the license plate listed on the invoice.</p>
                                                            </dd>

                                                    <dt class="first-dt-dd-pair"><label for="invoice">Invoice Number:</label></dt>
                                                            <dd class="first-dt-dd-pair">
                                                                    <input type="text" class="textfield" id="invoice" name="invoiceId" value="${violatorForm.map.invoiceId}" tabindex="3" />
                                                                    <p class="help">Example: 01234567890</p>
<%-- For GPW handling (not currently implemented) this will be optional (remember to update validation.xml)
<p class="help">Note: The Invoice Number is optional.  If not provided, only uninvoiced tolls will be displayed.</p>
--%>
                                                            </dd>
                                    </dl>                            
                            </fieldset>

                    <ul class="form-actions">
                            <c:if test="${not empty requestScope.returnAction}">							
                                <li><input type="image" id="remove-this-vehicle" name="remove-this-vehicle" value="Remove this vehicle from this list" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" onclick="javascript:goBack();return false;" /></li>
                            </c:if>
                            <li><input id="continue" type="image" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Continue" onclick="javascript:doSubmit();return false;" tabindex="3" /></li>
                    </ul> <!-- end of form-actions -->

                                                      <h2>Toll Violation Events</h2>
 													<p>When you access a toll road, payment is expected at the time of the transaction.</p>
 													<p>To make payment for a toll violation event which has not yet been invoiced to you,
 													   please call 281-875-3279 to pay by credit card. Payment may also be made by US check 
  													   or money order at one of our 
  													   <a href="${appDelegate.domainName}/about_locations" title="EZ TAG Store locations page">EZ TAG Store locations</a>, or by mail to the address below.
  													   Because toll payment is due at the time of the transaction, payment for a toll violation event will only be accepted as a <strong>one-time courtesy</strong>
  													   and is intended for drivers who have accidentally entered the toll road without the proper payment method. All future toll transactions
  													   will be processed as part of a Toll Violation Invoice if payment is not received at the time of the transaction, per Texas Transportation Code, Chapter 284.</p>
  													   
  													<p> HCTRA - Payment Processing<br>
  													    7701 Wilshire Place Drive<br>
  													    Houston, TX 77040-5326</p>
  														        
                            						<p>Payment cannot be processed without your license plate number, state and 
                            						   contact information. Include the date, time and location of the event, if possible.
                            						   Please do not send cash!</p>
                            <a href="${appDelegate.domainName}/about_faq/violations">More Information</a>

                                    <!--        
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    -->

                            </div> <!-- end of section -->

                    </div> <!--end of primary-content -->
                    
                    <div id="secondary-content">

                            <div id="secondary-content-interior">
<h2>HEARING PROCESS</h2>
                                    
                                    <p>The Harris County Toll Road Authority has a hearing process in which assumed violators have the opportunity to present their argument to an Administrative Hearing Officer (judge).</p>

<p><a href="#" onClick="return toggleHearingSteps();">
<img src="${pageContext.request.contextPath}/meta/media/icons/hearing.png" alt="Hearing Process" style="vertical-align:middle;background-color:white;" />
What are the hearing process steps?</a></p>
<div id="hearing-process-steps" style="display:none">
                                    <p>For unpaid invoices, the following steps take place:</p>
                                    
                                    <p> <strong>Step #1:</strong> If payment is not received within forty-five (45) days, the Toll Violation Invoice is referred to a collection agency. The collection agency will then add an additional $42 fee. </p>
                                    
                                    <p><strong>Step #2:</strong> For a period of thirty (30) days, the collection agency prepares and mails a series of demand letters and makes attempts to contact the toll violator by telephone. </p>
                                    
                                    <p><strong>Step #3:</strong>If payment is not received within sixty (60) days after the collection agency's efforts have taken place and if the toll violator is eligible for an Administrative Hearing, an additional fee is added to the outstanding amount and a hearing is scheduled.</p>
                                    
                                    <p><strong>Step #4:</strong> During the Administrative Hearing process a judge will listen to arguments presented by the Toll Road Authority and by the toll violator and issue an Order of Judgment. The Order of Judgment will indicate the Judge's ruling and determine the final amount due. As a part of the ruling, the Judge has the option to assess additional fines and costs, flag (block) the vehicle registration, or place a device (boot) on the vehicle prohibiting movement until the outstanding tolls and fees are paid.</p>
                                    
                                    <p><strong>Step #5:</strong>If payment for tolls, charges, fees or costs is not received within thirty-one (31) days of the hearing decision being filed with the County Clerk's office, registration of the vehicle will be blocked.</p>
                                    <p><strong>Note:Failure to appear at the hearing is considered an admission of liability and the hearing officer may issue an order assessing a fine and costs on that basis.</strong> </p>                               
</div> <!-- end of hearing process steps -->

                                    <p><br /><a href="${appDelegate.domainName}/about_contact/">
<img src="${pageContext.request.contextPath}/meta/media/icons/contact.png" alt="Contact Us" style="vertical-align:middle;background-color:white;" />
<strong>Contact Us</strong></a><br />
                                    Customer Service: <bean:message key="HCTRA.telephone.number" /></p>
                                    
                                    <div id="admin-hearing-center" style="display:none">
                                      <p>Administrative Hearing Center<br />
                                      <bean:message key="HCTRA.administrative.hearing.center1" /></p>
									  
									  <!-- 
									  <p>Administrative Hearing Center<br />
                                      <bean:message key="HCTRA.administrative.hearing.center2" /></p>
                                      -->
 
                                    </div>

                            </div> <!--end of secondary-content-interior -->

                    </div> <!--end of secondary-content -->

            </div> <!--end of primary-and-secondary-content -->

                    <input type="hidden" name="returnAction" value="${requestScope.returnAction}"/>
            </form>

    </div> <!-- end of content-container -->
        
<script type="text/javascript"> 
document.forms[0].licPlateNbr.focus();
loadingBody();

function loadingBody(){
	getErrorfields();        
}

function doSubmit(){
	document.forms[0].submit();
}

function checkForErrors(fieldname) {
  return (fieldname != null && fieldname != "" && fieldname.length != 0);
}

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}
function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function getErrorfields(){
	var licPlateNbr = '<html:errors property="licPlateNbr"/>';
	var invoiceId = '<html:errors property="invoiceId"/>';
	if (checkForErrors(licPlateNbr) == true){
		changeTextFieldColor(document.forms[0].licPlateNbr);
	}else{
		changeTextFieldWhite(document.forms[0].licPlateNbr);
	}
	if (checkForErrors(invoiceId) == true){
		changeTextFieldColor(document.forms[0].invoiceId);
	}else{
		changeTextFieldWhite(document.forms[0].invoiceId);
	}
}

function goBack() {
	document.forms[0].action = '${pageContext.request.contextPath}/${requestScope.returnAction}.do';
	document.forms[0].submit();
}

function openNewWin(href, title) {
    //var title = "olvps_help";
    window.open(href, title, "status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=600,height=500");
    return false;
}

function showHearingSteps() {
        document.getElementById("hearing-process-steps").style.display = "";
        return false;
}
function toggleHearingSteps() {
    var e = document.getElementById("hearing-process-steps");
    var s = document.getElementById("admin-hearing-center");
    if (e.style.display == "none") {
        e.style.display = "";
        s.style.display = "";
    } else {
        e.style.display = "none";
        s.style.display = "none";
    }
    return false;
}
</script>
