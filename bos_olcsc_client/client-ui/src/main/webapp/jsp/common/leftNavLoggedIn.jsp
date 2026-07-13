<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<SCRIPT LANGUAGE="javascript">
     <c:if test="${top != null}">
        var menu_on=<c:out value="${top}"/>;
    </c:if>
     <c:if test="${top == null}">
        var menu_on=0;
    </c:if>

     <c:if test="${loc != null}">
        var arr=<c:out value="${loc}"/>
    </c:if>
     <c:if test="${loc == null}">
        var arr=01;
    </c:if>
     var p_subarrowon = "${pageContext.request.contextPath}/images/subarrow.gif";
     var p_subarrowoff = "${pageContext.request.contextPath}/images/pixel.gif";
     var p_mainOffsetY = 0;
     var p_mainOffsetYNs6 = -1;
     var p_onTopBgColor = "#618b8a";
     var p_offTopBgColor = "#adbfd1";
     var p_BgColorFolded = "#adbfd1";
     var p_BgColorShow = "#618b8a";
     var p_StretchHeight2 = 10;
     var p_buttonheight   = 24;

     menuheight = new Array()
     menuheight[0] = 48; //Account History
     menuheight[1] = 120; //Account Management
     menuheight[2] = 24; //Locations
     menuheight[3] = 120; //TollTag Help
     menuheight[4] = 24; //Contact TollTag
     menuheight[5] = 24; //Log Out
     var p_FoldNumber = <c:out value="${menuSize}"/>;

     function execute(top, loc, url) {
        var form = document.menuForm;
        form.url.value = url;
        form.loc.value = loc;
        form.top.value = top;
        form.submit();
//        window.location.href = "${pageContext.request.contextPath}/executeMenu.do?url="
//            + url + "&top=" + top + "&loc=" + loc;
     }
</script>
<script language="javascript" src="${pageContext.request.contextPath}/js/tollnav.js"/>


<script language="javascript">
function popUp(url) {
 var w =
"toolbar=0,location=0,directories=0,status=1,menubar=1,scrollbars=1,resizable=1,width=500,height=450";
sealWin=window.open(url,"win",w);
self.name = "mainWin";
}

</script>

<html:form action="executeMenu">
    <html:hidden property="url"/>
    <html:hidden property="top"/>
    <html:hidden property="loc"/>
</html:form>

