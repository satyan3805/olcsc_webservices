<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<c:choose>
    <c:when test="${HAS}">
        <c:set var="actionName" value="${pageContext.request.contextPath}/accountTransactionsHAS.do?HAS=Y"/>
        <c:set var="transTypeLabel" value=" parking transactions"/>
    </c:when>
    <c:otherwise>
        <c:set var="actionName" value="${pageContext.request.contextPath}/accountTransactions.do"/>
    </c:otherwise>
</c:choose>

<div id="content-container">

	<form id="data-actions" name="TransactionsForm" action="${actionName}" method="post">
              <input type="hidden" name="siteCat" value="${siteCat}"/>
		<div id="content">

			<h1 id="account-activity-transactions">Account Activity &ndash;
                        <c:choose>
                            <c:when test="${HAS}">
                                HAS
                            </c:when>
                            <c:otherwise>
                                Account
                            </c:otherwise>
                        </c:choose>
                        Transactions</h1>

			<div class="section">
			  <logic:messagesPresent message="true" property="viewTransactonErrors">
                            <div class="section">
                            <dl class="errors"/>
                            <html:messages id="msg" message="true" property="viewTransactonErrors">
                                <dd><bean:write name="msg"/></dd>
                            </html:messages>
                            </div>
                          </logic:messagesPresent>
			</div>

                        <fieldset>

				<dl>
					<dt><label for="transactions-select">Show${transTypeLabel}<span class="accessibility">transactions</span></label></dt>
                                        <c:if test="${not HAS}">
                                            <dd>
                                                    <select id="transactions-select" name="transactionType">
                                                            <option value="All Transactions" <c:if test="${TransactionsForm.map.transactionType=='All Transactions'}">selected</c:if>>All Transactions</option>
                                                            <option value="All TOLLS" <c:if test="${TransactionsForm.map.transactionType=='All TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp All Tolls</option>
															<option value="HCTRA TOLLS" <c:if test="${TransactionsForm.map.transactionType=='HCTRA TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp HCTRA Tolls</option>
                                                            <option value="METRO TOLLS" <c:if test="${TransactionsForm.map.transactionType=='METRO TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp METRO Tolls</option>
															<option value="NTTA TOLLS" <c:if test="${TransactionsForm.map.transactionType=='NTTA TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp NTTA Tolls</option>
															<option value="TXDOT TOLLS" <c:if test="${TransactionsForm.map.transactionType=='TXDOT TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp TxDOT-TOD Tolls</option>
															<option value="CTRMA TOLLS" <c:if test="${TransactionsForm.map.transactionType=='CTRMA TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp CTRMA Tolls</option>
															<option value="FBGPTRA TOLLS" <c:if test="${TransactionsForm.map.transactionType=='FBGPTRA TOLLS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp FBGPTRA Tolls</option>
                                                            <option value="ALL PAYMENTS" <c:if test="${TransactionsForm.map.transactionType=='ALL PAYMENTS'}">selected</c:if>>&nbsp&nbsp&nbsp All Payments</option>
                                                            <option value="AUTOMATIC PAYMENTS" <c:if test="${TransactionsForm.map.transactionType=='AUTOMATIC PAYMENTS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Automatic Payments</option>
                                                            <option value="MANUAL PAYMENTS" <c:if test="${TransactionsForm.map.transactionType=='MANUAL PAYMENTS'}">selected</c:if>>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Manual Payments</option>
                                                            <option value="TAG ACTIVATION FEE OR SALE" <c:if test="${TransactionsForm.map.transactionType=='TAG ACTIVATION FEE OR SALE'}">selected</c:if>>&nbsp&nbsp&nbsp Tag Activation Fee</option>

                                                    </select>
                                            </dd>
                                        </c:if>

					<dt><label for="vehicle-select">for<span class="accessibility"> vehicle</span></label></dt>
					<dd>
						<select id="vehicle-select" name="nickName">
							<option value="All Vehicles" <c:if test="${(empty TransactionsForm) or (empty TransactionsForm.map.nickName) or (TransactionsForm.map.nickName=='all')}">selected</c:if>>All Vehicles</option>
                                                        <c:forEach var="nickName" items="${nickNames}">
                                                            <option value="${nickName.vehicleNickNameValue}" <c:if test="${TransactionsForm.map.nickName==nickName.vehicleNickNameValue}">selected</c:if>>${nickName.vehicleNickName}</option>
                                                        </c:forEach>
                                                        <!--
							<option value="marks-accord">Mark&rsquo;s Accord</option>
							<option value="jennys-suburban">Jenny&rsquo;s Suburban</option>
                                                        -->
						</select>
					</dd>
				<!-- </dl>
				<dl> -->
				<!-- QC_11613 start -->
				<dt><label for="vehicle-select">Date Type</label></dt>
					<dd>
						<select id="dateType" name="dateType">
							<option value="0">Select </option>
							<option value="TRANSACTION">Transaction Date</option>
							<option value="POSTED">Posted Date</option>
						</select>
					</dd>
				<!-- QC_11613 end -->
				</dl>

				<ul>
					<li>
						<dl>
							<dt><label for="from-date">from<span class="accessibility"> date</span></label></dt>
							<dd><input type="text" class="date textfield format-m-d-y" id="from-date" name="startDate" value="${TransactionsForm.map.startDate}" />
                                                        </dd>

							<dt><label for="through-date">through<span class="accessibility"> date</span></label></dt>
							<dd><input type="text" class="date textfield format-m-d-y" id="through-date" name="endDate" value="${TransactionsForm.map.endDate}"/></dd>
						</dl>
					</li>
					<li>
						<input type="button" value="Search" onclick="javascript:return search();"/>
					</li>
				</ul>



			</fieldset>
                     </td><td width="40%"><b>Please Note: Transactions may take several business days to post to your EZ TAG Account.</b></td></tr></table>


