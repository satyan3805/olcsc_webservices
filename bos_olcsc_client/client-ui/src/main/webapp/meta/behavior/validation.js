/**
 * Refactored from the existing TollTag application.
**/
function cleanField(ctl) {
    // Remove special chars
    ctl.value = ctl.value.replace(/[\#,\.,\,,\!,\@,\$,\%,\^,\&,\*,\(,\),\_,\+,\=,\~,\`,\'"',\"''",\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, "");
    // replace mutliple spaces WITH ONLY 1
    // E.g.: "Fort  Worth" -> "Fort Worth"
    ctl.value = ctl.value.replace(/\s+/g, " ");
    // Remove leading/trailing spaces
    ctl.value = ctl.value.replace(/^\s*|\s*$/g, "");
    return true;
}

function cleanEmailField(ctl) {
    // Remove special chars (EXCEPT FOR "_" AND "@" AND ".")
    ctl.value = ctl.value.replace(/[\,,\#,\!,\$,\%,\^,\&,\*,\(,\),\+,\=,\~,\`,\'"',\"''",\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, "");
    // replace ALL spaces
    ctl.value = ctl.value.replace(/\s*/g, "");
    // replace dups
    ctl.value = ctl.value.replace(/\@+/g, "@");
    ctl.value = ctl.value.replace(/\_+/g, "_");
    ctl.value = ctl.value.replace(/\.+/g, ".");
    return true;
}



function cleanUserField(ctl) {
    // Remove special chars (EXCEPT FOR "_")
    ctl.value = ctl.value.replace(/[^\w\s]/g, "");
    
    // Remove ALL spaces
    //ctl.value = ctl.value.replace(/\s+/g, "");
    
    // Remove leading/trailing spaces
    ctl.value = ctl.value.replace(/^\s*|\s*$/g, "");
    
    
    // replace ALL spaces
    //ctl.value = ctl.value.replace(/\s*/g, "");
    
    //Uppercase
    ctl.value = ctl.value.toUpperCase();

    return true;
}

function toUpper(ctl)
{
    var ctlValue = ctl.value;
    ctl.value = ctlValue.toUpperCase();
    return true;
}

function cleanAllSpaces(ctl)
{
    ctl.value = ctl.value.replace(/\s*/g, "");
    return true;
}

function cleanNumericField(ctl){    
    var ctlValue = ctl.value;    
    var count=0;
    if(eval(ctlValue.length) > 0){
      for(var i=0; i<5; i++){
          if((ctl == document.getElementById("bank-account-number-1")) && (ctlValue[i] == "*"))
             count = count +1;
        }
    }
    ctlValue = ctlValue.replace(/\D/g, "").replace(/\s+/g, "");   
    var cValue="";
    if(count >=3){
      while(count >0){
       cValue = cValue + "*";
       count = count - 1;
      }
          ctl.value = cValue + ctlValue; 
      }else
          ctl.value = ctlValue;  
    return true;
}

function cleanAlphaNumericField(ctl){
    var ctlValue = ctl.value;
    ctlValue = ctlValue.replace(/\W/g, "").replace(/\s*/g, "");
    ctl.value = ctlValue;
    return true;
}


function validateExpDate(monthField, yearField) {
    var currDate = new Date();
    var currMo   = currDate.getMonth()+1;
    var currYr   = currDate.getFullYear();
    if (monthField.value == "") {
        alert("Month cannot be empty.");
        monthField.focus();
        return false;
    }
    if (yearField.value == "") {
        alert("Year cannot be empty.");
        yearField.focus();
        return false;
    }
    if ((currYr == yearField.value) && (monthField.value < currMo)) {
        alert('Credit card expiration date is older than the current date!\nPlease enter a current expiration date.');
        monthField.focus();
        return false;
    }
    if (currYr > yearField.value) {
        alert('Credit card expiration date is older than the current date!\nPlease enter a current expiration date.');
        yearField.focus();
        return false;
    }
    return true;
}

function validateMinLength(field, minLength) {
    if (field.value == "") {
        return false;
    } 
    if (field.value.length < minLength) {
        return false;
    }
    return true;
}

function validateMaxLength(field, maxLength) {
    if (field.value == "") {
        return false;
    } 
    if (field.value.length > maxLength) {
        return false;
    }
    return true;
}


var dtCh= "/";
var minYear=1900;
var maxYear=2100;


function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}


function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {
                    this[i] = 30;
                }
		if (i==2) {
                    this[i] = 29;
                }
   } 
   return this;
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}

	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("The date format should be: mm/dd/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false
	}

        
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
        
   return true;

}

