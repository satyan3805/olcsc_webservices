<%@ include file="/jsp/common/Taglibs.jsp" %>

<tr>
		<td class="left"></td>
		<td class="content">

<c:set var="step">
    <fmt:message key="label.step"/>
</c:set>

<div class="steps">
			<span><fmt:message key="label.steps"/>:</span>
			<ul>
				<li class="step1-taken">${step} 1</li>
				<li class="step2-taken">${step} 2</li>
				<li class="step3-taken">${step} 3</li>
				<li class="step4-taken">${step} 4</li>
				<li class="step5-taken">${step} 5</li>
				<li class="step6-here">${step} 6</li>
				<li class="step7">${step} 7</li>																					
			</ul>
		</div>
		
		<br class="clear">


		</td>		
		<td class="right"></td>		
	</tr>
        
<tiles:insert name="body" ignore="true"/>
        