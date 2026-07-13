<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<table  width="149" height="100%" cellpadding="0" cellspacing="0" border="0"> 
    <tr valign="top">
        <td bgcolor="#adbfd1" class="bdTny">
            <html:form action="/AuthenticateUser.do">
                <center>
                    <table height="115" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="bdTny" align="center">User ID:<br>
                                <input type="text"  class="bdTny" name="userName" MAXLENGTH="16" size="20"><br>
                                Password:<br>
                                <input type="password" name="password" size="20"  MAXLENGTH="16" class="bdTny"><br>
                                <input type="submit" NAME="p_submit" value="   Log In   "  style="width:65" class="Logbutn">
                            </td>
                        </tr>
                    </table>
                </center>
            </html:form>
        </td>
    </tr>
    <tr valign="top">
        <td bgcolor="#adbfd1" height="38" class="contentb">
            <center>
                <a href="/webcust/appl_security.forgot_password_form_step1" class="password"><b><u>Did you forget your password?</u></b></a>
            </center>
        </td>
    </tr>
<%--    
    <tr valign="top">
        <td colspan="2" width="149" height="5">
            <img src="${pageContext.request.contextPath}/images/log_blue.gif" width="149" height="10" border="0">
        </td>
    </tr>
--%>    
</table>