function isDateTime(dtStr)
{
    if (/^(\d{1,2})\/(\d{1,2})\/(\d{4})(\s*)(\d{1,2})\:(\d{1,2})\:(\d{1,2})$/.test(dtStr)==false)
    {
        alert("The date format should be: MM/dd/yyyy hh:mm:ss");
        return false;
    }
    
    var pos1=dtStr.indexOf(" ");
    var strDate=dtStr.substring(0,pos1);
    var strTime=dtStr.substring(pos1+1);
    
    if (!isDate(strDate))
        return false;
        
    if (!isTime(strTime))
        return false;
    
    return true;

}

function isTime(dtStr)
{
    var timeDt = ":";
    
    var pos1=dtStr.indexOf(timeDt);
    var pos2=dtStr.indexOf(timeDt,pos1+1);
    var strHour=dtStr.substring(0,pos1);
    var strMinute=dtStr.substring(pos1+1,pos2);
    var strSecond=dtStr.substring(pos2+1);
    
    if (strHour.length<1 || strHour>23){
            alert("Please enter a valid hour");
            return false;
    }
    
    if (strMinute.length<1 ||  strMinute>59){
            alert("Please enter a valid minute");
            return false;
    }
    
    if (strSecond.length<1 || strSecond>59){
            alert("Please enter a valid second");
            return false;
    }
    
    return true;
}

/**
 * Converts a date string to a date object.
 */
function stringToDate(dtStr) {
    var pos1=dtStr.indexOf(dtCh)
    var pos2=dtStr.indexOf(dtCh,pos1+1)
    var strMonth=dtStr.substring(0,pos1)
    var strDay=dtStr.substring(pos1+1,pos2)
    var strYear=dtStr.substring(pos2+1)
    var date = new Date();
    date.setFullYear(strYear); 
    date.setMonth(strMonth -1);
    date.setDate(strDay);
    return date;
}


function stringToDateTime(dtStr) {
    var pos1=dtStr.indexOf(" ");
    var strDate=dtStr.substring(0,pos1);
    var strTime=dtStr.substring(pos1+1);

    pos1=strDate.indexOf(dtCh);
    var pos2=strDate.indexOf(dtCh,pos1+1);
    var strMonth=strDate.substring(0,pos1);
    var strDay=strDate.substring(pos1+1,pos2);
    var strYear=strDate.substring(pos2+1);
    
    var timeDt = ":";
    
    pos1=strTime.indexOf(timeDt);
    pos2=strTime.indexOf(timeDt,pos1+1);
    var strHour=strTime.substring(0,pos1);
    var strMinute=strTime.substring(pos1+1,pos2);
    var strSecond=strTime.substring(pos2+1);
        
    var date = new Date();
    date.setFullYear(strYear); 
    date.setMonth(strMonth -1);
    date.setDate(strDay);
    date.setHours(strHour);
    date.setMinutes(strMinute);
    date.setSeconds(strSecond);
    return date;
}

/**
 *  Compares two dates. Returns -1 if date1 is less than date2. 0 if the
 *  dates are equal. 1 if date1 is greater than date2.
 */
function compareDates(date1, date2) {
    if (date1 == date2) {
        return 0;
    } else if (date1 < date2) {
        return -1;
    } else if (date1 > date2) {
        return 1;
    }
    return 0;
}

/**
 * Unselects the selected text.
 */
function unselect() {
    this.dom=document.getElementById?1:0
    this.ver=navigator.appVersion
    if (this.dom && parseInt(this.ver) >= 5) {
        window.find(' ');
    } else if (document.layers) {
        window.find(' ');
    } else if (document.all) {
        document.selection.empty();
    }
    return false;
}  

