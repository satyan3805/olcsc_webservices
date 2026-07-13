<%-- 
  - Author(s): Adapted from HNTB.
  - Date: May 17, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: VEA acceptance page.
  --%>
<%@ include file="/jsp/common/Taglibs.jsp" %>
		
                <tr>
                  <td class="left"></td>
                  <td class="content"><%--<h2>Violation Enforcement Agreement (VEA)</h2>
                    <p>
                        <h2><strong>Name:</strong> ${veaForm.map.firstName}&nbsp;${veaForm.map.lastName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>License Plate:</strong> ${veaForm.map.licPlateNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>License State:</strong> ${veaForm.map.licPlateState}</h2>
                    </p>--%>
                    <p><fmt:message key="PaymentInfoForm.vea.agree1"><fmt:param value="6"/></fmt:message></p>
			<div class="scroll">
                            ${vea}
                            <div class="accept">
                                <a href="javascript: document.veaForm.submit();" class="next" onclick="this.className='next-disabled';return checkVeaSubmitted();"><span><fmt:message key="PaymentInfoForm.vea.agree2"/></span></a>
                            </div>
                        </div>
                  <p>&nbsp;</p>
                  <html:form action="/acceptVea.do">
                    <html:hidden property="index"/>      
                  </html:form>
                        <!--<input type="button" class="button" value="Save">-->
                        <p> <a href="${pageContext.request.contextPath}/DisplayPaymentDetail.do" 
                                class="prev" onclick="this.className='prev-disabled';return checkVeaSubmitted();"><fmt:message key="PaymentInfoForm.vea.button.violation"/></a></p>
                  </td>
                  <td class="right"></td>
                </tr>
		
<script type="text/javascript">
var veaSubmitted = false;

function checkVeaSubmitted()
{
    if (veaSubmitted == false)
    {
        veaSubmitted = true;
        return true;
    }
    return false;
}
</script>