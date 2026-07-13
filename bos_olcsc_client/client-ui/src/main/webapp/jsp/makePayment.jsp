
    <table width="589"  cellpadding="0" cellspacing="0" border="0" bgcolor="#FFFFFF">
     <tr valign="top"><td width="1"><img src="/rev-images/pixel.gif" border="0" height="457" width="1"></td>
      <td bgColor="#ffffff" width="584">
<BR>
<CENTER>

<table border="0" width="95%" cellpadding="0" cellspacing="0" class="bdBdy2">
<tr><td><b><big>Make Payments</big></b></td><td align="right">
		<SCRIPT>
<!-- Hide from old browsers
document.write('<a href="javascript:web_help()"><B>Help</B></a>');
//-->
function web_help() {
   var filter = "";
   var the_pathname = location.pathname;
   var i            = the_pathname.indexOf ('/:');
   var j            = the_pathname.indexOf ('/', ++i);
   if (i != -1)
   {
     // Syntactically incorrect url so it needs TO be corrected
     the_pathname = the_pathname.substring (j, the_pathname.length);
   }; // (i != -1)
   frmLOV = open("/rev-help/Revamp.htm#Changing_Credit_Card_Information.htm",
 "winLOV", "scrollbars=yes,resizable=yes,width=700,height=500");
   if (frmLOV.opener == null) {
      frmLOV.opener = self;
   }
}
</SCRIPT>
</td></tr>
</table>
<STYLE type="text/css">
          <!--
           .nobold SELECT {font-weight: normal; font-family: arial, helvetica, sans-serif; color:#000000; font-size:12px; text-decoration:none}
           .nobold INPUT  {font-weight: normal; font-family: arial, helvetica, sans-serif; color:#000000; font-size:12px; text-decoration:none}
           .nobold TT     {font-weight: bold; font-family: arial, helvetica, sans-serif; color:#0C2D83; font-size:12px;}
           .nobold B      {font-weight: normal; font-family: arial, helvetica, sans-serif; color:#0C2D83; font-size:12px; text-decoration:none}
           .nobold        {font-family: arial, helvetica, sans-serif; color:#000000; font-size:12px; text-decoration:none}
          -->
          </STYLE>

<script>
function NAMEONCARD_OnChange(ctl, index) {
// Remove special chars and numbers
ctl.value = ctl.value.replace(/[0-9,\#,\.,\,,\!,\@,\$,\%,\^,\&,\*,\(,\),\_,\+,\=,\~,\`,\",\',\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, '');

// Replace mutliple spaces with only 1
// E.g.: "Fort  Worth" -> "Fort Worth"
ctl.value = ctl.value.replace(/\s+/g, ' ');

// Remove leading/trailing spaces
ctl.value = ctl.value.replace(/^\s*|\s*$/g, '');

// Make text UPPERCASE
ctl.value = ctl.value.toUpperCase();

// Return TRUE
return true;
}

function ADDRESS1_OnChange(ctl, index) {
// Remove special chars (except for "#" and ".")
ctl.value = ctl.value.replace(/[\,,\!,\@,\$,\%,\^,\&,\*,\(,\),\_,\+,\=,\~,\`,\",\',\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, '');

// Replace mutliple spaces with only 1
// E.g.: "Fort  Worth" -> "Fort Worth"
ctl.value = ctl.value.replace(/\s+/g, ' ');

// Remove leading/trailing spaces
ctl.value = ctl.value.replace(/^\s*|\s*$/g, '');

// Make text UPPERCASE
ctl.value = ctl.value.toUpperCase();

// Return TRUE
return true;
}

function CITY_OnChange(ctl, index) {
// Remove special chars and numbers (except ".")
ctl.value = ctl.value.replace(/[0-9,\#,\,,\!,\@,\$,\%,\^,\&,\*,\(,\),\_,\+,\=,\~,\`,\",\',\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, '');

// Replace mutliple spaces with only 1
// E.g.: "Fort  Worth" -> "Fort Worth"
ctl.value = ctl.value.replace(/\s+/g, ' ');

// Remove leading/trailing spaces
ctl.value = ctl.value.replace(/^\s*|\s*$/g, '');

// Make text UPPERCASE
ctl.value = ctl.value.toUpperCase();

// Return TRUE
return true;
}
</script>


<FORM ACTION="ActionItem" METHOD="POST" NAME="VF$AIFormTop">
</FORM>
<P>
<TABLE  border="0" width="95%" class="nobold" style="{border:1px solid #cccccc;}" cellpadding="3" cellspacing="0">
<TR></TR>
<tr><td bgcolor="#cccccc" nowrap><tt>&nbsp;Personal Information</tt></td><td bgcolor="#cccccc"><img src="/rev-images/pixel.gif" border="0" height="20" width="1" alt=" "></td></tr>
<INPUT TYPE="hidden" NAME="P_ACCT_ID" VALUE="429474">
<INPUT TYPE="hidden" NAME="H_ACCT_ID" VALUE="429474">
<TR VALIGN="TOP"><TD ALIGN="LEFT" width="30%" nowrap><B>Account Number:</B></TD><TD ALIGN="LEFT">429474</TD></TR>
<INPUT TYPE="hidden" NAME="H_UB_FULLNAME" VALUE="MARK  PIERCE">
<TR VALIGN="TOP"><TD ALIGN="LEFT" width="30%" nowrap><B>Account Name:</B></TD><TD ALIGN="LEFT">MARK  PIERCE</TD></TR>
<INPUT TYPE="hidden" NAME="H_COMPANY_NAME" VALUE="">
<TR VALIGN="TOP"></TR>
<INPUT TYPE="hidden" NAME="H_UB_BALANCE" VALUE="      $79.30 (Through 02/23/2006)">
<TR VALIGN="TOP"><TD ALIGN="LEFT" width="30%" nowrap><B>Account Balance:</B></TD><TD ALIGN="LEFT">      $79.30 (Through 02/23/2006)</TD></TR>
</TR>
<SCRIPT>
<!-- Hide from old browsers
document.write('<INPUT TYPE="hidden" NAME="z_modified" VALUE="N">');
//-->
</SCRIPT>
<form action="webpage.tsfoammp_actionview" method="post" name="tsfoammp$accounts$VForm"><table width="95%" border="0" cellspacing="0" cellpadding="0"><tr><td><br></td></tr><tr><td><table border="0" id="paymentTable" width="100%" class="bdBdy2" style="{border:1px solid #cccccc;}" cellpadding="3" cellspacing="0"><tr><td bgcolor="#cccccc"><span style="{font-weight: bold; font-family: arial, helvetica, sans-serif; color:#0C2D83; font-size:12px;}">&nbsp;Payment Information</span></td><td bgcolor="#cccccc"><img src="/rev-images/pixel.gif" border="0" height="20" width="1" alt=" "></td><td align="right" bgcolor="#cccccc"><span style="{font-family: arial, helvetica, sans-serif; color:#FF0000; font-size:12px;}">* = Required Field</span></td></tr><TR><TD width="30%" nowrap>Name On Card:<font color="red">&nbsp;*</font></TD><TD colspan="2"><INPUT TYPE="text" NAME="P_NAME_ON_CARD" SIZE="30" MAXLENGTH="80" VALUE=" MARK PIERCE" class="bdyBk"  onChange="return NAMEONCARD_OnChange(this, 0)"></TD></TR><TR><TD width="30%" nowrap>Billing Address:<font color="red">&nbsp;*</font></TD><TD colspan="2"><INPUT TYPE="text" NAME="P_A_CARDS_ADDRESS1" SIZE="30" MAXLENGTH="100" VALUE="1931 LAVACA TR" class="bdyBk" onChange="return ADDRESS1_OnChange(this, 0)"></TD></TR><TR><TD>Apt./Suite:</TD><TD colspan="2"><INPUT TYPE="text" NAME="P_A_CARDS_ADDRESS2" SIZE="30" MAXLENGTH="100" class="bdyBk" onChange="return ADDRESS1_OnChange(this, 0)"></TD></TR><TR><TD>City:<font color="red">&nbsp;*</font></TD><TD colspan="2"><INPUT TYPE="text" NAME="P_A_CARDS_CITY" SIZE="20" MAXLENGTH="20" VALUE="CARROLLTON" class="bdyBk" onChange="return CITY_OnChange(this, 0)">&nbsp;&nbsp;</TD></TR><TR><TD>State<font color="red">&nbsp;*</font>, ZIP:<font color="red">&nbsp;*</font></TD><TD colspan="2"><select name="P_A_CARDS_STATE" class="bdyBk"><option value="AK">AK</option><option value="AL">AL</option><option value="AR">AR</option><option value="AZ">AZ</option><option value="CA">CA</option><option value="CO">CO</option><option value="CT">CT</option><option value="DC">DC</option><option value="DE">DE</option><option value="FL">FL</option><option value="GA">GA</option><option value="HI">HI</option><option value="IA">IA</option><option value="ID">ID</option><option value="IH">IH</option><option value="IL">IL</option><option value="IN">IN</option><option value="KS">KS</option><option value="KY">KY</option><option value="LA">LA</option><option value="MA">MA</option><option value="MD">MD</option><option value="ME">ME</option><option value="MI">MI</option><option value="MN">MN</option><option value="MO">MO</option><option value="MS">MS</option><option value="MT">MT</option><option value="NC">NC</option><option value="ND">ND</option><option value="NE">NE</option><option value="NH">NH</option><option value="NJ">NJ</option><option value="NM">NM</option><option value="NV">NV</option><option value="NY">NY</option><option value="OH">OH</option><option value="OK">OK</option><option value="OR">OR</option><option value="PA">PA</option><option value="PR">PR</option><option value="RI">RI</option><option value="SC">SC</option><option value="SD">SD</option><option value="TN">TN</option><option value="TX" selected="selected">TX</option><option value="UT">UT</option><option value="VA">VA</option><option value="VT">VT</option><option value="WA">WA</option><option value="WI">WI</option><option value="WV">WV</option><option value="WY">WY</option></select>&nbsp;&nbsp;&nbsp;<INPUT TYPE="text" NAME="P_A_CARDS_ZIP_CODE" SIZE="5" MAXLENGTH="5" VALUE="75010" class="bdyBk" onChange="this.value = this.value.replace(/\D/g, '');">&nbsp;-&nbsp;<INPUT TYPE="text" NAME="P_A_CARDS_PLUS4" SIZE="4" MAXLENGTH="4" class="bdyBk" onChange="this.value = this.value.replace(/\D/g, '');"></TD></TR><TR><TD>Card Type:<font color="red">&nbsp;*</font></TD><TD colspan="2"><select name="P_CC_TYPES_CARD_NAME" class="bdyBk" onChange="clearObfusCardNbr(this);"><option value="American Express">American Express</option><option value="Discover">Discover</option><option value="MasterCard">MasterCard</option><option value="Visa" selected="selected">Visa</option><option value="Diner's Club">Diner's Club</option></select></TD></TR><TR><TD>Card Number:<font color="red">&nbsp;*</font></TD><TD colspan="2"><INPUT TYPE="text" NAME="P_CC_NUM_OBFUS" SIZE="20" MAXLENGTH="20" VALUE="******-1111" class="bdyBk" onChange="this.value = this.value.replace(/\D/g, '');"></TD></TR><TR><TD>Card Expiration:<font color="red">&nbsp;*</font></TD><TD colspan="2"><select name="P_CC_EXP_MONTH" class="bdyBk"><option value=""></option><option value="01">01</option><option value="02" selected="selected">02</option><option value="03">03</option><option value="04">04</option><option value="05">05</option><option value="06">06</option><option value="07">07</option><option value="08">08</option><option value="09">09</option><option value="10">10</option><option value="11">11</option><option value="12">12</option></select>&nbsp;<select name="P_CC_EXP_YEAR" class="bdyBk"><option value=""></option><option value="2006">2006</option><option value="2007">2007</option><option value="2008" selected="selected">2008</option><option value="2009">2009</option><option value="2010">2010</option><option value="2011">2011</option><option value="2012">2012</option><option value="2013">2013</option><option value="2014">2014</option><option value="2015">2015</option></select></TD></TR><TR><TD>Payment Amount:<font color="red">&nbsp;*</font></TD><TD colspan="2"><INPUT TYPE="text" NAME="P_PAY_AMOUNT" SIZE="3" MAXLENGTH="9" VALUE="40.00" class="bdyBk" style="{text-align:right}" onChange="this.value = this.value.replace(/[A-Za-z]|\$|\,/g, '');">&nbsp;&nbsp;<font color="#666666">(Amount must be at least $40.00)</font></TD></TR><TR><td colspan="3">
                <table class="bdBdy2" width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#eeeeee">
                <tr>
                   <td><input type="radio" name="P_UPDATE_CC_INFO" value="N"></td>
                   <td>Make a one time, non-recurring payment using this credit card.</td></tr>
                <tr>
                   <td valign="top"><input type="radio" name="P_UPDATE_CC_INFO" value="Y" checked></td>
                   <td>Update my account information with the credit card above, AND make a payment.</td></tr>
                </table></TR>
                </TABLE></td></tr>
                
                </table><br><INPUT TYPE="hidden" NAME="P_ACCT_ID" VALUE="429474"><INPUT TYPE="hidden" NAME="O_NAME_ON_CARD" VALUE=" MARK PIERCE"><INPUT TYPE="hidden" NAME="O_A_CARDS_ADDRESS1" VALUE="1931 LAVACA TR"><INPUT TYPE="hidden" NAME="O_A_CARDS_ADDRESS2" VALUE=""><INPUT TYPE="hidden" NAME="O_A_CARDS_CITY" VALUE="CARROLLTON"><INPUT TYPE="hidden" NAME="O_A_CARDS_STATE" VALUE="TX"><INPUT TYPE="hidden" NAME="O_A_CARDS_ZIP_CODE" VALUE="75010"><INPUT TYPE="hidden" NAME="O_A_CARDS_PLUS4" VALUE=""><INPUT TYPE="hidden" NAME="O_CC_TYPES_CARD_NAME" VALUE="Visa"><INPUT TYPE="hidden" NAME="O_CC_NUM_OBFUS" VALUE="******-1111"><INPUT TYPE="hidden" NAME="O_CC_EXP_MONTH" VALUE="02"><INPUT TYPE="hidden" NAME="O_CC_EXP_YEAR" VALUE="2008"><INPUT TYPE="hidden" NAME="Z_CHK" VALUE="3388968730"><script><!--
             document.write('<input class="submit" type="hidden" name="Z_ACTION">')
             //-->
             </script>
             <script><!--
             document.write('<input class="Tagbutn" type="submit" value="  Make Online Payment  " onClick="this.form.Z_ACTION.value=\'UPDATE\'; return btnVFU_OnClick(this),false">')
             //-->
             </script>
             <noscript>
             <input type="submit" name="Z_ACTION" value="  Make Online Payment  ">
             </noscript>
             </form>
</TABLE>
<p><p><p>