<table  width="149" bgcolor="#adbfd1" height="100%" cellpadding="0" cellspacing="0" border="0">
    <tr valign="top">
        <td id="stretchme">
            <table width="149"  height="100%" cellpadding="0"  cellspacing="0" border="0">
                <tr valign="top">
                        <td  height="100%" width="149" >

                            <div id="divCont" style="position:relative;left:0;top:0;height:100%;">
                                <table border="0" bgcolor="#adbfd1" height="100%" width="149"  cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td >
                                            <c:if test="${menus != null}">
                                                <logic:iterate id="menu" name="menus" indexId="menuCtr" scope="request">
                                                    <div id="divTop<c:out value='${menuCtr}'/>" class="clTop" style="width:149px; height:24px;">

                                                        <table border="0" cellspacing="0" cellpadding="0" height="24" width="149" class="border">
                                                            <tr valign="middle">
                                                                <td width="23" height="24" valign="middle">
                                                                    <a href="javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuCtr}'/><c:out value='${menuItemCtr+1}'/>, <c:out value='${menu.itemUrl}'/>)"
                                                                    <c:if test="${menu.menuItems != null}">
                                                                        onclick="menu(<c:out value='${menuCtr}'/>);return false"
                                                                    </c:if>
                                                                    onfocus="this.blur()"><img src="${pageContext.request.contextPath}/images/pixel.gif" name="imgA<c:out value='${menuCtr}'/>" width="5" height="1" alt="" border="0">&#187;</a>
                                                                </td>
                                                                <td valign="middle" height="24" width="126"><a href="javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuCtr}'/><c:out value='${menuItemCtr+1}'/>, '<c:out value='${menu.itemUrl}'/>')"
                                                                    <c:if test="${menu.menuItems != null}">
                                                                        onclick="menu(<c:out value='${menuCtr}'/>); return false"
                                                                    </c:if>
                                                                    onfocus="this.blur()"> <bean:write name="menu" property="itemLabel"/></a>
                                                                </td>
                                                            </tr>
                                                        </table>

                                                        <div id="divSub<c:out value='${menuCtr}'/>" class="clSub">
                                                            <table border="0" bgcolor="#456362" cellspacing="0" cellpadding="0" width="149">
                                                                <c:if test="${menu.menuItems != null}">
                                                                    <logic:iterate id="menuItem" name="menu" property="menuItems" indexId="menuItemCtr">
                                                                        <tr height="24">
                                                                            <td width="23" height="24" valign="middle"
                                                                                <c:if test="${menuItemCtr == 0}">
                                                                                    background="${pageContext.request.contextPath}/images/2dkGrdrop.gif"
                                                                                </c:if>
                                                                                <c:if test="${menuItemCtr > 0}">
                                                                                    class="border2a"
                                                                                </c:if>
                                                                                >
                                                                                <a HREF="javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuCtr}'/><c:out value='${menuItemCtr+1}'/>,'<c:out value='${menuItem.itemUrl}'/>')" class="subfont" onClick="menu(<c:out value='${menuCtr}'/>,<c:out value='${menuItemCtr}'/>);"><img
                                                                                src="${pageContext.request.contextPath}/images/pixel.gif" width="15" height="15" border="0" id="arr<c:out value='${menuCtr}'/><c:out value='${menuItemCtr+1}'/>"></a>
                                                                            </td>
                                                                            <td valign="middle" width="126"
                                                                                <c:if test="${menuItemCtr == 0}">
                                                                                    background="${pageContext.request.contextPath}/images/2dkGrdrop.gif"
                                                                                </c:if>
                                                                                <c:if test="${menuItemCtr > 0}">
                                                                                    class="border2a"
                                                                                </c:if>
                                                                            >
                                                                            <a href="javascript:execute(<c:out value='${menuCtr}'/>, <c:out value='${menuCtr}'/><c:out value='${menuItemCtr+1}'/>,'<c:out value='${menuItem.itemUrl}'/>')"
                                                                                onClick="menu(0,01);"> <bean:write name="menuItem" property="itemLabel"/> </a>
                                                                            </td>
                                                                        </tr>
                                                                    </logic:iterate>
                                                                </c:if>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </logic:iterate>
                                            </c:if>

                                </td>
                                </tr>
                            </table>

                        </div><!-- Here ends the foldoutmenu. -->

                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="bottom" height="100%">
            <table border="0" width="149" cellpadding="0" cellspacing="0">
                <tr valign="top">
                    <td width="149" height="10"><img src="${pageContext.request.contextPath}/images/pixelBl.gif" width="149"
                        height="10" border="0">
                    </td>
                </tr>
                <tr valign="center">
                    <td width="149" height="92">
                        <!--Online2 Tagstore Server Certificate 5-15-02 ~ 5-14-02 (Organizational Name = NTTA2)-->
                        <script src=https://seal.verisign.com/getseal?host_name=tagstore.ntta.org&size=L&use_flash=NO&use_transparent=NO></script>
                    </td>
                </tr>
                <tr valign="top">
                    <td width="149" height="19"><img src="${pageContext.request.contextPath}/images/btmGr.gif" width="149" height="19"
                        border="0">
                    </td>
                </tr>
                <tr valign="top">
                    <td width="149" height="34"><img src="${pageContext.request.contextPath}/images/dots.gif" width="149" height="34"
                        border="0">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr valign="top">
        <td width="148" height="2" bgcolor="#ffffff"><img src="${pageContext.request.contextPath}/images/pixel.gif" border="0" height="2" width="148">
        </td>
    </tr>
</table>