<br/>


<c:if test="${not empty requestScope.TRANSACTIONS_TRANS_REC}">
    <div class="section with-primary-and-secondary-content">
        <div class="primary-section-content">
		    <h2>Account Transactions</h2>
			<!-- RFC20130184 -->

			<fieldset class="agencyinfo">
            	 <b>For Agency Contact Information <a class="legal-document" href="${pageContext.request.contextPath}/agencyInfo.do">click here</a></b>
			</fieldset>

		</div>

        <div class="secondary-section-content" align="right">
            <dl>
                <dt>
                    <b>Total Amount:</b> $<fmt:formatNumber value="${totalAmt}" minFractionDigits="2" maxFractionDigits="2"/>
                </dt>
                <dd>
                    <i>(based on your filtered transactions)</i>
                </dd>
            </dl>
        </div>
    </div>
      <display:table name="TRANSACTIONS_TRANS_REC" cellspacing="1" class="data-table transactions"
        pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/accountTransactions.do" id="record" sort="list"
        decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="regTrans">
        <display:column title="#"  property="serialNum" group="1" sortable="false" class="hideColumn" headerClass="hideColumn"/>
        <display:column title="Transaction Date/Time" property="strTrxnDate" sortable="true" sortProperty="trxnDate"/>

        <display:column title="Posted Date/Time" property="strPostedDate" sortable="true" sortProperty="postedDate"/>

        <display:column property="vehicleNickName" title="Vehicle" sortable="false"/>
        <display:column property="vehicleClassCode" title="Axle Count" sortable="false"/>
        <display:column property="locationName" title= "Location <img id='agency-info' src='${pageContext.request.contextPath}/meta/media/icons/information-icon.png' title = 'HCTRA: Harris County Toll Road Authority&#10 281-875-3279&#10 www.hctra.org&#10&#10CTRMA: Central Texas Regional Mobility Authority&#10 512-996-9778&#10 www.mobilityauthority.com&#10&#10FBGPTRA: Fort Bend Grand Parkway Toll Road Authority&#10 281-242-9740&#10 www.fbgptra.com&#10&#10METRO: Metro High Occupancy Toll (HOT) Lanes&#10 713-462-5263&#10 www.ridemetro.org&#10&#10NTTA: North Texas Tollway Authority&#10 972-818-6882&#10 www.ntta.org&#10&#10TxDOT-TOD: Texas Department of Transportation Toll Operations Division&#10 888-468-9824&#10 www.txtag.org' />" />
        <display:column property="transType" title="Description" sortable="false"/>
        <display:column class="currency" style="text-align:right;" title="Amount" sortable="false">
            <span>$</span><fmt:formatNumber value="${regTrans.amount}" minFractionDigits="2" maxFractionDigits="2"/>
            </display:column>
        <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFound} {1} found</span>' />
        <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFound} {1} found, {5} pages total</span>' />
    </display:table>
