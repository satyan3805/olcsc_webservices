<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
<%@page import="com.etcc.csc.util.SessionUtil"%>
<%SessionUtil.resetCurrentTabMenuId(session);%>

<c:if test="${(requestScope.fromConfirmation) or (OnlineAccessForm.fromConfirmation)}">
    <c:set var="fromConfirmationVal" value="true"/>
</c:if>
<body onLoad="javascript:loadingBody()">
<div id="content-container">

		<div id="primary-and-secondary-content">
                    <h1 id="set-up-your-online-access">Set up your online access</h1>
                    <div id="primary-content">
			<etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/signupAccountInfo.do">
                                <input type="hidden" name="fromConfirmation" value="${fromConfirmationVal}"/>

                                <div class="section">
					<p>You may open an EZ TAG Account online using the form below and the <br> EZ TAG(s) will be mailed to you or download a copy of <a href="${appDelegate.domainName}/about_forms/" title="PDF version of the EZ TAG Account Application">the EZ TAG Account application form</a> to complete and bring to any of our <a href="${appDelegate.domainName}/about_locations" title="EZ TAG Store locations page">EZ TAG Store locations</a>.</p>
	<p>If you already have an existing account, then you can <a href="${pageContext.request.contextPath}/home/onlineAccessAccountInfo.do">setup online access</a> to that account without creating a new account.</p>
				</div> <!-- end of section -->

				<div class="section">

					<jsp:include page="/jsp/includes/signup/edit-login.jsp"/>

				</div> <!-- end of section -->

				<ul class="form-actions">

					<c:if test="${fromConfirmationVal}">
                                            <li><input id="cancel" type="image"  src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif"  onclick="javascript:goToConfirmation();"/></li>
                                        </c:if>
                                        <li><input id="continue" type="image"
                                            src=
                                            <c:choose>
                                                <c:when test="${fromConfirmationVal}">
                                                    "${pageContext.request.contextPath}/meta/media/buttons/continue.gif"
                                                </c:when>
                                                <c:otherwise>
                                                    "${pageContext.request.contextPath}/meta/media/buttons/continue.gif"
                                                </c:otherwise>
                                            </c:choose>
                                            value="Add Account Information" onClick="doSubmit();return false;"/></li>

				</ul> <!-- end of form-actions -->

			</etcc-extended:autocompleteOffForm>
                    </div>

                    <div id="secondary-content">

                        <div id="secondary-content-interior">

                                <h6>EZ TAG Questions and Answers</h6>
                                <p class="introduction">Wondering how an EZ TAG Account works? Here are answers to some of our most often asked questions:</p>

                                <dl class="faq">
                                        <dt id="what-am-i-paying-for" class="first-dt-dd-pair">What am I paying for?</dt>
                                                <dd class="first-dt-dd-pair">
                                                        <ul>
                                                                <li>no stopping</li>
                                                                <li>no waiting in line</li>
                                                                <li>no fumbling for loose change</li>
                                                                <li>lower tolls</li>
                                                        </ul>
                                                </dd>

                                        <dt id="opening-costs">Opening Costs</dt>
                                                <dd>
                                                        <p>Opening an EZ TAG Account requires a minimum pre-paid deposit of $40 per each three (3) vehicles to a maximum of $600 if you are paying by Credit/Debit Card.</p>
                                                        <p>If you prefer to have funds directly deducted from your bank, then a minimum of $80 per each three (3) vehicles to a maximum of $1200 is required.</p>
                                                        <p>Additionally we charge a one time activation fee of <strong>$15.00 per EZ TAG for the first 3</strong> and <strong>$10.00 per EZ TAG thereafter</strong>. Learn more <a href="${appDelegate.domainName}/about_faq/ez-tags">About EZ TAG</a>.</p>
                                                </dd>

                                        <dt id="how-does-it-work">How does it work?</dt>
                                                <dd>
                                                        <p>Tolls are automatically deducted from your EZ TAG Account balance every time you pass through a toll lane using your EZ TAG.</p>
                                                        <p>When your EZ TAG Account balance reaches 25% or below of the pre-paid deposit, a replenishment amount equivalent to the pre-paid deposit amount shall be automatically charged against your Credit/Debit Card (or bank account).</p>
                                                </dd>

                                        <dt id="do-i-need-more-than-one-ez-tag-account">Do I need more than one EZ TAG Account?</dt>
                                                <dd>
                                                        <p>No. You can manage as many vehicles as you like using one EZ TAG Account.</p>
                                                </dd>

                                        <dt id="is-this-web-site-secure">Is this website secure?</dt>
                                                <dd>
                                                        <p>This site is tested and certified daily to pass the FBI/SANS Internet Security Test.</p>
                                                        <!-- START SCANALERT CODE -->

														<!-- McAfee Secure Trustmark for www.hctra.org -->
															<a target="_blank" href="https://www.mcafeesecure.com/verify?host=www.hctra.org"><img class="mfes-trustmark mfes-trustmark-hover" border="0" src="//cdn.ywxi.net/meter/www.hctra.org/101.gif" width="125" height="55" title="McAfee SECURE sites help keep you safe from identity theft, credit card fraud, spyware, spam, viruses and online scams" alt="McAfee SECURE sites help keep you safe from identity theft, credit card fraud, spyware, spam, viruses and online scams" oncontextmenu="alert('Copying Prohibited by Law - McAfee Secure is a Trademark of McAfee, Inc.'); return false;"></a>
														<!-- End McAfee Secure Trustmark -->




                                                     <!--   <p><a target="_blank" href="https://www.scanalert.com/RatingVerify?ref=hctra.org"><img width="115" height="32" src="https://images.scanalert.com/meter/hctra.org/12.gif" alt="HACKER SAFE certified sites prevent over 99.9% of hacker crime." /></a></p>
                                                        <!-- END SCANALERT CODE -->
                                                </dd>

                                </dl> <!-- end of faq -->

                        </div> <!-- end of secondary-content-interior -->

                </div> <!-- end of secondary-content -->

		</div> <!-- end of content -->

		<p class="progress-bar" id="step-1-of-6">
			New EZ TAG Account
			<em>Step 1 of 6</em>
		</p>

	</div> <!-- end of content-container -->
