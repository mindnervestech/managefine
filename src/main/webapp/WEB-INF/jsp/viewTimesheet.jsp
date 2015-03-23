<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#custom-modal-container{
		/* width: 950px !important; */
		left: 32% !important;
		top: 20%;
	}

	.formClass{
		padding: 0 30px;
	}
	
	.timesheetFirstRow{
		padding: 2% 5% 4%;
		margin-bottom: 0%;
		border-bottom: 1px solid #eee;
	}
	
	.timesheetFirstRow p{
		float: left;
		margin-right: 8%;
	}
	
	.timesheetSecondRow{
		margin: 2% 0%;
		width: 100%;
	}
	
	.timesheetSecondRow td{
		padding: 1% 3% !important;
		border-right: 1px solid #d5d5d5;
		text-align: center; 
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		border-left: 1px solid #eee;

	}
	
	.timesheetSecondRow td:LAST-CHILD{
		border-right: 1px solid #eee;
	}
	
	.action{
		margin: 2% 0;
		float: right;
	}
	
	.modal-footer{
		padding: 15px 15px 5px !important;
		text-align: left;
		background-color: none !important;
		margin-top: 15px;
	}
	
	.modal-header .close{
		margin-right: -25px;
	}
</style>

<div class="formClass">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4>Timesheet of ${user.firstName} ${user.lastName}</h4>
	</div>

	<div class="timesheetFirstRow"> 
		<p style="display:none;"><b>Timesheet ID :</b> <span id="timesheetID">${timesheet.id}</span></p> 
	
		<p><b>Week Of Year :</b> ${timesheet.weekOfYear}</p>
		
		<p><b>Status :</b> ${timesheet.status}</p>
		
		<p><b>Timesheet With</b> : ${timesheet.timesheetWith.firstName} ${timesheet.timesheetWith.lastName}</p>
	</div>

	<table class="timesheetSecondRow">
		<thead>
			<tr>
				<td style="width:15%">Project Code</td>
				<td style="width:15%">Task Code</td>
				<td style="width:10%">Mon</td>
				<td style="width:10%">Tue</td>
				<td style="width:10%">Wed</td>
				<td style="width:10%">Thu</td>
				<td style="width:10%">Fri</td>
				<td style="width:10%">Sat</td>
				<td style="width:10%">Sun</td>
				<td style="width:10%">Total Hrs</td>
				<td style="width:10%">is Overtime</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${timesheet.timesheetRows}">
				<tr>
					<td>${row.projectCode}</td>
					<td>${row.taskCode}</td>
					<td>
						<c:choose>
							<c:when test="${row.mon == null}">
								0
							</c:when>
							<c:otherwise>
								${row.mon}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.tue == null}">
								0
							</c:when>
							<c:otherwise>
								${row.tue}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.wed == null}">
								0
							</c:when>
							<c:otherwise>
								${row.wed}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.thu == null}">
								0
							</c:when>
							<c:otherwise>
								${row.thu}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.fri == null}">
								0
							</c:when>
							<c:otherwise>
								${row.fri}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.sat == null}">
								0
							</c:when>
							<c:otherwise>
								${row.sat}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.sun == null}">
								0
							</c:when>
							<c:otherwise>
								${row.sun}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.totalHrs == null}">
								0
							</c:when>
							<c:otherwise>
								${row.totalHrs}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.overTime == null}">
								No
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${row.overTime == true}">
										Yes
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="action">
		<input class="btn btn-warning" type="button" id="approveTimeSheet" value="Approve">
		<input class="btn btn-warning" type="button" id="rejectTimeSheet" value="Reject">
	</div>	
</div>

<script>
	$("#approveTimeSheet").click(function(){
		$.get('${pageContext.request.contextPath}/timesheet/approveTimesheetsOk', {id: $('#timesheetID').html()}, 
				function(response) {
					_dataType = JSON.parse(response);
					$("#custom-modal-container").modal('hide');
					$.pnotify({
						history:false,
				        text: _dataType.message
				    });
					My_TimeSheet_Approval_Bucketadd_SearchGrid.doSearch();
		});
	});

	$("#rejectTimeSheet").click(function(){
		$.get('${pageContext.request.contextPath}/timesheet/rejectTimesheetsOk', {id: $('#timesheetID').html()}, 
				function(response) {
			_dataType = JSON.parse(response);
			$("#custom-modal-container").modal('hide');
			$.pnotify({
				history:false,
		        text: _dataType.message
		    });
			My_TimeSheet_Approval_Bucketadd_SearchGrid.doSearch();
		});
	});
</script>

