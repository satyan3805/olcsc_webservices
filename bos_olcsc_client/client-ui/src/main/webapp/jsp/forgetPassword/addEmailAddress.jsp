<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/META-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>

<br class="clear">
		
<h2><bean:message key="text.passwordRetrieval.addEmail.pageTitle"/></h2>

<bean:message key="text.passwordRetrieval.addEmail.introduction1"/>
<bean:message key="text.passwordRetrieval.addEmail.introduction2"/>

<br/>  
<logic:messagesPresent message="false">
    <div id="error">
        <div class="error-img"/>
        <ul>
            <html:messages id="msg" message="false">
                <li>
                    <bean:write name="msg"/>
                </li>
            </html:messages>
        </ul>
        <br/>
    </div>
</logic:messagesPresent>
<logic:messagesPresent message="true" property="addEmailFailed">
    <div id="error">
        <div class="error-img"/>
        <ul>
            <html:messages id="msg" message="true" property="addEmailFailed">
                <li>
                    <bean:write name="msg"/>
                </li>
            </html:messages>
        </ul>
        <br/>
    </div>
</logic:messagesPresent>

<etcc-extended:autocompleteOffForm method="POST" action="/addEmailAddress.do" styleId="mainForm">
<input type="hidden" name="submitted" value="false"/>
<table cellspacing="0" class="form">
    <tr class="odd">
        <th><bean:message key="onlineAccessForm.emailAddress"/>: * </th>
        <td colspan="5"><input type="text" name="emailAddress" size="30" maxlength="30" class="text" onselect="javascript:unselect()"/></td>
    </tr>

    <tr>
      <th nowrap><bean:message key="onlineAccessForm.emailAddress2"/>: *</th>
      <td colspan="5"><input type="text" name="emailAddress2" size="30" maxlength="30" class="text" onselect="javascript:unselect()"/></td>
    </tr>
</table>

<!--<input type="button" class="button" value="Save">-->
<p>
        <a href="javascript: doSubmit();" class="next" id="nextLink">Next</a>			</p>
</etcc-extended:autocompleteOffForm>	

<script language="javascript">
    function doSubmit() {
        if (document.forms['mainForm'].submitted.value == "false") {
            document.forms['mainForm'].submitted.value = "true";
            document.getElementById("nextLink").className="next-disabled";
            document.forms['mainForm'].submit();    
        } else {
//            alert("Your request has already been submitted.");
        }
    }
</script>
