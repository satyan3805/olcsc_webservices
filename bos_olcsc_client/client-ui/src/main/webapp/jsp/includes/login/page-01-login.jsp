<%@ include file="/jsp/common/Taglibs.jsp" %>

	<div id="content-container">
	

<c:set var="acctId" value="${requestScope.acctId}" />
<% 
	String string = request.getParameter("acctId");
    String emailAcctId = "";
    if(request.getParameter("acctId") != null) {
        //out.println(string + "The hidden text is:" +request.getParameter("acctId")); 
        emailAcctId = request.getParameter("acctId");
    }
 %>




		<form id="sign-up" name="accountUserForm" action="${pageContext.request.contextPath}/AuthenticateUser.do?acctId=<%=emailAcctId %>" method="post" AUTOCOMPLETE="off">			
			<%request.setAttribute("acctId", emailAcctId);%>
            <input type="hidden" name="acctId" value="<%= emailAcctId %>" scope="request"/>
			<div id="primary-and-secondary-content">				 
				<h1 id="manage-your-account">Manage your account</h1>

				<div id="primary-content">

					<div class="section">

						<p>Log in to your account online:</p>
                                                <c:set var="acctErrorImgCtr" value="0"/>
                                                <logic:messagesPresent message="false" property="userName">
                                                    <c:set var="acctErrorImgCtr" value="1"/>
                                                    <dl class="errors"/>
                                                        <dt>Unable to login.</dt>
                                                           <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="false" property="userName">
                                                                <dd><bean:write name="msg"/></dd>
                                                            </html:messages>
                                              <!--      </dl>  end of errors --> 
                                                </logic:messagesPresent>
                                                
                                                <logic:messagesPresent message="false" property="password">
                                                    <c:if test="${acctErrorImgCtr == 0}">
                                                        <dl class="errors"/>   
                                                        <dt>Unable to login.</dt>
                                                    </c:if>
                                                           <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="false" property="password">
                                                                <dd><bean:write name="msg"/></dd>
                                                            </html:messages>
                                                </logic:messagesPresent>
                                                
                                                <logic:messagesPresent message="true"  property="invalidAccount">
                                                    <c:if test="${acctErrorImgCtr == 0}">
                                                        <dl class="errors"/>   
                                                        <dt>Unable to login.</dt>    
                                                    </c:if>
                                                    <!--     <dd>Username and password do not match.<br /> Please check both for accuracy and try again.</dd> -->
                                                           <html:messages id="msg" message="true" property="invalidAccount">
                                                                <dd>${msg}</dd>
                                                            </html:messages>
                                                </logic:messagesPresent>
                                                
					</div> <!-- end of section -->

					<div class="section">

						<fieldset>

							<dl>

								<dt class="first-dt-dd-pair"><label for="username">Username:</label></dt>
									<dd class="first-dt-dd-pair">
										<input type="text" class="textfield" id="username" name="userName" value="${accountUserForm.map.userName}" tabindex="1" />
										<p class="help">Did you <a href="${pageContext.request.contextPath}/home/resetPassword.do">forget your username?</a></p>
									</dd>

								<dt><label for="password">Password:</label></dt>
									<dd>
										<input type="password" class="textfield" id="password" name="password" value="${accountUserForm.map.password}" tabindex="2" />
										<p class="help">Did you <a href="${pageContext.request.contextPath}/home/resetPassword.do">forget your password?</a></p>

									</dd>
							</dl>

							<p><input id="login" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/login.gif" value="Login" title="&rarr; page-01b-set-up-your-online-access" tabindex="3" /></p>

						</fieldset>

					</div> <!-- end of section -->

				</div> <!-- end of primary-content -->

				<div id="secondary-content">

					<div id="secondary-content-interior">

						<h6>Online account assistance</h6>

						<p>Did you <a href="${pageContext.request.contextPath}/home/resetPassword.do">forget your username or password?</a></p>
						<p>Would you like to <a href="${pageContext.request.contextPath}/home/onlineAccessAccountInfo.do">setup online access for an EZ TAG</a> you already have?</p>
						<p>Don&rsquo;t have an EZ TAG Account yet? <a href="${pageContext.request.contextPath}/signup.do">You can open one online today</a>.</p>

					</div> <!-- end of secondary-content-interior -->

				</div> <!-- end of secondary-content -->

			</div> <!-- end of primary-and-secondary-content -->

		</form>

	</div> <!-- end of content-container -->
        
<script type="text/javascript">
    <c:if test="${setEvent3}">
        s.events="event3";
        s.eVar2="EZ Account - Log In";
    </c:if>
loadingBody();
  
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

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function getErrorfields(){

var userName = '<html:errors property="userName"/>';
var password = '<html:errors property="password"/>';
 
var tagForm = document.accountUserForm;

         if (checkForErrors(userName) == true){
		changeTextFieldColor(tagForm.userName);
	 }else{
		 changeTextFieldWhite(tagForm.userName);
	 }
         if (checkForErrors(password) == true){
		changeTextFieldColor(tagForm.password);
	 }else{
		 changeTextFieldWhite(tagForm.password);
	 }
}


</script>
