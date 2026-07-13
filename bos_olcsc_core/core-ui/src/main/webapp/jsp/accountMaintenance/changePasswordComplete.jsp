<%@ include file="/jsp/common/Taglibs.jsp" %>
               
<!-- begin tabular data -->
<td class="content" height="300px">

    <table cellspacing="0" class="form">
    	
        <tr>
            <td style="width:20px">&nbsp;</td>
            <td class="odd">
                <bean:message key="changePasswordForm.complete.text1"/>
            </td>
        </tr>	
        <logic:notPresent name="passwordAction" >
       		<tr>
	            <td style="width:20px">&nbsp;</td>
	            <td>
	                <bean:message key="changePasswordForm.complete.text2"/>
	            </td>
        	</tr>
    		<tr align="center">
	            <td style="width:20px">&nbsp;</td>
	            <td>
	                <input type=button class="button" value="<bean:message key="changePasswordForm.button.accessYourAccount"/>" onclick="javascript:goHome();"/><br/>
	            </td>
	        </tr>
    	 </logic:notPresent>
    	
    	<logic:present name="passwordAction" >
    		<tr>
    			<td style="width:20px">&nbsp;</td>
    			<td>
    				<bean:message key="resetPassword.complete.text"/>
    				
    			</td>
    		</tr>
    		<tr align="center">
	            <td style="width:20px">&nbsp;</td>
	            <td>
	            	<a href="${pageContext.request.contextPath}/index.jsp" id="button"><bean:message key="app.account.home.title"/></a>
	            </td>
	        </tr>
    	</logic:present>
        
       
    </table>	
</td>

 <script type="text/javascript">
    function goHome()
    {
        window.location.href="${pageContext.request.contextPath}/myAccountHome.do";
    }
</script>