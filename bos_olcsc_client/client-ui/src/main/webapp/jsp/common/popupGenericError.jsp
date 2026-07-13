<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<style type="text/css">	
#error {font: 12px/16px Arial, sans-serif; font-weight: bold; color: #F00; }
</style>

<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
    
<br>
<br>
<table>
  <tr>
    <td>&nbsp; &nbsp;</td>
    <td valign="top">
      <div id="error"><bean:message arg0="${appDelegate.contactPhoneNumber}" key="error.generic.message"/></div>
    </td>
  </tr>
</table>