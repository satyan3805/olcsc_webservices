<%@ include file="/jsp/common/Taglibs.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}<fmt:message key="js.messages"/>" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<c:set var="maxRows" value="${appHelper.tablePageSize}"/>
<c:set var="licPlate"><fmt:message key="tagInfo.licPlate"/></c:set>
<c:set var="licState"><fmt:message key="tagInfo.licState"/></c:set>
<c:set var="vehicleYear"><fmt:message key="tagInfo.vehicleYear"/></c:set>
<c:set var="vehicleColor"><fmt:message key="tagInfo.vehicleColor"/></c:set>
<c:set var="vehicleMake"><fmt:message key="tagInfo.vehicleMake"/></c:set>
<c:set var="vehicleModel"><fmt:message key="tagInfo.vehicleModel"/></c:set>
<c:set var="makeSelection"><fmt:message key="tagInfo.makeSelection"/></c:set>
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
      <html:form action="/account/tollTagDisplay" styleClass="form" method="POST">
         
            <table cellspacing="0" width="100%" >
            <tr><td>
                                <table>   
                        <tr>
                            <td><h2 id="verbiage"  ><font color="#6e94ab"><b><fmt:message key="accountTagForm.activation.hdr.text"></fmt:message></b></font> </h2></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;</td>
                        </tr>
                        
                        
                    </table>
                    </td></tr>
            <tr><td>
            <table class="form" >
                                <tr class="odd">
                        <th ><fmt:message key="tagRequestForm.newVehicle"/>: </th>
                        <td align="left"> <input type="radio" name="vehicleType" onClick="newVehicleEntry();"/> </td></tr>
                                                        <tr >
                        <th ><fmt:message key="tagRequestForm.existingVehicle"/>: </th>
                        <td align="left" > <input type="radio" name="vehicleType" onClick="showVehicleSelection();"/> </td></tr>
                      </table>                        </td>
                      </tr> <tr> <td>
                      <table> <tr> <td>
                        <div id="vehicleSelectionDiv" style="display:none">

               <display:table name="accountTagForm.tollTags" cellspacing="0" 
                    pagesize="${maxRows}" requestURI="/account/tollTagDisplay.do?editMode=false&overrideDuplicates=false&searchString=''" 
                    id="tollTag" excludedParams="*">
               
                        <display:column title='${makeSelection}' sortable="false">
                        <input type="radio" name="vehSelection"/>
                        </display:column>
                          <display:column property="licPlate" title='${licPlate}' sortable="false">
                            <input type="hidden" name="tollTag[${tollTag_rowNum-1}].licPlate" value='${tollTag.licPlate}'/>
                            </display:column>
                           <display:column property="licState" title='${licState}' sortable="false">
                             <input type="hidden" name="tollTag[${tollTag_rowNum-1}].licState" value='${tollTag.licState}'/>
                           </display:column>
                          <display:column property="vehicleYear" title='${vehicleYear}' sortable="false">
                           <input type="hidden" name="tollTag[${tollTag_rowNum-1}].vehicleYear" value='${tollTag.vehicleYear}'/>
                          </display:column>
                          <display:column property="vehicleColor" title='${vehicleColor}' sortable="false" class="data-nowrap">
                          <input type="hidden" name="tollTag[${tollTag_rowNum-1}].vehicleColor" value='${tollTag.vehicleColor}'/>
                          </display:column>
                          
                          <display:column property="vehicleMake" title='${vehicleMake}' sortable="false" class="data-nowrap">
                          <input type="hidden" name="tollTag[${tollTag_rowNum-1}].vehicleMake" value='${tollTag.vehicleMake}'/>
                          </display:column>
                          <display:column property="vehicleModel" title='${vehicleModel}' sortable="false" class="data-nowrap">
                          <input type="hidden" name="tollTag[${tollTag_rowNum-1}].vehicleModel" value='${tollTag.vehicleModel}'/>
                          <input type="hidden" name="tollTag[${tollTag_rowNum-1}].vehicleClass" value='${tollTag.vehicleClassCode}'/>
						<input type="hidden" name="tollTag[${tollTag_rowNum-1}].temporaryLicPlate" value='${tollTag.temporaryLicPlate}'/>
				         <input type="hidden" name="tollTag[${tollTag_rowNum-1}].licenseExpiration" value='${tollTag.expirationDate}'/>
						<input type="hidden" name="tollTag[${tollTag_rowNum-1}].rental" value='${tollTag.rentalVehicle}'/>
				         <input type="hidden" name="tollTag[${tollTag_rowNum-1}].rentalStart" value='${tollTag.rentalStart}'/>
				         
                          </display:column>
                    </display:table>      
                    <input name="submit3" type="button" class="button" value='<fmt:message key="button.next"/>' onclick="submitActivation();" />
                         </div>  </td></tr></table>
                        </td>
                        </tr>
                        </table>
                       
                        <br/>
                        <br/>
                        
                        <html:hidden property="licPlate" styleId="licPlate"/>
                          <html:hidden property="licState" styleId="licState"/>
                            <html:hidden property="vehicleColor" styleId="vehicleColor"/>
                              <html:hidden property="vehicleMake" styleId="vehicleMake"/>
                                <html:hidden property="vehicleModel" styleId="vehicleModel"/>
                                  <html:hidden property="vehicleClassCode" styleId="vehicleClassCode"/>
                                    <html:hidden property="vehicleYear" styleId="vehicleYear"/>
                                      <html:hidden property="temporaryLicPlate" styleId="temporaryLicPlate"/>
                                        <html:hidden property="expirationDate" styleId="expirationDate"/>
											<html:hidden property="rentalVehicle" styleId="rentalVehicle"/>
                                  			    <html:hidden property="rentalStart" styleId="rentalStart"/>
                                  			      <html:hidden property="rentalEnd" styleId="rentalEnd"/>
                        	
                      
                       
                        </html:form>		
    </td>
    <td class="right"></td>
