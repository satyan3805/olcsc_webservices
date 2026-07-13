<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
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
		<td id="data" class="content">
		
	<!-- IMPORTANT: If any of the cells below have no data, make sure to insert an &nbsp; (e.g. see Account Summary below)
	otherwise the cell will be invisible, and the border will break in that cell. -->
	
			<!-- begin statement for current month -->
			
                                <tiles:get name="contactUsContentTable"/>         
		</td>		
		<!-- end tabular data-->
    </td>
    <td class="right"></td>
</tr>	
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
