<%@ include file="/jsp/common/Taglibs.jsp"%>





<table class="data-table" id="ccTable">
									<thead>
										<tr>
											<th scope="col">Row Index</th>
											<th scope="col">Reason</th>
											<th scope="col">License Plate</th>
											<th scope="col">License State</th>
											<th scope="col">Error Message</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${errorVehicleRecords}" var="item" varStatus="i">
											<tr>
											<td>${i.index + 1}</td>
											<td scope="col">${item.reason}</td>
											<td scope="col">${item.licPlate}</td>
											<td scope="col">${item.licState}</td>
											<td scope="col">${item.errorMsg}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