function cleanPwdField(ctl, fieldname) {
    
    if (ctl.value == '')
        return true;
        
    if (fieldname == null || fieldname=='')
        fieldname = 'password';
    
    if(validatePwFormat(ctl.value)==false)
    {
        alert("The " + fieldname + " you entered is invalid.");
        ctl.value = "";
        return false;
    }
    
    if (!validateMinLength(ctl,8))
    {
        alert("The " + fieldname + " must be at least 8 characters in length");
        return false;
    }
    
    return true;
}

function validatePwFormat(value){
    return /^[0-9a-zA-Z\._\-@]*$/.test(value);
}


function cleanSqAnswerField(ctl) {
        
    if(/^[0-9a-zA-Z?\.\,\"\'\s]*$/.test(ctl.value)==false)
    {
        alert("The security question answer you entered is invalid.");
        ctl.value = "";
        return false;
    }
     
    return true;
}




function identicalFields( first, second, message )
{
  if ( first != second )
  {
    alert( message );
  }
}

function autotab(original,destination, e){
    var key = (window.event) ? event.keyCode : e.which;

    if(key ==9 || key ==16)
        return;
    if (original.getAttribute("maxlength")!=null && original.value.length==original.getAttribute("maxlength"))
    destination.focus();
}

function convertTempLicenseToDate(tempLicensePlate)
{
    var currDate = new Date();
    var currYr   = currDate.getFullYear();
    
   
    
    if (tempLicensePlate == null || tempLicensePlate.length<=2)
        return tempLicensePlate;
    
    var expDate = tempLicensePlate.substring(0,2) + "/";
    
    if (tempLicensePlate.length<=4)
        expDate = expDate + tempLicensePlate.substring(2) + "/" + currYr;
    else if (tempLicensePlate.length<=6)
        expDate = expDate + tempLicensePlate.substring(2,4) + "/20" + tempLicensePlate.substring(4);
    else 
        expDate = expDate + tempLicensePlate.substring(2,4) + "/" + tempLicensePlate.substring(4);
    
    return expDate;
}

function checkTempLicensePlate(tempLicensePlate)
{
    var numericValue = tempLicensePlate.replace(/\D/g, "").replace(/\s*/g, "");
    if (tempLicensePlate != numericValue)
    {
        alert("Temporary License Plate must be numeric only!");
        return false;
    }
    
    return true;
}

function showTransition()
{ 
  document.getElementById('page').style.display = "none";
  document.getElementById('transition').style.display = "";
}


function formatPhoneNumber(phoneNumber)
{
    if (validateNumeric(phoneNumber))
    {
        if(phoneNumber.length == 10)
        {
            phoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6);
        }
    }
        
    return phoneNumber;
}

function  validateNumeric( strValue ) {
/*****************************************************************
DESCRIPTION: Validates that a string contains only valid numbers.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************/
  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;

  //check for numeric characters
  return objRegExp.test(strValue);
}

function cleanPhoneField(ctl) {
    // Remove special chars
    ctl.value = ctl.value.replace(/[\#,\.,\,,\!,\@,\$,\%,\^,\&,\*,\(,\),\_,\+,\=,\~,\`,\'"',\"''",\<,\>,\/,\?,\:,\;,\{,\},\[,\],\\,\|]/g, "");
    // replace mutliple spaces WITH ONLY 1
    // E.g.: "Fort  Worth" -> "Fort Worth"
    ctl.value = ctl.value.replace(/\s+/g, "");
    
    //Remove alpha characters
    ctl.value = ctl.value.replace(/[a-zA-Z]*/g, "");
    // Remove leading/trailing spaces
    ctl.value = ctl.value.replace(/^\s*|\s*$/g, "");
    return true;
}


function removeUnwantedChar(ctl){
    // Remove special chars (EXCEPT FOR "_" AND "@" AND ".")        
    ctl.value = ctl.value.replace(/[\<\>\"\`\']/g, "");     
    ctl.value = ctl.value.replace(/\s+/g, " ");
    // replace dups
    ctl.value = ctl.value.replace(/\@+/g, "@");
    ctl.value = ctl.value.replace(/\_+/g, "_");
    ctl.value = ctl.value.replace(/\.+/g, ".");
    ctl.value = ctl.value.replace(/\-+/g, "-");
    return true;
}



