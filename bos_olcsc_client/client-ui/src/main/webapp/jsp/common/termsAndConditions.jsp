
<%@ include file="/jsp/common/Taglibs.jsp" %>
<div id="content">
<div id="agreement-image" style="position:relative;width:700px; height:308px;text-align:left ; font-family:Arial;background-image:url(meta/media/common/terms-condition.png);background-repeat: no-repeat;" >
<label><br>
					<div style="color:green;font-style:bold;">Termination of Agreement/Ownership of an EZ TAG Account</div>		  <br><br>
					<!--I understand that by closing this account, all EZ TAG(s)<br>
					associated with the account will no longer be active and<br>
will be in violation if used in an EZ TAG lane.<br>
I also understand that if I have any unpaid violations,I<br> 
am still responsible for payment of those violations.And<br>
the closing of this EZ Account, does not reprieve me of my<br>
responsibility to pay existing violations.<br>
I understand that if there is a remaining balance on my <br>
account, my credit card or bank account will be credited<br>
that amount within 45 days.<br>-->
					<div style="color:black;font-style:bold;font-size:11pt"> ${sessionScope.termsCond}</div>
					 <br>
					 <a href="#" onClick="window.close()">I do not Accept </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <%
					   String balance = (String)session.getAttribute("acctInfo.balanceAmt");
					   //out.print("balance String " + balance);
					   //Integer balInteger = Integer.getInteger(balance);
					   if(balance != null && balance != "" ){
					   Float balInteger = new Float(balance);
					   //out.print("balance Int " + balInteger);
					   int balInt = balInteger.intValue();
					   //out.print("balance Int " + balInt);
					   
						   if(balInt > 0){
								 session.setAttribute("balInt","GE");
						   }else{
								if(balInt == 0){
									session.setAttribute("balInt" , "EQ");
								}else{
									session.setAttribute("balInt" , "NE");
								}
						   }
					   }
					 %>

					 <c:choose>

					 <c:when test="${sessionScope.balInt eq 'GE'}">
					  <a href="#" onClick=" window.opener.showrefund();window.close()">Accept </a>
					 </c:when>
					 <c:when test="${sessionScope.balInt eq 'EQ'}">
					  <a href="#" onClick=" window.opener.dontshowrefund();window.close()">Accept </a>
					 </c:when>
					  <c:when test="${sessionScope.balInt eq 'NE'}">
						<a href="#" onClick=" window.opener.dontshowrefundFornegativeBal();window.close()">Accept </a>
					 </c:when>
					</c:choose>
					<%
					//session.removeAttribute("balInt");
					//session.removeAttribute("acctInfo.balanceAmt");
					%>
					</label>

					</div>

</div> <!-- content -->
