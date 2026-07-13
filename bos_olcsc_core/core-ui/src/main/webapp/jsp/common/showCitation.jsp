<%@ include file="/jsp/common/Taglibs.jsp" %>

        <table width="100%" cellpadding="0" cellspacing="0" id="data-table">

            <tr>
                <td></td>
                <td class="panel-content">
                   
                        <c:if test="${citationDetail != null}">
                           
                                <display:table name="citationDetail" cellspacing="0" 
                                    pagesize="500" requestURI="/showCitation.do" id="alertRow"
                                    decorator="com.etcc.csc.decorator.GroupRowColorTableDecorator">
                                    <display:column title="Court Name" sortable="false">
                                        ${alertRow.courtName}
                                    </display:column>
                                    <display:column title="Court Phone Number" sortable="false">
                                        ${alertRow.phoneNumber}
                                    </display:column>
                                    <display:column title="Judge" sortable="false">
                                        ${alertRow.judge}
                                    </display:column>
                                    <display:column title="Court Date" sortable="false" class="data-nowrap">
                                         <fmt:formatDate value="${alertRow.courtDate}" pattern="MM/dd/yyyy HH:mm"/>
                                    </display:column>
                                    <display:column title="Court Address" sortable="false">
                                        ${alertRow.overallAddress}
                                    </display:column>
                                    <display:column title="City, State" sortable="false">
                                        ${alertRow.cityStateZip}
                                    </display:column>
                                </display:table>
                        
                        </c:if>
                        <c:if test="${alerts == null}">
                            <fmt:message key="accountHome.noAlert"/>
                        </c:if>
                  
                </td>		
               
            </tr>
         
        </table>

<script type="text/javascript">
    changeFont('-small');
    readFontSize = false;
</script>