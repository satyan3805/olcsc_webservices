<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>
 <%--       <fieldset>
		<div id="fieldset-interior">

			<label for="search">Search the site</label>
			<input type="text" class="textfield" id="search" name="search" />
			<input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/search.gif" value="Search" />

		</div> <!-- end of fieldset-interior -->
	</fieldset>
--%>
        
        
<!-- Google CSE Search Box Begins -->
<form id="searchbox_000587684621316683554:vnbkuxm5hto" action="${appDelegate.domainName}/search/">
<fieldset>
<div id="fieldset-interior">
<input type="hidden" name="cx" value="000587684621316683554:vnbkuxm5hto" />
<input type="hidden" name="cof" value="FORID:11" />
<label for="search">Search the site</label>
<input type="text" style="width:120px" class="textfield" id="search" name="q" />
<input type="image" class="image-based submit-button" src="${pageContext.request.contextPath}/meta/media/buttons/search.gif" value="Search" name="sa" />
</div> <!-- end of fieldset-interior -->
</fieldset>
</form>
<!-- Google CSE Search Box Ends --> 