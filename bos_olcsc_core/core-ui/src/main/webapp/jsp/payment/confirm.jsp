<%@ include file="/jsp/common/Taglibs.jsp" %>

	<tr>
		<td class="topleft"></td>
		<td class="topcenter"></td>
		<td class="topright"></td>
	</tr>

                <tr>
                  <td class="left"></td>
                  <td class="content">

<tiles:insert name="body" ignore="true"/>                    
                  
<%--                  
                  <h2>Confirm Payment</h2>
                    <p>When you click the &quot;Pay&quot; button, your credit card will be charged.</p>
                    <p>&nbsp;</p>




<tiles:insert name="method" ignore="true"/>
<tiles:insert name="invoice" ignore="true"/>
<tiles:insert name="violation" ignore="true"/>
<tiles:insert name="total" ignore="true"/>
                    
                        <p>&nbsp;</p>
<p>
  <a href="DisplayPaymentInfo.do" class="prev" onclick="this.className='prev-disabled';return checkSubmitted();">Back to Payment Information</a>
  <a href="FinalizePayment.do" class="next" onclick="this.className='next-disabled';return checkSubmitted();">Pay</a>
</p>
                  
<script type="text/javascript">
if(window.history.forward(1) != null)
    window.history.forward(1);
</script>
--%>
                  </td>
                  <td class="right"></td>
                </tr>

	<tr>
		<td class="bottomleft"></td>
		<td class="bottomcenter"></td>
		<td class="bottomright"></td>
	</tr>