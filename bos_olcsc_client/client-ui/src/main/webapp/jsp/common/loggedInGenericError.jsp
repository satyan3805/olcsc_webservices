<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

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
		<!-- begin options (buttons, pulldowns, etc.) -->
	<!-- end options -->	

	<!-- begin tabular data -->
		<td class="content">
                    <table>
                        <tr>
                            <td>
                                <img alt="error" src="${pageContext.request.contextPath}/images/Wally_stop_Final.jpg" width="100" height="150"/>
                            </td>
                            <td valign="top">
                                <div id="error">
                                  <bean:message arg0="${appDelegate.contactPhoneNumber}" key="error.generic.message"/>
                                  <br/><br/>
                                  <br/><br/>
                                  <br/><br/>
                                  <br/><br/>
                                  <br/><br/>
                                  <br/><br/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
		<!-- end tabular data-->
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
    
