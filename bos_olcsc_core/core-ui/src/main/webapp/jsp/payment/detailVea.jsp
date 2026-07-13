<%@ include file="/jsp/common/Taglibs.jsp" %>

<c:if test="${sessionScope.PAYMENT_CONTEXT.veaEligible}"><!--

<table cellspacing="0" class="yellow" width="95%">
  <tr>
    <td class="yellow-topleft"></td>
    <td class="yellow-top"></td>
    <td class="yellow-topright"></td>
  </tr>
  <tr>
    <td class="yellow-left"></td>
    <td class="yellow-content">
      <p class="text-warning"> <fmt:message key="label.vea.warning"/></p>
      <br>
      <p><fmt:message key="PaymentInfoForm.detailVea.note1"/>
      <br><br><a href="javascript:openWin('DisplayVeaDisclaimer.do',800,600)"><fmt:message key="PaymentInfoForm.detailVea.note2"/></a><br></p>
<html:radio property="acceptVea" value="Y" onclick="actionOnVea(true);updateTotal();"/>&nbsp; <fmt:message key="PaymentInfoForm.detailVea.yes"/><p>
<html:radio property="acceptVea" value="" onclick="actionOnVea(false);updateTotal();"/>&nbsp; <fmt:message key="PaymentInfoForm.detailVea.no"/><p>
      <p><fmt:message key="PaymentInfoForm.detailVea.note3"/></p>
    </td>
    <td class="yellow-right"></td>
  </tr>
  <tr>
    <td class="yellow-bottomleft"></td>
    <td class="yellow-bottom"></td>
    <td class="yellow-bottomright"></td>
  </tr>												
  </table>
--></c:if>
<html:hidden property="acceptVea" value="" />