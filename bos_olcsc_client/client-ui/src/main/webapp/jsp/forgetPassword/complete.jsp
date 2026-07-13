<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<br class="clear">
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
		
<h2><bean:message key="text.passwordRetrieval.complete.pageTitle"/></h2>

<bean:message key="text.passwordRetrieval.complete.introduction" arg0="${appDelegate.contactPhoneNumber}"/>