</c:if>

<br/><br/>

<c:if test="${not empty requestScope.TRANSACTIONS_PARKING_REC}">
      <h2>HAS Transactions</h2>
      <display:table name="TRANSACTIONS_PARKING_REC" cellspacing="1" class="data-table transactions"
        pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/accountTransactions.do" id="record" sort="list"
        decorator="com.etcc.csc.decorator.RowStyleTableDecorator" uid="parkingTrans">
        <display:column title="#"  property="serialNum" group="1" sortable="false" class="hideColumn" headerClass="hideColumn"/>
        <display:column title="Transaction Date/Time" property="strTrxnDate" sortable="true" sortProperty="trxnDate"/>
        <display:column title="Posted Date/Time" property="strPostedDate" sortable="true" sortProperty="postedDate"/>


        <display:column property="vehicleNickName" title="Vehicle" class="${cellStyle}" sortable="false"/>
        <display:column property="locationName" class="${cellStyle}" title="Location" sortable="false"/>
        <display:column class="${cellStyle}" property="transType" title="Description" sortable="false"/>
        <display:column class="currency" style="text-align:right;" title="Amount" sortable="false" >
                <span>$</span><fmt:formatNumber value="${parkingTrans.amount}" minFractionDigits="2" maxFractionDigits="2"/>
        </display:column>

       <display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner">${itemsFoundParking} {1} found</span>' />
       <display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner">${itemsFoundParking} {1} found, {5} pages total</span>' />

    </display:table>
</c:if>

<c:if test="${(empty requestScope.TRANSACTIONS_TRANS_REC) and (empty requestScope.TRANSACTIONS_PARKING_REC)}">
    <dl class="errors">
        <dd> No transaction found.</dd>
    </dl>
</c:if>



			<div id="tertiary-navigation-and-or-page-controls">

				<!--#include virtual="/includes/manage/tertiary-navigation-manage-account-activity.shtml" -->
                                <jsp:include page="/jsp/includes/manage/tertiary-navigation-manage-account-activity.jsp"/>
				<ul id="page-controls">
                                            <li><a href="#0" class="pdf" title="PDF report" onclick="javascipt:downloadPDF();">download into PDF</a></li>
                                            <li><a href="#1" class="excel" title="Excel report" onclick="javascipt:downloadExcel();">download into Excel</a></li>
				<!--	<li><a href="#" class="excel">download Excel</a></li>
                                -->

					<!-- These are hidden by default so that browsers without JavaScipt don't even see it.
						Naturally, it's automatically unhidden by JavaScript. -->
                                        <c:if test="${preview}">
                                            <li><a href="#2" class="print" onclick="javascript: window.print();return false;">print</a></li>
                                        </c:if>
					<li><a href="#3"
                                            <c:choose>
                                                <c:when test="${preview}">
                                                    class="print-preview"
                                                </c:when>
                                                <c:otherwise>
                                                    class="print-all"
                                                </c:otherwise>
                                            </c:choose>

                                            onclick="javascript:printPreview();return false;">
                                            <c:choose>
                                                <c:when test="${preview}">
                                                    turn off print preview
                                                </c:when>
                                                <c:otherwise>
                                                    print all transactions
                                                </c:otherwise>
                                            </c:choose>
                                        </a></li>
				</ul>

			</div> <!-- end of tertiary-navigation-and-or-page-controls -->



	</div> <!-- end of content -->

        <jsp:include page="/accountInfo.do"/>
    </form>
