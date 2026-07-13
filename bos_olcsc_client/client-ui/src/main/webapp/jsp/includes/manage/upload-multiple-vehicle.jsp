<%@ include file="/jsp/common/Taglibs.jsp" %>



<div id="content-container">
<input type="hidden" name="retailTransId" value="${retailTransId}">
		<form name="fileUploadForm" action="${pageContext.request.contextPath}/accountUploadMultipleVehicle.do" method="post" enctype="multipart/form-data">

		<div class="section">
                <h1 id="upload-multiple-vehicle"  style="margin-left:15px;">Multiple Vehicle Upload</h1>
                   <div id="errorMessage">
                    <logic:messagesPresent message="true" property="saveFailed">
                        <dl class="errors"/>
                        <dt/>
                        <html:messages id="msg" message="true" property="saveFailed">
                            <dd>${msg}</dd>
                        </html:messages>
                    </logic:messagesPresent>
                     <logic:messagesPresent message="true" property="alertMsg">
                          <dl class="alerts"/>
                          <dt/>
                          <html:messages id="msg" message="true" property="alertMsg">
                              <dd>${msg}</dd>
                          </html:messages>
                      </logic:messagesPresent>
                </div>
                </div>
                <div class="section">
                  <table width="100%">
                    <tr>
                      <td width="5%"></td>

                      <td width="21%" ><a href="javascript:downloadme();" title="PDF version of Instructions" >DOWNLOAD Instructions </a>Requires Adobe Acrobat Reader <a href="#2" onclick="javascript:downloadAdobe();" title="Download Adobe Acrobat Reader " >available here</a></td>

                      <td width="1%" ><table cellpadding="0" cellspacing="0" border="1" width="50%">
                        <tr>
                          <td height="70" valign="top" style="background-color:#afafaf"></td>
                       </tr>
                      </table>
                      </td>
                      <td width="4%" ><table cellpadding="0" cellspacing="0" border="0" width="100%">
                        <tr>
                          <td height="70" valign="top" ></td>
                       </tr>
                      </table>
                      </td>
                      <td width="27%" ><a href="#3" onclick="javascript:doDownloadVehicleTemplate();" title="XLS Multiple Vehicle Template">DOWNLOAD Multiple Vehicle Template</a> Requires Microsoft Excel(.xls) 100kb</td>
                       <td width="1%" ><table cellpadding="0" cellspacing="0" border="1" width="50%">
                        <tr>
                          <td height="70" valign="top" style="background-color:#afafaf;"></td>
                       </tr>
                      </table>
                      </td>
                      <td width="4%" ><table cellpadding="0" cellspacing="0" border="0" width="100%">
                        <tr>
                          <td height="70" valign="top"></td>
                       </tr>
                      </table>
                      </td>
                      <td width="37%"><a href="#4" onclick="javascript:showUpload();" title="Upload CSV file">Upload Completed Vehicle File (CSV format) </a>
                          <div id="uploadFile" style="display:none;"><input id="file_path" type="file" name="uploadFile" size="30" onchange="javascript:enableUpload();"></input></div>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%">
                    <tr></tr>
                    <tr>
                    <td><div id="uploadFileButton" style="display:none;"><input id="upload_button" type="button" value="UPLOAD FILE" name="uploadfile" alt="Upload File" title="&rarr; Upload Template with Vehicles" onclick="javascript:doSubmit();"/></div>
                    <div id="uploadProgressbar" style="display:none;"><input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/progress-bars/upload-progress-bar.gif" value="Upload in Progress" alt="Upload in Progress" title="Upload in Progress"/>Upload in progress<td>
                    </tr>
                    </table>
                      </td>

                    </tr>
                  </table>
                 </div> <!-- end of section -->

			<ul class="form-actions">

			<li><input type="image" src="${pageContext.request.contextPath}/meta/media/buttons/cancel-do-not-save-changes.gif" alt="Cancel" title="&rarr; Cancel" value="cancel" onclick="javascript:doCancel();"/></li>
                        <li></li>

			</ul> <!-- end of form-action -->
		</form>

	<jsp:include page="/accountInfo.do"/>

</div> <!-- end of content-container -->

<script type="text/javascript">
var submitted = false;
function showUpload()
{
  document.getElementById('uploadFile').style.display = 'block';
  document.getElementById('uploadFileButton').style.display = 'block';
  document.getElementById('upload_button').disabled  = true;
}
function doSubmit()
{
  document.fileUploadForm.action = "${pageContext.request.contextPath}/accountUploadMultipleVehicle.do";
   if(stringTrim(document.getElementById('file_path').value).length > 0){
        document.getElementById('uploadFileButton').style.display = 'none';
        document.getElementById('uploadProgressbar').style.display = 'block';
        document.getElementById('errorMessage').style.display = 'none';
        document.fileUploadForm.submit();
    }
}
function downloadAdobe()
{
  window.open('http://www.adobe.com/products/acrobat/readstep2.html');
}

function doDownloadVehicleTemplate()
{
  document.fileUploadForm.action = "${pageContext.request.contextPath}/accountUploadMultipleVehicle.do?download=y";
  document.fileUploadForm.submit();
}
function doCancel()
{
  document.fileUploadForm.action = "${pageContext.request.contextPath}/accountVehiclesAndTags.do";
  document.fileUploadForm.submit();
}
function enableUpload()
{

 if(stringTrim(document.getElementById('file_path').value).length==""){
       document.getElementById('upload_button').disabled  = true;
  }else
     document.getElementById('upload_button').disabled  = false;
}
function stringTrim(strToTrim) {
	return(strToTrim.replace(/^\s+|\s+$/g, ''));
}

function downloadPDF() {
    var url = "${pageContext.request.contextPath}/accountMultipleVehicleReport.do";
    window.open(url);
}

function downloadme(){
 document.fileUploadForm.action = "${pageContext.request.contextPath}/accountUploadMultipleVehicle.do?inst=y";
  document.fileUploadForm.submit();
}

function proceedToContinue(){
  document.fileUploadForm.action = "${pageContext.request.contextPath}/accountUploadMultipleVehicle.do?success=true";
  document.fileUploadForm.submit();
}

</script>
