<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/META-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>

<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
    <br class="clear">

    <h2><bean:message key="text.passwordRetrieval.step1.pageTitle"/></h2>

    <p><bean:message key="text.passwordRetrieval.step1.text1"/></p>
            <ul class="content">
                    <li><bean:message key="text.passwordRetrieval.step1.text3" arg0="openWin('${pageContext.request.contextPath}/jsp/home/tolltag-number.jsp',320,190);"/></li>
                    <li><bean:message key="text.passwordRetrieval.step1.text4"/></li>
            </ul>
     <p><bean:message key="text.passwordRetrieval.step1.text2" arg0="${appDelegate.contactPhoneNumber}"/></p>

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
    <logic:messagesPresent message="true" property="invalidAccountInfo">
        <div id="error">
            <div class="error-img"/>
            <ul>
                <html:messages id="msg" message="true" property="invalidAccountInfo">
                    <li>
                        <bean:write name="msg"/>
                    </li>
                </html:messages>
            </ul>
            <br/>
        </div>
    </logic:messagesPresent>
    
    <etcc-extended:autocompleteOffForm method="POST" styleId="mainForm" action="/retrieveSecurityQuestion.do">
    <table cellspacing="0" width="100%" class="form">
    <tr class="odd">
            <th><bean:message key="onlineAccessForm.acctId"/>: *</th>
            <td colspan="5"><html:text property="acctId" styleClass="text" size="30" maxlength="12" onblur="javascript:cleanNumericField(this);"/></td>
    </tr>
    <tr class="odd">
            <td>&nbsp;</td>
            <td colspan="5"><div class="text-or"></div></td>
    </tr>
    <tr class="odd">
            <th><bean:message key="onlineAccessForm.tagId"/>: *</th>
            <td colspan="5">
                <html:select property="agencyId" styleClass="text">
                    <html:options collection="tagAuthorities" property="value" labelProperty="label"/>
                </html:select>
                <html:text property="tagId" styleClass="text" size="25" maxlength="12" onblur="javascript:cleanNumericField(this);" />
            </td>
    </tr>
    <tr class="odd">
            <th>&nbsp;</th>
            <td colspan="5"><bean:message key="text.passwordRetrieval.step1.note1" arg0="openWin('${pageContext.request.contextPath}/jsp/home/tolltag-number.jsp',320,190);"/></td>
    </tr>
    <tr>
            <td>&nbsp;</td>
            <td colspan="5"><div class="text-and"></div></td>
    </tr>
    <tr class="odd">
            <th nowrap><bean:message key="onlineAccessForm.driverLicNbr"/>: *</th>
            <td colspan="5">
                <html:select property="driverLicState" styleClass="text">
                    <html:options collection="states" property="value" labelProperty="label"/>
                </html:select>
                <html:text property="driverLicNbr" styleClass="text" size="20" maxlength="15" onblur="javascript:cleanAlphaNumericField(this);" />
            </td>
    </tr>
    <tr class="odd">
            <td>&nbsp;</td>
            <td colspan="5"><div class="text-or"></div></td>
    </tr>
    <tr class="odd">
            <th nowrap><bean:message key="onlineAccessForm.companyTaxId"/>:  *</th>
            <td colspan="5" nowrap>
                <html:text property="companyTaxId" styleClass="text" size="30" maxlength="12" onchange="javascript:cleanNumericField(this);"/>
            </td>
    </tr>
    </table>

    <!--<input type="button" class="button" value="Save">-->
    <p>
        <a href="javascript: doSubmit()" class="next" id="nextLink" >Next</a>
    </p>
    </etcc-extended:autocompleteOffForm>
    
    <script  language="javascript">
       
        var submitted =  false;
        function removeSpace(ctl)
        {
            ctl.value = ctl.value.replace(/\s*/g, '');
        }

        function doSubmit() {
            if (validateLicNbr()) {
                if (submitted == false)
                {
                    submitted = true;
                    document.getElementById("nextLink").className="next-disabled";
                    document.forms['mainForm'].submit();
                }
            }
        }

        function validateLicNbr() {
            if (document.forms['mainForm'].driverLicNbr.value.length == 0)
                return true;
            
            if (document.forms['mainForm'].companyTaxId.value != '' && document.forms['mainForm'].driverLicNbr.value.length == 0)
                return true;
                
            if (document.forms['mainForm'].driverLicState.value == "TX") {
                if (document.forms['mainForm'].driverLicNbr.value.length != 8) {
                    alert('Error:\nTexas DL number is invalid. Please re-enter.');
                    document.forms['mainForm'].driverLicNbr.focus();
                    return false;
                }
            }
            return true;
        }
    </script>