</body>
<script type="text/javascript">
s.events="event2";
s.eVar2="Create New EZ Account";
document.forms[0].loginId.focus();
var submitted = false;
function loadingBody(){
	getErrorfields();
}

function checkForErrors(fieldname){
    var isErrorField = false;
	if(fieldname != null && fieldname != "" && fieldname.length != 0){
		isErrorField = true;
	}
	return isErrorField;
}

function getErrorfields(){
var loginId = '<html:errors property="loginId"/>';
var password = '<html:errors property="password"/>';
var confirmPassword='<html:errors property="confirmPassword"/>';
var emailAddress= '<html:errors property="emailAddress" />';
var emailAddress2='<html:errors property="emailAddress2"/>';
var securityQuestionAnswer= '<html:errors property="securityQuestionAnswer"/>';
var securityQuestionAnswer2 = '<html:errors property="securityQuestionAnswer2"/>';
var alternateEmail= '<html:errors property="alternateEmail"/>';
var confirmAlternateEmail= '<html:errors property="confirmAlternateEmail"/>';
var fousfield = true;

	 if (checkForErrors(loginId) == true){
		changeTextFieldColor(document.forms[0].loginId);
                if(fousfield){
                document.forms[0].loginId.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].loginId);
	 }
	 if (checkForErrors(password) == true){
		changeTextFieldColor(document.forms[0].password);
                if(fousfield){
                document.forms[0].password.focus();
                fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].password);
	 }

	if (checkForErrors(confirmPassword) == true){
		changeTextFieldColor(document.forms[0].confirmPassword);
                if(fousfield){
                document.forms[0].confirmPassword.focus();
                 fousfield = false;
                }
	 }
	 else{
		 changeTextFieldWhite(document.forms[0].confirmPassword);
	 }

	if (checkForErrors(emailAddress) == true){
		changeTextFieldColor(document.forms[0].emailAddress);
                 if(fousfield){
                document.forms[0].emailAddress.focus();
                fousfield = false;
                }
	 }
	 else{
		 changeTextFieldWhite(document.forms[0].emailAddress);
	 }

	if (checkForErrors(emailAddress2) == true){
		changeTextFieldColor(document.forms[0].emailAddress2);
                 if(fousfield){
                document.forms[0].emailAddress2.focus();
                 fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].emailAddress2);
	 }
	 if (checkForErrors(securityQuestionAnswer) == true){
		changeTextFieldColor(document.forms[0].securityQuestionAnswer);
                 if(fousfield){
                document.forms[0].securityQuestionAnswer.focus();
                 fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
	 }
	  if (checkForErrors(securityQuestionAnswer2) == true){
		changeTextFieldColor(document.forms[0].securityQuestionAnswer2);
                 if(fousfield){
                document.forms[0].securityQuestionAnswer2.focus();
                 fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].securityQuestionAnswer2);
	 }
	if (checkForErrors(alternateEmail) == true){
		changeTextFieldColor(document.forms[0].alternateEmail);
                 if(fousfield){
                document.forms[0].alternateEmail.focus();
                 fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].alternateEmail);
	 }
	 if (checkForErrors(confirmAlternateEmail) == true){
		changeTextFieldColor(document.forms[0].confirmAlternateEmail);
                 if(fousfield){
                document.forms[0].confirmAlternateEmail.focus();
                 fousfield = false;
                }
	 }else{
		 changeTextFieldWhite(document.forms[0].confirmAlternateEmail);
	 }

}


