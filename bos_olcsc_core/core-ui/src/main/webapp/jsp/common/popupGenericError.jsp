<%@ taglib uri="/WEB-INF/tld/etcc-extended.tld" prefix="etcc-extended"%>

<style type="text/css">	
#error {font: 12px/16px Arial, sans-serif; font-weight: bold; color: #F00; }
</style>

    
<br>
<br>
<table>
  <tr>
    <td><img alt="error" src="${pageContext.request.contextPath}<bean:message key="images.wallystop"/>" width="100" height="150"/></td>
    <td valign="top">
      <div id="error"><fmt:message key="error.generic.message"><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message></div>
    </td>
  </tr>
</table>