<%@ include file="/jsp/common/Taglibs.jsp" %>
<tiles:useAttribute name="pageTitleKey"  id="pageTitleKey" classname="java.lang.String"/>
<tiles:useAttribute name="introductionKey" id="introductionKey" classname="java.lang.String"/>
<tiles:useAttribute name="panelTitleKey" id="panelTitleKey" classname="java.lang.String" ignore="true" />
<tiles:useAttribute name="panelIntroductionKey" id="panelIntroductionKey" classname="java.lang.String" ignore="true"/>

<table width="589"  cellpadding="0" cellspacing="0" border="0" bgcolor="#FFFFFF" style="height:350px">
    <tr valign="top"><td width="1"><img src="images/pixel.gif" border="0" height="457" width="1" alt=""></td>
        <td bgColor="#ffffff" width="584">
            <br/>
            
            <center>
            
            <b><big><bean:message key="${pageTitleKey}"/></big></b>
            <p/>
            <p/>
            
            <table border="0" id="loginTable" width="80%" class="bdBdy2" cellpadding="3" cellspacing="0">
                <tr>
                    <td>
                        <bean:message key="${introductionKey}"/>
                        <br/>
                    </td>
                </tr>
            </table>
            
            <c:if test="${panelTitleKey!=null}">
                <table border="0" id="setupTable" width="80%" class="bdBdy2" style="border:1px solid #cccccc;" cellpadding="3" cellspacing="0" >
                    <tr>
                        <td bgcolor="#cccccc">
                            <span style="font-weight: bold; font-family: arial, helvetica, sans-serif; color:#0C2D83; font-size:12px;">
                                <bean:message key="${panelTitleKey}"/>
                            </span>
                        </td>
                        <td align="right" bgcolor="#cccccc">
                            <span style="font-family: arial, helvetica, sans-serif; color:#FF0000; font-size:12px;">
                                * = <fmt:message key="label.required.field"/>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center" style="background:#eee; border:4px solid white;">
                            <bean:message key="${panelIntroductionKey}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><br/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <tiles:insert name="content" flush="false"/>
                            <br>
                        </td>
                    </tr>
                    
                </table>
                <br>
                <br>
            </c:if>
            
            </center>
            
        </td>
    </tr>
</table>  
