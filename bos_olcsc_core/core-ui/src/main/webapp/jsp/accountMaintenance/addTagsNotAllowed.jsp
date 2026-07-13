<%@ include file="/jsp/common/Taglibs.jsp" %>

<tr>
    <td class="topleft"></td>
    <td class="topcenter"></td>
    <td class="topright"></td>
</tr>
<tr id="content-top">
    <td class="left"></td>
    <td class="content"></td>		
    <td class="right"></td>		
</tr>
<tr>
    <td class="left"></td>
    <td id="data" class="content">

<br/><br/>

    <div id="error">
      <div class="error-img"/>
      <ul>
        <li>
        <c:choose>
        <c:when test="${editTagNotAllowed}">
        <fmt:message key="tagInfo.message.editTagNotAllowed"><fmt:param value="${acctInfo.acctTypeDescr}"/><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>
            </c:when>
        <c:otherwise>
        <fmt:message key="tagInfo.message.addTagNotAllowed"><fmt:param value="${acctInfo.acctTypeDescr}"/><fmt:param><etcc-extended:Translation property="contactPhoneNumber"/></fmt:param></fmt:message>
            </c:otherwise>
        </c:choose>
        
        </li>
      </ul>
      <br/>
    </div>

<br/><br/><br/><br/><br/><br/><br/><br/>
    
    </td>		
    <td class="right"></td>		
</tr>
        
<tr id="content-bottom">
    <td class="left"></td>
    <td class="content"></td>		
    <td class="right"></td>		
</tr>
        
<tr>
    <td class="bottomleft"></td>
    <td class="bottomcenter"></td>
    <td class="bottomright"></td>
</tr>
