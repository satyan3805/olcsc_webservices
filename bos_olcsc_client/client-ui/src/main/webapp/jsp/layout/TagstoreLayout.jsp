<%@ include file="/jsp/common/Taglibs.jsp" %>

<HTML>
<HEAD>
<title>North Texas Tollway Authority: Online TollTag Store</title>

<tiles:insert attribute="css"/>

<script language="javascript" type="text/javascript">
  <tiles:insert attribute="pageSpecificJavascript" ignore="true"/>
</script>


         <script>
         document.cookie="G_POS_ID=2001";
         document.cookie="G_POS_TYPE=";
         try {
                 top.topmenubar.location.reload();
         } catch (e) {}
         </script>
         
<script language="javascript">
 var icon = "${pageContext.request.contextPath}/images/pixel.gif"
 var title= "Online TollTag Store"
 </script>

 <script language="JavaScript">
function ck_tx_dl (e) {
var p_str = e.value;
var v_char = "";
var v_ret = "";
  	 for (var x = 1; x <= p_str.length; x++) {
  	   	 v_char = p_str.substr(x-1,1);
      	 if ((v_char >= 0) && (v_char <= 9)){
  	   	  	v_ret = v_ret + v_char;
  	     }
    }
    if (v_ret.length != 8) {
	 	alert('Error:\nTexas DL number is invalid. Please re-enter.');
	 	e.focus();
	 	return false;
    }
    else {
       e.value = v_ret;
    }
  return true;
}


function checkall() {
if ( document.regform.p_acct_id.value == "" ) {
  alert('Error - Account Number needs to be entered ');
  document.regform.p_acct_id.focus(); return false; 
} 
if ( document.regform.p_tag_nbr.value == "" ) {
  alert('Error - Tag Number needs to be entered ');
  document.regform.p_tag_nbr.focus(); return false; 
} 
if ((document.regform.p_driver_lic_nbr.value == "") && (document.regform.p_tax_id.value == "")) {
  alert('Error - Either a Drivers License Number OR Tax ID Number needs to be entered '); 
  document.regform.p_driver_lic_nbr.focus(); return false;
}
var stateStr = document.regform.p_driver_lic_state.options[document.regform.p_driver_lic_state.selectedIndex].text
if ((stateStr == "TX") && (document.regform.p_driver_lic_nbr.value != "")) {
  if (! ck_tx_dl(document.regform.p_driver_lic_nbr)) {
    return false; 
  }
}
return true; }
</script>


 
</head>
 
 
 
<body bgColor="#00336F" topmargin="10" MARGINHEIGHT="10" MARGINWIDTH="0">




 <table align="center" width="744" cellspacing="0" cellpadding="0" border="0" >
 
 
 
 
 
 
  <tr valign="top">
   <td colspan="2" width="151" height="2"><img src="${pageContext.request.contextPath}/images/white_r1.gif" width="151" height="2"
border="0"></td>
   <td colspan="5" width="593" height="2"><img src="${pageContext.request.contextPath}/images/green_r1.gif" width="593" height="2"
border="0"></td>
  </tr>
  
  
  
  
  
  
  
  
  <tr valign="top">
   <td colspan="2" width="151" height="20"><img src="${pageContext.request.contextPath}/images/blue_r2.jpg" width="151" height="20"
border="0"></td>
   <td valign="top" rowspan="2" width="4" height="523" bgcolor="#cccccc"><img
src="images/gray_r2.jpg" border="0" width="4" height="523"></td>
   <td width="5" height="20"><img src="${pageContext.request.contextPath}/images/1green_r2.gif" width="5" height="20" border="0"></td>
   <td width="449" height="20"><img src="${pageContext.request.contextPath}/images/2green_r2.gif" width="449" height="20"
border="0"></td>
   <td class="links" width="56" height="20" background="${pageContext.request.contextPath}/images/3green_r2.gif" border="0"> </td>
   <td width="79" height="20" background="${pageContext.request.contextPath}/images/4green_r2.gif" border="0" class="links"><a
class="links"
href="/webcust/tt_front_page_publish">TOLLTAG&nbsp;HOME</a>&nbsp;<br></td>
  </tr>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  <tr valign="top">
   <td width="2" height="240" bgcolor="#ffffff"><img src="${pageContext.request.contextPath}/images/pixel.gif" border="0" height="240"
width="2"></td>
   <td height="100%" width="149" valign="top">


<tiles:importAttribute name="leftBody" scope="request"/>
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <table  width="149" bgcolor="#adbfd1" height="100%" cellpadding="0" cellspacing="0" border="0"> 
                    <tr valign="top">
                        <td height="140"><a href="http://www.ntta.org"><img src="${pageContext.request.contextPath}/images/tagnttalogo_r3.jpg" width="149" height="140" border="0"></a>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td colspan="2" width="149" height="11"><img src="${pageContext.request.contextPath}/images/gray_r5.gif" width="149" height="11" border="0" alt="img">
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
            
      <logic:iterate id="items" name="leftBody">
          <tr>
            <td>
              <tiles:insert beanName="items" flush="false"/>
            </td>
          </tr>
        </logic:iterate>
    </table>




   </td>
   
   
   
   
   
   
   
   
   
   
<td colspan="4" valign="top" bgcolor="#FFFFFF">
   <table width="589" height="28" cellpadding="0" cellspacing="0" border="0" bgColor="#00336F">
     <tr valign="top"><td width="5" height="28"><img src="${pageContext.request.contextPath}/images/blue_r3.jpg" width="5" height="28" border="0"></td>
      <script language="javascript">
document.write('<td width="29"><img src="' + icon + '" width="29"')
document.write(' height="28" border="0"></td>')</script>
   <td align="left" class="title" width="225" height="28" background="${pageContext.request.contextPath}/images/title_r3.jpg" valign="middle">
      <script language="javascript">document.write(title)</script></td>
      <td width="330" height="28" ><img src="${pageContext.request.contextPath}/images/tagdots.gif" width="330" height="28" border="0" align="right"></td></tr>
     <tr valign="top">
         <td colspan="4" height="7" width="589" ><img src="${pageContext.request.contextPath}/images/1blue_r4.jpg" width="589" height="7" border="0"></td></tr>
    </table>
    

    
    

<tiles:importAttribute name="mainBody" scope="request"/>
      <logic:iterate id="items" name="mainBody">
          <tiles:insert beanName="items" flush="false"/>
        </logic:iterate>











    
    
    
    
   </td>
  </tr>
  
  
  
  
   </table>




</body>
</html>

