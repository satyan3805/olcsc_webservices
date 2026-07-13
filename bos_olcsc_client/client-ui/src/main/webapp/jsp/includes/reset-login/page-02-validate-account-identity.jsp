<%@ include file="/jsp/common/Taglibs.jsp" %>
<body onload="loadingBody()">
<div id="content-container">

        <div id="content">

<!--            <form id="sign-up" action="?" method="post">-->
                        <etcc-extended:autocompleteOffForm method="POST" action="/validateSecurityAnswer.do" styleId="mainForm">

                <div class="section">

                    <h1 id="validate-account-identity">Validate account identity</h1>

                    <p>Answer your security question to validate your identity.</p>

                </div> <!-- end of section -->

                <div class="section">

                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                                <html:messages id="msg" message="false">
                                                    <dd>${msg}</dd>
                                                </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true" property="invalidSecurityAnswer">
                                        <dl class="errors"/>
                                                <html:messages id="msg" message="true" property="invalidSecurityAnswer">
                                                    <dd>${msg}</dd>
                                                </html:messages>
                                    </logic:messagesPresent>
                                </div>

                <div class="section">
                    <fieldset>

                        <dl>

                            <dt class="first-dt-dd-pair">Security Question:</dt>
                                &nbsp<dd class="first-dt-dd-pair">${passwordRetrievalDTO.securityQuestion}</dd>

                            <dt><label for="security-answer">Your Answer <span class="accessibility">to the security question</span>:</label></dt>
                                <dd>*<html:password property="securityQuestionAnswer" styleClass="textfield" maxlength="20" styleId="security-answer" /></dd>

                        </dl>

                    </fieldset>

                </div> <!-- end of section -->

                <ul class="form-actions">

                    <li><input id="reset-password" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/continue.gif" value="Contine" onclick="return doSubmit();" title="&rarr; page-01b-set-up-your-online-access.shtml" /></li>

                </ul> <!-- end of form-actions -->

            </etcc-extended:autocompleteOffForm>

        </div> <!-- end of content -->

        <p class="progress-bar" id="step-2-of-3">
            Reset login information
            <em>Step 2 of 3</em>
        </p>

    </div> <!-- end of content-container -->
</body>
    <script type="text/javascript">


    function loadingBody(){
    getErrorfields();
}

    function doSubmit() {
    	var secQ= document.forms[0].securityQuestionAnswer.value;
    	
            document.forms[0].submit();
        }
    
function changeTextFieldColor(field){
    field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
    field.style.background="#FFFFFF";
}

function checkForErrors(fieldname){
    var isErrorField = false;
    if(fieldname != null && fieldname != "" && fieldname.length != 0){
        isErrorField = true;
    }
    return isErrorField;
}



function getErrorfields(){

var securityQuestionAnswer ='<html:errors property="securityQuestionAnswer"/>';
//changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
      if (checkForErrors(securityQuestionAnswer) == true){
        changeTextFieldColor(document.forms[0].securityQuestionAnswer);
     }else{
         changeTextFieldWhite(document.forms[0].securityQuestionAnswer);
     }  
     

 var error = '${retrieveSecAnsError}';
 if (error != "")
 {
    changeTextFieldColor(document.forms[0].securityQuestionAnswer); 
 }

}
</script>
