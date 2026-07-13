<%@ include file="/jsp/common/Taglibs.jsp" %>

<body onload="loadingBody()">
<div id="content-container">

	<div id="content">
		
		<etcc-extended:autocompleteOffForm method="POST" action="/accountInformation/changeRebillAmt.do" styleId="mainForm">

		<h1 id="account-information-edit-username">Account Information &ndash; edit EZ TAG Account replenish</h1>
		
                                <div class="section">
                                    <logic:messagesPresent message="false">
                                        <dl class="errors"/>
                                        <html:messages id="msg" message="false">										
                                            <dd><bean:write name="msg"/></dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                    <logic:messagesPresent message="true">
                                        <dl class="errors"/>										
                                        <html:messages id="msg" message="true" property="changeRebillAmtError">							
                                            <dd>${msg}</dd>
                                        </html:messages>
                                    </logic:messagesPresent>
                                </div>
                
			<div class="section">
				<p>You currently authorize HCTRA to charge your ${acctInfo.pmtType} automatically
                                when the EZ TAG Account balance falls at or below <b>$<fmt:formatNumber value="${accountForm.lowBalLevel}" minFractionDigits="2" maxFractionDigits="2"/></b>.</p>
                                
				<p>Your required Pre-paid Deposit is $<fmt:formatNumber value="${accountForm.depAmt}" minFractionDigits="2" maxFractionDigits="2"/> per each three (3) EZ tags. When your 
                                account balance falls at or below 25% of this minimum deposit balance, your ${acctInfo.pmtType}
                                account shall be charged the required Pre-paid Deposit in order to
                                re-establish the required minimum deposit balance.</p></p>
                                
                                <strong>RECOMMENDED to our customers with multi-axle vehicles (3 or more axles):</strong><p>

                                <p>You may opt to establish a higher minimum deposit balance and replenish amount 
                                to limit the number of transactions against your ${acctInfo.pmtType}
                                account by entering the desired amount and saving your changes.</p>
                                
                                
                                <p>I would like to increase my EZ TAG Account minimum balance from <b>$<fmt:formatNumber value="${accountForm.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/></b><br>
                                to $<input type="text" name="rebillAmt" value="<fmt:formatNumber value="${accountForm.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/>" size="10" onblur="populateAmts()"/> <i> enter desired minimum deposit balance amount </i></p>
                                <p>I agree that my ${acctInfo.pmtType} account will be billed $<input type="text" name="rebillAmtDisplay" size="10" value="<fmt:formatNumber value="${accountForm.rebillAmt}" minFractionDigits="2" maxFractionDigits="2"/>" disabled/><br>
                                when my minimum deposit balance falls at or below $<input type="text" name="lowBalLevelDisplay" size="10" value="<fmt:formatNumber value="${accountForm.rebillAmt/4}" minFractionDigits="2" maxFractionDigits="2"/>" disabled/></p>
				
			</div> <!-- end of section -->
                        
                        <input type="hidden" name="lowBalLevel" value="${accountForm.lowBalLevel}"/>
                        <input type="hidden" name="depAmt" value="${accountForm.depAmt}"/>
                        <input type="hidden" name="reqMinRebillAmt" value="${accountForm.reqMinRebillAmt}"/>
                        <input type="hidden" name="noOfTags" value="${accountForm.noOfTags}"/>
                        <input type="hidden" name="pmtTypeCode" value="${accountForm.pmtTypeCode}"/>
                        <input type="hidden" name="reqMinRebillAmtForCC" value="${accountForm.reqMinRebillAmtForCC}"/>
                        <input type="hidden" name="reqMinRebillAmtForEFT" value="${accountForm.reqMinRebillAmtForEFT}"/>
				
			<ul class="form-actions">
				<li><input id="cancel-do-not-save-changes" type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" value="Cancel &mdash; Do Not Save Changes" onclick="document.location.href='${pageContext.request.contextPath}/accountInformation.do'; return false" title="&rarr; account-information.shtml" /></li>
									
				<li><img src="${pageContext.request.contextPath}/meta/media/buttons/save-changes.gif" value="Save Changes" onclick="doSubmit(); return false"  /></li>
			</ul> <!-- end of form-actions -->

		</etcc-extended:autocompleteOffForm>

	</div> <!-- end of content -->

	<!--#include virtual="/includes/manage/status-bar-manage.shtml" -->
        <jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->
</body>

<script type="text/javascript">
s.events="event2";
s.eVar2="EZ Account - Edit Rebill Amt";

function populateAmts() {
    var amt = document.forms[0].rebillAmt.value;
    amt = amt.replace(/,/g,"");
    document.forms[0].rebillAmtDisplay.value=cent(amt);
    document.forms[0].lowBalLevelDisplay.value=cent(amt/4);
}

function cent(amount) {
// returns the amount in the .99 format
    amount -= 0;
    if (amount == Math.floor(amount)) {
        return amount + '.00';
    } else if (amount*10 == Math.floor(amount*10)) {
        return amount + '0';
    } else {
        var stramt = amount.toString();
        var ind = stramt.indexOf('.');
        var last = ind + 3;
        var finalstr = stramt.substring(0,last);
        return finalstr;
    }
}

function isNum(str) {
  if(!str) return false;
  for(var i=0; i<str.length; i++){
    var ch=str.charAt(i);
    if ("0123456789.".indexOf(ch) ==-1) return false;
  }
  return true;
} 
function loadingBody(){
    
	getErrorfields();	
}
function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function checkForErrors(fieldname){
    var isErrorField = false;
	if(fieldname != null && fieldname != "" && fieldname.length != 0){
		isErrorField = true;
	}
	return isErrorField;
}

function getErrorfields(){


var rebillAmt ='<html:errors property="rebillAmt"/>';

	
	  if (checkForErrors(rebillAmt) == true){
		changeTextFieldColor(document.forms[0].rebillAmt);
	 }else{
		 changeTextFieldWhite(document.forms[0].rebillAmt);
	 }	  
}




function doSubmit() {

  changeTextFieldWhite(document.forms[0].rebillAmt);

    var amt = document.forms[0].rebillAmt.value;
    amt = amt.replace(/,/g,"");
    document.forms[0].rebillAmt.value = amt;
	if (! isNum(document.forms[0].rebillAmt.value)) {
        alert('Provided minimum balance is not valid');
		changeTextFieldColor(document.forms[0].rebillAmt);
        return false;
    }	
   // if (document.forms[0].rebillAmt.value < ${accountForm.reqMinRebillAmt}) {
	   if (document.forms[0].rebillAmt.value < ${accountForm.reqMinRebillAmt}){
        //alert('The provided minimum balance is too low. The required minimum balance for ' + ${accountForm.noOfTags} + ' EZ TAG(s) is $' + ${accountForm.reqMinRebillAmt} + '.');
		alert('The provided minimum balance is too low. The required minimum balance for ' + ${accountForm.noOfTags} + ' EZ TAG(s) is $' + ${accountForm.reqMinRebillAmt} + '.');
		changeTextFieldColor(document.forms[0].rebillAmt);
    }
	else {
		if (document.forms[0].rebillAmt.value > ${accountForm.reqMaxRebillAmt}) {
			//alert('The rebilling amount is too much for this account. Please provide less than ');
			var answer = confirm('Please verify your updated minimum balance amount as it is above our suggested replenishment amount. If this is correct please click “OK” otherwise please click “CANCEL” and you may adjust your dollar amount.');
			//var answer = confirm('The rebilling amount is too much for this account. Do you really want to pay more than ');
			//changeTextFieldColor(document.forms[0].rebillAmt);
			if(answer){
				
				document.forms[0].submit();
			}else{
				changeTextFieldColor(document.forms[0].rebillAmt);
				return false;
			}
			//accountForm.lowBalLevel
		}else{
			
			document.forms[0].submit();
		}
    }
}
</script>