<%-- 
  - Author(s): Noel Ternida
  - Date: April 26, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: Layout for printing a page.
  --%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/jsp/common/Taglibs.jsp" %>

<tiles:useAttribute name="useSmallFont" id="useSmallFont" classname="java.lang.String"/>

<html>
<head>
	<title>NTTA <fmt:message key="label.customerServiceCenter"/></title>
	<style type="text/css">	
		@import url(${pageContext.request.contextPath}/css/printerFriendly.css);
                @import url(${pageContext.request.contextPath}<fmt:message key="css.localespecificstyles.printerFriendly"/>);
	</style>
	
	<!-- 
			If no JavaScript is used, use the following empty JavaScript statement
			to avoid the Flash Of Unstyled Content (FOUC) bug in IE, where the content
			briefly appears without the CSS styling.	
	-->
	<script type="text/javascript">
        </script>
	<script type="text/javascript">
                var readFontSize = true;
                function fontsize(size)
                {
                    if (size != null && size.length>0)
                        size = "-"+size;
              
                    changeFont(size);
                    createCookie("fontSize", size);           
            
                }
                
                function changeFont(size)
                {
                    var styleName = "content"+size;
                    var table = document.getElementById("record");
                    var tds = table.getElementsByTagName("td");
                    
                    for (var i=0; i<tds.length;i++)
                    {
                            var tdClassName = tds[i].className;
                            if (tdClassName != null && tdClassName.indexOf("hideColumn")==-1) {
                                    tds[i].className = styleName;
                            }
                    }

                    var tds = table.getElementsByTagName("th");
                    for (var i=0; i<tds.length;i++)
                    {
                            var tdClassName = tds[i].className;
                            if (tdClassName != null && tdClassName.indexOf("hideColumn")==-1) {
                                    tds[i].className = styleName;
                            }
                    }
            
                    styleName = "text"+size;
            
                    var inputs = table.getElementsByTagName("input");
                    for (var i=0; i<inputs.length;i++)
                    {
                            var inputClassName = inputs[i].className;
                            if (inputClassName != null && inputClassName.indexOf("text")==0)
                                    inputs[i].className = styleName;
                    }
                    
                    var selects = table.getElementsByTagName("select");
                    for (var i=0; i<selects.length;i++)
                    {
                            var selectClassName = selects[i].className;
                            if (selectClassName != null && selectClassName.indexOf("text")==0)
                                    selects[i].className = styleName;
                    }

                }
    </script>
</head>

<body>
     <div style="text-align:center"> 
        <br/>
        <input name="print" type="button" class="button" value="<fmt:message key="button.print"/>" onclick="javascript: window.print()"/>&nbsp;&nbsp;
        <input name="close" type="button" class="button" value="<fmt:message key="button.closewindow"/>" onclick="javascript: window.close()"/>
        <hr/>
        <br/>
        <table cellspacing="0">
            <tiles:get name="content"/>        
        </table>
        
    </div>

<c:if test="${useSmallFont == 'true'}">
<script type="text/javascript">
    changeFont('-small');
    readFontSize = false;
</script>
</c:if>
</body>
</html>