</div> <!-- end of content-container -->


<script type="text/javascript">
document.TransactionsForm.startDate.focus();
//QC_11613 start
document.addEventListener('DOMContentLoaded', function() {		
	loadDateType();
})
function loadDateType(){
	var getDateType="${dateType}";
	
	if(getDateType=='TRANSACTION'){  
		var dateTypeSelected = document.getElementById('dateType');
    	dateTypeSelected.getElementsByTagName('option')[1].setAttribute('selected','selected');
    }/* else if(getDateType=='Posted'){   
    	var dateTypeSelected = document.getElementById('dateType');
    	dateTypeSelected.getElementsByTagName('option')[2].setAttribute('selected','selected');
    } */else{
    	var dateTypeSelected = document.getElementById('dateType');
    	dateTypeSelected.getElementsByTagName('option')[2].setAttribute('selected','selected');
    }
}
//QC_11613 end
function downloadPDF()
    {
      if (validateDates())
        {
            var actionstring = "${pageContext.request.contextPath}/accountTransactionsHAS.do";

            <c:if test="${not HAS }">
                actionstring = actionstring + "?pdf=Y";
            </c:if>
             <c:if test="${ HAS }">
                actionstring = actionstring + "?pdf=Y&HAS=Y";
             </c:if>
                document.TransactionsForm.action = actionstring;
                //alert("actionstring :"+actionstring);

           var acrobat=new Object();

            acrobat.installed=false;
            acrobat.version='0.0';

            if (navigator.plugins && navigator.plugins.length)
            {
            for ( var x = 0, l = navigator.plugins.length; x < l; ++x )
            {
            if (navigator.plugins[x].description.indexOf('Adobe Acrobat') != -1)
            {
            acrobat.version=parseFloat(navigator.plugins[x].description.split('Version ')[1]);

            if (acrobat.version.toString().length == 1) acrobat.version+='.0';

            acrobat.installed=true;
            break;
            }
            }
            }
            else if (window.ActiveXObject)
            {
            for (x=6; x<10; x++)
            {
            try
            {
            oAcro=eval("new ActiveXObject('PDF.PdfCtrl."+x+"');");
            if (oAcro)
            {
            acrobat.installed=true;
            acrobat.version=x+'.0';
            }
            }
            catch(e) {}
            }

            try
            {
            oAcro4=new ActiveXObject('PDF.PdfCtrl.1');
            if (oAcro4)
            {
            acrobat.installed=true;
            acrobat.version='4.0';
            }
            }
            catch(e) {}

            try
            {
            oAcro9=new ActiveXObject('AcroPDF.PDF.1');
            if (oAcro9)
            {
            acrobat.installed=true;
            acrobat.version='9.0';
            }
            }
            catch(e) {}

            }
             if (navigator.plugins != null && navigator.plugins.length > 0) {
                  for (i=0; i < navigator.plugins.length; i++ ) {
                     var plugin = navigator.plugins[i];
                     if (plugin.name.indexOf("Adobe Acrobat") != -1) {
                                       acrobat.installed=true;
                                        break;
                     }
                  }
                }
          //    if(!acrobat.installed)
          //     {
          //     window.open('http://www.adobe.com/products/acrobat/readstep2.html');
          //    }
          //    else
          //      {
               document.TransactionsForm.submit();
          //      }
              }
                    else
                        return false;
}

function downloadExcel()
{
      if (validateDates())
        {
            var actionstring = "${pageContext.request.contextPath}/accountTransactionsHAS.do";

            <c:if test="${not HAS }">
                actionstring = actionstring + "?excel=Y";
            </c:if>
             <c:if test="${ HAS }">
                actionstring = actionstring + "?excel=Y&HAS=Y";
             </c:if>
                document.TransactionsForm.action = actionstring;
                document.TransactionsForm.submit();

        }else
            return false;
}