</tr>	
<script type="text/javascript">

	function submitActivation(){

			var chk=false;
    		radios=document.accountTagForm.elements["vehSelection"];
		 	for(var i=0; i<document.accountTagForm.elements["vehSelection"].length; i++) {

				 	if(radios[i].checked){
					 chk=true;	
					 document.getElementById("licPlate").value=document.accountTagForm.elements["tollTag["+i+"].licPlate"].value;
					 document.getElementById("licState").value=document.accountTagForm.elements["tollTag["+i+"].licState"].value;
					 document.getElementById("vehicleColor").value=document.accountTagForm.elements["tollTag["+i+"].vehicleColor"].value;
					 document.getElementById("vehicleMake").value=document.accountTagForm.elements["tollTag["+i+"].vehicleMake"].value;
					 document.getElementById("vehicleModel").value=document.accountTagForm.elements["tollTag["+i+"].vehicleModel"].value;
					 document.getElementById("vehicleYear").value=document.accountTagForm.elements["tollTag["+i+"].vehicleYear"].value;
					 document.getElementById("vehicleClassCode").value=document.accountTagForm.elements["tollTag["+i+"].vehicleClass"].value;
					 document.getElementById("temporaryLicPlate").value=document.accountTagForm.elements["tollTag["+i+"].temporaryLicPlate"].value;
					 document.getElementById("expirationDate").value=document.accountTagForm.elements["tollTag["+i+"].licenseExpiration"].value;
					 document.getElementById("rentalVehicle").value=document.accountTagForm.elements["tollTag["+i+"].rentalVehicle"].value;
					 document.getElementById("rentalStart").value=document.accountTagForm.elements["tollTag["+i+"].rentalStart"].value;
					 document.getElementById("rentalEnd").value=document.accountTagForm.elements["tollTag["+i+"].rentalEnd"].value;
				 }
		}
		if(chk){
			document.accountTagForm.action="/activateTag";
				document.accountTagForm.submit();
		} else {
				alert('<fmt:message key="tagRequestForm.selectAVehicle"/>');
				return;		
		}
	}

	function showVehicleSelection()
	{
		document.getElementById('vehicleSelectionDiv').style.display = "block";
		
	}

	function newVehicleEntry()
	{
		document.getElementById('vehicleSelectionDiv').style.display = "none";
		document.accountTagForm.action="/activateTag";
		document.accountTagForm.submit();
		
	}

</script>


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