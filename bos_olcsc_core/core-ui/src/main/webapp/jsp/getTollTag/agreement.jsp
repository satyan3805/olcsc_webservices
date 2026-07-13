<%@ include file="/jsp/common/Taglibs.jsp" %>

<STYLE MEDIA="print">
    #data-table .content .scroll {overflow: visible; padding: 10px 10px 10px 0; margin: 10px 0 10px 0; }
    #data-table{position:absolute; left:10px; top:0px;width:650px}
    #data-table .content .accept,h2 {display:none;}
    #footer {display:none;}
    #tabs li.here a {display:none;}
    #tabs li.here a span {display:none;}
    a.print {display:none;}
</STYLE>

<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>

<tr>
 <td class="left">&nbsp;</td>
 <td class="content">
  <div class="steps">
   <span><fmt:message key="label.steps"/>:</span>
    
   <ul>
    <li class="step1-here">${step} 1</li>
    <li class="step2">${step} 2</li>
    <li class="step3">${step} 3</li>
    <li class="step4">${step} 4</li>
    <li class="step5">${step} 5</li>
    <li class="step6">${step} 6</li>
    <li class="step7">${step} 7</li>
   </ul>
  </div>
  <br class="clear"></br>
  
  <h2>
   <span>${step} 1:</span>
   <fmt:message key="getTolltag.agreement"/>
  </h2>
  <p>
   <strong><fmt:message key="getTolltag.agreement.note1"/></strong>
  </p>
    
	<div style="padding: 10px 0 10px 30px; margin: 10px 0 10px 0;overflow: auto; height: 400px;">
		${TOLL_TAG_APPLICATION_AGREEMENT}
		<div class="accept">
			<a href="GetTollTagLoginInfo.do" class="next" id="nextLink" onclick="javascript: doSubmit();">
				<fmt:message key="getTolltag.agreement.note2"/>
			</a>
		</div>
	</div>
  
  <br class="clear"></br>
 </td>
 <td class="right">&nbsp;</td>
</tr>

<script type="text/javascript">
var submitted = false;
function doSubmit()
{
    if (submitted == false)
    {
        document.getElementById("nextLink").className="next-disabled";
        submitted = true;
    }
}
</script>