function printPreview()
    {
        if (validateDates())
        {
            showLoading();
            var actionstring = "${pageContext.request.contextPath}/accountTransactions.do";

            <c:if test="${HAS}">
                actionstring ="${pageContext.request.contextPath}/accountTransactionsHAS.do"
            </c:if>

            <c:if test="${(not preview) and (not HAS) }">
                actionstring = actionstring + "?preview=Y";
            </c:if>

            <c:if test="${(not preview) and  HAS }">
                actionstring = actionstring + "?preview=Y&HAS=Y";
            </c:if>

            <c:if test="${preview and  HAS }">
                actionstring = actionstring + "?HAS=Y";
            </c:if>
            document.TransactionsForm.action = actionstring;
            document.TransactionsForm.submit();
        }
        else
            return false;
    }

    function validateDates()
    {
        var fromDate = document.TransactionsForm.elements["startDate"];
        var toDate = document.TransactionsForm.elements["endDate"];
      	//QC_11613 start
        var dateType = document.TransactionsForm.elements["dateType"];
      	//QC_11613 end
        var reportDays = ${appDelegate.reportDays};

        var minStartDate =  new Date();
        minStartDate.setDate(minStartDate.getDate() - reportDays);

        if(fromDate.value == "")
        {
            alert("\"From Date\" must be provided.");
            changeTextFieldColor(fromDate);
            changeTextFieldWhite(toDate);
            fromDate.focus();
            return false
        }
        else if(toDate.value == "")
        {
            alert("\"Through Date\" must be provided.");
            changeTextFieldColor(toDate);
            changeTextFieldWhite(fromDate);
            toDate.focus();
            return false;
        }
        else if (!isDate(fromDate.value))
        {
            changeTextFieldWhite(fromDate);
            changeTextFieldWhite(toDate);
            fromDate.focus();
            return false;
        }
        else if (!isDate(toDate.value))
        {
            toDate.focus();
            return false;
        }
        else if(compareDates(stringToDate(fromDate.value),
                stringToDate(toDate.value)) == 1)
        {
            alert("\"From Date\" should not be greater than \"Through Date\".");
            fromDate.focus();
            return false;
        }
        else if (compareDates(stringToDate(toDate.value), new Date()) == 1)
        {
            alert("Your selected date range must be within the past " + reportDays + " days.");
            toDate.focus();
            return false;
        }
        else if (compareDates(minStartDate, stringToDate(fromDate.value)) == 1)
        {
            alert("Your selected date range must be within the past " + reportDays + " days.");
            fromDate.focus();
            return false;
        } else if (dateType.value == "0"){//QC_11613 start
        	alert("Please select Date Type.");
        	dateType.focus();
        	return false;
        }//QC_11613 end

        
        //showLoading();
        //document.TransactionsForm.submit();
        return true;
    }
  function search()
  {
    if (validateDates())
        {
         showLoading();
         document.TransactionsForm.action="${actionName}";
         document.TransactionsForm.siteCat.value="true";
         document.TransactionsForm.submit();
         return true;
        }
        else
         return false;
  }

function changeTextFieldColor(field){
	field.style.background="#00FFCC";
}

function changeTextFieldWhite(field){
	field.style.background="#FFFFFF";
}

function openAgecnyInfo(url)
{
   window.open(url,"","width=800,height=500,scrollbars=1,resizable");
}

function checkForErrors(fieldname){
   var isErrorField = false;
     if(fieldname != null && fieldname != "" && fieldname.length != 0){
        isErrorField = true;
       }
   return isErrorField;
}


<c:if test="${siteCat}">
  s.events="event43";
  s.eVar19="${TransactionsForm.map.transactionType}";
  s.products=";Account Activity -"+"${TransactionsForm.map.transactionType};"+"${TransactionsForm.map.nickName};"+"${TransactionsForm.map.startDate};"+"${TransactionsForm.map.endDate}";
  document.TransactionsForm.siteCat.value="false";
</c:if>

</script>
