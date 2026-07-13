<%-- 
  - Author(s): Noel Ternida
  - Date: March 27, 2006
  - Copyright Notice: Electronic Transaction Consultants
  - @(#)
  - Description: JSP fragment of the menu page.
  --%>

<%@ include file="/jsp/common/Taglibs.jsp" %>

<script language="JavaScript" type="text/javascript">
     <c:if test="${currentMenu.map.top != null  && currentMenu.map.top != 'undefined'}">
        var top_menu="${currentMenu.map.top}";
    </c:if>
     <c:if test="${currentMenu.map.top == null  || currentMenu.map.top == 'undefined'}">
        var top_menu=-1;
    </c:if>

     <c:if test="${currentMenu.map.loc != null && currentMenu.map.loc != 'undefined'}">
        var loc=${currentMenu.map.loc};
    </c:if>
     <c:if test="${currentMenu.map.loc == null  || currentMenu.map.loc == 'undefined'}">
        var loc=-1;
    </c:if>

    var sub_menu = -1;
     <c:if test="${currentMenu.map.subMenu != null && currentMenu.map.subMenu != '' && currentMenu.map.subMenu != 'undefined'}">
        sub_menu=${currentMenu.map.subMenu};
    </c:if>

    foldinImg=new Image(); foldinImg.src="${pageContext.request.contextPath}/images/logged-in/bullet.gif"		//The image for the closed state.
    foldoutImg=new Image(); foldoutImg.src="${pageContext.request.contextPath}/images/logged-in/bullet-more.gif"	//The image for the open state.
    //Here are the images for the sub links.
    foldsubinImg=new Image(); foldsubinImg.src="${pageContext.request.contextPath}/images/logged-in/bullet.gif"		//The image for the "in" state.
    foldsuboutImg=new Image(); foldsuboutImg.src="${pageContext.request.contextPath}/images/logged-in/bullet-more.gif"	//The image for the "out" state.
    selectedImg=new Image(); selectedImg.src="${pageContext.request.contextPath}/images/logged-in/bullet-here.gif"	//The image for the selected state.
    
    mainmenus=<c:out value="${menuSize}"/>
    submenus=new Array(<c:out value="${arraySize}"/>) 
    subsubmenus=<c:out value="${subSize}"/> 
</script>        
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>

<script language="javascript">
     function execute(top, loc, url, subMenu) {
        var form = document.menuForm;
        form.url.value = url;
        form.loc.value = loc;
        form.top.value = top;
        form.subMenu.value = subMenu;
        form.submit();
     }
     
</script>

<html:form action="executeMenu">
    <input type="hidden" name="url" value="${currentMenu.map.url}"/>
    <input type="hidden" name="top" value="${currentMenu.map.top}"/>
    <input type="hidden" name="loc" value="${currentMenu.map.loc}"/>
    <input type="hidden" name="subMenu" value="${currentMenu.map.subMenu}"/>
</html:form>

<!-- Menu container -->
<div id="divFoldCont">
<table>
    <tr><td>
    <logic:iterate id="menu" name="menus" indexId="menuCtr" scope="request">
	<div id="divFold<c:out value='${menuCtr}'/>" class="clFold">
            <div id="separator">
                <a 
                    <c:if test="${menu.menuItems != null}">
                        href="execute.do" onclick="foldmenu(<c:out value='${menuCtr}'/>); return false;" 
                    </c:if>
                    <c:if test="${menu.menuItems == null}">
                        href='javascript:execute(<c:out value='${menuCtr}'/>, -1, "<c:out value='${menu.itemUrl}'/>")'  
                    </c:if>            
                    class="clFoldLinks" onfocus="if(this.blur)this.blur()"><img src="${pageContext.request.contextPath}/images/logged-in/bullet.gif" name="imgFold<c:out value='${menuCtr}'/>" alt="" border=0> <bean:write name="menu" property="itemLabel"/></a>
                    
                    <c:if test="${menu.menuItems != null}">
                        <logic:iterate id="menuItem" name="menu" property="menuItems" indexId="menuItemCtr">
                            <div id="divFoldSub<c:out value='${menuCtr}'/>_<c:out value='${menuItemCtr}'/>" class="clFoldSub">
                                <div id="separator" class="sub">     
                                    <c:if test="${menuItem.menuItems == null}">
                                        <a href='javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuItemCtr}'/>, "<c:out value='${menuItem.itemUrl}'/>")' class="clSubLinks" onfocus="if(this.blur)this.blur()"><img src="${pageContext.request.contextPath}/images/logged-in/bullet.gif" name="imgFold<c:out value='${menuCtr}'/>Sub<c:out value='${menuItemCtr}'/>"  alt="" border=0> <bean:write name="menuItem" property="itemLabel"/></a>
                                        <div id="divFoldSub<c:out value='${menuCtr}'/>_<c:out value='${menuItemCtr}'/>_0" class="clFoldSub2"></div>
                                    </c:if>
                                    <c:if test="${menuItem.menuItems != null}">
                                        <a href="#" onclick="subfoldmenu(<c:out value='${menuCtr}'/>,<c:out value='${menuItemCtr}'/>); return false" class="clSubLinks" onfocus="if(this.blur)this.blur()"><img src="${pageContext.request.contextPath}/images/logged-in/bullet.gif" name="imgFold<c:out value='${menuCtr}'/>Sub<c:out value='${menuItemCtr}'/>"  alt="" border=0> <bean:write name="menuItem" property="itemLabel"/></a><br/>
                                            <div id="divFoldSub<c:out value='${menuCtr}'/>_<c:out value='${menuItemCtr}'/>_0" class="clFoldSub2">
                                                <logic:iterate id="menuItemDetail" name="menuItem" property="menuItems" indexId="menuItemDetailCtr">
                                                        <div id="separator" class="sub2">
                                                            <a href='javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuItemCtr}'/>, "<c:out value='${menuItemDetail.itemUrl}'/>", <c:out value='${menuItemDetailCtr}'/>)' class="clSubLinks2" onfocus="if(this.blur)this.blur()"><img src="${pageContext.request.contextPath}/images/logged-in/bullet.gif" name="imgFold<c:out value='${menuCtr}'/>Sub<c:out value='${menuItemCtr}'/>Sub<c:out value='${menuItemDetailCtr}'/>" alt=""/>&nbsp;<bean:write name="menuItemDetail" property="itemLabel"/></a>
                                                        </div>
                                                </logic:iterate>
                                            </div>
                                    </c:if>
                                </div>
                            </div>
                        </logic:iterate>
                    </c:if>
            </div>
	</div>
    </logic:iterate>
    </td></tr>
</table>
</div>