function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}
function doSubmit()
{
    if (!cleanPwdField(document.forms[0].password))
    {
	changeTextFieldColor(document.forms[0].password);//Lakshmi
        document.forms[0].password.focus();
        return;
    }
    else if(document.forms[0].password.value.length !=0)
          changeTextFieldWhite(document.forms[0].password);

     if (!cleanSqAnswerField(document.forms[0].securityQuestionAnswer))
     {
	changeTextFieldColor(document.forms[0].securityQuestionAnswer);//Lakshmi
        document.forms[0].securityQuestionAnswer.focus();
        return;
     }
     else if(document.forms[0].securityQuestionAnswer.value.length !=0)
           changeTextFieldWhite(document.forms[0].securityQuestionAnswer);

    if (document.forms[0].password.value.toUpperCase() != document.forms[0].confirmPassword.value.toUpperCase()) {
        alert("'Confirm Password' should match Password.");
             changeTextFieldWhite(document.forms[0].password);
             changeTextFieldColor(document.forms[0].confirmPassword);//Lakshmi
             document.forms[0].confirmPassword.focus();
    } else if (document.forms[0].emailAddress.value.toUpperCase() != document.forms[0].emailAddress2.value.toUpperCase()) {
        alert("'Confirm E-mail Address' should match 'E-mail Address'.");
                changeTextFieldWhite(document.forms[0].emailAddress);
		changeTextFieldColor(document.forms[0].emailAddress2);//Lakshmi
                document.forms[0].emailAddress2.focus();
    } else if (document.forms[0].securityQuestionAnswer.value.toUpperCase() != document.forms[0].securityQuestionAnswer2.value.toUpperCase()) {
        alert("'Confirm Your Answer' should match 'Your Answer'.");
                changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
		changeTextFieldColor(document.forms[0].securityQuestionAnswer2);//Lakshmi
                document.forms[0].securityQuestionAnswer2.focus();
    } else if (document.forms[0].alternateEmail.value != document.forms[0].confirmAlternateEmail.value) {
        alert("'Confirm Alternate E-mail Address' should match 'Alternate E-mail Address'.");
                changeTextFieldWhite(document.forms[0].alternateEmail);
		changeTextFieldColor(document.forms[0].confirmAlternateEmail);//Lakshmi
                document.forms[0].confirmAlternateEmail.focus();
    } else if (document.forms[0].alternateEmail.value != '' && document.forms[0].alternateEmail.value == document.forms[0].emailAddress.value) {
        alert("'Alternate E-mail Address' cannot be the same as 'Email Address'. ");
                changeTextFieldWhite(document.forms[0].confirmAlternateEmail);
		changeTextFieldColor(document.forms[0].alternateEmail);//Lakshmi
                document.forms[0].alternateEmail.focus();
    }
    else {
        if (!submitted)
        {
            submitted = true;
            document.forms[0].submit();
        }
    }
}

function goToConfirmation()
{
    document.forms[0].action ="${pageContext.request.contextPath}/signupConfirmationDisplay.do";
    if (!submitted)
    {
        submitted = true;
        document.forms[0].submit();
    }
}
</script>
